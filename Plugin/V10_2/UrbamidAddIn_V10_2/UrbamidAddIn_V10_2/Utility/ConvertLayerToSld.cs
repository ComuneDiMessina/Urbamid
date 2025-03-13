using ESRI.ArcGIS.Carto;
using ESRI.ArcGIS.Display;
using ESRI.ArcGIS.Geodatabase;
using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Windows.Forms;

namespace UrbamidAddIn_V10_2.Utility
{
    public class ConvertLayerToSld
    {
        #region property
        //private ILayer selectedLayer = null;
        //private IDataset dataset = null;
        //private IFeatureLayer featureLayer = null;
        //private IGeoFeatureLayer geoFeatureLayer = null;
        private IFeatureRenderer featureRenderer = null;
        //private IFeatureClass featureClass = null;
        //private IFeatureCursor featureCursor = null;
        //private IFeature feature = null;

        //private ISimpleRenderer simpleRenderer = null;
        //private ISymbol symbol = null;
        //private IFillSymbol fillSymbol = null;
        //private Color fillColor;
        //private Color outlineColor;

        //string rendererID;
        //string fillColorHEX;
        //string outlineColorHEX;
        //string outlineWidth;

        //private IAnnotateLayerPropertiesCollection annotateLPC = null;
        //private IAnnotateLayerProperties annotateLP = null;
        //private IElementCollection elementCol = null;
        //private IElementCollection elementCol2 = null;
        //private ILabelEngineLayerProperties leProps = null;
        //private ITextSymbol textSymbol = null;
        //bool labelOpenOrClosed;
        //string labelField;
        //private IFontDisp labelFont;
        //string labelFontFamily;
        //string labelSize;

        //string simpleSldXmlWithoutLabel;
        //string simpleSldXmlWithLabel;

        #endregion


        #region LYR to SLD
        public StructProject strutturaLayer;
        public struct StructProject
        {
            public ArrayList LayerList;
            public int LayerCount;
        }

        #endregion


        #region FileSLD
        private XMLHandle m_objXMLHandle;

        internal struct StructSimpleRenderer
        {
            public Feature FeatureCls;
            public string LayerName;
            public string DatasetName;
            public ArrayList SymbolList;
            public StructAnnotation Annotation;
        }

        internal struct StructClassBreaksRenderer
        {
            public Feature FeatureCls;
            public string LayerName;
            public string DatasetName;
            public int BreakCount;
            public string FieldName;
            public string NormFieldName;
            public ArrayList SymbolList;
            public StructAnnotation Annotation;
        }

        #endregion
        private ArrayList m_alClassifiedFields;

