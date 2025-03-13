'use strict'

/**
 * Controller principale della pagina catasto. 
 * Fondamentalmente aggiunge solo degli eventi di click che aprono un oggetto controller appropriato che gestisce
 * la pagina di ricerca.
 */
class PianoRegolatorePageCtrl {
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
		
		$('#gestioneVarianti').off('click').on('click', function(event){
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaGestioneVariantiPianoCtrl();
		});
		
		$('#strutturaPiano').off('click').on('click', function(event){
			if ( sessionStorage.getItem('windowOpened')!=null ) 
				closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaStrutturaPianoCtrl();
		});
	
		$('#strutturaPianoTipiDocumento').off('click').on('click', function(event){
			if ( sessionStorage.getItem('windowOpened')!=null ) 
			closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaStrutturaPianoTipiDocumentoCtrl();
		});
		
		$('#certificatoUrbanistico').off('click').on('click', function(event){
			if ( sessionStorage.getItem('windowOpened')!=null ) 
			closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaCertificatoUrbanisticoCtrl();
		});
		
		$('#strutturaPianoStratiInformativi').off('click').on('click', function(event){
			if ( sessionStorage.getItem('windowOpened')!=null ) 
			closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaStruttraPianoStatiInformativiCtrl();
		});

		$('#interrogazionePiano').off('click').on('click', function(event){
			if ( sessionStorage.getItem('windowOpened')!=null ) 
			closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaInterrogazionePianoCtrl();
		});
	
		$('#anagraficaCdu').off('click').on('click', function(event){
			if ( sessionStorage.getItem('windowOpened')!=null ) 
			closeStaticDetail( sessionStorage.getItem('windowOpened')  ); new PaginaAnagraficaCduCtrl();
		});
		
	}
}

/**
 * Classe controller per la gestione delle varianti
 */
