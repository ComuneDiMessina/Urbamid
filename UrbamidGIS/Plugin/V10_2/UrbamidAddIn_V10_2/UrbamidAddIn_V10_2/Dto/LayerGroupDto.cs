using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class LayerGroups
    {

       
        public IList<LayerGroup> layerGroup { get; set; }
    }

    public class LayerGroupDto
    {

        [JsonProperty("layerGroups")]
        public LayerGroups layerGroups { get; set; }
    }

    public class LayerGroup
    {

        [JsonProperty("name")]
        public string name { get; set; }

        [JsonProperty("mode")]
        public string mode { get; set; }

        [JsonProperty("title")]
        public string title { get; set; }

        [JsonProperty("abstractTxt")]
        public string abstractTxt { get; set; }

        [JsonProperty("workspace")]
        public Workspace workspace { get; set; }

        [JsonProperty("publishables")]
        public Publishables publishables { get; set; }

        [JsonProperty("styles")]
        public Styles styles { get; set; }

        [JsonProperty("bounds")]
        public Bounds bounds { get; set; }

        [JsonProperty("keywords")]
        public Keywords keywords { get; set; }

        [JsonProperty("href")]
        public string href { get; set; }
    }


}
