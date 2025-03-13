/*******************************************************************************************************************************************************/
/***************************************			FUNZIONALITA' GESTIONE MAPPE		****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/
/**
 * @include configuration.js
 * @include util.js
 * @include rest.js
 */

$(document).ready(function() {
	
	/**Imposto l'header della pagina**/
	appGestMappe.start();
	/**Carico la tabella**/
	appGestMappe.inizializzaDatatableMappe();
	/**Carico la lista di tool, ricerche**/
	loadMapProperties();

	//appGestMappe.inizializzaDatatableTema();

	/********************************************************************************************************************/
	/******************************	conAlert	GESTIONE DELLE AZIONI IN PAGINA		*********************************************/
	/********************************************************************************************************************/
	jQAjaxSupport.get('mappaController') 
    .set("type", 'POST')
    .set("dataType", 'json')
    .set("contentType", 'application/json; charset=utf-8')
    .set("cache", false)
    .setMessage("success","Operazione eseguita con successo!",true,true)
    .setMessage("error","Operazione non eseguita correttamente,riprova!",true,true)
    .setUrlController(appUtil.getUrbamidOrigin() + appConfig.urbamidService +"mappaController")
    .onSuccess(function (data) {
 	   if(data.success && data.aaData && data.aaData!="0"){
		this.initSuccess(data)	
		if(this.show.success)
 		 appUtil.showMessageAlertApplication(this.message.success, "Success", false, false, true);
			
				return data;
			}else{
				if(this.show.error)
				appUtil.showMessageAlertApplication(this.message.error, "Info", true, false, false);
				this.restoreMsg();
				return null;
			}
    })
    .onError(function (jqXHR, textStatus, errorThrown) {appUtil.showMessageAlertApplication("General Error!!", "Error", false, true, false); console.log("error", textStatus,errorThrown); })
    .onComplete(function (jqXHR, textStatus, errorThrown) {  })
	//END   gestionemappa

 jQAjaxSupport.get('layerController') 
    .set("type", 'POST')
    .set("dataType", 'json')
    .set("contentType", 'application/json; charset=utf-8')
    .set("cache", false)
    .setMessage("success","Operazione eseguita con successo!",true,true)
    .setMessage("error","Operazione non eseguita correttamente,riprova!",true,true)
    .setUrlController(appUtil.getUrbamidOrigin() + appConfig.urbamidService + "layerController")
    .onSuccess(function (data) {
 	   if(data.success && data.aaData && data.aaData!="0"){
		this.initSuccess(data)	
		if(this.show.success)
 		 appUtil.showMessageAlertApplication(this.message.success, "Success", false, false, true);
			
				return data;
			}else{
				if(this.show.error)
				appUtil.showMessageAlertApplication(this.message.error, "Info", true, false, false);
				this.restoreMsg();
				return null;
			}
    })
    .onError(function (jqXHR, textStatus, errorThrown) {appUtil.showMessageAlertApplication("General Error!!", "Error", false, true, false); console.log("error", textStatus,errorThrown); })
    .onComplete(function (jqXHR, textStatus, errorThrown) {  })
	//END  gestionelivello
    
    
	/**** NUOVA MAPPA	*****************/
	$(document).on("click", "button.btnew", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			
			appGestMappe.layerOpened = false;
 
			$("#containerTableMappe").hide();
	 
			//MS DISCRIMINO SE IN NUOVO
			 appGestMappe.isNew=true;
			 
			 //MS AGGIORNO L'OGGETTO  

			 appGestMappe.mappaSelected = new getUMappa(); //TABELLA NUOVA
			 appGestMappe.mappaSelected.codice="";
			 appGestMappe.mappaSelected.isNew=true;
			 
			 appGestMappe.showDetailMappa(true);
				/**Gestione**/
			$("#containerDetailMappa").show();
  
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});		
	
	/**** GESTIONE MAPPE	*****************/
	$(document).on("click", ".gestioneMappe", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		mappaSelected = null;
		$("#containerTableMappe").show();
		$("#containerGestioneTemi").hide();
		openSezione(event, 'elenco-tema');
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});	
	
	/****MS FUNCTION DETTAGLIO MAPPA	*****************/	
function showDettaglioMappa(){
	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	
	//MS DISCRIMINO SE IN MODIFICA
	 appGestMappe.isNew=false;
	
	appGestMappe.layerOpened = false;
	if ( appGestMappe.mappeSelectedList.length > 1 || appGestMappe.mappeSelectedList.length == 0 ) {
		appUtil.showMessageAlertApplication('Devi selezionare una sola mappa per il dettaglio', 'Attenzione', false, true, false, null, null);
	} else {
		mappaSelected = null;
		$("#containerTableMappe").hide();
		$.each( appGestMappe.mappeList, function( keyMappa, valueMappa ) {
			if (valueMappa.id == appGestMappe.mappeSelectedList[0] ) {
				appGestMappe.mappaSelected = valueMappa;
			}
		});
		if ( appGestMappe.mappaSelected!=null ) {
			appGestMappe.showDetailMappa();
			 
			var url__="/UrbamidWeb/webgis?authority=ROLE_ACCESSO_WEBGIS&preview=true&codeMappa="+(appGestMappe.mappaSelected).codice;
			$(".visualizzamappa").attr("href",url__);
		}
		
	 
	}
	appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
}	
	
	/**** DETTAGLIO MAPPA	*****************/
	$(document).on("click", "button.dettaglio", function() {
		showDettaglioMappa();
	});	
	/**** RIMUOVI MAPPA	*****************/
	$(document).on("click", ".remove", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		if ( appGestMappe.mappeSelectedList.length > 1 || appGestMappe.mappeSelectedList.length == 0 ) {
			appUtil.showMessageAlertApplication('Devi selezionare una sola mappa per il dettaglio', 'Attenzione', false, true, false, null, null);
		} else {
			mappaSelected = null;
			$("#containerTableMappe").hide();
			$.each( appGestMappe.mappeList, function( keyMappa, valueMappa ) {
				if (valueMappa.id == appGestMappe.mappeSelectedList[0] ) {
					appGestMappe.mappaSelected = valueMappa;
				}
			});
			if ( appGestMappe.mappaSelected!=null ) {
					 appGestMappe.showDetailMappa(); 
			}
			
			var url__="/UrbamidWeb/webgis?authority=ROLE_ACCESSO_WEBGIS&preview=true&codeMappa="+(appGestMappe.mappaSelected).codice;
			$(".visualizzamappa").attr("href",url__);
		}
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});
	/**** NUOVA MAPPA	*****************/
	$(document).on("click", ".gestioneTemi", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		mappaSelected = null;
		$("#containerTableMappe").hide();
		$("#containerGestioneTemi").show();
		openSezione(event, 'elenco-tema');
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});
	/**** MODIFICA MAPPA	*****************/
	$(document).on("click", ".modifica", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		/**GESTIONE VARIABILE DELLA MODIFICA**/
		appGestMappe.modifica	= true;
		appGestMappe.blocked 	= !appGestMappe.blocked;
		 
		 
		
		/**GESTIONE DATI DI BASE - INPUT FORM**/
		if($('#dati-base').is(':visible')){
			var items = $("#dati-base input:text");
			var n = items.length;
		  	if (n > 0) {
		  		items.each(function(idx,item){
		  			var choice = $(item);
		  			choice.attr("readonly", appGestMappe.blocked); 
		  		});
		  	}
		  	
			 
			 //MS
		  	if (appGestMappe.blocked) {	
				appGestMappe.modifica= false;	
		  	 	$("input, select, button, textarea", $("#dati-base")).attr("disabled","disabled")
		  	}else{
		  		appGestMappe.modifica= true;
		  		$("input, select, button, textarea", $("#dati-base")).removeAttr("disabled")
		  	}
		  	
		}
		/**GESTIONE DATI LAYER**/
		if($('#dati-layer').is(':visible')){
			var buttonItems = $('#dati-layer button');
			var nButtons = buttonItems.length;
			if(nButtons > 0) {
				buttonItems.each(function(idx, item) {
					var choice = $(item);
					choice.attr('disabled', appGestMappe.blocked)
				});
			}
			
			var chkItems = $('#dati-layer input:checkbox');
			var nChk = chkItems.length;
			if(nChk > 0) {
				chkItems.each(function(idx, item) {
					var choice = $(item);
					choice.attr('disabled', appGestMappe.blocked)
				});
			}
			
			if(appGestMappe) {
				$(".panel-group").removeClass( "activeGroup" );
			}
			

			$('#importaGruppiSlc').attr('disabled', appGestMappe.blocked);
			$('#mappa_livello_zoom').attr('disabled', appGestMappe.blocked);
			  
		}
		/**GESTIONE DATI RICERCHE**/
		if($('#dati-ricerche').is(':visible')){
		  	var itemsText = $("#dati-ricerche input:text");
			var n = itemsText.length;
		  	if (n > 0) {
		  		itemsText.each(function(idx,item){
		  			var choice = $(item);
		  			choice.attr("readonly", appGestMappe.blocked); 
		  		});
		  	}
		  	var itemsCheck = $("#dati-ricerche input:checkbox");
			var n = itemsCheck.length;
		  	if (n > 0) {
		  		itemsCheck.each(function(idx,item){
		  			var choice = $(item);
		  			choice.attr("disabled", appGestMappe.blocked); 
		  		});
		  	}
		  	/**Buttons**/
		  	var btnItemsAdd = $("button.addElem");
		  	btnItemsAdd.attr("disabled", appGestMappe.blocked); 
		  	var btnItemsRem = $("button.removeElem");
		  	btnItemsRem.attr("disabled", appGestMappe.blocked);
		}
		/**GESTIONE DATI TOOLS**/
		if($('#dati-tools').is(':visible')){
		  	var itemsText = $("#dati-tools input:text");
			var n = itemsText.length;
		  	if (n > 0) {
		  		itemsText.each(function(idx,item){
		  			var choice = $(item);
		  			choice.attr("readonly", appGestMappe.blocked); 
		  		});
		  	}
		  	var itemsCheck = $("#dati-tools input:checkbox");
			var n = itemsCheck.length;
		  	if (n > 0) {
		  		itemsCheck.each(function(idx,item){
		  			var choice = $(item);
		  			choice.attr("disabled", appGestMappe.blocked); 
		  		});
		  	}
		  	/**Buttons**/
		  	var btnItemsAdd = $("button.addElem");
		  	btnItemsAdd.attr("disabled", appGestMappe.blocked); 
		  	var btnItemsRem = $("button.removeElem");
		  	btnItemsRem.attr("disabled", appGestMappe.blocked);
		}
		
		if($('#dati-permessi').is(':visible')) {
			var itemsCheck = $("#dati-permessi input:checkbox");
			var n = itemsCheck.length;
			if(n > 0) {
				itemsCheck.each(function(idx, item) {
					var choice = $(item);
					choice.attr("disabled", appGestMappe.blocked);
				});
			}
		}
		
	  	/**GESTIONE BUTTON SALVA**/
  		var btnItems = $("button.salva");
  		var n = btnItems.length;
	  	if (n > 0) {
	  		btnItems.each(function(idx,item){
	  			var choice = $(item);
	  			choice.attr("disabled", appGestMappe.blocked); 
	  		});
	  	}
	  	/**GESTIONE BUTTON MODIFICA**/
  		var btnItems = $("button.modifica");
  		var n = btnItems.length;
	  	if (n > 0) {
	  		btnItems.each(function(idx,item){
	  			var choice = $(item);
	  			if ( appGestMappe.blocked ) {choice.children().html("Modifica"); appGestMappe.modifica	= false;}
	  			else {choice.children().html("Annulla"); appGestMappe.modifica	= true;}
	  				
	  		});
	  	}              
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});
	/**** SALVA MAPPA	*****************/
	