class PaginaGestioneVariantiPianoCtrl extends BaseJsTreeModaleRicercaCtrl {
	constructor( ) {
		super('paginaGestioneVariantiPiano','GESTIONE VARIANTI', 'gestioneVariantiPiano');
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			sessionStorage.removeItem('layerSelezionati');
			$("#enteRiferimentoVariante").val("F158");
			$("#dataAdozioneDalVariante").datepicker();
			$("#dataAdozioneAlVariante").datepicker();
			$("#dataApporvazioneDalVariante").datepicker();
			$("#dataApporvazioneAlVariante").datepicker();

			$("#dataAdozioneDettaglio").datepicker();
			$("#dataApprovazioneDettaglio").datepicker();

			self.aggiungiEventiTornaIndietroARicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.aggiungiEventiCreaNuovaVariante();
			self.aggiungiEventiSalvaVariante();
			self.inizializzaBreadcrumb();
			self.nascondiPaginaDettaglio();
			self.inizializzaJsTreeGrid('#menu-cartografia-jstree', '#plugins4_q', true);	//idDivJsTree, idInputRicerca, isRicercaAbilitata //TODO magari passare il menu
			self.nomeVariante;
			$("#menu-cartografia-jstree").on("deselect_node.jstree", function (e, data) {
				//MEGA-IPER-PEZZOTTONE
				let nome = data.node.original.dettaglioAreaTematica.layer.name;
				let itemViewerId = appUtil.generateId(nome);
				let checkbox = $("#itemViewer_"+itemViewerId).find('input:checkbox');
				if( $(checkbox).prop('checked') === true ){
					$(checkbox).trigger('change');
					$(checkbox).prop('checked', false);
				}
			});
			
			$("#menu-cartografia-jstree").on('select_node.jstree', function (event, data) {
	            if (data.node.data.layer && data.node.data.layer === true) {
	            	let nome = data.node.original.dettaglioAreaTematica.layer.name;
					let href = data.node.original.dettaglioAreaTematica.layer.resource.href;
					self.addLayerVisualizzatore(nome, href, true);
	            } else {
	            	//pezzottone! se non è di tipo layer (quindi una "foglia") forza una DESELEZIONE
	            	//così da simulare una situazione in cui si possono selezionare solo le foglie
	            	$("#menu-cartografia-jstree").jstree(true).deselect_node(data.node);                    
	            	$("#menu-cartografia-jstree").jstree(true).toggle_node(data.node);                    
	            }
	        });
			self.aggiungiEventiEstrazioneAmbitoVariante();
			appUtil.reloadTooltips();
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});

		$('#tabellaRisultatiVariante').hide();

		$( "#eseguiBtnVariante" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			self.inizializzaDatatableRicerca();
		});
	
		$( "#azzeraBtnVariante" ).click(function() {
			$('#nomeVariante').val('');
			$('#descrizioneVariante').val('');
			$('#dataAdozioneDalVariante').val('');
			$('#dataAdozioneAlVariante').val('');
			$('#dataApporvazioneDalVariante').val('');
			$("#dataApporvazioneAlVariante").val('');
		});

		$("#dataAdozioneDettaglio").change(function() {
			var campo = $("#dataAdozioneDettaglio").val();
			if (campo == null || campo == "") {
				document.getElementById("dataApprovazioneDettaglio").disabled = true;
				document.getElementById("numeroApprovazioneDettaglio").disabled = true;
				document.getElementById("organoApprovazioneDettaglio").disabled = true;
				document.getElementById("noteApprovazioneDettaglio").disabled = true;
			} else {
				document.getElementById("dataApprovazioneDettaglio").disabled = false;
				document.getElementById("numeroApprovazioneDettaglio").disabled = false;
				document.getElementById("organoApprovazioneDettaglio").disabled = false;
				document.getElementById("noteApprovazioneDettaglio").disabled = false;
			}
		})
	
		$( "#nuovoDocumento" ).click(function() {
			self.apriModaleCreaModificaVersioneDocumento(null);
		});

		$( "#aggiungiCartografia" ).click(function() {
			self.apriModaleSelezionaCartografie();
		});

		$( "#visualizzaCartografia" ).click(function() {
			
			var layerVariante = [];
			
			if (document.getElementById("visualizzatorelayerVariante") === null) {
				var gruppo = {'idGruppo':'layerVariante', 'nomeGruppo':self.nomeVariante};
				htmlGruppo = compilaTemplate("contGruppoV", gruppo);
				$("#boxVisualizzatoreMappa").append( htmlGruppo );
			}

			appMappa.deselezionaTuttiLayer();
			$('#visualizzatoreDatiBaseVariante_chk').prop('checked', true);
			
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			
			var selezionati = [];
			var layersCatalogo = [];
			var oldSelezionati = JSON.parse(sessionStorage.getItem('layerSelezionati'));

			selezionati = JSON.parse(sessionStorage.getItem('layersInSessione'));
			/** Aggiorno la lista layerVariante */

			if (oldSelezionati !== null && oldSelezionati.length > 0) {
				$.each( oldSelezionati, function( key, item ) {
					var split = item.split("+");
					var layerName = split[0];
					appMappa.removeLayer(layerName);
					if ( appUtil.containsString(appUrbamid.layerAddedViewVariante, layerName) ) {
						itemViewerId = appUtil.generateId(layerName);
						appUrbamid.layerAddedViewVariante.splice($.inArray(layerName, appUrbamid.layerAddedViewVariante),1);
						appMappa.removeLayer(layerName);
						$("#itemViewer_V_"+itemViewerId).empty();
						if ( appUtil.getNumElem(appUrbamid.layerAddedViewVariante)==0 )
							$("#layerVariante-layer").html("");
					}
				});
			}

			if (selezionati != null && selezionati.length != 0) {
				$.when(
					pianoRegolatoreRest.reperimentoCatalogoVariante()
				).done(function(catalogo){
					layersCatalogo = catalogo.aaData;
					if (selezionati.length > 0) {
						sessionStorage.setItem('layerSelezionati', JSON.stringify(selezionati));
						$.each( selezionati, function( key, item ) {
							var split = item.split("+");
							var layerName = split[0];
							var title = split[1];
							/**Aggiungo il tag dell'item legenda layer**/
							var itemViewerId = appUtil.generateId(layerName);
							if ( appUrbamid.layerAddedViewVariante.length==0 ) $("#layerVariante-layer").html("");
							var enableLayerHtml = "<div id=\"itemViewer_V_"+itemViewerId+"\" class='itemViewerContainer'></div>";
							$("#layerVariante-layer").append(enableLayerHtml);
							appUrbamid.layerAddedViewVariante.push(layerName);
							if ( $("#visualizzatore").hasClass("showHide") ) appMappa.toggleVisualizzatore('visualizzatore', event);
							
							var hrefDetail = ''//"http://192.168.11.222:9191/geoserver/rest/workspaces/urbamid/datastores/" + layerName + "/featuretypes/" + layerName + ".json"
								for (var i = 0; i < layersCatalogo.length; i++) {
									if(layersCatalogo[i].listCatalogoLayer != null) {
										for (var j = 0; j < layersCatalogo[i].listCatalogoLayer.length; j++) {
											if (layersCatalogo[i].listCatalogoLayer[j].idLayer === layerName) {
												hrefDetail = layersCatalogo[i].listCatalogoLayer[j].hrefDetail;
												break;
											}
										}
									}
								}
							
							responseLayerDetail = appRest.restGeoserver ( hrefDetail );
							
							/**Valorizzo l'item legenda layer**/
							itemViewerId 	= appUtil.generateId(layerName);
							itemCheckId 	= appUtil.generateId(layerName + '_variante'); 
							hrefLegend		= appUtil.getOrigin() + appConfig.geoserverService + appConfig.wmsService + "?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER="+appConfig.workspaceGeoserver+":"+layerName;
							name			= "";
							
							var itemViewer = {};
							if ( responseLayerDetail.featureType!=null ) {
								title = (responseLayerDetail.featureType.title).replace(/'/g, "");
								itemViewer = {"title":title, "layerName":layerName, "hrefLegend":hrefLegend, "visibilita":false, "itemCheckId":itemCheckId, "itemViewerId":itemViewerId, "opacity":10, "opacityPercent":100, "suffixGraf": '_variante', 'idParent': 'Variante'  };
							} else if ( responseLayerDetail.coverage!=null ) {
								title = (responseLayerDetail.coverage.title).replace(/'/g, "");
								itemViewer = {"title":title, "layerName":layerName, "hrefLegend":hrefLegend, "visibilita":false, "itemCheckId":itemCheckId, "itemViewerId":itemViewerId, "opacity":10, "opacityPercent":100, "suffixGraf": '_variante', 'idParent': 'Variante'};
							}else {
								iziToast.show({
									title: 'Attenzione',
									theme: 'dark',
									icon:'fa fa-check',
									message: "Il layer " + title + " non e' disponibile per la visualizzazione",
									animateInside: false,
									position: 'topCenter',
								});
								appUtil.hideLoader();
								return;
							}
							$("#itemViewer_V_"+itemViewerId).html( compilaTemplate("itemViewer", itemViewer) );

							/**Apro il box della variante **/
							if ( $("#visualizzatoreDatiBaselayerVariante").attr("aria-expanded")=="false" )
								$("#visualizzatoreDatiBaselayerVariante").click();
							
							let checkbox = $("#itemViewer_V_"+itemViewerId).find('input:checkbox');
							
							$(checkbox).prop('checked', true);
							$(checkbox).trigger('change');
							$.each(layers, function(key, value) {
								if(value.idParent.indexOf('Variante') != 0 && value.idParent.indexOf('DATI DI BASE') != 0) {
									if(value.nomeLayer.indexOf(layerName) != -1) {
										$('#' + value.itemCheckId + '_chk').click();
									}
								}
							});
							$("#visualizzatoreCatalogo").addClass("show");
							appUtil.hideLoader();
							self.riduciAdIconaTutteLeModali();
						});
					}
				});
			} else {
				iziToast.show({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'Per visualizzare le cartografie &egrave; necessario selezionarne almeno una.',
					animateInside: false,
					position: 'topCenter',
				});
				appUtil.hideLoader();
			}
		});

		$( "#cancellaCartografia" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var selezionati = []

			$('.select-checkbox').prop('checked', false);

			selezionati = JSON.parse(sessionStorage.getItem('layersInSessione'));

			if (selezionati == null || selezionati.length == 0) {
				iziToast.show({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'Per cancellare le cartografie &egrave; necessario selezionarne almeno una.',
					animateInside: false,
					position: 'topCenter',
				});
				appUtil.hideLoader();
			} else {
				var cartografia = new Object();
				cartografia.idVariante = $('#idVarianteDettaglio').val();
				cartografia.layers = selezionati;
				$.when(
					pianoRegolatoreRest.cancellaCartografia(cartografia)
				).done(function(risultato){
					if (risultato.message === 'Success') {
						sessionStorage.removeItem('layersInSessione');
						iziToast.show({
							title: 'OK',
							theme: 'dark',
							icon:'fa fa-check',
							message: 'Cartografia cancellata con successo.',
							animateInside: false,
							position: 'topCenter',
						});
					} else {
						iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon:'fa fa-times',
							message: 'Si &egrave; verificato un errore durante la cancellazione.',
							animateInside: false,
							position: 'topCenter',
						});
					}
					$.when(
						pianoRegolatoreRest.cercaCartografieByIdVariante($('#idVarianteDettaglio').val())
					).done(function(cartografie){
						self.inizializzaDatatableCartografia(cartografie.aaData);
						appUtil.hideLoader();
					});
				});
			}
		});

		$( "#nuovoIndice" ).click(function() {
			self.apriModaleNuovoIndice();
		});

		$( "#rimuoviIndice" ).click(function() {
			self.apriModaleConfermaEliminazioneIndiceDocumento();
		});

		$( "#importaIndice" ).click(function() {
			self.apriModaleImportaIndice();
		});

		$( "#nuovoCodice" ).click(function() {
			self.apriModaleSelezionaCodici();
		});

		$( "#esportaIndice" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var idDocumento = $("input[name='selezioneDocumento']:checked").val();
			pianoRegolatoreRest.esportaIndice(idDocumento).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/txt;base64,'+response;
				    const downloadLink = document.createElement('a');
				    const fileName = "Export_indici.txt";
				    downloadLink.href = linkSource;
				    downloadLink.download = fileName;
				    downloadLink.click();
				} else {
					iziToast.error({
						balloon: false,
						icon:'fa fa-times', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
					    title: 'Errore',
					    message: 'Si &egrave; verificato un errore durante la fase di export',
					});
				}
			}, function(a,b,c){
				appUtil.hideLoader();
				console.log(a,b,c);
			});
		});
		
		$( "#tab1varianti" ).click(function() {
			$('input[name="selezioneDocumento"]:checkbox').removeAttr('checked');
			$('input[name="selezioneIndice"]:checkbox').removeAttr('checked');
			$('input[name="cartografiaDaVisualizzare"]:checkbox').removeAttr('checked');
			$('input[name="select-all-cartografia"]:checkbox').removeAttr('checked');
			$('#sezioneIndice').hide();
			$('#sezioneCodici').hide();
		});

		$( "#tab2varianti" ).click(function() {
			$('input[name="selezioneDocumento"]:checkbox').removeAttr('checked');
			$('input[name="selezioneIndice"]:checkbox').removeAttr('checked');
			$('input[name="cartografiaDaVisualizzare"]:checkbox').removeAttr('checked');
			$('input[name="select-all-cartografia"]:checkbox').removeAttr('checked');
			$('#sezioneIndice').hide();
			$('#sezioneCodici').hide();
		});

		$( "#tab3varianti" ).click(function() {
			$('input[name="selezioneDocumento"]:checkbox').removeAttr('checked');
			$('input[name="selezioneIndice"]:checkbox').removeAttr('checked');
			$('input[name="cartografiaDaVisualizzare"]:checkbox').removeAttr('checked');
			$('input[name="select-all-cartografia"]:checkbox').removeAttr('checked');
			$('#sezioneIndice').hide();
			$('#sezioneCodici').hide();
		});

		$( "#tab4varianti" ).click(function() {
			$('#sezioneIndice').hide();
			$('#sezioneCodici').hide();
		});
		
		$('#' + self.idDialog + ' .pulsanti-dettaglio').hide();
		$('#' + self.idDialog + ' .pulsanti-ricerca').show();

		sessionStorage.setItem('windowOpened','paginaGestioneVariantiPiano');	
	}
	
	//QUESTA E' UNA CALLBACK RICHIAMATA DOPO AVER TRACCIATO L'ARIA DELL'AMBITO DELLA VARIANTE
	callbackDiRitornoDaAmbito(){
		appMappa.removeLayer("u_cat_particelle");
		appMappa.removeLayer("u_cat_fabbricati");
		minifize('paginaGestioneVariantiPiano');	//TODO aggiunti controllo per capire se è già "ingrandita" la finestra
		iziToast.show({
			title: 'OK',
			theme: 'dark',
			icon:'fa fa-check',
			message: 'La localizzazione dell\'ambito della Variante &egrave; stato acquisita con successo.',
			animateInside: false,
			position: 'topCenter',
			timeout: 12000,
		});
	}
	
	aggiungiEventiEstrazioneAmbitoVariante(){
		var self = this;
		$('#'+self.idDialog+' #selezionaSuMappaBtn').off('click').on('click', function(){
			self.riduciAdIconaTutteLeModali();
			appMappa.addRemoveLayer("u_cat_particelle", "Particelle", null, '', '');
			appMappa.addRemoveLayer("u_cat_fabbricati", "Fabbricati", null, '', '');
			new PaginaAmbitoDaSelezioneSuMappaCtrl( self.callbackDiRitornoDaAmbito );
		});
		$('#'+self.idDialog+' #tracciaSuMappaBtn').off('click').on('click', function(){
			self.riduciAdIconaTutteLeModali();
			new PaginaAmbitoDaTracciatoSuMappaCtrl( self.callbackDiRitornoDaAmbito );
		});
		$('#'+self.idDialog+' #taglioParticellareBtn').off('click').on('click', function(){
			self.riduciAdIconaTutteLeModali();
			new PaginaPianoParticellareDaSelezionaLayerCtrl( self.callbackDiRitornoDaAmbito );
		});
		$('#'+self.idDialog+' #strumentiRicercaMappaBtn').off('click').on('click', function(){
			self.riduciAdIconaTutteLeModali();
			new PaginaAmbitoDaStrumentiRicercaCtrl( self.callbackDiRitornoDaAmbito );
		});
	}
	
	aggiungiEventiTornaIndietroARicerca(){
		var self = this;
		$('#' + self.idDialog + ' .pulsanti-dettaglio').hide();
		$('#' + self.idDialog + ' .pulsanti-ricerca').show();
		self.aggiungiEventiClickTornaIndietroARicerca();
		$('#'+self.idDialog+' #tabellaRisultatiVariante').off('click').on('click', function(event){
			event.preventDefault();
			self.vaiAllaRicerca();
			$(event.target).parent('li').removeClass('active');
		});
	}
	
	inizializzaDatatableRicerca(){
		var self = this;
		var ente = $('#enteRiferimentoVariante').val();
		var nome = $('#nomeVariante').val();
		var descrizione = $('#descrizioneVariante').val();
		var dataAdozioneDal = $('#dataAdozioneDalVariante').val();
		var dataAdozioneAl = $('#dataAdozioneAlVariante').val();
		var dataApprovazioneDal = $('#dataApporvazioneDalVariante').val();
		var dataApporvazioneAl = $("#dataApporvazioneAlVariante").val();
		var url = pianoRegolatoreRest.ricercaVarianti(ente.trim(), nome.trim(), descrizione.trim(), dataAdozioneDal, dataAdozioneAl, dataApprovazioneDal, dataApporvazioneAl);
		$('#'+self.idDialog+' #tabellaRisultatiVariante').DataTable( {
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
	        ajax: url,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets :  0, name: 'nome', render: function(d, t, r) {return r.nome;}, orderable : true},
	        	{targets :  1, name: 'dataDelAdoz', render: function(d, t, r) {return r.dataDelAdoz;}, orderable : true},
	        	{targets :  2, name: 'dataDelAppr', render: function(d, t, r) {return r.dataDelAppr;}, orderable : true},
	        	{targets :  3, name: 'descrizione', render: function(d, t, r) {return r.descrizione;}, orderable : true},
	        	{targets :  4, className: "text-right", orderable : false, render: function(d, t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaVarianti', {id: r.idVariante});
	        		return templatePulsanti;
	        	}}
	        ],
	        drawCallback: function( settings ) {
				
				self.aggiungiEventiAiPulsantiAzione();
	        	$('#tabellaRisultatiVariante').show();
	        	sessionStorage.setItem('variante', JSON.stringify(settings.json.data));
	        	appUtil.hideLoader();
	        }
	    });
	}

	recuperaDettaglio(id){
		let variante = JSON.parse(sessionStorage.getItem('variante'));
		let dettaglio;
		for(var i=0; i<variante.length; i++){
			if(variante[i].idVariante === id){
				dettaglio = variante[i];
				break;
			}
		}
		return dettaglio;
	}
	
	inizializzaDatatableDocumenti(documenti, nomeVariante){
		$('#sezioneIndice').hide();
		$('#sezioneCodici').hide();
		var self = this;
		var UrbamidResponse = appRest.restUrbamid(appUtil.getUrbamidOrigin() + "/UrbamidWeb/prgController/findAllTipoDocumento", "GET", null);
		$('#'+self.idDialog+' #tabellaDocumentiVariante').DataTable( {
			data: documenti,
			language: self.datatableLan,
	        paging: true,
			lengthChange: false,
			ordering: true,
	        info: true,
	        searching: false,
	        destroy: true,
	        processing: true,
	        bSort: false,
	        select: {
	            style:    'os',
	            selector: 'td:first-child'
	        },
	        columnDefs: [
	        	{targets: 0, orderable: false, data: null, defaultContent: '', render: function(d, t, r){
	        		return '<input type="checkbox" id="'+ r.idDocumento +'" name="selezioneDocumento" value="' + r.idDocumento + '">'
	        	}},
	        	{targets : 1, render: function(d, t, r) {return r.tipoDocumento;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {var descrizione = UrbamidResponse.aaData.find(x => x.codice == r.tipoDocumento);
        		if (descrizione != undefined) {
        			return descrizione.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
	        	{targets : 3, render: function(d, t, r) {return nomeVariante;}, orderable : true},
	        	{targets : 4, render: function(d, t, r) {return r.pathDocumento;}, orderable : true},
	        	{targets : 5, className: "text-right", orderable : false, render: function(d, t, r){
	        		var approvazione = $('#dataApprovazioneDettaglio').val();
	        		if (approvazione !== null && approvazione !== undefined && approvazione !== "") {
	        			let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumentiVariantiApprovata', {id: r.idDocumento});
	        			return templatePulsanti;	        			
	        		} else {
	        			let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumentiVarianti', {id: r.idDocumento});
	        			return templatePulsanti;
	        		}
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	self.aggiungiEventiAiPulsantiAzioneDocumenti();
	        	sessionStorage.setItem('documenti', JSON.stringify(documenti));
	        	
	        	$("input[name='selezioneDocumento']").change(function() {
	        		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	    		    $("input[name='selezioneDocumento']").not(this).prop('checked', false);
	    		    var id = $("input[name='selezioneDocumento']:checked").val();
	    		    if (id === null || id === undefined || id === "") {
	    		    	$('#sezioneIndice').hide();
	    				$('#sezioneCodici').hide();
	    		    } else {
	    		    	$.when(
    						pianoRegolatoreRest.cercaIndiciByIdDocumento(id)
    					).done(function(indici){
    						self.inizializzaDatatableIndici(indici.aaData);
    					});
						$('#sezioneIndice').show();
						$('#sezioneCodici').hide();
	    		    }
	    		    appUtil.hideLoader();
	    		});
	        	
	        }
	    });
	}

	inizializzaDatatableIndici(indici){
		var self = this;
		$('#'+self.idDialog+' #tabellaDocumentiIndice').DataTable( {
			data: indici,
			language: self.datatableLan,
			paging: true,
			lengthChange: false,
	        ordering: true,
	        info: true,
	        searching: false,
	        destroy: true,
	        processing: true,
	        bSort: false,
	        select: {
	            style:    'os',
	            selector: 'td:first-child'
	        },
	        columnDefs: [
	        	{targets: 0, orderable: false, data: null, defaultContent: '', render: function(d, t, r){
	        		return '<input type="checkbox" id="'+ r.idIndice +'" name="selezioneIndice" value="' + r.idIndice + '">'
	        	}},
	        	{targets : 1, render: function(d, t, r) {return r.articolo;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.elencoPagine;}, orderable : true},
	        	{targets : 3, orderable : false, render: function(d, t, r){
	        		if ($('#dataApprovazioneDettaglio').val() !== "") {
	        			return null;
	        		} else {
	        			return '<button type="button" id="' + r.idIndice + '" data-info="Elimina indice" class="btn-trasp bttn cancella-indice pull-right"><em class="fa fa-trash-o"></em></button>';	        			
	        		}
		        }}
	        ],
	        drawCallback: function( settings ) {
	        	$("input[name='selezioneIndice']").change(function() {
	        		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	    		    $("input[name='selezioneIndice']").not(this).prop('checked', false);
	    		    var id = $("input[name='selezioneIndice']:checked").val();
	    		    if (id === null || id === undefined || id === "") {
	    		    	$('#sezioneCodici').hide();
	    		    } else {
	    		    	$.when(
    						pianoRegolatoreRest.cercaCodiciByIdIndice(id)
    					).done(function(codici){
    						self.inizializzaDatatableCodici(codici.aaData);
    					});
	    		    	$('#sezioneCodici').show();
	    		    }
	    		    appUtil.hideLoader();
	    		});
	        	
	        	$('#'+self.idDialog+' #tabellaDocumentiIndice .cancella-indice').off('click').on('click', function () {
	        		self.apriModaleConfermaEliminazioneIndiceDocumento(this.id);
	            });
	        	
	        }
	    });
	}

	inizializzaDatatableCodici(codici){
		var self = this;
		$('#'+self.idDialog+' #tabellaIndiceCodici').DataTable( {
			data: codici,
			language: self.datatableLan,
			paging: true,
			lengthChange: false,
	        ordering: true,
	        info: true,
	        searching: false,
	        destroy: true,
	        processing: true,
	        bSort: false,
	        select: {
	            style:    'os',
	            selector: 'td:first-child'
	        },
	        columnDefs: [
	        	{targets : 0, render: function(d, t, r) {return r.codice;}, orderable : true},
	        	{targets : 1, render: function(d, t, r) {return r.descrizione != null ? r.descrizione : 'N/D';}, orderable : true},
	        ],
	        drawCallback: function( settings ) {
				sessionStorage.setItem('codiciZTO', JSON.stringify(codici))
	        	//MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	// $('#'+self.idDialog+' .dataTables_paginate ').show();
	        	// $('#'+self.idDialog+' .dataTables_info').show();
	        }
	    });
	}

	aggiungiEventiAiPulsantiAzioneDocumenti(){
		var self = this;
		$('#'+self.idDialog+' #tabellaDocumentiVariante button.nuova-versione-documento-btn').off('click').on('click', function(event){
			self.apriModaleCreaModificaVersioneDocumento();
        });
		$('#'+self.idDialog+' #tabellaDocumentiVariante button.modifica-versione-documento-btn').off('click').on('click', function(event){
			let documento = self.recuperaDettaglioDocumento($(this).data('id'));
			self.apriModaleCreaModificaVersioneDocumento(documento);
        });
		$('#'+self.idDialog+' #tabellaDocumentiVariante button.rimuovi-documento-btn').off('click').on('click', function(event){
			let documento = self.recuperaDettaglioDocumento($(this).data('id'));
			self.apriModaleConfermaEliminazioneVarianteDocumento(documento)
        });
		$('#'+self.idDialog+' #tabellaDocumentiVariante button.download-documento-btn').off('click').on('click', function(event){
			let documento = self.recuperaDettaglioDocumento($(this).data('id'));
			self.downloadDocumento(documento);
        });
		$('#'+self.idDialog+' #tabellaDocumentiVariante button.gestisci-associazioni-btn').off('click').on('click', function(event){
			self.apriModaleGestisciAssociazioniDocumento($(this).data('idDocumento'));
        });
	}
	
	apriModaleCreaModificaVersioneDocumento(documento){
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		var self = this;
		var messaggioModale = documento ? 'La versione del documento &egrave; stata aggiornata con successo.' : 'La versione del documento &egrave; stato creata con successo.';
		var titolo = documento ? 'MODIFICA VERSIONE DOCUMENTO' : 'CREA NUOVA VERSIONE DOCUMENTO';
		var idVariante = $('#idVarianteDettaglio').val();
		var tipiDocumento = [];
		if (idVariante === null && idVariante === undefined && idVariante === "" || documento != null && documento != undefined) {
			var hrefMapTipoDocumentiUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/prgController/findAllTipoDocumento";			
			var UrbamidResponse = appRest.restUrbamid(hrefMapTipoDocumentiUrbamid, "GET", null);
			tipiDocumento = UrbamidResponse.aaData;
			self.modaleDocumenti(documento, titolo, messaggioModale, tipiDocumento);
		} else {
			$.when(
				pianoRegolatoreRest.cercaTipiDocumentoMancanti(idVariante)
			).done(function(risultato){
				tipiDocumento = risultato.aaData;
				if (tipiDocumento.length == 0) {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon: 'fa fa-times',
						message: 'Tutti i tipi di documento sono gi&agrave; inseriti per la variante corrente.',
						animateInside: false,
						position: 'topCenter',
					});
					appUtil.hideLoader()
				} else {
					self.modaleDocumenti(documento, titolo, messaggioModale, tipiDocumento);
				}
			});
		}
	}

	modaleDocumenti (documento, titolo, messaggioModale, tipiDocumento) {
		var self = this;
		appUtil.hideLoader()
		openStaticDetailViaHandlebars(self.idDialog+'creaModificaVersioneDocumento', 'modaleCreaModificaVersioneDocumento', {tipoDocumenti: tipiDocumento}, titolo, function(){
    		appUtil.reloadTooltips();
    		var tipoDocumento = documento ? documento.tipoDocumento ? documento.tipoDocumento : '' : '';
   			$('#'+self.idDialog+'creaModificaVersioneDocumento #tipoDocumento').val(tipoDocumento);
   			if (documento != undefined) {
   				$('#'+self.idDialog+'creaModificaVersioneDocumento #idDocumento').val(documento.idDocumento);
   			}
   			if (titolo == 'MODIFICA VERSIONE DOCUMENTO') {
    			$('#tipoDocumento').prop( "disabled", true );
	   			$('#azione').prop( "disabled", true );
	   			$('#azione').val("M");
    		} else {
    			$('#azione').prop( "disabled", true );
    			$('#azione').val("N");
    		}
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
   			$('#'+self.idDialog+'creaModificaVersioneDocumento'+' #nuovaVersioneUpload').filer(opzioniUploader);
    		$('#'+self.idDialog+'creaModificaVersioneDocumento #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaVersioneDocumento').dialog('close');
    		});
    		$('#'+self.idDialog+'creaModificaVersioneDocumento #salvaBtn').off('click').on('click', function(){
    			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
    			var idVariante = $('#idVarianteDettaglio').val();
    			var idDocumento = $('#idDocumento').val();
    			var ente = $('#enteRiferimentoDettaglio').val();
    			var tipo = $('#tipoDocumento').val();
    			var vigente = $('#vigente').val();
    			var azione = $('#azione').val();
    			var file = $('#nuovaVersioneUpload').prop('files')[0];
    			
                var arraysName = (file!=null && file!=undefined && file.name!=null && file.name!=undefined && file.name!="") ? 
                					file.name.split(".") : undefined;

    			if (tipo==null || tipo==undefined || tipo == "" ||  azione == "" || file == undefined) {
    				iziToast.error({
    					title: 'Attenzione',
    					theme: 'dark',
    					icon:'fa fa-times',
    					message: 'Popolare tutti i campi.',
    					animateInside: false,
    					position: 'topCenter',
    				});
    				appUtil.hideLoader();
    			} else if ( arraysName!=null && arraysName!=undefined && arraysName.length>1 && 
    			                arraysName[arraysName.length-1] !== 'pdf' && 
    			                arraysName[arraysName.length-1] !== 'doc' &&
    			                arraysName[arraysName.length-1] !== 'docx') {
    				iziToast.error({
    					title: 'Attenzione',
    					theme: 'dark',
    					icon:'fa fa-times',
    					message: "&#201; possibile caricare solo file .doc, .docx o .pdf",
    					animateInside: false,
    					position: 'topCenter',
    				});
    				appUtil.hideLoader();
    			} else {
    				var documenti = JSON.parse(sessionStorage.getItem('documenti'));
    				var docAlreadyExist = false;
    				$.each( documenti, function( key, item ) {
    					if (item.nomeDocumento === file.name) {
    						docAlreadyExist = true;
    					}
    	        	});
    				if (docAlreadyExist) {
    					iziToast.error({
        					title: 'Attenzione',
        					theme: 'dark',
        					icon:'fa fa-times',
        					message: "Il file selezionato &egrave; gi&agrave; stato caricato per la variante corrente",
        					animateInside: false,
        					position: 'topCenter',
        				});
        				appUtil.hideLoader();
    				} else {
		    			var formData = new FormData();
		    			formData.append('idVariante', idVariante);
		    			formData.append('idDocumento', idDocumento);
		    			formData.append('ente', ente);
		    			formData.append('tipo', tipo);
		    			formData.append('vigente', vigente);
		    			formData.append('azione', azione);
		    			formData.append('file', file);
		    			formData.append('estensione', file.type);
		    			$('#'+self.idDialog+'creaModificaVersioneDocumento').dialog('close');
		    			$.when(
							pianoRegolatoreRest.creaOrSalvaDocumento(formData)
						).done(function(risultato){
							if (risultato.message === 'Success') {
								iziToast.show({
					    			title: 'OK',
					    			theme: 'dark',
					    			icon:'fa fa-check',
					    			message: messaggioModale,
					    			animateInside: false,
					    			position: 'topCenter',
					    		});
								$.when(
									pianoRegolatoreRest.cercaDocumentiByIdVariante($('#idVarianteDettaglio').val())
								).done(function(data){
									self.inizializzaDatatableDocumenti(data.aaData, $('#nomeVarianteDettaglio').val());
									appUtil.hideLoader();
								});
							} else {
								iziToast.error({
									title: 'Attenzione',
									theme: 'dark',
									icon:'fa fa-times',
									message: 'Si &egrave; verificato un errore durante il salvataggio.',
									animateInside: false,
									position: 'topCenter',
								});
							}
							appUtil.hideLoader();
						});
    				}
    			}
    		});
    	}, {width: 500, height: 400, resizable: false, modale: true, closable: true});
	}
	
	apriModaleGestisciAssociazioniDocumento(id){
		var self = this;
    	openStaticDetailViaHandlebars(self.idDialog+'gestioneAssociazioneDocumento', 'modaleGestioneAssociazioniDocumento', {}, 'GESTIONE ASSOCIAZIONI DOCUMENTO', function(){
    		appUtil.reloadTooltips();
			self.inizializzaJsTree('#gestione-associazione-documenti-jstree', '#inputRicercaJsTree', true);
    		$('#'+self.idDialog+'gestioneAssociazioneDocumento #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'gestioneAssociazioneDocumento').dialog('close');
    		});
    		$('#'+self.idDialog+'gestioneAssociazioneDocumento #salvaBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'gestioneAssociazioneDocumento').dialog('close');
    			iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: 'Le associazioni del documento sono state salvate con successo.',
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
    			tippy.hideAll();
    		});
    	}, {width: 500, height: 300, resizable: false, modale: true, closable: true});
	}
	
	apriModaleConfermaEliminazioneVarianteDocumento(documento){
		var self = this;
		var id = documento.idDocumento;
		appUtil.confirmOperation( function(){
			//CONFERMA
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			$.when(
				pianoRegolatoreRest.cancellaDocumento(id)
			).done(function(risultato){
				if (risultato.message === 'Success') {
					iziToast.show({
						title: 'OK',
						theme: 'dark',
						icon:'fa fa-check',
						message: 'La versione del documento &egrave; stata rimossa con successo.',
						animateInside: false,
						position: 'topCenter',
					});
					$.when(
						pianoRegolatoreRest.cercaDocumentiByIdVariante($('#idVarianteDettaglio').val())
					).done(function(data){
						self.inizializzaDatatableDocumenti(data.aaData, $('#nomeVarianteDettaglio').val());
						appUtil.hideLoader();
					});
				} else {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Si &egrave; verificato un errore durante la cancellazione.',
						animateInside: false,
						position: 'topCenter',
					});
				}
			});
			appUtil.hideLoader();
		}, function(){
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere con la cancellazione del documento?');
	}

	downloadDocumento(documento){
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		pianoRegolatoreRest.downloadDocumento(documento.idDocumento).then(function(response){
			appUtil.hideLoader();
			if (response !== null || response !== undefined || response !== '') {
				var linkSource = 'data:' + documento.estensione + ';base64,'+response;
				const downloadLink = document.createElement('a');
				downloadLink.href = linkSource;
				downloadLink.download = documento.nomeDocumento;
				downloadLink.click();
			} else {
				iziToast.error({
					balloon: false,
					icon:'fa fa-times', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
					title: 'Errore',
					message: 'Si &egrave; verificato un errore durante la fase di export',
				});
			}
		}, function(a,b,c){
			appUtil.hideLoader();
			console.log(a,b,c);
		});
	}

	inizializzaDatatableCartografia(cartografie){
		var self = this;
		var table = $('#'+self.idDialog+' #tabellaCartografiaVariante').DataTable( {
			data: cartografie,
			language: self.datatableLan,
			lengthChange: false,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: false,
	        destroy: true,
	        processing: true,
	        bSort: false,
	        select: {
	            style:    'os',
	            selector: 'td:first-child'
	        },
	        columnDefs: [
	        	{targets: 0, orderable: false, data: null, defaultContent: '', width: '5%', render: function(d, t, r){
					return '<input type="checkbox" id="'+ r.idCartografia +'" name="cartografiaDaVisualizzare" value="' + r.nomeLayer + "+" + r.descrizioneLayer + '">'
				}},
	        	{targets : 1, render: function(d, t, r) {return r.descrizioneLayer;}, orderable : false},
				{targets : 2, render: function(d, t, r) {return r.gruppoLayer;}, orderable : false},
			],
	        drawCallback: function( settings ) {
				sessionStorage.setItem('cartografie', JSON.stringify(cartografie));

				$('.select-checkbox').on('click', function(e) {
					let checked = $(this).is(":checked");
					var tabella = $('#'+self.idDialog+' #tabellaCartografiaVariante').DataTable();
						
					tabella.rows().every(function(rowIdx, tableLoop, rowLoop) {
						$('input', this.nodes()).each(function() {
							if(checked){
								$(this).prop('checked', true);
								self.popolaListaCartografiaDaVisualizzare(this);
							}
							else {
								$(this).prop('checked', false);
								self.popolaListaCartografiaDaVisualizzare(this);
							}
						});	
					});

				});

	        	$("input[name='cartografiaDaVisualizzare']").change(function() {
					self.popolaListaCartografiaDaVisualizzare(this);
	        	})
	        }
	    });
	}

	popolaListaCartografiaDaVisualizzare(input) {
		if($(input).is(":checked")) {
			var isAllChecked = 0;
			var tabella = $('#'+this.idDialog+' #tabellaCartografiaVariante').DataTable();
			
			$("input[name='cartografiaDaVisualizzare']", tabella.rows({page: 'all'}).nodes()).each(function(){
				if(!this.checked) {
					isAllChecked = 1;
				}
			});

			if(isAllChecked == 0) {
				$('.select-checkbox').prop('checked', true)		
			}

			var checked = $(input).val();
			var layersInSessione = sessionStorage.getItem('layersInSessione');
			if (layersInSessione === null || layersInSessione === '') {
				var tmp = [];
				tmp.push(checked);
				sessionStorage.setItem('layersInSessione', JSON.stringify(tmp));
			} else {
				var layers = [];
				layers = JSON.parse(sessionStorage.getItem('layersInSessione'));
				var isAlreadyPresent = layers.includes(checked);
				if (isAlreadyPresent == false) {
					layers.push(checked);
					sessionStorage.setItem('layersInSessione', JSON.stringify(layers));
				}
			}

		} else {
			var layers = [];
			layers = JSON.parse(sessionStorage.getItem('layersInSessione'));
			layers = layers.filter(e => e !== $(input).val());
			sessionStorage.setItem('layersInSessione', JSON.stringify(layers));
			$('.select-checkbox').prop('checked', false);
		}
	}
	
	aggiungiEventiAiPulsantiAzione(){
		var self = this;
		$('#'+self.idDialog+' .dettaglio-variante-btn').off('click').on('click', function(event){
        	self.vaiAlDettaglio($(this).data('id'));
        });
        $('#'+self.idDialog+' .storico-variante-btn').off('click').on('click', function(event){
        	var id = $(this).data('id');
        	var dettaglio = self.recuperaDettaglio(id);
        	openStaticDetailViaHandlebars(self.idDialog+'storico', 'modaleStoricoVariante', {intestatari: dettaglio.listaStorico}, 'STORICO VARIANTE', function(){
        		appUtil.reloadTooltips();
        		$('#'+self.idDialog+'storico #chiudiBtn').off('click').on('click', function(){
        			$('#'+self.idDialog+'storico').dialog('close');
        		});
        	}, {width: 500, height: 500, resizable: false, modale: true, closable: true});
        });
        $('#'+self.idDialog+' .cambia-proprietario-btn').off('click').on('click', function(event){
        	openStaticDetailViaHandlebars(self.idDialog+'cambiaProprietario', 'modaleCambiaProprietario', {}, 'CAMBIO UTENTE PER VARIANTE', function(){
        		appUtil.reloadTooltips();
        		$('#'+self.idDialog+'cambiaProprietario #chiudiBtn').off('click').on('click', function(){
        			$('#'+self.idDialog+'cambiaProprietario').dialog('close');
        		});
        		$('#'+self.idDialog+'cambiaProprietario #salvaBtn').off('click').on('click', function(){
        			$('#'+self.idDialog+'cambiaProprietario').dialog('close');
        			iziToast.show({
    	    			title: 'OK',
    	    			theme: 'dark',
    	    			icon:'fa fa-check',
    	    			message: 'L\'utente proprietario &egrave; stato aggiornato con successo.',
    	    			animateInside: false,
    	    			position: 'topCenter',
    	    		});
        		});
        	}, {width: 500, height: 200, resizable: false, modale: true, closable: true});
        });
        $('#'+self.idDialog+' .rimuovi-variante-btn').off('click').on('click', function(event){
        	self.rimuoviVariante($(this).data('id'));
        });
	}
	
	rimuoviVariante(id){
		var self = this;
		appUtil.confirmOperation( function(){
			//CONFERMA
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			$.when(
				pianoRegolatoreRest.cancellaVariante(id)
			).done(function(risultato){
				if (risultato.message === 'Success') {
					iziToast.show({
						title: 'OK',
						theme: 'dark',
						icon:'fa fa-check',
						message: 'La variante &egrave; stata rimossa con successo.',
						animateInside: false,
						position: 'topCenter',
					});
					self.inizializzaDatatableRicerca();
				} else {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Si &egrave; verificato un errore durante la cancellazione.',
						animateInside: false,
						position: 'topCenter',
					});
				}
			});
			appUtil.hideLoader();
		}, function(){
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere con la cancellazione della variante?');
	}
	
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			self.mostraTabDocumenti_e_Cartografia();
			let dettaglio = self.recuperaDettaglio(id);
			self.nomeVariante = dettaglio.nome;
			sessionStorage.removeItem('layerSelezionati');
			sessionStorage.removeItem('layersInSessione');
			self.pulisciCampiForm();
			
			$('#aggiungiCartografia').show();
			$('#nuovoDocumento').show();
			$('#tabAmbitoVariante').show();
			$('#cancellaCartografia').show();
			$('#messaggioVariante').hide();
			
			$('#nuovoIndice').prop('disabled', false);
			$('#importaIndice').prop('disabled', false);
			$('#nuovoCodice').prop('disabled', false);
			
			$('#' + self.idDialog + ' .pulsanti-dettaglio').show();
			$('#' + self.idDialog + ' .pulsanti-ricerca').hide();
			$('#'+self.idDialog+' #idVarianteDettaglio').val(id);
			sessionStorage.setItem('idVariante', id);
			$('#'+self.idDialog+' #nomeVarianteDettaglio').val(dettaglio.nome);
			$('#enteRiferimentoDettaglio option[value="' + dettaglio.codComune + '"]').attr('selected', true).change();
			$('#'+self.idDialog+' #descrizioneVarianteDettaglio').val(dettaglio.descrizione);
			//adozione
			if($.type(dettaglio.dataDelAdoz) === 'string' && dettaglio.dataDelAdoz !== ''){
				$('#'+self.idDialog+' #dataAdozioneDettaglio').val(dettaglio.dataDelAdoz);
				$('#'+self.idDialog+' #numeroAdozioneDettaglio').val(/*'CC ' + */dettaglio.numDelAdoz/* + ' N42'*/);
				$('#'+self.idDialog+' #organoAdozioneDettaglio').val(dettaglio.orgDelAdoz);
				$('#'+self.idDialog+' #noteAdozioneDettaglio').val(dettaglio.noteDelAdoz);
			}
			//approvazione
			if($.type(dettaglio.dataDelAppr) === 'string' && dettaglio.dataDelAppr !== ''){
				$('#'+self.idDialog+' #dataApprovazioneDettaglio').val(dettaglio.dataDelAppr);
				$('#'+self.idDialog+' #numeroApprovazioneDettaglio').val(/*'N' + */dettaglio.numDelAppr);
				$('#'+self.idDialog+' #organoApprovazioneDettaglio').val(dettaglio.orgDelAppr);
				$('#'+self.idDialog+' #noteApprovazioneDettaglio').val(dettaglio.noteDelAppr);
				
				document.getElementById("nomeVarianteDettaglio").disabled = true;
				document.getElementById("enteRiferimentoDettaglio").disabled = true;
				document.getElementById("descrizioneVarianteDettaglio").disabled = true;
				
				document.getElementById("dataAdozioneDettaglio").disabled = true;
				document.getElementById("numeroAdozioneDettaglio").disabled = true;
				document.getElementById("organoAdozioneDettaglio").disabled = true;
				document.getElementById("noteAdozioneDettaglio").disabled = true;
				
				document.getElementById("dataApprovazioneDettaglio").disabled = true;
				document.getElementById("numeroApprovazioneDettaglio").disabled = true;
				document.getElementById("organoApprovazioneDettaglio").disabled = true;
				document.getElementById("noteApprovazioneDettaglio").disabled = true;
				$('#aggiungiCartografia').hide();
				$('#nuovoDocumento').hide();
				$('#tabAmbitoVariante').hide();
				$('#cancellaCartografia').hide();
				$('#messaggioVariante').show();
				
				$('#nuovoIndice').prop('disabled', true);
				$('#importaIndice').prop('disabled', true);
				$('#nuovoCodice').prop('disabled', true);
				
				$('#salvaBtn').hide();
				$('#salvaChiudiBtn').hide();
				
			} else {
				document.getElementById("nomeVarianteDettaglio").disabled = true;
				document.getElementById("enteRiferimentoDettaglio").disabled = true;
				document.getElementById("dataApprovazioneDettaglio").disabled = false;
				document.getElementById("numeroApprovazioneDettaglio").disabled = false;
				document.getElementById("organoApprovazioneDettaglio").disabled = false;
				document.getElementById("noteApprovazioneDettaglio").disabled = false;
			}

			$('#tab1varianti').addClass('active');
			$('#tab2varianti').removeClass('active');
			$('#tab3varianti').removeClass('active');
			$('#tab4varianti').removeClass('active');
			
			$.when(
				pianoRegolatoreRest.cercaDocumentiByIdVariante(id),
				pianoRegolatoreRest.cercaCartografieByIdVariante(id)
			).done(function(documenti, cartografie){
				self.inizializzaDatatableDocumenti(documenti[0].aaData, dettaglio.nome);
				self.inizializzaDatatableCartografia(cartografie[0].aaData);
				self.nascondiPaginaRicerca();
				//aggiorno il breadcrumb
				self.aggiornaBreadcrumb('dettaglio', dettaglio.nome);
				appUtil.hideLoader();
			});

	}
	recuperaDettaglioDocumento(id){
		let documenti = JSON.parse(sessionStorage.getItem('documenti'));
		let dettaglio = null;
		for(var i=0;i<documenti.length;i++){
			if(documenti[i].idDocumento === id){
				dettaglio = documenti[i];
				break;
			}
		}
		return dettaglio;
	}
	
	aggiungiEventiSalvaVariante(){
		var self = this;
		$('#'+self.idDialog+' #salvaBtn, '+'#'+self.idDialog+' #salvaChiudiBtn').off('click').on('click', function(){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var id = $(this).attr('id');
			var variante = new Object();
			variante.idVariante = $('#idVarianteDettaglio').val();
			variante.nome = $('#nomeVarianteDettaglio').val();
			variante.descrizione = $('#descrizioneVarianteDettaglio').val();
			variante.codComune = $('#enteRiferimentoDettaglio').val();
			variante.dataDelAdoz = $('#dataAdozioneDettaglio').val();
			variante.numDelAdoz = $('#numeroAdozioneDettaglio').val();
			variante.orgDelAdoz = $('#organoAdozioneDettaglio').val();
			variante.noteDelAdoz = $('#noteAdozioneDettaglio').val();
			variante.dataDelAppr = $('#dataApprovazioneDettaglio').val();
			variante.numDelAppr = $('#numeroApprovazioneDettaglio').val();
			variante.orgDelAppr = $('#organoApprovazioneDettaglio').val();
			variante.noteDelAppr = $('#noteApprovazioneDettaglio').val();
			if (variante.nome == "" || variante.codComune == "" || variante.dataDelAdoz == "" || variante.numDelAdoz == "") {
				iziToast.error({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-times',
					message: 'Popolare i campi obbligatori.',
					animateInside: false,
					position: 'topCenter',
				});
				appUtil.hideLoader();
			} else {
				self.salvaVariante(variante);
			}
		});
	}
	
	salvaVariante(variante) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(
			pianoRegolatoreRest.creaOrSalvaVariante(variante)
		).done(function(risultato){
			if (risultato.message === 'Success') {
				iziToast.show({
					title: 'OK',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'Dati variante salvati con successo.',
					animateInside: false,
					position: 'topCenter',
				});
				$('#indietroBtn').click();
			} else {
				iziToast.error({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-times',
					message: 'Si &egrave; verificato un errore durante il salvataggio.',
					animateInside: false,
					position: 'topCenter',
				});
			}
			if(id === 'salvaChiudiBtn'){
				$('#'+self.idDialog).dialog('close');
			}
			self.inizializzaDatatableRicerca();
			appUtil.hideLoader();
		});
	}
	
	aggiungiEventiCreaNuovaVariante(){
		var self = this;
		$('#'+self.idDialog+' #menuAggiungiVariante, '+'#'+self.idDialog+' #nuovoBtn').off('click').on('click', function(event){
			event.preventDefault();
			self.vaiAlDettaglioInCreazione();
			$(event.target).parent('li').removeClass('active');
			document.getElementById("nomeVarianteDettaglio").disabled = false;
			document.getElementById("enteRiferimentoDettaglio").disabled = false;
			document.getElementById("dataApprovazioneDettaglio").disabled = true;
			document.getElementById("numeroApprovazioneDettaglio").disabled = true;
			document.getElementById("organoApprovazioneDettaglio").disabled = true;
			document.getElementById("noteApprovazioneDettaglio").disabled = true;
		});
	}
	vaiAlDettaglioInCreazione(){
		this.nascondiTabDocumenti_e_Cartografia();
		this.pulisciCampiForm();
		this.nascondiPaginaRicerca();
		this.aggiornaBreadcrumb('nuovo', null);
		$('#idVarianteDettaglio').val('');
		$('#tab1varianti').addClass('active');
		$('#salvaBtn').show();
		$('#salvaChiudiBtn').show();
		
		document.getElementById("nomeVarianteDettaglio").disabled = false;
		document.getElementById("enteRiferimentoDettaglio").disabled = false;
		document.getElementById("descrizioneVarianteDettaglio").disabled = false;
		
		document.getElementById("dataAdozioneDettaglio").disabled = false;
		document.getElementById("numeroAdozioneDettaglio").disabled = false;
		document.getElementById("organoAdozioneDettaglio").disabled = false;
		document.getElementById("noteAdozioneDettaglio").disabled = false;
	}
	nascondiTabDocumenti_e_Cartografia(){
		//nascondo e de-activo tutto
		$('#'+this.idDialog+' ul#dettaglioTab1 li').removeClass('active').addClass('hidden');
		$('#'+this.idDialog+' div#dettaglioTabContent div.tab-pane').removeClass('active').addClass('hidden');
		//attivo il primo tab e mostro anche l'ultimo
		$('#'+this.idDialog+' ul#dettaglioTab1 li:first').removeClass('hidden');
		$('#'+this.idDialog+' div#dettaglioTabContent div.tab-pane:first').removeClass('hidden').addClass('active');
	}
	mostraTabDocumenti_e_Cartografia(){
		$('#'+this.idDialog+' ul#dettaglioTab1 li').removeClass('hidden').removeClass('active');
		$('#'+this.idDialog+' div#dettaglioTabContent div.tab-pane').removeClass('hidden').removeClass('active');
		$('#'+this.idDialog+' div#dettaglioTabContent div.tab-pane:first').addClass('active');
	}
	pulisciCampiForm(){
		$('#idVarianteDettaglio').val('');
		$('#nomeVarianteDettaglio').val('');
		$('#descrizioneVarianteDettaglio').val('');
		$('#dataAdozioneDettaglio').val('');
		$('#numeroAdozioneDettaglio').val('');
		$('#organoAdozioneDettaglio').val('');
		$('#noteAdozioneDettaglio').val('');
		$('#dataApprovazioneDettaglio').val('');
		$('#numeroApprovazioneDettaglio').val('');
		$('#organoApprovazioneDettaglio').val('');
		$('#noteApprovazioneDettaglio').val('');
	}

	apriModaleSelezionaCartografie(){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		var titolo = "SELEZIONA CARTOGRAFIA";
    	openStaticDetailViaHandlebars(self.idDialog+'creaSelezionaCartografie', 'modaleCreaSelezionaCartografie', {}, titolo, function(){
    		
    		appUtil.reloadTooltips();
    		var cartografie = JSON.parse(sessionStorage.getItem('cartografie'));
    		var firstRadio = true;
			var ztoAlreadyExist = false;
    		
    		$.each( cartografie, function( keyVar, item ) {
				if (item.nomeLayer.includes('ZTO2')) {
					ztoAlreadyExist = true;
				}
			});
    		var listGruppo = [];
    		Handlebars.registerHelper("containString", function(idLayer, nomeLayer) {
				let gruppo = null;
				let sorgente = null;
				let nomeColonna = null;
				$.each(listGruppo, function(index, value) {
					if(nomeLayer.indexOf(value.nomeLayer) != -1) {
						gruppo = value.nomeGruppo;
						sorgente = value.sorgente;
						nomeColonna = value.nomeColonna;
					}
				});
				if (idLayer.includes('ZTO2') && ztoAlreadyExist === false) {
    				if (firstRadio === true) {
    					firstRadio = false;
    					return '<div class="itemMappa"> <a class="linkItemMappa" href="#" title="' + nomeLayer + '">' + nomeLayer + '</a><input type="radio" class="pull-right" name="aggiungi-layer-radio" value="' + idLayer + '+' + nomeLayer + '+' + gruppo + '+' + sorgente + '+' + nomeColonna +'" checked></div>';
    				} else {
    					return '<div class="itemMappa"> <a class="linkItemMappa" href="#" title="' + nomeLayer + '">' + nomeLayer + '</a><input type="radio" class="pull-right" name="aggiungi-layer-radio" value="' + idLayer + '+' + nomeLayer + '+' + gruppo + '+' + sorgente + '+' + nomeColonna +'"></div>';
    				}
    			} else if (idLayer.includes('ZTO2') && ztoAlreadyExist === true) { 
    				return '<div class="itemMappa"> <a class="linkItemMappa" href="#" title="' + nomeLayer + '">' + nomeLayer + '</a><input type="radio" class="pull-right" name="aggiungi-layer-radio" value="' + idLayer + '+' + nomeLayer + '+' + gruppo + '+' + sorgente + '+' + nomeColonna +'" disabled></div>';
    			} else {
    				return '<div class="itemMappa"> <a class="linkItemMappa" href="#" title="' + nomeLayer + '">' + nomeLayer + '</a><input type="checkbox" class="pull-right" name="aggiungi-layer" value="' + idLayer + '+' + nomeLayer + '+' + gruppo + '+' + sorgente + '+' + nomeColonna +'"></div>';
    			}
			});

    		$.when(
				pianoRegolatoreRest.reperimentoCatalogoVariante()
			).done(function(catalogo){
				$.each(catalogo.aaData, function(index, value) {
					if(value.listCatalogoLayer != null) {
						$.each(value.listCatalogoLayer, function(indexListLayer, valueListLayer) {
							listGruppo.push({nomeGruppo: value.nomeGruppo, nomeLayer: valueListLayer.nomeLayer, sorgente: valueListLayer.sorgente, nomeColonna: valueListLayer.nomeColonna});
						});
					}
				});
				htmlGruppo = compilaTemplate("modaleCreazioneCatalogoVariante", {result: catalogo.aaData});
				$("#catalogoVarianteAggiunta").append( htmlGruppo );
				appUtil.hideLoader();
			});
    		
    		/*$('#'+self.idDialog+'creaSelezionaCartografie .vai-alla-mappa').off('click').on('click', function () {
    			console.log(this.id);
    		});*/

    		$('#'+self.idDialog+'creaSelezionaCartografie #salvaBtn').off('click').on('click', function(){
    			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
    			var selezionati = []
				
    			if ($('input[name="aggiungi-layer-radio"]:checked').val() !== undefined) {
    				selezionati.push($('input[name="aggiungi-layer-radio"]:checked').val());    				
    			}
    			
    			$('input[name="aggiungi-layer"]:checkbox').each(function () {
    				if (this.checked && $(this).val() !== "") {
						selezionati.push($(this).val());
    				}
                });
    			
    			var cartografia = new Object();
    			cartografia.idVariante = $('#idVarianteDettaglio').val();
				cartografia.layers = selezionati;
    			$.when(
					pianoRegolatoreRest.salvaCartografia(cartografia)
				).done(function(risultato){
					if (risultato.message === 'Success') {
						iziToast.show({
							title: 'OK',
							theme: 'dark',
							icon:'fa fa-check',
							message: 'Cartografia salvata con successo.',
							animateInside: false,
							position: 'topCenter',
						});
					} else {
						iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon:'fa fa-times',
							message: 'Si &egrave; verificato un errore durante il salvataggio.',
							animateInside: false,
							position: 'topCenter',
						});
					}
					$.when(
						pianoRegolatoreRest.cercaCartografieByIdVariante($('#idVarianteDettaglio').val())
					).done(function(cartografie){
						self.inizializzaDatatableCartografia(cartografie.aaData);
						appUtil.hideLoader();
					});
					appUtil.hideLoader();
				});
    			$('#'+self.idDialog+'creaSelezionaCartografie').dialog('close');
    		});

    		$('#'+self.idDialog+'creaSelezionaCartografie #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaSelezionaCartografie').dialog('close');
    		});
    	}, {width: 800, height: 600, resizable: false, modale: true, closable: true});
	}

	apriModaleNuovoIndice(){
		var self = this;
		appUtil.hideLoader()
		openStaticDetailViaHandlebars(self.idDialog+'creaModificaIndici', 'modaleCreaModificaIndici', {}, 'INSERISCI INDICE', function(){
    		appUtil.reloadTooltips();
    		$('#'+self.idDialog+'creaModificaIndici #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaIndici').dialog('close');
    		});
    		$('#'+self.idDialog+'creaModificaIndici #salvaBtnIndici').off('click').on('click', function(){
    			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	    			var indice = new Object();
	    			indice.articolo = $('#articolo').val();
	    			indice.elencoPagine = $('#elencoPagine').val();
	    			indice.idDocumento = $("input[name='selezioneDocumento']:checked").val();
	    			$.when(
						pianoRegolatoreRest.salvaIndice(indice)
					).done(function(risultato){
						if (risultato.message === 'Success') {
							iziToast.show({
				    			title: 'OK',
				    			theme: 'dark',
				    			icon:'fa fa-check',
				    			message: "Salvataggio dell\'indice effettuato con successo",
				    			animateInside: false,
				    			position: 'topCenter',
				    		});
							$('#'+self.idDialog+'creaModificaIndici').dialog('close');
							$.when(
	    						pianoRegolatoreRest.cercaIndiciByIdDocumento($("input[name='selezioneDocumento']:checked").val())
	    					).done(function(indici){
	    						self.inizializzaDatatableIndici(indici.aaData);
	    					});
						} else {
							iziToast.error({
								title: 'Attenzione',
								theme: 'dark',
								icon:'fa fa-times',
								message: 'Si &egrave; verificato un errore durante il salvataggio.',
								animateInside: false,
								position: 'topCenter',
							});
						}
						appUtil.hideLoader();
					});
    		});
    	}, {width: 500, height: 250, resizable: false, modale: true, closable: true});
	}

	apriModaleConfermaEliminazioneIndiceDocumento(idIndice){
		var self = this;
		var id = idIndice;
		appUtil.confirmOperation( function(){
			//CONFERMA
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			$.when(
				pianoRegolatoreRest.cancellaIndice(id)
			).done(function(risultato){
				if (risultato.message === 'Success') {
					iziToast.show({
						title: 'OK',
						theme: 'dark',
						icon:'fa fa-check',
						message: 'La variante &egrave; stata rimossa con successo.',
						animateInside: false,
						position: 'topCenter',
					});
					$.when(
						pianoRegolatoreRest.cercaIndiciByIdDocumento($("input[name='selezioneDocumento']:checked").val())
					).done(function(indici){
						self.inizializzaDatatableIndici(indici.aaData);
						$('#sezioneCodici').hide();
					});
				} else {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Si &egrave; verificato un errore durante la cancellazione.',
						animateInside: false,
						position: 'topCenter',
					});
				}
			});
			console.log(id);
			appUtil.hideLoader();
		}, function(){
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere con la cancellazione del documento?');
	}

	apriModaleImportaIndice(){
		var self = this;
		appUtil.hideLoader()
		openStaticDetailViaHandlebars(self.idDialog+'importaIndiceDocumento', 'modaleImportaIndiceDocumento', {}, 'IMPORT INDICE', function(){
			appUtil.reloadTooltips();			
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
   			$('#'+self.idDialog+'importaIndiceDocumento'+' #importIndiceDocumento').filer(opzioniUploader);
    		$('#'+self.idDialog+'importaIndiceDocumento #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'importaIndiceDocumento').dialog('close');
    		});
    		$('#'+self.idDialog+'importaIndiceDocumento #salvaBtn').off('click').on('click', function(){
    			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	    			var formData = new FormData();
	    			formData.append('idDocumento', $("input[name='selezioneDocumento']:checked").val());
	    			formData.append('file', $('#importIndiceDocumento').prop('files')[0]);
	    			$.when(
						pianoRegolatoreRest.importaIndice(formData)
					).done(function(risultato){
						if (risultato.message === 'Success') {
							iziToast.show({
				    			title: 'OK',
				    			theme: 'dark',
				    			icon:'fa fa-check',
				    			message: "Importazione dell\'indice effettuato con successo",
				    			animateInside: false,
				    			position: 'topCenter',
				    		});
							$('#'+self.idDialog+'importaIndiceDocumento').dialog('close');
							$.when(
	    						pianoRegolatoreRest.cercaIndiciByIdDocumento($("input[name='selezioneDocumento']:checked").val())
	    					).done(function(indici){
	    						self.inizializzaDatatableIndici(indici.aaData);
	    					});
						} else {
							iziToast.error({
								title: 'Attenzione',
								theme: 'dark',
								icon:'fa fa-times',
								message: 'Si &egrave; verificato un errore durante il salvataggio.',
								animateInside: false,
								position: 'topCenter',
							});
						}
						appUtil.hideLoader();
					});
    		});
    	}, {width: 500, height: 250, resizable: false, modale: true, closable: true});
	}

	inizializzaDatatableCodiciZTO(data) {
		var self = this;
		var count = 0;
		$('#tabellaCodiciZTO').DataTable({
			data: data,
			scrollCollapse: true,
			ordering: true,
			scrollY : '350px',
			scrollX: false,
			paging: false,
			info: false,
			lengthChange: false,
			language: self.datatableLan,
			order: [[1, 'asc']],
			select: {
				style:    'os',
	            selector: 'td:first-child'
			},
			searching: true,
			columnDefs: [
	        	{targets: 0, orderable: false, data: null, defaultContent: '', width: '5%', render: function(d, t, r){
					count += 1;
					return '<div class="custom-control custom-checkbox">'+
								'<input id="checkbox-record-'+ count +'" type="checkbox" class="custom-control-input" data-codice="' + r.codice + '" data-descrizione="' + r.descrizione + '">'+
	                            '<label class="custom-control-label" for="checkbox-record-'+ count +'"><span class="sr-only">Seleziona</span></label>'+
							'</div>'; 
					},
					title: ''
				},
	        	{targets : 1, title: 'Codice', render: function(d, t, r) {return r.codice;}, orderable : true},
				{targets : 2, title: 'Descrizione', render: function(d, t, r) {return r.descrizione != null ? r.descrizione : 'N/D';}, orderable : true},
			],
			initComplete: function(settings, json) {
				$('.dataTables_scrollBody th').hide();
			},
			drawCallback: function( settings ) {
				$('.dataTables_scrollBody th').hide();
				let tabellaZTO = $('#tabellaCodiciZTO').DataTable();
				let codiciZTO = JSON.parse(sessionStorage.getItem('codiciZTO'));
				
				tabellaZTO.rows().every(function(rowIdx, tableLoop, rowLoop) {
					$('input', this.nodes()).each(function() {
						let codice = $(this).data('codice');
						let descrizione = $(this).data('descrizione') != null ? $(this).data('descrizione') : 'N/D';
						let checked = false;
						$.each(codiciZTO, function(key, value) {
							if(value.descrizione == descrizione && value.codice == codice) {
								checked = true
								return;
			}
		});
						if(checked) {
							$(this).prop('checked', true);
						}
					});	
				});
			}
		});
	};

	apriModaleSelezionaCodici(){
		var self = this;
		var titolo = "SELEZIONA CODICI";
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		$.when(
			pianoRegolatoreRest.cercaCodiciZto()
		).done(function(codiceZto) {
			if(codiceZto.success) {
				appUtil.hideLoader();
				self.inizializzaDatatableCodiciZTO(codiceZto.aaData);
			} else {
				iziToast.error({
					title: 'Errore',
					theme: 'dark',
					icon:'fa fa-times',
					message: "Nel recuperare i codice della ZTO",
					animateInside: false,
					position: 'topCenter',
				});
			}
		})

    	openStaticDetailViaHandlebars(self.idDialog+'selezionaCodici', 'modaleSelezionaCodici', {}, titolo, function(){

			$(self.idDialog+'selezionaCodici .ui-dialog-titlebar-minifize').hide();
			$('#codiciSelezionati').hide();

			appUtil.reloadTooltips();

    		$('#'+self.idDialog+'selezionaCodici #salvaBtn').off('click').on('click', function(){
    			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
    			var selezionati = [];
				
				var tabella = $('#tabellaCodiciZTO').DataTable();
				
				/** MI CONTROLLA I CHECK BOX SU TUTTE LE PAGINE DI DATATABLE */
				$("input.custom-control-input", tabella.rows({page: 'all'}).nodes()).each(function(){
					if($(this).is(':checked')) {
						let codice = $(this).data('codice');
						let descrizione = $(this).data('descrizione') != null ? $(this).data('descrizione') : 'N/D';
						selezionati.push(codice + '+' + descrizione);
					}
				});

				var codici = new Object();
    			codici.idIndice = $("input[name='selezioneIndice']:checked").val();
    			codici.layers = selezionati;
    			sessionStorage.setItem("indicePopolato", true);
    			$.when(
					pianoRegolatoreRest.salvaCodici(codici)
				).done(function(risultato){
					if (risultato.message === 'Success') {
						iziToast.show({
							title: 'OK',
							theme: 'dark',
							icon:'fa fa-check',
							message: jQuery.i18n.prop('message.operation.success'),
							animateInside: false,
							position: 'topCenter',
						});
					} else {
						iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon:'fa fa-times',
							message: jQuery.i18n.prop('message.insert.error'),
							animateInside: false,
							position: 'topCenter',
						});
					}
					$.when(
						pianoRegolatoreRest.cercaCodiciByIdIndice($("input[name='selezioneIndice']:checked").val())
					).done(function(codici){
						self.inizializzaDatatableCodici(codici.aaData);
					});
					appUtil.hideLoader();
				});
				$('#'+self.idDialog+'selezionaCodici').dialog('close');
				$('.ui-dialog-titlebar-minifize').show();
    		});

    		$('#'+self.idDialog+'selezionaCodici #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'selezionaCodici').dialog('close');
    		});
    	}, {width: 850, height: 650, resizable: false, modale: true, closable: true});
	}

}

