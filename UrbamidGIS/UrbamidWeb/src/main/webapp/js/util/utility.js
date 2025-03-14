/*******************************************************************************************************************************************************/
/***************************************			Funzionalita' di UTILITY			****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

$(function() {
	//jquery alert box - the general alert box
	$alertDialogMB = 
		$('<div style="vertical-align:middle; "></div>').html("")
		    .dialog({
		    	classes: {'ui-dialog':'ui-dialog modale-messina'},
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
	//jquery confirm box - the general confirm box
	$confirmDialogMB = 
		$('<div style="vertical-align:middle; "></div>').html("")
		    .dialog({
		    	classes: {'ui-dialog':'ui-dialog modale-messina'},
		    	title : jQuery.i18n.prop('PopupAlert.conferma'),
		    	position: { my : "center", at : "center", of : window },
		        autoOpen: false,
		        modal: true,
		        draggable: true,
		        resizable: false,
		        width:"auto",
		        height:"auto"
		    });
	//jquery confirm box - the general confirm box
	$confirmDeleteArchiveDialogMB = 
		$('<div style="vertical-align:middle; "></div>').html("")
		    .dialog({
		    	classes: {'ui-dialog':'ui-dialog modale-messina'},
		    	title : jQuery.i18n.prop('PopupAlert.conferma'),
		    	position: { my : "center", at : "center", of : window },
		        autoOpen: false,
		        modal: true,
		        draggable: true,
		        resizable: false,
		        width:"auto",
		        height:"auto"
		    });
});
appUtil={
		
		/**
		 * Definizione del nameSpace URBAMID
		 * @param
		 * @returns {String}
		 **/
		getUrbamidOrigin : function (){
			var confOrigin = "";
			var confProt 	= appConfig.urbamidProtocol==null || appConfig.urbamidProtocol=="" ? 
								window.location.protocol : appConfig.urbamidProtocol+":";
			var confHost 	= appConfig.urbamidHostname==null || appConfig.urbamidHostname=="" ? 
								window.location.hostname : appConfig.urbamidHostname;
			var confPort	= appConfig.urbamidPort==null || appConfig.urbamidPort=="" ? 
								(window.location.port ? ':' + window.location.port: '') : ":"+appConfig.urbamidPort;
								confOrigin = confProt + "//" + confHost + confPort;
			return confOrigin;
		},
		
		/**
		 * Definizione del nameSpace GEOSERVER
		 * @param
		 * @returns {String}
		 **/
		getOrigin : function (){
			var confOrigin = "";
			var confProt 	= appConfig.protocol==null || appConfig.protocol=="" ? 
								window.location.protocol : appConfig.protocol+":";
			var confHost 	= appConfig.hostname==null || appConfig.hostname=="" ? 
								window.location.hostname : appConfig.hostname;
			var confPort	= appConfig.port==null || appConfig.port=="" ? "" : ":"+appConfig.port;
            
            confOrigin = confProt + "//" + confHost + confPort;
			
			return confOrigin;
		},

		/** 
		 * Replace del nameSpace proveniente da urbamid 
		 * 		DA:		http:\\localhost:8080\geoserver\rest\workspaces\urbamid\layergroups.json
		 * 		A:		http:\\192.168.11.72:8080\geoserver\rest\workspaces\urbamid\layergroups.json
		 **/
		replaceUrbamidNameSpace : function ( href ){
			if (href!=null && href!=undefined)
				href = href.replace( appUtil.extractHostname(href), appUtil.getUrbamidOrigin());
			return href;
		},
		
		/** 
		 * Replace del nameSpace proveniente da geoserver 
		 * 		DA:		http:\\localhost:8080\geoserver\rest\workspaces\urbamid\layergroups.json
		 * 		A:		http:\\192.168.11.72:8080\geoserver\rest\workspaces\urbamid\layergroups.json
		 **/
		replaceNameSpace : function ( href ){
			if (href!=null && href!=undefined)
				href = href.replace( appUtil.extractHostname(href), appUtil.getOrigin());
			return href;
		},

		/**
		 * Estrae protocollo, hostname e portNumber se presente nella url passata in input
		 * @param url
		 * @returns {String}
		 */
		extractHostname : function (URL) {
			var hostname;
			if (URL.indexOf("//") > -1) 
				hostname = URL.split('/')[0]+"//"+""+URL.split('/')[2];
			else 
				hostname = URL.split('/')[0];
			return hostname;
		},

		/**
		 * Restituisce il tipo di oggetto di geoserver. 
		 * Può essere:
		 * 	- areaTematica
		 *  - tematismo
		 *  - layerGroup
		 *  - layer
		 * @param item
		 * @returns {String}
		 */
		getTypeItemGeoserver : function ( item ) {
			if ( (item.name).indexOf("_areaTematica")!=-1 ) {
				return "areaTematica";
			} else if ( (item.name).indexOf("_tematismo")!=-1 ) {
				return "tematismo";
			} else if ( (item.name).indexOf("_layergroup")!=-1 ) {
				return "layergroup";
			} else {
				return "layer";
			}
			return "";
		},

		/**
		 * Genera un id per l'oggetto del menu di Jstree
		 * @param parent
		 * @param newItemId
		 * @returns {___anonymous_id}
		 */
		generateItemId : function (parent, newItemId) {
			id = parent;
			if (newItemId.indexOf("_areaTematica")!=-1 || newItemId.indexOf("_ATE")!=-1)
				id = id+newItemId.replace("_areaTematica","_ATE%")
			if (newItemId.indexOf("_tematismo")!=-1 || newItemId.indexOf("_TE")!=-1)
				id = id+(id==""?"":"%")+newItemId.replace("_tematismo","_TE%")
			if (newItemId.indexOf("_layergroup")!=-1 || newItemId.indexOf("_LG")!=-1)
				id = id+(id==""?"":"%")+newItemId.replace("_layergroup","_LG%")
			if ( newItemId.indexOf("_areaTematica")==-1 && newItemId.indexOf("_ATE")==-1 && 
					newItemId.indexOf("_tematismo")==-1 && newItemId.indexOf("_TE")==-1 && 
					newItemId.indexOf("_layergroup")==-1 && newItemId.indexOf("_LG")==-1 ) {
				id = id+(id==""?"":"%")+(newItemId!=null?newItemId:"");
			}
			return (id.substring(id.length-1)=="%")?id.substring(0,id.length-1): id;
		},
		
		generateId : function (string) {
			return string.replace(/\ /g, '_').replace(/\-/g, '_').replace(/\(/g, '_').replace(/\)/g, '_');
		},

		/********************
		 * FUNZIONI ARRAY
		 *******************/
		/**
		 * Ritorna il numero di elementi dell'array
		 * @param array
		 * @returns {Number}
		 */
		getNumElem : function (array){
			var numElem=0;
			$.each( array, function( keyL, layer ) {
				numElem++;
			});
			return numElem;
		},

		/**
		 * Estrae l'elemento dall'array se la key o il value sono uguali
		 * @param array
		 * @param key
		 * @param value
		 * @returns {___anonymous_outValue}
		 */
		getElemFromArray : function  (array, key, value) {
			outValue = null;
			$.each( array, function( keyO, valueO ) {
				if (valueO==value || valueO.id==key) 
					outValue = valueO;
			});
			return outValue;
		},

		contains : function (array, id) {
			result = false;
			$.each( array, function( keyO, valueO ) {
				if (valueO.id==id) 
					result = true;
			});
			return result;
		},
		
		containsString : function (arrayStr, str) {
			result = false;
			$.each( arrayStr, function( keyStr, valueStr ) {
				if (valueStr==str) 
					result = true;
			});
			return result;
		},
		
		/**
		 * Elimina dall'array l'elemento se la key o il value sono uguali
		 * @param array
		 * @param key
		 * @param value
		 */
		removeElemFromArray : function (array, key, value){
			for (i=0;i<array.length;i++){
				if (array[i].id == key) {
					array.splice(i, 1);
					break;
				}
			}
			return array;
		},
		
		/**
		 * Elimina dall'array l'elemento se la key o il value sono uguali
		 * @param array
		 * @param key
		 * @param value
		 */
		removeElemFromHm : function (array, key){
			delete array[key];
			array.length--;
			return array;
		},

		/**
		 * Imposta il messaggio nel box dei messaggi per la durata di 4 secondi
		 * @param mes
		 */
		setMessage : function( mes ){
			$("#message").html( mes );
			$("#header-message").slideDown("slow");
			setTimeout(function(){
				$( "#header-message" ).slideUp();
				$("#message").empty();
			}, 4000);
		},
		
		/**
		 * @param mes
		 */
		containsFeature : function( featureSelected, singleFeature ){
			contains = false;
			$.each( featureSelected, function( keyF, valueF ) {
				if (valueF.id_==singleFeature.id_)
					contains = true;
			});
			return contains;
		},
		
		isEmptyOrNull : function ( string ) {
			if( typeof string == "undefined" ) {
				return true;
			} else if ( string == ''){
				return true;
			} else if ( string == null ) {
				return true;
			}
		},
		
		arraySort : function ( arrayGeoserver ) {
			arrayGeoserver.sort( 
				function(a, b){
			    	var nameA=a.name.toLowerCase(), nameB=b.name.toLowerCase()
			    	if (nameA < nameB) //sort string ascending
			        	return -1 
			        if (nameA > nameB)
			    		return 1
			    	return 0 //default return value (no sorting)
				} 
			);
			return arrayGeoserver;
		},
		
		/**
		 * Mostra il loading
		 * 
		 * Uso, codice da inserire nella vostra pagina di homepage(.jsp):
		 *  (code)
		 *    <div id="loader">
		 *  	 <img src="images/loader/Load1.gif" />
		 *  	 <span>Loading</span>
		 *       <div class="overlayer"></div>
		 *    </div>
		 *  (end)
		 * 
		 * @param type, opzionale
		 * @param customMessage, testo che deve apparire vicino al loading (opzionale)
		 */
		showLoader : function(type, customMessage){	
			let message = ($.type(customMessage) === 'string' && customMessage.length>0) ? customMessage : jQuery.i18n.prop('Loader.default');
			var options = {
				theme: 'sk-folding-cube',
				message: message,
				backgroundColor: '#000',
				textColor:'white'
			};
			HoldOn.open(options);
		},
		
		/**
		 * Rimuove il loading
		 */
		hideLoader : function (){
			HoldOn.close();
			$("#loader").hide();
		},
		
		/**
		 * Apre l' alert box configurata secondo i parametri forniti in ingresso,
		 * se vuoti per default viene aperto un'alert informativo.
		 * 
		 * @param {@link String} message, message to display on the alert box (Required)
		 * @param {@link String} title,  title of the box
		 * @param {@link Boolean} isInfo
		 * @param {@link Boolean} isError
		 * @param {@link Boolean} isSuccess
		 * @param {@link Integer} height
		 * @param {@link Integer} width
		 * 
		 * @returns {Boolean}
		 */
		showMessageAlertApplication : function (message, title, isInfo, isError, isSuccess, height, width){
			   
			var iconError = "<img src='images/iconAlert/errorAlert.png' class='f' />";
			var iconInfo = "<img src='images/iconAlert/infoAlert.png' class='f' />";
			var iconSuccess = "<img src='images/iconAlert/successAlert.png' class='f' />";
			
			var iconStyle  = "";
			var alertHeader = "";
			if(isInfo) {
				iconStyle = iconInfo;
				alertHeader = jQuery.i18n.prop('PopupAlert.info');
			}
			else
				if(isError){
					iconStyle = iconError;
					alertHeader = jQuery.i18n.prop('PopupAlert.attenzione');
				}
			else
				if(isSuccess){
					iconStyle = iconSuccess;
					alertHeader = jQuery.i18n.prop('PopupAlert.success');
				}
			// deafult is info
			else{
				iconStyle = iconInfo;
				alertHeader = jQuery.i18n.prop('PopupAlert.info');
			}
			
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
		    
		    $alertDialogMB.dialog( "option", "dialogClass", "ui-dialog-alermessages");
		    $alertDialogMB.dialog( "option", "dialogClass", "modale-messina");
		    
		    // open the alert dialog box    
		    $alertDialogMB.dialog('open');
		    
		    // prevent the default action
		    return false;
		},
		//MS
		showDialogApplication : function (message, title, width, height){
	 
			var messageStyleStart = "<div class='col-md-12 show-dialog-application' >";
			var messageStyleEnd = "</div>";
			var alertDialogHeight = "auto";
			var alertDialogWidth = "auto";
			
			$alertDialogMB.html(messageStyleStart + message + messageStyleEnd);

			if(title != 'undefined' && null != title && '' != title)
		        $alertDialogMB.dialog("option", "title", title );
 
		    if(width == 'undefined' || null == width || ''== width)
		        $alertDialogMB.dialog("option", "width", alertDialogWidth);
		    else
		        $alertDialogMB.dialog( "option", "width", width);
 
		    if(height == 'undefined' || null == height || '' == height)
		        $alertDialogMB.dialog( "option", "height", alertDialogHeight);
		    else
		        $alertDialogMB.dialog( "option", "height", height);
		    
		    $alertDialogMB.dialog( "option", "dialogClass", "ui-dialog-alermessages");
		    $alertDialogMB.dialog( "option", "dialogClass", "modale-messina");
		    
		    
		    $alertDialogMB.dialog('open');
		    
		 
		    return false;
		},
		
		/**
		 * Funzione che permette di aggiungere la funzione di autocomplete ad un campo.
		 * 
		 * TODO da estendere con altre opzioni : per ora permette autocomplete su dati caricati lato server con method POST
		 * 
		 * {@link http://easyautocomplete.com/guide}
		 * 
		 * @param idField - Identificativo del campo di input su cui abilitare l'autocomplete
		 * @param url - url per il recupero dei dati lato server
		 * @param searchTerm - Nome della proprietà su cui effettuare la ricerca
		 * @param label - Nome della proprietà da visualizzare come label del campo
		 * @param value - Nome della proprietà da utilizzare come value
		 * @param placeholder - Messaggio di testo da visualizzare come placeholder
		 */
		activeAutocompleteOnFiled : function (idField, url, searchTerm, label, value, placeholder){
			 var options = {
						
				  url: function(phrase) { return url; },
				  ajaxSettings: {
					method: "POST",
					contentType: "application/json",
					dataType: "json",
					data: {}
				  },
				  preparePostData: function(data) {
					data[searchTerm] = $("#"+idField).val();
					return data;
				  },
				  list: {
					//Alla scelta dell'opzione setto il valore del campo univoco da memorizzare (Ex. id)
					onChooseEvent: function() {
						var val = $("#"+idField).getSelectedItemData()[value];
						var sigla = $("#"+idField).getSelectedItemData().sigla;
						var id = $("#" + idField).getSelectedItemData().id
						$("#"+idField+"Value").val(id);
						
						if(sigla != null && sigla != undefined) {
							$("#codiceCippoM").val(sigla);
						}
					},
					//Svuoto il valore del campo univoco da memorizzare (Ex. id)
					onLoadEvent: function() {
						$("#"+idField+"Value").val(null);
					},
				  },
				  requestDelay: 400,
				  listLocation: "aaData",
				  placeholder: placeholder,
				  getValue: function(element) {
					  return element[label];
				  }
			  };
			  
			 $("#"+idField).easyAutocomplete(options);
		},

		/** 
		 * Funzione generica di conferma.
		 *
		 * @param saveFunction - Funzione di callback da richiamare alla pressione del tasto Conferma
		 * @param cancelFunction - Funzione di callback da richiamare alla pressione del tasto Annulla
		 * @param {@link Object} param, parametri da passare in ingresso alla funzione che verrà richiamata
		 * @param message - Messaggio personalizzato di conferma
		 * **/
		confirmOperation : function (saveFunction, cancelFunction, param, message){
			
			var activeButtons = [];
			var buttonAnnulla = {
								  text: jQuery.i18n.prop('Button.annulla'),
								  "class": 'annullaButtonClass',
							      click: function() { 
							      	$(this).dialog('close');
							        //Effettuo eventuale funzione di callback alla pressione del tasto Annulla
							        if(cancelFunction) cancelFunction(param); 
							      }
								};
			 var buttonConferma = {
								    text: jQuery.i18n.prop('Button.conferma'),
								    "class": 'saveButtonClass',
							        click: function() { 
							        	$(this).dialog('close');
										//Effettuo eventuale funzione di callback alla pressione del tasto salva
										if(saveFunction) saveFunction(param);
							        }
								  };

		    activeButtons.push(buttonConferma);
		    activeButtons.push(buttonAnnulla);
		    
			var iconInfo = "<img src='images/iconAlert/infoAlert.png' class='f' />";
			var messageStyleStart = "<p class='alertmessages' >";
			var messageStyleEnd = "</p>";
			
			var confirmMessage = jQuery.i18n.prop('message.operation.confirm');
			if(null != message) confirmMessage = message;
			
			$confirmDialogMB.html(iconInfo + messageStyleStart + confirmMessage + messageStyleEnd);  
			$confirmDialogMB.dialog("option", "dialogClass", "ui-dialog-alermessages" );
			$confirmDialogMB.dialog("option", "dialogClass", "modale-messina" );
			$confirmDialogMB.dialog('option', 'buttons', activeButtons);
			$confirmDialogMB.dialog('open');
		},
		
		/** 
		 * Funzione generica di conferma.
		 *
		 * @param saveFunction - Funzione di callback da richiamare alla pressione del tasto Conferma
		 * @param cancelFunction - Funzione di callback da richiamare alla pressione del tasto Annulla
		 * @param {@link Object} param, parametri da passare in ingresso alla funzione che verrà richiamata
		 * @param message - Messaggio personalizzato di conferma
		 * **/
		confirmDeleteArchiveOperation : function (saveFunction, cancelFunction, param, message){
			
			var activeButtons = [];
			var buttonAnnulla = {
								  text: jQuery.i18n.prop('Button.annulla'),
								  "class": 'annullaButtonClass',
							      click: function() { 
							      	$(this).dialog('close');
							        //Effettuo eventuale funzione di callback alla pressione del tasto Annulla
							        if(cancelFunction) cancelFunction(param); 
							      }
								};
			 var buttonConferma = {
								    text: jQuery.i18n.prop('Button.conferma'),
								    "class": 'saveButtonClass',
							        click: function() { 
							        	$(this).dialog('close');
										//Effettuo eventuale funzione di callback alla pressione del tasto salva
										if(saveFunction) saveFunction(param);
							        }
								  };

		    activeButtons.push(buttonConferma);
		    activeButtons.push(buttonAnnulla);
		    
		    var confirmMessage = jQuery.i18n.prop('message.operation.confirm');
			if(null != message) confirmMessage = message;
			
		    var content = "<img src='images/iconAlert/infoAlert.png' class='f' />" +
		    			  "<p class='alertmessages' >"+confirmMessage+"</p>" +
		    			  "<div class='alertcheckbox'>"+
		    				 "<input type='checkbox' id='archive' name='archive' value='true'>"+
		    				 "<label class='labelCheckDialog' for='archive'>Archivia elemento</label>"+
		    			  "</div>";
			
			$confirmDeleteArchiveDialogMB.html(content);  
			$confirmDeleteArchiveDialogMB.dialog("option", "dialogClass", "ui-dialog-alermessages" );
			$confirmDeleteArchiveDialogMB.dialog("option", "dialogClass", "modale-messina" );
			$confirmDeleteArchiveDialogMB.dialog('option', 'buttons', activeButtons);
			$confirmDeleteArchiveDialogMB.dialog('open');
		},
		
		/** 
		 * Funzione generica di conferma.
		 *
		 * @param saveFunction - Funzione di callback da richiamare alla pressione del tasto Conferma
		 * @param cancelFunction - Funzione di callback da richiamare alla pressione del tasto Annulla
		 * @param {@link Object} param, parametri da passare in ingresso alla funzione che verrà richiamata
		 * @param message - Messaggio personalizzato di conferma
		 * **/
		confirmInserisciPubblicaOperation : function (publish, saveFunction, cancelFunction, param, message){
			
			var activeButtons = [];
			var buttonAnnulla = {
								  text: jQuery.i18n.prop('Button.annulla'),
								  "class": 'annullaButtonClass',
							      click: function() { 
							      	$(this).dialog('close');
							        //Effettuo eventuale funzione di callback alla pressione del tasto Annulla
							        if(cancelFunction) cancelFunction(param); 
							      }
								};
			 var buttonConferma = {
								    text: jQuery.i18n.prop('Button.conferma'),
								    "class": 'saveButtonClass',
							        click: function() { 
							        	$(this).dialog('close');
										//Effettuo eventuale funzione di callback alla pressione del tasto salva
										if(saveFunction) saveFunction(param);
							        }
								  };

		    activeButtons.push(buttonConferma);
		    activeButtons.push(buttonAnnulla);
		    
		    var confirmMessage = jQuery.i18n.prop('message.operation.confirm');
			if(null != message) confirmMessage = message;
			
			var divCheckBox = "";
			if (publish) {
				divCheckBox = "<div>"+
  				 				"<input type='checkbox' id='publish' name='publish' value=''>"+
  				 				"<label class='labelCheckDialog' for='archive'>Pubblica elemento</label>"+
  				 			  "</div>";
		    }
			
		    var content = "<img src='images/iconAlert/infoAlert.png' class='f' />" +
		    			  "<div class='alertmessages'>"+confirmMessage+"</br>"+
		    			  		divCheckBox+
		    			  "</div>";
		    
			$confirmDeleteArchiveDialogMB.html(content);  
			$confirmDeleteArchiveDialogMB.dialog("option", "dialogClass", "ui-dialog-alermessages" );
			$confirmDeleteArchiveDialogMB.dialog("option", "dialogClass", "modale-messina" );
			$confirmDeleteArchiveDialogMB.dialog('option', 'buttons', activeButtons);
			$confirmDeleteArchiveDialogMB.dialog('open');
		},
		/** 
		 * Navigator Object
		 *  The navigator object contains information about the browser.
		 * 
		 * Note: There is no public standard that applies to the navigator object, but all major browsers support it.
		 * 
		 * @return {@link String} - A String, representing the language version of the browser. 
		 * 
		 **/
		currentLocale : function (){
			
			return navigator != null ? navigator.language : "it";
		},
		
		/**
		 * La finestra di timeout è utilizzato per visualizzare all'utente che ha effettuato l'accesso che la sessione 
		 * sta per scadere. 
		 * Si crea una finestra con un conto alla rovescia, al termine del conto a rovescia si viene reindirizzati alla pagina di logout.
		 * Al, contrario se viene efftuata una qualche azione in pagina, il timer riparte.
		 * 
		 * @param {@Integer} timeWarnAfter, Time in milliseconds after page is opened until warning dialog is opened
		 * @param {@Integer} timeRedirAfter, Time in milliseconds after page is opened until browser is redirected to redirUrl
		 */
		initSessionTimeout : function (timeWarnAfter, timeRedirAfter){
			
		    $.sessionTimeout({
		    	/**
				 * Il messaggio titolo nella finestra di dialogo. Valore di default: 'Your Session is About to Expire!'
				 */
		    	title: jQuery.i18n.prop('Session.title.warning.expired'),
		    	/**
		    	 * Text shown to user in dialog after warning period. Default: 'Your session is about to expire.'
		    	 */
		        message: "",
		    	/**
			   	 * Il testo del pulsante NO per terminare la sessione. Valore di default: 'Logout'
			   	 */
		        logoutButton: jQuery.i18n.prop('Session.button.no.warning.expired'),
		        /**
		    	 * Il testo del tasto YES per mantenere viva la sessione. Valore di default: 'Stay Connected'
		    	 */
		        keepAliveButton: jQuery.i18n.prop('Session.button.yes.warning.expired'),
		        /**
		         * Time in milliseconds after page is opened until warning dialog is opened. Default plugin: 900000 (15 minutes)
		         */
		     	warnAfter: (timeWarnAfter != undefined && timeWarnAfter != null && timeWarnAfter != "" && timeWarnAfter != 0) ? timeWarnAfter : Constants.TIMEOUT_WARN_TIME,
		     	/**
		     	 * Time in milliseconds after page is opened until browser is redirected to redirUrl. Default plugin: 1200000 (20 minutes)
		     	 */
		    	redirAfter: (timeRedirAfter != undefined && timeRedirAfter != null && timeRedirAfter != "" && timeRedirAfter != 0) ? timeRedirAfter : Constants.TIMEOUT_REDIR_TIME,
		    	/**
		    	 * URL da chiamare tramite AJAX per mantenere viva sessione. Questa risorsa dovrebbe fare qualcosa di innocuo che avrebbe mantenuto la 
		    	 * sessione viva, che dipenderà dalla vostra piattaforma server-side. Predefinito: '/ keep-alive'
		    	 */
		        keepAliveUrl: "./Mappa",
		        /**
		         * How should we make the call to the keep-alive url? (GET/POST/PUT) Default: 'POST'
		         */
		        ajaxType: 'GET',
		        /**
		         * URL to take browser to if no action is take after warning period. Default: '/timed-out'
		         */
		        redirUrl: "./logout?expired",
		        /**
		         * URL to take browser to if user clicks "Log Out Now". Default: '/log-out'
		         */
		        logoutUrl: "./logout?logout",
		        /**
		         * Reset timer on any of these events : keyup mouseup mousemove touchend touchmove
		         */
		        ignoreUserActivity: true,
		        /**
		         * Il messaggio di conto alla rovescia in cui verrà utilizzato {timer} per inserire il valore del conto alla rovescia.
		         */
		        countdownMessage: "<img src='./images/iconAlert/errorAlert.png' />" + jQuery.i18n.prop('Session.message.warning.expired'),
		        /**
		         * Progress bar del conto alla rovescia
		         */
		        countdownBar: false,
		        /**
		         * Format del conto alla rovescia
		         */
		        countdownSmart: true
		    });
		},
		
		/**
		 * Ricarica e/o ricostruisce i tooltip
		 * 
		 * TODO gestire una maggiore personalizzazione via data-attribute
		 * TODO raggruppare le proprietà comuni in un object a parte
		 */
		reloadTooltips : function(){
			//dopodichè imposto per tutti gli elementi il content del tooltip basandoci
			//o sull'attributo 'title' (che viene anche rimosso per evitare conflitti) oppure 
			//sul data-attribute relativo (data-tooltip-content)
			var elementi = document.querySelectorAll('[data-info]');
			for(var i=0;i<elementi.length;i++){
				let contentTitle = elementi[i].getAttribute('title');
				let contentData = elementi[i].getAttribute('data-info');
				let content = '';
				if(contentTitle !== null){
					elementi[i].removeAttribute('title');
					elementi[i].setAttribute('data-info', contentTitle);
				} else if(contentData !== null){
					content = contentData;
				} else {
					//I tooltip bruciano, i div muoiono ma il vero html è per sempre
					elementi[i]._tippy().destroy();
				}
				if( elementi[i]._tippy ){
					elementi[i]._tippy.setProps({ content: content, });
				} else {
					tippy(elementi[i], {
						placement: 'top',
						theme: 'material',
						animation: 'perspective',
					});
				}
			}
		},
		
		numHmElem : function(hm){
			var len = 0, key;
		    for (key in hm) {
		        if (hm.hasOwnProperty(key)) len++;
		    }
		    return len;
		}
};