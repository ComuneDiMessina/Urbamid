using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class DefaultStyle
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("href")]
        public string href { get; set; }
    }

    public class Resource
    {

        [JsonProperty("@class")]
        public string @class { get; set; }

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("href")]
        public string href { get; set; }
    }

    public class Attribution
    {

        [JsonProperty("logoWidth")]
        public int logoWidth { get; set; }

        [JsonProperty("logoHeight")]
        public int logoHeight { get; set; }
    }

    public class Layer
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("href")]
        public string link { get; set; }

        [JsonProperty("type")]
        public string type { get; set; }

        [JsonProperty("defaultStyle")]
        public DefaultStyle defaultStyle { get; set; }

        [JsonProperty("resource")]
        public Resource resource { get; set; }

        [JsonProperty("attribution")]
        public Attribution attribution { get; set; }
    }

    public class LayerDetailDto : BaseDto
    {

        [JsonProperty("layer")]
        public Layer layer { get; set; }
    }
}
