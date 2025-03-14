using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace UrbamidAddIn_V10_2.Dto
{
    public class GetFeatureByLayer_InDto
    {
        public string baseUrl { get; set; }
        public string workspace { get; set; }
        public string featureType { get; set; }
        public string dataStore { get; set; }

    }
}
