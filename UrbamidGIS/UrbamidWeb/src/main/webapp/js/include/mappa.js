/*******************************************************************************************************************************************************/
/***************************************			FUNZIONALITA' MAPPA					****************************************************************/
/*******************************************************************************************************************************************************/
/*******************************************************************************************************************************************************/

/**
 * @include /plugin/ol-v4.0.1
 * @include util.js						FppUtil, 	viene utilizzato per la gestione delle url 
 * @include configuration.js			appConfig, 	viene utilizzato per i parametri di configurazione come geoserverService, restService
 * @include rest.js						appRest, 	viene utilizzato per le chiamate rest
 */

/**
 * Oggetto image
 */
image = new ol.style.Circle({
				radius: 5,
				fill: null,
				stroke: new ol.style.Stroke({color: 'red', width: 1})
});

/**
 * Oggetto degli stili
 */
appMappaStyle = {
		'Point': new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
				anchor: [0.5, 46],
				anchorXUnits: 'fraction',
				anchorYUnits: 'pixels',
				opacity: 1,
				src: 'images/marker/marker_staz.png'
			}))
	    }),
	    'LineString': new ol.style.Style({
	    	stroke: new ol.style.Stroke({
	    		color: 'rgb(33, 150, 243)',
	    		width: 3
	    	})
	    }),
	    'MultiLineString': new ol.style.Style({
	    	stroke: new ol.style.Stroke({
	    		color: 'rgb(33, 150, 243)',
	    		width: 3
	    	})
	    }),
	    'MultiPoint': new ol.style.Style({
	    	image: image
	    }),
	    'MultiPolygon': new ol.style.Style({
	    	stroke: new ol.style.Stroke({
	    		color: 'yellow',
	    		width: 1
	    	}),
	    	fill: new ol.style.Fill({
	    		color: 'rgba(255, 255, 0, 0.1)'
	    	})
	    }),
	    'Polygon': new ol.style.Style({
	    	stroke: new ol.style.Stroke({
		        color: 'blue',
		        lineDash: [4],
		        width: 3
	    	}),
	    	fill: new ol.style.Fill({
		        color: 'rgba(0, 0, 255, 0.1)'
	    	})
	    }),
	    'GeometryCollection': new ol.style.Style({
	    	stroke: new ol.style.Stroke({
	    		color: 'magenta',
	    		width: 2
	    	}),
	    	fill: new ol.style.Fill({
	    		color: 'magenta'
	    	}),
	    	image: new ol.style.Circle({
	    		radius: 10,
	    		fill: null,
	    		stroke: new ol.style.Stroke({
	    			color: 'magenta'
	    		})
	    	})
	    }),
	    'Circle': new ol.style.Style({
	    	stroke: new ol.style.Stroke({
	    		color: 'red',
	    		width: 2
	    	}),
	    	fill: new ol.style.Fill({
	    		color: 'rgba(255,0,0,0.2)'
	    	})
	    }),

	    /**
		 * TODO: inserisci commento
	     * @param feature
	     * @returns
	     */
		styleFunction : function(feature) {
			return styles[feature.getGeometry().getType()];
		},
		
		/**
		 * TODO: inserisci commento
		 */
		styleDraw : new ol.style.Style({
			fill: new ol.style.Fill({
				color: 'rgba(143,153,21,0.7)'
			}),
			stroke:new ol.style.Stroke({
				color: '#ff00009e',
				width: 5
			}),
			image: new ol.style.Circle({radius: 7,
		        /**Centro del punto**/
		        fill: new ol.style.Fill({color: 'rgba(200,0,0,1)',width: 6}),
		        /**Bordo del punto**/
		        stroke: new ol.style.Stroke({color: 'rgb(255,255,255)',width: 1})
		    })
		}),
		
		/**
		 * TODO: inserisci commento
		 */
		stylePerimetro : new ol.style.Style({
			fill: new ol.style.Fill({
				color: 'rgba(255,255,255,0)'
			}),
			stroke:new ol.style.Stroke({
				color: '#ff00009e',
				width: 5
			})
		}),
		
		/**
		 * TODO: inserisci commento
		 */
		stylePoint : new ol.style.Style({
			image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
				anchor: [0.5, 46],
				anchorXUnits: 'fraction',
				anchorYUnits: 'pixels',
				opacity: 1,
				src: 'images/marker/marker_r.png'
			}))
		}),
		
		/**
		 * 
		 * @param cluColor, colore del cluster
		 * @returns {ol.style.Style}
		 */
		getStyleCluster : function ( cluColor, size ) {
			styleCluster = new ol.style.Style({
				image: new ol.style.Circle({
					radius: 10,
					stroke: new ol.style.Stroke({
						color: '#000'
					}),
					fill: new ol.style.Fill({
						color: cluColor
					})
				}),
				text: new ol.style.Text({
					text: size.toString(),
					fill: new ol.style.Fill({
						color: '#000'
					})
				})
			});
			return styleCluster;
		}
}

drawLayerSource 					= new ol.source.Vector({wrapX: false});
editingLayerSource 					= new ol.source.Vector({wrapX: false});
drawLayerSourcePerimetro			= new ol.source.Vector({wrapX: false});

selectLayerSource 					= new ol.source.Vector({wrapX: false});

const dims = {
  a0: [1189, 841],
  a1: [841, 594],
  a2: [594, 420],
  a3: [420, 297],
  a4: [297, 210],
  a5: [210, 148],
};

// export options for html2canvase.
// See: https://html2canvas.hertzen.com/configuration
const exportOptions = {
  useCORS: true,
  ignoreElements: function (element) {
    const className = element.className || '';
    return !(
      className.indexOf('ol-control') === -1 ||
      className.indexOf('ol-scale') > -1 ||
      (className.indexOf('ol-attribution') > -1 &&
        className.indexOf('ol-uncollapsible'))
    );
  },
};
const scaleLine = new ol.control.ScaleLine({bar: true, text: true, minWidth: 125});
/**
 * Oggetto mappa
 */
