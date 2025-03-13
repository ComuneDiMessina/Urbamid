/**
 * @include util.js
 */

editingLayerRest = {

	visualizzaGeometrie : function(idLayer) {
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'adminController/findAllGeometry?idLayer=' + idLayer,
			cache : true,
			async: false
		})
	},

	countLayerByIdentificativo : function(identificativo) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'adminController/countLayerByIdentificativo?identificativo=' + identificativo,
			cache : true,
			async: true
		})
	},

    inserisciLayer : function(layers) {
        return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'adminController/insertOrUpdate',
			data : JSON.stringify(layers),
			cache : true,
			async: true
		})
	},

	eliminaLayer : function(layers) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'adminController/eliminaLayer',
			data : JSON.stringify(layers), 
			cache : true,
			async: true
		})
	},

	eliminaGeometria : function(idGeometria) {
		return $.ajax({
			type : "POST",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'adminController/eliminaGeometria?idGeometria=' + idGeometria,
			cache : true,
			async: true
		})
	}, 

}