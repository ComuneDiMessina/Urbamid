using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Display;
using ESRI.ArcGIS.Geodatabase;
using stdole;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using UrbamidAddIn_V10_2.Dto;

namespace UrbamidAddIn_V10_2.Utility
{
    public class Utility : Log
    {

        #region property
        private ILayer selectedLayer = null;
        private IDataset dataset = null;
        private IFeatureLayer featureLayer = null;
        private IGeoFeatureLayer geoFeatureLayer = null;
        private IFeatureRenderer featureRenderer = null;
        private IFeatureClass featureClass = null;
        //private IFeatureCursor featureCursor = null;
        //private IFeature feature = null;

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

        //string simpleSldXmlWithoutLabel;
        //string simpleSldXmlWithLabel;

        #endregion



        internal void message(TipoMessaggio tipoMessaggio, string messaggio)
        {

            switch (tipoMessaggio)
            {
                case TipoMessaggio.ERRORE:
                    MessageBox.Show(messaggio, "ERRORE", MessageBoxButtons.OK);
                    break;
                case TipoMessaggio.INFO:
                    MessageBox.Show(messaggio, "INFO", MessageBoxButtons.OK);
                    break;
                case TipoMessaggio.WARNING:
                    MessageBox.Show(messaggio, "ATTENZIONE", MessageBoxButtons.OK);
                    break;
                default:
                    MessageBox.Show(messaggio, "INFO", MessageBoxButtons.OK);
                    break;
            }


        }

        public bool createZipFile(ZipDto inputZip)
        {
            try
            {
                String nomeFile = inputZip.nomeLayer.Replace(" ", "_");
                if (File.Exists(System.IO.Path.Combine(inputZip.pathFolder, nomeFile + ".zip")))
                    File.Delete(System.IO.Path.Combine(inputZip.pathFolder, nomeFile + ".zip"));

                var zipFile = System.IO.Path.Combine(inputZip.pathFolder, nomeFile + ".zip");
                DirectoryInfo d = new DirectoryInfo(inputZip.pathFolder);
                List<FileInfo> files = d.GetFiles().Where(p => p.Name.StartsWith(inputZip.nomeFile) &&
                !p.Name.EndsWith(".lock")).ToList();



                using (var archive = ZipFile.Open(zipFile, ZipArchiveMode.Create))
                {
                    foreach (FileInfo f in files)
                    {
                        if (System.IO.Path.GetFileNameWithoutExtension(f.Name) == inputZip.nomeFile ||
                            System.IO.Path.GetFileNameWithoutExtension(f.Name).EndsWith(".shp") &&
                            !System.IO.Path.GetFileNameWithoutExtension(f.Name).EndsWith(".zip"))
                        {
                            if (inputZip.nomeFile != inputZip.nomeLayer)
                            {
                                if (!f.Name.EndsWith(".zip"))
                                {
                                    bool isCreate = false;
                                    String newFile = System.IO.Path.Combine(f.DirectoryName, nomeFile + f.Extension);
                                    if (!File.Exists(newFile))
                                    {
                                        isCreate = true;
                                        File.Copy(f.FullName, newFile);
                                    }
                                    archive.CreateEntryFromFile(System.IO.Path.GetFullPath(newFile), nomeFile + f.Extension);

                                    if (isCreate)
                                        File.Delete(newFile);
                                }
                            }
                            else
                            {
                                if (!f.Name.EndsWith(".zip"))
                                    archive.CreateEntryFromFile(f.FullName, f.Name.Replace(" ", "_"));
                            }
                        }
                    }
                }
                return true;
            }
            catch (Exception ex)
            {
                scriviLog(ex.StackTrace);
                return false;
            }
        }

        public bool checkSelectedLayer()
        {
            ILayer selectedLayer = null;
            try
            {
                if (ArcMap.Document.SelectedLayer == null)
                {
                    message(Dto.TipoMessaggio.ERRORE, "Selezionare un layer");
                    return false;
                }
                else
                    selectedLayer = ArcMap.Document.SelectedLayer;


                return true;

            }
            catch (Exception ex)
            {
                message(Dto.TipoMessaggio.ERRORE, "Selezionare un layer");
                scriviLog(ex.StackTrace);
                return false;
            }
        }

