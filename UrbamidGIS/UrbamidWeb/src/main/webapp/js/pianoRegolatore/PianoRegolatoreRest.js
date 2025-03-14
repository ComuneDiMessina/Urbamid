/**
 * @include util.js
 */

pianoRegolatoreRest = {

		recuperaListaAreeTematiche : function(hrefListaAreeTematiche) {
			return $.ajax({
				type 		: "GET",
				dataType	: "json",
				contentType	: "application/json",
				url 		: appUtil.replaceNameSpace(hrefListaAreeTematiche),
				cache 		: true,
				timeout		: 10000,
			});
		},

		recuperaDettaglioAreaTematica : function(areaTematica) {
			let href = appUtil.replaceNameSpace(areaTematica.href);
			return $.ajax({
				type 		: "GET",
				dataType	: "json",
				contentType	: "application/json",
				url 		: appUtil.replaceNameSpace(href),
				cache 		: true,
				timeout		: 10000,
			});
		},

		ricercaVarianti : function(ente, nome, descrizione, dataAdozioneDal, dataAdozioneAl, dataApprovazioneDal, dataApporvazioneAl) {
			var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/ricercaVarianti?';
			if (ente !== null && ente !== undefined && ente !== '') 
				url += '&ente=' + ente;
			if (nome !== null && nome !== undefined && nome !== '') 
				url += '&nome=' + nome;
			if (descrizione !== null && descrizione !== undefined && descrizione !== '') 
				url += '&descrizione=' + descrizione;
			if (dataAdozioneDal !== null && dataAdozioneDal !== undefined && dataAdozioneDal !== '') 
				url += '&dataAdozioneDal=' + dataAdozioneDal;
			if (dataAdozioneAl !== null && dataAdozioneAl !== undefined && dataAdozioneAl !== '') 
				url += '&dataAdozioneAl=' + dataAdozioneAl;
			if (dataApprovazioneDal !== null && dataApprovazioneDal !== undefined && dataApprovazioneDal !== '') 
				url += '&dataApprovazioneDal=' + dataApprovazioneDal;
			if (dataApporvazioneAl !== null && dataApporvazioneAl !== undefined && dataApporvazioneAl !== '') 
				url += '&dataApporvazioneAl=' + dataApporvazioneAl;
			return url;
		},

		creaOrSalvaVariante : function(variante) {
			return $.ajax({
				type : "POST",
				processData : false,
				dataType : 'json',
				contentType : 'application/json; charset=UTF-8',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/creaOrSalvaVariante',
				data : JSON.stringify(variante),
				cache : true,
				async: true
			});
		},

		cancellaVariante : function(idVariante) {
			return $.ajax({
				type : "GET",
				processData : false,
				dataType : 'json',
				contentType : 'application/json; charset=UTF-8',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cancellaVariante?idVariante=' + idVariante,
				cache : true,
				async: true
			});
		},

		ricercaTipoDocumenti : function(codice, descrizione, descrizioneCDU, note) {

			var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/ricercaTipoDocumenti?';

			if(codice !== null && codice !== undefined && codice !== '')
				url += "&codice=" + codice;
			if(descrizione !== null && descrizione !== undefined && descrizione !== '')
				url += "&descrizione=" + descrizione;
			if(descrizioneCDU !== null && descrizioneCDU !== undefined && descrizioneCDU !== '') 
				url += "&descrizioneCDU=" + descrizioneCDU;
			if(note !== null && note !== undefined && note !== '')
				url += "&note=" + note;

			return url;

		},
		
		recupera: function() {
			return $.ajax({
				type : "GET",
				processData : false,
				dataType : 'json',
				contentType : 'application/json; charset=UTF-8',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/findAllTipoDocumento',
				cache : true,
				async: true
			});
		},

		varianteByTipoDocumento: function(tipoDocumento) {
			return $.ajax({
				type: 'GET',
				processData : false,
				dataType : 'json',
				contentType : 'application/json; charset=UTF-8',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/varianteByTipoDocumento?tipoDocumento='+tipoDocumento,
				cache : true,
				async: true
			});

		},
		
		insertOrUpdateTipoDocumento: function (tipoDocumento) {
			return $.ajax({
				type : "POST",
				processData : false,
				dataType : 'json',
				contentType : 'application/json; charset=UTF-8',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/insertOrUpdateTipoDocumento',
				data : JSON.stringify(tipoDocumento),
				cache : true,
				async: true
			});
		},

		cancellaTipoDocumento : function(idTipoDocumento) {
			return $.ajax({
				type: 'GET',
				processData: false,
				dataType: 'json',
				contentType : 'application/json; charset=UTF-8',
				url: appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cancellaTipoDocumento?id=' + idTipoDocumento,
				cache : true,
				async: true
			});
		},

	cercaDocumentiByIdVariante : function(idVariante) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaDocumentiByIdVariante?idVariante=' + idVariante,
			cache : true,
			async: true
		});
	},

	creaOrSalvaDocumento : function(documento) {
		return $.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
	        processData: false,
	        contentType: false,
	        cache: false,
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/creaOrSalvaDocumento',
			data : documento,
		});
	},

	cancellaDocumento : function(idDocumento) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cancellaDocumento?idDocumento=' + idDocumento,
			cache : true,
			async: true
		});
	},

	downloadDocumento : function(idDocumento) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/downloadDocumento?idDocumento=' + idDocumento;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	salvaAmbitoVarianteTracciato : function(data) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaAmbitoVarianteTracciato',
			data : JSON.stringify(data),
			cache : true,
			async: true
		});
	},

	salvaAmbitoVarianteSelezione : function(data) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaAmbitoVarianteSelezione',
			data : JSON.stringify(data),
			cache : true,
			async: true
		});
	},

	salvaAmbitoVarianteSelezioneLayer : function(data) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaAmbitoVarianteSelezioneLayer',
			data : JSON.stringify(data),
			cache : true,
			async: true
		});
	},

	cercaCartografieByIdVariante : function(idVariante) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaCartografieByIdVariante?idVariante=' + idVariante,
			cache : true,
			async: true
		});
	},

	salvaCartografia : function(cartografia) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaCartografia',
			data : JSON.stringify(cartografia),
			cache : true,
			async: true
		});
	},

	salvaAmbitoVarianteRicerca : function(data) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaAmbitoVarianteRicerca',
			data : JSON.stringify(data),
			cache : true,
			async: true
		});
	},

	cercaTipiDocumentoMancanti : function(idVariante) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaTipiDocumentoMancanti?idVariante=' + idVariante,
			cache : true,
			async: true
		});
	},

	cercaIndiciByIdDocumento : function(idDocumento) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaIndiciByIdDocumento?idDocumento=' + idDocumento,
			cache : true,
			async: true
		});
	},

	cercaCodiciByIdIndice : function(idIndice) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaCodiciByIdIndice?idIndice=' + idIndice,
			cache : true,
			async: true
		});
	},

	salvaIndice : function(indice) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaIndice',
			data : JSON.stringify(indice),
			cache : true,
			async: true
		});
	},

	cancellaIndice : function(idIndice) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cancellaIndice?idIndice=' + idIndice,
			cache : true,
			async: true
		});
	},

	importaIndice : function(indice) {
		return $.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
	        processData: false,
	        contentType: false,
	        cache: false,
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/importaIndice',
			data : indice,
		});
	},

	esportaIndice : function(idDocumento) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/esportaIndice?idDocumento=' + idDocumento;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	cercaAmbitoByIdVariante : function(idVariante) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaAmbitoByIdVariante?idVariante=' + idVariante,
			cache : true,
			async: true
		});
	},

	cercaCodiciZto : function() {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaCodiciZto',
			cache : true,
			async: true
		});
	},

	salvaCodici : function(codici) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaCodici',
			data : JSON.stringify(codici),
			cache : true,
			async: true
		});
	},

	salvaNuovoGruppo : function(nomeGruppo) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaNuovoGruppo?nomeGruppo=' + nomeGruppo,
			cache : true,
			async: true
		});
	},

	reperimentoCatalogoVariante : function() {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/reperimentoCatalogoVariante',
			cache : true,
			async: true
		});
	},

	salvaLayer : function(layer) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/salvaLayer',
			data : JSON.stringify(layer),
			cache : true,
			async: true
		});
	},

	cancellaLayer : function(idLayer) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cancellaLayer?idLayer=' + idLayer,
			cache : true,
			async: true
		});
	},

	cancellaGruppo : function(idGruppo) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cancellaGruppo?idGruppo=' + idGruppo,
			cache : true,
			async: true
		});
	},

	varianteByNomeLayer : function(nomeLayer) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/varianteByNomeLayer?nomeLayer=' + nomeLayer,
			cache : true,
			async: true
		});
	},

	downloadCdu : function(dto) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			//url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/downloadCdu',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgConsultazioneController/downloadCdu',
			data : JSON.stringify(dto),
			cache : true,
			async: true
		});
	},
	
	downloadCduByProtocollo : function(protocollo) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgConsultazioneController/downloadCduByProtocollo?protocollo=' + protocollo,
			cache : true,
			async: true
		});
	},
	
	cancellaCartografia : function(cartografia) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cancellaCartografia',
			data : JSON.stringify(cartografia),
			cache : true,
			async: true
		});
	},

	cercaProtocollo : function(protocollo) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			//url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaProtocollo?protocollo=' + protocollo,
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgConsultazioneController/cercaProtocollo?protocollo=' + protocollo,
			cache : true,
			async: true
		});
	},

	ricercaCdu : function(protocollo, dataCreazione) {
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/cercaCdu?';
		if (protocollo !== null && protocollo !== undefined && protocollo !== '') 
			url += '&protocollo=' + protocollo;
		if (dataCreazione !== null && dataCreazione !== undefined && dataCreazione !== '') 
			url += '&dataCreazione=' + dataCreazione;
		return url;
	},

	downloadCduAnagrafica : function(protocollo) {
		//var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/downloadCduAnagrafica?protocollo=' + protocollo;
		var url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgConsultazioneController/downloadCduAnagrafica?protocollo=' + protocollo;
		return $.ajax({
			type : "GET",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : url,
			cache : true,
			async: true
		});
	},

	reperimentoColonneLayer : function(nomeLayer) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/prgController/reperimentoColonneLayer?nomeLayer=' + nomeLayer,
			cache : true,
			async: true
		});
	}

}