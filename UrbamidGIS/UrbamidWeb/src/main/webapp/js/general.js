var dataSet;
$.ajax({
	url:"resources/contesto.json"
}).done(function( data ) {
	dataSet=data;
	aggiornaDatiInterfaccia();
});

$.validate({
	lang : 'it'
});

$.formUtils.addValidator({
	name : 'max_then',
	validatorFunction : function(value, $el, config, language, $form) {
		var min = $el.attr("data-max-then");
		if($.isNumeric(value))
			if(value != '' && value > ($("#"+min).val()*1))
				return true;
			else
				return false;
		else
			return false
	},
	errorMessage : 'Range non corretto, deve essere maggiore del minimo',
	errorMessageKey: 'badEvenNumber'
});

$.formUtils.addValidator({
	name : 'select_required',
	validatorFunction : function(value, $el, config, language, $form) {
		if(value == '0')
			return false
		else
			return true;
	},
	errorMessage : 'Campo obbligatorio',
	errorMessageKey: 'badEvenNumber'
});

function simulaUpload(e){
	$(e.currentTarget).closest(".globalContainer").find(".progress").removeClass("hidden").delay(2000).queue(function(){
	    $(this).addClass("hidden").dequeue();
	});
	
}

function getDettaglio(){
	$("#myModalData").modal({
        show: 'true'
    });
}
function openDesc(tipo){
	if(tipo=='salva'){
		$("#myModalDesc .salvac").removeClass("hidden").siblings(".avviac").addClass("hidden");
	}
	else if(tipo=='avvia'){
		$("#myModalDesc .avviac").removeClass("hidden").siblings(".salvac").addClass("hidden");	
	}
	else{
		$("#myModalDesc .salvac,#myModalDesc .avviac").addClass("hidden");
	}
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; 
	var yyyy = today.getFullYear();
	var hh = today.getHours();
	var min = today.getMinutes();
	var ss = today.getSeconds();
	
	if(dd<10) {
	    dd='0'+dd
	} 

	if(mm<10) {
	    mm='0'+mm
	} 

	today = mm+''+dd+''+yyyy+""+hh+""+min;
	
	$("#idRifContestoTmp").html("S_"+today+"_tmp");
	$("#myModalDesc").modal({show: 'true'});
}

function aggiornaDatiInterfaccia(ctx){
	if(ctx!=null){
		$(".idCtx").html(ctx.idContesto);
	}
	else{
		$("#rifCont,.rifCont,.idCtx").html(dataSet.idContesto);
		$("#rifContDistretto,.rifContDistretto").html(dataSet.ds);
		$("#rifContCentro,.rifContCentro").html(dataSet.c);
		$("#rifContSeg,.rifContSeg").html(dataSet.segmentazione);
		$("#rifContData,.rifContData").html(dataSet.timestamp);
		$("#rifContDesc,.rifContDesc").html(dataSet.desc);
		$("#rifTipDistretto,.rifTipDistretto").html(dataSet.tipo);
	}
}

