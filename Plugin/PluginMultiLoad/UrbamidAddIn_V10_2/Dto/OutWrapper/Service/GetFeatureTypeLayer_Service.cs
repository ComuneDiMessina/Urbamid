using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class GetFeatureTypeLayer_Service : Wrapper_BaseOut
    {
        [JsonProperty("aaData")]
        public Features dettaglio { get; set; }
    }

    public class Features
    {
        [JsonProperty("featureType")]
        public FeatureType featureType { get; set; }
    }
}
