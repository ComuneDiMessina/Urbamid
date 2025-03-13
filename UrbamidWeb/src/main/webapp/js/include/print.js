/*******************************************************************************************************************************************************/
/***************************************			FUNZIONALITA' URBAMID				****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

/**
 * @include /plugin/jsTree-3.3.8
 * @include catalogo.js
 * @include configuration.js
 * @include util.js
 * @include rest.js
 * @include mappa.js
 */

$(document).ready(function() {
	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	/**Quando ho recuperato le voci del menu di 2° e 3° Livello**/
	$.when( appUrbamid.start() ).then(function( x ) {
		appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	});
});


appUrbamid = {
		
	authority						: "",
	codeMappa						: "",
	centerMap						: [],
	zoom							: "",
	heigth							: "",
	width							: "",
	
	/**
	 * @param
	 * @return
	 */
	start : function (){
		
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		
		urlParams = appConfig.getUrlVars();
		if (urlParams!=null && urlParams["data"]!=null) {
		
			/**RECUPERO OGGETTO CON INFORMAZIONI SU COME COSTRUIRE LA MAPPA DA STAMPARE**/
			jsonString 				= atob( urlParams["data"] );
			appUrbamid.printGis		= jsonString!="" ? JSON.parse( jsonString ) : null;
			
			/**parametri di base**/
			appUrbamid.authority 	= urlParams["authority"];
			appUrbamid.codeMappa 	= appUrbamid.printGis.codeMappa;
			appUrbamid.centerMap	= (appUrbamid.printGis).center;
			appUrbamid.zoom 		= (appUrbamid.printGis).zoom;
			appUrbamid.heigth		= 565;
			appUrbamid.width		= 1045;
			
			/**CREO LA MAPPA**/
			tools = {};
			appMappa.createMappa(tools);
					
			/**AGGIUNGO TUTTI I LAYER COMPRESI QUELLI DI BASE**/
			$.each(((appUrbamid.printGis).layers), function(key, layer) {
			
				/**PARAMETRI DI BASE DEL LAYER DA AGGIUNGERE**/
				layerName = layer.layerName;
				layerOpacity = layer.layerOpacity;
				layerZIndex = layer.layerZIndex;
				
				/**AGGIUNGO IL LAYER ALLA MAPPA**/
				if (layerName.indexOf("ortofoto")!=-1){
				
					(appMappa.maps).addLayer( (appMappa.layerOrtofoto) );
				} else if(layerName.indexOf("layer")!=-1) {
				
					(appMappa.maps).addLayer( (appMappa.layer) );
				} else {
				
					var source = new ol.source.TileWMS({
							serverType	: "geoserver",
							params		: {"service":"WMS", "layers":appConfig.workspaceGeoserver+":"+layerName},
							url			: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceGeoserver+"/wms",
					        projection	: appConfig.mapProjection,
					        crossOrigin	: 'Anonymous'
						});
						var tileWms = new ol.layer.Tile({
							opacity		: layerOpacity,
					        source		: source,
					        visible		: true,
					        zIndex		: layerZIndex, 
					        name		: layerName,
							title		: layerName
					    });
						(appMappa.maps).addLayer(tileWms);
				} 
			});
				
			$(".justify-content-end").hide();
			$(".ol-overlaycontainer-stopevent").hide();
		
			var centerSource = new ol.source.Vector({});
			var centerStyle = new ol.style.Style({
				fill: new ol.style.Fill({
					color: 'rgba(143,153,21,0.7)'
				})
			});
			centerLayer = new ol.layer.Vector({
				source: centerSource,
				style: 	centerStyle
			});
			(appMappa.maps).addLayer(centerLayer);
			var point = new ol.geom.Point(appUrbamid.centerMap);
		    var iconFeature = new ol.Feature({geometry: point});
		    centerSource.addFeature(iconFeature);
			(appMappa.maps).getView().fit(centerSource.getExtent(), (appMappa.maps).getSize());	
			(appMappa.maps).getView().setZoom(appUrbamid.zoom);
		}
	},
	
	print: function(){
	
		var p = new Promise(function(resolve, reject) {
			html2canvas(document.getElementById("map"), {useCORS: true}).then(function(canvas) {
				resolve(canvas);
			});
		});
		p.then(function(canvas) {
			
			if (window.navigator.msSaveBlob) { 				//IE
				
				var blob = canvas.msToBlob();
                window.navigator.msSaveBlob(blob, (grafico.titolo).replace(/[^A-Za-z0-9]/g, '_').toLowerCase()+ ".png");
			} else {										//NOT IE
			
				appUtil.showLoader();
				let formato = $("#lstFormati").val();
				
				var dataUrl = canvas.toDataURL("image/jpeg");
				var blob = appUrbamid.dataURItoBlob(dataUrl);
				
				let url = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService
				url += "printController/printMap?formato="+formato;
				var formData = new FormData();
				formData.append("file", blob);
				$.ajax({
				
					url: url,
					type: 'POST',
			        data: formData,
			        enctype: 'multipart/form-data',
			        contentType: false,
		            processData: false,
		            cache: false,
				success: function (response) {
				
						appUtil.hideLoader();
						if (response !== null || response !== undefined) {
							const linkSource = 'data:application/octet-stream;base64,'+response;
						    const downloadLink = document.createElement('a');
						    const fileName = "export.pdf";
						    downloadLink.href = linkSource;
						    downloadLink.download = fileName;
						    downloadLink.click();
						} else {
							iziToast.show({
								balloon: false,
								icon:'fa fa-info-circle', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
							    title: 'Errore',
							    message: 'Si è verificato un errore durante la fase di export',
							});
						}
					},
					error: function (e) {
						console.log("ERROR : ", e);
					}
				});
			}
		});
	},
	
	dataURItoBlob : function (dataURI) {
	    var byteString = atob(dataURI.split(',')[1]);
	
	    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]
	
	    var ab = new ArrayBuffer(byteString.length);
	    var ia = new Uint8Array(ab);
	    for (var i = 0; i < byteString.length; i++)
	    {
	        ia[i] = byteString.charCodeAt(i);
	    }
	
	    var bb = new Blob([ab], { "type": mimeString });
	    return bb;
	},
	
	changeFormat: function(value){
		format = $("#lstFormati").val();
		if (format.indexOf("a4v")!=-1){
			$("#map").css('width', '716px');
		    $("#map").css('height', '641px');
		    (appMappa.maps).updateSize();
		} else if (format.indexOf("a4h")!=-1) {
			$("#map").css('width', '1045px');
		    $("#map").css('height', '565px');
		    (appMappa.maps).updateSize();
		} else if (format.indexOf("a3")!=-1) {
			$("#map").css('width', '1396px');
		    $("#map").css('height', '641px');
		    (appMappa.maps).updateSize();
		}
	},
	
	changeScale: function(value){
		var zoom = (appMappa.maps).getView().getZoom();
		scale = $("#lstScales").val();
		if (scale.indexOf("20000000")!=-1) {
			(appMappa.maps).getView().setZoom(6);
        } else if (scale.indexOf("10000000")!=-1) {
        	(appMappa.maps).getView().setZoom(7);
        } else if (scale.indexOf("5000000")!=-1) {
        	(appMappa.maps).getView().setZoom(8);
        } else if (scale.indexOf("2000000")!=-1) {
        	(appMappa.maps).getView().setZoom(9);
        } else if (scale.indexOf("1000000")!=-1) {
        	(appMappa.maps).getView().setZoom(10);
        } else if (scale.indexOf("500000")!=-1) {
        	(appMappa.maps).getView().setZoom(11);
        } else if (scale.indexOf("200000")!=-1) {
        	(appMappa.maps).getView().setZoom(12);
        } else if (scale.indexOf("100000")!=-1) {
        	(appMappa.maps).getView().setZoom(13);
        } else if (scale.indexOf("50000")!=-1) {
        	(appMappa.maps).getView().setZoom(14);
        } else if (scale.indexOf("20000")!=-1) {
        	(appMappa.maps).getView().setZoom(15);
        } else if (scale.indexOf("10000")!=-1) {
        	(appMappa.maps).getView().setZoom(16);
        } else if (scale.indexOf("5000")!=-1) {
        	(appMappa.maps).getView().setZoom(17);
        } else if (scale.indexOf("2000")!=-1) {
        	(appMappa.maps).getView().setZoom(18);
        } else if (scale.indexOf("1000")!=-1) {
        	(appMappa.maps).getView().setZoom(19);
        } else if (scale.indexOf("500")!=-1) {
        	(appMappa.maps).getView().setZoom(20);
        }
	}
}