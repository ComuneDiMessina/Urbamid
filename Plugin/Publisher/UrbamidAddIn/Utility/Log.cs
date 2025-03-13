using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Configuration;
using System.Windows.Forms;
using UrbamidAddIn.Dto;

namespace UrbamidAddIn
{
    public class Log
    {
        public void scriviLog(String messaggio)
        {

            try
            {
                String pathLogFiles = getPathLog();
                if (string.IsNullOrEmpty(pathLogFiles))
                {
                    MessageBox.Show("Path log file non trovato ");
                    return;
                }
                String nomeFile = "log_" + DateTime.Now.Date.ToString("yyyyMMdd") + ".log";
                String pathfile = Path.Combine(pathLogFiles, nomeFile);

                if (!Directory.Exists(pathLogFiles))
                    Directory.CreateDirectory(pathLogFiles);


                if (!File.Exists(Path.Combine(pathLogFiles, nomeFile)))
                {
                    // Create a file to write to.
                    using (StreamWriter sw = File.CreateText(Path.Combine(pathLogFiles, nomeFile)))
                    {
                        sw.WriteLine(DateTime.Now.ToString() + "-->     " + messaggio);

                    }
                }
                else
                {
                    using (StreamWriter sw = File.AppendText(Path.Combine(pathLogFiles, nomeFile)))
                    {
                        sw.WriteLine(DateTime.Now.ToString() + "-->     " + messaggio);
                    }
                }
            }
            catch (Exception ex)
            {

                MessageBox.Show(ex.Message);
            }
       }

        private string getPathLog()
        {
            Utility util = new Utility();
            try
            {

                //ExeConfigurationFileMap fileMap = new ExeConfigurationFileMap();
                //fileMap.ExeConfigFilename = Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "Urbamid", "Config", "ConfigurationManager.exe.config");
                //System.Configuration.Configuration config = ConfigurationManager.OpenMappedExeConfiguration(fileMap,
                //    ConfigurationUserLevel.None);

                //return config.AppSettings.Settings["logPath"].Value;

                return Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "Urbamid", "Log");
            }
            catch (Exception ex)
            {
                MessageBox.Show("log " + ex.Message);
                util.message(TipoMessaggio.ERRORE, "Si è verificato un errore nella lettura delle configurazioni!");
                return String.Empty;
            }
        }
    }
}
