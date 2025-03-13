'use strict'

/**
 * Controller principale della pagina della toponomastica 
 * Fondamentalmente aggiunge solo degli eventi di click che aprono un oggetto controller appropriato che gestisce la pagina di ricerca.
 */
class ToponomasticaPageCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		this.aggiungiEventiClickVaiAPagina();
		appUtil.reloadTooltips();
	}
	/**
	 * Aggiunge gli eventi di click sul menu che allocano il relativo controller della modale
	 */
	aggiungiEventiClickVaiAPagina(){
		 sessionStorage.setItem('windowOpened',null); 
		/**GESTIONE DATI TOPONOMASTICA**/
		$('#insModTopStr').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaGestioneToponimoCtrl(); });
		$('#insModPer').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail( sessionStorage.getItem('windowOpened')  );  new PaginaGestionePercorsiCtrl();});
		$('#insModLoc').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null )
				closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaLocalitaCtrl(); });
		$('#modGiuStr').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail(sessionStorage.getItem('windowOpened')); new PaginaGestioneGiunzioneStradaleCtrl(); });
		$('#insModEstAmm').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail(sessionStorage.getItem('windowOpened')); new PaginaGestioneEstesaAmministrativaCtrl(); });
		$('#insModEleAreStr').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null )
				closeStaticDetail(sessionStorage.getItem('windowOpened')); new PaginaGestioneAreeStradaliCtrl(); });
		$('#insModCipChi').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail(sessionStorage.getItem('windowOpened')); new PaginaGestioneCippiChilometriciCtrl(); });
		$('#insModAccesso').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail(sessionStorage.getItem('windowOpened')); new PaginaGestioneAccessoCtrl(); });
		/** IMPORT SHAPE FILE **/
		$('#importShape').off('click').on('click', function(_event) {
			if( sessionStorage.getItem('windowOpened')!=null )
				closeStaticDetail(sessionStorage.getItem('windowOpened')); new PaginaGestioneImportCtrl(); });
		/**RICERCA DATI TOPONOMASTICA**/
		$('#ricEnt').off('click').on('click', function(_event){ 
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail(sessionStorage.getItem('windowOpened')); new PaginaRicercaEntitaCtrl(); });
	}
}


/**
 * CONTROLLER PER LA GESTIONE DELLE RICERCHE
 */
class PaginaRicercaEntitaCtrl extends BaseModaleRicercaCtrl {

	constructor() {

		super('paginaRicercaEntita', 'Ricerca Entit\u00E0', 'modaleRicercaEntita');

		this.datiMockInterno = [{
			id: 1,
			comune: {idComune: 10185, descrizione: 'MESSINA'},
			foglio: 3,
			numero: 234,
			subalterno: 702,
			note: ''
		}];

		this.inizializzaPagina();

	}

	inizializzaPagina() {
		var self = this;

		/**Recupero dei dati di base della modale Ricerca Entità**/
		/**************************** TIPI TOPONIMI **************************/
		var hrefToponomasticaTipoToponimoUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaConsultazioneCtrl/getTipoToponimo";
    	var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaTipoToponimoUrbamid, "GET", null);
		self.tipiToponimi = UrbamidResponse.aaData;
		/**************************** PROVINCE **************************/
    	self.provinceMessina = [{id:57675,descrizione:"MESSINA"}];
		/**************************** COMUNI **************************/
		// var hrefToponomasticaComuniUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/toponomasticaCtrl/getComuniByMessina";
    	// var UrbamidResponse = appRest.restUrbamid(hrefToponomasticaComuniUrbamid, "GET", null);
    	self.comuniMessina = [{id:10185,descrizione:"MESSINA"}];
    	/**************************** LOCALITA' **************************/
		//var hrefToponomasticaLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/findAllLocalita';
		//var UrbamidResponseLocalita = appRest.restUrbamid(hrefToponomasticaLocalitaToponimoUrbamid, "GET", null);
		//self.localita = UrbamidResponseLocalita.aaData;
		///**************************** TIPO ACCESSO **************************/
		//var hrefToponomasticaTipoAccessoToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getTipoAccesso';
		//var UrbamidResponseTipoAccessi = appRest.restUrbamid(hrefToponomasticaTipoAccessoToponimoUrbamid, "GET", null);
		//self.tipoAccessi = UrbamidResponseTipoAccessi.aaData;
		///**************************** TIPO ACCESSO **************************/
		//var hrefToponomasticaTipoLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getTipoLocalita';
		var hrefToponomasticaLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/findAllLocalita';
		var UrbamidResponseLocalita = appRest.restUrbamid(hrefToponomasticaLocalitaToponimoUrbamid, "GET", null);
		self.localita = UrbamidResponseLocalita.aaData;
		/**************************** TIPO ACCESSO **************************/
		var hrefToponomasticaTipoAccessoToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getTipoAccesso';
		var UrbamidResponseTipoAccessi = appRest.restUrbamid(hrefToponomasticaTipoAccessoToponimoUrbamid, "GET", null);
		self.tipoAccessi = UrbamidResponseTipoAccessi.aaData;
		/**************************** TIPO ACCESSO **************************/
		var hrefToponomasticaTipoLocalitaToponimoUrbamid = appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getTipoLocalita';
		var UrbamidResponseTipoLocalita = appRest.restUrbamid(hrefToponomasticaTipoLocalitaToponimoUrbamid, "GET", null);
		self.tipoLocalita = UrbamidResponseTipoLocalita.aaData;
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

		let contextHandlebar = {localita: self.localita, tipoAccesso: self.tipoAccessi, comuniMessina: self.comuniMessina, tipoLocalita: self.tipoLocalita, enteGestore: self.enteGestore, classificaAmministrativa: self.classificaAmministrativa, classificaFunzionale: self.classificaFunzionale, patrimonialita: self.patrimonialita};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
					
			/** ====================== INIZIALIZZAZIONI ====================== */
			/** ====================== 		INIZIO 		====================== */
			self.aggiungiEventoClickChiudiFinestra();
			self.nascondiDettaglio();
			self.inizializzaBreadcrumb();
			self.inizializzaStepper();

			/** DATAPICKER */
			$("#dataDeliberaToponimoDal").datepicker({dateFormat: 'dd/mm/yy', });
			$("#dataDeliberaToponimoAl").datepicker({dateFormat: 'dd/mm/yy'});
			$("#dataAutorizzazioneToponimoDal").datepicker({dateFormat: 'dd/mm/yy'});
			$("#dataAutorizzazioneToponimoAl").datepicker({dateFormat: 'dd/mm/yy'});

