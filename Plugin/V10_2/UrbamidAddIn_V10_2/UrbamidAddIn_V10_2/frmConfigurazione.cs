using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using UrbamidAddIn_V10_2.Dto;
using UrbamidAddIn_V10_2.Utility;


namespace UrbamidAddIn
{
    public partial class frmConfigurazione : Form
    {
        private String FILE_CONFIG = Path.Combine(System.Environment.GetEnvironmentVariable("USERPROFILE"), "Urbamid", "Config", "ConfigurationManager.exe.config");
        public string Password { get; set; }
        public string Username { get; set; }
        public string Geoserver { get; set; }

        public frmConfigurazione()
        {
            InitializeComponent();
        }

        private void frmConfigurazione_Load(object sender, EventArgs e)
        {

            if (!loadfileConfig())
            {
                Utility util = new Utility();
                util.message(TipoMessaggio.ERRORE, String.Format("File di configurazione non trovato! {0}", FILE_CONFIG));
            }
        }


        public bool loadfileConfig()
        {
            Utility util = new Utility();

            try
            {

                ExeConfigurationFileMap fileMap = new ExeConfigurationFileMap();
                fileMap.ExeConfigFilename = FILE_CONFIG;
                if (!File.Exists(fileMap.ExeConfigFilename))
                    return false;

                System.Configuration.Configuration config = ConfigurationManager.OpenMappedExeConfiguration(fileMap,
                    ConfigurationUserLevel.None);

                txtGeoServer.Text = config.AppSettings.Settings["geoServerUrl"].Value;
                txtUserName.Text = config.AppSettings.Settings["userName"].Value;
                txtPassword.Text = config.AppSettings.Settings["password"].Value;
                txtPasswordConfirm.Text = config.AppSettings.Settings["password"].Value;
                this.Password = txtPassword.Text;
                this.Geoserver = txtGeoServer.Text;
                this.Username = txtUserName.Text;

                return true;
            }
            catch (Exception ex)
            {
                util.scriviLog(ex.Message);
                util.message(TipoMessaggio.ERRORE, "Si è verificato un errore nella lettura delle configurazioni!");
                return false;
            }
        }

        private void btnChiudi_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnConfirm_Click(object sender, EventArgs e)
        {
            Utility util = new Utility();
            try
            {
                if (!controlliFormali())
                {
                    this.DialogResult = DialogResult.None;
                    return;
                }



                if (MessageBox.Show("ATTEZIONE, Si sta procedendo alla modifica delle configurazione di sistema. Si vuole continuare?", "Attenzione", MessageBoxButtons.YesNo) ==
                    DialogResult.Yes)
                {
                    ExeConfigurationFileMap fileMap = new ExeConfigurationFileMap();
                    fileMap.ExeConfigFilename = FILE_CONFIG;
                    System.Configuration.Configuration config = ConfigurationManager.OpenMappedExeConfiguration(fileMap, ConfigurationUserLevel.None);

                    config.AppSettings.Settings["geoServerUrl"].Value = txtGeoServer.Text.Trim();
                    config.AppSettings.Settings["userName"].Value = txtUserName.Text.Trim();
                    config.AppSettings.Settings["password"].Value = txtPassword.Text.Trim();

                    this.Password = txtPassword.Text;
                    this.Geoserver = txtGeoServer.Text;
                    this.Username = txtUserName.Text;

                    config.Save(ConfigurationSaveMode.Modified);

                    util.message(TipoMessaggio.INFO, "Operazione avvenuta con successo!");
                }
                else
                {

                    ExeConfigurationFileMap fileMap = new ExeConfigurationFileMap();
                    fileMap.ExeConfigFilename = FILE_CONFIG;
                    System.Configuration.Configuration config = ConfigurationManager.OpenMappedExeConfiguration(fileMap,
                        ConfigurationUserLevel.None);

                    txtGeoServer.Text = config.AppSettings.Settings["geoServerUrl"].Value;
                    txtUserName.Text = config.AppSettings.Settings["userName"].Value;
                    txtPassword.Text = config.AppSettings.Settings["password"].Value;
                    this.Close();

                }
            }
            catch (Exception ex)
            {

                util.scriviLog(ex.Message);
                util.message(TipoMessaggio.ERRORE, "Si è verificato un errore nel salvataggio delle configurazioni!");
            }
        }

        private bool controlliFormali()
        {
            if (String.IsNullOrEmpty(txtGeoServer.Text.Trim()))
            {
                MessageBox.Show("Attenzione! Inserire indirizzo Geoserver", "Attenzione");
                return false;
            }

            if (String.IsNullOrEmpty(txtUserName.Text.Trim()))
            {
                MessageBox.Show("Attenzione! Inserire UserName", "Attenzione");
                return false;
            }

            if (String.IsNullOrEmpty(txtPassword.Text.Trim()))
            {
                MessageBox.Show("Attenzione! Inserire Password", "Attenzione");
                return false;
            }

            if (String.IsNullOrEmpty(txtPasswordConfirm.Text.Trim()))
            {
                MessageBox.Show("Attenzione! Inserire Conferma Password", "Attenzione");
                return false;
            }

            if (txtPasswordConfirm.Text.Trim() != txtPassword.Text.Trim())
            {
                MessageBox.Show("Attenzione! Inserire Password e Conferma Password non coincidono", "Attenzione");
                return false;
            }


            return true;

        }

    }
}
