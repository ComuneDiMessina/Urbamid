using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class GetDettaglioLayer_InDto
    {
        public string baseUrl { get; set; }
        public string nomeLayer { get; set; }
        public string workspace { get; set; }
        public bool isLayerDb { get; set; }
    }
}
