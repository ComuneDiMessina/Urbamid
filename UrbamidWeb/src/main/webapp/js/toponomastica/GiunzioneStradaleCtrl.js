class PaginaGestioneGiunzioneStradaleCtrl extends BaseModaleRicercaCtrl {
	constructor() {
		super('paginaGestioneGiunzioneStradaleCtrl','Gestione Giunzione Stradale', 'modaleGestioneGiunzioni');		
		
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		/** Recupero dei dati di base della modale Giunzione Stradale */
		/**************************** TIPO TOPOLOGICO **************************/
		var hrefToponomasticaTopologicoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getTipoTopologico';
		var UrbamidResponseTipoTopologico = appRest.restUrbamid(hrefToponomasticaTopologicoUrbamid, "GET", null);
		self.tipiTopologici = UrbamidResponseTipoTopologico.aaData;
		/**************************** TIPI FUNZIONALE **************************/
		var hrefToponomasticaFunzionaleUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getTipoFunzionale';
		var UrbamidResponseTipoFunzionale = appRest.restUrbamid(hrefToponomasticaFunzionaleUrbamid, "GET", null);
		self.tipiFunzionali = UrbamidResponseTipoFunzionale.aaData;

		/**Il seguente metodo viene chiamato nella BaseModaleCtrl per formare la modale**/
		let contextHandlebar = {tipiTopologici: self.tipiTopologici, tipiFunzionali: self.tipiFunzionali};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			
			/** INIZIALIZZAZIONI */
			appUtil.reloadTooltips();
			self.aggiungiEventoClickChiudiFinestra();
	    	self.aggiungiEventiClickTornaIndietroARicerca();
			self.inizializzaBreadcrumb();
			self.upperCase('ricerca');
			/* NASCONDO LA TABELLA E DETTAGKUI*/
	    	$("#tabellaGiunzioni").hide();			
			$('#dettaglioAnagGiunzione').hide();
			/** IMPOSTAZIONI BUTTON MODALE */
			self.setButtonModale('ricerca');
			/**IMPOSTAZIONI EDITING**/
    		self.properties = {
    				geometryWKT:null, 
    				typeEditing:'LineString',
    				pagina:'paginaGestioneGiunzioneStradaleCtrl', 
    				idIziToast:'editingGiunzioneIziToast', 
    				stato:null,
    				activeCuting: false,
    				activeLayers : []
    		};
    		/************************************************************************************************ AZIONI MODALE RICERCA***************** INIZIO **/
			/** CERCA */
			$('#' + self.idDialog + ' #ricercaBtnGiunzioni').click(function() {
				/** VISUALIZZO LA TABELLA */
				$('#' + self.idDialog +  ' #tabellaGiunzioni').show();
				/** INIZIALIZZO DATATABLE */
				self.inizializzaDatatableRicerca();
			});
			/** AZZERA **/
			$('#' + self.idDialog + ' #azzeraBtn').click(function() {
	    		self.resettaForm('ricerca')
	    	})
			/** NUOVO */
	    	$('#' + self.idDialog + ' #nuovoBtnGiunzione').click(function() {
	    		/**INIZIALIZZA**/
	    		self.properties.newEntita 	= true;
	    		self.idDettaglio 			= null;
	    		self.modified 				= false;
	    		self.cuting 				= false;
	    		self.confermaDisegno 		= false;
	    		self.blockEditing 			= false;
	    		/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
	    		self.riduciAdIconaTutteLeModali();
				/**ABILITA DISEGNO SU MAPPA DI UNA NUOVA GEOMETRIA**/
				self.avviaEditingGiunzione(null);
			});
			/** CHIUDI */
			$('#' + self.idDialog + ' #chiudiBtnGiunzione').click(function() {
				/** PULIZIA DEL LAYER DEL DISEGNO */
				drawLayerSource.clear();
				/** CHIUSURA DELLA MODALE */
				$('#' + self.idDialog).dialog('close');
			})
			/************************************************************************************************ AZIONI MODALE RICERCA ***************** FINE **/
	    	/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** INIZIO **/
	    	$('#' + self.idDialog + ' #aggiornaBtnGiunzione').click(function(event) {
				event.stopImmediatePropagation();

				/** RECUPERO IL VECCHIO DETTAGLIO */
				var dettaglio = self.recuperaDettaglio(self.idDettaglio);
				/** CREO L'OGGETTO DA SALVARE */
				var giunzioneStradaleMod = { id: self.idDettaglio,
										      descrizione			: $('#' + self.idDialog + ' #descrizioneGiunzioneM').val() != '' ? $('#' + self.idDialog + ' #descrizioneGiunzioneM').val() : null,
										      tipoTopologico		: { id: $('#' + self.idDialog + ' #tipoTopologicoGiunzioneM option:selected').val() },
										      tipoFunzionale		: { id: $('#' + self.idDialog + ' #tipoFunzionaleGiunzioneM option:selected').val() },
										      limiteAmministrativo  : $('#' + self.idDialog + ' #limiteAmministrativoGiunzioneM').is(':checked'),
										      inizioFineStrada	  	: $('#' + self.idDialog + ' #inizioFineStradaGiunzioneM').is(':checked'),
										      geom				  	: sessionStorage.getItem('wktEditFeatureGeom')!=null && sessionStorage.getItem('wktEditFeatureGeom')!=""  
																			? sessionStorage.getItem('wktEditFeatureGeom') : dettaglio.geom,
											  note				    : $('#' + self.idDialog + ' #noteGiunzione').val() != '' ? $('#' + self.idDialog + ' #noteGiunzione').val() : null,
										      stato				    : dettaglio.stato,
											  isCircle				: dettaglio.isCircle
				};
				/** SALVATAGGIO DELLA MODIFICA */
				self.modificaGiunzione(giunzioneStradaleMod);
			});
			/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** FINE **/
			/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* INIZIO **/
			$('#' + self.idDialog + ' #salvaBtnGiunzione').click(function(event) {
				event.stopImmediatePropagation();
			
				/** CREO L'OGGETTO DA SALVARE (id null perchè utile per definire la nuova entità) */
				var giunzioneStradaleIns = { id: null,
										  	  descrizione: $('#' + self.idDialog + ' #descrizioneGiunzioneM').val() == '' ? null :  $('#' + self.idDialog + ' #descrizioneGiunzioneM').val(),
										  	  tipoTopologico: { id: $('#' + self.idDialog + ' #tipoTopologicoGiunzioneM option:selected').val() },
										  	  tipoFunzionale: { id: $('#' + self.idDialog + ' #tipoFunzionaleGiunzioneM option:selected').val() },
										  	  limiteAmministrativo: $('#' + self.idDialog + ' #limiteAmministrativoGiunzioneM').is(':checked'),
										  	  inizioFineStrada: $('#' + self.idDialog + ' #inizioFineStradaGiunzioneM').is(':checked'),
										  	  geom: sessionStorage.getItem('wktEditFeatureGeom'),
										  	  note: $('#' + self.idDialog + ' #noteGiunzione').val() == '' ? null : $('#' + self.idDialog + ' #noteGiunzione').val(),
											  isCircle: self.properties.typeEditing.indexOf('Circle') != -1 ? true : false
				};
				/**SALVATAGGIO NUOVA ENTITA**/
				self.inserisciGiunzione(giunzioneStradaleIns);
			}); 
			/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* FINE **/
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** INIZIO **/
			$('#' + self.idDialog + ' #indietroBtn').click(function() {
	    		/** INIZIALIZZAZIONE */
				$('#ricercaAnagGiunzione').show();
				$('#dettaglioAnagGiunzione').hide();
				/** PULIZIA DELLE VARIABILI USATE NEL DETTAGLIO */
				self.idDettaglio = null;
				/** BUTTON MODALE */
				self.setButtonModale('ricerca');
				/** AGGIORNO BREADCRUMB */
				self.aggiornaBreadcrumb('ricerca');
			});
			/** ================= AZIONI MODALE DETTAGLIO-INSERIMENTO ================= FINE */
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaGestioneGiunzioneStradaleCtrl');
	}
	
	/**
	 * Il metodo setta i bottoni nella modale per tipo di azione. 
	 * Le azioni possibili sono: "ricerca", "inserimento" e "dettaglio"
	 */
	setButtonModale (type){
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
		$(':input', '#' + form).each(function() {
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
	 * Imposta tutti gli input type text di un form in maiuscolo
	 * @param form, l'id del form
	 */
	upperCase(form) {
		$(':input', '#' + form).keyup(function() {
			let type = this.type;
			if(type == "text") {
				this.value = this.value.toLocaleUpperCase();
			}
		})
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
		/** INIZIALIZZAZIONE */
		var self = this;
		/** RECUPERO PARAMETRI DI RICERCA */
		var descrizione = $('#descrizioneGiunzione').val();
		var tipoTopologico = $('#tipoTopologicoGiunzione option:selected').val();
		var tipoFunzionale = $('#tipoFunzionaleGiunzione option:selected').val();
		/** INIZIALIZZO DATATABLE */
		$("#tabellaGiunzioni").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 1, 2, 3, 4, 5 ],
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
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaGiunzioniStradali',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var colonnaSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							colonnaSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {descrizione: descrizione.trim(),
								  tipoTopologico: tipoTopologico,
								  tipoFunzionale: tipoFunzionale,
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
	        	{targets : 0, name: 'descrizione', render: function(_d, _t, r) {return r.descrizione != null ? r.descrizione : '';}, orderable : true},
	        	{targets : 1, name: 'tipoTopologico', render: function(_d, _t, r) {return r.tipoTopologico != null ? r.tipoTopologico.descrizione : '';}, orderable : true},
	        	{targets : 2, name: 'tipoFunzionale', render: function(_d, _t, r) {return r.tipoFunzionale != null ? r.tipoFunzionale.descrizione : '';}, orderable : true},
	        	{targets : 3, name: 'limiteAmministrativo', render: function(_d, _t, r) {return r.limiteAmministrativo ? 'SI' : 'NO';}, orderable : true},
	        	{targets : 4, name: 'inizioFineStrada', render: function(_d, _t, r) {return r.inizioFineStrada ? 'SI' : 'NO';}, orderable : true},
				{targets : 5, name: 'stato', render: function(_d, _t, r) {return r.stato;}, orderable : true },
				{targets : 6, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabella', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});	        		
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	self.aggiungiEventiAiPulsantiAzioneGiunzione();
	        	sessionStorage.setItem('ricercaGiunzioni', JSON.stringify(settings.json.data));
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio delle giunzioni stradali. Il salvataggio di una modifica non cambierà lo stato dell'entità ma soltanto gli attributi
	 * - elimina, aprirà un box di conferma con la richiesta di eliminazione della giunzione stradale
	 *     1. si procede con eliminazione nella tabella u_topo_giunzione_stradale
	 * 	   2. l'operazione verrà annullata.
	 * - localizza, viene richiamato il metodo getGeometry che ridurrà la modale mostrando su mappa la geometria
	 * 
	 **/
	aggiungiEventiAiPulsantiAzioneGiunzione() {
		var self = this;
		$("#tabellaGiunzioni button.modifica-btn").off('click').on('click', function(_event){

			self.properties.newEntita 	= true;
    		self.modified 				= false;
    		self.cuting 				= false;
    		self.blockEditing 			= false;
			/** VISUALIZZA IL DETTAGLIO */
			self.visualizzaDettaglio($(this).data('id'));
			/** INIZIALIZZA LA TABELLA DEI DOCUMENTI */
			self.inizializzaDatatableDocumenti($(this).data('id'), 4);
        });
		
		$("#tabellaGiunzioni button.elimina-btn").off('click').on('click', function(_event){
			/** ELIMINA GIUNZIONE */
			self.eliminaGiunzione($(this).data('id'));
        });
		
		$("#tabellaGiunzioni button.localizza-btn").off('click').on('click', function(_event) {
			/** LOCALIZZA LA GEOMETRIA */
			self.getGeometry($(this).data('geometry'));
		})
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
			iziToast.show({
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
	 * Il metodo visualizza il dettaglio dell'estesa amministrativa con id passato in input. La visualizzazione del dettaglio prevede una modale già aperta
	 * @param id, identificativo dell'entità estesa amministrativa
	 */
	visualizzaDettaglio(id) {
		/** INIZIALIZZAZIONI */
		var self = this;
		/** VISUALIZZO IL LOADER */
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

		if(id != null) {

			var giunzioneStradaleM = {};

			/** RECUPERO IL DETTAGLIO */
			var dettaglio = self.recuperaDettaglio(id);
			self.idDettaglio = id;

			/** IMPOSTAZIONI PAGINA */
			$('#anagrafica-tab1').click();
			$('#' + self.idDialog + ' #ricercaAnagGiunzione').hide();
			$('#' + self.idDialog + ' #dettaglioAnagGiunzione').show();
			$('#' + self.idDialog + ' #liDetDocumentoGiunzione').show();
			/** BUTTON MODALE */
			self.setButtonModale('dettaglio');
			/** OGGETTO ESTESA PER LA VISUALIZZAZIONE */
			var detGiunzione = { id: id,
								 descrizione: dettaglio.descrizione == null ? '' : dettaglio.descrizione,
								 tipiTopologici: self.tipiTopologici,
								 tipoTopologico: dettaglio.tipoTopologico == null ? '' : dettaglio.tipoTopologico.id,
								 tipiFunzionali: self.tipiFunzionali,
								 tipoFunzionale: dettaglio.tipoFunzionale == null ? '' : dettaglio.tipoFunzionale.id,
								 limiteAmministrativo: dettaglio.limiteAmministrativo ? 'checked' : '',
								 inizioFineStrada: dettaglio.inizioFineStrada ? 'checked' : '',
								 note: dettaglio.note == null ? '' : dettaglio.note,
								 geom: dettaglio.geom == null ? false : true,
							    }

			/** COMPILO IL TEMPLATE CON L'OGGETTO detGiunzione */
			let html = compilaTemplate('modaleDettaglioGiunzione', detGiunzione);
			$('#dettaglio').html(html);

			/** UPPER CASE CAMPI INPUT */
			self.upperCase('dettaglio');

			/** INIZIALIZZO LE SELECT CON GLI ID DEL TIPO TOPOLOGICO E TIPO FUNZIONALE */
			$('#' + self.idDialog + ' #tipoTopologicoGiunzioneM').val(dettaglio.tipoTopologico != null ? detGiunzione.tipoTopologico : '');
			$('#' + self.idDialog + ' #tipoFunzionaleGiunzioneM').val(dettaglio.tipoFunzionale != null ? detGiunzione.tipoFunzionale : '');

			/** AGGIORNO IL BREADCRUMB */
			if (!self.modified) self.aggiornaBreadcrumb('dettaglio', detGiunzione.descrizione);
			
			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita = false;
			(self.properties).geometryWKT = dettaglio.geom == null || dettaglio.isCircle ? null : dettaglio.geom;
			(self.properties).stato = dettaglio.stato == null ? null : dettaglio.stato;
			(self.properties).typeEditing = dettaglio.isCircle ? 'Circle' : 'LineString';
			/** ================================================================================================================================================== */
			/** VISUALIZZA GIUNZIONE */
			$('#' + self.idDialog + ' .localizza-geom-giunzione-btn').click(function() {
				/** FACCIO VISUALIZZARE LA GEOMETRIA SU MAPPA */
				self.getGeometry(dettaglio.geom)
			});
			if (self.blockEditing)
				$("#"+self.idDialog+" .editing-geom-giunzione-btn").hide();
			/** EDITING GIUNZIONE */
			$('#' + self.idDialog + ' .editing-geom-giunzione-btn').click(function() {

				self.properties.newEntita 	= false;
				self.confermaDisegno 		= false;
				/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
				self.riduciAdIconaTutteLeModali();
				/**ABILITA DISEGNO SU MAPPA DI UNA GEOMETRIA PRESENTE SOLO SU INTERFACCIA**/
				var geomWKT = dettaglio != undefined ? dettaglio.geom : null;
				self.avviaEditingGiunzione(geomWKT);
			});

			/** TAB DOCUMENTI */
			$('#' + self.idDialog + ' #documentoBtnGiunzione').click(function() {
				self.uploadDocumento(self.idDettaglio, 4)
			});

		} else {

			/** BUTTON MODALE */
			self.setButtonModale('inserimento');

			/** IMPOSTAZIONI PAGINA */
			if($('#' + self.idDialog + ' ul.breadcrumb li:last-child').text().indexOf('Giunzioni') != -1) {
				self.aggiornaBreadcrumb('nuovo');
			} else {
				$('#' + self.idDialog + ' ul.breadcrumb li:last-child').remove();
				self.aggiornaBreadcrumb('nuovo');	
			}
			$('#anagrafica-tab1').click();
			$('#' + self.idDialog + ' #ricercaAnagGiunzione').hide();
			$('#' + self.idDialog + ' #dettaglioAnagGiunzione').show();
			$('#' + self.idDialog + ' #liDetDocumentoGiunzione').hide();

			/** COMPILO TEMPLATE */
			var detGiunzione = { tipiTopologici: self.tipiTopologici, tipiFunzionali: self.tipiFunzionali };
			var html = compilaTemplate('modaleDettaglioGiunzione', detGiunzione);
			$('#dettaglio').html(html);

			/** UPPER CASE CAMPI INPUT */
			self.upperCase('dettaglio');
			
			/** EDITING GIUNZIONE */
			$('#' + self.idDialog + ' .editing-geom-giunzione-btn').click(function() {
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

		}
		appUtil.hideLoader();
	}
	
	/**
	 * Metodo per il recupero del dettaglio di un estesa amministrativa dato l'id
	 * @param id, identificativo dell'estesa
	 */
	recuperaDettaglio(id) {
		let risultato = JSON.parse(sessionStorage.getItem('ricercaGiunzioni'));
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
	 * Il metodo inizializza la tabella dei documenti legata con idEstesa e tipo.
	 * @param idGiunzione è l'indentficativo del 
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
	inizializzaDatatableDocumenti(idGiunzione, tipo) {
		var self = this;
		$("#tabellaDocumenti").DataTable({
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
								  idRisorsa: idGiunzione,
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw };
					
					return JSON.stringify(filtri);
				}  
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(_d, _t, r) {return r.nomeDocumento;}, width: '70%', orderable : true},
	        	{targets : 1, render: function(_d, _t, r) {return new Date(r.dataInserimento).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'});}, orderable : true},
	        	{targets : 2, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: idGiunzione, nomeFile: r.nomeDocumento});
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
		$("#tabellaDocumenti button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('id'), $(this).data('nome'), 4);
        });
		
		$("#tabellaDocumenti button.download-documento-btn").off('click').on('click', function(_event){
			self.downloadDocumento($(this).data('id'), $(this).data('nome'), 4);
        });
	}

	/**
	 * Il metodo apre una modale con un form di upload del file.
	 * @param idGiunzione indentifica la giunzione
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
	uploadDocumento(idGiunzione, tipo) {
		var self = this;
    	openStaticDetailViaHandlebars('uploadDocumentoGiunzione', 'modaleUploadDocumentiGiunzione', {}, 'Upload', function(){
    		
    		appUtil.reloadTooltips();
    		
			$('#uploadDocumentoBtn').click(function() {
				self.inserisciDocumento(idGiunzione, tipo);
			})
    		
    	}, {width: 350, height: 155, resizable: false, modale: true, closable: true});
		
	}

	/**
	 * Il metodo carica sul server il documento legandolo all'id identificativo dell'estesa amministrativa e al tipo di risorsa.
	 * @param idEstesa indentifica il cippo chilometrico
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
	inserisciDocumento(idGiunzione, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var fd = new FormData($('#uploadForm')[0]);
		var files = $('#file')[0].files[0];
        fd.append('file',files);
		
		setTimeout(function(){
			$.when(toponomasticaRest.uploadFile(fd, idGiunzione, tipo)).done(function (response) {
				appUtil.hideLoader()
				if(response.success) {
					appUtil.hideLoader();
		        	$('#uploadDocumentoGiunzione').dialog('close');
		        	self.ricaricaDataTable('tabellaDocumenti')
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
	 * @param idGiunzione è l'id della giunzione stradale
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
	downloadDocumento(idGiunzione, nomeFile, tipo) {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();
			toponomasticaRest.downloadFile(nomeFile, idGiunzione, tipo);
		})
	}
	
	/**
	 * Il metodo elimina dal server il documento.
	 * @param nomeFile il nome del file
	 * @param idGiunzione è l'id del cippo chilometrico
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
	eliminaDocumento(idGiunzione, nomeFile, tipo) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaDocumento(idGiunzione, tipo, nomeFile)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {
						self.ricaricaDataTable('tabellaDocumenti');
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
	 * Il metodo salva la giunzione stradale e riporta l'utente sulla pagina di ricerca. 
	 * @param giunzioneStradale, è un oggetto contentente le informazioni di una giunzione stradale
	 */
	inserisciGiunzione(giunzioneStradale) {
		var self = this;
		$.when(toponomasticaRest.inserisciGiunzione(giunzioneStradale)).done(function(response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				sessionStorage.removeItem('wktEditFeatureGeom');
				$('#ricercaAnagGiunzione').show();
				$('#dettaglioAnagGiunzione').hide();
				self.aggiornaBreadcrumb('ricerca');
				self.setButtonModale('ricerca');
				self.ricaricaDataTable('tabellaGiunzioni');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Giunzione stradale: ' + giunzioneStradale.descrizione + ' inserita con successo',
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
		    		message: response.sEcho,
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		});
	}

	/**
	 * Il metodo salva la modifica di una giunzione stradale e riporta l'utente sulla pagina di ricerca. 
	 * @param giunzioneStradaleM, è un oggetto contentente le informazioni di una giunzione stradale
	 */
	modificaGiunzione(giunzioneStradaleM) {
		var self = this;
		$.when(toponomasticaRest.inserisciGiunzione(giunzioneStradaleM)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				sessionStorage.removeItem('wktEditFeatureGeom');
				$('#ricercaAnagGiunzione').show();						
				$('#dettaglioAnagGiunzione').hide();
				self.aggiornaBreadcrumb('ricerca');
				self.setButtonModale('ricerca');
				self.ricaricaDataTable('tabellaGiunzioni');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Giunzione stradale: ' + giunzioneStradaleM.descrizione + ' modificata con successo',
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
		    		message: response.sEcho,
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		});
	}

	/**
	 * Il metodo elimina una giunzione stradale chiedendo conferma. In seguito all'operazione l'utente viene riportato sulla pagina di ricerca. 
	 * @param id, identificativo di una giunzione stradale
	 */
	eliminaGiunzione(id) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaGiunzione(id)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {
						self.ricaricaDataTable('tabellaGiunzioni');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Giunzione eliminata con successo!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: response.sEcho,
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
				})
			});
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione della giunzione stradale?');
	}
	
	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* INIZIO ********/
	/****************************************************************************************************************************************************************/
	/**
	 * Avvio dell'editing
	 */
	avviaEditingGiunzione(geometry) {
		/**1 - inizializzo**/
		var self = this;
		drawLayerSource.clear();
		/**2 - avvia la gestione dell'editing**/
		manageEditing(self, self.properties);
		if(!self.properties.newEntita && self.properties.typeEditing.indexOf('Circle') == -1) {
			$('#editingGiunzioneIziToast #circleBtn').hide();
		}	
	}
	
	/**
	 * Il metodo conferma le feature disegnate/tagliate e da il via al salvataggio chiudendo il iziToast "editingGiunzioneIziToast"
	 */
	confermaFeatureDisegnate(){
		var self = this;
		
		if( self.modified ){																//HO EFFETTUATO DELLE MODIFICHE
			/**1.1 - Recupero la geometria**/
			var src = 'EPSG:3857';
			var dest = 'EPSG:4326';
			var format = new ol.format.WKT();
			var wkts = "MULTILINESTRING(";
			$.each(self.editSourceFeatures.getFeatures(), function(index, value){
				wkts = wkts + "(";
				if (self.properties.typeEditing =='Circle') {
					/** CREO UN OGGETTO CIRCLE PASSANDOGLI IL CENTRO, IL RAGGIO E IL LAYOUT */
					var circle = new ol.geom.Circle(value.getGeometry().getCenter(), 
													value.getGeometry().getRadius(), 
													value.getGeometry().getLayout());

					/** TRASFORMO L'OGGETTO CIRCLE IN UN POLIGONO */
					var polygonFeature = ol.geom.Polygon.fromCircle(circle);
					/** TRASFORMO LE COORDINATE DEL POLIGONO APPENA TRASFORMATO NELLA PROJ 4326 */
					var polygonGeometry = polygonFeature.transform(src, dest);
					/** MI LEGGO OGNI COORDINATE PER POTERLA TRASFORMARE IN MULTILINESTRING */
					$.each(polygonGeometry.getCoordinates(), function(indexCoord, valueCoord) {
						if ($.isArray(valueCoord) && valueCoord.length>2) {
							$.each(valueCoord, function(indexSubCoord, valueSubCoord){
								if (valueSubCoord[0]>100) {
									valueSubCoord = new ol.proj.transform([valueSubCoord[0], valueSubCoord[1]], 'EPSG:3857', 'EPSG:4326');
									wkts = wkts +valueSubCoord[0]+" "+valueSubCoord[1]+",";
								} else {
									wkts = wkts +valueSubCoord[0]+" "+valueSubCoord[1]+",";
								}
							});
						} else {
						if (valueCoord[0]>100) {
							valueCoord = new ol.proj.transform([valueCoord[0], valueCoord[1]], 'EPSG:3857', 'EPSG:4326');
							wkts = wkts +valueCoord[0]+" "+valueCoord[1]+",";
						} else {
							wkts = wkts +value[0]+" "+value[1]+",";
						}
						}
					});

				} else {
					$.each(value.getGeometry().getCoordinates(), function(indexCoord, valueCoord){
						if ($.isArray(valueCoord) && valueCoord.length>2) {
							$.each(valueCoord, function(indexSubCoord, valueSubCoord){
								if (valueSubCoord[0]>100) {
									valueSubCoord = new ol.proj.transform([valueSubCoord[0], valueSubCoord[1]], 'EPSG:3857', 'EPSG:4326');
									wkts = wkts +valueSubCoord[0]+" "+valueSubCoord[1]+",";
									} else {
									wkts = wkts +valueSubCoord[0]+" "+valueSubCoord[1]+",";
									}
							});
						} else {
							if (valueCoord[0]>100) {
								valueCoord = new ol.proj.transform([valueCoord[0], valueCoord[1]], 'EPSG:3857', 'EPSG:4326');
								wkts = wkts +valueCoord[0]+" "+valueCoord[1]+",";
								} else {
								wkts = wkts +value[0]+" "+value[1]+",";
								}
						}
					});
				}
				wkts = wkts.replace(/.$/,"),");
			});
			wkts = wkts.replace(/.$/,")");
			sessionStorage.setItem('wktEditFeatureGeom', wkts);
			
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			$.when(toponomasticaRest.intersecaGiunzione(wkts)).done(function(response) {
				if(response.success) {
					var message = 'Sono stati intersecati i seguenti toponimi stradali: </br>';
					$.each(response.aaData, function(index, value){
						message = message+(value.denominazioneUfficiale!=null?value.denominazioneUfficiale:"")+"</br>";
					});
					appUtil.hideLoader();
					appUtil.confirmOperation(function() {
						appUtil.hideLoader();
						/**1.2 - Disabilito il disegno su mappa**/
						self.toast = document.getElementById('editingGiunzioneIziToast'); // Selector of your toast
						iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
						/**1.3 - Confermo il disegno**/
						self.confermaDisegno = true;
						/**1.4 - Blocco l'editing**/
						self.blockEditing = true;
					}, function() {
						//ANNULLA
					}, {}, message)
				} else {
					appUtil.hideLoader();
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: response.sEcho != null ? response.sEcho : 'Nessun toponimo stradare intersecato',
						animateInside: false,
						position: 'topCenter',
					});
				}
			});
		} else if( self.cuting ){															//HO EFFETTUATO DEI TAGLI
			
			var numCat = sessionStorage.getItem('numCut');
			var numDefCut = sessionStorage.getItem('numDefCut');
			if (numCat==numDefCut) {

				/**2.1 - Blocco l'editing**/
				self.blockEditing = true;
				/**2.2 - Disabilito il disegno su mappa**/
				self.toast = document.getElementById('editingGiunzioneIziToast'); // Selector of your toast
				iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
				/**2.3 - Confermo il disegno**/
				self.confermaDisegno = true;
			} else {
				
				appUtil.showMessageAlertApplication(
						'Per salvare &eacute; necessario definire le nuove entit&agrave;', 
						'Nuovi toponimi stradali non definiti', false, true, false, null, null);
			}
		} else {
			appUtil.showMessageAlertApplication(
					'Per definire un\'entit&agrave; di toponomastica &eacute; necessario modificare la geometria', 
					'Nessuna area tracciata', false, true, false, null, null);
		} 
	}
}