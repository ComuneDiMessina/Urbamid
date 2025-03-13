using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using UrbamidAddIn;

namespace UrbamidAddIn
{
    public class Urbamid : ESRI.ArcGIS.Desktop.AddIns.Button
    {
       
        public Urbamid()
        {
        }

        protected override void OnClick()
        {
            UrbamidAddIn.Utility util = new UrbamidAddIn.Utility();
            try
            {
                
        
            UrbamidAddIn.frmManageGeoServer form = new frmManageGeoServer();
            form.ShowDialog();
            ArcMap.Application.CurrentTool = null;
            }
            catch (Exception ex)
            {
                util.scriviLog(ex.StackTrace);
                util.message(Dto.TipoMessaggio.ERRORE, "ERRORE AVVIO PLUGIN");
            }

        }
        protected override void OnUpdate()
        {
            Enabled = ArcMap.Application != null;
        }
    }

}
