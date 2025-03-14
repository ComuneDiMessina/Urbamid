/**
 * 
 */

var applicationTema = {
	modifica:false,	
	temaList : [],
	temaListSelected : {},
	tabellaTema : null,
	idTema : null,
	temaSelezionatoModifica:{},
	temaListDeleted:[],
	checkSelectRowTema : function(id) {
		var found = false;
		// MS Gestione bottoni header [NUOVO|DETTAGLIO|ELIMINA]
		if ($("input[type='checkbox']:checked", $('#tabellaTema')).length < 1) {
			$(".dettaglio-tema").get(0).setAttribute("disabled", "disabled")
			$(".elimina-tema").get(0).setAttribute("disabled", "disabled")
		
			applicationTema.temaListSelected = {};
		
		} else if ($("input[type='checkbox']:checked", $('#tabellaTema')).length > 1) {
			$(".dettaglio-tema").get(0).setAttribute("disabled", "disabled")
			$(".elimina-tema").get(0).removeAttribute("disabled")
				applicationTema.temaListSelected = {};
			
			$("input[type='checkbox']:checked", $('#tabellaTema')).each(function(){
				var id__=this.id.split('checkbox-tema-')[1];
				for (var i = 0; i < (applicationTema.temaList).length; i++) {
					if ((applicationTema.temaList)[i].id == id__) {
						applicationTema.temaListSelected[id__] = applicationTema.temaList[i]

					}
				}	
				
			})
		
		} else {
			$(".dettaglio-tema").get(0).removeAttribute("disabled")
			$(".elimina-tema").get(0).removeAttribute("disabled")
			applicationTema.temaListSelected = {};

			var id__= $("input[type='checkbox']:checked", $('#tabellaTema')).get(0).id.split('checkbox-tema-')[1];
 
				for (var i = 0; i < (applicationTema.temaList).length; i++) {
					if ((applicationTema.temaList)[i].id == id__) {
						applicationTema.temaListSelected[id__] = applicationTema.temaList[i]

					}
				}	
	 
		}

		idTema = id;

	},


	intDatatableTema : function() {

		if ($.fn.DataTable.isDataTable('#tabellaTema'))
			(applicationTema.tabellaTema).destroy();

		hrefUrbamidTema = appUtil.getUrbamidOrigin()+"/UrbamidWeb/temaController/getTemi";
		
		temaResponse = appRest.restUrbamid(hrefUrbamidTema, "POST", null);
		if (temaResponse != null) {

			applicationTema.temaList = temaResponse.aaData;


			applicationTema.tabellaTema = $('#tabellaTema').DataTable(
							{
								// pageLength : appGestMappe.tabellaPageLength,
								data : temaResponse.aaData,
								language : {
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
									},
								order : [ 0, "asc" ],
								"searching" : true,
								"aLengthMenu" : [ [ 5, 10, 25, 50, -1 ],
									['Seleziona valore', 10, 25, 50, "Tutti"] ],
								columns : [
										{
											mData : "select",
											className : "nowrap",
											bSortable : false,
											checkboxes : {
												selectRow : true
											},
											render : function(data, type, row,
													meta) {
												return '<div class="custom-control custom-checkbox">'
														+ '<input id="checkbox-tema-'
														+ row.id
														+ '" type="checkbox" class="custom-control-input" onclick="applicationTema.checkSelectRowTema('
														+ row.id
														+ ');">'
														+ '<label class="custom-control-label" for="checkbox-tema-'
														+ row.id
														+ '"><span class="sr-only">Seleziona</span></label>'
														+ '</div>';
											},
											title : ""
										},
									
										{
											mData : "nome",
											title : "Nome"
										},
										{
											mData : "descrizione",
											title : "Descrizione"
										},
										{
											mData : "ordinamento",
											title : "Posizione"
										},
										{
											mData : "dataCreazione",
											title : "Data creazione",
											render : function(data, type, row, meta) {
												return data?moment(data).format('DD/MM/YYYY'):"";
											}
										},

								]
							});

		}

 
	},
	
	 changeSezione:function(evt, sezione) {
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		var i, tabcontent, tablinks;

		// Get all elements with class="tabcontent" and hide them
		tabcontent = document.getElementsByClassName("tabTemi");
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
		  if(sezione=="elenco-tema"){
	    $("button",$('.menu-gestione-temi')).removeAttr("disabled")
		$('.menu-gestione-temi').show();
			$(".dettaglio-tema").get(0).setAttribute("disabled", "disabled")
			$(".elimina-tema").get(0).setAttribute("disabled", "disabled")
		
			applicationTema.temaListSelected = {};
			$("input[type='checkbox']:checked", $('#tabellaTema')).each(function(){
				this.checked=false;
			})
	   
	   }
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	},
	
	openSezione:function(evt, sezione) {

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		if (applicationTema.modifica) {
			appUtil.confirmOperation( function(){
				applicationTema.changeSezione(evt, sezione);
			}, function(){
				applicationTema.modifica = false;
				//ANNULLA
			}, {}, 'Vuoi salvare le modifiche?');
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		} else {
			applicationTema.changeSezione(evt, sezione);
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		}
	}
	
	
};


