using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    //public class CreateLayerGroupInDto : BaseDto
    //{
    //    public string nome { get; set; }
    //    public string titolo { get; set; }
    //}
 
    public class CreateLayerGroupInDto : BaseDto
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
        public Workspace workspaces { get; set; }

        [JsonProperty("publishables")]
        public Publishables publishables { get; set; }

        [JsonProperty("styles")]
        public Styles styles { get; set; }

        //[JsonProperty("metadataLinks")]
        //public IList<MetadataLink> metadataLinks { get; set; }

        [JsonProperty("bounds")]
        public Bounds bounds { get; set; }

        [JsonProperty("keywords")]
        public Keywords keywords { get; set; }
    }


}