appMappa = {
		maps								: null,

		layerBaseItem						: {"enabled":false,"layerName":"layer","title":"Layer base","baseLayer":true},
		layer		 						: new ol.layer.Tile({
												name		: "layer",
												isBaseLayer : true,
												idParent	: 'DATI DI BASE', 
												source 		: new ol.source.XYZ({
																attributions: 'Tiles © <a href="http://services.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer">ArcGIS</a>',
																url: 'http://services.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}'
												}),
												crossOrigin: "Anonymous"
								  			}),											
								  			  
		layerOrtofotoItem					: {"enabled":true,"layerName":"ortofoto","title":"Ortofoto","baseLayer":true},
		layerOrtofoto 						: new ol.layer.Tile({
												name		: "ortofoto",
												isBaseLayer : true,
												idParent	: 'DATI DI BASE', 
												source 		: new ol.source.XYZ({
																attributions: 'Tiles © <a href="http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer">ArcGIS</a>',
																url: 'http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}'
												}),
												crossOrigin: "Anonymous"
								  			}),
		  			
								  			
        drawLayer 							: new ol.layer.Vector({
            									source: drawLayerSource,
            									style: 	appMappaStyle.styleDraw
        						  			}),
        editingLayer						: new ol.layer.Vector({
												source: editingLayerSource,
												style: 	appMappaStyle.stylePoint
											}),
	    drawLayerPerimetro 					: new ol.layer.Vector({
	    										source: drawLayerSourcePerimetro,
	    										style: appMappaStyle.stylePerimetro
	    						  			}),
	    view								: new ol.View({
												center		: appConfig.mapCenter,
												zoom		: appConfig.mapZoom,
												projection	: appConfig.mapProjection
	    						  			}),
	    						  			
	    selectLayer							: new ol.layer.Vector({
            									source: selectLayerSource,
            									style: 	appMappaStyle.styleDraw
        						  			}),	    						  			
	    events								: new Object(),
	    prop 								: {
												mappaId   				:   'map',
												targetMap   			:   'map',
												enableLayersTree    	:    false,
												enableFullscreen    	:    false,
												enable3D    			:    false,
												enableAttribution    	:    false,
												enableRotation    		:    true,
												enableGeolocation    	:    false,
												enableScaleLine    		:    true,
												enableMeasureLength    	:    false,
												enableMeasureArea    	:    false,
												enableMousePosition    	:    true,
												center    				:    [15.549679, 38.191324],
												zoom    				:    7
	    									},
		layerFieldsCaricati					: new Object(),
		layerOnDb							: new Object(),
		enableInfoMappa						: false,
		overlay								: null,
		hmOverlay							: new Object(),
		overlayCluster						: null,
		baseLayerEnable						: false,															// Boolean che indica se ho un layer di base (es. ortofoto)
		countListLayer						: 0,
		
		lsLayerEnabled						: [],
		isLayerDb							: false,
		
		infoLayerClicked					: [],
		infoLayerGeomClicked				: [],
		infoPopupLayerClicked				: [],
		
		geomFields							: ["the_geom","geom","geometry"],
		
		degree								: 0.000000000000010,
		
		/**
		 * TODO: inserisci commento
		 * Apre il popup con le proprieta' caricate precedentemente e le visualizza nel popup
		 * @param coordinate
		 * @param popupFeatures
		 */
		apriPopup							: function (layerName, layerTitle, coordinate, feature) {
			/**1. Inizializzo il l'object per handlebars**/ 
			var popup = {};
			/**2. Valorizzo l'object per handlebars**/
			popup.layerTitle = layerTitle;
			popup.layerName = layerName;
			listaField = [];
			if ( Array.isArray(feature) ) {
                $.each( feature, function( key, value ) {
                	value = value.split("@")[0];
					if (appMappa.layerFieldsCaricati[popup.layerName+"_"+value]!=null){
						field 			= {};
						field.label 	= appMappa.layerFieldsCaricati[popup.layerName+"_"+value];
						field.value 	= "n.d.";
						listaField		.push(field);
					}
				});
			} else {
				$.each( feature.properties, function( key, value ) {
					if (appMappa.layerFieldsCaricati[popup.layerName+"_viewField_"+key]!=null){
						field 			= {};
						field.label 	= appMappa.layerFieldsCaricati[popup.layerName+"_viewField_"+key];
						field.label 	= appMappa.replaceChar(field.label);
						field.value 	= (key.indexOf("shape_leng")!=-1 && $.isNumeric(value)?parseFloat(value).toFixed(2)+" m":value);
						listaField		.push(field);
					}
				});
			}
			popup.data 		= listaField;
			/**3. Sostituisco il template di handlebars con il codice**/
			var containerLayer = document.getElementById('popupLayer');
			if (containerLayer!=null || containerLayer!=undefined) {
				
			} else {
				$("body").append("<div id=\"popupLayer\" class=\"hidden\"></div>");
				containerLayer = document.getElementById('popupLayer');
				appMappa.overlay = new ol.Overlay({
			        element				: containerLayer,
			        autoPan				: true,
			        autoPanAnimation	: {
			          duration: 250
			        }
			    });
				(appMappa.maps).addOverlay( appMappa.overlay );
			}
			
			var sourcePopup = document.getElementById("popupTemplate").innerHTML;
			var templatePopup = Handlebars.compile(sourcePopup);
		    outputPopup = templatePopup(popup);
//		    $('#popupLayer #popup-content').fadeOut();
//		    $("#popupLayer").fadeOut("slow", function () {
//                $(this).html(outputPopup).fadeIn();
//            });
//		    
//			/**4. Visualizzo il popup e lo posiziono su mappa**/
//			if ( $("#popupLayer").hasClass("hidden") )
//				$("#popupLayer").toggleClass("hidden"); 
//			appMappa.overlay.setPosition(coordinate);

			$("#popupLayer").dialog({
				classes: {'ui-dialog': 'ui-dialog modale-messina'},
				title: 'Dettaglio',
				position: { my: 'right-400 top', at : 'right top+105', of : window, collision: 'none' },
				modal: false,
				width: 320,
				autoOpen: true,
		        draggable: true,
				resizable: true,
				open: function(event, ui) {
					//$(event.target).parent().find(".ui-dialog-titlebar-close").remove();
					//$(event.target).parent().find(".ui-dialog-titlebar").append("<button type=\"button\" onclick=\"minifize('visualizzatore')\" class=\"ui-dialog-titlebar-minifize\" data-info=\"Riduci visualizzatore\" style=\"position: absolute; left: 90%\"><i class=\"fa fa-chevron-up\"></i></button>");
				},
			});
			
			$("#popupLayer").html(outputPopup);
			if ( $("#popupLayer").hasClass("hidden") )
				$("#popupLayer").toggleClass("hidden"); 
		},
		
		/**
		 * TODO: inserisci commento
		 * Apre il popup con le proprieta' caricate precedentemente e le visualizza nel popup
		 * @param coordinate
		 * @param popupFeatures
		 */
		apriEditingPopup					: function (entita, coordinate) {
			/**1. Inizializzo il l'object per handlebars**/ 
			var popup = {};
			/**2. Valorizzo l'object per handlebars**/
			popup.entita = entita;
			
			/**3. Sostituisco il template di handlebars con il codice**/
			var containerLayer = document.getElementById('popupLayer');
			if (containerLayer!=null || containerLayer!=undefined) {
				
			} else {
				$("body").append("<div id=\"popupLayer\" class=\"hidden\"></div>");
				containerLayer = document.getElementById('popupLayer');
				appMappa.overlay = new ol.Overlay({
			        element				: containerLayer,
			        autoPan				: true,
			        autoPanAnimation	: {
			          duration: 250
			        },
			        positioning: 'bottom-right'
			    });
				(appMappa.maps).addOverlay( appMappa.overlay );
			}
			
			var sourcePopup = document.getElementById("editingPopupTemplate").innerHTML;
			var templatePopup = Handlebars.compile(sourcePopup);
		    outputPopup = templatePopup(popup);
			$("#popupLayer").html(outputPopup);
			/**4. Visualizzo il popup e lo posiziono su mappa**/
			if ( $("#popupLayer").hasClass("hidden") )
				$("#popupLayer").toggleClass("hidden"); 
			appMappa.overlay.setPosition(coordinate[0]);
		},
		
		/**
		 * TODO: inserisci commento
		 * Chiude il popup
		 * @param
		 */
		chiudiPopup							: function (){
			/** 1. CHIUDO POPUP**/
			appMappa.overlay.setPosition(undefined);
		    $("#popupLayer-closer").blur();
		    /** 2. ELIMINO SELEZIONE SU RISULTATI RICERCA**/
		    $("#resultRicerca tr.sel").removeClass("sel");
		    /** 3. RIPULISCO LA MAPPA DALLA FEATURE SELEZIONATA**/
			appConfig.vectorSourceSearched.clear();
			/** 4. RIMUOVO IL DATO SELEZIONATO IN PRECEDENZA**/
			(appMappa.maps).removeLayer(appConfig.featureVectorSelected);
		    return false;
		},
		
		/**
		 * TODO: inserisci commento
		 * Chiude il popup
		 * @param
		 */
		chiudiEditingPopup						: function (){
			/** 1. CHIUDO POPUP**/
			appMappa.overlay.setPosition(undefined);
		    $("#popupLayer-closer").blur();
		    return false;
		},
		
		/**
		 * TODO: inserisci commento
		 * Riduce il popup
		 * @param
		 */
		riduciCutLayerPopup						: function ( id ){
			if($("#popupLayer-"+id+" .cutLayerContainer").is(":visible")) {
				$("#popupLayer-"+id+" .cutLayerContainer").hide();
				$("#popupLayer-"+id+" .intestazioneCutLayer #popupLayer-reduces").attr("class","fa fa-chevron-down");
			} else { 
				$("#popupLayer-"+id+" .cutLayerContainer").show();
				$("#popupLayer-"+id+" .intestazioneCutLayer #popupLayer-reduces").attr("class","fa fa-chevron-up");
			}
			return false;
		},
		
		/**
		 * TODO: inserisci commento
		 * Chiude il popup di un cluster layer
		 * @param
		 */
		chiudiPopupCluster					: function (){
			/** 1. CHIUDO POPUP**/
			appMappa.overlayCluster.setPosition(undefined);
		    $("#popupLayer-closer").blur();
		    return false;
		},
		
		/**
		 * TODO: inserisci commento
		 * Creazione Mappa
		 * @param
		 */
		createMappa 						: function ( controls ){
			/** 
			 * Recupero le configurazioni definite per l'identificativo profilo impostato, se recuperate procedo al 
			 * build della mappa.
			 */
			/** 1. INIZIALIZZO LA MAPPA**/
			$("#map").html("");
			appMappa.maps = new ol.Map({
		        target		: 'map',
		        layers		: [],
		        view		: appMappa.view,
		        crossOrigin	: 'anonymous'
		    });
			
			/** 2. IMPOSTO I CONTROLLI DELLA MAPPA**/
			$("#gisTool").html("");
			appMappa.prop.enableFullscreen      = true;
			appMappa.prop.enableGeolocation     = false;
			appMappa.prop.enableMeasureArea     = false;
			appMappa.prop.enableMeasureLength   = false;
			appMappa.prop.enableCenterMap       = false;
			displayOlZoomExtent                 = "none";
			
			if (appMappa.prop.enableFullscreen) {
				appMappa.maps.addControl(new ol.control.FullScreen());
			}
			if (controls.length>0) {
				$.each( controls, function( key, val ) {
					if (val.name=='enableFullscreen' && !appMappa.prop.enableFullscreen)
					    appMappa.prop.enableFullscreen 		= true;
					else if (val.name=='enableGeolocation' && !appMappa.prop.enableGeolocation)
				        appMappa.prop.enableGeolocation 	= true;
				    else if (val.name=='enableMeasureArea' && !appMappa.prop.enableMeasureArea)
				        appMappa.prop.enableMeasureArea 	= true;
				    else if (val.name=='enableMeasureLength' && !appMappa.prop.enableMeasureLength)
				        appMappa.prop.enableMeasureLength 	= true
				    else if (val.name=='enableCenterMap')
				    	displayOlZoomExtent = "block";
				});
				var setting = new ClassBuilder.MapOptions(appMappa.prop);
				var controls = new ControlsBuilder.buildAdvancedControls(appMappa.maps, null, setting);
				$(".ol-zoom-extent").css( "display", displayOlZoomExtent );
			} 
			
			/** 3. IMPOSTO I LAYER PER DISEGNARE FEATURE SULLA MAPPA**/
			appMappa.drawLayer.setZIndex(3);
			(appMappa.maps).addLayer(appMappa.drawLayer);
			appMappa.drawLayerPerimetro.setZIndex(3);
			(appMappa.maps).addLayer(appMappa.drawLayerPerimetro);
			appMappa.editingLayer.setZIndex(3);
			(appMappa.maps).addLayer(appMappa.editingLayer);
			appMappa.selectLayer.setZIndex(3);
			(appMappa.maps).addLayer(appMappa.selectLayer);
			
			/** 5. IMPOSTO LA GRANDEZZA DELLA MAPPA**/
			appMappa.setMapHeight();
			
			/** 6. AGGIUNGO I POPUP ALLA MAPPA**/
			var containerLayer = document.getElementById('popupLayer');
			appMappa.overlay = new ol.Overlay({
		        element				: containerLayer,
		        autoPan				: true,
		        autoPanAnimation	: {
		          duration: 250
		        }
		    });
			(appMappa.maps).addOverlay( appMappa.overlay );
			
			/** 7. AGGIUNGO I POPUP CLUSTER ALLA MAPPA**/
			var containerClusterLayer = document.getElementById('popupClusterLayer');
		    appMappa.overlayCluster = new ol.Overlay({
		        element: containerClusterLayer,
		        offset : [10,0],
		        autoPan: false,
		        autoPanAnimation: {
		             duration: 250
		        }
		    });
		    (appMappa.maps).addOverlay(appMappa.overlayCluster);
		},
		
		/**
		 * TODO: inserisci commento
		 * Creazione Mappa
		 * @param
		 */
		createPrintMappa						: function (){
		
			/** 1. CREO MAPPA**/
			$("#map").html("");
			appMappa.maps = new ol.Map({
		        target			: 'map',
		        layers			: [],
		        view			: appMappa.view,
		        crossOrigin		: 'anonymous',
		        controls		: [],
		        interactions	: []
		    });
		    appMappa.maps.addControl(new ol.control.FullScreen());
		    
		    /** 2. IMPOSTO I CONTROLLI DELLA MAPPA**/
			appMappa.prop.enableFullscreen      = true;
			appMappa.prop.enableGeolocation     = true;
			appMappa.prop.enableMeasureArea     = true;
			appMappa.prop.enableMeasureLength   = true;
			appMappa.prop.enableCenterMap       = true;
			var setting = new ClassBuilder.MapOptions(appMappa.prop);
			var controls = new ControlsBuilder.buildAdvancedControls(appMappa.maps, null, setting);
		},
		
		/**
		 * TODO: inserisci commento
		 * Carica dalla response di dettaglio di un layer, i campi da visualizzare quando si vuole un dettaglio
		 * Il caricamento è sulla forma delle keywords; se il layer contiene una keyword con suffisso "viewField_" viene aggiunto all'hashmap layerFieldsCaricati. 
		 * ad.es. Viene utilizzata ad esempio nei popup
		 * 
		 * @param response, response di geoserver al richiesta di dettaglio di un layer 
		 */
		caricaFieldsFromResponse 			: function ( response ) {
			if (response.layer!=null && response.layer.resource!=null && response.layer.resource.href!=null) {
				layerName = response.layer.name;
				responseLayerDetail = appRest.restGeoserver(response.layer.resource.href);
				if (responseLayerDetail.featureType!=null && responseLayerDetail.featureType.keywords!=null) {
					$.each( responseLayerDetail.featureType.keywords.string, function( key, val ) {
						if (val.indexOf("@")!=-1 && val.indexOf("viewField_")!=-1) {
						    fieldId 		= val.substring(val.indexOf("viewField_"), val.indexOf("@"));	    
						    fieldLabel 		= val.substring(val.indexOf("@")+1,val.length);  	 
						    appMappa.layerFieldsCaricati[layerName+"_"+fieldId] = fieldLabel;
						}
					});
					
					/**Verifico se il layer e' sul db**/
					$.each( responseLayerDetail.featureType.keywords.string, function( key, val ) {
						if (val.indexOf("shapeDb")!=-1) 
							appMappa.layerOnDb[layerName] = true;
					});
				}
			}
		},

		addLayerMap: function (layerName, title, lay, nomeGruppo, itemCheckId){
			if(appUrbamid.layerMap[itemCheckId] != null && appUrbamid.layerMap[itemCheckId] != undefined) {
				
				if(appUrbamid.layerMap[itemCheckId].I.name == layerName ) {
					var tileWms = appUrbamid.layerMap[itemCheckId] != null ? appUrbamid.layerMap[itemCheckId] : null;
					if(tileWms != null) {
						(appMappa.maps).removeLayer(tileWms);
						appUtil.removeElemFromHm(appUrbamid.layerMap, itemCheckId);
						if ( tileWms.I.isBaseLayer ) 
						appMappa.baseLayerEnable = false;
					} else if (tileWms==null) {
						/** Devo eliminare un cluster layer **/
						tileWms = clusterLayerAddedMap[layerName]!=null ? clusterLayerAddedMap[layerName]: null;
						if ( tileWms!=null ) {
							(appMappa.maps).removeLayer(tileWms);
							appUtil.removeElemFromHm(clusterLayerAddedMap, layerName);		
							if ( tileWms.I.isBaseLayer ) 
								appMappa.baseLayerEnable = false;
						}
					}
				} else if (appUrbamid.layerMap[itemCheckId].I.layerName == undefined){
					var tileWms = appUrbamid.layerMap[itemCheckId] != null ? appUrbamid.layerMap[itemCheckId] : null;
					if(tileWms != null) {
						(appMappa.maps).removeLayer(tileWms);
						appUtil.removeElemFromHm(appUrbamid.layerMap, itemCheckId);
					}
				}

				/**Gestione grafica del check**/
				$("#"+itemCheckId+"_chkImg").removeAttr("class");

			} else {
				
				//IMPOSTAZIONE DEL OPACITA'
				layDes = null;
				if (appUrbamid.layerSeleDeselMap[layerName]==null) {
					appUrbamid.layerSeleDeselMap[layerName] = lay;
                } else {
                	layDes = appUrbamid.layerSeleDeselMap[layerName];
                }
				opacity = (lay!=null && lay.opacity!=undefined) ? (lay.opacity/10): 1;
				opacity = (layDes!=null && layDes.opacity!=undefined) ? (layDes.opacity/10): opacity;
				
				//IMPOSTAZIONE DELL'ORDINE DI ATTIVAZIONE
				appUrbamid.layerOrderMap++;
				layOrder = null;
				if (appUrbamid.layerOrderAddMap[layerName]==null) {
					layOrder = appUrbamid.layerOrderMap;
					appUrbamid.layerOrderAddMap[layerName] = layOrder;
                } else {
                	layOrder = appUrbamid.layerOrderAddMap[layerName];	
                }
				
				/**ABILITO IL LAYER**/
				if(layerName.indexOf("layer")!=-1 && layerName.indexOf("u_admin_edtl") == -1) {													// Devo aggiungere un layer di base
					
					(appMappa.layer).setZIndex(0);
					(appMappa.layer).setOpacity(opacity);
					appUrbamid.layerMap[itemCheckId] = (appMappa.layer);
					appUrbamid.layerMap.length++;
					(appMappa.maps).addLayer( (appMappa.layer) );
					
					appMappa.baseLayerEnable = true;
				} else if(layerName.indexOf("ortofoto")!=-1 && layerName.indexOf("u_admin_edtl") == -1) {											// Devo aggiungere un layer di base
					
					(appMappa.layerOrtofoto).setZIndex(0);
					(appMappa.layer).setOpacity(opacity);
					appUrbamid.layerMap[itemCheckId] = (appMappa.layerOrtofoto);
					appUrbamid.layerMap.length++;
					(appMappa.maps).addLayer( (appMappa.layerOrtofoto) );
					appMappa.baseLayerEnable = true;
				}  else { 																				// Devo aggiungere un layer

					appUrbamid.titleLayerList[layerName] = title;
					isCluster = false;
					href = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "layers/"+layerName+".json";
					responseTmp = appRest.restGeoserver ( href );
					if (responseTmp.layer!=undefined) {
						href = responseTmp.layer.resource.href;
						responseLayerDetail = appRest.restGeoserver ( href );
						if( responseLayerDetail.featureType && responseLayerDetail.featureType.keywords && responseLayerDetail.featureType.keywords.string){
							$.each( responseLayerDetail.featureType.keywords.string, function( key, val ) {
								if (val.indexOf("clusterLayer")!=-1 && 
										( (val.substring(val.indexOf("vocabulary=")+11,val.length-2)).toLowerCase()=="true" || (val.substring(val.indexOf("@")+1,val.length)).toLowerCase()=="true"))
									isCluster=true;
								else 
									isCluster=false;
							});
						} else if( responseLayerDetail.coverage && responseLayerDetail.coverage.keywords && responseLayerDetail.coverage.keywords.string){
							$.each( responseLayerDetail.coverage.keywords.string, function( key, val ) {
								if (val.indexOf("clusterLayer")!=-1 && 
										( (val.substring(val.indexOf("vocabulary=")+11,val.length-2)).toLowerCase()=="true" || (val.substring(val.indexOf("@")+1,val.length)).toLowerCase()=="true"))
									isCluster=true;
								else 
									isCluster=false;
							});
						}
					}
					isRaster = false;
					if (responseTmp.layer!=undefined && responseTmp.layer.type=="RASTER") {
						isRaster = true;
					}
					
					if (isCluster) {																	// Devo aggiungere un layer tipo cluster
						
						appMappa.addClusterLayer( layerName, title );
						appMappa.abilitaClusterHover( );
					} else if (isRaster){
					
						var osmSource = new ol.source.TileWMS({																		/**Layer source for tile data from WMS servers. */
							serverType	: "geoserver",																				/**The type of the remote WMS server: mapserver, geoserver or qgis. Only needed if hidpi is true. Default is undefined. */
							params		: {"service":"WMS", "layers":appConfig.workspaceGeoserver+":"+layerName},					/**WIDTH, HEIGHT, BBOX and CRS (SRS for WMS version < 1.3.0) will be set dynamically. Required.**/
							url			: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceGeoserver+"/wms",	/**WMS service URL. */
					        projection	: appConfig.mapProjection,																	/**Recupero la projection del layer da geoserver**/
					        crossOrigin	: 'anonymous'
						});
						var rasterSource = new ol.source.Raster({
						      sources: [osmSource],
						      operation: function(pixels,data) {
						            var pixel = pixels[0];
						
						            var r = pixel[0];
						            var g = pixel[1];
						            var b = pixel[2];
						
						            // CIE luminance for the RGB
						            var v = 0.2126 * r + 0.7152 * g + 0.0722 * b;
						
						            pixel[0] = v; // Red
						            pixel[1] = v; // Green
						            pixel[2] = v; // Blue
						
						            return pixel;
						        }
						  });
						  var mapLayer = new ol.layer.Image({
						      source: rasterSource
						  });
						
						
						(appMappa.maps).addLayer(mapLayer);
						appUrbamid.layerMap[itemCheckId] = mapLayer;
					} else{																				// Devo aggiungere un layer non cluster
						
						var source = new ol.source.TileWMS({																		/**Layer source for tile data from WMS servers. */
							serverType	: "geoserver",																				/**The type of the remote WMS server: mapserver, geoserver or qgis. Only needed if hidpi is true. Default is undefined. */
							params		: {"service":"WMS", "layers":appConfig.workspaceGeoserver+":"+layerName},					/**WIDTH, HEIGHT, BBOX and CRS (SRS for WMS version < 1.3.0) will be set dynamically. Required.**/
							url			: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceGeoserver+"/wms",	/**WMS service URL. */
					        projection	: appConfig.mapProjection,																	/**Recupero la projection del layer da geoserver**/
					        crossOrigin	: 'anonymous'
						});
						var tileWms = new ol.layer.Tile({
							opacity		: opacity,																					/**Opacity. 0-1. Default is 1.**/
					        source		: source,																					/**Source. Required.**/
					        visible		: true,																						/**Visibility.**/
					        zIndex		: layOrder,																					/**The default Z-index is 0.**/ 
					        name		: layerName,																				/**Nome del layer**/
							title		: layerName,
							idParent	: nomeGruppo,
							variante	: nomeGruppo.indexOf('Variante') != -1 ? true : false,
					    });
						tileWms.setZIndex(layOrder);
						tileWms.setOpacity(opacity);
						(appMappa.maps).addLayer(tileWms);
						appUrbamid.layerMap[itemCheckId] = tileWms;
					}
					appUrbamid.layerMap.length++;
				}
				/**Gestione grafica del check**/
				$("#"+itemCheckId+"_chkImg").removeAttr("class").attr("class", "fa fa-check");

			}
			
			if(nomeGruppo != undefined && nomeGruppo != null && nomeGruppo != '') {
				let isAllChecked = 0; /** Il totale dei checkbox con la proprietà checked */
				let isAllNotChecked = 0; /** Il totale dei checkbox senza la proprietà checked */

				/** Per ogni tavola recupero i checkbox e indico quanti sono checked e quanti no */
				$('input[data-gruppo="'+ nomeGruppo +'"]').each(function(e){
					if($(this).is(':checked')) {
						isAllChecked++;
					} else {
						isAllNotChecked++;
					}
				});

				
				if($('input[data-gruppo="'+ nomeGruppo +'"]').length == isAllChecked) { /** Verifico che i checkbox della tavola siano uguali alla variabile isAllChecked */
					$('#visualizzatoreDatiBase' + nomeGruppo + '_chk').prop('checked', true);
					$('#visualizzatoreDatiBase' + nomeGruppo + '_chk').prop('indeterminate', false);
				} else if($('input[data-gruppo="'+ nomeGruppo +'"]').length == isAllNotChecked) { /** Verifico che i checkbox della tavola siano uguali alla variabile isAllNotChecked */
					$('#visualizzatoreDatiBase' + nomeGruppo + '_chk').prop('checked', false);
					$('#visualizzatoreDatiBase' + nomeGruppo + '_chk').prop('indeterminate', false);
				} else { /** In caso contrario setto la proprietà indeterminate su true */
					$('#visualizzatoreDatiBase' + nomeGruppo + '_chk').prop('checked', false);
					$('#visualizzatoreDatiBase' + nomeGruppo + '_chk').prop('indeterminate', true);
				}
			}
		},

		/**
		 * TODO: inserisci commento
		 * Aggiunge un layer alla mapppa (maps)
		 * 
		 * @param layerName, nome del layer da aggiungere alla mappa 
		 */
		addRemoveLayer							: function (layerName, title, lay, suffixGraf, nomeGruppo){
			if ( appUrbamid.layerAddedMap[layerName]!=null ){											// Devo eliminare un layer
				
				/**DIS-ABILITO IL LAYER**/
				var tileWms = appUrbamid.layerAddedMap[layerName]!=null ? appUrbamid.layerAddedMap[layerName] : null;
				if ( tileWms!=null ) {
					/** Devo eliminare un layer standard **/
					(appMappa.maps).removeLayer(tileWms);
					appUtil.removeElemFromHm(appUrbamid.layerAddedMap, layerName);		
					if ( tileWms.I.isBaseLayer ) 
						appMappa.baseLayerEnable = false;
				} else if (tileWms==null) {
					/** Devo eliminare un cluster layer **/
					tileWms = clusterLayerAddedMap[layerName]!=null ? clusterLayerAddedMap[layerName]: null;
					if ( tileWms!=null ) {
						(appMappa.maps).removeLayer(tileWms);
						appUtil.removeElemFromHm(clusterLayerAddedMap, layerName);		
						if ( tileWms.I.isBaseLayer ) 
							appMappa.baseLayerEnable = false;
					}
				}
				/**Gestione grafica del check**/
				itemCheckId 		= appUtil.generateId(layerName);
				$("#"+itemCheckId+suffixGraf+"_chkImg").removeAttr("class");
				if(nomeGruppo.indexOf('Dati_Catalogo') != -1) {
					$('#visualizzatoreDatiBasedatiCatalogo_chk').prop('checked', false);
				} else if(nomeGruppo.indexOf('Variante') != -1) {
					$('#visualizzatoreDatiBaselayerVariante_chk').prop('checked', false);
				} else {
					$('#visualizzatoreDatiBase' + appUtil.generateId(nomeGruppo) + '_chk').prop('checked', false);
				}
			} else {
				
				opacity = (lay!=null && lay.opacity!=undefined) ? (lay.opacity/10): 1;
				
				/**ABILITO IL LAYER**/
				if(layerName.indexOf("layer")!=-1) {													// Devo aggiungere un layer di base
					
					(appMappa.layer).setZIndex(0);
					(appMappa.layer).setOpacity(opacity);
					(appMappa.maps).addLayer( (appMappa.layer) );
					appUrbamid.layerAddedMap[layerName] = (appMappa.layer);
					appUrbamid.layerAddedMap.length++;
					appMappa.baseLayerEnable = true;
				} else if(layerName.indexOf("ortofoto")!=-1) {											// Devo aggiungere un layer di base
					
					(appMappa.layerOrtofoto).setZIndex(0);
					(appMappa.layer).setOpacity(opacity);
					(appMappa.maps).addLayer( (appMappa.layerOrtofoto) );
					appUrbamid.layerAddedMap[layerName] = (appMappa.layerOrtofoto);
					appUrbamid.layerAddedMap.length++;
					appMappa.baseLayerEnable = true;
				}  else { 																				// Devo aggiungere un layer

					appUrbamid.titleLayerList[layerName] = title;
					isCluster = false;
					href = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "layers/"+layerName+".json";
					responseTmp = appRest.restGeoserver ( href );
					if (responseTmp.layer != undefined) {
					
						href = responseTmp.layer.resource.href;
						responseLayerDetail = appRest.restGeoserver ( href );
						if( responseLayerDetail.featureType && responseLayerDetail.featureType.keywords && responseLayerDetail.featureType.keywords.string){
							$.each( responseLayerDetail.featureType.keywords.string, function( key, val ) {
								if (val.indexOf("clusterLayer")!=-1 && 
										( (val.substring(val.indexOf("vocabulary=")+11,val.length-2)).toLowerCase()=="true" || (val.substring(val.indexOf("@")+1,val.length)).toLowerCase()=="true"))
									isCluster=true;
								else 
									isCluster=false;
							});
						}
					}
					
					if (isCluster) {																	// Devo aggiungere un layer tipo cluster
						
						appMappa.addClusterLayer( layerName, title );
						appMappa.abilitaClusterHover( );
					} else{																				// Devo aggiungere un layer non cluster
						
						var source = new ol.source.TileWMS({																		/**Layer source for tile data from WMS servers. */
							serverType	: "geoserver",																				/**The type of the remote WMS server: mapserver, geoserver or qgis. Only needed if hidpi is true. Default is undefined. */
							params		: {"service":"WMS", "layers":appConfig.workspaceGeoserver+":"+layerName},					/**WIDTH, HEIGHT, BBOX and CRS (SRS for WMS version < 1.3.0) will be set dynamically. Required.**/
							url			: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceGeoserver+"/wms",	/**WMS service URL. */
					        projection	: appConfig.mapProjection,																	/**Recupero la projection del layer da geoserver**/
					        crossOrigin	: 'anonymous'
						});
						var tileWms = new ol.layer.Tile({
							opacity		: opacity,																					/**Opacity. 0-1. Default is 1.**/
					        source		: source,																					/**Source. Required.**/
					        visible		: true,																						/**Visibility.**/
					        zIndex		: 0,																						/**The default Z-index is 0.**/ 
					        name		: layerName,																				/**Nome del layer**/
					        title		: layerName																					/**Nome del layer**/
					    });
						tileWms.setZIndex(1);
						(appMappa.maps).addLayer(tileWms);
						appUrbamid.layerAddedMap[layerName] = tileWms;
					}
					appUrbamid.layerAddedMap.length++;
				}
				/**Gestione grafica del check**/
				itemCheckId 		= appUtil.generateId(layerName);
				$("#"+itemCheckId+suffixGraf+"_chkImg").removeAttr("class").attr("class", "fa fa-check");
				
				let isAllChecked = 0;

				if(nomeGruppo != '') {
					$('.' + nomeGruppo).each(function(e){
						if(!$(this).is(':checked')) {
							isAllChecked = 1;
							return;
						}
					});
	
					if(isAllChecked == 0) {
						if(nomeGruppo.indexOf('Dati_Catalogo') == -1 && nomeGruppo.indexOf('Variante') == -1) {
							$('#visualizzatoreDatiBase' + appUtil.generateId(nomeGruppo) + '_chk').prop('checked', true);
						} else if(nomeGruppo.indexOf('Dati_Catalogo') != -1) {
							$('#visualizzatoreDatiBasedatiCatalogo_chk').prop('checked', true);
						} else if(nomeGruppo.indexOf('Variante') != -1) {
							$('#visualizzatoreDatiBaselayerVariante_chk').prop('checked', true);
						}					
					}
				} 
			
			}
		},
		
		
		/**
		 * TODO: inserisci commento
		 * Aggiunge un layer alla mapppa (maps)
		 * 
		 * @param layerName, nome del layer da aggiungere alla mappa 
		 */
		addRemoveLayerGroup						: function (layerGroupName, titleGroup, lay){
			alert("layerGroupName: "+layerGroupName);
		},
		/**
		 * TODO: inserisci commento
		 * @param layerName
		 * @param title
		 */
		addClusterLayer						: function ( layerName, title ) {
			/**TODO: generalizzare l'abilitazione di un layer cluster**/
			var featureRequest = new ol.format.WFS().writeGetFeature({
		        srsName			: 'EPSG:4326',
		        featurePrefix	: 'geoserver',
		        featureTypes	: [responseLayerDetail.featureType.name],
		        outputFormat	: "application/json", 
		        filter			: null
			});
			fetch(appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceService + appConfig.wfsService, {
		        method: 'POST',
		        body: new XMLSerializer().serializeToString(featureRequest)
		    }).then(function(response) {
		        return response.json();
		    }).then(function(json) {
		    	clusterLayerAddedMap = [];
		    	
		        var features = new ol.format.GeoJSON().readFeatures(json);
		        
		        var source = new ol.source.Vector({
		        	features: features
		        });

		        var clusterSource = new ol.source.Cluster({
		        	distance: parseInt(1, 10),
		        	source: source
		        });

		        var styleCache = {};
		        var clusters = new ol.layer.Vector({
		        	name : title,
		        	source: clusterSource,
		        	style: function(feature) {
		        		var size = feature.get('features').length;
    					cluColor = appMappa.getClusterColor(feature.I.features[0]);
    					style = appMappaStyle.getStyleCluster( cluColor, size );
    					return style;
					}
		        });
		        clusters.setZIndex(2);
		        (appMappa.maps).addLayer(clusters);
		        appUrbamid.layerAddedMap[layerName] = clusters;
		        clusterLayerAddedMap[layerName] = clusters;
		    });		
		},
		
		/**
		 * TODO: inserisci commento
		 * @param feature
		 * @returns {String}
		 */
		getClusterColor						: function ( feature ){
			if ( (feature.I.peric_ita).indexOf("P1")!=-1 ) {
				return "#F8FFBE";
			} else if ( (feature.I.peric_ita).indexOf("P2")!=-1 ) {
				return "#EEFE00";
			} else if ( (feature.I.peric_ita).indexOf("P3")!=-1 ) {
				return "#E0A906";
			} else if ( (feature.I.peric_ita).indexOf("P4")!=-1 ) {
				return "#D20404";
			}
			return "#FFFFFF";
		},
		
		
		/**
		 * TODO: inserisci commento
		 * @param layerName
		 */
		removeLayer							: function (layerName){
			if ( appUrbamid.layerAddedMap[layerName]!=null ){
				/**DIS-ABILITO IL LAYER**/
				var tileWms = appUrbamid.layerAddedMap[layerName]!=null ? appUrbamid.layerAddedMap[layerName] : null;
				if ( tileWms!=null ) {
					/** Devo eliminare un layer standard **/
					(appMappa.maps).removeLayer(tileWms);
					appUtil.removeElemFromHm(appUrbamid.layerAddedMap, layerName);		
					if ( tileWms.I.isBaseLayer ) 
						appMappa.baseLayerEnable = false;
				} else if (tileWms==null) {
					/** Devo eliminare un cluster layer **/
					tileWms = clusterLayerAddedMap[layerName]!=null ? clusterLayerAddedMap[layerName]: null;
					if ( tileWms!=null ) {
						(appMappa.maps).removeLayer(tileWms);
						appUtil.removeElemFromHm(clusterLayerAddedMap, layerName);		
						if ( tileWms.I.isBaseLayer ) 
							appMappa.baseLayerEnable = false;
					}
				}
			}			
		},
		
		/**
		 * TODO: inserisci commento
		 * @param enable
		 * @param layerName
		 * @param layer
		 */
		manageLayer 						: function ( enable, layerName, layer ) {
			if (enable && layer!=null && !(appConfig.vectorLayerMap)[layerName]){							//AGGIUNGO LAYER
																															/**Definizione del layer**/
				var source = new ol.source.TileWMS({																		/**Layer source for tile data from WMS servers. */
					serverType	: "geoserver",																				/**The type of the remote WMS server: mapserver, geoserver or qgis. Only needed if hidpi is true. Default is undefined. */
					params		: {"service":"WMS", "layers":appConfig.workspaceGeoserver+":"+layerName},					/**WIDTH, HEIGHT, BBOX and CRS (SRS for WMS version < 1.3.0) will be set dynamically. Required.**/
					url			: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceGeoserver+"/wms",	/**WMS service URL. */
		            projection	: appConfig.mapProjection,																	/**Recupero la projection del layer da geoserver**/
		            crossOrigin	: 'anonymous'
				});
				var tileWms = new ol.layer.Tile({
					opacity		: 1,																						/**Opacity. 0-1. Default is 1.**/
			        source		: source,																					/**Source. Required.**/
			        visible		: true,																						/**Visibility.**/
			        zIndex		: 0,																						/**The default Z-index is 0.**/ 
			        name		: layerName,																				/**Nome del layer**/
			        title		: layerName																					/**Nome del layer**/
			    });
				(appMappa.maps).addLayer(tileWms);																					/**Layer aggiunto alla mappa**/										
				(appConfig.vectorLayerMap)[layerName] = tileWms;															/**Layer aggiunto al hashmap dei layer aggiunti alla mappa**/
				(appConfig.layerEnabledMap)[layerName] = layer;																/**TODO: commenti**/
				layer.id = layerName;
				(appConfig.vectorLayerEnabledArray).unshift(layer);															/**TODO: commenti**/
			} else if (!enable && (appConfig.vectorLayerMap)[layerName]){									//ELIMINO LAYER
				tileWms = (appConfig.vectorLayerMap)[layerName];															/**Recupero il layer dal hashmap dei layer aggiunti alla mappa**/
				(appMappa.maps).removeLayer(tileWms);																				/**Elimino il layer dalla mappa**/
				appConfig.vectorLayerEnabledArray =																			/**Elimino il layer dal hashmap dei layer aggiunti alla mappa**/ 
					appUtil.removeElemFromArray(appConfig.vectorLayerEnabledArray, layerName, (appConfig.vectorLayerMap)[layerName]);
				delete (appConfig.layerEnabledMap)[layerName];																/**Elimino il layer dal hashmap dei layer abilitati**/
				delete (appConfig.vectorLayerMap)[layerName];																/**Elimino il layer dai layer attivi**/
			};
		},

		/**
		 * FUNCTION VERIFICATA 
		 * TODO: INS_COMMENTO
		 */
		toggleTool : function (id,e) {
		
			/*Elimino le feature selezionate*/
			selectLayerSource.clear();
			drawLayerSource.clear();
			
			/*Elimino i popup*/
			(appMappa.maps).removeOverlay( appMappa.overlay );
			
			/*Abilito il box e disattivo tutti gli altri areeTematiche*/
			if (id.indexOf("printToolContainer")!=-1){
				if (appMappa.print()!=-1)
					$("#mapsChoice").dialog("close");	
				else 
					appUtil.showMessageAlertApplication("Disabilitare i livelli di base", 'Informazione', true, false, false, null, null);
			} else if (id.indexOf("mapsChoice")!=-1){
				$("#mapsChoice").dialog({
					classes: {'ui-dialog': 'ui-dialog modale-messina'},
					title: 'Mappe',
					position: { my: 'left+60 top', at : 'left top+125', of : window, collision: 'none' },
					modal: false,
					width: 320,
					height: 300,
					autoOpen: true,
			        draggable: true,
					resizable: true,
					open: function(event, ui) {
						hrefUrbamidMappeTemi = appUtil.getUrbamidOrigin() + appConfig.urbamidService + appConfig.urbamidMappeTemi;
						urbamidMappeTemiResponse = appRest.restUrbamid(hrefUrbamidMappeTemi, "POST", null);
						appUrbamid.temiMappeList = urbamidMappeTemiResponse.aaData;
						var temiHm = [];
						$.each( appUrbamid.temiMappeList, function( keyTema, valueTema ) {
							if( temiHm[valueTema.idTema]!=null ) {
								if(valueTema.stato == 'P') {
									valueMappa = {'id':valueTema.idMappa,'title':valueTema.nomeMappa, 'codeMappa':valueTema.codeMappa};
									(temiHm[valueTema.idTema].mappe).push( valueMappa );
									temiHm[valueTema.idTema].mappe.sort(ordinaPer("title"));
								}
							} else {
								temiHm[valueTema.idTema] = {'id':valueTema.idTema, 'nomeTema':valueTema.nomeTema, 'mappe':[], 'ordinamento': valueTema.ordinamento};
								if(valueTema.stato == 'P') {
									valueMappa = {'id':valueTema.idMappa,'title':valueTema.nomeMappa, 'codeMappa':valueTema.codeMappa};
									(temiHm[valueTema.idTema].mappe).push( valueMappa );
									temiHm[valueTema.idTema].mappe.sort(ordinaPer("title"));
								}
							}					
						});
					    var elencoMappe = {};
					    elencoMappe.temiHm = temiHm.sort(ordinaPer("nomeTema"));
						$("#elenco-mappe-choice-content").html( compilaTemplate("elencoMappeTemplate", elencoMappe) );
						$("#mapsChoice").toggleClass("hidden").siblings(".boxMappaChoice").addClass("hidden");
						$(".boxMappa").addClass("hidden");
					},
					close: function(event, ui) {
						$("#mapsChoice").toggleClass("hidden").siblings(".boxMappaChoice").addClass("hidden");
						$("#elenco-mappe-choice-content").html( "" );
					}
				});
			} else {
				$("#"+id).toggleClass("hidden").siblings(".boxMappa").addClass("hidden");
				if (!$('#mapsChoice').hasClass('hidden'))
					$("#mapsChoice").dialog("close");
			}
			
			/*Attivo il button che ho cliccato*/
			$("#"+id+"Btn").toggleClass("active").parent().siblings(".ol-control").find("button").removeClass("active");
			
			/*Abilito il button infoTool se era già attivo*/
			if ( appMappa.enableInfoMappa ) {
				appMappa.enableInfoMappa=false;
			}
			
			/**/
			if($(e.currentTarget).hasClass("active")){
				$("#map,.generalContainer").addClass("pleft");
				$(".strumentiMappa").addClass("coll");
			}
			else{
				$("#map,.generalContainer").removeClass("pleft");
				$(".strumentiMappa").removeClass("coll");
			}
			
			if (id.indexOf("catalogo")!=-1) {
				if(appConfig.jsTreeCatalogo === null) {
					appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
					setTimeout(function(){
						appUtil.hideLoader();
						appCatalogo.getStartCatalogo();
						cbkOnClick = "appUrbamid.addLayerVisualizzatore";
						/** Inizializzo la variabile che contiene il menu con le vodi di default **/
						appCatalogo.initializeCatalogo( "menu-content", cbkOnClick );
					}, 500);
				}
			}
		},
		
		replaceChar : function (s){
			var response = "";
			var a = "Ã,Á,á,À,Â,à,Â,â,Ä,ä,Ã,ã,Å,å";
			var e = "É,é,È,è,Ê,ê,Ë,ë";
			var i = "Í,í,Ì,ì,Î,î,Ï,ï";
			var o = "Ó,ó,Ò,ò,Ô,ô,Ö,ö,Õ,õ,Ø,ø";
			var u = "Ú,ú,Ù,ù,Û,û,Ü,ü";
			arrS = Array.from(s);
			$.each( arrS, function( keyC, valueC ) {
				if (!valueC.trim()) response = response + " ";
				else if (a.indexOf(valueC)!=-1) response = response+"a'";
				else if (e.indexOf(valueC)!=-1) response = response+"e'";
				else if (i.indexOf(valueC)!=-1) response = response+"i'";
				else if (o.indexOf(valueC)!=-1) response = response+"o'";
				else if (u.indexOf(valueC)!=-1) response = response+"u'";
				else response = response+valueC;
			});
			return response;			
		},
		
		/**
		 * FUNCTION VERIFICATA 
		 * TODO: INS_COMMENTO
		 */
		toggleVisualizzatore				: function (id,e) {
			/*Abilito il box e disattivo tutti gli altri areeTematiche*/
			$("#"+id).toggleClass("showHide");
			$("#"+id+" .boxMappa_body").toggleClass("hidden");
			$('#'+id+ '.ui-dialog-titlebar').toggleClass("fa-chevron-up").toggleClass("fa-chevron-down");
			$('#'+id+ ' .ui-dialog-content ui-widget-content').hide();
		},
		
		/**
		 * FUNCTION VERIFICATA 
		 * TODO: INS_COMMENTO
		 * CALCOLA L'ALTEZZA DELLA MAPPA DEL SINOTTICO
		 */
		setMapHeight						: function () {
			if(appMappa.maps != null)
				(appMappa.maps).updateSize();
		}, 

		/**
		 * TODO: inserisci commento
		 */
		abilitaClickMappa					: function () {
		},

		/**
		 * TODO: inserisci commento
		 */
		disabilitaClickMappa				: function () {
		},

		/**
		 * TODO: inserisci commento
		 * @param evt
		 */
		callbackClickMappa					: function (evt) {
			if (appUrbamid.layerAddedMap.length>0) {
			}
		},

		/**
		 * TODO: inserisci commento
		 */
		dims : {
	        a0: [1189, 841],
	        a1: [841, 594],
	        a2: [594, 420],
	        a3: [420, 297],
	        a4: [297, 210],
	        a5: [210, 148]
		},
		/**
		 * TODO: inserisci commento
		 */
		print								: function () {
		
			//Base url
			var url = appConfig.urbamidProtocol + "://"+ appConfig.urbamidHostname + (appConfig.urbamidPort!=""?":"+appConfig.urbamidPort : "") + appConfig.urbamidService + "print?";
			var urlObj = appConfig.urbamidProtocol + "://"+ appConfig.urbamidHostname + (appConfig.urbamidPort!=""?":"+appConfig.urbamidPort : "") + appConfig.urbamidService + "print?";
			var objData = {};
			
			//Authority and CodeMappa
			url += "authority="+appUrbamid.authority;
			urlObj += "authority="+appUrbamid.authority+"&";
			if ( appUrbamid.mappaApplicata!=null && appUrbamid.mappaApplicata!="" ){	
				url += "&codeMappa="+appUrbamid.mappaApplicata;
				objData.codeMappa = appUrbamid.mappaApplicata;
			} else {
				url += "&codeMappa=WEBGIS";
				objData.codeMappa = "WEBGIS";
			}
			
			//Recupero center e zoom
			var zoom=appMappa.maps.getView().getZoom();
			var center=appMappa.maps.getView().getCenter();
			var centerMapPrint= new ol.proj.transform([center[0],center[1]], 'EPSG:3857', 'EPSG:4326');
			url +="&center="+center[0]+","+center[1]+"&zoom="+zoom;
			objData.zoom = zoom;
			objData.center = center;
			
			//Recupero tutti i layer applicati
			layApplicati = "";
			layOpacity = "";
			layZIndex = "";
			objData.layers = [];
			if (appUrbamid.layerMap!=null) {
				urlLayApplicati ="&layersApplicati=";
				urlLayOpacity ="&layersOpacity=";
				urlLayZIndex ="&layersZIndex=";
				for(var i in appUrbamid.layerMap) {
					//Check if dati di base selected
					if ((appUrbamid.layerMap[i].I.name).indexOf("ortofoto")!=-1 ||
							(appUrbamid.layerMap[i].I.name).indexOf("layer")!=-1)
						return -1;
					let objLayer = {};
					let layer = appUrbamid.layerMap[i];
					let layerName = layer.I.name;
					let layerOpacity = layer.I.opacity;
					let layerZIndex = layer.I.zIndex;
					urlLayApplicati +=layerName+",";
					urlLayOpacity +=layerOpacity+",";
					urlLayZIndex +=layerZIndex+",";
					
					objLayer.layerName = layerName;
					objLayer.layerOpacity = layerOpacity;
					objLayer.layerZIndex = layerZIndex;
					objData.layers.push(objLayer);
				};
			}
			urlLayApplicati = urlLayApplicati.slice(0,-1);
			urlLayOpacity = urlLayOpacity.slice(0,-1);
			urlLayZIndex = urlLayZIndex.slice(0,-1);
			url += urlLayApplicati+"&"+urlLayOpacity+"&"+urlLayZIndex;
			
			urlObj += "data=" + btoa(JSON.stringify(objData)); 
//			window.open(url, "Stampa",'height=768,width=1366,left=10,top=10,titlebar=no,toolbar=no,menubar=no,location=no,directories=no,status=no');
			window.open(urlObj, "Stampa Obj",'height=768,width=1366,left=10,top=10,titlebar=no,toolbar=no,menubar=no,location=no,directories=no,status=no');
		},
		
		
		screenshotMap : function (targetElem) {
			var link = document.createElement('a');
			var p = new Promise(function(resolve, reject) {
				html2canvas(document.getElementById("printMap"), {
					logging : false
				}).then(function(canvas) {
					resolve(canvas);
				});
			});
			p.then(function(canvas) {
				if (window.navigator.msSaveBlob) { 				//IE
					var blob = canvas.msToBlob();
                    window.navigator.msSaveBlob(blob, (objMap.name).replace(/\s+/g, '_').toLowerCase()+".png");
				} else {										//NOT IE
					var image = canvas.toDataURL("image/png");
					link.href = image.replace(/^data:image\/png/,"data:application/octet-stream");
				    link.download = "Mappa.png";
				    link.id = "tmpLink";
					document.body.appendChild(link);
				    link.click();
				}
				$("#tmpLink").remove();
			});
			
//		    document.body.style.cursor = 'progress';
//			const dim = dims["a4"];
//		    html2canvas((appMappa.maps).getViewport(), exportOptions).then(function (canvas) {
//				const pdf = new jspdf.jsPDF('landscape', undefined, "a4");
//        		pdf.addImage(canvas.toDataURL('image/jpeg'),'JPEG',0,0,dim[0],dim[1]);
//        		pdf.save('map.pdf');
//        		document.body.style.cursor = 'auto';
//      		});
//      
//		    document.body.style.cursor = 'auto';
		     
		},
		
		/**
		 * TODO: inserisci commento
		 * Abilita il click sulle feature presenti in mappa. Il risultato è aprire un popup sulla feature.
		 * Il popup si chiuderà solo se clicca su chiudi popup
		 */
		abilitaInfoMappa					: function () {
			/** 1. ABILITO INFO SU MAPPA E INIZIALIZZO**/
			appMappa.enableInfoMappa = !appMappa.enableInfoMappa;
			
			/** 2. IMPOSTO IL BOTTONE ATTIVO/DISATTIVO **/
			$("#infoToolContainerBtn").toggleClass("active");
			
			/** 3. CHIUDO IL POPUP **/
			$("#popupLayer-closer").click();
			
			/** 4. RIMUOVO IL DATO SELEZIONATO IN PRECEDENZA**/
			(appMappa.maps).removeLayer(appConfig.featureVectorSelected);
			
			/** 5. RIPULISCO IL CONTENUTO DEL POPUP**/
			$("#layer-info-content").html("Seleziona un punto sulla mappa");
			
			/** 6. AGGIUNGO L'OPERAZIONE CLICK SULLA MAPPA**/
			(appMappa.maps).on('singleclick', function(evt) {
				
				if ( appMappa.enableInfoMappa ) {													/**NON È STATA ABILITATA LA FUNZIONALITÀ DI INFO SU MAPPA**/
					if (appUrbamid.layerMap.length>0) {										/**BISOGNA SELEZIONARE IL LAYER DAL VISUALIZZATORE**/

						appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
						setTimeout(function(){
							/** 1. INIZIALIZZO MAPPA E OGGETTI IN PAGINA **/
							(appMappa.maps)					.removeLayer(appConfig.featureVectorSelected);
							appMappa.infoLayerClicked 		= [];
							appMappa.infoLayerGeomClicked	= [];
							layersEnable 					= [];
							$("#layer-info-content")		.html("");
							
							/** 2. IMPOSTO LA LISTA DEI LAYER ABILITATI PER EFFETTUARE UNA RICERCA**/
							for(var i in appUrbamid.layerMap) {
								let layer = appUrbamid.layerMap[i];
								if (layer.I.isBaseLayer!=undefined && layer.I.isBaseLayer) {
									console.log("Non cerco informazione su un baseLayer come "+layer.I.name);
								} else {
									$.each( appUrbamid.defaultLayerAddedView, function( keyL, valueL ) {
										if (layer.I.isBaseLayer==undefined && layer.I.name.indexOf(valueL) != -1) {
											layersEnable.push(valueL);
										}
									});
								}
							}
							if (appUrbamid.layerAddedView.length==0){
								console.log("Non sono stati aggiunti layer dal catalogo e non aggiungo layer da ricercare");
							} else if (appUrbamid.layerAddedView.length>0){
								for(var i in appUrbamid.layerMap) {
									let layer = appUrbamid.layerMap[i];
									if (layer.I.isBaseLayer!=undefined && layer.I.isBaseLayer) {
										console.log("Non cerco informazione su un baseLayer come "+layer.I.name);
									} else {
										$.each( appUrbamid.layerAddedView, function( keyL, valueL ) {
											if(layer.I.isBaseLayer==undefined && layer.I.name.indexOf(valueL) != -1) {
												layersEnable.push(valueL);
											}
										});
									}
								}
							}
							
							/** 3. RECUPERO INFORMAZIONI SUI LAYER ABILITATI  **/
							var zoomLayerForInfo = "";
							$.each( layersEnable, function( keyLayer, layerName ) {
	                        	
								/**RECUPERO IL CAMPO GEOMETRY**/
								var geomField = "geom";
								var geomType = "Point";
								var href = appUtil.getOrigin() + appConfig.geoserverService +
						        				"wfs?SERVICE=WFS&version=2.0.0&request=GetFeature"+
						        				"&typeNames=urbamid:"+layerName+
						        				"&outputFormat=application%2Fjson&count=1";
								if (href!=null) {
									responseLayerClicked = appRest.restGeoserver(href);
									if (responseLayerClicked!=null && responseLayerClicked.features!=null && responseLayerClicked.features.length>0) {
										geomField = responseLayerClicked.features[0].geometry_name;
										geomType = responseLayerClicked.features[0].geometry.type; 
									}
								}
								
								/**RECUPERO LE FEATURE INTERSECANDO IL PUNTO SELEZIONATO SU MAPPA**/	
								var coord = new ol.proj.transform([evt.coordinate[0], evt.coordinate[1]], 'EPSG:3857', 'EPSG:4326');
								var href = appUtil.getOrigin() + appConfig.geoserverService +
						        				"wfs?SERVICE=WFS&version=2.0.0&request=GetFeature"+
						        				"&typeNames=urbamid:"+layerName+
						        				"&outputFormat=application%2Fjson";
								if (geomType=='Point' || geomType=='MultiLineString')
									//href = href + "&CQL_FILTER=DWITHIN("+geomField+",POINT("+coord[1]+" "+coord[0]+"),0.0005,kilometers)";
									href = href + "&bbox="+(coord[1]-parseFloat(appMappa.degree))+","+(coord[0]-parseFloat(appMappa.degree))+","+
											(coord[1]+parseFloat(appMappa.degree))+","+(coord[0]+parseFloat(appMappa.degree));
									
								else 
									href = href + "&CQL_FILTER=Intersects("+geomField+",POINT("+coord[1]+" "+coord[0]+"))";
								if (href!=null) {
									
									responseLayerClicked = appRest.restGeoserver(href);
									if (responseLayerClicked!=null && responseLayerClicked.features!=null && responseLayerClicked.features.length>0) {
										
										valueFeaClick 								= responseLayerClicked.features[0];
										if (appMappa.infoLayerGeomClicked[layerName]!=null)
											singleFeatures							= appMappa.infoLayerGeomClicked[layerName];
										else
											singleFeatures							= (appConfig.featureSourceSelected).getFormat()
																						.readFeatures( valueFeaClick, { dataProjection : 'EPSG:4326', featureProjection : 'EPSG:3857'});										
										appMappa.infoLayerClicked[layerName] 		= singleFeatures;
										appMappa.infoPopupLayerClicked[layerName] 	= {coords:evt.coordinate, valueFeaClick:valueFeaClick};
										
									} else {
										console.log("Non ho ottenuto informazioni da geoserver per il layer "+layerName);	
									}
								} else {
									console.log("Non ho una url per chiedere informazioni a geoserver per il layer "+layerName);
								}
								
								/**AGGIUNGO NEL BOX I LAYER INTERSECATI NEL PUNTO SELEZIONATO SU MAPPA**/
								if (appMappa.infoLayerClicked[layerName]!=undefined) {
									
									/**3.1.3. AGGIUNGO BOX NEL CONTAINER DELLE INFO**/
									itemInfoLayer = {layerTitle:appUrbamid.titleLayerList[layerName], layerName:layerName};
									var sourceItemLayer = document.getElementById("itemInfoLayer").innerHTML;
									var templateItemLayer = Handlebars.compile(sourceItemLayer);
								    outputItemLayer = templateItemLayer(itemInfoLayer);
									$("#layer-info-content").append( outputItemLayer );
								} else {
									
									zoomLayerForInfo = appUrbamid.titleLayerList[layerName]+",";
								}
	                        });
	                        
	                        /** 4. VERIFICO SE HO SELEZIONATO FEATURE, IN CASO NEGATIVO AVVERTO L'UTENTE **/
	                        if ( jQuery.isEmptyObject( appMappa.infoLayerClicked )  ){
	                        	appUtil.showMessageAlertApplication('Selezionare un punto dove sono presenti informazioni', 'Attenzione', false, true, false, null, null);
	                        	$("#layer-info-content").html("<h5>Seleziona un punto sulla mappa</h5>");
	                        }
	                        
	                        appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
						}, 1000);
					} else {
						appUtil.showMessageAlertApplication('Selezionare un punto dove sono presenti informazioni', 'Attenzione', false, true, false, null, null);
					}
				} else {
					return;
				}
			});
		},

		/**
		 * TODO: inserisci commento
		 * @param layerName
		 * @param title
		 */
		getLayerInfo					: function ( layerName ) {
			var result = null;
			
			var href = appUtil.getOrigin() + appConfig.geoserverService + "rest/workspaces/urbamid/layers/"+layerName+".json";
			if (href!=null) {
				
				/*Recupero le feature*/
				var responseLayerJson = appRest.restGeoserver(href);
				if (responseLayerJson!=null && responseLayerJson.layer!=null && 
				        responseLayerJson.layer.resource!=null && responseLayerJson.layer.resource.href!=null) {
					
				    var hrefDetailLayerJson = responseLayerJson.layer.resource.href;
			        if (hrefDetailLayerJson!=null) {
			        	
				        /*Recupero le feature*/
				        var responseDetailLayerJson = appRest.restGeoserver(hrefDetailLayerJson);  	
                        if (responseDetailLayerJson!=null && responseDetailLayerJson.featureType!=null &&
                                responseDetailLayerJson.featureType.keywords!=null && responseDetailLayerJson.featureType.keywords.string!=null) {
                        	
                        	result = responseDetailLayerJson.featureType.keywords.string;
                        }
				    }
				}
			}
			return result;
		},
		
		selectInfoLayer: function(layerTitle, layerName) {
			/**1. APERTURA POPUP**/
			appMappa.apriPopup(layerName, layerTitle, appMappa.infoPopupLayerClicked[layerName].coords, appMappa.infoPopupLayerClicked[layerName].valueFeaClick);
			
			/**AGGIUNGE LA FEATURE AL LAYER CON ZOOM**/
			var fea = appMappa.infoLayerClicked[layerName][0];
			
			selectLayerSource.clear();
			selectLayerSource.addFeature( fea );
			
			const extent = fea.getGeometry().getExtent();
			
			(appMappa.maps).getView().fit(
					extent, appMappa.maps.getSize(), { 
							duration: 1000}
					
			);
			
			appConfig.featureSelected.push( appConfig.featureVectorSelected );
		},
		
		
		/**
		 * TODO: inserisci commento
		 * @param idTabella
		 */
		abilitaSelectServiceGis				: function ( idTabella ) {
			
			(appMappa.maps).on("singleclick", function(evt) {
				
				if (appUrbamid.layerMap.length>0) {
					appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
					/** 1. IMPOSTO LA LISTA DEI LAYER ABILITATI PER EFFETTUARE UNA RICERCA**/
					layersRicerca = "urbamid:u_cat_particelle";
					/**2. RICHIESTA A GEOSERVER DEI LAYER ABILITATI**/
					var untiled = new ol.layer.Image({
						source: new ol.source.ImageWMS({
							ratio: 1,
							url: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceService + appConfig.wmsService,
							params: {'FORMAT': appConfig.acceptsFormatGeoserver, 'VERSION': '1.1.1', "LAYERS": layersRicerca, "exceptions": 'application/vnd.ogc.se_inimage'}
						})
					});
					
					var view = (appMappa.maps).getView();
					var viewResolution = view.getResolution();
					var source = untiled.get('visible') ? untiled.getSource() : tiled.getSource();
					var coord = new ol.proj.transform([evt.coordinate[0], evt.coordinate[1]], 'EPSG:4326', 'EPSG:4326');
					var href = source.getGetFeatureInfoUrl(coord, viewResolution, view.getProjection(),{'INFO_FORMAT': appConfig.acceptsFormatGeoserver, 'FEATURE_COUNT': appConfig.numFeatureSelect});
					if (href!=null) {
						
						var xhr = new XMLHttpRequest();
						xhr.open('GET', appUtil.replaceNameSpace(href) );
						xhr.onerror = "";
						xhr.onload = function() {
							if (xhr.status == 200 && xhr.responseText.indexOf("No LAYERS")==-1) {
								
								appConfig.featureOnClick = (appConfig.featureSourceSelected).getFormat().readFeatures(xhr.responseText);
								if ( appConfig.featureOnClick.length ==0 ){
									/*HO CLICCATO IN UNA ZONA DOVE NON E' PRESENTE UNA FEATURE*/
									appUtil.showMessageAlertApplication('Nessuna particella selezionata', 'Attenzione', false, true, false, null, null);
								} else if ( appConfig.listFeatureAdded[appConfig.featureOnClick[0].getId()]==null ) {
									/**
									 * HO CLICCATO IN UNA ZONA DOVE E' PRESENTE UNA FEATURE
									 * NON HO MAI AGGIUNTO LA FEATURE
									 **/
									//Inizializzo le variabili
									idTag = (appConfig.featureOnClick[0].getId()).replace(/\./g, '_');
									valueFea = appConfig.featureOnClick[0];
									keyFea = (appConfig.featureOnClick[0].getId());
									//Aggiungo la feature dalla lista delle feature aggiunte in mappa
									appConfig.listFeatureAdded[keyFea] = valueFea;
									//Aggiungo la feature alla mappa
									if ((appConfig.featureSourceSelected).getFeatureById(keyFea)==null)
										(appConfig.featureSourceSelected).addFeature( valueFea );
									//Aggiungo in tabella
									hmValue = valueFea.I;
									$("#"+idTabella+" tbody").append("<tr id='"+idTag+"'>" +
																		"<td>"+hmValue.codice_com+"</td><td>n.d.</td><td>"+hmValue.foglio+"</td><td>"+hmValue.mappale+"</td><td>n/d</td><td>n/d</td><td>n/d</td><td>n/d</td>" +
																		"<td class='rimuoviFeatureTable' onclick=\"rimuoviParticella('"+idTag+"','"+keyFea+"' )\"><i class=\"fa fa-times\" data-info=\"Rimuovi particella\"></i></td>"+
																	 "</tr>");
								} else  if ( appConfig.listFeatureAdded[ appConfig.featureOnClick[0].getId()]!=null ){
									/**
									 * HO CLICCATO IN UNA ZONA DOVE E' PRESENTE UNA FEATURE 
									 * HO GIà AGGIUNTO LA FEATURE
									 **/
									//Inizializzo le variabili
									idTag = (appConfig.featureOnClick[0].getId()).replace(/\./g, '_');
									valueFea = appConfig.featureOnClick[0];
									keyFea = (appConfig.featureOnClick[0].getId());
									//Rimuovo la feature dalla lista delle feature aggiunte in mappa
									delete appConfig.listFeatureAdded[keyFea];
									//Elimino la feature alla mappa
									if ((appConfig.featureSourceSelected).getFeatureById(keyFea)!=null)
										(appConfig.featureSourceSelected).removeFeature( (appConfig.featureSourceSelected).getFeatureById(keyFea) );
									//Elimino dalla tabella 
									$("#"+idTag).remove();
								}
							} else {
								onError();
							}
							appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
						}
						xhr.send();
						
						if ( appConfig.featureVectorSelected==null ) {
							appConfig.featureVectorSelected = new ol.layer.Vector({
								source	: appConfig.featureSourceSelected, 				
								style	: appConfig.featureStyleSelected.olStyle 		
							});
							/*EVIDENZIO SU MAPPA IL SELEZIONATO*/
							(appMappa.maps).addLayer(appConfig.featureVectorSelected);
							appConfig.featureSelected.push( appConfig.featureVectorSelected );
						}
					}
				} else {
					/*HO CLICCATO IN MAPPA MA NON SONO ABILITATI I LAYER */
					appUtil.showMessageAlertApplication('Abilitare i dati dal visualizzatore', 'Attenzione', false, true, false, null, null);
				}
				
			});
		}, 

		/**
		 * TODO: inserisci commento
		 * Abilita l'aggiunta del popup quando si passa con il mouse sul layer passato come input. Il nome è utile per costruire il contenuto del popup 
		 * @param layerName, nome del layer per il quale abilitare l'hover
		 */
		abilitaClusterHover					: function ( ) {
		    /**3. Aggiungo l'event mouse hover sulle feature**/
			var cursorHoverStyle 	= "pointer";
			var target 				= (appMappa.maps).getTarget();
			var jTarget 			= typeof target === "string" ? $("#"+target) : $(target);
			
		    var feature_onHover = false
		    appMappa.maps.on("pointermove", function(evt) {
		    	//Chiudo il popup precedentemente aperto
				appMappa.chiudiPopupCluster();
				//Recupero le feature sotto al puntatore
				feature_onHover = (appMappa.maps).forEachFeatureAtPixel(evt.pixel, function(feature, layer) {
					return feature;
				});
				//Sono su una feature
				if (feature_onHover) {
					//Cambio il cursore sulla feature
					jTarget.css("cursor", cursorHoverStyle);
					//HOVER VISUALIZZA IL POPUP SOLO SU CLUSTER DI MAX 6 FEATURE
					if ( feature_onHover.getProperties().features.length<6 && feature_onHover.getProperties().features.length>0 ) {
						//1. Contenuto del POPUP
						var popup 		= {};
						dataFeatures 	= [];
						$.each( feature_onHover.getProperties().features, function( keyFea, valueFea ) {
							name = (clusterLayerAddedMap[ (valueFea.a).split(".")[0] ].I).name;
							dataFeature 		= {"name":name};
							popup.layerName 	= (valueFea.a).split(".")[0];
							listaField 			= [];	
							$.each( valueFea.I, function( keyPro, valuePro ) {
								if (appMappa.layerFieldsCaricati[popup.layerName+"_viewField_"+keyPro]!=null){
									field 			= {};
									field.label 	= appMappa.layerFieldsCaricati[popup.layerName+"_viewField_"+keyPro];
									field.value 	= valuePro
									listaField		.push(field);
								}
							});
							dataFeature.data 	= listaField;
							dataFeatures		.push(dataFeature);
						});
						popup.dataFeatures 		= dataFeatures;
						var sourcePopup 		= document.getElementById("popupClusterTemplate").innerHTML;
						var templatePopup 		= Handlebars.compile(sourcePopup);
					    outputPopup 			= templatePopup(popup);
						$("#popupClusterLayer").html(outputPopup);
						//2. Grafica del POPUP
						appMappa.overlayCluster.setPosition(evt.coordinate);
			         	if ( $("#popupClusterLayer").hasClass("hidden") )
							$("#popupClusterLayer").removeClass("hidden");
					} else {
						//1. Imposto ad hidden il POPUP
						if ( !$("#popupClusterLayer").hasClass("hidden") )
							$("#popupClusterLayer").addClass("hidden");
			      	}
				}
				//Non sono su una feature
				else {
					//Imposto il cursore di default
					jTarget.css("cursor", "");
				}
			});
		},

		/**
		 * TODO: inserisci commento
		 * @param idTag
		 * @param keyFea
		 */
		rimuoviParticella					: function ( idTag, keyFea ){
			//Rimuovo la feature dalla lista delle feature aggiunte in mappa
			delete appConfig.listFeatureAdded[keyFea];
			//Elimino la feature alla mappa
			if ((appConfig.featureSourceSelected).getFeatureById(keyFea)!=null)
				(appConfig.featureSourceSelected).removeFeature( (appConfig.featureSourceSelected).getFeatureById(keyFea) );
			//Elimino dalla tabella 
			$("#"+idTag).remove();
		},

		/**
		 * Disabilita tutti i layers che non hanno la proprietà isBaseLayer (come ortofoto o layer base)
		 */
		deselezionaTuttiLayer: function () {
			let layersInSessione = JSON.parse(sessionStorage.getItem('layersInSessione'));
			for (var i in appUrbamid.layerMap) {
				var layer = appUrbamid.layerMap[i];
				if (layer.I.isBaseLayer != true) {
					$('#' + i + '_chk').click();
				}
			}
		},
		
		/**
		 * Disabilita tutti i layers
		 */
		deselezionaTuttiLayerCompletamente: function () {
			let layersInSessione = JSON.parse(sessionStorage.getItem('layersInSessione'));
			for (var i in appUrbamid.layerMap) {
				var layer = appUrbamid.layerMap[i];
				$('#' + i + '_chk').click();
			}
		}
};