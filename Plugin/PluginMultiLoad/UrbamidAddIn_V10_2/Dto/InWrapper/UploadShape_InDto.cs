using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class UploadShape_InDto
    {
        public string baseUrl { get; set; }
        public string pathFile { get; set; }
        public bool overwrite { get; set; }
        public string nomeLayer { get; set; }
    }
}