			/** KEYUP NUMCIVICO */
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
						message: 'Il numero civico del lato sinistro dev\'essere dispari!',
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
			});

			/** AUTOCOMPLETE DUG */
			//appUtil.activeAutocompleteOnFiled('dug', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...');
			///** AUTOCOMPLETE TOPONIMI */
			//appUtil.activeAutocompleteOnFiled('toponimoStradaleAccesso', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			///** AUTOCOMPLETE ESTESE AMMINISTRATIVE */
			//appUtil.activeAutocompleteOnFiled('cippoEstesaAmministrativa', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa Amministrativa...')
			appUtil.activeAutocompleteOnFiled('dug', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...');
			/** AUTOCOMPLETE TOPONIMI */
			appUtil.activeAutocompleteOnFiled('toponimoStradaleAccesso', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getToponimo', 'toponimo', 'toponimo', 'toponimo', 'Toponimo...')
			/** AUTOCOMPLETE ESTESE AMMINISTRATIVE */
			appUtil.activeAutocompleteOnFiled('cippoEstesaAmministrativa', appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getEstesaAmministrativa', 'descrizione', 'descrizione', 'descrizione', 'Estesa Amministrativa...')
			
			/** UPPER CASE INPUT RICERCA */
			self.upperCase('formRicerche');
			/** ====================== INIZIALIZZAZIONI ====================== */
			/** ====================== 		FINE 		====================== */

			/** ====================== AZIONI PULSANTI ====================== */
			/** ====================== 		INIZIO	   ====================== */
			/** RICERCA */
			$('#' + self.idDialog + ' #ricercaBtn').click(function() {
				
				if($('#' + self.idDialog + ' #stepToponimoStradaleDiv').hasClass('active')) {
					if(($('#numCivicoDxDa').val() != '' && $('#numCivicoDxA').val() != '') && Number($('#numCivicoDxA').val()) > Number($('#numCivicoDxDa').val())) {

						iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon: 'fa fa-times',
							message: 'Il numero civico A, non pu&ograve; essere maggiore del numero civico Da',
							animateInside: false,
							position: 'topCenter',
						});

					} else if(($('#numCivicoSxDa').val() != '' && $('#numCivicoSxA').val() != '') && Number($('#numCivicoSxA').val()) > Number($('#numCivicoSxDa').val())) {

						iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon: 'fa fa-times',
							message: 'Il numero civico A, non pu&ograve; essere maggiore del numero civico Da',
							animateInside: false,
							position: 'topCenter',
						});

					} else {

						self.inizializzaDatatableRicercaToponimoStradali();

						$("#tabellaRicercaToponimoStradale").show()

					}
					
				} else if($('#' + self.idDialog + ' #stepAccessoDiv').hasClass('active')) {
					self.inizializzaDatatableRicercaAccessi();
					
					$('#tabellaAccessi').show()
				} else if($('#' + self.idDialog + ' #stepEstesaAmministrativaDiv').hasClass('active')) {
					self.inizializzaDatatableRicercaEstesaAmministrativa();

					$('#tabellaEstesaAmministrativa').show()
				} else if($('#' + self.idDialog + ' #stepCippoDiv').hasClass('active')) {
					if($('#cippoMisura').val() != '' && !self.isNumero($('#cippoMisura').val())) {
						iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon: 'fa fa-times',
							message: 'Il campo Misura dev\'essere un numero!',
							animateInside: false,
							position: 'topCenter',
						});
					} else {
						self.inizializzaDatatableRicercaCippo();

						$('#tabellaCippo').show()
					}
				} else if($('#' + self.idDialog + ' #stepPercorsoDiv').hasClass('active')) {
					if($('#estesaAmministrativaPercorso').prop('checked')) {
						self.inizializzaDatatableRicercaPercorso(true);
					} else if($('#toponimoStradalePercorso').prop('checked')) {
						self.inizializzaDatatableRicercaPercorso(false);
					} else {
						self.inizializzaDatatableRicercaPercorso('');
					}

					$('#tabellaPercorso').show()
				} else if($('#' + self.idDialog + ' #stepLocalitaDiv').hasClass('active')) {
					self.inizializzaDatatableRicercaLocalita();

					$('#tabellaLocalita').show()
				} else if($('#' + self.idDialog + ' #stepInternoDiv').hasClass('active')) {
					self.inizializzaDatatableRicercaInterno(self.datiMockInterno);

					$('#tabellaInterno').show()
				}
				
			});

			/** RESETTO I FORM */
			$('#' + self.idDialog + ' #azzeraBtn').click(function() {
				if($('#' + self.idDialog + ' #stepToponimoStradaleDiv').hasClass('active')) {
					self.resettaForm('#stepToponimoStradaleDiv');
				} else if($('#' + self.idDialog + ' #stepAccessoDiv').hasClass('active')) {
					self.resettaForm('#stepAccessoDiv');
				} else if($('#' + self.idDialog + ' #stepEstesaAmministrativaDiv').hasClass('active')) {
					self.resettaForm('#stepEstesaAmministrativaDiv');
				} else if($('#' + self.idDialog + ' #stepCippoDiv').hasClass('active')) {
					self.resettaForm('#stepCippoDiv');
				} else if($('#' + self.idDialog + ' #stepPercorsoDiv').hasClass('active')) {
					self.resettaForm('#stepPercorsoDiv');
				} else if($('#' + self.idDialog + ' #stepLocalitaDiv').hasClass('active')) {
					self.resettaForm('#stepLocalitaDiv');
				} else if($('#' + self.idDialog + ' #stepInternoDiv').hasClass('active')) {
					self.resettaForm('#stepInternoDiv');
				}

			})

			/** NASCONDO LA PAGINA DI DETTAGLIO */
			$('#' + self.idDialog + ' #indietroBtn').click(function() {
				self.nascondiDettaglio();
			})
			/** ====================== AZIONI PULSANTI ====================== */
			/** ====================== 		FINE	   ====================== */

			/** INIZIALIZZO I TOOLTIPS */
			appUtil.reloadTooltips();

		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaRicercaEntita');
	}

	/** METODO DI UTILITY PER NASCONDERE LA RICERCA E VISUALIZZARE I PULSANTI CORRETTI */
	nascondiRicerca() {
		var self = this;

		$('#'+self.idDialog+' #ricerca').hide();
		$('#'+self.idDialog+' #dettaglio').show();
		
		//mostro i pulsanti corretti
		$('#'+self.idDialog+' .pulsante-ricerca').each(function(){
			$(this).children('li').hide()
		});
		$('#'+self.idDialog+' .pulsante-dettaglio').each(function(){
			$(this).children('li').show()
		});

	}

	/** METODO DI UTILITY PER NASCONDERE IL DETTAGLIO E VISUALIZZARE I PULSANTI CORRETTI */
	nascondiDettaglio() {
		var self = this;

		/** AGGIORNO BREADCRUMB */
		self.aggiornaBreadcrumb('ricerca');

		//NASCONDO I PULSANTI DEL DETTAGLIO
		$('#'+self.idDialog+' .pulsante-ricerca').each(function(){
			$(this).children('li').show();
		});
		$('.pulsante-dettaglio').each(function(){
			$(this).children('li').hide();
		});

		/** NASCONDO IL DETTAGLIO */
		$('#' + self.idDialog + ' #dettaglio').each(function() {
			$(this).children().hide();
			$(this).hide();
		})
			
		$('#' + self.idDialog + ' #ricerca').show();
		
	}

	/** Ripulisce tutti i campi input del form dato l'id
	 * @param form, indentificato del form
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
	isNumero(numero) {
		return !isNaN(parseFloat(numero)) && isFinite(numero)
	}

	/**
	 * Metodo di utility per verificare se è un numero intero, passandogli in input un valore. Il risultato
	 * true = è un numero intero
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
	 * Metodo utile solo per aggiungere su mappa una geometria
	 * @param WKT
	*/
	getGeometry(WKT) {
		var self = this;

		if (WKT != '' && WKT != null && WKT != undefined) {
			drawLayerSource.clear();

			var format = new ol.format.WKT();
			var feature = format.readFeature(WKT, {
				dataProjection: 'EPSG:4326',
				featureProjection: 'EPSG:3857'
			});

			drawLayerSource.addFeature(feature);
			(appMappa.maps).getView().fit(feature.getGeometry(), { padding: [0, 0, 0, 0] });
			(appMappa.maps).getView().setZoom(13);

			self.riduciAdIconaTutteLeModali();
	
		} else {
				
			iziToast.error({
				title: 'Attenzione',
				theme: 'dark',
				icon:'fa fa-times',
				message: 'Impossibile visualizzare la geometria in mappa!',
				animateInside: false,
				position: 'topCenter',
			});
		}
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
	    $('#stepToponimoStradale').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(1)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
			$('#'+self.idDialog+' #stepToponimoStradaleDiv').addClass('active');
	    });
	    $('#stepAccesso').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(2)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
			$('#'+self.idDialog+' #stepAccessoDiv').addClass('active');
	    });
	    $('#stepEstesaAmministrativa').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(3)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
	    	$('#'+self.idDialog+' #stepEstesaAmministrativaDiv').addClass('active');
	    });
	    $('#stepCippo').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(4)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
			$('#'+self.idDialog+' #stepCippoDiv').addClass('active');
	    });
	    // $('#stepPercorso').on('click', function(){
	    // 	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    // 	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(5)').addClass('active');
	    // 	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
	    // 	$('#'+self.idDialog+' #stepPercorsoDiv').addClass('active');
	    // });
	    $('#stepLocalita').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(5)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
	    	$('#'+self.idDialog+' #stepLocalitaDiv').addClass('active');
	    });
	    // $('#stepInterno').on('click', function(){
	    // 	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    // 	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(7)').addClass('active');
	    // 	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
	    // 	$('#'+self.idDialog+' #stepInternoDiv').addClass('active');
	    // });
	}

	/**
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicercaToponimoStradali() {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** FILTRI DI RICERCA */
		var classe = $('#dug').val();
		var duf = $('#descrizioneToponimoStradale').val();
		var numeroDelibera = $('#numeroDelibera').val();
		var codiceAutorizzazione = $('#codiceAutorizzazione').val();
		var dataDeliberaDal = $('#dataDeliberaToponimoDal').val();
		var dataDeliberaAl = $('#dataDeliberaToponimoAl').val();
		var dataAutorizzazioneDal = $('#dataAutorizzazioneToponimoDal').val();
		var dataAutorizzazioneAl = $('#dataAutorizzazioneToponimoAl').val();
		var stato = $('#statoToponimo').val();
		var numCivicoDxDa = $('#numCivicoDxDa').val();
		var numCivicoDxA = $('#numCivicoDxA').val();
		var numCivicoSxDa = $('#numCivicoSxDa').val();
		var numCivicoSxA = $('#numCivicoSxA').val();
		/** INIZIALIZZA DATATABLE */
		$("#tabellaRicercaToponimoStradale").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 2, 3, 4, 5, 6, 7, 8, 9, 10 ],
				postfixButtons: [ { extend: 'colvisRestore',
									text: '<hr size="1" style="margin-top: 6px">' +
										  '<button class="btn btn-primary btnColvisRestore" >Visualizza tutto</button>',
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
				//url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaToponimo',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/ricercaToponimo',
				contentType : 'application/json; charset=UTF-8',
				data : function(json) {
					var columnSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							columnSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {classe: classe.trim(),
								  stato: stato, 
								  denominazioneUfficiale: duf.trim(),
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
								  pageOrder: { column: columnSort, dir: json.order[0].dir } }
					
					return JSON.stringify(filtri);
				}  
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRicerca', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
					}
				}
		   ],
		   initComplete: function(settings) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	self.aggiungiEventiAiPulsantiAzioneToponimi();
	        	sessionStorage.setItem('ricercaToponimoStradale', JSON.stringify(settings.json.data));
	        }
		});
	}

	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - anagrafica-toponimo, apre un form di dettaglio del toponimo stradale, con i campi input a readonly
	 * - localizza-toponimo, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 **/
	aggiungiEventiAiPulsantiAzioneToponimi() {
		var self = this;
		$('#tabellaRicercaToponimoStradale button.anagrafica-btn').off('click').on('click', function(_event){
			self.visualizzaDettaglio($(this).data('id'));
		});
		
		$('#tabellaRicercaToponimoStradale button.localizza-btn').off('click').on('click', function(_event){
			self.getGeometry($(this).data('geometry'));
        });
	}

	/**
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicercaAccessi() {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** FILTRI DI RICERCA */
		var toponimo = $('#toponimoStradaleAccesso').val();
		var localita = $('#localitaAccesso').val();
		var tipo = $('#tipoAccesso').val();
		var stato = $('#statoAccesso').val();
		/** INIZIALIZZA DATATABLE */
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
										  '<button class="btn btn-primary btnColvisRestore" >Visualizza tutto</button>',
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
					var columnSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							columnSort = json.columns[i].name;
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
								  pageOrder: { column: columnSort, dir: json.order[0].dir } };
					
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
	        	{targets : 8, className: "text-right", orderable : false, render: function(_d, _t, r) {
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRicerca', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	self.aggiungiEventiAiPulsantiAzioneAccessi();
	        	sessionStorage.setItem('ricercaAccesso', JSON.stringify(settings.json.data));
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - anagrafica-accessi, apre un form di dettaglio dell'accesso, con i campi input a readonly
	 * - localizza-accessi, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 **/
	aggiungiEventiAiPulsantiAzioneAccessi() {
		var self = this;
		$('#tabellaAccessi button.anagrafica-btn').off('click').on('click', function(_event){
			self.visualizzaDettaglio($(this).data('id'));
        });
		
		$('#tabellaAccessi button.localizza-btn').off('click').on('click', function(_event){
			 self.getGeometry($(this).data('geometry'));
		});
	}

	/**
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicercaEstesaAmministrativa() {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** FILTRI RICERCA */
		var sigla = $('#siglaEstesaAmministrativa').val();
		var descrizione = $('#descrizioneEstesaAmministrativa').val();
		var codice = $('#codiceEstesaAmministrativa').val();
		var classificaAmmEstesaAmministrativa = $('#classificaAmmEstesaAmministrativa option:selected').val();
		var classificaFunzEstesaAmministrativa = $('#classificaFunzEstesaAmministrativa option:selected').val();
		/** INIZIALIZZO DATATABLE */
		$("#tabellaEstesaAmministrativa").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 2, 3, 4, 5, 6, 7, 8 ],
				postfixButtons: [ { extend: 'colvisRestore',
									text: '<hr size="1" style="margin-top: 6px">' +
										  '<button class="btn btn-primary btnColvisRestore" >Visualizza tutto</button>',
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
					var columnSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							columnSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {sigla: sigla.trim(),
								  descrizione: descrizione.trim(),
								  codice: codice.trim(),
								  classificaAmministrativa: classificaAmmEstesaAmministrativa,
								  classificaFunzionale: classificaFunzEstesaAmministrativa,
								  stato: 'PUBBLICATO',
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: columnSort, dir: json.order[0].dir } };
					
					return JSON.stringify(filtri);
				}  
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, name: 'sigla', render: function(_d, _t, r) {return r.sigla;}, orderable : true},
	        	{targets : 1, name: 'descrizione', render: function(_d, _t, r) {return r.descrizione;}, orderable : true},
				{targets : 2, name: 'classificaAmministrativa', render: function(_d, _t, r) {return r.classificaAmministrativa == null ? '' : r.classificaAmministrativa.descrizione ;}, orderable : true},
				{targets : 3, name: 'classificaFunzionale', render: function(_d, _t, r) {return r.classificaFunzionale == null ? '' : r.classificaFunzionale.descrizione ;}, orderable : true},
	        	{targets : 4, name: 'codice', render: function(_d, _t, r) {return r.codice;}, orderable : true},
	        	{targets : 5, name: 'estensione', render: function(_d, _t, r) {return r.estensione;}, orderable : true},
	        	{targets : 6, name: 'tronco', render: function(_d, _t, r) {return r.tronco;}, orderable : true},
	        	{targets : 7, name: 'provincia', render: function(_d, _t, r) {return r.provincia == null ? '' : r.provincia.denominazione;}, orderable : true},
	        	{targets : 8, name: 'comune', render: function(_d, _t, r) {return r.comune == null ? '' : r.comune.denominazione;}, orderable : true},
				{targets : 9, name: 'stato', render: function(_d, _t, r) {return r.stato}, orderable : true},
				{targets : 10, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRicerca', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
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
	 * - anagrafica-estesaAmministrativa, apre un form di dettaglio dell'estesa amministrativa, con i campi input a readonly
	 * - localizza-estesaAmministrativa, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 **/
	aggiungiEventiAiPulsantiAzioneEstesaAmministrativa() {
		var self = this;
		$('#tabellaEstesaAmministrativa button.anagrafica-btn').off('click').on('click', function(_event){
			self.visualizzaDettaglio($(this).data('id'));
        });
		
		$('#tabellaEstesaAmministrativa button.localizza-btn').off('click').on('click', function(_event){
			self.getGeometry($(this).data('geometry'));
		});
	}

	/**
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicercaCippo() {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** FILTRI RICERCA */
		var estesaAmministrativa = $('#cippoEstesaAmministrativa').val();
		var misura = $('#cippoMisura').val();
		var codice = $('#cippoCodice').val();
		/** INIZIALIZZA DATATABLE */
		$("#tabellaCippo").DataTable({
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
										  '<button class="btn btn-primary btnColvisRestore" >Visualizza tutto</button>',
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
					var columnSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							columnSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {codice: codice.trim(),
								  estesaAmministrativa: estesaAmministrativa.trim(),
								  misura: misura.trim(),
								  stato: 'PUBBLICATO',
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: columnSort, dir: json.order[0].dir }  };
					
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRicerca', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
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
	 * - anagrafica-cippo, apre un form di dettaglio dell'estesa amministrativa, con i campi input a readonly
	 * - localizza-cippo, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 **/
	aggiungiEventiAiPulsantiAzioneCippo() {
		var self = this;
		$('#tabellaCippo button.anagrafica-btn').off('click').on('click', function(_event){
			self.visualizzaDettaglio($(this).data('id'));
        });
		
		$('#tabellaCippo button.localizza-btn').off('click').on('click', function(_event){
			self.getGeometry($(this).data('geometry'));
		});
	}

	/**
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicercaPercorso(tipo) {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** FILTRI RICERCA */
		var sigla = $('#siglaPercorso').val();
		var descrizione = $('#descrizionePercorso').val();
		var stato = $('#statoPercorso').val();
		/** INIZIALIZZO DATATABLE */
		$("#tabellaPercorso").DataTable({
			dom: self.datatableDom,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 2, 3, 4, 5 ],
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
	        	{targets : 4, render: function(_d, _t, r) {return r.note;}, width: '30%', orderable : true},
				{targets : 5, render: function(_d, _t, r) {return r.stato}, orderable : true},
				{targets : 6, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRicerca', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( settings ) {
	        	self.aggiungiEventiAiPulsantiAzionePercorso();
		    	sessionStorage.setItem('ricercaPercorso', JSON.stringify(settings.json.data));
	        }
		});
	}
	 
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - anagrafica-percorso, apre un form di dettaglio del percorso, con i campi input a readonly
	 * - localizza-percorso, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 **/
	aggiungiEventiAiPulsantiAzionePercorso() {
		var self = this;
		$('#tabellaPercorso button.anagrafica-btn').off('click').on('click', function(_event){
			self.visualizzaDettaglio($(this).data('id'));
		});
		
		$('#tabellaPercorso button.localizza-btn').off('click').on('click', function(_event){
			self.getGeometry($(this).data('geometry'));
        });
	}

	/**
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicercaLocalita() {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** FILTRI RICERCA */
		var descrizione = $('#descrizioneLocalita').val();
		var frazione = $('#frazioneLocalita').val();
		/** INIZIALIZZO DATATABLE */
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
										  '<button class="btn btn-primary btnColvisRestore" >Visualizza tutto</button>',
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRicerca', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
	   		},
	       drawCallback: function( settings ) {
	        	self.aggiungiEventiAiPulsantiAzioneLocalita();
		    	sessionStorage.setItem('ricercaLocalita', JSON.stringify(settings.json.data));
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - anagrafica-localita, apre un form di dettaglio della località, con i campi input a readonly
	 * - localizza-localita, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 **/
	aggiungiEventiAiPulsantiAzioneLocalita() {
		var self = this;
		$('#tabellaLocalita button.anagrafica-btn').off('click').on('click', function(_event){
			self.visualizzaDettaglio($(this).data('id'));
		});
		
		$('#tabellaLocalita button.localizza-btn').off('click').on('click', function(_event){
			self.getGeometry($(this).data('geometry'));
        });
	}

	/**
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicercaInterno(interno) {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** INIZIALIZZO DATATABLE */
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
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRicerca', {id: r.id, geom: r.geom != null ? true : false, geometry: r.geom});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {
	    	   sessionStorage.setItem('ricercaInterno', JSON.stringify(interno));
	    	   self.aggiungiEventiAiPulsantiAzioneInterno();
	        }
		});
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - anagrafica-interno, apre un form di dettaglio dell'interno, con i campi input a readonly
	 * - localizza-interno, viene richiamato il metodo getGeometry che riducerà la modale mostrando su mappa la geometria
	 **/
	aggiungiEventiAiPulsantiAzioneInterno() {
		var self = this;
		$('#tabellaInterno button.anagrafica-btn').off('click').on('click', function(_event){
			self.visualizzaDettaglio($(this).data('id'));
        });
		
		$('#tabellaInterno button.localizza-btn').off('click').on('click', function(_event){
			self.getGeometry($(this).data('geometry'))
        });
	}

	/**
	 * Metodo per il recupero del dettaglio di un entità dato l'id dell'entità e la key del session storage.
	 * @param idEntita, identificativo dell'entità.
	 * @param keySessionStorage, key del session storage utilizzato per il recupero
	 * @returns dettaglio
	 */
	recuperaDettaglio(idEntita, keySessionStorage) {
		let value = JSON.parse(sessionStorage.getItem(keySessionStorage));
		let dettaglio;
		for(var i=0; i<value.length; i++){
			if(value[i].id === idEntita){
				dettaglio = value[i];
				break;
			}
		}
		return dettaglio;
	}

	/**
	 * Il metodo visualizza il dettaglio delle varie entità con id passato in input. La visualizzazione del dettaglio prevede una modale già aperta
	 * @param id, identificativo dell'entità
	 */
	visualizzaDettaglio(id) {
		/** INIZIALIZZAZIONE */
		var self = this;
		
		/** NASCONDO LA RICERCA */
		self.nascondiRicerca();

		appUtil.showLoader(null, 'Per favore, attendere il completamente dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();

			if($('#' + self.idDialog + ' #stepToponimoStradaleDiv').hasClass('active')) {

				/** VISUALIZZO IL DETTAGLIO RICERCA */
				$('#dettaglioRicercaToponimo').show();

				/** RECUPERO IL DETTAGLIO  */
				var dettaglioToponimo = self.recuperaDettaglio(id, 'ricercaToponimoStradale');
				
				/** OGGETTO TOPONIMO PER LA VISUALIZZAZIONE */
				var detToponimo = { id: id, 
									classe: dettaglioToponimo.classe == null ? '' : dettaglioToponimo.classe.dug,
									denominazione: dettaglioToponimo.denominazione == null ? '' : dettaglioToponimo.denominazione,
									denominazioneUfficiale: dettaglioToponimo.denominazioneUfficiale == null ? '' : dettaglioToponimo.denominazioneUfficiale,
									comune:	dettaglioToponimo.comune == null ? '' : dettaglioToponimo.comune.idComune,
									comuni: self.comuniMessina,
									tipo: dettaglioToponimo.tipo == null ? '' : dettaglioToponimo.tipo.id,
									tipi: dettaglioToponimo.tipo,
									codice: dettaglioToponimo.codice == null ? '' : dettaglioToponimo.codice,
									note: dettaglioToponimo.note == null ? '' : dettaglioToponimo.note,
									dataDelibera: dettaglioToponimo.dataDelibera == null ? '' : new Date(dettaglioToponimo.dataDelibera).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'}),
									numeroDelibera:dettaglioToponimo.numeroDelibera == null ? '' : dettaglioToponimo.numeroDelibera,
									dataAutorizzazione: dettaglioToponimo.dataAutorizzazione == null ? '' : new Date(dettaglioToponimo.dataAutorizzazione).toLocaleDateString('it-IT', {month: '2-digit', day: '2-digit', year: 'numeric'}),
									codiceAutorizzazione: dettaglioToponimo.codiceAutorizzazione == null ? '' : dettaglioToponimo.codiceAutorizzazione,
									cap: dettaglioToponimo.cap == null ? '' : dettaglioToponimo.cap,
									compendi: dettaglioToponimo.compendi == null ? '' : dettaglioToponimo.compendi,
									geom:dettaglioToponimo.geom,
									readonly: 'readonly',
									disabled: 'disabled',
									hidden: 'hidden' };
				
				/** COMPILO IL TEMPLATE CON L'OGGETTO detToponimo */
				var htmlDet = compilaTemplate('modaleDettaglioToponimi', detToponimo);
				$("#" + self.idDialog + " #dettaglioToponimo").html(htmlDet);
				
				/** INIZIALIZZO I CAMPI SELECT */
				$("#tipoToponimoM").val(detToponimo.tipo == null ? '' : dettaglioToponimo.tipo.id);
				$("#comuneToponimoM").val(detToponimo.comune == null ? '' : dettaglioToponimo.comune.idComune);
				 
				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('dettaglio', dettaglioToponimo.denominazioneUfficiale);
	
			}

			if($('#' + self.idDialog + ' #stepAccessoDiv').hasClass('active')) {

				/** VISUALIZZO IL DETTAGLIO RICERCA */
				$('#dettaglioRicercaAccesso').show();

				/** RECUPERO IL DETTAGLIO  */
				var dettaglioAccesso = self.recuperaDettaglio(id, 'ricercaAccesso');

				/** OGGETTO ACCESSO PER LA VISUALIZZAZIONE */
				var detAccesso = { id: id,
								   toponimo: dettaglioAccesso.toponimo == null ? '' : dettaglioAccesso.toponimo.denominazioneUfficiale,
								   numero: dettaglioAccesso.numero == null ? '' : dettaglioAccesso.numero,
								   esponente: dettaglioAccesso.esponente == null ? '' : dettaglioAccesso.esponente,
								   localita: self.localita, 
								   localitaID: dettaglioAccesso.localita == null ? '' : dettaglioAccesso.localita.id,
								   tipoAccesso: self.tipoAccessi,
								   tipoAccessoID: dettaglioAccesso.tipo == null ? '' : dettaglioAccesso.tipo.id,
								   passoCarrabile: dettaglioAccesso.passoCarrabile ? 'checked' : '',
								   principale: dettaglioAccesso.principale ? 'checked' : '',
								   metodo: dettaglioAccesso.metodo == null ? '' : dettaglioAccesso.metodo,
								   note: dettaglioAccesso.note == null ? '' : dettaglioAccesso.note,
								   readonly: 'readonly',
								   disabled: 'disabled',
								   hidden: 'hidden' }

				/** COMPILO IL TEMPLATE CON L'OGGETTO detAccesso */
				let html = compilaTemplate('modaleDettaglioAccesso', detAccesso);
				$('#' + self.idDialog + ' #dettaglioAccesso').html(html);

				/** INIZIALIZZO I CAMPI SELECT */
				$('#' + self.idDialog + ' #localitaAccessoM').val(detAccesso.localita == null ? '' : dettaglioAccesso.localita.id);
				$('#' + self.idDialog + ' #tipoAccessoM').val(detAccesso.tipoAccesso == null ? '' : dettaglioAccesso.tipo.id);

				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('dettaglio', 'Accesso ' + detAccesso.toponimo);

			}

			if($('#' + self.idDialog + ' #stepEstesaAmministrativaDiv').hasClass('active')) {

				/** VISUALIZZO IL DETTAGLIO RICERCA */
				$('#dettaglioRicercaEstesa').show();

				/** RECUPERO IL DETTAGLIO  */
				var dettaglioEstesaAmministrativa = self.recuperaDettaglio(id, 'ricercaEstesaAmministrativa');

				/** OGGETTO ESTESA PER LA VISUALIZZAZIONE */
				var detEstesa = { id: id, 
								  sigla: dettaglioEstesaAmministrativa.sigla == null ? '' : dettaglioEstesaAmministrativa.sigla,
								  descrizione: dettaglioEstesaAmministrativa.descrizione == null ? '' : dettaglioEstesaAmministrativa.descrizione,
								  classificheAmministrative: self.classificaAmministrativa,
								  classificaAmministrativa: dettaglioEstesaAmministrativa.classificaAmministrativa == null ? '' : dettaglioEstesaAmministrativa.classificaAmministrativa.id,
								  codice: dettaglioEstesaAmministrativa.codice == null ? '' : dettaglioEstesaAmministrativa.codice,
								  tronco: dettaglioEstesaAmministrativa.tronco == null ? '' : dettaglioEstesaAmministrativa.tronco,
								  estensione: dettaglioEstesaAmministrativa.estensione == null ? '' : dettaglioEstesaAmministrativa.estensione,
								  provinceMessina: self.provinceMessina,
								  provincia: dettaglioEstesaAmministrativa.provincia == null ? '' : dettaglioEstesaAmministrativa.provincia.idProvincia,
								  comuniMessina: self.comuniMessina,
								  comune: dettaglioEstesaAmministrativa.comune == null ? '' : dettaglioEstesaAmministrativa.comune.idComune,
								  classificheFunzionali: self.classificaFunzionale,
								  classificaFunzionale: dettaglioEstesaAmministrativa.classificaFunzionale == null ? '' : dettaglioEstesaAmministrativa.classificaFunzionale.id,
								  entiGestori: self.enteGestore,
								  enteGestore: dettaglioEstesaAmministrativa.enteGestore == null ? '' : dettaglioEstesaAmministrativa.enteGestore.id,
								  patrimonialita: self.patrimonialita,
								  patrimonialitaID: dettaglioEstesaAmministrativa.patrimonialita == null ? '' : dettaglioEstesaAmministrativa.patrimonialita.id,
								  note: dettaglioEstesaAmministrativa.note == null ? '' : dettaglioEstesaAmministrativa.note,
								  hidden: 'hidden',
								  readonly: 'readonly',
								  disabled: 'disabled'}

				/** COMPILO IL TEMPLATE CON L'OGGETTO detEstesa */
				let html = compilaTemplate('modaleDettaglioEstesa', detEstesa);
				$('#' + self.idDialog + ' #dettaglioEstesaAmministrativa').html(html);

				/** INIZIALIZZO I CAMPI SELECT */
				$('#' + self.idDialog + ' #classificaEstesaAmministrativaM').val(dettaglioEstesaAmministrativa.classificaAmministrativa == null ? '' : detEstesa.classificaAmministrativa);
				$('#' + self.idDialog + ' #provinciaEstesaAmministrativaM').val(dettaglioEstesaAmministrativa.provincia == null ? '' : dettaglioEstesaAmministrativa.provincia.idProvincia);
				$('#' + self.idDialog + ' #comuneEstesaAmministrativaM').val(dettaglioEstesaAmministrativa.comune == null ? '' : dettaglioEstesaAmministrativa.comune.idComune);
				$('#' + self.idDialog + ' #patrimonialitaEstesaAmministrativaM').val(dettaglioEstesaAmministrativa.patrimonialita == null ? '' : detEstesa.patrimonialitaID);
				$('#' + self.idDialog + ' #enteGestoreEstesaAmministrativaM').val(dettaglioEstesaAmministrativa.enteGestore == null ? '' : detEstesa.enteGestore);
				$('#' + self.idDialog + ' #classificaFunzionaleEstesaAmministrativaM').val(dettaglioEstesaAmministrativa.classificaFunzionale == null ? '' : detEstesa.classificaFunzionale);

				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('dettaglio', dettaglioEstesaAmministrativa.sigla);
			}

			if($('#' + self.idDialog + ' #stepCippoDiv').hasClass('active')) {

				/** VISUALIZZO IL DETTAGLIO RICERCA */
				$('#dettaglioRicercaCippo').show();

				/** RECUPERO IL DETTAGLIO  */
				var dettaglioCippo = self.recuperaDettaglio(id, 'ricercaCippo');

				/** OGGETTO CIPPO PER LA VISUALIZZAZIONE */
				var detCippo = { id: id,
								 codice: dettaglioCippo.codice == null ? '' : dettaglioCippo.codice,
								 estesaAmministrativa: dettaglioCippo.estesaAmministrativa == null ? '' : dettaglioCippo.estesaAmministrativa.descrizione,
								 misura: dettaglioCippo.misura == null ? '' : dettaglioCippo.misura,
								 note: dettaglioCippo.note == null ? '' : dettaglioCippo.note,
								 readonly: 'readonly',
								 hidden: 'hidden'}

				/** COMPILO IL TEMPLATE CON L'OGGETTO detCippo */
				let html = compilaTemplate('modaleDettaglioCippo', detCippo);
				$('#' + self.idDialog + ' #dettaglioCippo').html(html);

				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('dettaglio', 'Km ' + detCippo.misura + ' - ' + detCippo.codice);
			}

			if($('#' + self.idDialog + ' #stepPercorsoDiv').hasClass('active')) {

				/** VISUALIZZO IL DETTAGLIO RICERCA */
				$('#dettaglioRicercaPercorso').show();

				/** RECUPERO IL DETTAGLIO  */
				var dettaglioPercorso = self.recuperaDettaglio(id, 'ricercaPercorso');

				/** OGGETTO PERCORSO PER LA VISUALIZZAZIONE */
				var detPercorso = { id: id,
									sigla: dettaglioPercorso.sigla == null ? '' : dettaglioPercorso.sigla,
									descrizione: dettaglioPercorso.descrizione == null ? '' : dettaglioPercorso.descrizione,
									estesaAmministrativa: dettaglioPercorso.estesaAmministrativa.id == null ? '' : dettaglioPercorso.estesaAmministrativa.descrizione,
									checkedEstesa: dettaglioPercorso.estesaAmministrativa.id != null ? 'checked' : '',
									toponimo: dettaglioPercorso.toponimo.id == null ? '' : dettaglioPercorso.toponimo.denominazioneUfficiale,
									checkedToponimo: dettaglioPercorso.toponimo.id != null ? 'checked' : '',
									note: dettaglioPercorso.note == null ? '' : dettaglioPercorso.note,
									readonly: 'readonly',
									disabled: 'disabled',
									hidden: 'hidden' };

				/** COMPILO IL TEMPLATE CON L'OGGETTO detPercorso */					
				let html = compilaTemplate('modaleDettaglioPercorso', detPercorso );
				$('#' + self.idDialog + ' #dettaglioPercorso').html(html);

				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('dettaglio', dettaglioPercorso.descrizione);
				
			}

			if($('#' + self.idDialog + ' #stepLocalitaDiv').hasClass('active')) {

				/** VISUALIZZO IL DETTAGLIO RICERCA */
				$('#dettaglioRicercaLocalita').show();

				/** RECUPERO IL DETTAGLIO  */
				var dettaglioLocalita = self.recuperaDettaglio(id, 'ricercaLocalita');

				/** OGGETTO LOCALITA' PER LA VISUALIZZAZIONE */
				var detLocalita = { id: id,
									comuni: self.comuniMessina,
									comune: dettaglioLocalita.comune == null ? '' : dettaglioLocalita.comune.idComune,
									descrizione: dettaglioLocalita.descrizione == null ? '' : dettaglioLocalita.descrizione,
									frazione: dettaglioLocalita.frazione == null ? '' : dettaglioLocalita.frazione,
									tipi: self.tipoLocalita,
									tipo: dettaglioLocalita.tipo == null ? '' : dettaglioLocalita.tipo.id,
									note: dettaglioLocalita.note == null ? '' : dettaglioLocalita.note,
									readonly: 'readonly',
									disabled: 'disabled',
									hidden: 'hidden' };
				
				/** COMPILO IL TEMPLATE CON L'OGGETTO detLocalita */
				let html = compilaTemplate('modaleDettaglioLocalita', detLocalita);
				$('#' + self.idDialog + ' #dettaglioLocalita').html(html);

				/** INIZIALIZZO I CAMPI SELECT */
				$('#' + self.idDialog + ' #comuneLocalitaM').val(dettaglioLocalita.comune == null ? '' : detLocalita.comune);
				$('#' + self.idDialog + ' #tipoLocalitaM').val(dettaglioLocalita.tipo == null ? '' : detLocalita.tipo);

				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('dettaglio', dettaglioLocalita.descrizione);
			}

			if($('#' + self.idDialog + ' #stepInternoDiv').hasClass('active')) {

				/** VISUALIZZO IL DETTAGLIO RICERCA */
				$('#dettaglioRicercaInterno').show();

				/** RECUPERO IL DETTAGLIO */
				var dettaglioInterno = self.recuperaDettaglio(id, 'ricercaInterno');

				/** OGGETTO INTERNO PER LA VISUALIZZAZIONE */
				var detInterno = {id: id,
								  comuniMessina: self.comuniMessina,
								  comune: dettaglioInterno.comune == null ? '' : dettaglioInterno.comune.idComune,
								  foglio: dettaglioInterno.foglio == null ? '' : dettaglioInterno.foglio,
								  numero: dettaglioInterno.numero == null ? '' : dettaglioInterno.numero,
								  subalterno: dettaglioInterno.subalterno == null ? '' : dettaglioInterno.subalterno,
								  note: dettaglioInterno.note == null ? '' : dettaglioInterno.note,
								  readonly: 'readonly',
								  hidden: 'hidden',
								  disabled: 'disabled'}

				/** COMPILO IL TEMPLATE CON L'OGGETTO detInterno */
				let html = compilaTemplate('modaleDettaglioInterno', detInterno);
				$('#' + self.idDialog + ' #dettaglioInterno').html(html);

				/** INIZIALIZZO I CAMPI SELECT */
				$('#' + self.idDialog + ' #comuneInternoM').val(dettaglioInterno.comune == null ? '' : dettaglioInterno.comune.idComune);

				/** AGGIORNO IL BREADCRUMB */
				self.aggiornaBreadcrumb('dettaglio', 'Foglio ' + dettaglioInterno.foglio)

			}
			
		}, 1000)
	}

}



