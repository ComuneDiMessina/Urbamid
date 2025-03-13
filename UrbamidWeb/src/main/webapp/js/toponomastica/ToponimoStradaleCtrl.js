/**
 * CONTROLLER PER LA GESTIONE DEI TOPONIMI STRADALI
 */
class PaginaGestioneToponimoCtrl extends BaseModaleRicercaCtrl {
	constructor() {
		/**Inserisce come html la modale in pagina**/
		super('paginaGestioneToponimoCtrl','Gestione Toponimo Stradale', 'modaleGestioneToponimi');
		/**Inizializza la modale con i dati dei Toponimi Stradali**/
		this.inizializzaPagina();
	}
	
	/**
	 * Inizializza la modale dei Toponimi Stradali
	 */
	inizializzaPagina(){
		var self = this;

		/** HELPER HENDOLBAR */
		Handlebars.registerHelper('dateFormatter', function(dataOriginale) {
			if(dataOriginale != undefined || dataOriginale != null) {
				return new Date(dataOriginale).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'})	
			}
		})
		/**Recupero dei dati di base della modale Toponimi Stradale**/
		/**************************** TIPI TOPONIMI **************************/
		var hrefToponomasticaTipoToponimoUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaConsultazioneCtrl/getTipoToponimo";
    	var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaTipoToponimoUrbamid, "GET", null);
		self.tipiToponimi = UrbamidResponse.aaData;
		/**************************** COMUNI **************************/
		// var hrefToponomasticaComuniUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaCtrl/getComuniByMessina";
    	// var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaComuniUrbamid, "GET", null);
    	self.comuniMessina = [{id: 10185, descrizione: 'MESSINA'}];
    	/**************************** LOCALITA' **************************/
		//var hrefToponomasticaLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/findAllLocalita';
		var hrefToponomasticaLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/findAllLocalita';
		var UrbamidResponseLocalita = appRest.restUrbamid(hrefToponomasticaLocalitaToponimoUrbamid, "GET", null);
		self.localita = UrbamidResponseLocalita.aaData;
		/**************************** STATI' **************************/
		self.stati = [{id:"BOZZA",descrizione:"BOZZA"},{id:"COMPLETATO",descrizione:"COMPLETATO"},{id:"PUBBLICATO",descrizione:"PUBBLICATO"}];

