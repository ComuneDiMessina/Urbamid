using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Linq;
using System.Xml.XPath;

namespace UrbamidAddIn_V10_2.Utility
{
    public class XMLHandle
    {
        private string m_cXMLFilename;
        private XMLHandle.XMLState m_enDocMode;
        private XmlDocument m_objDoc;
        private StringDictionary m_objNameDict;
        private StringDictionary m_objNamespaceDict;
        private Hashtable m_objXPathDict;
        private XmlElement m_objRoot;
        private short m_iLevelCount;
        private XmlNode m_objActiveNode;
        private XmlAttribute m_objActiveAttribute;
        private string m_cXMLVersion;
        private string m_cSLDVersion;
        private string m_cXMLEncoding;
        private Store2Fields m_objNamespaceURL;
        private XmlNamespaceManager m_objNSManager;
        //[SpecialName]
        //private short \u0024STATIC\u0024ParseDoc\u002420121280E1\u0024iLevelCount;

        public XMLHandle(string FileName)
        {
            this.m_cXMLFilename = FileName;
            this.start();
        }

        public void start()
        {
            this.ReadLUT();
            this.m_enDocMode = XMLHandle.XMLState.xmlDocClosed;
            this.m_objDoc = new XmlDocument();
            this.m_iLevelCount = (short)0;
            if (String.IsNullOrEmpty(m_cXMLFilename))
                return;
            this.OpenDoc();
        }

        public void OpenDoc()
        {
            try
            {
                if (this.m_enDocMode == XMLHandle.XMLState.xmlDocClosed)
                {
                    if (!File.Exists(this.m_cXMLFilename))
                        return;
                    this.m_objDoc = new XmlDocument();
                    this.m_objDoc.Load(this.m_cXMLFilename);
                    this.m_objRoot = this.m_objDoc.DocumentElement;
                    if (this.m_objRoot == null)
                        throw new Exception("Das Dokument ist leer. Bitte wählen Sie eine existierende, gültige XML-Datei aus");
                    this.m_enDocMode = XMLHandle.XMLState.xmlDocOpen;
                }
                else
                {
                    int num = (int)MessageBox.Show("Das Dokument ist schon geöffnet!");
                }
            }
            catch (Exception ex)
            {
                throw ex;
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Konnte XML-Dokument nicht öffnen", exception.Message, exception.StackTrace, nameof(OpenDoc));
                //ProjectData.ClearProjectError();
            }
        }

        public string XMLFilename
        {
            get
            {
                return this.m_cXMLFilename;
            }
            set
            {
                this.m_cXMLFilename = value;
            }
        }

        public XmlElement GetRoot
        {
            get
            {
                this.m_iLevelCount = (short)0;
                return this.m_objRoot;
            }
        }

        public short LevelNumber
        {
            get
            {
                return this.m_iLevelCount;
            }
        }

        public bool NavigateElement(string AliasTagName)
        {
            short num1 = 0;
            bool flag1 = false;
            bool flag2;
            try
            {
                if (!this.m_objXPathDict.ContainsKey((object)AliasTagName))
                    throw new Exception("Die Datei LUT_sld_mapping_file.xml enthält den Tag-Alias '" + AliasTagName + "' nicht.");
                StringCollection stringCollection = (StringCollection)this.m_objXPathDict[(object)AliasTagName];
                XmlNode xmlNode1 = this.m_objActiveNode;
                XPathNavigator navigator = xmlNode1.CreateNavigator();
                while (!flag1)
                {
                    if (xmlNode1.ParentNode.ChildNodes.Count > 1)
                    {
                        XmlNode xmlNode2 = xmlNode1;
                        for (; xmlNode1 != null; xmlNode1 = xmlNode1.PreviousSibling)
                        {
                            int num2 = 0;
                            short num3 = checked((short)(stringCollection.Count - 1));
                            short num4 = (short)num2;
                            while ((int)num4 <= (int)num3)
                            {
                                XmlNodeList xmlNodeList = this.m_objActiveNode.SelectNodes(stringCollection[(int)num4], this.m_objNSManager);
                                if (xmlNodeList[checked(xmlNodeList.Count - 1)] == xmlNode1)
                                {
                                    this.m_objActiveNode = xmlNode1;
                                    flag2 = true;
                                    goto label_26;
                                }
                                else
                                    checked { ++num4; }
                            }
                        }
                        xmlNode1 = xmlNode2;
                    }
                    else
                    {
                        int num2 = 0;
                        short num3 = checked((short)(stringCollection.Count - 1));
                        short num4 = (short)num2;
                        while ((int)num4 <= (int)num3)
                        {
                            XmlNodeList xmlNodeList = this.m_objActiveNode.SelectNodes(stringCollection[(int)num4], this.m_objNSManager);
                            if (xmlNodeList[checked(xmlNodeList.Count - 1)] == xmlNode1)
                            {
                                this.m_objActiveNode = xmlNode1;
                                flag2 = true;
                                goto label_26;
                            }
                            else
                                checked { ++num4; }
                        }
                    }
                    if (!navigator.Matches("/"))
                    {
                        navigator.MoveToParent();
                        xmlNode1 = xmlNode1.ParentNode;
                    }
                    checked { ++num1; }
                    if (num1 > (short)100)
                        throw new Exception("Kein gültiger XPathausdruck für '" + AliasTagName + "' gefunden - Sicherheitsabbruch");
                }
                flag2 = true;
            }
            catch (Exception ex)
            {
                throw ex;
                ////ProjectData.SetProjectError(ex);
                ////Exception exception = ex;
                ////this.ErrorMsg("Der XPath-Ausdruck '" + AliasTagName + "' in der LUT-Datei stimmt nicht; oder Navigieren nicht möglich.", exception.Message, exception.StackTrace, nameof(NavigateElement));
                ////flag2 = false;
                ////ProjectData.ClearProjectError();
            }
        label_26:
            return flag2;
        }