        internal struct StructBarChartSymbol
        {
            public bool ShowAxes;
            public double Spacing;
            public bool VerticalBars;
            public double Width;
            public LineStructs kindOfAxeslineStruct;
            public StructSimpleLineSymbol Axes_SimpleLine;
            public StructCartographicLineSymbol Axes_CartographicLine;
            public StructMarkerLineSymbol Axes_MarkerLine;
            public StructHashLineSymbol Axes_HashLine;
            public StructPictureLineSymbol Axes_PictureLine;
            public StructMultilayerLineSymbol Axes_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructPieChartSymbol
        {
            public bool Clockwise;
            public bool UseOutline;
            public LineStructs kindOfOutlineStruct;
            public StructSimpleLineSymbol Outline_SimpleLine;
            public StructCartographicLineSymbol Outline_CartographicLine;
            public StructMarkerLineSymbol Outline_MarkerLine;
            public StructHashLineSymbol Outline_HashLine;
            public StructPictureLineSymbol Outline_PictureLine;
            public StructMultilayerLineSymbol Outline_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructStackedChartSymbol
        {
            public bool Fixed;
            public bool UseOutline;
            public bool VerticalBar;
            public double Width;
            public LineStructs kindOfOutlineStruct;
            public StructSimpleLineSymbol Outline_SimpleLine;
            public StructCartographicLineSymbol Outline_CartographicLine;
            public StructMarkerLineSymbol Outline_MarkerLine;
            public StructHashLineSymbol Outline_HashLine;
            public StructPictureLineSymbol Outline_PictureLine;
            public StructMultilayerLineSymbol Outline_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructMultilayerFillSymbol
        {
            public ArrayList MultiFillLayers;
            public int LayerCount;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructDotDensityFillSymbol
        {
            public string BackgroundColor;
            public byte BackgroundTransparency;
            public string Color;
            public byte Transparency;
            //public int DotCount;
            //public double DotSize;
            public double DotSpacing;
            public bool FixedPlacement;
            public ArrayList SymbolList;
            public int SymbolCount;
            public LineStructs kindOfOutlineStruct;
            public StructSimpleLineSymbol Outline_SimpleLine;
            public StructCartographicLineSymbol Outline_CartographicLine;
            public StructMarkerLineSymbol Outline_MarkerLine;
            public StructHashLineSymbol Outline_HashLine;
            public StructPictureLineSymbol Outline_PictureLine;
            public StructMultilayerLineSymbol Outline_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructPictureFillSymbol
        {
            public double Angle;
            public string BackgroundColor;
            public byte BackgroundTransparency;
            public string Color;
            public byte Transparency;
            public stdole.IPictureDisp Picture;
            public double XScale;
            public double YScale;
            public LineStructs kindOfOutlineStruct;
            public StructSimpleLineSymbol Outline_SimpleLine;
            public StructCartographicLineSymbol Outline_CartographicLine;
            public StructMarkerLineSymbol Outline_MarkerLine;
            public StructHashLineSymbol Outline_HashLine;
            public StructPictureLineSymbol Outline_PictureLine;
            public StructMultilayerLineSymbol Outline_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructGradientFillSymbol
        {
            public string Color;
            public byte Transparency;
            public ArrayList Colors;
            public double GradientAngle;
            public double GradientPercentage;
            public int IntervallCount;
            public string Style;
            public LineStructs kindOfOutlineStruct;
            public StructSimpleLineSymbol Outline_SimpleLine;
            public StructCartographicLineSymbol Outline_CartographicLine;
            public StructMarkerLineSymbol Outline_MarkerLine;
            public StructHashLineSymbol Outline_HashLine;
            public StructPictureLineSymbol Outline_PictureLine;
            public StructMultilayerLineSymbol Outline_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructLineFillSymbol
        {
            public double Angle;
            public string Color;
            public byte Transparency;
            public double Offset;
            public double Separation;
            public LineStructs kindOfLineStruct;
            public StructSimpleLineSymbol LineSymbol_SimpleLine;
            public StructCartographicLineSymbol LineSymbol_CartographicLine;
            public StructMarkerLineSymbol LineSymbol_MarkerLine;
            public StructHashLineSymbol LineSymbol_HashLine;
            public StructPictureLineSymbol LineSymbol_PictureLine;
            public StructMultilayerLineSymbol LineSymbol_MultiLayerLines;
            public LineStructs kindOfOutlineStruct;
            public StructSimpleLineSymbol Outline_SimpleLine;
            public StructCartographicLineSymbol Outline_CartographicLine;
            public StructMarkerLineSymbol Outline_MarkerLine;
            public StructHashLineSymbol Outline_HashLine;
            public StructPictureLineSymbol Outline_PictureLine;
            public StructMultilayerLineSymbol Outline_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructMarkerFillSymbol
        {
            public string Color;
            public byte Transparency;
            public double GridAngle;
            public MarkerStructs kindOfMarkerStruct;
            public StructSimpleMarkerSymbol MarkerSymbol_SimpleMarker;
            public StructCharacterMarkerSymbol MarkerSymbol_CharacterMarker;
            public StructPictureMarkerSymbol MarkerSymbol_PictureMarker;
            public StructArrowMarkerSymbol MarkerSymbol_ArrowMarker;
            public StructMultilayerMarkerSymbol MarkerSymbol_MultilayerMarker;
            public LineStructs kindOfOutlineStruct;
            public StructSimpleLineSymbol Outline_SimpleLine;
            public StructCartographicLineSymbol Outline_CartographicLine;
            public StructMarkerLineSymbol Outline_MarkerLine;
            public StructHashLineSymbol Outline_HashLine;
            public StructPictureLineSymbol Outline_PictureLine;
            public StructMultilayerLineSymbol Outline_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructUniqueValueRenderer
        {
            public Feature FeatureCls;
            public string LayerName;
            public string DatasetName;
            public int ValueCount;
            public ArrayList SymbolList;
            public int FieldCount;
            public ArrayList FieldNames;
            public string StylePath;
            public StructAnnotation Annotation;
        }
        internal enum Feature
        {
            PointFeature,
            LineFeature,
            PolygonFeature,
        }

        internal struct StructAnnotation
        {
            public bool IsSingleProperty;
            public string PropertyName;
            public StructTextSymbol TextSymbol;
            public double MaxScale;
            public double MinScale;
        }

        internal struct StructTextSymbol
        {
            public double Angle;
            public string Color;
            public string Font;
            public string Style;
            public string Weight;
            public string HorizontalAlignment;
            public bool RightToLeft;
            public double Size;
            public string Text;
            public string VerticalAlignment;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructSimpleMarkerSymbol
        {
            public double Angle;
            public bool Filled;
            public string Color;
            public bool Outline;
            public string OutlineColor;
            public double OutlineSize;
            public double Size;
            public string Style;
            public double XOffset;
            public double YOffset;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructCharacterMarkerSymbol
        {
            public double Angle;
            public int CharacterIndex;
            public string Color;
            public string Font;
            public double Size;
            public double XOffset;
            public double YOffset;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructPictureMarkerSymbol
        {
            public double Angle;
            public string BackgroundColor;
            public string Color;
            public stdole.IPicture Picture;
            public double Size;
            public double XOffset;
            public double YOffset;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructArrowMarkerSymbol
        {
            public double Angle;
            public string Color;
            public double Length;
            public double Size;
            public string Style;
            public double Width;
            public double XOffset;
            public double YOffset;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }


        internal struct StructMultilayerMarkerSymbol
        {
            public ArrayList MultiMarkerLayers;
            public int LayerCount;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructCartographicLineSymbol
        {
            public string Color;
            public byte Transparency;
            public double Width;
            public string Join;
            public double MiterLimit;
            public string Cap;
            public ArrayList DashArray;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }


        internal struct StructHashLineSymbol
        {
            public double Angle;
            public string Color;
            public byte Transparency;
            public double Width;
            public LineStructs kindOfLineStruct;
            public StructSimpleLineSymbol HashSymbol_SimpleLine;
            public StructCartographicLineSymbol HashSymbol_CartographicLine;
            public StructMarkerLineSymbol HashSymbol_MarkerLine;
            public StructPictureLineSymbol HashSymbol_PictureLine;
            public StructMultilayerLineSymbol HashSymbol_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal enum LineStructs
        {
            StructSimpleLineSymbol,
            StructMarkerLineSymbol,
            StructHashLineSymbol,
            StructPictureLineSymbol,
            StructMultilayerLineSymbol,
            StructCartographicLineSymbol,
        }


        internal struct StructSimpleLineSymbol
        {
            public string Color;
            public byte Transparency;
            public string publicStyle;
            public double Width;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructMarkerLineSymbol
        {
            public string Color;
            public byte Transparency;
            public double Width;
            public MarkerStructs kindOfMarkerStruct;
            public StructSimpleMarkerSymbol MarkerSymbol_SimpleMarker;
            public StructCharacterMarkerSymbol MarkerSymbol_CharacterMarker;
            public StructPictureMarkerSymbol MarkerSymbol_PictureMarker;
            public StructArrowMarkerSymbol MarkerSymbol_ArrowMarker;
            public StructMultilayerMarkerSymbol MarkerSymbol_MultilayerMarker;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructPictureLineSymbol
        {
            public string BackgroundColor;
            public byte BackgroundTransparency;
            public string Color;
            public byte Transparency;
            public double Offset;
            public stdole.IPicture Picture;
            public bool Rotate;
            public double Width;
            public double XScale;
            public double YScale;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal struct StructMultilayerLineSymbol
        {
            public ArrayList MultiLineLayers;
            public int LayerCount;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        internal enum MarkerStructs
        {
            StructSimpleMarkerSymbol,
            StructCharacterMarkerSymbol,
            StructPictureMarkerSymbol,
            StructArrowMarkerSymbol,
            StructMultilayerMarkerSymbol,
        }

        internal struct StructSimpleFillSymbol
        {
            public string Color;
            public string Style;
            public byte Transparency;
            public LineStructs kindOfOutlineStruct;
            public StructSimpleLineSymbol Outline_SimpleLine;
            public StructCartographicLineSymbol Outline_CartographicLine;
            public StructMarkerLineSymbol Outline_MarkerLine;
            public StructHashLineSymbol Outline_HashLine;
            public StructPictureLineSymbol Outline_PictureLine;
            public StructMultilayerLineSymbol Outline_MultiLayerLines;
            public string Label;
            public ArrayList Fieldvalues;
            public double UpperLimit;
            public double LowerLimit;
            public bool IsDefault;
        }

        private bool gimmeUniqeValuesForFieldname(ITable Table, string FieldName)
        {
            ArrayList arrayList = new ArrayList();
            bool flag;
            try
            {
                IDataset idataset = (IDataset)Table;
                IQueryDef queryDef = ((IFeatureWorkspace)idataset.Workspace).CreateQueryDef();
                queryDef.Tables = idataset.Name;
                queryDef.SubFields = "DISTINCT(" + FieldName + ")";
                ICursor icursor = queryDef.Evaluate();
                for (IRow irow = icursor.NextRow(); irow != null; irow = icursor.NextRow())
                    arrayList.Add(RuntimeHelpers.GetObjectValue(irow.get_Value(0)));
                this.m_alClassifiedFields.Add((object)arrayList);
                flag = true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
            return flag;
        }

        private bool gimmeUniqeValuesForFieldname(ITable Table, string FieldName, ArrayList JoinedTables)
        {
            ArrayList arrayList = new ArrayList();
            bool flag;
            try
            {
                string str = String.Empty;
                foreach (object joinedTable in JoinedTables)
                {
                    str = Convert.ToString(joinedTable);
                    str = "," + str;
                }
                IDataset idataset = (IDataset)Table;
                IQueryDef queryDef = ((IFeatureWorkspace)idataset.Workspace).CreateQueryDef();
                queryDef.Tables = idataset.Name + str;
                queryDef.SubFields = "DISTINCT(" + FieldName + ")";
                ICursor icursor = queryDef.Evaluate();
                for (IRow irow = icursor.NextRow(); irow != null; irow = icursor.NextRow())
                    arrayList.Add(RuntimeHelpers.GetObjectValue(irow.get_Value(0)));
                this.m_alClassifiedFields.Add((object)arrayList);
                flag = true;
            }
            catch (Exception ex)
            {
                throw ex;
            }
            return flag;
        }

        internal void saveLayerBreaktoTempPath(StructClassBreaksRenderer strutturaLayer, string nomeFileStyle)
        {
            bool flag1 = false;
            bool flag2 = false;
            string tempSLDfileName = String.Format("{0}.sld", nomeFileStyle);

            if (!Directory.Exists(Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "URBAMID", "SLD")))
                Directory.CreateDirectory(Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "URBAMID", "SLD"));


            string tempSLDpath = System.IO.Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "URBAMID", "SLD", tempSLDfileName);
            nomeFileStyle = nomeFileStyle.Replace(" ", "_");
            try
            {
                this.m_objXMLHandle = new XMLHandle(tempSLDpath);
                this.m_objXMLHandle.CreateNewFile(true);

                int num1 = 0;
                int num2 = checked(strutturaLayer.BreakCount - 1);
                int index1 = num1;
                while (index1 <= num2)
                {
                    string str1 = NewLateBinding.LateGet(strutturaLayer.SymbolList[index1], (Type)null, "LayerName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString();

                    this.m_objXMLHandle.CreateElement("NamedLayer");
                    this.m_objXMLHandle.CreateElement("LayerName");
                    String valElemento = NewLateBinding.LateGet(strutturaLayer.SymbolList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString();
                    this.m_objXMLHandle.SetElementText(valElemento);
                    this.m_objXMLHandle.CreateElement("UserStyle");
                    this.m_objXMLHandle.CreateElement("StyleName");
                    //this.m_objXMLHandle.SetElementText("Style1");
                    this.m_objXMLHandle.SetElementText(nomeFileStyle);
                    this.m_objXMLHandle.CreateElement("FeatureTypeStyle");
                    this.m_objXMLHandle.CreateElement("FeatureTypeName");
                    this.m_objXMLHandle.SetElementText(nomeFileStyle);
                    //this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.SymbolList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                    ArrayList arrayList1 = (ArrayList)NewLateBinding.LateGet(strutturaLayer.SymbolList[index1], (Type)null, "SymbolList", new object[0], (string[])null, (Type[])null, (bool[])null);
                    int num3 = 0;
                    int num4 = checked(arrayList1.Count - 1);
                    int index2 = num3;
                    while (index2 <= num4)
                    {
                        //int num5;
                        //int num6;

                        if (strutturaLayer.SymbolList[index1] is StructUniqueValueRenderer)
                        {
                            object layer = strutturaLayer.SymbolList[index1];
                            StructUniqueValueRenderer uniqueValueRenderer1 = new StructUniqueValueRenderer();
                            StructUniqueValueRenderer uniqueValueRenderer2 = layer != null ? (StructUniqueValueRenderer)layer : uniqueValueRenderer1;
                            this.m_objXMLHandle.CreateElement("Rule");
                            this.m_objXMLHandle.CreateElement("RuleName");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            this.m_objXMLHandle.CreateElement("Title");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            this.m_objXMLHandle.CreateElement("Filter");

                            ArrayList arrayList2 = (ArrayList)NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Fieldvalues", new object[0], (string[])null, (Type[])null, (bool[])null);
                            if (uniqueValueRenderer2.FieldCount > 1)
                            {
                                this.m_objXMLHandle.CreateElement("And");
                                int num7 = 0;
                                int num8 = checked(uniqueValueRenderer2.FieldCount - 1);
                                int index3 = num7;
                                while (index3 <= num8)
                                {
                                    XMLHandle objXmlHandle = this.m_objXMLHandle;
                                    objXmlHandle.CreateElement("PropertyIsEqualTo");
                                    objXmlHandle.CreateElement("PropertyName");
                                    objXmlHandle.SetElementText(uniqueValueRenderer2.FieldNames[index3].ToString());
                                    objXmlHandle.CreateElement("Fieldvalue");
                                    objXmlHandle.SetElementText(arrayList2[index3].ToString());
                                    checked { ++index3; }
                                }
                            }
                            else if (uniqueValueRenderer2.FieldCount == 1)
                            {
                                if (arrayList2.Count > 1)
                                    this.m_objXMLHandle.CreateElement("Or");
                                int num7 = 0;
                                int num8 = checked(arrayList2.Count - 1);
                                int index3 = num7;
                                while (index3 <= num8)
                                {
                                    XMLHandle objXmlHandle = this.m_objXMLHandle;
                                    objXmlHandle.CreateElement("PropertyIsEqualTo");
                                    objXmlHandle.CreateElement("PropertyName");
                                    objXmlHandle.SetElementText(uniqueValueRenderer2.FieldNames[0].ToString());
                                    objXmlHandle.CreateElement("Fieldvalue");
                                    objXmlHandle.SetElementText(arrayList2[index3].ToString());
                                    checked { ++index3; }
                                }
                            }
                            switch (uniqueValueRenderer2.FeatureCls)
                            {
                                case Feature.PointFeature:
                                    writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.LineFeature:
                                    writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.PolygonFeature:
                                    writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                            }
                        }
                        else if (strutturaLayer.SymbolList[index1] is StructClassBreaksRenderer)
                        {
                            object layer = strutturaLayer.SymbolList[index1];
                            StructClassBreaksRenderer classBreaksRenderer1 = new ConvertLayerToSld.StructClassBreaksRenderer();
                            StructClassBreaksRenderer classBreaksRenderer2 = layer != null ? (StructClassBreaksRenderer)layer : classBreaksRenderer1;
                            this.m_objXMLHandle.CreateElement("Rule");
                            this.m_objXMLHandle.CreateElement("RuleName");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            this.m_objXMLHandle.CreateElement("Title");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            this.m_objXMLHandle.CreateElement("Filter");

                            this.m_objXMLHandle.CreateElement("PropertyIsBetween");
                            this.m_objXMLHandle.CreateElement("PropertyName");
                            this.m_objXMLHandle.SetElementText(classBreaksRenderer2.FieldName);
                            this.m_objXMLHandle.CreateElement("LowerBoundary");
                            this.m_objXMLHandle.CreateElement("Fieldvalue");
                            this.m_objXMLHandle.SetElementText(this.CommaToPoint(Conversions.ToDouble(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "LowerLimit", new object[0], (string[])null, (Type[])null, (bool[])null))));
                            this.m_objXMLHandle.CreateElement("UpperBoundary");
                            this.m_objXMLHandle.CreateElement("Fieldvalue");
                            this.m_objXMLHandle.SetElementText(this.CommaToPoint(Conversions.ToDouble(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "UpperLimit", new object[0], (string[])null, (Type[])null, (bool[])null))));
                            switch (classBreaksRenderer2.FeatureCls)
                            {
                                case Feature.PointFeature:
                                    writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.LineFeature:
                                    writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.PolygonFeature:
                                    writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                            }
                        }
                        else if (strutturaLayer.SymbolList[index1] is StructSimpleRenderer)
                        {
                            object layer = strutturaLayer.SymbolList[index1];
                            StructSimpleRenderer structSimpleRenderer1 = new ConvertLayerToSld.StructSimpleRenderer();
                            StructSimpleRenderer structSimpleRenderer2 = layer != null ? (StructSimpleRenderer)layer : structSimpleRenderer1;
                            this.m_objXMLHandle.CreateElement("Rule");
                            this.m_objXMLHandle.CreateElement("RuleName");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.SymbolList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            this.m_objXMLHandle.CreateElement("Title");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.SymbolList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            switch (structSimpleRenderer2.FeatureCls)
                            {
                                case Feature.PointFeature:
                                    writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.LineFeature:
                                    writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.PolygonFeature:
                                    writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                            }
                            object obj = NewLateBinding.LateGet(strutturaLayer.SymbolList[index1], (Type)null, "Annotation", new object[0], (string[])null, (Type[])null, (bool[])null);
                            StructAnnotation structAnnotation = new StructAnnotation();
                            writeAnnotation(obj != null ? (StructAnnotation)obj : structAnnotation);
                        }
                        checked { ++index2; }
                    }
                    if (!flag1)
                        this.m_objXMLHandle.SaveDoc();
                    checked { ++index1; }
                }
                if (flag1)
                    this.m_objXMLHandle.SaveDoc();
                flag2 = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Konnte die SLD nicht schreiben", exception.Message, exception.StackTrace, nameof(WriteToSLD));
                flag2 = false;
                ProjectData.ClearProjectError();
            }
        }

        internal StructProject getStrutturaPoint(IGeoFeatureLayer geoFeatureLayer, IFeatureLayer featureLayer, bool isLayerDb)
        {
            try
            {
                ISimpleRenderer unique = (ISimpleRenderer)geoFeatureLayer.Renderer;
                strutturaLayer.LayerList = new ArrayList();
                strutturaLayer.LayerList.Add((object)StoreStructPointRenderer((ISimpleRenderer)geoFeatureLayer.Renderer, featureLayer, isLayerDb));
                addOneToLayerNumber();

                return strutturaLayer;

            }
            catch (Exception ex)
            {

                throw ex;
            }
        }

        private StructSimpleRenderer StoreStructPointRenderer(ISimpleRenderer Renderer, IFeatureLayer Layer, bool isLayerDb)
        {
            StructSimpleRenderer structSimpleRenderer1 = new StructSimpleRenderer();
            IDataset featureClass = (IDataset)Layer.FeatureClass;
            structSimpleRenderer1.SymbolList = new ArrayList();
            StructSimpleRenderer structSimpleRenderer2 = new StructSimpleRenderer();
            try
            {
                structSimpleRenderer1.LayerName = Layer.Name;
                structSimpleRenderer1.DatasetName = featureClass.Name;
                structSimpleRenderer1.Annotation = getAnnotation(Layer, isLayerDb);
                ISymbol symbol = Renderer.Symbol;
                if (symbol is ITextSymbol)
                {
                    StructTextSymbol structTextSymbol = storeText((ITextSymbol)symbol);
                    structTextSymbol.Label = Renderer.Label;
                    structSimpleRenderer1.SymbolList.Add((object)structTextSymbol);
                }
                if (symbol is IMarkerSymbol)
                {
                    structSimpleRenderer1.FeatureCls = Feature.PointFeature;
                    IMarkerSymbol Symbol = (IMarkerSymbol)symbol;
                    string Left = markerSymbolScan(Symbol);
                    if (Operators.CompareString(Left, "ISimpleMarkerSymbol", false) == 0)
                    {
                        StructSimpleMarkerSymbol simpleMarkerSymbol = storeSimpleMarker((ISimpleMarkerSymbol)Symbol);
                        simpleMarkerSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)simpleMarkerSymbol);
                    }
                    else if (Operators.CompareString(Left, "ICharacterMarkerSymbol", false) == 0)
                    {
                        StructCharacterMarkerSymbol characterMarkerSymbol = storeCharacterMarker((ICharacterMarkerSymbol)Symbol);
                        characterMarkerSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)characterMarkerSymbol);
                    }
                    else if (Operators.CompareString(Left, "IPictureMarkerSymbol", false) == 0)
                    {
                        StructPictureMarkerSymbol pictureMarkerSymbol = storePictureMarker((IPictureMarkerSymbol)Symbol);
                        pictureMarkerSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)pictureMarkerSymbol);
                    }
                    else if (Operators.CompareString(Left, "IArrowMarkerSymbol", false) == 0)
                    {
                        StructArrowMarkerSymbol arrowMarkerSymbol = storeArrowMarker((IArrowMarkerSymbol)Symbol);
                        arrowMarkerSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)arrowMarkerSymbol);
                    }
                    else if (Operators.CompareString(Left, "IMultiLayerMarkerSymbol", false) == 0)
                    {
                        StructMultilayerMarkerSymbol multilayerMarkerSymbol = storeMultiLayerMarker((IMultiLayerMarkerSymbol)Symbol);
                        multilayerMarkerSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)multilayerMarkerSymbol);
                    }
                    else if (Operators.CompareString(Left, "false", false) == 0)
                    {
                        //SIMBOLO NON RICONOSCIUTO
                    }

                }
                if (symbol is ILineSymbol)
                {
                    structSimpleRenderer1.FeatureCls = Feature.LineFeature;
                    ILineSymbol Symbol = (ILineSymbol)symbol;
                    string Left = lineSymbolScan(Symbol);
                    if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
                    {
                        StructCartographicLineSymbol cartographicLineSymbol = storeCartographicLine((ICartographicLineSymbol)Symbol);
                        cartographicLineSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)cartographicLineSymbol);
                    }
                    else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
                    {
                        StructHashLineSymbol structHashLineSymbol = storeHashLine((IHashLineSymbol)Symbol);
                        structHashLineSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)structHashLineSymbol);
                    }
                    else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
                    {
                        StructMarkerLineSymbol markerLineSymbol = storeMarkerLine((IMarkerLineSymbol)Symbol);
                        markerLineSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)markerLineSymbol);
                    }
                    else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
                    {
                        StructSimpleLineSymbol simpleLineSymbol = storeSimpleLine((ISimpleLineSymbol)Symbol);
                        simpleLineSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)simpleLineSymbol);
                    }
                    else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
                    {
                        StructPictureLineSymbol pictureLineSymbol = storePictureLine((IPictureLineSymbol)Symbol);
                        pictureLineSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)pictureLineSymbol);
                    }
                    else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
                    {
                        StructMultilayerLineSymbol multilayerLineSymbol = storeMultilayerLines((IMultiLayerLineSymbol)Symbol);
                        multilayerLineSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)multilayerLineSymbol);
                    }
                    else if (Operators.CompareString(Left, "false", false) == 0)
                    {
                        //SIMBOLO NON TROVATO
                    }
                }
                if (symbol is IFillSymbol)
                {
                    structSimpleRenderer1.FeatureCls = Feature.PolygonFeature;
                    IFillSymbol Symbol = (IFillSymbol)symbol;
                    string Left = this.fillSymbolScan(Symbol);
                    if (Operators.CompareString(Left, "ISimpleFillSymbol", false) == 0)
                    {
                        StructSimpleFillSymbol simpleFillSymbol = storeSimpleFill((ISimpleFillSymbol)Symbol);
                        simpleFillSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)simpleFillSymbol);
                    }
                    else if (Operators.CompareString(Left, "IMarkerFillSymbol", false) == 0)
                    {
                        StructMarkerFillSymbol markerFillSymbol = this.storeMarkerFill((IMarkerFillSymbol)Symbol);
                        markerFillSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)markerFillSymbol);
                    }
                    else if (Operators.CompareString(Left, "ILineFillSymbol", false) == 0)
                    {
                        StructLineFillSymbol structLineFillSymbol = this.storeLineFill((ILineFillSymbol)Symbol);
                        structLineFillSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)structLineFillSymbol);
                    }
                    else if (Operators.CompareString(Left, "IDotDensityFillSymbol", false) == 0)
                    {
                        StructDotDensityFillSymbol densityFillSymbol = this.storeDotDensityFill((IDotDensityFillSymbol)Symbol);
                        densityFillSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)densityFillSymbol);
                    }
                    else if (Operators.CompareString(Left, "IPictureFillSymbol", false) == 0)
                    {
                        StructPictureFillSymbol pictureFillSymbol = this.storePictureFill((IPictureFillSymbol)Symbol);
                        pictureFillSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)pictureFillSymbol);
                    }
                    else if (Operators.CompareString(Left, "IGradientFillSymbol", false) == 0)
                    {
                        StructGradientFillSymbol gradientFillSymbol = this.storeGradientFill((IGradientFillSymbol)Symbol);
                        gradientFillSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)gradientFillSymbol);
                    }
                    else if (Operators.CompareString(Left, "IMultiLayerFillSymbol", false) == 0)
                    {
                        StructMultilayerFillSymbol multilayerFillSymbol = this.storeMultiLayerFill((IMultiLayerFillSymbol)Symbol);
                        multilayerFillSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)multilayerFillSymbol);
                    }
                    else if (Operators.CompareString(Left, "false", false) == 0)
                    {
                        //SIMBOLO NON TROVATO
                    }
                }
                if (symbol is I3DChartSymbol)
                {
                    I3DChartSymbol Symbol = (I3DChartSymbol)symbol;
                    string Left = this.IIIDChartSymbolScan(Symbol);
                    if (Operators.CompareString(Left, "IBarChartSymbol", false) == 0)
                    {
                        StructBarChartSymbol structBarChartSymbol = this.storeBarChart((IBarChartSymbol)Symbol);
                        structBarChartSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)structBarChartSymbol);
                    }
                    else if (Operators.CompareString(Left, "IPieChartSymbol", false) == 0)
                    {
                        StructPieChartSymbol structPieChartSymbol = this.storePieChart((IPieChartSymbol)Symbol);
                        structPieChartSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)structPieChartSymbol);
                    }
                    else if (Operators.CompareString(Left, "IStackedChartSymbol", false) == 0)
                    {
                        StructStackedChartSymbol stackedChartSymbol = this.storeStackedChart((IStackedChartSymbol)Symbol);
                        stackedChartSymbol.Label = Renderer.Label;
                        structSimpleRenderer1.SymbolList.Add((object)stackedChartSymbol);
                    }
                    else if (Operators.CompareString(Left, "false", false) == 0)
                    {
                        //Simbolo non trovato
                    }
                }
                structSimpleRenderer2 = structSimpleRenderer1;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Speichern der Symbole in den Layerstrukturen", exception.Message, exception.StackTrace, nameof(StoreStructSimpleRenderer));
                ProjectData.ClearProjectError();
            }
            return structSimpleRenderer2;
        }

        internal void saveLayertoTempPath(StructProject strutturaLayer, string nomeFileStyle)
        {
            bool flag1 = false;
            bool flag2 = false;
            string tempSLDfileName = String.Format("{0}.sld", nomeFileStyle);
           // string tempSLDpath = System.IO.Path.Combine(System.IO.Path.GetTempPath(), tempSLDfileName);
           if (!Directory.Exists(Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "URBAMID", "SLD")))
               Directory.CreateDirectory(Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "URBAMID", "SLD"));


            string tempSLDpath = Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "URBAMID",  "SLD", tempSLDfileName);
                

            try
            {
                if (File.Exists(tempSLDpath))
                    File.Delete(tempSLDpath);


                this.m_objXMLHandle = new XMLHandle(tempSLDpath);
                this.m_objXMLHandle.CreateNewFile(true);

                int num1 = 0;
                int num2 = checked(strutturaLayer.LayerList.Count - 1);// checked(strutturaLayer.LayerCount - 1);
                int index1 = num1;
                while (index1 <= num2)
                {
                    string str1 = NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "LayerName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString();

                    this.m_objXMLHandle.CreateElement("NamedLayer");
                    this.m_objXMLHandle.CreateElement("LayerName");
                    String valElemento = NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString();
                    this.m_objXMLHandle.SetElementText(valElemento);
                    this.m_objXMLHandle.CreateElement("UserStyle");
                    this.m_objXMLHandle.CreateElement("StyleName");
                    //this.m_objXMLHandle.SetElementText("Style1");
                    this.m_objXMLHandle.SetElementText(nomeFileStyle);
                    this.m_objXMLHandle.CreateElement("FeatureTypeStyle");
                    this.m_objXMLHandle.CreateElement("FeatureTypeName");
                    // this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                    this.m_objXMLHandle.SetElementText(nomeFileStyle);
                    ArrayList arrayList1 = (ArrayList)NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "SymbolList", new object[0], (string[])null, (Type[])null, (bool[])null);
                    int num3 = 0;
                    int num4 = checked(arrayList1.Count - 1);
                    int index2 = num3;
                    while (index2 <= num4)
                    {
                        if (strutturaLayer.LayerList[index1] is StructUniqueValueRenderer)
                        {
                            object layer = strutturaLayer.LayerList[index1];
                            StructUniqueValueRenderer uniqueValueRenderer1 = new StructUniqueValueRenderer();
                            StructUniqueValueRenderer uniqueValueRenderer2 = layer != null ? (StructUniqueValueRenderer)layer : uniqueValueRenderer1;
                            this.m_objXMLHandle.CreateElement("Rule");
                            this.m_objXMLHandle.CreateElement("RuleName");
                            String nome = NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString();
                            this.m_objXMLHandle.SetElementText(HttpUtility.HtmlEncode(nome));
                            this.m_objXMLHandle.CreateElement("Title");
                            String titolo = NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString();
                            //this.m_objXMLHandle.SetElementText(HttpUtility.HtmlEncode(titolo));
                            this.m_objXMLHandle.SetElementText(titolo);
                            ArrayList arrayList2 = (ArrayList)NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Fieldvalues", new object[0], (string[])null, (Type[])null, (bool[])null);
                            bool isDefault =(Boolean)NewLateBinding.LateGet(arrayList1[index2], (Type)null, "IsDefault", new object[0], (string[])null, (Type[])null, (bool[])null);

                            if (!isDefault)

                            {

                                this.m_objXMLHandle.CreateElement("Filter");

                             
                                if (uniqueValueRenderer2.FieldCount > 1)
                                {
                                    this.m_objXMLHandle.CreateElement("And");
                                    int num7 = 0;
                                    int num8 = checked(uniqueValueRenderer2.FieldCount - 1);
                                    int index3 = num7;
                                    while (index3 <= num8)
                                    {
                                        XMLHandle objXmlHandle = this.m_objXMLHandle;
                                        objXmlHandle.CreateElement("PropertyIsEqualTo");
                                        objXmlHandle.CreateElement("PropertyName");
                                        objXmlHandle.SetElementText(uniqueValueRenderer2.FieldNames[index3].ToString());
                                        objXmlHandle.CreateElement("Fieldvalue");
                                        if(arrayList2.Count > index3)
                                            objXmlHandle.SetElementText(arrayList2[index3].ToString());
                                        checked { ++index3; }
                                    }
                                }
                                else if (uniqueValueRenderer2.FieldCount == 1)
                                {
                                    if (arrayList2.Count > 1)
                                        this.m_objXMLHandle.CreateElement("Or");
                                    int num7 = 0;
                                    int num8 = checked(arrayList2.Count - 1);
                                    int index3 = num7;
                                    while (index3 <= num8)
                                    {
                                        XMLHandle objXmlHandle = this.m_objXMLHandle;
                                        objXmlHandle.CreateElement("PropertyIsEqualTo");
                                        objXmlHandle.CreateElement("PropertyName");
                                        objXmlHandle.SetElementText(uniqueValueRenderer2.FieldNames[0].ToString());
                                        objXmlHandle.CreateElement("Fieldvalue");
                                        objXmlHandle.SetElementText(arrayList2[index3].ToString());
                                        checked { ++index3; }
                                    }
                                }
                            }
                            switch (uniqueValueRenderer2.FeatureCls)
                            {
                                case Feature.PointFeature:
                                    writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.LineFeature:
                                    writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.PolygonFeature:
                                    writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                            }
                            
                            //STAMPO LE LABEL SUI POLIGONI
                            object obj = NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "Annotation", new object[0], (string[])null, (Type[])null, (bool[])null);
                            if (obj != null)
                            {
                                StructAnnotation struttura = (StructAnnotation)obj;
                                if(struttura.MinScale == 0 && struttura.MaxScale == 0)
                                    writeAnnotation(struttura);
                                else
                                {
                                    //devo riscrivere un nuovo rule con la sola proprietà TEXT 
                                    this.m_objXMLHandle.CreateElement("Rule");
                                    this.m_objXMLHandle.CreateElement("RuleName");
                                    this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                                    this.m_objXMLHandle.CreateElement("Title");
                                    this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                                    this.m_objXMLHandle.CreateElement("Filter");

                                    if (uniqueValueRenderer2.FieldCount > 1)
                                    {
                                        this.m_objXMLHandle.CreateElement("And");
                                        int num7 = 0;
                                        int num8 = checked(uniqueValueRenderer2.FieldCount - 1);
                                        int index3 = num7;
                                        while (index3 <= num8)
                                        {
                                            XMLHandle objXmlHandle = this.m_objXMLHandle;
                                            objXmlHandle.CreateElement("PropertyIsEqualTo");
                                            objXmlHandle.CreateElement("PropertyName");
                                            objXmlHandle.SetElementText(uniqueValueRenderer2.FieldNames[index3].ToString());
                                            objXmlHandle.CreateElement("Fieldvalue");
                                            objXmlHandle.SetElementText(arrayList2[index3].ToString());
                                            checked { ++index3; }
                                        }
                                    }
                                    else if (uniqueValueRenderer2.FieldCount == 1)
                                    {
                                        if (arrayList2.Count > 1)
                                            this.m_objXMLHandle.CreateElement("Or");
                                        int num7 = 0;
                                        int num8 = checked(arrayList2.Count - 1);
                                        int index3 = num7;
                                        while (index3 <= num8)
                                        {
                                            XMLHandle objXmlHandle = this.m_objXMLHandle;
                                            objXmlHandle.CreateElement("PropertyIsEqualTo");
                                            objXmlHandle.CreateElement("PropertyName");
                                            objXmlHandle.SetElementText(uniqueValueRenderer2.FieldNames[0].ToString());
                                            objXmlHandle.CreateElement("Fieldvalue");
                                            objXmlHandle.SetElementText(arrayList2[index3].ToString());
                                            checked { ++index3; }
                                        }
                                    }

                                    if (struttura.MinScale > 0)
                                    {
                                        XMLHandle objXmlHandle = this.m_objXMLHandle;
                                        objXmlHandle.CreateElement("MaxScale");
                                        objXmlHandle.SetElementText(struttura.MinScale.ToString());
                                    }
                                    if (struttura.MaxScale > 0)
                                    {
                                        XMLHandle objXmlHandle = this.m_objXMLHandle;
                                        objXmlHandle.CreateElement("MinScale");
                                        objXmlHandle.SetElementText(struttura.MinScale.ToString());

                                    }
                                    writeAnnotation(struttura);
                                }
                                    
                            }
                            //END STAMPA LABEL
                        }
                        else if (strutturaLayer.LayerList[index1] is StructClassBreaksRenderer)
                        {
                            object layer = strutturaLayer.LayerList[index1];
                            StructClassBreaksRenderer classBreaksRenderer1 = new ConvertLayerToSld.StructClassBreaksRenderer();
                            StructClassBreaksRenderer classBreaksRenderer2 = layer != null ? (StructClassBreaksRenderer)layer : classBreaksRenderer1;
                            this.m_objXMLHandle.CreateElement("Rule");
                            this.m_objXMLHandle.CreateElement("RuleName");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            this.m_objXMLHandle.CreateElement("Title");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            this.m_objXMLHandle.CreateElement("Filter");

                            this.m_objXMLHandle.CreateElement("PropertyIsBetween");
                            this.m_objXMLHandle.CreateElement("PropertyName");
                            this.m_objXMLHandle.SetElementText(classBreaksRenderer2.FieldName);
                            this.m_objXMLHandle.CreateElement("LowerBoundary");
                            this.m_objXMLHandle.CreateElement("Fieldvalue");
                            this.m_objXMLHandle.SetElementText(this.CommaToPoint(Conversions.ToDouble(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "LowerLimit", new object[0], (string[])null, (Type[])null, (bool[])null))));
                            this.m_objXMLHandle.CreateElement("UpperBoundary");
                            this.m_objXMLHandle.CreateElement("Fieldvalue");
                            this.m_objXMLHandle.SetElementText(this.CommaToPoint(Conversions.ToDouble(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "UpperLimit", new object[0], (string[])null, (Type[])null, (bool[])null))));
                            switch (classBreaksRenderer2.FeatureCls)
                            {
                                case Feature.PointFeature:
                                    writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.LineFeature:
                                    writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.PolygonFeature:
                                    writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                            }
                        }
                        else if (strutturaLayer.LayerList[index1] is StructSimpleRenderer)
                        {
                            object layer = strutturaLayer.LayerList[index1];
                            StructSimpleRenderer structSimpleRenderer1 = new ConvertLayerToSld.StructSimpleRenderer();
                            StructSimpleRenderer structSimpleRenderer2 = layer != null ? (StructSimpleRenderer)layer : structSimpleRenderer1;
                            this.m_objXMLHandle.CreateElement("Rule");
                            this.m_objXMLHandle.CreateElement("RuleName");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            this.m_objXMLHandle.CreateElement("Title");
                            this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
                            switch (structSimpleRenderer2.FeatureCls)
                            {
                                case Feature.PointFeature:
                                    writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.LineFeature:
                                    writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                                case Feature.PolygonFeature:
                                    writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
                                    break;
                            }
                            object obj = NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "Annotation", new object[0], (string[])null, (Type[])null, (bool[])null);
                            StructAnnotation structAnnotation = new StructAnnotation();
                            writeAnnotation(obj != null ? (StructAnnotation)obj : structAnnotation);
                        }
                        checked { ++index2; }
                    }
                    if (!flag1)
                        this.m_objXMLHandle.SaveDoc();
                    checked { ++index1; }
                }
                if (flag1)
                    this.m_objXMLHandle.SaveDoc();
                flag2 = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Konnte die SLD nicht schreiben", exception.Message, exception.StackTrace, nameof(WriteToSLD));
                flag2 = false;
                ProjectData.ClearProjectError();
            }
           
        }

        internal StructProject getStrutturaLayer(IGeoFeatureLayer geoFeatureLayer, IFeatureLayer featureLayer, bool isLayerDb)
        {

            try
            {
                IUniqueValueRenderer unique = (IUniqueValueRenderer)featureRenderer;
                strutturaLayer.LayerList = new ArrayList();
                strutturaLayer.LayerList.Add((object)StoreStructUVRenderer((IUniqueValueRenderer)geoFeatureLayer.Renderer, featureLayer, isLayerDb));
                addOneToLayerNumber();

                return strutturaLayer;

            }
            catch (Exception ex)
            {

                throw ex;
            }
        }

        private string checkDigits(string value)
        {
            string str = value;
            if (str.Length == 1)
                str = str.Insert(0, "0");
            return str;
        }

        private string gimmeStringForColor(IColor color)
        {
            string str;
            if (color.Transparency == (byte)0)
            {
                str = "";
            }
            else
            {

                IRgbColor irgbColor = (IRgbColor)new RgbColor();
                irgbColor.RGB = color.RGB;
                str = "#" + checkDigits(irgbColor.Red.ToString("X2"))
                    + checkDigits(irgbColor.Green.ToString("X2"))
                    + checkDigits(irgbColor.Blue.ToString("X2"));
            }
            return str;
        }

        private StructTextSymbol storeText(ITextSymbol symbol)
        {
            StructTextSymbol structTextSymbol = new StructTextSymbol();
            structTextSymbol.Angle = symbol.Angle;
            structTextSymbol.Color = gimmeStringForColor(symbol.Color);
            structTextSymbol.Font = symbol.Font.Name;
            structTextSymbol.Style = "normal";
            if (symbol.Font.Italic)
                structTextSymbol.Style = "italic";
            structTextSymbol.Weight = "normal";
            if (symbol.Font.Bold)
                structTextSymbol.Weight = "bold";
            structTextSymbol.HorizontalAlignment = Conversions.ToString((int)symbol.HorizontalAlignment);
            structTextSymbol.RightToLeft = symbol.RightToLeft;
            structTextSymbol.Size = symbol.Size;
            structTextSymbol.Text = symbol.Text;
            structTextSymbol.VerticalAlignment = Conversions.ToString((int)symbol.VerticalAlignment);
            return structTextSymbol;
        }

        private ArrayList gimmeSeperateFieldValues(string value, string FieldDelimiter)
        {
            ArrayList arrayList1 = new ArrayList();
            IQueryFilter iqueryFilter = (IQueryFilter)new QueryFilter();
            ArrayList arrayList2;
            try
            {
                switch (this.m_alClassifiedFields.Count)
                {
                    case 2:
                        ArrayList alClassifiedField1 = (ArrayList)this.m_alClassifiedFields[0];
                        ArrayList alClassifiedField2 = (ArrayList)this.m_alClassifiedFields[1];
                        int num1 = 0;
                        int num2 = checked(alClassifiedField1.Count - 1);
                        int index1 = num1;
                        while (index1 <= num2)
                        {
                            int num3 = 0;
                            int num4 = checked(alClassifiedField2.Count - 1);
                            int index2 = num3;
                            while (index2 <= num4)
                            {
                                if (Operators.CompareString(Conversions.ToString(alClassifiedField1[index1]) + FieldDelimiter + Conversions.ToString(alClassifiedField2[index2]), value, false) == 0)
                                {
                                    arrayList1.Add(RuntimeHelpers.GetObjectValue(alClassifiedField1[index1]));
                                    arrayList1.Add(RuntimeHelpers.GetObjectValue(alClassifiedField2[index2]));
                                    goto label_21;
                                }
                                else
                                    checked { ++index2; }
                            }
                            checked { ++index1; }
                        }
                        break;
                    case 3:
                        ArrayList alClassifiedField3 = (ArrayList)this.m_alClassifiedFields[0];
                        ArrayList alClassifiedField4 = (ArrayList)this.m_alClassifiedFields[1];
                        ArrayList alClassifiedField5 = (ArrayList)this.m_alClassifiedFields[2];
                        int num5 = 0;
                        int integer1 = Conversions.ToInteger(Operators.SubtractObject(NewLateBinding.LateGet(this.m_alClassifiedFields[0], (Type)null, "Count", new object[0], (string[])null, (Type[])null, (bool[])null), (object)1));
                        int index3 = num5;
                        while (index3 <= integer1)
                        {
                            int num3 = 0;
                            int integer2 = Conversions.ToInteger(Operators.SubtractObject(NewLateBinding.LateGet(this.m_alClassifiedFields[1], (Type)null, "Count", new object[0], (string[])null, (Type[])null, (bool[])null), (object)1));
                            int index2 = num3;
                            while (index2 <= integer2)
                            {
                                int num4 = 0;
                                int integer3 = Conversions.ToInteger(Operators.SubtractObject(NewLateBinding.LateGet(this.m_alClassifiedFields[2], (Type)null, "Count", new object[0], (string[])null, (Type[])null, (bool[])null), (object)1));
                                int index4 = num4;
                                while (index4 <= integer3)
                                {
                                    if (Operators.CompareString(Conversions.ToString(alClassifiedField3[index3]) + FieldDelimiter + Conversions.ToString(alClassifiedField4[index2]) + FieldDelimiter + Conversions.ToString(alClassifiedField5[index4]), value, false) == 0)
                                    {
                                        arrayList1.Add(RuntimeHelpers.GetObjectValue(alClassifiedField3[index3]));
                                        arrayList1.Add(RuntimeHelpers.GetObjectValue(alClassifiedField4[index2]));
                                        arrayList1.Add(RuntimeHelpers.GetObjectValue(alClassifiedField5[index4]));
                                        goto label_21;
                                    }
                                    else
                                        checked { ++index4; }
                                }
                                checked { ++index2; }
                            }
                            checked { ++index3; }
                        }
                        break;
                }
            label_21:
                arrayList2 = arrayList1;
            }
            catch (Exception ex)
            {
                throw ex;
            }
            return arrayList2;
        }

        private ArrayList getUVFieldValues(IUniqueValueRenderer Renderer, int Index)
        {
            int fieldCount = Renderer.FieldCount;
            ArrayList arrayList = (ArrayList)null;
            if (fieldCount > 0)
            {
                int valueCount = Renderer.ValueCount;
                bool flag = false;
                if (fieldCount > 1)
                    flag = true;
                string label1 = Renderer.get_Label(Renderer.get_Value(Index));
                if (!flag)
                {
                    arrayList = new ArrayList();
                    arrayList.Add((object)Renderer.get_Value(Index));
                    int num = checked(Index + 1);
                    while (num < valueCount)
                    {
                        ISymbol symbol = Renderer.get_Symbol(Renderer.get_Value(num));
                        string label2 = Renderer.get_Label(Renderer.get_Value(num));
                        if (symbol == null & Operators.CompareString(label1, label2, false) == 0)
                        {
                            arrayList.Add((object)Renderer.get_Value(num));
                            checked { ++num; }
                        }
                        else
                            break;
                    }
                }
                else
                    arrayList = gimmeSeperateFieldValues(Renderer.get_Value(Index), Renderer.FieldDelimiter);
            }
            return arrayList;
        }

        private ArrayList getUVFieldDefaultValues(IUniqueValueRenderer Renderer)
        {
            int fieldCount = 1;// Renderer.FieldCount;
            ArrayList arrayList = (ArrayList)null;
            if (fieldCount > 0)
            {
                bool flag = false;
                string label1 = Renderer.DefaultLabel;
                if (!flag)
                {
                    arrayList = new ArrayList();
                    arrayList.Add(Renderer.DefaultLabel);
           
                }
              
            }
            return arrayList;
        }


        private string markerSymbolScan(IMarkerSymbol Symbol)
        {
            if (Symbol is ISimpleMarkerSymbol)
                return "ISimpleMarkerSymbol";
            if (Symbol is ICartographicMarkerSymbol)
            {
                ICartographicMarkerSymbol icartographicMarkerSymbol = (ICartographicMarkerSymbol)Symbol;
                if (icartographicMarkerSymbol is ICharacterMarkerSymbol)
                    return "ICharacterMarkerSymbol";
                if (icartographicMarkerSymbol is IPictureMarkerSymbol)
                    return "IPictureMarkerSymbol";

                return String.Empty;
            }
            if (Symbol is IArrowMarkerSymbol)
                return "IArrowMarkerSymbol";
            return Symbol is IMultiLayerMarkerSymbol ? "IMultiLayerMarkerSymbol" : "false";
        }

        private StructUniqueValueRenderer StoreStructUVRenderer(IUniqueValueRenderer Renderer, IFeatureLayer Layer, bool isLayerDb)
        {
            StructUniqueValueRenderer uniqueValueRenderer1 = new StructUniqueValueRenderer();
            int valueCount = Renderer.ValueCount;
            ArrayList FieldNames = new ArrayList();
            ITable featureClass = (ITable)Layer.FeatureClass;
            IDataset idataset = (IDataset)featureClass;
            uniqueValueRenderer1.SymbolList = new ArrayList();
            bool allValues = Renderer.UseDefaultSymbol;


            bool flag1 = false;
            this.m_alClassifiedFields = new ArrayList();
            bool flag2 = false;
            StructUniqueValueRenderer uniqueValueRenderer2;
            try
            {
                uniqueValueRenderer1.ValueCount = valueCount;
                uniqueValueRenderer1.LayerName = Layer.Name;
                uniqueValueRenderer1.DatasetName = idataset.Name;
                uniqueValueRenderer1.Annotation = getAnnotation(Layer, isLayerDb);
                int fieldCount = Renderer.FieldCount;
                uniqueValueRenderer1.FieldCount = fieldCount;
                if (fieldCount > 1)
                    flag1 = true;
                IDisplayTable idisplayTable = (IDisplayTable)Layer;
                ITable itable = idisplayTable.DisplayTable;
                if (itable is IRelQueryTable)
                    flag2 = true;
                if (flag1)
                {
                    if (idataset.Workspace.Type == 0)
                    {
                        int num1 = 1;
                        int num2 = fieldCount;
                        int num3 = num1;
                        while (num3 <= num2)
                        {
                            FieldNames.Add((object)Renderer.get_Field(checked(num3 - 1)));
                            checked { ++num3; }
                        }
                        gimmeUniqueValuesFromShape(featureClass, FieldNames);
                        uniqueValueRenderer1.FieldNames = FieldNames;
                    }
                    else
                    {
                        int num1 = 1;
                        int num2 = fieldCount;
                        int num3 = num1;
                        while (num3 <= num2)
                        {
                            FieldNames.Add((object)Renderer.get_Field(checked(num3 - 1)));
                            if (itable is IRelQueryTable)
                            {
                                ArrayList JoinedTables = new ArrayList();
                                while (itable is IRelQueryTable)
                                {
                                    IRelQueryTable irelQueryTable = (IRelQueryTable)itable;
                                    IDataset destinationTable = (IDataset)irelQueryTable.DestinationTable;
                                    itable = irelQueryTable.SourceTable;
                                    JoinedTables.Add((object)destinationTable.Name);
                                }
                                gimmeUniqeValuesForFieldname(featureClass, Renderer.get_Field(checked(num3 - 1)), JoinedTables);
                                itable = idisplayTable.DisplayTable;
                            }
                            else
                                gimmeUniqeValuesForFieldname(featureClass, Renderer.get_Field(checked(num3 - 1)));
                            checked { ++num3; }
                        }
                        uniqueValueRenderer1.FieldNames = FieldNames;
                    }
                }
                else
                {
                    FieldNames.Add((object)Renderer.get_Field(checked(fieldCount - 1)));
                    uniqueValueRenderer1.FieldNames = FieldNames;
                }
                if (!flag2)
                {
                    int num1 = 0;
                    int num2 = checked(uniqueValueRenderer1.FieldNames.Count - 1);
                    int index = num1;
                    while (index <= num2)
                    {
                        uniqueValueRenderer1.FieldNames[index] = uniqueValueRenderer1.FieldNames[index];
                        //uniqueValueRenderer1.FieldNames[index] = Operators.ConcatenateObject((object)(idataset.Name + "."), uniqueValueRenderer1.FieldNames[index]);
                        checked { ++index; }
                    }
                }
                int num4 = 0;
                int num5 = checked(valueCount - 1);
                int Index = num4;

                //Visualizzo il DEFAULT VALUE
                if (allValues)
                {
                    ISymbol symbol = Renderer.DefaultSymbol;
                    if (symbol is ITextSymbol)
                    {
                        StructTextSymbol structTextSymbol = storeText((ITextSymbol)symbol);
                        structTextSymbol.Label = Renderer.DefaultLabel;
                        structTextSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                        structTextSymbol.IsDefault = true;
                        uniqueValueRenderer1.SymbolList.Add((object)structTextSymbol);
                    }
                    if (symbol is IMarkerSymbol)
                    {
                        uniqueValueRenderer1.FeatureCls = Feature.PointFeature;
                        IMarkerSymbol Symbol = (IMarkerSymbol)symbol;
                        string Left = markerSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ISimpleMarkerSymbol", false) == 0)
                        {
                            StructSimpleMarkerSymbol simpleMarkerSymbol = storeSimpleMarker((ISimpleMarkerSymbol)Symbol);
                            simpleMarkerSymbol.Label = Renderer.DefaultLabel;
                            simpleMarkerSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            simpleMarkerSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)simpleMarkerSymbol);

                        }
                        else if (Operators.CompareString(Left, "ICharacterMarkerSymbol", false) == 0)
                        {
                            StructCharacterMarkerSymbol characterMarkerSymbol = storeCharacterMarker((ICharacterMarkerSymbol)Symbol);
                            characterMarkerSymbol.Label = Renderer.DefaultLabel; 
                            characterMarkerSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            characterMarkerSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)characterMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureMarkerSymbol", false) == 0)
                        {
                            StructPictureMarkerSymbol pictureMarkerSymbol = storePictureMarker((IPictureMarkerSymbol)Symbol);
                            pictureMarkerSymbol.Label = Renderer.DefaultLabel; 
                            pictureMarkerSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            pictureMarkerSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)pictureMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IArrowMarkerSymbol", false) == 0)
                        {
                            StructArrowMarkerSymbol arrowMarkerSymbol = storeArrowMarker((IArrowMarkerSymbol)Symbol);
                            arrowMarkerSymbol.Label = Renderer.DefaultLabel;
                            arrowMarkerSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            arrowMarkerSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)arrowMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerMarkerSymbol", false) == 0)
                        {
                            StructMultilayerMarkerSymbol multilayerMarkerSymbol = storeMultiLayerMarker((IMultiLayerMarkerSymbol)Symbol);
                            multilayerMarkerSymbol.Label = Renderer.DefaultLabel;
                            multilayerMarkerSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            multilayerMarkerSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)multilayerMarkerSymbol);
                        }
                     

                    }
                    if (symbol is ILineSymbol)
                    {
                        uniqueValueRenderer1.FeatureCls = Feature.LineFeature;
                        ILineSymbol Symbol = (ILineSymbol)symbol;
                        string Left = lineSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
                        {
                            StructCartographicLineSymbol cartographicLineSymbol = storeCartographicLine((ICartographicLineSymbol)Symbol);
                            cartographicLineSymbol.Label = Renderer.DefaultLabel;
                            cartographicLineSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            cartographicLineSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)cartographicLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
                        {
                            StructHashLineSymbol structHashLineSymbol = storeHashLine((IHashLineSymbol)Symbol);
                            structHashLineSymbol.Label = Renderer.DefaultLabel;
                            structHashLineSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            structHashLineSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)structHashLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
                        {
                            StructMarkerLineSymbol markerLineSymbol = storeMarkerLine((IMarkerLineSymbol)Symbol);
                            markerLineSymbol.Label = Renderer.DefaultLabel;
                            markerLineSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            markerLineSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)markerLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
                        {
                            StructSimpleLineSymbol simpleLineSymbol = storeSimpleLine((ISimpleLineSymbol)Symbol);
                            simpleLineSymbol.Label = Renderer.DefaultLabel;
                            simpleLineSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            simpleLineSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)simpleLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
                        {
                            StructPictureLineSymbol pictureLineSymbol = storePictureLine((IPictureLineSymbol)Symbol);
                            pictureLineSymbol.Label = Renderer.DefaultLabel;
                            pictureLineSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            pictureLineSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)pictureLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
                        {
                            StructMultilayerLineSymbol multilayerLineSymbol = storeMultilayerLines((IMultiLayerLineSymbol)Symbol);
                            multilayerLineSymbol.Label = Renderer.DefaultLabel;
                            multilayerLineSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            multilayerLineSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)multilayerLineSymbol);
                        }
                      
                    }
                    if (symbol is IFillSymbol)
                    {
                        uniqueValueRenderer1.FeatureCls = Feature.PolygonFeature;
                        IFillSymbol Symbol = (IFillSymbol)symbol;
                        string Left = fillSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ISimpleFillSymbol", false) == 0)
                        {
                            StructSimpleFillSymbol simpleFillSymbol = storeSimpleFill((ISimpleFillSymbol)Symbol);
                            simpleFillSymbol.Label = Renderer.DefaultLabel;
                            simpleFillSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            simpleFillSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)simpleFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMarkerFillSymbol", false) == 0)
                        {
                            StructMarkerFillSymbol markerFillSymbol = storeMarkerFill((IMarkerFillSymbol)Symbol);
                            markerFillSymbol.Label = Renderer.DefaultLabel;
                            markerFillSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            markerFillSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)markerFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "ILineFillSymbol", false) == 0)
                        {
                            StructLineFillSymbol structLineFillSymbol = storeLineFill((ILineFillSymbol)Symbol);
                            structLineFillSymbol.Label = Renderer.DefaultLabel;
                            structLineFillSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            structLineFillSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)structLineFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IDotDensityFillSymbol", false) == 0)
                        {
                            StructDotDensityFillSymbol densityFillSymbol = storeDotDensityFill((IDotDensityFillSymbol)Symbol);
                            densityFillSymbol.Label = Renderer.DefaultLabel;
                            densityFillSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            densityFillSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)densityFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureFillSymbol", false) == 0)
                        {
                            StructPictureFillSymbol pictureFillSymbol = storePictureFill((IPictureFillSymbol)Symbol);
                            pictureFillSymbol.Label = Renderer.DefaultLabel;
                            pictureFillSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            pictureFillSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)pictureFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IGradientFillSymbol", false) == 0)
                        {
                            StructGradientFillSymbol gradientFillSymbol = storeGradientFill((IGradientFillSymbol)Symbol);
                            gradientFillSymbol.Label = Renderer.DefaultLabel;
                            gradientFillSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            gradientFillSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)gradientFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerFillSymbol", false) == 0)
                        {
                            StructMultilayerFillSymbol multilayerFillSymbol = storeMultiLayerFill((IMultiLayerFillSymbol)Symbol);
                            multilayerFillSymbol.Label = Renderer.DefaultLabel;
                            multilayerFillSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            multilayerFillSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)multilayerFillSymbol);
                        }
                       
                    }
                    if (symbol is I3DChartSymbol)
                    {
                        I3DChartSymbol Symbol = (I3DChartSymbol)symbol;
                        string Left = this.IIIDChartSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "IBarChartSymbol", false) == 0)
                        {
                            StructBarChartSymbol structBarChartSymbol = storeBarChart((IBarChartSymbol)Symbol);
                            structBarChartSymbol.Label = Renderer.DefaultLabel;
                            structBarChartSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            structBarChartSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)structBarChartSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPieChartSymbol", false) == 0)
                        {
                            StructPieChartSymbol structPieChartSymbol = storePieChart((IPieChartSymbol)Symbol);
                            structPieChartSymbol.Label = Renderer.DefaultLabel;
                            structPieChartSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            structPieChartSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)structPieChartSymbol);
                        }
                        else if (Operators.CompareString(Left, "IStackedChartSymbol", false) == 0)
                        {
                            StructStackedChartSymbol stackedChartSymbol = storeStackedChart((IStackedChartSymbol)Symbol);
                            stackedChartSymbol.Label = Renderer.DefaultLabel;
                            stackedChartSymbol.Fieldvalues = getUVFieldDefaultValues(Renderer);
                            stackedChartSymbol.IsDefault = true;
                            uniqueValueRenderer1.SymbolList.Add((object)stackedChartSymbol);
                        }
                    }

                  
                }

                while (Index <= num5)
                {

                    ISymbol symbol = Renderer.get_Symbol(Renderer.get_Value(Index));
                    if (symbol is ITextSymbol)
                    {
                        StructTextSymbol structTextSymbol = storeText((ITextSymbol)symbol);
                        structTextSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                        structTextSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                        uniqueValueRenderer1.SymbolList.Add((object)structTextSymbol);
                    }
                    if (symbol is IMarkerSymbol)
                    {
                        uniqueValueRenderer1.FeatureCls = Feature.PointFeature;
                        IMarkerSymbol Symbol = (IMarkerSymbol)symbol;
                        string Left = markerSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ISimpleMarkerSymbol", false) == 0)
                        {
                            StructSimpleMarkerSymbol simpleMarkerSymbol = storeSimpleMarker((ISimpleMarkerSymbol)Symbol);
                            simpleMarkerSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            simpleMarkerSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)simpleMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "ICharacterMarkerSymbol", false) == 0)
                        {
                            StructCharacterMarkerSymbol characterMarkerSymbol = storeCharacterMarker((ICharacterMarkerSymbol)Symbol);
                            characterMarkerSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            characterMarkerSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)characterMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureMarkerSymbol", false) == 0)
                        {
                            StructPictureMarkerSymbol pictureMarkerSymbol = storePictureMarker((IPictureMarkerSymbol)Symbol);
                            pictureMarkerSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            pictureMarkerSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)pictureMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IArrowMarkerSymbol", false) == 0)
                        {
                            StructArrowMarkerSymbol arrowMarkerSymbol = storeArrowMarker((IArrowMarkerSymbol)Symbol);
                            arrowMarkerSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            arrowMarkerSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)arrowMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerMarkerSymbol", false) == 0)
                        {
                            StructMultilayerMarkerSymbol multilayerMarkerSymbol = storeMultiLayerMarker((IMultiLayerMarkerSymbol)Symbol);
                            multilayerMarkerSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            multilayerMarkerSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)multilayerMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "false", false) == 0)
                        {
                            //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", "StoreStructLayer");
                            //SIMBOLO NON RICONOSCIUTO
                        }

                    }
                    if (symbol is ILineSymbol)
                    {
                        uniqueValueRenderer1.FeatureCls = Feature.LineFeature;
                        ILineSymbol Symbol = (ILineSymbol)symbol;
                        string Left = lineSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
                        {
                            StructCartographicLineSymbol cartographicLineSymbol = storeCartographicLine((ICartographicLineSymbol)Symbol);
                            cartographicLineSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            cartographicLineSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)cartographicLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
                        {
                            StructHashLineSymbol structHashLineSymbol = storeHashLine((IHashLineSymbol)Symbol);
                            structHashLineSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            structHashLineSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)structHashLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
                        {
                            StructMarkerLineSymbol markerLineSymbol = storeMarkerLine((IMarkerLineSymbol)Symbol);
                            markerLineSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            markerLineSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)markerLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
                        {
                            StructSimpleLineSymbol simpleLineSymbol = storeSimpleLine((ISimpleLineSymbol)Symbol);
                            simpleLineSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            simpleLineSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)simpleLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
                        {
                            StructPictureLineSymbol pictureLineSymbol = storePictureLine((IPictureLineSymbol)Symbol);
                            pictureLineSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            pictureLineSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)pictureLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
                        {
                            StructMultilayerLineSymbol multilayerLineSymbol = storeMultilayerLines((IMultiLayerLineSymbol)Symbol);
                            multilayerLineSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            multilayerLineSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)multilayerLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "false", false) == 0) {
                            //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", "StoreStructLayer");
                            //SIMBOLO NON RICONOSCIUTO
                        }

                    }
                    if (symbol is IFillSymbol)
                    {
                        uniqueValueRenderer1.FeatureCls = Feature.PolygonFeature;
                        IFillSymbol Symbol = (IFillSymbol)symbol;
                        string Left = fillSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ISimpleFillSymbol", false) == 0)
                        {
                            StructSimpleFillSymbol simpleFillSymbol = storeSimpleFill((ISimpleFillSymbol)Symbol);
                            simpleFillSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            simpleFillSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)simpleFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMarkerFillSymbol", false) == 0)
                        {
                            StructMarkerFillSymbol markerFillSymbol = storeMarkerFill((IMarkerFillSymbol)Symbol);
                            markerFillSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            markerFillSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)markerFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "ILineFillSymbol", false) == 0)
                        {
                            StructLineFillSymbol structLineFillSymbol = storeLineFill((ILineFillSymbol)Symbol);
                            structLineFillSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            structLineFillSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)structLineFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IDotDensityFillSymbol", false) == 0)
                        {
                            StructDotDensityFillSymbol densityFillSymbol = storeDotDensityFill((IDotDensityFillSymbol)Symbol);
                            densityFillSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            densityFillSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)densityFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureFillSymbol", false) == 0)
                        {
                            StructPictureFillSymbol pictureFillSymbol = storePictureFill((IPictureFillSymbol)Symbol);
                            pictureFillSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            pictureFillSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)pictureFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IGradientFillSymbol", false) == 0)
                        {
                            StructGradientFillSymbol gradientFillSymbol = storeGradientFill((IGradientFillSymbol)Symbol);
                            gradientFillSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            gradientFillSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)gradientFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerFillSymbol", false) == 0)
                        {
                            StructMultilayerFillSymbol multilayerFillSymbol = storeMultiLayerFill((IMultiLayerFillSymbol)Symbol);
                            multilayerFillSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            multilayerFillSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)multilayerFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "false", false) == 0)
                        {
                            //SIMBOLO NON TROVATO
                        }
                    }
                    if (symbol is I3DChartSymbol)
                    {
                        I3DChartSymbol Symbol = (I3DChartSymbol)symbol;
                        string Left = this.IIIDChartSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "IBarChartSymbol", false) == 0)
                        {
                            StructBarChartSymbol structBarChartSymbol = storeBarChart((IBarChartSymbol)Symbol);
                            structBarChartSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            structBarChartSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)structBarChartSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPieChartSymbol", false) == 0)
                        {
                            StructPieChartSymbol structPieChartSymbol = storePieChart((IPieChartSymbol)Symbol);
                            structPieChartSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            structPieChartSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)structPieChartSymbol);
                        }
                        else if (Operators.CompareString(Left, "IStackedChartSymbol", false) == 0)
                        {
                            StructStackedChartSymbol stackedChartSymbol = storeStackedChart((IStackedChartSymbol)Symbol);
                            stackedChartSymbol.Label = Renderer.get_Label(Renderer.get_Value(Index));
                            stackedChartSymbol.Fieldvalues = getUVFieldValues(Renderer, Index);
                            uniqueValueRenderer1.SymbolList.Add((object)stackedChartSymbol);
                        }
                        else if (Operators.CompareString(Left, "false", false) == 0)
                        {
                            //SIMBOLO NON TROVATO
                        }
                    }
                    checked { ++Index; }
                }
                uniqueValueRenderer2 = uniqueValueRenderer1;
            }
            catch (Exception ex)
            {
                throw ex;

                //ProjectData.SetProjectError(ex);
                //Exception exception = ex;
                //this.ErrorMsg("Fehler beim Speichern der Symbole in den Layerstrukturen", exception.Message, exception.StackTrace, nameof(StoreStructUVRenderer));
                //ProjectData.ClearProjectError();
            }
            return uniqueValueRenderer2;
        }

        private StructAnnotation getAnnotation(IFeatureLayer objLayer, bool isLayerDb)
        {
            StructAnnotation structAnnotation = new StructAnnotation();
            structAnnotation.PropertyName = "";
            if (objLayer is IGeoFeatureLayer)
            {
                IGeoFeatureLayer igeoFeatureLayer = (IGeoFeatureLayer)objLayer;
                IAnnotateLayerPropertiesCollection annotationProperties = igeoFeatureLayer.AnnotationProperties;
                //if (igeoFeatureLayer.DisplayAnnotation & annotationProperties.Count > 0)
                //{
                    IAnnotateLayerPropertiesCollection propertiesCollection = annotationProperties;
                    int num = 0;
                    IElementCollection ielementCollection1 = (IElementCollection)null;
                    IElementCollection ielementCollection2 = (IElementCollection)null;
                    IAnnotateLayerProperties iannotateLayerProperties;
                    propertiesCollection.QueryItem(num, out iannotateLayerProperties, out ielementCollection1, out ielementCollection2);
                    //if (iannotateLayerProperties is ILabelEngineLayerProperties & iannotateLayerProperties.DisplayAnnotation){
                    
                        ILabelEngineLayerProperties engineLayerProperties = (ILabelEngineLayerProperties)iannotateLayerProperties;
                        if (Operators.CompareString(iannotateLayerProperties.WhereClause, "", false) == 0 & engineLayerProperties.IsExpressionSimple)
                        {

                            structAnnotation.IsSingleProperty = true;
                            String expr = String.Empty;
                            if (engineLayerProperties.Expression.Contains("&"))
                                expr = engineLayerProperties.Expression.Substring(0, engineLayerProperties.Expression.IndexOf("&"));
                            else
                                expr = engineLayerProperties.Expression;

                            double ddd = engineLayerProperties.BasicOverposterLayerProperties.LineOffset;


                            if (isLayerDb)
                                structAnnotation.PropertyName = expr.Replace("[", "").Replace("]", "").ToLower(); // engineLayerProperties.Expression.Replace("[", "").Replace("]", "");
                            else
                                structAnnotation.PropertyName = expr.Replace("[", "").Replace("]", "");


                            structAnnotation.TextSymbol = storeText(engineLayerProperties.Symbol);
                            structAnnotation.MaxScale = iannotateLayerProperties.AnnotationMaximumScale;
                            structAnnotation.MinScale = iannotateLayerProperties.AnnotationMinimumScale;
                        }
                    //}
                //}
            }
            return structAnnotation;
        }

        private bool gimmeUniqueValuesFromShape(ITable Table, ArrayList FieldNames)
        {
            IQueryFilter iqueryFilter = (IQueryFilter)new QueryFilter();
            IDataStatistics idataStatistics = (IDataStatistics)new DataStatistics();
            try
            {

                if (MessageBox.Show("ATTENZIONE: Il layer che si sta importando contiene un numero elevato di informazionei.  L'operazione potrebbe richiedere diversi MINUTI. Si vuole continuare? ", "ATTENZIONE!", MessageBoxButtons.YesNo, MessageBoxIcon.Asterisk) == DialogResult.Yes)
                {
                    int num1 = 0;
                    short num2 = checked((short)(FieldNames.Count - 1));
                    short num3 = (short)num1;
                    while ((int)num3 <= (int)num2)
                    {
                        //this.frmMotherform.CHLabelSmall("Please wait - classified Field Nr. " + Conversions.ToString(checked((int)num3 + 1)) + " of " + Conversions.ToString(FieldNames.Count));
                        idataStatistics.Field = FieldNames[(int)num3].ToString();
                        iqueryFilter.SubFields = FieldNames[(int)num3].ToString();
                        ICursor icursor = Table.Search(iqueryFilter, false);
                        idataStatistics.Cursor = icursor;
                        //this.frmMotherform.DoEvents();
                        IEnumerator uniqueValues = idataStatistics.UniqueValues;
                        ArrayList arrayList = new ArrayList();
                        uniqueValues.MoveNext();
                        while (uniqueValues.Current != null)
                        {
                            arrayList.Add(RuntimeHelpers.GetObjectValue(uniqueValues.Current));
                            uniqueValues.MoveNext();
                        }
                        arrayList.Sort();
                        this.m_alClassifiedFields.Add((object)arrayList);
                        checked { ++num3; }
                    }
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
            //    bool flag;
            //    return flag;
            return true;
        }
        private void addOneToLayerNumber()
        {
            checked { ++strutturaLayer.LayerCount; }
        }

        private StructSimpleMarkerSymbol storeSimpleMarker(ISimpleMarkerSymbol symbol)
        {
            return new StructSimpleMarkerSymbol()
            {
                Angle = symbol.Angle,
                Filled = symbol.Color.Transparency != (byte)0,
                Color = gimmeStringForColor(symbol.Color),
                Outline = symbol.Outline,
                OutlineColor = gimmeStringForColor(symbol.OutlineColor),
                OutlineSize = symbol.OutlineSize,
                Size = symbol.Size,
                Style = ((Enum)(object)symbol.Style).ToString(),
                XOffset = symbol.XOffset,
                YOffset = symbol.YOffset
            };
        }


        private StructCharacterMarkerSymbol storeCharacterMarker(ICharacterMarkerSymbol symbol)
        {
            return new StructCharacterMarkerSymbol()
            {
                Angle = symbol.Angle,
                CharacterIndex = symbol.CharacterIndex,
                Color = gimmeStringForColor(symbol.Color),
                Font = symbol.Font.Name,
                Size = symbol.Size,
                XOffset = symbol.XOffset,
                YOffset = symbol.YOffset
            };
        }

        private StructPictureMarkerSymbol storePictureMarker(IPictureMarkerSymbol symbol)
        {
            return new StructPictureMarkerSymbol()
            {
                Angle = symbol.Angle,
                BackgroundColor = gimmeStringForColor(symbol.BackgroundColor),
                Color = gimmeStringForColor(symbol.Color),
                Picture = (stdole.IPicture)symbol.Picture,
                Size = symbol.Size,
                XOffset = symbol.XOffset,
                YOffset = symbol.YOffset
            };
        }

        private StructArrowMarkerSymbol storeArrowMarker(IArrowMarkerSymbol symbol)
        {
            return new StructArrowMarkerSymbol()
            {
                Angle = symbol.Angle,
                Color = gimmeStringForColor(symbol.Color),
                Length = symbol.Length,
                Size = symbol.Size,
                Style = ((int)symbol.Style).ToString(), //  Convers(int)symbol.get_Style()),
                Width = symbol.Width,
                XOffset = symbol.XOffset,
                YOffset = symbol.YOffset
            };
        }


        private StructMultilayerMarkerSymbol storeMultiLayerMarker(IMultiLayerMarkerSymbol symbol)
        {
            StructMultilayerMarkerSymbol multilayerMarkerSymbol = new StructMultilayerMarkerSymbol();
            multilayerMarkerSymbol.MultiMarkerLayers = new ArrayList();
            multilayerMarkerSymbol.LayerCount = symbol.LayerCount;
            int num1 = 0;
            int num2 = checked(symbol.LayerCount - 1);
            int num3 = num1;
            while (num3 <= num2)
            {
                string Left = markerSymbolScan(symbol.get_Layer(num3));
                if (Operators.CompareString(Left, "ISimpleMarkerSymbol", false) == 0)
                {
                    ISimpleMarkerSymbol layer = (ISimpleMarkerSymbol)symbol.get_Layer(num3);
                    multilayerMarkerSymbol.MultiMarkerLayers.Add((object)storeSimpleMarker(layer));
                }
                else if (Operators.CompareString(Left, "ICharacterMarkerSymbol", false) == 0)
                {
                    ICharacterMarkerSymbol layer = (ICharacterMarkerSymbol)symbol.get_Layer(num3);
                    multilayerMarkerSymbol.MultiMarkerLayers.Add((object)storeCharacterMarker(layer));
                }
                else if (Operators.CompareString(Left, "IPictureMarkerSymbol", false) == 0)
                {
                    IPictureMarkerSymbol layer = (IPictureMarkerSymbol)symbol.get_Layer(num3);
                    multilayerMarkerSymbol.MultiMarkerLayers.Add((object)storePictureMarker(layer));
                }
                else if (Operators.CompareString(Left, "IArrowMarkerSymbol", false) == 0)
                {
                    IArrowMarkerSymbol layer = (IArrowMarkerSymbol)symbol.get_Layer(num3);
                    multilayerMarkerSymbol.MultiMarkerLayers.Add((object)storeArrowMarker(layer));
                }
                else if (Operators.CompareString(Left, "false", false) == 0)
                {
                    //simobolo non riconosciuto
                }

                checked { ++num3; }
            }
            return multilayerMarkerSymbol;
        }

        private string lineSymbolScan(ILineSymbol Symbol)
        {
            bool flag1 = false;
            if (Symbol is ICartographicLineSymbol)
            {
                ICartographicLineSymbol icartographicLineSymbol = (ICartographicLineSymbol)Symbol;
                if (icartographicLineSymbol is IHashLineSymbol)
                {
                    string str = "IHashLineSymbol";
                    return str;
                }
                if (icartographicLineSymbol is IMarkerLineSymbol)
                {
                    string str = "IMarkerLineSymbol";
                    return str;
                }
                if (!flag1)
                    return "ICartographicLineSymbol";

                return String.Empty;
            }
            if (Symbol is ISimpleLineSymbol)
                return "ISimpleLineSymbol";
            if (Symbol is IPictureLineSymbol)
                return "IPictureLineSymbol";
            return Symbol is IMultiLayerLineSymbol ? "IMultiLayerLineSymbol" : "false";
        }


        private StructCartographicLineSymbol storeCartographicLine(ICartographicLineSymbol symbol)
        {
            StructCartographicLineSymbol cartographicLineSymbol = new StructCartographicLineSymbol();
            cartographicLineSymbol.Color = gimmeStringForColor(symbol.Color);
            cartographicLineSymbol.Transparency = symbol.Color.Transparency;
            cartographicLineSymbol.Width = symbol.Width;
            cartographicLineSymbol.Join = ((int)symbol.Join).ToString();
            cartographicLineSymbol.MiterLimit = symbol.MiterLimit;
            cartographicLineSymbol.Cap = ((int)symbol.Cap).ToString();
            cartographicLineSymbol.DashArray = new ArrayList();
            if (symbol is ILineProperties)
            {
                ILineProperties ilineProperties = (ILineProperties)symbol;



                bool isDecorated = ilineProperties.DecorationOnTop;
                bool isFlipped = ilineProperties.Flip;
                double listaStartOffSet = ilineProperties.LineStartOffset;
                double listaOffSet = ilineProperties.Offset;
                
                ILineDecoration decoration = ilineProperties.LineDecoration;
                if (decoration != null)
                {
                    int numDec = decoration.ElementCount;
                    if (numDec > 0)
                    {
                        List<ILineDecorationElement> lsDecoration = new List<ILineDecorationElement>();
                        for (int i = 0; i < decoration.ElementCount; i++)
                        {
                            ILineDecorationElement element = decoration.Element[i];
                            lsDecoration.Add(element);
                            cartographicLineSymbol.DashArray.Add((object)element);
                            if (element.PositionCount > 0)
                            {
                                bool isRatio = element.PositionAsRatio;
                                for (int x = 0; x < element.PositionCount; x++)
                                {
                                    double posElement = element.Position[x];
                                }
                            }

                            
                        }


                    }
                }

                if (ilineProperties.Template is ITemplate)
                {
                    ITemplate template = ilineProperties.Template;
                    double interval = template.Interval;
                    int num1 = 0;
                    int num2 = checked(template.PatternElementCount - 1);
                    int num3 = num1;
                    while (num3 <= num2)
                    {
                        double num4;
                        double num5;
                        template.GetPatternElement(num3, out num4, out num5);
                        cartographicLineSymbol.DashArray.Add((object)(num4 * interval));
                        cartographicLineSymbol.DashArray.Add((object)(num5 * interval));
                        checked { ++num3; }
                    }
                }
            }
            return cartographicLineSymbol;
        }

        private StructHashLineSymbol storeHashLine(IHashLineSymbol symbol)
        {
            StructHashLineSymbol structHashLineSymbol = new StructHashLineSymbol();
            structHashLineSymbol.Angle = symbol.Angle;
            structHashLineSymbol.Color = gimmeStringForColor(symbol.Color);
            structHashLineSymbol.Transparency = symbol.Color.Transparency;
            string Left = lineSymbolScan(symbol.HashSymbol);
            if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol hashSymbol = (ICartographicLineSymbol)symbol.HashSymbol;
                structHashLineSymbol.HashSymbol_CartographicLine = storeCartographicLine(hashSymbol);
                structHashLineSymbol.kindOfLineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol hashSymbol = (IMarkerLineSymbol)symbol.HashSymbol;
                structHashLineSymbol.HashSymbol_MarkerLine = storeMarkerLine(hashSymbol);
                structHashLineSymbol.kindOfLineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol hashSymbol = (ISimpleLineSymbol)symbol.HashSymbol;
                structHashLineSymbol.HashSymbol_SimpleLine = storeSimpleLine(hashSymbol);
                structHashLineSymbol.kindOfLineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol hashSymbol = (IPictureLineSymbol)symbol.HashSymbol;
                structHashLineSymbol.HashSymbol_PictureLine = storePictureLine(hashSymbol);
                structHashLineSymbol.kindOfLineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol hashSymbol = (IMultiLayerLineSymbol)symbol.HashSymbol;
                structHashLineSymbol.HashSymbol_MultiLayerLines = storeMultilayerLines(hashSymbol);
                structHashLineSymbol.kindOfLineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left, "false", false) == 0) {
                //               this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreHashLine));
                //SIMBOLO NON RICONOSCIUTO           
            }

            return structHashLineSymbol;
        }

        private StructMarkerLineSymbol storeMarkerLine(IMarkerLineSymbol symbol)
        {
            StructMarkerLineSymbol markerLineSymbol = new StructMarkerLineSymbol();
            markerLineSymbol.Color = gimmeStringForColor(symbol.Color);
            markerLineSymbol.Transparency = symbol.Color.Transparency;
            markerLineSymbol.Width = symbol.Width;
            string Left = markerSymbolScan(symbol.MarkerSymbol);
            if (Operators.CompareString(Left, "ISimpleMarkerSymbol", false) == 0)
            {
                ISimpleMarkerSymbol markerSymbol = (ISimpleMarkerSymbol)symbol.MarkerSymbol;
                markerLineSymbol.MarkerSymbol_SimpleMarker = storeSimpleMarker(markerSymbol);
                markerLineSymbol.kindOfMarkerStruct = MarkerStructs.StructSimpleMarkerSymbol;
            }
            else if (Operators.CompareString(Left, "ICharacterMarkerSymbol", false) == 0)
            {
                ICharacterMarkerSymbol markerSymbol = (ICharacterMarkerSymbol)symbol.MarkerSymbol;
                markerLineSymbol.MarkerSymbol_CharacterMarker = storeCharacterMarker(markerSymbol);
                markerLineSymbol.kindOfMarkerStruct = MarkerStructs.StructCharacterMarkerSymbol;
            }
            else if (Operators.CompareString(Left, "IPictureMarkerSymbol", false) == 0)
            {
                IPictureMarkerSymbol markerSymbol = (IPictureMarkerSymbol)symbol.MarkerSymbol;
                markerLineSymbol.MarkerSymbol_PictureMarker = storePictureMarker(markerSymbol);
                markerLineSymbol.kindOfMarkerStruct = MarkerStructs.StructPictureMarkerSymbol;
            }
            else if (Operators.CompareString(Left, "IArrowMarkerSymbol", false) == 0)
            {
                IArrowMarkerSymbol markerSymbol = (IArrowMarkerSymbol)symbol.MarkerSymbol;
                markerLineSymbol.MarkerSymbol_ArrowMarker = storeArrowMarker(markerSymbol);
                markerLineSymbol.kindOfMarkerStruct = MarkerStructs.StructArrowMarkerSymbol;
            }
            else if (Operators.CompareString(Left, "IMultiLayerMarkerSymbol", false) == 0)
            {
                IMultiLayerMarkerSymbol markerSymbol = (IMultiLayerMarkerSymbol)symbol.MarkerSymbol;
                markerLineSymbol.MarkerSymbol_MultilayerMarker = storeMultiLayerMarker(markerSymbol);
                markerLineSymbol.kindOfMarkerStruct = MarkerStructs.StructMultilayerMarkerSymbol;
            }
            else if (Operators.CompareString(Left, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerLine));
                //SIMBOLO NON RICONOSCIUTO
            }
            return markerLineSymbol;
        }


        private StructSimpleLineSymbol storeSimpleLine(ISimpleLineSymbol symbol)
        {
            return new StructSimpleLineSymbol()
            {
                Color = (int)symbol.Style != 5 ? gimmeStringForColor(symbol.Color) : "",
                Transparency = symbol.Color.Transparency,
                publicStyle = Conversions.ToString((int)symbol.Style),
                Width = symbol.Width
            };
        }

        private StructPictureLineSymbol storePictureLine(IPictureLineSymbol symbol)
        {
            return new StructPictureLineSymbol()
            {
                BackgroundColor = gimmeStringForColor(symbol.BackgroundColor),
                BackgroundTransparency = symbol.BackgroundColor.Transparency,
                Color = gimmeStringForColor(symbol.Color),
                Transparency = symbol.Color.Transparency,
                Offset = symbol.Offset,
                Picture = (stdole.IPicture)symbol.Picture,
                Rotate = symbol.Rotate,
                Width = symbol.Width,
                XScale = symbol.XScale,
                YScale = symbol.YScale
            };
        }

        private StructMultilayerLineSymbol storeMultilayerLines(IMultiLayerLineSymbol symbol)
        {
            StructMultilayerLineSymbol multilayerLineSymbol = new StructMultilayerLineSymbol();
            multilayerLineSymbol.MultiLineLayers = new ArrayList();
            multilayerLineSymbol.LayerCount = symbol.LayerCount;
            int num1 = 0;
            int num2 = checked(symbol.LayerCount - 1);
            int num3 = num1;
            while (num3 <= num2)
            {
                string Left = lineSymbolScan(symbol.get_Layer(num3));
                if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
                {
                    ICartographicLineSymbol layer = (ICartographicLineSymbol)symbol.get_Layer(num3);
                    multilayerLineSymbol.MultiLineLayers.Add((object)storeCartographicLine(layer));
                }
                else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
                {
                    IHashLineSymbol layer = (IHashLineSymbol)symbol.get_Layer(num3);
                    multilayerLineSymbol.MultiLineLayers.Add((object)storeHashLine(layer));
                }
                else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
                {
                    IMarkerLineSymbol layer = (IMarkerLineSymbol)symbol.get_Layer(num3);
                    multilayerLineSymbol.MultiLineLayers.Add((object)storeMarkerLine(layer));
                }
                else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
                {
                    ISimpleLineSymbol layer = (ISimpleLineSymbol)symbol.get_Layer(num3);
                    multilayerLineSymbol.MultiLineLayers.Add((object)storeSimpleLine(layer));
                }
                else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
                {
                    IPictureLineSymbol layer = (IPictureLineSymbol)symbol.get_Layer(num3);
                    multilayerLineSymbol.MultiLineLayers.Add((object)storePictureLine(layer));
                }
                else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
                {
                    IMultiLayerLineSymbol layer = (IMultiLayerLineSymbol)symbol.get_Layer(num3);
                    multilayerLineSymbol.MultiLineLayers.Add((object)storeMultilayerLines(layer));
                }
                else if (Operators.CompareString(Left, "false", false) == 0)
                {
                    //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMultilayerLines));
                    //SIMBOLO NON RICONOSCIUTO
                }

                checked { ++num3; }
            }
            return multilayerLineSymbol;
        }


        private string fillSymbolScan(IFillSymbol Symbol)
        {
            if (Symbol is ISimpleFillSymbol)
                return "ISimpleFillSymbol";
            if (Symbol is IMarkerFillSymbol)
                return "IMarkerFillSymbol";
            if (Symbol is ILineFillSymbol)
                return "ILineFillSymbol";
            if (Symbol is IDotDensityFillSymbol)
                return "IDotDensityFillSymbol";
            if (Symbol is IPictureFillSymbol)
                return "IPictureFillSymbol";
            if (Symbol is IGradientFillSymbol)
                return "IGradientFillSymbol";
            return Symbol is IMultiLayerFillSymbol ? "IMultiLayerFillSymbol" : "false";
        }


        private StructSimpleFillSymbol storeSimpleFill(ISimpleFillSymbol symbol)
        {
            StructSimpleFillSymbol simpleFillSymbol = new StructSimpleFillSymbol();
            simpleFillSymbol.Color = (int)symbol.Style != 1 ? gimmeStringForColor(symbol.Color) : "";
            simpleFillSymbol.Style = Conversions.ToString((int)symbol.Style);
            simpleFillSymbol.Transparency = symbol.Color.Transparency;
            string Left = lineSymbolScan(symbol.Outline);
            if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol outline = (ICartographicLineSymbol)symbol.Outline;
                simpleFillSymbol.Outline_CartographicLine = storeCartographicLine(outline);
                simpleFillSymbol.kindOfOutlineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol outline = (IMarkerLineSymbol)symbol.Outline;
                simpleFillSymbol.Outline_MarkerLine = storeMarkerLine(outline);
                simpleFillSymbol.kindOfOutlineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol outline = (IHashLineSymbol)symbol.Outline;
                simpleFillSymbol.Outline_HashLine = storeHashLine(outline);
                simpleFillSymbol.kindOfOutlineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol outline = (ISimpleLineSymbol)symbol.Outline;
                simpleFillSymbol.Outline_SimpleLine = storeSimpleLine(outline);
                simpleFillSymbol.kindOfOutlineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol outline = (IPictureLineSymbol)symbol.Outline;
                simpleFillSymbol.Outline_PictureLine = storePictureLine(outline);
                simpleFillSymbol.kindOfOutlineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol outline = (IMultiLayerLineSymbol)symbol.Outline;
                simpleFillSymbol.Outline_MultiLayerLines = storeMultilayerLines(outline);
                simpleFillSymbol.kindOfOutlineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left, "false", false) == 0)
            {
                //SIMBOLO NON RICONOSCIUTO
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreSimpleFill));

            }

            return simpleFillSymbol;
        }


        private StructMarkerFillSymbol storeMarkerFill(IMarkerFillSymbol symbol)
        {
            StructMarkerFillSymbol markerFillSymbol = new StructMarkerFillSymbol();
            markerFillSymbol.Color = gimmeStringForColor(symbol.Color);
            markerFillSymbol.Transparency = symbol.Color.Transparency;
            markerFillSymbol.GridAngle = symbol.GridAngle;
            string Left1 = markerSymbolScan(symbol.MarkerSymbol);
            if (Operators.CompareString(Left1, "ISimpleMarkerSymbol", false) == 0)
            {
                ISimpleMarkerSymbol markerSymbol = (ISimpleMarkerSymbol)symbol.MarkerSymbol;
                markerFillSymbol.MarkerSymbol_SimpleMarker = storeSimpleMarker(markerSymbol);
                markerFillSymbol.kindOfMarkerStruct = MarkerStructs.StructSimpleMarkerSymbol;
            }
            else if (Operators.CompareString(Left1, "ICharacterMarkerSymbol", false) == 0)
            {
                ICharacterMarkerSymbol markerSymbol = (ICharacterMarkerSymbol)symbol.MarkerSymbol;
                markerFillSymbol.MarkerSymbol_CharacterMarker = storeCharacterMarker(markerSymbol);
                markerFillSymbol.kindOfMarkerStruct = MarkerStructs.StructCharacterMarkerSymbol;
            }
            else if (Operators.CompareString(Left1, "IPictureMarkerSymbol", false) == 0)
            {
                IPictureMarkerSymbol markerSymbol = (IPictureMarkerSymbol)symbol.MarkerSymbol;
                markerFillSymbol.MarkerSymbol_PictureMarker = storePictureMarker(markerSymbol);
                markerFillSymbol.kindOfMarkerStruct = MarkerStructs.StructPictureMarkerSymbol;
            }
            else if (Operators.CompareString(Left1, "IArrowMarkerSymbol", false) == 0)
            {
                IArrowMarkerSymbol markerSymbol = (IArrowMarkerSymbol)symbol.MarkerSymbol;
                markerFillSymbol.MarkerSymbol_ArrowMarker = storeArrowMarker(markerSymbol);
                markerFillSymbol.kindOfMarkerStruct = MarkerStructs.StructArrowMarkerSymbol;
            }
            else if (Operators.CompareString(Left1, "IMultiLayerMarkerSymbol", false) == 0)
            {
                IMultiLayerMarkerSymbol markerSymbol = (IMultiLayerMarkerSymbol)symbol.MarkerSymbol;
                markerFillSymbol.MarkerSymbol_MultilayerMarker = storeMultiLayerMarker(markerSymbol);
                markerFillSymbol.kindOfMarkerStruct = MarkerStructs.StructMultilayerMarkerSymbol;
            }
            else if (Operators.CompareString(Left1, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }

            string Left2 = lineSymbolScan(symbol.Outline);
            if (Operators.CompareString(Left2, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol outline = (ICartographicLineSymbol)symbol.Outline;
                markerFillSymbol.Outline_CartographicLine = storeCartographicLine(outline);
                markerFillSymbol.kindOfOutlineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left2, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol outline = (IMarkerLineSymbol)symbol.Outline;
                markerFillSymbol.Outline_MarkerLine = storeMarkerLine(outline);
                markerFillSymbol.kindOfOutlineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left2, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol outline = (IHashLineSymbol)symbol.Outline;
                markerFillSymbol.Outline_HashLine = storeHashLine(outline);
                markerFillSymbol.kindOfOutlineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left2, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol outline = (ISimpleLineSymbol)symbol.Outline;
                markerFillSymbol.Outline_SimpleLine = storeSimpleLine(outline);
                markerFillSymbol.kindOfOutlineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left2, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol outline = (IPictureLineSymbol)symbol.Outline;
                markerFillSymbol.Outline_PictureLine = storePictureLine(outline);
                markerFillSymbol.kindOfOutlineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left2, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol outline = (IMultiLayerLineSymbol)symbol.Outline;
                markerFillSymbol.Outline_MultiLayerLines = storeMultilayerLines(outline);
                markerFillSymbol.kindOfOutlineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left2, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }

            return markerFillSymbol;
        }


        private StructLineFillSymbol storeLineFill(ILineFillSymbol symbol)
        {
            StructLineFillSymbol structLineFillSymbol = new StructLineFillSymbol();
            structLineFillSymbol.Angle = symbol.Angle;
            structLineFillSymbol.Color = gimmeStringForColor(symbol.Color);
            structLineFillSymbol.Transparency = symbol.Color.Transparency;
            structLineFillSymbol.Offset = symbol.Offset;
            structLineFillSymbol.Separation = symbol.Separation;
            string Left1 = lineSymbolScan(symbol.LineSymbol);
            if (Operators.CompareString(Left1, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol lineSymbol = (ICartographicLineSymbol)symbol.LineSymbol;
                structLineFillSymbol.LineSymbol_CartographicLine = storeCartographicLine(lineSymbol);
                structLineFillSymbol.kindOfLineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left1, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol lineSymbol = (IMarkerLineSymbol)symbol.LineSymbol;
                structLineFillSymbol.LineSymbol_MarkerLine = storeMarkerLine(lineSymbol);
                structLineFillSymbol.kindOfLineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left1, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol lineSymbol = (IHashLineSymbol)symbol.LineSymbol;
                structLineFillSymbol.LineSymbol_HashLine = storeHashLine(lineSymbol);
                structLineFillSymbol.kindOfLineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left1, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol lineSymbol = (ISimpleLineSymbol)symbol.LineSymbol;
                structLineFillSymbol.LineSymbol_SimpleLine = storeSimpleLine(lineSymbol);
                structLineFillSymbol.kindOfLineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left1, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol lineSymbol = (IPictureLineSymbol)symbol.LineSymbol;
                structLineFillSymbol.LineSymbol_PictureLine = storePictureLine(lineSymbol);
                structLineFillSymbol.kindOfLineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left1, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol lineSymbol = (IMultiLayerLineSymbol)symbol.LineSymbol;
                structLineFillSymbol.LineSymbol_MultiLayerLines = storeMultilayerLines(lineSymbol);
                structLineFillSymbol.kindOfLineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left1, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }
            string Left2 = lineSymbolScan(symbol.Outline);
            if (Operators.CompareString(Left2, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol outline = (ICartographicLineSymbol)symbol.Outline;
                structLineFillSymbol.Outline_CartographicLine = storeCartographicLine(outline);
                structLineFillSymbol.kindOfOutlineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left2, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol outline = (IMarkerLineSymbol)symbol.Outline;
                structLineFillSymbol.Outline_MarkerLine = storeMarkerLine(outline);
                structLineFillSymbol.kindOfOutlineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left2, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol outline = (IHashLineSymbol)symbol.Outline;
                structLineFillSymbol.Outline_HashLine = storeHashLine(outline);
                structLineFillSymbol.kindOfOutlineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left2, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol outline = (ISimpleLineSymbol)symbol.Outline;
                structLineFillSymbol.Outline_SimpleLine = storeSimpleLine(outline);
                structLineFillSymbol.kindOfOutlineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left2, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol outline = (IPictureLineSymbol)symbol.Outline;
                structLineFillSymbol.Outline_PictureLine = storePictureLine(outline);
                structLineFillSymbol.kindOfOutlineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left2, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol outline = (IMultiLayerLineSymbol)symbol.Outline;
                structLineFillSymbol.Outline_MultiLayerLines = storeMultilayerLines(outline);
                structLineFillSymbol.kindOfOutlineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left2, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }
            return structLineFillSymbol;
        }


        private StructDotDensityFillSymbol storeDotDensityFill(IDotDensityFillSymbol symbol)
        {
            StructDotDensityFillSymbol densityFillSymbol = new StructDotDensityFillSymbol();
            densityFillSymbol.SymbolList = new ArrayList();
            ISymbolArray isymbolArray = (ISymbolArray)symbol;
            densityFillSymbol.BackgroundColor = gimmeStringForColor(symbol.BackgroundColor);
            densityFillSymbol.BackgroundTransparency = symbol.BackgroundColor.Transparency;
            densityFillSymbol.Color = gimmeStringForColor(symbol.Color);
            densityFillSymbol.Transparency = symbol.Color.Transparency;
            densityFillSymbol.FixedPlacement = symbol.FixedPlacement;
            densityFillSymbol.DotSpacing = symbol.DotSpacing;
            densityFillSymbol.SymbolCount = isymbolArray.SymbolCount;
            int num1 = 0;
            int num2 = checked(isymbolArray.SymbolCount - 1);
            int num3 = num1;
            while (num3 <= num2)
            {
                if (isymbolArray.get_Symbol(num3) is IMarkerSymbol)
                {
                    IMarkerSymbol symbol1 = (IMarkerSymbol)isymbolArray.get_Symbol(num3);
                    densityFillSymbol.SymbolList = new ArrayList();
                    string Left = markerSymbolScan(symbol1);
                    if (Operators.CompareString(Left, "ISimpleMarkerSymbol", false) == 0)
                    {
                        ISimpleMarkerSymbol symbol2 = (ISimpleMarkerSymbol)symbol1;
                        densityFillSymbol.SymbolList.Add((object)storeSimpleMarker(symbol2));
                        densityFillSymbol.SymbolList.Add((object)symbol.get_DotCount(num3));
                    }
                    else if (Operators.CompareString(Left, "ICharacterMarkerSymbol", false) == 0)
                    {
                        ICharacterMarkerSymbol symbol2 = (ICharacterMarkerSymbol)symbol1;
                        densityFillSymbol.SymbolList.Add((object)storeCharacterMarker(symbol2));
                        densityFillSymbol.SymbolList.Add((object)symbol.get_DotCount(num3));
                    }
                    else if (Operators.CompareString(Left, "IPictureMarkerSymbol", false) == 0)
                    {
                        IPictureMarkerSymbol symbol2 = (IPictureMarkerSymbol)symbol1;
                        densityFillSymbol.SymbolList.Add((object)storePictureMarker(symbol2));
                        densityFillSymbol.SymbolList.Add((object)symbol.get_DotCount(num3));
                    }
                    else if (Operators.CompareString(Left, "IArrowMarkerSymbol", false) == 0)
                    {
                        IArrowMarkerSymbol symbol2 = (IArrowMarkerSymbol)symbol1;
                        densityFillSymbol.SymbolList.Add((object)storeArrowMarker(symbol2));
                        densityFillSymbol.SymbolList.Add((object)symbol.get_DotCount(num3));
                    }
                    else if (Operators.CompareString(Left, "IMultiLayerMarkerSymbol", false) == 0)
                    {
                        IMultiLayerMarkerSymbol symbol2 = (IMultiLayerMarkerSymbol)symbol1;
                        densityFillSymbol.SymbolList.Add((object)storeMultiLayerMarker(symbol2));
                        densityFillSymbol.SymbolList.Add((object)symbol.get_DotCount(num3));
                    }
                    else if (Operators.CompareString(Left, "false", false) == 0)
                    {
                        //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                        //SIMBOLO NON TROVATO
                    }
                }
                checked { ++num3; }
            }
            string Left1 = lineSymbolScan(symbol.Outline);
            if (Operators.CompareString(Left1, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol outline = (ICartographicLineSymbol)symbol.Outline;
                densityFillSymbol.Outline_CartographicLine = storeCartographicLine(outline);
                densityFillSymbol.kindOfOutlineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left1, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol outline = (IMarkerLineSymbol)symbol.Outline;
                densityFillSymbol.Outline_MarkerLine = storeMarkerLine(outline);
                densityFillSymbol.kindOfOutlineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left1, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol outline = (IHashLineSymbol)symbol.Outline;
                densityFillSymbol.Outline_HashLine = storeHashLine(outline);
                densityFillSymbol.kindOfOutlineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left1, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol outline = (ISimpleLineSymbol)symbol.Outline;
                densityFillSymbol.Outline_SimpleLine = storeSimpleLine(outline);
                densityFillSymbol.kindOfOutlineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left1, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol outline = (IPictureLineSymbol)symbol.Outline;
                densityFillSymbol.Outline_PictureLine = storePictureLine(outline);
                densityFillSymbol.kindOfOutlineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left1, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol outline = (IMultiLayerLineSymbol)symbol.Outline;
                densityFillSymbol.Outline_MultiLayerLines = storeMultilayerLines(outline);
                densityFillSymbol.kindOfOutlineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left1, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }
            return densityFillSymbol;
        }

        private StructPictureFillSymbol storePictureFill(IPictureFillSymbol symbol)
        {
            StructPictureFillSymbol pictureFillSymbol = new StructPictureFillSymbol();
            pictureFillSymbol.Angle = symbol.Angle;
            pictureFillSymbol.BackgroundColor = gimmeStringForColor(symbol.BackgroundColor);
            pictureFillSymbol.BackgroundTransparency = symbol.BackgroundColor.Transparency;
            pictureFillSymbol.Color = gimmeStringForColor(symbol.Color);
            pictureFillSymbol.Transparency = symbol.Color.Transparency;
            pictureFillSymbol.XScale = symbol.XScale;
            pictureFillSymbol.YScale = symbol.YScale;
            string Left = lineSymbolScan(symbol.Outline);
            pictureFillSymbol.Picture = symbol.Picture;



            if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol outline = (ICartographicLineSymbol)symbol.Outline;
                pictureFillSymbol.Outline_CartographicLine = storeCartographicLine(outline);
                pictureFillSymbol.kindOfOutlineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol outline = (IMarkerLineSymbol)symbol.Outline;
                pictureFillSymbol.Outline_MarkerLine = storeMarkerLine(outline);
                pictureFillSymbol.kindOfOutlineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol outline = (IHashLineSymbol)symbol.Outline;
                pictureFillSymbol.Outline_HashLine = storeHashLine(outline);
                pictureFillSymbol.kindOfOutlineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol outline = (ISimpleLineSymbol)symbol.Outline;
                pictureFillSymbol.Outline_SimpleLine = storeSimpleLine(outline);
                pictureFillSymbol.kindOfOutlineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol outline = (IPictureLineSymbol)symbol.Outline;
                pictureFillSymbol.Outline_PictureLine = storePictureLine(outline);
                pictureFillSymbol.kindOfOutlineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol outline = (IMultiLayerLineSymbol)symbol.Outline;
                pictureFillSymbol.Outline_MultiLayerLines = storeMultilayerLines(outline);
                pictureFillSymbol.kindOfOutlineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }
            return pictureFillSymbol;
        }

        private StructGradientFillSymbol storeGradientFill(IGradientFillSymbol symbol)
        {
            StructGradientFillSymbol gradientFillSymbol = new StructGradientFillSymbol();
            gradientFillSymbol.Color = gimmeStringForColor(symbol.Color);
            gradientFillSymbol.Transparency = symbol.Color.Transparency;
            gradientFillSymbol.Colors = gimmeArrayListForColorRamp(symbol.ColorRamp);
            gradientFillSymbol.GradientAngle = symbol.GradientAngle;
            gradientFillSymbol.GradientPercentage = symbol.GradientPercentage;
            gradientFillSymbol.IntervallCount = symbol.IntervalCount;
            string Left = lineSymbolScan(symbol.Outline);
            if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol outline = (ICartographicLineSymbol)symbol.Outline;
                gradientFillSymbol.Outline_CartographicLine = storeCartographicLine(outline);
                gradientFillSymbol.kindOfOutlineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol outline = (IMarkerLineSymbol)symbol.Outline;
                gradientFillSymbol.Outline_MarkerLine = storeMarkerLine(outline);
                gradientFillSymbol.kindOfOutlineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol outline = (IHashLineSymbol)symbol.Outline;
                gradientFillSymbol.Outline_HashLine = storeHashLine(outline);
                gradientFillSymbol.kindOfOutlineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol outline = (ISimpleLineSymbol)symbol.Outline;
                gradientFillSymbol.Outline_SimpleLine = storeSimpleLine(outline);
                gradientFillSymbol.kindOfOutlineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol outline = (IPictureLineSymbol)symbol.Outline;
                gradientFillSymbol.Outline_PictureLine = storePictureLine(outline);
                gradientFillSymbol.kindOfOutlineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol outline = (IMultiLayerLineSymbol)symbol.Outline;
                gradientFillSymbol.Outline_MultiLayerLines = storeMultilayerLines(outline);
                gradientFillSymbol.kindOfOutlineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }
            return gradientFillSymbol;
        }

        private StructMultilayerFillSymbol storeMultiLayerFill(IMultiLayerFillSymbol symbol)
        {
            StructMultilayerFillSymbol multilayerFillSymbol = new StructMultilayerFillSymbol();
            multilayerFillSymbol.LayerCount = symbol.LayerCount;
            multilayerFillSymbol.MultiFillLayers = new ArrayList();
            int num1 = 0;
            int num2 = checked(symbol.LayerCount - 1);
            int num3 = num1;
            while (num3 <= num2)
            {
                string Left = fillSymbolScan(symbol.get_Layer(num3));
                if (Operators.CompareString(Left, "ISimpleFillSymbol", false) == 0)
                {
                    ISimpleFillSymbol layer = (ISimpleFillSymbol)symbol.get_Layer(num3);
                    multilayerFillSymbol.MultiFillLayers.Add((object)storeSimpleFill(layer));
                }
                else if (Operators.CompareString(Left, "IMarkerFillSymbol", false) == 0)
                {
                    IMarkerFillSymbol layer = (IMarkerFillSymbol)symbol.get_Layer(num3);
                    multilayerFillSymbol.MultiFillLayers.Add((object)storeMarkerFill(layer));
                }
                else if (Operators.CompareString(Left, "ILineFillSymbol", false) == 0)
                {
                    ILineFillSymbol layer = (ILineFillSymbol)symbol.get_Layer(num3);
                    multilayerFillSymbol.MultiFillLayers.Add((object)storeLineFill(layer));
                }
                else if (Operators.CompareString(Left, "IPictureFillSymbol", false) == 0)
                {
                    IPictureFillSymbol layer = (IPictureFillSymbol)symbol.get_Layer(num3);
                    multilayerFillSymbol.MultiFillLayers.Add((object)storePictureFill(layer));
                }
                else if (Operators.CompareString(Left, "IDotDensityFillSymbol", false) == 0)
                {
                    IDotDensityFillSymbol layer = (IDotDensityFillSymbol)symbol.get_Layer(num3);
                    multilayerFillSymbol.MultiFillLayers.Add((object)storeDotDensityFill(layer));
                }
                else if (Operators.CompareString(Left, "IGradientFillSymbol", false) == 0)
                {
                    IGradientFillSymbol layer = (IGradientFillSymbol)symbol.get_Layer(num3);
                    multilayerFillSymbol.MultiFillLayers.Add((object)storeGradientFill(layer));
                }
                else if (Operators.CompareString(Left, "IMultiLayerFillSymbol", false) == 0)
                {
                    IMultiLayerFillSymbol symbol1 = symbol;
                    multilayerFillSymbol.MultiFillLayers.Add((object)storeMultiLayerFill(symbol1));
                }
                else if (Operators.CompareString(Left, "false", false) == 0)
                {
                    //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                    //SIMBOLO NON TROVATO
                }
                checked { ++num3; }
            }
            return multilayerFillSymbol;
        }


        private ArrayList gimmeArrayListForColorRamp(IColorRamp ColorRamp)
        {
            IEnumColors colors = ColorRamp.Colors;
            int num1 = 0;
            int num2 = checked(ColorRamp.Size - 1);
            int num3 = num1;
            ArrayList arrayList = null;

            while (num3 <= num2)
            {
                arrayList.Add((object)gimmeStringForColor(colors.Next()));
                checked { ++num3; }
            }
            return arrayList;
        }


        private string IIIDChartSymbolScan(I3DChartSymbol Symbol)
        {
            if (Symbol is IBarChartSymbol)
                return "IBarChartSymbol";
            if (Symbol is IPieChartSymbol)
                return "IPieChartSymbol";
            return Symbol is IStackedChartSymbol ? "IStackedChartSymbol" : "false";
        }

        private StructBarChartSymbol storeBarChart(IBarChartSymbol symbol)
        {
            StructBarChartSymbol structBarChartSymbol = new StructBarChartSymbol();
            structBarChartSymbol.ShowAxes = symbol.ShowAxes;
            structBarChartSymbol.Spacing = symbol.Spacing;
            structBarChartSymbol.VerticalBars = symbol.VerticalBars;
            structBarChartSymbol.Width = symbol.Width;
            string Left = lineSymbolScan(symbol.Axes);
            if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol axes = (ICartographicLineSymbol)symbol.Axes;
                structBarChartSymbol.Axes_CartographicLine = storeCartographicLine(axes);
                structBarChartSymbol.kindOfAxeslineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol axes = (IMarkerLineSymbol)symbol.Axes;
                structBarChartSymbol.Axes_MarkerLine = storeMarkerLine(axes);
                structBarChartSymbol.kindOfAxeslineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol axes = (IHashLineSymbol)symbol.Axes;
                structBarChartSymbol.Axes_HashLine = storeHashLine(axes);
                structBarChartSymbol.kindOfAxeslineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol axes = (ISimpleLineSymbol)symbol.Axes;
                structBarChartSymbol.Axes_SimpleLine = storeSimpleLine(axes);
                structBarChartSymbol.kindOfAxeslineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol axes = (IPictureLineSymbol)symbol.Axes;
                structBarChartSymbol.Axes_PictureLine = storePictureLine(axes);
                structBarChartSymbol.kindOfAxeslineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol axes = (IMultiLayerLineSymbol)symbol.Axes;
                structBarChartSymbol.Axes_MultiLayerLines = storeMultilayerLines(axes);
                structBarChartSymbol.kindOfAxeslineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }
            return structBarChartSymbol;
        }

        private StructPieChartSymbol storePieChart(IPieChartSymbol symbol)
        {
            StructPieChartSymbol structPieChartSymbol = new StructPieChartSymbol();
            structPieChartSymbol.Clockwise = symbol.Clockwise;
            structPieChartSymbol.UseOutline = symbol.UseOutline;
            string Left = lineSymbolScan(symbol.Outline);
            if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol outline = (ICartographicLineSymbol)symbol.Outline;
                structPieChartSymbol.Outline_CartographicLine = storeCartographicLine(outline);
                structPieChartSymbol.kindOfOutlineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol outline = (IMarkerLineSymbol)symbol.Outline;
                structPieChartSymbol.Outline_MarkerLine = storeMarkerLine(outline);
                structPieChartSymbol.kindOfOutlineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol outline = (IHashLineSymbol)symbol.Outline;
                structPieChartSymbol.Outline_HashLine = storeHashLine(outline);
                structPieChartSymbol.kindOfOutlineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol outline = (ISimpleLineSymbol)symbol.Outline;
                structPieChartSymbol.Outline_SimpleLine = storeSimpleLine(outline);
                structPieChartSymbol.kindOfOutlineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol outline = (IPictureLineSymbol)symbol.Outline;
                structPieChartSymbol.Outline_PictureLine = storePictureLine(outline);
                structPieChartSymbol.kindOfOutlineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol outline = (IMultiLayerLineSymbol)symbol.Outline;
                structPieChartSymbol.Outline_MultiLayerLines = storeMultilayerLines(outline);
                structPieChartSymbol.kindOfOutlineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }
            return structPieChartSymbol;
        }

        private StructStackedChartSymbol storeStackedChart(IStackedChartSymbol symbol)
        {
            StructStackedChartSymbol stackedChartSymbol = new StructStackedChartSymbol();
            stackedChartSymbol.Fixed = symbol.Fixed;
            stackedChartSymbol.UseOutline = symbol.UseOutline;
            stackedChartSymbol.VerticalBar = symbol.VerticalBar;
            stackedChartSymbol.Width = symbol.Width;
            string Left = lineSymbolScan(symbol.Outline);
            if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
            {
                ICartographicLineSymbol outline = (ICartographicLineSymbol)symbol.Outline;
                stackedChartSymbol.Outline_CartographicLine = storeCartographicLine(outline);
                stackedChartSymbol.kindOfOutlineStruct = LineStructs.StructCartographicLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
            {
                IMarkerLineSymbol outline = (IMarkerLineSymbol)symbol.Outline;
                stackedChartSymbol.Outline_MarkerLine = storeMarkerLine(outline);
                stackedChartSymbol.kindOfOutlineStruct = LineStructs.StructMarkerLineSymbol;
            }
            else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
            {
                IHashLineSymbol outline = (IHashLineSymbol)symbol.Outline;
                stackedChartSymbol.Outline_HashLine = storeHashLine(outline);
                stackedChartSymbol.kindOfOutlineStruct = LineStructs.StructHashLineSymbol;
            }
            else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
            {
                ISimpleLineSymbol outline = (ISimpleLineSymbol)symbol.Outline;
                stackedChartSymbol.Outline_SimpleLine = storeSimpleLine(outline);
                stackedChartSymbol.kindOfOutlineStruct = LineStructs.StructSimpleLineSymbol;
            }
            else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
            {
                IPictureLineSymbol outline = (IPictureLineSymbol)symbol.Outline;
                stackedChartSymbol.Outline_PictureLine = storePictureLine(outline);
                stackedChartSymbol.kindOfOutlineStruct = LineStructs.StructPictureLineSymbol;
            }
            else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
            {
                IMultiLayerLineSymbol outline = (IMultiLayerLineSymbol)symbol.Outline;
                stackedChartSymbol.Outline_MultiLayerLines = storeMultilayerLines(outline);
                stackedChartSymbol.kindOfOutlineStruct = LineStructs.StructMultilayerLineSymbol;
            }
            else if (Operators.CompareString(Left, "false", false) == 0)
            {
                //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", nameof(StoreMarkerFill));
                //SIMBOLO NON TROVATO
            }
            return stackedChartSymbol;
        }

        //internal bool SimpleRendererPolygon()
        //{
        //    try
        //    {
        //        selectedLayer = ArcMap.Document.SelectedLayer;
        //        dataset = (IDataset)selectedLayer;
        //        featureLayer = (IFeatureLayer)selectedLayer;
        //        geoFeatureLayer = (IGeoFeatureLayer)featureLayer;
        //        featureRenderer = geoFeatureLayer.Renderer;

        //        rendererID = geoFeatureLayer.RendererPropertyPageClassID.Value.ToString();

        //        String ok = string.Empty;

        //        if (geoFeatureLayer.Renderer is IUniqueValueRenderer)
        //        {
        //            IUniqueValueRenderer unique = (IUniqueValueRenderer)featureRenderer;
        //            strutturaLayer.LayerList = new ArrayList();
        //            strutturaLayer.LayerList.Add((object)StoreStructUVRenderer((IUniqueValueRenderer)geoFeatureLayer.Renderer, featureLayer));
        //            addOneToLayerNumber();
        //            scriviSLD(strutturaLayer);
        //        }
           

        //        if (featureClass.ShapeType == esriGeometryType.esriGeometryPoint)
        //        {

        //            return false;
        //        }

        //        if (featureClass.ShapeType == esriGeometryType.esriGeometryPolyline)
        //        {

        //            return false;
        //        }

        //        if (featureClass.ShapeType == esriGeometryType.esriGeometryPolygon)
        //        {
        //            if (rendererID == UrbamidResource.SimpleRenderer || rendererID == UrbamidResource.NullRenderer)
        //            {
        //                simpleRenderer = (ISimpleRenderer)featureRenderer;

        //                symbol = simpleRenderer.Symbol;
        //                fillSymbol = (IFillSymbol)symbol;

        //                fillColor = ColorTranslator.FromOle(fillSymbol.Color.RGB);
        //                fillColorHEX = String.Format("#{0:X6}", fillColor.ToArgb() & 0x00FFFFFF);

        //                outlineColor = ColorTranslator.FromOle(fillSymbol.Outline.Color.RGB);
        //                outlineColorHEX = String.Format("#{0:X6}", outlineColor.ToArgb() & 0x00FFFFFF);

        //                outlineWidth = fillSymbol.Outline.Width.ToString().Replace(",", ".");

        //                return true;
        //            }
        //            else if (rendererID == UrbamidResource.ClassBreaksRenderer)
        //            {
        //                IClassBreaksRenderer breaksRenderer = (IClassBreaksRenderer)featureRenderer;

        //                symbol = breaksRenderer.Symbol[0];


        //                fillSymbol = (IFillSymbol)symbol;

        //                fillColor = ColorTranslator.FromOle(fillSymbol.Color.RGB);
        //                fillColorHEX = String.Format("#{0:X6}", fillColor.ToArgb() & 0x00FFFFFF);

        //                outlineColor = ColorTranslator.FromOle(fillSymbol.Outline.Color.RGB);
        //                outlineColorHEX = String.Format("#{0:X6}", outlineColor.ToArgb() & 0x00FFFFFF);

        //                outlineWidth = fillSymbol.Outline.Width.ToString().Replace(",", ".");

        //            }
        //            else
        //            {

        //                IUniqueValueRenderer unique = (IUniqueValueRenderer)featureRenderer;
        //                symbol = unique.Symbol["0"];


        //            }

        //        }

        //        return false;




        //    }
        //    catch (Exception ex)
        //    {


        //        throw ex;
        //    }
        //}

        //private void scriviSLD(StructProject strutturaLayer)
        //{
        //    bool flag1 = false;
        //    bool flag2 = false;

        //    Utility util = new UrbamidAddIn.Utility();
        //    String nomeFile = @"C:\Temp\geoserver\style.sld";
           
        //    try
        //    {
        //        this.m_objXMLHandle = new XMLHandle(nomeFile);
        //        this.m_objXMLHandle.CreateNewFile(true);

        //        int num1 = 0;
        //        int num2 = checked(strutturaLayer.LayerCount - 1);
        //        int index1 = num1;
        //        while (index1 <= num2)
        //        {
        //            string str1 = NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "LayerName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString();

        //            this.m_objXMLHandle.CreateElement("NamedLayer");
        //            this.m_objXMLHandle.CreateElement("LayerName");
        //            String valElemento = NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString();
        //            this.m_objXMLHandle.SetElementText(valElemento);
        //            this.m_objXMLHandle.CreateElement("UserStyle");
        //            this.m_objXMLHandle.CreateElement("StyleName");
        //            this.m_objXMLHandle.SetElementText(nomeFileStyle);
        //            this.m_objXMLHandle.CreateElement("FeatureTypeStyle");
        //            this.m_objXMLHandle.CreateElement("FeatureTypeName");
        //            this.m_objXMLHandle.SetElementText(nomeFile);
        //            //this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());

        //            ArrayList arrayList1 = (ArrayList)NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "SymbolList", new object[0], (string[])null, (Type[])null, (bool[])null);
        //            int num3 = 0;
        //            int num4 = checked(arrayList1.Count - 1);
        //            int index2 = num3;
        //            while (index2 <= num4)
        //            {
        //                int num5;
        //                int num6;

        //                if (strutturaLayer.LayerList[index1] is StructUniqueValueRenderer)
        //                {
        //                    object layer = strutturaLayer.LayerList[index1];
        //                    StructUniqueValueRenderer uniqueValueRenderer1 = new StructUniqueValueRenderer();
        //                    StructUniqueValueRenderer uniqueValueRenderer2 = layer != null ? (StructUniqueValueRenderer)layer : uniqueValueRenderer1;
        //                    this.m_objXMLHandle.CreateElement("Rule");
        //                    this.m_objXMLHandle.CreateElement("RuleName");
        //                    this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
        //                    this.m_objXMLHandle.CreateElement("Title");
        //                    this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
        //                    this.m_objXMLHandle.CreateElement("Filter");

        //                    ArrayList arrayList2 = (ArrayList)NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Fieldvalues", new object[0], (string[])null, (Type[])null, (bool[])null);
        //                    if (uniqueValueRenderer2.FieldCount > 1)
        //                    {
        //                        this.m_objXMLHandle.CreateElement("And");
        //                        int num7 = 0;
        //                        int num8 = checked(uniqueValueRenderer2.FieldCount - 1);
        //                        int index3 = num7;
        //                        while (index3 <= num8)
        //                        {
        //                            XMLHandle objXmlHandle = this.m_objXMLHandle;
        //                            objXmlHandle.CreateElement("PropertyIsEqualTo");
        //                            objXmlHandle.CreateElement("PropertyName");
        //                            objXmlHandle.SetElementText(uniqueValueRenderer2.FieldNames[index3].ToString());
        //                            objXmlHandle.CreateElement("Fieldvalue");
        //                            objXmlHandle.SetElementText(arrayList2[index3].ToString());
        //                            checked { ++index3; }
        //                        }
        //                    }
        //                    else if (uniqueValueRenderer2.FieldCount == 1)
        //                    {
        //                        if (arrayList2.Count > 1)
        //                            this.m_objXMLHandle.CreateElement("Or");
        //                        int num7 = 0;
        //                        int num8 = checked(arrayList2.Count - 1);
        //                        int index3 = num7;
        //                        while (index3 <= num8)
        //                        {
        //                            XMLHandle objXmlHandle = this.m_objXMLHandle;
        //                            objXmlHandle.CreateElement("PropertyIsEqualTo");
        //                            objXmlHandle.CreateElement("PropertyName");
        //                            objXmlHandle.SetElementText(uniqueValueRenderer2.FieldNames[0].ToString());
        //                            objXmlHandle.CreateElement("Fieldvalue");
        //                            objXmlHandle.SetElementText(arrayList2[index3].ToString());
        //                            checked { ++index3; }
        //                        }
        //                    }
        //                    switch (uniqueValueRenderer2.FeatureCls)
        //                    {
        //                        case Feature.PointFeature:
        //                            writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                        case Feature.LineFeature:
        //                            writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                        case Feature.PolygonFeature:
        //                            writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                    }
        //                }
        //                else if (strutturaLayer.LayerList[index1] is StructClassBreaksRenderer)
        //                {
        //                    object layer = strutturaLayer.LayerList[index1];
        //                    StructClassBreaksRenderer classBreaksRenderer1 = new UrbamidAddIn.ConvertLayerToSld.StructClassBreaksRenderer();
        //                    StructClassBreaksRenderer classBreaksRenderer2 = layer != null ? (StructClassBreaksRenderer)layer : classBreaksRenderer1;
        //                    this.m_objXMLHandle.CreateElement("Rule");
        //                    this.m_objXMLHandle.CreateElement("RuleName");
        //                    this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
        //                    this.m_objXMLHandle.CreateElement("Title");
        //                    this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "Label", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
        //                    this.m_objXMLHandle.CreateElement("Filter");

        //                    this.m_objXMLHandle.CreateElement("PropertyIsBetween");
        //                    this.m_objXMLHandle.CreateElement("PropertyName");
        //                    this.m_objXMLHandle.SetElementText(classBreaksRenderer2.FieldName);
        //                    this.m_objXMLHandle.CreateElement("LowerBoundary");
        //                    this.m_objXMLHandle.CreateElement("Fieldvalue");
        //                    this.m_objXMLHandle.SetElementText(this.CommaToPoint(Conversions.ToDouble(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "LowerLimit", new object[0], (string[])null, (Type[])null, (bool[])null))));
        //                    this.m_objXMLHandle.CreateElement("UpperBoundary");
        //                    this.m_objXMLHandle.CreateElement("Fieldvalue");
        //                    this.m_objXMLHandle.SetElementText(this.CommaToPoint(Conversions.ToDouble(NewLateBinding.LateGet(arrayList1[index2], (Type)null, "UpperLimit", new object[0], (string[])null, (Type[])null, (bool[])null))));
        //                    switch (classBreaksRenderer2.FeatureCls)
        //                    {
        //                        case Feature.PointFeature:
        //                            writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                        case Feature.LineFeature:
        //                            writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                        case Feature.PolygonFeature:
        //                            writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                    }
        //                }
        //                else if (strutturaLayer.LayerList[index1] is StructSimpleRenderer)
        //                {
        //                    object layer = strutturaLayer.LayerList[index1];
        //                    StructSimpleRenderer structSimpleRenderer1 = new UrbamidAddIn.ConvertLayerToSld.StructSimpleRenderer();
        //                    StructSimpleRenderer structSimpleRenderer2 = layer != null ? (StructSimpleRenderer)layer : structSimpleRenderer1;
        //                    this.m_objXMLHandle.CreateElement("Rule");
        //                    this.m_objXMLHandle.CreateElement("RuleName");
        //                    this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
        //                    this.m_objXMLHandle.CreateElement("Title");
        //                    this.m_objXMLHandle.SetElementText(NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "DatasetName", new object[0], (string[])null, (Type[])null, (bool[])null).ToString());
        //                    switch (structSimpleRenderer2.FeatureCls)
        //                    {
        //                        case Feature.PointFeature:
        //                            writePointFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                        case Feature.LineFeature:
        //                            writeLineFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                        case Feature.PolygonFeature:
        //                            writePolygonFeatures(RuntimeHelpers.GetObjectValue(arrayList1[index2]));
        //                            break;
        //                    }
        //                    object obj = NewLateBinding.LateGet(strutturaLayer.LayerList[index1], (Type)null, "Annotation", new object[0], (string[])null, (Type[])null, (bool[])null);
        //                    StructAnnotation structAnnotation = new StructAnnotation();
        //                    writeAnnotation(obj != null ? (StructAnnotation)obj : structAnnotation);
        //                }
        //                checked { ++index2; }
        //            }
        //            if (!flag1)
        //                this.m_objXMLHandle.SaveDoc();
        //            checked { ++index1; }
        //        }
        //        if (flag1)
        //            this.m_objXMLHandle.SaveDoc();
        //        flag2 = true;
        //    }
        //    catch (Exception ex)
        //    {
        //        ProjectData.SetProjectError(ex);
        //        Exception exception = ex;
        //        //this.ErrorMsg("Konnte die SLD nicht schreiben", exception.Message, exception.StackTrace, nameof(WriteToSLD));
        //        flag2 = false;
        //        ProjectData.ClearProjectError();
        //    }

        //}

     

        private bool writePointFeatures(object Symbol)
        {
            bool flag;
            try
            {
                int num1 = 1;
                if (Symbol is StructMultilayerMarkerSymbol)
                {
                    object obj = Symbol;
                    StructMultilayerMarkerSymbol multilayerMarkerSymbol = new StructMultilayerMarkerSymbol();
                    num1 = (obj != null ? (StructMultilayerMarkerSymbol)obj : multilayerMarkerSymbol).LayerCount;
                }
                int num2 = 0;
                int num3 = checked(num1 - 1);
                //int LayerIdx = num2;
                //while (LayerIdx <= num3)
                for (int i = num3; i >= 0; i--)
                {
                    XMLHandle objXmlHandle = this.m_objXMLHandle;
                    objXmlHandle.CreateElement("PointSymbolizer");
                    objXmlHandle.CreateElement("PointGraphic");
                    objXmlHandle.CreateElement("Mark");
                    objXmlHandle.CreateElement("PointWellKnownName");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("WellKnownName", RuntimeHelpers.GetObjectValue(Symbol), i));
                    if (Operators.CompareString(getValueFromSymbolstruct("PointColor", RuntimeHelpers.GetObjectValue(Symbol), i), "", false) != 0)
                    {
                        objXmlHandle.CreateElement("PointFill");
                        objXmlHandle.CreateElement("PointFillCssParameter");
                        objXmlHandle.CreateAttribute("name");
                        objXmlHandle.SetAttributeValue("fill");
                        objXmlHandle.SetElementText(getValueFromSymbolstruct("PointColor", RuntimeHelpers.GetObjectValue(Symbol), i));
                        objXmlHandle.CreateElement("PointFillCssParameter");
                        objXmlHandle.CreateAttribute("name");
                        objXmlHandle.SetAttributeValue("fill-opacity");
                        objXmlHandle.SetElementText("1.0");
                    }
                    if (Operators.CompareString(getValueFromSymbolstruct("PointOutlineColor", RuntimeHelpers.GetObjectValue(Symbol), i), "", false) != 0 & Operators.CompareString(getValueFromSymbolstruct("PointOutlineSize", RuntimeHelpers.GetObjectValue(Symbol), i), "0", false) != 0)
                    {
                        objXmlHandle.CreateElement("PointStroke");
                        objXmlHandle.CreateElement("PointStrokeCssParameter");
                        objXmlHandle.CreateAttribute("name");
                        objXmlHandle.SetAttributeValue("stroke");
                        objXmlHandle.SetElementText(getValueFromSymbolstruct("PointOutlineColor", RuntimeHelpers.GetObjectValue(Symbol), i));
                        objXmlHandle.CreateElement("PointStrokeCssParameter");
                        objXmlHandle.CreateAttribute("name");
                        objXmlHandle.SetAttributeValue("stroke-width");
                        objXmlHandle.SetElementText(getValueFromSymbolstruct("PointOutlineSize", RuntimeHelpers.GetObjectValue(Symbol), i));
                        objXmlHandle.CreateElement("PointStrokeCssParameter");
                        objXmlHandle.CreateAttribute("name");
                        objXmlHandle.SetAttributeValue("stroke-opacity");
                        objXmlHandle.SetElementText("1.0");
                    }
                    objXmlHandle.CreateElement("PointSize");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PointSize", RuntimeHelpers.GetObjectValue(Symbol), i));
                    objXmlHandle.CreateElement("PointRotation");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PointRotation", RuntimeHelpers.GetObjectValue(Symbol), i));
                    //checked { ++LayerIdx; }
                }
                flag = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Schreiben der Schraffur", exception.Message, exception.StackTrace, nameof(writePointFeatures));
                flag = false;
                ProjectData.ClearProjectError();
            }
            return flag;
        }

        private bool writeLineFeatures(object Symbol)
        {
            bool flag;
            try
            {
                int num1 = 1;
                if (Symbol is StructMultilayerLineSymbol)
                {
                    object obj = Symbol;
                    StructMultilayerLineSymbol multilayerLineSymbol = new ConvertLayerToSld.StructMultilayerLineSymbol();
                    num1 = (obj != null ? (StructMultilayerLineSymbol)obj : multilayerLineSymbol).LayerCount;
                }
                int num2 = 0;
                int num3 = checked(num1 - 1);
                //int LayerIdx = num2;
                int Layeridx = num3;
                //while (LayerIdx <= num3)
                StructMultilayerLineSymbol strut = new StructMultilayerLineSymbol();
                if (Symbol is StructMultilayerLineSymbol)
                    strut = (StructMultilayerLineSymbol)Symbol;

                if (!strut.Equals(default(StructMultilayerLineSymbol)))
                {
                    for (int i = num3; i >= 0; i--)
                    {
                        if (strut.MultiLineLayers[i] is StructMultilayerMarkerSymbol)
                        {
                            StructMultilayerMarkerSymbol maker = ((ConvertLayerToSld.StructMarkerLineSymbol)(strut.MultiLineLayers[i])).MarkerSymbol_MultilayerMarker;
                            writePointFeatures(maker);
                        }
                        else
                        {

                            XMLHandle objXmlHandle = this.m_objXMLHandle;
                            this.m_objXMLHandle.CreateElement("LineSymbolizer");
                            objXmlHandle.CreateElement("LineStroke");
                            objXmlHandle.CreateElement("LineCssParameter");
                            objXmlHandle.CreateAttribute("name");
                            objXmlHandle.SetAttributeValue("stroke");
                            objXmlHandle.SetElementText(getValueFromSymbolstruct("LineColor", strut.MultiLineLayers[i]));
                            objXmlHandle.CreateElement("LineCssParameter");
                            objXmlHandle.CreateAttribute("name");
                            objXmlHandle.SetAttributeValue("stroke-width");
                            objXmlHandle.SetElementText(getValueFromSymbolstruct("LineWidth", strut.MultiLineLayers[i]));
                            objXmlHandle.CreateElement("LineCssParameter");
                            objXmlHandle.CreateAttribute("name");
                            objXmlHandle.SetAttributeValue("stroke-opacity");
                            objXmlHandle.SetElementText(getValueFromSymbolstruct("LineOpacity", strut.MultiLineLayers[i]));
                            String valDashArray = getValueFromSymbolstruct("LineDashArray", strut.MultiLineLayers[i]);
                            if (!String.IsNullOrEmpty(valDashArray) && valDashArray != "0")
                            {
                                objXmlHandle.CreateElement("LineCssParameter");
                                objXmlHandle.CreateAttribute("name");
                                objXmlHandle.SetAttributeValue("stroke-dasharray");
                                objXmlHandle.SetElementText(getValueFromSymbolstruct("LineDashArray", RuntimeHelpers.GetObjectValue(Symbol), i));
                            }

                            /*ASTERISCO*////
                            if (strut.MultiLineLayers[i] is StructCartographicLineSymbol)
                            {
                                object obj = strut.MultiLineLayers[i];
                                StructCartographicLineSymbol cartographicLineSymbol1 = new StructCartographicLineSymbol();
                                StructCartographicLineSymbol cartographicLineSymbol2 = obj != null ? (StructCartographicLineSymbol)obj : cartographicLineSymbol1;

                                String str12 = "";
                                int indice = 0;
                                int countArr = checked(cartographicLineSymbol2.DashArray.Count - 1);
                                int index = indice;
                                while (index <= countArr)
                                {
                                    if (index > 0)
                                        str12 += " ";


                                    object ddd = cartographicLineSymbol2.DashArray[index];
                                    if (ddd is SimpleLineDecorationElement)
                                    {
                                        SimpleLineDecorationElement element = (SimpleLineDecorationElement)ddd;
                                        objXmlHandle.CreateElement("LineSymbolizer");
                                        objXmlHandle.CreateElement("LineStroke");
                                        objXmlHandle.CreateElement("LineGraphicStroke");
                                        objXmlHandle.CreateElement("LineGraphic");
                                        objXmlHandle.CreateElement("LineMark");
                                        objXmlHandle.CreateElement("LineWellKnownName");
                                        objXmlHandle.SetElementText("shape://oarrow");
                                        objXmlHandle.CreateElement("LineStrokeMark");
                                        objXmlHandle.CreateElement("LineStrokeCssParameter");
                                        objXmlHandle.CreateAttribute("name");
                                        objXmlHandle.SetAttributeValue("stroke");
                                        objXmlHandle.SetElementText(gimmeStringForColor(element.MarkerSymbol.Color));
                                        objXmlHandle.CreateElement("LineStrokeCssParameter");
                                        objXmlHandle.CreateAttribute("name");
                                        objXmlHandle.SetAttributeValue("stroke-width");
                                        if (element.MarkerSymbol.Size > 2)
                                            objXmlHandle.SetElementText(CommaToPoint(element.MarkerSymbol.Size / 2));
                                        else
                                            objXmlHandle.SetElementText(CommaToPoint(element.MarkerSymbol.Size));

                                        //objXmlHandle.CreateAttribute("name");
                                        //objXmlHandle.SetAttributeValue("stroke-width");
                                        //objXmlHandle.SetElementText(CommaToPoint(element.MarkerSymbol.Size));
                                        //objXmlHandle.CreateElement("LineStrokeCssParameter");
                                        //objXmlHandle.CreateAttribute("name");
                                        //objXmlHandle.SetAttributeValue("stroke-opacity");
                                        //objXmlHandle.SetElementText("1.0");



                                            //for (int z = 0; z < element.PositionCount; z++)
                                            //{



                                            //bool flipAll = element.FlipAll;
                                            //bool flipFirst=  element.FlipFirst;
                                            //bool posRatio = element.PositionAsRatio;
                                            //int osCount = element.PositionCount;
                                            //bool rotate =element.Rotate;
                                            //List<double> listaPos = new List<double>();


                                            //    listaPos.Add(element.Position[z]);

                                            //double markAngolo =  element.MarkerSymbol.Angle;
                                            //string markColor = gimmeStringForColor(element.MarkerSymbol.Color);
                                            //double markSize =  element.MarkerSymbol.Size;
                                            //double markXOffSet = element.MarkerSymbol.XOffset;
                                            //double markYOffSet = element.MarkerSymbol.YOffset;
                                            //}

                                            //writeSlopedHatching(element.MarkerSymbol);
                                    }


                                    //double num3 = Conversions.ToDouble(cartographicLineSymbol2.DashArray[index]);
                                    str12 += this.CommaToPoint(num3);
                                    checked { ++index; }
                                }


                            }
                        }
                    }
                }
                else
                {
                    for (int i = num3; i >= 0; i--)
                    {
                        if (Operators.CompareString(getValueFromSymbolstruct("LineColor", RuntimeHelpers.GetObjectValue(Symbol), i), "", false) != 0 & Operators.CompareString(getValueFromSymbolstruct("LineWidth", RuntimeHelpers.GetObjectValue(Symbol), i), "0", false) != 0)
                        {
                            XMLHandle objXmlHandle = this.m_objXMLHandle;
                            this.m_objXMLHandle.CreateElement("LineSymbolizer");
                            objXmlHandle.CreateElement("LineStroke");
                            objXmlHandle.CreateElement("LineCssParameter");
                            objXmlHandle.CreateAttribute("name");
                            objXmlHandle.SetAttributeValue("stroke");
                            objXmlHandle.SetElementText(getValueFromSymbolstruct("LineColor", RuntimeHelpers.GetObjectValue(Symbol), i));
                            objXmlHandle.CreateElement("LineCssParameter");
                            objXmlHandle.CreateAttribute("name");
                            objXmlHandle.SetAttributeValue("stroke-width");
                            objXmlHandle.SetElementText(getValueFromSymbolstruct("LineWidth", RuntimeHelpers.GetObjectValue(Symbol), i));
                            objXmlHandle.CreateElement("LineCssParameter");
                            objXmlHandle.CreateAttribute("name");
                            objXmlHandle.SetAttributeValue("stroke-opacity");
                            objXmlHandle.SetElementText(getValueFromSymbolstruct("LineOpacity", RuntimeHelpers.GetObjectValue(Symbol), i));
                            if (Operators.CompareString(getValueFromSymbolstruct("LineDashArray", RuntimeHelpers.GetObjectValue(Symbol), i), "", false) != 0)
                            {
                                objXmlHandle.CreateElement("LineCssParameter");
                                objXmlHandle.CreateAttribute("name");
                                objXmlHandle.SetAttributeValue("stroke-dasharray");
                                objXmlHandle.SetElementText(getValueFromSymbolstruct("LineDashArray", RuntimeHelpers.GetObjectValue(Symbol), i));
                            }
                        }

                    
                    }
                }
                flag = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Schreiben der Schraffur", exception.Message, exception.StackTrace, nameof(WriteLineFeatures));
                flag = false;
                ProjectData.ClearProjectError();
            }
            return flag;
        }

        private bool writePolygonFeatures(object Symbol)
        {
            //short num1 = 0;
            bool flag;
            try
            {
                if (Symbol is StructSimpleFillSymbol)
                    writeSolidFill(RuntimeHelpers.GetObjectValue(Symbol));
                else if (Symbol is StructMarkerFillSymbol)
                {
                    StructMarkerFillSymbol tipo2 = (StructMarkerFillSymbol)Symbol;

                    switch (tipo2.kindOfMarkerStruct)
                    {
                        case MarkerStructs.StructSimpleMarkerSymbol:
                            break;
                        case MarkerStructs.StructCharacterMarkerSymbol:
                            break;
                        case MarkerStructs.StructPictureMarkerSymbol:
                            break;
                        case MarkerStructs.StructArrowMarkerSymbol:
                            break;
                        case MarkerStructs.StructMultilayerMarkerSymbol:
                            break;
                        default:
                            break;
                    }

                    writeMarkerFill(RuntimeHelpers.GetObjectValue(Symbol));

                
                }    
                else if (Symbol is StructLineFillSymbol)
                {
                    if (Conversions.ToBoolean(Operators.AndObject(Operators.CompareObjectGreater(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)22.5, false), Operators.CompareObjectLess(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)67.5, false))))
                        writeSlopedHatching(RuntimeHelpers.GetObjectValue(Symbol));
                    else if (Conversions.ToBoolean(Operators.AndObject(Operators.CompareObjectGreater(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)67.5, false), Operators.CompareObjectLess(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)112.5, false))))
                        writePerpendicularHatching(RuntimeHelpers.GetObjectValue(Symbol));
                    else if (Conversions.ToBoolean(Operators.AndObject(Operators.CompareObjectGreater(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)112.5, false), Operators.CompareObjectLess(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)157.5, false))))
                        writeSlopedHatching(RuntimeHelpers.GetObjectValue(Symbol));
                    else if (Conversions.ToBoolean(Operators.AndObject(Operators.CompareObjectGreater(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)157.5, false), Operators.CompareObjectLess(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)202.5, false))))
                        writePerpendicularHatching(RuntimeHelpers.GetObjectValue(Symbol));
                    else if (Conversions.ToBoolean(Operators.AndObject(Operators.CompareObjectGreater(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)202.5, false), Operators.CompareObjectLess(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)247.5, false))))
                        writeSlopedHatching(RuntimeHelpers.GetObjectValue(Symbol));
                    else if (Conversions.ToBoolean(Operators.AndObject(Operators.CompareObjectGreater(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)247.5, false), Operators.CompareObjectLess(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)292.5, false))))
                        writePerpendicularHatching(RuntimeHelpers.GetObjectValue(Symbol));
                    else if (Conversions.ToBoolean(Operators.AndObject(Operators.CompareObjectGreater(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)292.5, false), Operators.CompareObjectLess(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)337.5, false))))
                        writeSlopedHatching(RuntimeHelpers.GetObjectValue(Symbol));
                    else if (Conversions.ToBoolean(Operators.OrObject(Operators.AndObject(Operators.CompareObjectGreater(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)337.5, false), Operators.CompareObjectLessEqual(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)360.0, false)), Operators.AndObject(Operators.CompareObjectGreaterEqual(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)0.0, false), Operators.CompareObjectLess(NewLateBinding.LateGet(Symbol, (Type)null, "Angle", new object[0], (string[])null, (Type[])null, (bool[])null), (object)22.5, false)))))
                        writePerpendicularHatching(RuntimeHelpers.GetObjectValue(Symbol));
                }
                else if (Symbol is StructDotDensityFillSymbol)
                    writeMarkerFill(RuntimeHelpers.GetObjectValue(Symbol));
                else if (!(Symbol is StructPictureFillSymbol) && !(Symbol is StructGradientFillSymbol) && Symbol is StructMultilayerFillSymbol)
                {
                    object obj = Symbol;
                    StructMultilayerFillSymbol multilayerFillSymbol1 = new StructMultilayerFillSymbol();
                    StructMultilayerFillSymbol multilayerFillSymbol2 = obj != null ? (StructMultilayerFillSymbol)obj : multilayerFillSymbol1;
                    if (multilayerFillSymbol2.LayerCount == 1)
                        writePolygonFeatures(RuntimeHelpers.GetObjectValue(multilayerFillSymbol2.MultiFillLayers[0]));
                    else if (multilayerFillSymbol2.LayerCount == 2)
                    {
                        short num2 = checked((short)(multilayerFillSymbol2.LayerCount - 1));
                        while (num2 >= (short)0)
                        {
                            writePolygonFeatures(RuntimeHelpers.GetObjectValue(multilayerFillSymbol2.MultiFillLayers[(int)num2]));
                            checked { num2 += (short)-1; }
                        }
                    }
                    else if (multilayerFillSymbol2.LayerCount > 2)
                    {
                        short num2 = checked((short)(multilayerFillSymbol2.LayerCount - 1));
                        for (int i = 0; i <= num2; i++)
                        {
                            writePolygonFeatures(RuntimeHelpers.GetObjectValue(multilayerFillSymbol2.MultiFillLayers[(int)num2 - i]));
                        }

                        /*************************ORIGINAL VERSION
                        short num2 = checked((short)(multilayerFillSymbol2.LayerCount - 1));
                        while (num2 >= (short)0)
                        {
                            if (num1 <= (short)1)
                                writePolygonFeatures(RuntimeHelpers.GetObjectValue(multilayerFillSymbol2.MultiFillLayers[(int)num2]));
                            checked { ++num1; }
                            checked { num2 += (short)-1; }
                        }

                        *************************** END ORIGINAL VERSION****/
                    }
                 
                }
                else if (Symbol is StructPictureFillSymbol)
                {
                    writePolygonWithPoint(Symbol);
                    //writePointFeatures(RuntimeHelpers.GetObjectValue(Symbol));
                }
                flag = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Schreiben der Schraffur", exception.Message, exception.StackTrace, nameof(WritePolygonFeatures));
                flag = false;
                ProjectData.ClearProjectError();
            }
            return flag;
        }

        private string getValueFromSymbolstruct(string ValueNameOfValueYouWant, object SymbolStructure)
        {
            return getValueFromSymbolstruct(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(SymbolStructure), 0);
        }

        private string getValueFromSymbolstruct(string ValueNameOfValueYouWant, object SymbolStructure, int LayerIdx)
        {
            bool flag = false;
            string str1 = "0";
            string str2 = String.Empty;
            try
            {
                StructSimpleLineSymbol simpleLineSymbol1 = new StructSimpleLineSymbol();
                if (SymbolStructure is StructSimpleMarkerSymbol)
                    str2 = getMarkerValue(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(SymbolStructure));
                else if (SymbolStructure is StructCharacterMarkerSymbol)
                    str2 = getMarkerValue(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(SymbolStructure));
                else if (SymbolStructure is StructPictureMarkerSymbol)
                    str2 = getMarkerValue(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(SymbolStructure));
                else if (SymbolStructure is StructArrowMarkerSymbol)
                    str2 = getMarkerValue(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(SymbolStructure));
                else if (SymbolStructure is StructSimpleLineSymbol)
                {
                    object obj = SymbolStructure;
                    StructSimpleLineSymbol simpleLineSymbol2 = obj != null ? (StructSimpleLineSymbol)obj : simpleLineSymbol1;
                    string upper = ValueNameOfValueYouWant.ToUpper();
                    if (Operators.CompareString(upper, "LineWidth".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint(simpleLineSymbol2.Width);
                    else if (Operators.CompareString(upper, "LineColor".ToUpper(), false) == 0)
                    {
                        str1 = simpleLineSymbol2.Color.Equals("#000000") ? "#000001" : simpleLineSymbol2.Color;
                    }
                    else if (Operators.CompareString(upper, "LineOpacity".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint((double)simpleLineSymbol2.Transparency / (double)byte.MaxValue);
                    else if (Operators.CompareString(upper, "LineDashArray".ToUpper(), false) == 0)
                    {
                        string publicStyle = simpleLineSymbol2.publicStyle;
                        str1 = Operators.CompareString(publicStyle, "esriSLSDash", false) != 0 ? (Operators.CompareString(publicStyle, "esriSLSDashDot", false) != 0 ? (Operators.CompareString(publicStyle, "esriSLSDashDotDot", false) != 0 ? (Operators.CompareString(publicStyle, "esriSLSDot", false) != 0 ? "" : "1.0 5.0") : "10.0 10.0 1.0 10.0 1.0 10.0") : "10.0 10.0 1.0 10.0") : "10.0 10.0";
                    }
                    else if ( upper.Equals("WIDTH"))
                    {
                        str1 = this.CommaToPoint(simpleLineSymbol2.Width);
                    }
                    str2 = str1;
                }
                else
                {
                    StructSimpleFillSymbol simpleFillSymbol1 = new StructSimpleFillSymbol();
                    StructMarkerFillSymbol markerFillSymbol1 = new StructMarkerFillSymbol();
                    StructLineFillSymbol structLineFillSymbol1 = new StructLineFillSymbol();
                    if (SymbolStructure is StructCartographicLineSymbol)
                    {
                        object obj = SymbolStructure;
                        StructCartographicLineSymbol cartographicLineSymbol1 = new StructCartographicLineSymbol();
                        StructCartographicLineSymbol cartographicLineSymbol2 = obj != null ? (StructCartographicLineSymbol)obj : cartographicLineSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "LineWidth".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint(cartographicLineSymbol2.Width);
                        else if (Operators.CompareString(upper, "LineColor".ToUpper(), false) == 0)
                        {
                            str1 = cartographicLineSymbol2.Color.Equals("#000000") ? "#000001" : cartographicLineSymbol2.Color;
                        }
                        else if (Operators.CompareString(upper, "LineOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)cartographicLineSymbol2.Transparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "LineDashArray".ToUpper(), false) == 0)
                        {
                            str1 = "";
                            int num1 = 0;
                            int num2 = checked(cartographicLineSymbol2.DashArray.Count - 1);
                            int index = num1;
                            while (index <= num2)
                            {
                                if (index > 0)
                                    str1 += " ";
                                try
                                {

                                    double num3 = Conversions.ToDouble(cartographicLineSymbol2.DashArray[index]);
                                    str1 += this.CommaToPoint(num3);


                                    checked { ++index; }
                                }
                                catch (Exception)
                                {

                                    return "";
                                }
                            }
                        }else if (Operators.CompareString(upper, "Width".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint(cartographicLineSymbol2.Width);
                        else if (Operators.CompareString(upper, "", false) != 0)
                        {
                            Utility util = new Utility();
                            util.scriviLog("WARN: SIMBOLO NON TROVATO");
                        }
                    }
                    else if (SymbolStructure is StructHashLineSymbol)
                    {
                        object obj = SymbolStructure;
                        StructHashLineSymbol structHashLineSymbol1 = new StructHashLineSymbol();
                        StructHashLineSymbol structHashLineSymbol2 = obj != null ? (StructHashLineSymbol)obj : structHashLineSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "LineWidth".ToUpper(), false) == 0 || Operators.CompareString(upper, "LineDashArray".ToUpper(), false) == 0)
                        {
                            switch (structHashLineSymbol2.kindOfLineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)structHashLineSymbol2.HashSymbol_SimpleLine);
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)structHashLineSymbol2.HashSymbol_MarkerLine);
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)structHashLineSymbol2.HashSymbol_PictureLine);
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)structHashLineSymbol2.HashSymbol_MultiLayerLines, LayerIdx);
                                    break;
                                case LineStructs.StructCartographicLineSymbol:
                                    str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)structHashLineSymbol2.HashSymbol_CartographicLine);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "LineColor".ToUpper(), false) == 0)
                            str1 = structHashLineSymbol2.Color.Equals("#000000") ? "#000001" : structHashLineSymbol2.Color;
                       else if (Operators.CompareString(upper, "LineOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)structHashLineSymbol2.Transparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "", false) != 0)
                        {
                            Utility util = new Utility();
                            util.scriviLog("WARN: SIMBOLO NON TROVATO");
                        }
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructMarkerLineSymbol)
                    {
                        object obj = SymbolStructure;
                        StructMarkerLineSymbol markerLineSymbol1 = new StructMarkerLineSymbol();
                        StructMarkerLineSymbol markerLineSymbol2 = obj != null ? (StructMarkerLineSymbol)obj : markerLineSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "LineWidth".ToUpper(), false) == 0)
                        {
                            // this.InfoMsg("Abfrage von Linienbreite der Markerlines ist im Augenblick nicht implementiert", nameof(getValueFromSymbolstruct));
                        }
                        else if (Operators.CompareString(upper, "LineColor".ToUpper(), false) == 0)
                            str1 = markerLineSymbol2.Color.Equals("#000000") ? "#000001" : markerLineSymbol2.Color;
                        else if (Operators.CompareString(upper, "LineOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)markerLineSymbol2.Transparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "LINEDASHARRAY".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)markerLineSymbol2.Width) + " 0";
                        else if (Operators.CompareString(upper, "", false) != 0)
                        {
                            Utility util = new Utility();
                            util.scriviLog("WARN simbolo non trovato");
                        }
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructPictureLineSymbol)
                    {
                        object obj = SymbolStructure;
                        StructPictureLineSymbol pictureLineSymbol1 = new StructPictureLineSymbol();
                        StructPictureLineSymbol pictureLineSymbol2 = obj != null ? (StructPictureLineSymbol)obj : pictureLineSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "LineWidth".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint(pictureLineSymbol2.Width);
                        else if (Operators.CompareString(upper, "LineColor".ToUpper(), false) == 0)
                            str1 = pictureLineSymbol2.BackgroundColor.Equals("#000000") ? "#000001" : pictureLineSymbol2.BackgroundColor;
                        else if (Operators.CompareString(upper, "LineOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)pictureLineSymbol2.BackgroundTransparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "", false) != 0) {
                            Utility util = new Utility();
                            util.scriviLog("WARN: SIMBOLO NON TROVATO");
                        }
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructSimpleFillSymbol)
                    {
                        object obj = SymbolStructure;
                        StructSimpleFillSymbol simpleFillSymbol2 = obj != null ? (StructSimpleFillSymbol)obj : simpleFillSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "PolygonColor".ToUpper(), false) == 0)
                            str1 = simpleFillSymbol2.Color;
                        else if (Operators.CompareString(upper, "PolygonOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)simpleFillSymbol2.Transparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "PolygonBorderWidth".ToUpper(), false) == 0)
                        {
                            switch (simpleFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = this.CommaToPoint(simpleFillSymbol2.Outline_SimpleLine.Width);
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = this.CommaToPoint(simpleFillSymbol2.Outline_MarkerLine.Width);
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = this.CommaToPoint(simpleFillSymbol2.Outline_HashLine.Width);
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = this.CommaToPoint(simpleFillSymbol2.Outline_PictureLine.Width);
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct("LineWidth", (object)simpleFillSymbol2.Outline_MultiLayerLines);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderColor".ToUpper(), false) == 0)
                        {
                            switch (simpleFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = simpleFillSymbol2.Outline_SimpleLine.Color;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = simpleFillSymbol2.Outline_MarkerLine.Color;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = simpleFillSymbol2.Outline_HashLine.Color;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = simpleFillSymbol2.Outline_PictureLine.Color;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    {
                                        str1 = getValueFromSymbolstruct("LineColor", (object)simpleFillSymbol2.Outline_MultiLayerLines);
                                        str1 = str1.Equals("#000000") ? "#000001" : str1;
                                    }
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderOpacity".ToUpper(), false) == 0)
                        {
                            double num = (double)byte.MaxValue;
                            switch (simpleFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    num = (double)simpleFillSymbol2.Outline_SimpleLine.Transparency;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    num = (double)simpleFillSymbol2.Outline_MarkerLine.Transparency;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    num = (double)simpleFillSymbol2.Outline_HashLine.Transparency;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    num = (double)simpleFillSymbol2.Outline_PictureLine.Transparency;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    num = (double)byte.MaxValue * Conversions.ToDouble(getValueFromSymbolstruct("LineOpacity", (object)simpleFillSymbol2.Outline_MultiLayerLines));
                                    break;
                            }
                            str1 = this.CommaToPoint(num / (double)byte.MaxValue);
                        }
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructMarkerFillSymbol)
                    {
                        object obj = SymbolStructure;
                        StructMarkerFillSymbol markerFillSymbol2 = obj != null ? (StructMarkerFillSymbol)obj : markerFillSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "PolygonColor".ToUpper(), false) == 0)
                            str1 = markerFillSymbol2.Color;
                        else if (Operators.CompareString(upper, "PolygonOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)markerFillSymbol2.Transparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "PolygonBorderWidth".ToUpper(), false) == 0)
                        {
                            switch (markerFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = this.CommaToPoint(markerFillSymbol2.Outline_SimpleLine.Width);
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = this.CommaToPoint(markerFillSymbol2.Outline_MarkerLine.Width);
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = this.CommaToPoint(markerFillSymbol2.Outline_HashLine.Width);
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = this.CommaToPoint(markerFillSymbol2.Outline_PictureLine.Width);
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct("LineWidth", (object)markerFillSymbol2.Outline_MultiLayerLines);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderColor".ToUpper(), false) == 0)
                        {
                            switch (markerFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = markerFillSymbol2.Outline_SimpleLine.Color;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = markerFillSymbol2.Outline_MarkerLine.Color;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = markerFillSymbol2.Outline_HashLine.Color;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = markerFillSymbol2.Outline_PictureLine.Color;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    {
                                        str1 = getValueFromSymbolstruct("LineColor", (object)markerFillSymbol2.Outline_MultiLayerLines);
                                        str1 = str1.Equals("#000000") ? "#000001" : str1;
                                    }
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderOpacity".ToUpper(), false) == 0)
                        {
                            double num = (double)byte.MaxValue;
                            switch (markerFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    num = (double)markerFillSymbol2.Outline_SimpleLine.Transparency;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    num = (double)markerFillSymbol2.Outline_MarkerLine.Transparency;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    num = (double)markerFillSymbol2.Outline_HashLine.Transparency;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    num = (double)markerFillSymbol2.Outline_PictureLine.Transparency;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    num = (double)byte.MaxValue * Conversions.ToDouble(getValueFromSymbolstruct("LineOpacity", (object)markerFillSymbol2.Outline_MultiLayerLines));
                                    break;
                            }
                            str1 = this.CommaToPoint(num / (double)byte.MaxValue);
                        }
                        else if (Operators.CompareString(upper, "PointSize".ToUpper(), false) == 0)
                        {
                            switch (markerFillSymbol2.kindOfMarkerStruct)
                            {
                                case MarkerStructs.StructSimpleMarkerSymbol:
                                    str1 = this.CommaToPoint(markerFillSymbol2.MarkerSymbol_SimpleMarker.Size);
                                    break;
                                case MarkerStructs.StructCharacterMarkerSymbol:
                                    str1 = this.CommaToPoint(markerFillSymbol2.MarkerSymbol_CharacterMarker.Size);
                                    break;
                                case MarkerStructs.StructPictureMarkerSymbol:
                                    str1 = this.CommaToPoint(markerFillSymbol2.MarkerSymbol_PictureMarker.Size);
                                    break;
                                case MarkerStructs.StructArrowMarkerSymbol:
                                    str1 = this.CommaToPoint(markerFillSymbol2.MarkerSymbol_ArrowMarker.Size);
                                    break;
                                case MarkerStructs.StructMultilayerMarkerSymbol:
                                    str1 = getValueFromSymbolstruct("PointSize", (object)markerFillSymbol2.MarkerSymbol_MultilayerMarker);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PointColor".ToUpper(), false) == 0)
                        {
                            switch (markerFillSymbol2.kindOfMarkerStruct)
                            {
                                case MarkerStructs.StructSimpleMarkerSymbol:
                                    str1 = markerFillSymbol2.MarkerSymbol_SimpleMarker.Color;
                                    break;
                                case MarkerStructs.StructCharacterMarkerSymbol:
                                    str1 = markerFillSymbol2.MarkerSymbol_CharacterMarker.Color;
                                    break;
                                case MarkerStructs.StructPictureMarkerSymbol:
                                    str1 = markerFillSymbol2.MarkerSymbol_PictureMarker.Color;
                                    break;
                                case MarkerStructs.StructArrowMarkerSymbol:
                                    str1 = markerFillSymbol2.MarkerSymbol_ArrowMarker.Color;
                                    break;
                                case MarkerStructs.StructMultilayerMarkerSymbol:
                                    str1 = getValueFromSymbolstruct("PointColor", (object)markerFillSymbol2.MarkerSymbol_MultilayerMarker);
                                    break;
                            }
                        }
                   
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructLineFillSymbol)
                    {
                        object obj = SymbolStructure;
                        StructLineFillSymbol structLineFillSymbol2 = obj != null ? (StructLineFillSymbol)obj : structLineFillSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "PolygonColor".ToUpper(), false) == 0)
                            str1 = structLineFillSymbol2.Color;
                        else if (Operators.CompareString(upper, "PolygonOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)structLineFillSymbol2.Transparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "PolygonBorderWidth".ToUpper(), false) == 0)
                        {
                            switch (structLineFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.Outline_SimpleLine.Width);
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.Outline_MarkerLine.Width);
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.Outline_HashLine.Width);
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.Outline_PictureLine.Width);
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct("LineWidth", (object)structLineFillSymbol2.Outline_MultiLayerLines);
                                    break;
                                case LineStructs.StructCartographicLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.LineSymbol_CartographicLine.Width);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderColor".ToUpper(), false) == 0)
                        {
                            switch (structLineFillSymbol2.kindOfOutlineStruct)
                            {

                                case LineStructs.StructCartographicLineSymbol:
                                    str1 = structLineFillSymbol2.LineSymbol_CartographicLine.Color;
                                    break;
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = structLineFillSymbol2.Outline_SimpleLine.Color;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = structLineFillSymbol2.Outline_MarkerLine.Color;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = structLineFillSymbol2.Outline_HashLine.Color;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = structLineFillSymbol2.Outline_PictureLine.Color;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    {
                                        str1 = getValueFromSymbolstruct("LineColor", (object)structLineFillSymbol2.Outline_MultiLayerLines);
                                        str1 = str1.Equals("#000000") ? "#000001" : str1;
                                    }
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderOpacity".ToUpper(), false) == 0)
                        {
                            double num = (double)byte.MaxValue;
                            switch (structLineFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    num = (double)structLineFillSymbol2.Outline_SimpleLine.Transparency;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    num = (double)structLineFillSymbol2.Outline_MarkerLine.Transparency;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    num = (double)structLineFillSymbol2.Outline_HashLine.Transparency;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    num = (double)structLineFillSymbol2.Outline_PictureLine.Transparency;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    num = (double)byte.MaxValue * Conversions.ToDouble(getValueFromSymbolstruct("LineOpacity", (object)structLineFillSymbol2.Outline_MultiLayerLines));
                                    break;
                            }
                            str1 = this.CommaToPoint(num / (double)byte.MaxValue);
                        }
                        else if (Operators.CompareString(upper, "LineWidth".ToUpper(), false) == 0)
                        {
                            switch (structLineFillSymbol2.kindOfLineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.LineSymbol_SimpleLine.Width);
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.LineSymbol_MarkerLine.Width);
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.LineSymbol_HashLine.Width);
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.LineSymbol_PictureLine.Width);
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct("LineWidth", (object)structLineFillSymbol2.LineSymbol_MultiLayerLines);
                                    break;
                                case LineStructs.StructCartographicLineSymbol:
                                    str1 = this.CommaToPoint(structLineFillSymbol2.LineSymbol_CartographicLine.Width);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "LineColor".ToUpper(), false) == 0)
                        {
                            switch (structLineFillSymbol2.kindOfLineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = structLineFillSymbol2.LineSymbol_SimpleLine.Color;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = structLineFillSymbol2.LineSymbol_MarkerLine.Color;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = structLineFillSymbol2.LineSymbol_HashLine.Color;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = structLineFillSymbol2.LineSymbol_PictureLine.Color;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct("LineColor", (object)structLineFillSymbol2.LineSymbol_MultiLayerLines);
                                    break;
                                case LineStructs.StructCartographicLineSymbol:
                                    str1 = structLineFillSymbol2.LineSymbol_CartographicLine.Color;
                                    break;
                            }

                            str1 = str1.Equals("#000000") ? "#000001" : str1;
                        }
                        else if (Operators.CompareString(upper, "LineOpacity".ToUpper(), false) == 0)
                        {
                            double num = (double)byte.MaxValue;
                            switch (structLineFillSymbol2.kindOfLineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    num = (double)structLineFillSymbol2.LineSymbol_SimpleLine.Transparency;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    num = (double)structLineFillSymbol2.LineSymbol_MarkerLine.Transparency;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    num = (double)structLineFillSymbol2.LineSymbol_HashLine.Transparency;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    num = (double)structLineFillSymbol2.LineSymbol_PictureLine.Transparency;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    num = (double)byte.MaxValue * Conversions.ToDouble(getValueFromSymbolstruct("LineOpacity", (object)structLineFillSymbol2.LineSymbol_MultiLayerLines));
                                    break;
                                case LineStructs.StructCartographicLineSymbol:
                                    num = (double)structLineFillSymbol2.LineSymbol_CartographicLine.Transparency;
                                    break;
                            }
                            str1 = this.CommaToPoint(num / (double)byte.MaxValue);
                        }
                        else if (Operators.CompareString(upper, "Angle".ToUpper(), false) == 0)
                        {
                            double num = (double)byte.MaxValue;
                            num = structLineFillSymbol2.Angle;
                            str1 = num.ToString();
                        }

                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructDotDensityFillSymbol)
                    {
                        object obj = SymbolStructure;
                        StructDotDensityFillSymbol densityFillSymbol1 = new StructDotDensityFillSymbol();
                        StructDotDensityFillSymbol densityFillSymbol2 = obj != null ? (StructDotDensityFillSymbol)obj : densityFillSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "PolygonColor".ToUpper(), false) == 0)
                            str1 = densityFillSymbol2.BackgroundColor;
                        else if (Operators.CompareString(upper, "PolygonOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)densityFillSymbol2.BackgroundTransparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "PolygonBorderWidth".ToUpper(), false) == 0)
                        {
                            switch (densityFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = this.CommaToPoint(densityFillSymbol2.Outline_SimpleLine.Width);
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = this.CommaToPoint(densityFillSymbol2.Outline_MarkerLine.Width);
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = this.CommaToPoint(densityFillSymbol2.Outline_HashLine.Width);
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = this.CommaToPoint(densityFillSymbol2.Outline_PictureLine.Width);
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct("LineWidth", (object)densityFillSymbol2.Outline_MultiLayerLines);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderColor".ToUpper(), false) == 0)
                        {
                            switch (densityFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = densityFillSymbol2.Outline_SimpleLine.Color;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = densityFillSymbol2.Outline_MarkerLine.Color;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = densityFillSymbol2.Outline_HashLine.Color;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = densityFillSymbol2.Outline_PictureLine.Color;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    {
                                        str1 = getValueFromSymbolstruct("LineColor", (object)densityFillSymbol2.Outline_MultiLayerLines);
                                        str1 = str1.Equals("#000000") ? "#000001" : str1;
                                    }
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderOpacity".ToUpper(), false) == 0)
                        {
                            double num = (double)byte.MaxValue;
                            switch (densityFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    num = (double)densityFillSymbol2.Outline_SimpleLine.Transparency;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    num = (double)densityFillSymbol2.Outline_MarkerLine.Transparency;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    num = (double)densityFillSymbol2.Outline_HashLine.Transparency;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    num = (double)densityFillSymbol2.Outline_PictureLine.Transparency;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    num = (double)byte.MaxValue * Conversions.ToDouble(getValueFromSymbolstruct("LineOpacity", (object)densityFillSymbol2.Outline_MultiLayerLines));
                                    break;
                            }
                            str1 = this.CommaToPoint(num / (double)byte.MaxValue);
                        }
                        else if (Operators.CompareString(upper, "PointSize".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint(0);
                        else if (Operators.CompareString(upper, "PointColor".ToUpper(), false) == 0)
                            str1 = densityFillSymbol2.Color;
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructPictureFillSymbol)
                    {
                        object obj = SymbolStructure;
                        StructPictureFillSymbol pictureFillSymbol1 = new StructPictureFillSymbol();
                        StructPictureFillSymbol pictureFillSymbol2 = obj != null ? (StructPictureFillSymbol)obj : pictureFillSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "PolygonColor".ToUpper(), false) == 0)
                            str1 = pictureFillSymbol2.BackgroundColor;
                        else if (Operators.CompareString(upper, "PolygonOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)pictureFillSymbol2.BackgroundTransparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "PolygonBorderWidth".ToUpper(), false) == 0)
                        {
                            switch (pictureFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = this.CommaToPoint(pictureFillSymbol2.Outline_SimpleLine.Width);
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = this.CommaToPoint(pictureFillSymbol2.Outline_MarkerLine.Width);
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = this.CommaToPoint(pictureFillSymbol2.Outline_HashLine.Width);
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = this.CommaToPoint(pictureFillSymbol2.Outline_PictureLine.Width);
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct("LineWidth", (object)pictureFillSymbol2.Outline_MultiLayerLines);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderColor".ToUpper(), false) == 0)
                        {
                            switch (pictureFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = pictureFillSymbol2.Outline_SimpleLine.Color;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = pictureFillSymbol2.Outline_MarkerLine.Color;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = pictureFillSymbol2.Outline_HashLine.Color;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = pictureFillSymbol2.Outline_PictureLine.Color;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    {
                                        str1 = getValueFromSymbolstruct("LineColor", (object)pictureFillSymbol2.Outline_MultiLayerLines);
                                        str1 = str1.Equals("#000000") ? "#000001" : str1;
                                    }
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderOpacity".ToUpper(), false) == 0)
                        {
                            double num = (double)byte.MaxValue;
                            switch (pictureFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    num = (double)pictureFillSymbol2.Outline_SimpleLine.Transparency;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    num = (double)pictureFillSymbol2.Outline_MarkerLine.Transparency;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    num = (double)pictureFillSymbol2.Outline_HashLine.Transparency;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    num = (double)pictureFillSymbol2.Outline_PictureLine.Transparency;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    num = (double)byte.MaxValue * Conversions.ToDouble(getValueFromSymbolstruct("LineOpacity", (object)pictureFillSymbol2.Outline_MultiLayerLines));
                                    break;
                            }
                            str1 = this.CommaToPoint(num / (double)byte.MaxValue);
                        }
                        else if (upper.Equals("WIDTH"))
                        {
                            double num = (double)byte.MaxValue;
                            switch (pictureFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    num = (double)pictureFillSymbol2.Outline_SimpleLine.Transparency;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    num = (double)pictureFillSymbol2.Outline_MarkerLine.Transparency;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    num = (double)pictureFillSymbol2.Outline_HashLine.Transparency;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    num = (double)pictureFillSymbol2.Outline_PictureLine.Transparency;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    num = (double)byte.MaxValue * Conversions.ToDouble(getValueFromSymbolstruct("WIDTH", (object)pictureFillSymbol2.Outline_MultiLayerLines));
                                    break;
                            }
                            str1 = this.CommaToPoint(num / (double)byte.MaxValue);
                        }
                        else if (upper.Equals("YSCALE"))
                        {
                            double num = (double)byte.MaxValue;
                            num = (double)pictureFillSymbol2.YScale;
                            if (num > 2)
                                num = 2;

                            str1 = this.CommaToPoint(num);
                        }

                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructGradientFillSymbol)
                    {
                        object obj = SymbolStructure;
                        StructGradientFillSymbol gradientFillSymbol1 = new StructGradientFillSymbol();
                        StructGradientFillSymbol gradientFillSymbol2 = obj != null ? (StructGradientFillSymbol)obj : gradientFillSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "PolygonColor".ToUpper(), false) == 0)
                            str1 = gradientFillSymbol2.Color;
                        else if (Operators.CompareString(upper, "PolygonOpacity".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint((double)gradientFillSymbol2.Transparency / (double)byte.MaxValue);
                        else if (Operators.CompareString(upper, "PolygonBorderWidth".ToUpper(), false) == 0)
                        {
                            switch (gradientFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = this.CommaToPoint(gradientFillSymbol2.Outline_SimpleLine.Width);
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = this.CommaToPoint(gradientFillSymbol2.Outline_MarkerLine.Width);
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = this.CommaToPoint(gradientFillSymbol2.Outline_HashLine.Width);
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = this.CommaToPoint(gradientFillSymbol2.Outline_PictureLine.Width);
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    str1 = getValueFromSymbolstruct("LineWidth", (object)gradientFillSymbol2.Outline_MultiLayerLines);
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderColor".ToUpper(), false) == 0)
                        {
                            switch (gradientFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    str1 = gradientFillSymbol2.Outline_SimpleLine.Color;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    str1 = gradientFillSymbol2.Outline_MarkerLine.Color;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    str1 = gradientFillSymbol2.Outline_HashLine.Color;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    str1 = gradientFillSymbol2.Outline_PictureLine.Color;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    {
                                        str1 = getValueFromSymbolstruct("LineColor", (object)gradientFillSymbol2.Outline_MultiLayerLines);
                                        str1 = str1.Equals("#000000") ? "#000001" : str1;
                                    }
                                    break;
                            }
                        }
                        else if (Operators.CompareString(upper, "PolygonBorderOpacity".ToUpper(), false) == 0)
                        {
                            double num = (double)byte.MaxValue;
                            switch (gradientFillSymbol2.kindOfOutlineStruct)
                            {
                                case LineStructs.StructSimpleLineSymbol:
                                    num = (double)gradientFillSymbol2.Outline_SimpleLine.Transparency;
                                    break;
                                case LineStructs.StructMarkerLineSymbol:
                                    num = (double)gradientFillSymbol2.Outline_MarkerLine.Transparency;
                                    break;
                                case LineStructs.StructHashLineSymbol:
                                    num = (double)gradientFillSymbol2.Outline_HashLine.Transparency;
                                    break;
                                case LineStructs.StructPictureLineSymbol:
                                    num = (double)gradientFillSymbol2.Outline_PictureLine.Transparency;
                                    break;
                                case LineStructs.StructMultilayerLineSymbol:
                                    num = (double)byte.MaxValue * Conversions.ToDouble(getValueFromSymbolstruct("LineOpacity", (object)gradientFillSymbol2.Outline_MultiLayerLines));
                                    break;
                            }
                            str1 = this.CommaToPoint(num / (double)byte.MaxValue);
                        }
                       
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructBarChartSymbol)
                    {
                        object obj = SymbolStructure;
                        StructBarChartSymbol structBarChartSymbol1 = new StructBarChartSymbol();
                        StructBarChartSymbol structBarChartSymbol2 = obj != null ? (StructBarChartSymbol)obj : structBarChartSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "", false) == 0 || Operators.CompareString(upper, "", false) == 0 || Operators.CompareString(upper, "", false) != 0)
                        {
                            Utility util = new Utility();
                            util.scriviLog("WARN: SIMBOLO NON TROVATO");
                        }
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructPieChartSymbol)
                    {
                        object obj = SymbolStructure;
                        StructPieChartSymbol structPieChartSymbol1 = new StructPieChartSymbol();
                        StructPieChartSymbol structPieChartSymbol2 = obj != null ? (StructPieChartSymbol)obj : structPieChartSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "", false) == 0 || Operators.CompareString(upper, "", false) == 0 || Operators.CompareString(upper, "", false) != 0)
                        {
                            Utility util = new Utility();
                            util.scriviLog("WARN: SIMBOLO NON TROVATO");
                        }
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructStackedChartSymbol)
                    {
                        object obj = SymbolStructure;
                        StructStackedChartSymbol stackedChartSymbol1 = new StructStackedChartSymbol();
                        StructStackedChartSymbol stackedChartSymbol2 = obj != null ? (StructStackedChartSymbol)obj : stackedChartSymbol1;
                        string upper = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper, "", false) == 0 || Operators.CompareString(upper, "", false) == 0 || Operators.CompareString(upper, "", false) != 0)
                        {
                            Utility util = new Utility();
                            util.scriviLog("WARN: SIMBOLO NON TROVATO");
                        }
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructTextSymbol)
                    {
                        object obj = SymbolStructure;
                        StructTextSymbol structTextSymbol1 = new StructTextSymbol();
                        StructTextSymbol structTextSymbol2 = obj != null ? (StructTextSymbol)obj : structTextSymbol1;
                        string upper1 = ValueNameOfValueYouWant.ToUpper();
                        if (Operators.CompareString(upper1, "TextColor".ToUpper(), false) == 0)
                            str1 = structTextSymbol2.Color;
                        else if (Operators.CompareString(upper1, "TextFont".ToUpper(), false) == 0)
                            str1 = structTextSymbol2.Font;
                        else if (Operators.CompareString(upper1, "TextFontAlt".ToUpper(), false) == 0)
                        {
                            string upper2 = structTextSymbol2.Font.ToUpper();
                            if (Operators.CompareString(upper2, "ARIAL", false) == 0 || Operators.CompareString(upper2, "ARIAL BLACK", false) == 0 || (Operators.CompareString(upper2, "HELVETICA", false) == 0 || Operators.CompareString(upper2, "LUCIDA SANS UNICODE", false) == 0) || (Operators.CompareString(upper2, "MICROSOFT SANS SERIF", false) == 0 || Operators.CompareString(upper2, "TAHOMA", false) == 0 || Operators.CompareString(upper2, "VERDANA", false) == 0))
                                str1 = "Sans-Serif";
                            else if (Operators.CompareString(upper2, "COURIER", false) == 0 || Operators.CompareString(upper2, "COURIER NEW", false) == 0 || Operators.CompareString(upper2, "LUCIDA CONSOLE", false) == 0)
                                str1 = "Monospaced";
                            else if (Operators.CompareString(upper2, "PALATINO LINOTYPE", false) == 0 || Operators.CompareString(upper2, "TIMES", false) == 0 || Operators.CompareString(upper2, "TIMES NEW ROMAN", false) == 0)
                                str1 = "Serif";
                        }
                        else if (Operators.CompareString(upper1, "TextFontSize".ToUpper(), false) == 0)
                            str1 = Conversions.ToString(structTextSymbol2.Size);
                        else if (Operators.CompareString(upper1, "TextFontStyle".ToUpper(), false) == 0)
                            str1 = structTextSymbol2.Style;
                        else if (Operators.CompareString(upper1, "TextFontWeight".ToUpper(), false) == 0)
                            str1 = structTextSymbol2.Weight;
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructMultilayerMarkerSymbol)
                    {
                        object obj = SymbolStructure;
                        StructMultilayerMarkerSymbol multilayerMarkerSymbol1 = new StructMultilayerMarkerSymbol();
                        StructMultilayerMarkerSymbol multilayerMarkerSymbol2 = obj != null ? (StructMultilayerMarkerSymbol)obj : multilayerMarkerSymbol1;
                        str2 = getValueFromSymbolstruct(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(multilayerMarkerSymbol2.MultiMarkerLayers[LayerIdx]));
                        goto label_336;
                    }
                    else if (SymbolStructure is StructMultilayerLineSymbol)
                    {
                        object obj = SymbolStructure;
                        StructMultilayerLineSymbol multilayerLineSymbol1 = new StructMultilayerLineSymbol();
                        StructMultilayerLineSymbol multilayerLineSymbol2 = obj != null ? (StructMultilayerLineSymbol)obj : multilayerLineSymbol1;
                        if (multilayerLineSymbol2.LayerCount > 1)
                        {
                            int num1 = 0;
                            short num2 = checked((short)(multilayerLineSymbol2.LayerCount - 1));
                            short num3 = (short)num1;
                            while ((int)num3 <= (int)num2)
                            {
                                if (multilayerLineSymbol2.MultiLineLayers[(int)num3] is StructSimpleLineSymbol)
                                {
                                    object multiLineLayer = multilayerLineSymbol2.MultiLineLayers[(int)num3];
                                    StructSimpleLineSymbol simpleLineSymbol2 = multiLineLayer != null ? (StructSimpleLineSymbol)multiLineLayer : simpleLineSymbol1;
                                    str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)simpleLineSymbol2);
                                    flag = true;
                                }
                                checked { ++num3; }
                            }
                            if (!flag)
                                str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(multilayerLineSymbol2.MultiLineLayers[LayerIdx]));
                        }
                        else
                            str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(multilayerLineSymbol2.MultiLineLayers[LayerIdx]));
                        str2 = str1;
                        goto label_336;
                    }
                    else if (SymbolStructure is StructMultilayerFillSymbol)
                    {
                        object obj = SymbolStructure;
                        StructMultilayerFillSymbol multilayerFillSymbol1 = new StructMultilayerFillSymbol();
                        StructMultilayerFillSymbol multilayerFillSymbol2 = obj != null ? (StructMultilayerFillSymbol)obj : multilayerFillSymbol1;
                        if (multilayerFillSymbol2.LayerCount > 1)
                        {
                            int num1 = 0;
                            short num2 = checked((short)(multilayerFillSymbol2.LayerCount - 1));
                            short num3 = (short)num1;
                            while ((int)num3 <= (int)num2)
                            {
                                string upper = ValueNameOfValueYouWant.ToUpper();
                                if (Operators.CompareString(upper, "PolygonColor".ToUpper(), false) == 0 || Operators.CompareString(upper, "PolygonOpacity".ToUpper(), false) == 0 || (Operators.CompareString(upper, "PolygonBorderWidth".ToUpper(), false) == 0 || Operators.CompareString(upper, "PolygonBorderColor".ToUpper(), false) == 0) || Operators.CompareString(upper, "PolygonBorderOpacity".ToUpper(), false) == 0)
                                {
                                    if (multilayerFillSymbol2.MultiFillLayers[(int)num3] is StructSimpleFillSymbol)
                                    {
                                        object multiFillLayer = multilayerFillSymbol2.MultiFillLayers[(int)num3];
                                        StructSimpleFillSymbol simpleFillSymbol2 = multiFillLayer != null ? (StructSimpleFillSymbol)multiFillLayer : simpleFillSymbol1;
                                        str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)simpleFillSymbol2);
                                        flag = true;
                                    }
                                }
                                else if (Operators.CompareString(upper, "PointColor".ToUpper(), false) == 0 || Operators.CompareString(upper, "PointSize".ToUpper(), false) == 0)
                                {
                                    if (multilayerFillSymbol2.MultiFillLayers[(int)num3] is StructMarkerFillSymbol)
                                    {
                                        object multiFillLayer = multilayerFillSymbol2.MultiFillLayers[(int)num3];
                                        StructMarkerFillSymbol markerFillSymbol2 = multiFillLayer != null ? (StructMarkerFillSymbol)multiFillLayer : markerFillSymbol1;
                                        str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)markerFillSymbol2);
                                        flag = true;
                                    }
                                }
                                else if ((Operators.CompareString(upper, "LineWidth".ToUpper(), false) == 0 || Operators.CompareString(upper, "LineColor".ToUpper(), false) == 0 || Operators.CompareString(upper, "LineOpacity".ToUpper(), false) == 0) && multilayerFillSymbol2.MultiFillLayers[(int)num3] is StructLineFillSymbol)
                                {
                                    object multiFillLayer = multilayerFillSymbol2.MultiFillLayers[(int)num3];
                                    StructLineFillSymbol structLineFillSymbol2 = multiFillLayer != null ? (StructLineFillSymbol)multiFillLayer : structLineFillSymbol1;
                                    str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, (object)structLineFillSymbol2);
                                    str1 = str1.Equals("#000000") ? "#000001" : str1;
                                    flag = true;
                                }
                                checked { ++num3; }
                            }
                            if (!flag)
                                str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(multilayerFillSymbol2.MultiFillLayers[LayerIdx]));
                        }
                        else
                            str1 = getValueFromSymbolstruct(ValueNameOfValueYouWant, RuntimeHelpers.GetObjectValue(multilayerFillSymbol2.MultiFillLayers[LayerIdx]));
                        str2 = str1;
                        goto label_336;
                    }

                    str2 = str1;
                }
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;

                //this.ErrorMsg("Konnte den Wert aus der SymbolStruct nicht auswerten.", exception.Message, exception.StackTrace, nameof(getValueFromSymbolstruct));
                ProjectData.ClearProjectError();
            }
        label_336:
            return str2;
        }


        private string getMarkerValue(string ValueNameOfValueYouWant, object SymbolStructure)
        {
            
            string str1 = "0";
            try
            {
                if (SymbolStructure is StructSimpleMarkerSymbol)
                {
                    object obj = SymbolStructure;
                    StructSimpleMarkerSymbol simpleMarkerSymbol1 = new StructSimpleMarkerSymbol();
                    StructSimpleMarkerSymbol simpleMarkerSymbol2 = obj != null ? (StructSimpleMarkerSymbol)obj : simpleMarkerSymbol1;
                    string upper = ValueNameOfValueYouWant.ToUpper();
                    if (Operators.CompareString(upper, "WellKnownName".ToUpper(), false) == 0)
                    {
                        string style = simpleMarkerSymbol2.Style;
                        str1 = Operators.CompareString(style, "esriSMSCircle", false) != 0 ? (Operators.CompareString(style, "esriSMSSquare", false) != 0 ? (Operators.CompareString(style, "esriSMSCross", false) != 0 ? (Operators.CompareString(style, "esriSMSX", false) != 0 ? (Operators.CompareString(style, "esriSMSDiamond", false) != 0 ? "star" : "triangle") : "x") : "cross") : "square") : "circle";
                    }
                    else if (Operators.CompareString(upper, "PointColor".ToUpper(), false) == 0)
                        str1 = !simpleMarkerSymbol2.Filled ? "" : simpleMarkerSymbol2.Color;
                    else if (Operators.CompareString(upper, "PointSize".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint(simpleMarkerSymbol2.Size);
                    else if (Operators.CompareString(upper, "PointRotation".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint(simpleMarkerSymbol2.Angle);
                    else if (Operators.CompareString(upper, "PointOutlineColor".ToUpper(), false) == 0)
                        str1 = !simpleMarkerSymbol2.Outline ? "" : simpleMarkerSymbol2.OutlineColor;
                    else if (Operators.CompareString(upper, "PointOutlineSize".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint(simpleMarkerSymbol2.OutlineSize);
                    return str1;
                }
                if (SymbolStructure is StructCharacterMarkerSymbol)
                {
                    object obj = SymbolStructure;
                    StructCharacterMarkerSymbol characterMarkerSymbol1 = new StructCharacterMarkerSymbol();
                    StructCharacterMarkerSymbol characterMarkerSymbol2 = obj != null ? (StructCharacterMarkerSymbol)obj : characterMarkerSymbol1;
                    string upper1 = ValueNameOfValueYouWant.ToUpper();
                    if (Operators.CompareString(upper1, "WellKnownName".ToUpper(), false) == 0)
                    {
                       // str1 = "circle";
                        int characterIndex = characterMarkerSymbol2.CharacterIndex;
                        str1 = str1 = "ttf://" + characterMarkerSymbol2.Font + "#0x" + ((int)characterIndex).ToString("X4");
                        //string upper2 = characterMarkerSymbol2.Font.ToUpper();
                        //if (upper2.Contains("ESRI"))
                        //{
                        //    {
                        //        int characterIndex = characterMarkerSymbol2.CharacterIndex;
                        //        str1 = "ttf://" + characterMarkerSymbol2.Font + "#0x" + ((int)characterIndex).ToString("X4");
                        //    }
                        //    str1 = "ttf://" + characterMarkerSymbol2.Font + "#0x" + ((int)characterIndex).ToString("X4");
                        //}                      
                        //else if (upper2.ToUpper().StartsWith("ARIAL"))
                        //{
                        //    int characterIndex = characterMarkerSymbol2.CharacterIndex;
                        //    str1 = "ttf://Arial#0x" + ((int)characterIndex).ToString("X4");
                        //}

                        //if (Operators.CompareString(upper2, "ESRI DEFAULT MARKER", false) == 0)
                        //{
                        //    int characterIndex = characterMarkerSymbol2.CharacterIndex;
                        //    if (characterIndex == 33 || characterIndex == 40 || (characterIndex == 46 || characterIndex == 53) || (characterIndex >= 60 && characterIndex <= 67 || characterIndex == 72) || (characterIndex >= 79 && characterIndex <= 82 || characterIndex >= 90 && characterIndex <= 93 || (characterIndex == 171 || characterIndex == 172 || (characterIndex == 183 || characterIndex == 196))) || (characterIndex == 199 || characterIndex == 200 || characterIndex == 8729))
                        //        str1 = "circle";
                        //    else if (characterIndex == 34 || characterIndex == 41 || (characterIndex == 47 || characterIndex == 54) || (characterIndex == 74 || characterIndex == 83 || (characterIndex == 84 || characterIndex == 104)) || (characterIndex == 174 || characterIndex == 175 || (characterIndex == 179 || characterIndex == 190) || (characterIndex == 192 || characterIndex == 194 || (characterIndex == 198 || characterIndex == 201))))
                        //        str1 = "square";
                        //    else if (characterIndex == 35 || characterIndex == 42 || (characterIndex == 48 || characterIndex == 55) || (characterIndex == 73 || characterIndex == 86 || (characterIndex == 184 || characterIndex == 185)))
                        //        str1 = "triangle";
                        //    else if (characterIndex == 68)
                        //        str1 = "X";
                        //    else if (characterIndex == 69 || characterIndex == 70 || characterIndex == 71 || characterIndex >= 203 && characterIndex <= 211)
                        //        str1 = "cross";
                        //    else if (characterIndex == 94 || characterIndex == 95 || (characterIndex == 96 || characterIndex == 106) || (characterIndex == 107 || characterIndex == 108))
                        //        str1 = "star";


                        //    str1 = "ttf://ESRI Default Marker#0x" + ((int)characterIndex).ToString("X4");
                        //}
                        //else if (Operators.CompareString(upper2, "ESRI IGL FONT22", false) == 0)
                        //{
                        //    switch (characterMarkerSymbol2.CharacterIndex)
                        //    {
                        //        case 65:
                        //        case 66:
                        //        case 67:
                        //        case 68:
                        //        case 69:
                        //        case 93:
                        //        case 94:
                        //        case 95:
                        //        case 96:
                        //        case 103:
                        //        case 105:
                        //        case 106:
                        //            str1 = "circle";
                        //            break;
                        //        case 70:
                        //        case 71:
                        //        case 88:
                        //        case 89:
                        //        case 90:
                        //        case 91:
                        //        case 92:
                        //        case 118:
                        //        case 119:
                        //        case 120:
                        //        case 121:
                        //            str1 = "square";
                        //            break;
                        //        case 72:
                        //        case 73:
                        //        case 75:
                        //        case 81:
                        //        case 85:
                        //        case 86:
                        //        case 99:
                        //        case 100:
                        //        case 101:
                        //        case 102:
                        //        case 104:
                        //            str1 = "triangle";
                        //            break;
                        //        case 114:
                        //        case 115:
                        //        case 116:
                        //        case 117:
                        //            str1 = "X";
                        //            break;
                        //    }
                        //}
                        //else if (Operators.CompareString(upper2, "ESRI GEOMETRIC SYMBOLS", false) == 0)
                        //{
                        //    int characterIndex = characterMarkerSymbol2.CharacterIndex;
                        //    if (characterIndex >= 33 && characterIndex <= 35 || characterIndex >= 39 && characterIndex <= 41 || (characterIndex == 47 || characterIndex == 48 || characterIndex >= 56 && characterIndex <= 58) || (characterIndex == 65 || characterIndex >= 68 && characterIndex <= 71 || (characterIndex >= 74 && characterIndex <= 77 || (characterIndex == 82 || characterIndex == 83))) || (characterIndex >= 86 && characterIndex <= 89 || characterIndex >= 92 && characterIndex <= 95 || (characterIndex >= 98 && characterIndex <= 101 || characterIndex >= 104 && characterIndex <= 107) || (characterIndex >= 110 && characterIndex <= 113 || characterIndex >= 116 && characterIndex <= 125 || (characterIndex == 161 || characterIndex == 171 || characterIndex >= 177 && characterIndex <= 186))) || (characterIndex == 244 || characterIndex >= 246 && characterIndex <= 249 || characterIndex == 8729))
                        //        str1 = "circle";
                        //    else if (characterIndex == 37 || characterIndex == 42 || (characterIndex == 43 || characterIndex == 50) || (characterIndex == 55 || characterIndex == 67 || (characterIndex == 73 || characterIndex == 79)) || (characterIndex == 85 || characterIndex == 91 || (characterIndex == 97 || characterIndex == 103) || (characterIndex == 109 || characterIndex == 115 || (characterIndex == 170 || characterIndex == 172))) || (characterIndex >= 200 && characterIndex <= 205 || (characterIndex == 208 || characterIndex == 209) || (characterIndex == 210 || characterIndex >= 226 && characterIndex <= 241) || (characterIndex == 243 || characterIndex == 250)))
                        //        str1 = "square";
                        //    else if (characterIndex == 36 || characterIndex == 46 || (characterIndex == 49 || characterIndex == 66) || (characterIndex == 72 || characterIndex == 78 || (characterIndex == 84 || characterIndex == 90)) || (characterIndex == 96 || characterIndex == 102 || (characterIndex == 108 || characterIndex == 114) || (characterIndex == 162 || characterIndex == 168 || (characterIndex == 169 || characterIndex == 175))) || (characterIndex == 176 || characterIndex >= 186 && characterIndex <= 190 || (characterIndex >= 213 && characterIndex <= 220 || characterIndex == 245)))
                        //        str1 = "triangle";
                        //    else if (characterIndex >= 195 && characterIndex <= 199 || (characterIndex == 206 || characterIndex == 207))
                        //        str1 = "X";
                        //}

                    }
                    else
                    {
                        if (Operators.CompareString(upper1, "PointColor".ToUpper(), false) == 0 || Operators.CompareString(upper1, "PointOutlineColor".ToUpper(), false) == 0)
                        {
                            string str2 = characterMarkerSymbol2.Color;
                            string str3 = "";
                            string upper2 = characterMarkerSymbol2.Font.ToUpper();
                            if (Operators.CompareString(upper2, "ESRI DEFAULT MARKER", false) == 0)
                            {
                                int characterIndex = characterMarkerSymbol2.CharacterIndex;
                                if (characterIndex >= 33 && characterIndex <= 39 || characterIndex >= 67 && characterIndex <= 69 || (characterIndex == 71 || characterIndex == 81 || characterIndex == 88) || (characterIndex >= 97 && characterIndex <= 103 || (characterIndex == 107 || characterIndex == 113) || (characterIndex == 116 || characterIndex == 118 || (characterIndex == 161 || characterIndex == 163))) || (characterIndex == 165 || characterIndex == 167 || (characterIndex == 168 || characterIndex == 172) || (characterIndex == 174 || characterIndex == 175 || characterIndex == 179) || (characterIndex >= 182 && characterIndex <= 186 || characterIndex == 190 || (characterIndex >= 192 && characterIndex <= 201 || characterIndex >= 203 && characterIndex <= 211))) || (characterIndex == 215 || characterIndex == 219 || characterIndex == 8729))
                                {
                                    str2 = characterMarkerSymbol2.Color;
                                    str3 = "";
                                }
                                else
                                {
                                    str2 = "";
                                    str3 = characterMarkerSymbol2.Color;
                                }
                            }
                            else if (Operators.CompareString(upper2, "ESRI IGL FONT22", false) == 0)
                            {
                                int characterIndex = characterMarkerSymbol2.CharacterIndex;
                                if (characterIndex >= 72 && characterIndex <= 80 || characterIndex == 100 || characterIndex >= 118 && characterIndex <= 121)
                                {
                                    str2 = characterMarkerSymbol2.Color;
                                    str3 = "";
                                }
                                else
                                {
                                    str2 = "";
                                    str3 = characterMarkerSymbol2.Color;
                                }
                            }
                            else if (Operators.CompareString(upper2, "ESRI GEOMETRIC SYMBOLS", false) == 0)
                            {
                                int characterIndex = characterMarkerSymbol2.CharacterIndex;
                                if (characterIndex >= 34 && characterIndex <= 40 || characterIndex == 120 || (characterIndex >= 161 && characterIndex <= 167 || (characterIndex == 187 || characterIndex == 188)) || (characterIndex >= 194 && characterIndex <= 200 || characterIndex >= 202 && characterIndex <= 215 || (characterIndex == 217 || characterIndex == 218 || characterIndex >= 221 && characterIndex <= 229)) || characterIndex >= 231 && characterIndex <= 249)
                                {
                                    str2 = characterMarkerSymbol2.Color;
                                    str3 = "";
                                }
                                else
                                {
                                    str2 = "";
                                    str3 = characterMarkerSymbol2.Color;
                                }
                            }
                            return Operators.CompareString(ValueNameOfValueYouWant.ToUpper(), "PointColor".ToUpper(), false) != 0 ? str3 : str2;
                        }
                        if (Operators.CompareString(upper1, "PointSize".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint(characterMarkerSymbol2.Size);
                        else if (Operators.CompareString(upper1, "PointRotation".ToUpper(), false) == 0)
                            str1 = this.CommaToPoint(characterMarkerSymbol2.Angle);
                        else if (Operators.CompareString(upper1, "PointOutlineSize".ToUpper(), false) == 0)
                        {
                            str1 = "1";
                            if (Operators.CompareString(characterMarkerSymbol2.Font.ToUpper(), "ESRI GEOMETRIC SYMBOLS", false) == 0)
                            {
                                int characterIndex = characterMarkerSymbol2.CharacterIndex;
                                if (characterIndex == 85 || characterIndex == 88 || (characterIndex == 89 || characterIndex == 91) || (characterIndex == 94 || characterIndex == 95 || (characterIndex == 97 || characterIndex == 98)) || characterIndex == 100)
                                    str1 = "2";
                                else if (characterIndex == 41 || characterIndex >= 65 && characterIndex <= 83 || (characterIndex >= 122 && characterIndex <= 125 || (characterIndex == 168 || characterIndex == 169)) || (characterIndex == 188 || characterIndex == 189 || (characterIndex == 216 || characterIndex == 230) || characterIndex == 250))
                                    str1 = "3";
                                else if (characterIndex == 161 || characterIndex >= 170 && characterIndex <= 178 || characterIndex == 186)
                                    str1 = "4";
                            }
                        }
                        else if (upper1.ToUpper().Equals("LINECOLOR"))
                        {
                            str1 = characterMarkerSymbol2.Color;
                        }
                    }

                    return str1;
                }
                if (SymbolStructure is StructPictureMarkerSymbol)
                {
                    object obj = SymbolStructure;
                    StructPictureMarkerSymbol pictureMarkerSymbol1 = new StructPictureMarkerSymbol();
                    StructPictureMarkerSymbol pictureMarkerSymbol2 = obj != null ? (StructPictureMarkerSymbol)obj : pictureMarkerSymbol1;
                    string upper = ValueNameOfValueYouWant.ToUpper();
                    if (Operators.CompareString(upper, "WellKnownName".ToUpper(), false) == 0)
                        str1 = "circle";
                    else if (Operators.CompareString(upper, "PointColor".ToUpper(), false) == 0)
                        str1 = pictureMarkerSymbol2.BackgroundColor;
                    else if (Operators.CompareString(upper, "PointSize".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint(pictureMarkerSymbol2.Size);
                    else if (Operators.CompareString(upper, "PointRotation".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint(pictureMarkerSymbol2.Angle);
                    else if (Operators.CompareString(upper, "PointOutlineColor".ToUpper(), false) == 0)
                        str1 = "";
                    else if (Operators.CompareString(upper, "PointOutlineSize".ToUpper(), false) == 0)
                        str1 = "0";
                    return str1;
                }
                if (SymbolStructure is StructArrowMarkerSymbol)
                {
                    object obj = SymbolStructure;
                    StructArrowMarkerSymbol arrowMarkerSymbol1 = new StructArrowMarkerSymbol();
                    StructArrowMarkerSymbol arrowMarkerSymbol2 = obj != null ? (StructArrowMarkerSymbol)obj : arrowMarkerSymbol1;
                    string upper = ValueNameOfValueYouWant.ToUpper();
                    if (Operators.CompareString(upper, "WellKnownName".ToUpper(), false) == 0)
                        str1 = "triangle";
                    else if (Operators.CompareString(upper, "PointColor".ToUpper(), false) == 0)
                        str1 = arrowMarkerSymbol2.Color;
                    else if (Operators.CompareString(upper, "PointSize".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint(arrowMarkerSymbol2.Size);
                    else if (Operators.CompareString(upper, "PointRotation".ToUpper(), false) == 0)
                        str1 = this.CommaToPoint(arrowMarkerSymbol2.Angle);
                    else if (Operators.CompareString(upper, "PointOutlineColor".ToUpper(), false) == 0)
                        str1 = "";
                    else if (Operators.CompareString(upper, "PointOutlineSize".ToUpper(), false) == 0)
                        str1 = "0";
                    return str1;
                }
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Konnte den Wert aus der SymbolStruct nicht auswerten.", exception.Message, exception.StackTrace, "GetValueFromSymbolstruct");
                ProjectData.ClearProjectError();
            }
            return str1;
        }

        private string CommaToPoint(double value)
        {
            return value.ToString().Replace(",", ".");
        }

        private string CommaToPoint(string value)
        {
            return value.Replace(",", ".");
        }

        private bool writeAnnotation(StructAnnotation Annotation)
        {
            if (Annotation.IsSingleProperty & Operators.CompareString(Annotation.PropertyName, "", false) != 0)
            {
                XMLHandle objXmlHandle = this.m_objXMLHandle;
                objXmlHandle.CreateElement("TextSymbolizer");
                objXmlHandle.CreateElement("TextLabel");
                objXmlHandle.CreateElement("TextLabelProperty");
                objXmlHandle.SetElementText(Annotation.PropertyName);
                objXmlHandle.CreateElement("TextFont");
                objXmlHandle.CreateElement("TextFontCssParameter");
                objXmlHandle.CreateAttribute("name");
                objXmlHandle.SetAttributeValue("font-family");
                objXmlHandle.SetElementText(getValueFromSymbolstruct("TextFont", (object)Annotation.TextSymbol));
                if (Operators.CompareString(getValueFromSymbolstruct("TextFontAlt", (object)Annotation.TextSymbol), "", false) != 0)
                {
                    objXmlHandle.CreateElement("TextFontCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("font-family");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("TextFontAlt", (object)Annotation.TextSymbol));
                }
                objXmlHandle.CreateElement("TextFontCssParameter");
                objXmlHandle.CreateAttribute("name");
                objXmlHandle.SetAttributeValue("font-size");
                objXmlHandle.SetElementText(getValueFromSymbolstruct("TextFontSize", (object)Annotation.TextSymbol));
                objXmlHandle.CreateElement("TextFontCssParameter");
                objXmlHandle.CreateAttribute("name");
                objXmlHandle.SetAttributeValue("font-style");
                objXmlHandle.SetElementText(getValueFromSymbolstruct("TextFontStyle", (object)Annotation.TextSymbol));
                objXmlHandle.CreateElement("TextFontCssParameter");
                objXmlHandle.CreateAttribute("name");
                objXmlHandle.SetAttributeValue("font-weight");
                objXmlHandle.SetElementText(getValueFromSymbolstruct("TextFontWeight", (object)Annotation.TextSymbol));
                objXmlHandle.CreateElement("TextFill");
                objXmlHandle.CreateElement("TextFillCssParameter");
                objXmlHandle.CreateAttribute("name");
                objXmlHandle.SetAttributeValue("fill");
                objXmlHandle.SetElementText(getValueFromSymbolstruct("TextColor", (object)Annotation.TextSymbol));
                objXmlHandle.CreateElement("TextFillCssParameter");
                objXmlHandle.CreateAttribute("name");
                objXmlHandle.SetAttributeValue("fill-opacity");
                objXmlHandle.SetElementText("1.0");
            }
            return true;
        }

        private bool writeSolidFill(object Symbol)
        {
            bool flag;
            try
            {
                StructSimpleFillSymbol struc = (StructSimpleFillSymbol)Symbol;
                int numLinee = struc.Outline_MultiLayerLines.LayerCount;
                if (numLinee > 0)
                {
                    for (int i = numLinee - 1; i >= 0; i--)
                    {
                        XMLHandle objXmlLine = this.m_objXMLHandle;
                        this.m_objXMLHandle.CreateElement("LineSymbolizer");
                        objXmlLine.CreateElement("LineStroke");
                        objXmlLine.CreateElement("LineCssParameter");
                        objXmlLine.CreateAttribute("name");
                        objXmlLine.SetAttributeValue("stroke");
                        objXmlLine.SetElementText(getValueFromSymbolstruct("LineColor", struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        objXmlLine.CreateElement("LineCssParameter");
                        objXmlLine.CreateAttribute("name");
                        objXmlLine.SetAttributeValue("stroke-width");
                        objXmlLine.SetElementText(getValueFromSymbolstruct("LineWidth", struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        objXmlLine.CreateElement("LineCssParameter");
                        objXmlLine.CreateAttribute("name");
                        objXmlLine.SetAttributeValue("stroke-opacity");
                        objXmlLine.SetElementText(getValueFromSymbolstruct("LineOpacity", struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        String isDashArray = getValueFromSymbolstruct("LineDashArray", RuntimeHelpers.GetObjectValue(struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        if (isDashArray != "0")
                        {
                            objXmlLine.CreateElement("LineCssParameter");
                            objXmlLine.CreateAttribute("name");
                            objXmlLine.SetAttributeValue("stroke-dasharray");
                            objXmlLine.SetElementText(getValueFromSymbolstruct("LineDashArray", struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        }
                    }

                }

                XMLHandle objXmlHandle = this.m_objXMLHandle;
                objXmlHandle.CreateElement("PolygonSymbolizer");
                if (Operators.CompareString(getValueFromSymbolstruct("PolygonColor", RuntimeHelpers.GetObjectValue(Symbol)), "", false) != 0)
                {
                    objXmlHandle.CreateElement("Fill");
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonColor", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill-opacity");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonOpacity", RuntimeHelpers.GetObjectValue(Symbol)));
                }
                if (Operators.CompareString(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)), "", false) != 0 & Operators.CompareString(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)), "0", false) != 0)
                {
                    objXmlHandle.CreateElement("PolygonStroke");
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-width");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-opacity");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderOpacity", RuntimeHelpers.GetObjectValue(Symbol)));
                }

                flag = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Schreiben der Flächenfüllung", exception.Message, exception.StackTrace, "WriteSimpleFill");
                flag = false;
                ProjectData.ClearProjectError();
            }
            return flag;
        }
        private bool writeMarkerFill(object Symbol)
        {
            bool flag;
            try
            {
                //if (((UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructMarkerFillSymbol)(RuntimeHelpers.GetObjectValue(Symbol))).MarkerSymbol_MultilayerMarker.LayerCount > 0)
                //{
                //    writeMarkerFill(RuntimeHelpers.GetObjectValue(((UrbamidAddIn_V10_2.Utility.ConvertLayerToSld.StructMarkerFillSymbol)(RuntimeHelpers.GetObjectValue(Symbol))).MarkerSymbol_MultilayerMarker));
                //    return true;
                //}


                XMLHandle objXmlHandle = this.m_objXMLHandle;
                objXmlHandle.CreateElement("PolygonSymbolizer");
                if (Operators.CompareString(getValueFromSymbolstruct("PointColor", RuntimeHelpers.GetObjectValue(Symbol)), "", false) != 0)
                {
                    objXmlHandle.CreateElement("Fill");
                    objXmlHandle.CreateElement("PolygonGraphicFill");
                    objXmlHandle.CreateElement("PolygonGraphic");
                    objXmlHandle.CreateElement("PolygonMark");
                    objXmlHandle.CreateElement("PolygonSize");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PointSize", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolygonWellKnownName");
                    objXmlHandle.SetElementText("circle");
                    objXmlHandle.CreateElement("PolygonGraphicParamFill");
                    objXmlHandle.CreateElement("PolygonGraphicCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PointColor", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolygonGraphicCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill-opacity");
                    objXmlHandle.SetElementText("1.0");
                }
                if (Operators.CompareString(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)), "", false) != 0 & Operators.CompareString(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)), "0", false) != 0)
                {
                    objXmlHandle.CreateElement("PolygonStroke");
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-width");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-opacity");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderOpacity", RuntimeHelpers.GetObjectValue(Symbol)));
                }
                flag = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Schreiben der Punktfüllung", exception.Message, exception.StackTrace, nameof(writeMarkerFill));
                flag = false;
                ProjectData.ClearProjectError();
            }
            return flag;
        }

        private bool writeSlopedHatching(object Symbol)
        {
            bool flag;
            try
            {
                XMLHandle objXmlHandle = this.m_objXMLHandle;
                objXmlHandle.CreateElement("PolygonSymbolizer");
                if (Operators.ConditionalCompareObjectNotEqual(NewLateBinding.LateGet(Symbol, (Type)null, "Color", new object[0], (string[])null, (Type[])null, (bool[])null), (object)"", false))
                {
                    objXmlHandle.CreateElement("Fill");
                    objXmlHandle.CreateElement("PolygonGraphicFill");
                    objXmlHandle.CreateElement("PolygonGraphic");
                    objXmlHandle.CreateElement("PolygonMark");
                    objXmlHandle.CreateElement("PolygonSize");
                    double num = Conversions.ToDouble(Operators.AddObject(NewLateBinding.LateGet(Symbol, (Type)null, "Separation", new object[0], (string[])null, (Type[])null, (bool[])null), (object)5));
                    objXmlHandle.SetElementText(this.CommaToPoint(num));
                    objXmlHandle.CreateElement("PolygonWellKnownName");
                    //objXmlHandle.SetElementText("x");
                    String angolo = getValueFromSymbolstruct("Angle", RuntimeHelpers.GetObjectValue(Symbol));
                    if (!String.IsNullOrEmpty(angolo)) {
                        double dAng = Convert.ToDouble(angolo);
                    if (dAng > 0 && dAng <= 45.99)
                        objXmlHandle.SetElementText("shape://slash");
                    else if(dAng > 45.99 && dAng < 99.99)
                        objXmlHandle.SetElementText("shape://vertline");
                     else if(dAng >= 99.99 && dAng <= 135)
                        objXmlHandle.SetElementText("shape://backslash");
                    }else
                        objXmlHandle.SetElementText("shape://slash");

                    //NEW
                    objXmlHandle.CreateElement("PolygonGraphicStroke");
                    objXmlHandle.CreateElement("PolygonGraphicCssParameterStroke");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke");
                    objXmlHandle.SetElementText(Conversions.ToString(NewLateBinding.LateGet(Symbol, (Type)null, "Color", new object[0], (string[])null, (Type[])null, (bool[])null)));
                    objXmlHandle.CreateElement("PolygonGraphicCssParameterStroke");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-width");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("LineWidth", RuntimeHelpers.GetObjectValue(Symbol)));
                    //FINE NEW


                    objXmlHandle.CreateElement("PolygonGraphicParamFill");
                    objXmlHandle.CreateElement("PolygonGraphicCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill");
                    objXmlHandle.SetElementText(Conversions.ToString(NewLateBinding.LateGet(Symbol, (Type)null, "Color", new object[0], (string[])null, (Type[])null, (bool[])null)));



                    objXmlHandle.CreateElement("PolygonGraphicCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill-opacity");
                    objXmlHandle.SetElementText("1.0");
                                     
                }
                if (Operators.CompareString(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)), "", false) != 0 & Operators.CompareString(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)), "0", false) != 0)
                {
                    objXmlHandle.CreateElement("PolygonStroke");
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-width");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-opacity");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderOpacity", RuntimeHelpers.GetObjectValue(Symbol)));
                }
                flag = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Schreiben der Schraffur", exception.Message, exception.StackTrace, nameof(WriteSlopedHatching));
                flag = false;
                ProjectData.ClearProjectError();
            }
            return flag;
        }

        private bool writePerpendicularHatching(object Symbol)
        {
            bool flag;
            try
            {
                XMLHandle objXmlHandle = this.m_objXMLHandle;
                objXmlHandle.CreateElement("PolygonSymbolizer");
                if (Operators.ConditionalCompareObjectNotEqual(NewLateBinding.LateGet(Symbol, (Type)null, "Color", new object[0], (string[])null, (Type[])null, (bool[])null), (object)"", false))
                {
                    objXmlHandle.CreateElement("Fill");
                    objXmlHandle.CreateElement("PolygonGraphicFill");
                    objXmlHandle.CreateElement("PolygonGraphic");
                    objXmlHandle.CreateElement("PolygonMark");
                    objXmlHandle.CreateElement("PolygonSize");
                    double num = Conversions.ToDouble(Operators.AddObject(NewLateBinding.LateGet(Symbol, (Type)null, "Separation", new object[0], (string[])null, (Type[])null, (bool[])null), (object)5));
                    objXmlHandle.SetElementText(this.CommaToPoint(num));
                    objXmlHandle.CreateElement("PolygonWellKnownName");
                    //objXmlHandle.SetElementText("cross");

                    //NEW
                    String angolo = getValueFromSymbolstruct("Angle", RuntimeHelpers.GetObjectValue(Symbol));
                    if (!String.IsNullOrEmpty(angolo))
                    {
                        double dAng = Convert.ToDouble(angolo);
                        if (dAng == 0)
                            objXmlHandle.SetElementText("shape://horline"); 
                        else
                            objXmlHandle.SetElementText("shape://vertline");
                    }
                    else
                        objXmlHandle.SetElementText("shape://vertline");

                    objXmlHandle.CreateElement("PolygonGraphicStroke");
                    objXmlHandle.CreateElement("PolygonGraphicCssParameterStroke");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke");
                    objXmlHandle.SetElementText(Conversions.ToString(NewLateBinding.LateGet(Symbol, (Type)null, "Color", new object[0], (string[])null, (Type[])null, (bool[])null)));
                    objXmlHandle.CreateElement("PolygonGraphicCssParameterStroke");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-width");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("LineWidth", RuntimeHelpers.GetObjectValue(Symbol)));
                    //FINE NEW


                    objXmlHandle.CreateElement("PolygonGraphicParamFill");
                    objXmlHandle.CreateElement("PolygonGraphicCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill");
                    objXmlHandle.SetElementText(Conversions.ToString(NewLateBinding.LateGet(Symbol, (Type)null, "Color", new object[0], (string[])null, (Type[])null, (bool[])null)));
                    objXmlHandle.CreateElement("PolygonGraphicCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill-opacity");
                    objXmlHandle.SetElementText("1.0");
                }
                if (Operators.CompareString(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)), "", false) != 0 & Operators.CompareString(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)), "0", false) != 0)
                {
                    objXmlHandle.CreateElement("PolygonStroke");
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-width");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-opacity");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderOpacity", RuntimeHelpers.GetObjectValue(Symbol)));
                }
                flag = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Schreiben der Punktfüllung", exception.Message, exception.StackTrace, nameof(WritePerpendicularHatching));
                flag = false;
                ProjectData.ClearProjectError();
            }
            return flag;
        }


        private bool writePolygonWithPoint(object Symbol)
        {
            bool flag;
            try
            {
                XMLHandle objXmlHandle = this.m_objXMLHandle;
                objXmlHandle.CreateElement("PolygonSymbolizer");
                if (Operators.ConditionalCompareObjectNotEqual(NewLateBinding.LateGet(Symbol, (Type)null, "Color", new object[0], (string[])null, (Type[])null, (bool[])null), (object)"", false))
                {
                    objXmlHandle.CreateElement("Fill");
                    objXmlHandle.CreateElement("PolygonGraphicFill");
                    objXmlHandle.CreateElement("PolygonGraphic");
                    objXmlHandle.CreateElement("PolygonMark");
                    objXmlHandle.CreateElement("PolygonSize");
                    double num = Double.Parse(getValueFromSymbolstruct("Width", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.SetElementText("10");
                    objXmlHandle.CreateElement("PolygonWellKnownName");
                    objXmlHandle.SetElementText("shape://dot");


                    objXmlHandle.CreateElement("PolygonGraphicStroke");
                    objXmlHandle.CreateElement("PolygonGraphicCssParameterStroke");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke");
                    objXmlHandle.SetElementText(Conversions.ToString(NewLateBinding.LateGet(Symbol, (Type)null, "Color", new object[0], (string[])null, (Type[])null, (bool[])null)));
                    objXmlHandle.CreateElement("PolygonGraphicCssParameterStroke");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-width");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("Width", RuntimeHelpers.GetObjectValue(Symbol)));
                    //FINE NEW


                    objXmlHandle.CreateElement("PolygonGraphicParamFill");
                    objXmlHandle.CreateElement("PolygonGraphicCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill");
                    String bkColor = Conversions.ToString(NewLateBinding.LateGet(Symbol, (Type)null, "BackgroundColor", new object[0], (string[])null, (Type[])null, (bool[])null));
                    if (String.IsNullOrEmpty(bkColor))
                        bkColor = "#FFFFFF";
                    objXmlHandle.SetElementText(bkColor);
                    objXmlHandle.CreateElement("PolygonGraphicCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("fill-opacity");
                    objXmlHandle.SetElementText("1.0");
                }
                if (Operators.CompareString(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)), "", false) != 0 & Operators.CompareString(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)), "0", false) != 0)
                {
                    objXmlHandle.CreateElement("PolygonStroke");
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderColor", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-width");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderWidth", RuntimeHelpers.GetObjectValue(Symbol)));
                    objXmlHandle.CreateElement("PolyCssParameter");
                    objXmlHandle.CreateAttribute("name");
                    objXmlHandle.SetAttributeValue("stroke-opacity");
                    objXmlHandle.SetElementText(getValueFromSymbolstruct("PolygonBorderOpacity", RuntimeHelpers.GetObjectValue(Symbol)));
                }
                int numLinee = 0;
                StructSimpleFillSymbol struc = new StructSimpleFillSymbol();
                try
                {

                    struc = (StructSimpleFillSymbol)Symbol;
                    numLinee = struc.Outline_MultiLayerLines.LayerCount;
                }
                catch (Exception)
                {

                    numLinee = 0;
                }

                
                if (numLinee > 0)
                {
                    for (int i = numLinee - 1; i >= 0; i--)
                    {
                        XMLHandle objXmlLine = this.m_objXMLHandle;
                        this.m_objXMLHandle.CreateElement("LineSymbolizer");
                        objXmlLine.CreateElement("LineStroke");
                        objXmlLine.CreateElement("LineCssParameter");
                        objXmlLine.CreateAttribute("name");
                        objXmlLine.SetAttributeValue("stroke");
                        objXmlLine.SetElementText(getValueFromSymbolstruct("LineColor", struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        objXmlLine.CreateElement("LineCssParameter");
                        objXmlLine.CreateAttribute("name");
                        objXmlLine.SetAttributeValue("stroke-width");
                        objXmlLine.SetElementText(getValueFromSymbolstruct("LineWidth", struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        objXmlLine.CreateElement("LineCssParameter");
                        objXmlLine.CreateAttribute("name");
                        objXmlLine.SetAttributeValue("stroke-opacity");
                        objXmlLine.SetElementText(getValueFromSymbolstruct("LineOpacity", struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        String isDashArray = getValueFromSymbolstruct("LineDashArray", RuntimeHelpers.GetObjectValue(struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        if (isDashArray != "0")
                        {
                            objXmlLine.CreateElement("LineCssParameter");
                            objXmlLine.CreateAttribute("name");
                            objXmlLine.SetAttributeValue("stroke-dasharray");
                            objXmlLine.SetElementText(getValueFromSymbolstruct("LineDashArray", struc.Outline_MultiLayerLines.MultiLineLayers[i]));
                        }
                    }
                }
                flag = true;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Schreiben der Punktfüllung", exception.Message, exception.StackTrace, nameof(WritePerpendicularHatching));
                flag = false;
                ProjectData.ClearProjectError();
            }
            return flag;
        }


        internal StructClassBreaksRenderer getStructBreacks(IClassBreaksRenderer renderer, IFeatureLayer layer, bool isLayerDb)
        {
            StructClassBreaksRenderer classBreaksRenderer1 = new StructClassBreaksRenderer();
            classBreaksRenderer1.SymbolList = new ArrayList();
            int breakCount = renderer.BreakCount;
            classBreaksRenderer1.BreakCount = renderer.BreakCount;
            IDataset featureClass = (IDataset)layer.FeatureClass;
            bool flag = false;
            StructClassBreaksRenderer classBreaksRenderer2 = new StructClassBreaksRenderer(); 
            try
            {
                classBreaksRenderer1.LayerName = layer.Name;
                classBreaksRenderer1.DatasetName = featureClass.Name;
                classBreaksRenderer1.Annotation = getAnnotation(layer, isLayerDb); // this.GetAnnotation(Layer);
                if (((IDisplayTable)layer).DisplayTable is IRelQueryTable)
                    flag = true;
                if (!flag)
                {
                    classBreaksRenderer1.FieldName = renderer.Field;
                    classBreaksRenderer1.NormFieldName = renderer.NormField;
                }
                int num1 = 0;
                int num2 = checked(breakCount - 1);
                int num3 = num1;
                while (num3 <= num2)
                {
                    ISymbol symbol = renderer.get_Symbol(num3);
                    string str1 = Conversions.ToString(((IClassBreaksUIProperties)renderer).get_LowBreak(num3));
                    string str2 = Conversions.ToString(renderer.get_Break(num3));
                    if (symbol is ITextSymbol)
                    {
                        StructTextSymbol structTextSymbol = storeText((ITextSymbol)symbol);
                        structTextSymbol.Label = renderer.get_Label(num3);
                        structTextSymbol.LowerLimit = Conversions.ToDouble(str1);
                        structTextSymbol.UpperLimit = Conversions.ToDouble(str2);
                        classBreaksRenderer1.SymbolList.Add((object)structTextSymbol);
                    }
                    if (symbol is IMarkerSymbol)
                    {
                        classBreaksRenderer1.FeatureCls = Feature.PointFeature;
                        IMarkerSymbol Symbol = (IMarkerSymbol)symbol;
                        string Left = markerSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ISimpleMarkerSymbol", false) == 0)
                        {
                            StructSimpleMarkerSymbol simpleMarkerSymbol = storeSimpleMarker((ISimpleMarkerSymbol)Symbol);
                            simpleMarkerSymbol.Label = renderer.get_Label(num3);
                            simpleMarkerSymbol.LowerLimit = Conversions.ToDouble(str1);
                            simpleMarkerSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)simpleMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "ICharacterMarkerSymbol", false) == 0)
                        {
                            StructCharacterMarkerSymbol characterMarkerSymbol = storeCharacterMarker((ICharacterMarkerSymbol)Symbol);
                            characterMarkerSymbol.Label = renderer.get_Label(num3);
                            characterMarkerSymbol.LowerLimit = Conversions.ToDouble(str1);
                            characterMarkerSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)characterMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureMarkerSymbol", false) == 0)
                        {
                            StructPictureMarkerSymbol pictureMarkerSymbol = storePictureMarker((IPictureMarkerSymbol)Symbol);
                            pictureMarkerSymbol.Label = renderer.get_Label(num3);
                            pictureMarkerSymbol.LowerLimit = Conversions.ToDouble(str1);
                            pictureMarkerSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)pictureMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IArrowMarkerSymbol", false) == 0)
                        {
                            StructArrowMarkerSymbol arrowMarkerSymbol = storeArrowMarker((IArrowMarkerSymbol)Symbol);
                            arrowMarkerSymbol.Label = renderer.get_Label(num3);
                            arrowMarkerSymbol.LowerLimit = Conversions.ToDouble(str1);
                            arrowMarkerSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)arrowMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerMarkerSymbol", false) == 0)
                        {
                            StructMultilayerMarkerSymbol multilayerMarkerSymbol = storeMultiLayerMarker((IMultiLayerMarkerSymbol)Symbol);
                            multilayerMarkerSymbol.Label = renderer.get_Label(num3);
                            multilayerMarkerSymbol.LowerLimit = Conversions.ToDouble(str1);
                            multilayerMarkerSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)multilayerMarkerSymbol);
                        }
                        else if (Operators.CompareString(Left, "false", false) == 0)
                        {
                            //    this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", "StoreStructLayer");
                            //SIMBOLO NON SUPPORTATO
                        }
                    }
                    if (symbol is ILineSymbol)
                    {
                        classBreaksRenderer1.FeatureCls = Feature.LineFeature;
                        ILineSymbol Symbol = (ILineSymbol)symbol;
                        string Left = lineSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ICartographicLineSymbol", false) == 0)
                        {
                            StructCartographicLineSymbol cartographicLineSymbol = storeCartographicLine((ICartographicLineSymbol)Symbol);
                            cartographicLineSymbol.Label = renderer.get_Label(num3);
                            cartographicLineSymbol.LowerLimit = Conversions.ToDouble(str1);
                            cartographicLineSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)cartographicLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IHashLineSymbol", false) == 0)
                        {
                            StructHashLineSymbol structHashLineSymbol = storeHashLine((IHashLineSymbol)Symbol);
                            structHashLineSymbol.Label = renderer.get_Label(num3);
                            structHashLineSymbol.LowerLimit = Conversions.ToDouble(str1);
                            structHashLineSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)structHashLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMarkerLineSymbol", false) == 0)
                        {
                            StructMarkerLineSymbol markerLineSymbol = storeMarkerLine((IMarkerLineSymbol)Symbol);
                            markerLineSymbol.Label = renderer.get_Label(num3);
                            markerLineSymbol.LowerLimit = Conversions.ToDouble(str1);
                            markerLineSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)markerLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "ISimpleLineSymbol", false) == 0)
                        {
                            StructSimpleLineSymbol simpleLineSymbol = storeSimpleLine((ISimpleLineSymbol)Symbol);
                            simpleLineSymbol.Label = renderer.get_Label(num3);
                            simpleLineSymbol.LowerLimit = Conversions.ToDouble(str1);
                            simpleLineSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)simpleLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureLineSymbol", false) == 0)
                        {
                            StructPictureLineSymbol pictureLineSymbol = storePictureLine((IPictureLineSymbol)Symbol);
                            pictureLineSymbol.Label = renderer.get_Label(num3);
                            pictureLineSymbol.LowerLimit = Conversions.ToDouble(str1);
                            pictureLineSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)pictureLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerLineSymbol", false) == 0)
                        {
                            StructMultilayerLineSymbol multilayerLineSymbol = storeMultilayerLines((IMultiLayerLineSymbol)Symbol);
                            multilayerLineSymbol.Label = renderer.get_Label(num3);
                            multilayerLineSymbol.LowerLimit = Conversions.ToDouble(str1);
                            multilayerLineSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)multilayerLineSymbol);
                        }
                        else if (Operators.CompareString(Left, "false", false) == 0)
                        {
                            //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", "StoreStructLayer");
                            // SIMBOLO NON SUPPORTATO
                        }
                    }
                    if (symbol is IFillSymbol)
                    {
                        classBreaksRenderer1.FeatureCls = Feature.PolygonFeature;
                        IFillSymbol Symbol = (IFillSymbol)symbol;
                        string Left = fillSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "ISimpleFillSymbol", false) == 0)
                        {
                            StructSimpleFillSymbol simpleFillSymbol = storeSimpleFill((ISimpleFillSymbol)Symbol);
                            simpleFillSymbol.Label = renderer.get_Label(num3);
                            simpleFillSymbol.LowerLimit = Conversions.ToDouble(str1);
                            simpleFillSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)simpleFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMarkerFillSymbol", false) == 0)
                        {
                            StructMarkerFillSymbol markerFillSymbol = storeMarkerFill((IMarkerFillSymbol)Symbol);
                            markerFillSymbol.Label = renderer.get_Label(num3);
                            markerFillSymbol.LowerLimit = Conversions.ToDouble(str1);
                            markerFillSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)markerFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "ILineFillSymbol", false) == 0)
                        {
                            StructLineFillSymbol structLineFillSymbol = storeLineFill((ILineFillSymbol)Symbol);
                            structLineFillSymbol.Label = renderer.get_Label(num3);
                            structLineFillSymbol.LowerLimit = Conversions.ToDouble(str1);
                            structLineFillSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)structLineFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IDotDensityFillSymbol", false) == 0)
                        {
                            StructDotDensityFillSymbol densityFillSymbol = storeDotDensityFill((IDotDensityFillSymbol)Symbol);
                            densityFillSymbol.Label = renderer.get_Label(num3);
                            densityFillSymbol.LowerLimit = Conversions.ToDouble(str1);
                            densityFillSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)densityFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPictureFillSymbol", false) == 0)
                        {
                            StructPictureFillSymbol pictureFillSymbol = storePictureFill((IPictureFillSymbol)Symbol);
                            pictureFillSymbol.Label = renderer.get_Label(num3);
                            pictureFillSymbol.LowerLimit = Conversions.ToDouble(str1);
                            pictureFillSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)pictureFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IGradientFillSymbol", false) == 0)
                        {
                            StructGradientFillSymbol gradientFillSymbol = storeGradientFill((IGradientFillSymbol)Symbol);
                            gradientFillSymbol.Label = renderer.get_Label(num3);
                            gradientFillSymbol.LowerLimit = Conversions.ToDouble(str1);
                            gradientFillSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)gradientFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "IMultiLayerFillSymbol", false) == 0)
                        {
                            StructMultilayerFillSymbol multilayerFillSymbol = storeMultiLayerFill((IMultiLayerFillSymbol)Symbol);
                            multilayerFillSymbol.Label = renderer.get_Label(num3);
                            multilayerFillSymbol.LowerLimit = Conversions.ToDouble(str1);
                            multilayerFillSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)multilayerFillSymbol);
                        }
                        else if (Operators.CompareString(Left, "false", false) == 0)
                        {
                            //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", "StoreStructLayer");
                            // SIMBOLO NON SUPPORTATO
                        }
                    }
                    if (symbol is I3DChartSymbol)
                    {
                        I3DChartSymbol Symbol = (I3DChartSymbol)symbol;
                        string Left = this.IIIDChartSymbolScan(Symbol);
                        if (Operators.CompareString(Left, "IBarChartSymbol", false) == 0)
                        {
                            StructBarChartSymbol structBarChartSymbol = storeBarChart((IBarChartSymbol)Symbol);
                            structBarChartSymbol.Label = renderer.get_Label(num3);
                            structBarChartSymbol.LowerLimit = Conversions.ToDouble(str1);
                            structBarChartSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)structBarChartSymbol);
                        }
                        else if (Operators.CompareString(Left, "IPieChartSymbol", false) == 0)
                        {
                            StructPieChartSymbol structPieChartSymbol = storePieChart((IPieChartSymbol)Symbol);
                            structPieChartSymbol.Label = renderer.get_Label(num3);
                            structPieChartSymbol.LowerLimit = Conversions.ToDouble(str1);
                            structPieChartSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)structPieChartSymbol);
                        }
                        else if (Operators.CompareString(Left, "IStackedChartSymbol", false) == 0)
                        {
                            StructStackedChartSymbol stackedChartSymbol = storeStackedChart((IStackedChartSymbol)Symbol);
                            stackedChartSymbol.Label = renderer.get_Label(num3);
                            stackedChartSymbol.LowerLimit = Conversions.ToDouble(str1);
                            stackedChartSymbol.UpperLimit = Conversions.ToDouble(str2);
                            classBreaksRenderer1.SymbolList.Add((object)stackedChartSymbol);
                        }
                        else if (Operators.CompareString(Left, "false", false) == 0)
                        {
                            //this.InfoMsg("Seit Erstellen der Programmversion ist eine neue Symbolvariante zu den esri-Symbolen hinzugekommen", "StoreStructLayer");
                            // SIMBOLO NON SUPPORTATO
                        }
                    }
                    checked { ++num3; }
                }
                classBreaksRenderer2 = classBreaksRenderer1;
            }
            catch (Exception ex)
            {
                ProjectData.SetProjectError(ex);
                Exception exception = ex;
                //this.ErrorMsg("Fehler beim Speichern der Symbole in den Layerstrukturen", exception.Message, exception.StackTrace, nameof(StoreStructCBRenderer));
                ProjectData.ClearProjectError();
            }
            return classBreaksRenderer2;
        }
    }
}