/**
 * Classe controller per la gestione dei documenti
 */
class PaginaGestioneDocumentiPianoCtrl extends BaseJsTreeModaleRicercaCtrl {
	constructor( ) {
		super('paginaGestioneDocumentiPiano','GESTIONE DOCUMENTI', 'gestioneDocumentiPiano');
		this.datiMockRicerca = [{
			id: 1,
			tipo: 'DOC1',
			descrizione: 'DOC1 - Descrizione documento 1',
			variante: 'VARIANTE GENERALE',
			documento: 'doc1.pdf',
		},{
			id: 2,
			tipo: 'DOC2',
			descrizione: 'DOC2 - Descrizione documento 2',
			variante: 'PGT 2012',
			documento: 'doc2.pdf',
		},{
			id: 3,
			tipo: 'DOC3',
			descrizione: 'Documento 3',
			variante: 'PGT 2012',
			documento: 'nome_doc_3.pdf',
		},{
			id: 4,
			tipo: 'DOC4',
			descrizione: 'Documento 4',
			variante: 'PGT 2012',
			documento: 'doc4.pdf',
		},{
			id: 5,
			tipo: 'DOC5',
			descrizione: 'Documento',
			variante: 'PGT 2010',
			documento: 'doc5.pdf',
		},{
			id: 6,
			tipo: 'DOC6',
			descrizione: 'Descrizione del documento 6',
			variante: 'PGT 2010',
			documento: 'doc6.pdf',
		},{
			id: 7,
			tipo: 'DOC7',
			descrizione: 'Descrizione del documento 7',
			variante: 'PGT 2010',
			documento: 'doc7.pdf',
		}];
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventiClickTornaIndietroARicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			self.inizializzaDatatableRicerca();
			self.aggiungiEventiSalvaDocumento();
			self.aggiungiEventiCreaNuovaVariante();
			self.inizializzaJsTree('#menu-jstree', '#plugins4_q', true);	//idDivJsTree, idInputRicerca, isRicercaAbilitata
			appUtil.reloadTooltips();
		}, {closable: true});
	}
	inizializzaDatatableRicerca(){
		var self = this;
		$('#'+self.idDialog+' #tabellaRisultati').DataTable( {
			dom: self.datatableDom,
			buttons: self.datatableButtons,
			data: self.datiMockRicerca,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets :  0, render: function(d, t, r) {return r.tipo;}, orderable : true},
	        	{targets :  1, render: function(d, t, r) {return r.descrizione;}, orderable : true},
	        	{targets :  2, render: function(d, t, r) {return r.variante;}, orderable : true},
	        	{targets :  3, render: function(d, t, r) {return r.documento;}, orderable : true},
	        	{targets : 4, className: "text-right", orderable : false, render: function(d, t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: r.id});
	        		return templatePulsanti;
	        	}}
	        ],
	        initComplete: function( settings ) {
	        	self.aggiungiEventiAiPulsantiAzione();
	        }
	    });
	}
	aggiungiEventiAiPulsantiAzione(){
		var self = this;
		$('#'+self.idDialog+' .download-documento-btn').off('click').on('click', function(event){
			let id = $(this).data('id');
			let nomeFile = 'documento.pdf';
			for(var i=0; i<self.datiMockRicerca.length;i++){
				if(self.datiMockRicerca[i].id === id){
					nomeFile = self.datiMockRicerca[i].documento;
					break;
				}
			}
        	self.downloadFilePdfVuoto(nomeFile);
        });
		$('#'+self.idDialog+' .dettaglio-documento-btn').off('click').on('click', function(event){
			self.vaiAlDettaglio($(this).data('id'));
        });
		$('#'+self.idDialog+' .rimuovi-documento-btn').off('click').on('click', function(event){
			self.rimuoviDocumento($(this).parents('tr'));
        });
	}
	aggiungiEventiCreaNuovaVariante(){
		var self = this;
		$('#'+self.idDialog+' #menuAggiungiDocumento, '+'#'+self.idDialog+' #nuovoBtn').off('click').on('click', function(){
			//TODO TODO TODO
		});
	}
	rimuoviDocumento(rigaDatatable){
		var self = this;
		appUtil.confirmOperation( function(){
			//CONFERMA
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				appUtil.hideLoader();
				$('#'+self.idDialog+' #tabellaRisultati').DataTable().row(rigaDatatable).remove().draw();
				iziToast.show({
					title: 'OK',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'Il documento &egrave; stato rimosso con successo.',
					animateInside: false,
					position: 'topCenter',
				});
			}, 1500);
		}, function(){
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere con la cancellazione del documento?');
	}
	
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			self.pulisciCampiForm();
			let dettaglio = self.recuperaDettaglio(id);
			$('#'+self.idDialog+' #enteRiferimento').val('MESSINA');
			$('#'+self.idDialog+' #tipoDocumento').val(dettaglio.tipo);
			$('#'+self.idDialog+' #descrizioneDocumento').val(dettaglio.descrizione);
			$('#'+self.idDialog+' #varianteDocumento').val(dettaglio.variante);
			$('#'+self.idDialog+' #nomeDocumento').val(dettaglio.documento);
			self.nascondiPaginaRicerca();
			self.aggiornaBreadcrumb('dettaglio', dettaglio.id);
			appUtil.hideLoader();
		}, 1000);
	}
	
	recuperaDettaglio(id){
		let dettaglio = null;
		for(var i=0;i<this.datiMockRicerca.length;i++){
			if(this.datiMockRicerca[i].id === id){
				dettaglio = this.datiMockRicerca[i];
				break;
			}
		}
		return dettaglio;
	}
	
	pulisciCampiForm(){
		$('#'+this.idDialog+' #dettaglio :input').each(function(){
			$(this).val('');
		});
	}
	
	aggiungiEventiSalvaDocumento(){
		var self = this;
		$('#'+self.idDialog+' #salvaBtn, '+'#'+self.idDialog+' #salvaChiudiBtn').off('click').on('click', function(){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var id = $(this).attr('id');
			setTimeout(function(){
				appUtil.hideLoader();
				if(id === 'salvaChiudiBtn'){
					$('#'+self.idDialog).dialog('close');
				}
				iziToast.show({
					title: 'OK',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'Documento salvato con successo.',
					animateInside: false,
					position: 'topCenter',
				});
			}, 1000);
		});
	}
}

