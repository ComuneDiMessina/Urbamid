/*******************************************************************************************************************************************************/
/***************************************			FUNZIONALITA' CATALOGO				****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

/**
 * @include /plugin/jsTree-3.3.8
 * @include util.js						appUtil, 	viene utilizzato per la gestione delle url 
 * @include configuration.js			appConfig, 	viene utilizzato per i parametri di configurazione come geoserverService, restService
 * @include rest.js						appRest, 	viene utilizzato per le chiamate rest
 */


/** Al caricamento della pagina carico il catalogo */
$(document).ready(function() {

});

appCatalogo = {
		
		/****************************************************/
		/** CONFIGURAZIONI SOLO PER IL CATALOGO ESTRAZIONI **/
		itemCE_hm	    : [],												//Contiene l'area tematica e i layer al suo interno			
		itemCE_NoChild	: new Object(),										//HashMap di item del catalogo con valore NaN
		itemCE_TEtoOpen 	: "",											//Item "tematismi" del catalogo aperti 
		itemCE_Open		: "",												//Item del catalogo aperti
		cbkOnClick_CE	: "alert",											//Name of function da richiamare quendo si clicca sul "+" dell'item del menu. 
																			//La function da definire dove si inizializza il menu deve avere due parametri:
																			//	- nome del layer
																			//  - url del layer
		mesCbk_CE		: "Selezione Layer",
		filter_CE		: null,												//Campo utilizzato per mostrare o meno il layer
		idCount_CE		: 1,												//Contatore per gli Id
		
		/***************************************/
		/**CONFIGURAZIONI SOLO PER IL CATALOGO**/
		itemC_NoChild	: new Object(),										//HashMap di item del catalogo con valore NaN
		itemC_TEtoOpen 	: "",												//Item "tematismi" del catalogo aperti 
		itemC_Open		: "",												//Item del catalogo aperti
		cbkOnClick_C	: "alert",											//Name of function da richiamare quendo si clicca sul "+" dell'item del menu. 
																			//La function da definire dove si inizializza il menu deve avere due parametri:
																			//	- nome del layer
																			//  - url del layer
		mesCbk_C		: "Selezione Layer",
		filter_C		: null,												//Campo utilizzato per mostrare o meno il layer
		idCount_C		: 1,												//Contatore per gli Id
		
		/***************************************/
		/**CONFIGURAZIONI SOLO PER IL CATALOGO GEST MAPPE**/
		itemTE_Loaded	  : [],
		itemLA_Loaded	  : [],
		itemMenuGestMappe : [],
		countLayer		  : 0,

		/************************************************/
		/** Configurazioni per il catalogo della mappa **/
		itemNoChild 	: new Object(),										//HashMap di item del catalogo con valore NaN
		itemTEtoOpen 	: "",												//Item "tematismi" del catalogo aperti 
		itemOpen		: "",												//Item del catalogo aperti
		
		cbkOnClick		: "alert",											//Name of function da richiamare quendo si clicca sul "+" dell'item del menu. 
																			//La function da definire dove si inizializza il menu deve avere due parametri:
																			//	- nome del layer
																			//  - url del layer
		
		
		
		/**************************************************************************************************************************************************/
        /********* CATALOGO GENERALE *********************************************************************************************************** INIZIO ***/
		/**************************************************************************************************************************************************/		
		/**
		 * Recupero delle aree tematiche da geoserver. 
		 * Di seguito alcune caratteristiche della function:
		 * 	- Se l'aree tematiche presenta la keyword defaultView[true], la function carica i figli
		 * 	- Se il tematismo presenta la keyword defaultView[true], la function carica i figli
		 * 	- Se il layergroup presenta la keyword defaultView[true], la function carica i figli
		 * 
		 * N.b. Le aree tematiche sono LayerGroup con suffisso "_areaTematica", i tematismi sono LayerGroup con suffisso "_tematismo",
		 * i layergroup sono LayerGroup con suffisso "_layergroup".
		 * 
		 * N.b. Function viene chiamato solo per il catalogo completo mostrato nelle pagine con mappa
		 * 
		 * @param
		 * @returns 
		 */
		getStartMenu 	: function (){
			appConfig.menuJson.core.data = [];
			href = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "workspaces/"+appConfig.workspaceGeoserver+"/layergroups.json";
			responseListAreeTematiche = appRest.restGeoserver( href );
			if(responseListAreeTematiche!=null && responseListAreeTematiche.layerGroups!=null && responseListAreeTematiche.layerGroups.layerGroup!=null){
				$.each( responseListAreeTematiche.layerGroups.layerGroup, function( keyAreeTematiche, valueAreeTematiche ) {
					/**DETTAGLIO AREA TEMATICA**/
					if ( (valueAreeTematiche.name).indexOf("_areaTematica")!=-1 ) {
						responseDetailAT = appRest.restGeoserver( appUtil.replaceNameSpace(valueAreeTematiche.href) );
						if( responseDetailAT!=null && responseDetailAT.layerGroup!=null ){
							itemId	 								= appUtil.generateItemId("",responseDetailAT.layerGroup.name);
							itemLabel								= responseDetailAT.layerGroup.title;
							itemOpened								= appCatalogo.isDefaultView( responseDetailAT );
							itemIcon								= appConfig.itemIcon["folder"];
							itemMenu 								= {"id":itemId, "text":itemLabel, "parent":"#", "state":{"opened":itemOpened}, "icon":itemIcon, "li_attr":{"class":"liAreaTematica"}, "a_attr":{"class":"aAreaTematica"}};
							appConfig.menuJson.core.data			.push(itemMenu);
							appConfig.itemLoaded[itemId]			= $.isArray(responseDetailAT.layerGroup.publishables.published) ? responseDetailAT.layerGroup.publishables.published : [responseDetailAT.layerGroup.publishables.published];
							if ( itemOpened ) {
								appCatalogo.getItemMenu( itemId );
							} else {
								itemMenuEmpty							= {"id":"NaN_"+itemId, "text":"", "parent":itemId, "state":{}};
								appConfig.menuJson.core.data			.push(itemMenuEmpty);
								appCatalogo.itemNoChild["NaN_"+itemId]		= itemMenuEmpty;
							}
						} else {
							/**TODO: verificare nel caso di aree tematica non corretta **/
							appUtil.setMessage("Errore nel recuperare il dettaglio dell'area tematica");
						}
					}
				});
			} else {
				/**TODO: verificare nel caso di aree tematiche non presenti **/
				appUtil.setMessage(responseListAreeTematiche.message);
				$("#empty-menu-content").text( appConfig.emptyMenu );
			}
		}, 
		
		
		/**
		 * Inizializzo il Menu (JsTree)
		 * Nella seguente function vengono definite le azioni possibili sull'albero. Sono le seguenti:
		 * 	- APRO NODO MENU (open_node.jstree)
		 * 	- CHIUDO NODO MENU (close_node.jstree)
		 * 	- (disabilitato) SELEZIONO NODO MENU (select_node.jstree)
		 * 	- (disabilitato) DESELEZIONO NODO MENU (deselect_node.jstree)
		 * 	- (disabilitato) CAMBIO NEL MENU (changed.jstree)
		 * 	- (disabilitato) RICARICAMENTO DEL MENU (refresh.jstree)
		 */
		initializeMenu 	: function ( idMenu, cbkOnClick ) {
			
			appCatalogo.cbkOnClick = cbkOnClick;
			/**ORDINO I DATI**/
			(appConfig.menuJson.core.data).sort( 
				function(a, b){
					var nameA=a.text.toLowerCase(), nameB=b.text.toLowerCase()
					if (nameA < nameB) //sort string ascending
						return -1 
					if (nameA > nameB)
						return 1
					return 0 //default return value (no sorting)
				} 
			);
			
			/**INIZIALIZZO IL MENU **/
			appConfig.jsTreeMenu = $('#'+idMenu).jstree( appConfig.menuJson );
			
			/**APRO NODO MENU**/
			appConfig.jsTreeMenu.on("open_node.jstree", function (e, data) {
				appCatalogo.doRefresh = false;
				//1. Verifico se l'item che voglio aprire contiene figli non censiti
				if (data.node.id!=null && appCatalogo.itemNoChild["NaN_"+data.node.id]!=null) {
					//1.1 Carico gli item non presenti
					appCatalogo.getItemMenu( data.node.id );
					//1.2 Rimuovo l'item empty dal appConfig.menuJson
					appCatalogo.removeItemBlank( "NaN_"+data.node.id );
					//1.3 Rimuovo l'item empty dall'elenco degli item NaN					
					delete appCatalogo.itemNoChild["NaN_"+data.node.id];
					//1.4 Imposto l'aggiornamento del menu
					appCatalogo.doRefresh = true;
				}
				//2. Imposto l'item che sto aprendo, solo opened=true
				$.each( appConfig.menuJson.core.data, function( key, value ) {
					if (value.id==data.node.id) {
						value.state.opened = true;
					}
				});
				//3. id dell'item aperto utile per settare la top nel menu
				appCatalogo.itemOpen = data.node.id;
				//4. Aggiorno mappa se devo aggiornarla (appCatalogo.doRefresh)
				appCatalogo.itemTEtoOpen = data.node.id;
				appCatalogo.refreshMenu();
			});
			
			/**CHIUDO NODO MENU**/
			appConfig.jsTreeMenu.on("close_node.jstree", function (e, data) {
				appCatalogo.doRefresh = false;
				//1. Imposto l'item che sto chiudendo, opened=false
				$.each( appConfig.menuJson.core.data, function( key, value ) {
					if (value.id==data.node.id) {
						value.state.opened = false;
					}
				});
				//2. Elimino dalla mappa i layer figli dell'item che sto chudendo
				$.each( appConfig.menuJson.core.data, function( key, value ) {
					if (value.parent==data.node.id) {
					}
				});
				//3. id dell'item aperto utile per settare la top nel menu
				appCatalogo.itemOpen = data.node.id;
				//4. Aggiorno mappa se devo aggiornarla (appCatalogo.doRefresh)
				appCatalogo.refreshMenu();
			});
			
			/**SELEZIONO NODO MENU**/
			appConfig.jsTreeMenu.on("select_node.jstree", function(evt, data){
				appCatalogo.doRefresh = false;
				//1. Verifico se l'item che voglio aprire contiene figli non censiti
				if (data.node.id!=null && appCatalogo.itemNoChild["NaN_"+data.node.id]!=null) {
					//1.1 Carico gli item non presenti
					appCatalogo.getItemMenu( data.node.id );
					//1.2 Rimuovo l'item empty dal appConfig.menuJson
					appCatalogo.removeItemBlank( "NaN_"+data.node.id );
					//1.3 Rimuovo l'item empty dall'elenco degli item NaN					
					delete appCatalogo.itemNoChild["NaN_"+data.node.id];
					//1.4 Imposto l'aggiornamento del menu
					appCatalogo.doRefresh = true;
				}
				//2. Imposto l'item che sto aprendo, solo opened=true
				$.each( appConfig.menuJson.core.data, function( key, value ) {
					if (value.id==data.node.id) {
						value.state.opened = true;
					}
				});
				//3. Aggiorno mappa se devo aggiornarla (appCatalogo.doRefresh)
				appCatalogo.itemTEtoOpen = data.node.id;
				appCatalogo.refreshMenu();
			});
			
			
			/**RICARICAMENTO DEL MENU**/
			appConfig.jsTreeMenu.on('refresh.jstree', function () {
				
				if (appCatalogo.itemOpen!="") {
					if ( $("#"+appCatalogo.itemOpen).length > 0 ) {
						//Layer
						newTop = $("#"+appCatalogo.itemOpen).position().top;
						
					} else if ( $("#"+appCatalogo.itemOpen+"_ATE_anchor").length > 0 ) {
						//Area tematica
						newTop = $("#"+appCatalogo.itemOpen+"_ATE_anchor").position().top;
						
					} else if ( $("#"+appCatalogo.itemOpen+"_TE_anchor").length > 0 ) {
						//Tematismo
						newTop = $("#"+appCatalogo.itemOpen+"_TE_anchor").position().top;
						
					} 
					$('.boxMappa_body').animate({
						scrollTop: (newTop-40)
					},'slow');
				}
			});
		},
		
		/**
		 * Recupero, dato il parent, gli item figli
		 * 
		 * @param parent, id del padre
		 * @returns 
		 */
		getItemMenu : function( parent ){
			//1. Recupero oggetto caricato
			parentChildren = appConfig.itemLoaded[parent];
			$.each( parentChildren, function( keyChild, valueChild ) {
				//2. Per ogni oggetto caricato recupero i figli
				responseDetailChild = appRest.restGeoserver( appUtil.replaceNameSpace(valueChild.href) );
				//3. Gestione del figlio
				if( responseDetailChild!=null && responseDetailChild.layerGroup!=null && responseDetailChild.layerGroup.name.indexOf("DEFAULT_")==-1 && 
						appCatalogo.isLoaded(parent, appUtil.generateItemId("",responseDetailChild.layerGroup.name))==null ){
					/**###############	GESTIONE DEL LAYERGROUP	#############**/
					/**3.1 Se il figlio dell'oggetto già caricato è un layergroup e non è stato già caricato, lo carico**/ 

					//3.1.1 Compongo l'item da aggiungere
					itemId	 								= appUtil.generateItemId("",responseDetailChild.layerGroup.name);
					itemLabel								= responseDetailChild.layerGroup.title;
					itemOpened								= appCatalogo.isDefaultView( responseDetailChild );
					itemIcon								= itemId.indexOf("_LG")!=-1?appConfig.itemIcon["folder_layer"]:appConfig.itemIcon["folder"];
					itemLiAttr								= itemId.indexOf("_LG")!=-1?{"class":"liLayergroup"}:{"class":"liTematismo"};
					itemMenu 								= {"id":itemId, "text":itemLabel, "parent":parent, "state":{"opened":itemOpened}, "icon":itemIcon, "li_attr":itemLiAttr};
					
					//3.1.2 Aggiungo l'item al menu
					appConfig.menuJson.core.data			.push(itemMenu);
					//3.1.3 Aggiungo l'item agli elementi caricati 
					appConfig.itemLoaded[itemId]			= $.isArray(responseDetailChild.layerGroup.publishables.published) ? responseDetailChild.layerGroup.publishables.published : [responseDetailChild.layerGroup.publishables.published];
					//3.1.4 Elimino l'elemento NaN
					appCatalogo									.removeItemBlank( "NaN_"+parent );
					//3.1.5 Gestione del defaultView con caricamento dei figli o aggiunta di un figlio fittizio
					if ( itemOpened ) {
						appCatalogo.getItemMenu( itemId );
					} else {
						itemMenuEmpty							= {"id":"NaN_"+itemId, "text":"", "parent":itemId, "state":{}};
						appConfig.menuJson.core.data			.push(itemMenuEmpty);
						appCatalogo.itemNoChild["NaN_"+itemId]		= itemMenuEmpty;
					}
				} else if ( responseDetailChild!=null && responseDetailChild.layer!=null && responseDetailChild.layer.name.indexOf("DEFAULT_")==-1 &&
						appCatalogo.isLoaded(parent, appUtil.generateItemId("",responseDetailChild.layer.name))==null ) {
					/**###############	GESTIONE DEL LAYER	#################**/
					/**3.2 Se il figlio dell'oggetto già caricato è un layer e non è stato già caricato, lo carico**/
					
					//3.2.1 Recupero il dettaglio del Layer
					responseDetailLayer = appRest.restGeoserver( appUtil.replaceNameSpace(responseDetailChild.layer.resource.href) );
					//3.2.2 Compongo l'item da aggiungere
					itemDetailHref							= responseDetailChild.layer.resource.href;
					itemId	 								= appUtil.generateItemId("",responseDetailChild.layer.name);
					itemLayerName							= responseDetailChild.layer.name;
					if ( responseDetailLayer.featureType!=null ) 
						itemLabel								= "<span class='label' title='"+responseDetailLayer.featureType.title+"'>"+responseDetailLayer.featureType.title + "</span>" +
															  		"<i onclick='"+appCatalogo.cbkOnClick+"(\""+itemLayerName+"\",\""+responseDetailLayer.featureType.title+"\",\""+itemDetailHref+"\")' style='float:right' class='fa fa-plus' aria-hidden='true' title='Aggiungi'></i>";
					else if ( responseDetailLayer.coverage!=null )
						itemLabel								= "<span class='label' title='"+responseDetailLayer.coverage.title+"'>"+responseDetailLayer.coverage.title + "</span>" +
				  													"<i onclick='"+appCatalogo.cbkOnClick+"(\""+itemLayerName+"\",\""+responseDetailLayer.coverage.title+"\",\""+itemDetailHref+"\")' style='float:right' class='fa fa-plus' aria-hidden='true' title='Aggiungi'></i>";						
					itemSelected							= appCatalogo.isDefaultView( responseDetailChild );
					itemIcon								= appConfig.itemIcon["layer"];
					itemLiAttr								= {"class":"liLayer"};
					itemMenu 								= {"id"		:itemId, 
															   "text"	:itemLabel, 
															   "parent"	:parent, 
															   "state"	:{
																   			"selected":itemSelected
															   },
															   "icon"	:itemIcon, 
															   "li_attr":itemLiAttr
															  };
					//3.2.2 Aggiungo l'item al menu
					appConfig.menuJson.core.data			.push(itemMenu);
					//3.2.3 Aggiungo l'item agli elementi caricati 
					appConfig.itemLoaded[itemId]			= itemMenu;
					//3.2.4 Aggiungo l'item LAYER ai layer già caricati
					appConfig.layerMap[itemId]				= responseDetailChild;
					//3.2.5 Gestione del defaultView con applicazione del layer
					//3.2.6 Caricamento dei campi per un item di tipo Layer
					appMappa.caricaFieldsFromResponse( responseDetailChild );
					//3.2.7 Caricamento della ricerca per un item di tipo Layer
				}
			});
		},
		
		/**
		 * Ricarico il menu' con il nuovo MenuJson
		 * 
		 * @param
		 * @return
		 */
		refreshMenu 	: function (){
			if (appCatalogo.doRefresh) {
				appConfig.jsTreeMenu.jstree(true).settings.core.data = appConfig.menuJson.core.data;
				appConfig.jsTreeMenu.jstree("refresh");
			}
		},
		
		/**
		 * La function e' utile per verificare se l'item del menu ha la proprieta' defaultView impostata a true
		 * 
		 * @param responseGeoserver, la risposta della chiamata a geoserver per il dettaglio di layergroup o layer
		 * @return boolean, true se l'item ha la proprieta' true
		 */
		isDefaultView 	: function ( responseGeoserver ){
			var defaultView = false;
			if (responseGeoserver.layer) {
				var responseGeoserverChild = appRest.restGeoserver( appUtil.replaceNameSpace(responseGeoserver.layer.resource.href) );
				if (responseGeoserverChild.featureType!=null && responseGeoserverChild.featureType.keywords!=null) {
					$.each( responseGeoserverChild.featureType.keywords.string, function( key, val ) { 
						if ( val.indexOf("defaultView")!=-1)
							defaultView = val.substring( val.indexOf("@")+1,val.length);
					});
				}
			}
			if (responseGeoserver.layerGroup!=null && responseGeoserver.layerGroup.keywords!=null) {
				$.each( responseGeoserver.layerGroup.keywords.string, function( key, val ) {
					if ( val.indexOf("defaultView")!=-1 && 
							( (val.substring(val.indexOf("vocabulary=")+11,val.length-2)).toLowerCase()=="true" || (val.substring(val.indexOf("@")+1,val.length)).toLowerCase()=="true"))
						defaultView = true;
					else
						defaultView = false;
				});
			}
			return defaultView=="true"?true:false;
		},
		
		/**
		 * La function e' utile per verificare se l'item del menu ha la proprieta' defaultView impostata a true
		 * 
		 * @param responseGeoserver, la risposta della chiamata a geoserver per il dettaglio di layergroup o layer
		 * @return boolean, true se l'item ha la proprieta' true
		 */
		isFilterdView 	: function ( responseGeoserver ){
			var filtView = false;
			if ( appCatalogo.filter_C!=null ) {
				if (responseGeoserver.layer!=null && responseGeoserver.layer) {
					var responseGeoserverChild = appRest.restGeoserver( appUtil.replaceNameSpace(responseGeoserver.layer.resource.href) );
					if (responseGeoserverChild.featureType!=null && responseGeoserverChild.featureType.keywords!=null) {
						$.each( responseGeoserverChild.featureType.keywords.string, function( key, val ) {
							if (val.indexOf("vocabulary=")!=-1 && val.indexOf( appCatalogo.filter_C )!=-1) {
								    filtView = val.substring( val.indexOf("vocabulary=")+11,val.length-2).toLowerCase();
						    } else if ( val.indexOf("vocabulary=")==-1 && val.indexOf( appCatalogo.filter_C )!=-1) {
								    filtView = val.substring( val.indexOf("@")+1,val.length).toLowerCase();
						    }
						});
					}
				} else if (responseGeoserver.layerGroup!=null && responseGeoserver.layerGroup) {
					if (responseGeoserver.layerGroup.keywords!=null) {
						$.each( responseGeoserver.layerGroup.keywords.string, function( key, val ) { 
							if (val.indexOf("vocabulary=")!=-1 && val.indexOf( appCatalogo.filter_C )!=-1) {
								filtView = val.substring( val.indexOf("vocabulary=")+11,val.length-2).toLowerCase();
						    } else if ( val.indexOf("vocabulary=")==-1 && val.indexOf( appCatalogo.filter_C )!=-1) {
						    	filtView = val.substring( val.indexOf("@")+1,val.length).toLowerCase();
						    }
						});
					}
				}
			}
			return filtView=="true"?true:false;
		},
		
		/**
		 * La function rimuove l'item fittizio con prefisso "NaN_". Viene eliminato dalle liste:
		 * 	- appConfig.menuJson.core.data
		 * 	- appCatalogo.itemNoChild
		 * 
		 * @param itemId, identificativo dell'item da eliminare
		 */
		removeItemBlank : function ( itemId ){
			iDel = null;
			i = 0;
			$.each( appConfig.menuJson.core.data, function (key, value) {
				if (value.id==itemId) 
					iDel = i;
				i++;
			});
			if (iDel!=null)
				(appConfig.menuJson.core.data).splice(iDel,1);
			
			if (appCatalogo.itemNoChild!=null && appCatalogo.itemNoChild!=undefined && (appCatalogo.itemNoChild)[itemId]!=null)
				delete (appCatalogo.itemNoChild)[itemId];
		},
		
		/**
		 * La function verifica se un item e' stato gia' caricato nel MenuJson
		 * 
		 * @param parentId, identificativo del padre dell'item
		 * @param itemId, identificativo dell'item da eliminare
		 */
		isLoaded : function ( parentId, itemId){
			item = null;
			$.each( appConfig.menuJson.core.data, function (key, value) {
				if (value.id==itemId && value.parent==parentId)
					item = value;
			});
			return item;
		},
		/**************************************************************************************************************************************************/
        /********* CATALOGO GENERALE ************************************************************************************************************* FINE ***/
		/**************************************************************************************************************************************************/		
		
		
		
		
		
		
		
		
		/**************************************************************************************************************************************************/
        /********* SOLO PER IL CATALOGO ******************************************************************************************************** INIZIO ***/
		/**************************************************************************************************************************************************/
		/**
		 * Recupero delle aree tematiche da geoserver. 
		 * Di seguito alcune caratteristiche della function:
		 * 	- Se l'aree tematiche presenta la keyword defaultView[true], la function carica i figli
		 * 	- Se il tematismo presenta la keyword defaultView[true], la function carica i figli
		 * 	- Se il layergroup presenta la keyword defaultView[true], la function carica i figli
		 * 
		 * N.b. Le aree tematiche sono LayerGroup con suffisso "_areaTematica", i tematismi sono LayerGroup con suffisso "_tematismo",
		 * i layergroup sono LayerGroup con suffisso "_layergroup".
		 * 
		 * @param
		 * @returns 
		 */
		getStartCatalogo 	: function (){
			
			appConfig					.restartCatalogJson(); 
			href 						= appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "workspaces/"+appConfig.workspaceGeoserver+"/layergroups.json";
			responseListAreeTematiche 	= appRest.restGeoserver( href );
			
			if(responseListAreeTematiche!=null && responseListAreeTematiche.layerGroups!=null && responseListAreeTematiche.layerGroups.layerGroup!=null){
				
				numItemHide = 0;
				numItem = 0;
				$.each( responseListAreeTematiche.layerGroups.layerGroup, function( keyAreeTematiche, valueAreeTematiche ) {
					/**DETTAGLIO AREA TEMATICA**/
					if ( (valueAreeTematiche.name).indexOf("_areaTematica")!=-1 ) {
						numItem++;
						responseDetailAT = appRest.restGeoserver( appUtil.replaceNameSpace(valueAreeTematiche.href) );
						if( responseDetailAT!=null && responseDetailAT.layerGroup!=null ){
							
							itemId	 								= appUtil.generateItemId("",responseDetailAT.layerGroup.name);
							itemLabel								= responseDetailAT.layerGroup.title;
							itemOpened								= appCatalogo.isDefaultView( responseDetailAT );
							itemIcon								= appConfig.itemC_Icon["folder"];
							itemMenu 								= {"id":itemId, "text":itemLabel, "parent":"#", "state":{"disabled":true, "opened":itemOpened}, "icon":itemIcon, "li_attr":{"class":"liAreaTematica"}, "a_attr":{"class":"aAreaTematica"}};
							appConfig.catalogoJson.core.data			.push(itemMenu);
							appConfig.itemC_Loaded[itemId]			= $.isArray(responseDetailAT.layerGroup.publishables.published) ? responseDetailAT.layerGroup.publishables.published : [responseDetailAT.layerGroup.publishables.published];
							if ( itemOpened ) {
								appCatalogo.getItemCatalogo( itemId );
							} else {
								itemMenuEmpty								= {"id":"NaN_"+itemId, "text":"", "parent":itemId, "state":{}};
								appConfig.catalogoJson.core.data			.push(itemMenuEmpty);
								appCatalogo.itemC_NoChild["NaN_"+itemId]	= itemMenuEmpty;
							}
						} else {
							/**TODO: verificare nel caso di aree tematica non corretta **/
							appUtil.setMessage("Errore nel recuperare il dettaglio dell'area tematica");
						}
					}
				});
			} else {
				/**TODO: verificare nel caso di aree tematiche non presenti **/
				appUtil.setMessage(responseListAreeTematiche.message);
				$("#empty-menu-content").text( appConfig.emptyMenu );
			}
		}, 
		
		/**
		 * Inizializzo il Menu (JsTree)
		 * Nella seguente function vengono definite le azioni possibili sull'albero. Sono le seguenti:
		 * 	- APRO NODO MENU (open_node.jstree)
		 * 	- CHIUDO NODO MENU (close_node.jstree)
		 * 	- (disabilitato) SELEZIONO NODO MENU (select_node.jstree)
		 * 	- (disabilitato) DESELEZIONO NODO MENU (deselect_node.jstree)
		 * 	- (disabilitato) CAMBIO NEL MENU (changed.jstree)
		 * 	- (disabilitato) RICARICAMENTO DEL MENU (refresh.jstree)
		 */
		initializeCatalogo 	: function ( idMenu, cbkOnClick, mesCbk ) {
			
			/**IMPOSTO LA CALLBACK**/
			appCatalogo.cbkOnClick_C 	= cbkOnClick;
			appCatalogo.mesCbk_C 		= 'Aggiungi al visualizzatore';
			
			/**ORDINO I DATI**/
			(appConfig.catalogoJson.core.data).sort( 
				function(a, b){
					var nameA=a.text.toLowerCase(), nameB=b.text.toLowerCase()
					if (nameA < nameB) //sort string ascending
						return -1 
					if (nameA > nameB)
						return 1
					return 0 //default return value (no sorting)
				} 
			);
			
			/**INIZIALIZZO IL CATALOGO **/
			appConfig.jsTreeCatalogo = $('#'+idMenu).jstree( appConfig.catalogoJson );
			
			
			/**************************************************************************************************************************/
			/************ GESTIONE DELLE ACTION SUL CATALOGO ************************************************************ INIZIO   ****/
			/*APRO NODO MENU*/
			appConfig.jsTreeCatalogo.on("open_node.jstree", function (e, data) {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				setTimeout(function(e) {
					appCatalogo.doRefresh = false;
					//1. Verifico se l'item che voglio aprire contiene figli non censiti
					if (data.node.id!=null && appCatalogo.itemC_NoChild["NaN_"+data.node.id]!=null) {
						//1.1 Carico gli item non presenti
						appCatalogo.getItemCatalogo( data.node.id );
						//1.2 Rimuovo l'item empty dal appConfig.catalogoJson
						appCatalogo.removeItemC_Blank( "NaN_"+data.node.id );
						//1.3 Rimuovo l'item empty dall'elenco degli item NaN					
						delete appCatalogo.itemC_NoChild["NaN_"+data.node.id];
						//1.4 Imposto l'aggiornamento del menu
						appCatalogo.doRefresh = true;
					}
					//2. Imposto l'item che sto aprendo, solo opened=true
					$.each( appConfig.catalogoJson.core.data, function( key, value ) {
						if (value.id==data.node.id) {
							value.state.opened = true;
						}
					});
					//3. id dell'item aperto utile per settare la top nel menu
					appCatalogo.itemC_Open = data.node.id;
					//4. Aggiorno mappa se devo aggiornarla (appCatalogo.doRefresh)
					appCatalogo.itemC_TEtoOpen = data.node.id;
					appCatalogo.refreshCatalogo();
					appUtil.hideLoader();
				}, 200);
			});
			
			/*CHIUDO NODO MENU*/
			appConfig.jsTreeCatalogo.on("close_node.jstree", function (e, data) {
				appCatalogo.doRefresh = false;
				//1. Imposto l'item che sto chiudendo, opened=false
				$.each( appConfig.catalogoJson.core.data, function( key, value ) {
					if (value.id==data.node.id) {
						value.state.opened = false;
					}
				});
				//2. Elimino dalla mappa i layer figli dell'item che sto chudendo
				$.each( appConfig.catalogoJson.core.data, function( key, value ) {
					if (value.parent==data.node.id) {
					}
				});
				//3. id dell'item aperto utile per settare la top nel menu
				appCatalogo.itemC_Open = data.node.id;
				//4. Aggiorno mappa se devo aggiornarla (appCatalogo.doRefresh)
				appCatalogo.refreshCatalogo();
			});
			
//			/*SELEZIONO NODO MENU*/
//			appConfig.jsTreeCatalogo.on("select_node.jstree", function(evt, data){
//				appCatalogo.doRefresh = false;
//				//1. Verifico se l'item che voglio aprire contiene figli non censiti
//				if (data.node.id!=null && appCatalogo.itemC_NoChild["NaN_"+data.node.id]!=null) {
//					//1.1 Carico gli item non presenti
//					appCatalogo.getItemMenu( data.node.id );
//					//1.2 Rimuovo l'item empty dal appConfig.catalogoJson
//					appCatalogo.removeItemC_Blank( "NaN_"+data.node.id );
//					//1.3 Rimuovo l'item empty dall'elenco degli item NaN					
//					delete appCatalogo.itemC_NoChild["NaN_"+data.node.id];
//					//1.4 Imposto l'aggiornamento del menu
//					appCatalogo.doRefresh = true;
//				}
//				//2. Imposto l'item che sto aprendo, solo opened=true
//				$.each( appConfig.catalogoJson.core.data, function( key, value ) {
//					if (value.id==data.node.id) {
//						value.state.opened = true;
//					}
//				});
//				//3. Aggiorno mappa se devo aggiornarla (appCatalogo.doRefresh)
//				appCatalogo.itemC_TEtoOpen = data.node.id;
//				appCatalogo.refreshCatalogo();
//			});
			/************ GESTIONE DELLE ACTION SUL CATALOGO ************************************************************ FINE     ****/
			/**************************************************************************************************************************/
			
			
			/**RICARICAMENTO DEL MENU**/
			appConfig.jsTreeCatalogo.on('refresh.jstree', function () {
				if (appCatalogo.itemC_Open!="") {
					if ( $("#"+appCatalogo.itemC_Open).length > 0 ) {
						//Layer
						newTop = $("#"+appCatalogo.itemC_Open).position().top;
					} else if ( $("#"+appCatalogo.itemC_Open+"_ATE_anchor").length > 0 ) {
						//Area tematica
						newTop = $("#"+appCatalogo.itemC_Open+"_ATE_anchor").position().top;
					} else if ( $("#"+appCatalogo.itemC_Open+"_TE_anchor").length > 0 ) {
						//Tematismo
						newTop = $("#"+appCatalogo.itemC_Open+"_TE_anchor").position().top;
					} 
					$('.boxMappa_body').animate({scrollTop: (newTop-40)},'slow');
				}
			});
		},
		
		/**
		 * Recupero, dato il parent, gli item figli
		 * 
		 * @param parent, id del padre
		 * @returns 
		 */
		getItemCatalogo : function( parent ){
			//1. Recupero oggetto caricato
			parentChildren 	= appConfig.itemC_Loaded[parent];
			numItemHide 	= 0;
			$.each( parentChildren, function( keyChild, valueChild ) {
				//2. Per ogni oggetto caricato recupero i figli
				responseDetailChild = appRest.restGeoserver( appUtil.replaceNameSpace(valueChild.href) );
				//3. Gestione del figlio
				if( responseDetailChild!=null && responseDetailChild.layerGroup!=null && responseDetailChild.layerGroup.name.indexOf("DEFAULT_")==-1 && 
						appCatalogo.isLoaded_C(parent, appUtil.generateItemId("",responseDetailChild.layerGroup.name))==null ){
					/**###############	GESTIONE DEL LAYERGROUP	#############**/
					/**3.1 Se il figlio dell'oggetto già caricato è un layergroup e non è stato già caricato, lo carico**/ 

					//3.1.1 Compongo l'item da aggiungere
					itemId	 								= appUtil.generateItemId("",responseDetailChild.layerGroup.name);
					itemLabel								= responseDetailChild.layerGroup.title; 
					itemOpened								= appCatalogo.isDefaultView( responseDetailChild );
					itemIcon								= itemId.indexOf("_LG")!=-1?appConfig.itemC_Icon["folder_layer"]:appConfig.itemC_Icon["folder"];
					itemLiAttr								= itemId.indexOf("_LG")!=-1?{"class":"liLayergroup"}:{"class":"liTematismo"};
					itemMenu 								= {"id":itemId, "text":itemLabel, "parent":parent, "state":{"opened":itemOpened}, "icon":itemIcon, "li_attr":itemLiAttr};
					
					//3.1.2 Aggiungo l'item al menu
					appConfig.catalogoJson.core.data			.push(itemMenu);
					//3.1.3 Aggiungo l'item agli elementi caricati 
					appConfig.itemC_Loaded[itemId]			= $.isArray(responseDetailChild.layerGroup.publishables.published) ? responseDetailChild.layerGroup.publishables.published : [responseDetailChild.layerGroup.publishables.published];
					//3.1.4 Elimino l'elemento NaN
					appCatalogo									.removeItemC_Blank( "NaN_"+parent );
					//3.1.5 Gestione del defaultView con caricamento dei figli o aggiunta di un figlio fittizio
					if ( itemOpened ) {
						appCatalogo.getItemCatalogo( itemId );
					} else {
						itemMenuEmpty							= {"id":"NaN_"+itemId, "text":"", "parent":itemId, "state":{} };
						appConfig.catalogoJson.core.data			.push(itemMenuEmpty);
						appCatalogo.itemC_NoChild["NaN_"+itemId]		= itemMenuEmpty;
					}
				} else if ( responseDetailChild!=null && responseDetailChild.layer!=null && responseDetailChild.layer.name.indexOf("DEFAULT_")==-1 &&
						appCatalogo.isLoaded_C(parent, appUtil.generateItemId("",responseDetailChild.layer.name))==null ) {
					/**###############	GESTIONE DEL LAYER	#################**/
					/**3.2 Se il figlio dell'oggetto già caricato è un layer e non è stato già caricato, lo carico**/
					//3.2.1 Recupero il dettaglio del Layer
					responseDetailLayer = appRest.restGeoserver( appUtil.replaceNameSpace(responseDetailChild.layer.resource.href) );
					
					/**CONTROLLO SE DEVO CARICARE IL RAMO**/
					if ( (appCatalogo.filter_C!=null && appCatalogo.isFilterdView( responseDetailChild )) || 
							appCatalogo.filter_C==null ) {																//CARICO IL RAMO
						//3.2.2 Compongo l'item da aggiungere
						
						itemDetailHref							= responseDetailChild.layer.resource.href;
						itemId	 								= appUtil.generateItemId("",responseDetailChild.layer.name);
						itemLayerName							= responseDetailChild.layer.name;
						if ( responseDetailLayer.featureType!=null ) {
							itemLabel								= "<span class='label' title='"+responseDetailLayer.featureType.title+"'>"+responseDetailLayer.featureType.title + "</span>" +
																  		"<i style='float:right' class='fa fa-plus' aria-hidden='true' title=\""+appCatalogo.mesCbk_C+"\"></i>";
							itemAAttr								= ""+appCatalogo.cbkOnClick_C+"(\""+itemLayerName+"\",\""+responseDetailLayer.featureType.title+"\",\""+itemDetailHref+"\")";
						}
						else if ( responseDetailLayer.coverage!=null ) {
							itemLabel								= "<span class='label' title='"+responseDetailLayer.coverage.title+"'>"+responseDetailLayer.coverage.title + "</span>" +
					  													"<i style='float:right' class='fa fa-plus' aria-hidden='true' title=\""+appCatalogo.mesCbk_C+"\"></i>";
							itemAAttr								= ""+appCatalogo.cbkOnClick_C+"(\""+itemLayerName+"\",\""+responseDetailLayer.coverage.title+"\",\""+itemDetailHref+"\")";				
						}
							
						itemSelected							= appCatalogo.isDefaultView( responseDetailChild );
						itemIcon								= appConfig.itemC_Icon["layer"];
						itemLiAttr								= {"class":"liLayer"};
						itemMenu 								= {"id"		:itemId, 
																   "text"	:itemLabel, 
																   "parent"	:parent, 
																   "state"	:{
																	   			"selected":itemSelected
																   },
																   "icon"	:itemIcon, 
																   "li_attr":itemLiAttr,
																   "a_attr": {"onclick": itemAAttr }
																  };
						//3.2.2 Aggiungo l'item al menu
						appConfig.catalogoJson.core.data			.push(itemMenu);
						//3.2.3 Aggiungo l'item agli elementi caricati 
						appConfig.itemC_Loaded[itemId]			= itemMenu;
					} else {
						numItemHide++;
					}
				}
			});
			if ( numItemHide==parentChildren.length ) {
				//3.2.2 Compongo l'item da aggiungere
				itemDetailHref							= "";
				itemId	 								= appCatalogo.idCount_C++;
				itemLayerName							= "Nessun Layer";
				itemIcon								= appConfig.itemC_Icon["folder_layer"];
				itemLabel								= "<span class='label text-red' title='Nessun Layer per Estrazione Particellare'>Nessun Layer per Estrazione Particellare</span>";
				itemSelected							= false;
				itemMenu 								= {"id"		:itemId, 
														   "text"	:itemLabel, 
														   "parent"	:parent, 
														   "state"	:{
															   			"selected":itemSelected
														   },
														   "icon"	:itemIcon
														  };
				//3.2.2 Aggiungo l'item al menu
				appConfig.catalogoJson.core.data			.push(itemMenu);
				//3.2.3 Aggiungo l'item agli elementi caricati 
				appConfig.itemC_Loaded[itemId]			= itemMenu;	
			}
		},
		
		/**
		 * La function verifica se un item e' stato gia' caricato nel MenuJson
		 * 
		 * @param parentId, identificativo del padre dell'item
		 * @param itemId, identificativo dell'item da eliminare
		 */
		isLoaded_C : function ( parentId, itemId){
			item = null;
			$.each( appConfig.catalogoJson.core.data, function (key, value) {
				if (value.id==itemId && value.parent==parentId)
					item = value;
			});
			return item;
		},
		
		/**
		 * La function rimuove l'item fittizio con prefisso "NaN_". Viene eliminato dalle liste:
		 * 	- appConfig.catalogoJson.core.data
		 * 	- appCatalogo.itemC_NoChild
		 * 
		 * @param itemId, identificativo dell'item da eliminare
		 */
		removeItemC_Blank : function ( itemId ){
			iDel = null;
			i = 0;
			$.each( appConfig.catalogoJson.core.data, function (key, value) {
				if (value.id==itemId) 
					iDel = i;
				i++;
			});
			if (iDel!=null)
				(appConfig.catalogoJson.core.data).splice(iDel,1);
			
			if (appCatalogo.itemC_NoChild!=null && appCatalogo.itemC_NoChild!=undefined && (appCatalogo.itemC_NoChild)[itemId]!=null)
				delete (appCatalogo.itemC_NoChild)[itemId];
		},
		
		/**
		 * Ricarico il menu' con il nuovo MenuJson
		 * 
		 * @param
		 * @return
		 */
		refreshCatalogo 	: function (){
			if (appCatalogo.doRefresh) {
				appConfig.jsTreeCatalogo.jstree(true).settings.core.data = appConfig.catalogoJson.core.data;
				appConfig.jsTreeCatalogo.jstree("refresh");
			}
		},
		/**************************************************************************************************************************************************/
        /********* SOLO PER IL CATALOGO ********************************************************************************************************** FINE ***/
		/**************************************************************************************************************************************************/		
		
		
		/**************************************************************************************************************************************************/
        /********* SOLO PER IL CATALOGO GESTIONE MAPPE ******************************************************************************************************** INIZIO ***/
		/**************************************************************************************************************************************************/		
		/**
		 * Inizializzo il Menu (JsTree)
		 * Nella seguente function vengono definite le azioni possibili sull'albero. Sono le seguenti:
		 * 	- APRO NODO MENU (open_node.jstree)
		 * 	- CHIUDO NODO MENU (close_node.jstree)
		 * 	- SELEZIONO NODO MENU (select_node.jstree)
		 */
		initializeCatalogoGestMappe 	: function ( idMenu, cbkOnClick, mesCbk ) {
			
			/**IMPOSTO LA CALLBACK**/
			appCatalogo.cbkOnClick_C 	= cbkOnClick;
			appCatalogo.mesCbk_C 		= 'Aggiungi al visualizzatore';
			
			/**ORDINO I DATI**/
			(appConfig.catalogoJson.core.data).sort( 
				function(a, b){
					var nameA=a.text.toLowerCase(), nameB=b.text.toLowerCase()
					if (nameA < nameB) //sort string ascending
						return -1 
					if (nameA > nameB)
						return 1
					return 0 //default return value (no sorting)
				} 
			);
			
			/**INIZIALIZZO IL CATALOGO **/
			appConfig.jsTreeCatalogo = $('#'+idMenu).jstree( appConfig.catalogoJson );
			
			
			/**************************************************************************************************************************/
			/************ GESTIONE DELLE ACTION SUL CATALOGO ************************************************************ INIZIO   ****/
			/*APRO NODO MENU*/
			appConfig.jsTreeCatalogo.on("open_node.jstree", function (e, data) {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				setTimeout(function(e) {
					appCatalogo.doRefresh = false;
					//1. Verifico se l'item che voglio aprire contiene figli non censiti
					if (data.node.id!=null && appCatalogo.itemC_NoChild["NaN_"+data.node.id]!=null) {
						//1.1 Carico gli item non presenti						
						appCatalogo.getItemCatalogoGestMappe( data.node.id );
						//1.2 Rimuovo l'item empty dal appConfig.catalogoJson
						appCatalogo.removeItemC_Blank( "NaN_"+data.node.id );
						//1.3 Rimuovo l'item empty dall'elenco degli item NaN					
						delete appCatalogo.itemC_NoChild["NaN_"+data.node.id];
						//1.4 Imposto l'aggiornamento del menu
						appCatalogo.doRefresh = true;
					}
					//2. Imposto l'item che sto aprendo, solo opened=true
					$.each( appConfig.catalogoJson.core.data, function( key, value ) {
						if (value.id==data.node.id) {
							value.state.opened = true;
						}
					});
					//3. id dell'item aperto utile per settare la top nel menu
					appCatalogo.itemC_Open = data.node.id;
					//4. Aggiorno mappa se devo aggiornarla (appCatalogo.doRefresh)
					appCatalogo.itemC_TEtoOpen = data.node.id;
					appCatalogo.refreshCatalogo();
					appUtil.hideLoader();
				}, 200);
				
			});
			
			/*CHIUDO NODO MENU*/
			appConfig.jsTreeCatalogo.on("close_node.jstree", function (e, data) {
				appCatalogo.doRefresh = false;
				//1. Imposto l'item che sto chiudendo, opened=false
				$.each( appConfig.catalogoJson.core.data, function( key, value ) {
					if (value.id==data.node.id) {
						value.state.opened = false;
					}
				});
				//2. Elimino dalla mappa i layer figli dell'item che sto chudendo
				$.each( appConfig.catalogoJson.core.data, function( key, value ) {
					if (value.parent==data.node.id) {
					}
				});
				//3. id dell'item aperto utile per settare la top nel menu
				appCatalogo.itemC_Open = data.node.id;
				//4. Aggiorno mappa se devo aggiornarla (appCatalogo.doRefresh)
				appCatalogo.refreshCatalogo();
			});

			/************ GESTIONE DELLE ACTION SUL CATALOGO ************************************************************ FINE     ****/
			/**************************************************************************************************************************/
			
			
			/**RICARICAMENTO DEL MENU**/
			appConfig.jsTreeCatalogo.on('refresh.jstree', function () {
				if (appCatalogo.itemC_Open!="") {
					if ( $("#"+appCatalogo.itemC_Open).length > 0 ) {
						//Layer
						newTop = $("#"+appCatalogo.itemC_Open).position().top;
					} else if ( $("#"+appCatalogo.itemC_Open+"_ATE_anchor").length > 0 ) {
						//Area tematica
						newTop = $("#"+appCatalogo.itemC_Open+"_ATE_anchor").position().top;
					} else if ( $("#"+appCatalogo.itemC_Open+"_TE_anchor").length > 0 ) {
						//Tematismo
						newTop = $("#"+appCatalogo.itemC_Open+"_TE_anchor").position().top;
					} 
					$('.boxMappa_body').animate({scrollTop: (newTop-40)},'slow');
				}
			});
		},

		/**
		 * Recupero, dato il parent, gli item figli
		 * 
		 * @param parent, id del padre
		 * @returns 
		 */
		getItemCatalogoGestMappe : function( parent ){
			//1. Recupero oggetto caricato
			parentChildren 	= appConfig.itemC_Loaded[parent];
			numItemHide 	= 0;
			$.each( parentChildren, function( keyChild, valueChild ) {
				appCatalogo.countLayer++;
				//2. Per ogni oggetto caricato recupero i figli
				responseDetailChild = appRest.restGeoserver( appUtil.replaceNameSpace(valueChild.href) );
				sessionStorage.setItem('responseDetailChild', JSON.stringify(responseDetailChild));
				//3. Gestione del figlio
				if( responseDetailChild!=null && responseDetailChild.layerGroup!=null && responseDetailChild.layerGroup.name.indexOf("DEFAULT_")==-1 && 
						appCatalogo.isLoaded_C(parent, appUtil.generateItemId("",responseDetailChild.layerGroup.name))==null ){
					/**###############	GESTIONE DEL LAYERGROUP	#############**/
					/**3.1 Se il figlio dell'oggetto già caricato è un layergroup e non è stato già caricato, lo carico**/ 

					//3.1.1 Compongo l'item da aggiungere
					itemId	 								= appUtil.generateItemId("",responseDetailChild.layerGroup.name);
					itemLabel								= ($.isArray(responseDetailChild.layerGroup.publishables.published) && responseDetailChild.layerGroup.publishables.published != undefined) > 0 || responseDetailChild.layerGroup.publishables.published != undefined && responseDetailChild.layerGroup.publishables.published.name.indexOf("_layergroup") == -1 ? "<span class='label' title='"+ responseDetailChild.layerGroup.title + "'>" + responseDetailChild.layerGroup.title + "</span> <i style='float:right;margin: 5px 0 5px 0;' class='fa fa-plus' aria-hidden='true' title=\""+appCatalogo.mesCbk_C+"\" onclick='aggiungiTuttiLivelli(\""+itemId+"\",\""+valueChild.href+"\")'></i>" : "<span class='label' title='"+ responseDetailChild.layerGroup.title + "'>" + responseDetailChild.layerGroup.title + "</span>" ;
					itemOpened								= appCatalogo.isDefaultView( responseDetailChild );
					itemIcon								= itemId.indexOf("_LG")!=-1?appConfig.itemC_Icon["folder_layer"]:appConfig.itemC_Icon["folder"];
					itemLiAttr								= itemId.indexOf("_LG")!=-1?{"class":"liLayergroup"}:{"class":"liTematismo"};
					itemMenu 								= {"id":itemId, "text":itemLabel, "parent":parent, "state":{"opened":itemOpened}, "icon":itemIcon, "li_attr":itemLiAttr};
					
					//3.1.2 Aggiungo l'item al menu
					appConfig.catalogoJson.core.data			.push(itemMenu);
					//3.1.3 Aggiungo l'item agli elementi caricati 
					appConfig.itemC_Loaded[itemId]			= $.isArray(responseDetailChild.layerGroup.publishables.published) ? responseDetailChild.layerGroup.publishables.published : [responseDetailChild.layerGroup.publishables.published];
					//3.1.4 Elimino l'elemento NaN
					appCatalogo									.removeItemC_Blank( "NaN_"+parent );
					//3.1.5 Gestione del defaultView con caricamento dei figli o aggiunta di un figlio fittizio
					appCatalogo.getItemCatalogoGestMappe( itemId );
				} else if ( responseDetailChild!=null && responseDetailChild.layer!=null && responseDetailChild.layer.name.indexOf("DEFAULT_")==-1 &&
						appCatalogo.isLoaded_C(parent, appUtil.generateItemId("",responseDetailChild.layer.name))==null ) {
					/**###############	GESTIONE DEL LAYER	#################**/
					/**3.2 Se il figlio dell'oggetto già caricato è un layer e non è stato già caricato, lo carico**/
					console.log(responseDetailChild.layerGroup);
					//3.2.1 Recupero il dettaglio del Layer
					responseDetailLayer = appRest.restGeoserver( appUtil.replaceNameSpace(responseDetailChild.layer.resource.href) );
					
					/**CONTROLLO SE DEVO CARICARE IL RAMO**/
					if ( (appCatalogo.filter_C!=null && appCatalogo.isFilterdView( responseDetailChild )) || 
							appCatalogo.filter_C==null ) {																//CARICO IL RAMO
						//3.2.2 Compongo l'item da aggiungere
						
						itemDetailHref							= responseDetailChild.layer.resource.href;
						itemId	 								= appUtil.generateItemId("",responseDetailChild.layer.name);
						itemLayerName							= responseDetailChild.layer.name;
						if ( responseDetailLayer.featureType!=null ) 
							itemLabel								= "<span class='label' title='"+responseDetailLayer.featureType.title+"'>"+responseDetailLayer.featureType.title + "</span>" +
																  		"<i id='"+ itemId +"' onclick='"+appCatalogo.cbkOnClick_C+"(\""+itemLayerName+"\",\""+responseDetailLayer.featureType.title+"\",\""+itemDetailHref+"\")' style='float:right' class='fa fa-plus' aria-hidden='true' title=\""+appCatalogo.mesCbk_C+"\"></i>";
						else if ( responseDetailLayer.coverage!=null )
							itemLabel								= "<span class='label' title='"+responseDetailLayer.coverage.title+"'>"+responseDetailLayer.coverage.title + "</span>" +
					  													"<i id='"+ itemId +"' onclick='"+appCatalogo.cbkOnClick_C+"(\""+itemLayerName+"\",\""+responseDetailLayer.coverage.title+"\",\""+itemDetailHref+"\")' style='float:right' class='fa fa-plus' aria-hidden='true' title=\""+appCatalogo.mesCbk_C+"\"></i>";						
						itemSelected							= appCatalogo.isDefaultView( responseDetailChild );
						itemIcon								= appConfig.itemC_Icon["layer"];
						itemLiAttr								= {"class":"liLayer"};
						itemMenu 								= {"id"		:itemId, 
																   "text"	:itemLabel, 
																   "parent"	:parent, 
																   "state"	:{
																	   			"selected":itemSelected
																   },
																   "icon"	:itemIcon, 
																   "li_attr":itemLiAttr
																  };
						//3.2.2 Aggiungo l'item al menu
						appConfig.catalogoJson.core.data			.push(itemMenu);
						//3.2.3 Aggiungo l'item agli elementi caricati 
						appConfig.itemC_Loaded[itemId]			= itemMenu;
					} else {
						numItemHide++;
					}
				}
			});
		},
		
		/**************************************************************************************************************************************************/
        /********* SOLO PER IL CATALOGO ********************************************************************************************************** FINE ***/
		/**************************************************************************************************************************************************/
		
		
		
		
		
		
		/**************************************************************************************************************************************************/
        /********* CATALOGO ESTRAZIONI ********************************************************************************************************* INIZIO ***/
		/**************************************************************************************************************************************************/
		/**
		 * Recupero le area tematica da geoserver del tipo typeAreaTematica
		 * 
		 * @param divId, id dove verra' creato il catalogo per le estrazioni
		 * @param typeAreaTematica, il tipo di area tematica e' una proprieta' (Keyword) 
		 * @param cbkOnClick, function da richiamare al click del layer, e la function conterra' l'id del layer e il titolo del layer
		 * 
		 * @returns 
		 */
		getStartCatalogoEstraz 	: function ( divId, typeAreaTematica, cbkOnClick ){
			
			appConfig.itemCatalogoEstraz	= [];
			var areeTematiche 				= {"tematismi":[]};
			
			href 							= appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "workspaces/"+appConfig.workspaceGeoserver+"/layergroups.json";
			listAreaTematiche		 		= appRest.restGeoserver( href );
			if(listAreaTematiche!=null && listAreaTematiche.layerGroups!=null && listAreaTematiche.layerGroups.layerGroup!=null){
				
				$.each( listAreaTematiche.layerGroups.layerGroup, function( keyAreaTematica, valueAreaTematica ) {
					/**DETTAGLIO AREA TEMATICA**/
					if ( (valueAreaTematica.name).indexOf("_areaTematica")!=-1 ) {
						detailAreaTematica = appRest.restGeoserver( appUtil.replaceNameSpace(valueAreaTematica.href) );
						if( detailAreaTematica!=null && detailAreaTematica.layerGroup!=null && appCatalogo.isEstrazioneAT( detailAreaTematica, typeAreaTematica) ){
							areeTematiche.tematismi = appCatalogo.getTematismiEstrazione( detailAreaTematica.layerGroup, cbkOnClick );
						} else {
							appUtil.setMessage("Errore nel recuperare il dettaglio dell'area tematica");
						}
					}
				});
			} else {
				appUtil.setMessage(responseListAreeTematiche.message);
			}
			
			if ( areeTematiche!=null && areeTematiche.tematismi!=null && areeTematiche.tematismi.length>0 ) {
				$("#"+divId).html( compilaTemplate("elementiEstrazione", areeTematiche) );
			}
			
		}, 
		
		/**
		 * Recupero i tematismi da geoserver dove il parent e' l'area tematica del tipo typeAreaTematica
		 * 
		 * @param areaTematica, per il caricamento dei tematismi
		 * @param cbkOnClick, function da richiamare al click del layer, e la function conterra' l'id del layer e il titolo del layer
		 * @returns 
		 */
		getTematismiEstrazione : function( areaTematica, cbkOnClick ){
			
			var tematismi = [];
			//1. Recupero oggetto caricato
			$.each( areaTematica.publishables.published, function( keyTematismo, valueTematismo ) {
				//2. Per ogni oggetto caricato recupero i figli
				detailTematismo = appRest.restGeoserver( appUtil.replaceNameSpace(valueTematismo.href) );
				if( detailTematismo!=null && detailTematismo.layerGroup!=null && detailTematismo.layerGroup.name.indexOf("DEFAULT_")==-1 ){
					var tematismo = {
				            "idTema":appUtil.generateItemId("",detailTematismo.layerGroup.name),
				            "nomeTema":detailTematismo.layerGroup.title,
				            "layers":[]};
					tematismo.layers =  appCatalogo.getLayerEstrazione( detailTematismo.layerGroup, cbkOnClick );
					tematismi.push(tematismo);
				} else {
					appUtil.setMessage("Errore nel recuperare il dettaglio dell'area tematica");
				}			
			});
			return tematismi;
		},
		
		/**
		 * Recupero i layer da geoserver dove il parent e' tematismo-areaTematica del tipo typeAreaTematica
		 * 
		 * @param tematismo, per il caricamento dei tematismi
		 * @param cbkOnClick, function da richiamare al click del layer, e la function conterra' l'id del layer e il titolo del layer
		 * @returns 
		 */
		getLayerEstrazione : function( tematismo, cbkOnClick ){
			
			var layers = [];
			//1. Recupero oggetto caricato
			$.each( tematismo.publishables.published, function( keyLayer, valueLayer ) {
				//2. Per ogni oggetto caricato recupero i figli
				detailLayer = appRest.restGeoserver( appUtil.replaceNameSpace(valueLayer.href) );
				if ( detailLayer!=null && detailLayer.layer!=null && detailLayer.layer.name.indexOf("DEFAULT_")==-1 ) {
					
					detailFeatureLayer = appRest.restGeoserver( appUtil.replaceNameSpace(detailLayer.layer.resource.href) );
					layerId = appUtil.generateItemId("",detailLayer.layer.name);
					layerName = "";
					if ( detailFeatureLayer.featureType!=null ) 
						layerName = detailFeatureLayer.featureType.title;
					else if ( detailFeatureLayer.coverage!=null )
						layerName = detailFeatureLayer.coverage.title;
					var layer = {
								"idLayer":layerId,
								"titleLayer": layerName,
								"cbkOnClick":cbkOnClick
					};
					layers.push(layer);
				}
			});
			return layers;
		},
		
		/**
		 * Verifico se l'area tematica ha la proprieta' passata come input
		 * @param areaTematica
		 * @param typeAreaTematica, stringa utile per filtrare le aree tematiche
		 * @returns {Boolean}
		 */
		isEstrazioneAT : function ( areaTematica, typeAreaTematica ){
			var isValid = false;
			if (areaTematica.layerGroup.keywords !=null && areaTematica.layerGroup.keywords.string !=null) {
                $.each( areaTematica.layerGroup.keywords.string, function( key, keyword ) {
                	if (keyword.indexOf(typeAreaTematica)!=-1) {
                		isValid = true;
                	}
                });                	
			}
			return isValid;
		},
		/**************************************************************************************************************************************************/
        /********* CATALOGO ESTRAZIONI *********************************************************************************************************** FINE ***/
		/**************************************************************************************************************************************************/
		
		/**
		 * La funzione dato il nome del layer recupera la sorgente del layer.
		 * @return String, possibili valori sono 'geoserver e 'db'
		 */
		sorgenteLayer : function ( layerName ) {
			var sorgente = "geoserver";
			var href = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "workspaces/"+appConfig.workspaceGeoserver+"/layers/" +
						layerName+".json";
			var responseDetailLayer = appRest.restGeoserver( appUtil.replaceNameSpace( href ) );
			if(responseDetailLayer!=null && responseDetailLayer.layer!=null && 
					responseDetailLayer.layer.resource!=null && responseDetailLayer.layer.resource.href!=null){
				var hrefSorgente = responseDetailLayer.layer.resource.href;
				var responseSorgenteLayer = appRest.restGeoserver( appUtil.replaceNameSpace( hrefSorgente ) );
				if(responseSorgenteLayer!=null && responseSorgenteLayer.featureType!=null &&
						responseSorgenteLayer.featureType.keywords!=null && responseSorgenteLayer.featureType.keywords.string!=null){
					
					$.each( responseSorgenteLayer.featureType.keywords.string, function( keyString, valueString ) {
						if (valueString.toLowerCase().indexOf("shapedb")!=-1 && valueString.split("@")[1].toLowerCase().indexOf("true")!=-1) {
							sorgente = "db";
						}
					});
				} else {
					
					console.log("");
				}
			} else {
				
				console.log("");
			}
			return sorgente;
		}
};
