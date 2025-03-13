/** 
 * CONTROLLER PER LA GESTIONE DELLE LOCALITA'
 */
class PaginaLocalitaCtrl extends BaseModaleRicercaCtrl {
	constructor() {
		/** Inserisce come HTML la modale in pagina */
		super('paginaGestioneLocalita','Gestione Localit\u00E0', 'modaleGestioneLocalita');	
		/** Inizializza la modale con i dati delle località */
		this.inizializzaPagina();
	}
	
	/**
	 * Inizializza la modale delle località
	 */
	inizializzaPagina() {
		var self = this;
		/** RECUPERO DEI DATI DI BASE DELLA MODALE LOCALITA' */
		/**************************** COMUNI **************************/
		// var hrefToponomasticaComuniUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaCtrl/getComuniByMessina";
    	// var urbamidResponse = appRest.restUrbamid(hrefToponomasticaComuniUrbamid, "GET", null);
    	self.comuniMessina = [{id:10185,descrizione:"MESSINA"}];
		/**************************** TIPI LOCALITA' **************************/
		var hrefMapTipoLocalitaUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaConsultazioneCtrl/getTipoLocalita";
    	var urbamidResponse = appRest.restUrbamid(hrefMapTipoLocalitaUrbamid, "GET", null);
		self.tipiLocalita = urbamidResponse.aaData;
		
		/**Il seguente metodo viene chiamato nella BaseModaleCtrl per formare la modale**/
		let contextHandlebar = {tipoLocalita: self.tipiLocalita, comuniMessina: self.comuniMessina};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			/** INIZIALIZZAZIONI */
			appUtil.reloadTooltips();
						
	    	self.aggiungiEventiClickTornaIndietroARicerca();
			self.inizializzaBreadcrumb();
	    							
			/* NASCONDO LA TABELLA E IL DETTAGLIO */
			$('#tabellaLocalita').hide();
			$('#dettaglioAnagLocalita').hide();

			/** IMPOSTO BUTTON MODALE */
			self.setButtonModale('ricerca');

			/**IMPOSTAZIONI EDITING**/
    		self.properties = {
    				geometryWKT:null, 
    				typeEditing:'Polygon',
    				pagina:'paginaGestioneLocalita', 
    				idIziToast:'editingLocalitaIziToast', 
    				stato:null,
    				activeCuting: false,
    				activeLayers : []
    		};
    		/************************************************************************************************ AZIONI MODALE RICERCA***************** INIZIO **/
    		/** CERCA **/
	    	$('#' + self.idDialog + ' #ricercaBtnLocalita').click(function() {
				/** VISUALIZZA LA TABELLA */
				$("#tabellaLocalita").show();
				/** INIZIALIZZO DATATABLE */
				self.inizializzaDatatableRicerca();
	    	})
	    	/** AZZERA **/
			$('#' + self.idDialog + ' #azzeraBtnLocalita').click(function() {
	    		self.resettaForm('#ricerca')
	    	})
			/** NUOVO */
	    	$('#' + self.idDialog + ' #nuovoBtnLocalita').click(function() {
	    		/**INIZIALIZZA**/
	    		self.properties.newEntita 	= true;
	    		self.idDettaglio 			= null;
	    		self.modified 				= false;
	    		self.cuting 				= false;
	    		self.confermaDisegno 		= false;
	    		self.blockEditing 			= false;
	    		self.drawLocalita			= true;
	    		/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
	    		self.riduciAdIconaTutteLeModali();
				/**ABILITA DISEGNO SU MAPPA DI UNA NUOVA GEOMETRIA**/
				self.avviaEditingLocalita(null);
	    	})
			/** CHIUDI */
			$('#' + self.idDialog + ' #chiudiBtnLocalita').click(function() {
				/** PULIZIA DEL LAYER DEL DISEGNO */
				drawLayerSource.clear();
				/** CHIUSURA DELLA MODALE */
				$('#' + self.idDialog).dialog('close');
			})
			/************************************************************************************************ AZIONI MODALE RICERCA ***************** FINE **/
	    	/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** INIZIO **/
			$('#' + self.idDialog + ' #aggiornaBtnLocalita').click(function(event) {
				event.stopImmediatePropagation();
				
				if($('#descrizioneLocalitaM').val() == '' || $('#comuneLocalitaM').val() == '') {
										
					iziToast.error({
						title: 'Errore',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Devono essere compilati tutti i campi obbligatori',
						animateInside: false,
						position: 'topCenter',
					});
											
				} else {
					
					/** RECUPERO IL VECCHIO DETTAGLIO */
					var dettaglio = self.recuperaDettaglio(self.idDettaglio);
					/** CREO L'OGGETTO DA SALVARE */
					var localitaMod = {id: self.idDettaglio, 
									 	descrizione:  $('#' + self.idDialog + ' #descrizioneLocalitaM').val() != '' ? $('#' + self.idDialog + ' #descrizioneLocalitaM').val() : null, 
									 	comune: {idComune: $('#' + self.idDialog + ' #comuneLocalitaM option:selected').val()},
									 	frazione: $('#' + self.idDialog + ' #frazioneLocalitaM').val() != '' ? $('#' + self.idDialog + ' #frazioneLocalitaM').val() : null,
									 	tipo: {id: $('#' + self.idDialog + ' #tipoLocalitaM option:selected').val()},
									 	note: $('#' + self.idDialog + ' #noteLocalita').val() != '' ? $('#' + self.idDialog + ' #noteLocalita').val() : null,
									 	stato: dettaglio.stato,
									 	geom: sessionStorage.getItem('wktEditFeatureGeom')!=null? sessionStorage.getItem('wktEditFeatureGeom') : dettaglio.geom
					};
					/**SALVATAGGIO DELLA MODIFICA**/
					self.modificaLocalita(localitaMod);	
					
				}
		
			});
	    	/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** FINE **/
			/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* INIZIO **/
			$('#' + self.idDialog + ' #salvaBtnLocalita').click(function(event) {
				event.stopImmediatePropagation();

				if($('#descrizioneLocalitaM').val() == '' || $('#comuneLocalitaM').val() == '') {
										
					iziToast.error({
						title: 'Errore',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Devono essere compilati tutti i campi obbligatori',
						animateInside: false,
						position: 'topCenter',
					});
				} else {
					
					var localitaIns = {id: null, 
										descrizione:  $('#' + self.idDialog + ' #descrizioneLocalitaM').val() != '' ? $('#' + self.idDialog + ' #descrizioneLocalitaM').val() : null, 
										comune: {idComune: $('#' + self.idDialog + ' #comuneLocalitaM option:selected').val()},
										frazione: $('#' + self.idDialog + ' #frazioneLocalitaM').val() != '' ? $('#' + self.idDialog + ' #frazioneLocalitaM').val() : null,
										tipo: {id: $('#' + self.idDialog + ' #tipoLocalitaM option:selected').val()},
										note: $('#' + self.idDialog + ' #noteLocalita').val() != '' ? $('#' + self.idDialog + ' #noteLocalita').val() : null,
										geom: sessionStorage.getItem('wktEditFeatureGeom')
					};
					/** SALVATAGGIO NUOVA ENTITA' */
		 			self.inserisciLocalita(localitaIns);
				}
			})
			/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* FINE **/
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** INIZIO **/
			$('#' + self.idDialog + ' #indietroBtn').click(function() {
				/** INIZIALIZZAZIONE */
				$('#ricercaAnagLocalita').show();
				$('#dettaglioAnagLocalita').hide();
				/** PULIZIA DELLE VARIABILI USATE NEL DETTAGLIO */
				self.idDettaglio = null;
				/** BUTTON MODALE */
				self.setButtonModale('ricerca');
				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('ricerca')
			})
			/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** FINE **/
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaGestioneLocalita');
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
	isNumeroInt(numero) {
		if(!isNaN(parseFloat(numero)) && isFinite(numero)) {
			return (numero % 1 == 0)
		}
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
		/**INIZIALIZZAZIONI**/
		var self = this;
		/**RECUPERO PARAMETRI DI RICERCA**/
		var descrizione = $('#descrizioneLocalita').val();
		var frazione = $('#frazioneLocalita').val();
		/**INIZIALIZZO TABELLA**/
		$("#tabellaLocalita").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 1, 2, 3, 4 ],
				postfixButtons: [ { extend: 'colvisRestore',
									text: '<hr size="1" style="margin-top: 6px">' +
										  '<button class="btn btn-primary" style="position: relative; left: 6%;">Visualizza tutto</button>',
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
				//url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaLocalita',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/ricercaLocalita',
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
								  frazione: frazione.trim(),
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
	        	{targets : 0, name: 'descrizione', render: function(_d, _t, r) {return r.descrizione;}, orderable : true},
	        	{targets : 1, name: 'comune', render: function(_d, _t, r) {return r.comune != null ? r.comune.denominazione : '';}, orderable : true},
	        	{targets : 2, name: 'frazione', render: function(_d, _t, r) {return r.frazione;}, orderable : true},
				{targets : 3, name: 'tipo', render: function(_d, _t, r) {return r.tipo != null ? r.tipo.descrizione : '';}, width: '20%', orderable : true},
				{targets : 4, name: 'stato', render: function(_d, _t, r) {return r.stato;}, orderable : true},
	        	{targets : 5, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabella', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
		   },
	       drawCallback: function( settings ) {
	    	    var localita = settings.json.data
	        	sessionStorage.setItem('ricercaLocalita', JSON.stringify(localita));
	        	self.aggiungiEventiAiPulsantiLocalita();
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio della localita. Il salvataggio di una modifica non cambierà lo stato dell'entità ma soltanto gli attributi
	 * - elimina, aprirà un box di conferma con la richiesta di eliminazione della localita
	 *     1. si procede con eliminazione nella tabella u_topo_localita
	 * 	   2. l'operazione verrà annullata.
	 * - localizza, viene richiamato il metodo getGeometry che ridurrà la modale mostrando su mappa la geometria
	 * 
	 **/
	aggiungiEventiAiPulsantiLocalita() {
		var self = this;
		$("#tabellaLocalita button.modifica-btn").off('click').on('click', function(_event){
			self.properties.newEntita 	= true;
    		self.modified 				= false;
    		self.cuting 				= false;
    		self.blockEditing 			= false;
			/**VISUALIZZA DETTAGLIO**/
			self.visualizzaDettaglio($(this).data('id'));
			/** INIZIALIZZA LA TABELLA DEI DOCUMENTI */
			self.inizializzaDatatableDocumenti($(this).data('id'), 3);
			/** INIZIALIZZA LA TABELLA DEGLI ACCESSI */
			self.inizializzaDatatableAccessi($(this).data('id'));
        });
		$("#tabellaLocalita button.elimina-btn").off('click').on('click', function(_event){
			/** ELIMINA LOCALITA */
			self.eliminaLocalita($(this).data('id'));
        });
		$("#tabellaLocalita button.localizza-btn").off('click').on('click', function(_event) {
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
	 * Il metodo visualizza il dettaglio della localita con id passato in input. La visualizzazione del dettaglio prevede una modale già aperta
	 * @param id, identificativo dell'entità Localita 
	 */
	visualizzaDettaglio(id) {
		/** INIZIALIZZAZIONI */
		var self = this;
		/** VISUALIZZO IL LOADER */
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		if(id != null) {																							/**GESTIONE DI UN DETTAGLIO**/
		
			/** RECUPERO IL DETTAGLIO */
			var dettaglio 		= self.recuperaDettaglio(id);				//Recupero il dettaglio della località
			self.idDettaglio 	= id;										//Salvo l'identificativo del dettaglio
			var localitaMod		= {};										//Creo l'oggetto localitaMod utile per la modifica all'entità

			/** IMPOSTAZIONI PAGINA */
			$('#' + self.idDialog + ' #ricercaAnagLocalita').hide();
			$('#' + self.idDialog + ' #dettaglioAnagLocalita').show();
			$('#' + self.idDialog + ' #liDetAnagraficaLocalita').show();
			$('#' + self.idDialog + ' #liDetDocumentoLocalita').show();
			$('#' + self.idDialog + ' #liDetAccessoLocalita').show();
			/** BUTTON MODALE */
			self.setButtonModale('dettaglio');
			/** OGGETTO LOCALITA' PER LA VISUALIZZAZIONE */
			var detLocalita = { id: id,
								comuni: self.comuniMessina,
								comune: dettaglio.comune != null ? dettaglio.comune.idComune : '',
								descrizione: dettaglio.descrizione != null ? dettaglio.descrizione : '',
								frazione: dettaglio.frazione != null ? dettaglio.frazione : '',
								tipi: self.tipiLocalita,
								tipo: dettaglio.tipo != null ? dettaglio.tipo.id : '',
								note: dettaglio.note != null ? dettaglio.note : '',
								geom: dettaglio.geom != null ? true : false 
				};
				/** COMPILO IL TEMPLATE CON L'OGGETTO detLocalita */
				let html = compilaTemplate('modaleDettaglioLocalita', detLocalita);
				$('#dettaglio').html(html);
				
				/** AGGIORNO IL BREADCRUMB */
				if (!self.modified) self.aggiornaBreadcrumb('dettaglio', detLocalita.descrizione);
				
				/** INIZIALIZZO LE SELECT CON GLI ID DEL TIPI LOCALITA E I COMUNI */
				$('#' + self.idDialog + ' #comuneLocalitaM').val(dettaglio.comune == null ? '' : detLocalita.comune);
				$('#' + self.idDialog + ' #tipoLocalitaM').val(dettaglio.tipo == null ? '' : detLocalita.tipo);
				
				/**IMPOSTAZIONI AVANZATE EDITING**/
				(self.properties).newEntita = false;
				(self.properties).geometryWKT = dettaglio.geom == null ? null : dettaglio.geom;
				(self.properties).stato = dettaglio.stato == null ? null : dettaglio.stato;
				/*************************************************************************************************************************************/
				/** VISUALIZZA LA GEOMTRIA */
				$('#' + self.idDialog + ' .localizza-geom-localita-btn').click(function(_event) {
					/** FACCIO VISUALIZZARE LA GEOMETRIA SU MAPPA */
					self.getGeometry(dettaglio.geom);
				});
				if (self.blockEditing)
					$("#"+self.idDialog+" .editing-geom-localita-btn").hide();
				/** EDITING  LOCALITA */
				$('#' + self.idDialog + ' .editing-geom-localita-btn').click(function(_event) {
					self.properties.newEntita 	= false;
					self.confermaDisegno 		= false;
					/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
					self.riduciAdIconaTutteLeModali();
					/**ABILITA DISEGNO SU MAPPA DI UNA GEOMETRIA PRESENTE SOLO SU INTERFACCIA**/
					var geomWKT = dettaglio != undefined ? dettaglio.geom : null;
					self.avviaEditingLocalita(geomWKT);
				})
				/** TAB DOCUMENTI */
				$('#' + self.idDialog + ' #documentoLocalitaBtn').click(function() {
					self.uploadDocumento(self.idDettaglio, 3);
				})
				/** TAB ACCESSI */
				$('#' + self.idDialog + ' #accessoLocalitaBtn').click(function() {
					self.anagraficaAccesso();
				})
			
		} else {																							/**GESTIONE DI UN INSERIMENTO**/

			/** IMPOSTAZIONI PAGINA */
			if($('#' + self.idDialog + ' ul.breadcrumb li:last-child').text().indexOf('Località') != -1) {
				self.aggiornaBreadcrumb('nuovo');
			} else {
				$('#' + self.idDialog + ' ul.breadcrumb li:last-child').remove();
				self.aggiornaBreadcrumb('nuovo');
			}
			/** IMPOSTAZIONI PAGINA */
			$('#' + self.idDialog + ' #ricercaAnagLocalita').hide();
			$('#' + self.idDialog + ' #dettaglioAnagLocalita').show();
			$('#' + self.idDialog + ' #liDetAnagraficaLocalita').hide();
			$('#' + self.idDialog + ' #liDetDocumentoLocalita').hide();
			$('#' + self.idDialog + ' #liDetAccessoLocalita').hide();
	
			/**GESTIONE DEL DETTAGLIO DA VISUALIZZARE**/
			var detLocalita = {comuni: self.comuniMessina, tipi: self.tipiLocalita};
			var htmlDet = compilaTemplate('modaleDettaglioLocalita', detLocalita);
			$("#dettaglio").html(htmlDet);
			
			/** BUTTON MODALE */
			self.setButtonModale('inserimento');
			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita 	= true;
			(self.properties).geometryWKT 	= null;
			(self.properties).stato 		= null;
    		/************************************************************************************************ AZIONI NUOVO ************** INIZIO **/
    		/** EDITING DEL NUOVO ACCESSO **/
			$('#' + self.idDialog + ' .editing-geom-localita-btn').click(function() {
    			if ( sessionStorage.getItem('wktEditFeatureGeom') ) {
    				self.newEntita = true;
					/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
    				var format = new ol.format.WKT();
    				var features = format.readGeometry( sessionStorage.getItem('wktEditFeatureGeom') );
					/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
					self.riduciAdIconaTutteLeModali();
					/**ABILITA DISEGNO SU MAPPA**/
					var geomWKT = dettaglio != undefined ? dettaglio.geom : 
				        (sessionStorage.getItem('wktEditFeatureGeom')!=undefined ? sessionStorage.getItem('wktEditFeatureGeom') : null);
					self.avviaEditingLocalita(geomWKT);
    			}
			});
			/************************************************************************************************ AZIONI NUOVO ************** FINE **/
		}
		appUtil.hideLoader()
	}
	
	
	/**
	 * Metodo per il recupero del dettaglio di una localita chilometrico dato l'id
	 * @param id, identificativo del cippo
	 */
	recuperaDettaglio(id) {
		
		let risultato = JSON.parse(sessionStorage.getItem('ricercaLocalita'));
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
	 * Il metodo inizializza la tabella dei documenti legata con idLocalita e tipo.
	 * @param idLocalita è l'indentficativo del 
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
	inizializzaDatatableDocumenti(idLocalita, tipo) {
		var self = this;
		$("#tabellaDocumentiLocalita").DataTable({
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
								  idRisorsa: idLocalita,
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: idLocalita, nomeFile: r.nomeDocumento});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {
	        	self.aggiungiEventiAiPulsantiDocumentiLocalita();
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
	aggiungiEventiAiPulsantiDocumentiLocalita() {
		var self = this;
		$("#tabellaDocumentiLocalita button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('nome'), $(this).data('id'), 3);
        });
		
		$("#tabellaDocumentiLocalita button.download-documento-btn").off('click').on('click', function(_event){
			self.downloadDocumento($(this).data('nome'), $(this).data('id'), 3);
        });
	}

	/**
	 * Il metodo apre una modale con un form di upload del file.
	 * @param idLocalita indentifica il cippo
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
	uploadDocumento(idLocalita, tipo) {
		var self = this;
    	openStaticDetailViaHandlebars('uploadDocumentoLocalita', 'modaleUploadDocumentiLocalita', {}, 'Upload', function(){
    		
    		appUtil.reloadTooltips();
    		
			$('#uploadDocumentoBtn').click(function() {
				self.inserisciDocumento(idLocalita, tipo);
			})
    		
    	}, {width: 350, height: 155, resizable: false, modale: true, closable: true});
		
	}

	/**
	 * Il metodo carica sul server il documento legandolo all'id identificativo della localita e al tipo di risorsa.
	 * @param idLocalita indentifica la località
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
	inserisciDocumento(idLocalita, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var fd = new FormData($('#uploadForm')[0]);
		var files = $('#file')[0].files[0];
        fd.append('file',files);
		
		setTimeout(function(){
			$.when(toponomasticaRest.uploadFile(fd, idLocalita, tipo)).done(function (response) {
				appUtil.hideLoader()
				if(response.success) {
					appUtil.hideLoader();
		        	$('#uploadDocumentoLocalita').dialog('close');
		        	self.ricaricaDataTable('tabellaDocumentiLocalita')
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
	 * @param idLocalita è l'id della località
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
	downloadDocumento(nomeFile, idLocalita, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();
			toponomasticaRest.downloadFile(nomeFile, idLocalita, tipo);
		})
	}
	
	/**
	 * Il metodo elimina dal server il documento.
	 * @param nomeFile il nome del file
	 * @param idLocalita è l'id della localita
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
	eliminaDocumento(nomeFile, idLocalita, tipo) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaDocumento(idLocalita, tipo, nomeFile)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {
						self.ricaricaDataTable('tabellaDocumentiLocalita');
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
	
	
	inizializzaDatatableAccessi(idLocalita) {
		var self = this;
		$("#tabellaAccessiLocalita").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 0, 1, 2, 3, 4, 5, 6 ],
				postfixButtons: [ { extend: 'colvisRestore',
									text: '<hr size="1" style="margin-top: 6px">' +
										  '<button class="btn btn-primary" style="position: relative; left: 6%;">Visualizza tutto</button>',
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
				//url : appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaAccesso',
				url : appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/ricercaAccesso',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var colonnaSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							colonnaSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {localita: idLocalita,
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: colonnaSort, dir: json.order[0].dir } };
					
					return JSON.stringify(filtri);
				}  
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, name: 'toponimo', render: function(_d, _t, r) {return r.toponimo != null ?  r.toponimo.denominazioneUfficiale : '';}, orderable : true},
	        	{targets : 1, name: 'numero', render: function(_d, _t, r) {return r.numero;}, orderable : true},
	        	{targets : 2, name: 'esponente', render: function(_d, _t, r) {return r.esponente;}, orderable : true},
	        	{targets : 3, name: 'tipo', render: function(_d, _t, r) {return r.tipo != null ? r.tipo.descrizione : '';}, orderable : true},
	        	{targets : 4, name: 'passoCarrabile', render: function(_d, _t, r) {return r.passoCarrabile ? 'SI' : 'NO';}, orderable : true},
	        	{targets : 5, name: 'principale', render: function(_d, _t, r) {return r.principale ? 'SI' : 'NO';}, orderable : true},
	        	{targets : 6, name: 'note', render: function(_d, _t, r) {return r.note;}, orderable: true},
	        	{targets : 7, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaAccessi', {id: r.id, idEntity: r.localita.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	sessionStorage.setItem('ricercaAccessi', JSON.stringify(settings.json.data));
	        	self.aggiungiEventiAiPulsantiAccessiLocalita();
	        }
		});
	}
	
	aggiungiEventiAiPulsantiAccessiLocalita() {
		var self = this;
		$("#tabellaAccessiLocalita button.elimina-accesso-btn").off('click').on('click', function(_event){
			self.eliminaAccesso($(this).data('id'), $(this).data('entity'));
        });
		
		$("#tabellaAccessiLocalita button.anagrafica-accesso-btn").off('click').on('click', function(_event){
			self.anagraficaAccesso($(this).data('id'));
		});
		
		$('#tabellaAccessiLocalita button.localizza-accesso-btn').off('click').on('click', function(_event){
			self.getGeometry($(this).data('geometry'))
		});
	}
	
	anagraficaAccesso(id) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

		setTimeout(function() {
			
			appUtil.hideLoader();
			
			/**Recupero dei dati di base della modale anagrafica accesso**/
			/**************************** TIPO ACCESSO **************************/
			//var hrefToponomasticaTipoAccessoToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getTipoAccesso';
			var hrefToponomasticaTipoAccessoToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getTipoAccesso';
			var UrbamidResponseTipoAccessi = appRest.restUrbamid(hrefToponomasticaTipoAccessoToponimoUrbamid, "GET", null);
			self.tipoAccessi = UrbamidResponseTipoAccessi.aaData;
		
			openStaticDetailViaHandlebars('anagraficaAccesso', 'modaleAnagraficaAccessiLocalita', {tipoAccesso: self.tipoAccessi}, 'Anagrafica Accessi', function(){
								
				$('#anagraficaAccesso #chiudiBtn').on('click', function(){
					drawLayerSource.clear();
					$('#anagraficaAccesso').dialog('close');
				});

	    	
				if(id != null) {
					
					/** NASCONDO I PULSANTI */
					$('#anagraficaAccesso .absPulsante').hide();

					//RECUPERO DELLE INFORMAZIONI
					let accessi = JSON.parse(sessionStorage.getItem('ricercaAccessi'));
					let dettaglioAccessi;
					for(var i=0; i<accessi.length; i++){
						if(accessi[i].id === id){
							dettaglioAccessi = accessi[i];
							break;
						}
					}
	    		
					/** CREO L'OGGETTO DA FAR VISUALIZZARE */
					var detAccessi = {toponimo: dettaglioAccessi.toponimo == null ? '' : dettaglioAccessi.toponimo.denominazioneUfficiale,
									  numero: dettaglioAccessi.numero == null ? '' : dettaglioAccessi.numero,
									  esponente: dettaglioAccessi.esponente == null ? '' : dettaglioAccessi.esponente,
									  hiddenLocalita: 'hidden',
									  tipoAccesso: self.tipoAccessi,
									  tipoAccessoID: dettaglioAccessi.tipo == null ? '' : dettaglioAccessi.tipo.id,
									  passoCarrabile: dettaglioAccessi.passoCarrabile ? 'checked' : '',
									  principale: dettaglioAccessi.principale ? 'checked' : '',
									  metodo: dettaglioAccessi.metodo == null ? '' : dettaglioAccessi.metodo,
									  note: dettaglioAccessi.note == null ? '' : dettaglioAccessi.note,
									  readonly: 'readonly',
									  disabled: 'disabled',
									  hidden: 'hidden'}

					let html = compilaTemplate('modaleDettaglioAccesso', detAccessi);
					$('#anagraficaAccesso #dettaglio').html(html);

					/** INIZIALIZZO LE SELECT CON GLI ID DEL TIPO ACCESSO E DELLA LOCALITA' */
					$('#anagraficaAccesso #tipoAccessoM').val(dettaglioAccessi.tipo == null ? '' : dettaglioAccessi.tipo.id);

				} else {
	    		
					/** NASCONDO I PULSANTI */
					$('#anagraficaAccesso .absPulsante').show();
	    		
					/** CREO L'OGGETTO DA FAR VISUALIZZARE */
					var detAccessi = {localita: self.localita, tipoAccesso: self.tipoAccessi, hidden: 'hidden', hiddenLocalita: 'hidden'}
					let html = compilaTemplate('modaleDettaglioAccesso', detAccessi);
					$('#anagraficaAccesso #dettaglio').html(html);
				
					/** AUTOCOMPLETE */
					//appUtil.activeAutocompleteOnFiled('accessoToponimo', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
					appUtil.activeAutocompleteOnFiled('accessoToponimo', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')

					$('#salvaAccessiBtn').click(function() {

						if($('#accessoNumCivico').val() != '' && !self.isNumeroInt($('#accessoNumCivico').val())) {
							
							appUtil.hideLoader();
							iziToast.error({
				    			title: 'Attenzione',
				    			theme: 'dark',
				    			icon:'fa fa-times',
				    			message: 'Il campo Numero dev\'essere un numero intero!',
				    			animateInside: false,
				    			position: 'topCenter',
				    		});
							
						} else {
						
							//CREO L'OGGETTO DA MANDARE AL SERVER CON ID NULL, UTILE PER CREARE UNA NUOVA ENTITA'
							var accesso = { id: null,
											toponimo: { id: $('#accessoToponimoValue').val() },
											numero: $('#accessoNumCivico').val(),
											esponente: $('#esponenteCivico').val(),
											localita: { id: self.idDettaglio },
											tipo: { id: $('#tipoAccessoM option:selected').val() },
											passoCarrabile: $('#accessoPassoCarrabile').is(":checked"),
											principale: $('#accessoPrincipale').is(":checked"),
											metodo: $('#accessoMetodo').val(),
											geom: null,
											note: $('#accessoNote').val() };
							
							self.inserisciAccesso(accesso);
							
						}
					
					})
	    		
				}

				appUtil.reloadTooltips(); 							//INIZIALIZZO I TOOLTIPS
    		
			}, {width: 700, height: 370, resizable: false, modale: true, closable: true});
		}, 600)	
		
	}
	
	inserisciAccesso(accesso) {
		var self = this;
		setTimeout(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			$.when(toponomasticaRest.inserisciAccesso(accesso)).done(function (response) {
				if(response.success) {
					appUtil.hideLoader();
		        	$('#anagraficaAccesso').dialog('close');
		        	self.ricaricaDataTable('tabellaAccessiLocalita')
					iziToast.show({
		    			title: 'OK',
		    			theme: 'dark',
		    			icon:'fa fa-check',
		    			message: 'Accesso inserito con successo!',
		    			animateInside: false,
		    			position: 'topCenter',
		    		});
				} else {
					appUtil.hideLoader();
					iziToast.error({
		    			title: 'Attenzione',
		    			theme: 'dark',
		    			icon:'fa fa-times',
		    			message: 'Errore nell\'inserimento dell\'accesso!',
		    			animateInside: false,
		    			position: 'topCenter',
		    		});
				}
			})
		})	
	}

	eliminaAccesso(idAccesso, idLocalita) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaAccessoByLocalita(idAccesso, idLocalita)).done(function(response) {
					if(response.success) {
						appUtil.hideLoader();
						self.ricaricaDataTable('tabellaAccessiLocalita');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Accesso eliminato con successo!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {
						appUtil.hideLoader();
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Errore nella cancellazione dell\'accesso!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
				})
			})
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione dell\'acesso?');
	}
	
	/**
	 * Il metodo salva la localita e riporta l'utente sulla pagina di ricerca. 
	 * @param localita, è un oggetto contentente le informazioni di una localita da inserire
	 */
	inserisciLocalita(localita) {
		var self = this;
		$.when(toponomasticaRest.inserisciLocalita(localita)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
				$("#ricercaAnagLocalita").show();							//Visualizzo la ricerca
				$("#dettaglioAnagLocalita").hide();							//Nascondo il dettaglio
				self.aggiornaBreadcrumb('ricerca');							//Aggiorno il Breadcrumb
				self.setButtonModale('ricerca');	
				self.ricaricaDataTable('tabellaLocalita');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Localit&agrave;:' + localita.descrizione +  ' aggiunta con successo',
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
		    		message: 'Errore nell\'inserimento della localit&agrave; ' + localita.descrizione,
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		})
	}
	
	/**
	 * Il metodo salva la modifica di una localita e riporta l'utente sulla pagina di ricerca. 
	 * @param localitaM, è un oggetto contentente le informazioni di una localita da modificare
	 */
	modificaLocalita(localitaM) {
		var self = this;
		$.when(toponomasticaRest.inserisciLocalita(localitaM)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
				$("#ricercaAnagLocalita").show();							//Visualizzo la ricerca
				$("#dettaglioAnagLocalita").hide();							//Nascondo il dettaglio
				self.aggiornaBreadcrumb('ricerca');							//Aggiorno il Breadcrumb
				self.setButtonModale('ricerca');	
				self.ricaricaDataTable('tabellaLocalita');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Localit&agrave;: ' + localitaM.descrizione +  ' modificata con successo',
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
		    		message: 'Errore nella modifica della localit&agrave;: ' + localitaM.descrizione,
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		})
	}
	
	/**
	 * Il metodo elimina una localià chiedendo conferma. In seguito all'operazione l'utente viene riportato sulla pagina di ricerca. 
	 * @param id, identificativo della localita
	 */
	eliminaLocalita(id) {
		var self = this;
		appUtil.confirmOperation(function() {
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaLocalita(id)).done(function (response) {
					if(response.success) {
		    			self.ricaricaDataTable('tabellaLocalita');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Localit&agrave; eliminata con successo',
			    			animateInside: false,
			    			position: 'topCenter',
						});
						appUtil.hideLoader();
					} else {
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: response.sEcho != null ? response.sEcho : 'Accesso associato alla localit&agrave;',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
				})
			})
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione della localit&agrave;?');
	}
	
	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* INIZIO ********/
	/****************************************************************************************************************************************************************/
	/**
	 * Avvio dell'editing
	 */
	avviaEditingLocalita(geometry) {
		/**1 - inizializzo**/
		var self = this;
		drawLayerSource.clear();
		/**2 - avvia la gestione dell'editing**/
		manageEditing(self, self.properties);
	}
	
	/**
	 * Il metodo conferma le feature disegnate/tagliate e da il via al salvataggio chiudendo il iziToast "editingAccessoIziToast"
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
			if ( newWkt.indexOf("POLYGON")!=-1 ) 
			    sessionStorage.setItem('wktEditFeatureGeom', newWkt);
			/**1.2 - Disabilito il disegno su mappa**/
			self.toast = document.getElementById('editingLocalitaIziToast'); // Selector of your toast
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
				self.toast = document.getElementById('editingLocalitaIziToast'); // Selector of your toast
				iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
				/**1.4 - Confermo il disegno**/
				self.confermaDisegno = true;
				/**2.1 - Blocco l'editing**/
				self.blockEditing = true;
			} else {
				appUtil.showMessageAlertApplication(
						'Per salvare &eacute; necessario definire le due nuove entit&agrave;', 
						'Nuova localit&agrave; non definita', false, true, false, null, null);
			}
		} else if ( self.drawLocalita ) {
			console.log("asd");
			/**1.1 - Recupero la geometria**/
			sessionStorage.setItem('wktEditFeatureGeom', null);
			/**1.2 - Disabilito il disegno su mappa**/
			self.toast = document.getElementById('editingLocalitaIziToast'); // Selector of your toast
			iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
			/**1.3 - Confermo il disegno**/
			self.confermaDisegno = true;
			/**1.4 - Confermo il disegno**/
			self.blockEditing = true;
		} else {
			
			appUtil.showMessageAlertApplication(
					'Per definire un\'entit&agrave; di toponomastica &eacute; necessario modificare la geometria', 
					'Nessuna area tracciata', false, true, false, null, null);
		} 
	}
}