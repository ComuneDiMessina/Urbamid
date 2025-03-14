using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
   
    public class Layers
    {

        [JsonProperty("layer")]
        public IList<Layer> layer { get; set; }
    }

    public class GetListaLayerOutDto
    {

        [JsonProperty("layers")]
        public Layers layers { get; set; }
    }
}
