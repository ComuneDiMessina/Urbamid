using System.Runtime.InteropServices;
using ESRI.ArcGIS.ADF.CATIDs;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using UrbamidAddIn.Dto;
using System.IO.Compression;
using System.Xml;
using RestSharp;
using RestSharp.Authenticators;
using System.Web.Script.Serialization;
using Newtonsoft.Json;
using System.Web;
using ESRI.ArcGIS.Geodatabase;
using ESRI.ArcGIS;
using ESRI.ArcGIS.Geometry;
using ESRI.ArcGIS.esriSystem;
using ESRI.ArcGIS.DataSourcesGDB;
using ESRI.ArcGIS.Carto;
using System.Net;

namespace UrbamidAddIn
{
    public partial class Form1 : Form
    {
        #region COM Registration Function(s)
        [ComRegisterFunction()]
        [ComVisible(false)]
        static void RegisterFunction(Type registerType)
        {
            // Required for ArcGIS Component Category Registrar support
            ArcGISCategoryRegistration(registerType);

            //
            // TODO: Add any COM registration code here
            //
        }

        [ComUnregisterFunction()]
        [ComVisible(false)]
        static void UnregisterFunction(Type registerType)
        {
            // Required for ArcGIS Component Category Registrar support
            ArcGISCategoryUnregistration(registerType);

            //
            // TODO: Add any COM unregistration code here
            //
        }

        #region ArcGIS Component Category Registrar generated code
        /// <summary>
        /// Required method for ArcGIS Component Category registration -
        /// Do not modify the contents of this method with the code editor.
        /// </summary>
        private static void ArcGISCategoryRegistration(Type registerType)
        {
            string regKey = string.Format("HKEY_CLASSES_ROOT\\CLSID\\{{{0}}}", registerType.GUID);
            GxCommands.Register(regKey);
            GxCommandBars.Register(regKey);
            GxDockableWindows.Register(regKey);
            GxExtensions.Register(regKey);
            GxExtensionsJIT.Register(regKey);
            GxViews.Register(regKey);
            MxCommands.Register(regKey);
            MxCommandBars.Register(regKey);
            ContentsViews.Register(regKey);
            MxDockableWindows.Register(regKey);
            MxExtension.Register(regKey);
            MxExtensionJIT.Register(regKey);
            LayerPropertyPages.Register(regKey);
            GeoObjects.Register(regKey);

        }
        /// <summary>
        /// Required method for ArcGIS Component Category unregistration -
        /// Do not modify the contents of this method with the code editor.
        /// </summary>
        private static void ArcGISCategoryUnregistration(Type registerType)
        {
            string regKey = string.Format("HKEY_CLASSES_ROOT\\CLSID\\{{{0}}}", registerType.GUID);
            GxCommands.Unregister(regKey);
            GxCommandBars.Unregister(regKey);
            GxDockableWindows.Unregister(regKey);
            GxExtensions.Unregister(regKey);
            GxExtensionsJIT.Unregister(regKey);
            GxViews.Unregister(regKey);
            MxCommands.Unregister(regKey);
            MxCommandBars.Unregister(regKey);
            ContentsViews.Unregister(regKey);
            MxDockableWindows.Unregister(regKey);
            MxExtension.Unregister(regKey);
            MxExtensionJIT.Unregister(regKey);
            LayerPropertyPages.Unregister(regKey);
            GeoObjects.Unregister(regKey);

        }

        #endregion
        #endregion

        //ESRI.ArcGIS.LicenseLevel l = LicenseLevel.Standard;
        //ESRI.ArcGIS.ProductCode p = ProductCode.Desktop;
        public Form1()
        {
            InitializeComponent();
        }


