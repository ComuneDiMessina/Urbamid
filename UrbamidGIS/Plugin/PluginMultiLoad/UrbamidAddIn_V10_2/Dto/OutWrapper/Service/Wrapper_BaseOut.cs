using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class Wrapper_BaseOut
    {
        [JsonProperty("success")]
        public bool success { get; set; }

        [JsonProperty("iTotalRecords")]
        public int iTotalRecords { get; set; }

        [JsonProperty("iTotalDisplayRecords")]
        public int iTotalDisplayRecords { get; set; }

        [JsonProperty("message")]
        public string message { get; set; }

        [JsonProperty("sEcho")]
        public string sEcho { get; set; }
    }
}
