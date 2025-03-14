using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class UploadRasterDto : BaseDto
    {
        public string nome { get; set; }
        public string titolo { get; set; }
        public string extension { get; set; }

        public byte[] arrFile { get; set; }

    }
}
