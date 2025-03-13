using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class PublishTable_InDto
    {
        public string baseUrl { get; set; }
        public string dataStore { get; set; }
        public string nomeLayer { get; set; }
        public string titoloLayer { get; set; }
        public string workspace { get; set; }
        public bool shapeToDb { get; set; }
        public bool estrazionePart { get; set; }
        public bool defaultView { get; set; }
    }
}
