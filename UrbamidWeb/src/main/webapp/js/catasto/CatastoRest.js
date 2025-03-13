/**
 * @include util.js
 */

catastoRest = {

	popolaLsCategorieCatastali : function() {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/categorieCatastali',
			cache : true,
			async: true
		});
	},

	popolaLsCodiciDiritto : function() {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/codiciDiritto',
			cache : true,
			async: true
		});
	},

	popolaLsCodiciQualita : function() {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/codiciQualita',
			cache : true,
			async: true
		});
	},

	ricercaSuParticellePT : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/ricercaSuParticellePT',
			data : JSON.stringify(particelle),
			cache : true,
			async: true
		});
	},

	ricercaSuParticelleUI : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/ricercaSuParticelleUI',
			data : JSON.stringify(particelle),
			cache : true,
			async: true
		});
	},
	
	batchStatus : function(){
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/getExecutionJob',
			data : null,
			cache : false,
			async: true
		});
	},
	
	ricercaPersoneFisiche : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/ricercaPersoneFisiche',
			data : JSON.stringify(particelle),
			cache : true,
			async: true
		});
	},

	ricercaSoggettiGiuridici : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/ricercaSoggettiGiuridici',
			data : JSON.stringify(particelle),
			cache : true,
			async: true
		});
	},

	ricercaListaIntestatari : function(idImmobile) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/ricercaListaIntestatari?idImmobile=' + idImmobile,
			cache : true,
			async: true
		});
	},

	ricercaByFoglioMappale : function(foglio, mappale) {
		return $.ajax({
			type : "POST",
			processData : false,
		    contentType: false,
			enctype : 'application/x-www-form-urlencoded',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/findParticella?numFoglio=' + foglio + '&mappale=' + mappale,
			cache : false,
			async: false
		});
	},

	exportXls : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportXls',
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportSoggettiXls : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportSoggettiXls',
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportTerreniXls : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportTerreniXls',
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportUiuXls : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportUiuXls',
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportIntestazioniXls : function(particelle) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportIntestazioniXls',
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportPdf : function(particelle, titolo, tipologiaEstrazione) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportPdf?titolo=' + titolo + '&tipologiaEstrazione=' + tipologiaEstrazione,
			data : particelle,
			cache : true,
			async: true
		});
	},
	
	exportShape : function(particelle, titolo) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportShape?titolo=' + titolo,
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportFabbricatiPerNominativo : function(particelle, titolo, tipologiaEstrazione) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportFabbricatiPerNominativo?titolo=' + titolo+ '&tipologiaEstrazione=' + tipologiaEstrazione,
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportTerreniPerNominativo : function(particelle, titolo, tipologiaEstrazione) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportTerreniPerNominativo?titolo=' + titolo+ '&tipologiaEstrazione=' + tipologiaEstrazione,
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportFabbricatiPerParticella : function(particelle, titolo, tipologiaEstrazione) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportFabbricatiPerParticella?titolo=' + titolo+ '&tipologiaEstrazione=' + tipologiaEstrazione,
			data : particelle,
			cache : true,
			async: true
		});
	},

	exportTerreniPerParticella : function(particelle, titolo, tipologiaEstrazione) {
		return $.ajax({
			type : "POST",
			processData : false,
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + '/UrbamidWeb/catastoController/exportTerreniPerParticella?titolo=' + titolo+ '&tipologiaEstrazione=' + tipologiaEstrazione,
			data : particelle,
			cache : true,
			async: true
		});
	}

}