        public bool CreateElement(string AliasTagName)
        {
            bool flag = false;
            Utility util = new Utility();
            try
            {
                if (this.NavigateElement(AliasTagName))
                {
                    string namespacePrefix = this.GetNamespacePrefix(AliasTagName);
                    string namespaceUrl = this.GetNamespaceURL((object)namespacePrefix);
                    XmlElement element = this.m_objDoc.CreateElement(namespacePrefix, this.GetOGCName(AliasTagName), namespaceUrl);
                    this.m_objActiveNode.AppendChild((XmlNode)element);
                    this.m_objActiveNode = (XmlNode)element;
                    flag = true;
                }

                return flag;
            }
            catch (Exception ex)
            {
                util.scriviLog("ERRORE CREATE_ELEMENT: " + ex.StackTrace);
                return false;
                //throw ex;
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Konnte Elementknoten nicht erstellen.", exception.Message, exception.StackTrace, nameof(CreateElement));
                //flag = false;
                //ProjectData.ClearProjectError();
            }

        }

        public bool SetElementText(string InnerText)
        {
            bool flag;
            try
            {
                this.m_objActiveNode.InnerText = InnerText;
                flag = true;
            }
            catch (Exception ex)
            {
                throw ex;
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Konnte Elementtext nicht schreiben.", exception.Message, exception.StackTrace, nameof(SetElementText));
                //flag = false;
                //ProjectData.ClearProjectError();
            }
            return flag;
        }


        public bool SetElementTextXML(string InnerText)
        {
            bool flag;
            try
            {
                this.m_objActiveNode.InnerXml = InnerText;
                flag = true;
            }
            catch (Exception ex)
            {
                throw ex;
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Konnte Elementtext nicht schreiben.", exception.Message, exception.StackTrace, nameof(SetElementText));
                //flag = false;
                //ProjectData.ClearProjectError();
            }
            return flag;
        }


        public bool AppendElementText(string InnerText)
        {
            bool flag;
            try
            {
                XElement newNode = XDocument.Parse("<Stroke><CssParameter name='stroke'>#D65504</CssParameter><CssParameter name='stroke-width'>2</CssParameter></Stroke>").Root;
                XmlDocument xD = new XmlDocument();
                xD.LoadXml(newNode.ToString());
                XmlNode xN = xD.FirstChild;
                this.m_objActiveNode.ParentNode.AppendChild(xN);
                flag = true;
            }
            catch (Exception ex)
            {
                throw ex;
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Konnte Elementtext nicht schreiben.", exception.Message, exception.StackTrace, nameof(SetElementText));
                //flag = false;
                //ProjectData.ClearProjectError();
            }
            return flag;
        }

        public bool CreateAttribute(string AttributeName)
        {
            bool flag;
            try
            {
                XmlAttribute attribute = this.m_objDoc.CreateAttribute(AttributeName);
                this.m_objActiveNode.Attributes.Append(attribute);
                this.m_objActiveAttribute = attribute;
                flag = true;
            }
            catch (Exception ex)
            {
                throw ex;
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Konnte Attribut nicht erstellen", exception.Message, exception.StackTrace, nameof(CreateAttribute));
                //flag = false;
                //ProjectData.ClearProjectError();
            }
            return flag;
        }

        public bool SetAttributeValue(string AttributeValue)
        {
            bool flag = false;
            Utility util = new Utility();
            try
            {
                this.m_objActiveAttribute.Value = AttributeValue;
                flag = true;
            }
            catch (Exception ex)
            {
                util.scriviLog("ERRORE SetAttributeValue: " + ex.StackTrace);
                return false;
                //f
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Konnte Attribut nicht erstellen", exception.Message, exception.StackTrace, nameof(SetAttributeValue));
                //flag = false;
                //ProjectData.ClearProjectError();
            }
            return flag;
        }

