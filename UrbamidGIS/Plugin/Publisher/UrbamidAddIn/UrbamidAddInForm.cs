using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Display;
using ESRI.ArcGIS.Geodatabase;
using ESRI.ArcGIS.Geometry;
using RestSharp;
using RestSharp.Authenticators;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using stdole;
using System.Text.RegularExpressions;
using UrbamidAddIn;
using ESRI.ArcGIS.esriSystem;
using ESRI.ArcGIS.ArcMapUI;

namespace UrbamidAddIn
{
    public partial class UrbamidAddInForm : Form
    {
        private ILayer selectedLayer = null;
        private IDataset dataset = null;
        private IFeatureLayer featureLayer = null;
        private IGeoFeatureLayer geoFeatureLayer = null;
        private IFeatureRenderer featureRenderer = null;
        private IFeatureClass featureClass = null;
        private IFeatureCursor featureCursor = null;
        private IFeature feature = null;
        public String nomeFile { get; set; }
        private ISimpleRenderer simpleRenderer = null;
        private ISymbol symbol = null;
        private IFillSymbol fillSymbol = null;
        private Color fillColor;
        private Color outlineColor;

        string rendererID;
        string fillColorHEX;
        string outlineColorHEX;
        string outlineWidth;

        private IAnnotateLayerPropertiesCollection annotateLPC = null;
        private IAnnotateLayerProperties annotateLP = null;
        private IElementCollection elementCol = null;
        private IElementCollection elementCol2 = null;
        private ILabelEngineLayerProperties leProps = null;
        private ITextSymbol textSymbol = null;
        bool labelOpenOrClosed;
        string labelField;
        private IFontDisp labelFont;
        string labelFontFamily;
        string labelSize;

        string simpleSldXmlWithoutLabel;
        string simpleSldXmlWithLabel;

        private FolderBrowserDialog folderBrowserDialog;

        string tempSLDpath;

        string geoserverUrl = "http://192.168.11.180:8080/geoserver";
        string geoserverUsername = "admin";
        string geoserverPassword = "geoserver";
        public static string geoserverWorkspace = "urbamid";

        public UrbamidAddInForm()
        {
            InitializeComponent();
            textBoxGSurl.ForeColor = Color.LightGray;
            textBoxGSurl.Text = geoserverUrl;
            this.textBoxGSurl.Leave += new EventHandler(this.textBoxGSurl_Leave);
            this.textBoxGSurl.Enter += new EventHandler(this.textBoxGSurl_Enter);

            textBoxGSusername.Text = geoserverUsername;
            textBoxGSpassword.Text = geoserverPassword;

            /*
            try
            {
                selectedLayer = ArcMap.Document.SelectedLayer;
                dataset = (IDataset)selectedLayer;
                featureLayer = (IFeatureLayer)selectedLayer;
                geoFeatureLayer = (IGeoFeatureLayer)featureLayer;
                featureRenderer = geoFeatureLayer.Renderer;
                featureClass = featureLayer.FeatureClass;
                rendererID = geoFeatureLayer.RendererPropertyPageClassID.Value.ToString();
                labelOpenOrClosed = geoFeatureLayer.DisplayAnnotation;
            }
            catch (Exception)
            {
                //MessageBox.Show("Please make sure you select a layer in the Table Of Contents", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                MessageBox.Show("Selezionare un layer", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            */
        }

        public bool checkSelectedLayer()
        {

            Utility util = new UrbamidAddIn.Utility();
            try
            {
                selectedLayer = ArcMap.Document.SelectedLayer;

                return true;


                //dataset = (IDataset)selectedLayer;
                //featureLayer = (IFeatureLayer)selectedLayer;
                //geoFeatureLayer = (IGeoFeatureLayer)featureLayer;
                //featureRenderer = geoFeatureLayer.Renderer;
                //featureClass = featureLayer.FeatureClass;
                //rendererID = geoFeatureLayer.RendererPropertyPageClassID.Value.ToString();
                //labelOpenOrClosed = geoFeatureLayer.DisplayAnnotation;



                nomeFile = dataset.Workspace.PathName + "\\" + dataset.BrowseName + ".shp";
                return true;
            }
            catch (Exception ex)
            {
                util.message(Dto.TipoMessaggio.ERRORE, "Selezionare un layer");
                util.scriviLog(ex.StackTrace);
                return false;
            }
        }


        //private void getLayer(string lname)
        //{
        //    IEnumLayer pEnumLayer;
        //    ILayer layer;
        //    IMap map;
        //    map = ArcMap.Document.FocusMap;
        //    IFeatureLayer flayer;
        //    pEnumLayer = map.Layers;
        //    layer = pEnumLayer.Next();