/****************************************************************************************************************************************************************/
/************ METODI COMUNI PER L'EDITING DELLE GEOMETRIE *************************************************************************************** INIZIO ********/
/****************************************************************************************************************************************************************/
/**
 * Inizializza il disegno di una nuova o esistente geometria
 * @param self
 * @param prop, proprietà dell'editing
 * @returns
 */
function manageEditing(self, prop) {
	
	self.editingAct 	= false;
	self.cutingAct	 	= false;
	/**INIZIALIZZAZIONI**/
	self.numCut				= 1;						//Numero di punti  tracciati sulla geometria
	sessionStorage.removeItem('numCut');
	self.numDefCut			= 0;						//Numero di tratti con anagrafica definita
	sessionStorage.removeItem('numDefCut');
	self.hmCutPoint			= new Object();				//Hashmap dei punti tracciati sulla geometria
	sessionStorage.removeItem('hmCutPoint');
	self.hmEntityCut		= new Object();				//Hashmap dei tratti definiti
	sessionStorage.removeItem('hmEntityCut');
	self.hmCoordsCut 		= new Object();				//Hashmap con le coordinate dei tratti
	sessionStorage.removeItem('hmCoordsCut');
	self.hmWktCut	 		= new Object();				//Hashmap con i wkt dei tratti
	sessionStorage.removeItem('hmWktCut');
	(appMappa.maps).removeInteraction(self.draw);
    (appMappa.maps).removeInteraction(self.modify);
    (appMappa.maps).on('dblclick', function (e) {return;});
    $.each( prop.activeLayers, function( index, value ) {
    	if(appUrbamid.layerAddedMap[value.layerName]==null) {
    		appMappa.addRemoveLayer(value.layerName, value.layerTitle, null, '', '');
    	}
    });
    var interactions = (appMappa.maps).getInteractions();
    for (var i = 0; i < interactions.getLength(); i++) {
        var interaction = interactions.item(i);                          
        if (interaction instanceof ol.interaction.DoubleClickZoom) {
        	(appMappa.maps).removeInteraction(interaction);
            break;
        }
    }
    
	/**1. Inizializzazioni**/
	self.highlightStyle = new ol.style.Style({
		  fill: new ol.style.Fill({
		    color: 'rgba(255,255,255,0.7)',
		  }),
		  stroke: new ol.style.Stroke({
		    color: '#C80000', 
		    width: 10,
		  }),
	});
	/**GEOMETRIA CARICATA E DISEGNATA					editSourceFeatures**/
	self.editFeatures = new ol.Collection();
	self.editSourceFeatures = new ol.source.Vector({features: self.editFeatures, wrapX: false});
	self.editVectorFeatures = new ol.layer.Vector({
		source: self.editSourceFeatures, 
		style: new ol.style.Style({
			/**Stile della linea**/
		    stroke: new ol.style.Stroke({color: 'rgba(200,0,0,1)',width: 10}),
		    /**Stile dei point sulla mappa**/
		    image: new ol.style.Circle({radius: 8,
		        /**Centro del punto**/
		        fill: new ol.style.Fill({color: 'rgba(255,255,255)',width: 5}),
		        /**Bordo del punto**/
		        stroke: new ol.style.Stroke({color: 'rgb(200,0,0,0.8)',width: 3})
		    }),
		})
	});
	(appMappa.maps).addLayer( self.editVectorFeatures );
	
	/**2. GESTIONE CREA/MODIFICA **/
	if (prop.newEntita){																													/**INSERISCI**/
		
		/**E' una nuova entità quindi avvio direttamente l'editing e quindi il disegno**/
		prop.geometryWKT = null; 
		self.geometrySelected = [];
		activeEditing(self, prop);
	} else if (!prop.newEntita && ((prop.stato)!=null && (prop.stato).indexOf('BOZZA')!=-1) ){
		
		/**Non è una nuova entità ma è in stato BOZZA quindi avvio direttamente l'editing e quindi il disegno**/
		if (( (prop.typeEditing).indexOf('Point')!=-1 || (prop.typeEditing).indexOf('LineString')!=-1) && 
				prop.geometryWKT != '' && prop.geometryWKT != null && prop.geometryWKT != undefined) {		  								/**MODIFICA POINT/LINESTRING**/
			
			/**2.2.2.1 Devo editare un punto, quindi attivo direttamente la modifica**/
			iziToast.show({
				title: '',
				theme: 'dark',
				icon: 'fa fa-info',
				message: 'Ora &eacute; possibile spostare i punti/linee, disegnare nuovi punti/linee per modificare l\'entit&aacute;',
				animateInside: false,
				position: 'bottomRight',
			});
			activeEditing(self, prop);
		} else if((prop.typeEditing).indexOf('Circle') != -1 && prop.geometryWKT != '' && prop.geometryWKT == null) {
			/**2.2.2.2 Devo editare un cerchio, quindi attivo direttamente la modifica**/
			iziToast.show({
				title: '',
				theme: 'dark',
				icon: 'fa fa-info',
				message: 'Ora &eacute; possibile disegnare un nuovo cerchio per modificare l\'entit&aacute;',
				animateInside: false,
				position: 'bottomRight',
			});
			activeEditing(self, prop);
		} 
	} else if (!prop.newEntita && ((prop.stato)!=null && (prop.stato).indexOf('PUBBLICATO')!=-1) ) {
		
		/**Non è una nuova entità, è in stato PUBBLICATO quindi avvio l'editing se la tipologia è POINT e l'editing e cuting se è LINESTRING**/
		if ( (prop.typeEditing).indexOf('Point')!=-1 ) {
			
			/**2.2.2.1 Devo editare un punto, quindi attivo direttamente la modifica**/
			iziToast.show({
				title: '',
				theme: 'dark',
				icon: 'fa fa-info',
				message: 'Ora &eacute; possibile spostare i punti o disegnare un nuovo punto per defenire l\'entit&aacute;',
				animateInside: false,
				position: 'bottomRight',
			});
			activeEditing(self, prop);
		} else if ( (prop.typeEditing).indexOf('LineString')!=-1 ){
			 	
			if ( prop.activeCuting ){
				/**2.2.2.2 Devo editare una LineString, quindi mostro box per scelta dell'attività di editing (taglia, modifica)  **/
				self.iToastChoice = iziToast.show({
					id: 'attivitaEditing',
					title: '', 
					message: '', 
					theme: 'dark', 
					icon:'',/*'fa fa-object-group',*/
					layout:1,
					maxWidth: 500,
					balloon: false, 
					displayMode: 'once',
					progressBar: false,
					position: 'bottomLeft',
					animateInside: false, 
					timeout: false,
					drag: false,
			        buttons: [
						[
						'<button type="button" title="Modifica Geometria" class="btn-trasp bttn" style="background-color: #004275;">Modifica Geometria</button>',
						 function (instance, toast) {
								iziToast.show({
									title: '',
									theme: 'dark',
									icon: 'fa fa-info',
									message: 'Ora &eacute; possibile, partendo dai punti o dalla linea mofidicare l\'entit&aacute;',
									animateInside: false,
									position: 'bottomRight',
								});
						 	activeEditing(self, prop);
							/** NASCONDO IL PULSANTE DEL CERCHIO */
							$('#editingToponimoStradaleIziToast #circleBtn').hide();
						 	instance.hide({
						 		transitionOut: 'fadeOutUp'
						 	}, toast);		            	
						 }
						],
						[
						 '<button id="dividiGeom" type="button" title="Dividi entita" class="btn-trasp bttn" style="background-color: #004275;">Dividi Geometria</button>',
						 function (instance, toast) {
						 	iziToast.show({
									title: '',
									theme: 'dark',
									icon: 'fa fa-info',
									message: 'Ora &eacute; possibile dividere, partendo dai punti o dalla linea mofidicare l\'entit&aacute;',
									animateInside: false,
									position: 'bottomRight',
								});
						 	activeCuting(self, prop);
						 	instance.hide({
						 		transitionOut: 'fadeOutUp'
						 	}, toast);
						 }
						]	
					]
				}); 
			} else {
				
				/**2.2.2.1 Devo editare un punto, quindi attivo direttamente la modifica**/
				iziToast.show({
					title: '',
					theme: 'dark',
					icon: 'fa fa-info',
					message: 'Ora &eacute; possibile spostare i punti o disegnare un nuovo punto per defenire l\'entit&aacute;',
					animateInside: false,
					position: 'bottomRight',
				});
				activeEditing(self, prop);
			}
		} else if((prop.typeEditing).indexOf('Circle') != -1) {
			/**2.2.2.1 Devo editare un punto, quindi attivo direttamente la modifica**/
			iziToast.show({
				title: '',
				theme: 'dark',
				icon: 'fa fa-info',
				message: 'Ora &eacute; possibile disegnare un nuovo cerchio per modificare l\'entit&aacute;',
				animateInside: false,
				position: 'bottomRight',
			});
			activeEditing(self, prop);
		} else if ( (prop.typeEditing).indexOf('Polygon')!=-1 ) {
			
			/**2.2.2.1 Devo editare un punto, quindi attivo direttamente la modifica**/
			iziToast.show({
				title: '',
				theme: 'dark',
				icon: 'fa fa-info',
				message: 'Ora &eacute; possibile spostare l\'area o disegnare una nuova area per defenire l\'entit&aacute;',
				animateInside: false,
				position: 'bottomRight',
			});
			activeEditing(self, prop);
		}
	}
}

