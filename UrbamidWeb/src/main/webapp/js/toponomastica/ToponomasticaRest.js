/**
 * @include util.js
 */

toponomasticaRest = {
	
	/**
	 * Il metodo gestisce l'upload dei file
	 * @param file, è il file che si vuole uplodare
	 * @param idRisorsa, indentifica le entità
	 * @param tipo è un indentificativo numerico che serve per distinguere le varie entità:
	 * 1 - TOPONIMO STRADALE
	 * 2 - PERCORSO
	 * 3 - LOCALITA'
	 * 4 - GIUNZIONI STRADALI
	 * 5 - ESTESA AMMINISTRATIVA
	 * 6 - AREE E ELEMENTI STRADALI
	 * 7 - CIPPO CHILOMETRICO
	 * 8 - ACCESSO
	 */
	uploadFile : function(file, idRisorsa, tipo) {
		
		return $.ajax({
			url: appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/upload?risorsa=' + idRisorsa + '&tipo=' + tipo,
			type: 'POST',
	        data: file,
	        enctype: 'multipart/form-data',
	        contentType: false,
            processData: false,
            cache: false,
		})
		
	},

	/**
	 * Il metodo effettua il download del documento dal server.
	 * @param nomeFile, è il nome del file
	 * @param idRisorsa, indentifica le entità
	 * @param tipo è un indentificativo numerico che serve per distinguere le varie entità:
	 * 1 - TOPONIMO STRADALE
	 * 2 - PERCORSO
	 * 3 - LOCALITA'
	 * 4 - GIUNZIONI STRADALI
	 * 5 - ESTESA AMMINISTRATIVA
	 * 6 - AREE E ELEMENTI STRADALI
	 * 7 - CIPPO CHILOMETRICO
	 * 8 - ACCESSO
	 */
	downloadFile : function(nomeFile, risorsa, tipo) {
		
		return $.ajax({
			url: appConfig.urbamidProtocol + '://' + appConfig.urbamidHostname + ':' + appConfig.urbamidPort + appConfig.urbamidService + 'toponomasticaCtrl/download/' + nomeFile + '?risorsa=' + risorsa + '&tipo=' + tipo,
			method: 'GET',
			contentType: 'application/octet-stream',
			xhrFields: {
	            responseType: 'blob'
	        },
	        success: function(data) {
	        	var a =  document.createElement('a');
	        	var url = window.URL.createObjectURL(data);
	        	a.href = url;
	        	a.download = nomeFile;
	        	document.body.append(a);
	        	a.click();
	        	a.remove();
	        },
	        error: function() {
	        	iziToast.show({
	    			title: 'Attenzione',
	    			theme: 'dark',
	    			icon:'fa fa-times',
	    			message: 'Errore nel download del documento!',
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
	        }
		})
		
	},

	/**
	 * Il metodo effettua l'export dello shape file, in base ai filtri passati in input
	 * @param {*} toponimoFilter filtri 
	 */
	exportShpFile: function(toponimoFilter) {
		return $.ajax({
			url: appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/exportShape',
			method: 'POST',
			data: JSON.stringify(toponimoFilter),
			contentType: 'application/json; charset=UTF-8',
			xhrFields: {
				responseType: 'blob'
			},
			success: function(data) {
				appUtil.hideLoader();
	        	var a =  document.createElement('a');
	        	var url = window.URL.createObjectURL(data);
	        	a.href = url;
	        	a.download = 'export_Toponimi_Stradali.zip';
	        	document.body.append(a);
	        	a.click();
	        	a.remove();
	        },
	        error: function() {
				appUtil.hideLoader();
	        	iziToast.show({
	    			title: 'Attenzione',
	    			theme: 'dark',
	    			icon:'fa fa-times',
	    			message: 'Errore nel download degli shape file!',
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
	        }
		})
	},

	/**
	 * Il metodo effetua la chiamata ajax per importare i file .dbf, .shp e .shx sul server
	 * @param {*} data 
	 */
	importShpFile: function(data) {
		return $.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/importShape',
			data: data,
			processData: false,
			contentType: false,
		});
	},

	/**
	 * Il metodo effettua la chiamata ajax per l'eliminazione dei file .dbf, .shp e .shx dal server
	 * @param {*} id l'id del file
	 */
	eliminaShapeFile : function(id) {
		
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/eliminaShapeFile?id=' + id,
			cache : true,
			async: true
		});
		
	},

	/**
	 * Il metodo effettua l'eliminazione del documento dal server.
	 * @param nomeFile, è il nome del file
	 * @param idRisorsa, indentifica le entità
	 * @param tipo è un indentificativo numerico che serve per distinguere le varie entità:
	 * 1 - TOPONIMO STRADALE
	 * 2 - PERCORSO
	 * 3 - LOCALITA'
	 * 4 - GIUNZIONI STRADALI
	 * 5 - ESTESA AMMINISTRATIVA
	 * 6 - AREE E ELEMENTI STRADALI
	 * 7 - CIPPO CHILOMETRICO
	 * 8 - ACCESSO
	 */
	eliminaDocumento : function(idRisorsa, tipo, nomeFile) {
		
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/eliminaDocumenti?risorsa=' + idRisorsa + "&tipo=" + tipo + "&nome=" + nomeFile,
			cache : true,
			async: true
		});
		
	},
	
	/** Il metodo effettua l'inserimento e/o la modifica di un toponimo stradale
	 *  @param toponimo, è l'oggetto con tutte le informazioni da salvare
	 *  @param publish, è un variabile booleana che serve a indicare se il toponimo dev'essere pubblicato o meno 
	 */
	inserisciToponimo : function(toponimo, publish) {
		toponimo.isCircle = (toponimo.isCircle!=undefined) ? toponimo.isCircle : false;
		var salvaToponimoStradale = {toponimoStradale:toponimo,publish:publish}
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/insertOrUpdateToponimo',
			data : JSON.stringify(salvaToponimoStradale),
			cache : true,
			async: true
		});
	
	},
	
	/** Il metodo effetta l'eliminazione/archiviazione del toponimo stradale
	 *  @param idToponimo, indentifica il toponimo stradale
	 *  @param archivia, è una variabile booleana che server pe indicare se il toponimo dev'essere archiviato o meno.
	 */
	eliminaToponimo : function(idToponimo, archivia) {
		
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + "toponomasticaCtrl/cancellaToponimo?id=" + idToponimo+"&archivia="+archivia,
			cache : true,
			async: true
		});
		
	},
	
	/** Il metodo effettua un controllo per vedere se ci sono dei toponimi figli con lo stato 'PUBBLICATO'
	 *  @param idPadre, indentificato del toponimo padre
	 */
	isFigliPubblicati : function(idPadre) {
		
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/isFigliPubblicati?idPadre=' + idPadre,
			cache : true,
			async: true
		});
		
	},

	/** Il metodo effettua l'inserimento e/o la modifica di una località
	 *  @param localita, è l'oggetto con tutte le informazioni da salvare
	 */
	inserisciLocalita : function(localita) {
		
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/insertOrUpdateLocalita',
			data : JSON.stringify(localita),
			cache : true,
			async: true
		});
		
	},
	
	/** Il metodo effetta l'eliminazione della località
	 *  @param idLocalita, indentifica la località
	 */
	eliminaLocalita : function(idLocalita) {
		
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/cancellaLocalita?id=' + idLocalita,
			cache : true,
			async: true
		});
		
	},


	/**
	 * Il metodo effettua l'inserimento e/o la modifica di un accesso
	 * @param accesso, è l'oggetto che contiene tutte le informazioni neccessarie 
	 */
	inserisciAccesso: function(accesso) {
		
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/insertOrUpdateAccesso',
			data : JSON.stringify(accesso),
			cache : true,
			async: true
		});
		
	},
	
	/** Il metodo effetta l'eliminazione dell'accesso
	 *  @param idAccesso, indentifica l'accesso
	 */
	eliminaAccesso: function(idAccesso) {
		
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/cancellaAccesso?id=' + idAccesso,
			cache : true,
			async: true
		});
		
	},
	
	/** Il metodo effetta l'eliminazione dell'accesso in base all'indentificativo del toponimo stradale
	 *  @param idAccesso, indentifica la località
	 *  @param idToponimo, indentifica il toponimo stradale
	 */
	eliminaAccessoByToponimo: function(idAccesso, idToponimo) {
		
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/cancellaAccessoByToponimo?id=' + idAccesso + "&toponimo=" + idToponimo,
			cache : true,
			async: true
		});
		
	},

	/** Il metodo effetta l'eliminazione dell'accesso in base all'indentificativo della località
	 *  @param idAccesso, indentifica la località
	 *  @param idLocalita, indentifica il toponimo stradale
	 */
	eliminaAccessoByLocalita: function(idAccesso, idLocalita) {
		
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/cancellaAccessoByLocalita?id=' + idAccesso + "&localita=" + idLocalita,
			cache : true,
			async: true
		});
		
	},

	/**
	 * Il metodo effettua l'inserimento e/o la modifica di un percorso
	 * @param percorso, è l'oggetto che contiene tutte le informazioni neccessarie 
	 */
	inserisciPercorso: function(percorso) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/insertOrUpdatePercorso',
			data : JSON.stringify(percorso),
			cache : true,
			async: true
		});
	},

	/** Il metodo effetta l'eliminazione del percorso in base all'id
	 *  @param idPercorso, indentifica il percorso
	 */
	eliminaPercorso: function(idPercorso) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/cancellaPercorso?id=' + idPercorso,
			cache : true,
			async: true
		});
	},

	/**
	 * Il metodo effettua l'inserimento e/o la modifica di un'estesa amministrativa
	 * @param estesaAmministrativa, è l'oggetto che contiene tutte le informazioni neccessarie 
	 */
	inserisciEstesaAmministrativa: function(estesaAmministrativa) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/insertOrUpdateEstesa',
			data : JSON.stringify(estesaAmministrativa),
			cache : true,
			async: true
		});
	},

	findSiglaById: function(id) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/findSiglaEstesaById?id=' + id,
			cache : true,
			async: true
		});
	},
	
	/** Il metodo effetta l'eliminazione del percorso in base all'id
	 *  @param idPercorso, indentifica il percorso
	 */
	eliminaEstesaAmministrativa: function(idEstesa) {
	
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/cancellaEstesaAmministrativa?id=' + idEstesa,
			cache : true,
			async: true
		});
	
	},

	/**
	 * Il metodo effettua l'inserimento e/o la modifica di un cippo
	 * @param cippo, è l'oggetto che contiene tutte le informazioni neccessarie 
	 */
	inserisciCippo: function(cippo) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/insertOrUpdateCippo',
			data : JSON.stringify(cippo),
			cache : true,
			async: true
		});
	},
	
	/** Il metodo effettua l'eliminazione del cippo in base all'id
	 *  @param idCippo, indentifica il cippo chilometrico
	 */
	eliminaCippo: function(idCippo) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/cancellaCippo?id=' + idCippo,
			cache : true,
			async: true
		});
	},
	
	/**
	 * Il metodo prende tutti i figli di un determinato toponimo, passandogli l'id del toponimo padre
	 * @param id, indentifica il toponimo padre 
	 */
	getToponimoFigli: function(id) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/getToponimoFigli?idPadre=' + id,
			cache : true,
			async: true
		});
	},

	inserisciGiunzione: function(giunzioneStradale) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/insertOrUpdateGiunzione',
			data : JSON.stringify(giunzioneStradale),
			cache : true,
			async: true
		});
	},

	eliminaGiunzione: function(id) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/cancellaGiunzione?id=' + id,
			cache : true,
			async: true
		});
	},

	intersecaGiunzione: function(geomWkt) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/findIntersections?geom='+geomWkt,
//			data : JSON.stringify(giunzioneStradale),
			cache : true,
			async: true
		});
	},

	
}