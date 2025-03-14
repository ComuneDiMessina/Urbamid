/** 
 * CONTROLLER PER LA GESTIONE DEI PERCORSI
 */
class PaginaGestionePercorsiCtrl extends BaseModaleRicercaCtrl {
	constructor( ) {
		/** Inserisce come HTML la modale in pagina */
		super('paginaGestionePercorsi','Inserisci\Modifica Percorso', 'modaleGestionePercorsi');
		/** Inizializza la modale con i dati dei percorsi */
		this.inizializzaPagina();
	}
	
	/**
	 * Inizializza la modale dei percorsi
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

			/* NASCONDO LA TABELLA E IL DETTAGLIO*/
			$('#tabellaPercorso').hide();
			$('#dettaglioAnagPercorso').hide();
			
			/** IMPOSTAZIONI BUTTON MODALE */
			self.setButtonModale('ricerca');
			
			/** =================== AZIONI MODALE RICERCA ======================== */
			/** CERCA */
	    	$('#' + self.idDialog + ' #ricercaBtnPercorso').click(function() {
				/** VISUALIZZA LA TABELLA */
				$("#tabellaPercorso").show();

				/** VIENE UTILIZZATO NELLA PER LA SCELTA TRA TOPONIMO ED ESTESA AMMINISTRATIVA */
				if($('#estesaAmministrativaPercorso').prop('checked')) {
					self.inizializzaDatatableRicerca(true);
				} else if($('#toponimoStradalePercorso').prop('checked')) {
					self.inizializzaDatatableRicerca(false);
				} else {
					self.inizializzaDatatableRicerca('');
				}

			})

			$('#' + self.idDialog + ' #azzeraBtnPercorso').click(function() {
	    		self.resettaForm('#ricerca')
	    	})

			/** NUOVO */
	    	$('#' + self.idDialog + ' #nuovoBtnPercorso').click(function() {
				/** INIZIALIZZA */
				self.newEntita = true;
				/** RIDUCE LA MODALE */
				self.riduciAdIconaTutteLeModali();
				/** ABILITA IL DISEGNO SU MAPPA */
				self.visualizzaDettaglio();
				/** @TODO @AP EDITING */
			});

			/** CHIUDI */
			$('#' + self.idDialog + ' #chiudiBtnPercorso').click(function() {
				/** PULIZIA DEL LAYER DEL DISEGNO */
				drawLayerSource.clear();
				/** CHIUSURA DELLA MODALE */
				$('#' + self.idDialog).dialog('close');
			})
			
