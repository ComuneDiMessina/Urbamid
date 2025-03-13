using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class StyleDto
    {
        [JsonProperty("styles")]
        public Styles styles { get; set; }
    }

    public class Style
    {


        [JsonProperty("name", NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public string name { get; set; }
        [JsonProperty("href", NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public string href { get; set; }
    }

    public class Styles
    {

        [JsonProperty("style")]
        public IList<Style> style { get; set; }
    }
}