/**
 * Classe controller per la gestione delle preferenze
 */
class PaginaGestionePreferenzePianoCtrl extends BaseModaleCtrl {
	constructor( ) {
		super('paginaGestionePreferenzePiano','GESTIONE PREFERENZE PIANO REGOLATORE', 'gestionePreferenzePiano');
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventoClickChiudiFinestra();
			self.aggiungiEventoClickSalvaImpostazioni();
			self.inizializzaBreadcrumb();
			self.inizializzaColorpicker();
			appUtil.reloadTooltips();
		}, {width: 500, height: 500, resizable: false, closable: true, modale: true});
	}
	/**
	 * Aggiunge evento di click che "salva" le impostazioni.
	 * Fondamentalmente mostra solo il toast di successo
	 */
	aggiungiEventoClickSalvaImpostazioni(){
		var self = this;
		$('#'+self.idDialog+' #eseguiBtn').off('click').on('click', function(){
			$('#'+self.idDialog).dialog('close');
			iziToast.show({
    			title: 'OK',
    			theme: 'dark',
    			icon:'fa fa-check',
    			message: 'Impostazioni salvate con successo.',
    			animateInside: false,
    			position: 'topCenter',
    		});
		});
	}
	inizializzaColorpicker(){
		$('#colore, #coloreBordo').off('change').on('change', function(){
			$(this).parent().find('span.input-group-addon').css('background', $(this).val());
		});
		$('#colore').colorpicker();
		$('#coloreBordo').colorpicker();
	}
}

