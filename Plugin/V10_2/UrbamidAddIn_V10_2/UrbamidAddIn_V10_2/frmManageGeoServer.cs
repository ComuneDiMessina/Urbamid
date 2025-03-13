using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Geodatabase;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using UrbamidAddIn_V10_2.Dto;
using UrbamidAddIn_V10_2.Utility;

namespace UrbamidAddIn
{
    public partial class frmManageGeoServer : Form
    {

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
        }

        #region MenuTreeView

        public void InitializeMenuTreeView()
        {
            RestApiGeoServer service = new RestApiGeoServer();
            Utility util = new Utility();
            TreeNode layersNode = new TreeNode("Layers Cartografici");
           
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
            LayerGroupDto listaLayers = service.getLayergroups(inDto);
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
                    LayerDTO dettaglioGroup = service.getLayergroupsByName(inDto);
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

        private void menuTreeView_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            cmsMenu.Items.Find("tsmImporta", true).FirstOrDefault().Visible = false;
            cmsMenu.Items.Find("tsmPubblicaStile", true).FirstOrDefault().Visible = false;
            cmsMenu.Items.Find("tsmModifica", true).FirstOrDefault().Visible = false;
            cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = false;
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

                }
                else if (menuTreeView.SelectedNode.Name.EndsWith(Costanti.TEMATISMO_SUFFIX))
                {
                    cmsMenu.Items.Find("tsmImporta", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmCreaLayerGroup", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmModificaGroup", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                }
                else if (menuTreeView.SelectedNode.Name.EndsWith(Costanti.LAYER_GROUP_SUFFIX))
                {
                    cmsMenu.Items.Find("tsmImporta", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmModificaGroup", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                }
                else if (menuTreeView.SelectedNode.Parent == null)
                    cmsMenu.Items.Find("tsmCreaAreaTematica", true).FirstOrDefault().Visible = true;
                else
                {
                    cmsMenu.Items.Find("tsmPubblicaStile", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmModifica", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                }



                cmsMenu.Show(new System.Drawing.Point(MousePosition.X, MousePosition.Y));
            }
            else
            {
                RestApiGeoServer service = new RestApiGeoServer();
                Utility util = new Utility();
                this.Cursor = Cursors.WaitCursor;
                try
                {


                    if (e.Node.LastNode == null)
                        return;

                    nodeOpened = e.Node.Parent;

                    this.menuTreeView.SelectedNode = e.Node;
                    if (e.Node.LastNode.Name == Costanti.VOID_MENU)
                    {
                        BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);
                        GetGroupLayerDto inDto = TOA.assemblaGetDettaglioGruppo(dtoBase, e.Node.Name);
                        LayerDTO dettaglioGroup = service.getLayergroupsByName(inDto);

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

                                    LayerDTO gruppo = service.getLayergroupsByName(inDto);
                                    nodeDetails.Name = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                    nodeDetails.Text = gruppo.layerGroup.title;
                                    nodeDetails.Nodes.Add(Costanti.VOID_MENU).Name = Costanti.VOID_MENU;
                                    e.Node.Nodes.Add(nodeDetails);

                                }
                                else
                                {

                                    GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(dtoBase, item.href);

                                    LayerDetailDto dettaglio = service.getDettaglioLayer(inDettaglio);

                                    GetFeatureTypeDto input = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                                    if (dettaglio.layer.type != Costanti.TIPO_LAYER_RASTER)
                                    {
                                        FeaturesTypeDto outDto = service.getFeatureTypeByNomeLayer(input);
                                        if (outDto != null && outDto.featureType != null && !String.IsNullOrEmpty(outDto.featureType.title))
                                        {
                                            nodeDetails.Text = outDto.featureType.title;
                                            if (item.name.Contains(Costanti.LAYERDB_PREFIX))
                                                nodeDetails.Text += " - DB";

                                        }




                                    }
                                    else
                                    {
                                        CoverageDto outDto = service.getFeatureTypeRasterByNomeLayer(input);
                                        nodeDetails.Text = outDto.coverage.title;
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

                catch (Exception ex)
                {

                    throw ex;
                }
                finally
                {
                    this.Cursor = Cursors.Default;
                }

            }
        }
        

        #endregion

        #region ImportaShapeFile

        private void btnImportaShape_Click(object sender, EventArgs e)
        {

            Utility util = new Utility();

            String nomeLayer = String.Empty;
            String titoloLayer = txtNomeLayer.Text.Trim();
            String nomeNodo = menuTreeView.SelectedNode.Name;
            RestApiGeoServer service = new RestApiGeoServer();
            String messaggio = String.Empty;
            DialogResult dialogResult;


            if (String.IsNullOrEmpty(ShapeFile))
            {
                util.message(TipoMessaggio.ERRORE, "Nessun file .shp trovato");
                return;
            }
            if (String.IsNullOrEmpty(nomeNodo))
            {
                util.message(TipoMessaggio.ERRORE, "Nessun nodo selezionato");
                return;
            }

            if (String.IsNullOrEmpty(titoloLayer))
            {
                util.message(TipoMessaggio.WARNING, "Titolo Layer obbligatorio!");
                txtNomeLayer.Focus();
                return;
            }

            nomeLayer = Path.GetFileNameWithoutExtension(this.ShapeFile);
            if (String.IsNullOrEmpty(nomeLayer))
            {
                util.message(TipoMessaggio.ERRORE, "Nessuno shape trovato");
                return;
            }

            BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);



            if (Path.GetDirectoryName(this.ShapeFile).ToUpper().EndsWith(".GDB") ||
                this.ShapeFile.ToUpper().EndsWith(".TIF") || this.ShapeFile.ToUpper().EndsWith(".TIFF"))
            {
                //E' un raster

                if (importaFileRaster(this.ShapeFile, dtoBase))
                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");

                this.Cursor = Cursors.Default;
                reloadTreeView();
                return;
            }



            ZipDto inputZip = new ZipDto();
            inputZip.pathFolder = Path.GetDirectoryName(this.ShapeFile);
            inputZip.nomeLayer = nomeLayer;
            inputZip.nomeFile = nomeLayer;
            if (!util.createZipFile(inputZip))
            {
                util.message(TipoMessaggio.ERRORE, "Zip non creato");
                return;
            }


            this.Cursor = Cursors.WaitCursor;
            GeoServerDto dto = new GeoServerDto();
            dto = TOA.assemblaGeoServerDTO(dtoBase, String.Empty);
            dto.pathFile = Path.Combine(inputZip.pathFolder, nomeLayer + ".zip");
            dto.layerName = nomeLayer; //Path.GetFileNameWithoutExtension(this.ShapeFile);
            dto.storeName = nomeLayer;

            //verifico se è stato creato il layer
            nomeLayer = nomeLayer.Replace(" ", "_");
            dto.layerName = nomeLayer;
            dto.storeName = nomeLayer;
            bool esisteLayerGEO = false;
            bool esisteLayerDB = false;
            bool esisteLayer = false;
            String nomeLayerDaCancellare = nomeLayer;
            
            //chiamata al wrapper 
            GetDettaglioLayer_InDto inputGet = WrapperTOA.assembler(this.WrapperUrl, nomeLayerDaCancellare, this.DefaultWorkSpace, this.ckShapeDB.Checked);
            GetDettaglioLayer_OutDto outDettaglio = service.getDettaglioLayer(inputGet);
            if (!outDettaglio.IsSuccess)
            {
                util.message(TipoMessaggio.WARNING, outDettaglio.Message);
                return;
            }

            esisteLayerGEO = outDettaglio.Exist;
            inputGet = WrapperTOA.assembler(this.WrapperUrl, String.Concat("u_geo_", nomeLayerDaCancellare.ToLower()), this.DefaultWorkSpace, this.ckShapeDB.Checked);
            outDettaglio = service.getDettaglioLayer(inputGet);
            esisteLayerDB = outDettaglio.Exist;
           
            esisteLayer = esisteLayerGEO || esisteLayerDB;

            if (!esisteLayer)
            {
                if (this.ckShapeDB.Checked)
                {
                    nomeLayerDaCancellare = Costanti.LAYERDB_PREFIX + nomeLayer.ToLower();
                    inputGet.nomeLayer = nomeLayerDaCancellare;

                    outDettaglio = service.getDettaglioLayer(inputGet);
                    if (!outDettaglio.IsSuccess)
                    {
                        util.message(TipoMessaggio.WARNING, outDettaglio.Message);
                        return;
                    }
                    else
                        esisteLayer = outDettaglio.Exist;
                    
                }
            }

            
            //Caso in cui esiste già il layer su GeoServer
            if (esisteLayer)
            {
                this.Cursor = Cursors.Default;
                messaggio = String.Format("Attenzione, esiste già il layer {0}, si vuole sovrascriverlo?", dto.layerName);
                dialogResult = MessageBox.Show(messaggio, "ATTENZIONE", MessageBoxButtons.YesNo);
                if (dialogResult == DialogResult.No)
                    return;
                else
                {
                   
                    this.Cursor = Cursors.WaitCursor;


                    if (esisteLayerDB)
                    {
                        DeleteLayer_InDto inDelLayer = WrapperTOA.assemblerDeleteLayer(this.WrapperUrl, this.DefaultWorkSpace, String.Concat("u_geo_", nomeLayerDaCancellare.ToLower()));
                        DeleteLayer_OutDto outDelLayer = service.deleteLayer(inDelLayer);
                        if (outDelLayer == null || !outDelLayer.IsSuccess)
                        {
                            this.Cursor = Cursors.Default;
                            util.message(TipoMessaggio.ERRORE, outDelLayer.Message);
                            return;
                        }
                    }

                    if(esisteLayerGEO)
                    {
                        DeleteDatastoreDto delete = new DeleteDatastoreDto();
                        delete.baseUrl = this.Server;
                        delete.nomeDataStore = nomeLayerDaCancellare;
                        delete.password = this.Password;
                        delete.username = this.UserName;
                        delete.workspace = this.DefaultWorkSpace;
                        if (!service.deleteDataStore(delete))
                            return;
                    }
                }

            }


            if (this.ckShapeDB.Checked)
            {
                UploadShape_InDto storeWrapperDto = WrapperTOA.assembler(this.WrapperUrl, Path.Combine(inputZip.pathFolder, nomeLayer + ".zip"));

                UploadShape_OutDto resp_UploadShapeToDb = service.UploadShapeToDB(storeWrapperDto);
                if (resp_UploadShapeToDb == null || !resp_UploadShapeToDb.IsSuccess)
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.ERRORE, resp_UploadShapeToDb.Message);
                    return;
                }

                ConvertShapeToDb_InDto inConvert = WrapperTOA.assemblerConvert(this.WrapperUrl, nomeLayer);
                ConvertShapeToDb_OutDto outConvert = service.convertShapeToDb(inConvert);
                if (outConvert == null || !outConvert.IsSuccess)
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.ERRORE, outConvert.Message);
                    return;
                }

                PublishTable_InDto inPublish = WrapperTOA.assembler(this.WrapperUrl, this.DataStore, nomeLayer, txtNomeLayer.Text, this.ckShapeDB.Checked,
                                                this.ckEstrazionePart.Checked, this.ckDefaultView.Checked, this.DefaultWorkSpace);

                PublishTable_OutDto outPublish = service.publishTable(inPublish);
                if (outPublish == null || !outPublish.IsSuccess)
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.ERRORE, outPublish.Message);
                    return;
                }