        private void button2_Click(object sender, EventArgs e)
        {
            GeoServerDto dto = new GeoServerDto();
            dto.password = "geoserver";
            //C:\Users\Vignola\Desktop\URBAMID\test\APF_2018
            //C:\Users\Vignola\Desktop\URBAMID\test\APF_2016\
            dto.pathFile = @"C:\Users\Vignola\Desktop\URBAMID\test\APF_2018\APF_2018.zip";
            dto.baseUrl = @"http://192.168.11.222:8080/geoserver/rest/workspaces/urbamid/datastores/APF_2019/file.shp?configure=all";
            dto.username = "admin";
            dto.workspace = "urbamid";
            RestApiGeoServer proxy = new RestApiGeoServer();

            try
            {
                proxy.UploadShapeFile(dto);

            }
            catch (Exception ex)
            {

                throw ex;
            }
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            GeoServerDto dto = new GeoServerDto();
            dto.password = "geoserver";
            dto.baseUrl = @"http://192.168.11.180:8080/geoserver/rest/workspaces/urbamid/datastores/APF_2018/featuretypes/APF_2018?recalculate=nativebbox,latlonbbox";
            dto.username = "admin";
            dto.workspace = "urbamid";
            RestApiGeoServer proxy = new RestApiGeoServer();

            try
            {
                if (proxy.EditSrs(dto))
                    Console.WriteLine("OK");
                else
                    Console.WriteLine("KO");
            }
            catch (Exception ex)
            {

                throw ex;
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            RestApiGeoServer rest = new RestApiGeoServer();
            Utility util = new Utility();


            ZipDto dto = new ZipDto();
            dto.nomeLayer = "B5_CORRIDOI_ECOLOGICI_INT";
            dto.nomeFile = System.IO.Path.GetFileNameWithoutExtension(@"E:\Repository - SHAPE\shp_pdg\B5_CORRIDOI ECOLOGICI INT.shp");
            dto.pathFolder = @"E:\Repository - SHAPE\shp_pdg";
            if (util.createZipFile(dto))
                MessageBox.Show("OK");
            else
                MessageBox.Show("KO");
        }


        private void backgroundWorker_DoWork(object sender, DoWorkEventArgs e)
        {
            var backgroundWorker = sender as BackgroundWorker;
            for (int j = 0; j < 100000; j++)
            {
                Calculate(j);
                backgroundWorker.ReportProgress((j * 100) / 100000);
            }
        }



        private void Calculate(int i)
        {
            double pow = Math.Pow(i, i);
        }

        private void backgroundWorker1_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {


        }

        private void backgroundWorker1_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            Console.WriteLine("LAVORO FInito");


        }

        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            //Upload file 
            //zip file from folder
            int i = 0;
            String folder = @"C:\Users\Vignola\Desktop\URBAMID\test\APF_2016";
            var zipFile = System.IO.Path.Combine(folder, "APF_2016.zip");

            DirectoryInfo d = new DirectoryInfo(folder);
            List<FileInfo> files = d.GetFiles().Where(p => p.Name.StartsWith("APF_2016")).ToList();


            using (var archive = ZipFile.Open(zipFile, ZipArchiveMode.Create))
            {

                foreach (FileInfo f in files)
                {
                    i++;
                    backgroundWorker1.ReportProgress((i * 100) / 100000);

                    archive.CreateEntryFromFile(f.FullName, f.Name);

                }
            }


        }

        private void button4_Click(object sender, EventArgs e)
        {
            frmManageGeoServer f = new frmManageGeoServer();

            f.Show();
        }

        private void button5_Click(object sender, EventArgs e)
        {
            String urlRequest = @"http://192.168.11.222:8080/geoserver/rest/workspaces/urbamid/layergroups/areePercorseDalFuoco_tematismo.json";

            XmlDocument doc = httpGeoserverClient(urlRequest);


        }


