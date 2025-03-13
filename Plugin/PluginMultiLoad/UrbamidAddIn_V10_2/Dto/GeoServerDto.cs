using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public enum TipoMessaggio
    {
        ERRORE,
        INFO,
        WARNING

    }


    public class GeoServerDto : BaseDto
    {
        public string layerName { get; set; }

        public string pathFile { get; set; }

        public string storeName { get; set; }

        public string linkDettaglio { get; set; }

    }

    public class WMSStoreDto : BaseDto
    {
        public string storeName { get; set; }
        public string linkWMS { get; set; }

    }

    public class StoreWmsDto
    {
        [JsonProperty("wmsStores")]
        public StoresWms WmsStore;
    }


    public class StoresWms
    {
        [JsonProperty("wmsStore")]
        public List<StoreWms> WmsStore;
    }

    public class StoreWms
    {
        [JsonProperty("name")]
        public string Name;

        [JsonProperty("href")]
        public string Href;
    }



    public class LayerWMSDto : WMSStoreDto
    {
        public string nomeLayer { get; set; }
        public string titoloLayer { get; set; }

    }
}