			/** ================= AZIONI MODALE DETTAGLIO ================= INIZIO */
			$('#aggiornaBtnPercorso').click(function(event) {
				event.stopImmediatePropagation();
				/** VERIFICHE PER IL SALVATAGGIO DELLA MODIFICA */
				if($('#' + self.idDialog + ' #estesaAmministrativaM').val() != '' && $('#' + self.idDialog + ' #toponimoStradaleM').val() != '') {

					//RESETTO I CAMPI DI ESTESA E TOPONIMO
					$('#' + self.idDialog + ' #estesaAmministrativaMValue').val('');
					$('#' + self.idDialog + ' #estesaAmministrativaM').val('');
					$('#' + self.idDialog + ' #toponimoStradaleMValue').val('');
					$('#' + self.idDialog + ' #toponimoStradaleM').val('');

					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Non puoi inserire per un percorso un\'estesa amministrativa insieme a un toponimo stradale!',
						animateInside: false,
						position: 'topCenter',
					});
			
				} else {
					/** RECUPERA IL VECCHIO DETTAGLIO */
					var dettaglio = self.recuperaDettaglio(self.idDettaglio);
					/** CREO L'OGGETO DA SALVARE */
					var percorsoMod = {id: self.idDettaglio, 
								   	   sigla: $('#' + self.idDialog + ' #siglaPercorsoM').val(), 
								   	   descrizione: $('#' + self.idDialog + ' #descrizionePercorsoM').val(), 
								   	   estesaAmministrativa: { id: $( '#estesaAmministrativaM').val() != '' && $('#estesaAmministrativaMValue').val() == '' ? dettaglio.estesaAmministrativa.id :  $('#' + self.idDialog + ' #estesaAmministrativaMValue').val() }, 
								   	   toponimo: { id: $( '#toponimoStradaleM').val() != '' && $('#toponimoStradaleMValue').val() == '' ? dettaglio.toponimo.id :  $('#' + self.idDialog + ' #toponimoStradaleMValue').val() }, 
								   	   latoPercorso: $('#' + self.idDialog + ' #latoM').val(),
									   note: $('#' + self.idDialog + ' #notePercorso').val() };
										  
					 /** SALVATAGGIO DELLA MODIFICA */
	 				self.modificaPercorso(percorsoMod);
				}
			});
			/** ================= AZIONI MODALE DETTAGLIO ================= FINE */
			/** ================= AZIONI MODALE INSERIMENTO ================= INIZIO */
			$('#salvaBtnPercorso').click(function(event) {
				event.stopImmediatePropagation();
				/** VERIFICHE PER IL SALVATAGGIO DELLA MODIFICA */
				if($('#' + self.idDialog + '#estesaAmministrativaM').val() != '' && $('#' + self.idDialog + ' #toponimoStradaleM').val() != '') {

					//RESETTO I CAMPI DI ESTESA E TOPONIMO
					$('#estesaAmministrativaMValue').val('');
					$('#estesaAmministrativaM').val('');
					$('#toponimoStradaleMValue').val('');
					$('#toponimoStradaleM').val('');

					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Non puoi inserire per un percorso un\'estesa amministrativa insieme a un toponimo stradale!',
						animateInside: false,
						position: 'topCenter',
					});
			
				} else {
					var percorso = {id: null, 
									sigla: $('#' + self.idDialog + ' #siglaPercorsoM').val(), 
									descrizione: $('#' + self.idDialog + ' #descrizionePercorsoM').val(), 
									estesaAmministrativa: {id: $('#' + self.idDialog + ' #estesaAmministrativaMValue').val() }, 
									toponimo: { id: $('#' + self.idDialog + ' #toponimoStradaleMValue').val() }, 
									latoPercorso: $('#' + self.idDialog + ' #latoM').val(),
									stato: 'PUBBLICATO',
									note: $('#' + self.idDialog + ' #notePercorso').val() };

					/** SALVATAGGIO NUOVA ENTITA' */
					self.inserisciPercorso(percorso);

				}
			})
			/** ================= AZIONI MODALE DETTAGLIO ================= FINE */
	    	/** ================= AZIONI MODALE DETTAGLIO-INSERIMENTO ================= INIZIO */
	    	$('#' + self.idDialog + ' #indietroBtn').click(function() {
				/** INIZIALIZZAZIONE */
				$('#ricercaAnagPercorsi').show();
				$('#dettaglioAnagPercorso').hide();
				/** PULIZIA DELLE VARIABILI USATE NEL DETTAGLIO */
				self.idDettaglio = null;
				/** BUTTON MODALE */
				self.setButtonModale('ricerca');
				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('ricerca');
			})
			/** ================= AZIONI MODALE DETTAGLIO ================= FINE */
	    	
		}, {closable: true});
	}

	/**
	 * Il metodo setta i bottoni nella modale per tipo di azione. 
	 * Le azioni possibili sono: "ricerca", "inserimento" e "dettaglio"
	 */
	setButtonModale (type){
		switch (type) { 
			case 'ricerca': 
				$("#pulsante-ricerca").show();
				$("#pulsante-inserimento").hide();
				$("#pulsante-dettaglio").hide();
				break;
			case 'inserimento': 
				$("#pulsante-ricerca").hide();
				$("#pulsante-inserimento").show();
				$("#pulsante-dettaglio").hide();
				break;
			case 'dettaglio': 
				$("#pulsante-ricerca").hide();
				$("#pulsante-inserimento").hide();
				$("#pulsante-dettaglio").show();
				break;		
			default:
				$("#pulsante-ricerca").show();
				$("#pulsante-inserimento").hide();
				$("#pulsante-dettaglio").hide();
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
			} else if (type == 'text' || tag == 'textarea') {
                this.value = ''
            }
		})
	}

	/** 
	 * Il metodo ricarica DataTable
	 * @param idTable, identificativo della tabella
	 */
	ricaricaDataTable(idTable) {
		var table = $('#' + idTable).DataTable()
		table.ajax.reload();
	}
	
	/**
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicerca(tipo) {
		/** INIZIALIZZAZIONI */
		var self = this;
		/** RECUPERO PARAMETRI DI RICERCA */
		var sigla = $('#siglaPercorso').val();
		var descrizione = $('#descrizionePercorso').val();
		var stato = $('#statoPercorso').val();
		/** INIZIALIZZO TABELLA */
		$("#tabellaPercorso").DataTable({
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
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaPercorso',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var filtri = {sigla: sigla,
								  descrizione: descrizione,
								  tipo: tipo,
								  stato: stato,
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw };
					
					return JSON.stringify(filtri);
				}  
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(_d, _t, r) {return r.sigla;}, orderable : true},
	        	{targets : 1, render: function(_d, _t, r) {return r.descrizione;}, orderable : true},
	        	{targets : 2, render: function(_d, _t, r) {return r.estesaAmministrativa == null ? '' : r.estesaAmministrativa.descrizione;}, orderable : true},
				{targets : 3, render: function(_d, _t, r) {return r.toponimo == null ? '' : r.toponimo.denominazioneUfficiale;}, orderable : true},
				{targets : 4, render: function(_d, _t, r) {return r.stato;}, orderable : true},
				{targets : 5, render: function(_d, _t, r) {return r.note;}, width: '30%', orderable : true},
	        	{targets : 6, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabella', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	self.aggiungiEventiAiPulsantiToponimi();
	        	sessionStorage.setItem('ricercaPercorso', JSON.stringify(settings.json.data));
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio del percorso. Il salvataggio di una modifica non cambierà lo stato dell'entità ma soltanto gli attributi
	 * - elimina, aprirà un box di conferma con la richiesta di eliminazione del percorso
	 *     1. si procede con eliminazione nella tabella u_topo_percorso
	 * 	   2. l'operazione verrà annullata.
	 * - localizza, viene richiamato il metodo getGeometry che ridurrà la modale mostrando su mappa la geometria
	 * 
	 **/
	aggiungiEventiAiPulsantiToponimi() {
		var self = this;
		$('#tabellaPercorso button.modifica-btn').off('click').on('click', function(_event){
			/**VISUALIZZA DETTAGLIO**/
			self.visualizzaDettaglio($(this).data('id'));
			/** INIZIALIZZA LA TABELLA DOCUMENTI */
			self.inizializzaDatatableDocumenti($(this).data('id'), 2);
		});
		
		$("#tabellaPercorso button.elimina-btn").off('click').on('click', function(_event){
			/**ELIMINA PERCORSI**/
			self.eliminaPercorso($(this).data('id'));
        });
		$("#tabellaPercorso button.localizza-btn").off('click').on('click', function(_event) {
			/**LOCALIZZA GEOMETRIA**/
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
	 * Il metodo visualizza il dettaglio del percorso con id passato in input. La visualizzazione del dettaglio prevede una modale già aperta
	 * @param id, identificativo dell'entità Percorso 
	 */
	visualizzaDettaglio(id) {
		/** INIZIALIZZAZIONI */
		var self = this;
		/** VISUALIZZO IL LOADER */
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			    		
		if(id != null) {
			
			var percorsoMod = {};

			/** RECUPERO DEL DETTAGLIO */
			var dettaglio = self.recuperaDettaglio(id);
			self.idDettaglio = id;
			
			/** IMPOSTAZIONI PAGINA */
			$('#' + self.idDialog + ' #ricercaAnagPercorsi').hide();
			$('#' + self.idDialog + ' #dettaglioAnagPercorso').show();
			$('#' + self.idDialog + ' #liDetAnagraficaPercorso').show();
			$('#' + self.idDialog + ' #liDetDocumentoPercorso').show();
			/** BUTTON MODALE */
			self.setButtonModale('dettaglio');
			/** OGGETTO PERCORSO PER LA VISUALIZZAZIONE */
			var detPercorso = { id: id,
								sigla: dettaglio.sigla == null ? '' : dettaglio.sigla,
								descrizione: dettaglio.descrizione == null ? '' : dettaglio.descrizione,
								estesaAmministrativa: dettaglio.estesaAmministrativa.id == null ? '' : dettaglio.estesaAmministrativa.descrizione,
								checkedEstesa: dettaglio.estesaAmministrativa.id != null ? 'checked' : '',
								toponimo: dettaglio.toponimo.id == null ? '' : dettaglio.toponimo.denominazioneUfficiale,
								checkedToponimo: dettaglio.toponimo.id != null ? 'checked' : '',
								geom: dettaglio.geom == null ? false : true,
								note: dettaglio.note == null ? '' : dettaglio.note };

			/** COMPILO IL TEMPLATE CON L'OGGETTO detAccesso */
			let html = compilaTemplate('modaleDettaglioPercorso', detPercorso);
			$("#dettaglio").html(html);

			/** VERIFICO QUALE INPUT E' IMPOSTATO SU CHECKED */
			if($('#estesaAmministrativaPercorsoChecked').is(':checked')) {
				$('#' + self.idDialog + ' #estesaAmministrativaM').prop('readonly', false);
				$('#' + self.idDialog + ' #toponimoStradaleM').prop('readonly', true);
			} else {
				$('#' + self.idDialog + ' #estesaAmministrativaM').prop('readonly', true);
				$('#' + self.idDialog + ' #toponimoStradaleM').prop('readonly', false);
			}

			/** IN BASE ALLA SCELTA DEL CHECKBOX 
			 *  1. SE SI DOVESSE SCEGLIERE ESTESA AMMINISTRATIVA, ALLORA VERRA' IMPOSTATO IL READONLY SULL'INPUT DEL TOPONIMO
			 *  2. SE SI DOVESSE SCEGLIERE TOPONIMO STRADALE, ALLORA VERRA' IMPOSTATO IL READONLY SULL'ESTESA AMMINISTRATIVA
			 */
			$('#' + self.idDialog + ' [name="scelta"]:radio').click(function() {
				let radioVal = $('[name="scelta"]:radio:checked').val();
				if(radioVal == 'EA') {
					$('#' + self.idDialog + ' #estesaAmministrativaM').prop('readonly', false);
					$('#' + self.idDialog + ' #toponimoStradaleM').prop('readonly', true);
				} else if(radioVal == 'TS') {
					$('#' + self.idDialog + ' #estesaAmministrativaM').prop('readonly', true);
					$('#' + self.idDialog + ' #toponimoStradaleM').prop('readonly', false);
				} else {
					$('#' + self.idDialog + ' #estesaAmministrativaM').prop('readonly', false);
					$('#' + self.idDialog + ' #toponimoStradaleM').prop('readonly', false);
				}
			})

			/** AUTOCOMPLETE */
			//appUtil.activeAutocompleteOnFiled('toponimoStradaleM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			//appUtil.activeAutocompleteOnFiled('estesaAmministrativaM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa amministrativa...')
			appUtil.activeAutocompleteOnFiled('toponimoStradaleM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			appUtil.activeAutocompleteOnFiled('estesaAmministrativaM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa amministrativa...')
			/** AGGIORNO IL BREADCRUMB */
			self.aggiornaBreadcrumb('dettaglio', detPercorso.sigla);

			/** ================================================================================================================================================== */
			$('#' + self.idDialog + ' #localizza-geom-percorso-btn').click(function() {
				/** FACCIO VISUALIZZARE LA GEOMETRIA SU MAPPA */
				self.getGeometry(dettaglio.geom);
			})
			/** EDITING PERCORSO */
			$('#' + self.idDialog + ' .editing-geom-percorso-btn').click(function() {
				self.newEntita = false;
				/** RIDUCO TUTTE LE MODALI */
				self.riduciAdIconaTutteLeModali();

				var geomWKT = dettaglio != undefined ? dettaglio.geom : null;
				/** @TODO @AP EDITING PERCORSI */
			})
			/** TAB DOCUMENTI */
			$('#' + self.idDialog + ' #documentoBtnPercorso').click(function() {
				self.uploadDocumento(self.idDettaglio, 2);
			})

		} else {

			self.setButtonModale("inserimento");

			/** IMPOSTAZIONI PAGINA */
			if($('#' + self.idDialog + ' ul.breadcrumb li:last-child').text().indexOf('Accessi') != -1) {
				self.aggiornaBreadcrumb('nuovo');
			} else {
				$('#' + self.idDialog + ' ul.breadcrumb li:last-child').remove();
				self.aggiornaBreadcrumb('nuovo');
			}
			$('#' + self.idDialog + ' #ricercaAnagPercorsi').hide();
			$('#' + self.idDialog + ' #dettaglioAnagPercorso').show();
			$('#' + self.idDialog + ' #liDetAnagraficaPercorso').hide();
			$('#' + self.idDialog + ' #liDetDocumentoPercorso').hide();

			/** COMPILO IL TEMPLATE */
			var detPercorso = {};
			var htmlDet = compilaTemplate('modaleDettaglioPercorso', detPercorso);
			$('#dettaglio').html(htmlDet);

			/** IN BASE ALLA SCELTA DEL CHECKBOX 
			 *  1. SE SI DOVESSE SCEGLIERE ESTESA AMMINISTRATIVA, ALLORA VERRA' IMPOSTATO IL READONLY SULL'INPUT DEL TOPONIMO
			 *  2. SE SI DOVESSE SCEGLIERE TOPONIMO STRADALE, ALLORA VERRA' IMPOSTATO IL READONLY SULL'ESTESA AMMINISTRATIVA
			 */
			$('#' + self.idDialog + ' [name="scelta"]:radio').click(function() {
				let radioVal = $('[name="scelta"]:radio:checked').val();
				if(radioVal == 'EA') {
					$('#' + self.idDialog + ' #estesaAmministrativaM').prop('readonly', false);
					$('#' + self.idDialog + ' #toponimoStradaleM').prop('readonly', true);
				} else if(radioVal == 'TS') {
					$('#' + self.idDialog + ' #estesaAmministrativaM').prop('readonly', true);
					$('#' + self.idDialog + ' #toponimoStradaleM').prop('readonly', false);
				} else {
					$('#' + self.idDialog + ' #estesaAmministrativaM').prop('readonly', false);
					$('#' + self.idDialog + ' #toponimoStradaleM').prop('readonly', false);
				}
			})

			/** AUTOCOMPLETE */
			//appUtil.activeAutocompleteOnFiled('toponimoStradaleM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			//appUtil.activeAutocompleteOnFiled('estesaAmministrativaM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa amministrativa...')
			appUtil.activeAutocompleteOnFiled('toponimoStradaleM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			appUtil.activeAutocompleteOnFiled('estesaAmministrativaM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa amministrativa...')

			/** EDITING PERCORSO */
			$('#' + self.idDialog + ' .editing-geom-percorso-btn').click(function() {
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
	 * Metodo per il recupero del dettaglio di un percorso dato l'id
	 * @param id, identificativo del percorso
	 */
	recuperaDettaglio(id) {
		
		let percorso = JSON.parse(sessionStorage.getItem('ricercaPercorso'));
		let dettaglioPercorso;
		for(var i=0; i<percorso.length; i++){
			if(percorso[i].id === id){
				dettaglioPercorso = percorso[i];
				break;
			}
		}
		
		return dettaglioPercorso;
		
	}
	
	/**
	 * Il metodo inizializza la tabella dei documenti legata con idPercorso e tipo.
	 * @param idPercorso è l'indentficativo del 
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
	inizializzaDatatableDocumenti(idPercorso, tipo) {
		var self = this;
		$("#tabellaDocumentiPercorsi").DataTable({
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
								  idRisorsa: idPercorso,
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: idPercorso, nomeFile: r.nomeDocumento});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {
	        	self.aggiungiEventiAiPulsantiDocumentiPercorso();
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
	aggiungiEventiAiPulsantiDocumentiPercorso() {
		var self = this;
		$("#tabellaDocumentiPercorsi button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('nome'), $(this).data('id'), 2);
        });
		
		$("#tabellaDocumentiPercorsi button.download-documento-btn").off('click').on('click', function(_event){
			self.downloadDocumento($(this).data('nome'), $(this).data('id'), 2);
        });
	}

	/**
	 * Il metodo apre una modale con un form di upload del file.
	 * @param idPercorso indentifica il percorso
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
	uploadDocumento(idPercorso, tipo) {
		var self = this;
    	openStaticDetailViaHandlebars('uploadDocumentoPercorso', 'modaleUploadDocumentiPercorso', {}, 'Upload', function(){
    		
    		appUtil.reloadTooltips();
    		
			$('#uploadDocumentoBtn').click(function() {
				self.inserisciDocumento(idPercorso, tipo);
			})
    		
    	}, {width: 350, height: 155, resizable: false, modale: true, closable: true});
		
	}
	
	/**
	 * Il metodo carica sul server il documento legandolo all'id identificativo del percorso e al tipo di risorsa.
	 * @param idPercorso indentifica il percorso
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
	inserisciDocumento(idPercorso, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var fd = new FormData($('#uploadForm')[0]);
		var files = $('#file')[0].files[0];
        fd.append('file',files);
		
		setTimeout(function(){
			$.when(toponomasticaRest.uploadFile(fd, idPercorso, tipo)).done(function (response) {
				appUtil.hideLoader()
				if(response.success) {
					appUtil.hideLoader();
		        	$('#uploadDocumentoPercorso').dialog('close');
		        	self.ricaricaDataTable('tabellaDocumentiPercorsi')
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
	 * @param idPercorso è l'id del percorso
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
	downloadDocumento(nomeFile, idPercorso, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();
			toponomasticaRest.downloadFile(nomeFile, idPercorso, tipo);
		})
	}
	
	/**
	 * Il metodo elimina dal server il documento.
	 * @param nomeFile il nome del file
	 * @param idPercorso è l'id del percorso
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
	eliminaDocumento(nomeFile, idPercorso, tipo) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaDocumento(idPercorso, tipo, nomeFile)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {
						self.ricaricaDataTable('tabellaDocumentiPercorsi');
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
	 * Il metodo salva il percorso e riporta l'utente sulla pagina di ricerca. 
	 * @param percorso, è un oggetto contentente le informazioni di un percorso da inserire
	 */
	inserisciPercorso(percorso) {
		var self = this;
		$.when(toponomasticaRest.inserisciPercorso(percorso)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
				$("#ricercaAnagPercorsi").show();							//Visualizzo la ricerca
				$("#dettaglioAnagPercorso").hide();							//Nascondo il dettaglio
				self.aggiornaBreadcrumb('ricerca');							//Aggiorno il Breadcrumb
				self.setButtonModale('ricerca');							//Setto i pulsanti
	    		self.ricaricaDataTable('tabellaPercorso');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Percorso:' + percorso.sigla +  ' inserito con successo',
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
		    		message: 'Errore nell\'inserimento del percorso ' + percorso.sigla,
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		})
	}
	
	/**
	 * Il metodo salva la modifica di un percorso e riporta l'utente sulla pagina di ricerca. 
	 * @param percorsoM, è un oggetto contentente le informazioni di un percorso da modificare
	 */
	modificaPercorso(percorsoM) {
		var self = this;
		$.when(toponomasticaRest.inserisciPercorso(percorsoM)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
				$("#ricercaAnagPercorsi").show();							//Visualizzo la ricerca
				$("#dettaglioAnagPercorso").hide();							//Nascondo il dettaglio
				self.aggiornaBreadcrumb('ricerca');							//Aggiorno il Breadcrumb
				self.ricaricaDataTable('tabellaPercorso');					//Ricarico DataTable
				self.setButtonModale('ricerca');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Percorso:' + percorsoM.sigla +  ' modificato con successo',
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
		    		message: 'Errore nella modifica del percorso ' + percorsoM.sigla,
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		})
	}
	
	/**
	 * Il metodo elimina un percorso chiedendo conferma. In seguito all'operazione l'utente viene riportato sulla pagina di ricerca. 
	 * @param id, identificativo del percorso
	 */
	eliminaPercorso(id) {
		var self = this;
		appUtil.confirmOperation(function() {	
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaPercorso(id)).done(function (response) {
					if(response.success) {
		    			self.ricaricaDataTable('tabellaPercorso');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Percorso eliminato con successo',
			    			animateInside: false,
			    			position: 'topCenter',
						});
						appUtil.hideLoader();
					} else {
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Errore nella cancellazione del percorso!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
				})
			});
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione del percorso?');
	}
}