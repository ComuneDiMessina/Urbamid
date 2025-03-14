using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class WmsStore
    {
        [JsonProperty("name")]
        public string Name;

        [JsonProperty("href")]
        public string Href;
    }

    public class WmsStores
    {
        [JsonProperty("wmsStore")]
        public List<WmsStore> WmsStore;
    }

    public class Root
    {
        [JsonProperty("wmsStores")]
        public WmsStores WmsStores;
    }

    public partial class WmsStoreDTO
    {
        [JsonProperty("wmsStores")]
        public WmsStores WmsStores { get; set; }
    }

    public class TipologicaDTO
    {
        public string Nome { get; set; }
        public string Titolo { get; set; }
    }

   
    public partial class WmsFeatureDto
    {
        public WmsLayer wmsLayer;
    }

    public class WmsLayer
    {
        public string name;
        public string nativeName;
        public Namespace @namespace;
        public string title;
        public string description;
        public string @abstract;
        //public NativeCRS nativeCRS;
        public string srs;
        public NativeBoundingBox nativeBoundingBox;
        public LatLonBoundingBox latLonBoundingBox;
        public string projectionPolicy;
        public bool enabled;
        public Store store;
        public bool serviceConfiguration;
        public Keywords keywords;
    }


    public partial class LayerWmsDto
    {
        [JsonProperty("layer")]
        public Layer Layer { get; set; }
    }


    public partial class BoundingBox
    {
        [JsonProperty("minx")]
        public double Minx { get; set; }

        [JsonProperty("maxx")]
        public double Maxx { get; set; }

        [JsonProperty("miny")]
        public double Miny { get; set; }

        [JsonProperty("maxy")]
        public double Maxy { get; set; }

        [JsonProperty("crs")]
        public Crs Crs { get; set; }
    }

}
