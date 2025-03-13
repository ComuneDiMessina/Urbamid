class PaginaGestioneAccessoCtrl extends BaseModaleRicercaCtrl {
	constructor() {
		super('paginaGestioneAccessoCtrl','Gestione Accesso', 'modaleGestioneAccessi');		
	
		this.datiMockInterno = [{
			id: 1,
			comune: 'Messina',
			foglio: 3,
			numero: 234,
			subalterno: 702,
			note: ''
		}];
		
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		/**Recupero dei dati di base della modale Toponimi Stradale**/
		/**************************** LOCALITA' **************************/
		//var hrefToponomasticaLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/findAllLocalita';
		//var UrbamidResponseLocalita = appRest.restUrbamid(hrefToponomasticaLocalitaToponimoUrbamid, "GET", null);
		//self.localita = UrbamidResponseLocalita.aaData;
		///**************************** TIPI ACCESSO **************************/
		//var hrefToponomasticaTipoAccessoToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getTipoAccesso';
		var hrefToponomasticaLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/findAllLocalita';
		var UrbamidResponseLocalita = appRest.restUrbamid(hrefToponomasticaLocalitaToponimoUrbamid, "GET", null);
		self.localita = UrbamidResponseLocalita.aaData;
		/**************************** TIPI ACCESSO **************************/
		var hrefToponomasticaTipoAccessoToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getTipoAccesso';

		var UrbamidResponseTipoAccessi = appRest.restUrbamid(hrefToponomasticaTipoAccessoToponimoUrbamid, "GET", null);
		self.tipoAccessi = UrbamidResponseTipoAccessi.aaData;
		
		/**Il seguente metodo viene chiamato nella BaseModaleCtrl per formare la modale**/
		let contextHandlebar = {localita: self.localita, tipoAccesso: self.tipoAccessi};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			
			/** INIZIALIZZAZIONI **/
			appUtil.reloadTooltips();
	    	self.aggiungiEventoClickChiudiFinestra();
	    	self.aggiungiEventiClickTornaIndietroARicerca();
	    	self.inizializzaBreadcrumb();
	    	/* NASCONDO LA TABELLA E DETTAGKUI*/
			$("#"+self.idDialog+" #tabellaAccessi").hide();								/* NASCONDO LA TABELLA */
			$("#"+self.idDialog+" #dettaglioAnagAccesso").hide();						/* NASCONDO IL DETTAGLIO */
			/** IMPOSTAZIONI FORM DI RICERCA **/
	    	//appUtil.activeAutocompleteOnFiled('toponimoStradaleAccesso', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
	    	appUtil.activeAutocompleteOnFiled('toponimoStradaleAccesso', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
	    	/**IMPOSTAZIONI BUTTON MODALE**/
    		self.setButtonModale("ricerca");
	    	/**IMPOSTAZIONI EDITING**/
    		self.properties = {
    				geometryWKT:null, 
    				typeEditing:'Point',
    				pagina:'paginaGestioneAccessoCtrl', 
    				idIziToast:'editingAccessoIziToast', 
    				stato:null,
    				activeCuting: false,
    				activeLayers : [
    				               	{layerName:"u_cat_particelle",layerTitle:"Particelle"},
    				               	{layerName:"u_cat_fabbricati",layerTitle:"Fabbricati"}
    				              ]
    		};
	    	/************************************************************************************************ AZIONI MODALE RICERCA***************** INIZIO **/
    		/** CERCA **/
	    	$('#' + self.idDialog + ' #ricercaBtnAccesso').click(function() {
	    		/**VISUALIZZA LA TABELLA**/
				$("#"+self.idDialog+" #tabellaAccessi").show();
				/**INIZIALIZZA**/
				self.inizializzaDatatableRicerca();
			});
	    	/** AZZERA **/
	    	$('#' + self.idDialog + ' #azzeraBtnAccesso').click(function() {
	    		self.resettaForm('#ricerca')
	    	})
	    	/** NUOVO **/
	    	$('#' + self.idDialog + ' #nuovoBtnAccesso').click(function() {
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
				self.avviaEditingAccesso(null);
	    	})
	    	/** CHIUDI **/
	    	$('#' + self.idDialog + ' #chiudiBtnAccesso').click(function() {
	    		/**PULIZIA DEL LAYER DEL DISEGNO**/
	    		drawLayerSource.clear();
	    		/**CHIUDE LA MODALE DELL'ANAGRAFICA**/
	    		$('#'+self.idDialog).dialog('close');
	    	})
	    	/************************************************************************************************ AZIONI MODALE RICERCA ***************** FINE **/
	    	/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** INIZIO **/
			$('#' + self.idDialog + ' #aggiornaBtnAccesso').click(function(event) {
				event.stopImmediatePropagation();
				
				if($('#accessoNumCivico').val() != '' && !self.isNumeroInt($('#accessoNumCivico').val())) {
					
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il campo Numero dev\'essere un numero intero!',
						animateInside: false,
						position: 'topCenter',
					});

				} else {

					/**RECUPERA IL VECCHIO DETTAGLIO**/
					var dettaglio = self.recuperaDettaglio(self.idDettaglio);
					/**CREO L'OGGETTO DA SALVARE**/
					var accessoMod = { id: self.idDettaglio,
								 	 	toponimo		: { id: $('#accessoToponimoValue').val() != '' ? $('#accessoToponimoValue').val() : dettaglio.toponimo.id  },
								 	 	numero			: $('#accessoNumCivico').val() == '' ? null : $('#accessoNumCivico').val(),
								 	 	esponente		: $('#esponenteCivico').val() == '' ? null : $('#esponenteCivico').val(),
								 	 	localita		: { id: $('#localitaAccessoM option:selected').val() },
								 	 	tipo			: { id: $('#tipoAccessoM option:selected').val() },
								 	 	passoCarrabile	: $('#accessoPassoCarrabile').is(":checked"),
								 	    principale		: $('#accessoPrincipale').is(":checked"),
								 	    metodo			: $('#accessoMetodo').val() == '' ? null : $('#accessoMetodo').val(),
								 	    note			: $('#accessoNote').val() == '' ? null : $('#accessoNote').val(),
									    geom			: sessionStorage.getItem('wktEditFeatureGeom')!=null? sessionStorage.getItem('wktEditFeatureGeom') : dettaglio.geom,
									    stato			: dettaglio.stato
					};			 
					/**SALVATAGGIO DELLA MODIFICA**/
					self.modificaAccesso( accessoMod )

				}
			})
			/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** FINE **/
			/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* INIZIO **/
			$("#"+self.idDialog+" #salvaBtnAccesso").click(function(event) {
				event.stopImmediatePropagation();
				
				if($('#accessoNumCivico').val() != '' && !self.isNumeroInt($('#accessoNumCivico').val())) {
					
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il campo Numero dev\'essere un numero intero!',
						animateInside: false,
						position: 'topCenter',
					});

				} else {
				
					/**CREO L'OGGETTO DA SALVARE (id null perché utile per definire la nuova entità)**/
    				var accessoIns = { id: null,
									 	toponimo: { id: $('#accessoToponimo').val() == '' ? '' : $('#accessoToponimoValue').val() },
									 	numero: $('#accessoNumCivico').val() == '' ? null : $('#accessoNumCivico').val(),
									 	esponente: $('#esponenteCivico').val() == '' ? null : $('#esponenteCivico').val(),
									 	localita: { id: $('#localitaAccessoM option:selected').val() },
									 	tipo: { id: $('#tipoAccessoM option:selected').val() },
									 	passoCarrabile: $('#accessoPassoCarrabile').is(":checked"),
									 	principale: $('#accessoPrincipale').is(":checked"),
									 	metodo: $('#accessoMetodo').val() == '' ? null : $('#accessoMetodo').val(),
									 	note: $('#accessoNote').val() == '' ? null : $('#accessoNote').val(),
									 	geom: sessionStorage.getItem('wktEditFeatureGeom')
					};
					/**SALVATAGGIO NUOVA ENTITA**/
					self.inserisciAccesso(accessoIns);
				}
    		});
    		/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* FINE **/
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** INIZIO **/
    		$("#"+self.idDialog+" #indietroBtn").click(function(event) {
    			/**INIZIALIZZAZIONE**/
    			$("#ricercaAnagAccesso").show();
    			$("#dettaglioAnagAccesso").hide();
    			/**PULIZIA DELLE VARIABILI USATE NEL DETTAGLIO**/
    			self.idDettaglio = null;
    			/**BUTTON MODALE**/
				self.setButtonModale("ricerca");
				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('ricerca');
    		});
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** FINE **/
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaGestioneAccessoCtrl');
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
		var toponimo = $('#toponimoStradaleAccesso').val();
		var tipo = $('#tipoAccesso option:selected').val();
		var localita = $('#localitaAccesso option:selected').val();
		/**INIZIALIZZO TABELLA**/
		$("#tabellaAccessi").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ],
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
				//url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaAccesso',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/ricercaAccesso',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var colonnaSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							colonnaSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {toponimo: toponimo.trim(),
								  localita: localita,
								  tipo: tipo,
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
	        	{targets : 0, name: 'toponimo', render: function(_d, _t, r) {return r.toponimo == null ? '' : r.toponimo.denominazioneUfficiale;}, orderable : true},
	        	{targets : 1, name: 'localita', render: function(_d, _t, r) {return r.localita == null ? '' : r.localita.descrizione;}, orderable : true},
	        	{targets : 2, name: 'numero', render: function(_d, _t, r) {return r.numero;}, orderable : true},
	        	{targets : 3, name: 'esponente', render: function(_d, _t, r) {return r.esponente;}, orderable : true},
	        	{targets : 4, name: 'tipo', render: function(_d, _t, r) {return r.tipo == null ? '' : r.tipo.descrizione;}, orderable : true},
	        	{targets : 5, name: 'passoCarrabile', render: function(_d, _t, r) {return r.passoCarrabile ? 'SI' : 'NO';}, orderable : true},
				{targets : 6, name: 'principale', render: function(_d, _t, r) {return r.principale ? 'SI' : 'NO';}, orderable : true},
				{targets : 7, name: 'stato', render: function(_d, _t, r) {return r.stato;}, orderable: true},
				{targets : 8, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabella', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});	        		
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	self.aggiungiEventiAiPulsantiAzioneAccesso();
	        	sessionStorage.setItem('ricercaAccesso', JSON.stringify(settings.json.data));
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio dell'accesso. Il salvataggio di una modifica non cambierà lo stato dell'entità ma soltanto gli attributi
	 * - elimina, aprirà un box di conferma con richiesta di archiviazione rispettando i seguenti casi:
	 *     1. se si decide di archiviare allora update nella tabella u_topo_accesso impostanto stato a ARCHIVIATO
	 *     2. se non si decide di archiviare si procede con eliminazione nella tabella u_topo_accesso
	 * - localizza, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 * 
	 **/
	aggiungiEventiAiPulsantiAzioneAccesso() {
		var self = this;
		$("#tabellaAccessi button.modifica-btn").off('click').on('click', function(_event){
			self.properties.newEntita 	= true;
    		self.modified 				= false;
    		self.cuting 				= false;
    		self.blockEditing 			= false;
			/**VISUALIZZA DETTAGLIO**/
			self.visualizzaDettaglio($(this).data('id'));
			self.inizializzaDatatableDocumenti($(this).data('id'), 8);
			self.inizializzaDatatableInterno(self.datiMockInterno);
        });
		$("#tabellaAccessi button.elimina-btn").off('click').on('click', function(_event){
			/**ELIMINA ACCESSO**/
			self.eliminaAccesso($(this).data('id'));
        });
		
		$("#tabellaAccessi button.localizza-btn").off('click').on('click', function(_event) {
			/**LOCALIZZA GEOMETRIA**/
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
	 * Il metodo visualizza il dettaglio dell'accesso con id passato in input. La visualizzazione del dettaglio prevede una modale già aperta
	 * @param id, identificativo dell'entità Accesso 
	 */
	visualizzaDettaglio(id) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

		var passoCarrabile = false;
		var principale = false;
		
		if(id != null) {																							/**GESTIONE DI UN DETTAGLIO**/
			
			/**RECUPERO DEL DETTAGLIO**/
    		var dettaglio 				= self.recuperaDettaglio(id);				//Recupero il dettaglio dell'accesso
    		self.idDettaglio 			= id;										//Salvo l'identificativo del dettaglio
			var accessoMod 				= {};										//Creo l'oggetto accessoMod utile per la modifica all'entità
			
			/** IMPOSTAZIONI PAGINA */
			$("#"+self.idDialog+" #ricercaAnagAccesso").hide();
			$("#"+self.idDialog+" #dettaglioAnagAccesso").show();
			$('#anagrafica-tab1').click();
			$('#'+self.idDialog+' #liDetAnagraficaAccesso').show();
			$('#'+self.idDialog+' #liDetDocumentoAccesso').show();
			$('#'+self.idDialog+' #liDetInterniAccesso').show();
		
			/**BUTTON MODALE**/
    		self.setButtonModale("dettaglio");
    		/** OGGETTO ACCESSO PER LA VISUALIZZAZIONE */
			var detAccesso = { id: id,
							   toponimoId: dettaglio.toponimo == null ? '' : dettaglio.toponimo.id,
							   toponimo: dettaglio.toponimo == null ? '' : dettaglio.toponimo.denominazioneUfficiale,
							   numero: dettaglio.numero == null ? '' : dettaglio.numero,
							   esponente: dettaglio.esponente == null ? '' : dettaglio.esponente,
							   localita: self.localita, 
							   localitaID: dettaglio.localita == null ? '' : dettaglio.localita.id,
							   tipoAccesso: self.tipoAccessi,
							   tipoAccessoID: dettaglio.tipo == null ? '' : dettaglio.tipo.id,
							   passoCarrabile: dettaglio.passoCarrabile ? 'checked' : '',
							   principale: dettaglio.principale ? 'checked' : '',
							   metodo: dettaglio.metodo == null ? '' : dettaglio.metodo,
							   note: dettaglio.note == null ? '' : dettaglio.note,
							   geom: dettaglio.geom == null ? false : true
			};
			/** COMPILO IL TEMPLATE CON L'OGGETTO detAccesso */
			let html = compilaTemplate('modaleDettaglioAccesso', detAccesso);
			$("#dettaglio").html(html);
			/** AUTOCOMPLETE */
			//appUtil.activeAutocompleteOnFiled('accessoToponimo', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			appUtil.activeAutocompleteOnFiled('accessoToponimo', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			
			/** AGGIORNO IL BREADCRUMB */
			if(!self.modified) {
				self.aggiornaBreadcrumb('dettaglio', detAccesso.toponimo != null ? 'Accesso ' + detAccesso.toponimo : 'Accesso ')		
			}

			/** INIZIALIZZO LE SELECT CON GLI ID DEL TIPO ACCESSO E DELLA LOCALITA' */
			$('#' + self.idDialog + ' #localitaAccessoM').val(dettaglio.localita == null ? '' : dettaglio.localita.id);
			$('#' + self.idDialog + ' #tipoAccessoM').val(dettaglio.tipo == null ? '' : dettaglio.tipo.id);
			
			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita = false;
			(self.properties).geometryWKT = dettaglio.geom == null ? null : dettaglio.geom;
			(self.properties).stato = dettaglio.stato == null ? null : dettaglio.stato;
			/*************************************************************************************************************************************/
			/** VISUALIZZA L'ACCESSO */
			$('#' + self.idDialog + ' .localizza-accesso-btn').click(function(_event) {
				/** FACCIO VISUALIZZARE LA GEOMETRIA SU MAPPA */
				self.getGeometry(dettaglio.geom); 
			})
			if (self.blockEditing)
				$("#"+self.idDialog+" .editing-accesso-btn").hide();
			/** EDITING ACCESSO */
			$('#' + self.idDialog + ' .editing-accesso-btn').click(function(_event) {
				self.properties.newEntita 	= false;
				self.confermaDisegno 		= false;
				/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
				self.riduciAdIconaTutteLeModali();
				/**ABILITA DISEGNO SU MAPPA DI UNA GEOMETRIA PRESENTE SOLO SU INTERFACCIA**/
				var geomWKT = detAccesso != undefined ? detAccesso.geom : null;
				self.avviaEditingAccesso(geomWKT);
			})
			/** TAB INTERNO */
			$('#' + self.idDialog + ' #internoBtn').click(function() {
				/** @TODO LT devo fare l'anagrafica degli interni */
			})
			/** TAB DOCUMENTI */
			$('#' + self.idDialog + ' #documentoBtnAccesso').click(function() {
				self.uploadDocumento(self.idDettaglio, 8);
			})
		} else {																							/**GESTIONE DI UN INSERIMENTO**/
			
			/** IMPOSTAZIONI PAGINA */
			if($('#' + self.idDialog + ' ul.breadcrumb li:last-child').text().indexOf('Accessi') != -1) {
				self.aggiornaBreadcrumb('nuovo');
			} else {
				$('#' + self.idDialog + ' ul.breadcrumb li:last-child').remove();
				self.aggiornaBreadcrumb('nuovo');
			}
			$('#anagrafica-tab1').click();
			$('#'+self.idDialog+' #ricercaAnagAccesso').hide();
			$('#'+self.idDialog+' #dettaglioAnagAccesso').show();
			$('#'+self.idDialog+' #liDetDocumentoAccesso').hide();
			$('#'+self.idDialog+' #liDetInterniAccesso').hide();
			/**GESTIONE DEL DETTAGLIO DA VISUALIZZARE**/
    		var detAccessi = {localita:self.localita, tipoAccesso:self.tipoAccessi};
    		var htmlDet = compilaTemplate('modaleDettaglioAccesso', detAccessi);
			$("#dettaglio").html(htmlDet);
			//appUtil.activeAutocompleteOnFiled('accessoToponimo', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			appUtil.activeAutocompleteOnFiled('accessoToponimo', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			/**BUTTON MODALE**/
    		self.setButtonModale("inserimento");
			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita 	= true;
			(self.properties).geometryWKT 	= null;
			(self.properties).stato 		= null;
    		/************************************************************************************************ AZIONI NUOVO ************** INIZIO **/
    		/** EDITING DEL NUOVO ACCESSO **/
    		$("#"+self.idDialog+" .editing-accesso-btn").click(function(_event) {
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
					self.avviaEditingAccesso(geomWKT);
    			}
			});	
			/************************************************************************************************ AZIONI NUOVO ************** FINE **/
		}
		appUtil.hideLoader();
	}
	
	recuperaDettaglio(id) {
		
		let risultato = JSON.parse(sessionStorage.getItem('ricercaAccesso'));
		let dettaglio;
		for(var i=0; i<risultato.length; i++){
			if(risultato[i].id === id){
				dettaglio = risultato[i];
				break;
			}
		}
		
		return dettaglio;
		
	}
	
	inizializzaDatatableDocumenti(risorsa, tipo) {
		var self = this;
		$("#tabellaDocumentiAccessi").DataTable({
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
								  idRisorsa: risorsa,
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: risorsa, nomeFile: r.nomeDocumento});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {
	        	self.aggiungiEventiAiPulsantiDocumentiAccesso();
	        }
		});
	}
	
	aggiungiEventiAiPulsantiDocumentiAccesso() {
		var self = this;
		$("#tabellaDocumentiAccessi button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('id'), 8, $(this).data('nome'));
        });
		
		$("#tabellaDocumentiAccessi button.download-documento-btn").off('click').on('click', function(_event){
			self.downloadDocumento($(this).data('nome'), $(this).data('id'), 8);
        });
	}
	
	uploadDocumento(risorsa, tipo) {
		var self = this;
    	openStaticDetailViaHandlebars('uploadDocumentoAccessi', 'modaleUploadDocumentiAccessi', {}, 'Upload', function(){
    		
    		appUtil.reloadTooltips();
    		
			$('#uploadDocumentoBtn').click(function() {
				self.inserisciDocumento(risorsa, tipo);
			})
    		
    	}, {width: 350, height: 155, resizable: false, modale: true, closable: true});
		
	}
	
	inserisciDocumento(risorsa, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var fd = new FormData($('#uploadForm')[0]);
		var files = $('#file')[0].files[0];
        fd.append('file',files);
		
		setTimeout(function(){
			$.when(toponomasticaRest.uploadFile(fd, risorsa, tipo)).done(function (response) {
				appUtil.hideLoader()
				if(response.success) {
					appUtil.hideLoader();
		        	$('#uploadDocumentoAccessi').dialog('close');
		        	self.ricaricaDataTable('tabellaDocumentiAccessi')
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
		
	downloadDocumento(nome, risorsa, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();
			toponomasticaRest.downloadFile(nome, risorsa, tipo);
		})
	}
	
	eliminaDocumento(risorsa, tipo, nome) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaDocumento(risorsa, tipo, nome)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {
						self.ricaricaDataTable('tabellaDocumentiAccessi');
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
	
	inizializzaDatatableInterno(interno) {
		var self = this;
		$("#tabellaInterno").DataTable({
			data: interno,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        processing: true,
	        order: [[ 1, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(_d, _t, r) {return r.comune;}, orderable : true},
	        	{targets : 1, render: function(_d, _t, r) {return r.foglio;}, orderable : true},
	        	{targets : 2, render: function(_d, _t, r) {return r.numero;}, orderable : true},
	        	{targets : 3, render: function(_d, _t, r) {return r.subalterno;}, orderable : true},
	        	{targets : 4, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRicercaInterno', {id: r.id});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {}
		});
	}
	
	inserisciAccesso(accesso) {
		var self = this;
		$.when(toponomasticaRest.inserisciAccesso(accesso)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				/** INIZIALIZZAZIONE */
				sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
				$("#ricercaAnagAccesso").show();							//Visualizzo la ricerca
				$("#dettaglioAnagAccesso").hide();							//Nascondo il dettaglio
				self.aggiornaBreadcrumb('ricerca');							//Aggiorno il Breadcrumb
				$('#ricercaBtnAccesso').click();							//Ricarico dataTable
				/** BUTTON MODALE */
				self.setButtonModale("ricerca");
				/** MESSAGGIO */
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Accesso inserito con successo!',
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
		    		message: 'Errore nell\'inserimento dell\'accesso!',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		})
	}
	
	modificaAccesso( accessoM ) {
		var self = this;
		$.when(toponomasticaRest.inserisciAccesso(accessoM)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				/** INIZIALIZZAZIONE */
				sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
				$("#ricercaAnagAccesso").show();							//Visualizzo la ricerca
				$("#dettaglioAnagAccesso").hide();							//Nascondo il dettaglio
				self.aggiornaBreadcrumb('ricerca');							//Aggiorno il Breadcrumb
				$('#ricercaBtnAccesso').click();							//Ricarico dataTable
				/** BUTTON MODALE */
				self.setButtonModale("ricerca");
				/** MESSAGGIO */
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Accesso modificato con successo!',
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
		    		message: 'Errore nell\'inserimento dell\'accesso!',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		})
	}
	
	eliminaAccesso(id) {
		var self = this;
		var dettaglio = self.recuperaDettaglio(id);
		console.log(dettaglio.toponimo.id);
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaAccesso(id)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {
						$('#ricercaBtnAccesso').click();
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Accesso eliminato con successo!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {
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
	
	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* INIZIO ********/
	/****************************************************************************************************************************************************************/
	/**
	 * Avvio dell'editing
	 */
	avviaEditingAccesso(geometry) {
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
			if ( newWkt.indexOf("POINT")!=-1 ) 
			    sessionStorage.setItem('wktEditFeatureGeom', newWkt);
			/**1.2 - Disabilito il disegno su mappa**/
			self.toast = document.getElementById('editingAccessoIziToast'); // Selector of your toast
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
				/**2.1 - Blocco l'editing**/
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