using RestSharp;
using RestSharp.Authenticators;
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
using UrbamidAddIn.Dto;


namespace UrbamidAddIn
{
    public partial class frmManageGeoServerOLD : Form
    {

        XmlDocument xmlDoc;
        List<string> nodesList;
        Dictionary<string, XmlDocument> nodesListAttribute;
        Dictionary<string, List<string>> nodesChildNodesList;
        private string areaTematicaSuffix = "_areaTematica";
        private string tematismoSuffix = "_tematismo";
        private string layerGroupSuffix = "_layergroup";
        private string layerType = "layer";
        private string voidMenuNode = "nessun elemento";
        private string _defaultLayer = "DEFAULT";
        private bool isEditLayerGroup = false;

        public string UserName { get; internal set; }
        public string Password { get; internal set; }
        public string Server { get; internal set; }
        public object WorkSpace { get; internal set; }
        public string ShapeFile { get; internal set; }

        public string DefaultWorkSpace { get; set; }
        public FeaturesTypeDto featureLayer { get; private set; }


        public frmManageGeoServerOLD()
        {
            InitializeComponent();
        }

        #region MenuTreeView

        public void InitializeMenuTreeView()
        {

            TreeNode layersNode = new TreeNode("Layers Cartografici");
            //NEW GET
            RestApiGeoServer proxy = new UrbamidAddIn.RestApiGeoServer();
            GetGroupLayerDto inDto = new GetGroupLayerDto();
            inDto.baseUrl = this.Server;
            inDto.password = this.Password;
            inDto.username = this.UserName;
            inDto.workspace = this.DefaultWorkSpace;
            LayerGroupDto listaLayers = proxy.getLayergroups(inDto);
            List<LayerGroup> listaAreeTematiche = listaLayers.layerGroups.layerGroup.ToList().Where(p => p.name.EndsWith(areaTematicaSuffix)).ToList();
            if (listaAreeTematiche != null)
            {
                nodesList = new List<string>();
                foreach (var item in listaAreeTematiche)
                {   
                    TreeNode nodeDetails = new TreeNode();
                    inDto.nomeNodo = item.name;
                    LayerDTO dettaglioGroup = proxy.getLayergroupsByName(inDto);

                    
                    nodeDetails.Name = item.name;
                    nodeDetails.Text = dettaglioGroup.layerGroup.title;
                    nodeDetails.Nodes.Add(voidMenuNode).Name = voidMenuNode;
                    layersNode.Nodes.Add(nodeDetails);
                }
            }

            this.menuTreeView.Nodes.Add(layersNode);

            /********************************************OLD VERSION***************************/

            //xmlDoc = _getLayergroups();
            //if (xmlDoc == null)
            //    return;


            //XmlNodeList xmlDocNodesList = xmlDoc.DocumentElement.SelectNodes("/layerGroups/layerGroup");
            //nodesList = new List<string>();
            //foreach (XmlNode node in xmlDocNodesList)
            //{
            //    string name = node.SelectSingleNode("name").InnerText;
            //    //String nodeHref = xmlDoc.DocumentElement.SelectSingleNode("/layerGroups/layerGroup[name='" + nodeName + "']/*[local-name() = 'link']/@href").InnerText;
            //    //href = node.SelectSingleNode("//*[local-name() = 'link']/@href").InnerText;
            //    /*
            //    XmlNode nodeLinkHref = node.SelectSingleNode("//*[local-name() = 'link']/@href");
            //    if (nodeLinkHref != null) {
            //        href = nodeLinkHref.InnerText;
            //    }
            //    */
            //    string href = xmlDoc.DocumentElement.SelectSingleNode("/layerGroups/layerGroup[name='" + name + "']/*[local-name() = 'link']/@href").InnerText;
            //    nodesList.Add(name + "|" + href);
            //}

            //nodesListAttribute = new Dictionary<string, XmlDocument>();
            //nodesChildNodesList = new Dictionary<string, List<string>>();

            //if (nodesList != null && nodesList.Count > 0)
            //{
            //    foreach (string node in nodesList)
            //    {
            //        string nodeName = node.Split('|')[0];
            //        string nodeHref = node.Split('|')[1];
            //        if (nodeName.EndsWith(areaTematicaSuffix))
            //        {
            //            TreeNode nodeDetails = getNodeDetails(nodeName, nodeHref, "");
            //            //if (nodeDetails.Name.EndsWith(areaTematicaSuffix)) {
            //            // Area Tematica
            //            layersNode.Nodes.Add(nodeDetails);
            //            //} /*
            //            /*
            //            else {
            //                // Tematismo
            //                String nodeDetailsName = nodeDetails.Name;
                            
            //                String parentNodeName = nodesParentNode[nodeDetailsName];
            //                (layersNode.Nodes.Find(parentNodeName, false)[0]).Nodes.Add(nodeDetails);
            //            */
            //        }
            //    }
            //}
            //// ...  
            //// Add the root nodes to the TreeView.
            //this.menuTreeView.Nodes.Add(layersNode);

            //// Add the TreeView to the form.
            ////this.Controls.Add(menuTreeView);
            ////this.ResumeLayout(true);

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


            if (e.Button == MouseButtons.Right)
            {
                //controllo se ho selezionato un layer (lastnode = null) 
                if (menuTreeView.SelectedNode.LastNode == null)
                {
                    cmsMenu.Items.Find("tsmPubblicaStile", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmModifica", true).FirstOrDefault().Visible = true;
                    cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                }
                else
                {


                    if (menuTreeView.SelectedNode.Name.EndsWith(layerGroupSuffix))
                    {
                        cmsMenu.Items.Find("tsmImporta", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmModifica", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                    }
                    else if (menuTreeView.SelectedNode.Name.EndsWith(tematismoSuffix))
                    {
                        cmsMenu.Items.Find("tsmImporta", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmCreaLayerGroup", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmModifica", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;
                    }
                    else if (menuTreeView.SelectedNode.Name.EndsWith(areaTematicaSuffix))
                    {
                        cmsMenu.Items.Find("tsmCreaTematismo", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmModifica", true).FirstOrDefault().Visible = true;
                        cmsMenu.Items.Find("tsmElimina", true).FirstOrDefault().Visible = true;

                    }
                    else if (menuTreeView.SelectedNode.Parent == null)
                    {
                        cmsMenu.Items.Find("tsmCreaAreaTematica", true).FirstOrDefault().Visible = true;
                    }

                }

                cmsMenu.Show(new System.Drawing.Point(MousePosition.X, MousePosition.Y));
            }
            else
            {
                TreeNode clickedNode = e.Node;
                
                if (clickedNode.Nodes.ContainsKey(voidMenuNode))
                {
                    RestApiGeoServer proxy = new RestApiGeoServer();
                    clickedNode.Nodes.RemoveByKey(voidMenuNode);
                    //recupero i nodi figli 
                    if (clickedNode.Name.EndsWith(areaTematicaSuffix) || clickedNode.Name.EndsWith(tematismoSuffix) ||
                         clickedNode.Name.EndsWith(layerGroupSuffix))
                    {
                        //nel caso di area tematica o tematismo devo ricercare tra layer group
                        nodesList = new List<string>();

                        GetGroupLayerDto inDto = new GetGroupLayerDto();
                        inDto.baseUrl = this.Server;
                        inDto.password = this.Password;
                        inDto.username = this.UserName;
                        inDto.workspace = this.DefaultWorkSpace;
                        inDto.nomeNodo = clickedNode.Name;
                        LayerDTO dettaglioGroup = proxy.getLayergroupsByName(inDto);

                        if (dettaglioGroup != null && dettaglioGroup.layerGroup != null &&
                            dettaglioGroup.layerGroup.publishables != null && dettaglioGroup.layerGroup.publishables.published != null)
                        {
                            foreach (var item in dettaglioGroup.layerGroup.publishables.published)
                            {

                                if (!item.name.StartsWith(String.Concat(this.DefaultWorkSpace, ":", _defaultLayer)))
                                {
                                    TreeNode nodeDetails = new TreeNode();
                                    inDto.nomeNodo = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                    if (item.tipo == "layerGroup")
                                    {
                                        LayerDTO gruppo = proxy.getLayergroupsByName(inDto);
                                        nodeDetails.Name = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                        nodeDetails.Text = gruppo.layerGroup.title;
                                        nodeDetails.Nodes.Add(voidMenuNode).Name = voidMenuNode;
                                        clickedNode.Nodes.Add(nodeDetails);
                                    }
                                    else
                                    {
                                        nodeDetails.Name = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                        nodeDetails.Text = item.name.Replace(this.DefaultWorkSpace + ":", "");
                                        clickedNode.Nodes.Add(nodeDetails);
                                    }
                                }
                            }
                        }
                    }

                }
                
                //OLD VERSION

                //if (nodesChildNodesList.ContainsKey(clickedNode.Name))
                //{
                //    List<string> nodeChildsNames = nodesChildNodesList[clickedNode.Name];

                //    if (nodeChildsNames != null)
                //    {
                //        clickedNode.Nodes.RemoveByKey(voidMenuNode);
                //        foreach (string nodeChildName in nodeChildsNames)
                //        {
                //            if (!nodesListAttribute.ContainsKey(nodeChildName))
                //            {
                //                XmlDocument xmlDoc = nodesListAttribute[clickedNode.Name];
                //                XmlNode nodeChildTypeNode = xmlDoc.DocumentElement.SelectSingleNode("/layerGroup/publishables/published[name='" + nodeChildName + "']/@type");
                //                XmlNode nodeChildLinkNode = xmlDoc.DocumentElement.SelectSingleNode("/layerGroup/publishables/published[name='" + nodeChildName + "']/*[local-name() = 'link']/@href");
                //                String nodeChildType = "";
                //                if (nodeChildTypeNode != null)
                //                {
                //                    nodeChildType = nodeChildTypeNode.InnerText;
                //                }

                //                String nodeChildHref = "";
                //                if (nodeChildLinkNode != null)
                //                {
                //                    nodeChildHref = nodeChildLinkNode.InnerText;
                //                }

                //                TreeNode nodeChild = null;
                //                if (!nodeChildType.Equals(layerType))
                //                {
                //                    nodeChild = getNodeDetails(nodeChildName, nodeChildHref, "");
                //                }
                //                else
                //                {
                //                    nodeChild = getNodeDetails(nodeChildName, nodeChildHref, nodeChildType);
                //                }
                //                if (nodeChild != null)
                //                {
                //                    clickedNode.Nodes.Add(nodeChild);
                //                }
                //            }
                //        }
                //    }
                //}

                //END OLD VERSION
            }
        }

        public XmlDocument _getLayergroups()
        {

            List<string> geoserverLayerGroupStoreList = new List<string>();
            //List<string> nodesList = new List<string>();
            XmlDocument xmlDoc = null;
            XmlNodeList nodes = null;
            string geoserverUrl = String.Format(this.Server + "/rest/workspaces/" + this.DefaultWorkSpace + "/layergroups.xml");
            Utility util = new Utility();
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
                //todo
                //this.comboBoxGSlayers.DataSource = geoserverLayerGroupStoreList;
            }
            catch (Exception ex)
            {
                util.scriviLog("url: " + geoserverUrl + " @@ " + ex.StackTrace);
                util.message(TipoMessaggio.ERRORE, "Errore recupero Layers");
                return null;
            }

            return xmlDoc;
        }

        private TreeNode getNodeDetails(string nodeName, string nodeHref, string nodeType)
        {
            TreeNode nodeDetails = null;

            //if (!nodeType.Equals(layerType)) {
            if (nodeName != null && !nodeName.Equals(""))
            {
                nodeDetails = new TreeNode();
                XmlDocument xmlDoc = getLayergrouptOrLayerDetails(nodeHref);
                if (!nodesListAttribute.ContainsKey(nodeName))
                {
                    nodesListAttribute.Add(nodeName, xmlDoc);
                }

                if (!nodesChildNodesList.ContainsKey(nodeName))
                {
                    nodesChildNodesList.Add(nodeName, new List<string>());
                }

                String title = "";
                if (!nodeType.Equals(layerType))
                {
                    title = xmlDoc.DocumentElement.SelectSingleNode("title").InnerText;
                    XmlNodeList nodes = null;
                    if (nodeName.EndsWith(areaTematicaSuffix))
                    {
                        nodes = xmlDoc.DocumentElement.SelectNodes("/layerGroup/publishables/published[@type='layerGroup']");
                    }
                    else
                    {
                        nodes = xmlDoc.DocumentElement.SelectNodes("/layerGroup/publishables/published");
                    }

                    String childNodeName = "";
                    foreach (XmlNode node in nodes)
                    {
                        childNodeName = node.SelectSingleNode("name").InnerText;
                        //if (childNodeName.EndsWith(tematismoSuffix) || nodeName.EndsWith(tematismoSuffix))
                        //{
                        /*
                        if (childNodeName.StartsWith(URBAMIDArcmapAddInForm.geoserverWorkspace + ':')) {
                            int index = (URBAMIDArcmapAddInForm.geoserverWorkspace + ':').Length;
                            childNodeName = childNodeName.Substring(index, childNodeName.Length - index);
                        }
                        */
                        nodesChildNodesList[nodeName].Add(childNodeName);
                        //}
                    }
                }
                else
                {
                    title = xmlDoc.DocumentElement.SelectSingleNode("/layer/name").InnerText;
                }

                nodeDetails.Text = title;
                nodeDetails.Name = nodeName;
                if (nodesChildNodesList[nodeName] != null && nodesChildNodesList[nodeName].Count > 0)
                {
                    nodeDetails.Nodes.Add(voidMenuNode).Name = voidMenuNode;
                }

            }

            return nodeDetails;
        }

        private XmlDocument httpGeoserverClient(String url)
        {
            XmlDocument xmlDoc = new XmlDocument();
            Utility util = new UrbamidAddIn.Utility();
            var client = new RestClient(url);
            client.Authenticator = new HttpBasicAuthenticator(this.UserName, this.Password);
            var request = new RestRequest(Method.GET);
            IRestResponse response = client.Execute(request);

            try
            {

                var content = response.Content;
                if (!String.IsNullOrEmpty(content))
                    xmlDoc.LoadXml(content.ToString());
                else
                {
                    //creare un nodo fittizio
                }
            }
            catch (Exception ex)
            {
                util.scriviLog("URL: " + url + " @@ " + ex.StackTrace);
                util.message(TipoMessaggio.ERRORE, "Attenzione, connessione non stabilita 2");
            }

            return xmlDoc;
        }

        public XmlDocument getLayergrouptOrLayerDetails(string href)
        {
            Utility util = new UrbamidAddIn.Utility();
            List<string> geoserverLayerGroupStoreList = new List<string>();

            string geoserverUrl = String.Format(this.Server.Trim() + "/rest/workspaces/" + this.DefaultWorkSpace + "/" + href.Substring(href.IndexOf(this.DefaultWorkSpace) + this.DefaultWorkSpace.Length + 1));

            XmlDocument xmlDoc = null;
            try
            {
                xmlDoc = this.httpGeoserverClient(geoserverUrl);
            }
            catch (Exception ex)
            {
                util.scriviLog("url: " + geoserverUrl + " @@ " + ex.StackTrace);
                util.message(TipoMessaggio.ERRORE, "Attenzione, connessione non stabilita 1");
            }

            return xmlDoc;
        }

        #endregion

        private void frmManageGeoServer_Load(object sender, EventArgs e)
        {
            Utility util = new UrbamidAddIn.Utility();


            //#if DEBUG
            //            this.ShapeFile = @"C:\Users\Vignola\Desktop\URBAMID\test\PAI\PAI_Pericolo.shp";

            //#else



            //            if (!util.checkSelectedLayer())
            //            { 
            //                this.Close();
            //                return;
            //            }
            //            this.ShapeFile = util.getShapeFile();

            //#endif
            if (!loadfileConfig())
            {
                this.Close();
                return;
            }



            //if (String.IsNullOrEmpty(this.ShapeFile))
            //{
            //    util.message(TipoMessaggio.ERRORE, "Shape file non trovato!");
            //    this.Close();
            //    return;
            //}

            txtServer.Text = this.Server;
            txtUser.Text = this.UserName;


            InitializeMenuTreeView();
        }

        //private void cmsMenu_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        //{

        //    String nomeMenu = ((ToolStripMenuItem)e.ClickedItem).Name.ToString();
        //    switch (nomeMenu)
        //    {
        //        //case "tsmImporta":
        //        //    visualizzaTabImporta();  // Importa Shape 
        //        //    break;
        //        //case "tsmModifica":          // Modifica Layer
        //        //    isEditLayerGroup = true;
        //        //    visualizzaTabModifica();
        //        //    break;
        //        //case "tsmElimina":         //Cancella Layer
        //        //    eliminaLayer();
        //        //    break;
        //        //case "tsmPubblicaStile": // IMPORTA STILE":
        //        //    publicaStile();
        //        //    break;
        //        //case "tsmCreaLayerGroup": // CREA LAYER GROUP
        //        //case "tsmCreaTematismo":
        //        ////case "tsmCreaAreaTematica":
        //        ////    isEditLayerGroup = false;
        //        ////    visualizzaTabCreaLayerGroup(nomeMenu);
        //        //    break;
        //        default:
        //            break;
        //    }
        //}

        private void visualizzaTabCreaLayerGroup(String nomeMenu)
        {
            tlpModifica.Visible = false;
            tlpImporta.Visible = false;
            grbDettaglio.Visible = true;
            gbCreateLayerGroup.Visible = true;
            RestApiGeoServer rest = new RestApiGeoServer();
            clbLayerDisponibili.Items.Clear();
            try
            {
                GetListaLayerInDto input = new GetListaLayerInDto();
                input.baseUrl = this.Server;
                input.password = this.Password;
                input.username = this.UserName;
                input.workspace = this.DefaultWorkSpace;
                List<string> layers = rest.getListaLayerInWorkspace(input);
                if (nomeMenu == "tsmCreaLayerGroup")
                {
                    gbCreateLayerGroup.Text = "Layer Group";
                    if (layers != null && layers.Count > 0)
                        clbLayerDisponibili.Items.AddRange(layers.ToArray());
                }
                else
                {

                    layers.AddRange(rest.getListaLayerGroupInWorkspace(input));
                    if (nomeMenu == "tsmCreaTematismo")  // devo rimuovere tutti le aree tematiche e tematismi
                    {   
                        gbCreateLayerGroup.Text = "Tematismo";
                        layers.RemoveAll(p => p.EndsWith(tematismoSuffix));
                        layers.RemoveAll(p => p.EndsWith(areaTematicaSuffix));
                        clbLayerDisponibili.Items.AddRange(layers.ToArray());
                    }

                    if (nomeMenu == "tsmCreaAreaTematica")  // devo rimuovere tutti le aree tematiche e i layer group
                    {
                        gbCreateLayerGroup.Text = "Area Tematica";
                        layers.RemoveAll(p => p.EndsWith(layerGroupSuffix));
                        layers.RemoveAll(p => p.EndsWith(areaTematicaSuffix));
                        clbLayerDisponibili.Items.AddRange(layers.ToArray());
                    }
                }


            }
            catch (Exception ex)
            {

                throw ex;
            }

        }

        private void eliminaLayer()
        {
            RestApiGeoServer rest = new UrbamidAddIn.RestApiGeoServer();
            Utility util = new UrbamidAddIn.Utility();
            String nomeNodo = menuTreeView.SelectedNode.Name;
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
                DeleteDatastoreDto input = new Dto.DeleteDatastoreDto();
                input.baseUrl = this.Server;
                input.nomeDataStore = nomeNodo;
                input.password = this.Password;
                input.username = this.UserName;
                input.workspace = this.DefaultWorkSpace;

                if (isLayerGroup(nomeNodo))
                {
                    if (rest.deleteLayerGroup(input))
                    {
                        util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                        reloadTreeView();
                    }

                }
                else
                {
                    if (rest.deleteDataStore(input))
                    {
                        util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                        reloadTreeView();
                    }
                }
            }
        }

        private void publicaStile()
        {
            RestApiGeoServer rest = new UrbamidAddIn.RestApiGeoServer();
            Utility util = new UrbamidAddIn.Utility();
            String nomeNodo = menuTreeView.SelectedNode.Name;
            if (String.IsNullOrEmpty(nomeNodo))
            {
                util.message(TipoMessaggio.ERRORE, "Nessun nodo selezionato!");
                return;
            }
            else
                nomeNodo = nomeNodo.Replace("urbamid:", "");


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
                GeoServerDto input = new Dto.GeoServerDto();
                input.baseUrl = this.Server;
                input.password = this.Password;
                input.username = this.UserName;
                input.workspace = this.DefaultWorkSpace;
                input.layerName = nomeNodo;
                input.storeName = nomeNodo;

                if (rest.publishSLD(input))
                {
                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo");
                    reloadTreeView();
                }

            }
        }

        private void visualizzaTabImporta()
        {
            Utility util = new UrbamidAddIn.Utility();
            cmbSelectLayer.DataSource = util.getListaLayer();


            //if (String.IsNullOrEmpty(ShapeFile))
            //{
            //    util.message(TipoMessaggio.ERRORE, "Nessun file .shp trovato");
            //    return;
            //}
            //lblShapeVal.Text = ShapeFile;
            //lblNomeLayer.Text = Path.GetFileName(ShapeFile);
            tlpModifica.Visible = false;
            gbCreateLayerGroup.Visible = false;
            tlpImporta.Visible = true;
        }

        private void visualizzaTabModifica()
        {

            tlpModifica.Visible = true;
            tlpImporta.Visible = false;
            gbCreateLayerGroup.Visible = false;
            Utility util = new Utility();
            RestApiGeoServer rest = new RestApiGeoServer();
            String nomeLayer = this.menuTreeView.SelectedNode.Name;
            if (string.IsNullOrEmpty(nomeLayer))
            {
                util.message(TipoMessaggio.ERRORE, "Selezionare un layer");
                return;
            }

            try
            {

                if (isLayerGroup(nomeLayer))
                    visualizzaTabModificaLayerGroup();
                else
                {

                    this.dgvLayerDetail.Visible = true;
                    this.dgvLayerDetail.Columns.Clear();


                    nomeLayer = nomeLayer.Replace("urbamid:", "");

                    GetFeatureTypeDto input = new Dto.GetFeatureTypeDto();
                    input.nomeLayer = nomeLayer;
                    input.baseUrl = this.Server;
                    input.password = this.Password;
                    input.username = this.UserName;
                    input.workspace = this.DefaultWorkSpace;

                    featureLayer = rest.getFeatureTypeByNomeLayer(input);
                    string[] stringSeparators = new string[] { "@vocabulary=" };



                    DataTable dt = new DataTable();

                    dt.Columns.Add("Chiave", typeof(string));
                    dt.Columns.Add("Valore", typeof(string));


                    List<String> listaTipi = new List<String>();
                    if (featureLayer != null)
                    {
                        foreach (String item in featureLayer.featureType.keywords.chiave)
                        {
                            String[] arr = item.Split(stringSeparators, StringSplitOptions.None);

                            if (arr.Length > 1)
                            {
                                DataRow row = dt.NewRow();
                                if (arr[0].ToString().ToLower().StartsWith("viewfield_"))
                                {
                                    listaTipi.Add(TipoAttributoEnum.Visualizzazione.ToString());

                                    row["Chiave"] = arr[0].Substring(10, arr[0].Length - 11);
                                    row["Valore"] = arr[1].Substring(0, arr[1].Length - 2);

                                    dt.Rows.Add(row);
                                }
                                else if (arr[0].ToString().ToLower().StartsWith("searchfield_"))
                                {
                                    listaTipi.Add(TipoAttributoEnum.Ricerca.ToString());
                                    row["Chiave"] = arr[0].Substring(9, arr[0].Length - 10);
                                    row["Valore"] = arr[1].Substring(0, arr[1].Length - 2);

                                    dt.Rows.Add(row);
                                }
                                else
                                {
                                    listaTipi.Add(TipoAttributoEnum.Tutto.ToString());
                                    row["Chiave"] = arr[0].Substring(0, arr[0].Length - 1);
                                    row["Valore"] = arr[1].Substring(0, arr[1].Length - 2);

                                    dt.Rows.Add(row);
                                }
                            }
                        }
                        dgvLayerDetail.DataSource = dt;

                    }


                    #region DataSource ComboBox
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
                    #endregion
                    tlpImporta.Visible = false;
                    tlpModifica.Visible = true;

                }

            }
            catch (Exception ex)
            {
                util.message(TipoMessaggio.ERRORE, "Si è verificato un errore!");
                util.scriviLog(ex.StackTrace);
                return;

            }

        }

        private void visualizzaTabModificaLayerGroup()
        {
            String nomeLayer = this.menuTreeView.SelectedNode.Name.Replace("urbamid:", "");
            String titoloLayer = this.menuTreeView.SelectedNode.Text;


            txtNameLayerGroup.Enabled = false;
            txtNameLayerGroup.Text = nomeLayer;
            txtTitleLayerGroup.Enabled = false;
            txtTitleLayerGroup.Text = titoloLayer;
            dgvLayerDetail.Visible = false;
            gbCreateLayerGroup.Visible = true;


            RestApiGeoServer rest = new RestApiGeoServer();
            clbLayerDisponibili.Items.Clear();
            try
            {
                GetListaLayerInDto input = new GetListaLayerInDto();
                input.baseUrl = this.Server;
                input.password = this.Password;
                input.username = this.UserName;
                input.workspace = this.DefaultWorkSpace;
                List<string> layers = rest.getListaLayerInWorkspace(input);
                if (nomeLayer.EndsWith(layerGroupSuffix))
                {
                    gbCreateLayerGroup.Text = "Layer Group";
                    if (layers != null && layers.Count > 0)
                        clbLayerDisponibili.Items.AddRange(layers.ToArray());
                }
                else
                {


                    if (nomeLayer.EndsWith(tematismoSuffix))  // devo rimuovere tutti le aree tematiche e tematismi
                    {
                        layers.AddRange(rest.getListaLayerGroupInWorkspace(input));
                        gbCreateLayerGroup.Text = "Tematismo";
                        layers.RemoveAll(p => p.EndsWith(tematismoSuffix));
                        layers.RemoveAll(p => p.EndsWith(areaTematicaSuffix));
                        clbLayerDisponibili.Items.AddRange(layers.ToArray());
                    }

                    if (nomeLayer.EndsWith(areaTematicaSuffix))  // devo rimuovere tutti le aree tematiche e i layer group
                    {
                        layers = rest.getListaLayerGroupInWorkspace(input);
                        gbCreateLayerGroup.Text = "Area Tematica";
                        layers.RemoveAll(p => p.EndsWith(layerGroupSuffix));
                        layers.RemoveAll(p => p.EndsWith(areaTematicaSuffix));
                        clbLayerDisponibili.Items.AddRange(layers.ToArray());
                    }
                }

                GetGroupLayerDto inDto = new GetGroupLayerDto();
                inDto.baseUrl = this.Server;
                inDto.password = this.Password;
                inDto.username = this.UserName;
                inDto.nomeNodo = nomeLayer;
                inDto.workspace = this.DefaultWorkSpace;

                LayerDTO layGroup = rest.getLayergroupsByName(inDto);
                if (layGroup != null && layGroup.layerGroup != null)
                {
                    foreach (Published item in layGroup.layerGroup.publishables.published)
                    {
                        //lbLayersSelezionati.Items.Add(item.name.Replace("urbamid:", ""));
                        for (int count = 0; count < clbLayerDisponibili.Items.Count; count++)
                        {
                            if (clbLayerDisponibili.Items[count].ToString() == item.name.Replace("urbamid:", ""))
                            {
                                clbLayerDisponibili.SetSelected(count, true);
                                clbLayerDisponibili.SetItemCheckState(count, CheckState.Checked);
                                break;
                            }
                        }
                    }
                }

            }
            catch (Exception ex)
            {

                throw ex;
            }

        }

        private void btnImporta_Click(object sender, EventArgs e)
        {

            Utility util = new Utility();
            String nomeLayer = lblNomeLayer.Text.Trim();
            String titoloLayer = txtNameLayer.Text.Trim();
            String nomeNodo = menuTreeView.SelectedNode.Name;


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
                util.message(TipoMessaggio.WARNING, "Nome layer obbligatorio!");
                txtNameLayer.Focus();
                return;
            }

            titoloLayer = titoloLayer.Replace(" ", "_");
            ZipDto inputZip = new ZipDto();
            inputZip.pathFolder = Path.GetDirectoryName(this.ShapeFile);
            inputZip.nomeLayer = titoloLayer;
            inputZip.nomeFile = Path.GetFileNameWithoutExtension(this.ShapeFile);
            if (!util.createZipFile(inputZip))
            {
                util.message(TipoMessaggio.ERRORE, "Zip non creato");
                return;
            }


            this.Cursor = Cursors.WaitCursor;
            RestApiGeoServer rest = new UrbamidAddIn.RestApiGeoServer();

            GeoServerDto dto = new GeoServerDto();
            dto.password = this.Password;
            dto.pathFile = Path.Combine(inputZip.pathFolder, titoloLayer + ".zip");
            dto.baseUrl = this.Server;
            dto.username = this.UserName;
            dto.workspace = this.DefaultWorkSpace;
            dto.layerName = Path.GetFileNameWithoutExtension(this.ShapeFile);
            dto.storeName = titoloLayer;
            LayerDetailDto dettaglioLayer = null;
            //verifico se è stato creato il layer
            dettaglioLayer = rest.getDettaglioLayer(dto);
            if (dettaglioLayer != null)
            {
                this.Cursor = Cursors.Default;
                util.message(TipoMessaggio.WARNING, "Attenzione Layer già presente!");
                return;
            }


            if (!rest.UploadShapeFile(dto))
            {
                this.Cursor = Cursors.Default;
                util.message(TipoMessaggio.ERRORE, "Errore durante importazione file");
                return;
            }

            dettaglioLayer = rest.getDettaglioLayer(dto);
            if (dettaglioLayer == null)
            {
                this.Cursor = Cursors.Default;
                util.message(TipoMessaggio.WARNING, "Attenzione Layer NON CREATO!");
                return;
            }

            if (!rest.EditSrs(dto))
            {
                this.Cursor = Cursors.Default;
                return;
            }


            // aggiungo il layer creato al gruppo 

            nomeNodo = nomeNodo.Replace("urbamid:", "");
            GetGroupLayerDto input = new Dto.GetGroupLayerDto();
            input.baseUrl = this.Server;
            input.nomeNodo = nomeNodo;
            input.password = this.Password;
            input.username = this.UserName;
            input.workspace = this.DefaultWorkSpace;

            LayerDTO groupLayer = rest.getLayergroupsByName(input);

            if (groupLayer != null)
            {

                while (groupLayer.layerGroup.publishables.published.Count !=
                    groupLayer.layerGroup.styles.style.Count)
                {
                    if (groupLayer.layerGroup.publishables.published.Count > groupLayer.layerGroup.styles.style.Count)
                    {
                        Style style = new Style();
                        style.href = dettaglioLayer.layer.defaultStyle.href;
                        style.name = dettaglioLayer.layer.defaultStyle.name;
                        groupLayer.layerGroup.styles.style.Add(style);
                    }
                    else
                    {
                        Published pubbl = new Published();
                        pubbl.name = titoloLayer;
                        pubbl.tipo = "layer";
                        pubbl.href = dto.baseUrl + "/rest/workspaces/" + dto.workspace + "/layers/" + titoloLayer + ".json";
                        groupLayer.layerGroup.publishables.published.Add(pubbl);

                    }
                }
                Published layer = new Published();
                layer.name = titoloLayer;
                layer.tipo = "layer";
                layer.href = dto.baseUrl + "/rest/workspaces/" + dto.workspace + "/layers/" + titoloLayer + ".json";
                groupLayer.layerGroup.publishables.published.Add(layer);
                //groupLayer.layerGroup.publishables.published = layer;

                Style stile = new Style();
                stile.href = dettaglioLayer.layer.defaultStyle.href;
                stile.name = dettaglioLayer.layer.defaultStyle.name;
                groupLayer.layerGroup.styles.style.Add(stile);
                groupLayer.baseUrl = this.Server;
                groupLayer.password = this.Password;
                groupLayer.username = this.UserName;
                groupLayer.workspace = this.DefaultWorkSpace;


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

            GetFeatureTypeDto featureDto = new Dto.GetFeatureTypeDto();
            featureDto.nomeLayer = titoloLayer;
            featureDto.baseUrl = this.Server;
            featureDto.password = this.Password;
            featureDto.username = this.UserName;
            featureDto.workspace = this.DefaultWorkSpace;
            featureLayer = rest.getFeatureTypeByNomeLayer(featureDto);
            if (featureLayer != null)
            {
                List<string> attributtiFissi = featureLayer.featureType.keywords.chiave.ToList();
                attributtiFissi.RemoveAll(p => p.Contains("@vocabulary="));
                featureLayer.featureType.keywords.chiave = attributtiFissi;
                featureLayer.featureType.keywords.chiave.Add(String.Format("defaultView\\@vocabulary={0}\\;", this.chkDefaultView.Checked.ToString().ToLower()));
                featureLayer.featureType.enabled = true;



                EditLayerDto editDTO = new EditLayerDto();
                editDTO.baseUrl = this.Server;
                editDTO.password = this.Password;
                editDTO.username = this.UserName;
                editDTO.workspace = this.DefaultWorkSpace;
                editDTO.layerDto = featureLayer;
                editDTO.nomeLayer = titoloLayer;
                if (!rest.UpdateLayer(editDTO))
                {
                    this.Cursor = Cursors.Default;
                    return;
                }
            }
            reloadTreeView();
            this.Cursor = Cursors.Default;
            util.message(TipoMessaggio.INFO, "Importazione file avvenuta con successo");
        }

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
                + ", la cancellazione sarà effettiva soltanto alla click del tasto Conferma in basso, si vuole procedere?",
                  "ATTENZIONE", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {

                DataTable dt = (DataTable)this.dgvLayerDetail.DataSource;
                dt.Rows.RemoveAt(dgvLayerDetail.SelectedRows[0].Index);
                dt.AcceptChanges();
                this.dgvLayerDetail.DataSource = dt;
            }
        }

        private void btnConfirmEditLayer_Click(object sender, EventArgs e)
        {
            Utility util = new UrbamidAddIn.Utility();
            RestApiGeoServer rest = new RestApiGeoServer();
            try
            {
                if (featureLayer != null)
                {
                    List<string> attributtiFissi = featureLayer.featureType.keywords.chiave.ToList();
                    attributtiFissi.RemoveAll(p => p.Contains("@vocabulary="));
                    featureLayer.featureType.keywords.chiave = attributtiFissi;

                    for (int i = 0; i < this.dgvLayerDetail.Rows.Count; i++)
                    {
                        String keywords = String.Empty;
                        if (!this.dgvLayerDetail.Rows[i].Cells[1].Value.ToString().ToUpper().StartsWith("DEFAULTVIEW"))
                        {
                            if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(1))
                            {
                                keywords = "viewField_";
                            }
                            else if (this.dgvLayerDetail.Rows[i].Cells[0].Value.Equals(2))
                            {
                                keywords = "searchField_";
                            }
                            else
                            {
                                keywords = "viewField_";
                                featureLayer.featureType.keywords.chiave.Add("searchField_" + this.dgvLayerDetail.Rows[i].Cells[1].Value + "\\@vocabulary=" + this.dgvLayerDetail.Rows[i].Cells[2].Value + "\\;");
                            }
                        }

                        keywords += this.dgvLayerDetail.Rows[i].Cells[1].Value + "\\@vocabulary=" + this.dgvLayerDetail.Rows[i].Cells[2].Value + "\\;";

                        featureLayer.featureType.keywords.chiave.Add(keywords);
                    }

                    EditLayerDto input = new Dto.EditLayerDto();
                    String nomeLayer = this.menuTreeView.SelectedNode.Name;
                    if (string.IsNullOrEmpty(nomeLayer))
                    {
                        util.message(TipoMessaggio.ERRORE, "Selezionare un layer");
                        return;
                    }

                    nomeLayer = nomeLayer.Replace("urbamid:", "");


                    input.baseUrl = this.Server;
                    input.password = this.Password;
                    input.username = this.UserName;
                    input.workspace = this.DefaultWorkSpace;
                    input.layerDto = featureLayer;
                    input.nomeLayer = nomeLayer;


                    if (!rest.UpdateLayer(input))
                        return;

                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo!");
                    reloadTreeView();
                }
                else
                {
                    util.message(TipoMessaggio.ERRORE, "Errore nella modifica del layer - Layer non trovato");
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

        private void reloadTreeView()
        {
            txtTitleLayerGroup.Text = "";
            txtTitleLayerGroup.Enabled = true;
            txtNameLayerGroup.Text = "";
            txtNameLayerGroup.Enabled = true;
            txtNameLayer.Text = "";
            tlpImporta.Visible = false;
            tlpModifica.Visible = false;
            grbDettaglio.Visible = true;
            lbLayersSelezionati.Items.Clear();
            clbLayerDisponibili.Items.Clear();

            gbCreateLayerGroup.Visible = false;
            this.menuTreeView.Nodes.Clear();
            InitializeMenuTreeView();
        }

        private void btnUndoEditLayer_Click(object sender, EventArgs e)
        {
            featureLayer = null;
            tlpImporta.Visible = false;
            tlpModifica.Visible = false;
            grbDettaglio.Visible = true;
            gbCreateLayerGroup.Visible = false;
            reloadTreeView();
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

        private void btnAnnullaImporta_Click(object sender, EventArgs e)
        {
            reloadTreeView();
        }

        private void cmbSelectLayer_SelectedIndexChanged(object sender, EventArgs e)
        {
            try
            {

                Utility util = new UrbamidAddIn.Utility();
                ComboBox comboBox = (ComboBox)sender;
                string nomeLayer = (string)cmbSelectLayer.SelectedItem;

                this.ShapeFile = util.getShapeFileByLayerName(nomeLayer);
                txtNameLayer.Text = Path.GetFileNameWithoutExtension(this.ShapeFile);
                lblShapeVal.Text = ShapeFile;
                if (!this.ShapeFile.Contains("!"))
                    lblNomeLayer.Text = Path.GetFileName(ShapeFile);
            }
            catch (Exception ex)
            {

                throw ex;
            }
        }

        private void clbLayerDisponibili_ItemCheck(object sender, ItemCheckEventArgs e)
        {
            String nomeLayer = ((System.Windows.Forms.ListBox)sender).SelectedItem.ToString();
            if (e.NewValue == CheckState.Checked)
                lbLayersSelezionati.Items.Add(nomeLayer);
            else
                lbLayersSelezionati.Items.Remove(nomeLayer);
        }

        private void btnCreaLayerGroup_Click(object sender, EventArgs e)
        {
            //controlli
            Utility util = new UrbamidAddIn.Utility();
            RestApiGeoServer rest = new RestApiGeoServer();
            String nomeLayer = txtNameLayerGroup.Text.Trim();
            String titoloLayer = txtTitleLayerGroup.Text.Trim();

            if (String.IsNullOrEmpty(nomeLayer))
            {
                util.message(TipoMessaggio.WARNING, "Attenzione, nome layer obbligatorio!");
                return;
            }
            if (String.IsNullOrEmpty(titoloLayer))
            {
                util.message(TipoMessaggio.WARNING, "Attenzione, titolo layer obbligatorio!");
                return;
            }

            if (lbLayersSelezionati.Items.Count == 0)
            {
                lbLayersSelezionati.Items.Add("DEFAULT_layergroup");
                //util.message(TipoMessaggio.WARNING, "Attenzione,aggiungere almeno un layer al gruppo");
                //return;
            }

            String nomeNodo = menuTreeView.SelectedNode.Name;

            if (String.IsNullOrEmpty(nomeNodo))
            {
                if (!menuTreeView.SelectedNode.Text.Equals("Layers Cartografici"))
                {
                    util.message(TipoMessaggio.ERRORE, "Nessun nodo selezionato!");
                    return;
                }
            }
            else
                nomeNodo = nomeNodo.Replace("urbamid:", "");



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

        private void addLayerGroup(String titoloLayer, String nomeLayer, String nomeNodo)
        {

            RestApiGeoServer rest = new UrbamidAddIn.RestApiGeoServer();
            Utility util = new UrbamidAddIn.Utility();
            try
            {


                CreateLayerGroupInDto layerGroup = new Dto.CreateLayerGroupInDto();
                layerGroup.abstractTxt = titoloLayer;
                layerGroup.mode = "SINGLE";
                layerGroup.baseUrl = this.Server;


                layerGroup.title = titoloLayer;
                layerGroup.workspace = this.DefaultWorkSpace;
                layerGroup.password = this.Password;
                layerGroup.username = this.UserName;
                layerGroup.workspaces = new Workspace();
                layerGroup.workspaces.name = this.DefaultWorkSpace;
                layerGroup.keywords = new Keywords();
                layerGroup.keywords.chiave = new List<string>();
                layerGroup.keywords.chiave.Add("defaultView\\@vocabulary=false\\;");
                layerGroup.publishables = new Publishables();
                layerGroup.publishables.published = new List<Published>();
                //layerGroup.publishables.published = new Published();
                layerGroup.styles = new Styles();
                layerGroup.styles.style = new List<Style>();

                layerGroup.bounds = new Dto.Bounds();
                layerGroup.bounds.crs = "EPSG:4326";
                layerGroup.bounds.maxx = 0;
                layerGroup.bounds.maxy = 0;
                layerGroup.bounds.minx = 0;
                layerGroup.bounds.miny = 0;


                if (gbCreateLayerGroup.Text == "Layer Group")
                    layerGroup.name = nomeLayer + layerGroupSuffix;
                else if (gbCreateLayerGroup.Text == "Tematismo")
                    layerGroup.name = nomeLayer + tematismoSuffix;
                else if (gbCreateLayerGroup.Text == "Area Tematica")
                    layerGroup.name = nomeLayer + areaTematicaSuffix;
                //per ogni layer selezionato devo 
                GeoServerDto dto = new GeoServerDto();
                dto.baseUrl = this.Server;
                dto.password = this.Password;
                dto.username = this.UserName;
                dto.workspace = this.DefaultWorkSpace;
                foreach (var item in lbLayersSelezionati.Items)
                {
                    if (isLayerGroup(item.ToString()))
                    {
                        Style stile = new Style();

                        layerGroup.styles.style.Add(stile);
                        Published pub = new Published();
                        pub.name = item.ToString();
                        pub.tipo = "layerGroup";
                        pub.href = layerGroup.baseUrl + "/rest/workspaces/" + layerGroup.workspace + "/layergroups/" + item.ToString() + ".json";
                        layerGroup.publishables.published.Add(pub);
                        //layerGroup.publishables.published = pub;
                    }
                    else
                    {
                        dto.layerName = item.ToString();
                        dto.storeName = item.ToString();
                        LayerDetailDto output = rest.getDettaglioLayer(dto);
                        Style stile = new Style();
                        stile.href = output.layer.defaultStyle.href;
                        stile.name = output.layer.defaultStyle.name;
                        layerGroup.styles.style.Add(stile);
                        Published pub = new Published();
                        pub.name = dto.layerName;
                        pub.tipo = "layer";
                        pub.href = layerGroup.baseUrl + "/rest/workspaces/" + layerGroup.workspace + "/layers/" + item.ToString() + ".json";
                        layerGroup.publishables.published.Add(pub);
                        //layerGroup.publishables.published = pub;
                    }
                }


                if (rest.createLayerGroup(layerGroup))
                {
                    if (!layerGroup.name.EndsWith(areaTematicaSuffix))
                    {
                        if (String.IsNullOrEmpty(nomeNodo))
                            nomeNodo = layerGroup.name;

                        GetGroupLayerDto input = new Dto.GetGroupLayerDto();
                        input.baseUrl = this.Server;
                        input.nomeNodo = nomeNodo;
                        input.password = this.Password;
                        input.username = this.UserName;
                        input.workspace = this.DefaultWorkSpace;

                        LayerDTO groupLayer = rest.getLayergroupsByName(input);

                        if (groupLayer != null)
                        {
                            Published layer = new Published();
                            layer.name = layerGroup.name;
                            layer.tipo = "layerGroup";
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
                                    pubbl.tipo = "layer";
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

            RestApiGeoServer rest = new UrbamidAddIn.RestApiGeoServer();
            Utility util = new UrbamidAddIn.Utility();
            try
            {
                GetGroupLayerDto inDto = new GetGroupLayerDto();
                inDto.baseUrl = this.Server;
                inDto.password = this.Password;
                inDto.username = this.UserName;
                inDto.nomeNodo = nomeLayer;
                inDto.workspace = this.DefaultWorkSpace;
                LayerDTO dettaglioLayer = rest.getLayergroupsByName(inDto);

                CreateLayerGroupInDto layerGroup = new Dto.CreateLayerGroupInDto();
                layerGroup.abstractTxt = titoloLayer;
                layerGroup.mode = dettaglioLayer.layerGroup.mode;
                layerGroup.baseUrl = this.Server;

                layerGroup.name = inDto.nomeNodo;
                layerGroup.title = titoloLayer;
                layerGroup.workspace = this.DefaultWorkSpace;
                layerGroup.password = this.Password;
                layerGroup.username = this.UserName;
                layerGroup.bounds = dettaglioLayer.layerGroup.bounds;
                layerGroup.workspaces = dettaglioLayer.layerGroup.workspace;
                layerGroup.keywords = dettaglioLayer.layerGroup.keywords;
                layerGroup.publishables = new Publishables();
                layerGroup.publishables.published = new List<Published>();
                layerGroup.styles = new Styles();
                layerGroup.styles.style = new List<Style>();
                Style stile = new Style();
                Published pub = new Published();
                foreach (var item in lbLayersSelezionati.Items)
                {
                    pub = new Published();
                    stile = new Style();
                    pub.name = inDto.workspace + ":" + item.ToString();
                    if (isLayerGroup(item.ToString()))
                    {
                        pub.href = this.Server + "/workspaces/" + inDto.workspace + "/layergroups/" + item.ToString();
                        pub.tipo = "layerGroup";
                        layerGroup.publishables.published.Add(pub);

                        stile.href = this.Server;
                        stile.name = "##";
                        layerGroup.styles.style.Add(stile);
                    }
                    else
                    {
                        pub.href = this.Server + "/workspaces/" + inDto.workspace + "/layers/" + item.ToString();
                        pub.tipo = "layer";
                        layerGroup.publishables.published.Add(pub);

                        GeoServerDto input = new GeoServerDto();
                        input.baseUrl = this.Server;
                        input.password = this.Password;
                        input.username = this.UserName;
                        input.storeName = item.ToString();
                        input.workspace = this.DefaultWorkSpace;
                        LayerDetailDto dettaglio = rest.getDettaglioLayer(input);
                        stile.href = dettaglio.layer.defaultStyle.href;
                        stile.name = dettaglio.layer.defaultStyle.name;
                        layerGroup.styles.style.Add(stile);
                    }

                }



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

        private bool isLayerGroup(string nomeNodo)
        {
            return (nomeNodo.EndsWith(areaTematicaSuffix) || nomeNodo.EndsWith(tematismoSuffix) ||
                nomeNodo.EndsWith(layerGroupSuffix));

        }

        private void btnCloseLayerGroup_Click(object sender, EventArgs e)
        {
            reloadTreeView();

        }


        private void configurazioneToolStripMenuItem_Click_1(object sender, EventArgs e)
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
        
        private void tsmCreaAreaTematica_Click(object sender, EventArgs e)
        {
            isEditLayerGroup = false;
            visualizzaTabCreaLayerGroup("tsmCreaAreaTematica");
        }

        private void tsmCreaTematismo_Click(object sender, EventArgs e)
        {
            isEditLayerGroup = false;
            visualizzaTabCreaLayerGroup("tsmCreaTematismo");

        }

        private void tsmElimina_Click(object sender, EventArgs e)
        {
            eliminaLayer();
        }

        private void tsmPubblicaStile_Click(object sender, EventArgs e)
        {
            publicaStile();
        }

        private void tsmModifica_Click(object sender, EventArgs e)
        {
            isEditLayerGroup = true;
            visualizzaTabModifica();
        }

        private void tsmImporta_Click(object sender, EventArgs e)
        {
            visualizzaTabImporta();
        }

        private void tsmCreaLayerGroup_Click(object sender, EventArgs e)
        {
            isEditLayerGroup = false;
            visualizzaTabCreaLayerGroup("tsmCreaLayerGroup");
        }

        private void clbLayerDisponibili_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}

