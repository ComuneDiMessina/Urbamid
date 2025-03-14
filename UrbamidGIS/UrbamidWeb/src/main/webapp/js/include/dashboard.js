
var CODICI_BOTTONI_DIALOG = {
							  "RICERCA_TABELLA" : "RT",
							  "SALVA" : "S",
							  "ANNULLA" : "A",
							  "CREA" : "C",
							  "FORM_RICERCA" : "FR",
							  "MODIFICA" : "M",
							  "ELIMINA" : "E"
							};


$(window).resize(function(){
	if($(".itemSubmenu").is(":visible"))
		$(".itemSubmenu").css("height",(($( window ).height() - $(".itemSubmenu").offset().top - $("#searchBox").outerHeight()) - 5) + "px");	
});

$(document).ready(function(){
	
	/**
	 * Gestione click sul button X del dashboard.
	 */
	$("#closeDashboard").click(function(e){
		e.preventDefault();
		$("#menuContainer").addClass("hidden");
	});
	
	/**
	 * Gestione click sui cubotti della dashboard e visualizzazione del sotto menu.
	 */
	$(".gotoSubmenu").click(function(e){
		e.preventDefault();
		
		/** recupero l'id del sottomenu da visualizzare **/
		var subMenuId = $(this).attr("data-menu");
		
		/** controllo se è stato cliccato un cubotto che ha il sottomenu **/
		if(subMenuId != undefined && subMenuId != ""){
			
			if($(this).is(".active")){
				/** è stato cliccato lo stesso cubotto, quindi il sotto menu è gia visualizzato **/
				$(this).toggleClass("active");
				$("#menuContainer").toggleClass("open");
			}
			else{
				/** è stato cliccato un nuovo cubotto, quindi devo visualizzare il sotto menu **/
				$(".gotoSubmenu.active").removeClass("active");
				$(this).addClass("active");
				$("#menuContainer").addClass("open");
			}
			
			$("#" + subMenuId + "").css("height",(($( window ).height() - $("#" + subMenuId + "").offset().top - $("#searchBox").outerHeight()) - 5 ) + "px");
			// ad focus input di ricerca
			$("#q_view").focus();
			
			var dest = $(this).data("menu");
				$(dest).removeClass("hidden").siblings("ul").addClass("hidden");
			
			if($(this).closest(".menuBox"))
				$("#arrowItem").css("left", ($(this).parent().offset().left + ($(this).parent().width() / 2))-20 + "px");
		}else{
			/** Il cubotto selezionato non ha il sottomenu quindo chiudo quello eventualmente gia apero **/
			$(".gotoSubmenu.active").removeClass("active");
			$("#menuContainer").removeClass("open");
		}
	});
	
	/**
	 * Gestione click del bottone che permette l'apertura e la chiusura della menu.
	 */
	$("#dashboard").click(function(e){
		e.preventDefault();
		$("#menuContainer").toggleClass("hidden");
		$("#q_view").focus();
		$("#submenuHeight").css("height",(($( window ).height() - $("#submenuHeight").offset().top - $("#searchBox").outerHeight()) - 5) + "px");
	});
 
	/**
	 * Gestione click sul button di ricerca funzionalita della dashboard e dei sottomenu.
	 */
	$("#btnCercaItemDashboard").click(function(){
		$("#menuContainer .noStyle li").css("opacity","1");
		$("#menuContainer .noStyle li:not(:contains(" + $("#q_view").val() + "))").css("opacity","0.5");
	});
	
	/**
	 * Gestione click sull'cona di clear dell'input di ricerca funzionalità.
	 */
	$("#q_view").on('search', function () {
	    if($(this).val() == "") $("#menuContainer .noStyle li").css("opacity","1");
	});
	
	/**
	 * Apertura finestra dialogo delle funzionalità.
	 */
	$(".gotoDialog").click(function(e){
		  // loading ..
		  appUtil.showLoader();
		  
		  e.preventDefault();
		  $("#menuContainer").addClass("hidden");
		  var myURL = $(e.currentTarget).attr("href");
		  var myID = $(e.currentTarget).data("id");
		  var myTITLE = $(e.currentTarget).data("title") != undefined ? $(e.currentTarget).data("title") : "";
		  /**
		   * Recupero il tag data-buttons, che individua i button da aggiungere alla finestra.
		   */
		  var myFORM_BUTTONS = $(e.currentTarget).data("buttons") != undefined ? $(e.currentTarget).data("buttons").split(",") : [];
		  
		  if(myURL != ""){
			  
			  var buttonRicercaTabellare = 
			  	   {
			         text: jQuery.i18n.prop('Button.ricerca'),
			         "class": 'step2 ricercaButtonClass',
			         "id": myID + "_ricercaDialog",
			         "onClick": "javascript:" + myID + "_ricercaDialog();",
			         click: function() { return false; }
				   };
			  var buttonAnnulla = 
			  	   {
				     text: jQuery.i18n.prop('Button.annulla'),
				     "class": 'step1 annullaButtonClass',
				     "id": myID + "_annullaDialog",
				     "onClick": "javascript:" + myID + "_annullaDialog();",
			         click: function() { return false; }
				   };
			  var buttonSalva = 
			  	   {
				     text: jQuery.i18n.prop('Button.salva'),
				     "class": 'step1 saveButtonClass',
				     "id": myID + "_salvaDialog",
				     "onClick": "javascript:" + myID + "_salvaDialog();",
			         click: function() { return false; }
				   };
			  var buttonModifica = 
			  	   {
				     text: jQuery.i18n.prop('Button.modifica'),
				     "class": 'step1 modificaButtonClass',
				     "id": myID + "_modificaDialog",
				     "onClick": "javascript:" + myID + "_modificaDialog();",
			         click: function() { return false; }
				   };
			  var buttonElimina = 
			  	   {
				     text: jQuery.i18n.prop('Button.elimina'),
				     "class": 'saveButtonClass',
				     "id": myID + "_eliminaDialog",
				     "onClick": "javascript:" + myID + "_eliminaDialog();",
			         click: function() { return false; }
				   };
			  
			  var activeButtons = [];
			  
			  if($.inArray(CODICI_BOTTONI_DIALOG["RICERCA_TABELLA"], myFORM_BUTTONS) != -1) activeButtons.push(buttonRicercaTabellare);
			  if($.inArray(CODICI_BOTTONI_DIALOG["ANNULLA"], myFORM_BUTTONS) != -1) activeButtons.push(buttonAnnulla);
			  if($.inArray(CODICI_BOTTONI_DIALOG["SALVA"], myFORM_BUTTONS) != -1) activeButtons.push(buttonSalva);
			  if($.inArray(CODICI_BOTTONI_DIALOG["MODIFICA"], myFORM_BUTTONS) != -1) activeButtons.push(buttonModifica);
			  if($.inArray(CODICI_BOTTONI_DIALOG["ELIMINA"], myFORM_BUTTONS) != -1) activeButtons.push(buttonElimina);

			 openDetail(myID, myTITLE, myURL, myFORM_BUTTONS, activeButtons);
		  }
		  else
			 console.log("Url del controller mancante: " + myURL);
	});
});