class PaginaStrutturaPianoEntiCtrl extends BaseModaleRicercaCtrl {
	constructor( ) {
		super('paginaStrutturaPianoEnti','ENTI', 'modaleStrutturaPianoEnti');
		this.datiMockRicerca = [{
			id: 1,
			sigla: 'Messina',
			descrizione: 'Comune di Messina',
			note: 'Lorem ipsum dolor sit amet',
		},{
			id: 2,
			sigla: 'Palermo',
			descrizione: 'Comune di Palermo',
			note: 'Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua',
		},{
			id: 3,
			sigla: 'Caltanissetta',
			descrizione: 'Comune di Caltanissetta',
			note: 'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat',
		}];
		this.messaggioModale = '';
		this.messaggioModaleCrea = 'L\'ente &egrave; stato creato con successo.';
		this.messaggioModaleModifica = 'L\'ente &egrave; stato aggiornato con successo.';
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.inizializzaDatatableRicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.aggiungiEventiCreazioneEnte();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
		}, {closable: true});
	}
	inizializzaDatatableRicerca(){
		var self = this;
		$('#'+self.idDialog+' #tabellaRisultati').DataTable( {
			dom: self.datatableDom,
			buttons: self.datatableButtons,
			data: self.datiMockRicerca,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(d, t, r) {return r.sigla;}, orderable : true},
	        	{targets : 1, render: function(d, t, r) {return r.descrizione;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.note;}, orderable : true},
	        	{targets : 3, className: "text-right", orderable : false, render: function(d, t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiModificaElimina', {id: r.id});
	        		return templatePulsanti;
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	self.aggiungiEventiPulsantiTabella();
	        }
	    });
	}
	aggiungiEventiCreazioneEnte(){
		var self = this;
		$('#'+self.idDialog+' #menuAggiungiEnte, '+'#'+self.idDialog+' #nuovoBtn').off('click').on('click', function(event){
			self.apriModaleCreaModificaEnte();
		});
	}
	ricaricaDatatable(){
		let datatable = $('#'+this.idDialog+' #tabellaRisultati').DataTable();
		datatable.clear().draw();
		datatable.rows.add(this.datiMockRicerca);
		datatable.columns.adjust().draw();
	}
	rimuoviEnte(id){
		var self = this;
		appUtil.confirmOperation( function(){
			//CONFERMA
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				appUtil.hideLoader();
				for(let i=0; i<self.datiMockRicerca.length; i++){
					if(self.datiMockRicerca[i].id === id){
						self.datiMockRicerca.splice(i, 1);
						break;
					}
				}
				self.ricaricaDatatable();
				iziToast.show({
					title: 'OK',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'L\'ente &egrave; stato rimosso con successo.',
					animateInside: false,
					position: 'topCenter',
				});
			}, 1500);
		}, function(){
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere con la cancellazione dell\'ente?');
	}
	aggiungiEventiPulsantiTabella(){
		var self = this;
		$('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
			self.vaiAlDettaglio($(this).data('id'));
		});
		$('#'+self.idDialog+' .rimuovi-btn').off('click').on('click', function(event){
			self.rimuoviEnte($(this).data('id'));
		});
	}
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			let dettaglio = self.recuperaDettaglio(id);
			self.apriModaleCreaModificaEnte(dettaglio);
			appUtil.hideLoader();
		}, 1000);
	}
	recuperaDettaglio(id){
		let dettaglio = {};
		for(var i=0; i<this.datiMockRicerca.length; i++){
			if(this.datiMockRicerca[i].id === id){
				dettaglio = this.datiMockRicerca[i];
				break;
			}
		}
		return dettaglio;
	}
	apriModaleCreaModificaEnte(ente){
		var self = this;
		self.messaggioModale = ente ? self.messaggioModaleModifica : self.messaggioModaleCrea;
		let titolo = ente ? 'MODIFICA ENTE' : 'CREA NUOVO ENTE';
    	openStaticDetailViaHandlebars(self.idDialog+'creaModificaEnte', 'modaleCreaModificaEnte', {}, titolo, function(){
    		appUtil.reloadTooltips();
   			$('#'+self.idDialog+'creaModificaEnte #sigla').val(ente ? ente.sigla : '');
			$('#'+self.idDialog+'creaModificaEnte #note').val(ente ? ente.note : '');
			$('#'+self.idDialog+'creaModificaEnte #descrizione').val(ente ? ente.descrizione : '');
    		$('#'+self.idDialog+'creaModificaEnte #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaEnte').dialog('close');
    		});
    		$('#'+self.idDialog+'creaModificaEnte #salvaBtn').off('click').on('click', function(){
    			//self.callbackFineSalvataggioModale(ente);
    			let enteCreatoOAggiornato = {
    				id: ente ? ente.id : self.datiMockRicerca.length + 1,
    				sigla: $('#'+self.idDialog+'creaModificaEnte #sigla').val(),
    				note: $('#'+self.idDialog+'creaModificaEnte #note').val(),
    				descrizione: $('#'+self.idDialog+'creaModificaEnte #descrizione').val(),
    			}
    			if(ente){
    				self.modificaEnte(enteCreatoOAggiornato);
    			} else {
    				self.creaEnte(enteCreatoOAggiornato);
    			}
    			$('#'+self.idDialog+'creaModificaEnte').dialog('close');
    			iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: self.messaggioModale,
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
    			
    		});
    	}, {width: 500, height: 320, resizable: false, modale: true, closable: true});
	}
	creaEnte(ente){
		this.datiMockRicerca.push(ente);
		this.ricaricaDatatable();
	}
	modificaEnte(ente){
		for(let i=0; i<this.datiMockRicerca.length;i++){
			if(this.datiMockRicerca[i].id === ente.id){
				this.datiMockRicerca[i].sigla = ente.sigla;
				this.datiMockRicerca[i].descrizione = ente.descrizione;
				this.datiMockRicerca[i].note = ente.note;
				break;
			}
		}
		this.ricaricaDatatable();
	}
}

/**
 * CONTROLLER PER LA GESTIONE DEI TIPI DEI DOCUMENTI
 */
class PaginaStrutturaPianoTipiDocumentoCtrl extends BaseModaleRicercaCtrl {
	
	constructor( ) {
		/** Inserisce come html la modale in pagina**/
		super('paginaStrutturaPianoTipiDocumento','TIPI DOCUMENTO', 'modaleStrutturaPianoTipiDocumento');
		/** Inizializza la modale **/
		this.inizializzaPagina();
	}

	/** Inizializza la modale **/
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			
			/** INIZIALIZZAZIONI */
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
		
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		
		/** NASCONDO LA TABELLA */
		$('#tabellaTipoDocumenti').hide();
		
		/** CERCA */
		$( "#ricercaBtnDocumento" ).click(function() {
			/** VISUALIZZO LA TABELLA */
			$('#tabellaTipoDocumenti').show();
			/** INIZIALIZZO DATATABLE */
			self.inizializzaDatatableRicerca();
		});
		
		/** AZZERA */
		$( "#azzeraBtnDocumento" ).click(function() {
			self.resettaForm('ricerca')
		});

