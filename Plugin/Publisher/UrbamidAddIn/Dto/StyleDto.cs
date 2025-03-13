using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn.Dto
{
    public class StyleDto
    {
        [JsonProperty("styles")]
        public Styles styles { get; set; }
    }
}