function attivaControlliForm(id,callback){
	$.validate({
		form : '#'+id,
		validateOnBlur : false,
		showHelpOnFocus : false,
		addSuggestions : false,
		onError : function($form) {
			alert('Form non valido');
		},
		onSuccess : function($form) {
			if(callback!=null)
				callback($form.attr('id'));
			salva();
			resetForm(null,$form.attr('id'));
			return false; // Will stop the submission of the form
		}
	});
}
$(document).ready(function(){
	
	/**
	 * AGGIUNTA DA CIRO 
	 **/
	$(document).on("click","#menu-overlay-content a.first-level,#menu-overlay-content a.second-level,#menu-overlay-content a.third-level",function(e){
		e.preventDefault();
		$(this).toggleClass("open");
		$(this).next().toggleClass("hidden");
	});
	appUtil.reloadTooltips();
	initCalendar();
	$("[data-dialog]").on('click touch', function(e) {
		e.preventDefault();
		var $href = null;
		if($(this).prop('nodeName') == "A" && $(this).attr("href") != ""){
			$href = ($(this).attr("href").indexOf("op=")!=-1)?($(this).attr("href").split("op=")[1]):("void");

		}else if($(this).prop('nodeName') == "BUTTON" && $(this).attr("data-href") != ""){
			$href = ($(this).attr("data-href").indexOf("op=")!=-1)?($(this).attr("data-href").split("op=")[1]):("void");	
		}
		
		if($href != null && $href != "void"){
			window.history.pushState($href, $(this).text(), "?op="+$href);
			if($(this).attr("data-clone") == "true")
				openStaticDetail($href,"",e,null,null,true);
			else
				openStaticDetail($href,"",e,null,null,false);
		}
	});
	
	if(QueryString.op != undefined && QueryString.op != ""){
		var el = $('#myNavbar a[href*=\"'+QueryString.op+'\"]');
		var myURL = $(el).attr("href").split("?op=")[1];
		var myID = $(el).data("id");
		var myNOME = $(el).data("nome");
		if(myURL != ""){
			openStaticDetail(myURL, "",null);
		}
		else
			alert("Da implementare");
	}
});

function compilaFormObj(id,name,el,vis,callback){
	$("a[href='#"+id+"']").tab('show');
	$('.finestra.mainMenu li').removeClass("active");
	$("#"+id+" .bread").html("Modifica");
	$.each(el,function(index, value){
		var t = $("#"+id+" [name='"+index+name+"']");
		t.val(value);
		if(vis)
			t.attr("disabled","disabled");
		else
			t.removeAttr("disabled");
	});
	if (typeof callBack === "function") {
		callBack();					    	
    }
}

function compilaForm(id,el,vis,callback){
	$("a[href='#"+id+"']").tab('show');
	$('.finestra.mainMenu li').removeClass("active");
	$("#"+id+" .bread").html("Modifica");
	$("#"+id+" input[type='text'], #"+id+" select").each(function(index){
		$(this).val(el[index]);	
		if(vis)
			$(this).attr("disabled","disabled");
		else{
			$(this).removeAttr("disabled");
			if($(this).attr("data-dis") == "true")
				$(this).prop("disabled",true);
		}
	});
	if (typeof callBack === "function") {
		callBack();					    	
    }
}

function back(id,f,callback){
	$("a[href='#"+id+"']").tab('show');
	$('.finestra.mainMenu li').removeClass("active");
	$("a[href='#"+id+"']").parent().addClass("active");
	if(f!=null){
		$("#"+f+" input[type='text']").each(function(index){
			$(this).val('');
			$(this).removeAttr("disabled");
		});
		$("#"+f+" select").each(function(index){
			$(this).val(0);
			$(this).removeAttr("disabled");
		});
	}
	
	if(callback!=null){
		callback();					    	
    }
}

function resetRicerca(e){
	if(e!=null)
		e.preventDefault();
	
	$(e.currentTarget).closest(".tab-pane.active").find(".search input, .searchnoover input").each(function(index){
		$(this).val('');
	});
	$(e.currentTarget).closest(".tab-pane.active").find(".search select, .searchnoover select").each(function(index){
		$(this).val(0);
	});
}


function resetForm(e,id){
	if(e!=null){
		e.preventDefault();
		$(e.currentTarget).closest(".tab-pane.active").find(".globalContainer input").each(function(index){
			if($(this).attr("data-dis") == "true")
				$(this).prop("disabled",false);
			
			if(!$(this).attr("disabled") && !$(this).attr("type","submit") && !$(this).attr("type","reset"))
				$(this).val('');
		});
		$(e.currentTarget).closest(".tab-pane.active").find(".globalContainer select").each(function(index){
			if($(this).attr("data-dis") == "true")
				$(this).prop("disabled",false);
			
			if(!$(this).attr("disabled"))
				$(this).val(0);
		});
	}
	
	if(id != null){
		$("#"+id)[0].reset();
		$("[data-dis='true']").each(function(){
			$(this).prop("disabled",false);
		});
	}
}
function getDate(datum){
	fn = datum.split("/");
	if(datum!='')
		return fn[2]+'/'+fn[1]+'/'+fn[1];
	else
		return;
}
function initCalendar(){
	jQuery.datetimepicker.setLocale('it');

	$(".Datepicker").datetimepicker({
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
	 format:'d/m/Y H:i:s',
	 mask:''
	 });
	
	$(".Datep").datetimepicker({
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
	  timepicker:false,
	  format:'d/m/Y',
	  mask:''
	});
	
	$(".Timep").datetimepicker({
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
	  datepicker:false,
	  format:'H:i:s',
	  mask:''
	});
	$(".Year").datetimepicker({
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
			  timepicker:false,
	  format:'Y',
	  mask:''
	});
}
var suffixLocalStorage = "srg";


