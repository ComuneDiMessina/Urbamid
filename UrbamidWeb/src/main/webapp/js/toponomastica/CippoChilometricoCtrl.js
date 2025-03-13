/** 
 * CONTROLLER PER LA GESTIONE DEI CIPPI CHILOMETRICI
 */
class PaginaGestioneCippiChilometriciCtrl extends BaseModaleRicercaCtrl {
	constructor() {
		/** Inserisce come HTML la modale in pagina */
		super('paginaGestioneCippiChilometriciCtrl','Gestione Cippo Chilometrico', 'modaleGestioneCippo');
		/** Inizializza la modale con i dati dei cippi chilometrici */
		this.inizializzaPagina();
	}

	/**
	 * Inizializza la modale dei cippi chilometrici
	 */
	inizializzaPagina(){
		var self = this;
		/**Il seguente metodo viene chiamato nella BaseModaleCtrl per formare la modale**/
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			/** INIZIALIZZAZIONI */
			appUtil.reloadTooltips();
			
	    	self.aggiungiEventoClickChiudiFinestra();
	    	self.aggiungiEventiClickTornaIndietroARicerca();
			self.inizializzaBreadcrumb();
			$("#"+self.idDialog+" #tabellaCippoChilometrico").hide();								/* NASCONDO LA TABELLA */
			$("#"+self.idDialog+" #dettaglioAnagCippo").hide();										/* NASCONDO IL DETTAGLIO */
			/** IMPOSTAZIONI FORM DI RICERCA **/
			//appUtil.activeAutocompleteOnFiled('estesaCippo', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa Amministrativa...')
			appUtil.activeAutocompleteOnFiled('estesaCippo', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa Amministrativa...')
	    	/**IMPOSTAZIONI BUTTON MODALE**/
			self.setButtonModale('ricerca');
			/**IMPOSTAZIONI EDITING**/
    		self.properties = {
    				geometryWKT:null, 
    				typeEditing:'Point',
    				pagina:'paginaGestioneCippiChilometriciCtrl', 
    				idIziToast:'editingCippoIziToast', 
    				stato:null,
    				activeCuting: false,
    				activeLayers : []
    		};
    		/************************************************************************************************ AZIONI MODALE RICERCA***************** INIZIO **/
			/** CERCA */
	    	$('#' + self.idDialog + ' #ricercaBtnCippo').click(function() {
				if($('#misuraCippo').val() != '' && !self.isNumero($('#misuraCippo').val())) {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il campo Misura dev\'essere un numero!',
						animateInside: false,
						position: 'topCenter',
					});
				} else {
					/** VISUALIZZA LA TABELLA */
					$("#tabellaCippoChilometrico").show();
					/** INIZIALIZZO DATATABLE */
					self.inizializzaDatatableRicerca();
				}
			});
	    	/** AZZERA **/
			$('#' + self.idDialog + ' #azzeraBtnCippo').click(function() {
	    		self.resettaForm('#ricerca')
	    	})
			/** NUOVO */
	    	$('#' + self.idDialog + ' #nuovoBtnCippo').click(function() {
				/**INIZIALIZZA**/
				self.idDettaglio 			= null;
	    		self.properties.newEntita 	= true;
	    		self.modified	 			= false;
	    		self.cuting 				= false;
	    		self.confermaDisegno 		= false;
				self.blockEditing 			= false;
	    		/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
	    		self.riduciAdIconaTutteLeModali();
	    		/**ABILITA DISEGNO SU MAPPA DI UNA NUOVA GEOMETRIA**/
				self.avviaEditingCippo(null);

	    	})
			/** CHIUDI */
			$('#' + self.idDialog + ' #chiudiBtnCippo').click(function() {
				/**PULIZIA DEL LAYER DEL DISEGNO**/
	    		drawLayerSource.clear();
	    		/**CHIUDE LA MODALE DELL'ANAGRAFICA**/
	    		$('#'+self.idDialog).dialog('close');
			})
			/************************************************************************************************ AZIONI MODALE RICERCA ***************** FINE **/
	    	/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** INIZIO **/
			$('#' + self.idDialog + ' #aggiornaBtnCippo').click(function(event) {
				event.stopImmediatePropagation();

				if($('#misuraCippoM').val() != '' && !self.isNumero($('#misuraCippoM').val())) {

					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il campo Misura dev\'essere un numero!',
						animateInside: false,
						position: 'topCenter',
					});


				} else {

					/** RECUPERO IL VECCHIO DETTAGLIO */
					var dettaglio = self.recuperaDettaglio(self.idDettaglio);
					/** CREO L'OGGETTO DA MANDARE DA SALVARE */
					var cippoMod = {id						: self.idDettaglio,
									 estesaAmministrativa	: { id: $('#estesaAmministrativaCippoMValue').val() == '' ? dettaglio.estesaAmministrativa.id :  $('#' + self.idDialog + ' #estesaAmministrativaCippoMValue').val() },
									 misura					: $('#' + self.idDialog + ' #misuraCippoM').val() == '' ? null : $('#' + self.idDialog + ' #misuraCippoM').val(),
									 geom					: sessionStorage.getItem('wktEditFeatureGeom')!=null? sessionStorage.getItem('wktEditFeatureGeom') : dettaglio.geom,
									 note					: $('#' + self.idDialog + ' #noteCippo').val() == '' ? null : $('#' + self.idDialog + ' #noteCippo').val(),
									 codice					: $('#' + self.idDialog + ' #codiceCippoM').val() == '' ? null : $('#' + self.idDialog + ' #codiceCippoM').val()
					};
					/**SALVATAGGIO DELLA MODIFICA**/
					self.modificaCippo( cippoMod );
				}
			});
			/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** FINE **/
			/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* INIZIO **/
			$("#"+self.idDialog+" #salvaBtnCippo").click(function(event) {
				event.stopImmediatePropagation();

				if($('#misuraCippoM').val() != '' && !self.isNumero($('#misuraCippoM').val())) {

					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il campo Misura dev\'essere un numero!',
						animateInside: false,
						position: 'topCenter',
					});
				} else {

					var cippoIns = {id: null,
								 	estesaAmministrativa: { id: $('#estesaAmministrativaCippoMValue').val() == '' ? null :  $('#' + self.idDialog + ' #estesaAmministrativaCippoMValue').val() },
								 	misura				: $('#' + self.idDialog + ' #misuraCippoM').val() == '' ? null : $('#' + self.idDialog + ' #misuraCippoM').val(),
								 	geom				: sessionStorage.getItem('wktEditFeatureGeom'),
								 	note				: $('#' + self.idDialog + ' #noteCippo').val() == '' ? null : $('#' + self.idDialog + ' #noteCippo').val(),
								 	codice				: $('#' + self.idDialog + ' #codiceCippoM').val() == '' ? null : $('#' + self.idDialog + ' #codiceCippoM').val()
					};
					/** SALVATAGGIO NUOVA ENTITA' */
					self.inserisciCippo( cippoIns );
				}
			})
			/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* FINE **/
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** INIZIO **/
	    	$('#' + self.idDialog + ' #indietroBtn').click(function() {
				/** INIZIALIZZAZIONE */
				$('#ricercaAnagCippo').show();
				$('#dettaglioAnagCippo').hide();
				/** PULIZIA DELLE VARIABILI USATE NEL DETTAGLIO */
				self.idDettaglio = null;
				/** BUTTON MODALE */
				self.setButtonModale('ricerca');
				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('ricerca');
			})
			/** ================= AZIONI MODALE INSERIMENTO-DETTAGLIO ================= FINE */

		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaGestioneCippiChilometriciCtrl');
	}

	/**
	 * Il metodo setta i bottoni nella modale per tipo di azione. 
	 * Le azioni possibili sono: "ricerca", "inserimento" e "dettaglio"
	 */
	setButtonModale(type){
		switch (type) { 
			case 'ricerca': 
				$(".pulsante-ricerca").show();
				$(".pulsante-inserimento").hide();
				$(".pulsante-dettaglio").hide();
				break;
			case 'inserimento': 
				$(".pulsante-ricerca").hide();
				$(".pulsante-inserimento").show();
				$(".pulsante-dettaglio").hide();
				break;
			case 'dettaglio': 
				$(".pulsante-ricerca").hide();
				$(".pulsante-inserimento").hide();
				$(".pulsante-dettaglio").show();
				break;		
			default:
				$(".pulsante-ricerca").show();
				$(".pulsante-inserimento").hide();
				$(".pulsante-dettaglio").hide();
		}
	}

	/**
	 * Il metodo resetta il form di ricerca dato l'id.
	 * Gli input da ripulire hanno i type: text, textarea,select, checkbox e radio 
	 */
	resettaForm(form) {
		$(':input', form).each(function() {
			let type = this.type;
			let tag = this.tagName.toLowerCase();
		    if(type == 'checkbox' || type == 'radio') {
				 this.checked = false;
			} else if (tag == 'select') {
				this.selectedIndex = 0;
			} else if (type == 'text') {
                this.value = ''
            }
		})
	}

	/**
	 * Metodo di utility per verificare se è numero, passandogli in input un valore. Il risultato
	 * true = è un numero
	 * false = non è un numero
	 * @param {*} numero 
	 */
	isNumero(numero) {
		return !isNaN(parseFloat(numero)) && isFinite(numero)
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
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicerca() {
		/** INIZIALIZZAZIONI */
		var self = this;
		/** RECUPERO PARAMETRI DI RICERCA */
		var codice = $('#codiceCippo').val();
		var estesa = $('#estesaCippo').val();
		var misura = $('#misuraCippo').val();
		/** INIZIALIZZO LA TABELLA */
		$("#tabellaCippoChilometrico").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 1, 2, 3 ],
				postfixButtons: [ { extend: 'colvisRestore',
									text: '<hr size="1" style="margin-top: 6px">' +
										  '<button class="btn btn-primary btnColvisRestore">Visualizza tutto</button>',
								} ]
			} ],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
	        ajax: {
				type : "POST",
				//url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaCippo',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/ricercaCippo',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var colonnaSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							colonnaSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {codice: codice.trim(),
								  estesaAmministrativa: estesa.trim(),
								  misura: misura.trim(),
								  stato: 'PUBBLICATO',
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: colonnaSort, dir: json.order[0].dir } };
					
					return JSON.stringify(filtri);
				}  
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
				{targets : 0, name: 'codice', render: function(_d, _t, r) {return r.codice;}, orderable : true},
				{targets : 1, name: 'estesaAmministrativa', render: function(_d, _t, r) {return r.estesaAmministrativa == null ? '' : r.estesaAmministrativa.descrizione;}, orderable : true},
	        	{targets : 2, name: 'misura', render: function(_d, _t, r) {return r.misura + " KM";}, orderable : true},
				{targets : 3, name: 'stato', render: function(_d, _t, r) {return r.stato}, orderable : true},
				{targets : 4, name: 'note', render: function(_d, _t, r) {return r.note;}, orderable : true},
				{targets : 5, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabella', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	sessionStorage.setItem('ricercaCippo', JSON.stringify(settings.json.data));
	        	self.aggiungiEventiAiPulsantiAzioneCippo();
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio del cippo chilometrico. Il salvataggio di una modifica non cambierà lo stato dell'entità ma soltanto gli attributi
	 * - elimina, aprirà un box di conferma con la richiesta di eliminazione del cippo
	 *     1. si procede con eliminazione nella tabella u_topo_cippo_chilometrico
	 * 	   2. l'operazione verrà annullata.
	 * - localizza, viene richiamato il metodo getGeometry che ridurrà la modale mostrando su mappa la geometria
	 * 
	 **/
	aggiungiEventiAiPulsantiAzioneCippo() {
		var self = this;
		$("#tabellaCippoChilometrico button.modifica-btn").off('click').on('click', function(_event){
			self.properties.newEntita 	= true;
    		self.modified	 			= false;	
    		self.cuting 				= false;
			self.blockEditing 			= false;
			/** VISUALIZZA IL DETTAGLIO */
			self.visualizzaDettaglio($(this).data('id'));
			self.inizializzaDatatableDocumenti($(this).data('id'), 7);
        });
		
		$("#tabellaCippoChilometrico button.elimina-btn").off('click').on('click', function(_event){
			/** ELIMINA CIPPO */
			self.eliminaCippo($(this).data('id'));
        });
		
		$("#tabellaCippoChilometrico button.localizza-btn").off('click').on('click', function(_event) {
			/** LOCALIZZA LA GEOMETRIA */
			self.getGeometry($(this).data('geometry'));
		});
		
	}

	/**
	 * Metodo utile solo per aggiungere su mappa una geometria
	 * @param WKT
	 * @returns
	*/
	getGeometry(WKT) {
		var self = this;

		if (WKT != '' && WKT != null && WKT != undefined) {						//WKT DIVERSO DA NULL
			/**RIPULISCE LAYER**/
			drawLayerSource.clear();
			/**COSTRUISCE FEATURE DAL WKT**/
			var format = new ol.format.WKT();
			var feature = format.readFeature(WKT, {
				dataProjection: 'EPSG:4326',
				featureProjection: 'EPSG:3857'
			});
			/**AGGIUNGE LA FEATURE AL LAYER CON ZOOM**/
			drawLayerSource.addFeature(feature);
			const extent = feature.getGeometry().getExtent();
			(appMappa.maps).getView().fit(
					extent, {duration: 1000}
			);
			/**RIDUCE LA MODALE**/
			self.riduciAdIconaTutteLeModali();
		} else {																//WKT NULL												
			iziToast.error({
				title: 'Attenzione',
				theme: 'dark',
				icon: 'fa fa-times',
				message: 'Impossibile visualizzare la geometria in mappa!',
				animateInside: false,
				position: 'topCenter',
			});
		}
	}
	
	/**
	 * Il metodo visualizza il dettaglio del cippo chilometrico con id passato in input. La visualizzazione del dettaglio prevede una modale già aperta
	 * @param id, identificativo dell'entità Cippo Chilometrico 
	 */
	visualizzaDettaglio(id) {
		/** INIZIALIZZAZIONI */
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

		if(id != null) {																							/**GESTIONE DI UN DETTAGLIO**/

			/**RECUPERO DEL DETTAGLIO**/
    		var dettaglio 				= self.recuperaDettaglio(id);				//Recupero il dettaglio del cippo selezionato
    		self.idDettaglio 			= id;										//Salvo l'identificativo del dettaglio
			var cippoMod 				= {};										//Creo l'oggetto cippoMod utile per la modifica all'entità
			/** IMPOSTAZIONI PAGINA */
			$('#' + self.idDialog + ' #ricercaAnagCippo').hide();
			$('#' + self.idDialog + ' #dettaglioAnagCippo').show();
			$('#' + self.idDialog + ' #liDetAnagraficaCippo').show();
			$('#' + self.idDialog + ' #liDetDocumentoCippo').show();
			/** BUTTON MODALE */
			self.setButtonModale('dettaglio');
			/** OGGETTO CIPPO PER LA VISUALIZZAZIONE */
			var detCippo = { id: id,
								codice: dettaglio.codice == null ? '' : dettaglio.codice,
								estesaAmministrativa: dettaglio.estesaAmministrativa == null ? '' : dettaglio.estesaAmministrativa.descrizione,
                                misura: dettaglio.misura == null ? '' : dettaglio.misura,
								geom: dettaglio.geom == null ? false : true,
								note: dettaglio.note == null ? '' : dettaglio.note,
								disabledSigla: 'disabled'
			};
			/** COMPILO IL TEMPLATE CON L'OGGETTO detCippo */
			let html = compilaTemplate('modaleDettaglioCippo', detCippo);
			$('#dettaglio').html(html);
			/** AGGIORNO IL BREADCRUMB */
			if (!self.modified && !self.cuting) self.aggiornaBreadcrumb('dettaglio', 'Km ' + detCippo.misura + ' - ' + detCippo.codice);	
			/** AUTOCOMPLETE */
			//appUtil.activeAutocompleteOnFiled('estesaAmministrativaCippoM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa Amministrativa...');
			appUtil.activeAutocompleteOnFiled('estesaAmministrativaCippoM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa Amministrativa...');
			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita = false;
			(self.properties).geometryWKT = dettaglio.geom == null ? null : dettaglio.geom;
			(self.properties).stato = dettaglio.stato == null ? null : dettaglio.stato;
			/*************************************************************************************************************************************/
			/** VISUALIZZA IL CIPPO */
			$('#' + self.idDialog + ' .localizza-geom-cippo-btn').click(function() {
				/** FACCIO VISUALIZZARE LA GEOMETRIA SU MAPPA */
				self.getGeometry(dettaglio.geom);
			})
			if (self.blockEditing)
				$("#"+self.idDialog+" .editing-geom-cippo-btn").hide();
			/** EDITING CIPPO */
			$('#' + self.idDialog + ' .editing-geom-cippo-btn').click(function() {
				self.properties.newEntita 	= false;
				self.confermaDisegno 		= false;
				/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
				self.riduciAdIconaTutteLeModali();
				/**ABILITA DISEGNO SU MAPPA DI UNA GEOMETRIA PRESENTE SOLO SU INTERFACCIA**/
				var geomWKT = detCippo != undefined ? detCippo.geom : null;
				self.avviaEditingCippo(geomWKT);
			});
			/** TAB DOCUMENTI */
			$('#' + self.idDialog + ' #documentoBtnCippo').click(function() {
				self.uploadDocumento(self.idDettaglio, 7);
			})
		} else {																							/**GESTIONE DI UN INSERIMENTO**/
			
			/** IMPOSTAZIONI PAGINA */
			if($('#' + self.idDialog + ' ul.breadcrumb li:last-child').text().indexOf('Cippo') != -1) {
				self.aggiornaBreadcrumb('nuovo');
			} else {
				$('#' + self.idDialog + ' ul.breadcrumb li:last-child').remove();
				self.aggiornaBreadcrumb('nuovo');
			}
			$('#anagrafica-tab1').click();
			$('#' + self.idDialog + ' #ricercaAnagCippo').hide();
			$('#' + self.idDialog + ' #dettaglioAnagCippo').show();
			$('#' + self.idDialog + ' #liDetAnagraficaCippo').show();
			$('#' + self.idDialog + ' #liDetDocumentoCippo').hide();
			/**GESTIONE DEL DETTAGLIO DA VISUALIZZARE**/
			var detCippo = {disabledSigla: 'disabled'};
			var htmlDet = compilaTemplate('modaleDettaglioCippo', detCippo);
			$('#dettaglio').html(htmlDet);
			/** AUTOCOMPLETE */
			//appUtil.activeAutocompleteOnFiled('estesaAmministrativaCippoM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa Amministrativa...')
			appUtil.activeAutocompleteOnFiled('estesaAmministrativaCippoM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa Amministrativa...')
			/**BUTTON MODALE**/
    		self.setButtonModale("inserimento");
			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita 	= true;
			(self.properties).geometryWKT 	= null;
			(self.properties).stato 		= null;
			/************************************************************************************************ AZIONI NUOVO ************** INIZIO **/
			/** EDITING CIPPO */
			$('#' + self.idDialog + ' .editing-geom-cippo-btn').click(function() {
				if (sessionStorage.getItem('wktEditFeatureGeom') ) {
					self.newEntita = true;
					/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
					var format = new ol.format.WKT();
					var features = format.readGeometry( sessionStorage.getItem('wktEditFeatureGeom') );
					/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
					self.riduciAdIconaTutteLeModali();
					/**ABILITA DISEGNO SU MAPPA**/
					var geomWKT = dettaglio != undefined ? dettaglio.geom : 
						(sessionStorage.getItem('wktEditFeatureGeom')!=undefined ? sessionStorage.getItem('wktEditFeatureGeom') : null);
					/** @TODO @AP EDITING */
				}
			});
			/************************************************************************************************ AZIONI NUOVO ************** FINE **/
		}
		appUtil.hideLoader();
	}

	/**
	 * Metodo per il recupero del dettaglio di un cippo chilometrico dato l'id
	 * @param id, identificativo del cippo
	 */
	recuperaDettaglio(id) {
		
		let risultato = JSON.parse(sessionStorage.getItem('ricercaCippo'));
		let dettaglio;
		for(var i=0; i<risultato.length; i++){
			if(risultato[i].id === id){
				dettaglio = risultato[i];
				break;
			}
		}
		
		return dettaglio;
		
	}
	
	/**
	 * Il metodo inizializza la tabella dei documenti legata con idCippo e tipo.
	 * @param idCippo è l'indentficativo del 
	 * @param tipo è un codice numerico che indentifica le varie entità:
	 * - 1 TOPONIMO STRADALE
	 * - 2 PERCORSO
	 * - 3 LOCALITA
	 * - 4 GIUNZIONI STRADALI
	 * - 5 ESTESA AMMINISTRATIVA
	 * - 6 AREE E ELEMENTI STRADALI
	 * - 7 CIPPO CHILOMETRICO
	 * - 8 ACCESSO
	 */
	inizializzaDatatableDocumenti(idCippo, tipo) {
		var self = this;
		$("#tabellaDocumentiCippo").DataTable({
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
	        ajax: {
				type : "POST",
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaDocumenti',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var filtri = {tipoRisorsa: tipo,
								  idRisorsa: idCippo,
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw };
					
					return JSON.stringify(filtri);
				}  
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(_d, _t, r) {return r.nomeDocumento;}, width: '70%', orderable : true},
	        	{targets : 1, render: function(_d, _t, r) {return new Date(r.dataInserimento).toLocaleDateString();}, orderable : true},
	        	{targets : 2, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: idCippo, nomeFile: r.nomeDocumento});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {
	        	self.aggiungiEventiAiPulsantiDocumentiCippo();
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
	aggiungiEventiAiPulsantiDocumentiCippo() {
		var self = this;
		$("#tabellaDocumentiCippo button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('id'), 7, $(this).data('nome'));
        });
		
		$("#tabellaDocumentiCippo button.download-documento-btn").off('click').on('click', function(_event){
			self.downloadDocumento($(this).data('nome'), $(this).data('id'), 7);
        });
	}

	/**
	 * Il metodo apre una modale con un form di upload del file.
	 * @param idCippo indentifica il cippo
	 * @param tipo è un indentificativo numerico che serve per distinguere le varie entità:
	 * 1 - TOPONIMO STRADALE
	 * 2 - PERCORSO
	 * 3 - LOCALITA'
	 * 4 - GIUNZIONI STRADALI
	 * 5 - ESTESA AMMINISTRATIVA
	 * 6 - AREE E ELEMENTI STRADALI
	 * 7 - CIPPO CHILOMETRICO
	 * 8 - ACCESSO
	 */
	uploadDocumento(idCippo, tipo) {
		var self = this;
    	openStaticDetailViaHandlebars('uploadDocumento', 'modaleUploadDocumentiCippo', {}, 'Upload', function(){
    		
    		appUtil.reloadTooltips();
    		
			$('#uploadDocumentoBtn').click(function() {
				self.inserisciDocumento(idCippo, tipo);
			})
    		
    	}, {width: 350, height: 155, resizable: false, modale: true, closable: true});
		
	}
	
	/**
	 * Il metodo carica sul server il documento legandolo all'id identificativo del cippo e al tipo di risorsa.
	 * @param idCippo indentifica il cippo chilometrico
	 * @param tipo è un indentificativo numerico che serve per distinguere le varie entità:
	 * 1 - TOPONIMO STRADALE
	 * 2 - PERCORSO
	 * 3 - LOCALITA'
	 * 4 - GIUNZIONI STRADALI
	 * 5 - ESTESA AMMINISTRATIVA
	 * 6 - AREE E ELEMENTI STRADALI
	 * 7 - CIPPO CHILOMETRICO
	 * 8 - ACCESSO
	 */
	inserisciDocumento(idCippo, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var fd = new FormData($('#uploadForm')[0]);
		var files = $('#file')[0].files[0];
        fd.append('file',files);
		
		setTimeout(function(){
			$.when(toponomasticaRest.uploadFile(fd, idCippo, tipo)).done(function (response) {
				appUtil.hideLoader()
				if(response.success) {
					appUtil.hideLoader();
		        	$('#uploadDocumento').dialog('close');
					self.ricaricaDataTable('tabellaDocumentiCippo')
					iziToast.show({
		    			title: 'OK',
		    			theme: 'dark',
		    			icon:'fa fa-check',
		    			message: 'Upload completato!',
		    			animateInside: false,
		    			position: 'topCenter',
		    		});
				} else {
					appUtil.hideLoader();
					iziToast.error({
		    			title: 'Attenzione',
		    			theme: 'dark',
		    			icon:'fa fa-times',
		    			message: 'Errore nell\'upload del documento!',
		    			animateInside: false,
		    			position: 'topCenter',
		    		});
				}
			})
		})
	}

	/**
	 * Il metodo effettua il download del documento dal server.
	 * @param nomeFile il nome del file
	 * @param idCippo è l'id del cippo chilometrico
	 * @param tipo è un indentificativo numerico che serve per distinguere le varie entità:
	 * 1 - TOPONIMO STRADALE
	 * 2 - PERCORSO
	 * 3 - LOCALITA'
	 * 4 - GIUNZIONI STRADALI
	 * 5 - ESTESA AMMINISTRATIVA
	 * 6 - AREE E ELEMENTI STRADALI
	 * 7 - CIPPO CHILOMETRICO
	 * 8 - ACCESSO
	 */
	downloadDocumento(nomeFile, idCippo, tipo) {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();
			toponomasticaRest.downloadFile(nomeFile, idCippo, tipo);
		})
	}
	
	/**
	 * Il metodo elimina dal server il documento.
	 * @param nomeFile il nome del file
	 * @param idCippo è l'id del cippo chilometrico
	 * @param tipo è un indentificativo numerico che serve per distinguere le varie entità:
	 * 1 - TOPONIMO STRADALE
	 * 2 - PERCORSO
	 * 3 - LOCALITA'
	 * 4 - GIUNZIONI STRADALI
	 * 5 - ESTESA AMMINISTRATIVA
	 * 6 - AREE E ELEMENTI STRADALI
	 * 7 - CIPPO CHILOMETRICO
	 * 8 - ACCESSO
	 */
	eliminaDocumento(idCippo, tipo, nomeFile) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaDocumento(idCippo, tipo, nomeFile)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {
						self.ricaricaDataTable('tabellaDocumentiCippo');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Documento eliminato con successo!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Errore nella cancellazione del documento!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
				})
			})
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione del documento?');
	}

	/**
	 * Il metodo salva il toponimo stradale e riporta l'utente sulla pagina di ricerca. 
	 * @param cippo, è un oggetto contentente le informazioni di un cippo chilometrico da inserire
	 */
	inserisciCippo(cippo) {
		var self = this;
		$.when(toponomasticaRest.inserisciCippo(cippo)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				/** INIZIALIZZAZIONE */
				sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
				$("#ricercaAnagCippo").show();								//Visualizzo la ricerca
				$("#dettaglioAnagCippo").hide();							//Nascondo il dettaglio
				self.aggiornaBreadcrumb('ricerca');							//Aggiorno il Breadcrumb
	    		$('#ricercaBtnCippo').click();								//Ricarico la tabella
	    		/** BUTTON MODALE */
				self.setButtonModale("ricerca");
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Cippo chilometrico inserito con successo',
		    		animateInside: false,
		    		position: 'topCenter',
				});
				appUtil.hideLoader();
			} else {
				appUtil.hideLoader();
				iziToast.error({
		    		title: 'Attenzione',
		    		theme: 'dark',
		    		icon:'fa fa-times',
		    		message: 'Errore nell\'inserimento del cippo chilometrico',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		})

	}
	
	/**
	 * Il metodo salva la modifica di un cippo e riporta l'utente sulla pagina di ricerca. 
	 * @param cippoMod, è un oggetto contentente le informazioni di un cippo da modificare
	 */
	modificaCippo( cippoMod ) {
		var self = this;
		$.when(toponomasticaRest.inserisciCippo(cippoMod)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				/** INIZIALIZZAZIONE */
				sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
				$("#ricercaAnagCippo").show();								//Visualizzo la ricerca
				$("#dettaglioAnagCippo").hide();							//Nascondo il dettaglio
				self.aggiornaBreadcrumb('ricerca');							//Aggiorno il Breadcrumb
	    		$('#ricercaBtnCippo').click();
	    		/** BUTTON MODALE */
				self.setButtonModale("ricerca");
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Cippo chilometrico modificato con successo',
		    		animateInside: false,
		    		position: 'topCenter',
				});
				appUtil.hideLoader();
			} else {
				appUtil.hideLoader();
				iziToast.error({
		    		title: 'Attenzione',
		    		theme: 'dark',
		    		icon:'fa fa-times',
		    		message: 'Errore nella modifica del cippo chilometrico',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		})
	}

	/**
	 * Il metodo elimina un cippo chilometrico chiedendo conferma. In seguito all'operazione l'utente viene riportato sulla pagina di ricerca. 
	 * @param id, identificativo del cippo
	 */
	eliminaCippo(id) {
		var self = this;
		appUtil.confirmOperation(function() {	
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaCippo(id)).done(function (response) {
					if(response.success) {
		    			$('#ricercaBtnCippo').click();
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Cippo chilometrico eliminato con successo',
			    			animateInside: false,
			    			position: 'topCenter',
						});
						appUtil.hideLoader();
					} else {
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Errore nella cancellazione del cippo chilometrico!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
				})
			});

		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione del cippo chilometrico?');
	}
	
	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* INIZIO ********/
	/****************************************************************************************************************************************************************/
	/**
	 * Avvio dell'editing
	 */
	avviaEditingCippo(geometry) {
		/**1 - inizializzo**/
		var self = this;
		drawLayerSource.clear();
		/**2 - avvia la gestione dell'editing**/
		manageEditing(self, self.properties);
	}
	/**
	 * Il metodo conferma le feature disegnate/tagliate e da il via al salvataggio chiudendo il iziToast "editingCippoIziToast"
	 */
	confermaFeatureDisegnate(){
		var self = this;
		if( self.modified ){																//HO EFFETTUATO DELLE MODIFICHE
			
			/**1.1 - Recupero la geometria**/
			var wktfeaturegeom = "";
			var src = 'EPSG:3857';
			var dest = 'EPSG:4326';
			var format = new ol.format.WKT();
			var newGeom = self.editSourceFeatures.getFeatures()[self.editSourceFeatures.getFeatures().length-1];
			var newWkt = format.writeGeometry(newGeom.getGeometry().transform(src, dest));
			if ( newWkt.indexOf("POINT")!=-1 ) 
			    sessionStorage.setItem('wktEditFeatureGeom', newWkt);
			/**1.2 - Disabilito il disegno su mappa**/
			self.toast = document.getElementById('editingCippoIziToast'); // Selector of your toast
			iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
			/**1.3 - Confermo il disegno**/
			self.confermaDisegno = true;
			/**1.4 - Confermo il disegno**/
			self.blockEditing = true;
		} else if( self.cuting ){															//HO EFFETTUATO DEI TAGLI
			
			if (appUtil.numHmElem(self.hmEntityCut) == self.numCut+1 ){
				/**2.1 - Recupero la geometria**/
				var wktfeaturegeom = "";
				var src = 'EPSG:3857';
				var dest = 'EPSG:4326';
				var format = new ol.format.WKT();
				var wkts = [];
				$.each(self.editSourceFeatures.getFeatures(), function(index, value){
					var wkt = format.writeGeometry(value.getGeometry().transform(src, dest));
					if ( wkt.indexOf("LINE")!=-1 ) wkts.push(wkt);
				});
				sessionStorage.setItem('wktEditFeatureGeom', wkts);
				/**2.2 - Disabilito il disegno su mappa**/
				self.toast = document.getElementById('editingAccessoIziToast'); // Selector of your toast
				iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
				/**1.4 - Confermo il disegno**/
				self.confermaDisegno = true;
				/**1.4 - Confermo il disegno**/
				self.blockEditing = true;
			} else {
				appUtil.showMessageAlertApplication(
						'Per salvare &eacute; necessario definire le due nuove entit&agrave;', 
						'Nuovi toponimi stradali non definiti', false, true, false, null, null);
			}
		} else {
			
			appUtil.showMessageAlertApplication(
					'Per definire un\'entit&agrave; di toponomastica &eacute; necessario modificare la geometria', 
					'Nessuna area tracciata', false, true, false, null, null);
		} 
	}
}