        private XmlDocument httpGeoserverClient(String url)
        {
            XmlDocument xmlDoc = new XmlDocument();
            Utility util = new UrbamidAddIn.Utility();
            var client = new RestClient(url);
            client.Authenticator = new HttpBasicAuthenticator("Admin", "Geoserver");
            var request = new RestRequest(Method.GET);
            try
            {
                IRestResponse response = client.Execute(request);

                if (response.StatusCode == System.Net.HttpStatusCode.OK)
                {

                    // LayerDTO json = new JavaScriptSerializer().Deserialize<LayerDTO>(response.Content);
                    var settings = new JsonSerializerSettings
                    {
                        NullValueHandling = NullValueHandling.Ignore,
                        MissingMemberHandling = MissingMemberHandling.Ignore
                    };

                    response.Content = response.Content.Replace("\"null\"", "");
                    var json = JsonConvert.DeserializeObject<LayerDTO>(response.Content, settings);
                    Published newLayer = new Published();
                    newLayer.href = @"http://192.1668.11.222:8080/geoserver/rest/workspaces/urbamid/layers/APF_2018.json";
                    newLayer.name = "APF_2018";
                    //  newLayer.tipo = "layer";


                    Style stile = new Style();
                    stile.name = "line";
                    stile.href = @"http://192.168.11.222:8080/geoserver/rest/styles/line.json";
                    json.layerGroup.styles.style.Add(stile);
                    json.layerGroup.publishables.published.Add(newLayer);
                    //json.layerGroup.publishables.published = newLayer;


                    RestApiGeoServer proxy = new RestApiGeoServer();

                    if (proxy.addLayerToGroup(json))
                    {

                    }

                }




            }
            catch (Exception ex)
            {
                util.scriviLog(ex.StackTrace);
            }

            return xmlDoc;
        }

        private void button7_Click(object sender, EventArgs e)
        {
            //ADD NEW LAYER GROUP
            Utility util = new Utility();
            RestApiGeoServer rest = new RestApiGeoServer();
            try
            {
                String esito = String.Empty;
                //CreateLayerGroupInDto dto = new CreateLayerGroupInDto();
                //dto.baseUrl = @"http://192.168.11.222:8080/geoserver";
                //dto.nome = "Test_Layer_Group_areaTematica";
                //dto.titolo = "Layer Group di Test";
                //dto.password = "Geoserver";
                //dto.username = "admin";
                //dto.workspace = "urbamid";
                //if (rest.createLayerGroup(dto))
                //{
                //    esito = "OK";
                //}
                //else
                //{

                //    esito = "KO";
                //}
            }
            catch (Exception ex)
            {

                util.scriviLog(ex.Message + " --> " + ex.StackTrace);
            }

        }

        private void button8_Click(object sender, EventArgs e)
        {
            RestApiGeoServer rest = new RestApiGeoServer();
            try
            {

                GetGroupLayerDto input = new Dto.GetGroupLayerDto();
                //  input.baseUrl = @"http://192.168.11.222:8080/geoserver";
                input.baseUrl = @"http://192.168.11.175:8080/geoserver";
                input.nomeNodo = "C1_Uso_Suolo_layergroup";// "areePercorseDalFuoco_areaTematica";
                input.password = "Geoserver";
                input.username = "admin";
                input.workspace = "urbamid";

                LayerDTO groupLayer = rest.getLayergroupsByName(input);
            }
            catch (Exception ex)
            {

                throw ex;
            }

        }

        private void button6_Click(object sender, EventArgs e)
        {
            RestApiGeoServer rest = new RestApiGeoServer();
            try
            {
                GeoServerDto input = new Dto.GeoServerDto();
                input.baseUrl = @"http://192.168.11.175:8080/geoserver";
                input.password = "geoserver";
                input.username = "admin";
                input.workspace = "urbamid";
                input.layerName = "pianoDiGestioneMontiPeloritani_tematismo";
                input.storeName = "pianoDiGestioneMontiPeloritani_tematismo";

                if (rest.publishSLD(input))
                    MessageBox.Show("OK");
                else
                    MessageBox.Show("FAIL!");
            }
            catch (Exception ex)
            {

                throw ex;
            }
        }

        private void button9_Click(object sender, EventArgs e)
        {
            frmManageGeoServer f = new frmManageGeoServer();

            f.Show();
        }

