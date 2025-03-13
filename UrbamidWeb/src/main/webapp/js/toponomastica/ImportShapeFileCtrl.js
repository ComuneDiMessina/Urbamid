'use strict'

/**
 * CONTROLLER PER LA GESTIONE DEGLI IMPORT PER I SHAPE FILE
 */
class PaginaGestioneImportCtrl extends BaseModaleRicercaCtrl {
    constructor() {
		/**Inserisce come html la modale in pagina**/
		super('paginaGestioneImportCtrl', 'Import shape file', 'modaleGestioneImport');
		/**Inizializza la modale con i dati degli shape file**/
		this.inizializzaPagina();
	}

    /**
	 * Inizializza la modale dell'import 
	 */
	inizializzaPagina() {
        var self = this;

        /**Il seguente metodo viene chiamato nella BaseModaleCtrl per formare la modale**/
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){

            /** INIZIALIZZAZIONI **/
			appUtil.reloadTooltips();
	    	self.aggiungiEventoClickChiudiFinestra();
	    	self.aggiungiEventiClickTornaIndietroARicerca();
			self.inizializzaBreadcrumb();
			self.inizializzaStepper();
			self.inizializzaDatatableRicerca();
			self.inizializzaUploaderPerId('uploadShapeInput');
			self.listaFiles = null;
            
			/********************************************************************* AZIONI MODALE ***************** INIZIO **/
            $('#' + self.idDialog + ' #avviaUploadShapeButton').click(function() {
				if(self.controlloShapeFile()) {
				self.uploadShapeFile(self.listaFiles);
				} else {
					iziToast.error({
		    			title: 'Attenzione',
		    			theme: 'dark',
		    			icon:'fa fa-times',
		    			message: 'Devi caricare i file .shp, .shx e .dbf dello stesso shape file nell\'archivio .zip',
		    			animateInside: false,
		    			position: 'topCenter',
		    		});
				}
            });

			/** CHIUDI **/
	    	$('#' + self.idDialog + ' #chiudiBtnImport').click(function() {
	    		/**CHIUDE LA MODALE DELL'ANAGRAFICA**/
	    		$('#'+self.idDialog).dialog('close');
	    	});
            /********************************************************************* AZIONI MODALE ***************** FINE **/

        }, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
        sessionStorage.setItem('windowOpened','paginaGestioneImportCtrl');
    }

	/** METODO PER INIZIALIZZARE GLI STEPPER */
	inizializzaStepper() {
		var self = this;
		//impostiamo il primo tab ad attivo
		$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
		$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').first().addClass('active');
		$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
		$('#'+self.idDialog+' form div.tab-content div.tab-pane').first().addClass('active');

		//impostiamo gli eventi di click per passare da un tab all'altro
		$('#stepUploadShape').on('click', function(){
			$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
			$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(1)').addClass('active');
			$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
			$('#'+self.idDialog+' #stepUploadShapeDiv').addClass('active');
		});
	}

    /** 
	 * Il metodo ricarica DataTable
	 * @param idTable, identificativo della tabella
	 */
	ricaricaDataTable(idTable) {
		var self = this;
		if (!$.fn.DataTable.isDataTable('#' + idTable)) {
			self.inizializzaDatatableRicerca();
			$('#' + idTable).show();
		} else {
			var table = $('#' + idTable).DataTable()
			table.ajax.reload();
		}
	}

    /**
	 * Metodo per inizializzare la tabella di ricerca
	 */
	inizializzaDatatableRicerca() {
        /** INIZIALIZZAZIONI */
        var self = this;
        /**INIZIALIZZO TABELLA**/
        $('#tabellaShapeFile').DataTable({
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: false,
	        destroy: true,
	        serverSide: true,
			processing: true,
	        ajax: {
				type : "POST",
				processData : false,
				dataType : 'json',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getShapeFiles',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var colonnaSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							colonnaSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: colonnaSort, dir: json.order[0].dir } }
					
					return JSON.stringify(filtri);
				},
				cache : true,
				async: true
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, name: 'nameFile', render: function(_d, _t, r) {return r.nameFile;}, width: '40%', orderable: true},
                {targets : 1, name: 'sizeFile', render: function(_d, _t, r) {return (r.sizeFile / 1000).toFixed(0) < 1 ? '1 KB' : (r.sizeFile / 1000).toFixed(0) + ' KB';}, orderable: true},
				{targets : 2, name: 'dataImport', render: function(_d, _t, r) {return new Date(r.dataImport).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'});}, orderable : true},
	        	{targets : 3, className: "text-right", orderable : false, render: function(_d, _t, r) {
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {nomeFile: r.nameFile, id: r.id});
	        		return templatePulsanti;
					}
				}
		   ],
	       drawCallback: function( settings ) {
				$("#tabellaShapeFile button.download-documento-btn").hide();
				self.aggiungiEventiAiPulsanti();
	       	}
		});
    }

    /** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - elimina, aprirà un box di conferma con la richiesta di eliminazione rispettando i seguenti casi:
	 *     1. se si decide di procedere verrà eliminato il file dal server
	 *     2. se non si decide di procedere verrà annullata l'operazione.
	 * - download, verrà effettuato il download del file.
	 **/
	aggiungiEventiAiPulsanti() {
		var self = this;
		$("#tabellaShapeFile button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('id'), $(this).data('nome'));
        });
		
		// $("#tabellaShapeFile button.download-documento-btn").off('click').on('click', function(_event){
		// 	self.downloadShapeFile($(this).data('nome'));
        // });
	}

    // downloadDocumento(nomeFile, tipo) {
    //     var self = this;
    //     appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	// 	setTimeout(function(){
	// 		appUtil.hideLoader();
	// 		toponomasticaRest.downloadFile(nomeFile, tipo); //DOWNLOAD DEL FILE
	// 	})
    // }

	/**
	 * Il metodo elimina i file .dbf, .shp e .shx dal server
	 * @param {*} nomeFile il nome del file 
	 */
    eliminaDocumento(id, nomeFile) {
        var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaShapeFile(id)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {										 //SE LA RISPOSTA DEL SERVER E' POSITIVA
						self.ricaricaDataTable('tabellaImportShape');           //RICARICO DATATABLE
						iziToast.show({											 //MESSAGGIO
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Shape file eliminato con successo!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {													//SE LA RISPSOTA DEL SERVER E' NEGATIVA
						iziToast.error({											//MESSAGGIO
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Errore nella cancellazione dello shape file!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
				})
			})
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione dei file: ' + nomeFile +'?');
    }

	/**
	 * Metodo che avvia l'upload degli shape file, passandogli in input una lista di file
	 * @param {*} listaFiles la lista dei file
	 */
	uploadShapeFile(listaFiles) {
		var self = this;
		var uploader = $('#'+self.idDialog+' #uploadShapeInput').prop('jFiler');
		var data = new FormData($('#' + self.idDialog + ' #upload')[0]);

		appUtil.showLoader(null, 'Per favore, attendi il completamento dell\'elaborazione dei file.');
		// $.each(listaFiles, function(key, value) {
		// 	data.append('file', value);
		// });

		data.append('file', listaFiles);

		$.when(toponomasticaRest.importShpFile(data)).done(function (response) {
			if(response.success) {
				self.ricaricaDataTable('tabellaShapeFile');
				appUtil.hideLoader();
				uploader.reset();
				self.listaFiles = null;
				iziToast.show({
					title: 'OK',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'Import dello shape file completato!',
					animateInside: false,
					position: 'topCenter',
				});
			} else {
				appUtil.hideLoader();
				iziToast.error({
					title: 'ERRORE',
					theme: 'dark',
					icon:'fa fa-times',
					message: response.sEcho,
					animateInside: false,
					position: 'topCenter',
				});
			}
		});
	}

	/**
	 * Metodo che controlla il contenuto della lista @argument listaFiles per vedere se c'è un file con l'estenzione .zip
	 */
	controlloShapeFile() {
		var self = this;
		let fileCorretti = false;
		if(self.listaFiles != undefined || self.listaFiles != null) {
			let nomeFile = self.listaFiles.name.split(self.listaFiles.name.charAt(self.listaFiles.name.length - 4));
			if(nomeFile[1] == 'zip') {
				fileCorretti = true;
			} else {
				fileCorretti = false;
			}
		} else {
			fileCorretti = false;
		}

		return fileCorretti;
	}

	/**
	 * Metodo che inizializza l'uploader di jQuery.filer
	 */
	inizializzaUploaderPerId(idInput){
		var self = this;
		let templateDragAndDrop = compilaTemplate('dragdropUploader', {});
		$('#'+self.idDialog+' #'+idInput).filer({
			limit: 1,
			maxSize: 2000,
			extensions: ['zip'],
			changeInput: templateDragAndDrop,
			showThumbs: true,
			theme: "dragdropbox",
			templates: self.templateFiler,
			dragDrop: { dragEnter: null, dragLeave: null, drop: null, dragContainer: null, },
			files: [],
			addMore: true,
			allowDuplicates: false,
			clipBoardPaste: true,
			excludeName: null,
			beforeRender: null,
			afterRender: null,
			beforeShow: function(itemEl, file, id, listEl, boxEl, newInputEl, inputEl){
				self.aggiungiElementiAllaListaFileAggiunti(itemEl[0]);
				return true;
			},
			beforeSelect: null,
			onSelect: null,
			afterShow: null,
			onRemove: function(itemEl, file, id, listEl, boxEl, newInputEl, inputEl){
				// self.listaFiles.splice(id, 1);
			},
			onEmpty: null,
			options: null,
			dialogs: {
				alert: function(text) {
					return appUtil.showMessageAlertApplication(text, null, false, true, false, null, null);
				},
				confirm: function (text, callback) {
					appUtil.confirmOperation(callback,null,null,text) ? callback() : null;
				}
			},
			captions: {button: "Scegli i file",
					   feedback: "Scegli i file da caricare",
					   feedback2: "I files sono stati scelti",
					   drop: "Rilascia il file qui per caricarlo",
					   removeConfirmation: "Sei sicuro di voler rimuovere il file?",
					   errors: {
							filesLimit: "Solo {{fi-limit}} file sono consentiti per il caricamento.",
							filesType: "&#201; consentito il caricamento dei soli file con l'estensione .zip.",
							filesSize: "{{fi-name}} &#233; troppo grande! Per favore carica file di dimensioni fino a {{fi-maxSize}} GB.",
							filesSizeAll: "I file che hai scelto sono troppo grandi! Per favore carica file di dimensioni fino a {{fi-maxSize}} GB."
					   	}
					}
		});
	}

	/**
	 * Metodo che aggiunge alla lista dei file i file scelti.
	 * Il metodo accetta come parametro l'array di argomenti che viene dal jQuery.filer.
	 * @param {*} files lista di files
	 */
	aggiungiElementiAllaListaFileAggiunti(files){
		var self = this;
		if(files != null || files != undefined || files.length != 0) {
			self.listaFiles = files;
			// $.each(files, function(key, value) {
			// 	self.listaFiles.push(value);
			// })
		}
	}

}