		/** NUOVO */
		$('#'+ self.idDialog + ' #nuovoBtn').off('click').on('click', function(event){
			self.apriModaleCreaModificaTipoDocumento();
		});
		sessionStorage.setItem('windowOpened','paginaStrutturaPianoTipiDocumento');
	}

	/** Ripulisce tutti i campi input dato l'id
	 *  @param id, identificativo del form, o del div
	*/
	resettaForm(form) {
		$(':input', '#' + form).each(function() {
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
	 * Metodo per inizializzare la tabella di ricerca basandosi sui campi del filtro di ricerca
	 */
	inizializzaDatatableRicerca(){
		/** INIZIALIZZAZIONE */
		var self = this;
		/** RECUPERO PARAMETRI DI RICERCA */
		var codice = $('#codice').val();
		var descrizione = $('#descrizione').val();
		var descrizioneCDU = $('#descrizioneCDU').val();
		var note = $('#note').val();
		/** URL PER LA CHIAMATA AJAX DELLA TABELLA */
		var url = pianoRegolatoreRest.ricercaTipoDocumenti(codice.trim(), descrizione.trim(), descrizioneCDU.trim(), note.trim());
		$('#tabellaTipoDocumenti').DataTable({
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: false,
	        searchable: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
			ajax: url,
			order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(d, t, r) {return r.codice.toUpperCase();}, orderable : false},
	        	{targets : 1, render: function(d, t, r) {return r.descrizione.toUpperCase();}, orderable : false},
	        	{targets : 2, render: function(d, t, r) {return r.descrizioneCDU.toUpperCase();}, orderable : false},
	        	{targets : 3, render: function(d, t, r) {return r.note.toUpperCase();}, width: "25%", orderable : false},
	        	{targets : 4, className: "text-right", orderable : false, render: function(d, t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTipoDocumento', {id: r.id, codice: r.codice});
	        		return templatePulsanti;
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	self.aggiungiEventiPulsantiTabella();
	        	sessionStorage.setItem('documenti', JSON.stringify(settings.json.data));
	        },
	    });
		
	}
	
	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio del tipo documento.
	 * - elimina, aprirà un box di conferma con la richiesta di eliminazione del tipo documento
	 *     1. si procede con eliminazione nella tabella u_prg_tipo_documento
	 * 	   2. l'operazione verrà annullata
	 **/
	aggiungiEventiPulsantiTabella(){
		var self = this;
		$('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
			/** VISUALIZZA IL DETTAGLIO */
			self.vaiAlDettaglio($(this).data('id'));
		});
		$('#'+self.idDialog+' .rimuovi-btn').off('click').on('click', function(event){
			/** ELIMINA IL TIPO DI DOCUMENTO */
			self.rimuoviTipoDocumento($(this).data('id'), $(this).data('codice'));
		});
	}

	/** 
	 * Il metodo ricarica DataTable
	 * @param idTable, identificativo della tabella
	 */
	ricaricaTabella(idTable) {
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
	 * Metodo per il recupero del dettaglio di un tipo di documento dato l'id
	 * @param id, identificativo del tipo di documento
	 */
	recuperaDettaglio(id){
		let documenti = JSON.parse(sessionStorage.getItem('documenti'));
		let dettaglio;
		for(var i=0; i<documenti.length; i++){
			if(documenti[i].id === id){
				dettaglio = documenti[i];
				break;
			}
		}
		return dettaglio;
	}

	/**
	 * Il metodo chiama la modale di creazione/modifica del tipo di documento
	 * @param id, identificativo del tipo di documento
	 */
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			/** RECUPERO IL DETTAGLIO */
			let dettaglio = self.recuperaDettaglio(id);
			/** APERTURA DELLA MODALE DEL DETTAGLIO/INSERIMENTO */
			self.apriModaleCreaModificaTipoDocumento(dettaglio);
			/** NASCONDO IL LOADER */
			appUtil.hideLoader();
		}, 1000);
	}
	
	/** Modale per l'inserimento/modifica del tipo di documento */
	apriModaleCreaModificaTipoDocumento(tipo){
		/** INIZIALIZZAZIONE */
		var self = this;
		/** IMPOSTAZIONI TITOLO */
		let titolo = tipo ? 'MODIFICA TIPO DOCUMENTO' : 'CREA NUOVO TIPO DOCUMENTO';
		/** OGGETTO TIPO DI DOCUMENTO PER LA VISUALIZZAZIONE  */
		let contextHandlebar = { codice: tipo ? tipo.codice : '',
								 descrizione: tipo ? tipo.descrizione.toUpperCase() : '',
								 descrizioneCDU: tipo ? tipo.descrizioneCDU : '',
								 note: tipo ? tipo.note : '',
								 readonly: tipo ? 'readonly' : '' }

		openStaticDetailViaHandlebars(self.idDialog+'creaModificaTipoDocumento', 'modaleCreaModificaTipoDocumento', contextHandlebar, titolo, function(){
			appUtil.reloadTooltips();

			/** VERIFICHE PER IL SALVATAGGIO DELL'INSERIMENTO/MODIFICA */
    		$('#'+ self.idDialog + 'creaModificaTipoDocumento #salvaBtn').off('click').on('click', function(){
    			if($("#descrizioneM").val() == '' || $("#codiceM").val() == '') {
					/** MESSAGGIO */
					iziToast.error({
    	    			title: 'Attenzione',
    	    			theme: 'dark',
    	    			icon:'fa fa-times',
    	    			message: 'Devono essere compilati tutti i campi obbligatori',
    	    			animateInside: false,
    	    			position: 'topCenter',
    	    		});
    			} else {
					/** CREO L'GGETTO DA SALVARE */
    				let creatoOAggiornato = { id: tipo ? tipo.id : null,
    	    	    						  codice: $('#'+self.idDialog+'creaModificaTipoDocumento #codiceM').val(),
    	    	    						  descrizione: $('#'+self.idDialog+'creaModificaTipoDocumento #descrizioneM').val(),
    	    	    						  descrizioneCDU: $('#'+self.idDialog+'creaModificaTipoDocumento #descrizioneCDU').val(),
    	    	    						  note: $('#'+self.idDialog+'creaModificaTipoDocumento #note').val() }
    	    	    if(tipo){
						/** MODIFICA */
    	    	    	self.modificaTipoDocumento(creatoOAggiornato);
    	    	    } else {
						/** INSERIMENTO */
    	    	    	self.creaTipoDocumento(creatoOAggiornato);
    	    	    } 			
    			}
			});
			
			/** CHIUDI MODALE DETTAGLIO/INSERIMENTO */
			$('#' + self.idDialog + 'creaModificaTipoDocumento #chiudiBtn').off('click').on('click', function() {
				$('#' + self.idDialog + 'creaModificaTipoDocumento').dialog('close');
			});

    	}, {width: 500, height: 460, resizable: false, modale: true, closable: true});
	}

	/**
	 * Il metodo salva il tipo di documento e riporta l'utente sulla pagina di ricerca. 
	 * @param documento, è un oggetto contentente le informazioni di un tipo di documento da inserire
	 */
	creaTipoDocumento(documento){
		var self = this;
		$.when(pianoRegolatoreRest.insertOrUpdateTipoDocumento(documento)).done(function(result) {
			if(result.success) {
				/** CHIUSURA DELLA MODALE */
				$('#'+ self.idDialog + 'creaModificaTipoDocumento').dialog('close');
				/** RICARICO LA TABELLA */
				self.ricaricaTabella('tabellaTipoDocumenti');
				/** MESSAGGIO */
    			iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: 'Il tipo documento: ' + documento.codice.toUpperCase() +' &egrave; stato aggiunto con successo',
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
			} else {
				/** MESSAGGIO DI ERRORE */
				iziToast.error({
	    			title: 'Attenzione',
	    			theme: 'dark',
	    			icon:'fa fa-times',
	    			message: 'Errore nell\'inserimento del tipo di documento e/o duplicazione del parametro CODICE',
	    			animateInside: false,
	    			position: 'topCenter'
	    		});
			}
		});
	}

	/**
	 * Il metodo modifica il tipo di documento e riporta l'utente sulla pagina di ricerca. 
	 * @param documento, è un oggetto contentente le informazioni di un tipo di documento da modificare
	 */
	modificaTipoDocumento(documento) {
		var self = this;
		$.when(pianoRegolatoreRest.insertOrUpdateTipoDocumento(documento)).done(function(result) {
			if(result.success) {
				/** CHIUSURA DELLA MODALE */
				$('#'+self.idDialog+'creaModificaTipoDocumento').dialog('close');
				/** RICARICO LA TABELLA */
				self.ricaricaTabella('tabellaTipoDocumenti');
    			iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: 'Il tipo documento: ' + documento.codice.toUpperCase() +' &egrave; stato aggiornato con successo',
	    			animateInside: false,
	    			position: 'topCenter',
				});
			} else {
				/** MESSAGGIO DI ERRORE */
				iziToast.error({
	    			title: 'Attenzione',
	    			theme: 'dark',
	    			icon:'fa fa-times',
	    			message: 'Errore nella modifica del tipo di documento',
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
			}
		});
	}

	/**
	 * Il metodo elimina il tipo di documento
	 * @param id, identificativo del tipo di documento
	 */
	rimuoviTipoDocumento(id, tipoDocumento){
		var self = this;
		var messaggioVariantiAssociate = "Il tipo di documento &egrave; associato alle seguenti varianti: ";
		var messaggioDiConferma = "Sei sicuro di voler procedere con la cancellazione del tipo di documento?";
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(pianoRegolatoreRest.varianteByTipoDocumento(tipoDocumento)).done(function(response){
			if(response.success) {
				if(response.iTotalRecords != 0) {

					let listaVarianti = compilaTemplate("listaVarianti", {listaVarianti: response.aaData, messaggioLayerAssociati: messaggioVariantiAssociate, messaggioDiConferma: messaggioDiConferma});

					appUtil.hideLoader();

					appUtil.confirmOperation( function(){
						/** CONFERMA */
						appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							
						$.each(response.aaData, function(index, value) {
							$.when(pianoRegolatoreRest.cancellaDocumento(value.idDocumento)).done(function(response) {
								if(!response.success) {
									iziToast.error({
										title: 'Attenzione',
										theme: 'dark',
										icon:'fa fa-times',
										message: 'Si &egrave; verificato un errore durante la cancellazione.',
										animateInside: false,
										position: 'topCenter',
									});
								}
							});
						});
			
						$.when(pianoRegolatoreRest.cancellaTipoDocumento(id)).done(function(response){
							if(response.success) {
								/** NASCONDO IL LOADER */
								appUtil.hideLoader();
								/** RICARICO LA TABELLA */
								self.ricaricaTabella('tabellaTipoDocumenti');
								/** MESSAGGIO */
								iziToast.show({
									title: 'OK',
									theme: 'dark',
									icon:'fa fa-check',
									message: 'Tipo documento eliminato con successo',
									animateInside: false,
									position: 'topCenter',
								});
							} else {
								/** NASCONDO IL LOADER */
								appUtil.hideLoader();
								/** MESSAGGIO D'ERRORE */
								iziToast.error({
									title: 'Attenzione',
									theme: 'dark',
									icon:'fa fa-times',
									message: 'Si &egrave; verificato un errore durante la cancellazione.',
									animateInside: false,
									position: 'topCenter',
								});
							}
						});
					}, function(){
					//ANNULLA
					}, {}, listaVarianti);

				} else {
					appUtil.hideLoader();
					
					appUtil.confirmOperation( function(){
						
						$.when(pianoRegolatoreRest.cancellaTipoDocumento(id)).done(function (response) {
							if(response.success) {
								/** RICARICO LA TABELLA */
								self.ricaricaTabella('tabellaTipoDocumenti');
								/** MESSAGGIO */
								iziToast.show({
									title: 'OK',
									theme: 'dark',
									icon:'fa fa-check',
									message: 'Tipo documento eliminato con successo',
									animateInside: false,
									position: 'topCenter',
								});
							} else {
								/** MESSAGGIO DI ERRORE */
								iziToast.error({
									title: 'Attenzione',
									theme: 'dark',
									icon:'fa fa-times',
									message: 'Errore nella cancellazione del tipo documento!',
									animateInside: false,
									position: 'topCenter',
								});
							}
						})
						
					}, function(){
						//ANNULLA
					}, {}, messaggioDiConferma);
				}
			} else {
				appUtil.hideLoader();
				iziToast.error({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-times',
					message: 'Errore nella cancellazione del tipo documento!',
					animateInside: false,
					position: 'topCenter',
				});
			}
		});
		
	}
}

class PaginaStrutturaPianoCtrl extends BaseJsTreeModaleRicercaCtrl {
	constructor( ) {
		super('paginaStrutturaPiano','STRUTTURA DEL PIANO', 'modaleStrutturaPiano');
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			//i pulsanti contestuali vengono mostrati selezionando uno dei nodi di jstree.
			$('#'+self.idDialog+' .context-btn').off('click');
			$('#'+self.idDialog+' .context-btn').parent('li').hide();

			self.inizializzaJsTreeStrutturaPiano('#menu-jstree');	//idDivJsTree, idInputRicerca, isRicercaAbilitata
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
		}, {closable: true, width: 650, height: 550});
		sessionStorage.setItem('windowOpened','paginaStrutturaPiano');
	}
}

class PaginaCertificatoUrbanisticoCtrl extends BaseModaleCtrl {
	
	constructor( ) {
		super('paginaCertificatoUrbanistico','GENERAZIONE CERTIFICATO URBANISTICO', 'modaleCertificatoUrbanistico');
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;

		/**Inizializzo**/
		sessionStorage.removeItem('varianteSelezionata');
		sessionStorage.removeItem('geometriaPiano');
		
		/**Recupero dei dati di base della modale Certificato Urbanistico**/
		/**************************** TIPI TOPONIMI **************************/
		var hrefPRGAllVariantiUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/prgConsultazioneController/getAllVariantiByDate";
    	var urbamidResponse = appRest.restUrbamid(hrefPRGAllVariantiUrbamid, "POST", null);
		self.varianti = urbamidResponse.aaData;
		
		
		
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			
			/**GESTIONE DEL RADIO BUTTON PER LA SCELTA DELLA VARIANTE**/
			self.varianteDateSelected 	= null;
			self.enableVariantiDayAppr 	= [];
			self.hmVariantiDateAppr 	= new Object();
			$.each( self.varianti, function( keyVar, itemVar ) {
				if (itemVar.dataDelAppr!=null) {
					self.enableVariantiDayAppr.push(itemVar.dataDelAppr);
					self.hmVariantiDateAppr[itemVar.dataDelAppr] = itemVar;
				}
			});
			sessionStorage.setItem('enableVariantiDayAppr',JSON.stringify(self.enableVariantiDayAppr) );
			
			self.enableVariantiDayAdoz 	= [];
			self.hmVariantiDateAdoz		= new Object();
			$.each( self.varianti, function( keyVar, itemVar ) {
				if (itemVar.dataDelAdoz!=null && (itemVar.dataDelAppr==undefined || itemVar.dataDelAppr==null)) {
					self.enableVariantiDayAdoz.push(itemVar.dataDelAdoz);
					self.hmVariantiDateAdoz[itemVar.dataDelAdoz] = itemVar;
				}
			});
			sessionStorage.setItem('enableVariantiDayAdoz',JSON.stringify(self.enableVariantiDayAdoz) );
			
			$('input[type=radio][name=optradio]').change(function() {
			    if (this.value == 'vigente') {
			    	
			    	$("#varianteScelta").html("");
			    	// $("#selezioneGeometria").html("");
			    	$("#dataVigenteSel").attr('readonly','');
			    	var dates = JSON.parse(sessionStorage.getItem('enableVariantiDayAppr'));
			    	var sortedDates = dates.slice() // copy the array for keeping original array with order
			    	  .sort(function(a, b) {
			    		  var af = a.split("/")
			    		  var adf = new Date(af[2], af[1] - 1, af[0]);
			    		  var bf = b.split("/")
			    		  var bdf = new Date(bf[2], bf[1] - 1, bf[0]);
			    		  return new Date(adf) - new Date(bdf);
			    	});
			    	self.variante = self.hmVariantiDateAppr[sortedDates.pop()];
			    	$("#varianteScelta").html(compilaTemplate('modaleCertificatoUrbanisticoVariante', self.variante));
			    	sessionStorage.setItem('varianteSelezionata', JSON.stringify(self.variante));
			    }
			    else if (this.value == 'salvaguardia') {
			    	
			    	$("#varianteScelta").html("");
			    	$("#dataVigenteSel").attr('readonly','');
			    	var dates = JSON.parse(sessionStorage.getItem('enableVariantiDayAdoz'));
			    	var sortedDates = dates.slice() // copy the array for keeping original array with order
			    	  .sort(function(a, b) {
			    		  var af = a.split("/")
			    		  var adf = new Date(af[2], af[1] - 1, af[0]);
			    		  var bf = b.split("/")
			    		  var bdf = new Date(bf[2], bf[1] - 1, bf[0]);
			    		  return new Date(adf) - new Date(bdf);
			    	});
			    	self.variante = self.hmVariantiDateAdoz[sortedDates.pop()];
			    	$("#varianteScelta").html(compilaTemplate('modaleCertificatoUrbanisticoVariante', self.variante));
			    	sessionStorage.setItem('varianteSelezionata', JSON.stringify(self.variante));
			    }
			    else if (this.value == 'datavigente') {
			    	$("#varianteScelta").html();
			    	if ($("#dataVigenteSel").attr("readonly")!=null) {
			    		
			    		$("#dataVigenteSel").datepicker({
							dateFormat: 'dd/mm/yy', 
							changeMonth: true,
							yearRange: "-100:+0",
							changeYear: true,
							i18n:{
								  it:{
								   months:[
								    'Gennaio','Febbraio','Marzo','Aprile',
								    'Maggio','Giugno','Luglio','Agosto',
								    'Settembre','Ottobre','Novembre','Dicembre',
								   ],
								   dayOfWeek:[
								    "Lu", "Ma", "Me", "Gi", 
								    "Ve", "Sa", "Do",
								   ]
							}},
							beforeShowDay: self.enableAllTheseDays,
							onSelect: function( selectedDate ) {
								self.varianteDateSelected = selectedDate;
								self.variante = self.hmVariantiDateAppr[self.varianteDateSelected];
								$("#varianteScelta").html(compilaTemplate('modaleCertificatoUrbanisticoVariante', self.variante));
								sessionStorage.setItem('varianteSelezionata', JSON.stringify(self.variante));
							}
			    		});
			    		$("#dataVigenteSel").removeAttr('readonly');	
			    	}
			    }
			});
			
			$('#paginaCertificatoUrbanistico #esportaCduBtn').off('click').on('click', function(){
				var self = this;
				appUtil.hideLoader()
				if ( sessionStorage.getItem('varianteSelezionata')!=null && sessionStorage.getItem('geometriaPiano')!=null ) {
					
					openStaticDetailViaHandlebars(self.idDialog+'informazioniAggiuntiveCdu', 'modaleInformazioniAggiuntiveCdu', {}, 'INFORMAZIONI CDU', function(){
						$("#dataRichiesta").datepicker();
						$("#dataPrimoVersamento").datepicker();
						$("#dataSecondoVersamento").datepicker();
						$("#dataVisuraCatastale").datepicker();
						$("#dataModelloF23").datepicker();
						appUtil.reloadTooltips();
			    		$('#'+self.idDialog+'informazioniAggiuntiveCdu #chiudiBtn').off('click').on('click', function(){
			    			$('#'+self.idDialog+'informazioniAggiuntiveCdu').dialog('close');
			    		});
			    		$('#'+self.idDialog+'informazioniAggiuntiveCdu #salvaBtn').off('click').on('click', function(){
			    			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			    			
			    			if ($('#protocollo').val() === "") {
			    				appUtil.hideLoader();
			    				iziToast.error({
		    						balloon: false,
		    						icon:'fa fa-times', 
		    						animateInside: false,
		    						theme: 'dark', 
		    						position: 'topCenter',
		    						title: 'Errore',
		    						message: "E' necessario valorizzare il campo Protocollo.",
		    					});
			    			} else {
			    				
			    				$.when(
		    						pianoRegolatoreRest.cercaProtocollo($('#protocollo').val())
		    					).done(function(risultato){
		    						if (risultato.aaData !== null) {
		    							appUtil.hideLoader();
		    							appUtil.confirmOperation( function(){
		    								//CONFERMA
		    								appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		    								var dto = new Object();
		    								dto.dataRichiesta = $('#dataRichiesta').val();
		    								dto.nomeDitta = $('#nomeDitta').val();
		    								dto.protocollo = $('#protocollo').val();
											dto.codiceModelloF23 = $('#codiceModelloF23').val();
											dto.dataModelloF23 = $('#dataModelloF23').val();
											dto.riferimentoMappaCatastale = $('#riferimentoMappaCatastale').val();
											dto.riferimentoVisuraCatastale = $('#riferimentoVisuraCatastale').val();
											dto.dataVisuraCatastale = $('#dataVisuraCatastale').val();
											dto.codicePrimoVersamento = $('#codicePrimoVersamento').val();
		    								dto.dataPrimoVersamento = $('#dataPrimoVersamento').val();
		    								dto.importoPrimoVersamento = $('#importoPrimoVersamento').val();
		    								dto.codiceSecondoVersamento = $('#codiceSecondoVersamento').val();
		    								dto.dataSecondoVersamento = $('#dataSecondoVersamento').val();
		    								dto.importoSecondoVersamento = $('#importoSecondoVersamento').val();
		    								dto.entity = JSON.parse(sessionStorage.getItem('geometriaPiano'));	
		    								dto.variante = JSON.parse(sessionStorage.getItem('varianteSelezionata'));
		    								
		    								dto.dipartimento = JSON.parse(sessionStorage.getItem('dipartimento'));
		    								dto.servizio = JSON.parse(sessionStorage.getItem('servizio'));
		    								dto.emailReferente = JSON.parse(sessionStorage.getItem('emailReferente'));
		    								dto.responsabileServizio = JSON.parse(sessionStorage.getItem('responsabileServizio'));
		    								dto.responsabilePianoTerritoriale = JSON.parse(sessionStorage.getItem('responsabilePianoTerritoriale'));
		    								dto.dirigenteDipartimento = JSON.parse(sessionStorage.getItem('dirigenteDipartimento'));
		    								

		    								pianoRegolatoreRest.downloadCdu(dto).then(function(response){
		    									appUtil.hideLoader();
		    									if (response !== null || response !== undefined) {
		    										const linkSource = 'data:application/msword;base64,'+response;
		    										const downloadLink = document.createElement('a');
		    										const fileName = "CDU.doc";
		    										downloadLink.href = linkSource;
		    										downloadLink.download = fileName;
		    										downloadLink.click();
		    									} else {
		    										iziToast.error({
		    											balloon: false,
		    											icon:'fa fa-times', 
		    											animateInside: false,
		    											theme: 'dark', 
		    											position: 'topCenter',
		    											title: 'Errore',
		    											message: 'Si &egrave; verificato un errore durante la fase di export',
		    										});
		    									}
		    								}, function(a,b,c){
		    									appUtil.hideLoader();
		    									console.log(a,b,c);
		    								});
		    							}, function(){
		    								//ANNULLA
		    							}, {}, "Sovrascrivere il Protocollo gia' esistente?");		    							
		    						} else {
		    							var dto = new Object();
		    							dto.dataRichiesta = $('#dataRichiesta').val();
		    							dto.nomeDitta = $('#nomeDitta').val();
										dto.protocollo = $('#protocollo').val();
										dto.codiceModelloF23 = $('#codiceModelloF23').val();
										dto.dataModelloF23 = $('#dataModelloF23').val();
										dto.riferimentoMappaCatastale = $('#riferimentoMappaCatastale').val();
										dto.riferimentoVisuraCatastale = $('#riferimentoVisuraCatastale').val();
										dto.dataVisuraCatastale = $('#dataVisuraCatastale').val();
		    							dto.codicePrimoVersamento = $('#codicePrimoVersamento').val();
		    							dto.dataPrimoVersamento = $('#dataPrimoVersamento').val();
		    							dto.importoPrimoVersamento = $('#importoPrimoVersamento').val();
		    							dto.codiceSecondoVersamento = $('#codiceSecondoVersamento').val();
		    							dto.dataSecondoVersamento = $('#dataSecondoVersamento').val();
		    							dto.importoSecondoVersamento = $('#importoSecondoVersamento').val();
		    							dto.entity = JSON.parse(sessionStorage.getItem('geometriaPiano'));	
		    							dto.variante = JSON.parse(sessionStorage.getItem('varianteSelezionata'));
		    							
		    							dto.dipartimento = $('#dipartimento').val();
	    								dto.servizio = $('#servizio').val();
	    								dto.emailReferente = $('#emailReferente').val();
	    								dto.responsabileServizio = $('#responsabileServizio').val();
	    								dto.responsabilePianoTerritoriale = $('#responsabilePianoTerritoriale').val();
	    								dto.dirigenteDipartimento = $('#dirigenteDipartimento').val();
		    								
		    							setTimeout(function(){ 
		    							    appUtil.hideLoader();
		    							    $('#'+self.idDialog+'informazioniAggiuntiveCdu #chiudiBtn').click();
		    							    iziToast.show({
												title: 'OK',
												theme: 'dark',
												icon:'fa fa-check',
												message: "L'operazione di creazione del CDU sta impiegando pi&#249; tempo del previsto. Sar&#225; possibile effettuare il download accedendo alla sezione Anagrafica CDU",
												animateInside: false,
												position: 'topCenter',
											});
		    							}, 8000);
		    							pianoRegolatoreRest.downloadCdu(dto).then(function(response){
		    								appUtil.hideLoader();
		    								if (response !== null || response !== undefined) {
		    									const linkSource = 'data:application/msword;base64,'+response;
		    									const downloadLink = document.createElement('a');
		    									const fileName = "CDU.doc";
		    									downloadLink.href = linkSource;
		    									downloadLink.download = fileName;
		    									downloadLink.click();
		    								} else {
		    									iziToast.error({
		    										balloon: false,
		    										icon:'fa fa-times', 
		    										animateInside: false,
		    										theme: 'dark', 
		    										position: 'topCenter',
		    										title: 'Errore',
		    										message: 'Si &egrave; verificato un errore durante la fase di export',
		    									});
		    								}
		    							}, function(a,b,c){
		    								appUtil.hideLoader();
		    								console.log(a,b,c);
		    							});
		    						}
		    					});
			    			}
			    			
			    		});
			    	}, {width: 680, height: 550, resizable: false, modale: true, closable: true});
				} else {
					
					iziToast.error({
						balloon: false,
						icon:'fa fa-times', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
						title: 'Errore',
						message: '&Eacute; necessario selezionare un ambito di una variante ed acquisire le geometrie',
					});
				}
			});
			
			self.aggiungiEventoClickChiudiFinestra();
			self.aggiungiEventiAcquisizioneGeometria();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
		
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaCertificatoUrbanistico');
	}
	
