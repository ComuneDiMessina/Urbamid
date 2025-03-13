using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class GetListaLayerGroups_Service : Wrapper_BaseOut
    {            
        [JsonProperty("aaData")]
        public ElencoGruppi dettaglio { get; set; }
    }

    public class ElencoGruppi
    {
         [JsonProperty("layerGroups")]
        public LayerGroups listaGruppi { get; set; }
    }

    
}
