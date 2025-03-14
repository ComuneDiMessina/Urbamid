using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using UrbamidAddIn;
using UrbamidAddIn_V10_2.Utility;


namespace UrbamidAddIn_V10_2
{
    public class Urbamid : ESRI.ArcGIS.Desktop.AddIns.Button
    {
        public Urbamid()
        {
        }

        protected override void OnClick()
        {

            Utility.Utility util = new Utility.Utility();

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
