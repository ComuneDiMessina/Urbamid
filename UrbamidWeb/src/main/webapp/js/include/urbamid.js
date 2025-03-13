/*******************************************************************************************************************************************************/
/***************************************			FUNZIONALITA' URBAMID				****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

/**
 * @include /plugin/jsTree-3.3.8
 * @include catalogo.js
 * @include configuration.js
 * @include util.js
 * @include rest.js
 * @include mappa.js
 */

$(document).ready(function() {
	
	/** 4. LA FINESTRA DI TIMEOUT È UTILIZZATO PER VISUALIZZARE ALL'UTENTE CHE HA EFFETTUATO L'ACCESSO CHE LA SESSIONE STA PER SCADERE. */
	appUtil.initSessionTimeout(3600000,3660000);

	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
	if (typeof appGestMappe === "undefined" && typeof appGestProfile === "undefined" && typeof appCubotti === "undefined") {
		
		/**Quando ho recuperato le voci del menu di 2° e 3° Livello**/
		$.when( appUrbamid.start() ).then(function( x ) {

		});
		
		/**Aggiunta attributi per i GisTool**/
		$("#gisTool .ol-zoom-extent button").attr("title", "Centra mappa");
		$("#gisTool .ol-zoom-extent button").removeAttr("data-original-title");
		$("#gisTool .ol-geolocation button").attr("title", "Geolocalizza la tua posizione");
		$("#gisTool .ol-geolocation button").removeAttr("data-original-title");
		$("#gisTool .ol-measure-length button").attr("title", "Misura una linea");
		$("#gisTool .ol-measure-length button").removeAttr("data-original-title");
		$("#gisTool .ol-measure-area button").attr("title", "Misura un area");
		$("#gisTool .ol-measure-area button").removeAttr("data-original-title");
		
		$(".panel-heading").on('click', function(event){
			$(".form-search-panel.panel-collapse.collapse").removeClass("in");
			$("#resultRicercaParticella").hide();
			$("#resultRicercaParticella_info").hide();
        	$("#resultRicercaParticella_paginate").hide();
	    	$("#resultRicercaFoglio").hide();
	    	$("#resultRicercaFoglio_info").hide();
        	$("#resultRicercaFoglio_paginate").hide();
	    	$("#resultRicercaToponimo").hide();
	    	$("#resultRicercaToponimo_info").hide();
			$("#resultRicercaToponimo_paginate").hide();
	    	
		});
		
		$("a.first-level").on('click', function(event){
			$(this).parent("div.subnav-second").toggleClass("hidden");
		});
		$("a.second-level").on('click', function(event){
			$(this).parent("div.subnav-third").toggleClass("hidden");
		});
		
		$("#ricercaParticella").on('click', function(event){
			if ($("#ricercaFoglioForm").hasClass( "show" )) $("#ricercaFoglioForm").removeClass( "show" );
			if ($("#ricercaToponimoForm").hasClass( "show" )) $("#ricercaToponimoForm").removeClass( "show" );
		});
		
		$("#ricercaFoglio").on('click', function(event){
			if ($("#ricercaParticelleForm").hasClass( "show" )) $("#ricercaParticelleForm").removeClass( "show" );
			if ($("#ricercaToponimoForm").hasClass( "show" )) $("#ricercaToponimoForm").removeClass( "show" );
		});
		
		$("#ricercaToponimo").on('click', function(event){
			if ($("#ricercaParticelleForm").hasClass( "show" )) $("#ricercaParticelleForm").removeClass( "show" );
			if ($("#ricercaFoglioForm").hasClass( "show" )) $("#ricercaFoglioForm").removeClass( "show" );
		});
		
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	} else {
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	}
});

/** 
 * 
 * @param value
 * @returns 
 */
function ordinaPer(value){
    return function(a,b){
       if(a[value] > b[value]){
           return 1;
       }else if(a[value] < b[value]){
           return -1;
       }
       return 0;
    }
}

/**
 * Questa funzione, restituisce l'oggetto @viario in cui sono memorizzati la latitudine, la longitudine, il sistema di riferimento 4326 e lo zoom
 * per eseguire lo zoom sulla mappa
 * @param {*} denominazione, corrisponde al nome della strada
 * @param {*} civico
 * @returns viario
 * */
function viario(denominazione, civico) {
	var viario = null;
	$.ajax({
		type : "GET",
		processData : false,
		dataType : 'json',
		contentType : 'application/json; charset=UTF-8',
		url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + "/urbamid/toponomastica/viario/strade?search=" + denominazione,
		success : function(dataToponimo) {
			/** VERIFICO SE IL CIVICO E' DIVERSO DA NULL O UNDEFINED */
			if (dataToponimo==null || dataToponimo.payload==null || dataToponimo.payload[0]==null || dataToponimo.payload[0].id==null){
			
				/** MESSAGGIO D'ERRORE */
				iziToast.error({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-times',
					message: 'Non &#232; stato trovato nessun toponimo "'+denominazione+'" ',
					animateInside: false,
					position: 'topCenter',
				});
			} else {
				if(!civico.trim()) {
				
					/** CREO L'OGGETTO NEL CASO IN CUI IL CIVICO E' UNDEFINED O NULL E PRENDO COME RIFERIMENTO
					 *  LA LATIDUINE, LA LONGITUDINE E IL SISTEMA DI RIFERIMENTO DEL TOPONIMO
					*/
					iziToast.show({
						balloon: false,
						icon:'fa fa-info-circle', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
					    title: 'Errore',
					    message: "E' stato ricercato il toponimo senza civico",
					});
					viario = { lon: dataToponimo.payload[0].lon, lat: dataToponimo.payload[0].lat, sRef: dataToponimo.payload[0].sRef, zoom: 17 };
				} else {
					/** EFFETTUO LA CHIAMATA AL SERVER */
					$.ajax({
						type : "GET",
						processData : false,
						dataType : 'json',
						contentType : 'application/json; charset=UTF-8',
						url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + "/urbamid/toponomastica/viario/strade/"+ dataToponimo.payload[0].id +"/numeriCivici",
						success : function(dataCivico) {
							/** SCANSIONO TUTTI I RISULTATI AFFINCHE' IL NUMERO RECUPERATO DAL DB, CORRISPONDE AL NUMERO PASSATO IN INPUT */
							viario = null;
							if (dataCivico==null || dataCivico.payload==null || dataCivico.payload[0]==null) {
								iziToast.show({
									balloon: false,
									icon:'fa fa-info-circle', 
									animateInside: false,
									theme: 'dark', 
									position: 'topCenter',
								    title: 'Errore',
								    message: "Il toponimo '"+denominazione+"' &#232; senza civici",
								});
								viario = { lon: dataToponimo.payload[0].lon, lat: dataToponimo.payload[0].lat, sRef: dataToponimo.payload[0].sRef, zoom: 17 };
							} else {
								$.each(dataCivico.payload, function(index, value) {
									if(civico == value.num) {
										/** CREO L'OGGETTO, PRENDENDO COME DATI LA LATITUDINE, LA LONGITUDINE E
										 * 	IL SISTEMA DI RIFERIMENTO DEL CIVICO
										 */
										viario = { lon: value.lon, lat: value.lat, sRef: value.sRef, zoom: 17 };
									}
								})
								if (viario==null) {
									iziToast.error({
										title: 'Attenzione',
										theme: 'dark',
										icon:'fa fa-times',
										message: 'Il civico "'+civico+'" non &#232; tra i civici presenti per il toponimo "'+denominazione+'"',
										animateInside: false,
										position: 'topCenter',
									});
								}
							}
						},
						error: function() {
							/** MESSAGGIO D'ERRORE */
							iziToast.error({
								title: 'Attenzione',
								theme: 'dark',
								icon:'fa fa-times',
								message: 'Errore nel recupero delle informazioni su mappa',
								animateInside: false,
								position: 'topCenter',
							});
						},
						cache : true,
						async: false
					});
				}
			}
		},
		error : function() {
			/** MESSAGGIO D'ERRORE */
			iziToast.error({
				title: 'Attenzione',
				theme: 'dark',
				icon:'fa fa-times',
				message: 'Nessun toponimo ricercato',
				animateInside: false,
				position: 'topCenter',
			});
		},
		cache : true,
		async: false
	});
	/** RITORNO L'OGGETTO */
	return viario;
} 

