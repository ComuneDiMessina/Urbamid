'use strict'

/**
 * Controller principale della pagina di gestione dati catastali. 
 * Fondamentalmente aggiunge solo degli eventi di click che aprono un oggetto controller appropriato che gestisce
 * la pagina di gestione dati.
 */
class GestioneDatiPageCtrl {
	constructor( ) {
		this.aggiungiEventiClickVaiAPagina();
		appUtil.reloadTooltips();
	}
	aggiungiEventiClickVaiAPagina(){
		$('#importaDati').off('click').on('click', function(event){ new PaginaImportaDatiCtrl('ATTUALITA'); });
		$('#importaCartografiaCatastale').off('click').on('click', function(event){ new PaginaImportaCartografiaCatastaleCtrl(); });
		$('#importaAggiornamenti').off('click').on('click', function(event){ new PaginaImportaDatiCtrl('AGGIORNAMENTO'); });
		$('#importaPlanimetrie').off('click').on('click', function(event){ new PaginaImportaPlanimetrieCtrl(); });
		$('#statoAggiornamenti').off('click').on('click', function(event){ new PaginaStatoAggiornamentiCtrl(); });
		$('#esportaDati').off('click').on('click', function(event){ new PaginaEsportaDatiCtrl(); });
	}
}

/**
 * Superclasse dei controller di gestione dati catastali
 */
class BaseModaleGestioneDatiCtrl  extends BaseModaleCtrl{
	/**
	 * Costruttore base del controller
	 */
	constructor(idDialog, nomePagina, idScriptHandlebars){
		super(idDialog, nomePagina, idScriptHandlebars);
		//template per l'uploader
		this.templateInputDataRiferimento = 
			'<div class="input-group">\
					<input type="text" class="form-control data-riferimento" placeholder="Data riferimento">\
					<span class="input-group-addon"><i class="fa fa-calendar"></i></span>\
			</div>';
		this.templateTipologiaImportamento = 
			'<select class="form-control tipologia-caricamento">\
				<option>Seleziona...</option>\
				<option>Nuovo impianto</option>\
				<option>Aggiornamento</option>\
			</select>';
		this.templateTipoEstrazione = 
			'<select class="form-control tipo-estrazione">\
				<option>Seleziona...</option>\
				<option>Tipo 1</option>\
				<option>Tipo 2</option>\
				<option>Tipo 3</option>\
				<option>Tipo 4</option>\
			</select>';
		this.templateTipoFileImportaCartografia =
			'<select class="form-control tipo-estrazione">\
				<option>Seleziona...</option>\
				<option>Cartografia catastale vettoriale (*.cxf)</option>\
				<option>Cartografia catastale (*.cmf)</option>\
			</select>';
		this.templateFiler = {
			box: '<ul class="jFiler-items-list jFiler-items-grid"></ul>',
			item: '<li class="jFiler-item">\
						<div class="jFiler-item-container">\
							<div class="jFiler-item-inner">\
								<div class="jFiler-item-thumb">\
									<div class="jFiler-item-status"></div>\
									<div class="jFiler-item-thumb-overlay">\
										<div class="jFiler-item-info">\
											<div style="display:table-cell;vertical-align: middle;">\
												<span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name}}</b></span>\
												<span class="jFiler-item-others">{{fi-size2}}</span>\
											</div>\
										</div>\
									</div>\
									{{fi-image}}\
								</div>\
								<div class="jFiler-item-assets jFiler-row">\
									<ul class="list-inline pull-left">\
										<li>{{fi-progressBar}}</li>\
									</ul>\
									<ul class="list-inline pull-right">\
										<li><a class="icon-jfi-trash jFiler-item-trash-action"></a></li>\
									</ul>\
								</div>\
							</div>\
						</div>\
					</li>',
			itemAppend: '<li class="jFiler-item">\
							<div class="jFiler-item-container">\
								<div class="jFiler-item-inner">\
									<div class="jFiler-item-thumb">\
										<div class="jFiler-item-status"></div>\
										<div class="jFiler-item-thumb-overlay">\
											<div class="jFiler-item-info">\
												<div style="display:table-cell;vertical-align: middle;">\
													<span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name}}</b></span>\
													<span class="jFiler-item-others">{{fi-size2}}</span>\
												</div>\
											</div>\
										</div>\
										{{fi-image}}\
									</div>\
									<div class="jFiler-item-assets jFiler-row">\
										<ul class="list-inline pull-left">\
											<li><span class="jFiler-item-others">{{fi-icon}}</span></li>\
										</ul>\
										<ul class="list-inline pull-right">\
											<li><a class="icon-jfi-trash jFiler-item-trash-action"></a></li>\
										</ul>\
									</div>\
								</div>\
							</div>\
						</li>',
			progressBar: '<div class="bar"></div>',
			itemAppendToEnd: true,
			canvasImage: true,
			removeConfirmation: true,
			_selectors: {
				list: '.jFiler-items-list',
				item: '.jFiler-item',
				progressBar: '.bar',
				remove: '.jFiler-item-trash-action'
			}
		};
		this.captionFiler = {
			button: "Scegli i file",
			feedback: "Scegli i file da caricare",
			feedback2: "I files sono stati scelti",
			drop: "Rilascia il file qui per caricarlo",
			removeConfirmation: "Sei sicuro di voler rimuovere il file?",
			errors: {
				filesLimit: "Solo {{fi-limit}} file sono al pi&#250; consentiti per il caricamento.",
				filesType: "&#201; consentito il caricamento di solo file di tipo immagine.",
				filesSize: "{{fi-name}} &#233; troppo grande! Per favore carica file di dimensioni fino a {{fi-maxSize}} MB.",
				filesSizeAll: "I file che hai scelto sono troppo grandi! Per favore carica file di dimensioni fino a {{fi-maxSize}} MB."
			}
		};
	}
	dimensioneInKb(dimensione){
		return Math.round( (dimensione/1000) * 100) / 100;
	}
}