function recuperaParametriDialog(id,e){
	if(e!=null){
		return $(e.currentTarget).data();
	}
	else if($("[href*=\""+id+"\"]").length >0){// altrimenti verifico se vi è un qualsiasi elemento che abbia un id che contenga questa chiave
		return $("[href*=\""+id+"\"]").data();
	}
	else
		return new Object();
};

/* Function che restituisce un'oggetto dei parametri nell'url e lo assegna alla variabile QueryString */
var QueryString = function () {
  var query_string = {};
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i=0;i<vars.length;i++) {
    var pair = vars[i].split("=");
    if (typeof query_string[pair[0]] === "undefined") {
      query_string[pair[0]] = pair[1];
    } else if (typeof query_string[pair[0]] === "string") {
      var arr = [ query_string[pair[0]], pair[1] ];
      query_string[pair[0]] = arr;
    } else {
      query_string[pair[0]].push(pair[1]);
    }
  } 
  return query_string;
}();

function StringPath(path) {
	  var query_string = {};
	  var query = path;
	  var vars = query.split("&");
	  for (var i=0;i<vars.length;i++) {
	    var pair = vars[i].split("=");
	    if (typeof query_string[pair[0]] === "undefined") {
	    	if(pair[1]!=undefined)
	    		query_string[pair[0]] = pair[1];
	    	else
	    		query_string["urlToGo"] = pair[0];
	    } else if (typeof query_string[pair[0]] === "string") {
	      var arr = [ query_string[pair[0]], pair[1] ];
	      query_string[pair[0]] = arr;
	    } else {
	      query_string[pair[0]].push(pair[1]);
	    }
	  } 
	  return query_string;
};

/* 
/**
 * Mi permette di aprire in maniera predefinita un pannello (v1.0)*/
/* id -> identifica quale box devo aprire 
 * param -> se siamo alla prima apertura della pagina o in caso di refresh viene forzata l'apertura della dialog che risultano active in localStorage,
 * 			negli altri casi, invece, la dialog non deve essere aperta se già attiva.
 */