        public bool ParseDoc(XmlElement CurrentNode)
        {
            //checked { (++this.\u0024STATIC\u0024ParseDoc\u002420121280E1\u0024iLevelCount); }
            Utility util = new Utility();
            bool flag = false;
            try
            {
                if (this.m_enDocMode == XMLHandle.XMLState.xmlDocOpen)
                {
                    if (CurrentNode.HasChildNodes)
                    {
                        for (XmlElement CurrentNode1 = (XmlElement)CurrentNode.FirstChild; CurrentNode1 != null; CurrentNode1 = (XmlElement)CurrentNode1.NextSibling)
                        {
                            if (CurrentNode1.HasChildNodes && CurrentNode1.FirstChild is XmlElement)
                                this.ParseDoc(CurrentNode1);
                        }
                    }
                    //   this.m_iLevelCount = this.u0024STATIC\u0024ParseDoc\u002420121280E1\u0024iLevelCount;
                    //   this.\u0024STATIC\u0024ParseDoc\u002420121280E1\u0024iLevelCount = (short)0;
                    flag = true;
                }
                else
                {
                    int num = (int)MessageBox.Show("Das Dokument ist noch nicht geöffnet");
                }
            }
            catch (Exception ex)
            {
                util.scriviLog("Errore ParseDoc-->" + ex.StackTrace);
                return false;
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Das Dokument ist unbrauchbar", exception.Message, exception.StackTrace, nameof(ParseDoc));
                //ProjectData.ClearProjectError();
            }
            return flag;
        }

        public bool CreateNewFile(bool OverWrite)
        {
            bool flag;
            try
            {
                if (File.Exists(this.m_cXMLFilename))
                {
                    if (!OverWrite)
                        throw new Exception("Fehler beim Anlegen der XML-Datei (Datei besteht bereits)");
                    File.Delete(this.m_cXMLFilename);
                }
                string namespacePrefix = this.GetNamespacePrefix("StyledLayerDescriptor");
                this.m_objDoc = new XmlDocument();
                this.m_objRoot = this.m_objDoc.CreateElement(namespacePrefix, this.GetOGCName("StyledLayerDescriptor"), this.GetNamespaceURL((object)namespacePrefix));
                this.m_objDoc.AppendChild((XmlNode)this.m_objRoot);
                this.m_objActiveNode = (XmlNode)this.m_objRoot;
                this.CreateAttribute("version");
                this.SetAttributeValue(this.m_cSLDVersion);
                int num1 = 0;
                short num2 = checked((short)(this.m_objNamespaceURL.Count - 1));
                short num3 = (short)num1;
                while ((int)num3 <= (int)num2)
                {
                    this.CreateAttribute("xmlns:" + this.m_objNamespaceURL.get_GetString1ByIndex((int)num3));
                    this.SetAttributeValue(this.m_objNamespaceURL.get_GetString2ByIndex((int)num3));
                    checked { ++num3; }
                }
                this.m_objDoc.InsertBefore((XmlNode)this.m_objDoc.CreateXmlDeclaration(this.m_cXMLVersion, this.m_cXMLEncoding, "yes"), (XmlNode)this.m_objRoot);
                this.SaveDoc();
                this.m_enDocMode = XMLHandle.XMLState.xmlDocOpen;
                this.m_objNSManager = new XmlNamespaceManager(this.m_objDoc.NameTable);
                int num4 = 0;
                short num5 = checked((short)(this.m_objNamespaceURL.Count - 1));
                short num6 = (short)num4;
                while ((int)num6 <= (int)num5)
                {
                    this.m_objNSManager.AddNamespace(this.m_objNamespaceURL.get_GetString1ByIndex((int)num6), this.m_objNamespaceURL.get_GetString2ByIndex((int)num6));
                    checked { ++num6; }
                }
                flag = true;
            }
            catch (Exception ex)
            {
                Utility util = new Utility();
                util.scriviLog("ERRORE CreateNewFile: " + ex.StackTrace);
                return false;
                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Fehler beim Anlegen der XML-Datei (" + exception.Message.ToString() + ")", exception.Message, exception.StackTrace, nameof(CreateNewFile));
                //flag = false;
                //ProjectData.ClearProjectError();
            }
            return flag;
        }

