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
	
	$('#input-codice').keyup(function(){
        $(this).val($(this).val().toUpperCase());
    });
	
	/**Imposto l'header della pagina**/
	appGestUtente.start();
	
	/**Carico la tabella degli utenti**/
	appGestUtente.inizializzaDatatableUtenti();
	
	/********************************************************************************************************************/
	/******************************		GESTIONE TAB UTENTI					*********************************************/
	/********************************************************************************************************************/
	/**** MOSTRA UTENTI	*****************/
	$(document).on("click", ".utenti", function() {
		appGestUtente.inizializzaDatatableUtenti();
		$("#eleUtente").show();
		$("#detNewUtente").hide();
		$("#containerPageUtenti").show();
		$("#containerPageRuoliPermessi").hide();
		$("#ruoli").hide();
		$("#permessiRuolo").hide();
	});
	/**** NUOVO UTENTE	*****************/
	$(document).on("click", "button.btnNuovoUtente", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$("#eleUtente").hide();
		$("#detNewUtente").show();
		$("#containerPageRuoliPermessi").hide();
				
		$("#map-title").html("Nuovo Utente ");
		appGestUtente.utenteDetMod = {};
		$("#input-username").val ( appGestUtente.utenteSelectedList.length>0?appGestUtente.utenteDetMod.username:"");
		$("#input-codicefiscale").val ( appGestUtente.utenteSelectedList.length>0?appGestUtente.utenteDetMod.codiceFiscale:"");
		$("#input-nome").val ( appGestUtente.utenteSelectedList.length>0?appGestUtente.utenteDetMod.nome:"");
		$("#input-cognome").val ( appGestUtente.utenteSelectedList.length>0?appGestUtente.utenteDetMod.cognome:"");
		$("#input-abilitato").prop("checked", appGestUtente.utenteSelectedList.length>0?appGestUtente.utenteDetMod.abilitato:false);
		$("#input-note").val ( appGestUtente.utenteSelectedList.length>0?appGestUtente.utenteDetMod.note:"");
		$("#input-email").val ( appGestUtente.utenteSelectedList.length>0?appGestUtente.utenteDetMod.email:"");
		hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/ruoli";
		urbamidRuoliResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
		if ( urbamidRuoliResponse!=null ) {
			appGestUtente.ruoliPermessiList 		= urbamidRuoliResponse.aaData;
			var optionRuoli = "";
			$.each( appGestUtente.ruoliPermessiList, function( keyR, valueR ) {
				optionRuoli += "<option value='"+valueR.id+"'>"+valueR.denominazione+"</option>";
			});
			$("#input-ruoli").html(optionRuoli);
		}
		appGestUtente.utenteDetMod.ruolo="";
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});	
	/**** DETTAGLIO\MODIFICA UTENTE	*****************/
	$(document).on("click", "button.btnDettaglioUtente", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$("#eleUtente").hide();
		$("#detNewUtente").show();
		$("#containerPageRuoliPermessi").hide();
				
		var url = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/utente?";
			url += 'id=' + appGestUtente.utenteSelectedList[0].id;

		$.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: false,
			success: function (data) {
			
				if (data.success && data.aaData!=null) {
					
					appGestUtente.utenteDetMod = data.aaData;
					$("#map-title").html("Dettaglio "+ appGestUtente.utenteDetMod.nome);
					$("#input-username").val ( appGestUtente.utenteDetMod.username );
					$("#input-codicefiscale").val ( appGestUtente.utenteDetMod.codiceFiscale );
					$("#input-nome").val ( appGestUtente.utenteDetMod.nome );
					$("#input-cognome").val ( appGestUtente.utenteDetMod.cognome );
					$("#input-abilitato").prop("checked", appGestUtente.utenteDetMod.abilitato );
					$("#input-note").val ( appGestUtente.utenteDetMod.note );
					$("#input-email").val ( appGestUtente.utenteDetMod.email );
					
					hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/ruoli";
					urbamidRuoliResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
					if ( urbamidRuoliResponse!=null ) {
						appGestUtente.ruoliPermessiList 		= urbamidRuoliResponse.aaData;
						var optionRuoli = "";
						$.each( appGestUtente.ruoliPermessiList, function( keyR, valueR ) {
							if ( appGestUtente.utenteDetMod.ruoli!=null && appGestUtente.utenteDetMod.ruoli.length==1 && appGestUtente.utenteDetMod.ruoli[0].id==valueR.id)
								optionRuoli += "<option value='"+valueR.id+"' selected>"+valueR.denominazione+"</option>";
							else 
								optionRuoli += "<option value='"+valueR.id+"'>"+valueR.denominazione+"</option>";
						});
						$("#input-ruoli").html(optionRuoli);
					}
					appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				} else  {
				
					error = true
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Errore nella recupero utente!',
						animateInside: false,
						position: 'topCenter',
					});
				}
			}
		});			
	});
	/**** MOSTRA UTENTI	*****************/
	$(document).on("click", ".btnChiudiUtente", function() {
		appGestUtente.inizializzaDatatableUtenti();
		appGestUtente.utenteSelectedList = [];
		$("#map-title").html("Elenco Utenti");
		$("#eleUtente").show();
		$("#detNewUtente").hide();
		$("#containerPageRuoliPermessi").hide();
		$("#ruoli").hide();
		$("#permessiRuolo").hide();
		$(".btnDettaglioUtente").get(0).setAttribute("disabled","disabled");
		$(".btnEliminaUtente").get(0).setAttribute("disabled","disabled");
	});
	/**** RIMUOVI UTENTE	*****************/
	$(document).on("click", "button.btnEliminaUtente", function() {
		error = false;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		setTimeout(function(){
			if(appGestUtente.utenteSelectedList.length != 0 && appGestUtente.utenteSelectedList != null && appGestUtente.utenteSelectedList != undefined) {
				for(let i in appGestUtente.utenteSelectedList) {
					var url = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/eliminaUtenteById?";
						url += 'id=' + appGestUtente.utenteSelectedList[i].id;
						url += '&ruolo=' + appGestUtente.utenteSelectedList[i].idRuolo;

					$.ajax({
						type : "POST",
						processData : false,
						contentType : 'application/json; charset=UTF-8',
						url : url,
						cache : true,
						async: false,
						success: function (data) {
							if(!data.success) {
								error = true
								iziToast.error({
									title: 'Attenzione',
									theme: 'dark',
									icon:'fa fa-times',
									message: 'Errore nella cancellazione degli utenti!',
									animateInside: false,
									position: 'topCenter',
								});
							}
						}
					});	
				}
			}
			if(!error) {
				$(".btnDettaglioUtente").get(0).setAttribute("disabled","disabled");
				$(".btnEliminaUtente").get(0).setAttribute("disabled","disabled");
				appGestUtente.inizializzaDatatableUtenti();
				appGestUtente.utenteSelectedList = [];
			}
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		}, 500);
		

	});
	/**** NUOVO UTENTE	*****************/
	$(document).on("click", "button.btnSalvaUtente", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		username = "";
		error = false;
		var url = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/salvaUtente?";
		if ($("#input-username").val () !== null && $("#input-username").val () !== undefined && $("#input-username").val () !== '') { 
			url += 'username=' + $("#input-username").val ();
			username = $("#input-username").val ();
		}
		if ($("#input-codicefiscale").val () !== null && $("#input-codicefiscale").val () !== undefined && $("#input-codicefiscale").val () !== '') 
			url += '&codiceFiscale=' + $("#input-codicefiscale").val ();
		else {error= true;}
		if ($("#input-nome").val () !== null && $("#input-nome").val () !== undefined && $("#input-nome").val () !== '') 
			url += '&nome=' + $("#input-nome").val ();
		else {error= true;}
		if ($("#input-cognome").val () !== null && $("#input-cognome").val () !== undefined && $("#input-cognome").val () !== '') 
			url += '&cognome=' + $("#input-cognome").val ();
		else {error= true;}
		if ($("#input-note").val () !== null && $("#input-note").val () !== undefined && $("#input-note").val () !== '') 
			url += '&note=' + $("#input-note").val ();
		if ($("#input-email").val () !== null && $("#input-email").val () !== undefined && $("#input-email").val () !== '') 
			url += '&email=' + $("#input-email").val ();
		url += '&abilitato=' + $("#input-abilitato").is(':checked');
		
		if ($("#input-ruoli").val () !== null && $("#input-ruoli").val () !== undefined && $("#input-ruoli").val () !== '') 
			url += '&ruolo=' + $("#input-ruoli").val ();
		
		if (!error) {
			$.ajax({
				type : "POST",
				processData : false,
				contentType : 'application/json; charset=UTF-8',
				url : url,
				cache : true,
				async: false,
				success: function (data) {
					if(data.success) {
						
						appGestUtente.utenteSelectedList = [];
						$("#map-title").html("Elenco Utenti");
						$("#eleUtente").show();
						$("#detNewUtente").hide();
						$("#containerPageRuoliPermessi").hide();
						$("#ruoli").hide();
						$("#permessiRuolo").hide();
						$(".btnDettaglioUtente").get(0).setAttribute("disabled","disabled");
						$(".btnEliminaUtente").get(0).setAttribute("disabled","disabled");
						appGestUtente.inizializzaDatatableUtenti();
						iziToast.show({
							timeout: 3000,
							balloon: false,
							icon:'fa fa-done', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
							title: 'OK',
							message: 'Utente '+username+' salvato con successo ',
						});
						appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
					} else {
						iziToast.show({
							timeout: 3000,
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
							title: 'Errore',
							message: 'Si &#233; verificato un errore durante il salvataggio. '
						});
						appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
					}
				},
				error: function (e) {
					iziToast.show({
						timeout: 3000,
						balloon: false,
						icon:'fa fa-info-circle', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
						title: 'Errore',
						message: 'Si &#233; verificato un errore durante il salvataggio. '
					});
					appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				}
			});
		} else {
			iziToast.show({
				timeout: 3000,
				balloon: false,
				icon:'fa fa-info-circle', 
				animateInside: false,
				theme: 'dark', 
				position: 'topCenter',
				title: 'Errore',
				message: 'Campi obbligatori non valorizzati '
			});
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		}
	});	
	
	
	/********************************************************************************************************************/
	/******************************		GESTIONE TAB RUOLI PERMESSI			*********************************************/
	/********************************************************************************************************************/
	/**** DETTAGLIO RUOLO	*****************/
	$(document).on("click", "button.btnNuovoRuoloPermesso", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		if ( appGestRuoliPermessi.ruoloDetMod==null ) {
			appUtil.showMessageAlertApplication('Devi selezionare un solo ruolo per il dettaglio', 'Attenzione', false, true, false, null, null);
		} else {
			hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/permessi";
			urbamidPermessiResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
			
			if ( urbamidPermessiResponse!=null) {
				appGestRuoliPermessi.permessiList 			= urbamidPermessiResponse.aaData;

				$("#map-title").html("Nuovo Ruolo");
				
				
				$("#input-codice").prop( "disabled", false );
				$("#input-codice").val ("");
				$("#input-denominazione").val ("");
				$("#input-descrizione").val ("");
				
                var rowRuoli = "<div  class='row form-row' style='background-color: #f3f3f3;font-weight: bold;border:1px solid #e9ecef;'>" +
				  							"<div class='form-group col-xs-6 col-md-4 col-lg-6'></div>";
				rowRuoli += "<div class='form-group col-xs-6 col-md-4 col-lg-6' style='text-align: center;'>"+$("#input-denominazione").val ()+"</div>";
				rowRuoli +="</div>";
				
				var rowPermessi = "";
				var padre = "";
				$.each( appGestRuoliPermessi.permessiList, function( keyP, valueP ) {
					if (padre!=valueP.padre){
                        rowPermessi += "<div  class='row form-row' style='height: 36px;border-bottom: 1px solid #333;background-color: #f3f3f3;padding: 7px 0 7px 40px;text-transform: uppercase;font-weight: bold;border-top: 1px solid #333;'>"+valueP.padre+"</div>";
						padre = valueP.padre;
					}
					
					valueRP = appGestRuoliPermessi.ruoloDetMod;
                	id = valueP.codice+"|NUOVO";
                	//checked = appGestRuoliPermessi.hmRuoliPermessi[id]?"checked":"";
                	checked = valueP.checked?"checked":"";
                	disabled = valueP.blocked?"disabled":"";
                	idRow = 'row_'+valueP.codice;
					rowPermessi += "<div id='row_"+valueP.codice+"' class='row form-row' style='padding: 10px 0px;height: 36px;border-bottom: 1px solid #e9ecef;'>";
                    	rowPermessi += "<div class='form-group col-xs-6 col-md-4 col-lg-6'>"+valueP.denominazione+"</div>";
                    	rowPermessi += "<div class='form-group col-xs-6 col-md-4 col-lg-6' style='text-align:center;'>";
                    	//rowPermessi += "<input id=\"checkbox-"+id+"\" "+disabled+" "+checked+" type=\"checkbox\" onclick=\"appGestRuoliPermessi.showPermessoSelectRow('"+id+"','row_"+valueP.codice+"');\">";
                    	rowPermessi += "<input id=\"checkbox-"+id+"\" "+disabled+" "+checked+" type=\"checkbox\" onclick=\"appGestRuoliPermessi.showPermessoSelectRow('"+id+"','"+idRow+"');\">";
                    	rowPermessi += "</div>";
                    rowPermessi += "</div>";
                    if (checked) appGestRuoliPermessi.showPermessoSelectRow(id,idRow);
				});
				
				$("#listaRuoliPermessi").html(rowRuoli + rowPermessi);
			}
		}

	
		$("#ruoli.tab-content").hide();
		$("#permessiRuolo").show();
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});
	/**** DETTAGLIO RUOLO	*****************/
	$(document).on("click", "button.btnDettaglioRuoloPermesso", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		if ( appGestRuoliPermessi.ruoloDetMod==null ) {
			appUtil.showMessageAlertApplication('Devi selezionare un solo ruolo per il dettaglio', 'Attenzione', false, true, false, null, null);
		} else {
			hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/permessi";
			urbamidPermessiResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
			
			if ( urbamidPermessiResponse!=null) {
				appGestRuoliPermessi.permessiList 			= urbamidPermessiResponse.aaData;

				$("#map-title").html("Dettaglio "+appGestRuoliPermessi.ruoloDetMod.codice);
				
				$("#input-codice").prop( "disabled", true );
				$("#input-codice").val ( appGestRuoliPermessi.ruoloDetMod!=null?appGestRuoliPermessi.ruoloDetMod.codice:"");
				$("#input-denominazione").val ( appGestRuoliPermessi.ruoloDetMod!=null?appGestRuoliPermessi.ruoloDetMod.denominazione:"");
				$("#input-descrizione").val ( appGestRuoliPermessi.ruoloDetMod!=null?appGestRuoliPermessi.ruoloDetMod.descrizione:"");
				
                var rowRuoli = "<div  class='row form-row' style='background-color: #f3f3f3;font-weight: bold;border:1px solid #e9ecef;'>" +
				  							"<div class='form-group col-xs-6 col-md-4 col-lg-6'></div>";
				    rowRuoli += "<div class='form-group col-xs-6 col-md-4 col-lg-6' style='text-align: center;'>"+appGestRuoliPermessi.ruoloDetMod.denominazione+"</div>";
				rowRuoli +="</div>";
				
				var rowPermessi = "";
				var padre = "";
				$.each( appGestRuoliPermessi.permessiList, function( keyP, valueP ) {
					if (padre!=valueP.padre){
                        rowPermessi += "<div  class='row form-row' style='height: 36px;border-bottom: 1px solid #333;background-color: #f3f3f3;padding: 7px 0 7px 40px;text-transform: uppercase;font-weight: bold;border-top: 1px solid #333;'>"+valueP.padre+"</div>";
						padre = valueP.padre;
					}
					
					valueRP = appGestRuoliPermessi.ruoloDetMod;
                	id = valueP.codice+"|"+valueRP.codice;
                	checked = appGestRuoliPermessi.hmRuoliPermessi[id]?"checked":"";
                	disabled = valueP.blocked?"disabled":"";
					rowPermessi += "<div id='row_"+valueP.codice+"' class='row form-row' style='padding: 10px 0px;height: 36px;border-bottom: 1px solid #e9ecef;'>";
                    	rowPermessi += "<div class='form-group col-xs-6 col-md-4 col-lg-6'>"+valueP.denominazione+"</div>";
                    	rowPermessi += "<div class='form-group col-xs-6 col-md-4 col-lg-6' style='text-align:center;'>";
                    		rowPermessi += "<input id=\"checkbox-"+id+"\" "+disabled+" "+checked+" type=\"checkbox\" onclick=\"appGestRuoliPermessi.showPermessoSelectRow('"+id+"','row_"+valueP.codice+"');\">";
                    	rowPermessi += "</div>";
                    rowPermessi += "</div>";
				});
				
				$("#listaRuoliPermessi").html(rowRuoli + rowPermessi);
			}
		}

		$("#ruoli.tab-content").hide();
		$("#permessiRuolo").show();
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});
	/**** ELIMINA RUOLO	*****************/
	$(document).on("click", "button.btnEliminaRuoloPermesso", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.each()
		if ( appGestRuoliPermessi.ruoloDetMod==null ) {
			appUtil.showMessageAlertApplication('Devi selezionare un solo ruolo per il dettaglio', 'Attenzione', false, true, false, null, null);
		} else {
			var found = false;
			$.each( appGestUtente.utentiList, function( keyU, valueU ) {
				$.each( valueU.ruoli, function( keyUR, valueUR ) {
                    if(valueUR.codice===appGestRuoliPermessi.ruoloDetMod.codice)
                        found=true;
				});
			});
			
			hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/mappaController/getRuoloMappa?idRuolo=" + appGestRuoliPermessi.ruoloDetMod.id;
			urbamidMappaRuoloResponse = appRest.restUrbamid(hrefUrbamid, "GET", null);
			
			if(!found && !urbamidMappaRuoloResponse.success) {
				hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/deleteRuolo?"+
									"codice="+appGestRuoliPermessi.ruoloDetMod.codice;
				urbamidDeleteRuoliResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
				
				if ( urbamidDeleteRuoliResponse!=null) {
					iziToast.show({
						timeout: 3000,
						balloon: false,
						icon:'fa fa-done', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
						title: 'OK',
						message: 'Il ruolo '+appGestRuoliPermessi.ruoloDetMod.codice+' &#233; stato eliminato',
					});
					$("button.ruoliPermessi").click();
					appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');				
				} else {
					iziToast.show({
						timeout: 3000,
						balloon: false,
						icon:'fa fa-info-circle', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
						title: 'Errore',
						message: 'Si &#233; verificato un errore durante eliminazione ruolo. '
					});
					appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				}
			} else {
				iziToast.show({
					timeout: 3000,
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
					title: 'Errore',
					message: 'Il ruolo &#233; collegato ad un Utente o a una Mappa.'
				});
				appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			}
		}
	});
	/**** MOSTRA RUOLI PERMESSI	*****************/
	$(document).on("click", ".ruoliPermessi", function() {
		appGestRuoliPermessi.inizializzaRuoliPermessi();
		$("#eleUtente").hide();
		$("#detNewUtente").hide();
		$("#containerPageUtenti").hide();
		$("#containerPageRuoliPermessi").show();
		$("#ruoli.tab-content").show();
		$("#permessiRuolo").hide();
		$(".btnDettaglioRuoloPermesso").get(0).setAttribute("disabled","disabled");
		$(".btnEliminaRuoloPermesso").get(0).setAttribute("disabled","disabled");
		$("#map-title").html("Elenco Ruoli Permessi");
	});
	/**** MODIFICA MAPPA	*****************/
	$(document).on("click", ".btnSalvaRuoloPermesso", function() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var modifichePermessi	= 0;			//Numero di modifiche ai permessi
		var modificheBase		= false;		//Modifica ai dati di base
		var idRuolo 			= null;			//Id del ruolo utilizzato in fase di nuovo e modifica
		
		/**GESTIONE MODIFICHE DATI DI BASE RUOLO**/
		if ( appGestRuoliPermessi.ruoloDetMod.modificato ) {				
			
			var found = false;
			//Verifica se è già presente
			$.each( appGestRuoliPermessi.ruoliPermessiList, function( keyR, valueR ) {
				if ($("#input-codice").val()===valueR.codice)
				    found = true;
			});
			
			if (found || !found) {
				
				if($("#input-denominazione").val()=="") {
					
					modificheBase	= true;
				
				} else {
				
					modificheBase	= true;
					hrefUrbamid 	= appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/salvaRuolo?" +
											"codice="+$("#input-codice").val() +
											"&denominazione="+$("#input-denominazione").val() +
											"&descrizione="+$("#input-descrizione").val();
					
					appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
					
					$.ajax({
						type: "POST",
						url: hrefUrbamid,
						data: data,
						processData: false,
						contentType: "application/json",
						cache: false,
						async:false,
						timeout: 600000,
						success: function (data) {

							if(data.success) {

								idRuolo 											= data.aaData.id;
								appGestRuoliPermessi.ruoloDetMod.codice 			= $("#input-codice").val();
								appGestRuoliPermessi.ruoloDetMod.denominazione 		= $("#input-denominazione").val();
								appGestRuoliPermessi.ruoloDetMod.descrizione 		= $("#input-descrizione").val();
								appGestRuoliPermessi.utenteModificato 				= false;
								modificheBase 										= false;
			            	} else {
								iziToast.show({
									timeout: 3000,
									balloon: false,
									icon:'fa fa-info-circle', 
									animateInside: false,
									theme: 'dark', 
									position: 'topCenter',
									title: 'Errore',
									message: 'Si &#233; verificato un errore durante il salvataggio del ruolo '+$("#input-codice").val()
								});
			            	}
						},
						error: function (e) {
							appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							iziToast.show({
								timeout: 3000,
								balloon: false,
								icon:'fa fa-info-circle', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
								title: 'Errore',
								message: 'Si &#233; verificato un errore durante il salvataggio del ruolo '+$("#input-codice").val()
							});
						}	
					});
					
				}
			
			}
			
		}
			
		if(appGestRuoliPermessi.modifyPermessi && appGestRuoliPermessi.hmRuoliPermessiMod.length>0) {
			for (var keyRP in appGestRuoliPermessi.hmRuoliPermessiMod) {
				modifichePermessi++;
		        valueRP = appGestRuoliPermessi.hmRuoliPermessiMod[keyRP];
				codes = valueRP.split("|");
				azione = "";
		        hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/";
		        if ( appGestRuoliPermessi.hmRuoliPermessi[valueRP] ) {
		        	hrefUrbamid += "salvaRuoloPermesso?";
		            azione = "associato";
		        } else { 
		            hrefUrbamid += "deleteRuoloPermesso?";
		            azione = "eliminato";
		        }

				idPermesso = null;
				descPermesso = "";
				$.each( appGestRuoliPermessi.permessiList, function( keyP, valueP ) {
					if (valueP.codice==codes[0]) {
						idPermesso = valueP.id;
						descPermesso = valueP.denominazione;
						return false;
					}
				});
						
				if(idRuolo==null) {
					//E' una modifica, recupero il ruolo dall hashmap
					descRuolo = "";
					$.each( appGestRuoliPermessi.ruoliPermessiList, function( keyR, valueR ) {
						if (valueR.codice==codes[1]) {
							idRuolo = valueR.id;
							descRuolo = valueR.denominazione;
							return false;
						}
					});
					} else {
						//E' un nuovo ruolo, recupero l'idRuolo salvato prima
					}
						
					hrefUrbamid += "idRuolo="+idRuolo+"&idPermesso="+idPermesso;
					$.ajax({
						type: "POST",
						url: hrefUrbamid,
						data: data,
						processData: false,
						contentType: "application/json",
						cache: false,
						async:false,
						timeout: 600000,
						success: function (data) {
								
							if(data.success) {
									
								delete appGestRuoliPermessi.hmRuoliPermessiMod[keyRP];
								appGestRuoliPermessi.hmRuoliPermessiMod.length = appGestRuoliPermessi.hmRuoliPermessiMod.length-1;
								modifichePermessi = modifichePermessi-1;
				            
							} else {
				            		
								iziToast.show({
									timeout: 3000,
									balloon: false,
									icon:'fa fa-info-circle', 
									animateInside: false,
									theme: 'dark', 
									position: 'topCenter',
									title: 'Errore',
									message: 'Si &#233; verificato un errore durante il salvataggio del ruolo '+$("#input-codice").val()
								});
									
								appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				            }
						},
						error: function (e) {
								
							iziToast.show({
								timeout: 3000,
								balloon: false,
								icon:'fa fa-info-circle', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
								title: 'Errore',
								message: 'Si &#233; verificato un errore durante il salvataggio del ruolo '+$("#input-codice").val()
							});
							appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
						}
					});
				}
			
			}
				
		/**GESTIONE MODIFICHE AI PERMESSI DEL RUOLO**/
			
		if(modifichePermessi==0 && !modificheBase){
			iziToast.show({
				timeout: 3000,
				balloon: false,
				icon:'fa fa-done', 
				animateInside: false,
				theme: 'dark', 
				position: 'topCenter',
				title: 'OK',
				message: 'Il ruolo '+$("#input-codice").val()+' &#233; salvato'
			});
			
			$(".btnSalvaRuoloPermesso").get(0).setAttribute("disabled","disabled");
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			} else if ($("#input-denominazione").val()==""){
				iziToast.show({
					timeout: 3000,
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
					title: 'Errore',
					message: 'Il Ruolo deve avere il campo denominazione'
				});
				appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			} else {
				iziToast.show({
					timeout: 3000,
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
					title: 'Errore',
					message: 'Ruolo '+$("#input-codice").val()+' presente'
				});
				appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			}
		
		appGestRuoliPermessi.modifyPermessi = false; 
		appGestRuoliPermessi.listRuoliPermessiDetMod = [];			
	
	});

});