$(document).ready(function() {

	applicationTema.intDatatableTema();

	// Gestione Temi
$(".dettaglio-tema").bind("click", function() {
	    
	applicationTema.changeSezione(event, 'dettaglio-tema');
	 
		for (var i in applicationTema.temaListSelected) {
			var tema = applicationTema.temaListSelected[i];
			applicationTema.temaSelezionatoModifica=tema;
			$("#mod-nome").val(tema.nome);
			$("#mod-descrizione").val(tema.descrizione);
			$("#mod-position").val(tema.ordinamento);
			 
			break;
		}
		$('.menu-gestione-temi').hide();
	})
	
$(".elimina-tema").bind("click", function() {
	applicationTema.temaListDeleted=[];
	
	for (var i in applicationTema.temaListSelected) {
		var tema = applicationTema.temaListSelected[i];
		applicationTema.temaListDeleted.push(i);
		}
	
	var hrefMappeToTema = appUtil.getUrbamidOrigin() +"/UrbamidWeb/temaController/mappeToTema";
	
	jQAjaxSupport.get('mappetotema')
	.setUrl(hrefMappeToTema)
	.setData(JSON.stringify(applicationTema.temaListDeleted))
	.onSuccess(function(data){
		if(data.success && data.iTotalRecords){
			appUtil.showMessageAlertApplication("Non e' possibile eliminare il tema corrente, risultano mappe associate al tema!", "Info", true, false, false);	
		 
		}
		else if(data.success && !data.iTotalRecords){
		
			callDelete();
		}
		
	}).start();
	
var callDelete=function(){
    if(applicationTema.temaListDeleted.length)
	appUtil.confirmOperation( function(){
		//CONFERMA
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
 
			var hrefDeleteTema = appUtil.getUrbamidOrigin() +"/UrbamidWeb/temaController/delete";

			jQAjaxSupport.get('general')
			.setUrl(hrefDeleteTema)
			.setData(JSON.stringify(applicationTema.temaListDeleted))
			.start()
			
			$("button",$('.menu-gestione-temi')).removeAttr("disabled")
			$('.menu-gestione-temi').show();
			$(".dettaglio-tema").get(0).setAttribute("disabled", "disabled")
			$(".elimina-tema").get(0).setAttribute("disabled", "disabled")
		
			applicationTema.temaListSelected = {};
			$("input[type='checkbox']:checked", $('#tabellaTema')).each(function(){
				this.checked=false;
			})
			
			
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	}, function(){
		//ANNULLA
	}, {}, 'Sei sicuro di voler eliminare i Temi selezionati?');	
	
}
	})

$(".btnew-tema").bind("click", function() {
		
		$("#new-nome").val("");
		$("#new-descrizione").val("");
		$("#new-position").val("");
 
	$(".dettaglio-tema").get(0).setAttribute("disabled", "disabled")
	$(".elimina-tema").get(0).setAttribute("disabled", "disabled")
	$('.menu-gestione-temi').hide();
	$("input[type='checkbox']:checked", $('#tabellaTema')).each(function(){
		this.checked=false;
	})
	
	applicationTema.temaListSelected = {};
			
		
	})

	
	
	
	/**** SALVA TEMA	*****************/
	$("button.salva-tema").bind("click", function() {
		appUtil.confirmOperation( function(){
			//CONFERMA
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			//SALVO DATI DI BASE
				applicationTema.TemaToSave={};
				applicationTema.TemaToSave.isNew=true;
				applicationTema.TemaToSave.nome = $("#new-nome").val();
				applicationTema.TemaToSave.descrizione = $("#new-descrizione").val();
				applicationTema.TemaToSave.ordinamento = $("#new-position").val() || 1;
			 
	  
				var hrefSalvaTema = appUtil.getUrbamidOrigin() +"/UrbamidWeb/temaController/saveOrUpdate";

				jQAjaxSupport.get('general')
				.setUrl(hrefSalvaTema)
				.setData(JSON.stringify(applicationTema.TemaToSave))
				.start()
				
			 
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		}, function(){
			//ANNULLA
		}, {}, 'Sei sicuro di voler salvare il nuovo Tema?');
	});
	
/**** MODIFICA TEMA	*****************/
$("button.midifica-tema").bind("click", function() {
	appUtil.confirmOperation( function(){
		//CONFERMA
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			
			applicationTema.temaSelezionatoModifica.isNew=false;
			applicationTema.temaSelezionatoModifica.nome = $("#mod-nome").val();
			applicationTema.temaSelezionatoModifica.descrizione = $("#mod-descrizione").val();
			applicationTema.temaSelezionatoModifica.ordinamento = $("#mod-position").val();
			
			var hrefModificaTema = appUtil.getUrbamidOrigin() +"/UrbamidWeb/temaController/saveOrUpdate";

			jQAjaxSupport.get('general')
						 .setUrl(hrefModificaTema)
						 .setData(JSON.stringify(applicationTema.temaSelezionatoModifica))
						 .start();
		 
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	}, function(){
		//ANNULLA
	}, {}, 'Sei sicuro di voler modificare Tema corrente?');
});

 //MS DICHIARO UN OGGETTO GLOBALE PER LE CHIAMATE AJAX NON SETTO NE LA URL E NE IL DATA
// LA URL E IL DATA VERRA SETTATO NELLE VARIE OPERAZIONI DI (INSERIMENTO/MODIFICA/ELIMINAZIONE)
   jQAjaxSupport.get('general')
       .set("type", 'POST')
       .set("dataType", 'json')
       .set("contentType", 'application/json; charset=utf-8')
       .set("cache", false)
       .onSuccess(function (data) {
    	   if(data.success && data.aaData){
				 appUtil.showMessageAlertApplication("Operazione eseguita con successo!", "Success", false, false, true);
			applicationTema.intDatatableTema();
				return data;
			}else{
				appUtil.showMessageAlertApplication("Operazione non eseguita correttamente,riprova!", "Info", true, false, false);
				return null;
			}
       })
       .onError(function (jqXHR, textStatus, errorThrown) {appUtil.showMessageAlertApplication("General Error!!", "Error", false, true, false); console.log("error", textStatus,errorThrown); })
       .onComplete(function (jqXHR, textStatus, errorThrown) { console.log("complete", textStatus); })

       
  //MS DICHIARO UN OGGETTO GLOBALE PER LE CHIAMATE AJAX NON SETTO NE LA URL E NE IL DATA
// LA URL E IL DATA VERRA SETTATO NELLE VARIE OPERAZIONI DI (INSERIMENTO/MODIFICA/ELIMINAZIONE)
   jQAjaxSupport.get('mappetotema')
       .set("type", 'POST')
       .set("dataType", 'json')
       .set("contentType", 'application/json; charset=utf-8')
       .set("cache", false)
       .onError(function (jqXHR, textStatus, errorThrown) {appUtil.showMessageAlertApplication("General Error!!", "Error", false, true, false); console.log("error", textStatus,errorThrown); })
       .onComplete(function (jqXHR, textStatus, errorThrown) { console.log("complete", textStatus); })      
       
});