$(document).on("click", ".salva", function() {
		
		var cod__=$("#code").val();
	 	
		if(/[`~!@#$%^&*()|+\=?;:'",.<>\{\}\[\]\\\/]/.test(cod__)){
			appUtil.showMessageAlertApplication("Campo Codice, non valido! Sono ammessi solo caratteri alfanumerici piu' [ - _ ]", "Info", true, false, false);	
		}else{

		appUtil.confirmOperation( function(){
			//CONFERMA
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			//SALVO DATI DI BASE
			if($('#dati-base').is(':visible')){
				
				appGestMappe.mappaToSave.code = $("#code").val();
				appGestMappe.mappaToSave.title = $("#title").val();
				appGestMappe.mappaToSave.description = $("#description").val();
				
				//MS CREO NUOVO OGGETTO PER IL SALVATAGGIO SALVA/MODIFICA
				UMappa= new getUMappa();
				UMappa.isNew=appGestMappe.isNew;
				if(!appGestMappe.isNew)
				UMappa.id= appGestMappe.mappeSelectedList[0] || $("input[type='checkbox']:checked", $('#tabellaMappe')).get(0).id.split('checkbox-record-')[1];
				UMappa.codice= cod__.toUpperCase();
				UMappa.nome=$("#title").val();
				UMappa.descrizione=$("#description").val();
				UMappa.stato=$("#stato").val();
				UMappa.mappaPredefinita=$("#mappaPredifinita").get(0).checked;
				UMappa.showCatalog=true;
				UMappa.zoom=10;
				//MS SALVA/MODIFICA
			
				jQAjaxSupport.get('mappaController') 
				.setAction("saveOrUpdate")
				.setData(JSON.stringify(UMappa))
				.showMsg(true)
				.setMessage("error","Non e possibile proseguire! Codice inserito duplicato",true)
				.addSuccess(function (data) {
				 
				$(".modifica")[0].click();
			
				 	appGestMappe.inizializzaDatatableMappe();
					appGestMappe.modifica=false;
					
					if(appGestMappe.isNew)
					{

						// se l'operazione di inserimento e andata a buon fine
						// associo l'id della mappa appena creata alla lista  riaggiorno  i risultati in datatable 
						// e proseguo nell'inserimento di altre info 			
					appGestMappe.mappeSelectedList=[];
					appGestMappe.mappeSelectedList.push(data.aaData);
					showDettaglioMappa();
				
					}		 
						 
						return data;
				 
					})
				.start()
 
			}
			//SALVO RICERCHE
			if($('#dati-ricerche').is(':visible')) {
				appGestMappe.mappaToSave.search = JSON.stringify((appGestMappe.mappaSelected).searchListOk);
				//AGGIUNGE NELLA TABELLA DI JOIN TUTTI GLI ID CHE SI TROVANO IN SEARCHLISTOK
				datiRicerche = [];
				$.each((appGestMappe.mappaSelected).searchListOk, function(keySearchOk, valueSearchOk) {
					datiRicerche.push({"id_mappa": (appGestMappe.mappaSelected).id, "id_mapricerca": valueSearchOk.id });
				})			

				if(datiRicerche.length == 0 && (appGestMappe.mappaSelected).searchListOk.length == 0) {
					datiRicerche.push({"id_mappa": (appGestMappe.mappaSelected).id, "id_mapricerca": 0 });  
				} 
				
				hrefMapRicercheUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/mappaController/insertRicerche";
            	UrbamidResponseRicerche = appRest.restUrbamid(hrefMapRicercheUrbamid, "POST", JSON.stringify(datiRicerche));
 
            	if(UrbamidResponseRicerche.success) {
					appUtil.hideLoader();

					appGestMappe.modifica = false;
					resetSezioneModifica();
					var itemsCheck = $("#dati-ricerche input:checkbox");
					itemsCheck.each(function(idx, item) {
						var choice = $(item);
						choice.attr("disabled", appGestMappe.blocked);
					});
					
					$('button.addElem').attr('disabled', appGestMappe.blocked);
					$('button.removeElem').attr('disabled', appGestMappe.blocked);
				
					$("#search").text((appGestMappe.mappaSelected).searchListOk.length);
				
				} else {
					appUtil.hideLoader();
					appUtil.showMessageAlertApplication('Errore nell\'inserimento delle ricerche', 'Attenzione', false, true, false, null, null);
				}
           
			}         
		
			//SALVO TOOLS
			if($('#dati-tools').is(':visible')){
				appGestMappe.mappaToSave.tools = "{";
				$.each( (appGestMappe.mappaSelected).toolListOk, function( keyToolOk, valueToolOk ) {
					appGestMappe.mappaToSave.tools+= "'"+valueToolOk.name+"':true,";
				});
				appGestMappe.mappaToSave.tools +="}";

				//AGGIUNGE NELLA TABELLA DI JOIN TUTTI GLI ID CHE SI TROVANO IN TOOLLISTOK
				datiTools = [];
				$.each((appGestMappe.mappaSelected).toolListOk, function(keySearchOk, valueToolOk) {
					datiTools.push({"id_mappa": (appGestMappe.mappaSelected).id, "id_maptool": valueToolOk.id });
				})
        		
		        if(datiTools.length == 0 && (appGestMappe.mappaSelected).toolListOk.length == 0) {
					
		        	datiTools.push({"id_mappa": (appGestMappe.mappaSelected).id, "id_maptool": 0 });  
					
				} 
		        
		        hrefMapRicercheUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/mappaController/insertToolJoin";
        		UrbamidResposeTool = appRest.restUrbamid(hrefMapRicercheUrbamid, "POST", JSON.stringify(datiTools));
        		
        		if(UrbamidResposeTool.success) {
					appUtil.hideLoader();

					appGestMappe.modifica = false;
					resetSezioneModifica();
					var itemsCheck = $("#dati-tools input:checkbox");
					itemsCheck.each(function(idx, item) {
						var choice = $(item);
						choice.attr("disabled", appGestMappe.blocked);
					});
					
					$('button.addElem').attr('disabled', appGestMappe.blocked);
					$('button.removeElem').attr('disabled', appGestMappe.blocked);

					$("#tool").text((appGestMappe.mappaSelected).toolListOk.length);
				} else {
					appUtil.hideLoader();
					appUtil.showMessageAlertApplication('Errore nell\'inserimento dei tools', 'Attenzione', false, true, false, null, null);
				}

        		
			}         
			//TODO: SALVO LAYERS
			if($('#dati-layer').is(':visible')){

				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

				setTimeout(function(e) {
				 	ListGroup=JBoxGroup.get("gestmappa").getGroup();

					hrefMapListGroupUrbamid = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'layerController/saveGrups';
					UrbamidResposeListGroup = appRest.restUrbamid(hrefMapListGroupUrbamid, "POST", JSON.stringify(ListGroup));

					if(UrbamidResposeListGroup.success) {
						appUtil.hideLoader();
						//MS MODIFICA DATI MAPPA ZOOM isshow catalog
						UMappa={};
						UMappa.isNew=false;
						UMappa.zoom=appGestMappe.mappaSelected.zoom;
						UMappa.showCatalog=$("#mappa_catalogo_visible").get(0).checked;
						UMappa.id=appGestMappe.mappaSelected.id;
						UMappa.codice=appGestMappe.mappaSelected.codice;
						UMappa.nome=appGestMappe.mappaSelected.nome;
						UMappa.descrizione=appGestMappe.mappaSelected.descrizione;
						UMappa.stato=appGestMappe.mappaSelected.stato;
						UMappa.utenteModifica=appGestMappe.mappaSelected.utenteModifica;
						
						hrefMapUrbamid = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'mappaController/updateZoomShowCat';
						UrbamidResposeMap = appRest.restUrbamid(hrefMapUrbamid, "POST", JSON.stringify(UMappa));
						

						if(!UrbamidResposeMap.success) {
							appUtil.hideLoader();
							appUtil.showMessageAlertApplication('Errore nella modifica della mappa', 'Attenzione', false, true, false, null, null);
						} else {
							appUtil.hideLoader();
							
							$("#accordion_" + JBoxGroup.instance['gestmappa'].nomeGruppoReplaceSelect).removeClass( "activeGroup" );
							$(".panel-group").addClass( "activeGroup" );

							JBoxGroup.instance['gestmappa'].nomeGruppoReplaceSelect = undefined
							JBoxGroup.instance['gestmappa'].nomeGruppoSelect = undefined
							
							iziToast.show({
								timeout: 3000,
								balloon: false,
								icon:'fa fa-done', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
								title: 'OK',
								message: 'Livelli salvati con successo!',
							});

							appGestMappe.modifica = false;
							resetSezioneModifica();

							var buttonItems = $('#dati-layer button');
							var nButtons = buttonItems.length;
							if(nButtons > 0) {
								buttonItems.each(function(idx, item) {
									var choice = $(item);
									choice.attr('disabled', appGestMappe.blocked);
								});
							}
							
							var chkItems = $('#dati-layer input:checkbox');
							var nChk = chkItems.length;
							if(nChk > 0) {
								chkItems.each(function(idx, item) {
									var choice = $(item);
									choice.attr('disabled', appGestMappe.blocked)
								});
							}
							
							$('#importaGruppiSlc').prop('selectedIndex', 0);

							$(".panel-group").removeClass( "activeGroup" );
							$('#importaGruppiSlc').attr('disabled', appGestMappe.blocked);
							$('#mappa_livello_zoom').attr('disabled', appGestMappe.blocked);

						}

					} else {
						appUtil.showMessageAlertApplication('Errore nell\'inserimento dei gruppi', 'Attenzione', false, true, false, null, null);

					}

				}, 1000);
				
			}
			
			if($('#dati-permessi').is(':visible')) {
				datiPermessiUpdate = [];
				datiPermessiInsert = [];
			
				$.each(appGestMappe.permessiModificati, function (key, permesso) {
						if (permesso.valuePermesso) {
							datiPermessiInsert.push({"isNew": true, "id_risorsa": (appGestMappe.mappaSelected).id, "id_tipo_risorsa": 1, "id_ruolo": permesso.idRuolo, "abilita_visualizzazione": true});
						} else {
							datiPermessiUpdate.push({"isNew": false, "id_risorsa": (appGestMappe.mappaSelected).id, "id_tipo_risorsa": 1, "id_ruolo": permesso.idRuolo });
						}
				});

									
				if(datiPermessiInsert.length != 0) {
					hrefMapPermessiUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/mappaController/insertPermessi";
					UrbamidResposePermessi = appRest.restUrbamid(hrefMapPermessiUrbamid, "POST", JSON.stringify(datiPermessiInsert));
				}

				
				hrefMapPermessiUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/mappaController/insertPermessi";
				UrbamidResposePermessi = appRest.restUrbamid(hrefMapPermessiUrbamid, "POST", JSON.stringify(datiPermessiUpdate));
				
				if(UrbamidResposePermessi.success) {
					appUtil.hideLoader();

					appGestMappe.modifica = false;
					resetSezioneModifica();
					var itemsCheck = $("#dati-permessi input:checkbox");
					itemsCheck.each(function(idx, item) {
						var choice = $(item);
						choice.attr("disabled", appGestMappe.blocked);
					});
					appGestMappe.permessiModificati = [];
					appGestMappe.numPermessiModificati = 0;
				} else {
					appUtil.hideLoader();

					appUtil.showMessageAlertApplication('Errore nell\'inserimento dei permessi', 'Attenzione', false, true, false, null, null);
					appGestMappe.permessiModificati = [];
					appGestMappe.numPermessiModificati = 0;
				}
				
			}
		 
			 
		}, function(){
			//ANNULLA
		}, {}, 'Sei sicuro di voler salvare le modifiche alla mappa?');
		
	}//fine else
		
	});
	 
	/** ELIMINA MAPPA */
	$("button.elimina").bind("click",function(){
		if(appGestMappe.mappeSelectedList.length)
		{
			appUtil.confirmOperation( function(){
			
				jQAjaxSupport.get('mappaController') 
				.setAction("deleteSel")
				.setData(JSON.stringify(appGestMappe.mappeSelectedList))
				.showMsg(true)
				.addSuccess(function (data) {
				  setTimeout(function(){location.href="/UrbamidWeb/gestioneMappe";},350);
				})
				.start()
				
				
			}, function(){
				//ANNULLA
			}, {}, 'Sei sicuro di voler eliminare la Mappa corrente?');
		}
		
	})
	
	/**** MS DUPLICA MAPPA	*****************/
	$(document).on("click", ".duplica", function() {
	 
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		 
			//CONFERMA
			 
			if ( appGestMappe.mappeSelectedList.length > 1 || appGestMappe.mappeSelectedList.length == 0 ) {
				appUtil.showMessageAlertApplication('Devi selezionare una sola mappa per duplicare', 'Attenzione', false, true, false, null, null);
			} else {
				
				appUtil.confirmOperation( function(){
		
					UMappa= new getUMappa();
					UMappa.isNew=true;
					UMappa.id= appGestMappe.mappeSelectedList[0];
	 
					jQAjaxSupport.get('mappaController') 
					.setAction("duplica")
					.setData(JSON.stringify(UMappa))
					.showMsg(true)
					.setMessage("error","Non e possibile proseguire!",true)
					.addSuccess(function (data) {
					 appGestMappe.inizializzaDatatableMappe();	
					 appGestMappe.mappeSelectedList=[];
					$(".dettaglio").get(0).setAttribute("disabled","disabled")
					$(".elimina").get(0).setAttribute("disabled","disabled")
					$(".duplica").get(0).setAttribute("disabled","disabled")
						}).start()
					
			
				}, function(){
					//ANNULLA Ciao Ciao
				}, {}, 'Sei sicuro di voler duplicare la mappa?');
			}
				
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		 
	});
	/**** ELENCO MAPPA	*****************/
	$('.showElenco').click(function(e){
		e.stopPropagation();
		if(appGestMappe.modifica)
		$('button.modifica').get(0).click();
		appGestMappe.layerLoaded= false;
	
		appGestMappe.modifica=false;
		 
		//DISABILITO IL BTN CANC. TEMA ASSOCIATO
		$(".boxtemi,button.btn-remove").attr("disabled","disabled")
		location.href="/UrbamidWeb/accessoGestioneMappe";
	});
	
	
	/********************************************************************************************************************/
	/******************************		GESTIONE DELLE AZIONI MULTISELECT	*********************************************/
	/********************************************************************************************************************/
	$(document).on("click", ".addElem", function() {
	    $('.all').prop("checked",false);
	    var items = [];
	    if($('#dati-ricerche').is(':visible')){
	    	items = $("#searchList input:checked:not('.all')");
		} else if($('#dati-tools').is(':visible'))	{
			items = $("#toolList input:checked:not('.all')");
		}
	    var n = items.length;
	    var arraySelected = [];
	  	if (n > 0) {
	  		items.each(function(idx,item){
	  			var choice = $(item);
	  			choice.prop("checked",false);
	  			if($('#dati-ricerche').is(':visible')){
	  				choice.parent().parent().parent().appendTo("#ricercheAttiveList");
	  				arraySelected.push(choice.parent().parent().parent().children("label").html());
	  			} else if($('#dati-tools').is(':visible'))	{
	  				choice.parent().parent().parent().appendTo("#toolAttiviList");
	  				arraySelected.push(choice.parent().parent().parent().children("label").html());
	  			}
	  		});
	  	} else {
	    	appUtil.showMessageAlertApplication('Seleziona un elemento tra gli elementi disponibili', 'Attenzione', false, true, false, null, null);
	    }
	  	
	  	if (arraySelected!=null && arraySelected.length>0) {
	  		$.each( arraySelected, function( idx, item ) {
	  			
	  			if($('#dati-ricerche').is(':visible')){
	  				valueMappaRicercaKoSel 	= null;
		  			idMappaRicercaKoSel		= null;
		  			$.each( (appGestMappe.mappaSelected).searchListKo, function( keyMappaRicercaKo, valueMappaRicercaKo ) {
		  				if (valueMappaRicercaKo.title==item) {
		  					valueMappaRicercaKoSel = valueMappaRicercaKo;
		  					idMappaRicercaKoSel=keyMappaRicercaKo;
		  				}
		  			});
		  			if (valueMappaRicercaKoSel!=null) 
		  				((appGestMappe.mappaSelected).searchListOk).push( valueMappaRicercaKoSel );
		  			if(idMappaRicercaKoSel!=null)
		  				((appGestMappe.mappaSelected).searchListKo).splice(idMappaRicercaKoSel,1);
	  			} else if($('#dati-tools').is(':visible'))	{
	  				valueMappaToolKoSel = null;
	  				idMappaToolKoSel		= null;
		  			$.each( (appGestMappe.mappaSelected).toolListKo, function( keyMappaToolKo, valueMappaToolKo ) {
		  				if (valueMappaToolKo.title==item) {
		  					valueMappaToolKoSel = valueMappaToolKo;
		  					idMappaToolKoSel=keyMappaToolKo;
		  				}
		  			});
		  			if (valueMappaToolKoSel!=null)
		  				((appGestMappe.mappaSelected).toolListOk).push( valueMappaToolKoSel );
		  			if(idMappaToolKoSel!=null)
		  				((appGestMappe.mappaSelected).toolListKo).splice(idMappaToolKoSel,1);
	  			}
	  		});
	  	}
	});
	$(document).on("click", ".removeElem", function() {
	    $('.all').prop("checked",false);
	    var items = [];
	    if($('#dati-ricerche').is(':visible')){
	    	items = $("#ricercheAttiveList input:checked:not('.all')");
		} else if($('#dati-tools').is(':visible'))	{
			items = $("#toolAttiviList input:checked:not('.all')");
		}
	    var n = items.length;
	    var arraySelected = [];
	  	if (n > 0) {
	  		items.each(function(idx,item){
	  			var choice = $(item);
	  			choice.prop("checked",false);
	  			if($('#dati-ricerche').is(':visible')){
	  				choice.parent().parent().parent().appendTo("#searchList");
	  				arraySelected.push(choice.parent().parent().parent().children("label").html());
	  			} else if($('#dati-tools').is(':visible'))	{
	  				choice.parent().parent().parent().appendTo("#toolList");
	  				arraySelected.push(choice.parent().parent().parent().children("label").html());
	  			}
	  		});
	  	} else {
	  		appUtil.showMessageAlertApplication('Seleziona un elemento tra gli elementi disponibili', 'Attenzione', false, true, false, null, null);
	    }
	  	
	  	if (arraySelected!=null && arraySelected.length>0) {
	  		$.each( arraySelected, function( idx, item ) {
	  			
	  			if($('#dati-ricerche').is(':visible')){
	  				valueMappaRicercaOkSel 	= null;
		  			idMappaRicercaOkSel		= null;
		  			$.each( (appGestMappe.mappaSelected).searchListOk, function( keyMappaRicercaOk, valueMappaRicercaOk ) {
		  				if (valueMappaRicercaOk.title==item) {
		  					valueMappaRicercaOkSel = valueMappaRicercaOk;
		  					idMappaRicercaOkSel=keyMappaRicercaOk;
		  				}
		  			});
		  			if (valueMappaRicercaOkSel!=null) 
		  				((appGestMappe.mappaSelected).searchListKo).push( valueMappaRicercaOkSel );
		  			if(idMappaRicercaOkSel!=null)
		  				((appGestMappe.mappaSelected).searchListOk).splice(idMappaRicercaOkSel,1);
	  			} else if($('#dati-tools').is(':visible'))	{
	  				valueMappaToolOkSel = null;
	  				idMappaToolOkSel = null;
		  			$.each( (appGestMappe.mappaSelected).toolListOk, function( keyMappaToolOk, valueMappaToolOk ) {
		  				if (valueMappaToolOk.title==item) {
		  					valueMappaToolOkSel = valueMappaToolOk;
		  					idMappaToolOkSel=keyMappaToolOk;
		  					console.log(idMappaToolOkSel)
		  				}
		  			});
		  			if (valueMappaToolOkSel != null)
		  				((appGestMappe.mappaSelected).toolListKo).push( valueMappaToolOkSel );
		  			if(idMappaToolOkSel != null)
		  				((appGestMappe.mappaSelected).toolListOk).splice(idMappaToolOkSel, 1);
	  			}
	  		});
	  	}
	});
	$('.all').click(function(e){
		e.stopPropagation();
		var $this = $(this);
	    if($this.is(":checked")) {
	    	$this.parents('.list-group').find("[type=checkbox]").prop("checked",true);
	    }
	    else {
	    	$this.parents('.list-group').find("[type=checkbox]").prop("checked",false);
	        $this.prop("checked",false);
	    }
	});
	
	
 });

var appGestMappe = {
		
		urlParams						: new Object(),
		tabellaMappe					: null,
		mappeList						: [],
		mappeSelectedList				: [],
		mappaSelected					: new Object(),
		mappaToSave						: new Object(),

		groupTable						: [],
		tables 						    : [],
		mappaTools						: [],
		mappaToolsAttivi				: [],
		mappaRicerche					: [],
		mappaRicercheAttive				: [],
		ruoli							: [],
		permessi						: [],
		permessiLayer 					: [],
		permessiModificaLayer 			: [],
		permessiModificati				: [],
		numPermessiModificati			: 0,
		zoomMappa						: 0,

		modifica						: false,
		blocked							: true,
		tabellaPageLength				: 5,
		
		layerLoaded						: false,
		
		start : function (){
			//IMPOSTAZIONI GRAFICHE AMMINISTRATORE
		},
		
		inizializzaDatatableMappe	: function () {
		
			/**distruggo la vecchia mappa**/
			if ($.fn.DataTable.isDataTable('#tabellaMappe'))
				(appGestMappe.tabellaMappe).destroy();
			/**inizializzo mappa**/
			hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/getAllMapp";//getMappe";
			urbamidMappeResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
			if ( urbamidMappeResponse!=null) {
				appGestMappe.mappeList 		= urbamidMappeResponse.aaData;
				appGestMappe.tabellaMappe 	= $('#tabellaMappe').DataTable({
					pageLength	: 10,
			        data		: urbamidMappeResponse.aaData,
			        language	: Constants.TABLE_LANGUAGE,
			        order		: [[1, 'asc']],
			        select		: {
		                style: 'multi'
		            },
		            "searching": true,
		            "aLengthMenu": [
		                [5, 10, 25, 50, -1],
		                ['Seleziona valore', 10, 25, 50, "Tutti"]
		              ],
			        columns		: [
			            {mData:"select", 
			            	className:"nowrap", 
			            	bSortable: false,
			            	checkboxes: {
			                    selectRow: true
			                },
			                render : function(data,type,row,meta){
			            		return '<div class="custom-control custom-checkbox">'+
	                                '<input id="checkbox-record-'+row.id+'" pkmappa="'+row.id+'" type="checkbox" class="custom-control-input" onclick="appGestMappe.showContextSelectRow(' + row.id + ',this);">'+
	                                '<label class="custom-control-label" for="checkbox-record-'+row.id+'"><span class="sr-only">Seleziona</span></label>'+
	                            '</div>';
	                         },
	                         title: ""
	                    },
			     
			            {mData:"codice",title: "Codice"},
			            {mData:"nome",title: "Nome"},
			            {mData:"descrizione",title: "Descrizione"},
			            {mData:"stato",title: "Stato",render : function(data,type,row,meta){ return data=="P"?"Pubblicato":data=="D"?"Disabilitato":""; }},
			            {mData:"dataCreazione",title: "Data creazione",render : function(data,type,row,meta){ return data?moment(data).format('DD/MM/YYYY'):""; }},
			            {mData:"dataModifica",title: "Data ultima modifica",render : function(data,type,row,meta){ return data?moment(data).format('DD/MM/YYYY'):""; }},
			            {mData:"mappaPredefinita",title: "Predefinito",render : function(data,type,row,meta){ return data?"SI":"NO"; }}
			       	]
			    });
				
 
			}
			/**imposto il numero di row per pagina**/
		},

		showContextSelectRow : function ( id,el ){
			var found = false;
			//MS Gestione bottoni header [NUOVO|DETTAGLIO|ELIMINA|DUPLICA]
			 
			   if(el.checked){
				   appGestMappe.mappeSelectedList.push(id) 
			   } else {
				   for (i=0;i<(appGestMappe.mappeSelectedList).length;i++){
						if ((appGestMappe.mappeSelectedList)[i] == id) {
							(appGestMappe.mappeSelectedList).splice(i, 1);
						}   
					}   
				}
			
			if(appGestMappe.mappeSelectedList.length<1){
				$(".dettaglio").get(0).setAttribute("disabled","disabled")
				$(".elimina").get(0).setAttribute("disabled","disabled")
				$(".duplica").get(0).setAttribute("disabled","disabled")	
				appGestMappe.mappeSelectedList=[];
			}	   
			else if(appGestMappe.mappeSelectedList.length>1)
			{
				$(".dettaglio").get(0).setAttribute("disabled","disabled")
				$(".elimina").get(0).removeAttribute("disabled")
				$(".duplica").get(0).setAttribute("disabled","disabled")
				for(i in appGestMappe.mappeSelectedList){// cosi se perdono il focus l'utente li puo visualizzare
				$("#checkbox-record-"+appGestMappe.mappeSelectedList[i]).each(function(){this.checked=true;})
				}
			}else
			{
				$(".dettaglio").get(0).removeAttribute("disabled")
				$(".elimina").get(0).removeAttribute("disabled")
				$(".duplica").get(0).removeAttribute("disabled")
				
				
			}
			
			
		},
		
		showElencoMappe : function () {
			$("#containerDetailMappa").hide();
			$("#containerGestioneTemi").hide();
			$("#containerTableMappe").show();
			appGestMappe.mappaSelected = {};
		},
		
		showDetailMappa : function (isnew) {
			
			/* ============= */
			//MS vanno settati con i vari oggetti legati alla id mappa
			appGestMappe.mappaSelected.layer="{\"defaultLayer\": []}";
			appGestMappe.mappaSelected.search="[]";
			appGestMappe.mappaSelected.tools="{}";
			appGestMappe.mappaSelected.layerOk=[];
			
			/* ============= */			
			/**Gestione layer**/
		 
			appGestMappe.mappaSelected.numLayers=0;	
			/**Gestione Ricerche**/
			 
			numSearchs = 0;
		 
			searchListOk = [];
			searchListKo = [];

			hrefMapRicercheUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/getMapAttRicerche?idMappa=" + (appGestMappe.mappaSelected).id;
			urbamidMapRicercheResponse = appRest.restUrbamid(hrefMapRicercheUrbamid, "GET", null);
			risultatoRicercheAttive = urbamidMapRicercheResponse.aaData;
			
			$.each( appGestMappe.mappaRicerche, function( keyMappaRicerca, valueMappaRicerca ) {
				found=false;
				for (let index = 0; index < risultatoRicercheAttive.length; index++) {
					const element = risultatoRicercheAttive[index];
					if(element.title == valueMappaRicerca.title){
						found = true;
					}
				}
				if(found) {
					searchListOk.push(valueMappaRicerca);
					numSearchs++;
				} else {
					searchListKo.push(valueMappaRicerca);
				}
			});
			(appGestMappe.mappaSelected).searchListOk = searchListOk;
			(appGestMappe.mappaSelected).searchListKo = searchListKo;
			(appGestMappe.mappaSelected).numSearchs = numSearchs;
			 
			
			/**Gestione Tool**/
			 
			numTools = 0;
			toolListOk = [];
			toolListKo = [];
			
			hrefMapToolUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/getMapAttTools?idMappa=" + (appGestMappe.mappaSelected).id;
			urbamidMapToolResponse = appRest.restUrbamid(hrefMapToolUrbamid, "GET", null);
			risultatoToolsAttivi = urbamidMapToolResponse.aaData;
						
			$.each( appGestMappe.mappaTools, function( keyMappaTool, valueMappaTool ) {
				found=false;
				for (let index = 0; index < risultatoToolsAttivi.length; index++) {
					const element = risultatoToolsAttivi[index];
					if(element.name == valueMappaTool.name){
						found = true;
					}
				}
				if(found) {
					toolListOk.push(valueMappaTool);
					numTools++;
				} else {
					toolListKo.push(valueMappaTool);
				}
			});
			(appGestMappe.mappaSelected).toolListOk = toolListOk;
			(appGestMappe.mappaSelected).toolListKo = toolListKo;
			(appGestMappe.mappaSelected).numTools = numTools;
			if(typeof (appGestMappe.mappaSelected).zoom==="undefined")
			(appGestMappe.mappaSelected).zoom = 18;//MS NEW
			if(typeof (appGestMappe.mappaSelected).showCatalog==="undefined")
				(appGestMappe.mappaSelected).showCatalog = true;//MS NEW
			
			/** GESTIONE PERMESSI **/
			ruoliList = [];

			permessiListOk = [];

			hrefMapPermessiUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/getAllMapPermessi?idMappa=" + (appGestMappe.mappaSelected).id;
			urbamidMapPermessiResponse = appRest.restUrbamid(hrefMapPermessiUrbamid, "GET", null);
			risultatoPermessiAttivi = urbamidMapPermessiResponse.aaData;

			$.each(appGestMappe.ruoli, function (keyRuoli, valueRuoli) {
				ruoliList.push(valueRuoli);
			});

			if(risultatoPermessiAttivi!=null) {
				for (var i = 0; i < risultatoPermessiAttivi.length; i++) {
					var element = risultatoPermessiAttivi[i];
					if(element.isNew == null) {
						element.isNew = false;
						permessiListOk.push(element);
					}
				}
			}
			
			
			(appGestMappe.mappaSelected).permessiListOk = permessiListOk;
			(appGestMappe.mappaSelected).ruoliList = ruoliList;

			/**Gestione**/
			$("#containerDetailMappa").show();

		    /** Imposto value html**/
			var zoomValue = appGestMappe.mappaSelected.zoom;
		    if (zoomValue==10) {
				zoomLabel = "10 km"
				appGestMappe.mappaSelected.zoom = 10;
				appGestMappe.zoomMappa = 19;
			} else if(zoomValue == 11) {
				zoomLabel = "5 km"
				appGestMappe.zoomMappa = 18;
				appGestMappe.mappaSelected.zoom = 11;
			} else if(zoomValue == 12) {
				zoomLabel = "2 km"
				appGestMappe.zoomMappa = 17;
				appGestMappe.mappaSelected.zoom = 12;
			} else if(zoomValue == 13) {
				zoomLabel = "1 km"
				appGestMappe.zoomMappa = 16;
				appGestMappe.mappaSelected.zoom = 13;
			} else if(zoomValue == 14) {
				zoomLabel = "500 m"
				appGestMappe.zoomMappa = 15;
				appGestMappe.mappaSelected.zoom = 14;
			} else if(zoomValue == 15) {
				zoomLabel = "500 m"
				appGestMappe.zoomMappa = 14;
				appGestMappe.mappaSelected.zoom = 15;
			} else if(zoomValue == 16) {
				zoomLabel = "200 m"
				appGestMappe.zoomMappa = 13;
				appGestMappe.mappaSelected.zoom = 16;
				// $('#mappa_livello_zoom').val(16);
			} else if(zoomValue == 17) {
				zoomLabel = "100 m"
				appGestMappe.zoomMappa = 12;
				appGestMappe.mappaSelected.zoom = 17;
				// $('#mappa_livello_zoom').val(17);
			} else if(zoomValue == 18) {
				zoomLabel = "50 m"
				appGestMappe.zoomMappa = 11;
				appGestMappe.mappaSelected.zoom = 18;
				// $('#mappa_livello_zoom').val(18);
			} else if(zoomValue == 19) {
				zoomLabel = "20 m"
				appGestMappe.zoomMappa = 10;
				appGestMappe.mappaSelected.zoom = 19;
				// $('#mappa_livello_zoom').val(19);
			}
		    appGestMappe.mappaSelected.zoomLabel = zoomLabel;
			
			$("#map-title").html( (appGestMappe.mappaSelected.nome) + ' ( Cod. ' + (appGestMappe.mappaSelected.codice).toUpperCase() + ' )');//MS
			$("#sezioni-mappa").html( compilaTemplate("sezioni-mappa-template", appGestMappe.mappaSelected) );
			openSezione(event, 'dati-base');
			 
			 
			//MS SE SONO IN DETTAGLIO RICHIAMO TEMI ASSOCIATI ALLA MAPPA
			if(!appGestMappe.isNew){
			appGestMappe.aggiornaTemiAssociatiMappa();
			$("input,select,button,textarea",$("#dati-base")).attr("disabled","disabled")
			} else {
			$("input,select,button,textarea",$("#dati-base")).removeAttr("disabled")
			 setTimeout(function(){$(".modifica")[0].click();},100)
			}
		},
		
		changeNumResult : function () {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			appGestMappe.inizializzaDatatableMappe();
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		},
		
		addLayerToMap : function(layerName, layerHref) {
			(appGestMappe.mappaSelected).layerOk.push(layerName);
		},
	  
            
		changePermesso: function (idRuolo) {
			(appGestMappe).permessi[idRuolo] = !appGestMappe.permessi[idRuolo];
			if(!appGestMappe.checkAndDeletePermesso(false, idRuolo)) {
				permessoModificato = {"idRuolo": idRuolo, "valuePermesso": appGestMappe.permessi[idRuolo]};
				appGestMappe.permessiModificati.push(permessoModificato);
				appGestMappe.numPermessiModificati++;
			} else {
				appGestMappe.checkAndDeletePermesso(true, idRuolo);
				appGestMappe.numPermessiModificati = appGestMappe.numPermessiModificati - 1;
			}
		},
		
		checkAndDeletePermesso: function(del, idRuolo) {
			$.each(appGestMappe.permessiModificati, function (key, valuePermesso) {
				console.log(valuePermesso);
				if(valuePermesso.idRuolo === idRuolo) {
					if(del) {
						delete appGestMappe.permessiModificati[key];
					}
					return true;
				} 
			});
			return false;
		},
		
		changeVisualizzaLayer: function (idRuolo) {
			found = false;
			var key = 0;
			(appGestMappe).permessiLayer[idRuolo] = !(appGestMappe).permessiLayer[idRuolo];
			if(appGestMappe.permessiModificati.length == 0) {
				permessoModificato = {"idRuolo": idRuolo, "visualizza":(appGestMappe).permessiLayer[idRuolo], "modifica": (appGestMappe).permessiModificaLayer[idRuolo]};
				appGestMappe.permessiModificati.push(permessoModificato);
			} else {
				$.each(appGestMappe.permessiModificati, function (keyPermesso, valuePermesso) {
					if(valuePermesso.idRuolo == idRuolo) {
						key = keyPermesso;
						found = true;
					}
				})

				if(found) {
					appGestMappe.permessiModificati[key].visualizza = (appGestMappe).permessiLayer[idRuolo];
					key = 0;
				} else {
					permessoModificato = {"idRuolo": idRuolo, "visualizza":(appGestMappe).permessiLayer[idRuolo], "modifica": (appGestMappe).permessiModificaLayer[idRuolo]};
				    appGestMappe.permessiModificati.push(permessoModificato);
				}
			}
			appGestMappe.numPermessiModificati++;
		},
		
		changeModificaLayer: function(idRuolo) {
			found = false;
			var key = 0;
			(appGestMappe).permessiModificaLayer[idRuolo] = !(appGestMappe).permessiModificaLayer[idRuolo];
			if(appGestMappe.permessiModificati.length == 0) {
				permessoModificato = {"idRuolo": idRuolo, "visualizza":(appGestMappe).permessiLayer[idRuolo], "modifica": (appGestMappe).permessiModificaLayer[idRuolo]};
				appGestMappe.permessiModificati.push(permessoModificato);
			} else {
				$.each(appGestMappe.permessiModificati, function (keyPermesso, valuePermesso) {
					
					if(valuePermesso.idRuolo == idRuolo) {
						key = keyPermesso;
						found = true;
					}
				})
				if(found) {
					appGestMappe.permessiModificati[key].modifica = (appGestMappe).permessiModificaLayer[idRuolo];
					key = 0;
				} else {
					permessoModificato = {"idRuolo": idRuolo, "visualizza":(appGestMappe).permessiLayer[idRuolo], "modifica": (appGestMappe).permessiModificaLayer[idRuolo]};
				    appGestMappe.permessiModificati.push(permessoModificato);
				}
			}
			appGestMappe.numPermessiModificati++;
		}
		
}; 
 

function openSezione(evt, sezione) {
	
	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	if (appGestMappe.modifica) {
		appUtil.confirmOperation( function(){
			changeSezione(evt, sezione);
		}, function(){
			appGestMappe.modifica = false;
			//ANNULLA
		}, {}, 'Non hai salvato le ultime modifiche, sei sicuro di voler continuare?');
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	} else {
		changeSezione(evt, sezione);
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	}
}

function loadMapProperties () {
	
	hrefMapToolUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/mappaController/getMapTools";
	urbamidMapToolResponse = appRest.restUrbamid(hrefMapToolUrbamid, "GET", null);
	appGestMappe.mappaTools 		= urbamidMapToolResponse.aaData;
	
	hrefMapRicercheUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/mappaController/getMapAllRicerche";
	urbamidMapRicercheResponse = appRest.restUrbamid(hrefMapRicercheUrbamid, "GET", null);
	appGestMappe.mappaRicerche = urbamidMapRicercheResponse.aaData;
	
	hrefRuoliUrbamid = appUtil.getUrbamidOrigin() + "/UrbamidWeb/mappaController/getAllRuoli";
	urbamidRuoliResponse = appRest.restUrbamid(hrefRuoliUrbamid, "GET", null);
	appGestMappe.ruoli = urbamidRuoliResponse.aaData;
}

Constants.TABLE_LANGUAGE = {
		"sEmptyTable":     "Nessun dato presente nella tabella",
		"sInfo":           "Vista da _START_ a _END_ di _TOTAL_ elementi",
		"sInfoEmpty":      "Vista da 0 a 0 di 0 elementi",
		"sInfoFiltered":   "(filtrati da _MAX_ elementi totali)",
		"sInfoPostFix":    "",
		"sInfoThousands":  ".",
		"sLengthMenu":     "Risultati per pagina _MENU_",
		"sLoadingRecords": "Caricamento...",
		"sProcessing":     "Elaborazione...",
		"sSearch":         " Cerca ",
		"sZeroRecords":    "La ricerca non ha portato alcun risultato.",
		"oPaginate": {
			"sFirst":      "Inizio",
			"sPrevious":   "Indietro",
			"sNext":       "Successivo",
			"sLast":       "Fine"
		},
		"oAria": {
			"sSortAscending":  ": attiva per ordinare la colonna in ordine crescente",
			"sSortDescending": ": attiva per ordinare la colonna in ordine decrescente"
		}
}

function changeSezione (evt, sezione) {
	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	
	appGestMappe.mappaToSave = {};
	appGestMappe.modifica = false;
	// Declare all variables
	var i, tabcontent, tablinks;

	// Get all elements with class="tabcontent" and hide them
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}

	// Get all elements with class="tablinks" and remove the class "active"
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}

	// Show the current tab, and add an "Mappeactive" class to the link that opened the tab
	document.getElementById(sezione).style.display = "block";
	document.getElementById(sezione+"-link").className += " active";
	  
	appGestMappe.blocked = true;
	/**GESTIONE BUTTON SALVA**/
	var btnItems = $("button.salva");
	var n = btnItems.length;
	if (n > 0) {
		btnItems.each(function(idx,item){
			var choice = $(item);
			choice.attr("disabled", appGestMappe.blocked); 
		});
	}
	/**GESTIONE BUTTON MODIFICA**/
	var btnItems = $("button.modifica");
	var n = btnItems.length;
	if (n > 0) {
		btnItems.each(function(idx,item){
			var choice = $(item);
			if ( appGestMappe.blocked ) choice.children().html("Modifica");
			else choice.children().html("Annulla");
		});
	}
	
	if (sezione=="dati-layer") {
		if(!appGestMappe.layerLoaded) {
			$.when( appUrbamid.startCatalogo( null ) ).then(function( x ) {
				cbkOnClick = "aggiungiLivello";
				appCatalogo.initializeCatalogoGestMappe("menu-content", cbkOnClick, "Aggiungi al visualizzatore");
			});
			appGestMappe.layerLoaded = true;
			JBoxGroup.get("gestmappa",{
			idMappa:appGestMappe.mappeSelectedList[0],
			idBoxGroup:"#boxGruppiLayer",
			onShowPermessi:openModalePermessi}).create();
		}
	
			var itemButton = $('#dati-layer button');
			var nButton = itemButton.length;
	
			if(nButton > 0) {
				itemButton.each(function(idx, item) {
					var choice = $(item);
					choice.attr('disabled', appGestMappe.blocked);
				})
			}

			var itemChk = $('#dati-layer input:checkbox');
			var nChk = itemChk.length;

			if(nChk > 0) {
				itemChk.each(function(idx, item) {
					var choice = $(item);
					choice.attr('disabled', appGestMappe.blocked);
				})
			}

			JBoxGroup.instance['gestmappa'].nomeGruppoReplaceSelect = undefined
			JBoxGroup.instance['gestmappa'].nomeGruppoSelect = undefined

			$(".panel-group").removeClass( "activeGroup" );
			$('#mappa_catalogo_visible').attr('disabled', appGestMappe.blocked);
			$('#mappa_livello_zoom').val(appGestMappe.zoomMappa);
			$('#mappa_livello_zoom').attr('disabled', appGestMappe.blocked);
	
			hrefGroupTableUrbamid = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + "layerController/getGroupTableMap?idMappa=" + (appGestMappe.mappaSelected).id;
			urbamidGroupTableResponse = appRest.restUrbamid(hrefGroupTableUrbamid, "GET", null)
			risulatoGroup = urbamidGroupTableResponse.aaData;

			var htmlDet = compilaTemplate('itemGruppoTavole', risulatoGroup);
			$("#selectGroupTable").html(htmlDet);
	}
	
	if (sezione=="dati-ricerche") {
		var itemsText = $("#dati-ricerche input:text");
		var n = itemsText.length;
		if(n > 0) {
			itemsText.each(function(idx,item){
	  			var choice = $(item);
	  			choice.attr("readonly", appGestMappe.blocked); 
	  		});
		}
		var itemsCheck = $("#dati-ricerche input:checkbox");
		var n = itemsCheck.length;
	  	if (n > 0) {
	  		itemsCheck.each(function(idx,item){
	  			var choice = $(item);
	  			choice.attr("disabled", appGestMappe.blocked); 
	  		});
		}
		$('button.addElem').attr('disabled', appGestMappe.blocked);
		$('button.removeElem').attr('disabled', appGestMappe.blocked);  
	}
	
	if (sezione=="dati-tools") {
		var itemsText = $("#dati-tools input:text");
		var n = itemsText.length;
	  	if (n > 0) {
	  		itemsText.each(function(idx,item){
	  			var choice = $(item);
	  			choice.prop("readonly", appGestMappe.blocked); 
	  		});
	  	}
	  	var itemsCheck = $("#dati-tools input:checkbox");
		var n = itemsCheck.length;
	  	if (n > 0) {
	  		itemsCheck.each(function(idx,item){
	  			var choice = $(item);
	  			choice.prop("disabled", appGestMappe.blocked); 
	  		});
	  	}
	}
	
	if(sezione == "dati-permessi") {
		permessi = [];
		$.each(appGestMappe.ruoli, function (keyRuoli, valueRuoli) {
			found = false;
			for (var i = 0; i < permessiListOk.length; i++) {
				var element = permessiListOk[i];
				if(valueRuoli.id == element.id_ruolo) {
					found = true;
				}
			}
			if(found) {
					$("#perm-" + valueRuoli.id).prop('checked', true);
					(appGestMappe).permessi[valueRuoli.id] = true;
			} else {
				(appGestMappe).permessi[valueRuoli.id] = false;
			}
		});
				
		var itemsCheck = $("#dati-permessi input:checkbox");
		var n = itemsCheck.length;
		if(n > 0) {
			itemsCheck.each(function(idx, item) {
				var choice = $(item);
				choice.attr("disabled", appGestMappe.blocked);
			});
		}
	}
	
	if (sezione=="dati-base") {
		$("input,select,button,textarea",$("#dati-base")).attr("disabled","disabled")
	  }
	
	
	appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
}

//AGGIUNGE LAYER
function aggiungiLivello(layerName, layerTitle, hrefDetail){
 
	if(!appGestMappe.blocked) {
		
		JBoxGroup.get("gestmappa").addLayer(layerName, layerTitle);
	
	} else {

		iziToast.error({
			timeout: 3000,
			balloon: false,
			icon:'fa fa-times', 
			animateInside: false,
			theme: 'dark', 
			position: 'topCenter',
			title: 'OK',
			message: 'Devi abilitare la modifica!',
		});

	}
 
}

function aggiungiTuttiLivelli(itemId, responseDetailChild) {

	if(!appGestMappe.blocked) {

		let node = appConfig.jsTreeCatalogo.jstree(true).get_json(itemId);

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

		setTimeout(() => {
			
			if(node != null && node != undefined && node.children.length > 0) {

				$.each(node.children, function(keyChild, valueChild) {
	
					if(valueChild.id.indexOf('_LG') == -1) {
						
						JBoxGroup.get("gestmappa").addLayer(valueChild.id, $(valueChild.text)[0].title);
	
					} else {
	
						if(valueChild.children.length > 0) {
	
							$.each(valueChild.children, function(key, value) {
	
								JBoxGroup.get("gestmappa").addLayer(value.id, $(value.text)[0].title);
	
							});
	
						}
					}
				});
	
			} else {
	
				iziToast.error({
					timeout: 3000,
					balloon: false,
					icon:'fa fa-times', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
					title: 'OK',
					message: 'Non ci sono livelli nel tematismo: ' + $(node.text)[0].title,
				});
	
			}

			appUtil.hideLoader();

		}, 500);

	} else {

		iziToast.error({
			timeout: 3000,
			balloon: false,
			icon:'fa fa-times', 
			animateInside: false,
			theme: 'dark', 
			position: 'topCenter',
			title: 'OK',
			message: 'Devi abilitare la modifica!',
		});

	}

}

function resetSezioneModifica() {
	appGestMappe.blocked = true;
	/**GESTIONE BUTTON SALVA**/
	var btnItems = $("button.salva");
	var n = btnItems.length;
	if (n > 0) {
		btnItems.each(function(idx,item){
			var choice = $(item);
			choice.attr("disabled", appGestMappe.blocked); 
		});
	}
	/**GESTIONE BUTTON MODIFICA**/
	var btnItems = $("button.modifica");
	var n = btnItems.length;
	if (n > 0) {
		btnItems.each(function(idx,item){
			var choice = $(item);
			if ( appGestMappe.blocked ) choice.children().html("Modifica");
			else choice.children().html("Annulla");
		});
	}
}

//MS INIZIO NUOVE FUNZIONI PERSONALIZZATE

//MS MEMORIZZA LO STATO DELLA MAPPA - NUOVO/MODIFICA
appGestMappe.isNew=false;


//MS MEMORIZZA I TEMI SELEZIONATI
appGestMappe.elTemiSelezionati=[];  

appGestMappe.aggiornaTemiAssociatiMappa=function(){
	UMappa= new getUMappa();
	UMappa.id= appGestMappe.mappeSelectedList[0] || $("input[type='checkbox']:checked", $('#tabellaMappe')).get(0).id.split('checkbox-record-')[1];
	  
//MS TEMI ASSOCIATI ALLA MAPPA
	appGestMappe.elTemiSelezionati=[];
	 
	jQAjaxSupport.get('mappaController') 
	.setAction("temiToMappa")
	.setData(JSON.stringify(UMappa))
	.showMsg(false)
	.addSuccess(function(data){
	    
		for(i in data.aaData)
	appGestMappe.elTemiSelezionati.push({id:data.aaData[i].id,nome:data.aaData[i].nome})	
		var sourceHtml = document.getElementById("temiAssociatiTemplate").innerHTML;
		var templateHtml = Handlebars.compile(sourceHtml);
	    outputHtml = templateHtml({aaData:appGestMappe.elTemiSelezionati});  
	    $("#boxtemi").html(outputHtml);
	    setTimeout(function(){$("input,select,button,textarea",$("#dati-base")).attr("disabled","disabled")},350)
	   
	}).start()	
	
	
};
//MS APRE IL MODALE CREA GRUPPO
appGestMappe.showModalNuovoGruppo=function(){

	JBoxGroup.get("gestmappa").showAddGruppo();

 };
 
appGestMappe.copyTavole = function() {
	let selezionato = $('#importaGruppiSlc option:selected').val();
	let idMappa = $('#importaGruppiSlc option:selected').data('mappa');
	
	if(selezionato.length != 0 && (idMappa != null || idMappa != undefined)) {
		/** RECUPERO I LAYER DALLA TAVOLA */
		hrefTavolaUrbamid = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + "layerController/getLayerToTavola?idMappa=" + idMappa + "&nomeTavola=" + selezionato;
		urbamidTavolaResponse = appRest.restUrbamid(hrefTavolaUrbamid, "GET", null)
		risultatoTavola = urbamidTavolaResponse.aaData;
		if(urbamidTavolaResponse.success) {
			/** CREO IL GRUPPO */
			let uGroup = JBoxGroup.get("gestmappa").addGroup(selezionato);
			if(uGroup != null) {
				/** SELEZIONO IL GRUPPO DOVE INSERIRE I LAYER */
				JBoxGroup.get("gestmappa").setSelectedGroup(uGroup.idParent, uGroup.groupName);
				/** INSERISCO I LAYERS RECUPERATI DAL DB */
				for(const item in risultatoTavola) {
					JBoxGroup.get("gestmappa").addLayer(risultatoTavola[item].nomeLayer, risultatoTavola[item].titoloLayer, risultatoTavola[item].trasparenza, risultatoTavola[item].posizione);
				}
			}
		} else {
			/** ERRORE NEL CASO IN CUI NON RIESCE A RECUPERARE I LAYERS DAL DB */
			iziToast.error({
				timeout: 3000,
				balloon: false,
				icon:'fa fa-times', 
				animateInside: false,
				theme: 'dark', 
				position: 'topCenter',
				title: 'ERROR',
				message: 'Nel recupero dei layers!',
			});

		}
	} else {
		/** ERRORE NEL CASO IN CUI NON SELEZIONA NULLA */
		iziToast.error({
			timeout: 3000,
			balloon: false,
			icon:'fa fa-times', 
			animateInside: false,
			theme: 'dark', 
			position: 'topCenter',
			title: 'ERROR',
			message: 'Bisogna scegliere una tavola per importarla!',
		});
		
	}
}

//MS APRE IL MODALE CON LA LISTA DEI TEMI PER L'ASSOCIAZIONE
var dialogTemi={dialog:function(){}}; 
appGestMappe.showTemi=function(){
	//MS assiocia temi a mappa
	
	var sourceDialog = document.getElementById("associaTemiTemplate").innerHTML;
	var templateDialog = Handlebars.compile(sourceDialog);
    outputDialog = templateDialog({aaData:applicationTema.temaList});
    
	 dialogTemi =$('<div style="vertical-align:middle; "></div>').html(outputDialog)
	 dialogTemi.dialog({
    	classes: {'ui-dialog':'ui-dialog modale-messina scelta-tema'},
    	position: { my : "center", at : "center", of : window },
    	title:"Associa Tema",
        height: 385,
        width: 345,
        modal: true,
        autoOpen: false,
        modal: true,
        draggable: true,
        resizable: false,
        close: function( event, ui ) {
        	dialogTemi.dialog( "destroy" );	
	        },
        buttons: {
        	"Inserisci":appGestMappe.addTema,
            "Chiudi": function() {
                $(this).dialog( "close" );
            }
        }
});
	dialogTemi.dialog( "open" );
    
 
 };
//MS OGGETTO DI DEFAULT PER CREAZIONE MAPPA (VECCHIA TABELLA)
 appGestMappe.obj_new_mappa={ 
         
 };
   

//MS ADDIZIONA I TEMI ALLA LISTASELEZIONATI E LI VISUALIZZA NEL DETTAGLIO PAGINA
appGestMappe.addTema=function(tema){

  	var idsTemiDaAssociare=[];
  	$(".show-dialog-application input").each(function(){
  	 		if(this.checked){
  	 			var found=false;
  	 			if(appGestMappe.elTemiSelezionati.length)
  	 			{
  	 				for(i in appGestMappe.elTemiSelezionati)
  	 					{
  	 					if(appGestMappe.elTemiSelezionati[i].id==this.id)
  	 						found=true;
  	 					}
  	 				
  	 			}
  	 			if(!found){
  	         appGestMappe.elTemiSelezionati.push({id:this.id,nome:this.value})
  	         idsTemiDaAssociare.push(this.id);
  	 			}
  	 		}
  	})
  	
  	dialogTemi.dialog( "close" );
  	
  	
    if(idsTemiDaAssociare.length){
  	
    	UMappa.idsTemi=idsTemiDaAssociare;
    	
	jQAjaxSupport.get('mappaController') 
	.setData(JSON.stringify(UMappa))
	.setAction("associaTemiToMap") 
	.showMsg(false)
	.addSuccess(function(data){
		if(data.success && data.aaData!=null){
	   
	   var sourceDialog = document.getElementById("temiAssociatiTemplate").innerHTML;
		var templateDialog = Handlebars.compile(sourceDialog);
	    outputDialog = templateDialog({aaData:appGestMappe.elTemiSelezionati});  
	    
	    $("#boxtemi").html(outputDialog);
 
   }
	})
	.start()
	

	
}

    
         };
 
//MS RIMUOVE IL TEMA DALLA LISTASELEZIONATI E DAL DETTAGLIO PAGINA         
appGestMappe.removeTema=function(id,el){

	 
	 if(appGestMappe.elTemiSelezionati.length)
			{
				for(i in appGestMappe.elTemiSelezionati)
					{
					if(appGestMappe.elTemiSelezionati[i].id==id){
					 
						UMappa.idsTemi=[id];
							jQAjaxSupport.get('mappaController') 
							.setAction("deleteTemiToMap")
							.setData(JSON.stringify(UMappa))
							.showMsg(false)
							.addSuccess(function(data){
							   if(data.success && data.aaData!=null){
								   
								   appGestMappe.elTemiSelezionati.splice(i,1)
								   $(el).remove();
							 
							   }
							}).start()
						
						
						 
					}
					}
				
			}
	  	 
   };


//MS OGGETTO GLOBAL MAPPA 
var UMappa={};
 

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
		this.showCatalog=true;
		this.zoom=10;
 }

//MS ISTRUZIONI PER GESTIRE - DETTMAPPA-HANDLEBARS.JSP
Handlebars.registerHelper('isPubblicato', function (stato) {
    return stato=="P"?"selected":"";
});
Handlebars.registerHelper('isDisabilitato', function (stato) {
    return stato=="D"?"selected":"";
}); 
Handlebars.registerHelper('isChecked', function (mappaPredefinita) {
    return mappaPredefinita?"checked":"";
}); 
Handlebars.registerHelper('isNewMapp', function (value) {
	  return value !== true;
	});
Handlebars.registerHelper('isAbilitato', function (value) {
	  return value?"fa fa-check":"";
	});
Handlebars.registerHelper('valoreTrasparenza', function (value) {
	if(value && value=="" || value==null || typeof value==="undefined")
		value=0;
	return  Number(value)*10;
	});
Handlebars.registerHelper('labelZoom', function (value) {
	if(value && value=="" || value==null || typeof value==="undefined")
		value=50;
	 if(Number(value)<1000)
		 return (Number(value)).toFixed(0)+" m";
		   else if(Number(value)>1001)
		return (Number(value)/1000).toFixed(1)+" km";
	});

// LAYER
 

 
//elimino tutti icaratteri speciali e lo spazio per fomattare un id valido
appGestMappe.replaceId=function (string) {
		return string.replace(/[^a-z0-9\s]/gi, '').replace(/[-\s]/g, '_');
}; 

//MS OGGETTO GLOBAL LAYER 
var UMappaLayer={};

//MS CLASSE LAYER	
function getUMappaLayer(){
	this.isNew=false;
	this.id=0;
	this.idMappa=null;
	this.nomeLayer=null;
	this.titleLayer=null;
	this.idParent=null;
	this.tipo=null;
	this.abilitato=false;
	this.trasparenza=null;
	this.campo1=null;
	this.campo2=null;
	this.campo3=null;
}

appGestMappe.changeZoom=function(){
	var th__ = this; 
	var value = $("#mappa_livello_zoom").val();

	if(value == 10) {
		appGestMappe.mappaSelected.zoom = 19;
		$("#mappa_label_zoom").html("20 m");
	} else if(value == 11) {
		appGestMappe.mappaSelected.zoom = 18;
		$("#mappa_label_zoom").html("50 m");
	} else if(value == 12) {
		appGestMappe.mappaSelected.zoom = 17;
		$("#mappa_label_zoom").html("100 m");
	} else if(value == 13) {
		appGestMappe.mappaSelected.zoom = 16;
		$("#mappa_label_zoom").html("200 m");
	} else if(value == 14) {
		appGestMappe.mappaSelected.zoom = 15;
		$("#mappa_label_zoom").html("500 m");
	} else if(value == 15) {
		appGestMappe.mappaSelected.zoom = 14;
		$("#mappa_label_zoom").html("500 m");
	} else if(value == 16) {
		appGestMappe.mappaSelected.zoom = 13;
		$("#mappa_label_zoom").html("1 km");
	} else if(value == 17) {
		appGestMappe.mappaSelected.zoom = 12;
		$("#mappa_label_zoom").html("2 km");
	} else if(value == 18) {
		appGestMappe.mappaSelected.zoom = 11;
		$("#mappa_label_zoom").html("5 km");
	} else if(value == 19) {
		appGestMappe.mappaSelected.zoom = 10;
		$("#mappa_label_zoom").html("10 km");
	}
}
 
//FUNZIONE PER LUCA
function openModalePermessi(id, nomeLayer,idParent,idMappa){
	
	/** GESTIONE PERMESSI LAYER **/
	permessiLayerOk = [];
	
	hrefLayerPermessiUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/getAllLayerPermessi?idMappa=" + idMappa + "&nomeLayer=" + nomeLayer;
	urbamidLayerPermessiResponse = appRest.restUrbamid(hrefLayerPermessiUrbamid, "GET", null);
	risultatoPermessiLayerAttivi = urbamidLayerPermessiResponse.aaData;
	
	if(risultatoPermessiLayerAttivi != null) {
		for (var i = 0; i < risultatoPermessiLayerAttivi.length; i++) {
			permessiLayerOk.push(risultatoPermessiLayerAttivi[i]);
		}
	}
	
	(appGestMappe.mappaSelected).permessiAttiviLayer = permessiLayerOk;
	
	/** COSTRUZIONE HTML **/
    
	$.each(ruoliList, function (keyRuoli, valueRuoli) {
		valueRuoli.checkedVisualizza = "";
		valueRuoli.checkedModifica = "";
		(appGestMappe).permessiLayer[valueRuoli.id] = false;
		(appGestMappe).permessiModificaLayer[valueRuoli.id] = false;
		for (var i = 0; i < permessiLayerOk.length; i++) {
			var element = permessiLayerOk[i];
			if(element.id_ruolo == valueRuoli.id) {
				if(element.abilita_visualizzazione && !element.abilita_modifica) {
					valueRuoli.checkedVisualizza = "checked";
					(appGestMappe).permessiLayer[valueRuoli.id] = true;
					(appGestMappe).permessiModificaLayer[valueRuoli.id] = false;
				} else if(element.abilita_modifica && !element.abilita_visualizzazione) {
					valueRuoli.checkedModifica = "checked";
					(appGestMappe).permessiLayer[valueRuoli.id] = false;
					(appGestMappe).permessiModificaLayer[valueRuoli.id] = true;
				} else if(element.abilita_visualizzazione && element.abilita_modifica) {
					valueRuoli.checkedVisualizza = "checked";
					valueRuoli.checkedModifica = "checked";
					(appGestMappe).permessiLayer[valueRuoli.id] = true;
					(appGestMappe).permessiModificaLayer[valueRuoli.id] = true;
				}
			};
			
		}

	});
			
	var sourceHtml = document.getElementById("permessiLayer").innerHTML;
	var templateHtml = Handlebars.compile(sourceHtml);
    outputHtml = templateHtml({ruoliList});
	
    
    /** GESTIONE SALVATAGGIO PERMESSI LAYER **/
    this.modalePermessi(outputHtml, nomeLayer ,function() {
    	
    	datiPermessiLayer = [];
    	
    	$.each(appGestMappe.permessiModificati, function (key, permesso) {
        	datiPermessiLayer.push({"id_ruolo": permesso.idRuolo, "abilita_visualizzazione": permesso.visualizza, "abilita_modifica": permesso.modifica, "nome_layer": nomeLayer });
    	});
    	
    	hrefCountLayerPermessiUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/countPermessiLayer?nomeLayer=" + nomeLayer;
    	urbamidCountLayerPermessiResponse = appRest.restUrbamid(hrefCountLayerPermessiUrbamid, "GET", null);
    	
    	if(urbamidCountLayerPermessiResponse.iTotalRecords > 1) {
    		
    		appUtil.confirmOperation(function() {
				appUtil.showLoader(null, 'Per favore, attendere il caricamento della pagina.');
				
				hrefInsertLayerPermessiUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/insertPermessiLayer";
				urbamidLayerPermessiResponse = appRest.restUrbamid(hrefInsertLayerPermessiUrbamid, "POST", JSON.stringify(datiPermessiLayer));
	    		
				if(urbamidLayerPermessiResponse.success) {
	        		
	        		appUtil.hideLoader();
	        		
	        		appGestMappe.permessiModificati = [];
	        		appGestMappe.numPermessiModificati = 0;
	        	
	        		iziToast.show({
						timeout: 3000,
						balloon: false,
						icon:'fa fa-done', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
						title: 'OK',
						message: 'Il permesso &egrave; stato associato con successo!',
					});
	        		
	        	} else {
	    			
	        		appUtil.hideLoader();
	        		
	        		appUtil.showMessageAlertApplication('Errore nell\'inserimento dei permessi', 'Attenzione', false, true, false, null, null);
	        	
	        	}
				
    		}, function() {
    			appUtil.hideLoader();
    		}, null, 'Il permesso assocciato verr&agrave; applicato a tutte le mappe che contengono il livello: ' + urbamidCountLayerPermessiResponse.aaData[0].titleLayer.toUpperCase());
    	
    	} else {
    		
    		hrefInsertLayerPermessiUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/insertPermessiLayer";
        	urbamidLayerPermessiResponse = appRest.restUrbamid(hrefInsertLayerPermessiUrbamid, "POST", JSON.stringify(datiPermessiLayer));
    		
			appUtil.showLoader(null, 'Per favore, attendere il caricamento della pagina.');
        	
        	if(urbamidLayerPermessiResponse.success) {
        		
        		appUtil.hideLoader();
        		
        		appGestMappe.permessiModificati = [];
        		appGestMappe.numPermessiModificati = 0;
        		
        		iziToast.show({
					timeout: 3000,
					balloon: false,
					icon:'fa fa-done', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
					title: 'OK',
					message: 'Il permesso &egrave; stato associato con successo!',
				});
        	
        	} else {
    			
        		appUtil.hideLoader();
        		
        		appUtil.showMessageAlertApplication('Errore nell\'inserimento dei permessi', 'Attenzione', false, true, false, null, null);
        	
        	}
        	
    	}

    });
} 