	enableAllTheseDays(date) {
        var sdate = $.datepicker.formatDate( 'dd/mm/yy', date)
        if($.inArray(sdate, JSON.parse(sessionStorage.getItem('enableVariantiDayAppr'))) != -1) {
            return [true];
        }
        return [false];
    }
	
	aggiungiEventiAcquisizioneGeometria(){
		var self = this;
		$('#'+self.idDialog+' #selezionaSuMappaBtn').off('click').on('click', function(){
			self.riduciAdIconaTutteLeModali();
			appMappa.addRemoveLayer("u_cat_particelle", "Particelle", null, '', '');
			appMappa.addRemoveLayer("u_cat_fabbricati", "Fabbricati", null, '', '');
			new PaginaCertificatoUrbanisticoDaSelezioneSuMappaCtrl( self.callbackDiRitornoDaStrumentiAcquisizioneGeometria );
		});
		$('#'+self.idDialog+' #tracciaSuMappaBtn').off('click').on('click', function(){
			self.riduciAdIconaTutteLeModali();
			new PaginaCertificatoUrbanisticoDaTracciatoSuMappaCtrl( self.callbackDiRitornoDaStrumentiAcquisizioneGeometria );
		});
	}
	
	callbackDiRitornoDaStrumentiAcquisizioneGeometria(self){
	var modalitaAcquisizione = sessionStorage.getItem('modalitaAcquisizione');
		if (modalitaAcquisizione === 'selezione') {
				appMappa.removeLayer("u_cat_particelle");
				appMappa.removeLayer("u_cat_fabbricati")
		}
		
		var particelle = []
		
		var hmFoglioParticelle = new Object();
		$.each( JSON.parse(sessionStorage.getItem('geometriaPiano')), function( key, item ) {

			if (hmFoglioParticelle[item.foglio]!=null) {
                var mappale = hmFoglioParticelle[item.foglio]+item.mappale+", ";
                hmFoglioParticelle[item.foglio] = mappale;
			} else {
				hmFoglioParticelle[item.foglio]=item.mappale+", ";
			}
		});
		
		var formatParticelle = [];
		$.each( hmFoglioParticelle, function( key, item ) {
			formatParticelle.push({foglio:key,numero:item.slice(0,-2)});
		});
		
		var template = compilaTemplate('modaleCertificatoUrbanisticoSelezioneParticelle', {listaParticelle: formatParticelle});
		
		$("#selezioneGeometria").html(template);
		
		iziToast.show({
			title: 'OK',
			theme: 'dark',
			icon:'fa fa-check',
			message: 'Geometria acquisita.',
			animateInside: false,
			position: 'topCenter',
		});
	}
	
}

class PaginaStruttraPianoStatiInformativiCtrl extends BaseJsTreeModaleRicercaCtrl {
	constructor( ) {
		super('paginaStrutturaPianiInformativi','GESTIONE STRATI INFORMATIVI', 'modaleStrutturaPianiInformativi');
		this.inizializzaPagina();
		}
	
	inizializzaPagina(){
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.inizializzaBreadcrumb();
			
			appCatalogo.getStartMenu();
			appCatalogo.initializeMenu( "menu-content-variante", "addLayerCatalogoVariante" );
			
			Handlebars.registerHelper("ifEliminaGruppo", function(nomeGruppo, options) {
				if(nomeGruppo.indexOf('CDU') == -1) {
					return options.fn(this);
				} 
			});
			
			$.when(
				pianoRegolatoreRest.reperimentoCatalogoVariante()
			).done(function(catalogo){
				htmlGruppo = compilaTemplate("creazioneCatalogo", {result: catalogo.aaData});
				$("#catalogoVariante").append( htmlGruppo );
				appUtil.hideLoader();
			});

			$('#' + self.idDialog + ' #chiudiBtn').click(function(event) {
				$('#' + self.idDialog).dialog('close');
			});
			
			appUtil.reloadTooltips();
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});

		$( "#aggiungiGruppo" ).click(function() {
			self.apriModaleNuovoGruppoLayer();
		});
		sessionStorage.setItem('windowOpened','paginaStrutturaPianiInformativi');
	}

	apriModaleNuovoGruppoLayer(id){
		var self = this;

    	openStaticDetailViaHandlebars(self.idDialog+'gestioneNuovoGruppoLayer', 'modaleNuovoGruppoLayer', {}, 'GESTIONE GRUPPI STRATI INFORMATIVI', function(){
    		appUtil.reloadTooltips();
			
			$('#nomeGruppo').prop('readonly', true);
			
			$('#' + self.idDialog + 'gestioneNuovoGruppoLayer #codiceGruppo').on('change', function() {
				if($('option:selected', this).val() != '') {
					$('#nomeGruppo').val($('option:selected', this).val() + ' - ');
					$('#nomeGruppo').prop('readonly', false);
				} else {
					$('#nomeGruppo').val('');
					$('#nomeGruppo').prop('readonly', true);
				}
			})
			
			$('#'+self.idDialog+'gestioneNuovoGruppoLayer #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'gestioneNuovoGruppoLayer').dialog('close');
    		});
    		$('#'+self.idDialog+'gestioneNuovoGruppoLayer #salvaBtn').off('click').on('click', function(){
				var nomeGruppo = $('#nomeGruppo').val().split("- ");
				
				if($('#nomeGruppo').val() == '' || $('#codiceGruppo').val() == '') {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Devono essere compilati tutti i campi obbligatori!',
						animateInside: false,
						position: 'topCenter',
					});
				} else if(nomeGruppo.length > 1 && nomeGruppo[1].trim() == '') {
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Devi inserire un nome al gruppo!',
						animateInside: false,
						position: 'topCenter',
					});
				} else {
					appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
					
					if(nomeGruppo[0].trim() != $('#codiceGruppo option:selected').val()) {
						$('#nomeGruppo').val($('#codiceGruppo option:selected').val() + ' - ' + $('#nomeGruppo').val())
					}
					
					$.when(
						pianoRegolatoreRest.salvaNuovoGruppo($('#nomeGruppo').val())
					).done(function(risultato){
						if (risultato.message === 'Success') {
							iziToast.show({
								title: 'OK',
								theme: 'dark',
								icon:'fa fa-check',
								message: "Salvataggio del gruppo effettuato con successo",
								animateInside: false,
								position: 'topCenter',
							});
							$('#'+self.idDialog+'gestioneNuovoGruppoLayer').dialog('close');
							$.when(
								pianoRegolatoreRest.reperimentoCatalogoVariante()
							).done(function(catalogo){
								htmlGruppo = compilaTemplate("creazioneCatalogo", {result: catalogo.aaData});
								$("#catalogoVariante").html("");
								$("#catalogoVariante").append( htmlGruppo );
								appUtil.hideLoader();
							});
						} else {
							iziToast.error({
								title: 'Attenzione',
								theme: 'dark',
								icon:'fa fa-times',
								message: 'Si &egrave; verificato un errore durante il salvataggio.',
								animateInside: false,
								position: 'topCenter',
							});
							appUtil.hideLoader();
						}
					});

				}
    		});
    	}, {width: 500, height: 200, resizable: false, modale: true, closable: true});
	}

}

function addLayerCatalogoVariante(layerName, layerTitle, hrefDetail){
	
	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	$.when(
		pianoRegolatoreRest.reperimentoCatalogoVariante(),
		pianoRegolatoreRest.reperimentoColonneLayer(layerName)
	).done(function(gruppi, colonne){
		apriModaleAssociaLayer(layerName, layerTitle, hrefDetail, gruppi[0].aaData, colonne[0].aaData);
		appUtil.hideLoader();
	});
 
}

function apriModaleAssociaLayer(layerName, layerTitle, hrefDetail, listaGruppi, listaColonne) {
	var self = this;
	openStaticDetailViaHandlebars('gestioneAssociaLayer', 'modaleAssociaLayer', {listaGruppi: listaGruppi, listaColonne: listaColonne}, 'GESTIONE GRUPPI STRATI INFORMATIVI', function(){
		appUtil.reloadTooltips();
		$('#divColonneLayer').hide();
		
		$('#gruppoLayerSelezionato').change(function() {
        	if($('#gruppoLayerSelezionato option:selected').text().indexOf('CDU 06') > -1) {
                $('#divColonneLayer').show();
        	} else {
        		$('#divColonneLayer').hide();
        	}
        });
		
		$('#gestioneAssociaLayer #chiudiBtn').off('click').on('click', function(){
			$('#gestioneAssociaLayer').dialog('close');
		});
		$('#gestioneAssociaLayer #salvaBtn').off('click').on('click', function(){
			
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var idGruppo = $('#gruppoLayerSelezionato').val();
			var nomeColonnaLayer = $('#nomeColonnaLayer').val();

			var giaAssociato = findIfLayerAlreadyAssociated(listaGruppi, idGruppo, layerName);
			
			if (giaAssociato === false) {
				var layer = new Object();
				layer.idGruppo = idGruppo;
				layer.layerName = layerName;
				layer.layerTitle = layerTitle;
				layer.hrefDetail = hrefDetail;
				layer.sorgente = appCatalogo.sorgenteLayer(layerName);
				layer.nomeColonnaLayer = nomeColonnaLayer;
				$.when(
					pianoRegolatoreRest.salvaLayer(layer)
				).done(function(risultato){
					if (risultato.message === 'Success') {
						iziToast.show({
			    			title: 'OK',
			    			theme: 'dark',
			    			icon:'fa fa-check',
			    			message: "Salvataggio del gruppo effettuato con successo",
			    			animateInside: false,
			    			position: 'topCenter',
			    		});
						$('#gestioneAssociaLayer').dialog('close');
						$.when(
							pianoRegolatoreRest.reperimentoCatalogoVariante()
						).done(function(catalogo){
							htmlGruppo = compilaTemplate("creazioneCatalogo", {result: catalogo.aaData});
							$("#catalogoVariante").html("");
							$("#catalogoVariante").append( htmlGruppo );
							appUtil.hideLoader();
						});
					} else {
					iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon:'fa fa-times',
							message: 'Si &egrave; verificato un errore durante il salvataggio.',
							animateInside: false,
							position: 'topCenter',
						});
						appUtil.hideLoader();
					}
				});
			} else {
				iziToast.error({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-times',
					message: "Il layer scelto &egrave; gi&agrave; associato a questo gruppo",
					animateInside: false,
					position: 'topCenter',
				});
				appUtil.hideLoader();
			}
		});
	}, {width: 500, height: 300, resizable: false, modale: true, closable: true});
}

function findIfLayerAlreadyAssociated(listaGruppi, idGruppo, idLayer) {
	var gruppoTrovato;
	var giaAssociato = false;

	for(var i=0; i<listaGruppi.length; i++){
		if(listaGruppi[i].id === parseInt(idGruppo)){
			gruppoTrovato = listaGruppi[i];
			break;
		}
	}
	
	for(var i=0; i<gruppoTrovato.listCatalogoLayer.length; i++){
		if(gruppoTrovato.listCatalogoLayer[i].idLayer === idLayer){
			giaAssociato = true;
			break;
		}
	}
	
	return giaAssociato;
	
}

