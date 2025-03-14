using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn.Dto
{

   


    public class UploadRasterDto : BaseDto
    {
        public string nome { get; set; }
        public string titolo { get; set; }

        public byte[] arrFile { get; set; }

    }
}
