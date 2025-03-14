using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn.Dto
{
    public class LayerGroups
    {

        [JsonProperty("layerGroup")]
        public IList<LayerGroup> layerGroup { get; set; }
    }

    public class LayerGroupDto
    {

        [JsonProperty("layerGroups")]
        public LayerGroups layerGroups { get; set; }
    }


}