		/**Il seguente metodo viene chiamato nella BaseModaleCtrl per formare la modale**/
		let contextHandlebar = {tipoToponimo: self.tipiToponimi, localita: self.localita, comuniMessina: self.comuniMessina, stati: self.stati};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){

			/** INIZIALIZZAZIONI **/
			appUtil.reloadTooltips();
	    	self.aggiungiEventoClickChiudiFinestra();
	    	self.aggiungiEventiClickTornaIndietroARicerca();
			self.inizializzaBreadcrumb();
			$("#"+self.idDialog+" #tabellaToponimoStradale").hide();								/* NASCONDO LA TABELLA */
			$("#"+self.idDialog+" #dettaglioAnagToponimo").hide();									/* NASCONDO IL DETTAGLIO */
			/** IMPOSTAZIONI FORM DI RICERCA **/
	    	//appUtil.activeAutocompleteOnFiled('dug', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...')
	    	appUtil.activeAutocompleteOnFiled('dug', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...')
			$("#"+self.idDialog+" #dataDeliberaToponimoDal").datepicker({dateFormat: 'dd/mm/yy'});
			$("#"+self.idDialog+" #dataDeliberaToponimoAl").datepicker({dateFormat: 'dd/mm/yy'});
			$("#"+self.idDialog+" #dataAutorizzazioneToponimoDal").datepicker({dateFormat: 'dd/mm/yy'});
			$("#"+self.idDialog+" #dataAutorizzazioneToponimoAl").datepicker({dateFormat: 'dd/mm/yy'});
			$('#'+self.idDialog+' #exportLiToponimo').hide();
			$('#descrizioneToponimoStradale').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#numCivicoDxDa').keyup(function() {
				if($(this).val() != '' && (!self.isNumeroInt($(this).val()) || $(this).val() % 2 != 0)) {
					$(this).val('');

					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il numero civico del lato destro dev\'essere pari!',
						animateInside: false,
						position: 'topCenter',
					});
				} 
			});
			$('#numCivicoDxA').keyup(function() {
				if($(this).val() != '' && (!self.isNumeroInt($(this).val()) || $(this).val() % 2 != 0)) {
					$(this).val('');
					
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il numero civico del lato destro dev\'essere pari!',
						animateInside: false,
						position: 'topCenter',
					});
				}
			});
			$('#numCivicoSxDa').keyup(function() {
				if($(this).val() != '' && (!self.isNumeroInt($(this).val()) || $(this).val() % 2 == 0)) {
					$(this).val('');
					
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il numero civico del lato destro dev\'essere pari!',
						animateInside: false,
						position: 'topCenter',
					});
				}
			});
			$('#numCivicoSxA').keyup(function() {
				if($(this).val() != '' && (!self.isNumeroInt($(this).val()) || $(this).val() % 2 == 0)) {
					$(this).val('');

					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il numero civico del lato sinistro dev\'essere dispari!',
						animateInside: false,
						position: 'topCenter',
					});
				}
			})
    		/**IMPOSTAZIONI BUTTON MODALE**/
    		self.setButtonModale("ricerca");
    		/**IMPOSTAZIONI EDITING**/
    		self.properties = {
    				geometryWKT:null, 
    				typeEditing:'LineString',
    				pagina:'paginaGestioneToponimoCtrl', 
    				idIziToast:'editingToponimoStradaleIziToast', 
    				stato:null,
    				activeCuting: true,
    				activeLayers : []
    		};
    		/************************************************************************************************ AZIONI MODALE RICERCA***************** INIZIO **/
    		/** CERCA **/
    		$('#' + self.idDialog + ' #ricercaBtnToponimo').click(function() {
				/** CONTROLLO SE I NUMERI CIVICI DA (LATO DX E LATO SX) SONO MINORI DEI NUMERI CIVICI A (LATO DX E LATO SX) */
				if(($('#numCivicoDxDa').val() != '' && $('#numCivicoDxA').val() != '') && Number($('#numCivicoDxDa').val()) <= Number($('#numCivicoDxA').val())) {

					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il numero civico Da, non pu&ograve; essere maggiore del numero civico A',
						animateInside: false,
						position: 'topCenter',
					});

				} else if(($('#numCivicoSxDa').val() != '' && $('#numCivicoSxA').val() != '') && Number($('#numCivicoSxA').val()) <= Number($('#numCivicoSxDa').val())) {

					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Il numero civico A, non pu&ograve; essere maggiore del numero civico Da',
						animateInside: false,
						position: 'topCenter',
					});

				} else {

					/**VISUALIZZA LA TABELLA**/
					$("#"+self.idDialog+" #tabellaToponimoStradale").show();
					/**INIZIALIZZA**/
					self.inizializzaDatatableRicerca();

					$('#'+self.idDialog+' #exportLiToponimo').show();

				}
			})
			/** AZZERA **/
	    	$('#' + self.idDialog + ' #azzeraBtnToponimo').click(function() {
	    		/**RIPULISCE IL FORM DI RICERCA**/
	    		self.resettaForm('#ricerca')
	    	})
    		/** NUOVO **/
	    	$('#' + self.idDialog + ' #nuovoBtnToponimo').click(function() {
	    		/**INIZIALIZZA**/
	    		self.properties.newEntita 	= true;
	    		self.modified 				= false;
	    		self.cuting 				= false;
				self.confermaDisegno 		= false;
				self.blockEditing 			= false;
	    		/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
	    		self.riduciAdIconaTutteLeModali();
				/**ABILITA DISEGNO SU MAPPA DI UNA NUOVA GEOMETRIA**/
				self.avviaEditingToponimo(null);
	    	});
    		/** CHIUDI **/
	    	$('#' + self.idDialog + ' #chiudiBtnToponimo').click(function() {
	    		/**PULIZIA DEL LAYER DEL DISEGNO**/
	    		drawLayerSource.clear();
	    		/**CHIUDE LA MODALE DELL'ANAGRAFICA**/
	    		$('#'+self.idDialog).dialog('close');
	    	});
	    	/************************************************************************************************ AZIONI MODALE RICERCA ***************** FINE **/
	    	/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** INIZIO **/
			$("#"+self.idDialog+" #aggiornaBtnToponimo").click(function(event) {
				event.stopImmediatePropagation();
				/**VERIFICHE PER IL SALVATAGGIO DELLA MODIFICA**/
				if($('#classeToponimoM').val() == '' || $('#denominazione').val() == '' || $('#comuneToponimoM').val() == '') {
    				iziToast.error({
    	    			title: 'Attenzione',
    	    			theme: 'dark',
    	    			icon:'fa fa-times',
    	    			message: 'Devono essere compilati tutti i campi obbligatori',
    	    			animateInside: false,
    	    			position: 'topCenter',
					});
				} else if(($('#dataDeliberaToponimoM').val() == '' ||  $('#numeroDeliberaToponimoM').val() == '') && 
							($('#dataAutorizzazioneToponimoM').val() != '' || $('#codiceAutorizzazioneToponimoM').val() != '')) {
					iziToast.error({
    	    			title: 'Attenzione',
    	    			theme: 'dark',
    	    			icon:'fa fa-times',
    	    			message: 'Devi compilare prima i campi Data delibera e Numero delibera',
    	    			animateInside: false,
    	    			position: 'topCenter',
					});
				} else {
					/**RECUPERA IL VECCHIO DETTAGLIO**/
					var dettaglio = self.recuperaDettaglio(self.idDettaglio);
					/**CREO L'OGGETTO DA SALVARE**/
					var toponimoStradale = {
							id						: self.idDettaglio, 
							idPadre					: dettaglio.idPadre,
							comuneLabel				: $('#comuneToponimoM option:selected').text(),
							denominazione			: $('#denominazione').val() == '' ? null : $('#denominazione').val(),
							denominazioneUfficiale	: $('#denominazioneUfficiale').val() == '' ? null : $('#denominazioneUfficiale').val(),
							classeLabel				: $('#classeToponimoM').val(),
							shapeLeng				: sessionStorage.getItem('wktEditFeatureGeom') != null && sessionStorage.getItem('wktEditFeatureGeom') != '' ? sessionStorage.getItem('wktEditFeatureGeom') : dettaglio.geom,
							cap						: $('#capToponimoM').val() == '' ? null : $('#capToponimoM').val(),
							compendi				: $('#compendiToponimoM').val() == '' ? null : $('#compendiToponimoM').val(),
							numeroDelibera			: $('#numeroDeliberaToponimoM').val() == '' ? null : $('#numeroDeliberaToponimoM').val(),
							dataDelibera			: self.convertiDate('dataDeliberaToponimoM'),
							codiceAutorizzazione	: $('#codiceAutorizzazioneToponimoM').val() == '' ? null : $('#codiceAutorizzazioneToponimoM').val(),
							dataAutorizzazione		: self.convertiDate('dataAutorizzazioneToponimoM'),
			    			comune					: { idComune: $('#comuneToponimoM option:selected').val() },
							classe					: { id: $( '#classeToponimoMValue').val() == '' ? dettaglio.classe.id :  $("#"+self.idDialog+" #classeToponimoMValue").val()}, 
							note					: $('#noteToponimo').val() == '' ? null : $('#noteToponimo').val(),
			    			codice					: $('#codiceToponimoM').val() == '' ? null : $('#codiceToponimoM').val(), 
							geom					: sessionStorage.getItem('wktEditFeatureGeom')!=null && sessionStorage.getItem('wktEditFeatureGeom')!=""  
															? sessionStorage.getItem('wktEditFeatureGeom') : dettaglio.geom, 
							tipo					: { id: $('#tipoToponimoM').val()},
							stato					: dettaglio.stato,
							isCircle				: dettaglio.isCircle
		    		};
		    		/**SALVATAGGIO DELLA MODIFICA**/
					self.modificaToponimo(toponimoStradale).done(function (){
						/**Figli nuovi**/
						if ( JSON.parse(sessionStorage.getItem('hmEntityCut')) && JSON.parse(sessionStorage.getItem('hmEntityCut'))!=undefined ){
				    		var figli = JSON.parse(sessionStorage.getItem('hmEntityCut'));
				    		if (figli!=null || figli!=undefined){
				    			dettaglio.figli = [];
				    		    $.each(figli, function(index, toponimo){
				    		    	/**1.1 - Recupero la geometria**/
				    		    	var toponimoStradaleFiglio = {
				    		    			id						: null,
				    		    			idPadre					: self.idDettaglio,
				    		    			comuneLabel				: toponimo.comuneLabel,
											denominazione			: toponimo.denominazione,
											denominazioneUfficiale	: toponimo.denominazioneUfficiale,
											classeLabel				: toponimo.classeLabel,
											shapeLeng				: null,
											cap						: null,
											compendi				: null,
											numeroDelibera			: null,
											dataDelibera			: null,
											codiceAutorizzazione	: null,
											dataAutorizzazione		: null,
											comune					: { idComune: toponimo.comune },
											classe					: { id: toponimo.classe},
											note					: null,
											codice					: null, 
											geom					: toponimo.geom, 
											tipo					: null,
											stato					: "BOZZA"
				    		    	};
				    		    	self.inserisciToponimoFiglio(toponimoStradaleFiglio);
				    		    });
				    		}
						}
					});
				}
			});
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO *************** FINE **/
    		/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* INIZIO **/
    		$("#"+self.idDialog+" #salvaBtnToponimo").click(function(event) {
    			event.stopImmediatePropagation();
    			/**VERIFICHE PER IL SALVATAGGIO DELLA MODIFICA**/
    			if($('#classeToponimoM').val() == '' || $('#denominazione').val() == '' || $('#comuneToponimoM').val() == '') {
    				iziToast.error({
    	    			title: 'Attenzione',
    	    			theme: 'dark',
    	    			icon:'fa fa-times',
    	    			message: 'Devono essere compilati tutti i campi contrassegnati con *',
    	    			animateInside: false,
    	    			position: 'topCenter',
					});
				} else if(($('#dataDeliberaToponimoM').val() == '' ||  $('#numeroDeliberaToponimoM').val() == '') && 
							($('#dataAutorizzazioneToponimoM').val() != '' || $('#codiceAutorizzazioneToponimoM').val() != '')) {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Devi compilare i campi Data delibera e Numero delibera',
						animateInside: false,
						position: 'topCenter',
					});
    			} else {
    				/**CREO L'OGGETTO DA SALVARE (id null perché utile per definire la nuova entità)**/
    				var toponimoStradale = {id: null, 
    										comuneLabel: $('#comuneToponimoM option:selected').text(),
											denominazione: $('#denominazione').val() == '' ? null : $('#denominazione').val(),
											denominazioneUfficiale: $('#denominazioneUfficiale').val() == '' ? null : $('#denominazioneUfficiale').val(),
											classeLabel: $('#classeToponimoM').val(),
											shapeLeng: sessionStorage.getItem('wktEditFeatureGeom'),
    										cap: $('#capToponimoM').val() == '' ? null : $('#capToponimoM').val(),
    										compendi: $('#compendiToponimoM').val() == '' ? null : $('#compendiToponimoM').val(),
    										numeroDelibera: $('#numeroDeliberaToponimoM').val() == '' ? null : $('#numeroDeliberaToponimoM').val(),
    										dataDelibera: self.convertiDate('dataDeliberaToponimoM'),
    										codiceAutorizzazione: $('#codiceAutorizzazioneToponimoM').val() == '' ? null : $('#codiceAutorizzazioneToponimoM').val(),
    										dataAutorizzazione: self.convertiDate('dataAutorizzazioneToponimoM'),
    										comune: { idComune: $('#comuneToponimoM option:selected').val()}, 
    										classe: { id: $("#"+self.idDialog+" #classeToponimoMValue").val()}, 
    										note: $('#noteToponimo').val() == '' ? null : $('#noteToponimo').val(),
    										codice: $('#codiceToponimoM').val() == '' ? null : $('#codiceToponimoM').val(), 
    										geom: sessionStorage.getItem('wktEditFeatureGeom'), 
    										tipo: { id: $('#tipoToponimoM').val()},
											stato: "BOZZA",
											isCircle: self.properties.typeEditing.indexOf('Circle') != -1 ? true : false
    				};
    				/**SALVATAGGIO NUOVA ENTITA**/
    				self.inserisciToponimo(toponimoStradale);
    			}
			});
			$('#exportBtnToponimo').click(function(e) {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				/**RECUPERO PARAMETRI DI RICERCA**/
				var filtriRicerca = JSON.parse(sessionStorage.getItem('filtriRicercaShp'));
				toponomasticaRest.exportShpFile(filtriRicerca);
				
			});
    		/************************************************************************************************ AZIONI MODALE INSERIMENTO ************* FINE **/
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** INIZIO **/
    		$("#"+self.idDialog+" #indietroBtn").click(function(event) {
    			/**INIZIALIZZAZIONE**/
    			$("#ricercaAnagToponimo").show();
    			$("#dettaglioAnagToponimo").hide();
    			/**PULIZIA DELLE VARIABILI USATE NEL DETTAGLIO**/
    			self.idDettaglio = null;
    			/**BUTTON MODALE**/
				self.setButtonModale("ricerca");
				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('ricerca');
			});
    		/************************************************************************************************ AZIONI MODALE DETTAGLIO-INSERIMENTO *** FINE **/
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaGestioneToponimoCtrl');
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
	 * Metodo di utility per verificare se è numero, passandogli in input un valore. Il risultato
	 * true = è un numero
	 * false = non è un numero
	 * @param {*} numero 
	 */
	isNumeroInt(numero) {
		if(!isNaN(parseFloat(numero)) && isFinite(numero)) {
			return (numero % 1 == 0)
		}
		return false;
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
		var stato = $('#stato').val();
		var classe = $('#dug').val();
		var duf = $('#descrizioneToponimoStradale').val();
		// var comune = $('#comuneToponimoStradale').val();
		var numeroDelibera = $('#numeroDelibera').val();
		var codiceAutorizzazione = $('#codiceAutorizzazione').val();
		var dataDeliberaDal = $('#dataDeliberaToponimoDal').val();
		var dataDeliberaAl = $('#dataDeliberaToponimoAl').val();
		var dataAutorizzazioneDal = $('#dataAutorizzazioneToponimoDal').val();
		var dataAutorizzazioneAl = $('#dataAutorizzazioneToponimoDal').val();
		var numCivicoDxDa = $('#numCivicoDxDa').val();
		var numCivicoDxA = $('#numCivicoDxA').val();
		var numCivicoSxDa = $('#numCivicoSxDa').val();
		var numCivicoSxA = $('#numCivicoSxA').val();
		/**INIZIALIZZO TABELLA**/
		$('#tabellaToponimoStradale').DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ],
				postfixButtons: [ { extend: 'colvisRestore',
									text: '<hr size="1" style="margin-top: 6px">' +
										  '<button class="btn btn-primary btnColvisRestore">Visualizza tutto</button>',
								} ]
			} ],
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
				//url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaToponimo',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/ricercaToponimo',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var colonnaSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							colonnaSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {classe: classe.trim(),
								  stato: stato, 
								  denominazioneUfficiale: duf.trim(),
								  comune: '',
								  dataDeliberaDal: dataDeliberaDal,
								  dataDeliberaAl: dataDeliberaAl,
								  dataAutorizzazioneDal: dataAutorizzazioneDal,
								  dataAutorizzazioneAl: dataAutorizzazioneAl,
								  numeroDelibera: numeroDelibera.trim(),
								  codiceAutorizzazione: codiceAutorizzazione.trim(),
								  numCivicoDxDa: numCivicoDxDa,
								  numCivicoDxA: numCivicoDxA,
								  numCivicoSxDa: numCivicoSxDa,
								  numCivicoSxA: numCivicoSxA,
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: colonnaSort, dir: json.order[0].dir } }

					sessionStorage.setItem('filtriRicercaShp', JSON.stringify(filtri));
					
					return JSON.stringify(filtri);
				},
				cache : true,
				async: true
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(_d, _t, r) {return r.classeLabel;}, orderable : true, name: 'classeLabel'},
	        	{targets : 1, render: function(_d, _t, r) {return r.denominazioneUfficiale;}, width: '25%', orderable : true, name: 'denominazioneUfficiale' },
				{targets : 2, render: function(_d, _t, r) {return r.comuneLabel;}, orderable : true, name: 'comuneLabel' },
				{targets : 3, render: function(_d, _t, r) {return r.cap;}, orderable : true, name: 'cap'},
	        	{targets : 4, render: function(_d, _t, r) {return r.tipo.descrizione;}, width: '20%', orderable : true, name: 'tipo'},
				{targets : 5, render: function(_d, _t, r) {return r.shapeLeng != null ? r.shapeLeng > 1000 ? Number(r.shapeLeng / 1000).toFixed(2) + ' Km': Number(r.shapeLeng).toFixed(3) + ' m' : 'N.D';}, orderable: true, name: 'shapeLeng'},
				{targets : 6, render: function(_d, _t, r) {return r.codice;}, orderable: true, name: 'codice'},
				{targets : 7, render: function(_d, _t, r) {return r.numeroDelibera ;}, orderable : true, name: 'numeroDelibera'},
				{targets : 8, render: function(_d, _t, r) {return r.dataDelibera == null ? '' : new Date(r.dataDelibera).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'})}, orderable : true, name: 'dataDelibera'},
				{targets : 9, render: function(_d, _t, r) {return r.codiceAutorizzazione;}, orderable : true, name: 'codiceAutorizzazione'},
				{targets : 10, render: function(_d, _t, r) {return r.dataAutorizzazione == null ? '' : new Date(r.dataAutorizzazione).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'}); }, orderable : true, name: 'dataAutorizzazione'},
				{targets : 11, render: function(_d, _t, r) {return r.stato;}, orderable : true, name: 'stato'},
	        	{targets : 12, className: "text-right", orderable : false, render: function(_d, _t, r) {
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabella', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
					}
				}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	    	   	self.aggiungiEventiAiPulsantiToponimi();
				sessionStorage.setItem('ricercaToponimoStradale', JSON.stringify(settings.json.data));	
	       	}
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio del toponimo stradale. Il salvataggio di una modifica non cambierà lo stato dell'entità ma soltanto gli attributi
	 * - elimina, aprirà un box di conferma con richiesta di archiviazione rispettando i seguenti casi:
	 *     1. se si decide di archiviare allora update nella tabella u_topo_toponimo_stradale impostanto stato a ARCHIVIATO
	 *     2. se non si decide di archiviare si procede con eliminazione nella tabella u_topo_toponimo_stradale
	 * - localizza, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 * 
	 **/
	aggiungiEventiAiPulsantiToponimi() {
		var self = this;
		$('#tabellaToponimoStradale button.modifica-btn').off('click').on('click', function(_event){
			/**RIPULISCO VARIABILI**/
			sessionStorage.removeItem('hmEntityCut');
			self.properties.newEntita 	= false;
    		self.modified 				= false;
    		self.cuting 				= false;
			self.blockEditing 			= false;
			/**VISUALIZZA DETTAGLIO**/
			self.visualizzaDettaglio($(this).data('id'));
			self.inizializzaDatatableDocumenti($(this).data('id'), 1);
			self.inizializzaDatatableAccessi($(this).data('id'));
		});
		$("#"+self.idDialog+" #tabellaToponimoStradale button.elimina-btn").off('click').on('click', function(_event){
			/**ELIMINA TOPONIMO**/
			self.eliminaToponimo($(this).data('id'));
        });
		$("#"+self.idDialog+" #tabellaToponimoStradale button.localizza-btn").off('click').on('click', function(_event){
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
	 * Il metodo visualizza il dettaglio del toponimo stradale con id passato in input. La visualizzazione del dettaglio prevede una modale già aperta
	 * @param id, identificativo dell'entità toponimo stradale 
	 */
	visualizzaDettaglio(id) {
		var self = this;
		
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		if(id != null) {																							/**GESTIONE DI UN DETTAGLIO**/
			
			/**RECUPERO DEL DETTAGLIO**/
    		var dettaglio 				= self.recuperaDettaglio(id);				//Recupero il dettaglio del toponimo selezionato
    		self.idDettaglio 			= id;										//Salvo l'identificativo del dettaglio
			var toponimoStradaleMod 	= {};										//Creo l'oggetto toponimoStradaleMod utile per la modifica all'entità
			
			/**IMPOSTAZIONI DI PAGINA**/
			if (!self.modified && !self.cuting) self.aggiornaBreadcrumb('dettaglio', dettaglio.denominazioneUfficiale);
			$("#"+self.idDialog+" #ricercaAnagToponimo").hide();
			$("#"+self.idDialog+" #dettaglioAnagToponimo").show();
			$('#anagrafica-tab1').click();
			$("#"+self.idDialog+" #liDetAnagraficaTopo").show();
			$("#"+self.idDialog+" #liDetDocumentiTopo").show();
			$("#"+self.idDialog+" #liDetAccessiTopo").show();
			/**BUTTON MODALE**/
    		self.setButtonModale("dettaglio");
    		
    		/**GESTIONE DEL DETTAGLIO DA VISUALIZZARE**/
    		var toponimoStradaleMod = {
    							id: id, 
    							classe: dettaglio.classe == null ? '' : dettaglio.classe.dug,
								denominazione: dettaglio.denominazione == null ? '' : dettaglio.denominazione,
								denominazioneUfficiale:dettaglio.denominazioneUfficiale == null ? '' : dettaglio.denominazioneUfficiale,
    							comune:	dettaglio.comune == null ? '' : dettaglio.comune.idComune,
    							comuni: self.comuniMessina,
    							tipo: dettaglio.tipo == null ? '' : dettaglio.tipo.id,
    							tipi:self.tipiToponimi,
    							codice: dettaglio.codice == null ? '' : dettaglio.codice,
    							note: dettaglio.note == null ? '' : dettaglio.note,
    							dataDelibera: dettaglio.dataDelibera == null ? '' : new Date(dettaglio.dataDelibera).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'}),
    							numeroDelibera:dettaglio.numeroDelibera == null ? '' : dettaglio.numeroDelibera,
    							dataAutorizzazione: dettaglio.dataAutorizzazione == null ? '' : new Date(dettaglio.dataAutorizzazione).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'}),
    							codiceAutorizzazione: dettaglio.codiceAutorizzazione == null ? '' : dettaglio.codiceAutorizzazione,
    							cap: dettaglio.cap == null ? '' : dettaglio.cap,
    							compendi: dettaglio.compendi == null ? '' : dettaglio.compendi,
    							geom:dettaglio.geom
    		};
    		var htmlDet = compilaTemplate('modaleDettaglioToponimi', toponimoStradaleMod);
			$("#dettaglio").html(htmlDet);
			//appUtil.activeAutocompleteOnFiled('classeToponimoM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...');
			appUtil.activeAutocompleteOnFiled('classeToponimoM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...');

			/**RECUPERO EVENTUALI TOPONIMI FIGLI**/
    		if ( sessionStorage.getItem('hmEntityCut')!=null || sessionStorage.getItem('hmEntityCut')!=undefined ){
    			
	    		/**FIGLI NUOVI, SE VENGO DA UN CUTING**/
	    		var figli = JSON.parse(sessionStorage.getItem('hmEntityCut'));
	    		if (figli!=null || figli!=undefined){
	    			toponimoStradaleMod.figli = [];
	    		    $.each(figli, function(index, value){
	    		    	toponimoStradaleMod.figli.push(value);
	    		    });
	    		    $("#figliToponimoTable").html(compilaTemplate('figliToponimo', toponimoStradaleMod));
	    		}
			} else {
				
				/**FIGLI VECCHI, SE PROVENGONO DAL DATABASE**/
				sessionStorage.setItem('figliToponimo',null);
				self.caricaFigliToponimo(toponimoStradaleMod.id).done(function (response){
					/**Figli vecchi**/
					var pubblicaFigliToponimo = true;
	    			var figli = JSON.parse(sessionStorage.getItem('figliToponimo'));
		    		if (figli!=null || figli!=undefined){
		    			toponimoStradaleMod.figli = [];
		    		    $.each(figli, function(index, value){
		    		    	toponimoStradaleMod.figli.push(value);
		    		    	if ( (value.stato).indexOf("BOZZA")!=-1 ) 
		    		    		pubblicaFigliToponimo = false;
		    		    });
		    		    if (toponimoStradaleMod.figli.length>0) {
		    		    	/**1. Aggiungo i valori in tabella compilando il template**/
		    		        $("#figliToponimoTable").html(compilaTemplate('figliToponimo', toponimoStradaleMod));
		    		        /**2. Abilito il button per pubblicare i figli e dismettere il padre**/
		    		        if (pubblicaFigliToponimo) $("#pubblicaFigliToponimo").show(); 
		    		    }
		    		}
				});
				
			}
			/** INIZIALIZZO I DATAPICKER DEL DETTAGLIO */  
			$("#"+self.idDialog+" #dataDeliberaToponimoM").datepicker({dateFormat: 'dd/mm/yy'});
			$("#"+self.idDialog+" #dataAutorizzazioneToponimoM").datepicker({dateFormat: 'dd/mm/yy'});
			/** INIZIALIZZO LE SELECT CON GLI ID DEL TIPO E DEL COMUNE */
			$("#tipoToponimoM").val(dettaglio.tipo == null ? '' : dettaglio.tipo.id);
			$("#comuneToponimoM").val(dettaglio.comune == null ? '' : dettaglio.comune.idComune);
			/** KEYUP DEGLI INPUT DELL'INSERIMENTO */
			// $('#descrizioneToponimoM').keyup(function(){
			// 	$(this).val($(this).val().toUpperCase());
			// });
			$('#classeToponimoM').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#denominazione').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#codiceToponimoM').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			
			/** POPOLAMENTO CAMPO DENOMINAZIONE UFFICIALE CON IL DUG + DENOMINAZIONE */
			$('#classeToponimoM').change(function(e) {
				$(this).data('dug', $(this).val());
				$('#denominazioneUfficiale').val($(this).val() + ' ' + $('#denominazione').data('denominazione'));
			});
			$('#denominazione').change(function(e) {
				$(this).data('denominazione', $(this).val());
				$('#denominazioneUfficiale').val($('#classeToponimoM').data('dug') + ' ' + $(this).val()); 
			});

			
			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita = false;
			(self.properties).geometryWKT = dettaglio.geom == null || dettaglio.isCircle ? null : dettaglio.geom;
			(self.properties).stato = dettaglio.stato == null ? null : dettaglio.stato;
			(self.properties).typeEditing = dettaglio.isCircle ? 'Circle' : 'LineString';
			
    		/************************************************************************************************ AZIONI DETTAGLIO ************** INIZIO **/
			/** VISUALIZZA IL TOPONIMO **/
			$("#"+self.idDialog+" .localizza-geo-toponimo-btn").click(function(_event) {
				/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
				self.getGeometry(dettaglio.geom);
			})
			if (self.blockEditing)
				$("#"+self.idDialog+" .editing-toponimo-btn").hide();
			/** EDITING DEL TOPONIMO **/
			$("#"+self.idDialog+" .editing-toponimo-btn").click(function(_event) {
				self.properties.newEntita 	= false;
				self.confermaDisegno 		= false;
				sessionStorage.removeItem('hmEntityCut')
				/**FACCIO VISUALIZZARE PER PRIMA LA MAPPA PER POTER DISEGNARE LA GEOMETRIA**/
				self.riduciAdIconaTutteLeModali();
				/**ABILITA DISEGNO SU MAPPA DI UNA GEOMETRIA PRESENTE SOLO SU INTERFACCIA**/
				var geomWKT = dettaglio != undefined ? dettaglio.geom : null;

				self.avviaEditingToponimo(geomWKT);
			});
			/** EDITING DEL TOPONIMO **/
			$("#"+self.idDialog+" .formalizes-toponimo-figli-btn").click(function(_event) {
				self.pubblicaNuoviToponimi(dettaglio);
			});
			/** TAB ACCESSI**/
			$("#"+self.idDialog+" #accessoBtnToponimo").click(function() {
				self.anagraficaAccesso();
			})
			/** TAB DOCUMENTI**/
			$("#"+self.idDialog+" #documentoBtnToponimo").click(function() {
				self.uploadDocumento(self.idDettaglio, 1);
			})
			/************************************************************************************************ AZIONI DETTAGLIO ************** FINE **/
		} else {																							/**GESTIONE DI UN INSERIMENTO**/
			
			/**IMPOSTAZIONI DI PAGINA**/
			if($('#'+self.idDialog+' ul.breadcrumb li:last-child').text().indexOf('Toponimo') != -1) {
				self.aggiornaBreadcrumb('nuovo')
			} else {
				$('#'+this.idDialog+' ul.breadcrumb li:last-child').remove();
				self.aggiornaBreadcrumb('nuovo')
			}
			$('#anagrafica-tab1').click();
			$("#"+self.idDialog+" #ricercaAnagToponimo").hide();
			$("#"+self.idDialog+" #dettaglioAnagToponimo").show();
			$("#"+self.idDialog+" #liDetDocumentiTopo").hide();
			$("#"+self.idDialog+" #liDetAccessiTopo").hide();
			
			/**GESTIONE DEL DETTAGLIO DA VISUALIZZARE**/
    		var toponimoStradaleIns = {comuni:self.comuniMessina,tipi:self.tipiToponimi};
    		var htmlDet = compilaTemplate('modaleDettaglioToponimi', toponimoStradaleIns);
			$("#dettaglio").html(htmlDet);
			
			/** INIZIALIZZO I DATAPICKER PER L'INSERIMENTO */
			$("#"+self.idDialog+" #dataDeliberaToponimoM").datepicker({dateFormat: 'dd/mm/yy'});
			$("#"+self.idDialog+" #dataAutorizzazioneToponimoM").datepicker({dateFormat: 'dd/mm/yy'});
			/** KEYUP DEGLI INPUT DELL'INSERIMENTO */
			// $('#descrizioneToponimoM').keyup(function(){
			// 	$(this).val($(this).val().toUpperCase());
			// });
			$('#classeToponimoM').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#denominazione').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			$('#codiceToponimoM').keyup(function(){
				$(this).val($(this).val().toUpperCase());
			});
			/** AUTOCOMPLETE DUG */
			//appUtil.activeAutocompleteOnFiled('classeToponimoM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...');
			appUtil.activeAutocompleteOnFiled('classeToponimoM', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...');
			
			$('#classeToponimoM').change(function(e) {
				$(this).data('dug', $(this).val());
				$('#denominazioneUfficiale').val($(this).val() + ' ' + $('#denominazione').data('denominazione'));
			});
			$('#denominazione').change(function(e) {
				$(this).data('denominazione', $(this).val());
				$('#denominazioneUfficiale').val($('#classeToponimoM').data('dug') + ' ' + $(this).val()); 
			})

			/**BUTTON MODALE**/
    		self.setButtonModale("inserimento");
    		
    		/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.properties).newEntita = true;
			(self.properties).geometryWKT = null;
			(self.properties).stato = null;
    		/************************************************************************************************ AZIONI NUOVO ************** INIZIO **/
    		/** EDITING DEL NUOVO TOPONIMO **/
    		$("#"+self.idDialog+" .editing-toponimo-btn").click(function(_event) {
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
					self.avviaEditingToponimo(geomWKT);
    			}
			});
		}
		appUtil.hideLoader();	
	}

	/**
	 * Metodo di utility per la conversione da stringa a data
	 * @data, è una stringa che rappresenta la data dd/mm/yyyy
	 **/
	convertiDate(data) {
		var self = this;
		var dataDaConvertire = $("#"+self.idDialog+" #" + data).val();
		var dateParts = dataDaConvertire.split("/");
		return new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
	}
	
	/**
	 * Metodo per il recupero del dettaglio di un toponimo stradale dato l'id
	 * @id, identificativo del toponimo stradale
	 */
	recuperaDettaglio(id) {
		let toponimo = JSON.parse(sessionStorage.getItem('ricercaToponimoStradale'));
		let dettaglioToponimo;
		for(var i=0; i<toponimo.length; i++){
			if(toponimo[i].id === id){
				dettaglioToponimo = toponimo[i];
				break;
			}
		}
		return dettaglioToponimo;
	}
	
	/**
	 * Il metodo inizializza la tabella dei documenti legata con idToponimo e tipo.
	 * @param idToponimo è l'indentficativo del toponimo stradale.
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
	inizializzaDatatableDocumenti(idToponimo, tipo) {
		var self = this;
		$("#tabellaDocumentiToponimo").DataTable({
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
								  idRisorsa: idToponimo,
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: idToponimo, nomeFile: r.nomeDocumento});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {
	        	self.aggiungiEventiAiPulsantiDocumentiToponimo();
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
	aggiungiEventiAiPulsantiDocumentiToponimo() {
		var self = this;
		$("#tabellaDocumentiToponimo button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('id'), 1, $(this).data('nome'));
        });
		
		$("#tabellaDocumentiToponimo button.download-documento-btn").off('click').on('click', function(_event){
			self.downloadDocumento($(this).data('nome'), $(this).data('id'), 1);
        });
	}
	
	/**
	 * Il metodo apre una modale con un form di upload del file.
	 * @param idToponimo indentifica il toponimo stradale
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
	uploadDocumento(idToponimo, tipo) {
		var self = this;
    	openStaticDetailViaHandlebars('uploadDocumento', 'modaleUploadDocumentiToponimo', {}, 'Upload', function(){
    		appUtil.reloadTooltips();
			$('#uploadDocumentoBtn').click(function() {
				self.inserisciDocumento(idToponimo, tipo);
			})
    	}, {width: 350, height: 155, resizable: false, modale: true, closable: true});
		
	}
	
	/**
	 * Il metodo carica sul server il documento legandolo all'id identificativo del toponimo stradale e al tipo di risorsa.
	 * @param idToponimo indentifica il toponimo stradale
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
	inserisciDocumento(idToponimo, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var fd = new FormData($('#uploadForm')[0]);
		var files = $('#file')[0].files[0];
        fd.append('file',files);
		
		setTimeout(function(){
			$.when(toponomasticaRest.uploadFile(fd, idToponimo, tipo)).done(function (response) {
				appUtil.hideLoader()
				if(response.success) {
					appUtil.hideLoader();
		        	$('#uploadDocumento').dialog('close');
		        	self.ricaricaDataTable('tabellaDocumentiToponimo')
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
	 * @param idToponimo è l'id del toponimo stradale
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
	downloadDocumento(nomeFile, idToponimo, tipo) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();
			toponomasticaRest.downloadFile(nomeFile, idToponimo, tipo); //DOWNLOAD DEL FILE
		})
	}
	
	/**
	 * Il metodo elimina dal server il documento.
	 * @param nomeFile il nome del file
	 * @param idToponimo è l'id del toponimo stradale
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
	eliminaDocumento(idToponimo, tipo, nomeFile) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaDocumento(idToponimo, tipo, nomeFile)).done(function (response) {
					appUtil.hideLoader()
					if(response.success) {										 //SE LA RISPOSTA DEL SERVER E' POSITIVA
						self.ricaricaDataTable('tabellaDocumentiToponimo');      //RICARICO DATATABLE
						iziToast.show({											 //MESSAGGIO
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Documento eliminato con successo!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {													//SE LA RISPSOTA DEL SERVER E' NEGATIVA
						iziToast.error({											//MESSAGGIO
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
	 * Il metodo inizializza la tabella degli accessi legata all'idToponimo.
	 * @param idToponimo identificativo del toponimo stradale
	 */
	inizializzaDatatableAccessi(idToponimo) {
		var self = this;
		$("#tabellaAccessiToponimo").DataTable({
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
					var filtri = {idToponimo: idToponimo,
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: colonnaSort, dir: json.order[0].dir } };
					
					return JSON.stringify(filtri);
				}  
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, name: 'localita', render: function(_d, _t, r) {return r.localita == null ? '' : r.localita.descrizione;}, orderable : true},
	        	{targets : 1, name: 'numero', render: function(_d, _t, r) {return r.numero;}, orderable : true},
	        	{targets : 2, name: 'esponente', render: function(_d, _t, r) {return r.esponente;}, orderable : true},
	        	{targets : 3, name: 'tipo', render: function(_d, _t, r) {return r.tipo == null ? '' : r.tipo.descrizione;}, orderable : true},
	        	{targets : 4, name: 'passoCarrabile', render: function(_d, _t, r) {return r.passoCarrabile ? 'SI' : 'NO';}, orderable : true},
	        	{targets : 5, name: 'principale', render: function(_d, _t, r) {return r.principale ? 'SI' : 'NO';}, orderable : true},
	        	{targets : 6, name: 'note', render: function(_d, _t, r) {return r.note;}, orderable: true},
	        	{targets : 7, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaAccessi', {id: r.id, idEntity: r.toponimo.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	sessionStorage.setItem('ricercaAccessi', JSON.stringify(settings.json.data));
	        	self.aggiungiEventiAiPulsantiAzioneAccessi();
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - anagrafica-accesso, apre un modale di inserimento/dettaglio dell'accesso.
	 * - elimina-accesso, aprirà un box di conferma con richiesta di eliminazione rispettando i seguenti casi:
	 *     1. se si decide di procedere allora verrà eliminato dalla tabella u_topo_accesso
	 * 	   2. se non si decide di procedere l'operazione verrà annullata
	 * - localizza-accesso, viene richiamato il metodo getGeometry che ridurrà la modale mostrando su mappa la geometria
	 **/
	aggiungiEventiAiPulsantiAzioneAccessi() {
		var self = this;
		
		$("#tabellaAccessiToponimo button.anagrafica-accesso-btn").off('click').on('click', function(_event){
			self.anagraficaAccesso($(this).data('id'));
        });
		
		$("#tabellaAccessiToponimo button.elimina-accesso-btn").off('click').on('click', function(_event){
			self.eliminaAccesso($(this).data('id'), $(this).data('entity'));
        });
		
		$("#tabellaAccessiToponimo button.localizza-accesso-btn").off('click').on('click', function(_event){
			self.getGeometry($(this).data('geometry'));
        });
	}
	
	/**
	 * Il metodo apre una modale per l'anagrafica accessi, nella quale vengono impostati tutte le informazioni neccessarie 
	 * per l'inserimento/dettaglio dell'accesso.
	 * @param idAccesso
	 */
	anagraficaAccesso(idAccesso) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

		setTimeout(function() {									//AGGIUNGE UN TIMEOUT PER IL LOADER
			
			appUtil.hideLoader();								//NASCONDO IL LOADER
			
			/**Recupero dei dati di base della modale anagrafica accesso**/
			/**************************** LOCALITA' **************************/
			//var hrefToponomasticaLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/findAllLocalita';
			//var UrbamidResponseLocalita = appRest.restUrbamid(hrefToponomasticaLocalitaToponimoUrbamid, "GET", null);
			//var Localita = UrbamidResponseLocalita.aaData;
			///**************************** TIPO ACCESSO **************************/
			//var hrefToponomasticaTipoAccessoToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getTipoAccesso';
			var hrefToponomasticaLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/findAllLocalita';
			var UrbamidResponseLocalita = appRest.restUrbamid(hrefToponomasticaLocalitaToponimoUrbamid, "GET", null);
			var Localita = UrbamidResponseLocalita.aaData;
			/**************************** TIPO ACCESSO **************************/
			var hrefToponomasticaTipoAccessoToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getTipoAccesso';
			var UrbamidResponseTipoAccessi = appRest.restUrbamid(hrefToponomasticaTipoAccessoToponimoUrbamid, "GET", null);
			var tipoAccessi = UrbamidResponseTipoAccessi.aaData;
		
			openStaticDetailViaHandlebars('anagraficaAccesso', 'modaleAnagraficaAccessi', {localita: Localita, tipoAccesso: tipoAccessi}, 'Anagrafica Accessi', function(){
								
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

				//CHIUDE LA MODALE ANAGRAFICA ACCESSO
				$('#anagraficaAccesso #chiudiBtn').on('click', function(){
					drawLayerSource.clear();
					$('#anagraficaAccesso').dialog('close');
				});

				//VERIFICO SE L'ID E' DIVERSO DA NULL, ALLORA SETTO TUTTI I CAMPI IN READONLY
				if(idAccesso != null) {
								
					/** NASCONDO I PULSANTI */
					$('#anagraficaAccesso .absPulsante').hide();

					//RECUPERO LE INFORMAZIONI DEGLI ACCESSI
					let accessi = JSON.parse(sessionStorage.getItem('ricercaAccessi'));
					let dettaglioAccessi;
					for(var i=0; i<accessi.length; i++){
						if(accessi[i].id === idAccesso){
							dettaglioAccessi = accessi[i];
							break;
						}
					}

					/** CREO L'OGGETTO DA FAR VISUALIZZARE */
					var detAccessi = {hiddenToponimo: 'hidden',
								  	  numero: dettaglioAccessi.numero == null ? '' : dettaglioAccessi.numero,
								  	  esponente: dettaglioAccessi.esponente == null ? '' : dettaglioAccessi.esponente,
								  	  localita: self.localita,
								  	  localitaID: dettaglioAccessi.localita == null ? '' : dettaglioAccessi.localita.id,
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
					$('#anagraficaAccesso #localitaAccessoM').val(dettaglioAccessi.localita == null ? '' : dettaglioAccessi.localita.id);
					$('#anagraficaAccesso #tipoAccessoM').val(dettaglioAccessi.tipo == null ? '' : dettaglioAccessi.tipo.id);	
	    		
				} else {
					
					//INIZIALIZZO LE VARIABILI A FALSE
					var passoCarrabile = false;
					var principale = false;
	    		
					//VISUALIZZO I PULSANTI
					$('#anagraficaAccesso .absPulsante').show();
				
					/** CREO L'OGGETTO DA FAR VISUALIZZARE */
					var detAccessi = {localita: self.localita, tipoAccesso: self.tipoAccessi, hidden: 'hidden', hiddenToponimo: 'hidden'}
					let html = compilaTemplate('modaleDettaglioAccesso', detAccessi);
					$('#anagraficaAccesso #dettaglio').html(html);
				
					//CLICK DEL PULANTE SALVA
					$('#salvaAccessiBtn').click(function() {
							
						/** SE IL CHECKBOX PASSOCARRABILE E' SU CHECKED ALLORA IMPOSTO LA VARIABILE SU TRUE */
						if($('#accessoPassoCarrabile').is(":checked")) {
							passoCarrabile = true;
						}
						/** SE IL CHECKBOX PRINCIPALE E' SU CHECKED ALLORA IMPOSTO LA VARIABILE SU TRUE */
						if($('#accessoPrincipale').is(":checked")) {
							principale = true;
						}	
						
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
							//CREO L'OGGETTO DA MANDARE AL SERVER CON ID NULL, UTILE PER CREARE UNA NUOVA ENTITA'
							var accesso = { id: null,
											toponimo: { id: self.idDettaglio },
											numero: $('#accessoNumCivico').val(),
											esponente: $('#esponenteCivico').val(),
											localita: { id: $('#localitaAccessoM option:selected').val() },
											tipo: { id: $('#tipoAccessoM option:selected').val() },
											passoCarrabile: passoCarrabile,
											principale: principale,
											metodo: $('#accessoMetodo').val(),
											geom: null,
											note: $('#accessoNote').val() };
							
							//RICHIAMO LA FUNZIONALITA' INSERISCI ACCESSO
							self.inserisciAccesso(accesso);
						}
					})
				}
				appUtil.reloadTooltips(); 							//INIZIALIZZO I TOOLTIPS
    		
			}, {width: 700, height: 370, resizable: false, modale: true, closable: true}); //IMPOSTO I VALORI DELLA MODALE
		}, 600)	
	}
	
	/**
	 * Il metodo salva l'accesso e riporta l'utente sulla pagina di ricerca degli accessi. 
	 * @param accesso, è un oggetto contentente le informazioni da inserire
	 */
	inserisciAccesso(accesso) {
		var self = this;
		setTimeout(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			$.when(toponomasticaRest.inserisciAccesso(accesso)).done(function (response) {
				appUtil.hideLoader()
				if(response.success) {
					appUtil.hideLoader();
		        	$('#anagraficaAccesso').dialog('close');
		        	self.ricaricaDataTable('tabellaAccessiToponimo')
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

	/** 
	 * Il metodo elimina un accesso richiedendo se deve continuare con l'eliminazione o meno. 
	 * In seguito all'operazione l'utente verrà riportato sulla pagina di ricerca degli accessi. 
	 * @param id, identificativo dell'accesso
	 * @param idToponimo, indentificato del toponimo stradale
	 */
	eliminaAccesso(id, idToponimo) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				$.when(toponomasticaRest.eliminaAccessoByToponimo(id, idToponimo)).done(function (response) {
					if(response.success) {											//SE LA RISPOSTA DAL SERVER E' POSITIVA
						appUtil.hideLoader()										//NASCONDO IL LOADER
						self.ricaricaDataTable('tabellaAccessiToponimo');			//RICARICA DATATABLE ANAGRAFICA ACCESSI
						iziToast.show({												//MESSAGGIO
			    			title: 'OK',	
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: 'Accesso eliminato con successo!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {														//SE LA RISPOSTA DAL SERVER E' NEGATIVA
						appUtil.hideLoader()										//NASCONDO IL LOADER
						iziToast.error({												//MESSAGGIO
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
	 * Il metodo salva il toponimo stradale e riporta l'utente sulla pagina di ricerca. 
	 * @param toponimoStradale, è un oggetto contentente le informazioni di un toponimo stradale da inserire
	 */
	inserisciToponimo(toponimoStradale) {
		var self = this;
		
		var publish = (toponimoStradale.stato.indexOf("PUBBLICATO")!=-1 &&    
				toponimoStradale.numeroDelibera!=null && toponimoStradale.dataDelibera.getTime()!=NaN && 
				toponimoStradale.codiceAutorizzazione!=null && toponimoStradale.dataAutorizzazione.getTime()!=NaN) ? true : false;
		appUtil.confirmInserisciPubblicaOperation(publish, function() {	
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

				var pubblicaToponimo = $("#publish").val()==null || $("#publish").val()==undefined ? false: $("#publish").prop("checked");
				$.when(toponomasticaRest.inserisciToponimo(toponimoStradale, pubblicaToponimo)).done(function (response) {
					if(response.success) {
						/**INIZIALIZZAZIONE**/
						sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
		    			$("#ricercaAnagToponimo").show();							//Visualizzo la ricerca
						$("#dettaglioAnagToponimo").hide();							//Nascondo il dettaglio
						self.ricaricaDataTable('tabellaToponimoStradale');			//Ricarico DataTable
						// $("#ricercaBtnToponimo").click();							//Effettuo ricerca 
		    			self.aggiornaBreadcrumb('ricerca');							//Aggiorno Breadcrumb
		    			/**BUTTON MODALE**/
			    		self.setButtonModale("ricerca");							
			    		/**MESSAGGIO**/
						iziToast.show({
				    		title: 'OK',
				    		theme: 'dark',
				    		icon:'fa fa-check',
				    		message: 'Toponimo: ' + toponimoStradale.denominazioneUfficiale +  ' aggiunto con successo',
				    		animateInside: false,
				    		position: 'topCenter',
				    	});
					} else {
						/**MESSAGGIO**/
						iziToast.error({
				    		title: 'Attenzione',
				    		theme: 'dark',
				    		icon:'fa fa-times',
				    		message: 'Errore nell\'inserimento del toponimo ' + toponimoStradale.denominazioneUfficiale,
				    		animateInside: false,
				    		position: 'topCenter',
				    	});
					}
					appUtil.hideLoader();
				})
		}, function() {
			//ANNULLA
			appUtil.hideLoader();
		}, {}, 'Sei sicuro di voler salvare il toponimo stradale?');
	}	
	
	/**
	 * Il metodo salva il toponimo stradale e riporta l'utente sulla pagina di ricerca. 
	 * @param toponimoStradale, è un oggetto contentente le informazioni di un toponimo stradale da inserire
	 */
	inserisciToponimoFiglio(toponimoStradale) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(toponomasticaRest.inserisciToponimo(toponimoStradale, false)).done(function (response) {
			if(response.success) {
				self.ricaricaDataTable('tabellaToponimoStradale')
	    		/**MESSAGGIO**/
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Toponimo: ' + toponimoStradale.denominazioneUfficiale +  ' aggiunto con successo',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			} else {
				/**MESSAGGIO**/
				iziToast.error({
		    		title: 'Attenzione',
		    		theme: 'dark',
		    		icon:'fa fa-times',
		    		message: 'Errore nell\'inserimento del toponimo ' + toponimoStradale.denominazioneUfficiale,
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
			appUtil.hideLoader();
		})
	}	
	
	/**
	 * Il metodo salva la modifica di un toponimo stradale e riporta l'utente sulla pagina di ricerca. 
	 * @param toponimoStradaleM, è un oggetto contentente le informazioni di un toponimo stradale da modificare
	 */
	caricaFigliToponimo(id) {
		var self = this;
		var dfrd = $.Deferred();
		if (id!=null) {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.</br>Stiamo caricando i nuovi toponimi stradali associati');
			$.when( toponomasticaRest.getToponimoFigli(id) ).done(function (response) {
				var message = "Errore nel recuperare i Toponimi Stradali associati";
				if(response.success) {
					sessionStorage.setItem('figliToponimo',JSON.stringify(response.aaData) );
		    		if (response.aaData.length>0) message = "Sono stati caricati i nuovi Toponimi stradali associati";
		    		else message = "Il toponimo stradale non ha nuovi Toponimi Stradali associati";
					/**MESSAGGIO**/
					iziToast.show({
			    		title: 'OK',
			    		theme: 'dark',
			    		icon:'fa fa-check',
			    		message: message,
			    		animateInside: false,
			    		position: 'topCenter',
			    	});
					appUtil.hideLoader();
				} else {
					sessionStorage.setItem('figliToponimo',null);
					iziToast.error({
			    		title: 'Attenzione',
			    		theme: 'dark',
			    		icon:'fa fa-times',
			    		message: message,
			    		animateInside: false,
			    		position: 'topCenter',
			    	});
					appUtil.hideLoader();
				}
				dfrd.resolve();
			})
		}
		return $.when(dfrd).done(function(){}).promise();
	}
	
	/**
	 * Il metodo salva la modifica di un toponimo stradale e riporta l'utente sulla pagina di ricerca. 
	 * @param toponimoStradaleM, è un oggetto contentente le informazioni di un toponimo stradale da modificare
	 */
	modificaToponimo(toponimoStradaleM) {
		var self = this;
		var dfrd = $.Deferred();
		
		if ( (toponimoStradaleM.stato).indexOf("BOZZA")!=-1 ) {
			
			var publish = (toponimoStradaleM.stato.indexOf("BOZZA")!=-1 &&
							toponimoStradaleM.numeroDelibera!=null && toponimoStradaleM.numeroDelibera!="" && 
							toponimoStradaleM.dataDelibera!=null && toponimoStradaleM.dataDelibera!="" && 
							toponimoStradaleM.codiceAutorizzazione!=null && toponimoStradaleM.codiceAutorizzazione!="" && 
							toponimoStradaleM.dataAutorizzazione!=null && toponimoStradaleM.dataAutorizzazione!="") ? true : false;
			appUtil.confirmInserisciPubblicaOperation(publish, function() {	
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			
					var pubblicaToponimo = $("#publish").val()==null || $("#publish").val()==undefined ? false: $("#publish").prop("checked");
					$.when(toponomasticaRest.inserisciToponimo(toponimoStradaleM, pubblicaToponimo)).done(function (response) {
						if(response.success) {
							
							/**INIZIALIZZAZIONE**/
							sessionStorage.removeItem('wktEditFeatureGeom');			//Svuolo la sessionStorage
							$("#ricercaAnagToponimo").show();							//Visualizzo la ricerca
							$("#dettaglioAnagToponimo").hide();							//Nascondo il dettaglio
							self.ricaricaDataTable('tabellaToponimoStradale');			//Ricarico DataTable
							self.aggiornaBreadcrumb('ricerca');							//Aggiorno Breadcrumb
							/**BUTTON MODALE**/
				    		self.setButtonModale("ricerca");							
				    		/**MESSAGGIO**/
							iziToast.show({
					    		title: 'OK',
					    		theme: 'dark',
					    		icon:'fa fa-check',
					    		message: 'Toponimo: ' + toponimoStradaleM.denominazioneUfficiale +  ' modificato con successo',
					    		animateInside: false,
					    		position: 'topCenter',
					    	});
							appUtil.hideLoader();
						} else {
							
							iziToast.error({
					    		title: 'Attenzione',
					    		theme: 'dark',
					    		icon:'fa fa-times',
					    		message: 'Errore nella modifica del toponimo ' + toponimoStradaleM.denominazioneUfficiale,
					    		animateInside: false,
					    		position: 'topCenter',
					    	});
							appUtil.hideLoader();
						}
						dfrd.resolve();
					})
			}, function() {
				//ANNULLA
				appUtil.hideLoader();
				return false;
			}, {}, 'Sei sicuro di voler salvare il toponimo stradale?');
		
			
		} else {

			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.</br>Stiamo salvando i nuovi toponimi stradali');
			$.when(toponomasticaRest.inserisciToponimo(toponimoStradaleM, false)).done(function (response) {
				if(response.success) {
					self.ricaricaDataTable('tabellaToponimoStradale');
		    		/**MESSAGGIO**/
					iziToast.show({
			    		title: 'OK',
			    		theme: 'dark',
			    		icon:'fa fa-check',
			    		message: 'Toponimo: ' + toponimoStradaleM.denominazioneUfficiale +  ' modificato con successo',
			    		animateInside: false,
			    		position: 'topCenter',
			    	});
					appUtil.hideLoader();
				} else {
					
					iziToast.error({
			    		title: 'Attenzione',
			    		theme: 'dark',
			    		icon:'fa fa-times',
			    		message: 'Errore nella modifica del toponimo ' + toponimoStradaleM.denominazioneUfficiale,
			    		animateInside: false,
			    		position: 'topCenter',
			    	});
					appUtil.hideLoader();
				}
				dfrd.resolve();
			})
		
		}
		return $.when(dfrd).done(function(){}).promise();
	}
	
	/**
	 * @param toponimoStradale, toponimo stradale da archiviare con i figli da ufficializzare
	 */
	pubblicaNuoviToponimi(toponimoStradale) {
		var self = this;
		
		self.caricaFigliToponimo(toponimoStradale.id).done(function (response){
			
			var figli = JSON.parse(sessionStorage.getItem('figliToponimo'));
    		if (figli!=null || figli!=undefined){
    			toponimoStradale.figli = [];
    		    $.each(figli, function(index, value){
    		    	toponimoStradale.figli.push(value);
    		    });
    		    
				var archiviaToponimo = true;
				$.when(toponomasticaRest.eliminaToponimo(toponimoStradale.id, true)).done(function (response) {
					if(response.success) {
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Il toponimo '+toponimoStradale.denominazioneUfficiale+' &eacute; stato archiviato',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
						$.each(toponimoStradale.figli, function(index, figlio){
							$.when(toponomasticaRest.inserisciToponimo(figlio, true)).done(function (response) {
								if(response.success) {
									
									iziToast.show({
						    			title: 'OK',
						    			theme: 'dark',
						    			icon:'fa fa-times',
						    			message: 'Il toponimo '+figlio.denominazioneUfficiale+' &eacute; in stato ufficiale',
						    			animateInside: false,
						    			position: 'topCenter',
						    		});
								} else {
									
									iziToast.error({
						    			title: 'Attenzione',
						    			theme: 'dark',
						    			icon:'fa fa-times',
						    			message: 'Il toponimo '+figlio.denominazioneUfficiale+' non &eacute; in stato ufficiale',
						    			animateInside: false,
						    			position: 'topCenter',
						    		});
								}
							});
						});
					} else {
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Il toponimo '+toponimoStradale.denominazioneUfficiale+' non &eacute; stato archiviato',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
					self.ricaricaDataTable('tabellaToponimoStradale');
					appUtil.hideLoader();
				});
    		}
		});
	}
	
	/**
	 * Il metodo elimina un toponimo stradale richiedendo se deve archiviare o meno. In seguito all'operazione l'utente viene riportato sulla pagina di ricerca. 
	 * @param id, identificativo del toponimo stradale
	 */
	eliminaToponimo(id) {
		var self = this;
		var dettaglio 				= self.recuperaDettaglio(id);
		if( (dettaglio.stato).indexOf("BOZZA")!=-1 ) {
			appUtil.confirmOperation(function() {	
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			
				var archiviaToponimo = false;
				$.when(toponomasticaRest.eliminaToponimo(id, archiviaToponimo, dettaglio.denominazioneUfficiale)).done(function (response) {
					if(response.success) {
		    			self.ricaricaDataTable('tabellaToponimoStradale');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: archiviaToponimo ? 'Toponimo stradale archiviato con successo' : 'Toponimo stradale eliminato con successo',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Errore nella cancellazione e/o accesso assocciato al toponimo stradale!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
					appUtil.hideLoader();
				})

			}, function() {
				//ANNULLA
			}, {}, 'Sei sicuro di voler procedere alla cancellazione del toponimo stradale?');
		} else if ( (dettaglio.stato).indexOf("PUBBLICATO")!=-1 ){
			appUtil.confirmDeleteArchiveOperation(function() {	
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			
				var archiviaToponimo = $("#archive").prop("checked");
				$.when(toponomasticaRest.eliminaToponimo(id, archiviaToponimo)).done(function (response) {
					if(response.success) {
		    			self.ricaricaDataTable('tabellaToponimoStradale');
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: archiviaToponimo ? 'Toponimo stradale archiviato con successo' : 'Toponimo stradale eliminato con successo',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					} else {
						iziToast.error({
			    			title: 'Attenzione',
			    			theme: 'dark',
			    			icon:'fa fa-times',
			    			message: 'Errore nella cancellazione e/o accesso assocciato al toponimo stradale!',
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
					}
					appUtil.hideLoader();
				})

			}, function() {
				//ANNULLA
			}, {}, 'Sei sicuro di voler procedere alla cancellazione del toponimo stradale?');
		} else if ( (dettaglio.stato).indexOf("ARCHIVIATO")!=-1 ) {
			appUtil.showMessageAlertApplication('Non &eacute; possibile eliminare un elemento ARCHIVIATO', '', false, true, false, null, null);
		}
	}
	
	
	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* INIZIO ********/
	/****************************************************************************************************************************************************************/
	/**
	 * Avvio dell'editing con il passaggio della geometria in formato WKT
	 * @param geometry, stringa che riporta il WKT della geometria
	 */
	avviaEditingToponimo(geometry) {
		/**1 - inizializzo**/
		var self = this;
		drawLayerSource.clear();
		/**2 - avvia la gestione dell'editing**/
		manageEditing(self, self.properties);
		if(!self.properties.newEntita && self.properties.typeEditing.indexOf('Circle') == -1) {
			$('#editingToponimoStradaleIziToast #circleBtn').hide();
		}	
	}
	
	/**
	 * Il metodo conferma le feature disegnate/tagliate e da il via al salvataggio chiudendo il iziToast "editingToponomasticaIziToast"
	 */
	confermaFeatureDisegnate(){
		var self = this;
		
		if( self.modified ){																//HO EFFETTUATO DELLE MODIFICHE
			/**1.1 - Recupero la geometria**/
			var typeEditing = self.properties.typeEditing;
			var src = 'EPSG:3857';
			var dest = 'EPSG:4326';
			var format = new ol.format.WKT();
			var wkts = "MULTILINESTRING(";
			$.each(self.editSourceFeatures.getFeatures(), function(index, value){
				wkts = wkts + "(";
				if (typeEditing=='Circle') {
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

    				// WKTCircle = format.writeGeometry(polygonFeature, {
                   	//  		dataProjection: dest,
                    // 		featureProjection: src 
					// });
					
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
			self.toast = document.getElementById('editingToponimoStradaleIziToast'); // Selector of your toast
			iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);
			/**1.3 - Confermo il disegno**/
			self.confermaDisegno = true;
			/**1.4 - Blocco l'editing**/
			self.blockEditing = true;
			/**1.5 - Setto il LineString */
			// self.properties.typeEditing = 'LineString';
		} else if( self.cuting ){															//HO EFFETTUATO DEI TAGLI
			
			var numCat = sessionStorage.getItem('numCut');
			var numDefCut = sessionStorage.getItem('numDefCut');
			if (numCat==numDefCut) {

				/**2.1 - Blocco l'editing**/
				self.blockEditing = true;
				/**2.2 - Disabilito il disegno su mappa**/
				self.toast = document.getElementById('editingToponimoStradaleIziToast'); // Selector of your toast
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
	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* FINE **********/
	/****************************************************************************************************************************************************************/
}