        private bool ReadLUT()
        {
            this.m_objNameDict = new StringDictionary();
            this.m_objNamespaceDict = new StringDictionary();
            this.m_objXPathDict = new Hashtable();
            this.m_objNamespaceURL = new Store2Fields();
            bool flag = false;
            try
            {
                string str = Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "Urbamid", "Config", "LUT_sld_mapping_file.xml");
                if (!File.Exists(str))
                    throw new FileNotFoundException();
              
                XmlDocument xmlDocument = new XmlDocument();
                xmlDocument.Load(str);
                for (XmlElement xmlElement1 = (XmlElement)xmlDocument.DocumentElement.FirstChild; xmlElement1 != null; xmlElement1 = (XmlElement)xmlElement1.NextSibling)
                {
                    if (Operators.CompareString(xmlElement1.Name, "sldSyntax", false) == 0)
                    {
                        for (XmlElement xmlElement2 = (XmlElement)xmlElement1.FirstChild; xmlElement2 != null; xmlElement2 = (XmlElement)xmlElement2.NextSibling)
                        {
                            this.m_objNameDict.Add(xmlElement2.Name, xmlElement2.GetAttribute("ogcTag"));
                            this.m_objNamespaceDict.Add(xmlElement2.Name, xmlElement2.GetAttribute("Namespace"));
                            StringCollection stringCollection = new StringCollection();
                            for (XmlElement xmlElement3 = (XmlElement)xmlElement2.FirstChild; xmlElement3 != null; xmlElement3 = (XmlElement)xmlElement3.NextSibling)
                                stringCollection.Add(xmlElement3.InnerText);
                            this.m_objXPathDict.Add((object)xmlElement2.Name, (object)stringCollection);
                        }
                    }
                    else if (Operators.CompareString(xmlElement1.Name, "sldConfiguration", false) == 0)
                    {
                        for (XmlElement xmlElement2 = (XmlElement)xmlElement1.FirstChild; xmlElement2 != null; xmlElement2 = (XmlElement)xmlElement2.NextSibling)
                        {
                            string name = xmlElement2.Name;
                            if (Operators.CompareString(name, "xmlVersion", false) == 0)
                                this.m_cXMLVersion = xmlElement2.InnerText;
                            else if (Operators.CompareString(name, "xmlEncoding", false) == 0)
                                this.m_cXMLEncoding = xmlElement2.InnerText;
                            else if (Operators.CompareString(name, "Namespaces", false) == 0)
                            {
                                for (XmlElement xmlElement3 = (XmlElement)xmlElement2.FirstChild; xmlElement3 != null; xmlElement3 = (XmlElement)xmlElement3.NextSibling)
                                    this.m_objNamespaceURL.Add2Strings(xmlElement3.Name, xmlElement3.InnerText);
                            }
                            else if (Operators.CompareString(name, "sldVersion", false) == 0)
                                this.m_cSLDVersion = xmlElement2.InnerText;
                        }
                    }
                }
                flag = true;
            }
            catch (FileNotFoundException ex)
            {
                Utility util = new Utility();
                util.scriviLog("ERRORE CreateNewFile: " + ex.StackTrace);

                ProjectData.SetProjectError((Exception)ex);
                FileNotFoundException notFoundException = ex;
                ProjectData.ClearProjectError();
            }
            catch (Exception ex)
            {
                Utility util = new Utility();
                util.scriviLog("ERRORE REadLUT: " + ex.StackTrace);

                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Fehler beim öffnen der Konfigurationsdatei", exception.Message, exception.StackTrace, nameof(ReadLUT));
                ProjectData.ClearProjectError();
            }
            return flag;
        }

        private string GetOGCName(string Value)
        {
            string str = String.Empty;
            try
            {
                if (!this.m_objNameDict.ContainsKey(Value))
                    throw new Exception("Der Tag " + Value + " ist noch nicht in die LUT-Datei aufgenommen");
                str = this.m_objNameDict[Value];
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;

                ProjectData.ClearProjectError();
            }
            return str;
        }

        private string GetNamespacePrefix(string Value)
        {
            string str = String.Empty;
            try
            {
                if (!this.m_objNamespaceDict.ContainsKey(Value))
                    throw new Exception("Leider ist dieser Namensraumkürzel " + Value + " noch nicht in die LUT aufgenommen");
                str = this.m_objNamespaceDict[Value];
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;

                ProjectData.ClearProjectError();
            }
            return str;
        }

        private string GetNamespaceURL(object Value)
        {
            string str = String.Empty;
            try
            {
                if (!this.m_objNamespaceURL.get_ContainsString1(Conversions.ToString(Value)))
                    throw new Exception(Conversions.ToString(Operators.ConcatenateObject(Operators.ConcatenateObject((object)"Leider ist dieser URL ", Value), (object)" noch nicht in die LUT aufgenommen")));
                str = this.m_objNamespaceURL.get_GetString2ForString1(Conversions.ToString(Value));
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                ProjectData.ClearProjectError();
            }
            return str;
        }



        public bool SaveDoc()
        {
            try
            {
                this.m_objDoc.Save(this.m_cXMLFilename);
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                ProjectData.ClearProjectError();
            }
            return true;
        }

        private enum XMLState
        {
            xmlDocClosed,
            xmlDocOpen,
        }


    }
}