        private void btnUploadRASTER_Click(object sender, EventArgs e)
        {
            try
            {
                GeoServerDto dto = new GeoServerDto();
                dto.password = "geoserver";
                //C:\Users\Vignola\Desktop\URBAMID\test\APF_2018
                //C:\Users\Vignola\Desktop\URBAMID\test\APF_2016\
                dto.pathFile = @"C:\Users\Vignola\Desktop\URBAMID\RASTER\TEST\CTR10K_Raster.tif";
                //dto.baseUrl = @"http://192.168.11.222:9191/geoserver/rest/workspaces/urbamid/datastores/APF_2019/file.shp?configure=all&update=overwrite";
                dto.baseUrl = @"http://192.168.11.222:9191/geoserver";
                dto.username = "admin";
                dto.workspace = "urbamid";
                dto.storeName = "CTR10K_Raster";
                //RestApiGeoServer proxy = new RestApiGeoServer();
                //UploadLodto = new UploadLoadRasterDto();


                //proxy.UploadRaster()

               // proxy.UploadRaster(dto);

            }
            catch (Exception ex)
            {

                throw ex;
            }
        }


        IRasterWorkspaceEx OpenFileGDBWorkspace(string filePath)
        {
            IWorkspaceFactory wsFactory = new FileGDBWorkspaceFactoryClass();
            IRasterWorkspaceEx rasterWS = (IRasterWorkspaceEx)wsFactory.OpenFromFile
              (filePath, 0);
            return rasterWS;
        }

        //public IRasterWorkspace2 SetRasterWorkspace(string sPath)
        //{
        //    IWorkspaceFactory workspaceFactory = new RasterWorkspaceFactory();
        //    IWorkspace workspace = workspaceFactory.OpenFromFile(sPath, 0);
        //    IRasterWorkspace2 rasterWorkspace = (IRasterWorkspace2)workspace;

        //    return rasterWorkspace;
        //}