                //AddLayerToGroup_InDto inAddLayerToGroup = WrapperTOA.assembler(this.WrapperUrl, this.DefaultWorkSpace, nomeNodo, nomeLayer, "");
                //AddLayerToGroup_OutDto outAddLayerToGroup = service.addLayerToGroup(inAddLayerToGroup);
                //if (outAddLayerToGroup == null || !outAddLayerToGroup.IsSuccess)
                //{
                //    this.Cursor = Cursors.Default;
                //    util.message(TipoMessaggio.ERRORE, outAddLayerToGroup.Message);
                //    return;
                //}


            }
            else
            {
                if (!service.UploadShapeFile(dto))
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.ERRORE, "Errore durante importazione file");
                    return;
                }

                // AGGIORNO LE FEATURE NEL VECCHIO MODO
                GetFeatureTypeDto inGetFeature = new GetFeatureTypeDto();
                inGetFeature.link = String.Concat(this.Server, Costanti.REST_SERVICE_PREFIX, this.DefaultWorkSpace,
                    "/datastores/", dto.layerName, "/featuretypes/", dto.layerName, ".json");

                //VECCHIA CHIAMATA ALLE FEATURE


                //WrapperTOA.assemblaGetFeature(this.WrapperUrl, this.DefaultWorkSpace, dto.layerName, false);
                FeaturesTypeDto outGetFeature = service.getFeatureTypeByNomeLayer(inGetFeature);

                if (outGetFeature == null || outGetFeature.featureType == null)
                {
                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.ERRORE, "Errore nel recupero della Feature!");
                    return;
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
                outGetFeature.featureType.title = titoloLayer;
                outGetFeature.featureType.astratto = titoloLayer;


                outGetFeature.featureType.srs = "EPSG:4326";
                outGetFeature.featureType.projectionPolicy = "REPROJECT_TO_DECLARED";
               
                EditLayerDto editDTO = new EditLayerDto();
                editDTO.baseUrl = this.Server;
                editDTO.password = this.Password;
                editDTO.username = this.UserName;
                editDTO.workspace = this.DefaultWorkSpace;
                editDTO.layerDto = outGetFeature;
                editDTO.nomeLayer = nomeLayer;



                if (!service.UpdateLayer(editDTO))
                {
                    this.Cursor = Cursors.Default;
                    return;
                }

            }

   
            outDettaglio = service.getDettaglioLayer(inputGet);
            if (!outDettaglio.IsSuccess)
            {
                util.message(TipoMessaggio.WARNING, outDettaglio.Message);
                return;
            }

            // aggiungo il layer creato al gruppo 
            nomeNodo = nomeNodo.Replace("urbamid:", "");
            GetGroupLayerDto input = new GetGroupLayerDto();
            input.baseUrl = this.Server;
            input.nomeNodo = nomeNodo;
            input.password = this.Password;
            input.username = this.UserName;
            input.workspace = this.DefaultWorkSpace;

            LayerDTO groupLayer = service.getLayergroupsByName(input);

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

                Style stile = new Style();
                stile.href = outDettaglio.layer.defaultStyle.href;
                stile.name = outDettaglio.layer.defaultStyle.name;
                groupLayer.layerGroup.styles.style.Add(stile);
                groupLayer.baseUrl = this.Server;
                groupLayer.password = this.Password;
                groupLayer.username = this.UserName;
                groupLayer.workspace = this.DefaultWorkSpace;


                if (!service.addLayerToGroup(groupLayer))
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

            dto.layerName = outDettaglio.layer.name;
            messaggio = String.Format("Importazione avvenuta con successo. Si vuole importare anche il foglio di stile per il layer {0}?", dto.layerName);
            dialogResult = MessageBox.Show(messaggio, "ATTENZIONE", MessageBoxButtons.YesNo);
            if (dialogResult == DialogResult.Yes)
            {
                if (service.publishSLD(dto))
                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");

            }
            this.Cursor = Cursors.Default;
            reloadTreeView();
        }

        private void btnChiudiImporta_Click(object sender, EventArgs e)
        {
            rimuoviTabs();
            reloadTreeView();
        }

        private void cmbSelectLayer_SelectedIndexChanged(object sender, EventArgs e)
        {
            try
            {

                Utility util = new Utility();
                ComboBox comboBox = (ComboBox)sender;
                string nomeLayer = (string)cmbSelectLayer.SelectedItem;
                toolTip.SetToolTip(cmbSelectLayer, nomeLayer);
                this.ShapeFile = util.getShapeFileByLayerName(nomeLayer);
                txtNomeLayer.Text = nomeLayer; // Path.GetFileNameWithoutExtension(this.ShapeFile);
                lblPathShape.Text = ShapeFile;
                if (!this.ShapeFile.Contains("!"))
                    lblShapeFile.Text = Path.GetFileName(ShapeFile);
            }
            catch (Exception ex)
            {

                throw ex;
            }
        }

        private void tsmImporta_Click(object sender, EventArgs e)
        {
            Utility util = new Utility();
            rimuoviTabs();
            List<String> listaLayer = util.getListaLayer();
            if (listaLayer.Count > 0)
            {
                cmbSelectLayer.DataSource = listaLayer;
                toolTip.SetToolTip(cmbSelectLayer, listaLayer[0]);
                this.tabDettaglio.TabPages.Add(this.tabImporta);
            }
            else
            {
                util.message(TipoMessaggio.WARNING, "Attenzione, nessun layer da importare trovato");
                return;
            }
        }

        private void eliminaLayer()
        {
            RestApiGeoServer rest = new RestApiGeoServer();
            Utility util = new Utility();
            String nomeNodo = menuTreeView.SelectedNode.Name;
            // GetListaLayerInDto input = TOA.assemblaGetListaLayerInDto(this.Server, this.Password, this.UserName, this.DefaultWorkSpace);
            BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);

            if (String.IsNullOrEmpty(nomeNodo))
            {
                util.message(TipoMessaggio.ERRORE, "Nessun layer selezionato!");
                return;
            }
            else
                nomeNodo = nomeNodo.Replace("urbamid:", "");

            if (MessageBox.Show("ATTEZIONE, Si sta procedendo alla cancellazione di " + nomeNodo, "Attenzione", MessageBoxButtons.YesNo) ==
                DialogResult.Yes)
            {
                DeleteDatastoreDto inDeleteDataStore = TOA.assemblaDeleteDataStore(dtoBase, nomeNodo);

                if (isLayerGroup(nomeNodo))
                {
                    if (nomeNodo.EndsWith(Costanti.AREA_TEMATICA_SUFFIX))
                    {
                        if (rest.deleteLayerGroup(inDeleteDataStore))
                        {
                            util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                            nodeOpened = null;
                            reloadTreeView();
                        }
                    }
                    else
                    {
                        //recupero tutti i layer groups
                        GetGroupLayerDto inDto = TOA.assemblaGetDettaglioGruppo(dtoBase, nomeNodo);
                        LayerGroupDto listaLayers = rest.getLayergroups(inDto);
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

                            LayerDTO dettgruppo = rest.getLayergroupsByName(inDto);
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

                                    rest.UpdateLayerGroup(layerGroup);
                                }
                            }

                        }

                        if (rest.deleteLayerGroup(inDeleteDataStore))
                        {
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
                        DeleteLayer_OutDto outDelete = rest.deleteLayer(inDelete);
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
                        LayerDetailDto outDetail = rest.getDettaglioLayer(inDettaglio);

                        if (outDetail.layer.type == Costanti.TIPO_LAYER_RASTER)
                            esito = rest.deleteRaster(inDeleteDataStore);
                        else

                            esito = rest.deleteDataStore(inDeleteDataStore);

                    }
                    if (esito)
                    {
                        util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                        reloadTreeView();
                    }
                }
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

                String nomeLayer = this.menuTreeView.SelectedNode.Name;
               if (string.IsNullOrEmpty(nomeLayer))
               {
                        util.message(TipoMessaggio.ERRORE, "Selezionare un layer");
                        return;
               }

                if (String.IsNullOrEmpty(txtEditTitoloLayer.Text))
                {
                    util.message(TipoMessaggio.WARNING, "Titolo Layer obbligatorio!");
                    return;
                }

                nomeLayer = nomeLayer.Replace(this.DefaultWorkSpace + ":", "");

                GetDettaglioLayer_InDto inputGet = WrapperTOA.assembler(this.WrapperUrl, nomeLayer, this.DefaultWorkSpace, false);
                GetDettaglioLayer_OutDto outDettaglio = service.getDettaglioLayer(inputGet);
                if (outDettaglio.layer.type.Equals(Costanti.TIPO_RASTER))
                {
                    util.message(TipoMessaggio.INFO, "Funzionalità non disponibile per i raster");
                    return;
                }


                
                GetFeatureByLayer_OutDto outGetFeature = null;
                FeaturesTypeDto featureDto = null;
                
                if (nomeLayer.ToUpper().StartsWith("U_GEO"))
                {
                    GetFeatureByLayer_InDto inGetFeature = WrapperTOA.assemblaGetFeature(this.WrapperUrl, this.DefaultWorkSpace, nomeLayer, false);
                    outGetFeature = service.getFeatureByLayer(inGetFeature);
                    if (outGetFeature == null || !outGetFeature.IsSuccess)
                    {
                        util.message(TipoMessaggio.ERRORE, outGetFeature.Message);
                        return;
                    }
                }
                else
                {

                    GetFeatureTypeDto input = new GetFeatureTypeDto();
                    input.link = outDettaglio.layer.resource.href;
                    input.baseUrl = this.Server;
                    input.password = this.Password;
                    input.username = this.UserName;
                    input.workspace = this.DefaultWorkSpace;

                    featureDto = service.getFeatureTypeByNomeLayer(input);
                }


                FeatureType featureType = new FeatureType();
                featureType = outGetFeature != null ? outGetFeature.feature : featureDto.featureType;


                if (featureType != null)
                {
                    List<string> attributtiFissi = featureType.keywords.chiave.ToList();
                    attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.VIEW_FIELD));
                    attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.SEARCH_FIELD));
                    attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.NASCONDI_FIELD));
                    attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.ALL_FIELD));

                    attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.ESTRAZIONE_PARTICELLARE_CONST));
                    attributtiFissi.RemoveAll(p => p.StartsWith(Costanti.DEFAULT_VIEW_CONST));

                    //modifica 13/07/2020 La chiocciola viene usata negli attributi

                    //List<string> attributiNorm = new List<string>();
                    //foreach (string attributo in attributtiFissi)
                    //{
                    //    attributiNorm.Add(attributo.Replace("@", "="));
                    //}

                    //featureType.keywords.chiave = attributiNorm;
                    featureType.keywords.chiave = attributtiFissi;
                    //fine modifica 13/07/2020


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
                                {
                                    keywords = Costanti.VIEW_FIELD;
                                }
                                else if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(2))
                                {
                                    keywords = Costanti.SEARCH_FIELD;
                                }
                                else if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(3))
                                {
                                    keywords = Costanti.ALL_FIELD;
                                }
                                else if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(4))
                                {
                                    keywords = Costanti.NASCONDI_FIELD;
                                }
                                //else
                                //{
                                //    keywords = "viewField_";
                                //    featureType.keywords.chiave.Add("searchField_" + this.dgvLayerDetail.Rows[i].Cells[1].Value + "=" + this.dgvLayerDetail.Rows[i].Cells[2].Value + ";");
                                //}
                            }

                            keywords += this.dgvLayerDetail.Rows[i].Cells[1].Value + "@" + this.dgvLayerDetail.Rows[i].Cells[2].Value + ";";

                            featureType.keywords.chiave.Add(keywords);
                        }
                    }


                    featureType.keywords.chiave.Add(Costanti.ESTRAZIONE_PARTICELLARE_CONST + "@" + ckEstrazionePart_Mod.Checked.ToString().ToLower());
                    featureType.keywords.chiave.Add(Costanti.DEFAULT_VIEW_CONST + "@" + ckDefaultView_Mod.Checked.ToString().ToLower());


                    String keys = String.Empty;
                    foreach (var chiave in featureType.keywords.chiave)
                    {
                        if (!chiave.EndsWith(";"))
                            keys += chiave + ";";
                        else
                            keys += chiave;
                    }
                  keys =  keys.TrimEnd(';');

                    if (nomeLayer.ToUpper().StartsWith("U_GEO"))
                    {

                        //DEVO INVOCARE IL SERVIZIO DEL WRAPPER EDIT
                        EditFeature_InDto inEditFeature = WrapperTOA.assemblerEditFeature(this.WrapperUrl, outGetFeature.feature.name, keys, this.DefaultWorkSpace, featureType.title);

                        EditFeature_OutDto outEditFeature = service.editFeatureType(inEditFeature);
                        if (outEditFeature == null || !outEditFeature.IsSuccess)
                        {
                            this.Cursor = Cursors.Default;
                            util.message(TipoMessaggio.ERRORE, outEditFeature.Message);
                            return;
                        }

                    }
                    else
                    {
                        featureDto.featureType.keywords.chiave = keys.Split(';').ToList();
                        BaseDto dtoBase = TOA.assemblaBase(this.Server, this.UserName, this.Password, this.DefaultWorkSpace);
                        GeoServerDto inDettDTO = TOA.assemblaGeoServerDTO(dtoBase, String.Empty);
                        inDettDTO.storeName = nomeLayer;
                        LayerDetailDto outDettDTO = service.getDettaglioLayer(inDettDTO);

                        EditLayerDto inEditDto = TOA.assemblaEditLayer(dtoBase);
                        inEditDto.layerDto = featureDto;


                        if (outDettDTO != null && outDettDTO.layer != null && outDettDTO.layer.resource != null &&
                            !String.IsNullOrEmpty(outDettDTO.layer.resource.href))
                            inEditDto.linkFeature = outDettDTO.layer.resource.href;
                        else
                            inEditDto.nomeLayer = nomeLayer;


                        if (!service.UpdateLayer(inEditDto))
                            return;

                    }

                    this.Cursor = Cursors.Default;
                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo!");
                    reloadTreeView();
                }
                else
                {
                    util.message(TipoMessaggio.ERRORE, "Errore nella modifica del layer - Features non trovate");
                    return;
                }

            }
            catch (Exception ex)
            {
                util.message(TipoMessaggio.ERRORE, "Errore nella modifica del layer");
                util.scriviLog(ex.StackTrace);
                return;
            }

        }

        private void visualizzaTabModifica()
        {
            Utility util = new Utility();
            RestApiGeoServer service = new RestApiGeoServer();
            String nomeLayer = this.menuTreeView.SelectedNode.Name;
            if (string.IsNullOrEmpty(nomeLayer))
            {
                util.message(TipoMessaggio.ERRORE, "Selezionare un layer");
                return;
            }

            try
            {

                nomeLayer = nomeLayer.Replace("urbamid:", "");
                GetDettaglioLayer_InDto inputGet = WrapperTOA.assembler(this.WrapperUrl, nomeLayer, this.DefaultWorkSpace, false);
                GetDettaglioLayer_OutDto outDettaglio = service.getDettaglioLayer(inputGet);
                if (outDettaglio.layer.type.Equals(Costanti.TIPO_RASTER))
                {
                    util.message(TipoMessaggio.INFO, "Funzionalità non disponibile per i raster");
                    return;
                }

                rimuoviTabs();
                this.tabDettaglio.TabPages.Add(this.tabModifica);
                this.dgvLayerDetail.Visible = true;
                this.dgvLayerDetail.Columns.Clear();
                GetFeatureByLayer_OutDto outGetFeature = null;
                FeaturesTypeDto featureDto = null;

                if (nomeLayer.ToUpper().StartsWith("U_GEO"))
                {
                    GetFeatureByLayer_InDto inGetFeature = WrapperTOA.assemblaGetFeature(this.WrapperUrl, this.DefaultWorkSpace, nomeLayer, false);
                    outGetFeature = service.getFeatureByLayer(inGetFeature);
                    if (outGetFeature == null || !outGetFeature.IsSuccess)
                    {
                        util.message(TipoMessaggio.ERRORE, outGetFeature.Message);
                        return;
                    }
                }
                else
                {
                    
                    GetFeatureTypeDto input = new GetFeatureTypeDto();
                    input.link = outDettaglio.layer.resource.href;
                    input.baseUrl = this.Server;
                    input.password = this.Password;
                    input.username = this.UserName;
                    input.workspace = this.DefaultWorkSpace;

                    featureDto = service.getFeatureTypeByNomeLayer(input);
                }

                

                FeatureType featureType = new FeatureType();
                featureType = outGetFeature != null ? outGetFeature.feature : featureDto.featureType;


                this.linkDettaglioFeatureType = nomeLayer;
                string[] stringSeparators = new string[] { "@" };
                
                DataTable dt = new DataTable();

                dt.Columns.Add("Chiave", typeof(string));
                dt.Columns.Add("Valore", typeof(string));


                List<String> listaTipi = new List<String>();
                if (featureType != null)
                {
                    List<string> lsKeys = new List<string>();

                    foreach (string chiave in featureType.keywords.chiave)
                    {
                        String nuovaChiave = chiave;
                        nuovaChiave = nuovaChiave.Replace("\\", "");
                        nuovaChiave = nuovaChiave.Replace("@vocabulary=", "@");
                        lsKeys.Add(nuovaChiave);
                    }

                    txtEditTitoloLayer.Text = featureType.title;

                   
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
            catch (Exception ex)
            {
                util.message(TipoMessaggio.ERRORE, "Si è verificato un errore!");
                util.scriviLog(ex.StackTrace);
                return;

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
            this.lbLayersSelezionati.DataSource = bsSelezionati;
            lbLayersSelezionati.DisplayMember = "titolo";
            lbLayersSelezionati.ValueMember = "nome";
            bsSelezionati.ResetBindings(false);
         

        }


        private void btnCreaLayerGroup_Click(object sender, EventArgs e)
        {
            //controlli
            Utility util = new Utility();
            RestApiGeoServer rest = new RestApiGeoServer();
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
            {
                nomeNodo = nomeNodo.Replace("urbamid:", "");
            
            }

            if (lbLayersSelezionati.Items.Count == 0)
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
                lbLayersSelezionati.DataSource = bsSelezionati;
                lbLayersSelezionati.DisplayMember = "titolo";
                lbLayersSelezionati.ValueMember = "nome";
              
            }


            try
            {
                this.Cursor = Cursors.WaitCursor;
                if (isEditLayerGroup)//  if (isLayerGroup(nomeNodo))
                    editLayerGroup(titoloLayer, nomeLayer, nomeNodo);
                else
                    addLayerGroup(titoloLayer, nomeLayer, nomeNodo);

                this.Cursor = Cursors.Default;
            }
            catch (Exception ex)
            {
                this.Cursor = Cursors.Default;
                util.scriviLog(String.Format("Errore Crea Layer Group - {0}, stack: {1}", ex.Message, ex.StackTrace));
                util.message(TipoMessaggio.ERRORE, "Errore Crea Layer Group: " + ex.Message);
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

            GetListaLayerInDto input = new GetListaLayerInDto(); //aggiunto ora per compilare
            RestApiGeoServer service = new RestApiGeoServer();
            Utility util = new Utility();
            try
            {
                this.Cursor = Cursors.WaitCursor;
                
                List<LayerGroup> lsGruppi = new List<LayerGroup>();
                //layersDisponibili = new List<GenericLayer>();
                //GetListaLayerGroups_InDto inGetGruppi = WrapperTOA.assemblerGetGruppi(this.WrapperUrl, this.DefaultWorkSpace);
                //GetListaLayerGroups_OutDto outGetGruppi = service.getListaLayerGroups(inGetGruppi);
                //if (outGetGruppi == null || !outGetGruppi.IsSuccess)
                //{
                //    util.message(TipoMessaggio.ERRORE, outGetGruppi.Message);
                //    return;
                //}
                //lsGruppi.AddRange(outGetGruppi.gruppi.layerGroup);
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
                    //GetDettaglioGruppo_InDto inGruppo = WrapperTOA.assemblerDettaglioGruppo(this.WrapperUrl, this.DefaultWorkSpace, gruppo.name);
                    //GetDettaglioGruppo_OutDto outGruppo = service.getDettaglioGruppo(inGruppo);
                    //if (outGruppo == null || !outGruppo.IsSuccess)
                    //{
                    //    util.message(TipoMessaggio.ERRORE, outGruppo.Message);
                    //    return;
                    //}

                    //layersDisponibili.Add(WrapperTOA.assembler(outGruppo.Gruppo));

                    GetGroupLayerDto inDettGruppo = TOA.assemblaGetDettaglioGruppo(input);
                    inDettGruppo.link = gruppo.href;
                    LayerDTO outGruppo = service.getLayergroupsByName(inDettGruppo);
                    GenericLayer genDTO = TOA.assemblaGenericLayer(outGruppo.layerGroup);
                    layersDisponibili.Add(genDTO);
                }


                if (nomeLayer.EndsWith(Costanti.TEMATISMO_SUFFIX) || nomeLayer.EndsWith(Costanti.LAYER_GROUP_SUFFIX))
                {
                    //GetListaLayers_InDto inListaLayer = WrapperTOA.assemblerGetLista(this.WrapperUrl, this.DefaultWorkSpace);
                    //GetListaLayers_OutDto outListaLayer = service.getListaLayers(inListaLayer);
                    //if (outListaLayer == null || !outListaLayer.IsSuccess)
                    //{
                    //    util.message(TipoMessaggio.ERRORE, outListaLayer.Message);
                    //    return;
                    //}

                    //List<Layer> listaLayers = outListaLayer.layers.ToList();
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
                    lbLayersSelezionati.DataSource = bsSelezionati;
                    lbLayersSelezionati.DisplayMember = "titolo";
                    lbLayersSelezionati.ValueMember = "nome";
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

        }

        
        private void visualizzaTabCreaLayerGroup(String gruppo)
        {

            this.Cursor = Cursors.WaitCursor;
            isEditLayerGroup = false;
            rimuoviTabs();
            txtNameLayerGroup.Enabled = true;
            txtTitleLayerGroup.Enabled = true;
            txtNameLayerGroup.Text = "";
            txtTitleLayerGroup.Text = "";
            this.tabDettaglio.TabPages.Add(this.tabCreaGruppo);
            Utility util = new Utility();

            RestApiGeoServer rest = new RestApiGeoServer();
           // clbLayerDisponibili.Items.Clear();
            try
            {
                layersDisponibili = new List<GenericLayer>();
                bsSelezionati.DataSource = null;
                listaSelezionati = new List<GenericLayer>();
                List<LayerGroup> lista = new List<LayerGroup>();
                BaseDto dtoBase = TOA.assemblaBase(this.Server, this.Password, this.UserName, this.DefaultWorkSpace);
                GetListaLayerInDto input = TOA.assemblaGetListaLayerInDto(dtoBase);
                if (gruppo == "AreaTematica" || gruppo == "Tematismo")
                {
                    
                    lista = rest.getListaLayerGroupInWorkspace(input);
                    lista.RemoveAll(p => p.name.ToUpper().StartsWith(Costanti.DEFAULT_LAYER));
                    if (gruppo == "AreaTematica")
                        lista = lista.FindAll(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX));
                    else if (gruppo == "Tematismo")
                    {
                        lista.RemoveAll(p => p.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX));
                        lista.RemoveAll(p => p.name.EndsWith(Costanti.TEMATISMO_SUFFIX));
                    }


                }
      

                foreach (LayerGroup item in lista)
                {
                    GetGroupLayerDto grupDTO = TOA.assemblaGetDettaglioGruppo(dtoBase, item);
                   

                    LayerDTO dettaglio = rest.getLayergroupsByName(grupDTO);

                    GenericLayer layer = TOA.assemblaGenericLayer(item);
                    layer.titolo = dettaglio.layerGroup.title;
                    layersDisponibili.Add(layer);
                }




                if (gruppo == "Tematismo" || gruppo == "LayerGroup") // devo aggiungere anche i layers
                {
                    List<Layer> listaLayers = rest.getListaLayerInWorkspace(input); //recupero tutti i layers presenti nel WORKSPACE
                    foreach (Layer item in listaLayers)
                    {
                        GeoServerDto inDTO = TOA.assemblaGeoServerDTO(dtoBase, item.link);
                        LayerDetailDto dettaglio = rest.getDettaglioLayer(inDTO);
                        String titolo = dettaglio.layer.name;

                        if (dettaglio.layer.type != Costanti.TIPO_LAYER_RASTER)
                        {
                            GetFeatureTypeDto inFeature = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                            FeaturesTypeDto outFeature = rest.getFeatureTypeByNomeLayer(inFeature);
                            if(outFeature != null && outFeature.featureType != null && !String.IsNullOrEmpty(outFeature.featureType.title))
                                titolo = outFeature.featureType.title;
    
                            if (dettaglio.layer.name.StartsWith(Costanti.LAYERDB_PREFIX))
                                titolo += " - DB";

                        }
                        else
                        {
                            GetFeatureTypeDto inFeatureDto = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                            CoverageDto outDto = rest.getFeatureTypeRasterByNomeLayer(inFeatureDto);
                            titolo = outDto.coverage.title;
                        }
                        GenericLayer genDTO = TOA.assemblaGenericLayer(dettaglio.layer);
                        genDTO.titolo = titolo;

                        layersDisponibili.Add(genDTO);

                    }

                }

                layersDisponibili.RemoveAll(p => p.nome.StartsWith(Costanti.DEFAULT_LAYER));
                layersDisponibili.OrderBy(p => p.titolo);
                clbLayerDisponibili.DataSource = layersDisponibili;
                clbLayerDisponibili.DisplayMember = "titolo";
                clbLayerDisponibili.ValueMember = "nome";
            }
            catch (Exception ex)
            {
                util.scriviLog(String.Format("Errore TabCreaLayerGroup: {0} --> {1}", ex.Message, ex.StackTrace));
                util.message(TipoMessaggio.ERRORE, "Errore nella visualizzazione del dettaglio");
            }
            finally{
                this.Cursor = Cursors.Default;
            }
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

                foreach (GenericLayer item in lbLayersSelezionati.Items)
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
                        LayerDetailDto output = rest.getDettaglioLayer(dto);
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


                if (rest.createLayerGroup(layerGroup))
                {
                    if (!layerGroup.name.EndsWith(Costanti.AREA_TEMATICA_SUFFIX))
                    {
                        if (String.IsNullOrEmpty(nomeNodo))
                            nomeNodo = layerGroup.name;

                        GetGroupLayerDto inGruppo = TOA.assemblaGetDettaglioGruppo(input);
                        inGruppo.nomeNodo = nomeNodo;

                        LayerDTO groupLayer = rest.getLayergroupsByName(inGruppo);

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

                            if (!rest.addLayerToGroup(groupLayer))
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
            catch (Exception ex)
            {

                throw ex;
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

                LayerDTO dettaglioLayer = rest.getLayergroupsByName(inDto);

                CreateLayerGroupInDto layerGroup = new CreateLayerGroupInDto();
                layerGroup = TOA.assemblaLayerGroup(inLista, dettaglioLayer.layerGroup);
                Style stile = new Style();
                Published pub = new Published();
                List<GenericLayer> listaLayers = (List<GenericLayer>)bsDisponibili.DataSource;
                foreach (GenericLayer item in lbLayersSelezionati.Items)
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

                        LayerDetailDto dettaglio = rest.getDettaglioLayer(input);
                        stile.href = dettaglio.layer.defaultStyle.href;
                        stile.name = dettaglio.layer.defaultStyle.name;
                        layerGroup.styles.style.Add(stile);
                    }

                }

                layerGroup.title = titoloLayer.Trim();

                if (rest.UpdateLayerGroup(layerGroup))
                {
                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo!");
                    reloadTreeView();
                }

            }
            catch (Exception ex)
            {

                throw ex;
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
            txtNomeLayer.Text = "";
            lblPathShape.Text = "";
            lblShapeFile.Text = "";
            cmbSelectLayer.DataSource = null;
            cmbSelectLayer.Items.Clear();

            txtNameLayerGroup.Text = "";
            txtTitleLayerGroup.Text = "";
            txtFindLayers.Text = "";
            clbLayerDisponibili.DataSource = null;

            lbLayersSelezionati.DataSource = null;



            this.tabDettaglio.TabPages.Remove(this.tabImporta);
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
                lbLayersSelezionati.Items.Clear();
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
                LayerDTO dettaglioGroup = proxy.getLayergroupsByName(inDto);

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
                                LayerDTO gruppo = proxy.getLayergroupsByName(inDto);
                                nodeDetails.Name = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                nodeDetails.Text = gruppo.layerGroup.title;
                                nodeDetails.Nodes.Add(Costanti.VOID_MENU).Name = Costanti.VOID_MENU;
                                nodeOpened.Nodes.Add(nodeDetails);
                            }
                            else
                            {
                                GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(dtoBase, item.href);

                                LayerDetailDto dettaglio = proxy.getDettaglioLayer(inDettaglio);

                                GetFeatureTypeDto input = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                                if (dettaglio.layer.type != Costanti.TIPO_LAYER_RASTER)
                                {
                                    FeaturesTypeDto outDto = proxy.getFeatureTypeByNomeLayer(input);
                                    nodeDetails.Text = outDto.featureType.title;
                                }
                                else
                                {
                                    CoverageDto outDto = proxy.getFeatureTypeRasterByNomeLayer(input);
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
            catch (Exception ex)
            {
                util.scriviLog(ex.Message);
                util.message(TipoMessaggio.ERRORE, "Si è verificato un errore nella lettura delle configurazioni!");
                return false;
            }



        }

        #endregion


        #region Form Load
        private void frmManageGeoServer_Load(object sender, EventArgs e)
        {
            Utility util = new Utility();

            #region ToolTips
            toolTip.SetToolTip(btnConfirmEditLayer, "Conferma");
            toolTip.SetToolTip(btnImportaShape, "Conferma");
            toolTip.SetToolTip(btnCreaLayerGroup, "Conferma");

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
            LayerDetailDto dettaglio = rest.getDettaglioLayer(input);
            if (dettaglio.layer.type.Equals("RASTER"))
            {
                util.message(TipoMessaggio.INFO, "Funzionalità non disponibile per i Raster");
                return;

            }

            String nomeLayer = util.getFileStyleName();
            if (String.IsNullOrEmpty(nomeLayer))
            {
                util.message(TipoMessaggio.ERRORE, "Nessun layer selezionato!");
                return;
            }
        
            String messaggio = String.Format("ATTENZIONE, Si sta procedendo alla pubblicazione dello stile {0}, per il layer {1}. Continuare?",
               nomeLayer, nomeNodo);

            if (MessageBox.Show(messaggio, "Attenzione", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {  
               

                if (rest.publishSLD(input))
                {
                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                    reloadTreeView();
                }

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
            lista.Items.AddRange(this.lbLayersSelezionati.Items);

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

        private void lbLayersSelezionati_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            int index = this.lbLayersSelezionati.IndexFromPoint(e.Location);
            GenericLayer item = (GenericLayer)this.lbLayersSelezionati.Items[index];



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
            this.lbLayersSelezionati.SelectedIndex = -1;
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

        private bool importaFileRaster(String shapeFile, BaseDto dtoBase)
        {
            bool esito = false;
            String messaggio = String.Empty;
            RestApiGeoServer proxy = new RestApiGeoServer();
            DialogResult dialogResult;
            Utility util = new Utility();
            String nomeLayer = Path.GetFileNameWithoutExtension(shapeFile);
            String nomeFile = String.Empty;
            try
            {
                if (shapeFile.ToUpper().EndsWith(".TIF") || shapeFile.ToUpper().EndsWith(".TIFF"))
                {
                    byte[] arrByte1 = null;

                    nomeFile = Path.GetFileName(shapeFile);

                    using (FileStream fs = new FileStream(shapeFile, FileMode.Open, FileAccess.Read))
                    {
                        byte[] bytes = System.IO.File.ReadAllBytes(shapeFile);
                        fs.Read(bytes, 0, System.Convert.ToInt32(fs.Length));
                        fs.Close();
                        arrByte1 = bytes;

                    }
                    

                    UploadRasterDto inDtoUploadRaster1 = TOA.assmblaUploadRaster(dtoBase, arrByte1, nomeFile);

                    inDtoUploadRaster1.extension = ".worldimage";
          
                    if (!proxy.UploadRaster(inDtoUploadRaster1))
                    {
                        this.Cursor = Cursors.Default;

                        util.message(TipoMessaggio.ERRORE, String.Format("Si è verificato un errore nel caricamento nel caricamento del raster {0}!", nomeFile));
                        return esito;
                    }


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
                        if (!proxy.UploadRaster(inDtoUploadRaster))
                        {
                            this.Cursor = Cursors.Default;

                            util.message(TipoMessaggio.ERRORE, String.Format("Si è verificato un errore nel caricamento nel caricamento del raster {0}!", nomeFile));
                            return esito;
                        }

                    }
                }

                GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(dtoBase, String.Empty);
                inDettaglio.storeName = System.IO.Path.GetFileNameWithoutExtension(nomeFile);
                LayerDetailDto dettaglio = proxy.getDettaglioLayer(inDettaglio);
                GetFeatureTypeDto inFeatureDto = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                CoverageDto outDto = proxy.getFeatureTypeRasterByNomeLayer(inFeatureDto);
                //if (!proxy.EditSrsRaster(outDto, dtoBase))
                //{

                //    util.message(TipoMessaggio.ERRORE, String.Format("Si è verificato un errore nel caricamento nella riproiezione del raster {0}!", nomeFile));
                //    this.Cursor = Cursors.Default;
                //    return esito;
                //}


                // aggiungo il layer creato al gruppo 
                String nomeNodo = menuTreeView.SelectedNode.Name;
                nomeNodo = nomeNodo.Replace(dtoBase.workspace + ":", "");

                GetGroupLayerDto input = TOA.assemblaGroupLayer(dtoBase, nomeNodo);

                LayerDTO groupLayer = proxy.getLayergroupsByName(input);

                if (groupLayer == null)
                    return esito;

                while (groupLayer.layerGroup.publishables.published.Count != groupLayer.layerGroup.styles.style.Count)
                {
                    if (groupLayer.layerGroup.publishables.published.Count > groupLayer.layerGroup.styles.style.Count)
                    {
                        Style style = new Style();
                        style.href = dettaglio.layer.defaultStyle.href;
                        style.name = dettaglio.layer.defaultStyle.name;
                        groupLayer.layerGroup.styles.style.Add(style);
                    }
                    else
                    {
                        Published pubbl = new Published();
                        pubbl.name = nomeLayer;
                        pubbl.tipoPublished = "layer";
                        pubbl.href = dtoBase.baseUrl + "/rest/workspaces/" + dtoBase.workspace + "/layers/" + inDettaglio.storeName + ".json";
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


                if (!proxy.addLayerToGroup(groupLayer))
                    return esito;

                return true;

            }
            catch (Exception ex)
            {

                util.scriviLog("importaFileRaster: " + ex.StackTrace);
                util.message(TipoMessaggio.ERRORE, "Errore nella trasformazione del raster");
                return esito;

            }
        }

     
       
    }
}