function openStaticDetail(id, param, e, callBack, callBackClose,clone){
	
	console.log ("*** openStaticDetail id: " +  id);
	
	if(clone)
		id=id+"_clone";
	
	if(!$("#"+id).length){
		
		// se true apre la dialog, altrimenti non viene aperta
		var flag = false;
		var tmpW = new Object();
		var page = StringPath(id);
		var globalE;
		id = page.urlToGo;
		if(e != null){
			e.preventDefault();
			globalE = e;
		}
		
		/**
		 * Esegue i controlli per la decidere la modalità di apertura delle dialog (es. con o senza il supporto di localStorage,
		 * con recupero dello stato dal localStora, qualora presente).
		 * 
		 */
		
			var parametri;
			if(e != null)
				parametri = recuperaParametriDialog(id,e);
			else
				parametri = recuperaParametriDialog(id);
			
			console.log("--------------------------------------");
			console.log(parametri);
			console.log("--------------------------------------");
			
			
			if(QueryString.t != undefined)
				page.t = QueryString.t;
			
			//disabilitaOverlayer();
	
			var w = (parametri.width != undefined)?parametri.width:window.innerWidth - 50;
			var h = (parametri.height != undefined)?parametri.height:window.innerHeight - 50;
			var t = "center";
			var l = "center ";
			var la="center";
			var modale = false;
			var dragga = true;
			var ridim = true;
	
			if(tmpW.w !=undefined){
				w = tmpW.w;
				h = tmpW.h;
			}
			if(tmpW.x !=undefined){
				t = "top+"+tmpW.y+"";
				l = "left+"+tmpW.x+" ";
				la="left top";
			}
			
			if(param == null)
				param = "";
			else
				if(param == "init")
					param = "";
			
			if(parametri.fullscreen == true){
				modale = true;
				ridim = true;
				dragga = false;
				t = "center";
				l = "center ";
				la= "center";
			}
			/**
			 * Creo in runtime il div che conterrà la dialog e la inserisco come figlio di #mapContainer
			 * **/
			
			$("body").append("<div id='"+id+"' class=\"innerleftMapsize\"></div>");

			$.ajax({
				url: clone?"page/"+id.split("_clone")[0]+".jsp":"page/"+id+".jsp",
				context: document.body,
				data: {"p": JSON.stringify(param)},
				success : function(result) {
						try{
							$("#"+id).html(result).dialog({
								modal: modale,
								dialogClass: clone?"light modale ns "+modale:"light modale"+modale,
								width: w>window.innerWidth ? window.innerWidth-50:w,
								height: h,
								position: { my: l + t, at: la},
								//position: [t,l],
								title:$("#"+id+" .titolo").text()!= '' ? $("#"+id+" .titolo").text() : id.toUpperCase(),
								//title: parametri.breadcrub,
								resizable: ridim,
								draggable: dragga,
								//snap: true,
								open: function( event, ui ) {
									//$(event.target).parent().find(".ui-dialog-titlebar").append("<button class=\"ui-dialog-titlebar-minifize\"></button><button class=\"ui-dialog-titlebar-info\" data-id=\""+id+"\" ></button>");
									
									if(page.t != undefined )
										$('#'+id+' a[href="#'+page.t+'"]').tab('show');
									
									if(window[id+"Open"])
										window[id+"Open"](e!=null?e.currentTarget:"");
	
									// Make sure the callback is a function​
								    if (typeof callBack === "function") {
										callBack();					    	
								    }
								    
								    $(".ui-dialog-titlebar" ).dblclick(function() {
								    	$(this).parent().toggleClass("maximized");
								    }).click(function(){
								    	$(this).parent().addClass("active").siblings(".ui-dialog").removeClass("active");
								    });
								    appUtil.reloadTooltips();
								    $('#'+id+' .dropdown.mainMenu').prependTo($("#"+id).parent());
								    initCalendar();
								},
								beforeClose: function( event, ui ) {
									if(window[id+"Close"])
										window[id+"Close"]();
								}
							}).on('dialogclose', function(event) {
								closeDetail(id);
								$(this).dialog('destroy').remove();
							});
						}
						catch(e){
							hideLoader();
							removeParamFromStorage(id);
							$("#"+id).remove();
							console.log(e);
							alert("Si è verificato un errore!");
						}
				}
			});//close call ajax	
	}
	else
		console.log("Finestra già aperta");
}
function showLoader(type){	
	$("body").addClass("inprogress");
	if(type)
		$("#loader").addClass("all");
	else
		$("#loader").removeClass("all");
};
function hideLoader(){
	$("body").removeClass("inprogress");
};
function closeDetail(id){
	selected="";
	window.history.pushState("#void", "","Mappa");
	console.log("CHIUDO: "+id);
}
/**
 * Questa function mi permette di eliminare un qualsiasi item dialog dal sessionStorage
 * @para id -> chiave salvata in session storage
 * **/
function removeParamFromStorage(id){
	if(sessionStorage[""+suffixLocalStorage+".dialog."+id] != undefined){
		var obj = JSON.parse(sessionStorage[""+suffixLocalStorage+".dialog."+id]);
		obj.active = false;
		sessionStorage[""+suffixLocalStorage+".dialog."+id] = JSON.stringify(obj);
	}
}

