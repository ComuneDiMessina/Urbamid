using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class Namespace
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("href")]
        public string href { get; set; }
    }

    public class NativeCRS
    {

        [JsonProperty("@class")]
        public string Classe { get; set; }

        [JsonProperty("$")]
        public string Valore { get; set; }
    }

    public class NativeBoundingBox
    {

        [JsonProperty("minx")]
        public double minx { get; set; }

        [JsonProperty("maxx")]
        public double maxx { get; set; }

        [JsonProperty("miny")]
        public double miny { get; set; }

        [JsonProperty("maxy")]
        public double maxy { get; set; }

    }

    public class LatLonBoundingBox
    {

        [JsonProperty("minx")]
        public double minx { get; set; }

        [JsonProperty("maxx")]
        public double maxx { get; set; }

        [JsonProperty("miny")]
        public double miny { get; set; }

        [JsonProperty("maxy")]
        public double maxy { get; set; }

        [JsonProperty("crs")]
        public string crs { get; set; }
    }

    public class Store
    {

        [JsonProperty("@class")]
        public string Classe { get; set; }

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("href")]
        public string href { get; set; }
    }

    public class Attribute
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("minOccurs")]
        public int minOccurs { get; set; }

        [JsonProperty("maxOccurs")]
        public int maxOccurs { get; set; }

        [JsonProperty("nillable")]
        public bool nillable { get; set; }

        [JsonProperty("binding")]
        public string binding { get; set; }

        [JsonProperty("length")]
        public int? length { get; set; }
    }

    public class Attributes
    {

        [JsonProperty("attribute")]
        public IList<Attribute> attribute { get; set; }
    }

    public class FeatureType
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("nativeName")]
        public string nativeName { get; set; }

        [JsonProperty("namespace")]
        public Namespace _space { get; set; }

        [JsonProperty("title")]
        public string title { get; set; }

        [JsonProperty("abstract")]
        public string astratto { get; set; }

        [JsonProperty("keywords")]
        public Keywords keywords { get; set; }

        //[JsonProperty("nativeCRS")]
        //public NativeCRS nativeCRS { get; set; }

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

        [JsonProperty("store")]
        public Store store { get; set; }

        [JsonProperty("serviceConfiguration")]
        public bool serviceConfiguration { get; set; }

        [JsonProperty("maxFeatures")]
        public int maxFeatures { get; set; }

        [JsonProperty("numDecimals")]
        public int numDecimals { get; set; }

        [JsonProperty("padWithZeros")]
        public bool padWithZeros { get; set; }

        [JsonProperty("forcedDecimal")]
        public bool forcedDecimal { get; set; }

        [JsonProperty("overridingServiceSRS")]
        public bool overridingServiceSRS { get; set; }

        [JsonProperty("skipNumberMatched")]
        public bool skipNumberMatched { get; set; }

        [JsonProperty("circularArcPresent")]
        public bool circularArcPresent { get; set; }

        [JsonProperty("attributes")]
        public Attributes attributes { get; set; }
    }

    public class FeaturesTypeDto
    {

        [JsonProperty("featureType")]
        public FeatureType featureType { get; set; }
    }
}