/**
 * Controller per la modale di import cartografia catastale
 */ 
class PaginaImportaCartografiaCatastaleCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaImportaCartografiaCatastale','IMPORTA CARTOGRAFIA CATASTALE', 'modImportaCartografiaCatastale');
		this.datiMockFile = [{
			nome: 'C2018_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '01/05/2019',
		},{
			nome: 'C2019_120500.cxf',
			dimensione: '5784.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '01/05/2019',
		},{
			nome: 'C2000_007700.cxf',
			dimensione: '785.00 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '01/05/2019',
		},{
			nome: 'C2019_007700.cxf',
			dimensione: '123.67 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '01/05/2019',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '01/05/2019',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '15/05/2010',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '01/01/2009',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '01/05/2008',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '01/05/2007',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '11/08/2006',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '01/07/2005',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '01/01/2004',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '01/01/2003',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '01/01/2002',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '01/01/2001',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '06/01/2011',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '07/06/2015',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CXF',
			codiceComune: 'F158',
			data: '13/12/1999',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '02/05/2010',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '19/02/2018',
		},{
			nome: 'CAAA_007700.cxf',
			dimensione: '345.67 KB',
			tipo: 'CMF',
			codiceComune: 'F158',
			data: '11/11/2019',
		}];
		this.inizializzaPagina();
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			self.inizializzaStepper();
			self.inizializzaUploader();
			self.inizializzaDatatableFile();
			self.inizializzaMultiselect();
			self.inizializzaGestioneTabRicerca();
			self.aggiungiEventoChangeSuSelectFiltroDati();
			appUtil.reloadTooltips();
		}, {closable: true});
	}
	inizializzaGestioneTabRicerca(){
		$("div.bhoechie-tab-menu>div.list-group>a").on('click', function(e) {
			e.preventDefault();
			$(this).siblings('a.active').removeClass("active");
			$(this).addClass("active");
			var index = $(this).index();
			$("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active").addClass("hidden");
			$("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active").removeClass('hidden');
			$("em.da-cancellare-al-momento-opportuno").remove();
			$("div.bhoechie-tab-menu>div.list-group>a").eq(index).prepend('<em class="da-cancellare-al-momento-opportuno fa fa-check" style="font-size: 1.2em;margin-right:5px;"></em>');
		});
	}
	/**
	 * Metodo che inizializza lo stepper
	 */
	inizializzaStepper(){
		var self = this;
	    //Wizard
	    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
	        var $target = $(e.target);
	        if ($target.parent().hasClass('disabled')) {
	            return false;
	        }
	    });
	    $('#'+self.idDialog+' .next-step').click(function (e) {
	        var $active = $('.wizard .nav-tabs li.active');
	        $active.next().removeClass('disabled');
	        self.vaiAlloStepSuccessivo($active);
	        if( $('#'+self.idDialog+' #stepOpzioni').hasClass('active')){
		    	$('#'+self.idDialog+' #importaDatiBtn').parent('li').removeClass('hidden');
	        } else {
		    	$('#'+self.idDialog+' #importaDatiBtn').parent('li').addClass('hidden');
	        }
	    });
	    $('#'+self.idDialog+' .prev-step').click(function (e) {
	        var $active = $('.wizard .nav-tabs li.active');
	        self.vaiAlloStepPrecedente($active);
	        if( $('#'+self.idDialog+' #stepOpzioni').hasClass('active')){ 
		    	$('#'+self.idDialog+' #importaDatiBtn').parent('li').removeClass('hidden');
	        } else {
		    	$('#'+self.idDialog+' #importaDatiBtn').parent('li').addClass('hidden');
	        }
	    });
	    $('#'+self.idDialog+' #stepOpzioniA').on('click', function(){
	    	$('#'+self.idDialog+' #importaDatiBtn').parent('li').removeClass('hidden');
	    });
	    $('#'+self.idDialog+' #stepUploadA').on('click', function(){
	    	$('#'+self.idDialog+' #importaDatiBtn').parent('li').addClass('hidden');
	    });
	}
	/**
	 * Metodo che sposta allo step successivo
	 */
	vaiAlloStepSuccessivo(elemento) {
	    $(elemento).next().find('a[data-toggle="tab"]').click();
	}
	/**
	 * Metodo che sposta allo step precedente
	 */
	vaiAlloStepPrecedente(elemento) {
	    $(elemento).prev().find('a[data-toggle="tab"]').click();
	}
	/**
	 * Metodo che inizializza il datatable dei file
	 */
	inizializzaDatatableFile(){
		var self = this;
		$('#'+self.idDialog+' #tabellaFile').DataTable( {
			data: self.datiMockFile,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        processing: true,
	        order: [[ 1, 'asc' ]],
	        select: {
	            style:    'os',
	            selector: 'td:first-child'
	        },
	        columnDefs: [
	        	{targets: 0, orderable: false, className: 'select-checkbox', data: null, defaultContent: '',},
	        	{targets : 1, render: function(d, t, r) {return r.nome;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.dimensione;}, orderable : true},
	        	{targets : 3, render: function(d, t, r) {return r.tipo;}, orderable : true},
	        	{targets : 4, render: function(d, t, r) {return r.codiceComune;}, orderable : true},
	        	{targets : 5, render: function(d, t, r) {return r.data;}, orderable : true}
	        ],
	        initComplete: function( settings ) {
	        	//MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	        }
	    });
	}
	aggiungiElementoAllaTabella(parameters){
		var self = this;
		if(parameters && parameters.length > 0){
			let filesAggiunti = parameters[0];
			for(var i=0; i<filesAggiunti.length; i++){
				let nuovoFile = {};
				nuovoFile.dimensione = this.dimensioneInKb(filesAggiunti[i].size);
				nuovoFile.nome = filesAggiunti[i].name;
				nuovoFile.tipo = filesAggiunti[i].type;
				$('#'+self.idDialog+' #tabellaFile').dataTable().fnAddData([{
					nome: nuovoFile.nome,
					dimensione: nuovoFile.dimensione,
					tipo: nuovoFile.tipo,
				}]);
			}
		}
	}
	rimuoviElementoDallaTabella(parameters){
		var self = this;
		if(parameters && parameters.length > 0){
			let fileDaRimuovere = parameters[1];
			var table = $('#'+self.idDialog+' #tabellaFile').DataTable();
			table.rows( function ( idx, data, node ) {
				if(data.nome === fileDaRimuovere.name
						&& data.dimensione === self.dimensioneInKb(fileDaRimuovere.size)
							&& data.tipo === fileDaRimuovere.type ){
					return true;
				}
		        return false;
		    })
		    	.remove()
		    	.draw();
		}
	}
	inizializzaUploader(){
		var self = this;
		$('#'+self.idDialog+' #fileAdeguamento').filer({
			limit: 1,
			showThumbs: true,
			addMore: true,
			allowDuplicates: false,
			dialogs: {
				alert: function(text) {
					return appUtil.showMessageAlertApplication(text, null, false, true, false, null, null);
				},
				confirm: function (text, callback) {
					appUtil.confirmOperation(callback,null,null,text) ? callback() : null;
				}
			},
			captions: self.captionFiler
		});
	}
	inizializzaMultiselect(){
		$('#'+this.idDialog+' #multiselect-options').multiSelect();
	}
	/**
	 * Imposta un evento di onchange sulla select per filtare i file in formato CXF/CMF
	 */
	aggiungiEventoChangeSuSelectFiltroDati(){
		var self = this;
		$('#'+self.idDialog+' #selectCartografiaCatastale').off('change').on('change', function(){
			let table = $('#'+self.idDialog+' #tabellaFile').DataTable();
			table.column(3).search($(this).val()).draw();
		});
	}
}

