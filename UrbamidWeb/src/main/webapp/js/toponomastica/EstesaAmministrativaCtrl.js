/** 
 * CONTROLLER PER LA GESTIONE DELLE ESTESE AMMINISTRATIVE
 */
class PaginaGestioneEstesaAmministrativaCtrl extends BaseModaleRicercaCtrl {
	constructor() {
		/** Inserisce come HTML la modale in pagina */
		super('paginaGestioneEstesaAmministrativaCtrl','Gestione Estesa Amministrativa', 'modaleGestioneEsteseAmministrative');
		/** Inizializza la modale con i dati delle estese amministrative */
		this.inizializzaPagina();
	}

	/**
	 * Inizializza la modale dell'estese amministrative
	 */
	inizializzaPagina(){
		var self = this;

		/**Recupero dei dati di base della modale Toponimi Stradale**/
		/**************************** PROVINCE **************************/
    	self.provinceMessina = [{id:57675, descrizione: "MESSINA"}];
		/**************************** COMUNI **************************/
		// var hrefToponomasticaComuniUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaCtrl/getComuniByMessina";
    	// var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaComuniUrbamid, "GET", null);
    	self.comuniMessina = [{id:10185, descrizione: "MESSINA"}];
		/**************************** ENTE GESTORE **************************/
		var hrefToponomasticaEnteGestoreUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaConsultazioneCtrl/getEnteGestore";
    	var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaEnteGestoreUrbamid, "GET", null);
		self.enteGestore = UrbamidResponse.aaData;
		/**************************** CLASSIFICA AMMINISTRATIVA **************************/
		var hrefToponomasticaClassificaAmministrativaUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaConsultazioneCtrl/getClassificaAmministrativa";
    	var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaClassificaAmministrativaUrbamid, "GET", null);
		self.classificaAmministrativa = UrbamidResponse.aaData;
		/**************************** CLASSIFICA FUNZIONALE **************************/
		var hrefToponomasticaClassificaFunzionaleUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaConsultazioneCtrl/getClassificaFunzionale";
    	var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaClassificaFunzionaleUrbamid, "GET", null);
		self.classificaFunzionale = UrbamidResponse.aaData;
		/**************************** PATRIMONIALITA **************************/
		var hrefToponomasticaPatrimonialitaUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaConsultazioneCtrl/getPatrimonialita";
    	var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaPatrimonialitaUrbamid, "GET", null);
		self.patrimonialita = UrbamidResponse.aaData;

		/**Il seguente metodo viene chiamato nella BaseModaleCtrl per formare la modale**/
		let contextHandlebar = {enteGestore: self.enteGestore, classificaAmministrativa: self.classificaAmministrativa, classificaFunzionale: self.classificaFunzionale, patrimonialita: self.patrimonialita, comuniMessina: self.comuniMessina};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			/** INIZIALIZZAZIONI */
			appUtil.reloadTooltips();
	    	self.aggiungiEventoClickChiudiFinestra();
	    	self.aggiungiEventiClickTornaIndietroARicerca();
			self.inizializzaBreadcrumb();
			$('#tabellaEstese').hide();
			$('#dettaglioAnagEstesa').hide();
			$('#siglaEstesaAmministrativa').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#codiceEstesaAmministrativa').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#descrizioneEstesaAmministrativa').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			/** IMPOSTO BUTTON MODALE */
			self.setButtonModale('ricerca');
			/**IMPOSTAZIONI EDITING**/
    		self.properties = { 
    				geometryWKT:null, 
    				typeEditing:'LineString',
    				pagina:'paginaGestioneEstesaAmministrativaCtrl', 
    				idIziToast:'editingEstesaIziToast', 
    				stato:null, 
    				activeCuting: false,
    				activeLayers : []
    		};
			/** =================== AZIONI MODALE RICERCA ======================== */
			/** CERCA */
	    	$('#' + self.idDialog + ' #ricercaBtnEstesa').click(function() {
	    		/**VISUALIZZA LA TABELLA**/
				$("#"+self.idDialog+" #tabellaEstese").show();
				/**INIZIALIZZA**/
				self.inizializzaDatatableRicerca();
			});
	    	/** AZZERA **/
			$('#' + self.idDialog + ' #azzeraBtnEstesa').click(function() {
	    		self.resettaForm('ricerca')
	    	})
	    	/** NUOVO **/
	    	$('#' + self.idDialog + ' #nuovoBtnEstesa').click(function() {
	    		/**INIZIALIZZA**/
	    		self.properties.newEntita 	= true;
	    		self.modified 				= false;
	    		self.cuting 				= false;
				self.confermaDisegno 		= false;
				self.blockEditing 			= false;
	    		/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
	    		self.riduciAdIconaTutteLeModali();
				/**ABILITA DISEGNO SU MAPPA DI UNA NUOVA GEOMETRIA**/
				self.avviaEditingEstesa(null);
	    	})
			/** CHIUDI */
			$('#' + self.idDialog + ' #chiudiBtnEstesa').click(function() {
	    		/**PULIZIA DEL LAYER DEL DISEGNO**/
	    		drawLayerSource.clear();
	    		/**CHIUDE LA MODALE DELL'ANAGRAFICA**/
	    		$('#'+self.idDialog).dialog('close');
			})
			/************************************************************************************************ AZIONI MODALE RICERCA ***************** FINE **/
	    	/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** INIZIO **/
			$('#' + self.idDialog + ' #aggiornaBtnEstesa').click(function(event) {
				event.stopImmediatePropagation();
				/**VERIFICHE PER IL SALVATAGGIO DELLA MODIFICA**/
				if($('#provinciaEstesaAmministrativaM').val() == '' || $('#comuneEstesaAmministrativaM').val() == '') {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Devono essere compilati tutti i campi obbligatori',
						animateInside: false,
						position: 'topCenter',
					});
				} else {
					/** RECUPO IL VECCHIO DETTAGLIO */
					var dettaglio = self.recuperaDettaglio(self.idDettaglio);
					/** CREO L'OGGETTO DA SALVARE */
					var estesaAmministrativaMod = { id: self.idDettaglio,
					  		   sigla					: $('#' + self.idDialog + ' #siglaEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #siglaEstesaAmministrativaM').val(),
					  		   descrizione				: $('#' + self.idDialog + ' #descrizioneEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #descrizioneEstesaAmministrativaM').val(),
					  		   classificaAmministrativa	: { id: $('#' + self.idDialog + ' #classificaEstesaAmministrativaM option:selected').val() },
					  		   codice					: $('#' + self.idDialog + ' #codiceEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #codiceEstesaAmministrativaM').val(),
					  		   tronco					: $('#' + self.idDialog + ' #troncoEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #troncoEstesaAmministrativaM').val(),
					  		   estensione				: $('#' + self.idDialog + ' #estensioneEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #estensioneEstesaAmministrativaM').val(),
					  		   provincia				: { idProvincia: $('#' + self.idDialog + ' #provinciaEstesaAmministrativaM option:selected').val() },
					 	 	   comune					: { idComune: $('#' + self.idDialog + ' #comuneEstesaAmministrativaM option:selected').val() },
					  		   patrimonialita			: { id: $('#' + self.idDialog + ' #patrimonialitaEstesaAmministrativaM option:selected').val() },
					  		   enteGestore				: { id: $('#' + self.idDialog + ' #enteGestoreEstesaAmministrativaM option:selected').val() },
					  		   classificaFunzionale		: { id: $('#' + self.idDialog + ' #classificaFunzionaleEstesaAmministrativaM option:selected').val() },
					  		   stato					: dettaglio.stato,
							   geom						: sessionStorage.getItem('wktEditFeatureGeom') !=null ? sessionStorage.getItem('wktEditFeatureGeom') : dettaglio.geom,	
							   note						: $('#' + self.idDialog + ' #noteEstesaAmministrativa').val() == '' ? null : $('#' + self.idDialog + ' #noteEstesaAmministrativa').val(),
							   isCircle					: dettaglio.isCircle 
					};
					/**SALVATAGGIO DELLA MODIFICA**/
					self.modificaEstesa(estesaAmministrativaMod);
				}
			});
			/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** FINE **/
    		/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* INIZIO **/
			$('#' +  self.idDialog + ' #salvaBtnEstesa').click(function(event) {
				event.stopImmediatePropagation();
				/**VERIFICHE PER IL SALVATAGGIO **/
				if($('#provinciaEstesaAmministrativaM').val() == '' || $('#comuneEstesaAmministrativaM').val() == '') {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Devono essere compilati tutti i campi obbligatori',
						animateInside: false,
						position: 'topCenter',
					});

				} else {

					/**CREO L'OGGETTO DA SALVARE (id null perché utile per definire la nuova entità)**/
					var estesaAmministrativaIns = { id: null,
							  sigla						: $('#' + self.idDialog + ' #siglaEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #siglaEstesaAmministrativaM').val(),
							  descrizione				: $('#' + self.idDialog + ' #descrizioneEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #descrizioneEstesaAmministrativaM').val(),
							  classificaAmministrativa	: { id: $('#' + self.idDialog + ' #classificaEstesaAmministrativaM option:selected').val() },
							  codice					: $('#' + self.idDialog + ' #codiceEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #codiceEstesaAmministrativaM').val(),
							  tronco					: $('#' + self.idDialog + ' #troncoEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #troncoEstesaAmministrativaM').val(),
							  estensione				: $('#' + self.idDialog + ' #estensioneEstesaAmministrativaM').val() == '' ? null : $('#' + self.idDialog + ' #estensioneEstesaAmministrativaM').val(),
							  provincia					: { idProvincia: $('#' + self.idDialog + ' #provinciaEstesaAmministrativaM option:selected').val() },
							  comune					: { idComune: $('#' + self.idDialog + ' #comuneEstesaAmministrativaM option:selected').val() },
							  patrimonialita			: { id: $('#' + self.idDialog + ' #patrimonialitaEstesaAmministrativaM option:selected').val() },
							  enteGestore				: { id: $('#' + self.idDialog + ' #enteGestoreEstesaAmministrativaM option:selected').val() },
							  classificaFunzionale		: { id: $('#' + self.idDialog + ' #classificaFunzionaleEstesaAmministrativaM option:selected').val() },
							  geom						: sessionStorage.getItem('wktEditFeatureGeom'),
							  note						: $('#' + self.idDialog + ' #noteEstesaAmministrativa').val() == '' ? null : $('#' + self.idDialog + ' #noteEstesaAmministrativa').val(),
							  isCircle					: self.properties.typeEditing.indexOf('Circle') != -1 ? true : false 
					};
					/**SALVATAGGIO NUOVA ENTITA**/
					self.inserisciEstesa(estesaAmministrativaIns);
				}
			})
			/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* FINE **/
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** INIZIO **/
	    	$('#' + self.idDialog + ' #indietroBtn').click(function() {
    			/**INIZIALIZZAZIONE**/
    			$("#ricercaAnagEstesa").show();
    			$("#dettaglioAnagEstesa").hide();
    			/**PULIZIA DELLE VARIABILI USATE NEL DETTAGLIO**/
    			self.idDettaglio = null;
    			/**BUTTON MODALE**/
				self.setButtonModale("ricerca");
				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('ricerca');
			})
			/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** FINE **/
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaGestioneEstesaAmministrativaCtrl');
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
	 * Metodo di utility per verificare se è numero, passandogli in input un valore. Il risultato
	 * true = è un numero intero
	 * false = non è un numero intero
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
		/** INIZIALIZZAZIONI */
		var self = this;
		/** RECUPERO PARAMETRI DI RICERCA */
		var siglaEstesaAmministrativa = $('#siglaEstesaAmministrativa').val();
		var descrizioneEstesaAmministrativa = $('#descrizioneEstesaAmministrativa').val();
		var codiceEstesaAmministrativa = $('#codiceEstesaAmministrativa').val();
		var classificaAmmEstesaAmministrativa = $('#classificaAmmEstesaAmministrativa option:selected').val();
		var classificaFunzEstesaAmministrativa = $('#classificaFunzEstesaAmministrativa option:selected').val();
		/** INIZIALIZZO LA TABELLA */
		$("#tabellaEstese").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 1, 2, 3, 4, 5, 6, 7, 8 ],
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
				//url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaEstesaAmministrativa',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/ricercaEstesaAmministrativa',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var colonnaSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							colonnaSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {sigla: siglaEstesaAmministrativa.trim(),
								  descrizione: descrizioneEstesaAmministrativa.trim(),
								  codice: codiceEstesaAmministrativa.trim(),
								  classificaAmministrativa: classificaAmmEstesaAmministrativa,
								  classificaFunzionale: classificaFunzEstesaAmministrativa,
								  stato: 'PUBBLICATO',
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: colonnaSort, dir: json.order[0].dir }  };
					
					return JSON.stringify(filtri);
				}  
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, name: 'sigla', render: function(_d, _t, r) {return r.sigla;}, orderable : true},
	        	{targets : 1, name: 'descrizione', render: function(_d, _t, r) {return r.descrizione;}, orderable : true},
	        	{targets : 2, name: 'classificaAmministrativa', render: function(_d, _t, r) {return r.classificaAmministrativa == null ? '' : r.classificaAmministrativa.descrizione ;}, orderable : true},
				{targets : 3, name: 'classificaFunzionale', render: function(_d, _t, r) {return r.classificaFunzionale == null ? '' : r.classificaFunzionale.descrizione;}, orderable : true},
				{targets : 4, name: 'codice', render: function(_d, _t, r) {return r.codice;}, orderable : true},
	        	{targets : 5, name: 'estensione', render: function(_d, _t, r) {return r.estensione;}, orderable : true},
	        	{targets : 6, name: 'tronco', render: function(_d, _t, r) {return r.tronco;}, orderable : true},
	        	{targets : 7, name: 'provincia', render: function(_d, _t, r) {return r.provincia == null ? '' : r.provincia.denominazione;}, orderable : true},
	        	{targets : 8, name: 'comune', render: function(_d, _t, r) {return r.comune == null ? '' : r.comune.denominazione;}, orderable : true},
				{targets : 9, name: 'stato', render: function(_d, _t, r) {return r.stato}, orderable : true},
				{targets : 10, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabella', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	sessionStorage.setItem('ricercaEstesaAmministrativa', JSON.stringify(settings.json.data));
	        	self.aggiungiEventiAiPulsantiAzioneEstesaAmministrativa();
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio dell'estesa amministrativa. Il salvataggio di una modifica non cambierà lo stato dell'entità ma soltanto gli attributi
	 * - elimina, aprirà un box di conferma con la richiesta di eliminazione del cippo
	 *     1. si procede con eliminazione nella tabella u_topo_estesa_amministrativa
	 * 	   2. l'operazione verrà annullata.
	 * - localizza, viene richiamato il metodo getGeometry che ridurrà la modale mostrando su mappa la geometria
	 * 
	 **/
	aggiungiEventiAiPulsantiAzioneEstesaAmministrativa() {
		var self = this;
		$("#tabellaEstese button.modifica-btn").off('click').on('click', function(_event){
			/**RIPULISCO VARIABILI**/
			sessionStorage.removeItem('hmEntityCut');
			self.properties.newEntita 	= false;
    		self.modified 				= false;
    		self.cuting 				= false;
			self.blockEditing 			= false;
    		/** VISUALIZZA IL DETTAGLIO */
			self.visualizzaDettaglio($(this).data('id'));
			/** INIZIALIZZA LA TABELLA DEI DOCUMENTI */
			self.inizializzaDatatableDocumenti($(this).data('id'), 5);
			/** INIZIALIZZA LA TABELLA DEI CIPPI CHILOMETRICI */
			self.inizializzaDatatableCippi($(this).data('id'));
        });
		
		$("#tabellaEstese button.elimina-btn").off('click').on('click', function(_event){
			/** ELIMINA ESTESA */
			self.eliminaEstesa($(this).data('id'));
        });
		
		$("#tabellaEstese button.localizza-btn").off('click').on('click', function(_event) {
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
	 * Il metodo visualizza il dettaglio dell'estesa amministrativa con id passato in input. La visualizzazione del dettaglio prevede una modale già aperta
	 * @param id, identificativo dell'entità estesa amministrativa
	 */
	visualizzaDettaglio(id) {
		/** INIZIALIZZAZIONI */
		var self = this;
		/** VISUALIZZO IL LOADER */
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		if(id != null) {
			
			var estesaAmministrativaMod = {};

			/** RECUPERO IL DETTAGLIO */
			var dettaglio = self.recuperaDettaglio(id);
			self.idDettaglio = id;
			self.sigla = dettaglio.sigla;

			/** IMPOSTAZIONI PAGINA */
			$('#' + self.idDialog + ' #ricercaAnagEstesa').hide();
			$('#' + self.idDialog + ' #dettaglioAnagEstesa').show();
			$('#' + self.idDialog + ' #liDetAnagraficaEstesa').show();
			$('#' + self.idDialog + ' #liDetDocumentoEstesa').show();
			$('#' + self.idDialog + ' #liDetCippiEstesa').show();
			/** BUTTON MODALE */
			self.setButtonModale('dettaglio');
			/** OGGETTO ESTESA PER LA VISUALIZZAZIONE */
			var detEstesa = {id: id, 
							 sigla: dettaglio.sigla == null ? '' : dettaglio.sigla,
							 descrizione: dettaglio.descrizione == null ? '' : dettaglio.descrizione,
							 classificheAmministrative: self.classificaAmministrativa,
							 classificaAmministrativa: dettaglio.classificaAmministrativa == null ? '' : dettaglio.classificaAmministrativa.id,
							 codice: dettaglio.codice == null ? '' : dettaglio.codice,
							 tronco: dettaglio.tronco == null ? '' : dettaglio.tronco,
							 estensione: dettaglio.estensione == null ? '' : dettaglio.estensione,
							 provinceMessina: self.provinceMessina,
							 provincia: dettaglio.provincia == null ? '' : dettaglio.provincia.idProvincia,
							 comuniMessina: self.comuniMessina,
							 comune: dettaglio.comune == null ? '' : dettaglio.comune.idComune,
							 classificheFunzionali: self.classificaFunzionale,
							 classificaFunzionale: dettaglio.classificaFunzionale == null ? '' : dettaglio.classificaFunzionale.id,
							 entiGestori: self.enteGestore,
							 enteGestore: dettaglio.enteGestore == null ? '' : dettaglio.enteGestore.id,
							 patrimonialita: self.patrimonialita,
							 patrimonialitaID: dettaglio.patrimonialita == null ? '' : dettaglio.patrimonialita.id,
							 note: dettaglio.note == null ? '' : dettaglio.note,
							 geom: dettaglio.geom == null ? false : true }

				
				/** COMPILO IL TEMPLATE CON L'OGGETTO detEstesa */
				let html = compilaTemplate('modaleDettaglioEstesa', detEstesa);
				$('#dettaglio').html(html);
	
				/** INIZIALIZZO I CAMPI SELECT */
				$('#' + self.idDialog + ' #classificaEstesaAmministrativaM').val(dettaglio.classificaAmministrativa == null ? '' : detEstesa.classificaAmministrativa);
				$('#' + self.idDialog + ' #provinciaEstesaAmministrativaM').val(dettaglio.provincia.idProvincia == null ? '' : dettaglio.comune.idProvincia);
				$('#' + self.idDialog + ' #comuneEstesaAmministrativaM').val(dettaglio.comune.idComune == null ? '' : dettaglio.comune.idComune);
				$('#' + self.idDialog + ' #patrimonialitaEstesaAmministrativaM').val(dettaglio.patrimonialita == null ? '' : detEstesa.patrimonialitaID);
				$('#' + self.idDialog + ' #enteGestoreEstesaAmministrativaM').val(dettaglio.enteGestore == null ? '' : detEstesa.enteGestore);
				$('#' + self.idDialog + ' #classificaFunzionaleEstesaAmministrativaM').val(dettaglio.classificaFunzionale == null ? '' : detEstesa.classificaFunzionale);
				
				/** AGGIORNO IL BREADCRUMB */
				if (!self.modified) self.aggiornaBreadcrumb('dettaglio', detEstesa.sigla);

				/** UPPER CASE CAMPI INPUT */
				$('#siglaEstesaAmministrativaM').keyup(function(){
					$(this).val($(this).val().toUpperCase());
				});
				$('#codiceEstesaAmministrativaM').keyup(function(){
					$(this).val($(this).val().toUpperCase());
				});
				$('#descrizioneEstesaAmministrativaM').keyup(function(){
					$(this).val($(this).val().toUpperCase());
				});

				/**IMPOSTAZIONI AVANZATE EDITING**/
				(self.properties).newEntita = false;
				(self.properties).geometryWKT = dettaglio.geom == null || dettaglio.isCircle ? null : dettaglio.geom;
				(self.properties).stato = dettaglio.stato == null ? null : dettaglio.stato;
				(self.properties).typeEditing = dettaglio.isCircle ? 'Circle' : 'LineString';
				
				/************************************************************************************************ AZIONI DETTAGLIO ************** INIZIO **/
				/** VISUALIZZA IL TOPONIMO **/
				$('#' + self.idDialog +' .localizza-geom-estesa-btn').click(function() {
					/** FACCIO VISUALIZZARE LA GEOMETRIA SU MAPPA */
					self.getGeometry(dettaglio.geom);
				});
				if (self.blockEditing)
					$("#"+self.idDialog+" .editing-geom-estesa-btn").hide();
				/** EDITING ESTESA **/
				$('#' + self.idDialog + ' .editing-geom-estesa-btn').click(function() {
					self.properties.newEntita 	= false;
					self.confermaDisegno 		= false;
					sessionStorage.removeItem('hmEntityCut')
					/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
					self.riduciAdIconaTutteLeModali();
					/**ABILITA DISEGNO SU MAPPA DI UNA GEOMETRIA PRESENTE SOLO SU INTERFACCIA**/
					var geomWKT = dettaglio != undefined ? dettaglio.geom : null;
					self.avviaEditingEstesa(geomWKT);
				});
				/** TAB DOCUMENTI */
				$('#' + self.idDialog + ' #documentoBtnEstesa').click(function() {
					self.uploadDocumento(self.idDettaglio, 5)
				})
				/** TAB CIPPI */
				$('#' + self.idDialog + ' #cippoBtnEstesa').click(function() {
					self.anagraficaCippi();
				})
				/************************************************************************************************ AZIONI DETTAGLIO ************** FINE **/
		} else {

			/** IMPOSTAZIONI PAGINA */
			if($('#' + self.idDialog + ' ul.breadcrumb li:last-child').text().indexOf('Estesa') != -1) {
				self.aggiornaBreadcrumb('nuovo');
			} else {
				$('#' + self.idDialog + ' ul.breadcrumb li:last-child').remove();
				self.aggiornaBreadcrumb('nuovo');	
			}
			$('#anagrafica-tab1').click();
			$('#' + self.idDialog + ' #ricercaAnagEstesa').hide();
			$('#' + self.idDialog + ' #dettaglioAnagEstesa').show();		
			$('#' + self.idDialog + ' #liDetDocumentoEstesa').hide();
			$('#' + self.idDialog + ' #liDetCippiEstesa').hide();
			
			/**GESTIONE DEL DETTAGLIO DA VISUALIZZARE**/
			var detEstesa = {comuniMessina: self.comuniMessina, classificheAmministrative: self.classificaAmministrativa, provinceMessina: self.provinceMessina, patrimonialita: self.patrimonialita, entiGestori: self.enteGestore, classificheFunzionali: self.classificaFunzionale};
			var htmlDet = compilaTemplate('modaleDettaglioEstesa', detEstesa);
			$('#dettaglio').html(htmlDet);

			/** UPPER CASE CAMPI INPUT */
			$('#siglaEstesaAmministrativaM').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#codiceEstesaAmministrativaM').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#descrizioneEstesaAmministrativaM').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});

			/**BUTTON MODALE**/
    		self.setButtonModale("inserimento");

			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita = true;
			(self.properties).geometryWKT = null;
			(self.properties).stato = null;
			
			/** EDITING estesa */
			$('#' + self.idDialog + ' .editing-geom-estesa-btn').click(function() {
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
					self.avviaEditingEstesa(geomWKT);
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
		
		let risultato = JSON.parse(sessionStorage.getItem('ricercaEstesaAmministrativa'));
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
	 * @param idEstesa è l'indentficativo del 
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
	inizializzaDatatableDocumenti(idEstesa, tipo) {
		var self = this;
		$("#tabellaDocumentiEstesaAmministrativa").DataTable({
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
								  idRisorsa: idEstesa,
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: idEstesa, nomeFile: r.nomeDocumento});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {
	        	self.aggiungiEventiAiPulsantiDocumentiEstesaAmministrativa();
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
	aggiungiEventiAiPulsantiDocumentiEstesaAmministrativa() {
		var self = this;
		$("#tabellaDocumentiEstesaAmministrativa button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('id'), $(this).data('nome'), 5);
        });
		
		$("#tabellaDocumentiEstesaAmministrativa button.download-documento-btn").off('click').on('click', function(_event){
			self.downloadDocumento($(this).data('id'), $(this).data('nome'), 5);
        });
	}
	
	/**
	 * Il metodo apre una modale con un form di upload del file.
	 * @param idEstesa indentifica il cippo
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
	uploadDocumento(idEstesa, tipo) {
		var self = this;
    	openStaticDetailViaHandlebars('uploadDocumentoEstesa', 'modaleUploadDocumentiEstese', {}, 'Upload', function(){
    		
    		appUtil.reloadTooltips();
    		
			$('#uploadDocumentoBtn').click(function() {
				self.inserisciDocumento(idEstesa, tipo);
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
	inserisciDocumento(idEstesa, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var fd = new FormData($('#uploadForm')[0]);
		var files = $('#file')[0].files[0];
        fd.append('file',files);
		
		setTimeout(function(){
			$.when(toponomasticaRest.uploadFile(fd, idEstesa, tipo)).done(function (response) {
				appUtil.hideLoader()
				if(response.success) {
					appUtil.hideLoader();
		        	$('#uploadDocumentoEstesa').dialog('close');
		        	self.ricaricaDataTable('tabellaDocumentiEstesaAmministrativa')
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
	 * @param idEstesa è l'id del cippo chilometrico
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
	downloadDocumento(idEstesa, nomeFile, tipo) {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();
			toponomasticaRest.downloadFile(nomeFile, idEstesa, tipo);
		})
	}
	
	/**
	 * Il metodo elimina dal server il documento.
	 * @param nomeFile il nome del file
	 * @param idEstesa è l'id del cippo chilometrico
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
	eliminaDocumento(idEstesa, nomeFile, tipo) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaDocumento(idEstesa, tipo, nomeFile)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {
						self.ricaricaDataTable('tabellaDocumentiEstesaAmministrativa');
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
	
	inizializzaDatatableCippi(idEstesa) {
		var self = this;
		$("#tabellaCippiEstesaAmministrativa").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 1, 2 ],
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
					var filtri = {idEstesa: idEstesa,
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: colonnaSort, dir: json.order[0].dir } };
					
					return JSON.stringify(filtri);
				}  
			},
			order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, name: 'codice', render: function(_d, _t, r) {return r.codice;}, width: '50%', orderable : true},
	        	{targets : 1, name: 'misura', render: function(_d, _t, r) {return r.misura + ' KM';}, orderable : true},
				{targets : 2, name: 'note', render: function(_d, _t, r) {return r.note;}, orderable : false},
				{targets : 3, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaCippi', {id: r.id, geom: r.geom != null ? true : false});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	sessionStorage.setItem('ricercaCippo', JSON.stringify(settings.json.data));
	        	self.aggiungiEventiAiPulsantiDocumentiCippi();
	        }
		});
	}
	
	aggiungiEventiAiPulsantiDocumentiCippi() {
		var self = this;
		$("#tabellaCippiEstesaAmministrativa button.anagrafica-cippi-btn").off('click').on('click', function(_event){
			self.anagraficaCippi($(this).data('id'));
        });

		$("#tabellaCippiEstesaAmministrativa button.elimina-cippi-btn").off('click').on('click', function(_event){
			self.eliminaCippo($(this).data('id'));
        });
	}

	anagraficaCippi(idCippo) {
		var self = this;
    	openStaticDetailViaHandlebars('anagraficaCippi', 'modaleAnagraficaCippi', {}, 'Anagrafica Cippi', function(){

			$('#anagraficaCippi #chiudiBtn').on('click', function(){
				drawLayerSource.clear();
				$('#anagraficaCippi').dialog('close');
			});

			appUtil.reloadTooltips();
	
			if(idCippo != null) {

				//NASCODO I PULSANTI
				$('#anagraficaCippi .absPulsante').hide();

				//RECUPERO LE INFORMAZIONI
				let cippo = JSON.parse(sessionStorage.getItem('ricercaCippo'));
				let dettaglioCippo;
				for(var i=0; i<cippo.length; i++){
					if(cippo[i].id === idCippo){
						dettaglioCippo = cippo[i];
						break;
					}
				}
			
				/** CREO L'OGGETTO DA FAR VISUALIZZARE */
				var detCippo = {codice: dettaglioCippo.codice == null ? '' : dettaglioCippo.codice,
								misura: dettaglioCippo.misura == null ? '' : dettaglioCippo.misura,
								note: dettaglioCippo.note == null ? '' : dettaglioCippo.note,
								estesa: dettaglioCippo.estesaAmministrativa == null ? true : false,
								readonly: 'readonly',
								hidden: 'hidden',
								hiddenEstesa: 'hidden'};

				let html = compilaTemplate('modaleDettaglioCippo', detCippo);
				$('#anagraficaCippi #dettaglio').html(html);

			} else {

				//VISUALIZZO I PULSANTI
				$('#anagraficaCippi .absPulsante').show();

				/** CREO L'OGGETTO DA FAR VISUALIZZARE */
				var detCippo = {hidden: 'hidden', hiddenEstesa: 'hidden', codice: self.sigla, disabledSigla: 'disabled'};

				let html = compilaTemplate('modaleDettaglioCippo', detCippo);
				$('#anagraficaCippi #dettaglio').html(html);
				
				$("#salvaCippoBtn").click(function() {
					
					if($('#misuraCippoM').val() != '' && !(!isNaN(parseFloat($('#misuraCippoM').val())) && isFinite($('#misuraCippoM').val()))) {

						iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon: 'fa fa-times',
							message: 'Il campo Misura dev\'essere un numero!',
							animateInside: false,
							position: 'topCenter',
						});	

					} else {

						var cippo = {id: null,
									 codice: $("#codiceCippoM").val(),
									 estesaAmministrativa: { id: self.idDettaglio },
									 misura: $('#misuraCippoM').val(),
									 note: $('#noteCippo').val() }
				   
			   			self.inserisciCippo(cippo);

					}

				})

			}
    		
    	}, {width: 700, height: 290, resizable: false, modale: true, closable: true});
		
	}

	/**
	 * Il metodo inserisce un cippo chilometrico collegandolo all'Estesa amministrativa.
	 * @param cippo, l'oggetto contentente le informazioni di un cippo chilometrico da inserire. 
	 */
	inserisciCippo(cippo) {
		var self = this;
		$.when(toponomasticaRest.inserisciCippo(cippo)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				appUtil.hideLoader();
				$('#anagraficaCippi').dialog('close');
	    		self.ricaricaDataTable('tabellaCippiEstesaAmministrativa');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Cippo chilometrico inserito con successo',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
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
	 * Il metodo elimina un cippo chilometrico chiedendo conferma. In seguito all'operazione l'utente viene riportato sulla pagina di ricerca. 
	 * @param idCippo, identificativo del cippo
	 */
	eliminaCippo(idCippo) {
		var self = this;
		appUtil.confirmOperation(function() {	
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				appUtil.hideLoader();
				$.when(toponomasticaRest.eliminaCippo(idCippo)).done(function (response) {
					if(response.success) {
						self.ricaricaDataTable('tabellaCippiEstesaAmministrativa');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Cippo chilometrico eliminato con successo',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
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
	
	/**
	 * Il metodo salva il toponimo stradale e riporta l'utente sulla pagina di ricerca. 
	 * @param estesa, è un oggetto contentente le informazioni di un'estesa amministrativa da inserire
	 */
	inserisciEstesa(estesa) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(toponomasticaRest.inserisciEstesaAmministrativa(estesa)).done(function(response) {
			if(response.success) {
				
				sessionStorage.removeItem('wktEditFeatureGeom');
				$('#ricercaAnagEstesa').show();
				$('#dettaglioAnagEstesa').hide();
				self.aggiornaBreadcrumb('ricerca');
				self.setButtonModale('ricerca');
				self.ricaricaDataTable('tabellaEstese');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Estesa amministrativa: ' + estesa.sigla + ' inserita con successo',
		    		animateInside: false,
		    		position: 'topCenter',
				});
				appUtil.hideLoader();
			} else {
				
				iziToast.error({
		    		title: 'Attenzione',
		    		theme: 'dark',
		    		icon:'fa fa-times',
		    		message: response.sEcho,
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
				appUtil.hideLoader();
			}
		});
	}

	/**
	 * Il metodo salva la modifica di un'estesa amministrativa e riporta l'utente sulla pagina di ricerca. 
	 * @param estesaM, è un oggetto contentente le informazioni di un'estesaAmministrativa da modificare
	 */
	modificaEstesa(estesaM) {
		var self = this;
		console.log(estesaM)
		$.when(toponomasticaRest.inserisciEstesaAmministrativa(estesaM)).done(function (response) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			if(response.success) {
				sessionStorage.removeItem('wktEditFeatureGeom');
				$('#ricercaAnagEstesa').show();						
				$('#dettaglioAnagEstesa').hide();
				self.aggiornaBreadcrumb('ricerca');
				self.setButtonModale('ricerca');
				self.ricaricaDataTable('tabellaEstese');
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Estesa amministrativa: ' + estesaM.sigla + ' modificata con successo',
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
		})
	}

	/**
	 * Il metodo elimina un'estesa amminsitrativa chiedendo conferma. In seguito all'operazione l'utente viene riportato sulla pagina di ricerca. 
	 * @param id, identificativo dell'estesa amministrativa
	 */
	eliminaEstesa(id) {
		var self = this;
		appUtil.confirmOperation(function() {	
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaEstesaAmministrativa(id)).done(function (response) {
					if(response.success) {
		    			self.ricaricaDataTable('tabellaEstese');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Estesa amministrativa eliminata con successo',
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
			    			message: response.sEcho != null ? response.sEcho : 'Cippo chilometrico associato, non &egrave; possibile eliminare l\'entit&agrave;!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
				})
			});

		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione dell\'estesa amministrativa?');
	}
	
	
	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* INIZIO ********/
	/****************************************************************************************************************************************************************/
	/**
	 * Avvio dell'editing con il passaggio della geometria in formato WKT
	 * @param geometry, stringa che riporta il WKT della geometria
	 */
	avviaEditingEstesa(geometry) {
		/**1 - inizializzo**/
		var self = this;
		drawLayerSource.clear();
		/**2 - avvia la gestione dell'editing**/
		manageEditing(self, self.properties);
		if(!self.properties.newEntita && self.properties.typeEditing.indexOf('Circle') == -1) {
			$('#editingEstesaIziToast #circleBtn').hide();
		}	
	}
	
	/**
	 * Il metodo conferma le feature disegnate/tagliate e da il via al salvataggio chiudendo il iziToast "editingToponomasticaIziToast"
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
			/**1.2 - Disabilito il disegno su mappa**/
			self.toast = document.getElementById('editingEstesaIziToast'); // Selector of your toast
			iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
			/**1.3 - Confermo il disegno**/
			self.confermaDisegno = true;
			/**1.4 - Confermo il disegno**/
			self.blockEditing = true;
			/**1.5 - Setto il LineString */
			// self.properties.typeEditing = 'LineString';
		} else if( self.cuting ){															//HO EFFETTUATO DEI TAGLI
			
			var numCat = sessionStorage.getItem('numCut');
			var numDefCut = sessionStorage.getItem('numDefCut');
			if (numCat==numDefCut) {
				/**2.2 - Disabilito il disegno su mappa**/
				self.toast = document.getElementById('editingEstesaIziToast'); // Selector of your toast
				iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
				/**2.3 - Confermo il disegno**/
				self.confermaDisegno = true;
				/**1.4 - Confermo il disegno**/
				self.blockEditing = true;
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
	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* FINE **********/
	/****************************************************************************************************************************************************************/
}