using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class GetDettaglioLayer_OutDto : BaseOut_Dto
    {
        public bool Exist { get; set; }
        public Layer  layer { get; set; }
        
    }
}