appGestUtente = {
		
		detNewUtente					: new Object(),
		
		utentiList						: [],
		urlParams						: new Object(),
		tabellaUtenti					: null,
		utenteSelectedList				: [],
		ruoliPermessiList				: [],
		
		mappaTools						: [],
		mappaRicerche					: [],
		
		modifica						: false,
		blocked							: true,
		tabellaPageLength				: 10,
		
		layerLoaded						: false,
		
		start : function (){
		},
		
		inizializzaDatatableUtenti	: function () {
			
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			/**distruggo la vecchia mappa**/
			if ( $.fn.DataTable.isDataTable( '#tabellaUtenti' ) )
				(appGestUtente.tabellaUtenti).destroy();
			
			/**inizializzo mappa**/
			hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/utenti";
			urbamidUtentiResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
			if ( urbamidUtentiResponse!=null) {
				appGestUtente.utentiList 		= urbamidUtentiResponse.aaData;
				appGestUtente.tabellaUtenti 	= $('#tabellaUtenti').DataTable( {
					pageLength	: appGestUtente.tabellaPageLength,
			        data		: urbamidUtentiResponse.aaData,
			        language	: Constants.TABLE_LANGUAGE,
			        order		: [ 0, "asc" ],
			        select		: {
		                style: 'multi'
		            },
		            "searching": true,
		            "aLengthMenu": [
		                [5, 10, 25, 50, -1],
		                [5, 10, 25, 50, "Tutti"]
		            ],
			        columns		: [
						{mData:"select", 
							className:"nowrap", 
							bSortable: false,
							checkboxes: {
						        selectRow: true
						    },
						    render : function(data,type,row,meta){
						    	var html = "";
						    	if (row!=null && row.id!=null && row.ruoli!=null && row.ruoli[0]!=null) {
							    	html = "<div class='custom-control custom-checkbox'>"+
							            		"<input id='checkbox-record-"+row.id+"' type='checkbox' class='custom-control-input' onclick=\"appGestUtente.showUtenteSelectRow("+row.id+", "+row.ruoli[0].id+", '"+ row.username +"', this);\"'>"+
							            		"<label class='custom-control-label' for='checkbox-record-"+row.id+"'><span class='sr-only'>Seleziona</span></label>"+
							               "</div>";
							    }
								return html;
						     },
						     title: ""
						},
			            {mData:"username",title: "Username"},
			            {mData:"codiceFiscale",title: "Codice Fiscale"},
			            {mData:"nome",title: "Nome"},
			            {mData:"cognome",title: "Cognome"},
			            {mData:"ruolo.denominazione",
			                title: "Ruolo",
			                render : function(data,type,row,meta){
			                	return (row.ruoli!=null && row.ruoli.length>0)? row.ruoli[0].denominazione : "";
			                }
			            },
			            {mData:"abilitato", 
			            	className:"nowrap", 
			            	bSortable: false,
			            	checkboxes: {
			                    selectRow: true
			                },
			                searching: true,
				            aLengthMenu: [
				                [5, 10, 25, 50, -1],
				                [5, 10, 25, 50, "Tutti"]
				            ],
			                render : function(data,type,row,meta){
			                	if(data)
			                		btnAbDis = "<div class='btn-group p5'>" +
			                						"<button type='button' class='btn btn-danger disabilitaUtente' onclick='appGestUtente.disabilitaUtenteSelectRow("+row.id+", false);'>" +
            										  "<span class='label'>Disabilita</span>" +
            										"</button>" +
            									"</div>";
			                	else
			                		btnAbDis = "<div class='btn-group p5'>" +
			                						"<button type='button' class='btn btn-success abilitaUtente' onclick='appGestUtente.disabilitaUtenteSelectRow("+row.id+", true);'>" +
									  					"<span class='label'>Abilita</span>" +
									  				"</button>" +
									  			"</div>";
			                	return btnAbDis;
	                         },
	                         title: "Azione"
	                    }
			       	],
			       	drawCallback: function( settings ) {
			       		/**imposto il numero di row per pagina**/
						$(".risultati-pagina").val(appGestUtente.tabellaPageLength);
						appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			        }
		            
			    });
			}
		},
		
		showUtenteSelectRow : function ( id, idRuolo, username, el ){
			var found = false;
			
			if(el.checked) {
				(appGestUtente.utenteSelectedList).push( {'id': id, 'idRuolo': idRuolo, 'username': username} );
			} else {
				for (i=0;i<(appGestUtente.utenteSelectedList).length;i++){
					if ((appGestUtente.utenteSelectedList)[i].id == id) {
						(appGestUtente.utenteSelectedList).splice(i, 1);
						found = true;
						break;
					}
				}
			}

			if((appGestUtente.utenteSelectedList).length < 1) {
				$(".btnDettaglioUtente").get(0).setAttribute("disabled","disabled");
				$(".btnEliminaUtente").get(0).setAttribute("disabled","disabled");
			} else if ( (appGestUtente.utenteSelectedList).length > 1 ) {
				$(".btnDettaglioUtente").get(0).setAttribute("disabled","disabled");
				$(".btnEliminaUtente").get(0).removeAttribute("disabled");
				for(i in appGestUtente.utenteSelectedList){// cosi se perdono il focus l'utente li puo visualizzare
					$("#checkbox-record-"+appGestUtente.utenteSelectedList[i].id).each(function(){ this.checked=true; })}
			} else {
				$(".btnDettaglioUtente").get(0).removeAttribute("disabled");
				$(".btnEliminaUtente").get(0).removeAttribute("disabled");
			}
			
			$.each( appGestUtente.utentiList, function( keyU, valueU ) {
				
				if (valueU.id==id) {
					appGestUtente.utenteDetMod = valueU;
					return true;
				}
			});
		},
		
		disabilitaUtenteSelectRow : function ( id, abilitato){
			
			$.each( appGestUtente.utentiList, function( keyU, valueU ) {
				if (valueU.id==id) {
					appGestUtente.utenteDetMod = valueU;
					return true;
				}
			});

			var url = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/salvaUtente?";
				url += 'username=' + appGestUtente.utenteDetMod.username;
				url += '&codiceFiscale=' + appGestUtente.utenteDetMod.codiceFiscale;
				url += '&nome=' + appGestUtente.utenteDetMod.nome;
				url += '&cognome=' + appGestUtente.utenteDetMod.cognome;
				url += '&note=' + appGestUtente.utenteDetMod.note;
				url += '&email=' + appGestUtente.utenteDetMod.email;
				url += '&abilitato=' + abilitato;
				url += '&ruolo=' + appGestUtente.utenteDetMod.ruoli[0].id;
			
			$.ajax({
				type : "POST",
				processData : false,
				contentType : 'application/json; charset=UTF-8',
				url : url,
				cache : true,
				async: false,
				success: function (data) {
					if(data.success) {
						
						$("#eleUtente").show();
						$("#detNewUtente").hide();
						$("#containerPageRuoliPermessi").hide();
						$("#ruoli").hide();
						$("#permessiRuolo").hide();
						$(".btnDettaglioUtente").get(0).setAttribute("disabled","disabled");
						$(".btnEliminaUtente").get(0).setAttribute("disabled","disabled");
						appGestUtente.inizializzaDatatableUtenti();
						appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');	
					}
				},
				error: function (e) {
					alert("ERRORE");
					appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');	
				}
			});
		},
		
		changeNumResult : function () {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			appGestUtente.tabellaPageLength = $(".risultati-pagina").val();
			appGestUtente.inizializzaDatatableUtenti();
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		},
		
		changeDettUtente : function ( ){
			utenteModificato = false;
            if( !(  (appGestUtente.utenteDetMod.username === $("#input-username").val()) &&
                    (appGestUtente.utenteDetMod.codiceFiscale === $("#input-codicefiscale").val()) &&
                    (appGestUtente.utenteDetMod.ruoli[0].id === $("#input-ruoli").val()) &&
                    (appGestUtente.utenteDetMod.nome === $("#input-nome").val()) &&
                    (appGestUtente.utenteDetMod.cognome === $("#input-cognome").val())==-1) )
                utenteModificato = true;

			if (utenteModificato)
			    $(".btnSalvaUtente").get(0).removeAttribute("disabled");
			else 
			    $(".btnSalvaUtente").get(0).setAttribute("disabled","disabled");
		}
};

