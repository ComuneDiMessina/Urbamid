using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Geodatabase;
using NLog;
using NLog.Config;
using NLog.Targets;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Web;
using System.Windows.Forms;
using System.Xml;
using UrbamidAddIn_V10_2;
using UrbamidAddIn_V10_2.Dto;
using UrbamidAddIn_V10_2.Utility;

namespace UrbamidAddIn
{
    public partial class frmManageGeoServer : Form
    {
        private static readonly NLog.Logger logger = NLog.LogManager.GetCurrentClassLogger();
        //XmlDocument xmlDoc;
        List<string> nodesList;
        
        
        private bool isEditLayerGroup = false;

        public string UserName { get; internal set; }
        public string Password { get; internal set; }
        public string Server { get; internal set; }
        public object WorkSpace { get; internal set; }
        public string ShapeFile { get; internal set; }
        public string WrapperUrl { get; set; }
        public string DataStore { get; set; }
        public string DefaultWorkSpace { get; set; }

        public string linkDettaglioFeatureType { get; set; }
        private List<GenericLayer> layersDisponibili = new List<GenericLayer>();

        BindingSource bsSelezionati = new BindingSource();
        List<GenericLayer> listaSelezionati = new List<GenericLayer>();

        BindingSource bsDisponibili = new BindingSource();
        List<GenericLayer> listaDisponibili = new List<GenericLayer>();

        private TreeNode nodeOpened { get; set; }

        public frmManageGeoServer()
        {
            InitializeComponent();
            
            var configuration = new NLog.Config.LoggingConfiguration();
            var logfile = new NLog.Targets.FileTarget("logfile")
            {
                FileName = "C:\\logs\\Urbamid_" + DateTime.Now.Date.ToString("yyyy_MM_dd") + ".log",
                Layout = "${longdate} ${logger} Exception: ${exception:format=StackTrace}${newline} Target Site:  ${exception:format=Message}${newline} Message: ${message}"
            };

            configuration.AddRule(NLog.LogLevel.Trace, NLog.LogLevel.Info, logfile);
            configuration.AddRule(NLog.LogLevel.Trace, NLog.LogLevel.Error, logfile);
            configuration.AddRule(NLog.LogLevel.Trace, NLog.LogLevel.Debug, logfile);
            NLog.LogManager.Configuration = configuration;
                   
        }

        #region MenuTreeView