/**
 * 
 */
function openDetail(id, title, url, myFORM_BUTTONS, activeButtons) {
  $.ajax({
	  url: url,
	  context: document.body,
  }).done(function(response) {
	  $("body").append("<div id=\"" + id + "_DIALOG\">" + response + "</div>");
	  
	  $("#" + id + "_DIALOG").dialog({
			width: "900px",
			height: "300",
			title: title,
			dialogClass: "ui-dialog-dblclick-full-screen",
			close: function(event, ui) { $(this).dialog('destroy').remove(); },
			open: function() {
				$(this).parent().addClass("step1");
				
				if($.inArray(CODICI_BOTTONI_DIALOG["CREA"], myFORM_BUTTONS) != -1)
					$("<button type=\"button\" class=\"ui-dialog-titlebar-new\" id='" + id + "_formNuovoDialog' onClick='javascript:" + id + "_formNuovoDialog();' title='" + jQuery.i18n.prop('Button.crea') + "' >" + 
							jQuery.i18n.prop('Button.crea') + 
					  "</button>").insertBefore($("#" + id + "_DIALOG").parent().find('.ui-dialog-title'))
						.click(function(e) {
							e.preventDefault();
							gotoNextStep("#" + id);
						});

				if($.inArray(CODICI_BOTTONI_DIALOG["FORM_RICERCA"], myFORM_BUTTONS) != -1)
					$("<button type=\"button\" data-toggle=\".search\" class=\"ui-dialog-titlebar-search\" id='" + id + "_formRicercaDialog' onClick='javascript:" + id + "_formRicercaDialog();' title='" + jQuery.i18n.prop('Button.filtriRicerca') + "' >" + 
							jQuery.i18n.prop('Button.cerca') + 
					"</button>").insertBefore($("#" + id + "_DIALOG").parent().find('.ui-dialog-title'))
						.click(function(e) {
							e.preventDefault();
							gotoStep("#" + id, "1");
							toggleSearch("#" + id, e);
						});

				$("<button type=\"button\" class=\"ui-dialog-titlebar-info\" title='" + jQuery.i18n.prop('Button.riduciAIcona') + "' ></button>")
					.insertBefore($("#" + id + "_DIALOG").parent().find('.ui-dialog-titlebar-close'))
					.click(function(e) {
						e.preventDefault();
						iconize("#" + id + "_DIALOG");
					});

				$("<button type=\"button\" class=\"ui-dialog-titlebar-max\" title='" + jQuery.i18n.prop('Button.fullScreen') + "' ></button>")
					.insertBefore($("#" + id + "_DIALOG").parent().find('.ui-dialog-titlebar-info'))
					.click(function(e) {
						e.preventDefault();
						maximize("#" + id + "_DIALOG");
					});
			}// close open
		}).removeClass("hidden").parent().position({ my : "center", at : "center", of : window });

	  	// spengo il loading
		appUtil.hideLoader();
		
		$("#" + id + "_DIALOG").dialog('option', 'buttons', activeButtons);
	});// close done
};

