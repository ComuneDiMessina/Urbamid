using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UrbamidAddIn_V10_2.Dto;

namespace UrbamidAddIn_V10_2.Dto
{
    public class AaData
    {
        [JsonProperty("layer")]
        public Layer layer { get; set; }
    }

    public class Feauture
    {
        [JsonProperty("featuretype")]
        public FeatureType featuretype { get; set; }
    }
}
