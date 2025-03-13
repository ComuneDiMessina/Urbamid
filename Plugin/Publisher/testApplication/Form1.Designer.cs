namespace UrbamidAddIn
{
    partial class Form1
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
            this.button2 = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.button5 = new System.Windows.Forms.Button();
            this.button6 = new System.Windows.Forms.Button();
            this.button7 = new System.Windows.Forms.Button();
            this.button8 = new System.Windows.Forms.Button();
            this.button9 = new System.Windows.Forms.Button();
            this.btnUploadRASTER = new System.Windows.Forms.Button();
            this.btnReadGDB = new System.Windows.Forms.Button();
            this.btnImageMosaic = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(12, 12);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 81);
            this.button2.TabIndex = 1;
            this.button2.Text = "UPLOAD SHAPE";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(103, 12);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 81);
            this.button1.TabIndex = 2;
            this.button1.Text = "EDIT SRS";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click_1);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(194, 12);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 81);
            this.button3.TabIndex = 3;
            this.button3.Text = "ZIP FILE";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // backgroundWorker1
            // 
            this.backgroundWorker1.WorkerReportsProgress = true;
            this.backgroundWorker1.WorkerSupportsCancellation = true;
            this.backgroundWorker1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.backgroundWorker1_DoWork);
            this.backgroundWorker1.ProgressChanged += new System.ComponentModel.ProgressChangedEventHandler(this.backgroundWorker1_ProgressChanged);
            this.backgroundWorker1.RunWorkerCompleted += new System.ComponentModel.RunWorkerCompletedEventHandler(this.backgroundWorker1_RunWorkerCompleted);
            // 
            // button5
            // 
            this.button5.Location = new System.Drawing.Point(103, 99);
            this.button5.Name = "button5";
            this.button5.Size = new System.Drawing.Size(75, 81);
            this.button5.TabIndex = 7;
            this.button5.Text = "ADD LAYER TO GROUP";
            this.button5.UseVisualStyleBackColor = true;
            this.button5.Click += new System.EventHandler(this.button5_Click);
            // 
            // button6
            // 
            this.button6.Location = new System.Drawing.Point(194, 99);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(75, 81);
            this.button6.TabIndex = 8;
            this.button6.Text = "CONVERT LYR TO ";
            this.button6.UseVisualStyleBackColor = true;
            this.button6.Click += new System.EventHandler(this.button6_Click);
            // 
            // button7
            // 
            this.button7.Location = new System.Drawing.Point(12, 99);
            this.button7.Name = "button7";
            this.button7.Size = new System.Drawing.Size(75, 81);
            this.button7.TabIndex = 9;
            this.button7.Text = "CREATE LAYER GROUP";
            this.button7.UseVisualStyleBackColor = true;
            this.button7.Click += new System.EventHandler(this.button7_Click);
            // 
            // button8
            // 
            this.button8.Location = new System.Drawing.Point(12, 186);
            this.button8.Name = "button8";
            this.button8.Size = new System.Drawing.Size(75, 81);
            this.button8.TabIndex = 10;
            this.button8.Text = "GET LAYER GROUP BY NAME";
            this.button8.UseVisualStyleBackColor = true;
            this.button8.Click += new System.EventHandler(this.button8_Click);
            // 
            // button9
            // 
            this.button9.Location = new System.Drawing.Point(373, 12);
            this.button9.Name = "button9";
            this.button9.Size = new System.Drawing.Size(163, 122);
            this.button9.TabIndex = 11;
            this.button9.Text = "TEST PLUGIN";
            this.button9.UseVisualStyleBackColor = true;
            this.button9.Click += new System.EventHandler(this.button9_Click);
            // 
            // btnUploadRASTER
            // 
            this.btnUploadRASTER.Location = new System.Drawing.Point(103, 186);
            this.btnUploadRASTER.Name = "btnUploadRASTER";
            this.btnUploadRASTER.Size = new System.Drawing.Size(75, 81);
            this.btnUploadRASTER.TabIndex = 12;
            this.btnUploadRASTER.Text = "UPLOAD GEO TIFF";
            this.btnUploadRASTER.UseVisualStyleBackColor = true;
            this.btnUploadRASTER.Click += new System.EventHandler(this.btnUploadRASTER_Click);
            // 
            // btnReadGDB
            // 
            this.btnReadGDB.Location = new System.Drawing.Point(194, 186);
            this.btnReadGDB.Name = "btnReadGDB";
            this.btnReadGDB.Size = new System.Drawing.Size(75, 81);
            this.btnReadGDB.TabIndex = 13;
            this.btnReadGDB.Text = "READ GDB";
            this.btnReadGDB.UseVisualStyleBackColor = true;
            this.btnReadGDB.Click += new System.EventHandler(this.btnReadGDB_Click);
            // 
            // btnImageMosaic
            // 
            this.btnImageMosaic.Location = new System.Drawing.Point(12, 284);
            this.btnImageMosaic.Name = "btnImageMosaic";
            this.btnImageMosaic.Size = new System.Drawing.Size(75, 81);
            this.btnImageMosaic.TabIndex = 14;
            this.btnImageMosaic.Text = "UPLOAD MOSAIC IMAGE";
            this.btnImageMosaic.UseVisualStyleBackColor = true;
            this.btnImageMosaic.Click += new System.EventHandler(this.btnImageMosaic_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(548, 415);
            this.Controls.Add(this.btnImageMosaic);
            this.Controls.Add(this.btnReadGDB);
            this.Controls.Add(this.btnUploadRASTER);
            this.Controls.Add(this.button9);
            this.Controls.Add(this.button8);
            this.Controls.Add(this.button7);
            this.Controls.Add(this.button6);
            this.Controls.Add(this.button5);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.button2);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button3;
        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private System.Windows.Forms.Button button5;
        private System.Windows.Forms.Button button6;
        private System.Windows.Forms.Button button7;
        private System.Windows.Forms.Button button8;
        private System.Windows.Forms.Button button9;
        private System.Windows.Forms.Button btnUploadRASTER;
        private System.Windows.Forms.Button btnReadGDB;
        private System.Windows.Forms.Button btnImageMosaic;
    }
}