function salva(){
	showMessageAlertApplication(jQuery.i18n.prop('message.operation.success'), jQuery.i18n.prop('PopupAlert.success'), false, false, true);
}

function salvaC(){
	toggleSidebar();
	clearDetail();
	clearDisegno();
	$('#myModalDesc').modal('toggle');
	showMessageAlertApplication(jQuery.i18n.prop('message.operation.success'), jQuery.i18n.prop('PopupAlert.success'), false, false, true);
}
function eliminaC(){
	toggleSidebar();
	clearDetail();
	clearDisegno();
	toggleSidebar();
	$(".ctx").removeClass("hidden");
}

function avviaC(){
	toggleSidebar();
	$('#myModalDesc').modal('toggle');
	clearDetail();
	clearDisegno();
	clearPerimetro();
	toggleSidebar();
	$(".ctx").removeClass("hidden");
	showMessageAlertApplication(jQuery.i18n.prop('message.operation.success'), jQuery.i18n.prop('PopupAlert.success'), false, false, true);
}

function elimina(){
	showMessageAlertApplication(jQuery.i18n.prop('message.operation.confirm.cancel'), jQuery.i18n.prop('PopupAlert.conferma'), false, false, false);
}

function showMessageAlertApplication(message, title, isInfo, isError, isSuccess, height, width){
	   
	var iconError = "<img src='images/iconAlert/errorAlert.png' class='f' />";
	var iconInfo = "<img src='images/iconAlert/infoAlert.png' class='f' />";
	var iconSuccess = "<img src='images/iconAlert/successAlert.png' class='f' />";
	
	var iconStyle  = "";
	var alertHeader = "";
	
	if(isInfo) {
		iconStyle = iconInfo;
		alertHeader = jQuery.i18n.prop('PopupAlert.info');
	}
	else if(isError){
		iconStyle = iconError;
		alertHeader = jQuery.i18n.prop('PopupAlert.attenzione');
	}
	else if(isSuccess){
		iconStyle = iconSuccess;
		alertHeader = jQuery.i18n.prop('PopupAlert.success');
	}
	// deafult is info
	else{
		iconStyle = iconInfo;
		alertHeader = jQuery.i18n.prop('PopupAlert.info');
	}
	//console.log(iconStyle)
	
	var messageStyleStart = "<p class='alertmessages' >";
	var messageStyleEnd = "</p>";
	var alertDialogHeight = "auto";
	var alertDialogWidth = "auto";
	
	$alertDialogMB.html(iconStyle + messageStyleStart + message + messageStyleEnd);

	if(title == 'undefined' || null == title || '' == title)
        $alertDialogMB.dialog("option", "title", alertHeader );
    else
        $alertDialogMB.dialog("option", "title", title );
    
    // check if got height
    if(height == 'undefined' || null == height || '' == height)
        $alertDialogMB.dialog( "option", "height", alertDialogHeight);
    else
        $alertDialogMB.dialog( "option", "height", height);
    
    //check if got width
    if(width == 'undefined' || null == width || ''== width)
        $alertDialogMB.dialog("option", "width", alertDialogWidth);
    else
        $alertDialogMB.dialog( "option", "width", width);
    
    $alertDialogMB.dialog( "option", "dialogClass", "ui-dialog-alermessages" );
    
    // open the alert dialog box    
    $alertDialogMB.dialog('open');
    
    // prevent the default action
    return false;
};

$(function() {
	$alertDialogMB = $('<div style="vertical-align:middle; "></div>').html("").dialog({
		position: { my : "center", at : "center", of : window },
	    autoOpen: false,
	    modal: true,
	    draggable: true,
	    resizable: false,
	    buttons: {
	        Ok: function() {
	            $(this).dialog( "close" );
	        }
	    }
	}); 
});

function cercaCoordinate(){
	var lat = $("#searchLat").val()*1;
	var lon = $("#searchLon").val()*1;
	if(lat!="" && lon !="")
		(appMappa.maps).getView().setCenter(ol.proj.transform([lat, lon], 'EPSG:4326', 'EPSG:3857'));
}