        public string ExportSLD(bool labelStatus, string nomeFileStile)
        {
            try
            {

                simpleRenderer = (ISimpleRenderer)featureRenderer;
                symbol = simpleRenderer.Symbol;
                fillSymbol = (IFillSymbol)symbol;

                fillColor = ColorTranslator.FromOle(fillSymbol.Color.RGB);
                fillColorHEX = String.Format("#{0:X6}", fillColor.ToArgb() & 0x00FFFFFF);

                outlineColor = ColorTranslator.FromOle(fillSymbol.Outline.Color.RGB);
                outlineColorHEX = String.Format("#{0:X6}", outlineColor.ToArgb() & 0x00FFFFFF);

                outlineWidth = fillSymbol.Outline.Width.ToString().Replace(",", ".");

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

                    return String.Format(UrbamidResource.SimpleSLDwithLabel, nomeFileStile, fillColorHEX, outlineColorHEX, outlineWidth, labelField, labelFontFamily, labelSize);
                }
                else
                    return String.Format(UrbamidResource.SimpleSLDwithoutLabel, nomeFileStile, fillColorHEX, outlineColorHEX, outlineWidth);
            }

            catch (Exception ex)
            {
                message(TipoMessaggio.ERRORE, "Errore export SLD");
                scriviLog("Errore export sld -->" + ex.StackTrace);
                return String.Empty;
            }

        }

        internal bool getLabelStaus()
        {

            selectedLayer = ArcMap.Document.SelectedLayer;
            dataset = (IDataset)selectedLayer;
            featureLayer = (IFeatureLayer)selectedLayer;
            geoFeatureLayer = (IGeoFeatureLayer)featureLayer;
            featureRenderer = geoFeatureLayer.Renderer;
            featureClass = featureLayer.FeatureClass;
            rendererID = geoFeatureLayer.RendererPropertyPageClassID.Value.ToString();
            return geoFeatureLayer.DisplayAnnotation;
        }


        internal string getFileStyleName()
        {
            IMap map;
            IFeatureLayer flayer;
            IEnumLayer layerTag;
            ILayer layer;
            String nomeFile = string.Empty;
            try
            {
                map = ArcMap.Document.FocusMap;
                layerTag = map.Layers;
                layer = layerTag.Next();

                selectedLayer = ArcMap.Document.SelectedLayer;
                if (selectedLayer == null)
                    return String.Empty;

                //flayer = (IFeatureLayer)layer;
                nomeFile = selectedLayer.Name.ToString();// flayer.Name.ToString();
                return nomeFile;

            }
            catch (Exception ex)
            {

                message(TipoMessaggio.ERRORE, "Errore recupero file di stile");
                scriviLog(ex.StackTrace);
                return nomeFile;
            }
        }

        internal string getShapeFile()
        {
            ILayer selectedLayer = null;
            IDataset dataset = null;
            IFeatureLayer featureLayer = null;
            IGeoFeatureLayer geoFeatureLayer = null;
            IFeatureRenderer featureRenderer = null;
            IFeatureClass featureClass = null;


            try
            {
                selectedLayer = ArcMap.Document.SelectedLayer;
                dataset = (IDataset)selectedLayer;
                featureLayer = (IFeatureLayer)selectedLayer;
                geoFeatureLayer = (IGeoFeatureLayer)featureLayer;
                featureRenderer = geoFeatureLayer.Renderer;
                featureClass = featureLayer.FeatureClass;
                String rendererID = geoFeatureLayer.RendererPropertyPageClassID.Value.ToString();
                labelOpenOrClosed = geoFeatureLayer.DisplayAnnotation;

                return dataset.Workspace.PathName + "\\" + dataset.BrowseName + ".shp";

            }
            catch (Exception ex)
            {
                scriviLog(ex.StackTrace);
                return String.Empty;
            }
        }



