using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
   
    public class Crs
    {

        [JsonProperty("@class")]
        public string Classe { get; set; }

        [JsonProperty("$")]
        public string Valore { get; set; }
    }

    public class Entry
    {

        [JsonProperty("@key")]
        public string EntryKey { get; set; }

        [JsonProperty("$")]
        public string Valore { get; set; }
    }

    public class Metadata
    {

        [JsonProperty("entry")]
        public IList<Entry> entry { get; set; }
    }

    public class Range
    {

        [JsonProperty("low")]
        public string low { get; set; }

        [JsonProperty("high")]
        public string high { get; set; }
    }

    public class Transform
    {

        [JsonProperty("scaleX")]
        public double scaleX { get; set; }

        [JsonProperty("scaleY")]
        public double scaleY { get; set; }

        [JsonProperty("shearX")]
        public double shearX { get; set; }

        [JsonProperty("shearY")]
        public double shearY { get; set; }

        [JsonProperty("translateX")]
        public double translateX { get; set; }

        [JsonProperty("translateY")]
        public double translateY { get; set; }
    }

    public class Grid
    {

        [JsonProperty("@dimension")]
        public string GridDimension { get; set; }

        [JsonProperty("range")]
        public Range range { get; set; }

        [JsonProperty("transform")]
        public Transform transform { get; set; }

        [JsonProperty("crs")]
        public string crs { get; set; }
    }

    public class SupportedFormats
    {

        [JsonProperty("string")]
        public IList<string> SupportedFormatsValues { get; set; }
    }

    public class InterpolationMethods
    {

        [JsonProperty("string")]
        public IList<string> InterpolationMethodsValues { get; set; }
    }

    public class DimensionType
    {

        [JsonProperty("name")]
        public string name { get; set; }
    }

    public class CoverageDimension
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("description")]
        public string description { get; set; }



        [JsonProperty("dimensionType")]
        public DimensionType dimensionType { get; set; }
    }

    public class Dimensions
    {

        [JsonProperty("coverageDimension")]
        public IList<CoverageDimension> coverageDimension { get; set; }
    }

    public class RequestSRS
    {

        [JsonProperty("string")]
        public IList<string> RequestSrs { get; set; }
    }

    public class ResponseSRS
    {

        [JsonProperty("string")]
        public IList<string> ResponseSrs { get; set; }
    }

    public class Coverage
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("nativeName")]
        public string nativeName { get; set; }

        [JsonProperty("namespace")]
        public Namespace NameSpaceValue { get; set; }

        [JsonProperty("title")]
        public string title { get; set; }

        [JsonProperty("description")]
        public string description { get; set; }

        [JsonProperty("keywords")]
        public Keywords keywords { get; set; }

        [JsonProperty("nativeCRS")]
        public NativeCRS nativeCRS { get; set; }

        [JsonProperty("srs")]
        public string srs { get; set; }

        //[JsonProperty("nativeBoundingBox")]
        //public NativeBoundingBox nativeBoundingBox { get; set; }

        [JsonProperty("latLonBoundingBox")]
        public LatLonBoundingBox latLonBoundingBox { get; set; }

        [JsonProperty("projectionPolicy")]
        public string projectionPolicy { get; set; }

        [JsonProperty("enabled")]
        public bool enabled { get; set; }

        //[JsonProperty("metadata")]
        //public Metadata metadata { get; set; }

        [JsonProperty("store")]
        public Store store { get; set; }

        [JsonProperty("serviceConfiguration")]
        public bool serviceConfiguration { get; set; }

        [JsonProperty("nativeFormat")]
        public string nativeFormat { get; set; }

        [JsonProperty("grid")]
        public Grid grid { get; set; }

        [JsonProperty("supportedFormats")]
        public SupportedFormats supportedFormats { get; set; }

        [JsonProperty("interpolationMethods")]
        public InterpolationMethods interpolationMethods { get; set; }

        [JsonProperty("defaultInterpolationMethod")]
        public string defaultInterpolationMethod { get; set; }

        [JsonProperty("dimensions")]
        public Dimensions dimensions { get; set; }

        [JsonProperty("requestSRS")]
        public RequestSRS requestSRS { get; set; }

        [JsonProperty("responseSRS")]
        public ResponseSRS responseSRS { get; set; }

        [JsonProperty("nativeCoverageName")]
        public string nativeCoverageName { get; set; }
    }

    public class CoverageDto
    {

        [JsonProperty("coverage")]
        public Coverage coverage { get; set; }
    }
}
