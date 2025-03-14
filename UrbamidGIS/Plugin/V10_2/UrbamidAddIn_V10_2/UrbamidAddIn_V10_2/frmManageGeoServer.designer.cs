namespace UrbamidAddIn
{
    partial class frmManageGeoServer
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(frmManageGeoServer));
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
            this.tsmModificaGroup = new System.Windows.Forms.ToolStripMenuItem();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.configurazioneToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.tabDettaglio = new System.Windows.Forms.TabControl();
            this.tabImporta = new System.Windows.Forms.TabPage();
            this.tableImporta = new System.Windows.Forms.TableLayoutPanel();
            this.lblShapeFile = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.cmbSelectLayer = new System.Windows.Forms.ComboBox();
            this.lblPathShape = new System.Windows.Forms.Label();
            this.tableButtonImporta = new System.Windows.Forms.TableLayoutPanel();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.btnImportaShape = new System.Windows.Forms.Button();
            this.btnChiudiImporta = new System.Windows.Forms.Button();
            this.ckDefaultView = new System.Windows.Forms.CheckBox();
            this.ckShapeDB = new System.Windows.Forms.CheckBox();
            this.ckEstrazionePart = new System.Windows.Forms.CheckBox();
            this.tableCheckDefaultView = new System.Windows.Forms.TableLayoutPanel();
            this.txtNomeLayer = new System.Windows.Forms.TextBox();
            this.tabModifica = new System.Windows.Forms.TabPage();
            this.tableModificaLayer = new System.Windows.Forms.TableLayoutPanel();
            this.dgvLayerDetail = new System.Windows.Forms.DataGridView();
            this.tableCheckBoxModificaLayer = new System.Windows.Forms.TableLayoutPanel();
            this.ckEstrazionePart_Mod = new System.Windows.Forms.CheckBox();
            this.ckDefaultView_Mod = new System.Windows.Forms.CheckBox();
            this.tableButtonModificaLayer = new System.Windows.Forms.TableLayoutPanel();
            this.btnConfirmEditLayer = new System.Windows.Forms.Button();
            this.btnUndoEditLayer = new System.Windows.Forms.Button();
            this.tableEditTitoloLayer = new System.Windows.Forms.TableLayoutPanel();
            this.label10 = new System.Windows.Forms.Label();
            this.txtEditTitoloLayer = new System.Windows.Forms.TextBox();
            this.label11 = new System.Windows.Forms.Label();
            this.tabCreaGruppo = new System.Windows.Forms.TabPage();
            this.tableLayerGroup = new System.Windows.Forms.TableLayoutPanel();
            this.lbLayersSelezionati = new System.Windows.Forms.ListBox();
            this.clbLayerDisponibili = new System.Windows.Forms.CheckedListBox();
            this.txtTitleLayerGroup = new System.Windows.Forms.TextBox();
            this.txtNameLayerGroup = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.lblLayerDisponibili = new System.Windows.Forms.Label();
            this.lblLayerSelezionati = new System.Windows.Forms.Label();
            this.tableButtonLayerGroup = new System.Windows.Forms.TableLayoutPanel();
            this.btnCloseLayerGroup = new System.Windows.Forms.Button();
            this.btnCreaLayerGroup = new System.Windows.Forms.Button();
            this.label9 = new System.Windows.Forms.Label();
            this.txtFindLayers = new System.Windows.Forms.TextBox();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.panel1.SuspendLayout();
            this.cmsMenu.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            this.menuStrip1.SuspendLayout();
            this.tabDettaglio.SuspendLayout();
            this.tabImporta.SuspendLayout();
            this.tableImporta.SuspendLayout();
            this.tableButtonImporta.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            this.tabModifica.SuspendLayout();
            this.tableModificaLayer.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dgvLayerDetail)).BeginInit();
            this.tableCheckBoxModificaLayer.SuspendLayout();
            this.tableButtonModificaLayer.SuspendLayout();
            this.tableEditTitoloLayer.SuspendLayout();
            this.tabCreaGruppo.SuspendLayout();
            this.tableLayerGroup.SuspendLayout();
            this.tableButtonLayerGroup.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuTreeView
            // 
            this.menuTreeView.Dock = System.Windows.Forms.DockStyle.Fill;
            this.menuTreeView.FullRowSelect = true;
            this.menuTreeView.HideSelection = false;
            this.menuTreeView.Location = new System.Drawing.Point(3, 28);
            this.menuTreeView.Name = "menuTreeView";
            this.menuTreeView.Size = new System.Drawing.Size(304, 461);
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
            this.panel1.Location = new System.Drawing.Point(3, 495);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(615, 31);
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
            this.tsmPubblicaStile,
            this.tsmModificaGroup});
            this.cmsMenu.LayoutStyle = System.Windows.Forms.ToolStripLayoutStyle.HorizontalStackWithOverflow;
            this.cmsMenu.Name = "cmsMenu";
            this.cmsMenu.RenderMode = System.Windows.Forms.ToolStripRenderMode.System;
            this.cmsMenu.ShowImageMargin = false;
            this.cmsMenu.ShowItemToolTips = false;
            this.cmsMenu.Size = new System.Drawing.Size(152, 180);
            // 
            // tsmImporta
            // 
            this.tsmImporta.Name = "tsmImporta";
            this.tsmImporta.Size = new System.Drawing.Size(151, 22);
            this.tsmImporta.Text = "Importa";
            this.tsmImporta.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            this.tsmImporta.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.tsmImporta.Click += new System.EventHandler(this.tsmImporta_Click);
            // 
            // tsmCreaAreaTematica
            // 
            this.tsmCreaAreaTematica.Name = "tsmCreaAreaTematica";
            this.tsmCreaAreaTematica.Size = new System.Drawing.Size(151, 22);
            this.tsmCreaAreaTematica.Text = "Crea Area Tematica";
            this.tsmCreaAreaTematica.Click += new System.EventHandler(this.tsmCreaAreaTematica_Click);
            // 
            // tsmCreaTematismo
            // 
            this.tsmCreaTematismo.Name = "tsmCreaTematismo";
            this.tsmCreaTematismo.Size = new System.Drawing.Size(151, 22);
            this.tsmCreaTematismo.Text = "Crea Tematismo";
            this.tsmCreaTematismo.Click += new System.EventHandler(this.tsmCreaTematismo_Click);
            // 
            // tsmCreaLayerGroup
            // 
            this.tsmCreaLayerGroup.Name = "tsmCreaLayerGroup";
            this.tsmCreaLayerGroup.Size = new System.Drawing.Size(151, 22);
            this.tsmCreaLayerGroup.Text = "Crea Layer Group";
            this.tsmCreaLayerGroup.Click += new System.EventHandler(this.tsmCreaLayerGroup_Click);
            // 
            // tsmModifica
            // 
            this.tsmModifica.Name = "tsmModifica";
            this.tsmModifica.Size = new System.Drawing.Size(151, 22);
            this.tsmModifica.Text = "Modifica";
            this.tsmModifica.Click += new System.EventHandler(this.tsmModifica_Click);
            // 
            // tsmElimina
            // 
            this.tsmElimina.Name = "tsmElimina";
            this.tsmElimina.Size = new System.Drawing.Size(151, 22);
            this.tsmElimina.Text = "Elimina";
            this.tsmElimina.Click += new System.EventHandler(this.tsmElimina_Click);
            // 
            // tsmPubblicaStile
            // 
            this.tsmPubblicaStile.Name = "tsmPubblicaStile";
            this.tsmPubblicaStile.Size = new System.Drawing.Size(151, 22);
            this.tsmPubblicaStile.Text = "Importa Stile";
            this.tsmPubblicaStile.Click += new System.EventHandler(this.tsmPubblicaStile_Click);
            // 
            // tsmModificaGroup
            // 
            this.tsmModificaGroup.Name = "tsmModificaGroup";
            this.tsmModificaGroup.Size = new System.Drawing.Size(151, 22);
            this.tsmModificaGroup.Text = "Modifica";
            this.tsmModificaGroup.Click += new System.EventHandler(this.tsmModificaGroup_Click);
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 2;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Controls.Add(this.menuStrip1, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.menuTreeView, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.panel1, 0, 2);
            this.tableLayoutPanel1.Controls.Add(this.tabDettaglio, 1, 1);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 4;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 25F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 92.54144F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 7.458561F));
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
            this.menuStrip1.Size = new System.Drawing.Size(310, 24);
            this.menuStrip1.TabIndex = 4;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // configurazioneToolStripMenuItem
            // 
            this.configurazioneToolStripMenuItem.Name = "configurazioneToolStripMenuItem";
            this.configurazioneToolStripMenuItem.Size = new System.Drawing.Size(100, 20);
            this.configurazioneToolStripMenuItem.Text = "Configurazione";
            this.configurazioneToolStripMenuItem.Click += new System.EventHandler(this.configurazioneToolStripMenuItem_Click);
            // 
            // tabDettaglio
            // 
            this.tabDettaglio.Controls.Add(this.tabImporta);
            this.tabDettaglio.Controls.Add(this.tabModifica);
            this.tabDettaglio.Controls.Add(this.tabCreaGruppo);
            this.tabDettaglio.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabDettaglio.Location = new System.Drawing.Point(313, 28);
            this.tabDettaglio.Name = "tabDettaglio";
            this.tabDettaglio.SelectedIndex = 0;
            this.tabDettaglio.Size = new System.Drawing.Size(305, 461);
            this.tabDettaglio.TabIndex = 5;
            // 
            // tabImporta
            // 
            this.tabImporta.BackColor = System.Drawing.Color.WhiteSmoke;
            this.tabImporta.Controls.Add(this.tableImporta);
            this.tabImporta.Location = new System.Drawing.Point(4, 22);
            this.tabImporta.Name = "tabImporta";
            this.tabImporta.Padding = new System.Windows.Forms.Padding(3);
            this.tabImporta.Size = new System.Drawing.Size(297, 435);
            this.tabImporta.TabIndex = 0;
            this.tabImporta.Text = "Importa";
            // 
            // tableImporta
            // 
            this.tableImporta.ColumnCount = 2;
            this.tableImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 27.14777F));
            this.tableImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 72.85223F));
            this.tableImporta.Controls.Add(this.lblShapeFile, 1, 2);
            this.tableImporta.Controls.Add(this.label1, 0, 0);
            this.tableImporta.Controls.Add(this.label2, 0, 1);
            this.tableImporta.Controls.Add(this.label3, 0, 2);
            this.tableImporta.Controls.Add(this.label4, 0, 3);
            this.tableImporta.Controls.Add(this.cmbSelectLayer, 1, 0);
            this.tableImporta.Controls.Add(this.lblPathShape, 1, 1);
            this.tableImporta.Controls.Add(this.tableButtonImporta, 1, 4);
            this.tableImporta.Controls.Add(this.tableCheckDefaultView, 0, 4);
            this.tableImporta.Controls.Add(this.txtNomeLayer, 1, 3);
            this.tableImporta.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableImporta.Location = new System.Drawing.Point(3, 3);
            this.tableImporta.Name = "tableImporta";
            this.tableImporta.RowCount = 5;
            this.tableImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 15F));
            this.tableImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 15F));
            this.tableImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 15F));
            this.tableImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 15F));
            this.tableImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableImporta.Size = new System.Drawing.Size(291, 429);
            this.tableImporta.TabIndex = 0;
            // 
            // lblShapeFile
            // 
            this.lblShapeFile.AutoSize = true;
            this.lblShapeFile.BackColor = System.Drawing.Color.Silver;
            this.lblShapeFile.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.lblShapeFile.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblShapeFile.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblShapeFile.Location = new System.Drawing.Point(82, 128);
            this.lblShapeFile.Name = "lblShapeFile";
            this.lblShapeFile.Size = new System.Drawing.Size(206, 64);
            this.lblShapeFile.TabIndex = 6;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Dock = System.Windows.Forms.DockStyle.Left;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(3, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(66, 64);
            this.label1.TabIndex = 0;
            this.label1.Text = "Selezione Layer :";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Dock = System.Windows.Forms.DockStyle.Left;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(3, 64);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(47, 64);
            this.label2.TabIndex = 1;
            this.label2.Text = "Path Shape:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Dock = System.Windows.Forms.DockStyle.Left;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(3, 128);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(47, 64);
            this.label3.TabIndex = 2;
            this.label3.Text = "Nome Shape:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Dock = System.Windows.Forms.DockStyle.Left;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(3, 192);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(46, 64);
            this.label4.TabIndex = 3;
            this.label4.Text = "Titolo Layer :";
            // 
            // cmbSelectLayer
            // 
            this.cmbSelectLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.cmbSelectLayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cmbSelectLayer.FormattingEnabled = true;
            this.cmbSelectLayer.Location = new System.Drawing.Point(82, 3);
            this.cmbSelectLayer.Name = "cmbSelectLayer";
            this.cmbSelectLayer.Size = new System.Drawing.Size(206, 21);
            this.cmbSelectLayer.TabIndex = 4;
            this.cmbSelectLayer.SelectedIndexChanged += new System.EventHandler(this.cmbSelectLayer_SelectedIndexChanged);
            // 
            // lblPathShape
            // 
            this.lblPathShape.AutoSize = true;
            this.lblPathShape.BackColor = System.Drawing.Color.Silver;
            this.lblPathShape.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.lblPathShape.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblPathShape.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPathShape.Location = new System.Drawing.Point(82, 64);
            this.lblPathShape.Name = "lblPathShape";
            this.lblPathShape.Size = new System.Drawing.Size(206, 64);
            this.lblPathShape.TabIndex = 5;
            // 
            // tableButtonImporta
            // 
            this.tableButtonImporta.ColumnCount = 2;
            this.tableButtonImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 10F));
            this.tableButtonImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 90F));
            this.tableButtonImporta.Controls.Add(this.tableLayoutPanel2, 1, 4);
            this.tableButtonImporta.Controls.Add(this.ckDefaultView, 1, 2);
            this.tableButtonImporta.Controls.Add(this.ckShapeDB, 1, 0);
            this.tableButtonImporta.Controls.Add(this.ckEstrazionePart, 1, 1);
            this.tableButtonImporta.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableButtonImporta.Location = new System.Drawing.Point(82, 259);
            this.tableButtonImporta.Name = "tableButtonImporta";
            this.tableButtonImporta.RowCount = 5;
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 15.56886F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 14.97006F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 15.56886F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 5.389222F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 47.90419F));
            this.tableButtonImporta.Size = new System.Drawing.Size(206, 167);
            this.tableButtonImporta.TabIndex = 7;
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.tableLayoutPanel2.ColumnCount = 2;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.Controls.Add(this.btnImportaShape, 1, 0);
            this.tableLayoutPanel2.Controls.Add(this.btnChiudiImporta, 0, 0);
            this.tableLayoutPanel2.Location = new System.Drawing.Point(78, 93);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 1;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(125, 71);
            this.tableLayoutPanel2.TabIndex = 2;
            // 
            // btnImportaShape
            // 
            this.btnImportaShape.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnImportaShape.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnImportaShape.BackgroundImage")));
            this.btnImportaShape.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnImportaShape.Location = new System.Drawing.Point(65, 4);
            this.btnImportaShape.Name = "btnImportaShape";
            this.btnImportaShape.Size = new System.Drawing.Size(57, 64);
            this.btnImportaShape.TabIndex = 0;
            this.btnImportaShape.Text = "SALVA";
            this.btnImportaShape.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnImportaShape.UseVisualStyleBackColor = true;
            this.btnImportaShape.Click += new System.EventHandler(this.btnImportaShape_Click);
            // 
            // btnChiudiImporta
            // 
            this.btnChiudiImporta.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnChiudiImporta.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnChiudiImporta.BackgroundImage")));
            this.btnChiudiImporta.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnChiudiImporta.Location = new System.Drawing.Point(3, 4);
            this.btnChiudiImporta.Name = "btnChiudiImporta";
            this.btnChiudiImporta.Size = new System.Drawing.Size(56, 64);
            this.btnChiudiImporta.TabIndex = 1;
            this.btnChiudiImporta.Text = "CHIUDI";
            this.btnChiudiImporta.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnChiudiImporta.UseVisualStyleBackColor = true;
            this.btnChiudiImporta.Click += new System.EventHandler(this.btnChiudiImporta_Click);
            // 
            // ckDefaultView
            // 
            this.ckDefaultView.AutoSize = true;
            this.ckDefaultView.Location = new System.Drawing.Point(23, 54);
            this.ckDefaultView.Name = "ckDefaultView";
            this.ckDefaultView.Size = new System.Drawing.Size(143, 17);
            this.ckDefaultView.TabIndex = 0;
            this.ckDefaultView.Text = "Visualizzazione di default";
            this.ckDefaultView.UseVisualStyleBackColor = true;
            // 
            // ckShapeDB
            // 
            this.ckShapeDB.AutoSize = true;
            this.ckShapeDB.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ckShapeDB.Location = new System.Drawing.Point(23, 3);
            this.ckShapeDB.Name = "ckShapeDB";
            this.ckShapeDB.Size = new System.Drawing.Size(180, 20);
            this.ckShapeDB.TabIndex = 3;
            this.ckShapeDB.Text = "Crea Shape Database";
            this.ckShapeDB.UseVisualStyleBackColor = true;
            // 
            // ckEstrazionePart
            // 
            this.ckEstrazionePart.AutoSize = true;
            this.ckEstrazionePart.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ckEstrazionePart.Location = new System.Drawing.Point(23, 29);
            this.ckEstrazionePart.Name = "ckEstrazionePart";
            this.ckEstrazionePart.Size = new System.Drawing.Size(180, 19);
            this.ckEstrazionePart.TabIndex = 4;
            this.ckEstrazionePart.Text = "Estrazione Particellare";
            this.ckEstrazionePart.UseVisualStyleBackColor = true;
            // 
            // tableCheckDefaultView
            // 
            this.tableCheckDefaultView.ColumnCount = 1;
            this.tableCheckDefaultView.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 60F));
            this.tableCheckDefaultView.Location = new System.Drawing.Point(3, 259);
            this.tableCheckDefaultView.Name = "tableCheckDefaultView";
            this.tableCheckDefaultView.RowCount = 3;
            this.tableCheckDefaultView.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tableCheckDefaultView.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableCheckDefaultView.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tableCheckDefaultView.Size = new System.Drawing.Size(72, 167);
            this.tableCheckDefaultView.TabIndex = 8;
            // 
            // txtNomeLayer
            // 
            this.txtNomeLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtNomeLayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtNomeLayer.Location = new System.Drawing.Point(82, 195);
            this.txtNomeLayer.Name = "txtNomeLayer";
            this.txtNomeLayer.Size = new System.Drawing.Size(206, 20);
            this.txtNomeLayer.TabIndex = 9;
            // 
            // tabModifica
            // 
            this.tabModifica.BackColor = System.Drawing.Color.WhiteSmoke;
            this.tabModifica.Controls.Add(this.tableModificaLayer);
            this.tabModifica.Location = new System.Drawing.Point(4, 22);
            this.tabModifica.Name = "tabModifica";
            this.tabModifica.Padding = new System.Windows.Forms.Padding(3);
            this.tabModifica.Size = new System.Drawing.Size(297, 435);
            this.tabModifica.TabIndex = 1;
            this.tabModifica.Text = "Modifica";
            // 
            // tableModificaLayer
            // 
            this.tableModificaLayer.ColumnCount = 1;
            this.tableModificaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 291F));
            this.tableModificaLayer.Controls.Add(this.dgvLayerDetail, 0, 1);
            this.tableModificaLayer.Controls.Add(this.tableCheckBoxModificaLayer, 0, 2);
            this.tableModificaLayer.Controls.Add(this.tableButtonModificaLayer, 0, 3);
            this.tableModificaLayer.Controls.Add(this.tableEditTitoloLayer, 0, 0);
            this.tableModificaLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableModificaLayer.Location = new System.Drawing.Point(3, 3);
            this.tableModificaLayer.Name = "tableModificaLayer";
            this.tableModificaLayer.RowCount = 4;
            this.tableModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 13F));
            this.tableModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 55F));
            this.tableModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 12F));
            this.tableModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableModificaLayer.Size = new System.Drawing.Size(291, 429);
            this.tableModificaLayer.TabIndex = 0;
            // 
            // dgvLayerDetail
            // 
            this.dgvLayerDetail.AllowUserToAddRows = false;
            this.dgvLayerDetail.AllowUserToOrderColumns = true;
            this.dgvLayerDetail.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCells;
            this.dgvLayerDetail.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvLayerDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvLayerDetail.Location = new System.Drawing.Point(3, 58);
            this.dgvLayerDetail.MultiSelect = false;
            this.dgvLayerDetail.Name = "dgvLayerDetail";
            this.dgvLayerDetail.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvLayerDetail.Size = new System.Drawing.Size(285, 229);
            this.dgvLayerDetail.TabIndex = 1;
            // 
            // tableCheckBoxModificaLayer
            // 
            this.tableCheckBoxModificaLayer.ColumnCount = 2;
            this.tableCheckBoxModificaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 75F));
            this.tableCheckBoxModificaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableCheckBoxModificaLayer.Controls.Add(this.ckEstrazionePart_Mod, 0, 0);
            this.tableCheckBoxModificaLayer.Controls.Add(this.ckDefaultView_Mod, 0, 1);
            this.tableCheckBoxModificaLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableCheckBoxModificaLayer.Location = new System.Drawing.Point(3, 293);
            this.tableCheckBoxModificaLayer.Name = "tableCheckBoxModificaLayer";
            this.tableCheckBoxModificaLayer.RowCount = 2;
            this.tableCheckBoxModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableCheckBoxModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableCheckBoxModificaLayer.Size = new System.Drawing.Size(285, 45);
            this.tableCheckBoxModificaLayer.TabIndex = 2;
            // 
            // ckEstrazionePart_Mod
            // 
            this.ckEstrazionePart_Mod.AutoSize = true;
            this.ckEstrazionePart_Mod.Dock = System.Windows.Forms.DockStyle.Left;
            this.ckEstrazionePart_Mod.Location = new System.Drawing.Point(3, 3);
            this.ckEstrazionePart_Mod.Name = "ckEstrazionePart_Mod";
            this.ckEstrazionePart_Mod.Size = new System.Drawing.Size(130, 16);
            this.ckEstrazionePart_Mod.TabIndex = 0;
            this.ckEstrazionePart_Mod.Text = "Estrazione Particellare";
            this.ckEstrazionePart_Mod.UseVisualStyleBackColor = true;
            // 
            // ckDefaultView_Mod
            // 
            this.ckDefaultView_Mod.AutoSize = true;
            this.ckDefaultView_Mod.Dock = System.Windows.Forms.DockStyle.Left;
            this.ckDefaultView_Mod.Location = new System.Drawing.Point(3, 25);
            this.ckDefaultView_Mod.Name = "ckDefaultView_Mod";
            this.ckDefaultView_Mod.Size = new System.Drawing.Size(143, 17);
            this.ckDefaultView_Mod.TabIndex = 1;
            this.ckDefaultView_Mod.Text = "Visualizzazione di default";
            this.ckDefaultView_Mod.UseVisualStyleBackColor = true;
            // 
            // tableButtonModificaLayer
            // 
            this.tableButtonModificaLayer.ColumnCount = 3;
            this.tableButtonModificaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableButtonModificaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tableButtonModificaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tableButtonModificaLayer.Controls.Add(this.btnConfirmEditLayer, 2, 0);
            this.tableButtonModificaLayer.Controls.Add(this.btnUndoEditLayer, 1, 0);
            this.tableButtonModificaLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableButtonModificaLayer.Location = new System.Drawing.Point(3, 344);
            this.tableButtonModificaLayer.Name = "tableButtonModificaLayer";
            this.tableButtonModificaLayer.RowCount = 1;
            this.tableButtonModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 82F));
            this.tableButtonModificaLayer.Size = new System.Drawing.Size(285, 82);
            this.tableButtonModificaLayer.TabIndex = 3;
            // 
            // btnConfirmEditLayer
            // 
            this.btnConfirmEditLayer.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.btnConfirmEditLayer.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnConfirmEditLayer.BackgroundImage")));
            this.btnConfirmEditLayer.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnConfirmEditLayer.Location = new System.Drawing.Point(218, 11);
            this.btnConfirmEditLayer.Name = "btnConfirmEditLayer";
            this.btnConfirmEditLayer.Size = new System.Drawing.Size(64, 60);
            this.btnConfirmEditLayer.TabIndex = 1;
            this.btnConfirmEditLayer.Text = "SALVA";
            this.btnConfirmEditLayer.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnConfirmEditLayer.UseVisualStyleBackColor = true;
            this.btnConfirmEditLayer.Click += new System.EventHandler(this.btnConfirmEditLayer_Click);
            // 
            // btnUndoEditLayer
            // 
            this.btnUndoEditLayer.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.btnUndoEditLayer.AutoEllipsis = true;
            this.btnUndoEditLayer.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnUndoEditLayer.BackgroundImage")));
            this.btnUndoEditLayer.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnUndoEditLayer.Location = new System.Drawing.Point(132, 11);
            this.btnUndoEditLayer.Name = "btnUndoEditLayer";
            this.btnUndoEditLayer.Size = new System.Drawing.Size(64, 60);
            this.btnUndoEditLayer.TabIndex = 2;
            this.btnUndoEditLayer.Text = "CHIUDI";
            this.btnUndoEditLayer.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnUndoEditLayer.UseVisualStyleBackColor = true;
            this.btnUndoEditLayer.Click += new System.EventHandler(this.btnUndoEditLayer_Click);
            // 
            // tableEditTitoloLayer
            // 
            this.tableEditTitoloLayer.ColumnCount = 2;
            this.tableEditTitoloLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 29.12281F));
            this.tableEditTitoloLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 70.87719F));
            this.tableEditTitoloLayer.Controls.Add(this.label10, 0, 0);
            this.tableEditTitoloLayer.Controls.Add(this.txtEditTitoloLayer, 1, 0);
            this.tableEditTitoloLayer.Controls.Add(this.label11, 0, 1);
            this.tableEditTitoloLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableEditTitoloLayer.Location = new System.Drawing.Point(3, 3);
            this.tableEditTitoloLayer.Name = "tableEditTitoloLayer";
            this.tableEditTitoloLayer.RowCount = 2;
            this.tableEditTitoloLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableEditTitoloLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableEditTitoloLayer.Size = new System.Drawing.Size(285, 49);
            this.tableEditTitoloLayer.TabIndex = 4;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label10.Location = new System.Drawing.Point(3, 0);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(77, 24);
            this.label10.TabIndex = 1;
            this.label10.Text = "Titolo:";
            // 
            // txtEditTitoloLayer
            // 
            this.txtEditTitoloLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtEditTitoloLayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtEditTitoloLayer.Location = new System.Drawing.Point(86, 3);
            this.txtEditTitoloLayer.Name = "txtEditTitoloLayer";
            this.txtEditTitoloLayer.Size = new System.Drawing.Size(196, 20);
            this.txtEditTitoloLayer.TabIndex = 10;
            // 
            // label11
            // 
            this.label11.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label11.AutoSize = true;
            this.label11.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label11.Location = new System.Drawing.Point(3, 36);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(55, 13);
            this.label11.TabIndex = 2;
            this.label11.Text = "Attributi:";
            // 
            // tabCreaGruppo
            // 
            this.tabCreaGruppo.BackColor = System.Drawing.Color.WhiteSmoke;
            this.tabCreaGruppo.Controls.Add(this.tableLayerGroup);
            this.tabCreaGruppo.Location = new System.Drawing.Point(4, 22);
            this.tabCreaGruppo.Name = "tabCreaGruppo";
            this.tabCreaGruppo.Size = new System.Drawing.Size(297, 435);
            this.tabCreaGruppo.TabIndex = 2;
            this.tabCreaGruppo.Text = "Layer Group";
            // 
            // tableLayerGroup
            // 
            this.tableLayerGroup.ColumnCount = 2;
            this.tableLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 60F));
            this.tableLayerGroup.Controls.Add(this.lbLayersSelezionati, 1, 4);
            this.tableLayerGroup.Controls.Add(this.clbLayerDisponibili, 1, 3);
            this.tableLayerGroup.Controls.Add(this.txtTitleLayerGroup, 1, 1);
            this.tableLayerGroup.Controls.Add(this.txtNameLayerGroup, 1, 0);
            this.tableLayerGroup.Controls.Add(this.label5, 0, 0);
            this.tableLayerGroup.Controls.Add(this.label6, 0, 1);
            this.tableLayerGroup.Controls.Add(this.lblLayerDisponibili, 0, 3);
            this.tableLayerGroup.Controls.Add(this.lblLayerSelezionati, 0, 4);
            this.tableLayerGroup.Controls.Add(this.tableButtonLayerGroup, 1, 5);
            this.tableLayerGroup.Controls.Add(this.label9, 0, 2);
            this.tableLayerGroup.Controls.Add(this.txtFindLayers, 1, 2);
            this.tableLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayerGroup.Location = new System.Drawing.Point(0, 0);
            this.tableLayerGroup.Name = "tableLayerGroup";
            this.tableLayerGroup.RowCount = 6;
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 8F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 8F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 8F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 35F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16F));
            this.tableLayerGroup.Size = new System.Drawing.Size(297, 435);
            this.tableLayerGroup.TabIndex = 0;
            // 
            // lbLayersSelezionati
            // 
            this.lbLayersSelezionati.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lbLayersSelezionati.FormattingEnabled = true;
            this.lbLayersSelezionati.Location = new System.Drawing.Point(121, 257);
            this.lbLayersSelezionati.Name = "lbLayersSelezionati";
            this.lbLayersSelezionati.Size = new System.Drawing.Size(173, 102);
            this.lbLayersSelezionati.TabIndex = 9;
            this.lbLayersSelezionati.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.lbLayersSelezionati_MouseDoubleClick);
            // 
            // clbLayerDisponibili
            // 
            this.clbLayerDisponibili.CheckOnClick = true;
            this.clbLayerDisponibili.Dock = System.Windows.Forms.DockStyle.Fill;
            this.clbLayerDisponibili.Location = new System.Drawing.Point(121, 105);
            this.clbLayerDisponibili.Name = "clbLayerDisponibili";
            this.clbLayerDisponibili.Size = new System.Drawing.Size(173, 146);
            this.clbLayerDisponibili.TabIndex = 8;
            this.clbLayerDisponibili.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.clbLayerDisponibili_ItemCheck);
            this.clbLayerDisponibili.BindingContextChanged += new System.EventHandler(this.clbLayerDisponibili_BindingContextChanged);
            // 
            // txtTitleLayerGroup
            // 
            this.txtTitleLayerGroup.AcceptsReturn = true;
            this.txtTitleLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtTitleLayerGroup.Location = new System.Drawing.Point(121, 37);
            this.txtTitleLayerGroup.Name = "txtTitleLayerGroup";
            this.txtTitleLayerGroup.Size = new System.Drawing.Size(173, 20);
            this.txtTitleLayerGroup.TabIndex = 7;
            // 
            // txtNameLayerGroup
            // 
            this.txtNameLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtNameLayerGroup.Location = new System.Drawing.Point(121, 3);
            this.txtNameLayerGroup.Name = "txtNameLayerGroup";
            this.txtNameLayerGroup.Size = new System.Drawing.Size(173, 20);
            this.txtNameLayerGroup.TabIndex = 6;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Dock = System.Windows.Forms.DockStyle.Right;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(27, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(88, 34);
            this.label5.TabIndex = 1;
            this.label5.Text = "Nome Gruppo:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Dock = System.Windows.Forms.DockStyle.Right;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(23, 34);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(92, 34);
            this.label6.TabIndex = 2;
            this.label6.Text = "Titolo Gruppo :";
            // 
            // lblLayerDisponibili
            // 
            this.lblLayerDisponibili.AutoSize = true;
            this.lblLayerDisponibili.Dock = System.Windows.Forms.DockStyle.Right;
            this.lblLayerDisponibili.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLayerDisponibili.Location = new System.Drawing.Point(13, 102);
            this.lblLayerDisponibili.Name = "lblLayerDisponibili";
            this.lblLayerDisponibili.Size = new System.Drawing.Size(102, 152);
            this.lblLayerDisponibili.TabIndex = 3;
            this.lblLayerDisponibili.Text = "Layer disponibili:";
            // 
            // lblLayerSelezionati
            // 
            this.lblLayerSelezionati.AutoSize = true;
            this.lblLayerSelezionati.Dock = System.Windows.Forms.DockStyle.Right;
            this.lblLayerSelezionati.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLayerSelezionati.Location = new System.Drawing.Point(9, 254);
            this.lblLayerSelezionati.Name = "lblLayerSelezionati";
            this.lblLayerSelezionati.Size = new System.Drawing.Size(106, 108);
            this.lblLayerSelezionati.TabIndex = 4;
            this.lblLayerSelezionati.Text = "Layer selezionati:";
            // 
            // tableButtonLayerGroup
            // 
            this.tableButtonLayerGroup.ColumnCount = 3;
            this.tableButtonLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableButtonLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableButtonLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableButtonLayerGroup.Controls.Add(this.btnCloseLayerGroup, 1, 0);
            this.tableButtonLayerGroup.Controls.Add(this.btnCreaLayerGroup, 2, 0);
            this.tableButtonLayerGroup.Location = new System.Drawing.Point(121, 365);
            this.tableButtonLayerGroup.Name = "tableButtonLayerGroup";
            this.tableButtonLayerGroup.RowCount = 1;
            this.tableButtonLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableButtonLayerGroup.Size = new System.Drawing.Size(173, 66);
            this.tableButtonLayerGroup.TabIndex = 5;
            // 
            // btnCloseLayerGroup
            // 
            this.btnCloseLayerGroup.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnCloseLayerGroup.Dock = System.Windows.Forms.DockStyle.Right;
            this.btnCloseLayerGroup.Image = ((System.Drawing.Image)(resources.GetObject("btnCloseLayerGroup.Image")));
            this.btnCloseLayerGroup.Location = new System.Drawing.Point(37, 3);
            this.btnCloseLayerGroup.Name = "btnCloseLayerGroup";
            this.btnCloseLayerGroup.Size = new System.Drawing.Size(63, 60);
            this.btnCloseLayerGroup.TabIndex = 2;
            this.btnCloseLayerGroup.Text = "CHIUDI";
            this.btnCloseLayerGroup.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnCloseLayerGroup.UseVisualStyleBackColor = true;
            this.btnCloseLayerGroup.Click += new System.EventHandler(this.btnCloseLayerGroup_Click);
            // 
            // btnCreaLayerGroup
            // 
            this.btnCreaLayerGroup.Dock = System.Windows.Forms.DockStyle.Right;
            this.btnCreaLayerGroup.Image = ((System.Drawing.Image)(resources.GetObject("btnCreaLayerGroup.Image")));
            this.btnCreaLayerGroup.Location = new System.Drawing.Point(106, 3);
            this.btnCreaLayerGroup.Name = "btnCreaLayerGroup";
            this.btnCreaLayerGroup.Size = new System.Drawing.Size(64, 60);
            this.btnCreaLayerGroup.TabIndex = 3;
            this.btnCreaLayerGroup.Text = "SALVA";
            this.btnCreaLayerGroup.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnCreaLayerGroup.UseVisualStyleBackColor = true;
            this.btnCreaLayerGroup.Click += new System.EventHandler(this.btnCreaLayerGroup_Click);
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Dock = System.Windows.Forms.DockStyle.Right;
            this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label9.Location = new System.Drawing.Point(15, 68);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(100, 34);
            this.label9.TabIndex = 10;
            this.label9.Text = "Ricerca Layers :";
            // 
            // txtFindLayers
            // 
            this.txtFindLayers.AcceptsReturn = true;
            this.txtFindLayers.Location = new System.Drawing.Point(121, 71);
            this.txtFindLayers.Name = "txtFindLayers";
            this.txtFindLayers.Size = new System.Drawing.Size(173, 20);
            this.txtFindLayers.TabIndex = 11;
            this.txtFindLayers.TextChanged += new System.EventHandler(this.txtFindLayers_TextChanged);
            // 
            // toolTip
            // 
            this.toolTip.IsBalloon = true;
            // 
            // frmManageGeoServer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(621, 550);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "frmManageGeoServer";
            this.Text = "Gestione GeoServer";
            this.Load += new System.EventHandler(this.frmManageGeoServer_Load);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.cmsMenu.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.tabDettaglio.ResumeLayout(false);
            this.tabImporta.ResumeLayout(false);
            this.tableImporta.ResumeLayout(false);
            this.tableImporta.PerformLayout();
            this.tableButtonImporta.ResumeLayout(false);
            this.tableButtonImporta.PerformLayout();
            this.tableLayoutPanel2.ResumeLayout(false);
            this.tabModifica.ResumeLayout(false);
            this.tableModificaLayer.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dgvLayerDetail)).EndInit();
            this.tableCheckBoxModificaLayer.ResumeLayout(false);
            this.tableCheckBoxModificaLayer.PerformLayout();
            this.tableButtonModificaLayer.ResumeLayout(false);
            this.tableEditTitoloLayer.ResumeLayout(false);
            this.tableEditTitoloLayer.PerformLayout();
            this.tabCreaGruppo.ResumeLayout(false);
            this.tableLayerGroup.ResumeLayout(false);
            this.tableLayerGroup.PerformLayout();
            this.tableButtonLayerGroup.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TreeView menuTreeView;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label lblServer;
        private System.Windows.Forms.Label lblUser;
        private System.Windows.Forms.ContextMenuStrip cmsMenu;
        private System.Windows.Forms.ToolStripMenuItem tsmImporta;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.TextBox txtUser;
        private System.Windows.Forms.TextBox txtServer;
        private System.Windows.Forms.ToolStripMenuItem tsmModifica;
        private System.Windows.Forms.ToolStripMenuItem tsmElimina;
        private System.Windows.Forms.ToolStripMenuItem tsmPubblicaStile;
        private System.Windows.Forms.ToolStripMenuItem tsmCreaLayerGroup;
        private System.Windows.Forms.ToolStripMenuItem tsmCreaAreaTematica;
        private System.Windows.Forms.ToolStripMenuItem tsmCreaTematismo;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem configurazioneToolStripMenuItem;
        private System.Windows.Forms.TabControl tabDettaglio;
        private System.Windows.Forms.TabPage tabImporta;
        private System.Windows.Forms.TabPage tabModifica;
        private System.Windows.Forms.TableLayoutPanel tableImporta;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ComboBox cmbSelectLayer;
        private System.Windows.Forms.Label lblShapeFile;
        private System.Windows.Forms.Label lblPathShape;
        private System.Windows.Forms.TableLayoutPanel tableButtonImporta;
        private System.Windows.Forms.Button btnChiudiImporta;
        private System.Windows.Forms.TableLayoutPanel tableCheckDefaultView;
        private System.Windows.Forms.CheckBox ckDefaultView;
        private System.Windows.Forms.TextBox txtNomeLayer;
        private System.Windows.Forms.TableLayoutPanel tableModificaLayer;
        private System.Windows.Forms.DataGridView dgvLayerDetail;
        private System.Windows.Forms.TabPage tabCreaGruppo;
        private System.Windows.Forms.TableLayoutPanel tableLayerGroup;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label lblLayerDisponibili;
        private System.Windows.Forms.Label lblLayerSelezionati;
        private System.Windows.Forms.TableLayoutPanel tableButtonLayerGroup;
        private System.Windows.Forms.Button btnCloseLayerGroup;
        private System.Windows.Forms.Button btnCreaLayerGroup;
        private System.Windows.Forms.TextBox txtNameLayerGroup;
        private System.Windows.Forms.TextBox txtTitleLayerGroup;
        private System.Windows.Forms.CheckedListBox clbLayerDisponibili;
        private System.Windows.Forms.ListBox lbLayersSelezionati;
        private System.Windows.Forms.ToolStripMenuItem tsmModificaGroup;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox txtFindLayers;
        private System.Windows.Forms.Button btnImportaShape;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
        private System.Windows.Forms.TableLayoutPanel tableEditTitoloLayer;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.TextBox txtEditTitoloLayer;
        private System.Windows.Forms.CheckBox ckShapeDB;
        private System.Windows.Forms.CheckBox ckEstrazionePart;
        private System.Windows.Forms.TableLayoutPanel tableCheckBoxModificaLayer;
        private System.Windows.Forms.CheckBox ckEstrazionePart_Mod;
        private System.Windows.Forms.CheckBox ckDefaultView_Mod;
        private System.Windows.Forms.TableLayoutPanel tableButtonModificaLayer;
        private System.Windows.Forms.Button btnConfirmEditLayer;
        private System.Windows.Forms.Button btnUndoEditLayer;
    }
}