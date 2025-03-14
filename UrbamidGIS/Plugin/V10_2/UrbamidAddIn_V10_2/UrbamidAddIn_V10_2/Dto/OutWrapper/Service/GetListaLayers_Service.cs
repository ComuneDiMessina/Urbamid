using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class GetListaLayers_Service : Wrapper_BaseOut
    {  
        [JsonProperty("aaData")]
        public IList<Layer> lista { get; set; }
    }
}