        //    while (layer != null)
        //    {

        //        flayer = (IFeatureLayer)layer;
        //        MessageBox.Show("l: " + flayer.Name.ToString());
        //    }
        //}


        private IFeatureLayer getLayer(string lname)
        {
            IMap map;
            IFeatureLayer flayer;
            IEnumLayer layerTag;
            ILayer layer;

            try
            {
                map = ArcMap.Document.FocusMap;
                layerTag = map.Layers;
                layer = layerTag.Next();
                while (layer != null)
                {

                    flayer = (IFeatureLayer)layer;
                    layer = layerTag.Next();
                    MessageBox.Show("l: " + flayer.Name.ToString());
                }
                return null;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error: " + ex.ToString(), "Exception", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                return null;
            }
        }

        private void textBoxGSurl_Leave(object sender, EventArgs e)
        {
            if (textBoxGSurl.Text == "")
            {
                textBoxGSurl.Text = geoserverUrl;
                textBoxGSurl.ForeColor = Color.Gray;
            }
        }

        private void textBoxGSurl_Enter(object sender, EventArgs e)
        {
            if (textBoxGSurl.Text == geoserverUrl)
            {
                //textBoxGSurl.Text = "";
                textBoxGSurl.ForeColor = Color.Black;
            }
        }
        private void buttonGSconnect_Click(object sender, EventArgs e)
        {

            geoserverUsername = textBoxGSusername.Text.Trim();
            geoserverPassword = textBoxGSpassword.Text.Trim();

            string geoserverUrl = textBoxGSurl.Text.Trim() + "/rest/workspaces.xml";
            var client = new RestClient(geoserverUrl);
            client.Authenticator = new HttpBasicAuthenticator(geoserverUsername, geoserverPassword);
            var request = new RestRequest(Method.GET);
            IRestResponse response = client.Execute(request);

            try
            {
                var content = response.Content;
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.LoadXml(content.ToString());
                XmlNodeList nodes = xmlDoc.DocumentElement.SelectNodes("/workspaces/workspace");

                //string geoserverWorkspace;
                //List<string> geoserverWorkspaceList = new List<string>();
                string workspaceFromGeoserver = "";
                foreach (XmlNode node in nodes)
                {
                    //geoserverWorkspace = node.SelectSingleNode("name").InnerText;
                    //geoserverWorkspaceList.Add(geoserverWorkspace);
                    workspaceFromGeoserver = node.SelectSingleNode("name").InnerText;

                    if (!workspaceFromGeoserver.Equals(geoserverWorkspace))
                    {
                        workspaceFromGeoserver = "";
                    }

                }

                //this.comboBoxGSworkspaces.DataSource = geoserverWorkspaceList;

                if (workspaceFromGeoserver.Equals(""))
                {
                    throw new Exception();
                }
                else
                {
                    List<string> geoserverWorkspaceList = new List<string>();
                    geoserverWorkspaceList.Add(geoserverWorkspace);
                    this.comboBoxGSworkspaces.DataSource = geoserverWorkspaceList;
                    this.comboBoxGSworkspaces.SelectedIndex = 0;
                }


                if (checkSelectedLayer())
                {
                    frmManageGeoServer frmGeoServer = new frmManageGeoServer();
                    frmGeoServer.UserName = textBoxGSusername.Text.Trim();
                    frmGeoServer.Password = textBoxGSpassword.Text.Trim();
                    frmGeoServer.Server = textBoxGSurl.Text.Trim();
                    frmGeoServer.WorkSpace = comboBoxGSworkspaces.SelectedValue;
                    frmGeoServer.ShapeFile = nomeFile;
                    this.Dispose();
                    frmGeoServer.ShowDialog();

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Check connection parameters: " + ex.Message, "Connection Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }

        }

        private void buttonGSpublishSLD_Click(object sender, EventArgs e)
        {
            try
            {
                SimpleRendererPolygon();

                if (labelOpenOrClosed == true)
                {
                    SaveSLDtoTempPath(simpleSldXmlWithLabel);
                }
                else
                {
                    SaveSLDtoTempPath(simpleSldXmlWithoutLabel);
                }

                CreateStyleInWorkspace();

                UploadStyleWithinWorkspace();

                IRestResponse response3 = ApplyStyleToLayer();

                if (response3.StatusCode.ToString() == "OK")
                {
                    MessageBox.Show("Layer style has been successfully applied to the specified layer.", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Internal Server Error - Please make sure you've connected to GeoServer.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private IRestResponse ApplyStyleToLayer()
        {
            string geoserverUrl3 = String.Format(textBoxGSurl.Text.Trim() + "/rest/layers/{0}:{1}", comboBoxGSworkspaces.Text, comboBoxGSlayers.Text);
            var client3 = new RestClient(geoserverUrl3);
            client3.Authenticator = new HttpBasicAuthenticator(geoserverUsername, geoserverPassword);
            var request3 = new RestRequest(Method.PUT);
            string sldToBeAssigned = String.Format("<layer><defaultStyle><name>{0}</name><workspace>{1}</workspace></defaultStyle></layer>", textBoxGSsldName.Text, comboBoxGSworkspaces.Text);
            request3.AddHeader("Content-type", "text/xml");
            request3.AddParameter("text/xml", sldToBeAssigned, ParameterType.RequestBody);
            IRestResponse response3 = client3.Execute(request3);
            return response3;
        }

        private void UploadStyleWithinWorkspace()
        {
            var sldToBeUploaded = File.ReadAllBytes(tempSLDpath);
            string geoserverUrl2 = String.Format(textBoxGSurl.Text.Trim() + "/rest/workspaces/{0}/styles/{1}", comboBoxGSworkspaces.Text, textBoxGSsldName.Text);
            var client2 = new RestClient(geoserverUrl2);
            client2.Authenticator = new HttpBasicAuthenticator(geoserverUsername, geoserverPassword);
            var request2 = new RestRequest(Method.PUT);
            request2.AddHeader("Content-type", "application/vnd.ogc.sld+xml");
            request2.AddParameter("application/vnd.ogc.sld+xml", sldToBeUploaded, ParameterType.RequestBody);
            IRestResponse response2 = client2.Execute(request2);
        }

        private void CreateStyleInWorkspace()
        {
            string geoserverUrl = String.Format(textBoxGSurl.Text.Trim() + "/rest/workspaces/{0}/styles.xml", comboBoxGSworkspaces.Text);
            var client = new RestClient(geoserverUrl);
            client.Authenticator = new HttpBasicAuthenticator(geoserverUsername, geoserverPassword);
            var request = new RestRequest(Method.POST);
            string sldNameOnServer = String.Format("<style><name>{0}</name><filename>{0}.sld</filename></style>", textBoxGSsldName.Text);
            request.AddHeader("Content-type", "text/xml");
            request.AddParameter("text/xml", sldNameOnServer, ParameterType.RequestBody);
            IRestResponse response = client.Execute(request);
        }

        private void SaveSLDtoTempPath(string sldObject)
        {
            string tempSLDfileName = String.Format("{0}.sld", textBoxGSsldName.Text);
            tempSLDpath = System.IO.Path.Combine(System.IO.Path.GetTempPath(), tempSLDfileName);
            StreamWriter sw = new System.IO.StreamWriter(tempSLDpath);
            sw.WriteLine(sldObject);
            sw.Close();
        }

        private void buttonGSaddStyleToLayer_Click(object sender, EventArgs e)
        {

        }

        private void buttonExportSLD_Click(object sender, EventArgs e)
        {
            try
            {
                if (featureClass.ShapeType == esriGeometryType.esriGeometryPoint)
                {
                    MessageBox.Show("point feature class");
                }

                if (featureClass.ShapeType == esriGeometryType.esriGeometryPolyline)
                {
                    MessageBox.Show("line feature class");
                }

                if (featureClass.ShapeType == esriGeometryType.esriGeometryPolygon)
                {
                    if (rendererID == UrbamidResource.SimpleRenderer || rendererID == UrbamidResource.NullRenderer)
                    {
                        SimpleRendererPolygon();

                        if (labelOpenOrClosed == true)
                        {
                            SaveSLDtoSpecifiedPath(simpleSldXmlWithLabel);
                        }
                        else
                        {
                            SaveSLDtoSpecifiedPath(simpleSldXmlWithoutLabel);
                        }
                    }

                }

            }
            catch (Exception)
            {
                MessageBox.Show("Please make sure you select a layer in the Table Of Contents", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void SimpleRendererPolygon()
        {
            simpleRenderer = (ISimpleRenderer)featureRenderer;
            symbol = simpleRenderer.Symbol;
            fillSymbol = (IFillSymbol)symbol;

            fillColor = ColorTranslator.FromOle(fillSymbol.Color.RGB);
            fillColorHEX = String.Format("#{0:X6}", fillColor.ToArgb() & 0x00FFFFFF);

            outlineColor = ColorTranslator.FromOle(fillSymbol.Outline.Color.RGB);
            outlineColorHEX = String.Format("#{0:X6}", outlineColor.ToArgb() & 0x00FFFFFF);

            outlineWidth = fillSymbol.Outline.Width.ToString().Replace(",", ".");

            ExportSLD(labelOpenOrClosed);
        }

        private void ExportSLD(bool labelStatus)
        {
            if (labelStatus == true)
            {
                annotateLPC = geoFeatureLayer.AnnotationProperties;
                annotateLPC.QueryItem(0, out annotateLP, out elementCol, out elementCol2);
                leProps = (ILabelEngineLayerProperties)annotateLP;
                labelField = leProps.Expression.Replace(@"[", "").Replace(@"]", "");
                textSymbol = leProps.Symbol;
                labelFont = textSymbol.Font;
                labelFontFamily = labelFont.Name;
                labelSize = textSymbol.Size.ToString().Replace(",", ".");

                simpleSldXmlWithLabel = String.Format(UrbamidResource.SimpleSLDwithLabel, textBoxGSsldName.Text, fillColorHEX, outlineColorHEX, outlineWidth, labelField, labelFontFamily, labelSize);
            }
            else
            {
                simpleSldXmlWithoutLabel = String.Format(UrbamidResource.SimpleSLDwithoutLabel, textBoxGSsldName.Text, fillColorHEX, outlineColorHEX, outlineWidth);
            }
        }

        private void SaveSLDtoSpecifiedPath(string sldObject)
        {
            this.folderBrowserDialog = new FolderBrowserDialog();
            folderBrowserDialog.ShowNewFolderButton = true;
            DialogResult result = folderBrowserDialog.ShowDialog();
            if (result == DialogResult.OK)
            {
                string path = String.Format(@"{0}\{1}.sld", folderBrowserDialog.SelectedPath, textBoxGSsldName.Text);
                StreamWriter file = new System.IO.StreamWriter(path);
                file.WriteLine(sldObject);
                file.Close();
                MessageBox.Show("Layer style has been successfully saved on the specified path.", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        private void comboBoxGSworkspaces_SelectedIndexChanged(object sender, EventArgs e)
        {
            /*
            string geoserverUrl = String.Format(textBoxGSurl.Text.Trim() + "/rest/workspaces/" + "{0}/datastores.xml", comboBoxGSworkspaces.Text);
            var client = new RestClient(geoserverUrl);
            client.Authenticator = new HttpBasicAuthenticator(geoserverUsername, geoserverPassword);
            var request = new RestRequest(Method.GET);
            IRestResponse response = client.Execute(request);

            try
            {
                var content = response.Content;
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.LoadXml(content.ToString());
                XmlNodeList nodes = xmlDoc.DocumentElement.SelectNodes("/dataStores/dataStore");

                string geoserverDataStore;
                List<string> geoserverDataStoreList = new List<string>();

                foreach (XmlNode node in nodes)
                {
                    geoserverDataStore = node.SelectSingleNode("name").InnerText;
                    geoserverDataStoreList.Add(geoserverDataStore);
                }

                this.comboBoxGSdatastores.DataSource = geoserverDataStoreList;
            }
            catch (Exception)
            {
                MessageBox.Show("Check connection parameters", "Connection Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            */

            //http://192.168.11.180:8080/geoserver/rest/workspaces/urbamid/layergroups
            /*
            string geoserverUrl = String.Format(textBoxGSurl.Text.Trim() + "/rest/workspaces/" + geoserverWorkspace + "/layergroups.xml");
            var client = new RestClient(geoserverUrl);
            client.Authenticator = new HttpBasicAuthenticator(geoserverUsername, geoserverPassword);
            var request = new RestRequest(Method.GET);
            IRestResponse response = client.Execute(request);

            try
            {
                var content = response.Content;
                Console.WriteLine(content.ToString());

                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.LoadXml(content.ToString());
                XmlNodeList nodes = xmlDoc.DocumentElement.SelectNodes("/layerGroups/layerGroup");

                string geoserverLayerGroupStore;
                List<string> geoserverLayerGroupStoreList = new List<string>();

                foreach (XmlNode node in nodes)
                {
                    geoserverLayerGroupStore = node.SelectSingleNode("name").InnerText;
                    geoserverLayerGroupStoreList.Add(geoserverLayerGroupStore);
                }

                this.comboBoxGSlayers.DataSource = geoserverLayerGroupStoreList;
            */
            /* */

            //MenuTree menuTree = new MenuTree();
            //menuTree.InitializeMenuTreeView();
            //checkSelectedLayer();
            //menuTree.ShowDialog();
            /*
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                MessageBox.Show("Check connection parameters", "Connection Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }
            */
        }

        private XmlDocument httpGeoserverClient(String url)
        {
            XmlDocument xmlDoc = new XmlDocument();

            var client = new RestClient(url);
            client.Authenticator = new HttpBasicAuthenticator(geoserverUsername, geoserverPassword);
            var request = new RestRequest(Method.GET);
            IRestResponse response = client.Execute(request);

            try
            {
                var content = response.Content;
                Console.WriteLine(content.ToString());
                xmlDoc.LoadXml(content.ToString());
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                MessageBox.Show("Check connection parameters", "Connection Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }

            return xmlDoc;
        }

        public XmlDocument getLayergroups()
        {
            //http://192.168.11.180:8080/geoserver/rest/workspaces/urbamid/layergroups
            List<string> geoserverLayerGroupStoreList = new List<string>();
            //List<string> nodesList = new List<string>();
            XmlDocument xmlDoc = null;
            XmlNodeList nodes = null;
            string geoserverUrl = String.Format(textBoxGSurl.Text.Trim() + "/rest/workspaces/" + geoserverWorkspace + "/" + "layergroups" + ".xml");

            try
            {
                xmlDoc = this.httpGeoserverClient(geoserverUrl);
                nodes = xmlDoc.DocumentElement.SelectNodes("/layerGroups/layerGroup");

                string geoserverLayerGroupStore;
                //String nodeHref;
                foreach (XmlNode node in nodes)
                {
                    geoserverLayerGroupStore = node.SelectSingleNode("name").InnerText;
                    //nodeHref = xmlDoc.DocumentElement.SelectSingleNode("/layerGroups/layerGroup[name='" + geoserverLayerGroupStore + "']/*[local-name() = 'link']/@href").InnerText;
                    geoserverLayerGroupStoreList.Add(geoserverLayerGroupStore);
                    //nodesList.Add(geoserverLayerGroupStore + "|" + nodeHref);
                }

                this.comboBoxGSlayers.DataSource = geoserverLayerGroupStoreList;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                MessageBox.Show("Check connection parameters", "Connection Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }

            return xmlDoc;
        }


        public XmlDocument getLayergrouptOrLayerDetails(string href)
        {
            //http://192.168.11.180:8080/geoserver/rest/workspaces/urbamid/layergroups/xyz.xml
            List<string> geoserverLayerGroupStoreList = new List<string>();

            //string geoserverUrl = String.Format(textBoxGSurl.Text.Trim() + "/rest/workspaces/" + geoserverWorkspace + "/"+"layergroups"+"/"+name+".xml");
            string geoserverUrl = String.Format(textBoxGSurl.Text.Trim() + "/rest/workspaces/" + geoserverWorkspace + "/" + href.Substring(href.IndexOf(geoserverWorkspace) + geoserverWorkspace.Length + 1));

            XmlDocument xmlDoc = null;

            try
            {
                xmlDoc = this.httpGeoserverClient(geoserverUrl);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                MessageBox.Show("Check connection parameters", "Connection Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }

            return xmlDoc;
        }

        private void comboBoxGSdatastores_SelectedIndexChanged(object sender, EventArgs e)
        {

            string geoserverUrl = String.Format(textBoxGSurl.Text.Trim() + "/rest/workspaces/" + "{0}/datastores/{1}/featuretypes.xml", comboBoxGSworkspaces.Text, comboBoxGSdatastores.Text);
            var client = new RestClient(geoserverUrl);
            client.Authenticator = new HttpBasicAuthenticator(geoserverUsername, geoserverPassword);
            var request = new RestRequest(Method.GET);
            IRestResponse response = client.Execute(request);

            try
            {
                var content = response.Content;
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.LoadXml(content.ToString());
                XmlNodeList nodes = xmlDoc.DocumentElement.SelectNodes("/featureTypes/featureType");

                string geoserverFeatureType;
                List<string> geoserverFeatureTypeList = new List<string>();

                foreach (XmlNode node in nodes)
                {
                    geoserverFeatureType = node.SelectSingleNode("name").InnerText;
                    geoserverFeatureTypeList.Add(geoserverFeatureType);
                }

                this.comboBoxGSlayers.DataSource = geoserverFeatureTypeList;
            }
            catch (Exception)
            {
                MessageBox.Show("Check connection parameters", "Connection Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
            }

        }
    }
}
