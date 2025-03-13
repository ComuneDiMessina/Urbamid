using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class UploadShape_OutDto : BaseOut_Dto
    {

        public DateTime DataCreazione { get; set; }
        public string Nome { get; set; }
        public string Tipo { get; set; }
    }
}