/**
 * Metodo per compilare un template Handlebars e restituirne l'HTML.
 * @param idTemplate è l'id dello script Handlebars
 * @param context è l'oggetto context con la quale compilare il template.
 * @returns l'HTML del template compilato
 */
function compilaTemplate(idTemplate, context){
	let source   = document.getElementById(idTemplate).innerHTML;
	let template = Handlebars.compile(source);
	return template(context);
}

function minifize(id){
	$("#"+id).toggleClass("hidden").parent().toggleClass("maxWidth").removeClass("maximized");
}
function closeStaticDetail(id) {
	$("#"+id).dialog('destroy').remove();
}
function openStaticDetailViaHandlebars(id, idTemplateHandlebars, contextHandlebars, titolo, callBack, parameters){
	let html = compilaTemplate(idTemplateHandlebars, contextHandlebars);
	openStaticDetailViaHTML(id, html, titolo, callBack, parameters);
}

function openStaticDetailViaHTML(id, html, titolo, callBack, parameters){
	if($("#"+id).length <= 0){
		var marginHeight = parameters.marginHeight!=null?parameters.marginHeight:50;
		var marginWidth = parameters.marginWidth!=null?parameters.marginWidth:50;
		var w = parameters && $.isNumeric(parameters.width) ? parameters.width : window.innerWidth - marginWidth;
		var h = parameters && $.isNumeric(parameters.height) ? parameters.height : window.innerHeight - marginHeight;
		var t = "center";
		var l = "center ";
		var la="center";
		var position = parameters.position!=null ? parameters.position : { my: l + t, at: la};
		if( parameters && parameters.position ) position = parameters.position;
		var modale = ( parameters && $.type(parameters.modale) === 'boolean' ) ? parameters.modale : false;
		var draggable = ( parameters && $.type(parameters.draggable) === 'boolean' ) ? parameters.draggable : true;
		var resizable = ( parameters && $.type(parameters.resizable) === 'boolean' ) ? parameters.resizable : true;
		var closable = (parameters && $.type(parameters.closable) === 'boolean' ) ? parameters.closable : false;
		// Creo in runtime il div che conterrà la dialog e la inserisco come figlio di #mapContainer
		$("body").append("<div id='"+id+"' class=\"innerleftMapsize\"></div>");
			
		try{
			$("#"+id).html( html ).dialog({
				modal: modale,
				dialogClass: "light modale"+modale+" modale-messina",
				width: w>window.innerWidth ? window.innerWidth-50:w,
				height: h,
				position: position,
				title: titolo,
				resizable: resizable,
				draggable: draggable,
				closable: closable,
				//snap: true,
				show: 'drop',
				hide: 'drop',
				closeOnEscape: false,
				open: function( event, ui ) {
					$(event.target).parent().find(".ui-dialog-titlebar").append("<button type=\"button\" onclick=\"minifize('"+id+"')\" class=\"ui-dialog-titlebar-minifize\"><i class=\"fa fa-minus\"></i></button>");
					if(window[id+"Open"])
						window[id+"Open"](e!=null?e.currentTarget:"");
				    if ($.isFunction(callBack)) {
						callBack();					    	
				    }
				    if(resizable){
				    	$(event.target).parent().find(".ui-dialog-titlebar").dblclick(function() {
				    		$(this).parent().toggleClass("maximized");
				    		$(this).parent().removeClass("maxWidth").find(".innerleftMapsize").removeClass("hidden");
				    	});
				    }
				    if (!closable){
				    	$(event.target).parent().find(".ui-dialog-titlebar").addClass("hide");
				    }
				    	
				    appUtil.reloadTooltips();
				    $('#'+id+' .dropdown.mainMenu').prependTo($("#"+id).parent());
				},
				beforeClose: function( event, ui ) {
					if(window[id+"Close"])
						window[id+"Close"]();
				}
			}).on('dialogclose', function(event) {
				$(this).dialog('destroy').remove();
			});
		} catch(e){
			hideLoader();
			$("#"+id).remove();
			console.log(e);
		}
	} else {
		console.log("Finestra già aperta");
	}
}