appUrbamid = {
		
	mappeList						: [],										// lista delle mappe disponibili

	layerAddedMap					: [],										// lista, 	LAYER AGGIUNTI ALLA MAPPA
	defaultLayerAddedView			: [],										// lista, 	NOMI DEI LAYER PRESENTI IN DATI DI BASE (SONO I LAYER DELLA MAPPA)
	defaultLayerAddedViewSelAll		: false,									// boolean, INDICA SE STO VEDENDO TUTTI I LAYER DI BASE  
	layerAddedView					: [],										// lista, 	NOMI DEI LAYER PRESENTI IN DATI CATALOGO (SONO I LAYER AGGIUNTI DALL'UTENTE)
	layerAddedViewVariante			: [],										// lista, 	NOMI DEI LAYER PRESENTI NELLA TAVOLA CHE VIENE CREATA DALLA VARIANTE DEL PRG
	layerAddedViewSelAll			: false,									// TODO:non funziona boolean, INDICA SE STO VEDENDO TUTTI I LAYER DI BASE
	errorDefaultLayer				: [],										// Layer che sono cambiati e non piu presenti
	layerMap						: [],										// lista, LAYER AGGIUNTI ALLA MAPPA
	layerSeleDeselMap				: [],										// lista, LAYER DESELEZIONATI DALLA MAPPA
	layerOrderMap					: 0,										// ordine di attivazione dei layer
	layerOrderAddMap				: [],										// lista, dell'ordine di attivazione
	gruppiVisualizzatore			: [],										// lista, GRUPPI CON I RELATIVI LAYER DEL VISUALIZZATORE

	titleLayerList					: [],
	
	isServiceGis					: false,
	serviceGis                      : null,
	
	topLeftMapAction				: [243,284,325,366,407,457],
	
	elencoLGeL						: [],
	
	authority						: "",
	mappaApplicata					: "",
	
	/**
	 * La function recupera le informazioni utili dalla url per caricare la pagina di urbamid con mappa.
	 * La pagina e' composta da:
	 * 		1. IMPOSTAZIONI GRAFICHE
	 * 		2. RECUPERO IL CATALOGO
	 * 		3. CREO MAPPA E IMPOSTO IL GISTOOL
	 * 		4. RECUPERO LE RICERCHE
	 * 		5. RECUPERO DELLE MAPPE
	 *  	6. CARICO IL MENU
	 *  	7. IMPOSTAZIONE LAYER DI DEFAULT
	 *  	8. IMPOSTAZIONE URL PANNELLO DI CONTROLLO
	 *  
	 * @param
	 * @return
	 */
	start : function (){
		
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

		/************************************************************************************************************************/
		/*********************** SERVIZI GISTOOL ********************************************************************* INIZIO ***/
		urlParams = appConfig.getUrlVars();
		appUrbamid.authority = urlParams["authority"];
		if (urlParams["codeMappa"].indexOf("#")!=-1) urlParams["codeMappa"] = urlParams["codeMappa"].slice(0,-1);
		if ( urlParams["data"]!=null ) {  
			appUrbamid.isServiceGis = true;
			urlParams["authority"] = "ROLE_ACCESSO_WEBGIS";
			urlParams["codeMappa"] = "SERVICE_GIS";
			/**1. impostazione del pannello di controllo**/
			$("#backHome,.home-link").attr("href", appUtil.getUrbamidOrigin() + "/web/urbamid");
			$("#box-user").hide();
			$("#controlPanel").hide();
			$(".ol-maps").hide();
			$("#backHome").hide();
			$("#myNavbar").hide();
			/**2. recupero dei parametri ricevuti da client**/
			jsonString 					= atob( urlParams["data"] );
			appUrbamid.serviceGis		= jsonString!="" ? JSON.parse( jsonString ) : null;
		}
		/*********************** SERVIZI GISTOOL ********************************************************************* FINE   ***/
		/************************************************************************************************************************/
		
		/**RECUPERO IL DETTAGLIO DELLA MAPPA**/
		hrefUrbamid		= appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname +(appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
								appConfig.urbamidService + appConfig.urbamidMenuFunzionalita+"?authority="+urlParams["authority"];
		urbamidMenuResponse = appRest.restUrbamid(hrefUrbamid, "GET", null);
		
		if(urbamidMenuResponse.success && urbamidMenuResponse.aaData!=null && urbamidMenuResponse.aaData.menu!=null){
			
			var menus = JSON.parse(urbamidMenuResponse.aaData.menu) || {};		//get menu from DB
			$("#myNav").html( compilaTemplate("menusTemplate", menus) );
			
			if (menus.data.length==0) 
				$("#myNavbar").addClass("hidden");
			else 
				$("#myNavbar").removeClass("hidden");
				
			//catasto	
			if(typeof(RicercaDatiPageCtrl)  === 'function') new RicercaDatiPageCtrl();
			if(typeof(GestioneDatiPageCtrl) === 'function') new GestioneDatiPageCtrl();
			if(typeof(CartografiaPageCtrl)  === 'function') new CartografiaPageCtrl();
			//piano regolatore
			if(typeof(PianoRegolatorePageCtrl)  === 'function') new PianoRegolatorePageCtrl();
			//toponomastica
			if(typeof(ToponomasticaPageCtrl)  === 'function') new ToponomasticaPageCtrl();
			//editingLayer
			if(typeof(LayerPageCtrl)  === 'function') new LayerPageCtrl();
		} else {
			
			$("#myNavbar").css("cssText", "display:none!important");
			$("#mes-user").css("cssText", "display:none!important");
			$("#backHome").css("cssText", "display:none!important");
		}
		
		if (urlParams["preview"]!=null && urlParams["preview"]=="true") {
			$("#banner_preview").show();
		} else {
			$("#banner_preview").hide();
		}
		
		/************************	CARICO IL MENU	    				*********************							FINE ***/
		/*************************************************************************************************************************/
		
		/**Recupero informazioni utili dalla url per lo start della mappa di urbamid**/
		
		if ( urlParams["codeMappa"]==null || urlParams["codeMappa"]=="" )   
			urlParams["codeMappa"] = "DEFAULT";
		/**Recupero il dettaglio della mappa**/
		hrefUrbamid = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappa+"?codeMappa="+urlParams["codeMappa"];
		urbamidResponse = appRest.restUrbamid(hrefUrbamid, "GET", null);
		
		
		if (urbamidResponse.success) {
			
			/*************************************************************************************************************************/
			/*** 1 ******************	IMPOSTAZIONE GRAFICHE			    *********************							INIZIO ***/
			$("#title-map").html( urbamidResponse.aaData.title );
			appUrbamid.enableVisualzzatore();
			/*** 1 ******************	IMPOSTAZIONE GRAFICHE			    *********************							FINE ***/
			/*************************************************************************************************************************/
			
			indexTopLeftMapAction = 0;
			/*************************************************************************************************************************/
			/*** 2 ******************	RECUPERO DELLE MAPPE	    		*********************							INIZIO ***/
			if (!appUrbamid.isServiceGis && 
					( urlParams["codeMappa"]!=null && urlParams["codeMappa"]!="" && urlParams["codeMappa"]=="WEBGIS" ) ) {
				
				hrefUrbamidMappeTemi = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappeTemi;
				urbamidMappeTemiResponse = appRest.restUrbamid(hrefUrbamidMappeTemi, "POST", null);
				appUrbamid.temiMappeList = urbamidMappeTemiResponse.aaData;
				var temiHm = [];
				$.each( appUrbamid.temiMappeList, function( keyTema, valueTema ) {
					if( temiHm[valueTema.idTema]!=null ) {
						if(valueTema.stato == 'P') {
							valueMappa = {'id':valueTema.idMappa,'title':valueTema.nomeMappa, 'codeMappa':valueTema.codeMappa};
							(temiHm[valueTema.idTema].mappe).push( valueMappa );
							temiHm[valueTema.idTema].mappe.sort(ordinaPer("title"));
						}
					} else {
						temiHm[valueTema.idTema] = {'id':valueTema.idTema, 'nomeTema':valueTema.nomeTema, 'mappe':[], 'ordinamento': valueTema.ordinamento};
						if(valueTema.stato == 'P') {
							valueMappa = {'id':valueTema.idMappa,'title':valueTema.nomeMappa, 'codeMappa':valueTema.codeMappa};
							(temiHm[valueTema.idTema].mappe).push( valueMappa );
							temiHm[valueTema.idTema].mappe.sort(ordinaPer("title"));
						}
					}					
				});
			    var elencoMappe = {};
			    elencoMappe.temiHm = temiHm.sort(ordinaPer("nomeTema"));
				$("#elenco-mappe-content").html( compilaTemplate("elencoMappeTemplate", elencoMappe) );
				$(".ol-maps").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
				indexTopLeftMapAction++;
				
				/**Impostazioni grafiche**/
				$("#dropdownMenu1").hide();
				$("#dd-info").hide();
				$("#dd-benvenuto").hide();
				$("#dd-no-webgis").hide();	
			} else {
				$(".ol-maps").css( 'display', 'none' );
			}
			/*** 2 ******************	RECUPERO DELLE MAPPE	    		*********************							FINE ***/
			/*************************************************************************************************************************/
			
			/*************************************************************************************************************************/
			/*** 3 ******************	RECUPERO IL CATALOGO			    *********************							INIZIO ***/
			if(urbamidResponse.aaData.showCatalog) {
				appConfig.workspaceGeoserver = urbamidResponse.aaData.catalog || appConfig.workspaceGeoserver;
				appCatalogo.getStartMenu();
				$(".ol-layers").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
				indexTopLeftMapAction++;
			} else {
				$(".ol-layers").css('display', 'none');
			}
			
			/*** 3 ******************	RECUPERO IL CATALOGO			    *********************							FINE ***/
			/*************************************************************************************************************************/
			
			
			/*************************************************************************************************************************/
			/*** 4 ******************	CREO MAPPA E IMPOSTO IL GISTOOL	    *********************							INIZIO ***/
			idMappa = urbamidResponse.aaData.id;
			hrefUrbamidMappaTool = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappaTool+"?idMappa="+idMappa;
			urbamidMappaToolResponse = appRest.restUrbamid(hrefUrbamidMappaTool, "GET", null);
			if (urbamidMappaToolResponse.success) {
				appMappa.createMappa(urbamidMappaToolResponse.aaData);					
			}
			if (urbamidMappaToolResponse.aaData.length>0) {
				$(".ol-gistool").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
				indexTopLeftMapAction++;
			} else {
				$(".ol-gistool").css( 'display', 'none' );
			}
			/*** 4 ******************	CREO MAPPA E IMPOSTO IL GISTOOL	    *********************							  FINE ***/
			/*************************************************************************************************************************/
			

			/*************************************************************************************************************************/
			/*** 5 ******************	RECUPERO LE RICERCHE	    		*********************							INIZIO ***/
			if (!appUrbamid.isServiceGis) {
				idMappa = urbamidResponse.aaData.id;
				hrefUrbamidMappaRicerche = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappaRicerca+"?idMappa="+idMappa;
				urbamidMappaRicercaResponse = appRest.restUrbamid(hrefUrbamidMappaRicerche, "GET", null);
				if (urbamidMappaRicercaResponse.success) {
					if ( urbamidMappaRicercaResponse.aaData.length>0 ) {
						$.each( urbamidMappaRicercaResponse.aaData, function( keySearch, valueSearch ) {
						    var ricerca = {};		//get ricerca from DB
							$("#"+valueSearch.name).html( compilaTemplate(valueSearch.idHandle, ricerca) );
						});				
						$(".ol-searchtool").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
						indexTopLeftMapAction++;
					} else {
						$(".ol-searchtool").css( 'display', 'none' );
					}
				}
			}
			/*** 5 ******************	RECUPERO LE RICERCHE	    		*********************							FINE ***/
			/*************************************************************************************************************************/
			
			
			/*************************************************************************************************************************/
			/*** 6 ******************	IMPOSTO LA STAMPA	    		 ************************							INIZIO ***/
			$(".ol-printtool").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
			indexTopLeftMapAction++;
			/*** 6 ******************	IMPOSTO LA STAMPA	   			 **************************							  FINE ***/
			/*************************************************************************************************************************/
			
			
			/*************************************************************************************************************************/
			/*** 7 ******************	IMPOSTO INFO LAYER	    		 ************************							INIZIO ***/
			$(".ol-infotool").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
			indexTopLeftMapAction++;
			/*** 7 ******************	IMPOSTO INFO LAYER	   			 **************************							  FINE ***/
			/*************************************************************************************************************************/
			
			
			/*************************************************************************************************************************/
			/*** 8 ******************	IMPOSTAZIONE LAYER DI DEFAULT    	********************							INIZIO ***/
			hrefMappaLayerUrbamid = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappeLayer;
			var myMappa=new getUMappa();
			myMappa.id= urbamidResponse.aaData.id;
			var urbamidMappaLayerResponse = appRest.restSimplePost(hrefMappaLayerUrbamid, myMappa);
			gruppiVisualizzatore = urbamidMappaLayerResponse.aaData;

			layerToEnable 			= [];
			layers                	= [];
			/** 8.1 AGGIUNGO I GRUPPI **/
			$.each( urbamidMappaLayerResponse.aaData, function( keyGruppo, gruppoLayers ) {
				
				gruppo = {'idGruppo':appUtil.generateId(keyGruppo), 'nomeGruppo':keyGruppo};
				htmlGruppo = compilaTemplate("contGruppo", gruppo);
				$("#boxVisualizzatoreMappa").append( htmlGruppo );
			});
			gruppo = {'idGruppo':'Dati_Catalogo', 'nomeGruppo':'Dati Catalogo'};
			htmlGruppo = compilaTemplate("contGruppo", gruppo);
			$("#boxVisualizzatoreMappa").append( htmlGruppo );

			$('#visualizzatoreDatiBaseDati_Catalogo_chk').hide();

			var countLayer = 0;
			/** 8.2 AGGIUNGO I LAYER DEI GRUPPI **/
			$.each( urbamidMappaLayerResponse.aaData, function( keyGruppo, gruppoLayers ) {
				if (gruppoLayers.length>0) {
					$("#"+appUtil.generateId(keyGruppo)+"-layer").html("");
				}
				$.each( gruppoLayers, function( key, valueLayer ) {
					layerName		= valueLayer.layerName || valueLayer.nomeLayer;
					/**Aggiungo il tag dell'item legenda layer**/
					itemViewerId = appUtil.generateId(layerName+'_'+countLayer);
					defaultLayerHtml ="<div id=\"itemViewer_"+itemViewerId+"\" class='itemViewerContainer'></div>";
					$("#"+appUtil.generateId(keyGruppo)+"-layer").append( defaultLayerHtml );
					appUrbamid.defaultLayerAddedView.push(layerName);
					valueLayer.itemViewerId = itemViewerId
					valueLayer.itemCheckId = itemViewerId
					layers				.push( valueLayer);
					countLayer++;
				});
			});

			/** 8.3 VALORIZZO TUTTI I LAYER**/
			name			= "";
			hrefLegend 		= "";
			href 			= "";
			$.each( layers, function( keyLayer, valueLayer ) {

				layerName		= valueLayer.nomeLayer || valueLayer.layerName;
				itemViewerId 	= valueLayer.itemViewerId;
				itemCheckId 	= valueLayer.itemCheckId; /* appUtil.generateId(itemViewerId); */ 
				title			= "";
				opacity			= valueLayer.trasparenza;
				opacityPercent	= (valueLayer.trasparenza*10);
				hrefLegend		= "";
				idParent		= appUtil.generateId(valueLayer.idParent);

				isValidLay 		= true;
				if ( appUtil.generateId(valueLayer.idParent).indexOf("DATI_DI_BASE")!=0 ) {
					name			= "";
					hrefLegend 		= appUtil.getOrigin() + appConfig.geoserverService + appConfig.wmsService + "?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER="+appConfig.workspaceGeoserver+":"+layerName;
				
					href = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "layers/"+layerName+".json";
					responseTmp = appRest.restGeoserver ( href );
					if( responseTmp!=null && responseTmp.layer!=null && responseTmp.layer!=undefined) {
						href = responseTmp.layer.resource.href;
						responseLayerDetail = appRest.restGeoserver ( href );
						title = ((responseLayerDetail.featureType!=null && responseLayerDetail.featureType.title!=undefined) ? responseLayerDetail.featureType.title : 
									(responseLayerDetail.coverage!=null && responseLayerDetail.coverage.title!=undefined ? responseLayerDetail.coverage.title : 'Livello senza nome'));
						appMappa.caricaFieldsFromResponse( responseTmp );
					} else {
						skipItem = true;
						isValidLay = false;
						appUrbamid.errorDefaultLayer.push( layerName );
					}
				} else {
					title = (valueLayer.titleLayer).replace(/'/g, "");
				}
				var itemViewer = {"title": title, "layerName":layerName,"hrefLegend":hrefLegend,"visibilita":false, "itemCheckId":itemCheckId, "opacity":opacity, "opacityPercent":opacityPercent, idParent: idParent };
				$("#itemViewer_"+itemViewerId).html( compilaTemplate("itemViewer", itemViewer) );
				if (valueLayer.enabled || valueLayer.abilitato && isValidLay)
					layerToEnable.push(itemViewer);

			});

			/** 8.4 VISUALIZZO POPUP CON MESSAGGIO DI ERRORE. IL MESSAGGIO RIPORTERA I LAYER CHE NON SI POSSONO CARICARE **/
			if ( appUrbamid.errorDefaultLayer!=null && appUrbamid.errorDefaultLayer.length>0 ) {
				var messageErrorDefaultLayer = "Non &eacute; stato possibile caricari i seguenti dati di base:<br>";
				$.each( appUrbamid.errorDefaultLayer, function( keyErrorDefaultLayer, valueErrorDefaultLayer ) {
					messageErrorDefaultLayer+= "&nbsp;	&nbsp;	&raquo;	"+valueErrorDefaultLayer+"<br>"
				});
				appUtil.showMessageAlertApplication(messageErrorDefaultLayer, 'Informazione', true, false, false, null, null);
			};
			
			/** 8.5 APPLICO I LAYER DI DEFAULT ABILITATI **/
			$.each( layerToEnable, function( keyLayerToEnable, valueLayerToEnable ) {
				$( "#"+valueLayerToEnable.itemCheckId+"_chk" ).prop('checked', true);
				appMappa.addLayerMap( valueLayerToEnable.layerName, valueLayerToEnable.title, valueLayerToEnable, valueLayerToEnable.idParent, valueLayerToEnable.itemCheckId );
			});


			/** 8.7 APRO IL GRUPPO DATI CATALOGO E IMPOSTO LA SCRITTA */
			if ( $("#visualizzatoreDatiBaseDati_Catalogo").attr("aria-expanded")=="false" )
				$('#Dati_Catalogo-layer').html('<span>Seleziona livello dal catalogo</span>')

			/*** 8 ******************	IMPOSTAZIONE LAYER DI DEFAULT    	********************							FINE ***/
			/*************************************************************************************************************************/
			
			
			
			/*************************************************************************************************************************/
			/*** 9 ******************	SOLO PER SERVIZI GIS - IMPOSTAZIONE DELLA TABELLA**********							INIZIO ***/
			if (appUrbamid.isServiceGis) {
				
				/**Apertura della finestra modale dove saranno presenti le particelle selezionate in mappa**/
				var datiEstrazione = {};
			    openStaticDetailViaHTML(
			    						"finestraEstrazioneDati", 
			    						compilaTemplate("modServiceGis",  datiEstrazione), 
			    						"Elementi selezionati", 
			    						null, 
			    						{width: 650, height: 200, resizable: true,closable: false, position: { my: "bottom", at: "left bottom" }});
			    appMappa.abilitaSelectServiceGis("tabellaRisultatiServiceGis");
			    
			    /**Gestione dell'invio dei dati selezionati**/
			    $("#inviaBtn").on('click', function(event){
			    	arrayJson = [];
			    	$.each( appConfig.listFeatureAdded, function( keyFeature, valueFeature ) {
			    		hmValue = valueFeature.I;
			    		
			    		arrayJson.push( {
				   						 "comune":hmValue.codice_com,
				   						 "Sezione":hmValue.sviluppo,
				   						 "Foglio":hmValue.foglio,
				   						 "Numero":hmValue.mappale,
				   						 "Subalterno":"",
				   						 "Categoria":"",
				   						 "Classe":"",
				   						 "Consistenza":"",
				   						 "Ubicazione": getUbis(hmValue)
				   		});				    					    		
			    	});
			    	
			    	appUrbamid.serviceGis.cbck += "?chiaveSessione=" +appUrbamid.serviceGis.chiaveSessione + 
								    	"&immobili="+btoa( JSON.stringify(arrayJson) );
			    	window.location.replace( appUrbamid.serviceGis.cbck );
			    });
			    /**Gestione dell'invio dei dati selezionati**/
			    $("#backBtn").on('click', function(event){
			    	arrayJson = [];
			    	appUrbamid.serviceGis.cbck += "?chiaveSessione=" +appUrbamid.serviceGis.chiaveSessione + 
								    	"&immobili="+btoa( JSON.stringify(arrayJson) );
			    	window.location.replace( appUrbamid.serviceGis.cbck );
			    });
			    
			}
			/*** 9 ******************	SOLO PER SERVIZI GIS - IMPOSTAZIONE DELLA TABELLA**********							FINE ***/
			/*************************************************************************************************************************/

			/*************************************************************************************************************************/
			/*** 10 ******************	IMPOSTAZIONE GRAFICHE			    *********************							INIZIO ***/
			/**Inizializzazione pagina con zoom nel punto passato come input**/
			if (appUrbamid.isServiceGis) {
				if ( appUrbamid.serviceGis!=null && appUrbamid.serviceGis.tipoRicerca!=null ) {
					//Set del punto di zoom iniziale recuperato dai data
					if ( appUrbamid.serviceGis.tipoRicerca.indexOf("viario")!=-1 ) {
						if(appUrbamid.serviceGis.viario.toponimo != null) {
							var viarioObject = viario(appUrbamid.serviceGis.viario.toponimo, appUrbamid.serviceGis.viario.numcivico);
							if(viarioObject != null) {
								var newCenter = ol.proj.transform([viarioObject.lon, viarioObject.lat], "EPSG:"+viarioObject.sRef, 'EPSG:3857' );
								if(appUrbamid.serviceGis.viario.numcivico != null) {
									var marker = new ol.Feature(new ol.geom.Point([newCenter[0], newCenter[1]]))
									
									drawLayerSource.clear();
									drawLayerSource.addFeature(marker);
									(appMappa.maps).getView().fit(
										marker.getGeometry().getExtent(), 
										{ size: (appMappa.maps).getSize() }
									);
									(appMappa.maps).getView().setZoom(viarioObject.zoom);	
								} else {
									setTimeout(function(){
										(appMappa.maps).getView().animate({
											zoom: 16,
											center: newCenter,
											duration: 2000
										});	
									}, 2000);
								}
							}
						}
					} else if ( appUrbamid.serviceGis.tipoRicerca.indexOf("coord")!=-1 ) {
						var newCenter = ol.proj.transform(
												[appUrbamid.serviceGis.coord.longitudine, appUrbamid.serviceGis.coord.latitudine], 
												"EPSG:"+(appUrbamid.serviceGis.coord.sisRef!=null && appUrbamid.serviceGis.coord.sisRef!="" && appUrbamid.serviceGis.coord.sisRef!="0" ? appUrbamid.serviceGis.coord.sisRef : "4326"), 
												'EPSG:3857' );
						setTimeout(function(){
							(appMappa.maps).getView().animate({
								zoom: 16,
								center: newCenter,
								duration: 2000
							});	
						}, 2000);
					} else if ( appUrbamid.serviceGis.tipoRicerca.indexOf("catasto")!=-1 ) {
						
					}
				}
				$("#myNavbar").css("cssText", "display:none!important");
				$("#mes-user").css("cssText", "display:none!important");
			} else {
				zoomMap = urbamidResponse.aaData.zoom;
				centerMap = [1731514.79, 4608612.20],
				(appMappa.maps).getView().animate({
					zoom: zoomMap,
					center: centerMap,
					duration: 2000
				});
			}
			/*** 10 ******************	IMPOSTAZIONE GRAFICHE			    *********************							FINE ***/
			/*************************************************************************************************************************/
			appUrbamid.elencoLGeL = layers;
			appUtil.reloadTooltips();
		} else {
			/** Dalla url non ho recuperato tutte le informazioni necessarie **/
			/** TOOL MAPP**/
			$("#mappeBtn").attr("disabled","");
			$("#catalogoBtn").attr("disabled","");
			$("#gisToolContainerBtn").attr("disabled","");
			$("#searchToolContainerBtn").attr("disabled","");
			$("#infoToolContainerBtn").attr("disabled","");
			/** MAPPA **/
			tools = {};
			appMappa.createMappa(tools);
			/** VISUALIZZATORE**/
			$("#visualizzatore").hide();
			/** MENU**/
			$("#myNavbar").hide();
			/** MESSAGGIO DI ERRORE**/
			appUtil.showMessageAlertApplication('Ci sono stati problemi nel recuperare le informazioni per la mappa', 'Attenzione', false, true, false, null, null);
		}
		
	},

	/**
	 * La function recupera le informazioni utili dalla url per caricare la pagina di urbamid con mappa.
	 * La pagina e' composta da:
	 * 		1. RECUPERO IL CATALOGO
	 *  
	 * @param
	 * @return
	 */
	startCatalogo : function ( filter ){
		
		/**IMPOSTO UN CAMPO DI FILTRO**/
		appCatalogo.filter_C = filter;
		
		/**Recupero informazioni utili dalla url per lo start della mappa di urbamid**/
		urlParams = appConfig.getUrlVars();
		if ( urlParams["codeMappa"]==null || urlParams["codeMappa"]=="" )   
			urlParams["codeMappa"] = "DEFAULT";
		
		/**Recupero il dettaglio della mappa**/
		hrefUrbamid = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappa+"?codeMappa="+urlParams["codeMappa"];
		urbamidResponse = appRest.restUrbamid(hrefUrbamid, "GET", null);
		
		if (urbamidResponse.success) {
			
			/*************************************************************************************************************************/
			/************************	RECUPERO IL CATALOGO			    *********************							INIZIO ***/
			appConfig.workspaceGeoserver = urbamidResponse.aaData.catalog || appConfig.workspaceGeoserver;
			appCatalogo.getStartCatalogo();
			/************************	RECUPERO IL CATALOGO			    *********************							FINE ***/
			/*************************************************************************************************************************/
			
		} else {
			appUtil.showMessageAlertApplication('Ci sono stati problemi nel recuperare le informazioni per la mappa', 'Attenzione', false, true, false, null, null);
		}
	},

	
	/**
	 * Abilito il visualizzatore in pagina
	 * @param 
	 * @return
	 */
	enableVisualzzatore : function () {
	    var itemVisualizzatore = {};
			
		$("#visualizzatore").dialog({
			classes: {'ui-dialog': 'ui-dialog modale-messina'},
			title: 'Visualizzatore',
			position: { my: 'right-10 top', at : 'right top+105', of : window, collision: 'none' },
			modal: false,
			width: 320,
			autoOpen: true,
	        draggable: true,
			resizable: true,
			open: function(event, ui) {
				$(event.target).parent().find(".ui-dialog-titlebar-close").remove();
				$(event.target).parent().find(".ui-dialog-titlebar").append("<button type=\"button\" onclick=\"minifize('visualizzatore')\" class=\"ui-dialog-titlebar-minifize\" data-info=\"Riduci visualizzatore\" style=\"position: absolute; left: 90%\"><i class=\"fa fa-chevron-up\"></i></button>");
			},

		});
		appUtil.reloadTooltips();
		$("#visualizzatore").html( compilaTemplate("itemVisualizzatore", itemVisualizzatore) );
	},
	
	/**
	 * La function viene richiamata al click del "+" del catalogo. Aggiunge un tag checkbox nel visualizzatore 
	 * @param layerName
	 * @param hrefDetail
	 * @param itemChecked è un parametro che ha incluso Francesco Esposito che se pari a true imposta il layer già su mappa
	 * @return
	 */
	addLayerVisualizzatore : function (layerName, title, hrefDetail) {	
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.'); 
		if ( appUtil.containsString(appUrbamid.defaultLayerAddedView, layerName) ) {
			appUtil.showMessageAlertApplication('Il layer fa gi&agrave; parte dei layer di default', 'Attenzione', false, true, false, null, null);
		} else if ( appUtil.containsString(appUrbamid.layerAddedView, layerName) ) {
			appUtil.showMessageAlertApplication('Il layer fa gi&agrave; parte dei layer aggiunti', 'Attenzione', false, true, false, null, null);
		} else {
			
			$('#visualizzatoreDatiBaseDati_Catalogo_chk').show();
			$('#visualizzatoreDatiBaseDati_Catalogo_chk').prop('checked', false);
			
			/**Aggiungo il tag dell'item legenda layer**/
			itemViewerId = appUtil.generateId(layerName);
			if ( appUrbamid.layerAddedView.length==0 ) $("#Dati_Catalogo-layer").html("");
			enableLayerHtml = "<div id=\"itemViewer_"+itemViewerId+"\" class='itemViewerContainer'></div>";
			$("#Dati_Catalogo-layer").append(enableLayerHtml);
			appUrbamid.layerAddedView.push(layerName);
			if ( $("#visualizzatore").hasClass("showHide") ) appMappa.toggleVisualizzatore('visualizzatore', event);
			
			responseLayerDetail = appRest.restGeoserver ( hrefDetail );
			
			/**Valorizzo l'item legenda layer**/
			itemViewerId 	= appUtil.generateId(layerName);
			itemCheckId 	= appUtil.generateId(layerName); 
			hrefLegend		= appUtil.getOrigin() + appConfig.geoserverService + appConfig.wmsService + "?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER="+appConfig.workspaceGeoserver+":"+layerName;
			idParent		= appUtil.generateId('Dati Catalogo')
			name			= "";
			
			var itemViewer = {};
			if ( responseLayerDetail.featureType!=null ) {
				title = (responseLayerDetail.featureType.title).replace(/'/g, "");
		    	itemViewer = {"title":title, "layerName":layerName, "hrefLegend":hrefLegend, "visibilita":true, "itemCheckId":itemCheckId, "itemViewerId":itemViewerId, "opacity":1, "opacityPercent":100, idParent: idParent };
			} else if ( responseLayerDetail.coverage!=null ) {
				title = (responseLayerDetail.coverage.title).replace(/'/g, "");
		    	itemViewer = {"title":title, "layerName":layerName, "hrefLegend":hrefLegend, "visibilita":true, "itemCheckId":itemCheckId, "itemViewerId":itemViewerId , "opacity":1, "opacityPercent":100, idParent: idParent };
			}
			$("#itemViewer_"+itemViewerId).html( compilaTemplate("itemViewer", itemViewer) );

			/**Apro il box "DATI CATALOGO"**/
			if ( $("#visualizzatoreDatiBaseDati_Catalogo").attr("aria-expanded")=="false" )
				$("#visualizzatoreDatiBaseDati_Catalogo").click();
			
			/**CARICO PROPRIETA LAYER AGGIUNTO AL VISUALIZZATORE**/	
			href = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "layers/"+layerName+".json";
			responseTmp = appRest.restGeoserver ( href );
			if( responseTmp!=null && responseTmp.layer!=null) {
				href = responseTmp.layer.resource.href;
				responseLayerDetail = appRest.restGeoserver ( href );
				var title = "";
				if (responseLayerDetail.featureType!=undefined) {
					 title = responseLayerDetail.featureType.title;
				} else if (responseLayerDetail.coverage!=undefined) {
					title = responseLayerDetail.coverage.title;
				}
				appMappa.caricaFieldsFromResponse( responseTmp );
			} else {
				skipItem = true;
				appUrbamid.errorDefaultLayer.push( layerName );
			}
		}
		$("#visualizzatoreCatalogo").addClass("show");
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	},
	
	/**
	 * Elimina un layer dal visualizzatore. 
	 * Il layer viene eliminato dalla lista appUrbamid.layerAddedView ed in pagina attraverso l'id "itemViewer_'itemViewerId'"
	 * @param layerName, nome del layer da eliminare
	 * @return
	 */
	removeLayerVisualizzatore : function (layerName, idParent) {
		if ( appUtil.containsString(appUrbamid.layerAddedView, layerName) ) {
			itemViewerId = appUtil.generateId(layerName);
			appUrbamid.layerAddedView.splice($.inArray(layerName, appUrbamid.layerAddedView),1);
			$.each(layers, function(key, value) {
				if(value.layerName == layerName) {
					layers.splice(key, 1);
					return false;
				}
			});
			var tileWms = (appMappa.maps).getLayerByName(layerName);
			(appMappa.maps).removeLayer(tileWms);
			$("#itemViewer_"+itemViewerId).empty();
			if ( appUtil.getNumElem(appUrbamid.layerAddedView)==0 ) {
				$("#Dati_Catalogo-layer").html("<span>Seleziona livello dal catalogo</span>");
				$('#visualizzatoreDatiBaseDati_Catalogo_chk').hide();
			}
		}
		let isAllChecked = 0;

		$('input[data-gruppo="'+ idParent +'"]').each(function(e){
			if(!$(this).is(':checked')) {
				isAllChecked = 1;
				return false;
			}
		});

		if(isAllChecked == 0) {
			$('#visualizzatoreDatiBaseDati_Catalogo_chk').prop('checked', true);
		}
	},
	
	/**
	 * Abilita tutti i layer, presenti nei box del visualizzatore, in mapppa.
	 * Se è già abilitato, disabilita i layer e viceversa
	 * @param idBox, id del box. Esempio di idBox sono viewAll e viewAllDefault
	 * @param base, boolean che indica se bisogna gestire i dati di base o quelli extra
	 * @return
	 */
	viewAllLayerByBox : function ( idBox, base ) {
		
		if ( $("#"+idBox).children("i").hasClass("fa-eye") ) {
			/**Abilita tutti i layer**/
			$("#"+idBox).children("i").addClass( "fa-eye-slash" ).removeClass("fa-eye");
			if (base) {
				/**layer in altri dati	appUrbamid.layerAddedView**/
				/**Layer in mappa	appUrbamid.layerAddedMap*/
				$.each( appUrbamid.defaultLayerAddedView, function( keyDefaultLayerAddedView, valueDefaultLayerAddedView ) {
					if (  appUrbamid.layerAddedMap[valueDefaultLayerAddedView]==null ) {
						itemCheckId = appUtil.generateId(valueDefaultLayerAddedView);
						appMappa.addRemoveLayer( valueDefaultLayerAddedView, '', null, '', '' );
						$("#"+itemCheckId+"_chk").prop("checked", true);
					}
				});
				appUrbamid.defaultLayerAddedViewSelAll = true;					
			} else {
				/**layer in altri dati	appUrbamid.layerAddedView**/
				/**Layer in mappa	appUrbamid.layerAddedMap*/
				$.each( appUrbamid.layerAddedView, function( keyLayerAddedView, valueLayerAddedView ) {
					if (  appUrbamid.layerAddedMap[valueLayerAddedView]==null ) {
						itemCheckId = appUtil.generateId(valueLayerAddedView);
						appMappa.addRemoveLayer( valueLayerAddedView, '', '', '', '' );
						$("#"+itemCheckId+"_chk").prop("checked", true);
					}
				});
			}
		} else if ( $("#"+idBox).children("i").hasClass("fa-eye-slash") ) { 
			/**Disabilita tutti i layer**/
			$("#"+idBox).children("i").addClass( "fa-eye" ).removeClass("fa-eye-slash");
			if (base) {
				/**Disabilita tutti i layer**/
				$("#viewAllDefault").children("i").addClass( "fa-eye" ).removeClass("fa-eye-slash");
				/**layer in altri dati	appUrbamid.layerAddedView**/
				/**Layer in mappa	appUrbamid.layerAddedMap*/
				$.each( appUrbamid.defaultLayerAddedView, function( keyDefaultLayerAddedView, valueDefaultLayerAddedView ) {
					if (  appUrbamid.layerAddedMap[valueDefaultLayerAddedView]!=null ) {
						itemCheckId = appUtil.generateId(valueDefaultLayerAddedView);
						appMappa.addRemoveLayer( valueDefaultLayerAddedView, '', null, '', '' );
						$("#"+itemCheckId+"_chk").prop("checked", false);
					}
				});
				appUrbamid.defaultLayerAddedViewSelAll = false;
			} else {
				/**layer in altri dati	appUrbamid.layerAddedView**/
				/**Layer in mappa	appUrbamid.layerAddedMap*/
				$.each( appUrbamid.layerAddedView, function( keyLayerAddedView, valueLayerAddedView ) {
					if (  appUrbamid.layerAddedMap[valueLayerAddedView]!=null ) {
						itemCheckId = appUtil.generateId(valueLayerAddedView);
						appMappa.addRemoveLayer( valueLayerAddedView, '', null, '', '' );
						$("#"+itemCheckId+"_chk").prop("checked", false);
					}
				});
			}
		}
	},
	
	/**
	 * Viene abilitata una modale per la legenda
	 * @param layerName, nome del layer
	 * @param hrefLegend, il link della legenda di Geoserver
	 * @param title, il titolo del layer
	 * @return
	 */
	showLegend : function( layerName, hrefLegend, title ){
		var titolo = title.replace(/_/g, ' ');
		if ( sessionStorage.getItem('windowOpenedLegend')!=null ) {
			closeStaticDetail( sessionStorage.getItem('windowOpenedLegend')  );
		}
		openStaticDetailViaHandlebars('showLegend', 'legendaLayers', { hrefLegend: hrefLegend, title: titolo }, 'Legenda', function(){
			sessionStorage.setItem('windowOpenedLegend','showLegend');

			$('#showLegend.ui-dialog-titlebar-minifize').attr('data-info', 'Riduci legenda');
			$('#showLegend.ui-dialog-titlebar-close').attr('data-info', 'Chiudi legenda');
	
			appUtil.reloadTooltips();

		}, {closable:true, height: 260, width: 310, position : { my: "right top", at: "right-350 top+105", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
	},

	/**
	 * Viene abilitata il box per l'opacità già presente nel visualizzatore al di sotto del nome con il check
	 * @param layerName, nome del layer
	 * @return
	 */
	showOpacity : function( itemCheckId, opacity, layerName){
		let tileWms = appUrbamid.layerMap[itemCheckId] != null ? appUrbamid.layerMap[itemCheckId] : null;
		if(tileWms != null) {
			$("#"+itemCheckId+"_divgear").toggleClass("hidden");
			$("#"+itemCheckId+"_slider").slider({
				formatter: function(value) {
					return 'Current value: ' + value;
				}
			});
			$("#boxVisualizzatoreMappa").animate({scrollTop: $("#"+itemCheckId+"_divgear").position().top}, 1000);
		} else {
			appUtil.showMessageAlertApplication('Abilitare i dati su mappa', 'Attenzione', false, true, false, null, null);
		}
	},
	
	/**
	 * Viene disabilitata il box per l'opacità già presente nel visualizzatore al di sotto del nome con il check
	 * @param itemCheckId, id del check del layer utilizzato per recuperare il valore dello slider
	 * @return
	 */
	hideOpacity : function(itemCheckId){
		if(!$("#"+itemCheckId+"_divgear").hasClass("hidden")) {
			$("#"+itemCheckId+"_divgear").toggleClass("hidden");
		}
	}, 
	
	/**
	 * Viene gestita l'opacità del layer. Il layer è presente nel seguente HashMap appUrbamid.layerAddedMap
	 * @param layerName, nome del layer 
	 * @param itemCheckId, id del check del layer utilizzato per recuperare il valore dello slider
	 * 						L'Id dello slider è "itemCheckId+'_slider'"
	 * @return
	 */
	changeOpacity: function (layerName, itemCheckId){
		if (appUrbamid.layerMap[itemCheckId]!=null) {
			valueOpacity = $("#"+itemCheckId+"_slider").val();
			appUrbamid.layerMap[itemCheckId].setOpacity( (valueOpacity/10) );
			$("#"+itemCheckId+"_divgear #value-opa").html( (valueOpacity*10)+"%" );
		}
	},

	/** Viene gestito il checkbox per abilita o disabilita tutti i layer presenti in un determinato gruppo. 
	 *  @param idParent, l'ID del gruppo
	*/
	attivaDisattivaTuttiLayer: function(idParent) {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			var layerAttivi = [];
			var layerNonAttivi = [];

			/** 1.0 Controllo tramite l'attributo data-gruppo quali sono i checkbox attivi e non attivi
			 * 	prendendo l'ID di ogni singolo checkbox
			 */
			$('input[data-gruppo="'+ idParent +'"]').each(function(e){
				if($(this).is(':checked')) {
					layerAttivi.push($(this).attr('id'));
				} else {
					layerNonAttivi.push($(this).attr('id'));
				}
			});

			/** 2.0 Attivo/disattivo tutti i layer */
			if(layerNonAttivi.length != 0) {
				$.each(layerNonAttivi, function(key, value) {
					$('#' + value).click();
				});
			} else if(layerAttivi.length != 0) {
				$.each(layerAttivi, function(key, value) {
					$('#' + value).click();
				});
			}

			appUtil.hideLoader();
		}, 500);
	},
	
	/**
	 * La function viene richiamata al click del "+", presente nel box mappe, e sostituisce i layer presenti nel visualizzatore box 'dati di base'.
	 * I passi che vengono svolti sono i seguenti:
	 * 	1.  RIMUOVO I LAYER ATTIVI IN MAPPA
	 *  2.  RECUPERO LA MAPPA DALLE MAPPE CARICATE
	 *  3.  MODIFICO I DATI DI BASE CON I NUOVI DATI DI BASE DELLA MAPPA SELEZIONATA
	 *  4.	VALORIZZO LAYER NEW VISUALIZZATORE
	 *  5.  APPLICO I LAYER DI DEFAULT ABILITATI
	 *  
	 * @param code, codice della mappa da applicare in mappa
	 * @return
	 */
	applicaMappa: function ( code ) {
		
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		appUrbamid.mappaApplicata = code;
		
		/**1. RIMUOVO I LAYER ATTIVI IN MAPPA **/
		for (var item in appUrbamid.layerAddedMap) {
			appMappa.removeLayer( item );
		}
		appMappa.deselezionaTuttiLayerCompletamente();
		
		/**2. INIZIALIZZO**/
		layers                				= [];
		layerToEnable 						= [];
		appUrbamid.layerAddedMap 			= [];
		appUrbamid.defaultLayerAddedView 	= [];
		appUrbamid.errorDefaultLayer		= [];
		appUrbamid.enableVisualzzatore();
		
		/** 3. RECUPERO LA MAPPA DAL CODICE**/
		hrefUrbamid = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappa+"?codeMappa="+code;
		urbamidResponse = appRest.restUrbamid(hrefUrbamid, "GET", null);

		/*************************************************************************************************************************/
		/*** 1 ******************	IMPOSTAZIONE GRAFICHE			    *********************							INIZIO ***/
		$("#title-map").html( urbamidResponse.aaData.title );
		appUrbamid.initializeLeftMapAction();
		/*** 1 ******************	IMPOSTAZIONE GRAFICHE			    *********************							FINE ***/
		/*************************************************************************************************************************/
		
		indexTopLeftMapAction = 0;
		/*************************************************************************************************************************/
		/*** 2 ******************	RECUPERO DELLE MAPPE	    		*********************							INIZIO ***/
		$(".ol-maps").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
		indexTopLeftMapAction++;
		/*** 2 ******************	RECUPERO DELLE MAPPE	    		*********************							FINE ***/
		/*************************************************************************************************************************/
		
		
		/*************************************************************************************************************************/
		/*** 3 ******************	RECUPERO IL CATALOGO			    *********************							INIZIO ***/
		$(".ol-layers").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
		indexTopLeftMapAction++;
		/*** 3 ******************	RECUPERO IL CATALOGO			    *********************							FINE ***/
		/*************************************************************************************************************************/
		
		
		/*************************************************************************************************************************/
		/*** 4 ******************	CREO MAPPA E IMPOSTO IL GISTOOL	    *********************							INIZIO ***/
		idMappa = urbamidResponse.aaData.id;
		hrefUrbamidMappaTool = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappaTool+"?idMappa="+idMappa;
		urbamidMappaToolResponse = appRest.restUrbamid(hrefUrbamidMappaTool, "GET", null);
		if (urbamidMappaToolResponse.success) {
			appMappa.createMappa(urbamidMappaToolResponse.aaData);
			if (urbamidMappaToolResponse.aaData.length>0) {
				$(".ol-gistool").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
				indexTopLeftMapAction++;
			} else {
				$(".ol-gistool").css( 'display', 'none' );
			}
		}
		/*** 4 ******************	CREO MAPPA E IMPOSTO IL GISTOOL	    *********************							  FINE ***/
		/*************************************************************************************************************************/
		

		/*************************************************************************************************************************/
		/*** 5 ******************	RECUPERO LE RICERCHE	    		*********************							INIZIO ***/
		if (!appUrbamid.isServiceGis) {
			$("#ricercaParticella").html("");
			$("#ricercaFoglio").html("");
			$("#ricercaToponimo").html("");
			idMappa = urbamidResponse.aaData.id;
			hrefUrbamidMappaRicerche = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappaRicerca+"?idMappa="+idMappa;
			urbamidMappaRicercaResponse = appRest.restUrbamid(hrefUrbamidMappaRicerche, "GET", null);
			if (urbamidMappaRicercaResponse.success) {
				if ( urbamidMappaRicercaResponse.aaData.length>0 ) {
					$.each( urbamidMappaRicercaResponse.aaData, function( keySearch, valueSearch ) {
					    var ricerca = {};		//get ricerca from DB
						$("#"+valueSearch.name).html( compilaTemplate(valueSearch.idHandle, ricerca) );
					});
					$(".ol-searchtool").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
					indexTopLeftMapAction++;
				} else {
					$(".ol-searchtool").css( 'display', 'none' );
				}
			}
		}
		/*** 5 ******************	RECUPERO LE RICERCHE	    		*********************							FINE ***/
		/*************************************************************************************************************************/
		
		
		/*************************************************************************************************************************/
		/*** 6 ******************	IMPOSTO LA STAMPA	    		 ************************							INIZIO ***/
		$(".ol-printtool").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
		indexTopLeftMapAction++;
		/*** 6 ******************	IMPOSTO LA STAMPA	   			 **************************							  FINE ***/
		/*************************************************************************************************************************/
		
		
		/*************************************************************************************************************************/
		/*** 7 ******************	IMPOSTO INFO LAYER	    		 ************************							INIZIO ***/
		$(".ol-infotool").css( 'top',  appUrbamid.topLeftMapAction[indexTopLeftMapAction]);
		indexTopLeftMapAction++;
		/*** 7 ******************	IMPOSTO INFO LAYER	   			 **************************							  FINE ***/
		/*************************************************************************************************************************/
		
		
		/*************************************************************************************************************************/
		/*** 8 ******************	IMPOSTAZIONE LAYER DI DEFAULT    	********************							INIZIO ***/
		hrefMappaLayerUrbamid = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappeLayer;
		var myMappa=new getUMappa();
		myMappa.id= urbamidResponse.aaData.id;
		var urbamidMappaLayerResponse = appRest.restSimplePost(hrefMappaLayerUrbamid, myMappa);
		
		layerToEnable 			= [];
		layers                	= [];
		
		/** 8.1 AGGIUNGO I GRUPPI **/
		$.each( urbamidMappaLayerResponse.aaData, function( keyGruppo, gruppoLayers ) {
			gruppo = {'idGruppo':appUtil.generateId(keyGruppo), 'nomeGruppo':keyGruppo};
			htmlGruppo = compilaTemplate("contGruppo", gruppo);
			$("#boxVisualizzatoreMappa").append( htmlGruppo );
		});
		gruppo = {'idGruppo':'Dati_Catalogo', 'nomeGruppo':'Dati Catalogo'};
		htmlGruppo = compilaTemplate("contGruppo", gruppo);
		$("#boxVisualizzatoreMappa").append( htmlGruppo );
		
		/** 8.2 AGGIUNGO I LAYER DEI GRUPPI **/
		let countLayer = 0;
		$.each( urbamidMappaLayerResponse.aaData, function( keyGruppo, gruppoLayers ) {
			if (gruppoLayers.length>0) {
				$("#"+appUtil.generateId(keyGruppo)+"-layer").html("");
			}
			$.each( gruppoLayers, function( key, valueLayer ) {
				layerName		= valueLayer.layerName || valueLayer.nomeLayer;
				/**Aggiungo il tag dell'item legenda layer**/
				itemViewerId = appUtil.generateId(layerName + '_' + countLayer);
				defaultLayerHtml ="<div id=\"itemViewer_"+itemViewerId+"\" class='itemViewerContainer'></div>";
				$("#"+appUtil.generateId(keyGruppo)+"-layer").append( defaultLayerHtml );
				appUrbamid.defaultLayerAddedView.push(layerName);
				valueLayer.itemViewerId = itemViewerId
				valueLayer.itemCheckId = itemViewerId
				layers				.push( valueLayer);
				countLayer++;
			});
		});

		$('#visualizzatoreDatiBaseDati_Catalogo_chk').hide();

		/** 8.3 VALORIZZO TUTTI I LAYER**/
		name			= "";
		hrefLegend 		= "";
		href 			= "";
		$.each( layers, function( keyLayer, valueLayer ) {

			layerName		= valueLayer.nomeLayer || valueLayer.layerName;
			itemViewerId 	= valueLayer.itemViewerId;
			itemCheckId 	= valueLayer.itemCheckId; /* appUtil.generateId(itemViewerId); */ 
			title			= "";
			opacity			= valueLayer.trasparenza;
			opacityPercent	= (valueLayer.trasparenza*10);
			hrefLegend		= "";
			idParent		= appUtil.generateId(valueLayer.idParent);

			isValidLay 		= true;
			if ( appUtil.generateId(valueLayer.idParent).indexOf("DATI_DI_BASE")!=0 ) {
				name			= "";
				hrefLegend 		= appUtil.getOrigin() + appConfig.geoserverService + appConfig.wmsService + "?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER="+appConfig.workspaceGeoserver+":"+layerName;
			
				href = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "layers/"+layerName+".json";
				responseTmp = appRest.restGeoserver ( href );
				if( responseTmp!=null && responseTmp.layer!=null && responseTmp.layer!=undefined) {
					href = responseTmp.layer.resource.href;
					responseLayerDetail = appRest.restGeoserver ( href );
					title = ((responseLayerDetail.featureType!=null && responseLayerDetail.featureType.title!=undefined) ? responseLayerDetail.featureType.title : 
								(responseLayerDetail.coverage!=null && responseLayerDetail.coverage.title!=undefined ? responseLayerDetail.coverage.title : 'Livello senza nome'));
					appMappa.caricaFieldsFromResponse( responseTmp );
				} else {
					skipItem = true;
					isValidLay = false;
					appUrbamid.errorDefaultLayer.push( layerName );
				}
			} else {
				title = (valueLayer.titleLayer).replace(/'/g, "");
			}
			var itemViewer = {"title": title, "layerName":layerName,"hrefLegend":hrefLegend,"visibilita":false, "itemCheckId":itemCheckId, "opacity":opacity, "opacityPercent":opacityPercent, idParent: idParent };
			$("#itemViewer_"+itemViewerId).html( compilaTemplate("itemViewer", itemViewer) );
			if (valueLayer.enabled || valueLayer.abilitato && isValidLay) {
				layerToEnable.push(itemViewer);
			} else {
				//Salvo nell'hashMap layerSeleDeselMap i layer che non applico alla mappa, operazione utile per salvare informazioni come l'opacità
				appUrbamid.layerSeleDeselMap[layerName] = itemViewer;
			} 

		});

		/** 8.4 VISUALIZZO POPUPCON MESSAGGIO DI ERRORE. IL MESSAGGIO RIPORTERA I LAYER CHE NON SI POSSONO CARICARE **/
		if ( appUrbamid.errorDefaultLayer!=null && appUrbamid.errorDefaultLayer.length>0 ) {
			var messageErrorDefaultLayer = "Non &eacute; stato possibile caricari i seguenti dati di base:<br>";
			$.each( appUrbamid.errorDefaultLayer, function( keyErrorDefaultLayer, valueErrorDefaultLayer ) {
				messageErrorDefaultLayer+= "&nbsp;	&nbsp;	&raquo;	"+valueErrorDefaultLayer+"<br>"
			});
			appUtil.showMessageAlertApplication(messageErrorDefaultLayer, 'Informazione', true, false, false, null, null);
		};
		
		/** 8.5 APPLICO I LAYER DI DEFAULT ABILITATI **/
		$.each( layerToEnable, function( keyLayerToEnable, valueLayerToEnable ) {
			$( "#"+valueLayerToEnable.itemCheckId+"_chk" ).prop('checked', true);
			appMappa.addLayerMap( valueLayerToEnable.layerName, valueLayerToEnable.title, valueLayerToEnable, valueLayerToEnable.idParent, valueLayerToEnable.itemCheckId );
		});


		/** 8.7 APRO DATI CATALOGO E IMPOSTO LA SCRITTA */
		if ( $("#visualizzatoreDatiBaseDati_Catalogo").attr("aria-expanded")=="false" )
				$('#Dati_Catalogo-layer').html('<span>Seleziona livello dal catalogo</span>')
				$("#visualizzatoreDatiBaseDati_Catalogo").click();
		/*** 8 ******************	IMPOSTAZIONE LAYER DI DEFAULT    	********************							FINE ***/
		/*************************************************************************************************************************/

		/*************************************************************************************************************************/
		/*** 10 ******************	IMPOSTAZIONE GRAFICHE			    *********************							INIZIO ***/
		zoomMap = urbamidResponse.aaData.zoom;
		centerMap = [1731514.79, 4608612.20],
		(appMappa.maps).getView().animate({
			zoom: zoomMap,
			center: centerMap,
			duration: 2000
		});
		/*** 10 ******************	IMPOSTAZIONE GRAFICHE			    *********************							FINE ***/
		/*************************************************************************************************************************/
		
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	},
	
	initializeLeftMapAction : function () {
		$(".ol-infotool").css( 'display',  'block');
		$(".ol-printtool").css( 'display',  'block');
		$(".ol-searchtool").css( 'display', 'block' );
		$(".ol-gistool").css( 'display', 'block' );
		$(".ol-maps").css( 'display', 'block' );
	}
}

function deleteField(id) {
	$("#"+id).val("");
}

function openNav(event) {
	$(event.currentTarget).toggleClass("open");
	$("#myNav").toggleClass("hidden");
	if (!$("#mappe").hasClass("hidden")) appMappa.toggleTool('mappe', event);
	if (!$("#catalogo").hasClass("hidden")) appMappa.toggleTool('catalogo', event);
	if (!$("#gisToolContainer").hasClass("hidden")) appMappa.toggleTool('gisToolContainer', event);
	if (!$("#searchToolContainer").hasClass("hidden")) appMappa.toggleTool('searchToolContainer', event);
	if (!$("#infoLayerContainer").hasClass("hidden")) appMappa.toggleTool('infoLayerContainer', event);
}

function closeNav() {
	$("#myNav").addClass("hidden");
	$("#myNavbar").removeClass("open");
}

function getUbis(hmValue){
	mockUbis = [];
	numUbi = Math.floor(Math.random() * 2) + 1;
	arrayUbi = [
	            {"dug":"Viale", "toponimo":"Europa", "civico":"2", "coordinate":{"lat":"","lon":""}}
	];
	for (i=0;i<numUbi;i++) {
		ubi= arrayUbi[Math.floor(Math.random()*arrayUbi.length)];
		ubi.coordinate.lat = hmValue.bbox[1];
		ubi.coordinate.lon = hmValue.bbox[0];
		mockUbis.push( ubi );
	}
	return mockUbis;
}

//MS CLASSE MAPPA (NUOVA TABELLA)
function getUMappa(){
		this.isNew=false;
		this.id=0;
		this.codice=null;
		this.nome=null;
		this.descrizione=null;
		this.stato='P';
		this.mappaPredefinita=false;
		this.dataCreazione=null;
		this.dataModifica=null;
		this.utenteCreazione=null;
		this.utenteModifica=null;
		this.idsTemi=[];
		this.layer={};
		this.search=[];
		this.tools={};
 }