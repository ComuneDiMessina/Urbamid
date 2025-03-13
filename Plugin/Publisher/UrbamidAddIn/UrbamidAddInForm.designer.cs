namespace UrbamidAddIn
{
    partial class UrbamidAddInForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.groupBoxPublishSLD = new System.Windows.Forms.GroupBox();
            this.labelGSdatastores = new System.Windows.Forms.Label();
            this.comboBoxGSdatastores = new System.Windows.Forms.ComboBox();
            this.buttonExportSLD = new System.Windows.Forms.Button();
            this.labelGSurl = new System.Windows.Forms.Label();
            this.textBoxGSurl = new System.Windows.Forms.TextBox();
            this.labelGSsldName = new System.Windows.Forms.Label();
            this.textBoxGSsldName = new System.Windows.Forms.TextBox();
            this.labelGSworkspaces = new System.Windows.Forms.Label();
            this.comboBoxGSworkspaces = new System.Windows.Forms.ComboBox();
            this.buttonGSpublishSLD = new System.Windows.Forms.Button();
            this.labelGSlayers = new System.Windows.Forms.Label();
            this.comboBoxGSlayers = new System.Windows.Forms.ComboBox();
            this.buttonGSconnect = new System.Windows.Forms.Button();
            this.labelGSpassword = new System.Windows.Forms.Label();
            this.labelGSusername = new System.Windows.Forms.Label();
            this.textBoxGSpassword = new System.Windows.Forms.TextBox();
            this.textBoxGSusername = new System.Windows.Forms.TextBox();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.groupBoxPublishSLD.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // groupBoxPublishSLD
            // 
            this.groupBoxPublishSLD.Controls.Add(this.labelGSdatastores);
            this.groupBoxPublishSLD.Controls.Add(this.comboBoxGSdatastores);
            this.groupBoxPublishSLD.Controls.Add(this.buttonExportSLD);
            this.groupBoxPublishSLD.Controls.Add(this.labelGSurl);
            this.groupBoxPublishSLD.Controls.Add(this.textBoxGSurl);
            this.groupBoxPublishSLD.Controls.Add(this.labelGSsldName);
            this.groupBoxPublishSLD.Controls.Add(this.textBoxGSsldName);
            this.groupBoxPublishSLD.Controls.Add(this.labelGSworkspaces);
            this.groupBoxPublishSLD.Controls.Add(this.comboBoxGSworkspaces);
            this.groupBoxPublishSLD.Controls.Add(this.buttonGSpublishSLD);
            this.groupBoxPublishSLD.Controls.Add(this.labelGSlayers);
            this.groupBoxPublishSLD.Controls.Add(this.comboBoxGSlayers);
            this.groupBoxPublishSLD.Controls.Add(this.buttonGSconnect);
            this.groupBoxPublishSLD.Controls.Add(this.labelGSpassword);
            this.groupBoxPublishSLD.Controls.Add(this.labelGSusername);
            this.groupBoxPublishSLD.Controls.Add(this.textBoxGSpassword);
            this.groupBoxPublishSLD.Controls.Add(this.textBoxGSusername);
            this.groupBoxPublishSLD.Location = new System.Drawing.Point(10, 68);
            this.groupBoxPublishSLD.Margin = new System.Windows.Forms.Padding(2);
            this.groupBoxPublishSLD.Name = "groupBoxPublishSLD";
            this.groupBoxPublishSLD.Padding = new System.Windows.Forms.Padding(2);
            this.groupBoxPublishSLD.Size = new System.Drawing.Size(333, 272);
            this.groupBoxPublishSLD.TabIndex = 1;
            this.groupBoxPublishSLD.TabStop = false;
            this.groupBoxPublishSLD.Text = "Gestione pubblicazione dati cartografici";
            // 
            // labelGSdatastores
            // 
            this.labelGSdatastores.AutoSize = true;
            this.labelGSdatastores.Location = new System.Drawing.Point(27, 174);
            this.labelGSdatastores.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.labelGSdatastores.Name = "labelGSdatastores";
            this.labelGSdatastores.Size = new System.Drawing.Size(115, 13);
            this.labelGSdatastores.TabIndex = 18;
            this.labelGSdatastores.Text = "GeoServer Datastores:";
            this.labelGSdatastores.Visible = false;
            // 
            // comboBoxGSdatastores
            // 
            this.comboBoxGSdatastores.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxGSdatastores.FormattingEnabled = true;
            this.comboBoxGSdatastores.Location = new System.Drawing.Point(175, 171);
            this.comboBoxGSdatastores.Margin = new System.Windows.Forms.Padding(2);
            this.comboBoxGSdatastores.Name = "comboBoxGSdatastores";
            this.comboBoxGSdatastores.Size = new System.Drawing.Size(154, 21);
            this.comboBoxGSdatastores.TabIndex = 17;
            this.comboBoxGSdatastores.Visible = false;
            this.comboBoxGSdatastores.SelectedIndexChanged += new System.EventHandler(this.comboBoxGSdatastores_SelectedIndexChanged);
            // 
            // buttonExportSLD
            // 
            this.buttonExportSLD.Location = new System.Drawing.Point(175, 242);
            this.buttonExportSLD.Margin = new System.Windows.Forms.Padding(2);
            this.buttonExportSLD.Name = "buttonExportSLD";
            this.buttonExportSLD.Size = new System.Drawing.Size(71, 20);
            this.buttonExportSLD.TabIndex = 0;
            this.buttonExportSLD.Text = "Export";
            this.buttonExportSLD.UseVisualStyleBackColor = true;
            this.buttonExportSLD.Visible = false;
            this.buttonExportSLD.Click += new System.EventHandler(this.buttonExportSLD_Click);
            // 
            // labelGSurl
            // 
            this.labelGSurl.AutoSize = true;
            this.labelGSurl.Location = new System.Drawing.Point(60, 42);
            this.labelGSurl.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.labelGSurl.Name = "labelGSurl";
            this.labelGSurl.Size = new System.Drawing.Size(86, 13);
            this.labelGSurl.TabIndex = 16;
            this.labelGSurl.Text = "GeoServer URL:";
            // 
            // textBoxGSurl
            // 
            this.textBoxGSurl.Location = new System.Drawing.Point(175, 40);
            this.textBoxGSurl.Margin = new System.Windows.Forms.Padding(2);
            this.textBoxGSurl.Name = "textBoxGSurl";
            this.textBoxGSurl.Size = new System.Drawing.Size(154, 20);
            this.textBoxGSurl.TabIndex = 1;
            // 
            // labelGSsldName
            // 
            this.labelGSsldName.AutoSize = true;
            this.labelGSsldName.Location = new System.Drawing.Point(86, 222);
            this.labelGSsldName.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.labelGSsldName.Name = "labelGSsldName";
            this.labelGSsldName.Size = new System.Drawing.Size(62, 13);
            this.labelGSsldName.TabIndex = 12;
            this.labelGSsldName.Text = "SLD Name:";
            this.labelGSsldName.Visible = false;
            // 
            // textBoxGSsldName
            // 
            this.textBoxGSsldName.Location = new System.Drawing.Point(175, 219);
            this.textBoxGSsldName.Margin = new System.Windows.Forms.Padding(2);
            this.textBoxGSsldName.Name = "textBoxGSsldName";
            this.textBoxGSsldName.Size = new System.Drawing.Size(154, 20);
            this.textBoxGSsldName.TabIndex = 6;
            this.textBoxGSsldName.Visible = false;
            // 
            // labelGSworkspaces
            // 
            this.labelGSworkspaces.AutoSize = true;
            this.labelGSworkspaces.Location = new System.Drawing.Point(20, 150);
            this.labelGSworkspaces.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.labelGSworkspaces.Name = "labelGSworkspaces";
            this.labelGSworkspaces.Size = new System.Drawing.Size(124, 13);
            this.labelGSworkspaces.TabIndex = 10;
            this.labelGSworkspaces.Text = "GeoServer Workspaces:";
            this.labelGSworkspaces.Visible = false;
            // 
            // comboBoxGSworkspaces
            // 
            this.comboBoxGSworkspaces.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxGSworkspaces.FormattingEnabled = true;
            this.comboBoxGSworkspaces.Location = new System.Drawing.Point(175, 147);
            this.comboBoxGSworkspaces.Margin = new System.Windows.Forms.Padding(2);
            this.comboBoxGSworkspaces.Name = "comboBoxGSworkspaces";
            this.comboBoxGSworkspaces.Size = new System.Drawing.Size(154, 21);
            this.comboBoxGSworkspaces.TabIndex = 5;
            this.comboBoxGSworkspaces.Visible = false;
            this.comboBoxGSworkspaces.SelectedIndexChanged += new System.EventHandler(this.comboBoxGSworkspaces_SelectedIndexChanged);
            // 
            // buttonGSpublishSLD
            // 
            this.buttonGSpublishSLD.Location = new System.Drawing.Point(251, 242);
            this.buttonGSpublishSLD.Margin = new System.Windows.Forms.Padding(2);
            this.buttonGSpublishSLD.Name = "buttonGSpublishSLD";
            this.buttonGSpublishSLD.Size = new System.Drawing.Size(77, 20);
            this.buttonGSpublishSLD.TabIndex = 7;
            this.buttonGSpublishSLD.Text = "Publish";
            this.buttonGSpublishSLD.UseVisualStyleBackColor = true;
            this.buttonGSpublishSLD.Visible = false;
            this.buttonGSpublishSLD.Click += new System.EventHandler(this.buttonGSpublishSLD_Click);
            // 
            // labelGSlayers
            // 
            this.labelGSlayers.AutoSize = true;
            this.labelGSlayers.Location = new System.Drawing.Point(48, 198);
            this.labelGSlayers.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.labelGSlayers.Name = "labelGSlayers";
            this.labelGSlayers.Size = new System.Drawing.Size(95, 13);
            this.labelGSlayers.TabIndex = 6;
            this.labelGSlayers.Text = "GeoServer Layers:";
            this.labelGSlayers.Visible = false;
            // 
            // comboBoxGSlayers
            // 
            this.comboBoxGSlayers.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxGSlayers.FormattingEnabled = true;
            this.comboBoxGSlayers.Location = new System.Drawing.Point(175, 195);
            this.comboBoxGSlayers.Margin = new System.Windows.Forms.Padding(2);
            this.comboBoxGSlayers.Name = "comboBoxGSlayers";
            this.comboBoxGSlayers.Size = new System.Drawing.Size(154, 21);
            this.comboBoxGSlayers.TabIndex = 8;
            this.comboBoxGSlayers.Visible = false;
            // 
            // buttonGSconnect
            // 
            this.buttonGSconnect.Location = new System.Drawing.Point(251, 107);
            this.buttonGSconnect.Margin = new System.Windows.Forms.Padding(2);
            this.buttonGSconnect.Name = "buttonGSconnect";
            this.buttonGSconnect.Size = new System.Drawing.Size(77, 20);
            this.buttonGSconnect.TabIndex = 4;
            this.buttonGSconnect.Text = "Connect";
            this.buttonGSconnect.UseVisualStyleBackColor = true;
            this.buttonGSconnect.Click += new System.EventHandler(this.buttonGSconnect_Click);
            // 
            // labelGSpassword
            // 
            this.labelGSpassword.AutoSize = true;
            this.labelGSpassword.Location = new System.Drawing.Point(92, 87);
            this.labelGSpassword.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.labelGSpassword.Name = "labelGSpassword";
            this.labelGSpassword.Size = new System.Drawing.Size(56, 13);
            this.labelGSpassword.TabIndex = 3;
            this.labelGSpassword.Text = "Password:";
            // 
            // labelGSusername
            // 
            this.labelGSusername.AutoSize = true;
            this.labelGSusername.Location = new System.Drawing.Point(89, 65);
            this.labelGSusername.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.labelGSusername.Name = "labelGSusername";
            this.labelGSusername.Size = new System.Drawing.Size(58, 13);
            this.labelGSusername.TabIndex = 2;
            this.labelGSusername.Text = "Username:";
            // 
            // textBoxGSpassword
            // 
            this.textBoxGSpassword.Location = new System.Drawing.Point(175, 85);
            this.textBoxGSpassword.Margin = new System.Windows.Forms.Padding(2);
            this.textBoxGSpassword.Name = "textBoxGSpassword";
            this.textBoxGSpassword.Size = new System.Drawing.Size(154, 20);
            this.textBoxGSpassword.TabIndex = 3;
            this.textBoxGSpassword.UseSystemPasswordChar = true;
            // 
            // textBoxGSusername
            // 
            this.textBoxGSusername.Location = new System.Drawing.Point(175, 62);
            this.textBoxGSusername.Margin = new System.Windows.Forms.Padding(2);
            this.textBoxGSusername.Name = "textBoxGSusername";
            this.textBoxGSusername.Size = new System.Drawing.Size(154, 20);
            this.textBoxGSusername.TabIndex = 2;
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = global::UrbamidAddIn.UrbamidResource.gServerLogo;
            this.pictureBox1.Location = new System.Drawing.Point(12, 11);
            this.pictureBox1.Margin = new System.Windows.Forms.Padding(2);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(331, 53);
            this.pictureBox1.TabIndex = 2;
            this.pictureBox1.TabStop = false;
            // 
            // UrbamidAddInForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.ClientSize = new System.Drawing.Size(353, 346);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.groupBoxPublishSLD);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.IsMdiContainer = true;
            this.Margin = new System.Windows.Forms.Padding(2);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "UrbamidAddInForm";
            this.Text = "UrbamidAddIn";
            this.TopMost = true;
            this.groupBoxPublishSLD.ResumeLayout(false);
            this.groupBoxPublishSLD.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBoxPublishSLD;
        private System.Windows.Forms.Label labelGSlayers;
        private System.Windows.Forms.ComboBox comboBoxGSlayers;
        private System.Windows.Forms.Button buttonGSconnect;
        private System.Windows.Forms.Label labelGSpassword;
        private System.Windows.Forms.Label labelGSusername;
        private System.Windows.Forms.TextBox textBoxGSpassword;
        private System.Windows.Forms.TextBox textBoxGSusername;
        private System.Windows.Forms.Button buttonGSpublishSLD;
        private System.Windows.Forms.Button buttonExportSLD;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Label labelGSsldName;
        private System.Windows.Forms.TextBox textBoxGSsldName;
        private System.Windows.Forms.Label labelGSworkspaces;
        private System.Windows.Forms.ComboBox comboBoxGSworkspaces;
        private System.Windows.Forms.Label labelGSurl;
        private System.Windows.Forms.TextBox textBoxGSurl;
        private System.Windows.Forms.Label labelGSdatastores;
        private System.Windows.Forms.ComboBox comboBoxGSdatastores;
    }
}