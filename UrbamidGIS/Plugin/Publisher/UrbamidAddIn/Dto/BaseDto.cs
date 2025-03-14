using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn.Dto
{
    public enum TipoAttributoEnum
    {
        [Description("Solo Visualizzazione")]
        Visualizzazione = 1,
        [Description("Attributo di Ricerca")]
        Ricerca = 2,
        [Description("Ricerca e Visualizzazione")]
        Tutto = 3,
        [Description("Attributo non visibile")]
        Nascondi = 4

    }
    public class BaseDto
    {
        public string baseUrl { get; set; }
        public string password { get; set; }
        public string username { get; set; }
        public string workspace { get; set; }
    }

    public class GenericLayer
    {
        public string nome { get; set; }
        public string titolo { get; set; }
        public string linkFeature { get; set; }

        public string linkDetail { get; set; }

        public bool isLayerGroup { get; set; }
    }
}
