using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn.Dto
{

    public enum TipoMessaggio
    {
        ERRORE,
        INFO,
        WARNING

    }


    public class GeoServerDto : BaseDto
    {
        public string layerName { get; set; }
      
        public string pathFile { get; set; }

        public string storeName { get; set; }

        public string linkDettaglio { get; set; }

    }
}