/**
 * TODO 3
 */
class PaginaImportaAggiornamentiCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaImportaAggiornamenti','IMPORTA AGGIORNAMENTI', 'modImportaAggiornamenti');
		//this.inizializzaPagina();
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			appUtil.reloadTooltips();
		}, {closable: true});
	}
}

/**
 * TODO 4
 */
class PaginaImportaPlanimetrieCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaImportaPlanimetrie','IMPORTA PLANIMETRIE', 'modImportaPlanimetrie');
		this.inizializzaPagina();
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			//nascondiamo le altre scelte (inizialmente viene mostrato solo #scelta0)
			$('#scelta1, #scelta2, #scelta3').hide();
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			self.inizializzaGestioneTabRicerca();
			self.inizializzaUploader();
			appUtil.reloadTooltips();
		}, {closable: true});
	}
	inizializzaGestioneTabRicerca(){
		$("div.bhoechie-tab-menu>div.list-group>a").on('click', function(e) {
			e.preventDefault();
			$(this).siblings('a.active').removeClass("active");
			$(this).addClass("active");
			var index = $(this).index();
			$("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active").addClass("hidden");
			$("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active").removeClass('hidden');
			$("em.da-cancellare-al-momento-opportuno").remove();
			$("div.bhoechie-tab-menu>div.list-group>a").eq(index).prepend('<em class="da-cancellare-al-momento-opportuno fa fa-check" style="font-size: 1.2em;margin-right:5px;"></em>');
			switch(index){
			case 0:
				$('#scelta1, #scelta2, #scelta3').hide(0, function(){
					$('#scelta0').fadeIn('slow');
				});
				break;
			case 1:
				$('#scelta0, #scelta2, #scelta3').hide(0, function(){
					$('#scelta1').fadeIn('slow');
				});
				break;
			case 2:
				$('#scelta0, #scelta1, #scelta3').hide(0, function(){
					$('#scelta2').fadeIn('slow');
				});
				break;
			case 3:
				$('#scelta0, #scelta1, #scelta2').hide(0, function(){
					$('#scelta3').fadeIn('slow');
				});
				break;
			}
		});
	}
	inizializzaUploader(){
		var self = this;
		let opzioniUploader = {
			limit: 1,
			showThumbs: true,
			addMore: true,
			allowDuplicates: false,
			dialogs: {
				alert: function(text) {
					return appUtil.showMessageAlertApplication(text, null, false, true, false, null, null);
				},
				confirm: function (text, callback) {
					appUtil.confirmOperation(callback,null,null,text) ? callback() : null;
				}
			},
			captions: self.captionFiler
		};
		$('#'+this.idDialog+' #file00').filer(opzioniUploader);
		$('#'+this.idDialog+' #file01').filer(opzioniUploader);
		$('#'+this.idDialog+' #file10').filer(opzioniUploader);
		$('#'+this.idDialog+' #file20').filer(opzioniUploader);
		$('#'+this.idDialog+' #file21').filer(opzioniUploader);
		$('#'+this.idDialog+' #file22').filer(opzioniUploader);
		$('#'+this.idDialog+' #file30').filer(opzioniUploader);
	}
}

/**
 * Controller per la modale dello stato aggiornamenti
 */ 
class PaginaStatoAggiornamentiCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaStatoAggiornamenti','STATO AGGIORNAMENTI', 'modStatoAggiornamenti');
		this.inizializzaPagina();
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
		}, {width: 450, height: 350, closable: true});
		$('#'+self.idDialog).dialog('option', 'draggable', false);
	}
}

/**
 * GESTIONE DATI
 */
class PaginaEsportaDatiCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaEsportaDati','ESPORTA DATI', 'modEsportaDati');
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
		}, {closable: true});
	}
}