appGestRuoliPermessi = {
		
		urlParams						: new Object(),
		utenteModificato				: false,
		
		ruoliSelectedList				: [],
		codRuoliSelectedHm				: [],
		permessiSelectedList			: [],
		
		modifica						: false,
		blocked							: true,
		
		
		layerLoaded						: false,
		
		
		tabellaRuoli					: null,
		tabellaPageLength				: 10,
		ruoloDetMod						: null,
		
		hmRuoli							: [],
		hmRuoliPermessi					: [],
		modifyPermessi					: false,
		listRuoliPermessiDetMod			: [],
		hmRuoliPermessiMod				: [],
		
		start : function (){
		},
		
		inizializzaRuoliPermessi	: function () {
			
			appGestRuoliPermessi.ruoliSelectedList 		= [];
			appGestRuoliPermessi.codRuoliSelectedHm		= [];
			appGestRuoliPermessi.ruoloDetMod 			= {};
			
			/**recupero permessi**/
			hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/permessi";
			urbamidPermessiResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
				
			/**recupero ruoli**/
			hrefUrbamid = appUtil.getUrbamidOrigin()+"/UrbamidWeb/profileManagerController/ruoli";
			urbamidRuoliResponse = appRest.restUrbamid(hrefUrbamid, "POST", null);
			
			if ( urbamidRuoliResponse!=null) {
				
				/**distruggo la vecchia mappa**/
				
				if ( $.fn.DataTable.isDataTable( '#tabellaRuoli' ) )
					$('#tabellaRuoli').DataTable().destroy();
				
				appGestRuoliPermessi.ruoliList 		= urbamidUtentiResponse.aaData;
				appGestRuoliPermessi.tabellaRuoli 	= $('#tabellaRuoli').dataTable( {
					pageLength	: appGestRuoliPermessi.tabellaPageLength,
			        data		: urbamidRuoliResponse.aaData,
			        language	: Constants.TABLE_LANGUAGE,
			        order		: [ 0, "asc" ],
			        select		: {
		                style: 'multi'
		            },
		            searching: true,
		            aLengthMenu: [
		                [5, 10, 25, 50, -1],
		                [5, 10, 25, 50, "Tutti"]
		            ],
			        columns		: [
						{mData:"select", 
							className:"nowrap", 
							bSortable: false,
							checkboxes: {
						        selectRow: true
						    },
						    render : function(data,type,row,meta){
								return "<div class='custom-control custom-checkbox'>"+
						            	  "<input id='checkbox-record-"+row.codice+"' type='checkbox' class='custom-control-input' onclick='appGestRuoliPermessi.showRuoloSelectRow("+row.id+",\""+row.codice+"\", this);'>"+
						            	  "<label class='custom-control-label' for='checkbox-record-"+row.codice+"'><span class='sr-only'>Seleziona</span></label>"+
						               "</div>";
						     },
						     title: ""
						},
			            {mData:"codice",title: "Codice"},
			            {mData:"denominazione",title: "Denominazione"},
			            {mData:"descrizione",title: "Descrizione"},
			            {mData:"ruoloDefault",
							render : function(data,type,row,meta){
								return (row.ruoloDefault)?"SI":"NO";
						     },
						     title: "Predefinito"}
			       	],
			       	drawCallback: function( settings ) {
			       		/**imposto il numero di row per pagina**/
						$(".risultati-ruoli-pagina").val(appGestRuoliPermessi.tabellaPageLength);
						appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			        }
			    });
			}
			
			if ( urbamidRuoliResponse!=null && urbamidPermessiResponse!=null) {
				
				appGestRuoliPermessi.ruoliPermessiList 		= urbamidRuoliResponse.aaData;
				appGestRuoliPermessi.permessiList 			= urbamidPermessiResponse.aaData;
				appGestRuoliPermessi.hmRuoliPermessi 			= [];
				
				$.each( appGestRuoliPermessi.ruoliPermessiList, function( keyRP, valueRP ) {
					$.each( valueRP.listaPermessi, function( keyLP, valueLP ) {
						appGestRuoliPermessi.hmRuoliPermessi[valueLP.codice+"|"+valueRP.codice] = true;
				    });
					appGestRuoliPermessi.hmRuoli[valueRP.codice] = valueRP;
				});
			}
		},

		selectRuolo : function ( codice ){
			appGestRuoliPermessi.ruoloDetMod = appGestRuoliPermessi.hmRuoli[codice];
		},
		
		showRuoloSelectRow : function ( id, codice, el ){

			var found = false;
			if(el.checked) {
				(appGestRuoliPermessi.ruoliSelectedList).push( {'id': id, 'codice': codice} );
			} else {
				for (i=0;i<(appGestRuoliPermessi.ruoliSelectedList).length;i++){
					if ((appGestRuoliPermessi.ruoliSelectedList)[i].id == id) {
						(appGestRuoliPermessi.ruoliSelectedList).splice(i, 1);
						found = true;
						break;
					}
				}
			}

			if((appGestRuoliPermessi.ruoliSelectedList).length < 1) {
				$(".btnDettaglioRuoloPermesso").get(0).setAttribute("disabled","disabled");
				$(".btnEliminaRuoloPermesso").get(0).setAttribute("disabled","disabled");
			} else if ( (appGestRuoliPermessi.ruoliSelectedList).length > 1 ) {
				$(".btnDettaglioRuoloPermesso").get(0).setAttribute("disabled","disabled");
				$(".btnEliminaRuoloPermesso").get(0).setAttribute("disabled", "disabled");
				for(i in appGestRuoliPermessi.ruoliSelectedList){// cosi se perdono il focus l'utente li puo visualizzare
					$("#checkbox-record-"+appGestRuoliPermessi.ruoliSelectedList[i].codice).each(function(){ this.checked=true; })}
			} else {
				appGestRuoliPermessi.ruoloDetMod = appGestRuoliPermessi.hmRuoli[ appGestRuoliPermessi.ruoliSelectedList[0].codice ];
			
				if (appGestRuoliPermessi.ruoloDetMod.ruoloDefault)
					$(".btnEliminaRuoloPermesso").get(0).setAttribute("disabled","disabled");
				else 
					$(".btnEliminaRuoloPermesso").get(0).removeAttribute("disabled");
				
				$(".btnDettaglioRuoloPermesso").get(0).removeAttribute("disabled");
			}
			
			if ( !found ) {
				appGestRuoliPermessi.codRuoliSelectedHm[id] = codice;
			} else {
				delete appGestRuoliPermessi.codRuoliSelectedHm[id];
			}

		},
		
		
		showPermessoSelectRow : function ( id, idRow ){
			if (!appGestRuoliPermessi.modifyPermessi) appGestRuoliPermessi.modifyPermessi = true;
			appGestRuoliPermessi.hmRuoliPermessi[id] 	= !appGestRuoliPermessi.hmRuoliPermessi[id];
			if (appGestRuoliPermessi.hmRuoliPermessiMod[id]!=null){
			    delete appGestRuoliPermessi.hmRuoliPermessiMod[id];
			    appGestRuoliPermessi.hmRuoliPermessiMod.length = appGestRuoliPermessi.hmRuoliPermessiMod.length-1;
			    $("#"+idRow).removeClass("blink");
			} else {
			    appGestRuoliPermessi.hmRuoliPermessiMod[id]     = id;
			    appGestRuoliPermessi.hmRuoliPermessiMod.length = appGestRuoliPermessi.hmRuoliPermessiMod.length+1;
			    $("#"+idRow).addClass("blink");
			}
			
			if(appGestRuoliPermessi.hmRuoliPermessiMod.length<1 || $("#input-codice").val()=="" )
			{
				$(".btnSalvaRuoloPermesso").get(0).setAttribute("disabled","disabled");
			}
			else
			{
				$(".btnSalvaRuoloPermesso").get(0).removeAttribute("disabled");
			}
		},
		
		changeNumResult : function () {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			appGestRuoliPermessi.tabellaPageLength = $(".risultati-ruoli-pagina").val();
			appGestRuoliPermessi.inizializzaRuoliPermessi();
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		},
		
		changeDettRuolo : function ( ){
			ruoloModificato = false;
               
            if (appGestRuoliPermessi.ruoloDetMod!=null && appGestRuoliPermessi.ruoloDetMod.codice!=null && 
					appGestRuoliPermessi.ruoloDetMod.codice!=$("#input-codice").val())
				ruoloModificato = true;
			else if( $("#input-codice").val()!="" )
                ruoloModificato = true;
                
			if (appGestRuoliPermessi.ruoloDetMod!=null && appGestRuoliPermessi.ruoloDetMod.denominazione!=null && 
					appGestRuoliPermessi.ruoloDetMod.denominazione!=$("#input-denominazione").val())
				ruoloModificato = true;
				
			if (appGestRuoliPermessi.ruoloDetMod!=null && appGestRuoliPermessi.ruoloDetMod.descrizione!=null && 
					appGestRuoliPermessi.ruoloDetMod.descrizione!=$("#input-descrizione").val())
				ruoloModificato = true;
				
			if (ruoloModificato) {
			    $(".btnSalvaRuoloPermesso").get(0).removeAttribute("disabled");
			    appGestRuoliPermessi.ruoloDetMod.modificato = ruoloModificato;
			} else { 
			    $(".btnSalvaRuoloPermesso").get(0).setAttribute("disabled","disabled");
			}
		}
		
};

function openSezione(evt, sezione) {
	
	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	if (appGestUtente.modifica) {
		appUtil.confirmOperation( function(){
			changeSezione(evt, sezione);
		}, function(){
			//ANNULLA
		}, {}, 'Vuoi salvare le modifiche?');
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	} else {
		changeSezione(evt, sezione);
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	}
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
		"sSearch":         "Cerca:",
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
	
	appGestUtente.mappaToSave = {};
	appGestUtente.modifica = false;
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

	// Show the current tab, and add an "active" class to the link that opened the tab
	document.getElementById(sezione).style.display = "block";
	document.getElementById(sezione+"-link").className += " active";
	  
	appGestUtente.blocked = true;
	/**GESTIONE BUTTON SALVA**/
	var btnItems = $("button.salva");
	var n = btnItems.length;
	if (n > 0) {
		btnItems.each(function(idx,item){
			var choice = $(item);
			choice.attr("disabled", appGestUtente.blocked); 
		});
	}
}