        private void btnReadGDB_Click(object sender, EventArgs e)
        {

            try
            {

                DialogResult dialogResult;

                //E' un raster
                String nomeFileF = @"C:\TEMP\CTR_10_2007_2008.gdb";
                String nomeLayer = "Edificio";
                IWorkspace workspace = OpenWorkspace(nomeFileF);
                IRasterWorkspaceEx rasterEX = (IRasterWorkspaceEx)workspace;
                IRasterCatalog rasterCatalog = null;
                IFeatureClass fClass = null;
                try
                {
                    rasterCatalog = rasterEX.OpenRasterCatalog(nomeLayer);
                    fClass = (IFeatureClass)rasterCatalog;
                }
                catch (Exception exxx)
                {

                    IFeatureWorkspace fw = (IFeatureWorkspace)workspace;
                    IFeatureClass featureClass2 = fw.OpenFeatureClass(nomeLayer);
                    IEnumDataset enumDataset = workspace.Datasets[esriDatasetType.esriDTFeatureDataset];
                    IDataset dataset;
                    while ((dataset = enumDataset.Next()) != null)
                    {
                        //Do something with the feature class here

                        Marshal.ReleaseComObject(enumDataset);
                    }

                    fClass = featureClass2;
                }


                
                IQueryFilter queryFilterlast = new QueryFilter();
                IFeatureCursor pFCursor;
                IFeature pF;
                RestApiGeoServer rest = new UrbamidAddIn.RestApiGeoServer();
                Utility util = new Utility();

                queryFilterlast.WhereClause = null;
                pFCursor = fClass.Search(queryFilterlast, false);
                pF = pFCursor.NextFeature();
                int index = 1;
                while (pF != null && pF.Shape != null && pF.Shape.IsEmpty == false)
                {
                    index++;
                    pF = pFCursor.NextFeature();
                }

                String messaggio = String.Format("Attenzione saranno importati {0} raster. L'operazione potrebbe richiedere diversi minuti. Si vuole continuare?", index - 1);
                dialogResult = MessageBox.Show(messaggio, "ATTENZIONE", MessageBoxButtons.YesNo);
                if (dialogResult == DialogResult.No)
                    return;
                this.Cursor = Cursors.WaitCursor;
                for (int i = 1; i < index; i++)
                {
                    int indexI = fClass.GetFeature(i).Table.FindField("NAME");
                    string nomeFile = fClass.GetFeature(i).Value[indexI].ToString();

                    IRasterCatalogItem catalogItem = (IRasterCatalogItem)fClass.GetFeature(i);
                    IRasterDataset rasterDataset = catalogItem.RasterDataset;
                    IRaster raster = rasterDataset.CreateDefaultRaster();


                    raster.ResampleMethod = rstResamplingTypes.RSP_NearestNeighbor;
                        IRasterExporter pConverter3 = new RasterExporter();
                    byte[] arrByte;
                    arrByte = pConverter3.ExportToBytes(raster, "TIFF"); //
                    UploadRasterDto inDtoUploadRaster = new UploadRasterDto();
                    inDtoUploadRaster.baseUrl = @"http://192.168.11.222:9191//geoserver";
                    inDtoUploadRaster.arrFile = arrByte;
                    inDtoUploadRaster.nome = nomeFile;
                    inDtoUploadRaster.password = "Geoserver";
                    inDtoUploadRaster.titolo = nomeFile;
                    inDtoUploadRaster.titolo = nomeFile;
                    inDtoUploadRaster.username = "admin";
                    inDtoUploadRaster.workspace = "urbamid";

                        
                    if (!rest.UploadRaster(inDtoUploadRaster))
                    {
                        this.Cursor = Cursors.Default;

                      //  util.message(TipoMessaggio.ERRORE, String.Format("Si è verificato un errore nel caricamento del raster{0}!", nomeFile));
                        return;
                    }


                    GeoServerDto dto = new GeoServerDto();
                    dto.baseUrl = inDtoUploadRaster.baseUrl;
                    dto.layerName = nomeFile;
                    dto.storeName = nomeLayer;
                    dto.password = inDtoUploadRaster.password;
                    dto.username = inDtoUploadRaster.username;
                    dto.workspace = inDtoUploadRaster.workspace;

                    BaseDto dtoBase = new BaseDto();
                    dtoBase.baseUrl = inDtoUploadRaster.baseUrl;
                    dtoBase.password = inDtoUploadRaster.password;
                    dtoBase.username = inDtoUploadRaster.username;
                    dtoBase.workspace = inDtoUploadRaster.workspace;
                   

                    GeoServerDto inDettaglio = TOA.assemblaGeoServerDTO(dtoBase, String.Empty);
                    inDettaglio.storeName = System.IO.Path.GetFileNameWithoutExtension( nomeFile);
                    LayerDetailDto dettaglio = rest.getDettaglioLayer(inDettaglio);
                    GetFeatureTypeDto input = TOA.assemblaFeatureTypeDto(dtoBase, dettaglio.layer.resource.href);
                    CoverageDto outDto = rest.getFeatureTypeRasterByNomeLayer(input);
                    if (!rest.EditSrsRaster(outDto, dtoBase))
                    {
                        this.Cursor = Cursors.Default;
                        return;
                    }
                }
                this.Cursor = Cursors.Default;

                //GeoServerDto dto = new GeoServerDto();
                //dto.password = "geoserver";
                //dto.pathFile = @"E:\RASTER\CTR_Messina_Raster.gdb";
                //dto.baseUrl = @"http://192.168.11.222:9191/geoserver";
                //dto.username = "admin";
                //dto.workspace = "urbamid";
                //dto.storeName = "CTR10K_Raster";
                //RestApiGeoServer proxy = new RestApiGeoServer();





                //var featureWorkspace = workspaceFactory.OpenFromFile(@"E:\RASTER\CTR_Messina_Raster.gdb", 0);

                //String workspacePath = @"C:\TEMP\CTR_Messina_Raster.gdb";
                //String nomeLayer = "CTR_10";
                //IWorkspace workspace = OpenWorkspace(workspacePath);
                //IRasterWorkspace2 rasterWorkspace = (IRasterWorkspace2)workspace;
                //IRasterDataset rasterDataset = rasterWorkspace.OpenRasterDataset(nomeLayer);
                //IRasterWorkspaceEx rasterEX = (IRasterWorkspaceEx)workspace;
                //IRasterCatalog rasterCatalog = rasterEX.OpenRasterCatalog(nomeLayer);

                //IFeatureClass fClass = (IFeatureClass)rasterCatalog;
                //IQueryFilter queryFilterlast = new QueryFilter();
                //IFeatureCursor pFCursor;
                //IFeature pF;

                //queryFilterlast.WhereClause = null;
                //pFCursor = fClass.Search(queryFilterlast, false);
                //pF = pFCursor.NextFeature();
                //int iiii = 1;
                //while (pF != null && pF.Shape != null && pF.Shape.IsEmpty == false)
                //{
                //    int indexI = fClass.GetFeature(iiii).Table.FindField("NAME");
                //    string nomeFile = fClass.GetFeature(iiii).Value[indexI].ToString();

                //    IRasterCatalogItem catalogItem3 = (IRasterCatalogItem)fClass.GetFeature(iiii);
                //    IRasterDataset rasterDataset3 = catalogItem3.RasterDataset;
                //    IRaster praster3 = rasterDataset3.CreateDefaultRaster();






                //    praster3.ResampleMethod = rstResamplingTypes.RSP_VectorAverage;
                //    IRasterExporter pConverter3 = new RasterExporter();
                //    byte[] pBytesArr3;
                //    pBytesArr3 = pConverter3.ExportToBytes(praster3, "TIFF"); //
                //    File.WriteAllBytes(@"C:\TEMP\" + nomeFile + ".TIFF", pBytesArr3); // Requires System.IO
                //    pF = pFCursor.NextFeature();
                //    iiii++;


                //}
                //return;
                //    int counti = rasterCatalog.RasterFieldIndex;
                //IRasterCatalogItem catalogItem = (IRasterCatalogItem)fClass.GetFeature(3);

                //IRasterDataset rasterDataset2 = catalogItem.RasterDataset;
                //IRaster praster2 = rasterDataset2.CreateDefaultRaster();






                //praster2.ResampleMethod = rstResamplingTypes.RSP_VectorAverage;
                //IRasterExporter pConverter2 = new RasterExporter();
                //byte[] pBytesArr2;
                //pBytesArr2 = pConverter2.ExportToBytes(praster2, "TIFF"); //
                //File.WriteAllBytes(@"C:\TEMP\test_1.TIFF", pBytesArr2); // Requires System.IO
                //int ind = rasterCatalog.NameFieldIndex;
                //string nnn = rasterCatalog.RasterFieldName;
                //int iii = rasterCatalog.RasterFieldIndex;


                ////IDataset ids = rasterDataset as IDataset;
                ////IPropertySet thePropSet = ids.PropertySet;
                //bool isR = rasterCatalog.IsRasterDataset;


                //IRaster praster = rasterDataset.CreateDefaultRaster();

                //praster.ResampleMethod = rstResamplingTypes.RSP_VectorAverage;
                //IRasterExporter pConverter = new RasterExporter();
                //byte[] pBytesArr;
                //pBytesArr = pConverter.ExportToBytes(praster, "TIFF"); //







                //IFeatureWorkspace fw = (IFeatureWorkspace)workspace;
                //IFeatureClass featureClass = fw.OpenFeatureClass(nomeLayer);





                //int nameIndex = featureClass.FindField("NAME");
                //IFeatureCursor fCursor = null;
                //fCursor = featureClass.Search(null, true);
                //IFeature feature = fCursor.NextFeature();
                //int index = 0;
                //while (feature != null  )
                //{
                //    String aa = feature.Value[nameIndex].ToString();
                //    feature = fCursor.NextFeature();
                //}
                //    File.WriteAllBytes(@"C:\TEMP\test_1.TIFF", pBytesArr); // Requires System.IO


                // IName fName = ds.FullName;
                // String nomeRaste = fName.NameString;





                // IRasterWorkspaceEx wsFGDB = OpenFileGDBWorkspace(workspacePath);
                // IRasterCatalog rasterCatalog = wsFGDB.OpenRasterCatalog(nomeLayer);

                // nomeRaste = rasterCatalog.RasterFieldName;
                // nomeRaste = rasterDataset.CompleteName;
                // nomeRaste = rasterCatalog.RasterFieldName;


                // ISaveAs saveAs = (ISaveAs)rasterDataset;


                // saveAs.SaveAs("mygrid", workspace, "GRID");

                // ISaveAs2 saveAs2 = (ISaveAs2)rasterDataset;
                // IRasterStorageDef rasterStorageDef = new RasterStorageDef();
                // IRasterStorageDef2 rasterStorageDef2 = (IRasterStorageDef2)rasterStorageDef;






                // RasterCatalog rC = (RasterCatalog)rasterCatalog;



                // IRasterDatasetInfo r = (IRasterDatasetInfo)rasterDataset;
                // IStringArray arr =  r.FileList;
                // IRasterStorageDef def = r.StorageDef;

                // int xxxx = arr.Count;
















                // IFeatureWorkspace fw = (IFeatureWorkspace)workspace;
                // IFeatureClass featureClass = fw.OpenFeatureClass(nomeLayer);
                // IFeatureDataset dt =  featureClass.FeatureDataset;

                // if (dt != null)
                //     Console.WriteLine(dt.FullName);

                // IIndexes indici =  featureClass.Indexes;
                // int inCount = indici.IndexCount;
                // for (int i = 0; i < indici.IndexCount; i++)
                // {
                //     IIndex item = indici.Index[i];
                //     String nome = item.Name;
                //     IFields campis = item.Fields;
                //     for (int x = 0; x < campis.FieldCount; x++)
                //     {
                //        IField field = campis.Field[x];

                //         String fNome = field.Name;
                //     }

                // }



                //IFeature f = featureClass.GetFeature(0);

                // IGeometry gem =  f.Shape;

                // esriFeatureType tipo = featureClass.FeatureType;
                // IFields campi =  featureClass.Fields;

                // String nomeSH = featureClass.ShapeFieldName;

                MessageBox.Show("OK");
                //List<ITable> tables = GetTables(workspace);

            }
            catch (Exception ex)
            {

                MessageBox.Show("KO");
            }

        }


   
        IWorkspace OpenWorkspace(string workspacePath)
        {

            try
            {

            ESRI.ArcGIS.RuntimeManager.Bind(ESRI.ArcGIS.ProductCode.Desktop);
            ESRI.ArcGIS.RuntimeManager.BindLicense(ProductCode.Desktop);
            IWorkspaceFactory workspaceFactory = new ESRI.ArcGIS.DataSourcesGDB.FileGDBWorkspaceFactoryClass();
       
            return workspaceFactory.OpenFromFile(workspacePath, 0);
            }
            catch (Exception ex)
            {

                throw ex;
            }



        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void btnImageMosaic_Click(object sender, EventArgs e)
        {
            try
            {

                DialogResult dialogResult;

                //E' un raster
                String nomeFileF = @"C:\TEMP\CTR_Messina_Raster.gdb";
                String nomeLayer = "CTR_10";
                IWorkspace workspace = OpenWorkspace(nomeFileF);
                IRasterWorkspaceEx rasterEX = (IRasterWorkspaceEx)workspace;
                IRasterCatalog rasterCatalog = rasterEX.OpenRasterCatalog(nomeLayer);

                IFeatureClass fClass = (IFeatureClass)rasterCatalog;
                IQueryFilter queryFilterlast = new QueryFilter();
                IFeatureCursor pFCursor;
                IFeature pF;
                RestApiGeoServer rest = new UrbamidAddIn.RestApiGeoServer();
                Utility util = new Utility();

                queryFilterlast.WhereClause = null;
                pFCursor = fClass.Search(queryFilterlast, false);
                pF = pFCursor.NextFeature();
                int index = 1;
                while (pF != null && pF.Shape != null && pF.Shape.IsEmpty == false)
                {
                    index++;
                    pF = pFCursor.NextFeature();
                }

                String messaggio = String.Format("Attenzione saranno importati {0} raster. L'operazione potrebbe richiedere diversi minuti. Si vuole continuare?", index - 1);
                dialogResult = MessageBox.Show(messaggio, "ATTENZIONE", MessageBoxButtons.YesNo);
                if (dialogResult == DialogResult.No)
                    return;

                String tempPath = System.IO.Path.Combine(System.IO.Path.GetTempPath(), nomeLayer);
     

                for (int i = 1; i < index; i++)
                {
                    int indexI = fClass.GetFeature(i).Table.FindField("NAME");
                    string nomeFile = fClass.GetFeature(i).Value[indexI].ToString();

                    IRasterCatalogItem catalogItem = (IRasterCatalogItem)fClass.GetFeature(i);
                    IRasterDataset rasterDataset = catalogItem.RasterDataset;
                    IRaster raster = rasterDataset.CreateDefaultRaster();
                  

                    raster.ResampleMethod = rstResamplingTypes.RSP_NearestNeighbor;
                    IRasterExporter pConverter3 = new RasterExporter();
                    byte[] arrByte;
                    arrByte = pConverter3.ExportToBytes(raster, "TIFF"); //
                    UploadRasterDto inDtoUploadRaster = new UploadRasterDto();
                    inDtoUploadRaster.baseUrl = @"http://192.168.11.222:9191//geoserver";
                    inDtoUploadRaster.arrFile = arrByte;
                    inDtoUploadRaster.nome = nomeFile;
                    inDtoUploadRaster.password = "Geoserver";
                    inDtoUploadRaster.titolo = nomeFile;
                    inDtoUploadRaster.username = "admin";
                    inDtoUploadRaster.workspace = "urbamid";
                    if (!System.IO.Directory.Exists(tempPath))
                        System.IO.Directory.CreateDirectory(tempPath);

                    File.WriteAllBytes(System.IO.Path.Combine(tempPath, inDtoUploadRaster.nome), inDtoUploadRaster.arrFile);
                }

                String fileZip = System.IO.Path.Combine(tempPath, nomeLayer + ".zip");

                if (File.Exists(fileZip))
                    File.Delete(fileZip);


                DirectoryInfo d = new DirectoryInfo(tempPath);
                List<FileInfo> files = d.GetFiles().ToList();
                using (var archive = ZipFile.Open(fileZip, ZipArchiveMode.Create))
                {
                    foreach (FileInfo f in files)
                    {
                        archive.CreateEntryFromFile(f.FullName, f.Name);
                    }

                }

               byte[] arrZip=System.IO.File.ReadAllBytes(fileZip);
                String restService = @"http://192.168.11.222:9191/geoserver/rest/workspaces/urbamid/coveragestores/";
                restService += nomeLayer + "/file.imagemosaic?filename=" + nomeLayer;

                WebRequest request = WebRequest.Create(restService);
                request.ContentType = "application/zip";
                request.Method = "PUT";
                request.Credentials = new NetworkCredential("admin", "Geoserver");
                Stream requestStream = request.GetRequestStream();
                requestStream.Write(arrZip, 0, arrZip.Length);
                requestStream.Close();

                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                MessageBox.Show(response.StatusCode.ToString());

                
            }
            catch (Exception)
            {

                MessageBox.Show("KO");
            }
        }
    }
}
