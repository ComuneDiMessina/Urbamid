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
            this.tsmCreaAreaTematica = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmCreaTematismo = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmCreaLayerGroup = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmImporta = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmImportaWMS = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmModificaGroup = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmModifica = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmPubblicaStile = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmElimina = new System.Windows.Forms.ToolStripMenuItem();
            this.tsmDeleteStoreWMS = new System.Windows.Forms.ToolStripMenuItem();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.configurazioneToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.tabDettaglio = new System.Windows.Forms.TabControl();
            this.tabImporta = new System.Windows.Forms.TabPage();
            this.tableImporta = new System.Windows.Forms.TableLayoutPanel();
            this.label1 = new System.Windows.Forms.Label();
            this.tableButtonImporta = new System.Windows.Forms.TableLayoutPanel();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.btnImportaShape = new System.Windows.Forms.Button();
            this.btnChiudiImporta = new System.Windows.Forms.Button();
            this.ckDefaultView = new System.Windows.Forms.CheckBox();
            this.ckShapeDB = new System.Windows.Forms.CheckBox();
            this.ckEstrazionePart = new System.Windows.Forms.CheckBox();
            this.ckbSovrascrivi = new System.Windows.Forms.CheckBox();
            this.ckbImportaStili = new System.Windows.Forms.CheckBox();
            this.tableCheckDefaultView = new System.Windows.Forms.TableLayoutPanel();
            this.clbLayerDaImportare = new System.Windows.Forms.CheckedListBox();
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
            this.label2 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.txtEditTitoloLayer = new System.Windows.Forms.TextBox();
            this.txtShapeFile = new System.Windows.Forms.TextBox();
            this.tabCreaGruppo = new System.Windows.Forms.TabPage();
            this.tableLayerGroup = new System.Windows.Forms.TableLayoutPanel();
            this.clbLayersSelezionati = new System.Windows.Forms.ListBox();
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
            this.chkAbilitaRicerca = new System.Windows.Forms.CheckBox();
            this.tabImportaWMS = new System.Windows.Forms.TabPage();
            this.tableLayoutImportaWMS = new System.Windows.Forms.TableLayoutPanel();
            this.tableLayoutPanel3 = new System.Windows.Forms.TableLayoutPanel();
            this.btnSalvaWMS = new System.Windows.Forms.Button();
            this.btnChiudiWMS = new System.Windows.Forms.Button();
            this.tableLayoutImportWMSTextBox = new System.Windows.Forms.TableLayoutPanel();
            this.txtNomeWMS = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.lblNomeLayerWMS = new System.Windows.Forms.Label();
            this.txtLinkWMS = new System.Windows.Forms.TextBox();
            this.tableLayoutWMSLayers = new System.Windows.Forms.TableLayoutPanel();
            this.clbLayerWMS = new System.Windows.Forms.CheckedListBox();
            this.lblSelLayersWMS = new System.Windows.Forms.Label();
            this.tabEliminaWMS = new System.Windows.Forms.TabPage();
            this.tableLayoutEliminaWMS = new System.Windows.Forms.TableLayoutPanel();
            this.label4 = new System.Windows.Forms.Label();
            this.tableLayoutPanel4 = new System.Windows.Forms.TableLayoutPanel();
            this.btnDeleteStoreWMS = new System.Windows.Forms.Button();
            this.btnChiudiDeleteStoreWMS = new System.Windows.Forms.Button();
            this.clbStoresWMS = new System.Windows.Forms.CheckedListBox();
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
            this.tabImportaWMS.SuspendLayout();
            this.tableLayoutImportaWMS.SuspendLayout();
            this.tableLayoutPanel3.SuspendLayout();
            this.tableLayoutImportWMSTextBox.SuspendLayout();
            this.tableLayoutWMSLayers.SuspendLayout();
            this.tabEliminaWMS.SuspendLayout();
            this.tableLayoutEliminaWMS.SuspendLayout();
            this.tableLayoutPanel4.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuTreeView
            // 
            this.menuTreeView.Dock = System.Windows.Forms.DockStyle.Fill;
            this.menuTreeView.FullRowSelect = true;
            this.menuTreeView.HideSelection = false;
            this.menuTreeView.Location = new System.Drawing.Point(3, 28);
            this.menuTreeView.Name = "menuTreeView";
            this.menuTreeView.Size = new System.Drawing.Size(351, 515);
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
            this.panel1.Location = new System.Drawing.Point(3, 549);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(709, 35);
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
            this.tsmCreaAreaTematica,
            this.tsmCreaTematismo,
            this.tsmCreaLayerGroup,
            this.tsmImporta,
            this.tsmImportaWMS,
            this.tsmModificaGroup,
            this.tsmModifica,
            this.tsmPubblicaStile,
            this.tsmElimina,
            this.tsmDeleteStoreWMS});
            this.cmsMenu.LayoutStyle = System.Windows.Forms.ToolStripLayoutStyle.HorizontalStackWithOverflow;
            this.cmsMenu.Name = "cmsMenu";
            this.cmsMenu.RenderMode = System.Windows.Forms.ToolStripRenderMode.System;
            this.cmsMenu.ShowImageMargin = false;
            this.cmsMenu.ShowItemToolTips = false;
            this.cmsMenu.Size = new System.Drawing.Size(180, 224);
            // 
            // tsmCreaAreaTematica
            // 
            this.tsmCreaAreaTematica.Name = "tsmCreaAreaTematica";
            this.tsmCreaAreaTematica.Size = new System.Drawing.Size(179, 22);
            this.tsmCreaAreaTematica.Text = "Crea Area Tematica";
            this.tsmCreaAreaTematica.Click += new System.EventHandler(this.tsmCreaAreaTematica_Click);
            // 
            // tsmCreaTematismo
            // 
            this.tsmCreaTematismo.Name = "tsmCreaTematismo";
            this.tsmCreaTematismo.Size = new System.Drawing.Size(179, 22);
            this.tsmCreaTematismo.Text = "Crea Tematismo";
            this.tsmCreaTematismo.Click += new System.EventHandler(this.tsmCreaTematismo_Click);
            // 
            // tsmCreaLayerGroup
            // 
            this.tsmCreaLayerGroup.Name = "tsmCreaLayerGroup";
            this.tsmCreaLayerGroup.Size = new System.Drawing.Size(179, 22);
            this.tsmCreaLayerGroup.Text = "Crea Layer Group";
            this.tsmCreaLayerGroup.Click += new System.EventHandler(this.tsmCreaLayerGroup_Click);
            // 
            // tsmImporta
            // 
            this.tsmImporta.Name = "tsmImporta";
            this.tsmImporta.Size = new System.Drawing.Size(179, 22);
            this.tsmImporta.Text = "Importa Layer";
            this.tsmImporta.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            this.tsmImporta.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.tsmImporta.Click += new System.EventHandler(this.tsmImporta_Click);
            // 
            // tsmImportaWMS
            // 
            this.tsmImportaWMS.Name = "tsmImportaWMS";
            this.tsmImportaWMS.Size = new System.Drawing.Size(179, 22);
            this.tsmImportaWMS.Text = "Importa WMS";
            this.tsmImportaWMS.Click += new System.EventHandler(this.tsmImportaWMS_Click);
            // 
            // tsmModificaGroup
            // 
            this.tsmModificaGroup.Name = "tsmModificaGroup";
            this.tsmModificaGroup.Size = new System.Drawing.Size(179, 22);
            this.tsmModificaGroup.Text = "Modifica Gruppo";
            this.tsmModificaGroup.Click += new System.EventHandler(this.tsmModificaGroup_Click);
            // 
            // tsmModifica
            // 
            this.tsmModifica.Name = "tsmModifica";
            this.tsmModifica.Size = new System.Drawing.Size(179, 22);
            this.tsmModifica.Text = "Modifica Layer";
            this.tsmModifica.Click += new System.EventHandler(this.tsmModifica_Click);
            // 
            // tsmPubblicaStile
            // 
            this.tsmPubblicaStile.Name = "tsmPubblicaStile";
            this.tsmPubblicaStile.Size = new System.Drawing.Size(179, 22);
            this.tsmPubblicaStile.Text = "Importa Stile Selezionato";
            this.tsmPubblicaStile.Click += new System.EventHandler(this.tsmPubblicaStile_Click);
            // 
            // tsmElimina
            // 
            this.tsmElimina.Name = "tsmElimina";
            this.tsmElimina.Size = new System.Drawing.Size(179, 22);
            this.tsmElimina.Text = "Elimina Selezionato";
            this.tsmElimina.Click += new System.EventHandler(this.tsmElimina_Click);
            // 
            // tsmDeleteStoreWMS
            // 
            this.tsmDeleteStoreWMS.Name = "tsmDeleteStoreWMS";
            this.tsmDeleteStoreWMS.Size = new System.Drawing.Size(179, 22);
            this.tsmDeleteStoreWMS.Text = "Elimina Store WMS";
            this.tsmDeleteStoreWMS.Click += new System.EventHandler(this.tsmDeleteStoreWMS_Click);
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
            this.tableLayoutPanel1.Size = new System.Drawing.Size(715, 608);
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
            this.menuStrip1.Size = new System.Drawing.Size(357, 24);
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
            this.tabDettaglio.Controls.Add(this.tabImportaWMS);
            this.tabDettaglio.Controls.Add(this.tabEliminaWMS);
            this.tabDettaglio.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabDettaglio.Location = new System.Drawing.Point(360, 28);
            this.tabDettaglio.Name = "tabDettaglio";
            this.tabDettaglio.SelectedIndex = 0;
            this.tabDettaglio.Size = new System.Drawing.Size(352, 515);
            this.tabDettaglio.TabIndex = 5;
            // 
            // tabImporta
            // 
            this.tabImporta.BackColor = System.Drawing.Color.WhiteSmoke;
            this.tabImporta.Controls.Add(this.tableImporta);
            this.tabImporta.Location = new System.Drawing.Point(4, 22);
            this.tabImporta.Name = "tabImporta";
            this.tabImporta.Padding = new System.Windows.Forms.Padding(3);
            this.tabImporta.Size = new System.Drawing.Size(344, 489);
            this.tabImporta.TabIndex = 0;
            this.tabImporta.Text = "Importa";
            // 
            // tableImporta
            // 
            this.tableImporta.ColumnCount = 2;
            this.tableImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 27.14777F));
            this.tableImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 72.85223F));
            this.tableImporta.Controls.Add(this.label1, 0, 0);
            this.tableImporta.Controls.Add(this.tableButtonImporta, 1, 1);
            this.tableImporta.Controls.Add(this.tableCheckDefaultView, 0, 1);
            this.tableImporta.Controls.Add(this.clbLayerDaImportare, 1, 0);
            this.tableImporta.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableImporta.Location = new System.Drawing.Point(3, 3);
            this.tableImporta.Name = "tableImporta";
            this.tableImporta.RowCount = 2;
            this.tableImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableImporta.Size = new System.Drawing.Size(338, 483);
            this.tableImporta.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Dock = System.Windows.Forms.DockStyle.Left;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(3, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(66, 241);
            this.label1.TabIndex = 0;
            this.label1.Text = "Selezione Layer :";
            // 
            // tableButtonImporta
            // 
            this.tableButtonImporta.ColumnCount = 2;
            this.tableButtonImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 10F));
            this.tableButtonImporta.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 90F));
            this.tableButtonImporta.Controls.Add(this.tableLayoutPanel2, 1, 6);
            this.tableButtonImporta.Controls.Add(this.ckDefaultView, 1, 4);
            this.tableButtonImporta.Controls.Add(this.ckShapeDB, 1, 2);
            this.tableButtonImporta.Controls.Add(this.ckEstrazionePart, 1, 3);
            this.tableButtonImporta.Controls.Add(this.ckbSovrascrivi, 1, 0);
            this.tableButtonImporta.Controls.Add(this.ckbImportaStili, 1, 1);
            this.tableButtonImporta.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableButtonImporta.Location = new System.Drawing.Point(94, 244);
            this.tableButtonImporta.Name = "tableButtonImporta";
            this.tableButtonImporta.RowCount = 7;
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 11.34216F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 13.04348F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 11.84321F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 11.38771F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 11.84321F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 4.099575F));
            this.tableButtonImporta.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 36.44066F));
            this.tableButtonImporta.Size = new System.Drawing.Size(241, 236);
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
            this.tableLayoutPanel2.Location = new System.Drawing.Point(113, 162);
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
            this.ckDefaultView.Location = new System.Drawing.Point(27, 112);
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
            this.ckShapeDB.Location = new System.Drawing.Point(27, 59);
            this.ckShapeDB.Name = "ckShapeDB";
            this.ckShapeDB.Size = new System.Drawing.Size(211, 21);
            this.ckShapeDB.TabIndex = 3;
            this.ckShapeDB.Text = "Crea Shape Database";
            this.ckShapeDB.UseVisualStyleBackColor = true;
            // 
            // ckEstrazionePart
            // 
            this.ckEstrazionePart.AutoSize = true;
            this.ckEstrazionePart.Dock = System.Windows.Forms.DockStyle.Fill;
            this.ckEstrazionePart.Location = new System.Drawing.Point(27, 86);
            this.ckEstrazionePart.Name = "ckEstrazionePart";
            this.ckEstrazionePart.Size = new System.Drawing.Size(211, 20);
            this.ckEstrazionePart.TabIndex = 4;
            this.ckEstrazionePart.Text = "Estrazione Particellare";
            this.ckEstrazionePart.UseVisualStyleBackColor = true;
            // 
            // ckbSovrascrivi
            // 
            this.ckbSovrascrivi.AutoSize = true;
            this.ckbSovrascrivi.Location = new System.Drawing.Point(27, 3);
            this.ckbSovrascrivi.Name = "ckbSovrascrivi";
            this.ckbSovrascrivi.Size = new System.Drawing.Size(149, 17);
            this.ckbSovrascrivi.TabIndex = 5;
            this.ckbSovrascrivi.Text = "Sovrascrivi Layer Esistenti";
            this.ckbSovrascrivi.UseVisualStyleBackColor = true;
            // 
            // ckbImportaStili
            // 
            this.ckbImportaStili.AutoSize = true;
            this.ckbImportaStili.Location = new System.Drawing.Point(27, 29);
            this.ckbImportaStili.Name = "ckbImportaStili";
            this.ckbImportaStili.Size = new System.Drawing.Size(80, 17);
            this.ckbImportaStili.TabIndex = 6;
            this.ckbImportaStili.Text = "Importa Stili";
            this.ckbImportaStili.UseVisualStyleBackColor = true;
            // 
            // tableCheckDefaultView
            // 
            this.tableCheckDefaultView.ColumnCount = 1;
            this.tableCheckDefaultView.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 60F));
            this.tableCheckDefaultView.Location = new System.Drawing.Point(3, 244);
            this.tableCheckDefaultView.Name = "tableCheckDefaultView";
            this.tableCheckDefaultView.RowCount = 3;
            this.tableCheckDefaultView.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tableCheckDefaultView.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableCheckDefaultView.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tableCheckDefaultView.Size = new System.Drawing.Size(72, 167);
            this.tableCheckDefaultView.TabIndex = 8;
            // 
            // clbLayerDaImportare
            // 
            this.clbLayerDaImportare.CheckOnClick = true;
            this.clbLayerDaImportare.Dock = System.Windows.Forms.DockStyle.Fill;
            this.clbLayerDaImportare.FormattingEnabled = true;
            this.clbLayerDaImportare.Location = new System.Drawing.Point(94, 3);
            this.clbLayerDaImportare.Name = "clbLayerDaImportare";
            this.clbLayerDaImportare.Size = new System.Drawing.Size(241, 235);
            this.clbLayerDaImportare.TabIndex = 10;
            this.clbLayerDaImportare.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.clbLayerDaImportare_ItemCheck);
            // 
            // tabModifica
            // 
            this.tabModifica.BackColor = System.Drawing.Color.WhiteSmoke;
            this.tabModifica.Controls.Add(this.tableModificaLayer);
            this.tabModifica.Location = new System.Drawing.Point(4, 22);
            this.tabModifica.Name = "tabModifica";
            this.tabModifica.Padding = new System.Windows.Forms.Padding(3);
            this.tabModifica.Size = new System.Drawing.Size(344, 489);
            this.tabModifica.TabIndex = 1;
            this.tabModifica.Text = "Modifica";
            // 
            // tableModificaLayer
            // 
            this.tableModificaLayer.ColumnCount = 1;
            this.tableModificaLayer.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 338F));
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
            this.tableModificaLayer.Size = new System.Drawing.Size(338, 483);
            this.tableModificaLayer.TabIndex = 0;
            // 
            // dgvLayerDetail
            // 
            this.dgvLayerDetail.AllowUserToAddRows = false;
            this.dgvLayerDetail.AllowUserToOrderColumns = true;
            this.dgvLayerDetail.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCells;
            this.dgvLayerDetail.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvLayerDetail.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dgvLayerDetail.Location = new System.Drawing.Point(3, 65);
            this.dgvLayerDetail.MultiSelect = false;
            this.dgvLayerDetail.Name = "dgvLayerDetail";
            this.dgvLayerDetail.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvLayerDetail.Size = new System.Drawing.Size(332, 259);
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
            this.tableCheckBoxModificaLayer.Location = new System.Drawing.Point(3, 330);
            this.tableCheckBoxModificaLayer.Name = "tableCheckBoxModificaLayer";
            this.tableCheckBoxModificaLayer.RowCount = 2;
            this.tableCheckBoxModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableCheckBoxModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableCheckBoxModificaLayer.Size = new System.Drawing.Size(332, 51);
            this.tableCheckBoxModificaLayer.TabIndex = 2;
            // 
            // ckEstrazionePart_Mod
            // 
            this.ckEstrazionePart_Mod.AutoSize = true;
            this.ckEstrazionePart_Mod.Dock = System.Windows.Forms.DockStyle.Left;
            this.ckEstrazionePart_Mod.Location = new System.Drawing.Point(3, 3);
            this.ckEstrazionePart_Mod.Name = "ckEstrazionePart_Mod";
            this.ckEstrazionePart_Mod.Size = new System.Drawing.Size(130, 19);
            this.ckEstrazionePart_Mod.TabIndex = 0;
            this.ckEstrazionePart_Mod.Text = "Estrazione Particellare";
            this.ckEstrazionePart_Mod.UseVisualStyleBackColor = true;
            // 
            // ckDefaultView_Mod
            // 
            this.ckDefaultView_Mod.AutoSize = true;
            this.ckDefaultView_Mod.Dock = System.Windows.Forms.DockStyle.Left;
            this.ckDefaultView_Mod.Location = new System.Drawing.Point(3, 28);
            this.ckDefaultView_Mod.Name = "ckDefaultView_Mod";
            this.ckDefaultView_Mod.Size = new System.Drawing.Size(143, 20);
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
            this.tableButtonModificaLayer.Location = new System.Drawing.Point(3, 387);
            this.tableButtonModificaLayer.Name = "tableButtonModificaLayer";
            this.tableButtonModificaLayer.RowCount = 1;
            this.tableButtonModificaLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 93F));
            this.tableButtonModificaLayer.Size = new System.Drawing.Size(332, 93);
            this.tableButtonModificaLayer.TabIndex = 3;
            // 
            // btnConfirmEditLayer
            // 
            this.btnConfirmEditLayer.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.btnConfirmEditLayer.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnConfirmEditLayer.BackgroundImage")));
            this.btnConfirmEditLayer.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnConfirmEditLayer.Location = new System.Drawing.Point(265, 16);
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
            this.btnUndoEditLayer.Location = new System.Drawing.Point(164, 16);
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
            this.tableEditTitoloLayer.Controls.Add(this.label2, 0, 1);
            this.tableEditTitoloLayer.Controls.Add(this.label10, 0, 0);
            this.tableEditTitoloLayer.Controls.Add(this.txtEditTitoloLayer, 1, 0);
            this.tableEditTitoloLayer.Controls.Add(this.txtShapeFile, 1, 1);
            this.tableEditTitoloLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableEditTitoloLayer.Location = new System.Drawing.Point(3, 3);
            this.tableEditTitoloLayer.Name = "tableEditTitoloLayer";
            this.tableEditTitoloLayer.RowCount = 2;
            this.tableEditTitoloLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableEditTitoloLayer.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableEditTitoloLayer.Size = new System.Drawing.Size(332, 56);
            this.tableEditTitoloLayer.TabIndex = 4;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(3, 28);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(90, 28);
            this.label2.TabIndex = 11;
            this.label2.Text = "Nome:";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label10.Location = new System.Drawing.Point(3, 0);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(90, 28);
            this.label10.TabIndex = 1;
            this.label10.Text = "Titolo:";
            // 
            // txtEditTitoloLayer
            // 
            this.txtEditTitoloLayer.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtEditTitoloLayer.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtEditTitoloLayer.Location = new System.Drawing.Point(99, 3);
            this.txtEditTitoloLayer.Name = "txtEditTitoloLayer";
            this.txtEditTitoloLayer.Size = new System.Drawing.Size(230, 20);
            this.txtEditTitoloLayer.TabIndex = 10;
            // 
            // txtShapeFile
            // 
            this.txtShapeFile.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtShapeFile.Enabled = false;
            this.txtShapeFile.Location = new System.Drawing.Point(99, 31);
            this.txtShapeFile.Name = "txtShapeFile";
            this.txtShapeFile.Size = new System.Drawing.Size(230, 20);
            this.txtShapeFile.TabIndex = 12;
            // 
            // tabCreaGruppo
            // 
            this.tabCreaGruppo.BackColor = System.Drawing.Color.WhiteSmoke;
            this.tabCreaGruppo.Controls.Add(this.tableLayerGroup);
            this.tabCreaGruppo.Location = new System.Drawing.Point(4, 22);
            this.tabCreaGruppo.Name = "tabCreaGruppo";
            this.tabCreaGruppo.Size = new System.Drawing.Size(344, 489);
            this.tabCreaGruppo.TabIndex = 2;
            this.tabCreaGruppo.Text = "Layer Group";
            // 
            // tableLayerGroup
            // 
            this.tableLayerGroup.ColumnCount = 2;
            this.tableLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            this.tableLayerGroup.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 60F));
            this.tableLayerGroup.Controls.Add(this.clbLayersSelezionati, 1, 5);
            this.tableLayerGroup.Controls.Add(this.clbLayerDisponibili, 1, 4);
            this.tableLayerGroup.Controls.Add(this.txtTitleLayerGroup, 1, 1);
            this.tableLayerGroup.Controls.Add(this.txtNameLayerGroup, 1, 0);
            this.tableLayerGroup.Controls.Add(this.label5, 0, 0);
            this.tableLayerGroup.Controls.Add(this.label6, 0, 1);
            this.tableLayerGroup.Controls.Add(this.lblLayerDisponibili, 0, 4);
            this.tableLayerGroup.Controls.Add(this.lblLayerSelezionati, 0, 5);
            this.tableLayerGroup.Controls.Add(this.tableButtonLayerGroup, 1, 6);
            this.tableLayerGroup.Controls.Add(this.label9, 0, 3);
            this.tableLayerGroup.Controls.Add(this.txtFindLayers, 1, 3);
            this.tableLayerGroup.Controls.Add(this.chkAbilitaRicerca, 1, 2);
            this.tableLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayerGroup.Location = new System.Drawing.Point(0, 0);
            this.tableLayerGroup.Name = "tableLayerGroup";
            this.tableLayerGroup.RowCount = 7;
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 7.407407F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 7.407407F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 7.407407F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 7.407407F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 32.40741F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 23.14815F));
            this.tableLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 14.81481F));
            this.tableLayerGroup.Size = new System.Drawing.Size(344, 489);
            this.tableLayerGroup.TabIndex = 0;
            // 
            // clbLayersSelezionati
            // 
            this.clbLayersSelezionati.Dock = System.Windows.Forms.DockStyle.Fill;
            this.clbLayersSelezionati.FormattingEnabled = true;
            this.clbLayersSelezionati.Location = new System.Drawing.Point(140, 305);
            this.clbLayersSelezionati.Name = "clbLayersSelezionati";
            this.clbLayersSelezionati.Size = new System.Drawing.Size(201, 107);
            this.clbLayersSelezionati.TabIndex = 9;
            this.clbLayersSelezionati.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.clbLayersSelezionati_MouseDoubleClick);
            // 
            // clbLayerDisponibili
            // 
            this.clbLayerDisponibili.CheckOnClick = true;
            this.clbLayerDisponibili.Dock = System.Windows.Forms.DockStyle.Fill;
            this.clbLayerDisponibili.Location = new System.Drawing.Point(140, 147);
            this.clbLayerDisponibili.Name = "clbLayerDisponibili";
            this.clbLayerDisponibili.Size = new System.Drawing.Size(201, 152);
            this.clbLayerDisponibili.TabIndex = 8;
            this.clbLayerDisponibili.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.clbLayerDisponibili_ItemCheck);
            this.clbLayerDisponibili.BindingContextChanged += new System.EventHandler(this.clbLayerDisponibili_BindingContextChanged);
            // 
            // txtTitleLayerGroup
            // 
            this.txtTitleLayerGroup.AcceptsReturn = true;
            this.txtTitleLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtTitleLayerGroup.Location = new System.Drawing.Point(140, 39);
            this.txtTitleLayerGroup.Name = "txtTitleLayerGroup";
            this.txtTitleLayerGroup.Size = new System.Drawing.Size(201, 20);
            this.txtTitleLayerGroup.TabIndex = 7;
            // 
            // txtNameLayerGroup
            // 
            this.txtNameLayerGroup.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtNameLayerGroup.Location = new System.Drawing.Point(140, 3);
            this.txtNameLayerGroup.Name = "txtNameLayerGroup";
            this.txtNameLayerGroup.Size = new System.Drawing.Size(201, 20);
            this.txtNameLayerGroup.TabIndex = 6;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Dock = System.Windows.Forms.DockStyle.Right;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(46, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(88, 36);
            this.label5.TabIndex = 1;
            this.label5.Text = "Nome Gruppo:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Dock = System.Windows.Forms.DockStyle.Right;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(42, 36);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(92, 36);
            this.label6.TabIndex = 2;
            this.label6.Text = "Titolo Gruppo :";
            // 
            // lblLayerDisponibili
            // 
            this.lblLayerDisponibili.AutoSize = true;
            this.lblLayerDisponibili.Dock = System.Windows.Forms.DockStyle.Right;
            this.lblLayerDisponibili.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLayerDisponibili.Location = new System.Drawing.Point(32, 144);
            this.lblLayerDisponibili.Name = "lblLayerDisponibili";
            this.lblLayerDisponibili.Size = new System.Drawing.Size(102, 158);
            this.lblLayerDisponibili.TabIndex = 3;
            this.lblLayerDisponibili.Text = "Layer disponibili:";
            // 
            // lblLayerSelezionati
            // 
            this.lblLayerSelezionati.AutoSize = true;
            this.lblLayerSelezionati.Dock = System.Windows.Forms.DockStyle.Right;
            this.lblLayerSelezionati.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLayerSelezionati.Location = new System.Drawing.Point(28, 302);
            this.lblLayerSelezionati.Name = "lblLayerSelezionati";
            this.lblLayerSelezionati.Size = new System.Drawing.Size(106, 113);
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
            this.tableButtonLayerGroup.Location = new System.Drawing.Point(140, 418);
            this.tableButtonLayerGroup.Name = "tableButtonLayerGroup";
            this.tableButtonLayerGroup.RowCount = 1;
            this.tableButtonLayerGroup.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableButtonLayerGroup.Size = new System.Drawing.Size(173, 61);
            this.tableButtonLayerGroup.TabIndex = 5;
            // 
            // btnCloseLayerGroup
            // 
            this.btnCloseLayerGroup.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnCloseLayerGroup.Dock = System.Windows.Forms.DockStyle.Right;
            this.btnCloseLayerGroup.Image = ((System.Drawing.Image)(resources.GetObject("btnCloseLayerGroup.Image")));
            this.btnCloseLayerGroup.Location = new System.Drawing.Point(37, 3);
            this.btnCloseLayerGroup.Name = "btnCloseLayerGroup";
            this.btnCloseLayerGroup.Size = new System.Drawing.Size(63, 55);
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
            this.btnCreaLayerGroup.Size = new System.Drawing.Size(64, 55);
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
            this.label9.Location = new System.Drawing.Point(34, 108);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(100, 36);
            this.label9.TabIndex = 10;
            this.label9.Text = "Ricerca Layers :";
            // 
            // txtFindLayers
            // 
            this.txtFindLayers.AcceptsReturn = true;
            this.txtFindLayers.Location = new System.Drawing.Point(140, 111);
            this.txtFindLayers.Name = "txtFindLayers";
            this.txtFindLayers.Size = new System.Drawing.Size(173, 20);
            this.txtFindLayers.TabIndex = 11;
            this.txtFindLayers.TextChanged += new System.EventHandler(this.txtFindLayers_TextChanged);
            // 
            // chkAbilitaRicerca
            // 
            this.chkAbilitaRicerca.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.chkAbilitaRicerca.AutoSize = true;
            this.chkAbilitaRicerca.Location = new System.Drawing.Point(140, 75);
            this.chkAbilitaRicerca.Name = "chkAbilitaRicerca";
            this.chkAbilitaRicerca.Size = new System.Drawing.Size(201, 30);
            this.chkAbilitaRicerca.TabIndex = 12;
            this.chkAbilitaRicerca.Text = "Abilita Ricerca";
            this.chkAbilitaRicerca.UseVisualStyleBackColor = true;
            this.chkAbilitaRicerca.CheckedChanged += new System.EventHandler(this.chkAbilitaRicerca_CheckedChanged);
            // 
            // tabImportaWMS
            // 
            this.tabImportaWMS.Controls.Add(this.tableLayoutImportaWMS);
            this.tabImportaWMS.Location = new System.Drawing.Point(4, 22);
            this.tabImportaWMS.Name = "tabImportaWMS";
            this.tabImportaWMS.Size = new System.Drawing.Size(344, 489);
            this.tabImportaWMS.TabIndex = 3;
            this.tabImportaWMS.Text = "Importa WMS";
            this.tabImportaWMS.UseVisualStyleBackColor = true;
            // 
            // tableLayoutImportaWMS
            // 
            this.tableLayoutImportaWMS.BackColor = System.Drawing.Color.WhiteSmoke;
            this.tableLayoutImportaWMS.ColumnCount = 1;
            this.tableLayoutImportaWMS.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
            this.tableLayoutImportaWMS.Controls.Add(this.tableLayoutPanel3, 0, 2);
            this.tableLayoutImportaWMS.Controls.Add(this.tableLayoutImportWMSTextBox, 0, 0);
            this.tableLayoutImportaWMS.Controls.Add(this.tableLayoutWMSLayers, 0, 1);
            this.tableLayoutImportaWMS.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutImportaWMS.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutImportaWMS.Name = "tableLayoutImportaWMS";
            this.tableLayoutImportaWMS.RowCount = 3;
            this.tableLayoutImportaWMS.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tableLayoutImportaWMS.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutImportaWMS.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 20F));
            this.tableLayoutImportaWMS.Size = new System.Drawing.Size(344, 489);
            this.tableLayoutImportaWMS.TabIndex = 0;
            // 
            // tableLayoutPanel3
            // 
            this.tableLayoutPanel3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.tableLayoutPanel3.ColumnCount = 2;
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel3.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel3.Controls.Add(this.btnSalvaWMS, 1, 0);
            this.tableLayoutPanel3.Controls.Add(this.btnChiudiWMS, 0, 0);
            this.tableLayoutPanel3.Location = new System.Drawing.Point(216, 415);
            this.tableLayoutPanel3.Name = "tableLayoutPanel3";
            this.tableLayoutPanel3.RowCount = 1;
            this.tableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel3.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 71F));
            this.tableLayoutPanel3.Size = new System.Drawing.Size(125, 71);
            this.tableLayoutPanel3.TabIndex = 3;
            // 
            // btnSalvaWMS
            // 
            this.btnSalvaWMS.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnSalvaWMS.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnSalvaWMS.BackgroundImage")));
            this.btnSalvaWMS.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnSalvaWMS.Location = new System.Drawing.Point(65, 4);
            this.btnSalvaWMS.Name = "btnSalvaWMS";
            this.btnSalvaWMS.Size = new System.Drawing.Size(57, 64);
            this.btnSalvaWMS.TabIndex = 0;
            this.btnSalvaWMS.Text = "SALVA";
            this.btnSalvaWMS.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnSalvaWMS.UseVisualStyleBackColor = true;
            this.btnSalvaWMS.Click += new System.EventHandler(this.btnSalvaWMS_Click);
            // 
            // btnChiudiWMS
            // 
            this.btnChiudiWMS.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnChiudiWMS.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnChiudiWMS.BackgroundImage")));
            this.btnChiudiWMS.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnChiudiWMS.Location = new System.Drawing.Point(3, 4);
            this.btnChiudiWMS.Name = "btnChiudiWMS";
            this.btnChiudiWMS.Size = new System.Drawing.Size(56, 64);
            this.btnChiudiWMS.TabIndex = 1;
            this.btnChiudiWMS.Text = "CHIUDI";
            this.btnChiudiWMS.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnChiudiWMS.UseVisualStyleBackColor = true;
            this.btnChiudiWMS.Click += new System.EventHandler(this.btnChiudiWMS_Click);
            // 
            // tableLayoutImportWMSTextBox
            // 
            this.tableLayoutImportWMSTextBox.ColumnCount = 2;
            this.tableLayoutImportWMSTextBox.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 35.50296F));
            this.tableLayoutImportWMSTextBox.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 64.49704F));
            this.tableLayoutImportWMSTextBox.Controls.Add(this.txtNomeWMS, 1, 0);
            this.tableLayoutImportWMSTextBox.Controls.Add(this.label3, 0, 1);
            this.tableLayoutImportWMSTextBox.Controls.Add(this.lblNomeLayerWMS, 0, 0);
            this.tableLayoutImportWMSTextBox.Controls.Add(this.txtLinkWMS, 1, 1);
            this.tableLayoutImportWMSTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutImportWMSTextBox.Location = new System.Drawing.Point(3, 3);
            this.tableLayoutImportWMSTextBox.Name = "tableLayoutImportWMSTextBox";
            this.tableLayoutImportWMSTextBox.RowCount = 2;
            this.tableLayoutImportWMSTextBox.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 22.14286F));
            this.tableLayoutImportWMSTextBox.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 77.85714F));
            this.tableLayoutImportWMSTextBox.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutImportWMSTextBox.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutImportWMSTextBox.Size = new System.Drawing.Size(338, 140);
            this.tableLayoutImportWMSTextBox.TabIndex = 0;
            // 
            // txtNomeWMS
            // 
            this.txtNomeWMS.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtNomeWMS.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtNomeWMS.Location = new System.Drawing.Point(123, 3);
            this.txtNomeWMS.Name = "txtNomeWMS";
            this.txtNomeWMS.Size = new System.Drawing.Size(212, 20);
            this.txtNomeWMS.TabIndex = 11;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(3, 31);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(114, 109);
            this.label3.TabIndex = 2;
            this.label3.Text = "Link WMS:";
            // 
            // lblNomeLayerWMS
            // 
            this.lblNomeLayerWMS.AutoSize = true;
            this.lblNomeLayerWMS.Dock = System.Windows.Forms.DockStyle.Fill;
            this.lblNomeLayerWMS.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblNomeLayerWMS.Location = new System.Drawing.Point(3, 0);
            this.lblNomeLayerWMS.Name = "lblNomeLayerWMS";
            this.lblNomeLayerWMS.Size = new System.Drawing.Size(114, 31);
            this.lblNomeLayerWMS.TabIndex = 1;
            this.lblNomeLayerWMS.Text = "Nome Store WMS:";
            // 
            // txtLinkWMS
            // 
            this.txtLinkWMS.Dock = System.Windows.Forms.DockStyle.Fill;
            this.txtLinkWMS.Location = new System.Drawing.Point(123, 34);
            this.txtLinkWMS.Multiline = true;
            this.txtLinkWMS.Name = "txtLinkWMS";
            this.txtLinkWMS.Size = new System.Drawing.Size(212, 103);
            this.txtLinkWMS.TabIndex = 12;
            this.toolTip.SetToolTip(this.txtLinkWMS, "URL WMS");
            // 
            // tableLayoutWMSLayers
            // 
            this.tableLayoutWMSLayers.ColumnCount = 2;
            this.tableLayoutWMSLayers.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 36.09467F));
            this.tableLayoutWMSLayers.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 63.90533F));
            this.tableLayoutWMSLayers.Controls.Add(this.clbLayerWMS, 0, 0);
            this.tableLayoutWMSLayers.Controls.Add(this.lblSelLayersWMS, 0, 0);
            this.tableLayoutWMSLayers.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutWMSLayers.Location = new System.Drawing.Point(3, 149);
            this.tableLayoutWMSLayers.Name = "tableLayoutWMSLayers";
            this.tableLayoutWMSLayers.RowCount = 1;
            this.tableLayoutWMSLayers.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutWMSLayers.Size = new System.Drawing.Size(338, 238);
            this.tableLayoutWMSLayers.TabIndex = 4;
            // 
            // clbLayerWMS
            // 
            this.clbLayerWMS.CheckOnClick = true;
            this.clbLayerWMS.Dock = System.Windows.Forms.DockStyle.Fill;
            this.clbLayerWMS.FormattingEnabled = true;
            this.clbLayerWMS.Location = new System.Drawing.Point(124, 3);
            this.clbLayerWMS.Name = "clbLayerWMS";
            this.clbLayerWMS.Size = new System.Drawing.Size(211, 232);
            this.clbLayerWMS.TabIndex = 11;
            this.clbLayerWMS.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.clbLayerWMS_ItemCheck);
            // 
            // lblSelLayersWMS
            // 
            this.lblSelLayersWMS.AutoSize = true;
            this.lblSelLayersWMS.Dock = System.Windows.Forms.DockStyle.Left;
            this.lblSelLayersWMS.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblSelLayersWMS.Location = new System.Drawing.Point(3, 0);
            this.lblSelLayersWMS.Name = "lblSelLayersWMS";
            this.lblSelLayersWMS.Size = new System.Drawing.Size(105, 238);
            this.lblSelLayersWMS.TabIndex = 1;
            this.lblSelLayersWMS.Text = "Selezione Layer :";
            // 
            // tabEliminaWMS
            // 
            this.tabEliminaWMS.Controls.Add(this.tableLayoutEliminaWMS);
            this.tabEliminaWMS.Location = new System.Drawing.Point(4, 22);
            this.tabEliminaWMS.Name = "tabEliminaWMS";
            this.tabEliminaWMS.Size = new System.Drawing.Size(344, 489);
            this.tabEliminaWMS.TabIndex = 4;
            this.tabEliminaWMS.Text = "Elimina Store WMS";
            this.tabEliminaWMS.UseVisualStyleBackColor = true;
            // 
            // tableLayoutEliminaWMS
            // 
            this.tableLayoutEliminaWMS.ColumnCount = 2;
            this.tableLayoutEliminaWMS.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 35F));
            this.tableLayoutEliminaWMS.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 65F));
            this.tableLayoutEliminaWMS.Controls.Add(this.label4, 0, 0);
            this.tableLayoutEliminaWMS.Controls.Add(this.tableLayoutPanel4, 1, 1);
            this.tableLayoutEliminaWMS.Controls.Add(this.clbStoresWMS, 1, 0);
            this.tableLayoutEliminaWMS.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutEliminaWMS.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutEliminaWMS.Name = "tableLayoutEliminaWMS";
            this.tableLayoutEliminaWMS.RowCount = 2;
            this.tableLayoutEliminaWMS.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 70F));
            this.tableLayoutEliminaWMS.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 30F));
            this.tableLayoutEliminaWMS.Size = new System.Drawing.Size(344, 489);
            this.tableLayoutEliminaWMS.TabIndex = 0;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Dock = System.Windows.Forms.DockStyle.Left;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(3, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(105, 342);
            this.label4.TabIndex = 2;
            this.label4.Text = "Selezione Layer :";
            // 
            // tableLayoutPanel4
            // 
            this.tableLayoutPanel4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.tableLayoutPanel4.ColumnCount = 2;
            this.tableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel4.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel4.Controls.Add(this.btnDeleteStoreWMS, 1, 0);
            this.tableLayoutPanel4.Controls.Add(this.btnChiudiDeleteStoreWMS, 0, 0);
            this.tableLayoutPanel4.Location = new System.Drawing.Point(216, 415);
            this.tableLayoutPanel4.Name = "tableLayoutPanel4";
            this.tableLayoutPanel4.RowCount = 1;
            this.tableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel4.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 71F));
            this.tableLayoutPanel4.Size = new System.Drawing.Size(125, 71);
            this.tableLayoutPanel4.TabIndex = 4;
            // 
            // btnDeleteStoreWMS
            // 
            this.btnDeleteStoreWMS.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnDeleteStoreWMS.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnDeleteStoreWMS.BackgroundImage")));
            this.btnDeleteStoreWMS.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnDeleteStoreWMS.Location = new System.Drawing.Point(65, 4);
            this.btnDeleteStoreWMS.Name = "btnDeleteStoreWMS";
            this.btnDeleteStoreWMS.Size = new System.Drawing.Size(57, 64);
            this.btnDeleteStoreWMS.TabIndex = 0;
            this.btnDeleteStoreWMS.Text = "SALVA";
            this.btnDeleteStoreWMS.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnDeleteStoreWMS.UseVisualStyleBackColor = true;
            this.btnDeleteStoreWMS.Click += new System.EventHandler(this.btnDeleteStoreWMS_Click);
            // 
            // btnChiudiDeleteStoreWMS
            // 
            this.btnChiudiDeleteStoreWMS.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.btnChiudiDeleteStoreWMS.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btnChiudiDeleteStoreWMS.BackgroundImage")));
            this.btnChiudiDeleteStoreWMS.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.btnChiudiDeleteStoreWMS.Location = new System.Drawing.Point(3, 4);
            this.btnChiudiDeleteStoreWMS.Name = "btnChiudiDeleteStoreWMS";
            this.btnChiudiDeleteStoreWMS.Size = new System.Drawing.Size(56, 64);
            this.btnChiudiDeleteStoreWMS.TabIndex = 1;
            this.btnChiudiDeleteStoreWMS.Text = "CHIUDI";
            this.btnChiudiDeleteStoreWMS.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
            this.btnChiudiDeleteStoreWMS.UseVisualStyleBackColor = true;
            this.btnChiudiDeleteStoreWMS.Click += new System.EventHandler(this.btnChiudiDeleteStoreWMS_Click);
            // 
            // clbStoresWMS
            // 
            this.clbStoresWMS.Dock = System.Windows.Forms.DockStyle.Fill;
            this.clbStoresWMS.FormattingEnabled = true;
            this.clbStoresWMS.Location = new System.Drawing.Point(123, 3);
            this.clbStoresWMS.Name = "clbStoresWMS";
            this.clbStoresWMS.Size = new System.Drawing.Size(218, 336);
            this.clbStoresWMS.TabIndex = 5;
            this.clbStoresWMS.ItemCheck += new System.Windows.Forms.ItemCheckEventHandler(this.clbStoresWMS_ItemCheck);
            // 
            // toolTip
            // 
            this.toolTip.IsBalloon = true;
            // 
            // frmManageGeoServer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(715, 608);
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
            this.tabImportaWMS.ResumeLayout(false);
            this.tableLayoutImportaWMS.ResumeLayout(false);
            this.tableLayoutPanel3.ResumeLayout(false);
            this.tableLayoutImportWMSTextBox.ResumeLayout(false);
            this.tableLayoutImportWMSTextBox.PerformLayout();
            this.tableLayoutWMSLayers.ResumeLayout(false);
            this.tableLayoutWMSLayers.PerformLayout();
            this.tabEliminaWMS.ResumeLayout(false);
            this.tableLayoutEliminaWMS.ResumeLayout(false);
            this.tableLayoutEliminaWMS.PerformLayout();
            this.tableLayoutPanel4.ResumeLayout(false);
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
        private System.Windows.Forms.TableLayoutPanel tableButtonImporta;
        private System.Windows.Forms.Button btnChiudiImporta;
        private System.Windows.Forms.TableLayoutPanel tableCheckDefaultView;
        private System.Windows.Forms.CheckBox ckDefaultView;
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
        private System.Windows.Forms.ListBox clbLayersSelezionati;
        private System.Windows.Forms.ToolStripMenuItem tsmModificaGroup;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox txtFindLayers;
        private System.Windows.Forms.Button btnImportaShape;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
        private System.Windows.Forms.TableLayoutPanel tableEditTitoloLayer;
        private System.Windows.Forms.CheckBox ckShapeDB;
        private System.Windows.Forms.CheckBox ckEstrazionePart;
        private System.Windows.Forms.TableLayoutPanel tableCheckBoxModificaLayer;
        private System.Windows.Forms.CheckBox ckEstrazionePart_Mod;
        private System.Windows.Forms.CheckBox ckDefaultView_Mod;
        private System.Windows.Forms.TableLayoutPanel tableButtonModificaLayer;
        private System.Windows.Forms.Button btnConfirmEditLayer;
        private System.Windows.Forms.Button btnUndoEditLayer;
        private System.Windows.Forms.CheckBox chkAbilitaRicerca;
        private System.Windows.Forms.CheckedListBox clbLayerDaImportare;
        private System.Windows.Forms.CheckBox ckbSovrascrivi;
        private System.Windows.Forms.CheckBox ckbImportaStili;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.TextBox txtEditTitoloLayer;
        private System.Windows.Forms.TextBox txtShapeFile;
        private System.Windows.Forms.ToolStripMenuItem tsmImportaWMS;
        private System.Windows.Forms.TabPage tabImportaWMS;
        private System.Windows.Forms.TableLayoutPanel tableLayoutImportaWMS;
        private System.Windows.Forms.TableLayoutPanel tableLayoutImportWMSTextBox;
        private System.Windows.Forms.TextBox txtNomeWMS;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label lblNomeLayerWMS;
        private System.Windows.Forms.TextBox txtLinkWMS;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel3;
        private System.Windows.Forms.Button btnSalvaWMS;
        private System.Windows.Forms.Button btnChiudiWMS;
        private System.Windows.Forms.TableLayoutPanel tableLayoutWMSLayers;
        private System.Windows.Forms.CheckedListBox clbLayerWMS;
        private System.Windows.Forms.Label lblSelLayersWMS;
        private System.Windows.Forms.ToolStripMenuItem tsmDeleteStoreWMS;
        private System.Windows.Forms.TabPage tabEliminaWMS;
        private System.Windows.Forms.TableLayoutPanel tableLayoutEliminaWMS;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel4;
        private System.Windows.Forms.Button btnDeleteStoreWMS;
        private System.Windows.Forms.Button btnChiudiDeleteStoreWMS;
        private System.Windows.Forms.CheckedListBox clbStoresWMS;
    }
}