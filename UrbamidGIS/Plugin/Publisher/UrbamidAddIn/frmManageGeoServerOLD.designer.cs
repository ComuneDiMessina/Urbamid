namespace UrbamidAddIn
{
    partial class frmManageGeoServerOLD
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmManageGeoServerOLD));
            this.menuTreeView = new System.Windows.Forms.TreeView();
            this.panel1 = new System.Windows.Forms.Panel();
            this.txtUser = new System.Windows.Forms.TextBox();
            this.txtServer = new System.Windows.Forms.TextBox();
            this.lblUser = new System.Windows.Forms.Label();
            this.lblServer = new System.Windows.Forms.Label();
            this.cmsMenu = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.tsmImporta = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmCreaAreaTematica = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmCreaTematismo = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmCreaLayerGroup = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmModifica = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmElimina = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmPubblicaStile = new System.Windows.Forms.ToolStripMenuItem();
            this.grbDettaglio = new System.Windows.Forms.GroupBox();
            this.gbCreateLayerGroup = new System.Windows.Forms.GroupBox();
            this.tlpAddLayerGroup = new System.Windows.Forms.TableLayoutPanel();
            this.txtTitleLayerGroup = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.txtNameLayerGroup = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.clbLayerDisponibili = new System.Windows.Forms.CheckedListBox();
            this.label9 = new System.Windows.Forms.Label();
            this.lbLayersSelezionati = new System.Windows.Forms.ListBox();
            this.tableLayoutBottoniCreaLayer = new System.Windows.Forms.TableLayoutPanel();
            this.btnCreaLayerGroup = new System.Windows.Forms.Button();
            this.btnCloseLayerGroup = new System.Windows.Forms.Button();
            this.tlpModifica = new System.Windows.Forms.TableLayoutPanel();
            this.tableLayoutPanel4 = new System.Windows.Forms.TableLayoutPanel();
            this.btnUndoEditLayer = new System.Windows.Forms.Button();
            this.btnConfirmEditLayer = new System.Windows.Forms.Button();
            this.dgvLayerDetail = new System.Windows.Forms.DataGridView();
            this.tableLayoutPanel3 = new System.Windows.Forms.TableLayoutPanel();
            this.btnAddProperty = new System.Windows.Forms.Button();
            this.btnDeleteProperty = new System.Windows.Forms.Button();
            this.tlpImporta = new System.Windows.Forms.TableLayoutPanel();
            this.label1 = new System.Windows.Forms.Label();
            this.lblShapeVal = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.txtNameLayer = new System.Windows.Forms.TextBox();
            this.chkDefaultView = new System.Windows.Forms.CheckBox();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.btnAnnullaImporta = new System.Windows.Forms.Button();
            this.btnImporta = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.lblNomeLayer = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.cmbSelectLayer = new System.Windows.Forms.ComboBox();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.configurazioneToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.panel1.SuspendLayout();
            this.cmsMenu.SuspendLayout();
            this.grbDettaglio.SuspendLayout();
            this.gbCreateLayerGroup.SuspendLayout();
            this.tlpAddLayerGroup.SuspendLayout();
            this.tableLayoutBottoniCreaLayer.SuspendLayout();
            this.tlpModifica.SuspendLayout();
            this.tableLayoutPanel4.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvLayerDetail)).BeginInit();
            this.tableLayoutPanel3.SuspendLayout();
            this.tlpImporta.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuTreeView
            // 
            this.menuTreeView.Dock = System.Windows.Forms.DockStyle.Fill;
            this.menuTreeView.FullRowSelect = true;
            this.menuTreeView.HideSelection = false;
            this.menuTreeView.Location = new System.Drawing.Point(3, 23);
            this.menuTreeView.Name = "menuTreeView";
            this.menuTreeView.Size = new System.Drawing.Size(304, 465);
            this.menuTreeView.TabIndex = 0;
            this.menuTreeView.NodeMouseClick += new System.Windows.Forms.TreeNodeMouseClickEventHandler(this.menuTreeView_NodeMouseClick);
            // 
            // panel1
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.panel1, 2);
            this.panel1.Controls.Add(this.txtUser);
            this.panel1.Controls.Add(this.txtServer);
            this.panel1.Controls.Add(this.lblUser);
            this.panel1.Controls.Add(this.lblServer);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(3, 494);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(615, 32);
            this.panel1.TabIndex = 1;
            // 
            // txtUser
            // 
            this.txtUser.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.txtUser.Enabled = false;
            this.txtUser.Location = new System.Drawing.Point(443, 7);
            this.txtUser.Name = "txtUser";
            this.txtUser.Size = new System.Drawing.Size(135, 20);
            this.txtUser.TabIndex = 4;
            // 
            // txtServer
            // 
            this.txtServer.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.txtServer.Enabled = false;
            this.txtServer.Location = new System.Drawing.Point(42, 7);
            this.txtServer.Name = "txtServer";
            this.txtServer.Size = new System.Drawing.Size(330, 20);
            this.txtServer.TabIndex = 3;
            // 
            // lblUser
            // 
            this.lblUser.AutoSize = true;
            this.lblUser.Location = new System.Drawing.Point(401, 8);
            this.lblUser.Name = "lblUser";
            this.lblUser.Size = new System.Drawing.Size(42, 13);
            this.lblUser.TabIndex = 2;
            this.lblUser.Text = "Utente:";
            // 
            // lblServer
            // 
            this.lblServer.AutoSize = true;
            this.lblServer.Location = new System.Drawing.Point(3, 7);
            this.lblServer.Name = "lblServer";
            this.lblServer.Size = new System.Drawing.Size(41, 13);
            this.lblServer.TabIndex = 0;
            this.lblServer.Text = "Server:";
            // 
            // cmsMenu
            // 
            this.cmsMenu.AllowDrop = true;
            this.cmsMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.tsmImporta,
            this.tsmCreaAreaTematica,
            this.tsmCreaTematismo,
            this.tsmCreaLayerGroup,
            this.tsmModifica,
            this.tsmElimina,
            this.tsmPubblicaStile});
            this.cmsMenu.LayoutStyle = System.Windows.Forms.ToolStripLayoutStyle.HorizontalStackWithOverflow;
            this.cmsMenu.Name = "cmsMenu";
            this.cmsMenu.RenderMode = System.Windows.Forms.ToolStripRenderMode.System;
            this.cmsMenu.ShowImageMargin = false;
            this.cmsMenu.ShowItemToolTips = false;
            this.cmsMenu.Size = new System.Drawing.Size(153, 158);
            // 
            // tsmImporta
            // 
            this.tsmImporta.Name = "tsmImporta";
            this.tsmImporta.Size = new System.Drawing.Size(152, 22);
            this.tsmImporta.Text = "Importa";
            this.tsmImporta.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            this.tsmImporta.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.tsmImporta.Click += new System.EventHandler(this.tsmImporta_Click);
            // 
            // tsmCreaAreaTematica
            // 
            this.tsmCreaAreaTematica.Name = "tsmCreaAreaTematica";
            this.tsmCreaAreaTematica.Size = new System.Drawing.Size(152, 22);
            this.tsmCreaAreaTematica.Text = "Crea Area Tematica";
            this.tsmCreaAreaTematica.Click += new System.EventHandler(this.tsmCreaAreaTematica_Click);
            // 
            // tsmCreaTematismo
            // 
            this.tsmCreaTematismo.Name = "tsmCreaTematismo";
            this.tsmCreaTematismo.Size = new System.Drawing.Size(152, 22);
            this.tsmCreaTematismo.Text = "Crea Tematismo";
            this.tsmCreaTematismo.Click += new System.EventHandler(this.tsmCreaTematismo_Click);
            // 
            // tsmCreaLayerGroup
            // 
            this.tsmCreaLayerGroup.Name = "tsmCreaLayerGroup";
            this.tsmCreaLayerGroup.Size = new System.Drawing.Size(152, 22);
            this.tsmCreaLayerGroup.Text = "Crea Layer Group";
            this.tsmCreaLayerGroup.Click += new System.EventHandler(this.tsmCreaLayerGroup_Click);
            // 
            // tsmModifica
            // 
            this.tsmModifica.Name = "tsmModifica";
            this.tsmModifica.Size = new System.Drawing.Size(152, 22);
            this.tsmModifica.Text = "Modifica";
            this.tsmModifica.Click += new System.EventHandler(this.tsmModifica_Click);
            // 
            // tsmElimina
            // 
            this.tsmElimina.Name = "tsmElimina";
            this.tsmElimina.Size = new System.Drawing.Size(152, 22);
            this.tsmElimina.Text = "Elimina";
            this.tsmElimina.Click += new System.EventHandler(this.tsmElimina_Click);
            // 
            // tsmPubblicaStile
            // 
            this.tsmPubblicaStile.Name = "tsmPubblicaStile";
            this.tsmPubblicaStile.Size = new System.Drawing.Size(152, 22);
            this.tsmPubblicaStile.Text = "Importa Stile";
            this.tsmPubblicaStile.Click += new System.EventHandler(this.tsmPubblicaStile_Click);
            // 
            // grbDettaglio
            // 
            this.grbDettaglio.Controls.Add(this.gbCreateLayerGroup);
            this.grbDettaglio.Controls.Add(this.tlpModifica);
            this.grbDettaglio.Controls.Add(this.tlpImporta);
            this.grbDettaglio.Dock = System.Windows.Forms.DockStyle.Fill;
            this.grbDettaglio.Location = new System.Drawing.Point(313, 23);
            this.grbDettaglio.Name = "grbDettaglio";
            this.grbDettaglio.Size = new System.Drawing.Size(305, 465);
            this.grbDettaglio.TabIndex = 2;
            this.grbDettaglio.TabStop = false;
            this.grbDettaglio.Text = "Dettaglio";
            // 
            // gbCreateLayerGroup
            // 
            this.gbCreateLayerGroup.AutoSize = true;
            this.gbCreateLayerGroup.Controls.Add(this.tlpAddLayerGroup);
            this.gbCreateLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.gbCreateLayerGroup.Location = new System.Drawing.Point(3, 16);
            this.gbCreateLayerGroup.Name = "gbCreateLayerGroup";
            this.gbCreateLayerGroup.Size = new System.Drawing.Size(299, 446);
            this.gbCreateLayerGroup.TabIndex = 9;
            this.gbCreateLayerGroup.TabStop = false;
            this.gbCreateLayerGroup.Text = "Layer Group";
            this.gbCreateLayerGroup.Visible = false;
            // 
            // tlpAddLayerGroup
            // 
            this.tlpAddLayerGroup.AutoSize = true;
            this.tlpAddLayerGroup.ColumnCount = 2;
            this.tlpAddLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tlpAddLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 70F));
            this.tlpAddLayerGroup.Controls.Add(this.txtTitleLayerGroup, 1, 1);
            this.tlpAddLayerGroup.Controls.Add(this.label7, 0, 1);
            this.tlpAddLayerGroup.Controls.Add(this.label6, 0, 0);
            this.tlpAddLayerGroup.Controls.Add(this.txtNameLayerGroup, 1, 0);
            this.tlpAddLayerGroup.Controls.Add(this.label8, 0, 2);
            this.tlpAddLayerGroup.Controls.Add(this.clbLayerDisponibili, 1, 2);
            this.tlpAddLayerGroup.Controls.Add(this.label9, 0, 3);
            this.tlpAddLayerGroup.Controls.Add(this.lbLayersSelezionati, 1, 3);
            this.tlpAddLayerGroup.Controls.Add(this.tableLayoutBottoniCreaLayer, 1, 4);
            this.tlpAddLayerGroup.Dock = System.Windows.Forms.DockStyle.Top;
            this.tlpAddLayerGroup.Location = new System.Drawing.Point(3, 16);
            this.tlpAddLayerGroup.Name = "tlpAddLayerGroup";
            this.tlpAddLayerGroup.RowCount = 5;
            this.tlpAddLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tlpAddLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tlpAddLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 176F));
            this.tlpAddLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 102F));
            this.tlpAddLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 88F));
            this.tlpAddLayerGroup.Size = new System.Drawing.Size(293, 418);
            this.tlpAddLayerGroup.TabIndex = 0;
            // 
            // txtTitleLayerGroup
            // 
            this.txtTitleLayerGroup.AcceptsReturn = true;
            this.txtTitleLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtTitleLayerGroup.Location = new System.Drawing.Point(90, 29);
            this.txtTitleLayerGroup.Name = "txtTitleLayerGroup";
            this.txtTitleLayerGroup.Size = new System.Drawing.Size(200, 20);
            this.txtTitleLayerGroup.TabIndex = 3;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label7.Location = new System.Drawing.Point(3, 26);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(81, 26);
            this.label7.TabIndex = 2;
            this.label7.Text = "Titolo:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label6.Location = new System.Drawing.Point(3, 0);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(81, 26);
            this.label6.TabIndex = 0;
            this.label6.Text = "Nome:";
            // 
            // txtNameLayerGroup
            // 
            this.txtNameLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtNameLayerGroup.Location = new System.Drawing.Point(90, 3);
            this.txtNameLayerGroup.Name = "txtNameLayerGroup";
            this.txtNameLayerGroup.Size = new System.Drawing.Size(200, 20);
            this.txtNameLayerGroup.TabIndex = 1;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label8.Location = new System.Drawing.Point(3, 52);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(81, 176);
            this.label8.TabIndex = 4;
            this.label8.Text = "Layers:";
            // 
            // clbLayerDisponibili
            // 
            this.clbLayerDisponibili.Dock = System.Windows.Forms.DockStyle.Fill;
            this.clbLayerDisponibili.FormattingEnabled = true;
            this.clbLayerDisponibili.Location = new System.Drawing.Point(90, 55);
            this.clbLayerDisponibili.Name = "clbLayerDisponibili";
            this.clbLayerDisponibili.Size = new System.Drawing.Size(200, 170);
            this.clbLayerDisponibili.TabIndex = 5;
            this.clbLayerDisponibili.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.clbLayerDisponibili_ItemCheck);
            this.clbLayerDisponibili.SelectedIndexChanged += new System.EventHandler(this.clbLayerDisponibili_SelectedIndexChanged);
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label9.Location = new System.Drawing.Point(3, 228);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(81, 102);
            this.label9.TabIndex = 6;
            this.label9.Text = "Layers Selezionati:";
            // 
            // lbLayersSelezionati
            // 
            this.lbLayersSelezionati.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lbLayersSelezionati.FormattingEnabled = true;
            this.lbLayersSelezionati.Location = new System.Drawing.Point(90, 231);
            this.lbLayersSelezionati.Name = "lbLayersSelezionati";
            this.lbLayersSelezionati.Size = new System.Drawing.Size(200, 96);
            this.lbLayersSelezionati.TabIndex = 7;
            // 
            // tableLayoutBottoniCreaLayer
            // 
            this.tableLayoutBottoniCreaLayer.AutoSize = true;
            this.tableLayoutBottoniCreaLayer.ColumnCount = 3;
            this.tableLayoutBottoniCreaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableLayoutBottoniCreaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableLayoutBottoniCreaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableLayoutBottoniCreaLayer.Controls.Add(this.btnCreaLayerGroup, 2, 0);
            this.tableLayoutBottoniCreaLayer.Controls.Add(this.btnCloseLayerGroup, 1, 0);
            this.tableLayoutBottoniCreaLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutBottoniCreaLayer.Location = new System.Drawing.Point(90, 333);
            this.tableLayoutBottoniCreaLayer.Name = "tableLayoutBottoniCreaLayer";
            this.tableLayoutBottoniCreaLayer.RowCount = 1;
            this.tableLayoutBottoniCreaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutBottoniCreaLayer.Size = new System.Drawing.Size(200, 82);
            this.tableLayoutBottoniCreaLayer.TabIndex = 8;
            // 
            // btnCreaLayerGroup
            // 
            this.btnCreaLayerGroup.Image = global::UrbamidAddIn.UrbamidResource.accept;
            this.btnCreaLayerGroup.Location = new System.Drawing.Point(123, 3);
            this.btnCreaLayerGroup.Name = "btnCreaLayerGroup";
            this.btnCreaLayerGroup.Size = new System.Drawing.Size(70, 63);
            this.btnCreaLayerGroup.TabIndex = 0;
            this.btnCreaLayerGroup.UseVisualStyleBackColor = true;
            this.btnCreaLayerGroup.Click += new System.EventHandler(this.btnCreaLayerGroup_Click);
            // 
            // btnCloseLayerGroup
            // 
            this.btnCloseLayerGroup.Image = global::UrbamidAddIn.UrbamidResource.close;
            this.btnCloseLayerGroup.Location = new System.Drawing.Point(43, 3);
            this.btnCloseLayerGroup.Name = "btnCloseLayerGroup";
            this.btnCloseLayerGroup.Size = new System.Drawing.Size(68, 63);
            this.btnCloseLayerGroup.TabIndex = 1;
            this.btnCloseLayerGroup.UseVisualStyleBackColor = true;
            this.btnCloseLayerGroup.Click += new System.EventHandler(this.btnCloseLayerGroup_Click);
            // 
            // tlpModifica
            // 
            this.tlpModifica.ColumnCount = 1;
            this.tlpModifica.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tlpModifica.Controls.Add(this.tableLayoutPanel4, 0, 2);
            this.tlpModifica.Controls.Add(this.dgvLayerDetail, 2, 0);
            this.tlpModifica.Controls.Add(this.tableLayoutPanel3, 0, 1);
            this.tlpModifica.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tlpModifica.Location = new System.Drawing.Point(3, 16);
            this.tlpModifica.Name = "tlpModifica";
            this.tlpModifica.RowCount = 3;
            this.tlpModifica.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 86.89024F));
            this.tlpModifica.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 13.10976F));
            this.tlpModifica.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 97F));
            this.tlpModifica.Size = new System.Drawing.Size(299, 446);
            this.tlpModifica.TabIndex = 8;
            this.tlpModifica.Visible = false;
            // 
            // tableLayoutPanel4
            // 
            this.tableLayoutPanel4.ColumnCount = 3;
            this.tableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel4.Controls.Add(this.btnUndoEditLayer, 1, 1);
            this.tableLayoutPanel4.Controls.Add(this.btnConfirmEditLayer, 2, 1);
            this.tableLayoutPanel4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel4.Location = new System.Drawing.Point(3, 351);
            this.tableLayoutPanel4.Name = "tableLayoutPanel4";
            this.tableLayoutPanel4.RowCount = 2;
            this.tableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 10.86957F));
            this.tableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 89.13043F));
            this.tableLayoutPanel4.Size = new System.Drawing.Size(293, 92);
            this.tableLayoutPanel4.TabIndex = 2;
            // 
            // btnUndoEditLayer
            // 
            this.btnUndoEditLayer.Image = ((System.Drawing.Image)(resources.GetObject("btnUndoEditLayer.Image")));
            this.btnUndoEditLayer.Location = new System.Drawing.Point(149, 13);
            this.btnUndoEditLayer.Name = "btnUndoEditLayer";
            this.btnUndoEditLayer.Size = new System.Drawing.Size(63, 74);
            this.btnUndoEditLayer.TabIndex = 1;
            this.btnUndoEditLayer.UseVisualStyleBackColor = true;
            this.btnUndoEditLayer.Click += new System.EventHandler(this.btnUndoEditLayer_Click);
            // 
            // btnConfirmEditLayer
            // 
            this.btnConfirmEditLayer.Image = global::UrbamidAddIn.UrbamidResource.accept;
            this.btnConfirmEditLayer.Location = new System.Drawing.Point(222, 13);
            this.btnConfirmEditLayer.Name = "btnConfirmEditLayer";
            this.btnConfirmEditLayer.Size = new System.Drawing.Size(64, 74);
            this.btnConfirmEditLayer.TabIndex = 0;
            this.btnConfirmEditLayer.UseVisualStyleBackColor = true;
            this.btnConfirmEditLayer.Click += new System.EventHandler(this.btnConfirmEditLayer_Click);
            // 
            // dgvLayerDetail
            // 
            this.dgvLayerDetail.AllowUserToAddRows = false;
            this.dgvLayerDetail.AllowUserToOrderColumns = true;
            this.dgvLayerDetail.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgvLayerDetail.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCells;
            this.dgvLayerDetail.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvLayerDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvLayerDetail.Location = new System.Drawing.Point(3, 3);
            this.dgvLayerDetail.MultiSelect = false;
            this.dgvLayerDetail.Name = "dgvLayerDetail";
            this.dgvLayerDetail.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvLayerDetail.Size = new System.Drawing.Size(293, 297);
            this.dgvLayerDetail.TabIndex = 0;
            // 
            // tableLayoutPanel3
            // 
            this.tableLayoutPanel3.ColumnCount = 3;
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 80F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 10F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 10F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel3.Controls.Add(this.btnAddProperty, 2, 0);
            this.tableLayoutPanel3.Controls.Add(this.btnDeleteProperty, 1, 0);
            this.tableLayoutPanel3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel3.Location = new System.Drawing.Point(3, 306);
            this.tableLayoutPanel3.Name = "tableLayoutPanel3";
            this.tableLayoutPanel3.RowCount = 1;
            this.tableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutPanel3.Size = new System.Drawing.Size(293, 39);
            this.tableLayoutPanel3.TabIndex = 1;
            // 
            // btnAddProperty
            // 
            this.btnAddProperty.AutoSize = true;
            this.btnAddProperty.Dock = System.Windows.Forms.DockStyle.Fill;
            this.btnAddProperty.Image = ((System.Drawing.Image)(resources.GetObject("btnAddProperty.Image")));
            this.btnAddProperty.Location = new System.Drawing.Point(266, 3);
            this.btnAddProperty.Name = "btnAddProperty";
            this.btnAddProperty.Size = new System.Drawing.Size(24, 33);
            this.btnAddProperty.TabIndex = 0;
            this.btnAddProperty.UseVisualStyleBackColor = true;
            this.btnAddProperty.Click += new System.EventHandler(this.btnAddProperty_Click);
            // 
            // btnDeleteProperty
            // 
            this.btnDeleteProperty.Dock = System.Windows.Forms.DockStyle.Fill;
            this.btnDeleteProperty.Image = global::UrbamidAddIn.UrbamidResource.delete;
            this.btnDeleteProperty.Location = new System.Drawing.Point(237, 3);
            this.btnDeleteProperty.Name = "btnDeleteProperty";
            this.btnDeleteProperty.Size = new System.Drawing.Size(23, 33);
            this.btnDeleteProperty.TabIndex = 2;
            this.btnDeleteProperty.UseVisualStyleBackColor = true;
            this.btnDeleteProperty.Click += new System.EventHandler(this.btnDeleteProperty_Click);
            // 
            // tlpImporta
            // 
            this.tlpImporta.ColumnCount = 2;
            this.tlpImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tlpImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 70F));
            this.tlpImporta.Controls.Add(this.label1, 0, 1);
            this.tlpImporta.Controls.Add(this.lblShapeVal, 1, 1);
            this.tlpImporta.Controls.Add(this.label3, 0, 4);
            this.tlpImporta.Controls.Add(this.txtNameLayer, 1, 3);
            this.tlpImporta.Controls.Add(this.chkDefaultView, 1, 4);
            this.tlpImporta.Controls.Add(this.tableLayoutPanel2, 1, 6);
            this.tlpImporta.Controls.Add(this.label2, 0, 2);
            this.tlpImporta.Controls.Add(this.label4, 0, 3);
            this.tlpImporta.Controls.Add(this.lblNomeLayer, 1, 2);
            this.tlpImporta.Controls.Add(this.label5, 0, 0);
            this.tlpImporta.Controls.Add(this.cmbSelectLayer, 1, 0);
            this.tlpImporta.Location = new System.Drawing.Point(6, 19);
            this.tlpImporta.Name = "tlpImporta";
            this.tlpImporta.RowCount = 7;
            this.tlpImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16.66667F));
            this.tlpImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16.66667F));
            this.tlpImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 12.5F));
            this.tlpImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 12.5F));
            this.tlpImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 8.333333F));
            this.tlpImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 13.59223F));
            this.tlpImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20.38835F));
            this.tlpImporta.Size = new System.Drawing.Size(283, 412);
            this.tlpImporta.TabIndex = 7;
            this.tlpImporta.Visible = false;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(3, 68);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(60, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Shape File:";
            // 
            // lblShapeVal
            // 
            this.lblShapeVal.AutoEllipsis = true;
            this.lblShapeVal.AutoSize = true;
            this.lblShapeVal.BackColor = System.Drawing.SystemColors.Control;
            this.lblShapeVal.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.lblShapeVal.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblShapeVal.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblShapeVal.Location = new System.Drawing.Point(87, 68);
            this.lblShapeVal.MinimumSize = new System.Drawing.Size(180, 0);
            this.lblShapeVal.Name = "lblShapeVal";
            this.lblShapeVal.Size = new System.Drawing.Size(193, 68);
            this.lblShapeVal.TabIndex = 1;
            this.lblShapeVal.Text = "Shape File:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(3, 238);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(70, 13);
            this.label3.TabIndex = 4;
            this.label3.Text = "Default View:";
            // 
            // txtNameLayer
            // 
            this.txtNameLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtNameLayer.Location = new System.Drawing.Point(87, 190);
            this.txtNameLayer.Name = "txtNameLayer";
            this.txtNameLayer.Size = new System.Drawing.Size(193, 20);
            this.txtNameLayer.TabIndex = 3;
            // 
            // chkDefaultView
            // 
            this.chkDefaultView.AutoSize = true;
            this.chkDefaultView.Dock = System.Windows.Forms.DockStyle.Top;
            this.chkDefaultView.Location = new System.Drawing.Point(87, 241);
            this.chkDefaultView.Name = "chkDefaultView";
            this.chkDefaultView.Size = new System.Drawing.Size(193, 14);
            this.chkDefaultView.TabIndex = 5;
            this.chkDefaultView.UseVisualStyleBackColor = true;
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.ColumnCount = 2;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.Controls.Add(this.btnAnnullaImporta, 0, 0);
            this.tableLayoutPanel2.Controls.Add(this.btnImporta, 1, 0);
            this.tableLayoutPanel2.Location = new System.Drawing.Point(87, 330);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 1;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(193, 77);
            this.tableLayoutPanel2.TabIndex = 8;
            // 
            // btnAnnullaImporta
            // 
            this.btnAnnullaImporta.Dock = System.Windows.Forms.DockStyle.Right;
            this.btnAnnullaImporta.Image = global::UrbamidAddIn.UrbamidResource.close;
            this.btnAnnullaImporta.Location = new System.Drawing.Point(29, 3);
            this.btnAnnullaImporta.Name = "btnAnnullaImporta";
            this.btnAnnullaImporta.Size = new System.Drawing.Size(64, 71);
            this.btnAnnullaImporta.TabIndex = 8;
            this.btnAnnullaImporta.UseVisualStyleBackColor = true;
            this.btnAnnullaImporta.Click += new System.EventHandler(this.btnAnnullaImporta_Click);
            // 
            // btnImporta
            // 
            this.btnImporta.BackgroundImage = global::UrbamidAddIn.UrbamidResource.accept;
            this.btnImporta.Dock = System.Windows.Forms.DockStyle.Right;
            this.btnImporta.Location = new System.Drawing.Point(126, 3);
            this.btnImporta.Name = "btnImporta";
            this.btnImporta.Size = new System.Drawing.Size(64, 71);
            this.btnImporta.TabIndex = 7;
            this.btnImporta.UseVisualStyleBackColor = true;
            this.btnImporta.Click += new System.EventHandler(this.btnImporta_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(3, 136);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(72, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Nome Shape:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(3, 187);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(64, 13);
            this.label4.TabIndex = 9;
            this.label4.Text = "Name Layer";
            // 
            // lblNomeLayer
            // 
            this.lblNomeLayer.AutoSize = true;
            this.lblNomeLayer.BackColor = System.Drawing.SystemColors.Control;
            this.lblNomeLayer.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.lblNomeLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblNomeLayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblNomeLayer.Location = new System.Drawing.Point(87, 136);
            this.lblNomeLayer.Name = "lblNomeLayer";
            this.lblNomeLayer.Size = new System.Drawing.Size(193, 51);
            this.lblNomeLayer.TabIndex = 10;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(3, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(65, 26);
            this.label5.TabIndex = 11;
            this.label5.Text = "Layer Selezionato:";
            // 
            // cmbSelectLayer
            // 
            this.cmbSelectLayer.Dock = System.Windows.Forms.DockStyle.Top;
            this.cmbSelectLayer.FormattingEnabled = true;
            this.cmbSelectLayer.Location = new System.Drawing.Point(87, 3);
            this.cmbSelectLayer.Name = "cmbSelectLayer";
            this.cmbSelectLayer.Size = new System.Drawing.Size(193, 21);
            this.cmbSelectLayer.TabIndex = 12;
            this.cmbSelectLayer.SelectedIndexChanged += new System.EventHandler(this.cmbSelectLayer_SelectedIndexChanged);
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 2;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Controls.Add(this.grbDettaglio, 1, 1);
            this.tableLayoutPanel1.Controls.Add(this.menuStrip1, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.menuTreeView, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.panel1, 0, 2);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 4;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 92.54144F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 7.458562F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(621, 550);
            this.tableLayoutPanel1.TabIndex = 3;
            // 
            // menuStrip1
            // 
            this.menuStrip1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.menuStrip1.Dock = System.Windows.Forms.DockStyle.None;
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.configurazioneToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(310, 20);
            this.menuStrip1.TabIndex = 4;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // configurazioneToolStripMenuItem
            // 
            this.configurazioneToolStripMenuItem.Name = "configurazioneToolStripMenuItem";
            this.configurazioneToolStripMenuItem.Size = new System.Drawing.Size(100, 16);
            this.configurazioneToolStripMenuItem.Text = "Configurazione";
            this.configurazioneToolStripMenuItem.Click += new System.EventHandler(this.configurazioneToolStripMenuItem_Click_1);
            // 
            // frmManageGeoServerOLD
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(621, 550);
            this.Controls.Add(this.tableLayoutPanel1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "frmManageGeoServerOLD";
            this.Text = "Gestione GeoServer";
            this.Load += new System.EventHandler(this.frmManageGeoServer_Load);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.cmsMenu.ResumeLayout(false);
            this.grbDettaglio.ResumeLayout(false);
            this.grbDettaglio.PerformLayout();
            this.gbCreateLayerGroup.ResumeLayout(false);
            this.gbCreateLayerGroup.PerformLayout();
            this.tlpAddLayerGroup.ResumeLayout(false);
            this.tlpAddLayerGroup.PerformLayout();
            this.tableLayoutBottoniCreaLayer.ResumeLayout(false);
            this.tlpModifica.ResumeLayout(false);
            this.tableLayoutPanel4.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvLayerDetail)).EndInit();
            this.tableLayoutPanel3.ResumeLayout(false);
            this.tableLayoutPanel3.PerformLayout();
            this.tlpImporta.ResumeLayout(false);
            this.tlpImporta.PerformLayout();
            this.tableLayoutPanel2.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TreeView menuTreeView;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label lblServer;
        private System.Windows.Forms.Label lblUser;
        private System.Windows.Forms.ContextMenuStrip cmsMenu;
        private System.Windows.Forms.ToolStripMenuItem tsmImporta;
        private System.Windows.Forms.GroupBox grbDettaglio;
        private System.Windows.Forms.Label lblShapeVal;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.CheckBox chkDefaultView;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtNameLayer;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.TextBox txtUser;
        private System.Windows.Forms.TextBox txtServer;
        private System.Windows.Forms.TableLayoutPanel tlpImporta;
        private System.Windows.Forms.Button btnImporta;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
        private System.Windows.Forms.Button btnAnnullaImporta;
        private System.Windows.Forms.ToolStripMenuItem tsmModifica;
        private System.Windows.Forms.TableLayoutPanel tlpModifica;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel3;
        private System.Windows.Forms.Button btnAddProperty;
        private System.Windows.Forms.Button btnDeleteProperty;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel4;
        private System.Windows.Forms.Button btnConfirmEditLayer;
        private System.Windows.Forms.Button btnUndoEditLayer;
        private System.Windows.Forms.ToolStripMenuItem tsmElimina;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label lblNomeLayer;
        private System.Windows.Forms.ToolStripMenuItem tsmPubblicaStile;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.ComboBox cmbSelectLayer;
        private System.Windows.Forms.GroupBox gbCreateLayerGroup;
        private System.Windows.Forms.TableLayoutPanel tlpAddLayerGroup;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox txtNameLayerGroup;
        private System.Windows.Forms.TextBox txtTitleLayerGroup;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.CheckedListBox clbLayerDisponibili;
        private System.Windows.Forms.ToolStripMenuItem tsmCreaLayerGroup;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.ListBox lbLayersSelezionati;
        private System.Windows.Forms.TableLayoutPanel tableLayoutBottoniCreaLayer;
        private System.Windows.Forms.Button btnCreaLayerGroup;
        private System.Windows.Forms.ToolStripMenuItem tsmCreaAreaTematica;
        private System.Windows.Forms.ToolStripMenuItem tsmCreaTematismo;
        private System.Windows.Forms.Button btnCloseLayerGroup;
        private System.Windows.Forms.DataGridView dgvLayerDetail;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem configurazioneToolStripMenuItem;
    }
}