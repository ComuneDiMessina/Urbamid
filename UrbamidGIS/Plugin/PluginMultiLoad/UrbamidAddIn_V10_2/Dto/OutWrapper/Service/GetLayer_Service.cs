using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class GetLayer_Service : Wrapper_BaseOut
    {
        [JsonProperty("aaData")]
        public DettaglioLayer dettaglio { get; set; }
    }

    public class DettaglioLayer
    {
        [JsonProperty("layer")]
        public Layer layer { get; set; }
    }
}
