using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn.Dto
{
   public class EditLayerDto : BaseDto
    {
        public string nomeLayer { get; set; }
        public FeaturesTypeDto layerDto { get; set; }

        public string linkFeature { get; set; }
    }
}