/**
* Funzione che serve per la chiusura del toast strumenti quando la modale di selezione viene chiusa
* @param {*} idIziToast, id iziToast
*/
function chiusuraToastStrumentiQuandoLaModaleVieneChiusa(idIziToast) {
	$('#' + sessionStorage.getItem('windowOpened')).on('dialogclose', function(event, ui) {
		var iziToastStrumenti = document.getElementById(idIziToast);
		iziToast.hide({
			transitionOut: 'fadeOutUp'
		}, iziToastStrumenti);
	})
}

/**
 * Viene abilitato il taglio della entità
 * @param self
 * @returns
 */
function activeCuting(self, prop){
	
	self.editingAct 	= false;
	self.cutingAct	 	= true;
	/**INIZIALIZZAZIONI**/
	self.numCut				= 1;						//Numero di punti  tracciati sulla geometria
	sessionStorage.removeItem('numCut');
	self.numDefCut			= 0;						//Numero di tratti con anagrafica definita
	sessionStorage.removeItem('numDefCut');
	self.hmCutPoint			= new Object();				//Hashmap dei punti tracciati sulla geometria
	sessionStorage.removeItem('hmCutPoint');
	self.hmEntityCut		= new Object();				//Hashmap dei tratti definiti
	sessionStorage.removeItem('hmEntityCut');
	self.hmCoordsCut 		= new Object();				//Hashmap con le coordinate dei tratti
	sessionStorage.removeItem('hmCoordsCut');
	self.hmWktCut	 		= new Object();				//Hashmap con i wkt dei tratti
	(appMappa.maps).removeInteraction(self.draw);
    (appMappa.maps).removeInteraction(self.modify);
    (appMappa.maps).on('dblclick', function (e) {return;});

	$.each( appMappa.hmOverlay, function( keyOverlay, valueOverlay ) {
		(appMappa.maps).removeOverlay( valueOverlay );
	});
	$.each( self.hmCutPoint, function( keyPoint, coordsPoint ) {
		$("popupLayer-"+keyPoint).remove();
	});
	
	/**1. Inserisco su mappa la geometria				editSourceFeatures**/
	self.editSourceFeatures.clear();
	var format = new ol.format.WKT();
	var feature = format.readFeature(prop.geometryWKT, {
		dataProjection: 'EPSG:4326',
		featureProjection: 'EPSG:3857'
	});
	self.editSourceFeatures.addFeature(feature);
	var coordinates = feature.getGeometry().getCoordinates();
	self.pointFeatureList = [];
	$.each( coordinates, function( keyCoord, valueCoord ) {
		if( $.isArray(valueCoord) && valueCoord.length > 0 ){
			/**INIZIO**/
			var pointStartFeature = new ol.Feature(new ol.geom.Point( valueCoord[0] ));
			self.editSourceFeatures.addFeature( pointStartFeature );
			self.pointFeatureList.push(pointStartFeature);
			/**FINE**/
			var pointEndFeature = new ol.Feature(new ol.geom.Point(  valueCoord[valueCoord.length-1]  ));
			self.editSourceFeatures.addFeature( pointEndFeature );
			self.pointFeatureList.push(pointEndFeature);
		}
	});
	
	/**2. Inserisco il punto di partenza nell'Hashmap dei punti tracciati **/
	self.hmCutPoint[self.numCut] = coordinates[0][0];
	
	/**3. Salvo la vecchia geometria nello storage cosi da recuperarla quando taglio**/
	sessionStorage.setItem('oldGeomWkt', prop.geometryWKT);
	
	/**4. Aggiungo interazione di modifica per avere il punto sulla geometria quando devo tagliare**/
	self.drawStyle = new ol.style.Style({
		/**Stile dei point sulla mappa**/
	    image: new ol.style.Circle({radius: 7,
	        /**Centro del punto**/
	        fill: new ol.style.Fill({color: 'rgba(200,0,0,1)',width: 6}),
	        /**Bordo del punto**/
	        stroke: new ol.style.Stroke({color: 'rgb(255,255,255)',width: 1})
	    }),
	});
	self.modify = new ol.interaction.Modify({
		features: self.editFeatures,   
	    deleteCondition: function(event) {
			return ol.events.condition.shiftKeyOnly(event) &&
				ol.events.condition.singleClick(event);
		}
	});
	(appMappa.maps).addInteraction(self.modify);
	
	/**5. Gestione del DOPPIO CLICK per il taglio**/
	if (appMappa.events["dblclick"]==null) {
		(appMappa.maps).on('dblclick', function (e) {
			
			if (self.cutingAct) {
				/**INIZIALIZZO**/
				self.cuting				= true;
				self.numCut++;
				sessionStorage.setItem('numCut', self.numCut);
				/**5.1. Ripulisco la mappa dai popup**/
				$.each( appMappa.hmOverlay, function( keyOverlay, valueOverlay ) {
					(appMappa.maps).removeOverlay( valueOverlay );
				});
				$.each( self.hmCutPoint, function( keyPoint, coordsPoint ) {
					$("popupLayer-"+keyPoint).remove();
				});
				/**5.2. Inserisco il punto del doppio click sulla mappa**/
			    var coordinate = e.coordinate;
			    var pointFeature = new ol.Feature(new ol.geom.Point(  coordinate  ));
			    self.editSourceFeatures.addFeature( pointFeature );
			    self.pointFeatureList.push(pointFeature);
			    /**5.3. Inserisco il punto del doppio click nell'Hashmap dei punti tracciati**/
			    self.hmCutPoint[self.numCut]=coordinate;
			    /**5.4. Recupero le vecchie coordinate**/
			    var oldGeomWkt = sessionStorage.getItem('oldGeomWkt');
				var format = new ol.format.WKT();
				var oldFeature = format.readFeature(oldGeomWkt, {
					dataProjection: 'EPSG:4326',
					featureProjection: 'EPSG:3857'
				});
				var oldLineCoordinates 	= oldFeature.getGeometry().getCoordinates()[0];
			    /**5.5. Recupero le nuove coordinate**/
				var indexNewArray 		= 0;
				var newArrayGeometry 	= new Object();
				var newLineCoordinates 	= feature.getGeometry().getCoordinates()[0];				
				/**5.6. Popolo l'HashMap con le coordinate dei tratti **/
				var arrayCoords = [];
				var indexCoords = 0;
				if (oldLineCoordinates.length==newLineCoordinates.length) {

                    var arrayCoords = [];
					var indexCoords = 0;
					var iVC = 0;
					for (var iNC = 0;iNC<newLineCoordinates.length;iNC++){
						if( areCoordsEqual(newLineCoordinates[iNC],oldLineCoordinates[iVC]) ){
							arrayCoords.push(newLineCoordinates[iNC]);
							iVC++;
						} else {
							arrayCoords.push(newLineCoordinates[iNC]);
							self.hmCoordsCut[indexCoords] = arrayCoords;
							indexCoords++;
							var arrayCoords = [];
							arrayCoords.push(newLineCoordinates[iNC]);
						}
					}
				} else {
					
					var iVC = 0;
					for (var iNC = 0;iNC<newLineCoordinates.length;iNC++){
						if( areCoordsEqual(newLineCoordinates[iNC],oldLineCoordinates[iVC]) ){
							arrayCoords.push(newLineCoordinates[iNC]);
							iVC++;
						} else {
							arrayCoords.push(newLineCoordinates[iNC]);
							self.hmCoordsCut[indexCoords] = arrayCoords;
							indexCoords++;
							var arrayCoords = [];
							arrayCoords.push(newLineCoordinates[iNC]);
						}
					}
				}
				self.hmCoordsCut[indexCoords] = arrayCoords;
				sessionStorage.setItem('hmCoordsCut', JSON.stringify(self.hmCoordsCut));
				
				$.each( self.hmCoordsCut, function( keyCoord, valueCoord ) {
					var wkt = "MULTILINESTRING ((";
					$.each( valueCoord, function( keyGeom, valueGeom ) {
						wkt = wkt + valueGeom[0]+" "+valueGeom[1]+",";
					});
					wkt=wkt.replace(/.$/,"))");
					self.hmWktCut[keyCoord] = wkt;
				});
				sessionStorage.setItem('hmWktCut', JSON.stringify(self.hmWktCut));
				/**5.7. Inserisco sui punti tracciati e i popup per le nuove anagrafiche **/
				$.each( self.hmCutPoint, function( keyPoint, coordsPoint ) {
					/**5.7.1 Inizializzo il Popup**/
					var popup = {
							id: "popup-"+keyPoint,
							idPopup: keyPoint,
							classe: dettaglio.classe == null ? '' : dettaglio.classe.dug,
							denominazione:dettaglio.denominazione == null ? '' : dettaglio.denominazione,
							comune:	dettaglio.comune == null ? '' : dettaglio.comune.idComune,
							comuni: self.comuniMessina,
							geom:dettaglio.geom
					};
					/**5.7.2 Aggiungo il Popup in pagina**/
					$("body").append("<div id=\"popupLayer-"+keyPoint+"\" class=\"popupCutLayer\" style=\"z-index:"+keyPoint+"\"></div>");
					var containerLayer = document.getElementById('popupLayer-'+keyPoint);
					appMappa.hmOverlay[keyPoint] = new ol.Overlay({
				        										element				: containerLayer,
				        										autoPan				: true,
				        										autoPanAnimation	: {
				        											duration: 250
				        										}
				    });
					(appMappa.maps).addOverlay( appMappa.hmOverlay[keyPoint] );
					(appMappa.hmOverlay[keyPoint]).setPosition(coordsPoint);
					/**5.7.3 Valorizzo il contenuto del popup**/
					var sourcePopup = document.getElementById("popupCutingTemplate").innerHTML;
					var templatePopup = Handlebars.compile(sourcePopup);
				    var outputPopup = templatePopup(popup);
					$("#popupLayer-"+keyPoint).html(outputPopup);
					//appUtil.activeAutocompleteOnFiled('classeToponimoM-'+keyPoint, appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...');
					appUtil.activeAutocompleteOnFiled('classeToponimoM-'+keyPoint, appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/getDug', 'dug', 'dug', 'dug', 'Dug...');
				});
			    
				/**5.8. Salvataggio smart nuova entity **/
				$(".cutLayerContainer .btn").click(function() {
					/**Recupero dei dati**/
					var idEntity = $(this).attr("id");
					if ( $("#classeToponimoM-"+idEntity).val()!=undefined && $("#classeToponimoM-"+idEntity).val()!="" && 
							$("#descrizioneToponimoM-"+idEntity).val()!=undefined && 
							$("#comuneToponimoM-"+idEntity).val()!=undefined && $("#comuneToponimoM-"+idEntity).val()!="") {
						var hmWktCut = JSON.parse(sessionStorage.getItem('hmWktCut'));
						var wkt = format.writeGeometry( format.readGeometry(self.hmWktCut[(idEntity-1)]).transform('EPSG:3857', 'EPSG:4326') );
						var newEntity = {
										id:idEntity,
										classeLabel:$("#classeToponimoM-"+idEntity).val(),
										classe:$("#classeToponimoM-"+idEntity+"Value").val(),
										denominazione:$("#descrizioneToponimoM-"+idEntity).val().toLocaleUpperCase(),
										denominazioneUfficiale: $("#classeToponimoM-"+idEntity).val() + ' ' + $("#descrizioneToponimoM-"+idEntity).val().toLocaleUpperCase(),
										shapeLeng: wkt,
										comuneLabel:$('#comuneToponimoM-'+idEntity+' option:selected').text(),
										comune:$("#comuneToponimoM-"+idEntity).val(),
										geom:wkt,
										stato:"DA DEFINIRE"
						};
						$("#classeToponimoM-"+idEntity+"Value").val('')
						/**Riduco il popup**/
						appMappa.riduciCutLayerPopup( idEntity );
						/**Salvo in session temporamente le nuove entity**/
						if (self.hmEntityCut[idEntity-1]==null) self.numDefCut++;
						/**Salvo in session temporamente le nuove entity**/
						self.hmEntityCut[idEntity-1] = newEntity;
						sessionStorage.setItem('hmEntityCut', JSON.stringify(self.hmEntityCut));
						sessionStorage.setItem('numDefCut', self.numDefCut );
						/**Visualizzo il check**/
						$("#check-"+idEntity).show();
					} else {
						/**Nascondo il check**/
						$("#check-"+idEntity).hide();
						/****/
						iziToast.error({
	    	    			title: 'Attenzione',
	    	    			theme: 'dark',
	    	    			icon:'fa fa-times',
	    	    			message: 'Devono essere compilati tutti i campi',
	    	    			animateInside: false,
	    	    			position: 'topCenter',
						});
					}
				});
				
				(appMappa.maps).getView().setZoom(12);
			}
		});
		appMappa.events["dblclick"] = true;
	}
	
	/********************************/
	/**6. APERTURA TOAST STRUMENTI **/
	self.iToast = iziToast.show({
		id: prop.idIziToast,
		title: 'Dividi Geometria', 
		message: '', 
		theme: 'dark', 
		icon:'',/*'fa fa-object-group',*/
		layout:1,
		maxWidth: 500,
		balloon: false, 
		displayMode: 'once',
		progressBar: false,
		position: 'bottomLeft',
		animateInside: false, 
		timeout: false,
		drag: false,
		onOpened: function(data){
			chiusuraToastStrumentiQuandoLaModaleVieneChiusa(data.id);
			/**1. Ripulisco la mappa**/
//			if ( !self.cuting ) sessionStorage.setItem('wktEditFeatureGeom', "");
//	        (appMappa.maps).removeInteraction(self.draw);
//	        (appMappa.maps).removeInteraction(self.modify);
//	        self.editSourceFeatures.clear();
		},
		onClosed: function(instance, toast, closedBy){
			/**1. Riapro la modale **/
			minifize( prop.pagina );
			/**2. Ripulisco la mappa**/
			if ( !self.cuting ) sessionStorage.setItem('wktEditFeatureGeom', "");
	        (appMappa.maps).removeInteraction(self.draw);
	        (appMappa.maps).removeInteraction(self.modify);
	        self.editSourceFeatures.clear();
	        if (self.cuting){
	        	/**2.1. Ripulisco la mappa dai popup**/
	    		$.each( appMappa.hmOverlay, function( keyOverlay, valueOverlay ) {
	    			(appMappa.maps).removeOverlay( valueOverlay );
	    		});
	    		$.each( self.hmCutPoint, function( keyPoint, coordsPoint ) {
	    			$("popupLayer-"+keyPoint).remove();
	    		});
	        }
	        $.each( prop.activeLayers, function( index, value ) {
	        	appMappa.addRemoveLayer(value.layerName, value.layerTitle, null, '', '');
	        });
	        /**3. Visualizzo messaggio se non è stata modificata la geometria**/
	        if ( !self.cuting && !self.modified){ 
		        iziToast.show({
					title: '',
					theme: 'dark',
					icon: 'fa fa-info',
					message: 'Chiudendo l\'editing, l\'entit&aacute; non &eacute; stata modificata',
					animateInside: false,
					position: 'bottomRight',
				});
			}
	        /**
	         * 4. Riapro con un dettaglio: 
	         *  - empty, se è una nuova entità
	         *  - valorizzata, se è una entità esitente
	         **/
	        if (self.properties.newEntita)
	        	self.visualizzaDettaglio( null );
	        else if (!self.properties.newEntita && self.cuting && self.confermaDisegno){
	        	self.visualizzaDettaglio( self.idDettaglio );
	        } else {
	        	self.numCut				= 1;						//Numero di punti  tracciati sulla geometria
	        	sessionStorage.removeItem('numCut');
	        	self.numDefCut			= 0;						//Numero di tratti con anagrafica definita
	        	sessionStorage.removeItem('numDefCut');
	        	self.hmCutPoint			= new Object();				//Hashmap dei punti tracciati sulla geometria
	        	sessionStorage.removeItem('hmCutPoint');
	        	self.hmEntityCut		= new Object();				//Hashmap dei tratti definiti
	        	sessionStorage.removeItem('hmEntityCut');
	        	self.hmCoordsCut 		= new Object();				//Hashmap con le coordinate dei tratti
	        	sessionStorage.removeItem('hmCoordsCut');
	        	self.hmWktCut	 		= new Object();				//Hashmap con i wkt dei tratti
	        	sessionStorage.removeItem('hmWktCut');
	        	self.visualizzaDettaglio( self.idDettaglio );
	        } 
	    },
		buttons: [
			[
				'<button type="button" title="Rimuovi tutti i tagli" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
				function(instance, toast){
					activeCuting(self, prop);
				}
			],
			[
				'<button type="button" title="Conferma tagli" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina</button>',
				function(instance, toast){
					self.confermaFeatureDisegnate();
				}
			]
		],
	});
	
	
}

/**
 * Viene abilitato la modifica della entità
 * @param self
 * @param geomWKT
 * @param typeEditing
 * @returns
 */
function activeEditing(self, prop){
    
	self.editingAct 	= true;
	self.cutingAct 		= false;
	/**1 Inserisco su mappa la geometria**/
	self.editSourceFeatures.clear();
	if (prop.geometryWKT!=null) {
		
		/**COSTRUISCE FEATURE DAL WKT**/
		var format = new ol.format.WKT();
		var feature = format.readFeature(prop.geometryWKT, {
			dataProjection: 'EPSG:4326',
			featureProjection: 'EPSG:3857'
		});
		/**AGGIUNGE LA FEATURE AL LAYER CON ZOOM**/
		self.editSourceFeatures.addFeature(feature);
		(appMappa.maps).getView().fit(feature.getGeometry(), { padding: [0, 0, 0, 0] });
		(appMappa.maps).getView().setZoom(13);
	}
	
	/**2 Imposto lo stile per disegnare**/
	self.drawStyle = new ol.style.Style({
		/**Stile della linea**/
		stroke: new ol.style.Stroke({color: 'rgba(200,0,0,1)',width: 8}),
		/**Stile dei point sulla mappa**/
	    image: new ol.style.Circle({radius: 8,
	        /**Centro del punto**/
	        fill: new ol.style.Fill({color: 'rgba(200,0,0,1)',width: 5}),
	        /**Bordo del punto**/
	        stroke: new ol.style.Stroke({color: 'rgb(255,255,255)',width: 3})
	    }),
	});
	
	/** 3. Interazione di modifica**/
	self.modify = new ol.interaction.Modify({
		features: self.editFeatures,   
	    deleteCondition: function(event) {
			return ol.events.condition.shiftKeyOnly(event) &&
				ol.events.condition.singleClick(event);
		}
	});
	(appMappa.maps).addInteraction(self.modify);
	self.modify.on('modifystart', function(e) {
		if (self.editingAct) {
			let features = self.editSourceFeatures.getFeatures();
			self.modified = true;
		}
	});
	self.modify.on('modifyend', function (e) {
		if (self.editingAct) {
			let features = e.features.getArray();
			self.geometrySelected = features;
			self.modified = true;
		}
	});
	
	/** 4. Interazione di disegna, abilitato solo se:
	 *   - stai inserendo una nuova entità 
	 *   - stai modificando una LineString
	 **/
	if (prop.newEntita || prop.geometryWKT==null) {
		self.freeHand = false;
		self.draw = new ol.interaction.Draw({
			features: self.editFeatures,
		    type: prop.typeEditing,
		    style: self.drawStyle,
		    freehand: self.freeHand,
		});
		(appMappa.maps).addInteraction(self.draw);	
		self.draw.on('drawstart', function(e) {
			if (self.editingAct) {
				if ( prop.newEntita && self.editSourceFeatures.getFeatures().length>0 ){
					self.editSourceFeatures.removeFeature( self.editSourceFeatures.getFeatures()[self.editSourceFeatures.getFeatures().length-1] );
				}
				let features = self.editSourceFeatures.getFeatures();
				self.modified = true;
			}
			if(prop.typeEditing.indexOf('Circle') != -1) {
				let features = self.editSourceFeatures.getFeatures();
                
                if($.isArray(features) && features.length > 0) {
                    appUtil.confirmOperation(function() {
                        for(var i=0; i<features.length; i++){
                            self.editSourceFeatures.removeFeature( features[i] );
                        }

                        (appMappa.maps).removeInteraction(self.draw);
                        (appMappa.maps).removeInteraction(self.modified);
                        activeEditing(self, prop);
                        
                    }, function() { /** ANNULLA */
                        self.draw.finishDrawing();
                        activeEditing(self, prop);
                    }, null, 'Iniziando a disegnare una nuova geometria, perderai quella precedente. Desideri continuare comunque?')
                }
			}
		});
		self.draw.on('drawend', function (e) {
			if (self.editingAct) {
				self.feature = e.feature;
				self.geometrySelected = self.feature.getGeometry();
				self.modified = true;
			}
		});
	} 
	
	/**5. APERTURA TOAST STRUMENTI **/
	if (prop.typeEditing == 'Point') {
		/**5.1. Finestra per un Point**/
		self.iToast = iziToast.show({
			id: prop.idIziToast,
			title: 'Modifica Geometria', 
			message: '', 
			theme: 'dark', 
			icon:'',/*'fa fa-object-group',*/
			layout:1,
			maxWidth: 500,
			balloon: false, 
			displayMode: 'once',
			progressBar: false,
			position: 'bottomLeft',
			animateInside: false, 
			timeout: false,
			drag: false,
			onOpened: function(data){
				chiusuraToastStrumentiQuandoLaModaleVieneChiusa(data.id);
			},
			onClosed: function(instance, toast, closedBy){
				/**1. Riapro la modale **/
				minifize( prop.pagina );
				/**2. Ripulisco la mappa**/
				if ( !self.modified ) sessionStorage.setItem('wktEditFeatureGeom', "");
		        (appMappa.maps).removeInteraction(self.draw);
		        (appMappa.maps).removeInteraction(self.modify);
		        self.editSourceFeatures.clear();
		        $.each( prop.activeLayers, function( index, value ) {
		        	appMappa.addRemoveLayer(value.layerName, value.layerTitle, null, '', '');
		        });
		        /**3. Visualizzo messaggio se non è stata modificata la geometria**/
		        if ( !self.modified ){ 
			        iziToast.show({
						title: '',
						theme: 'dark',
						icon: 'fa fa-info',
						message: 'Chiudendo l\'editing, l\'entit&aacute; non &eacute; stata modificata',
						animateInside: false,
						position: 'bottomRight',
					});
				}
		        /**4. Riapro con un dettaglio vuoto**/
		        if ( self.modified && sessionStorage.getItem('wktEditFeatureGeom')!="" && self.confermaDisegno) self.visualizzaDettaglio(self.idDettaglio);
		    },
			buttons: [
				[
					'<button type="button" title="Conferma modifica" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina</button>',
					function(instance, toast){
						self.confermaFeatureDisegnate();
					}
				]
			],
		});
	}
	if (prop.typeEditing == 'LineString') {
		/**5.2. Finestra per una LineString**/
		self.iToast = iziToast.show({
			id: prop.idIziToast,
			title: 'Modifica Geometria', 
			message: '', 
			theme: 'dark', 
			icon:'',/*'fa fa-object-group',*/
			layout:1,
			maxWidth: 500,
			balloon: false, 
			displayMode: 'once',
			progressBar: false,
			position: 'bottomLeft',
			animateInside: false, 
			timeout: false,
			drag: false,
			onOpened: function(data){
				chiusuraToastStrumentiQuandoLaModaleVieneChiusa(data.id);
			},
			onClosed: function(instance, toast, closedBy){
				/**1. Riapro la modale **/
				minifize( prop.pagina );
				/**2. Ripulisco la mappa**/
				if ( !self.modified ) sessionStorage.setItem('wktEditFeatureGeom', "");
		        (appMappa.maps).removeInteraction(self.draw);
		        (appMappa.maps).removeInteraction(self.modify);
		        self.editSourceFeatures.clear();
				$.each( appMappa.hmOverlay, function( keyOverlay, valueOverlay ) {
					(appMappa.maps).removeOverlay( valueOverlay );
				});
				$.each( self.hmCutPoint, function( keyPoint, coordsPoint ) {
					$("popupLayer-"+keyPoint).remove();
				});
		        $.each( prop.activeLayers, function( index, value ) {
		        	appMappa.addRemoveLayer(value.layerName, value.layerTitle, null, '', '');
		        });
		        /**3. Visualizzo messaggio se non è stata modificata la geometria**/
		        if ( !self.modified ){ 
			        iziToast.show({
						title: '',
						theme: 'dark',
						icon: 'fa fa-info',
						message: 'Chiudendo l\'editing, l\'entit&aacute; non &eacute; stata modificata',
						animateInside: false,
						position: 'bottomRight',
					});
				}
				// prop.typeEditing == 'LineString'
		        /**4. Riapro con un dettaglio vuoto**/
		        if ( self.modified && sessionStorage.getItem('wktEditFeatureGeom')!="" && self.confermaDisegno) self.visualizzaDettaglio(self.idDettaglio);
		    },
			buttons: [
				[
					'<button id="typeDraw" type="button" title="Tracciato su mappa a mano libera" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-paint-brush"></em>&nbsp;</button>',
					function(instance, toast){
						//freeHandFeatureDisegnate(self, prop.typeEditing );
						self.editSourceFeatures.clear();
						self.typeEditing 	= 'LineString';
						self.freeHand 		= true;
						self.drawStyle = new ol.style.Style({
							stroke: new ol.style.Stroke({color: 'rgba(200,0,0,1)',width: 5}),
							/**Stile dei point sulla mappa**/
						    image: new ol.style.Circle({radius: 5,
						        /**Centro del punto**/
						        fill: new ol.style.Fill({color: 'rgba(200,0,0,1)',width: 6}),
						        /**Bordo del punto**/
						        stroke: new ol.style.Stroke({color: 'rgb(255,255,255)',width: 1})
						    }),
						});
						/** MODIFICA**/
						(appMappa.maps).removeInteraction(self.modify);
						self.modify = new ol.interaction.Modify({
							features: self.editFeatures,   
						    deleteCondition: function(event) {
								return ol.events.condition.shiftKeyOnly(event) &&
									ol.events.condition.singleClick(event);
							}
						});
						(appMappa.maps).addInteraction(self.modify);
						/** DISEGNA**/
						(appMappa.maps).removeInteraction(self.draw);
						self.draw = new ol.interaction.Draw({
							features: self.editFeatures,
						    type: self.typeEditing,
						    style: self.drawStyle,
						    freehand: self.freeHand,
						});
						(appMappa.maps).addInteraction(self.draw);	
						self.draw.on('drawstart', function(e) {
							self.editSourceFeatures.clear();
							let features = self.editSourceFeatures.getFeatures();
							self.modified = true;
						});
						self.modify.on('modifystart', function(e) {
							self.editSourceFeatures.clear();
							let features = self.editSourceFeatures.getFeatures();
							self.modified = true;
						});
						self.draw.on('drawend', function (e) {
							self.feature = e.feature;
							self.geometrySelected = self.feature.getGeometry();
							self.modified = true;
						});
						self.modify.on('modifyend', function (e) {
							let features = e.features.getArray();
							self.geometrySelected = features;
							self.modified = true;
						});
						/**MESSAGGIO**/
						iziToast.show({
							title: '',
							theme: 'dark',
							icon: 'fa fa-info',
							message: 'E\' stato abilitato il disegno a mano libera',
							animateInside: false,
							position: 'bottomRight',
						});
					}
				],
				[
    
                        '<button type="button" title="Linea" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-arrows-h"></em>&nbsp;Linea</button>',
                        function(instance, toast) {
                            (appMappa.maps).removeInteraction(self.draw);
                            (appMappa.maps).removeInteraction(self.modify);
    
                            prop.typeEditing = 'LineString'
                            activeEditing(self, prop);
                        }
    
                    ],
				[
    
                   '<button id="circleBtn" type="button" title="Cerchio" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-circle-o-notch"></em>&nbsp;Cerchio</button>',
                    function(instance, toast) {
						(appMappa.maps).removeInteraction(self.draw);
                        (appMappa.maps).removeInteraction(self.modify);

                        prop.typeEditing = 'Circle';
                        activeEditing(self, prop);
                    }
                ],
				[
					'<button type="button" title="Rimuovi tracciato su mappa" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
					function(instance, toast){
						activeEditing(self, prop);
					}
				],
				[
					'<button type="button" title="Rimuovi ultimo tracciato su mappa" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-undo"></em>&nbsp;Indietro</button>',
					function(instance, toast){
						rimuoviUltimeFeatureDisegnate( self, prop );
					}
				],
				[
					'<button type="button" title="Conferma modifica" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina</button>',
					function(instance, toast){
						self.confermaFeatureDisegnate();
					}
				]
			],
		});
	} 
	if(prop.typeEditing == 'Circle') {
		/**5.3. Finestra per una Circle**/
		self.iToast = iziToast.show({
			id: prop.idIziToast,
			title: 'Modifica Geometria', 
			message: '', 
			theme: 'dark', 
			icon:'',/*'fa fa-object-group',*/
			layout:1,
			maxWidth: 500,
			balloon: false, 
			displayMode: 'once',
			progressBar: false,
			position: 'bottomLeft',
			animateInside: false, 
			timeout: false,
			drag: false,
			onOpened: function(data){
				chiusuraToastStrumentiQuandoLaModaleVieneChiusa(data.id);
			},
			onClosed: function(instance, toast, closedBy){
				/**1. Riapro la modale **/
				minifize( prop.pagina );
				/**2. Ripulisco la mappa**/
				if ( !self.modified ) sessionStorage.setItem('wktEditFeatureGeom', "");
		        (appMappa.maps).removeInteraction(self.draw);
		        (appMappa.maps).removeInteraction(self.modify);
		        self.editSourceFeatures.clear();
				$.each( appMappa.hmOverlay, function( keyOverlay, valueOverlay ) {
					(appMappa.maps).removeOverlay( valueOverlay );
				});
				$.each( self.hmCutPoint, function( keyPoint, coordsPoint ) {
					$("popupLayer-"+keyPoint).remove();
				});
		        $.each( prop.activeLayers, function( index, value ) {
		        	appMappa.addRemoveLayer(value.layerName, value.layerTitle, null, '', '');
		        });
		        /**3. Visualizzo messaggio se non è stata modificata la geometria**/
		        if ( !self.modified ){ 
			        iziToast.show({
						title: '',
						theme: 'dark',
						icon: 'fa fa-info',
						message: 'Chiudendo l\'editing, l\'entit&aacute; non &eacute; stata modificata',
						animateInside: false,
						position: 'bottomRight',
					});
				}
		        /**4. Riapro con un dettaglio vuoto**/
		        if ( self.modified && sessionStorage.getItem('wktEditFeatureGeom')!="" && self.confermaDisegno) self.visualizzaDettaglio(self.idDettaglio);
		    },
			buttons: [
				[
					'<button type="button" title="Rimuovi tracciato su mappa" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
					function(instance, toast){
						/** PULIZIA DELLA GEOMETRIA */
						self.editSourceFeatures.clear();
					}
				],
				[
					'<button type="button" title="Conferma modifica" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina</button>',
					function(instance, toast){
						self.confermaFeatureDisegnate();
					}
				]
			],
		});
	}
	if (prop.typeEditing == 'Polygon') {
		var buttonsPolygon = [];
		if(self.drawLocalita){
			buttonsPolygon = [[
				'<button type="button" title="Rimuovi tracciato su mappa" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
				function(instance, toast){
					activeEditing(self, prop);
				}],
				[
				'<button type="button" title="Conferma modifica" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina</button>',
				function(instance, toast){
					self.confermaFeatureDisegnate();
				}],
				[
				'<button type="button" title="Avanti senza disegnare" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina senza geometria</button>',
				function(instance, toast){
					self.confermaFeatureDisegnate();
				}]];
		} else {
			buttonsPolygon = [[
				'<button type="button" title="Rimuovi tracciato su mappa" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
				function(instance, toast){
					activeEditing(self, prop);
				}],
				[
				'<button type="button" title="Conferma modifica" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina</button>',
				function(instance, toast){
					self.confermaFeatureDisegnate();
				}]];
		}
		/**5.3. Finestra per una Polygon**/
		self.iToast = iziToast.show({
			id: prop.idIziToast,
			title: 'Modifica Geometria', 
			message: '', 
			theme: 'dark', 
			icon:'',/*'fa fa-object-group',*/
			layout:1,
			maxWidth: 500,
			balloon: false, 
			displayMode: 'once',
			progressBar: false,
			position: 'bottomLeft',
			animateInside: false, 
			timeout: false,
			drag: false,
			onOpened: function(data){
				chiusuraToastStrumentiQuandoLaModaleVieneChiusa(data.id);
			},
			onClosed: function(instance, toast, closedBy){
				/**1. Riapro la modale **/
				minifize( prop.pagina );
				/**2. Ripulisco la mappa**/
				if ( !self.modified ) sessionStorage.setItem('wktEditFeatureGeom', "");
		        (appMappa.maps).removeInteraction(self.draw);
		        (appMappa.maps).removeInteraction(self.modify);
		        self.editSourceFeatures.clear();
		        $.each( prop.activeLayers, function( index, value ) {
		        	appMappa.addRemoveLayer(value.layerName, value.layerTitle, null, '', '');
		        });
		        /**3. Visualizzo messaggio se non è stata modificata la geometria**/
		        if ( !self.modified && self.drawLocalita ){
		        	self.visualizzaDettaglio(self.idDettaglio);
		        } else if ( !self.modified && !self.drawLocalita ){ 
			        iziToast.show({
						title: '',
						theme: 'dark',
						icon: 'fa fa-info',
						message: 'Chiudendo l\'editing, l\'entit&aacute; non &eacute; stata modificata',
						animateInside: false,
						position: 'bottomRight',
					});
				}
		        /**4. Riapro con un dettaglio vuoto**/
		        if ( self.modified && sessionStorage.getItem('wktEditFeatureGeom')!="" && self.confermaDisegno) self.visualizzaDettaglio(self.idDettaglio);
		    },
			buttons: buttonsPolygon,
		});
	}
//	/*************************************/
//	/**5. AGGIUNGI EVENTO KEYDOWN_CTRLZ **/
//	$(document).off('keydown').on('keydown', function(event) {
//		if(event.keyCode === 90) {
//            if (event.metaKey || event.ctrlKey) {
//                draw.removeLastPoint();
//            }
//		}
//	});
}

/**
 * Il metodo abilita il disegno a mano libera
 * @param self,
 * @typeEditing, tipologia di geometria che si intende disegnare (POINT, LINESTRING, MULTILINESTRING)
 */
function freeHandFeatureDisegnate(self, typeEditing){
	self.typeEditing 	= typeEditing;
	self.freeHand 		= !self.freeHand;
	self.drawStyle = new ol.style.Style({
		stroke: new ol.style.Stroke({color: 'rgba(200,0,0,1)',width: 5}),
		/**Stile dei point sulla mappa**/
	    image: new ol.style.Circle({radius: 5,
	        /**Centro del punto**/
	        fill: new ol.style.Fill({color: 'rgba(200,0,0,1)',width: 6}),
	        /**Bordo del punto**/
	        stroke: new ol.style.Stroke({color: 'rgb(255,255,255)',width: 1})
	    }),
	});
	/** MODIFICA**/
	(appMappa.maps).removeInteraction(self.modify);
	self.modify = new ol.interaction.Modify({
		features: self.editFeatures,   
	    deleteCondition: function(event) {
			return ol.events.condition.shiftKeyOnly(event) &&
				ol.events.condition.singleClick(event);
		}
	});
	(appMappa.maps).addInteraction(self.modify);
	/** DISEGNA**/
	(appMappa.maps).removeInteraction(self.draw);
	self.draw = new ol.interaction.Draw({
		features: self.editFeatures,
	    type: self.typeEditing,
	    style: self.drawStyle,
	    freehand: self.freeHand,
	});
	(appMappa.maps).addInteraction(self.draw);	
	self.draw.on('drawstart', function(e) {
		let features = self.editSourceFeatures.getFeatures();
		self.modified = true;
	});
	self.modify.on('modifystart', function(e) {
		let features = self.editSourceFeatures.getFeatures();
		self.modified = true;
	});
	self.draw.on('drawend', function (e) {
		self.feature = e.feature;
		self.geometrySelected = self.feature.getGeometry();
		self.modified = true;
	});
	self.modify.on('modifyend', function (e) {
		let features = e.features.getArray();
		self.geometrySelected = features;
		self.modified = true;
	});
	/**MESSAGGIO**/
	iziToast.show({
		title: '',
		theme: 'dark',
		icon: 'fa fa-info',
		message: 'E\' stato abilitato il disegno a mano libera',
		animateInside: false,
		position: 'bottomRight',
	});
}

/**
 * Rimuovo ultima feature disegnata
 */
function rimuoviUltimeFeatureDisegnate( self, prop ){
	let features = self.editSourceFeatures.getFeatures();
	if( $.isArray(features) && features.length > 0 && prop.newEntita ){
		
		self.editSourceFeatures.removeFeature( features[features.length-1] );
	} else if (  $.isArray(features) && features.length > 1 && !prop.newEntita ){
		
		self.editSourceFeatures.removeFeature( features[features.length-1] );
	} else if (  $.isArray(features) && features.length == 1 && !prop.newEntita ){
		
		activeEditing(self, prop);
	}
}

function definisciAnagrafica(idPopup){
	var classeToponimoM = $("#popupLayer-"+idPopup+" .classeToponimoM");
	var descrizioneToponimoM = $("#popupLayer-"+idPopup+" .descrizioneToponimoM");
	var comuneToponimoM = $("#popupLayer-"+idPopup+" .comuneToponimoM");
	
	appMappa.riduciCutLayerPopup(idPopup);
}

// Check for property equality between two ol.Feature objects
function areCoordsEqual(coordA, coordB){
	if (coordA[0]==coordB[0] && coordA[1]==coordB[1]) return true;
    else return false;
}
/****************************************************************************************************************************************************************/
/************ METODI COMUNI PER L'EDITING DELLE GEOMETRIE *************************************************************************************** INIZIO ********/
/****************************************************************************************************************************************************************/