        internal void SaveSLDtoTempPath(string sldObject, string nomeFileStile)
        {
            string tempSLDfileName = String.Format("{0}.sld", nomeFileStile);
            string tempSLDpath = System.IO.Path.Combine(System.IO.Path.GetTempPath(), tempSLDfileName);
            StreamWriter sw = new System.IO.StreamWriter(tempSLDpath);
            sw.WriteLine(sldObject);
            sw.Close();

            message(TipoMessaggio.INFO, "PATH SALVATAGGIO SLD: " + tempSLDpath);
        }


        internal List<String> getListaLayer()
        {
            IMap map;
            IFeatureLayer flayer;
            IEnumLayer layerTag;
            ILayer layer;
            List<String> layers = new List<string>();
            String nomeFile = string.Empty;
            try
            {
                layers.Add("Seleziona Layer");
                map = ArcMap.Document.FocusMap;
                layerTag = map.Layers;
                layer = layerTag.Next();
                while (layer != null)
                {
                    try
                    {
                        if (String.IsNullOrEmpty(layer.Name))
                        {
                            flayer = (IFeatureLayer)layer;
                            layers.Add(flayer.Name);
                        }
                        else
                            layers.Add(layer.Name);

                    }
                    catch (Exception exx)
                    {

                        scriviLog(exx.StackTrace);
                    }
                    finally
                    {
                        layer = layerTag.Next();
                    }

                }

                //selectedLayer = ArcMap.Document.SelectedLayer;
                //flayer = (IFeatureLayer)layer;
                //nomeFile = selectedLayer.Name.ToString();// flayer.Name.ToString();

                return layers;

            }
            catch (Exception ex)
            {
                scriviLog(ex.StackTrace);
                return new List<string>();
            }
        }

        internal string getShapeFileByLayerName(string nomeLayer)
        {
            try
            {
                IMap map;
                ILayer selectedLayer = null;
                IDataset dataset = null;
                IFeatureLayer featureLayer = null;
                IEnumLayer layerTag;
                string shapeFile = String.Empty;
                try
                {

                    map = ArcMap.Document.FocusMap;
                    layerTag = map.Layers;
                    selectedLayer = layerTag.Next();
                    while (selectedLayer != null)
                    {
                        try
                        {
                            if (selectedLayer.Name.Equals(nomeLayer))
                            {
                                dataset = (IDataset)selectedLayer;
                                shapeFile = dataset.Workspace.PathName + "\\" + dataset.BrowseName;


                                if (!shapeFile.ToUpper().EndsWith(".TIF") || shapeFile.ToUpper().EndsWith(".TIFF"))
                                    shapeFile += ".shp";


                                return shapeFile;
                            }

                           

                            //if (selectedLayer.Name.ToString().ToUpper().EndsWith(".TIF") ||
                            //    selectedLayer.Name.ToString().ToUpper().EndsWith(".TIFF"))
                            //{
                            //    if (selectedLayer.Name.Equals(nomeLayer))
                            //    {
                            //        dataset = (IDataset)selectedLayer;
                            //        return dataset.Workspace.PathName + "\\" + dataset.BrowseName;
                            //    }
                            //}
                            //else
                            //{


                            //    featureLayer = (IFeatureLayer)selectedLayer;
                            //    if (featureLayer.Name.Equals(nomeLayer))
                            //    {
                            //        dataset = (IDataset)selectedLayer;
                            //        return dataset.Workspace.PathName + "\\" + dataset.BrowseName + ".shp";
                            //    }

                            //}
                        }
                        catch (Exception exx)
                        {

                            scriviLog(exx.StackTrace);
                        }
                        finally
                        {
                            selectedLayer = layerTag.Next();

                        }


                    }

                    return "File non trovato!";

                }
                catch (Exception ex)
                {
                    scriviLog(ex.StackTrace);
                    return String.Empty;
                }

            }
            catch (Exception ex)
            {
                scriviLog(ex.Message + " --> " + ex.StackTrace);
                return String.Empty;
            }
        }
    }
}