function deleteLayer(id, nomeLayer) {
	var self = this;
	var messaggioLayerAssociati = "Il layer &egrave; associato alle seguenti varianti:";
	var messaggioDiConferma = "Sei sicuro di voler procedere con la cancellazione del layer?"
	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	$.when(pianoRegolatoreRest.varianteByNomeLayer(nomeLayer)).done(function(response){
		if(response.success) {
			
			if(response.aaData.length != 0) {

				let listaLayer = compilaTemplate("listaVarianti", {listaVarianti: response.aaData, messaggioLayerAssociati: messaggioLayerAssociati, messaggioDiConferma: messaggioDiConferma});

				appUtil.hideLoader();

				appUtil.confirmOperation( function(){
					/** CONFERMA */
					appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
						
					$.each(response.aaData, function(index, value) {
						let cartografia = new Object();
						cartografia.idVariante = value.idVariante;
						cartografia.layers = [value.nomeLayer];
						$.when(pianoRegolatoreRest.cancellaCartografia(cartografia)).done(function(response) {
							if(response.message != 'Success') {
								iziToast.error({
									title: 'Attenzione',
									theme: 'dark',
									icon:'fa fa-times',
									message: 'Si &egrave; verificato un errore durante la cancellazione.',
									animateInside: false,
									position: 'topCenter',
								});
							}
						});
					});
		
					$.when(pianoRegolatoreRest.cancellaLayer(id)).done(function(response){
						if(response.message == 'Success') {
							iziToast.show({
								title: 'OK',
								theme: 'dark',
								icon:'fa fa-check',
								message: 'Il layer &egrave; stato rimosso con successo.',
								animateInside: false,
								position: 'topCenter',
							});
							
							$.when(pianoRegolatoreRest.reperimentoCatalogoVariante()).done(function(catalogo){
								htmlGruppo = compilaTemplate("creazioneCatalogo", {result: catalogo.aaData});
								$("#catalogoVariante").html("");
								$("#catalogoVariante").append( htmlGruppo );
								appUtil.hideLoader();
							});
						};
					});
				}, function(){
				//ANNULLA
				}, {}, listaLayer);

			} else {
				appUtil.hideLoader();

				appUtil.confirmOperation(function() {

					appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

					$.when(pianoRegolatoreRest.cancellaLayer(id)).done(function(response){
						if(response.message == 'Success') {
							iziToast.show({
								title: 'OK',
								theme: 'dark',
								icon:'fa fa-check',
								message: 'Il layer &egrave; stato rimosso con successo.',
								animateInside: false,
								position: 'topCenter',
							});
							$.when(pianoRegolatoreRest.reperimentoCatalogoVariante()).done(function(catalogo){
								htmlGruppo = compilaTemplate("creazioneCatalogo", {result: catalogo.aaData});
								$("#catalogoVariante").html("");
								$("#catalogoVariante").append( htmlGruppo );
								appUtil.hideLoader();
							});
						} else {
							iziToast.error({
								title: 'Attenzione',
								theme: 'dark',
								icon:'fa fa-times',
								message: 'Si &egrave; verificato un errore durante la cancellazione.',
								animateInside: false,
								position: 'topCenter',
							});
							appUtil.hideLoader();
						}
					});

				}, function() {

				}, {}, messaggioDiConferma)

			}
		} else {
			
			iziToast.error({
				title: 'Attenzione',
				theme: 'dark',
				icon:'fa fa-times',
				message: 'Si &egrave; verificato un errore durante la cancellazione.',
				animateInside: false,
				position: 'topCenter',
			});
			appUtil.hideLoader();

		}

	});
}

function deleteGruppo(id) {
	var self = this;
	appUtil.confirmOperation( function(){
		//CONFERMA
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(
			pianoRegolatoreRest.cancellaGruppo(id)
		).done(function(risultato){
			if (risultato.message === 'Success') {
				iziToast.show({
					title: 'OK',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'Il gruppo &egrave; stato rimosso con successo.',
					animateInside: false,
					position: 'topCenter',
				});
				$.when(
					pianoRegolatoreRest.reperimentoCatalogoVariante()
				).done(function(catalogo){
					htmlGruppo = compilaTemplate("creazioneCatalogo", {result: catalogo.aaData});
					$("#catalogoVariante").html("");
					$("#catalogoVariante").append( htmlGruppo );
					appUtil.hideLoader();
				});
			} else {
				iziToast.error({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-times',
					message: 'Si &egrave; verificato un errore durante la cancellazione.',
					animateInside: false,
					position: 'topCenter',
				});
				appUtil.hideLoader();
			}
		});
	}, function(){
		//ANNULLA
	}, {}, 'Sei sicuro di voler procedere con la cancellazione del layer?');
}

class PaginaInterrogazionePianoCtrl extends BaseModaleRicercaCtrl {
	constructor() {
		super('paginaInterrogazionePiano','INTERROGAZIONE PIANO', 'modaleInterrogazionePiano');
		//TODO
		this.datiMockCDU = [{
			id: 1,
			area: '23215.25',
			gruppo: 'DDP TAV6 - Assetto idrogeologico',
			nomeLayer: 'FATTIBILITA\' GEOLOGICA',
			codiceGeometria: '2b - Aree stabili tavolta con presenza di microrilievo.',
			numeroEntitaIntersecate: 4,
			areaIntersecata: '6531.11',
			percentualeAreaIntersecata: '28.13%'
		},{
			id: 2,
			area: '23215.25',
			gruppo: 'DDP TAV6 - Assetto idrogeologico',
			nomeLayer: 'FATTIBILITA\' GEOLOGICA',
			codiceGeometria: '2c - Zona a vulnerabilit&agrave; degli acquiferi medio-elevata',
			numeroEntitaIntersecate: 4,
			areaIntersecata: '11718.65',
			percentualeAreaIntersecata: '50,48%'
		}];
		
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;

		/**Inizializzo**/
		sessionStorage.removeItem('varianteSelezionata');
		sessionStorage.removeItem('geometriaPiano');
		
		/**Recupero dei dati di base della modale Certificato Urbanistico**/
		/**************************** TIPI TOPONIMI **************************/
		var hrefPRGAllVariantiUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/prgConsultazioneController/getAllVariantiByDate";
    	var urbamidResponse = appRest.restUrbamid(hrefPRGAllVariantiUrbamid, "POST", null);
		self.varianti = urbamidResponse.aaData;
		
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			
			/**GESTIONE DEL RADIO BUTTON PER LA SCELTA DELLA VARIANTE**/
			self.varianteDateSelected 	= null;
			self.enableVariantiDayAppr 	= [];
			self.hmVariantiDateAppr 	= new Object();
			$.each( self.varianti, function( keyVar, itemVar ) {
				if (itemVar.dataDelAppr!=null) {
					self.enableVariantiDayAppr.push(itemVar.dataDelAppr);
					self.hmVariantiDateAppr[itemVar.dataDelAppr] = itemVar;
				}
			});
			sessionStorage.setItem('enableVariantiDayAppr',JSON.stringify(self.enableVariantiDayAppr) );
			
			self.enableVariantiDayAdoz 	= [];
			self.hmVariantiDateAdoz		= new Object();
			$.each( self.varianti, function( keyVar, itemVar ) {
				if (itemVar.dataDelAdoz!=null && (itemVar.dataDelAppr==undefined || itemVar.dataDelAppr==null)) {
					self.enableVariantiDayAdoz.push(itemVar.dataDelAdoz);
					self.hmVariantiDateAdoz[itemVar.dataDelAdoz] = itemVar;
				}
			});
			sessionStorage.setItem('enableVariantiDayAdoz',JSON.stringify(self.enableVariantiDayAdoz) );
			
			$('input[type=radio][name=optradio]').change(function() {
			    if (this.value == 'vigente') {
			    	
			    	$("#varianteScelta").html("");
			    	$("#dataVigenteSel").attr('readonly','');
			    	var dates = JSON.parse(sessionStorage.getItem('enableVariantiDayAppr'));
			    	var sortedDates = dates.slice() // copy the array for keeping original array with order
			    	  					   .sort(function(a, b) {
			    		  							var af = a.split("/")
			    		  							var adf = new Date(af[2], af[1] - 1, af[0]);
			    		  							var bf = b.split("/")
			    		  							var bdf = new Date(bf[2], bf[1] - 1, bf[0]);
			    		  							return new Date(adf) - new Date(bdf);
			    								});
			    	self.variante = self.hmVariantiDateAppr[sortedDates.pop()];
			    	$("#varianteScelta").html(compilaTemplate('modaleCertificatoUrbanisticoVariante', self.variante));
			    	sessionStorage.setItem('varianteSelezionata', JSON.stringify(self.variante));
			    }
			    else if (this.value == 'salvaguardia') {
			    	
			    	$("#varianteScelta").html("");
			    	$("#dataVigenteSel").attr('readonly','');
			    	var dates = JSON.parse(sessionStorage.getItem('enableVariantiDayAdoz'));
			    	var sortedDates = dates.slice() // copy the array for keeping original array with order
			    	  .sort(function(a, b) {
			    		  var af = a.split("/")
			    		  var adf = new Date(af[2], af[1] - 1, af[0]);
			    		  var bf = b.split("/")
			    		  var bdf = new Date(bf[2], bf[1] - 1, bf[0]);
			    		  return new Date(adf) - new Date(bdf);
			    	});
			    	self.variante = self.hmVariantiDateAdoz[sortedDates.pop()];
			    	$("#varianteScelta").html(compilaTemplate('modaleCertificatoUrbanisticoVariante', self.variante));
			    	sessionStorage.setItem('varianteSelezionata', JSON.stringify(self.variante));
			    }
			    else if (this.value == 'datavigente') {
			    	$("#varianteScelta").html();
			    	if ($("#dataVigenteSel").attr("readonly")!=null) {
			    		
			    		$("#dataVigenteSel").datepicker({
							dateFormat: 'dd/mm/yy', 
							i18n:{
								  it:{
								   months:[
								    'Gennaio','Febbraio','Marzo','Aprile',
								    'Maggio','Giugno','Luglio','Agosto',
								    'Settembre','Ottobre','Novembre','Dicembre',
								   ],
								   dayOfWeek:[
								    "Lu", "Ma", "Me", "Gi", 
								    "Ve", "Sa", "Do",
								   ]
								  }},
							beforeShowDay: self.enableAllTheseDays,
							onSelect: function( selectedDate ) {
								self.varianteDateSelected = selectedDate;
								self.variante = self.hmVariantiDateAppr[self.varianteDateSelected];
								$("#varianteScelta").html(compilaTemplate('modaleCertificatoUrbanisticoVariante', self.variante));
								sessionStorage.setItem('varianteSelezionata', JSON.stringify(self.variante));
							}
			    		});
			    		$("#dataVigenteSel").removeAttr('readonly');	
			    	}
			    }
			});

			$('#'+ self.idDialog + ' #dettaglioPianoBtn').off('click').on('click', function(){
				if ( sessionStorage.getItem('varianteSelezionata')!=null && sessionStorage.getItem('geometriaPiano')!=null ) {
					/** INIZIALIZZAZIONI */
					self.nascondiPaginaRicerca();
					self.aggiungiEventiClickTornaIndietroARicerca();
					self.aggiornaBreadcrumb('dettaglio', 'Interrogazione Piano');
					self.inizializzaDatatableRicerca(self.datiMockCDU);
				} else {
					iziToast.error({
						balloon: false,
						icon:'fa fa-times', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
						title: 'Errore',
						message: '&Eacute; necessario selezionare un ambito di una variante ed acquisire le geometrie',
					});
				}
			});

			self.nascondiPaginaDettaglio();
			self.aggiungiEventoClickChiudiFinestra();
			self.aggiungiEventiAcquisizioneGeometria();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();

		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		sessionStorage.setItem('windowOpened','paginaInterrogazionePiano');
	}
	
	enableAllTheseDays(date) {
        var sdate = $.datepicker.formatDate( 'dd/mm/yy', date)
        if($.inArray(sdate, JSON.parse(sessionStorage.getItem('enableVariantiDayAppr'))) != -1) {
            return [true];
        }
        return [false];
	}

	inizializzaDatatableRicerca(data) {
		/** INIZIALIZZAZIONE */
		var self = this;
		$('#tabellaInterrogazionePiano').DataTable({
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
			processing: true,
			data: data,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(_d, _t, r) {return r.area + ' mq';}, orderable : true},
	        	{targets : 1, render: function(_d, _t, r) {return r.gruppo;}, orderable : true},
	        	{targets : 2, render: function(_d, _t, r) {return r.nomeLayer;}, orderable : true},
	        	{targets : 3, render: function(_d, _t, r) {return r.codiceGeometria;}, orderable : true},
	        	{targets : 4, render: function(_d, _t, r) {return r.numeroEntitaIntersecate;}, orderable : true},
	        	{targets : 5, render: function(_d, _t, r) {return r.areaIntersecata + ' mq';}, orderable : true},
	        	{targets : 6, render: function(_d, _t, r) {return r.percentualeAreaIntersecata;}, orderable : true},
	        	{targets : 7, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaInterrogazionePiano', {id: r.id});
	        		return templatePulsanti;
	        	}}
		   	],
	    	drawCallback: function( _settings ) {
	        	sessionStorage.setItem('dettaglio', JSON.stringify(data));
	        	self.aggiungiEventiAiPulsantiAzioneInterrogazionePiano();
	        }
		});
	}

	aggiungiEventiAiPulsantiAzioneInterrogazionePiano() {
		var self = this;
		$('#tabellaInterrogazionePiano button.localizza-btn').off('click').on('click', function(_event){
			self.getGeometry('POLYGON((15.5422473402916 38.1601601155248,15.5446505995689 38.159282772366,15.5615592451988 38.1791891149236,15.5624175520836 38.1832371972355,15.5610442610679 38.1842491826704,15.5570102187095 38.1778397041727,15.5502295943199 38.171294707527,15.5459380598961 38.164614156407,15.5422473402916 38.1601601155248))')
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
			(appMappa.maps).getView().fit(
			        feature.getGeometry().getExtent(), 
			        { size: (appMappa.maps).getSize() }
			);
			(appMappa.maps).getView().setZoom(18);
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

	aggiungiEventiAcquisizioneGeometria(){
		var self = this;
		$('#'+self.idDialog+' #selezionaSuMappaBtn').off('click').on('click', function(){
			// $("#selezioneGeometria").html("");
			self.riduciAdIconaTutteLeModali();
			appMappa.addRemoveLayer("u_cat_particelle", "Particelle", null, '', '');
			appMappa.addRemoveLayer("u_cat_fabbricati", "Fabbricati", null, '', '');
			new PaginaCertificatoUrbanisticoDaSelezioneSuMappaCtrl( self.callbackDiRitornoDaStrumentiAcquisizioneGeometria );
		});
		$('#'+self.idDialog+' #tracciaSuMappaBtn').off('click').on('click', function(){
			// $("#selezioneGeometria").html("");
			self.riduciAdIconaTutteLeModali();
			new PaginaCertificatoUrbanisticoDaTracciatoSuMappaCtrl( self.callbackDiRitornoDaStrumentiAcquisizioneGeometria );
		});
	}
	
	callbackDiRitornoDaStrumentiAcquisizioneGeometria(self){
		var modalitaAcquisizione = sessionStorage.getItem('modalitaAcquisizione');
		if (modalitaAcquisizione === 'selezione') {
			appMappa.removeLayer("u_cat_particelle");
			appMappa.removeLayer("u_cat_fabbricati")
		}
		
		var particelle = []
		
		var hmFoglioParticelle = new Object();
		$.each( JSON.parse(sessionStorage.getItem('geometriaPiano')), function( key, item ) {

			if (hmFoglioParticelle[item.foglio]!=null) {
                var mappale = hmFoglioParticelle[item.foglio]+item.mappale+", ";
                hmFoglioParticelle[item.foglio] = mappale;
			} else {
				hmFoglioParticelle[item.foglio]=item.mappale+", ";
			}
		});
		
		var formatParticelle = [];
		$.each( hmFoglioParticelle, function( key, item ) {
			formatParticelle.push({foglio:key,numero:item.slice(0,-2)});
		});
		
		var template = compilaTemplate('modaleCertificatoUrbanisticoSelezioneParticelle', {listaParticelle: formatParticelle});
		
		$("#selezioneGeometria").html(template);
		
		minifize('paginaInterrogazionePiano');	//TODO aggiunti controllo per capire se è già "ingrandita" la finestra
		iziToast.show({
			title: 'OK',
			theme: 'dark',
			icon:'fa fa-check',
			message: 'Geometria acquisita',
			animateInside: false,
			position: 'topCenter',
		});
	}
	
}

class PaginaAnagraficaCduCtrl extends BaseModaleRicercaCtrl {
	constructor( ) {
		super('paginaAnagraficaCdu','ANAGRAFICA CDU', 'modaleAnagraficaCdu');
		this.inizializzaPagina();
	}
	
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			
			/** INIZIALIZZAZIONI */
			$("#dataCreazione").datepicker({dateFormat: 'dd/mm/yy'});
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
		
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		
		$('#tabellaAnagraficaCdu').hide();

		$( "#ricercaBtnAnagraficaCdu" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			self.inizializzaDatatableRicerca();
		});

	}

	inizializzaDatatableRicerca(){
		var self = this;
		var protocollo = $('#protocolloAnagraficaCdu').val();
		var dataCreazione = $('#dataCreazione').val();
		var url = pianoRegolatoreRest.ricercaCdu(protocollo.trim(), dataCreazione.trim());
		$('#'+self.idDialog+' #tabellaAnagraficaCdu').DataTable( {
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
	        ajax: url,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets :  0, name: 'protocollo', render: function(d, t, r) {return r.protocollo;}, orderable : true},
	        	{targets :  1, name: 'dataCreazione', render: function(d, t, r) {return r.dataCreazione;}, orderable : true},
	        	{targets :  2, className: "text-right", orderable : false, render: function(d, t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaAnagraficaCdu', {id: r.protocollo});
	        		return templatePulsanti;
	        	}}
	        ],
	        drawCallback: function( settings ) {
				
				self.aggiungiEventiAiPulsantiAzioneAnagraficaCdu();
	        	$('#tabellaAnagraficaCdu').show();
	        	appUtil.hideLoader();
	        }
	    });
	}

	aggiungiEventiAiPulsantiAzioneAnagraficaCdu(){
		var self = this;
		$('#'+self.idDialog+' #tabellaAnagraficaCdu button.download-documento-btn').off('click').on('click', function(event){
			self.downloadDocumento($(this).data('id'));
        });
	}

	downloadDocumento(protocollo){
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		pianoRegolatoreRest.downloadCduByProtocollo(protocollo).then(function(response){
			appUtil.hideLoader();
			if (response !== null || response !== undefined || response !== '') {
				const linkSource = 'data:application/msword;base64,'+response;
				const downloadLink = document.createElement('a');
				const fileName = "CDU.doc";
				downloadLink.href = linkSource;
				downloadLink.download = fileName;
				downloadLink.click();
			} else {
				iziToast.error({
					balloon: false,
					icon:'fa fa-times', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
					title: 'Errore',
					message: 'Si &egrave; verificato un errore durante la fase di export',
				});
			}
		}, function(a,b,c){
			appUtil.hideLoader();
			console.log(a,b,c);
		});
	}
}