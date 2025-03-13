/*******************************************************************************************************************************************************/
/***************************************			FUNZIONALITA' REST				********************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

/**
 * @include util.js
 */

appRest = {
		
		/**
		 * Chiamata rest per il recupero di informazioni da geoserver
		 * @param href
		 * @returns
		 */
		restGeoserver : function ( href ){
			responseRestGeoserver = "";
			$.ajax({
				type 		: "GET",
				dataType	: "json",
				contentType	: "application/json",
				url 		: appUtil.replaceNameSpace(href),
				cache 		: false,
				async		: false,
				timeout		: 1000,
				success		: function(response) {
					responseRestGeoserver = response;
				},
				error		: function(response){
					response.message = "Errore nel recuperare le informazioni su mappa";
					responseRestGeoserver = response;
				}
			});
			return responseRestGeoserver;
		},
		
		/**
		 * Chiamata rest per il recupero di informazioni da geoserver
		 * 
		 * @param href
		 * @returns
		 */
		restSearchGeoserver :function ( ricerca ) {
			//CREO LA RICHIESTA
			var featureRequest = new ol.format.WFS().writeGetFeature({
		        srsName			: 'EPSG:4326',
		        featurePrefix	: 'geoserver',
				featureTypes	: ricerca.featureTypes,
		        outputFormat	: ricerca.outputFormat, 
		        filter			: ricerca.defFilter
			});
			fetch( ricerca.urlSearch, {
		          method: 'POST',
		          body: new XMLSerializer().serializeToString( featureRequest )
			}).then(function(response) {
		          return response.json();
			}).then(function(json) {
				features = new ol.format.GeoJSON().readFeatures(json);
				return features;
			});
		},
		
		/**
		 * Chiamata rest per il recupero di informazioni da geoserver
		 * @param href
		 * @returns
		 */
		restLegendGeoserver : function ( href ){
			responseRestGeoserver = "";
			$.ajax({
				type 		: "GET",
				contentType	: "image/png",
				url 		: appUtil.replaceNameSpace(href),
				cache 		: false,
				async		: false,
				timeout		: 1000,
				success		: function(response) {
					responseRestGeoserver = response;
				},
				error		: function(response){
					response.message = "Errore nel recuperare le informazioni su mappa";
					responseRestGeoserver = response;
				}
			});
			return responseRestGeoserver;
		},
		
		
		/**
		 * Chiamata rest per il recupero di informazioni da Urbamid
		 * @param href
		 * @returns
		 */
		restUrbamid : function ( href, type, data ){
			responseRestUrbamid = "";
			$.ajax({
				type 		: type,
				dataType	: "json",
				contentType	: "application/json",
				url 		: appUtil.replaceUrbamidNameSpace(href),
				data		: data,
				cache 		: false,
				async		: false,
				timeout		: 1000,
				success		: function(response) {
					responseRestUrbamid = response;
				},
				error		: function(response){
					response.message = "Errore nel recuperare le informazioni su mappa";
					responseRestUrbamid = response;
				}
			});
			return responseRestUrbamid;
		},
		
		/**
		 * Chiamata rest per il recupero di informazioni da Urbamid
		 * @param href
		 * @returns
		 */
		restPostUrbamid : function ( href, type, dataPost ){
			responseRestUrbamid = "";
			$.ajax({
				type : "POST",
				processData : false,
			    contentType: false,
				enctype : 'application/x-www-form-urlencoded',
				url : href,
				data : dataPost,
				cache : false,
				async: false,
			    success: function( data, textStatus, jQxhr ){
			    	responseRestUrbamid = JSON.stringify( data );
			    },
			    error: function( jqXhr, textStatus, errorThrown ){
			        console.log( errorThrown );
			    }
			});
			return responseRestUrbamid;
		},
		
		restPost : function ( href, type, dataPost ){
			responseRestUrbamid = "";
			$.ajax({
				type : "POST",
				processData : false,
			    contentType: 'application/json',
//				enctype : 'application/json',
				url : href,
				data : dataPost,
				cache : false,
				async: false,
			    success: function( data, textStatus, jQxhr ){
			    	responseRestUrbamid = JSON.stringify( data );
			    },
			    error: function( jqXhr, textStatus, errorThrown ){
			        console.log( errorThrown );
			    }
			});
			return responseRestUrbamid;
		},
		
		restSimplePost : function (href, object){
			var responseSimplePost = null;
			$.ajax({
				type : "POST",
				processData : false,
				dataType : 'json',
				contentType : 'application/json; charset=UTF-8',
				url : href,
				data : JSON.stringify(object),
				cache : true,
				async: false,
				success		: function(response) {
					responseSimplePost = response;
				},
				error		: function(response){
					responseSimplePost = response;
				}
			});
			return responseSimplePost;
		}
}