        public void InitializeMenuTreeView()
        {
            RestApiGeoServer service = new RestApiGeoServer();
            Utility util = new Utility();
            TreeNode layersNode = new TreeNode("Layers Cartografici");
            try
            {


                //WRAPPER
                //GetListaLayerGroups_InDto inGetListaGrupppi = WrapperTOA.assemblerGetGruppi(this.WrapperUrl, this.DefaultWorkSpace);
                //GetListaLayerGroups_OutDto outGetGruppi = service.getListaLayerGroups(inGetListaGrupppi);
                //if (outGetGruppi == null || !outGetGruppi.IsSuccess)
                //{
                //    util.message(TipoMessaggio.ERRORE, outGetGruppi.Message);
                //    return;
                //}
                //List<LayerGroup> listaAreeTematiche = outGetGruppi.gruppi.layerGroup.Where(p => p.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX)).ToList();


                BaseDto dtoBase = new BaseDto() { baseUrl = this.Server, password = this.Password, username = this.UserName, workspace = this.DefaultWorkSpace };
                GetGroupLayerDto inDto = new GetGroupLayerDto() { baseUrl = dtoBase.baseUrl, password = dtoBase.password, username = dtoBase.username, workspace = dtoBase.workspace };
                LayerGroupDto listaLayers = service.getLayergroups(inDto, logger);
                if (listaLayers == null)
                    return;

                List<LayerGroup> listaAreeTematiche = listaLayers.layerGroups.layerGroup.ToList().Where(p => p.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX)).ToList();
                listaAreeTematiche = listaAreeTematiche.OrderBy(p => p.name).ToList();

                if (listaAreeTematiche != null)
                {
                    listaAreeTematiche = listaAreeTematiche.OrderBy(p => p.name).ToList();
                    nodesList = new List<string>();
                    foreach (var item in listaAreeTematiche)
                    {
                        //per ogni gruppo recupero il titolo dal dettaglio

                        TreeNode nodeDetails = new TreeNode();
                        nodeDetails.Name = item.name;
                        inDto.nomeNodo = item.name;
                        LayerDTO dettaglioGroup = service.getLayergroupsByName(inDto, logger);
                        if (dettaglioGroup != null && dettaglioGroup.layerGroup != null && !String.IsNullOrEmpty(dettaglioGroup.layerGroup.title))
                            nodeDetails.Text = dettaglioGroup.layerGroup.title;
                        else
                            nodeDetails.Text = "Non disponibile";
                        //WRAPPER
                        //GetDettaglioGruppo_InDto inGetGruppo = WrapperTOA.assemblerDettaglioGruppo(this.WrapperUrl, this.DefaultWorkSpace, item.name);
                        //GetDettaglioGruppo_OutDto outGetGruppo = service.getDettaglioGruppo(inGetGruppo);

                        //if (outGetGruppo == null || !outGetGruppo.IsSuccess)
                        //    nodeDetails.Text = "Non disponibile";
                        //else
                        //    nodeDetails.Text = outGetGruppo.Gruppo.title;

                        nodeDetails.Nodes.Add(Costanti.VOID_MENU).Name = Costanti.VOID_MENU;
                        layersNode.Nodes.Add(nodeDetails);
                    }
                }

                this.menuTreeView.Nodes.Add(layersNode);

            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "InitializeMenuTreeView");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore inizializzazione menu - verificare la connessione a Geoserver");
                return;
            }
        }

        private void menuTreeView_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            Utility util = new Utility();

            try
            {
                this.Cursor = Cursors.WaitCursor;

                cmsMenu.Items.Find("tsmImporta", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmImportaWMS", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmPubblicaStile", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmModifica", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmDeleteStoreWMS", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmCreaLayerGroup", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmCreaTematismo", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmCreaAreaTematica", true).FirstOrDefault().Visible = false;
                cmsMenu.Items.Find("tsmModificaGroup", true).FirstOrDefault().Visible = false;


                if (e.Button == MouseButtons.Right)
                {
                    if (menuTreeView.SelectedNode.Name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX))
                    {
                        cmsMenu.Items.Find("tsmCreaTematismo", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmModificaGroup", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmDeleteStoreWMS", true).FirstOrDefault().Visible = true;

                    }
                    else if (menuTreeView.SelectedNode.Name.EndsWith(Costanti.TEMATISMO_SUFFIX))
                    {
                        cmsMenu.Items.Find("tsmImporta", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmImportaWMS", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmDeleteStoreWMS", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmCreaLayerGroup", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmModificaGroup", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                    }
                    else if (menuTreeView.SelectedNode.Name.EndsWith(Costanti.LAYER_GROUP_SUFFIX))
                    {
                        cmsMenu.Items.Find("tsmImporta", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmImportaWMS", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmDeleteStoreWMS", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmModificaGroup", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                    }
                    else if (menuTreeView.SelectedNode.Parent == null)
                    {
                        cmsMenu.Items.Find("tsmCreaAreaTematica", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmDeleteStoreWMS", true).FirstOrDefault().Visible = true;
                    }    
                    else
                    {
                        cmsMenu.Items.Find("tsmPubblicaStile", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmModifica", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmDeleteStoreWMS", true).FirstOrDefault().Visible = true;
                    }
                  
                    cmsMenu.Show(new System.Drawing.Point(MousePosition.X, MousePosition.Y));
                }
                else
                {
                    RestApiGeoServer service = new RestApiGeoServer();
               
                    if (e.Node.LastNode == null)
                        return;

                    nodeOpened = e.Node.Parent;

                    this.menuTreeView.SelectedNode = e.Node;
                    if (e.Node.LastNode.Name == Costanti.VOID_MENU)
                    {
                        BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);
                        GetGroupLayerDto inDto = TOA.assemblaGetDettaglioGruppo(dtoBase, e.Node.Name);
                        LayerDTO dettaglioGroup = service.getLayergroupsByName(inDto, logger);

                        List<Published> listaFigli = new List<Published>();
                        if (dettaglioGroup != null && dettaglioGroup.layerGroup != null && dettaglioGroup.layerGroup.publishables != null)
                            listaFigli = dettaglioGroup.layerGroup.publishables.published.ToList();

                        listaFigli.OrderBy(p => p.name);
                        foreach (Published item in listaFigli)
                        {
                            if (!item.name.StartsWith(String.Concat(this.DefaultWorkSpace, ":", Costanti.DEFAULT_LAYER)))
                            {
                                TreeNode nodeDetails = new TreeNode();
                                string nomeNodo = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                nodeDetails.Name = nomeNodo;
                                inDto.nomeNodo = nodeDetails.Name;
                                if (item.tipoPublished == "layerGroup")
                                {

                                    LayerDTO gruppo = service.getLayergroupsByName(inDto, logger);
                                    if (gruppo == null)
                                        return;

                                    nodeDetails.Name = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                    nodeDetails.Text = gruppo.layerGroup.title;
                                    nodeDetails.Nodes.Add(Costanti.VOID_MENU).Name = Costanti.VOID_MENU;
                                    e.Node.Nodes.Add(nodeDetails);

                                }
                                else
                                {

                                    GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(dtoBase, item.href);

                                    LayerDetailDto dettaglio = service.getDettaglioLayer(inDettaglio, logger);

                                    GetFeatureTypeDto input = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                                    if (dettaglio.layer.type == Costanti.TIPO_LAYER_RASTER)
                                    {
                                        CoverageDto outDto = service.getFeatureTypeRasterByNomeLayer(input, logger);
                                        nodeDetails.Text = outDto.coverage.title;

                                    }
                                    else if (dettaglio.layer.type == Costanti.TIPO_LAYER_WMS) 
                                    {
                                        WmsFeatureDto outDto = service.getFeatureLayerWMS(input, logger);
                                        if (outDto != null && outDto.wmsLayer != null && !String.IsNullOrEmpty(outDto.wmsLayer.title))
                                        {
                                            nodeDetails.Text = outDto.wmsLayer.title;
                                            if (item.name.Contains(Costanti.LAYERDB_PREFIX))
                                                nodeDetails.Text += " - DB";

                                        }
                                    }
                                    else
                                    {
                                        FeaturesTypeDto outDto = service.getFeatureTypeByNomeLayer(input, logger);
                                        if (outDto != null && outDto.featureType != null && !String.IsNullOrEmpty(outDto.featureType.title))
                                        {
                                            nodeDetails.Text = outDto.featureType.title;
                                            if (item.name.Contains(Costanti.LAYERDB_PREFIX))
                                                nodeDetails.Text += " - DB";

                                        }
                                    }

                                    nodeDetails.Name = item.name.Replace(this.DefaultWorkSpace + ":", "");

                                    e.Node.Nodes.Add(nodeDetails);
                                }

                            }
                        }

                        if (e.Node.Nodes.Count > 1)
                            e.Node.Nodes.RemoveByKey(Costanti.VOID_MENU);
                    }
                }

            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "MenuTreeView Open");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Apertura Nodi Geoserver");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }
        

        #endregion

        #region ImportaShapeFile

        private void btnImportaShape_Click(object sender, EventArgs e)
        {
            Utility util = new Utility();
            try
            {
                this.Cursor = Cursors.WaitCursor;
                RestApiGeoServer service = new RestApiGeoServer();
                int numLayer = 0;
         
                BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);
                String nomeNodo = menuTreeView.SelectedNode.Name;
                
                if (clbLayerDaImportare.Items.Count == 0)
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.ERRORE, "Nessun layer selezionato!");
                    return;
                }

                foreach (var layerSelezionato in clbLayerDaImportare.CheckedItems)
                {
                    if (layerSelezionato.ToString() == "Seleziona\\Deseleziona tutti")
                        continue;

                    string messaggio = string.Empty;
                    string nomeLayer = string.Empty;
                    string nomeShapeFile = string.Empty;
                   

                    nomeShapeFile = util.getShapeFileByLayerName(layerSelezionato.ToString());
                    nomeLayer = Path.GetFileNameWithoutExtension(nomeShapeFile);
                    if (string.IsNullOrEmpty(nomeShapeFile) || string.IsNullOrEmpty(nomeLayer))
                    {
                        
                        util.message(TipoMessaggio.ERRORE, "Nessuno shape trovato");
                        break;
                    }


                    if (Path.GetDirectoryName(nomeShapeFile).ToUpper().EndsWith(".GDB") || nomeShapeFile.ToUpper().EndsWith(".TIF") ||
                        nomeShapeFile.ToUpper().EndsWith(".TIFF"))
                    {
                        //E' un raster
                        if (importaFileRaster(nomeShapeFile, dtoBase, logger))
                            util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                        else
                            util.message(TipoMessaggio.ERRORE, "Si è verificato un'errore nell'importazione del layer " + layerSelezionato.ToString());

                        continue;
                    }
                    else
                    {

                        ZipDto inputZip = new ZipDto
                        {
                            pathFolder = Path.GetDirectoryName(nomeShapeFile),
                            nomeLayer = nomeLayer,
                            nomeFile = nomeLayer
                        };
                        if (!util.createZipFile(inputZip))
                        {
                            util.message(TipoMessaggio.ERRORE, "Zip non creato per layer " + layerSelezionato.ToString());
                            continue;
                        }

                        GeoServerDto dto = new GeoServerDto();
                        dto = TOA.assemblaGeoServerDTO(dtoBase, String.Empty);
                        dto.pathFile = Path.Combine(inputZip.pathFolder, nomeLayer + ".zip");
                        dto.layerName = nomeLayer.Replace(" ", "_");
                        dto.storeName = nomeLayer.Replace(" ", "_");

                        bool esisteLayerGEO, esisteLayerDB = false;


                        //chiamata al wrapper 
                        GetDettaglioLayer_InDto inputGet = WrapperTOA.assembler(this.WrapperUrl, nomeLayer, this.DefaultWorkSpace, this.ckShapeDB.Checked);
                        GetDettaglioLayer_OutDto outDettaglio = service.getDettaglioLayer(inputGet, logger);
                        if (!outDettaglio.IsSuccess)
                        {
                            util.message(TipoMessaggio.WARNING, "Errore Dettaglio Layer: " + layerSelezionato.ToString() + " " + outDettaglio.Message);
                            continue;
                        }
                        esisteLayerGEO = outDettaglio.Exist;

                        inputGet = WrapperTOA.assembler(this.WrapperUrl, String.Concat("u_geo_", nomeLayer.ToLower()), this.DefaultWorkSpace, this.ckShapeDB.Checked);
                        outDettaglio = service.getDettaglioLayer(inputGet, logger);
                        if (!outDettaglio.IsSuccess)
                        {
                            util.message(TipoMessaggio.WARNING, "Errore Dettaglio Layer: " + layerSelezionato.ToString() + " " + outDettaglio.Message);
                            continue;
                        }
                        esisteLayerDB = outDettaglio.Exist;

                        //Caso in cui esiste già il layer su GeoServer
                        if (esisteLayerGEO || esisteLayerDB)
                        {

                            //this.Cursor = Cursors.Default;
                            //messaggio = String.Format("Attenzione, esiste già il layer {0}, si vuole sovrascriverlo?", dto.layerName);
                            //dialogResult = MessageBox.Show(messaggio, "ATTENZIONE", MessageBoxButtons.YesNo);
                            //if (dialogResult == DialogResult.No)
                            if (!ckbSovrascrivi.Checked)
                                continue;
                            else
                            {

                                this.Cursor = Cursors.WaitCursor;

                                if (esisteLayerDB)
                                {
                                    DeleteLayer_InDto inDelLayer = WrapperTOA.assemblerDeleteLayer(this.WrapperUrl, this.DefaultWorkSpace, String.Concat("u_geo_", nomeLayer.ToLower()));
                                    DeleteLayer_OutDto outDelLayer = service.deleteLayer(inDelLayer, logger);
                                    if (outDelLayer == null || !outDelLayer.IsSuccess)
                                    {
                                        this.Cursor = Cursors.Default;
                                        util.message(TipoMessaggio.ERRORE, outDelLayer.Message);
                                        continue;
                                    }
                                }

                                if (esisteLayerGEO)
                                {
                                    DeleteDatastoreDto delete = new DeleteDatastoreDto();
                                    delete.baseUrl = this.Server;
                                    delete.nomeDataStore = nomeLayer;
                                    delete.password = this.Password;
                                    delete.username = this.UserName;
                                    delete.workspace = this.DefaultWorkSpace;
                                    if (!service.deleteDataStore(delete, logger))
                                        continue;
                                }
                            }

                        }


                        if (this.ckShapeDB.Checked)
                        {
                            UploadShape_InDto storeWrapperDto = WrapperTOA.assembler(this.WrapperUrl, Path.Combine(inputZip.pathFolder, nomeLayer + ".zip"));

                            UploadShape_OutDto resp_UploadShapeToDb = service.UploadShapeToDB(storeWrapperDto, logger);
                            if (resp_UploadShapeToDb == null || !resp_UploadShapeToDb.IsSuccess)
                            {
                                this.Cursor = Cursors.Default;
                                util.message(TipoMessaggio.ERRORE, resp_UploadShapeToDb.Message);
                                continue;
                            }

                            ConvertShapeToDb_InDto inConvert = WrapperTOA.assemblerConvert(this.WrapperUrl, nomeLayer);
                            ConvertShapeToDb_OutDto outConvert = service.convertShapeToDb(inConvert, logger);
                            if (outConvert == null || !outConvert.IsSuccess)
                            {
                                this.Cursor = Cursors.Default;
                                util.message(TipoMessaggio.ERRORE, outConvert.Message);
                                continue;
                            }

                            PublishTable_InDto inPublish = WrapperTOA.assembler(this.WrapperUrl, this.DataStore, nomeLayer, layerSelezionato.ToString(), this.ckShapeDB.Checked,
                                                            this.ckEstrazionePart.Checked, this.ckDefaultView.Checked, this.DefaultWorkSpace);

                            PublishTable_OutDto outPublish = service.publishTable(inPublish, logger);
                            if (outPublish == null || !outPublish.IsSuccess)
                            {
                                this.Cursor = Cursors.Default;
                                util.message(TipoMessaggio.ERRORE, outPublish.Message);
                                continue;
                            }
                        }
                        else
                        {
                            service.UploadShapeFile(dto, logger);
                           
                            // AGGIORNO LE FEATURE NEL VECCHIO MODO
                            GetFeatureTypeDto inGetFeature = new GetFeatureTypeDto();
                            inGetFeature.link = String.Concat(this.Server, Costanti.REST_SERVICE_PREFIX, this.DefaultWorkSpace,
                                "/datastores/", dto.layerName, "/featuretypes/", dto.layerName, ".json");

                            FeaturesTypeDto outGetFeature = service.getFeatureTypeByNomeLayer(inGetFeature, logger);

                            if (outGetFeature == null || outGetFeature.featureType == null)
                            {
                                this.Cursor = Cursors.Default;
                                util.message(TipoMessaggio.ERRORE, "Errore nel recupero della Feature!");
                                continue;
                            }
                            List<string> attributtiFissi = outGetFeature.featureType.keywords.chiave.ToList();
                            attributtiFissi.RemoveAll(p => p.Contains("@"));

                            outGetFeature.featureType.keywords.chiave = attributtiFissi;
                            outGetFeature.featureType.keywords.chiave.Add(String.Format("defaultView@{0}", this.ckDefaultView.Checked.ToString().ToLower()));
                            //Aggiungo le properties in visualizzazione
                            foreach (var item in outGetFeature.featureType.attributes.attribute)
                            {
                                if (!item.binding.ToUpper().Contains("GEOMETRY") && !item.name.ToUpper().Equals("THE_GEOM"))
                                {
                                    String keywords = string.Empty;

                                    keywords += "viewField_" + item.name + "@" + item.name;

                                    outGetFeature.featureType.keywords.chiave.Add(keywords);
                                }
                            }
                            outGetFeature.featureType.enabled = true;
                            outGetFeature.featureType.title = layerSelezionato.ToString();
                            outGetFeature.featureType.astratto = nomeLayer;


                            outGetFeature.featureType.srs = "EPSG:4326";
                            outGetFeature.featureType.projectionPolicy = "REPROJECT_TO_DECLARED";

                            EditLayerDto editDTO = new EditLayerDto
                            {
                                baseUrl = this.Server,
                                password = this.Password,
                                username = this.UserName,
                                workspace = this.DefaultWorkSpace,
                                layerDto = outGetFeature,
                                nomeLayer = nomeLayer
                            };

                            if (!service.UpdateLayer(editDTO, logger))
                            {
                                this.Cursor = Cursors.Default;
                                continue;
                            }

                        }

                        if (!ckShapeDB.Checked)
                            inputGet.nomeLayer = nomeLayer;
                        else
                            inputGet.nomeLayer = "u_geo_" + nomeLayer.ToLower();




                        outDettaglio = service.getDettaglioLayer(inputGet, logger);
                        if (!outDettaglio.IsSuccess)
                        {
                            util.message(TipoMessaggio.WARNING, outDettaglio.Message);
                            continue;
                        }

                        // aggiungo il layer creato al gruppo 
                        nomeNodo = nomeNodo.Replace("urbamid:", "");
                        GetGroupLayerDto input = new GetGroupLayerDto();
                        input.baseUrl = this.Server;
                        input.nomeNodo = nomeNodo;
                        input.password = this.Password;
                        input.username = this.UserName;
                        input.workspace = this.DefaultWorkSpace;

                        LayerDTO groupLayer = service.getLayergroupsByName(input, logger);

                        if (groupLayer != null)
                        {

                            while (groupLayer.layerGroup.publishables.published.Count !=
                                groupLayer.layerGroup.styles.style.Count)
                            {
                                if (groupLayer.layerGroup.publishables.published.Count > groupLayer.layerGroup.styles.style.Count)
                                {
                                    Style style = new Style();

                                    style.href = outDettaglio.layer.defaultStyle.href; //dettaglioLayer.layer.defaultStyle.href;
                                    style.name = outDettaglio.layer.defaultStyle.name;
                                    groupLayer.layerGroup.styles.style.Add(style);
                                }
                                else
                                {
                                    Published pubbl = new Published();
                                    pubbl.name = outDettaglio.layer.name.Replace("urbamid:", "");
                                    pubbl.tipoPublished = "layer";
                                    pubbl.href = dto.baseUrl + "/rest/workspaces/" + dto.workspace + "/layers/" + nomeLayer + ".json";
                                    groupLayer.layerGroup.publishables.published.Add(pubbl);

                                }
                            }
                            Published layer = new Published();
                            layer.name = outDettaglio.layer.name;
                            layer.tipoPublished = "layer";
                            layer.href = dto.baseUrl + "/rest/workspaces/" + dto.workspace + "/layers/" + outDettaglio.layer.name + ".json";
                            groupLayer.layerGroup.publishables.published.Add(layer);
                            //groupLayer.layerGroup.publishables.published = layer;

                            Style stile = new Style
                            {
                                href = outDettaglio.layer.defaultStyle.href,
                                name = outDettaglio.layer.defaultStyle.name
                            };
                            groupLayer.layerGroup.styles.style.Add(stile);
                            groupLayer.baseUrl = this.Server;
                            groupLayer.password = this.Password;
                            groupLayer.username = this.UserName;
                            groupLayer.workspace = this.DefaultWorkSpace;


                            if (!service.AddLayerToGroup(groupLayer, logger))
                                continue;

                        }
                        else
                            continue;

                        numLayer++;
                        dto.layerName = outDettaglio.layer.name;
                        if (ckbImportaStili.Checked)
                        {
                            if (!service.publishSLD(dto, layerSelezionato.ToString(), ckbSovrascrivi.Checked, logger))
                                util.message(TipoMessaggio.INFO, "SLD "+ layerSelezionato.ToString() + " non importato");

                        }
                    }
                }

            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "Import Shape");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Importazione Layer");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
                util.message(TipoMessaggio.INFO, "Caricamento terminato");
                reloadTreeView();
            }


           
        }

        private void btnChiudiImporta_Click(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            rimuoviTabs();
            reloadTreeView();
            this.Cursor = Cursors.Default;
        }

        //private void cmbSelectLayer_SelectedIndexChanged(object sender, EventArgs e)
        //{
        //    try
        //    {

        //        Utility util = new Utility();
        //        ComboBox comboBox = (ComboBox)sender;
        //        string nomeLayer = (string)cmbSelectLayer.SelectedItem;
        //        toolTip.SetToolTip(cmbSelectLayer, nomeLayer);
        //        this.ShapeFile = util.getShapeFileByLayerName(nomeLayer);
        //        txtNomeLayer.Text = nomeLayer; // Path.GetFileNameWithoutExtension(this.ShapeFile);
        //        lblPathShape.Text = ShapeFile;
        //        if (!this.ShapeFile.Contains("!"))
        //            lblShapeFile.Text = Path.GetFileName(ShapeFile);
        //    }
        //    catch (Exception ex)
        //    {

        //        throw ex;
        //    }
        //    finally
        //    {
        //        this.Cursor = Cursors.Default;
        //    }
        //}


        private void tsmImporta_Click(object sender, EventArgs e)
        {
            Utility util = new Utility();
            rimuoviTabs();
            List<String> listaLayer = util.getListaLayer();
            if (listaLayer.Count > 0)
            {
                clbLayerDaImportare.DataSource = listaLayer;
                //cmbSelectLayer.DataSource = listaLayer;
                //toolTip.SetToolTip(cmbSelectLayer, listaLayer[0]);
                this.tabDettaglio.TabPages.Add(this.tabImporta);
            }
            else
            {
                util.message(TipoMessaggio.WARNING, "Attenzione, nessun layer da importare trovato");
                return;
            }
        }


        private void tsmImportaWMS_Click(object sender, EventArgs e)
        {
            rimuoviTabs();
            this.txtNomeWMS.Enabled = true;
            this.txtLinkWMS.Enabled = true;
            
            this.lblSelLayersWMS.Visible = false;
            this.clbLayerWMS.Visible = false;
            lblSelLayersWMS.Visible = false;
            clbLayerWMS.Visible = false;

            this.tabDettaglio.TabPages.Add(this.tabImportaWMS);
           
        }

        private void eliminaLayer()
        {
            RestApiGeoServer rest = new RestApiGeoServer();
            Utility util = new Utility();
            try
            {

                this.Cursor = Cursors.WaitCursor;
                String nomeNodo = menuTreeView.SelectedNode.Name;
                String nomeGruppo = menuTreeView.SelectedNode.Parent.Name;

                // GetListaLayerInDto input = TOA.assemblaGetListaLayerInDto(this.Server, this.Password, this.UserName, this.DefaultWorkSpace);
                BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);

                if (String.IsNullOrEmpty(nomeNodo))
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.ERRORE, "Nessun layer selezionato!");
                    return;
                }
                else
                    nomeNodo = nomeNodo.Replace("urbamid:", "");

                this.Cursor = Cursors.Default;
                if (MessageBox.Show("ATTEZIONE, Si sta procedendo alla cancellazione di " + nomeNodo, "Attenzione", MessageBoxButtons.YesNo) ==
                    DialogResult.Yes)
                {
                    this.Cursor = Cursors.WaitCursor;
                    DeleteDatastoreDto inDeleteDataStore = TOA.assemblaDeleteDataStore(dtoBase, nomeNodo);

                    if (isLayerGroup(nomeNodo))
                    {
                        if (nomeNodo.EndsWith(Costanti.AREA_TEMATICA_SUFFIX))
                        {
                            if (rest.deleteLayerGroup(inDeleteDataStore, logger))
                            {
                                this.Cursor = Cursors.Default;
                                util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                                nodeOpened = null;
                                reloadTreeView();
                            }
                        }
                        else
                        {
                            //recupero tutti i layer groups
                            GetGroupLayerDto inDto = TOA.assemblaGetDettaglioGruppo(dtoBase, nomeNodo);
                            LayerGroupDto listaLayers = rest.getLayergroups(inDto, logger);
                            List<LayerGroup> gruppi = new List<LayerGroup>();
                            if (nomeNodo.EndsWith(Costanti.LAYER_GROUP_SUFFIX))
                            {
                                gruppi = listaLayers.layerGroups.layerGroup.ToList().Where(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX)).ToList();
                            }
                            else if (nomeNodo.EndsWith(Costanti.TEMATISMO_SUFFIX))
                            {
                                gruppi = listaLayers.layerGroups.layerGroup.ToList().Where(p => p.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX)).ToList();
                            }

                            foreach (LayerGroup gruppo in gruppi)
                            {
                                inDto.nomeNodo = gruppo.name;

                                LayerDTO dettgruppo = rest.getLayergroupsByName(inDto, logger);
                                if (dettgruppo.layerGroup != null && dettgruppo.layerGroup.publishables != null &&
                                    dettgruppo.layerGroup.publishables.published != null)
                                {
                                    string nomeNodoCompleto = String.Format("{0}:{1}", inDto.workspace, nomeNodo);
                                    List<Published> lista = dettgruppo.layerGroup.publishables.published.ToList().FindAll(p => p.name == nomeNodoCompleto);

                                    if (lista != null && lista.Count > 0)
                                    {
                                        //cancello il layergroup dal tematismo;
                                        CreateLayerGroupInDto layerGroup = new CreateLayerGroupInDto();

                                        layerGroup = TOA.createLayerGroupInDto(dtoBase, dettgruppo.layerGroup);
                                        layerGroup.publishables.published = layerGroup.publishables.published.Where(p => p.name != nomeNodoCompleto).ToList();
                                        if (layerGroup.publishables.published.Count == 0)
                                        {
                                            //Aggiungo il default
                                            Published defPub = new Published();
                                            defPub.href = String.Concat(this.Server, "/geoserver", Costanti.REST_SERVICE_PREFIX, this.WorkSpace, "layers/DEFAULT_Layer");
                                            defPub.name = String.Concat(this.WorkSpace, ":", "DEFAULT_Layer");
                                            defPub.tipoPublished = Costanti.TIPO_LAYER_LAYER.ToLower();
                                            layerGroup.publishables.published.Add(defPub);
                                            Style stile = new Style();
                                            stile.href = String.Concat(this.Server, "/geoserver/rest/styles/polygon.json");
                                            stile.name = "polygon";
                                            layerGroup.styles.style.Add(stile);
                                        }

                                        rest.UpdateLayerGroup(layerGroup, logger);
                                    }
                                }

                            }

                            if (rest.deleteLayerGroup(inDeleteDataStore, logger))
                            {
                                this.Cursor = Cursors.WaitCursor;
                                util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                                reloadTreeView();
                            }

                        }
                    }
                    else
                    {
                        bool esito = false;
                        if (nomeNodo.ToLower().StartsWith("u_geo"))
                        {
                            DeleteLayer_InDto inDelete = WrapperTOA.assemblerDeleteLayer(this.WrapperUrl, this.DefaultWorkSpace, nomeNodo);
                            DeleteLayer_OutDto outDelete = rest.deleteLayer(inDelete, logger);
                            if (outDelete == null || !outDelete.IsSuccess)
                            {
                                this.Cursor = Cursors.Default;
                                util.message(TipoMessaggio.ERRORE, outDelete.Message);
                                return;
                            }
                            esito = true;

                        }
                        else
                        {
                            GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(dtoBase, "");
                            inDettaglio.storeName = nomeNodo;
                            LayerDetailDto outDetail = rest.getDettaglioLayer(inDettaglio, logger);
                            inDeleteDataStore.nomeDataStore = HttpUtility.HtmlEncode(outDetail.layer.name);
                            if (outDetail.layer.type == Costanti.TIPO_LAYER_RASTER)
                                esito = rest.deleteRaster(inDeleteDataStore, logger);
                            else if (outDetail.layer.type == Costanti.TIPO_LAYER_WMS)
                            {
                                String groupName = this.menuTreeView.SelectedNode.Parent.Name;
                                esito = rest.deleteLayerWMS(inDeleteDataStore, groupName,  logger);
                            }
                            else
                                esito = rest.deleteDataStore(inDeleteDataStore, logger);

                        }
                        if (esito)
                        {
                            this.Cursor = Cursors.Default;
                            util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                            reloadTreeView();
                        }
                    }
                }

            }
            catch (UrbamidException exx)
            {

                logger.Error(exx, "Elimina Layer");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore cancellazione layer");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        #endregion

        #region Modifica Layer
        private void btnAddProperty_Click(object sender, EventArgs e)
        {

            DataTable dt = (DataTable)this.dgvLayerDetail.DataSource;
            DataRow row = dt.NewRow();
            row["Chiave"] = "";
            row["Valore"] = "";
            dt.Rows.Add(row);
            this.dgvLayerDetail.DataSource = dt;


        }

        private void btnDeleteProperty_Click(object sender, EventArgs e)
        {
            String proprieta = String.Empty;
            if (dgvLayerDetail.SelectedRows.Count != 1)
                return;


            if (MessageBox.Show("Si sta cancellando la proprietà " + proprieta
                + ", la cancellazione sarà effettiva soltanto alla click del tasto Salva in basso, si vuole procedere?",
                  "ATTENZIONE", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {
                List<String> listaTipi = new List<String>();
                for (int i = 0; i < this.dgvLayerDetail.RowCount; i++)
                {
                    if (this.dgvLayerDetail.Rows[i].Cells[0].Value != null)
                    {
                        if (this.dgvLayerDetail.Rows[i].Cells[0].Value.ToString() == "1")
                            listaTipi.Add(TipoAttributoEnum.Visualizzazione.ToString());
                        else if (this.dgvLayerDetail.Rows[i].Cells[0].Value.ToString() == "2")
                            listaTipi.Add(TipoAttributoEnum.Ricerca.ToString());
                        else
                            listaTipi.Add(TipoAttributoEnum.Tutto.ToString());
                    }
                }

                int index = dgvLayerDetail.SelectedRows[0].Index;
                DataTable dt = (DataTable)this.dgvLayerDetail.DataSource;
                dt.Rows.RemoveAt(index);
                if(index < listaTipi.Count)
                 listaTipi.RemoveAt(index);
              
            }

        }

        private void btnConfirmEditLayer_Click(object sender, EventArgs e)
        {
            Utility util = new Utility();
            RestApiGeoServer service = new RestApiGeoServer();

            try
            {
                this.Cursor = Cursors.WaitCursor;
                String nomeLayer = this.menuTreeView.SelectedNode.Name;
                if (string.IsNullOrEmpty(nomeLayer))
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.ERRORE, "Selezionare un layer");
                    return;
                }

                if (String.IsNullOrEmpty(txtEditTitoloLayer.Text))
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.WARNING, "Titolo Layer obbligatorio!");
                    return;
                }

                nomeLayer = nomeLayer.Replace(this.DefaultWorkSpace + ":", "");

             
                GeoServerDto inDettaglio = new GeoServerDto()
                {
                    baseUrl = this.Server,
                    layerName = nomeLayer,
                    password = this.Password,
                    storeName = nomeLayer,
                    username = this.UserName,
                    workspace = this.DefaultWorkSpace
                };

                LayerDetailDto outDettaglio = service.getDettaglioLayer(inDettaglio, logger);
                if (outDettaglio.layer.type.Equals(Costanti.TIPO_LAYER_RASTER))
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.INFO, "Funzionalità non disponibile per i raster");
                    return;
                }

                

                GetFeatureByLayer_OutDto outGetFeature = null;
                FeatureType featureType = new FeatureType();
                
                if (nomeLayer.ToUpper().StartsWith("U_GEO"))
                {
                    GetFeatureByLayer_InDto inGetFeature = WrapperTOA.assemblaGetFeature(this.WrapperUrl, this.DefaultWorkSpace, nomeLayer, false);
                    outGetFeature = service.getFeatureByLayer(inGetFeature, logger);
                    if (outGetFeature == null || !outGetFeature.IsSuccess)
                    {
                        this.Cursor = Cursors.Default;
                        util.message(TipoMessaggio.ERRORE, outGetFeature.Message);
                        return;
                    }
                    featureType = outGetFeature.feature;
                    if (featureType != null)
                        featureType = leggiValoriForm(featureType);

                    String keys = String.Empty;
                    foreach (var chiave in featureType.keywords.chiave)
                    {
                        if (!chiave.EndsWith(";"))
                            keys += chiave + ";";
                        else
                            keys += chiave;
                    }
                    // UPDATE FEATURE
                    EditFeature_InDto inEditFeature = WrapperTOA.assemblerEditFeature(this.WrapperUrl, outGetFeature.feature.name, keys, this.DefaultWorkSpace, featureType.title);

                    EditFeature_OutDto outEditFeature = service.editFeatureType(inEditFeature, logger);
                    if (outEditFeature == null || !outEditFeature.IsSuccess)
                    {
                        this.Cursor = Cursors.Default;
                        util.message(TipoMessaggio.ERRORE, outEditFeature.Message);
                        return;
                    }

                }
                else if (outDettaglio.layer.type.Equals(Costanti.TIPO_LAYER_WMS))
                {

                    GetFeatureTypeDto input = new GetFeatureTypeDto
                    {
                        link = outDettaglio.layer.resource.href,
                        baseUrl = this.Server,
                        password = this.Password,
                        username = this.UserName,
                        workspace = this.DefaultWorkSpace
                    };
                    WmsFeatureDto featureWMS = service.getFeatureLayerWMS(input, logger);
                    featureType = TOA.Assembler(featureWMS);
                    if (featureType != null)
                        featureType = leggiValoriForm(featureType);
                    String keys = String.Empty;
                    foreach (var chiave in featureType.keywords.chiave)
                    {
                        if (!chiave.EndsWith(";"))
                            keys += chiave + ";";
                        else
                            keys += chiave;
                    }
                    keys = keys.TrimEnd(';');
                    featureType.keywords.chiave = keys.Split(';').ToList();
                    featureWMS.wmsLayer = TOA.Assembler(featureType, featureWMS.wmsLayer);

                    //UPDATE LAYER WMS
                   

                    featureWMS.wmsLayer.keywords.chiave = keys.Split(';').ToList();
                    service.UpdateLayerWMS(input, outDettaglio.layer.name, featureWMS, logger);
                }
                else
                {
                    GetFeatureTypeDto input = new GetFeatureTypeDto
                    {
                        link = outDettaglio.layer.resource.href,
                        baseUrl = this.Server,
                        password = this.Password,
                        username = this.UserName,
                        workspace = this.DefaultWorkSpace
                    };

                    FeaturesTypeDto featureDto = service.getFeatureTypeByNomeLayer(input, logger);
                    featureType = featureDto.featureType;
                    if (featureType != null)
                        featureType = leggiValoriForm(featureType);


                    // EDIT CLASSICO
                    String keys = String.Empty;
                    foreach (var chiave in featureType.keywords.chiave)
                    {
                        if (!chiave.EndsWith(";"))
                            keys += chiave + ";";
                        else
                            keys += chiave;
                    }
                    keys = keys.TrimEnd(';');
                    featureDto.featureType.keywords.chiave = keys.Split(';').ToList();
                    BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);
                    GeoServerDto inDettDTO = TOA.assemblaGeoServerDTO(dtoBase, String.Empty);
                    inDettDTO.storeName = nomeLayer;
                    LayerDetailDto outDettDTO = service.getDettaglioLayer(inDettDTO, logger);

                    EditLayerDto inEditDto = TOA.assemblaEditLayer(dtoBase);
                    inEditDto.layerDto = featureDto;


                    if (outDettDTO != null && outDettDTO.layer != null && outDettDTO.layer.resource != null &&
                        !String.IsNullOrEmpty(outDettDTO.layer.resource.href))
                        inEditDto.linkFeature = outDettDTO.layer.resource.href;
                    else
                        inEditDto.nomeLayer = nomeLayer;


                    if (!service.UpdateLayer(inEditDto, logger))
                        return;

                }

                util.message(TipoMessaggio.INFO, "Operazione terminata!");
                reloadTreeView();

              

            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "Edit Layer Click");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Modifica Layer");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private FeatureType leggiValoriForm(FeatureType featureType)
        {
            if (featureType.keywords != null && featureType.keywords.chiave != null)
            {
                List<string> attributtiFissi = featureType.keywords.chiave.ToList();
                attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.VIEW_FIELD));
                attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.SEARCH_FIELD));
                attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.NASCONDI_FIELD));
                attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.ALL_FIELD));

                attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.ESTRAZIONE_PARTICELLARE_CONST));
                attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.DEFAULT_VIEW_CONST));
                featureType.keywords.chiave = attributtiFissi;
            }
            else
            {
                featureType.keywords = new Keywords();
                featureType.keywords.chiave = new List<string>();
            }

            featureType.title = txtEditTitoloLayer.Text.Trim();

            for (int i = 0; i < this.dgvLayerDetail.Rows.Count; i++)
            {
                String keywords = String.Empty;
                if (!String.IsNullOrEmpty(this.dgvLayerDetail.Rows[i].Cells[2].Value.ToString()) &&
                    !(this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(4)))
                {

                    if (!this.dgvLayerDetail.Rows[i].Cells[1].Value.ToString().ToUpper().StartsWith("DEFAULTVIEW") ||
                        !this.dgvLayerDetail.Rows[i].Cells[1].Value.ToString().ToUpper().StartsWith("SHAPETODB"))
                    {
                        if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(1))
                            keywords = Costanti.VIEW_FIELD;
                        else if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(2))
                            keywords = Costanti.SEARCH_FIELD;
                        else if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(3))
                            keywords = Costanti.ALL_FIELD;
                        else if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(4))
                            keywords = Costanti.NASCONDI_FIELD;
                    }
                    keywords += this.dgvLayerDetail.Rows[i].Cells[1].Value + "@" + this.dgvLayerDetail.Rows[i].Cells[2].Value + ";";
                    featureType.keywords.chiave.Add(keywords);
                }
            }
            featureType.keywords.chiave.Add(Costanti.ESTRAZIONE_PARTICELLARE_CONST + "@" + ckEstrazionePart_Mod.Checked.ToString().ToLower());
            featureType.keywords.chiave.Add(Costanti.DEFAULT_VIEW_CONST + "@" + ckDefaultView_Mod.Checked.ToString().ToLower());


            

            return featureType;
        }

        private void visualizzaTabModifica()
        {
            Utility util = new Utility();
            RestApiGeoServer service = new RestApiGeoServer();
            try
            {
                this.Cursor = Cursors.WaitCursor;
                String nomeLayer = this.menuTreeView.SelectedNode.Name;

                if (string.IsNullOrEmpty(nomeLayer))
                {
                    util.message(TipoMessaggio.ERRORE, "Selezionare un layer");
                    this.Cursor = Cursors.Default;
                    return;
                }
                nomeLayer = nomeLayer.Replace("urbamid:", "");
                txtShapeFile.Text = nomeLayer;
                GeoServerDto geoServerDto = new GeoServerDto()
                {
                    baseUrl = this.Server,
                    layerName = nomeLayer,
                    password = this.Password,
                    storeName = nomeLayer,
                    username = this.UserName,
                    workspace = this.DefaultWorkSpace
                };


                LayerDetailDto dettaglio = service.getDettaglioLayer(geoServerDto, logger);

                //GetDettaglioLayer_InDto inputGet = WrapperTOA.assembler(this.WrapperUrl, nomeLayer, this.DefaultWorkSpace, false);
                //GetDettaglioLayer_OutDto outDettaglio = service.getDettaglioLayer(inputGet, logger);
                if (dettaglio.layer.type.Equals(Costanti.TIPO_LAYER_RASTER))
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.INFO, "Funzionalità non disponibile per i raster");
                    return;
                }

                rimuoviTabs();
                this.tabDettaglio.TabPages.Add(this.tabModifica);
                this.dgvLayerDetail.Visible = true;
                this.dgvLayerDetail.Columns.Clear();
                GetFeatureByLayer_OutDto outGetFeature = null;
                FeaturesTypeDto featureDto = null;
                FeatureType featureType = new FeatureType();
                if (nomeLayer.ToUpper().StartsWith("U_GEO"))
                {
                    GetFeatureByLayer_InDto inGetFeature = WrapperTOA.assemblaGetFeature(this.WrapperUrl, this.DefaultWorkSpace, nomeLayer, false);
                    outGetFeature = service.getFeatureByLayer(inGetFeature, logger);
                    if (outGetFeature == null || !outGetFeature.IsSuccess)
                    {
                        util.message(TipoMessaggio.ERRORE, outGetFeature.Message);
                        return;
                    }

                    featureType = outGetFeature.feature;
                }
                else if (dettaglio.layer.type == Costanti.TIPO_LAYER_WMS)
                {
                    GetFeatureTypeDto input = new GetFeatureTypeDto()
                    { 
                        baseUrl = geoServerDto.baseUrl,
                        password = geoServerDto.password,
                        username = geoServerDto.username,
                        workspace = geoServerDto.workspace,
                        link = dettaglio.layer.resource.href
                    };

                    WmsFeatureDto outDto = service.getFeatureLayerWMS(input, logger);
                    if (outDto != null && outDto.wmsLayer != null && String.IsNullOrEmpty(outDto.wmsLayer.title))
                    {
                        util.message(TipoMessaggio.ERRORE, "Errore nel recupero del Layer WMS");
                        return;

                    }

                    featureType = TOA.Assembler(outDto);
                   
                }
                else
                {

                    GetFeatureTypeDto input = new GetFeatureTypeDto();
                    input.link = dettaglio.layer.resource.href;
                    input.baseUrl = this.Server;
                    input.password = this.Password;
                    input.username = this.UserName;
                    input.workspace = this.DefaultWorkSpace;

                    featureDto = service.getFeatureTypeByNomeLayer(input, logger);
                    featureType = featureDto.featureType;
                }


                this.linkDettaglioFeatureType = nomeLayer;
                string[] stringSeparators = new string[] { "@" };

                DataTable dt = new DataTable();

                dt.Columns.Add("Chiave", typeof(string));
                dt.Columns.Add("Valore", typeof(string));


                List<String> listaTipi = new List<String>();
                if (featureType != null)
                {
                    List<string> lsKeys = new List<string>();
                    txtEditTitoloLayer.Text = featureType.title;
                    if (featureType.keywords != null && featureType.keywords.chiave != null)
                    {
                        foreach (string chiave in featureType.keywords.chiave)
                        {
                            String nuovaChiave = chiave;
                            nuovaChiave = nuovaChiave.Replace("\\", "");
                            nuovaChiave = nuovaChiave.Replace("@vocabulary=", "@");
                            lsKeys.Add(nuovaChiave);
                        }
                    }
                    foreach (String item in lsKeys)
                    {
                        String newItem = item.Replace(";", "");
                        String[] arr = newItem.Split(stringSeparators, StringSplitOptions.None);

                        if (arr[0] == null || arr[0].ToUpper().Equals("SHAPEDB"))
                            continue;

                        if (arr[0].Equals(Costanti.DEFAULT_VIEW_CONST))
                        {
                            ckDefaultView_Mod.Checked = Convert.ToBoolean(arr[1].ToLower());
                            continue;
                        }

                        if (arr[0].Equals(Costanti.ESTRAZIONE_PARTICELLARE_CONST))
                        {
                            ckEstrazionePart_Mod.Checked = Convert.ToBoolean(arr[1].ToLower());
                            continue;
                        }

                        if (arr.Length > 1)
                        {
                            if (arr[0].ToUpper().EndsWith("THE_GEOM"))
                                continue;

                            DataRow row = dt.NewRow();
                            if (arr[0].ToString().ToLower().StartsWith("viewfield_"))
                            {
                                listaTipi.Add(TipoAttributoEnum.Visualizzazione.ToString());

                                row["Chiave"] = arr[0].Replace("viewField_", "");
                                row["Valore"] = arr[1];

                                dt.Rows.Add(row);
                            }
                            else if (arr[0].ToString().ToLower().StartsWith("searchfield_"))
                            {
                                listaTipi.Add(TipoAttributoEnum.Ricerca.ToString());
                                row["Chiave"] = arr[0].Replace("searchField_", "");
                                row["Valore"] = arr[1];


                                dt.Rows.Add(row);
                            }
                            else if (arr[0].ToString().ToLower().StartsWith("nascondi_"))
                            {
                                listaTipi.Add(TipoAttributoEnum.Nascondi.ToString());
                                row["Chiave"] = arr[0].Replace("searchField_", "");
                                row["Valore"] = arr[1];


                                dt.Rows.Add(row);
                            }
                            else if (arr[0].ToString().ToLower().StartsWith("allfield_"))
                            {
                                listaTipi.Add(TipoAttributoEnum.Tutto.ToString());
                                row["Chiave"] = arr[0].Replace("searchField_", "");
                                row["Valore"] = arr[1];


                                dt.Rows.Add(row);
                            }
                            else
                            {

                                listaTipi.Add(TipoAttributoEnum.Tutto.ToString());
                                row["Chiave"] = arr[0];
                                row["Valore"] = arr[1];

                                dt.Rows.Add(row);
                            }
                        }
                    }


                    dgvLayerDetail.DataSource = dt;

                }



                DataGridViewComboBoxColumn cmb = new DataGridViewComboBoxColumn();
                Dictionary<string, int> dictionary = new Dictionary<string, int>();

                foreach (int enumValue in Enum.GetValues(typeof(TipoAttributoEnum)))
                {
                    dictionary.Add(Enum.GetName(typeof(TipoAttributoEnum), enumValue), enumValue);
                }

                cmb.DisplayMember = "Key";
                cmb.ValueMember = "Value";
                cmb.DataSource = dictionary.ToList();



                cmb.SortMode = DataGridViewColumnSortMode.Automatic;
                cmb.Name = "Tipo";
                cmb.HeaderText = "Tipo";
                cmb.DisplayStyle = DataGridViewComboBoxDisplayStyle.ComboBox;
                cmb.ReadOnly = false;

                dgvLayerDetail.Columns.Insert(0, cmb);
                for (int i = 0; i < dgvLayerDetail.RowCount; i++)
                {
                    dgvLayerDetail.Rows[i].Cells[0].Value = dictionary.ToList().Find(p => p.Key == listaTipi.ElementAt(i)).Value;
                    dgvLayerDetail.UpdateCellValue(0, i);
                }

                dgvLayerDetail.Refresh();
                dgvLayerDetail.Update();


            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "Apertura Layer Modifica");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, "Visualizza Tab Modifica");
                util.message(TipoMessaggio.ERRORE, "Errore Apertura Layer");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }

        }

        private void btnUndoEditLayer_Click(object sender, EventArgs e)
        {
            this.tabDettaglio.TabPages.Remove(this.tabModifica);
            reloadTreeView();
        }

        private void tsmModifica_Click(object sender, EventArgs e)
        {
            visualizzaTabModifica();
        }

        #endregion

        #region Layer Group
       
        private void clbLayerDisponibili_ItemCheck(object sender, ItemCheckEventArgs e)
        {
         
          
            listaSelezionati = (List<GenericLayer>)bsSelezionati.DataSource;
            if (listaSelezionati == null)
                listaSelezionati = new List<GenericLayer>();


            String nomeLayer = this.clbLayerDisponibili.Items[e.Index].ToString();
            GenericLayer layer = (GenericLayer)this.clbLayerDisponibili.Items[e.Index];
            if (e.NewValue == CheckState.Checked)
            {
                if (listaSelezionati.FindAll(p =>p.nome == layer.nome).Count == 0)
                    listaSelezionati.Add(layer);
            }
            else
            {
                if (listaSelezionati.FindAll(p => p.nome == layer.nome).Count > 0)
                    listaSelezionati.RemoveAll(p => p.nome == layer.nome);
            }
           
            bsSelezionati.DataSource = listaSelezionati;
            this.clbLayersSelezionati.DataSource = bsSelezionati;
            clbLayersSelezionati.DisplayMember = "titolo";
            clbLayersSelezionati.ValueMember = "nome";
            bsSelezionati.ResetBindings(false);
         

        }


        private void btnCreaLayerGroup_Click(object sender, EventArgs e)
        {
            //controlli
            Utility util = new Utility();
            RestApiGeoServer rest = new RestApiGeoServer();
            try
            {
                String nomeLayer = txtNameLayerGroup.Text.Trim();
                String titoloLayer = txtTitleLayerGroup.Text.Trim();

                if (String.IsNullOrEmpty(nomeLayer) || nomeLayer.Contains(" "))
                {
                    util.message(TipoMessaggio.WARNING, "Attenzione, nome layer non deve contenere spazi vuoti!");
                    return;
                }
                if (String.IsNullOrEmpty(titoloLayer))
                {
                    util.message(TipoMessaggio.WARNING, "Attenzione, titolo layer obbligatorio!");
                    return;
                }


                this.Cursor = Cursors.WaitCursor;
                String nomeNodo = menuTreeView.SelectedNode.Name;

                if (String.IsNullOrEmpty(nomeNodo))
                {
                    if (!menuTreeView.SelectedNode.Text.Equals("Layers Cartografici"))
                    {
                        util.message(TipoMessaggio.ERRORE, "Nessun nodo selezionato!");
                        return;
                    }
                    else
                        nomeLayer += Costanti.AREA_TEMATICA_SUFFIX;
                }
                else
                    nomeNodo = nomeNodo.Replace("urbamid:", "");

                if (clbLayersSelezionati.Items.Count == 0)
                {
                    GenericLayer defaultLayer = new GenericLayer();
                    if (nomeLayer.EndsWith(Costanti.AREA_TEMATICA_SUFFIX))
                        defaultLayer.nome = Costanti.DEFAULT_LAYER + Costanti.TEMATISMO_SUFFIX;
                    else if (nomeLayer.EndsWith(Costanti.TEMATISMO_SUFFIX))
                        defaultLayer.nome = Costanti.DEFAULT_LAYER + Costanti.LAYER_GROUP_SUFFIX;
                    else
                        defaultLayer.nome = Costanti.DEFAULT_LAYER + Costanti.LAYER_GROUP_SUFFIX;


                    defaultLayer.titolo = Costanti.DEFAULT_LAYER;

                    List<GenericLayer> layerSelezionati = new List<GenericLayer>();
                    layerSelezionati.Add(defaultLayer);
                    bsSelezionati.DataSource = layerSelezionati;
                    clbLayersSelezionati.DataSource = bsSelezionati;
                    clbLayersSelezionati.DisplayMember = "titolo";
                    clbLayersSelezionati.ValueMember = "nome";

                }

                if (isEditLayerGroup)//  if (isLayerGroup(nomeNodo))
                    editLayerGroup(titoloLayer, nomeLayer, nomeNodo);
                else
                    addLayerGroup(titoloLayer, nomeLayer, nomeNodo);

                this.Cursor = Cursors.Default;
            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "Crea Layer Group Open");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Creazione Gruppo");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private void visualizzaTabModificaLayerGroup()
        {
            String nomeLayer = this.menuTreeView.SelectedNode.Name.Replace(this.DefaultWorkSpace + ":", "");
            String titoloLayer = this.menuTreeView.SelectedNode.Text;
            

            txtNameLayerGroup.Enabled = false;
            txtNameLayerGroup.Text = nomeLayer;
            txtTitleLayerGroup.Text = titoloLayer;
            dgvLayerDetail.Visible = false;

            //ABILITA RICERCA CHECK
            txtFindLayers.Enabled = false;
            clbLayerDisponibili.Enabled = false;
            clbLayersSelezionati.Enabled = false;



            /*
            GetListaLayerInDto input = new GetListaLayerInDto(); //aggiunto ora per compilare
            RestApiGeoServer service = new RestApiGeoServer();
            Utility util = new Utility();
            try
            {
                this.Cursor = Cursors.WaitCursor;
                
                List<LayerGroup> lsGruppi = new List<LayerGroup>();
                input = TOA.assemblaGetListaLayerInDto(this.Server, this.Password, this.UserName, this.DefaultWorkSpace);
                layersDisponibili = new List<GenericLayer>();


               

                if (nomeLayer.EndsWith(Costanti.AREA_TEMATICA_SUFFIX))
                {
                    lsGruppi = service.getListaLayerGroupInWorkspace(input);
                    lsGruppi = lsGruppi.FindAll(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX));
                    lsGruppi.RemoveAll(p => p.name.StartsWith("DEFAULT_"));
                }
                else if (nomeLayer.EndsWith(Costanti.TEMATISMO_SUFFIX))
                {
                    lsGruppi = service.getListaLayerGroupInWorkspace(input);
                    lsGruppi.RemoveAll(p => p.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX));
                    lsGruppi.RemoveAll(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX));
                    lsGruppi.RemoveAll(p => p.name.StartsWith("DEFAULT_"));

                }


                foreach (LayerGroup gruppo in lsGruppi)
                {
               
                    GetGroupLayerDto inDettGruppo = TOA.assemblaGetDettaglioGruppo(input);
                    inDettGruppo.link = gruppo.href;
                    LayerDTO outGruppo = service.getLayergroupsByName(inDettGruppo);
                    GenericLayer genDTO = TOA.assemblaGenericLayer(outGruppo.layerGroup);
                    layersDisponibili.Add(genDTO);
                }


                if (nomeLayer.EndsWith(Costanti.TEMATISMO_SUFFIX) || nomeLayer.EndsWith(Costanti.LAYER_GROUP_SUFFIX))
                {
                    List<Layer> listaLayers = service.getListaLayerInWorkspace(input); //recupero tutti i layers presenti nel WORKSPACE
                    listaLayers.RemoveAll(p => p.name.StartsWith("DEFAULT_"));
                   

                    foreach (Layer item in listaLayers)
                    {
                        
                        GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(input);
                        inDettaglio.linkDettaglio = item.link;
                        LayerDetailDto outDettaglio = service.getDettaglioLayer(inDettaglio);

                        GetFeatureTypeDto inFeature = TOA.assemblaFeatureTypeDto(input);
                        inFeature.link = outDettaglio.layer.resource.href;
                        FeaturesTypeDto outFeature = service.getFeatureTypeByNomeLayer(inFeature);
                        GenericLayer genDto = TOA.assemblaGenericLayer(outDettaglio.layer);
                        if (outFeature != null && outFeature.featureType != null)
                            genDto.titolo = outFeature.featureType.title;
                        else
                            genDto.titolo = outDettaglio.layer.name;

                        layersDisponibili.Add(genDto);

                    }
                }




                GetGroupLayerDto inDto = TOA.assemblaGetDettaglioGruppo(input);
                inDto.nomeNodo = nomeLayer;
                LayerDTO layGroup = service.getLayergroupsByName(inDto);

                List<GenericLayer> layerSelezionati = new List<GenericLayer>();
                if (layGroup != null && layGroup.layerGroup != null)
                {
                    foreach (Published item in layGroup.layerGroup.publishables.published)
                    {

                        if (item.tipoPublished == "layer")
                        {
                            GeoServerDto inLayerDto = TOA.assemblaGeoServerDTO(input);
                            inLayerDto.linkDettaglio = item.href;
                            LayerDetailDto outDettaglio = service.getDettaglioLayer(inLayerDto);
                            GenericLayer generic = TOA.assemblaGenericLayer(outDettaglio.layer);
                            GetFeatureTypeDto inFeature = TOA.assemblaFeatureTypeDto(input);
                            inFeature.link = outDettaglio.layer.resource.href;
                            FeaturesTypeDto outFeature = service.getFeatureTypeByNomeLayer(inFeature);

                            if (outFeature != null && outFeature.featureType != null)
                                generic.titolo = outFeature.featureType.title;
                            else
                                generic.titolo = outDettaglio.layer.name;
                            layerSelezionati.Add(generic);
                        }
                        else
                        {
                            GetGroupLayerDto inDettGruppo = TOA.assemblaGetDettaglioGruppo(input);
                            inDettGruppo.link = item.href;
                            LayerDTO outGruppo = service.getLayergroupsByName(inDettGruppo);
                            layerSelezionati.Add(TOA.assemblaGenericLayer(outGruppo.layerGroup));
                        }
                    }

                    layerSelezionati.RemoveAll(p => p.nome.ToUpper().StartsWith(Costanti.DEFAULT_LAYER));
                    bsSelezionati.DataSource = layerSelezionati;
                    clbLayersSelezionati.DataSource = bsSelezionati;
                    clbLayersSelezionati.DisplayMember = "titolo";
                    clbLayersSelezionati.ValueMember = "nome";
                    clbLayerDisponibili.BeginUpdate();

                    bsDisponibili.DataSource = layersDisponibili;
                    clbLayerDisponibili.DataSource = bsDisponibili;
                    clbLayerDisponibili.DisplayMember = "titolo";
                    clbLayerDisponibili.ValueMember = "nome";
                    bsDisponibili.ResetBindings(false);
                    clbLayerDisponibili.EndUpdate();

                }

            
            }
            catch (Exception ex)
            {
                util.scriviLog("ERRORE Visualizza Tab Modifica: " + ex.Message + ex.StackTrace);
            }
            finally {
                this.Cursor = Cursors.Default;
            }
            */

        }

        
        private void visualizzaTabCreaLayerGroup(String gruppo)
        {

            isEditLayerGroup = false;
            rimuoviTabs();
            txtNameLayerGroup.Enabled = true;
            txtTitleLayerGroup.Enabled = true;
            txtNameLayerGroup.Text = "";
            txtTitleLayerGroup.Text = "";
            this.tabDettaglio.TabPages.Add(this.tabCreaGruppo);
            txtFindLayers.Enabled = false;
            clbLayerDisponibili.Enabled = false;
            clbLayersSelezionati.Enabled = false;

           // Utility util = new Utility();

           // RestApiGeoServer rest = new RestApiGeoServer();
           //// clbLayerDisponibili.Items.Clear();
           // try
           // {
           //     layersDisponibili = new List<GenericLayer>();
           //     bsSelezionati.DataSource = null;
           //     listaSelezionati = new List<GenericLayer>();
           //     List<LayerGroup> lista = new List<LayerGroup>();
           //     BaseDto dtoBase = TOA.assemblaBase(this.Server, this.Password, this.UserName, this.DefaultWorkSpace);
           //     GetListaLayerInDto input = TOA.assemblaGetListaLayerInDto(dtoBase);
           //     if (gruppo == "AreaTematica" || gruppo == "Tematismo")
           //     {
                    
           //         lista = rest.getListaLayerGroupInWorkspace(input);
           //         lista.RemoveAll(p => p.name.ToUpper().StartsWith(Costanti.DEFAULT_LAYER));
           //         if (gruppo == "AreaTematica")
           //             lista = lista.FindAll(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX));
           //         else if (gruppo == "Tematismo")
           //         {
           //             lista.RemoveAll(p => p.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX));
           //             lista.RemoveAll(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX));
           //         }


           //     }
      

           //     foreach (LayerGroup item in lista)
           //     {
           //         GetGroupLayerDto grupDTO = TOA.assemblaGetDettaglioGruppo(dtoBase, item);
                   

           //         LayerDTO dettaglio = rest.getLayergroupsByName(grupDTO);

           //         GenericLayer layer = TOA.assemblaGenericLayer(item);
           //         layer.titolo = dettaglio.layerGroup.title;
           //         layersDisponibili.Add(layer);
           //     }




           //     if (gruppo == "Tematismo" || gruppo == "LayerGroup") // devo aggiungere anche i layers
           //     {
           //         List<Layer> listaLayers = rest.getListaLayerInWorkspace(input); //recupero tutti i layers presenti nel WORKSPACE
           //         foreach (Layer item in listaLayers)
           //         {
           //             GeoServerDto inDTO = TOA.assemblaGeoServerDTO(dtoBase, item.link);
           //             LayerDetailDto dettaglio = rest.getDettaglioLayer(inDTO);
           //             String titolo = dettaglio.layer.name;

           //             if (dettaglio.layer.type != Costanti.TIPO_LAYER_RASTER)
           //             {
           //                 GetFeatureTypeDto inFeature = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
           //                 FeaturesTypeDto outFeature = rest.getFeatureTypeByNomeLayer(inFeature);
           //                 if(outFeature != null && outFeature.featureType != null && !String.IsNullOrEmpty(outFeature.featureType.title))
           //                     titolo = outFeature.featureType.title;
    
           //                 if (dettaglio.layer.name.StartsWith(Costanti.LAYERDB_PREFIX))
           //                     titolo += " - DB";

           //             }
           //             else
           //             {
           //                 GetFeatureTypeDto inFeatureDto = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
           //                 CoverageDto outDto = rest.getFeatureTypeRasterByNomeLayer(inFeatureDto);
           //                 titolo = outDto.coverage.title;
           //             }
           //             GenericLayer genDTO = TOA.assemblaGenericLayer(dettaglio.layer);
           //             genDTO.titolo = titolo;

           //             layersDisponibili.Add(genDTO);

           //         }

           //     }

           //     layersDisponibili.RemoveAll(p => p.nome.StartsWith(Costanti.DEFAULT_LAYER));
           //     layersDisponibili.OrderBy(p => p.titolo);
           //     clbLayerDisponibili.DataSource = layersDisponibili;
           //     clbLayerDisponibili.DisplayMember = "titolo";
           //     clbLayerDisponibili.ValueMember = "nome";
           // }
           // catch (Exception ex)
           // {
           //     util.scriviLog(String.Format("Errore TabCreaLayerGroup: {0} --> {1}", ex.Message, ex.StackTrace));
           //     util.message(TipoMessaggio.ERRORE, "Errore nella visualizzazione del dettaglio");
           // }
           // finally{
           //     this.Cursor = Cursors.Default;
           // }
        }

        private void addLayerGroup(String titoloLayer, String nomeLayer, String nomeNodo)
        {

            RestApiGeoServer rest = new RestApiGeoServer();
            Utility util = new Utility();
            try
            {
               GetListaLayerInDto input = TOA.assemblaGetListaLayerInDto(this.Server, this.Password, this.UserName, this.DefaultWorkSpace);

                CreateLayerGroupInDto layerGroup = new CreateLayerGroupInDto();
             
                layerGroup.abstractTxt = titoloLayer;
                layerGroup.mode = "SINGLE";
                layerGroup.baseUrl = this.Server;
                if (nomeNodo.EndsWith(Costanti.AREA_TEMATICA_SUFFIX) && (!nomeNodo.EndsWith(Costanti.TEMATISMO_SUFFIX)))
                    layerGroup.name = nomeLayer + Costanti.TEMATISMO_SUFFIX;
                else if (nomeNodo.EndsWith(Costanti.TEMATISMO_SUFFIX) && (!nomeNodo.EndsWith(Costanti.LAYER_GROUP_SUFFIX)))
                    layerGroup.name = nomeLayer + Costanti.LAYER_GROUP_SUFFIX;
                else
                    layerGroup.name = nomeLayer;



                layerGroup.title = titoloLayer;
                layerGroup.workspace = this.DefaultWorkSpace;
                layerGroup.password = this.Password;
                layerGroup.username = this.UserName;
                layerGroup.workspaces = new UrbamidAddIn_V10_2.Dto.Workspace();
                layerGroup.workspaces.name = this.DefaultWorkSpace;
                layerGroup.keywords = new Keywords();
                layerGroup.keywords.chiave = new List<string>();
                layerGroup.keywords.chiave.Add("defaultView=false;");
                layerGroup.publishables = new Publishables();
                layerGroup.publishables.published = new List<Published>();
                layerGroup.styles = new Styles();
                layerGroup.styles.style = new List<Style>();

                layerGroup.bounds = new Bounds();
                layerGroup.bounds.crs = "EPSG:4326";
                layerGroup.bounds.maxx = 0;
                layerGroup.bounds.maxy = 0;
                layerGroup.bounds.minx = 0;
                layerGroup.bounds.miny = 0;


                //per ogni layer selezionato devo 
                GeoServerDto dto = TOA.assemblaGeoServerDTO(input);

                foreach (GenericLayer item in clbLayersSelezionati.Items)
                {
                    
                    if (isLayerGroup(item.nome))
                    {
                        Style stile = new Style();

                        layerGroup.styles.style.Add(stile);
                        Published pub = new Published();
                        pub.name = item.nome;
                        pub.tipoPublished = "layerGroup";
                        pub.href = layerGroup.baseUrl + "/rest/workspaces/" + layerGroup.workspace + "/layergroups/" + item.nome + ".json";
                        layerGroup.publishables.published.Add(pub);
                        //layerGroup.publishables.published = pub;
                    }
                    else
                    {
                        dto.layerName = item.nome;
                        dto.storeName = item.nome;
                        LayerDetailDto output = rest.getDettaglioLayer(dto, logger);
                        Style stile = new Style();
                        stile.href = output.layer.defaultStyle.href;
                        stile.name = output.layer.defaultStyle.name;
                        layerGroup.styles.style.Add(stile);
                        Published pub = new Published();
                        pub.name = dto.layerName;
                        pub.tipoPublished = "layer";
                        pub.href = layerGroup.baseUrl + "/rest/workspaces/" + layerGroup.workspace + "/layers/" + item.nome + ".json";
                        layerGroup.publishables.published.Add(pub);
                        //layerGroup.publishables.published = pub;
                    }
                }


                if (rest.createLayerGroup(layerGroup, logger))
                {
                    if (!layerGroup.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX))
                    {
                        if (String.IsNullOrEmpty(nomeNodo))
                            nomeNodo = layerGroup.name;

                        GetGroupLayerDto inGruppo = TOA.assemblaGetDettaglioGruppo(input);
                        inGruppo.nomeNodo = nomeNodo;

                        LayerDTO groupLayer = rest.getLayergroupsByName(inGruppo, logger);

                        if (groupLayer != null)
                        {
                            Published layer = new Published();
                            layer.name = layerGroup.name;
                            layer.tipoPublished = "layerGroup";
                            layer.href = dto.baseUrl + "/rest/workspaces/" + dto.workspace + "/layergroups/" + layerGroup.name + ".json";
                            groupLayer.layerGroup.publishables.published.Add(layer);
                            //groupLayer.layerGroup.publishables.published = layer;
                            Style stile = new Style();
                            stile.href = "###";
                            stile.name = "###";
                            groupLayer.layerGroup.styles.style.Add(stile);
                            groupLayer.baseUrl = this.Server;
                            groupLayer.password = this.Password;
                            groupLayer.username = this.UserName;
                            groupLayer.workspace = this.DefaultWorkSpace;
                            while (groupLayer.layerGroup.publishables.published.Count !=
                             groupLayer.layerGroup.styles.style.Count)
                            {
                                if (groupLayer.layerGroup.publishables.published.Count > groupLayer.layerGroup.styles.style.Count)
                                {
                                    Style style = new Style();
                                    style.href = "";
                                    style.name = "";
                                    groupLayer.layerGroup.styles.style.Add(style);
                                }
                                else
                                {
                                    Published pubbl = new Published();
                                    pubbl.name = titoloLayer;
                                    pubbl.tipoPublished = "layer";
                                    pubbl.href = dto.baseUrl + "/rest/workspaces/" + dto.workspace + "/layers/" + titoloLayer + ".json";
                                    groupLayer.layerGroup.publishables.published.Add(pubbl);

                                }
                            }

                            if (!rest.AddLayerToGroup(groupLayer, logger))
                            {
                                this.Cursor = Cursors.Default;
                                return;
                            }
                        }
                        else
                        {
                            this.Cursor = Cursors.Default;
                            return;
                        }


                    }
                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo!");
                    reloadTreeView();
                }
            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "Add Layer Group Open");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Aggiunta Gruppo");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }

        }

        private void editLayerGroup(String titoloLayer, String nomeLayer, String nomeNodo)
        {

            RestApiGeoServer rest = new RestApiGeoServer();
            Utility util = new Utility();
            try
            {
                if (String.IsNullOrEmpty(titoloLayer.Trim()))
                {
                    util.message(TipoMessaggio.WARNING, "Titolo obbligatorio");
                    return;

                }

                GetListaLayerInDto inLista = TOA.assemblaGetListaLayerInDto(this.Server, this.Password, this.UserName, this.DefaultWorkSpace);
                GetGroupLayerDto inDto = TOA.assemblaGetDettaglioGruppo(inLista);
                inDto.nomeNodo = nomeLayer;

                LayerDTO dettaglioLayer = rest.getLayergroupsByName(inDto, logger);

                CreateLayerGroupInDto layerGroup = new CreateLayerGroupInDto();
                layerGroup = TOA.assemblaLayerGroup(inLista, dettaglioLayer.layerGroup);
                Style stile = new Style();
                Published pub = new Published();
                List<GenericLayer> listaLayers = (List<GenericLayer>)bsDisponibili.DataSource;
                foreach (GenericLayer item in clbLayersSelezionati.Items)
                {
                    pub = new Published();
                    stile = new Style();

                    pub.name = inDto.workspace + ":" + item.nome;
                    pub.name = item.nome;
                    if (isLayerGroup(item.nome))
                    {
                        //pub.href = this.Server + "/workspaces/" + inDto.workspace + "/layergroups/" + item.ToString();
                        pub.href = this.Server + "/workspaces/" + inDto.workspace + "/layergroups/" + item.nome;
                        pub.tipoPublished = "layerGroup";
                        layerGroup.publishables.published.Add(pub);

                        stile.href = this.Server;
                        stile.name = "##";
                        layerGroup.styles.style.Add(stile);
                    }
                    else
                    {
                        pub.href = this.Server + "/workspaces/" + inDto.workspace + "/layers/" + item.nome;
                        pub.tipoPublished = "layer";
                        layerGroup.publishables.published.Add(pub);

                        GeoServerDto input = TOA.assemblaGeoServerDTO(inLista);
                        input.storeName = item.nome;

                        LayerDetailDto dettaglio = rest.getDettaglioLayer(input, logger);
                        stile.href = dettaglio.layer.defaultStyle.href;
                        stile.name = dettaglio.layer.defaultStyle.name;
                        layerGroup.styles.style.Add(stile);
                    }

                }

                layerGroup.title = titoloLayer.Trim();

                if (rest.UpdateLayerGroup(layerGroup, logger))
                {
                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo!");
                    reloadTreeView();
                }

            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "Edit Layer Group ");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Modifica Gruppo");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }

        }

        private void btnCloseLayerGroup_Click(object sender, EventArgs e)
        {
            rimuoviTabs();
            reloadTreeView();

        }

        private void tsmCreaAreaTematica_Click(object sender, EventArgs e)
        {
            isEditLayerGroup = false;
            lblLayerDisponibili.Text = "Tematismi Disponibili";
            lblLayerSelezionati.Text = "Tematismi Selezionati";
            visualizzaTabCreaLayerGroup("AreaTematica");
        }

        private void tsmCreaTematismo_Click(object sender, EventArgs e)
        {
            isEditLayerGroup = false;
            lblLayerDisponibili.Text = "Gruppi/Layer Disponibili";
            lblLayerSelezionati.Text = "Gruppi/Layer Selezionati";
            visualizzaTabCreaLayerGroup("Tematismo");
        }

        private void tsmCreaLayerGroup_Click(object sender, EventArgs e)
        {

            isEditLayerGroup = false;
            lblLayerDisponibili.Text = "Gruppi/Layer Disponibili";
            lblLayerSelezionati.Text = "Gruppi/Layer Selezionati";

            visualizzaTabCreaLayerGroup("LayerGroup");

        }
       
        private void tsmModificaGroup_Click(object sender, EventArgs e)
        {
            isEditLayerGroup = true;
            rimuoviTabs();
            visualizzaTabModificaLayerGroup();
            this.tabDettaglio.TabPages.Add(this.tabCreaGruppo);

        }
        #endregion

        #region Common

        private bool isLayerGroup(string nomeNodo)
        {
            return (nomeNodo.EndsWith(Costanti.AREA_TEMATICA_SUFFIX) || nomeNodo.EndsWith(Costanti.TEMATISMO_SUFFIX) ||
                nomeNodo.EndsWith(Costanti.LAYER_GROUP_SUFFIX));

        }

        private void rimuoviTabs()
        {
            txtNameLayerGroup.Text = "";
            txtTitleLayerGroup.Text = "";
            txtFindLayers.Text = "";
            clbLayerDisponibili.DataSource = null;
            clbLayersSelezionati.DataSource = null;
            clbLayerDaImportare.DataSource = null;
            clbStoresWMS.DataSource = null;

            this.tabDettaglio.TabPages.Remove(this.tabImporta);
            this.tabDettaglio.TabPages.Remove(this.tabImportaWMS);
            this.tabDettaglio.TabPages.Remove(this.tabEliminaWMS);
            this.tabDettaglio.TabPages.Remove(this.tabModifica);
            this.tabDettaglio.TabPages.Remove(this.tabCreaGruppo);

        }

        private void reloadTreeView()
        {
            rimuoviTabs();
            txtTitleLayerGroup.Enabled = true;
            txtNameLayerGroup.Enabled = true;
            if (nodeOpened == null)
            {
                clbLayersSelezionati.Items.Clear();
                this.menuTreeView.Nodes.Clear();
                InitializeMenuTreeView();

            }
            else
            {
                refreshSelectedNode();
            }
        }

        private void refreshSelectedNode()
        {
            nodeOpened.Nodes.Clear();
            RestApiGeoServer proxy = new RestApiGeoServer();

            if (isLayerGroup(nodeOpened.Name))
            {
                //nel caso di area tematica o tematismo devo ricercare tra layer group
                nodesList = new List<string>();

                BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);
                GetGroupLayerDto inDto = TOA.assemblaGetDettaglioGruppo(dtoBase, nodeOpened.Name);
                LayerDTO dettaglioGroup = proxy.getLayergroupsByName(inDto, logger);

                if (dettaglioGroup != null && dettaglioGroup.layerGroup != null &&
                    dettaglioGroup.layerGroup.publishables != null && dettaglioGroup.layerGroup.publishables.published != null)
                {
                    List<Published> listaGruppi = dettaglioGroup.layerGroup.publishables.published.OrderBy(p => p.name).ToList();
                    foreach (var item in listaGruppi)
                    {

                        if (!item.name.StartsWith(String.Concat(this.DefaultWorkSpace, ":", Costanti.DEFAULT_LAYER)))
                        {
                            TreeNode nodeDetails = new TreeNode();
                            inDto.nomeNodo = item.name.Replace(this.DefaultWorkSpace + ":", "");
                            if (item.tipoPublished == "layerGroup")
                            {
                                LayerDTO gruppo = proxy.getLayergroupsByName(inDto, logger);
                                nodeDetails.Name = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                nodeDetails.Text = gruppo.layerGroup.title;
                                nodeDetails.Nodes.Add(Costanti.VOID_MENU).Name = Costanti.VOID_MENU;
                                nodeOpened.Nodes.Add(nodeDetails);
                            }
                            else
                            {
                                GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(dtoBase, item.href);

                                LayerDetailDto dettaglio = proxy.getDettaglioLayer(inDettaglio, logger);

                                GetFeatureTypeDto input = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                                if (dettaglio.layer.type != Costanti.TIPO_LAYER_RASTER)
                                {
                                    FeaturesTypeDto outDto = proxy.getFeatureTypeByNomeLayer(input, logger);
                                    nodeDetails.Text = outDto.featureType.title;
                                }
                                else
                                {
                                    CoverageDto outDto = proxy.getFeatureTypeRasterByNomeLayer(input, logger);
                                    nodeDetails.Text = outDto.coverage.title;
                                }

                                nodeDetails.Name = item.name.Replace(this.DefaultWorkSpace + ":", "");

                                nodeOpened.Nodes.Add(nodeDetails);
                            }
                        }
                    }
                }

                if (nodeOpened.Nodes.Count > 1)
                    nodeOpened.Nodes.RemoveByKey(Costanti.VOID_MENU);
            }
            else
            {
                this.menuTreeView.Nodes.Clear();// = new TreeView();
                  InitializeMenuTreeView();
            }
        }

        public bool loadfileConfig()
        {
            Utility util = new Utility();

            try
            {

                ExeConfigurationFileMap fileMap = new ExeConfigurationFileMap();
                //fileMap.ExeConfigFilename = @"C:\Urbamid\ConfigurationManager.exe.config";
                fileMap.ExeConfigFilename = Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "Urbamid", "Config", "ConfigurationManager.exe.config");
                if (!File.Exists(fileMap.ExeConfigFilename))
                {
                    util.message(TipoMessaggio.ERRORE, "File non trovato - " + fileMap.ExeConfigFilename);
                    return false;
                }
                System.Configuration.Configuration config = ConfigurationManager.OpenMappedExeConfiguration(fileMap,
                    ConfigurationUserLevel.None);
                

                this.Server = config.AppSettings.Settings["geoServerUrl"].Value;
                this.UserName = config.AppSettings.Settings["userName"].Value;
                this.Password = config.AppSettings.Settings["password"].Value;
                this.DefaultWorkSpace = config.AppSettings.Settings["workSpace"].Value;

                this.WrapperUrl = config.AppSettings.Settings["wrapperUrl"].Value;
                this.DataStore = config.AppSettings.Settings["dbStore"].Value;


                util.scriviLog("File Config: " + fileMap.ExeConfigFilename);
                util.scriviLog("GeoServer: " + this.Server);
                util.scriviLog("Wrapper: " + this.WrapperUrl);


                if (String.IsNullOrEmpty(this.Server) || String.IsNullOrEmpty(this.UserName) ||
                    String.IsNullOrEmpty(this.Password) || String.IsNullOrEmpty(this.DefaultWorkSpace))
                {
                    util.message(TipoMessaggio.ERRORE, "Attenzione parametri non configurati correttamente!");
                    return false;

                }
                return true;
            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "Lettura File Configurazione");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return false;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Lettura File Configurazione");
                return false;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }



        }

        #endregion


        #region Form Load
        private void frmManageGeoServer_Load(object sender, EventArgs e)
        {
            Utility util = new Utility();

            #region ToolTips
            toolTip.SetToolTip(btnConfirmEditLayer, "Modifica Layer");
            toolTip.SetToolTip(btnImportaShape, "Procedura Importazione Layer");
            toolTip.SetToolTip(btnCreaLayerGroup, "Crea Gruppo di Layer");

            toolTip.SetToolTip(btnCloseLayerGroup, "Annulla");
            toolTip.SetToolTip(btnUndoEditLayer, "Annulla");
            toolTip.SetToolTip(btnChiudiImporta, "Annulla");
         
            #endregion

            if (!loadfileConfig())
            {
                this.Close();
                return;
            }

            rimuoviTabs();

            txtServer.Text = this.Server;
            txtUser.Text = this.UserName;
            InitializeMenuTreeView();
        }

        private void configurazioneToolStripMenuItem_Click(object sender, EventArgs e)
        {
            frmConfigurazione frmConfig = new UrbamidAddIn.frmConfigurazione();
            frmConfig.FormClosed += new FormClosedEventHandler(frmConfig_FormClosed);
            frmConfig.ShowDialog();
        }

        private void frmConfig_FormClosed(object sender, FormClosedEventArgs e)
        {

            if (e.CloseReason.ToString() == "None")
            {
                this.Password = ((UrbamidAddIn.frmConfigurazione)sender).Password;
                this.Server = ((UrbamidAddIn.frmConfigurazione)sender).Geoserver;
                this.UserName = ((UrbamidAddIn.frmConfigurazione)sender).Username;
            }

        }
        #endregion


        private void tsmElimina_Click(object sender, EventArgs e)
        {
            eliminaLayer();
        }

        private void tsmPubblicaStile_Click(object sender, EventArgs e)
        {
            RestApiGeoServer rest = new RestApiGeoServer();
            Utility util = new Utility();
            try
            {

                String nomeNodo = menuTreeView.SelectedNode.Name;
                if (String.IsNullOrEmpty(nomeNodo))
                {
                    util.message(TipoMessaggio.ERRORE, "Nessun nodo selezionato!");
                    return;
                }
                else
                    nomeNodo = nomeNodo.Replace("urbamid:", "");



                BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);
                GeoServerDto input = TOA.assemblaGeoServerDTO(dtoBase, String.Empty);
                input.layerName = nomeNodo;
                input.storeName = nomeNodo;
                LayerDetailDto dettaglio = rest.getDettaglioLayer(input, logger);
                if (dettaglio.layer.type.Equals(Costanti.TIPO_LAYER_RASTER))
                {
                    util.message(TipoMessaggio.INFO, "Funzionalità non disponibile per i Raster");
                    return;

                }

                if (dettaglio.layer.type.Equals(Costanti.TIPO_LAYER_WMS))
                {
                    util.message(TipoMessaggio.INFO, "Funzionalità non disponibile per layer WMS");
                    return;

                }

                String nomeLayer = util.getFileStyleName(input.layerName);
                if (String.IsNullOrEmpty(nomeLayer))
                {
                    util.message(TipoMessaggio.ERRORE, "Nessun layer selezionato!");
                    return;
                }

                String messaggio = String.Format("ATTENZIONE, Si sta procedendo alla pubblicazione dello stile {0}, per il layer {1}. Continuare?",
                   nomeLayer, nomeNodo);

                if (MessageBox.Show(messaggio, "Attenzione", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    this.Cursor = Cursors.WaitCursor;

                    if (rest.publishSLD(input, ArcMap.Document.SelectedLayer.Name, logger))
                    {
                        util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                        reloadTreeView();
                    }

                }
            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "PubblicaStile CLICK");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
               
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "PubblicaStile CLICK");
               
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }

        }

        private void txtFindLayers_TextChanged(object sender, EventArgs e)
        {
            List<GenericLayer> layerDisp = layersDisponibili;

            clbLayerDisponibili.BeginUpdate();
            clbLayerDisponibili.DataSource = null;

            layerDisp = layerDisp.FindAll(p => p.titolo.ToUpper().Contains(txtFindLayers.Text.ToUpper()));

            clbLayerDisponibili.DataSource = layerDisp;
            clbLayerDisponibili.DisplayMember = "titolo";
            clbLayerDisponibili.ValueMember = "nome";
            clbLayerDisponibili.EndUpdate();
            ListBox lista = new ListBox();
            lista.Items.AddRange(this.clbLayersSelezionati.Items);

            foreach (var item in lista.Items)
            {
                int i = 0;
                foreach (var layer in clbLayerDisponibili.Items)
                {
                    if (layer == item)
                    {
                        clbLayerDisponibili.SetItemCheckState(i, CheckState.Checked);
                        break;
                    }
                    i++;
                }
            }
            
        }

        private void clbLayersSelezionati_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            int index = this.clbLayersSelezionati.IndexFromPoint(e.Location);
            GenericLayer item = (GenericLayer)this.clbLayersSelezionati.Items[index];



            int i = 0;
            foreach (var layer in clbLayerDisponibili.Items)
            {
                GenericLayer elemento = (GenericLayer)layer;
                listaSelezionati = (List<GenericLayer>)bsSelezionati.DataSource;
                if (elemento.nome == item.nome)
                {
                    listaSelezionati.RemoveAt(index);
                    clbLayerDisponibili.SetItemCheckState(i, CheckState.Unchecked);
                    break;
                }
                i++;
            }
            this.clbLayersSelezionati.SelectedIndex = -1;
            bsSelezionati.ResetBindings(false);

        }

        private void clbLayerDisponibili_BindingContextChanged(object sender, EventArgs e)
        {
            listaSelezionati = (List<GenericLayer>)bsSelezionati.DataSource;
            if (listaSelezionati != null)
            {
                for (int i = 0; i < this.clbLayerDisponibili.Items.Count; i++)
                {
                    GenericLayer layer = (GenericLayer)this.clbLayerDisponibili.Items[i];
                    if (listaSelezionati.FindAll(p => p.nome == layer.nome).Count > 0)
                        this.clbLayerDisponibili.SetItemChecked(i, true);


                }
            }

        }

        private bool importaFileRaster(String shapeFile, BaseDto dtoBase, Logger logger)
        {
            Utility util = new Utility();
            try
            {

                bool esito = false;
                String messaggio = String.Empty;
                RestApiGeoServer proxy = new RestApiGeoServer();
                DialogResult dialogResult;
                
                String nomeLayer = Path.GetFileNameWithoutExtension(shapeFile);
                String nomeFile = String.Empty;
                if (shapeFile.ToUpper().EndsWith(".TIF") || shapeFile.ToUpper().EndsWith(".TIFF"))
                {
                    byte[] arrByte = null;

                    nomeFile = Path.GetFileName(shapeFile);

                    using (FileStream fs = new FileStream(shapeFile, FileMode.Open, FileAccess.Read))
                    {
                        byte[] bytes = System.IO.File.ReadAllBytes(shapeFile);
                        fs.Read(bytes, 0, System.Convert.ToInt32(fs.Length));
                        fs.Close();
                        arrByte = bytes;

                    }


                    UploadRasterDto inDtoUploadRaster = TOA.assmblaUploadRaster(dtoBase, arrByte, nomeFile);
                    inDtoUploadRaster.extension = ".worldimage";
                    proxy.UploadRaster(inDtoUploadRaster, logger);

                }
                else
                {
                    IWorkspaceFactory workspaceFactory = new ESRI.ArcGIS.DataSourcesGDB.FileGDBWorkspaceFactory();
                    IWorkspace workspace = workspaceFactory.OpenFromFile(Path.GetDirectoryName(shapeFile), 0);
                    IRasterWorkspaceEx rasterEX = (IRasterWorkspaceEx)workspace;
                    IRasterWorkspaceEx raster2 = (IRasterWorkspaceEx)workspace;
                    IRasterCatalog rasterCatalog = null;

                    try
                    {
                        rasterCatalog = rasterEX.OpenRasterCatalog(nomeLayer);
                    }
                    catch (Exception)
                    {
                        rasterCatalog = raster2.OpenRasterCatalog(nomeLayer);
                    }


                    IFeatureClass fClass = (IFeatureClass)rasterCatalog;
                    IQueryFilter queryFilterlast = new QueryFilter();
                    IFeatureCursor pFCursor;
                    IFeature pF;

                    queryFilterlast.WhereClause = null;
                    pFCursor = fClass.Search(queryFilterlast, false);
                    pF = pFCursor.NextFeature();
                    int index = 1;
                    while (pF != null && pF.Shape != null && pF.Shape.IsEmpty == false)
                    {
                        index++;
                        pF = pFCursor.NextFeature();
                    }

                    this.Cursor = Cursors.Default;

                    messaggio = String.Format("Attenzione saranno importati {0} raster. L'operazione potrebbe richiedere diversi minuti. Si vuole continuare?", index - 1);
                    dialogResult = MessageBox.Show(messaggio, "ATTENZIONE", MessageBoxButtons.YesNo);
                    if (dialogResult == DialogResult.No)
                        return true;

                    this.Cursor = Cursors.WaitCursor;
                    for (int i = 1; i < index; i++)
                    {
                        int indexI = fClass.GetFeature(i).Table.FindField("NAME");
                        nomeFile = fClass.GetFeature(i).Value[indexI].ToString();

                        IRasterCatalogItem catalogItem = (IRasterCatalogItem)fClass.GetFeature(i);
                        IRasterDataset rasterDataset = catalogItem.RasterDataset;
                        IRaster raster = rasterDataset.CreateDefaultRaster();


                        raster.ResampleMethod = rstResamplingTypes.RSP_NearestNeighbor;    //rstResamplingTypes.RSP_VectorAverage;
                        IRasterExporter pConverter3 = new RasterExporter();
                        byte[] arrByte;
                        arrByte = pConverter3.ExportToBytes(raster, "TIFF"); //
                        UploadRasterDto inDtoUploadRaster = TOA.assmblaUploadRaster(dtoBase, arrByte, nomeFile);
                        proxy.UploadRaster(inDtoUploadRaster, logger);

                    }
                }

                GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(dtoBase, String.Empty);
                inDettaglio.storeName = System.IO.Path.GetFileNameWithoutExtension(nomeFile);
                LayerDetailDto dettaglio = proxy.getDettaglioLayer(inDettaglio, logger);
                GetFeatureTypeDto inFeatureDto = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                CoverageDto outDto = proxy.getFeatureTypeRasterByNomeLayer(inFeatureDto, logger);

                String nomeNodo = menuTreeView.SelectedNode.Name;
                nomeNodo = nomeNodo.Replace(dtoBase.workspace + ":", "");

                GetGroupLayerDto input = TOA.assemblaGroupLayer(dtoBase, nomeNodo);

                LayerDTO groupLayer = proxy.getLayergroupsByName(input, logger);

                if (groupLayer == null)
                    return esito;

                while (groupLayer.layerGroup.publishables.published.Count != groupLayer.layerGroup.styles.style.Count)
                {
                    if (groupLayer.layerGroup.publishables.published.Count > groupLayer.layerGroup.styles.style.Count)
                    {
                        Style style = new Style
                        {
                            href = dettaglio.layer.defaultStyle.href,
                            name = dettaglio.layer.defaultStyle.name
                        };
                        groupLayer.layerGroup.styles.style.Add(style);
                    }
                    else
                    {
                        Published pubbl = new Published
                        {
                            name = nomeLayer,
                            tipoPublished = "layer",
                            href = dtoBase.baseUrl + "/rest/workspaces/" + dtoBase.workspace + "/layers/" + inDettaglio.storeName + ".json"
                        };
                        groupLayer.layerGroup.publishables.published.Add(pubbl);

                    }
                }

                Published layer = new Published();
                layer.name = inDettaglio.storeName;
                layer.tipoPublished = "layer";
                layer.href = dtoBase.baseUrl + "/rest/workspaces/" + dtoBase.workspace + "/layers/" + inDettaglio.storeName + ".json";
                groupLayer.layerGroup.publishables.published.Add(layer);
                //groupLayer.layerGroup.publishables.published = layer;

                Style stile = new Style();
                stile.href = dettaglio.layer.defaultStyle.href;
                stile.name = dettaglio.layer.defaultStyle.name;
                groupLayer.layerGroup.styles.style.Add(stile);
                groupLayer.baseUrl = this.Server;
                groupLayer.password = this.Password;
                groupLayer.username = this.UserName;
                groupLayer.workspace = this.DefaultWorkSpace;


                if (!proxy.AddLayerToGroup(groupLayer, logger))
                    return esito;

                return true;

            }
            catch (UrbamidException exx)
            {
                logger.Error(exx, "Errore Importa File Raster");
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                return false;
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Importazione Raster");
                return false;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private void chkAbilitaRicerca_CheckedChanged(object sender, EventArgs e)
        {
            clbLayerDisponibili.Enabled = ((CheckBox)sender).Checked;
            clbLayersSelezionati.Enabled = ((CheckBox)sender).Checked;
            txtFindLayers.Enabled = ((CheckBox)sender).Checked;


            if (((CheckBox)sender).Checked && clbLayerDisponibili.Items.Count == 0)
            {
                String nomeLayer = this.menuTreeView.SelectedNode.Name.Replace(this.DefaultWorkSpace + ":", "");
                String titoloLayer = this.menuTreeView.SelectedNode.Text;

                //Attivo il caricamento dei layer disponibili e associati
                GetListaLayerInDto input = new GetListaLayerInDto(); //aggiunto ora per compilare
                RestApiGeoServer service = new RestApiGeoServer();
                Utility util = new Utility();
                try
                {
                    this.Cursor = Cursors.WaitCursor;

                    List<LayerGroup> lsGruppi = new List<LayerGroup>();
                    input = TOA.assemblaGetListaLayerInDto(this.Server, this.Password, this.UserName, this.DefaultWorkSpace);
                    layersDisponibili = new List<GenericLayer>();




                    if (nomeLayer.EndsWith(Costanti.AREA_TEMATICA_SUFFIX))
                    {
                        lsGruppi = service.getListaLayerGroupInWorkspace(input, logger);
                        lsGruppi = lsGruppi.FindAll(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX));
                        lsGruppi.RemoveAll(p => p.name.StartsWith("DEFAULT_"));
                    }
                    else if (nomeLayer.EndsWith(Costanti.TEMATISMO_SUFFIX))
                    {
                        lsGruppi = service.getListaLayerGroupInWorkspace(input, logger);
                        lsGruppi.RemoveAll(p => p.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX));
                        lsGruppi.RemoveAll(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX));
                        lsGruppi.RemoveAll(p => p.name.StartsWith("DEFAULT_"));

                    }


                    foreach (LayerGroup gruppo in lsGruppi)
                    {

                        GetGroupLayerDto inDettGruppo = TOA.assemblaGetDettaglioGruppo(input);
                        inDettGruppo.link = gruppo.href;
                        LayerDTO outGruppo = service.getLayergroupsByName(inDettGruppo, logger);
                        GenericLayer genDTO = TOA.assemblaGenericLayer(outGruppo.layerGroup);
                        layersDisponibili.Add(genDTO);
                    }


                    if (nomeLayer.EndsWith(Costanti.TEMATISMO_SUFFIX) || nomeLayer.EndsWith(Costanti.LAYER_GROUP_SUFFIX))
                    {
                        List<Layer> listaLayers = service.getListaLayerInWorkspace(input, logger); //recupero tutti i layers presenti nel WORKSPACE
                        listaLayers.RemoveAll(p => p.name.StartsWith("DEFAULT_"));


                        foreach (Layer item in listaLayers)
                        {

                            GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(input);
                            inDettaglio.linkDettaglio = item.link;
                            LayerDetailDto outDettaglio = service.getDettaglioLayer(inDettaglio, logger);

                            GetFeatureTypeDto inFeature = TOA.assemblaFeatureTypeDto(input);
                            inFeature.link = outDettaglio.layer.resource.href;
                            FeaturesTypeDto outFeature = service.getFeatureTypeByNomeLayer(inFeature, logger);
                            GenericLayer genDto = TOA.assemblaGenericLayer(outDettaglio.layer);
                            if (outFeature != null && outFeature.featureType != null)
                                genDto.titolo = outFeature.featureType.title;
                            else
                                genDto.titolo = outDettaglio.layer.name;

                            layersDisponibili.Add(genDto);

                        }
                    }




                    GetGroupLayerDto inDto = TOA.assemblaGetDettaglioGruppo(input);
                    inDto.nomeNodo = nomeLayer;
                    LayerDTO layGroup = service.getLayergroupsByName(inDto, logger);

                    List<GenericLayer> layerSelezionati = new List<GenericLayer>();
                    if (layGroup != null && layGroup.layerGroup != null)
                    {
                        foreach (Published item in layGroup.layerGroup.publishables.published)
                        {

                            if (item.tipoPublished == "layer")
                            {
                                GeoServerDto inLayerDto = TOA.assemblaGeoServerDTO(input);
                                inLayerDto.linkDettaglio = item.href;
                                LayerDetailDto outDettaglio = service.getDettaglioLayer(inLayerDto, logger);
                                GenericLayer generic = TOA.assemblaGenericLayer(outDettaglio.layer);
                                GetFeatureTypeDto inFeature = TOA.assemblaFeatureTypeDto(input);
                                inFeature.link = outDettaglio.layer.resource.href;
                                FeaturesTypeDto outFeature = service.getFeatureTypeByNomeLayer(inFeature, logger);

                                if (outFeature != null && outFeature.featureType != null)
                                    generic.titolo = outFeature.featureType.title;
                                else
                                    generic.titolo = outDettaglio.layer.name;
                                layerSelezionati.Add(generic);
                            }
                            else
                            {
                                GetGroupLayerDto inDettGruppo = TOA.assemblaGetDettaglioGruppo(input);
                                inDettGruppo.link = item.href;
                                LayerDTO outGruppo = service.getLayergroupsByName(inDettGruppo, logger);
                                layerSelezionati.Add(TOA.assemblaGenericLayer(outGruppo.layerGroup));
                            }
                        }

                        layerSelezionati.RemoveAll(p => p.nome.ToUpper().StartsWith(Costanti.DEFAULT_LAYER));
                        bsSelezionati.DataSource = layerSelezionati;
                        clbLayersSelezionati.DataSource = bsSelezionati;
                        clbLayersSelezionati.DisplayMember = "titolo";
                        clbLayersSelezionati.ValueMember = "nome";
                        clbLayerDisponibili.BeginUpdate();

                        bsDisponibili.DataSource = layersDisponibili;
                        clbLayerDisponibili.DataSource = bsDisponibili;
                        clbLayerDisponibili.DisplayMember = "titolo";
                        clbLayerDisponibili.ValueMember = "nome";
                        bsDisponibili.ResetBindings(false);
                        clbLayerDisponibili.EndUpdate();

                    }


                }
                catch (UrbamidException exx)
                {
                    logger.Error(exx, "Errore Abilita Ricerca");
                    util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
                    
                }
                catch (Exception ex)
                {
                    logger.Error(ex, ex.Message);
                    util.message(TipoMessaggio.ERRORE, "Errore Abilita Ricerca");
                }
                finally
                {
                    this.Cursor = Cursors.Default;
                }
            }



        }

        private void clbLayerDaImportare_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            if (e.Index == 0)
            {
                if (e.NewValue == CheckState.Checked)
                {
                    for (int i = 1; i < clbLayerDaImportare.Items.Count; i++)
                    { 
                        clbLayerDaImportare.SetItemChecked(i, true);
                    }
                }
                else
                {
                    for (int i = 1; i < clbLayerDaImportare.Items.Count; i++)
                    {
                        clbLayerDaImportare.SetItemChecked(i, false);
                    }
                }
            }
        }

        private void btnSalvaWMS_Click(object sender, EventArgs e)
        {
            Utility util = new Utility();
            RestApiGeoServer restService = new RestApiGeoServer();
            try
            {
                List<TipologicaDTO> layers = new List<TipologicaDTO>();

                BaseDto dtoBase = new BaseDto()
                {
                    password = this.Password,
                    username = this.UserName,
                    workspace = this.DefaultWorkSpace,
                    baseUrl = this.Server
                };


                if (txtLinkWMS.Enabled)
                {
                    if (String.IsNullOrEmpty(txtLinkWMS.Text) || String.IsNullOrWhiteSpace(txtLinkWMS.Text))
                    {
                        util.message(TipoMessaggio.INFO, "Attenzione Link WMS non valido");
                        return;
                    }

                    if (String.IsNullOrEmpty(txtNomeWMS.Text) || String.IsNullOrWhiteSpace(txtNomeWMS.Text) || txtNomeWMS.Text.Contains(" "))
                    {
                        util.message(TipoMessaggio.INFO, "Attenzione Nome WMS non valido");
                        return;
                    }
                    this.Cursor = Cursors.WaitCursor;
                    // Verifico che non sia presente già uno store con quel nome
                    WMSStoreDto inputDto = TOA.assembler(dtoBase, txtNomeWMS.Text);

                    if (restService.ExistStoreWMS(inputDto, logger))
                    {
                        util.message(TipoMessaggio.INFO, "Esiste già un WMS con questo nome");
                        return;
                    }
                    else
                    {
                        //Aggiungo lo store a GeoServer
                        inputDto.linkWMS = txtLinkWMS.Text.Trim();
                        restService.AddStoreWMS(inputDto, logger);
                        util.message(TipoMessaggio.INFO, "Store aggiunto a GeoServer. Selezionare i Layers da importare");
                        //Leggo i Layers dallo store
                        // Nel caso sia disabilitato la textbox devo caricare i layers da WMS
                        this.lblSelLayersWMS.Visible = true;
                        this.clbLayerWMS.Visible = true;
                        layers = restService.GetLayersFromWMS(inputDto, logger);
                        clbLayerWMS.DataSource = layers;
                        ((ListBox)clbLayerWMS).DisplayMember = "Titolo";
                        ((ListBox)clbLayerWMS).ValueMember = "Nome";

                        txtLinkWMS.Enabled = false;
                        txtNomeWMS.Enabled = false;
                        lblSelLayersWMS.Visible = true;
                        clbLayerWMS.Visible = true;
                    }
                }
                else
                {
                 
                    WMSStoreDto inputDto = TOA.assembler(dtoBase, txtNomeWMS.Text);
                    // importo i Layers selezionati
                    String messaggio = $"Si sta procedendo all'import dei layers selezionati. L'operazione potrebbe richiedere qualche minuto. Continuare?";
                    if (MessageBox.Show(messaggio, "Attenzione", MessageBoxButtons.YesNo) == DialogResult.No)
                        return;

                    this.Cursor = Cursors.WaitCursor;
                    foreach (TipologicaDTO layer in clbLayerWMS.CheckedItems)
                    {
                        if (layer.Nome == "SelectALL")
                            continue;


                        LayerWMSDto layerWmsDTO = TOA.Assembler(inputDto, layer);

                        restService.AddLayerWMS(layerWmsDTO, logger);
                        GeoServerDto dto = TOA.AssemblerGeoServer(dtoBase, layer.Nome);

                        LayerDetailDto dettaglioLayer = restService.getDettaglioLayer(dto, logger);
                        if (dettaglioLayer != null && dettaglioLayer.layer != null
                            && dettaglioLayer.layer.resource != null 
                            && !String.IsNullOrEmpty(dettaglioLayer.layer.resource.href))
                        {
                            //REPROJECT TO 4326 && ADD TITLE
                            layerWmsDTO.linkWMS = dettaglioLayer.layer.resource.href;
                            restService.ReprojectLayer(layerWmsDTO, logger);



                            GetGroupLayerDto gruppoIn= new GetGroupLayerDto();
                            gruppoIn.baseUrl = this.Server;
                            gruppoIn.nomeNodo = menuTreeView.SelectedNode.Name.Replace("urbamid:", ""); ;
                            gruppoIn.password = this.Password;
                            gruppoIn.username = this.UserName;
                            gruppoIn.workspace = this.DefaultWorkSpace;

                            LayerDTO groupLayer = restService.getLayergroupsByName(gruppoIn, logger);

                            if (groupLayer != null)
                            {

                                while (groupLayer.layerGroup.publishables.published.Count !=
                                    groupLayer.layerGroup.styles.style.Count)
                                {
                                    if (groupLayer.layerGroup.publishables.published.Count > groupLayer.layerGroup.styles.style.Count)
                                    {
                                        Style style = new Style();

                                        style.href = dettaglioLayer.layer.defaultStyle.href; //dettaglioLayer.layer.defaultStyle.href;
                                        style.name = dettaglioLayer.layer.defaultStyle.name;
                                        groupLayer.layerGroup.styles.style.Add(style);
                                    }
                                    else
                                    {
                                        Published pubbl = new Published();
                                        pubbl.name = dettaglioLayer.layer.name.Replace("urbamid:", "");
                                        pubbl.tipoPublished = "layer";
                                        pubbl.href = dto.baseUrl + "/rest/workspaces/" + dto.workspace + "/layers/" + layer.Nome + ".json";
                                        groupLayer.layerGroup.publishables.published.Add(pubbl);

                                    }
                                }
                                Published layerPubl = new Published();
                                layerPubl.name = dettaglioLayer.layer.name;
                                layerPubl.tipoPublished = "layer";
                                layerPubl.href = dto.baseUrl + "/rest/workspaces/" + dto.workspace + "/layers/" + dettaglioLayer.layer.name + ".json";
                                groupLayer.layerGroup.publishables.published.Add(layerPubl);
                                //groupLayer.layerGroup.publishables.published = layer;

                                Style stile = new Style
                                {
                                    href = dettaglioLayer.layer.defaultStyle.href,
                                    name = dettaglioLayer.layer.defaultStyle.name
                                };
                                groupLayer.layerGroup.styles.style.Add(stile);
                                groupLayer.baseUrl = this.Server;
                                groupLayer.password = this.Password;
                                groupLayer.username = this.UserName;
                                groupLayer.workspace = this.DefaultWorkSpace;


                                if (!restService.AddLayerToGroup(groupLayer, logger))
                                    continue;

                            }
                            else
                                continue;
                        }


                       

                        
                    }
                    util.message(TipoMessaggio.INFO, "Operazione terminata!");
                    reloadTreeView();

                }
            }
            catch (UrbamidException exx)
            {
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Importazione WMS");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private void btnChiudiWMS_Click(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            rimuoviTabs();
            reloadTreeView();
            this.Cursor = Cursors.Default;
        }

        private void clbLayerWMS_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            if (e.Index == 0)
            {
                if (e.NewValue == CheckState.Checked)
                {
                    for (int i = 1; i < clbLayerWMS.Items.Count; i++)
                    {
                        clbLayerWMS.SetItemChecked(i, true);
                    }
                }
                else
                {
                    for (int i = 1; i < clbLayerWMS.Items.Count; i++)
                    {
                        clbLayerWMS.SetItemChecked(i, false);
                    }
                }
            }
        }

        private void tsmDeleteStoreWMS_Click(object sender, EventArgs e)
        {
            rimuoviTabs();
            Utility util = new Utility();
            try
            {
                RestApiGeoServer restService = new RestApiGeoServer();
                this.Cursor = Cursors.WaitCursor;
                this.tabDettaglio.TabPages.Add(this.tabEliminaWMS);
                BaseDto dtoBase = new BaseDto()
                {
                    password = this.Password,
                    username = this.UserName,
                    workspace = this.DefaultWorkSpace,
                    baseUrl = this.Server
                };

                List<TipologicaDTO> stores = restService.GetStoreWms(dtoBase, logger);
                clbStoresWMS.DataSource = stores;
                ((ListBox)clbStoresWMS).DisplayMember = "Titolo";
                ((ListBox)clbStoresWMS).ValueMember = "Nome";
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Recupero Store WMS");
               
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }


        }

        private void btnChiudiDeleteStoreWMS_Click(object sender, EventArgs e)
        {
            this.Cursor = Cursors.WaitCursor;
            rimuoviTabs();
            reloadTreeView();
            this.Cursor = Cursors.Default;
        }

        private void btnDeleteStoreWMS_Click(object sender, EventArgs e)
        {
            Utility util = new Utility();
            RestApiGeoServer restService = new RestApiGeoServer();
            try
            {
                BaseDto dtoBase = new BaseDto()
                {
                    password = this.Password,
                    username = this.UserName,
                    workspace = this.DefaultWorkSpace,
                    baseUrl = this.Server
                };

                String messaggio = $"Si sta procedendo alla cancellazione degli stores selezionati. Tutti i layer associati saranno eliminati. Continuare?";
                if (MessageBox.Show(messaggio, "Attenzione", MessageBoxButtons.YesNo) == DialogResult.No)
                    return;

                this.Cursor = Cursors.WaitCursor;
                foreach (TipologicaDTO store in clbStoresWMS.CheckedItems)
                {
                    if (store.Nome == "SelectALL")
                        continue;

                    restService.DeleteStoreWMS(dtoBase, store.Titolo, logger);
                }

                util.message(TipoMessaggio.INFO, "Operazione terminata!");
                reloadTreeView();

            }
            catch (UrbamidException exx)
            {
                util.message(TipoMessaggio.ERRORE, UrbamidException.GetMessaggio(exx.TipoErrore));
            }
            catch (Exception ex)
            {
                logger.Error(ex, ex.Message);
                util.message(TipoMessaggio.ERRORE, "Errore Cancellazione Store WMS");
                return;
            }
            finally
            {
                this.Cursor = Cursors.Default;
            }
        }

        private void clbStoresWMS_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            if (e.Index == 0)
            {
                if (e.NewValue == CheckState.Checked)
                {
                    for (int i = 1; i < clbStoresWMS.Items.Count; i++)
                        clbStoresWMS.SetItemChecked(i, true);
                }
                else
                {
                    for (int i = 1; i < clbStoresWMS.Items.Count; i++)
                        clbStoresWMS.SetItemChecked(i, false);
                }
            }
        }
    }
}