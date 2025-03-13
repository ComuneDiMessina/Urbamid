/** ==================================================================================== **/
/** Funzioni di utilità per la gestione della mappa.  **/
/** ==================================================================================== **/

var FunctionBuilder = FunctionBuilder || {};


/**
 * Ritorna un ol.Coordinate (senza modificare i punti originali), ma convertendo la longitudine e la latitudine dalla proiezione di origine 
 * a quella di destinazione.
 * 
 * @param {@link Double} lon	
 * @param {@link Double} lat
 * @param {#link ol.proj.ProjectionLike} source
 * @param {#link ol.proj.ProjectionLike} destination
 * 
 * Returns:  
 * 		{@link ol.Coordinate} Coordinate | null. 
 */
FunctionBuilder.olProjTransorm = function(lon, lat, sourceProjection, destProjection){
 try{
	if(lon != null && lat != null && sourceProjection != null && destProjection != null){
		return new ol.proj.transform([lon, lat], sourceProjection, destProjection);
	}
	else
		return null;
 }catch(e){
	 console.log(e);
	 return null;
 }
};


/**
 * Ritorna un ol.Coordinate (senza modificare i punti originali), ma convertendo la longitudine e la latitudine dalla proiezione di origine 
 * a quella di destinazione.
 * 
 * @param {@link ol.Extent{Array.<number>} Un array di numeri: [minx, miny, maxx, maxy].
 * @param {#link ol.proj.ProjectionLike} source
 * @param {#link ol.proj.ProjectionLike} destination
 * 
 * Returns:  
 * 		{@link ol.Coordinate} Coordinate | null. 
 */
FunctionBuilder.olProjTransormExtent = function(extent, sourceProjection, destProjection){
 try{
	if(extent != null && sourceProjection != null && destProjection != null){
		return new ol.proj.transformExtent(extent, sourceProjection, destProjection);
	}
	else
		return null;
 }catch(e){
	 console.log(e);
	 return null;
 }
};


/**
 * Ritorna la geometria del punto.
 * 
 * @param {@link ol.Coordinate} coordinates.
 * @param {@link ol.geom.GeometryLayout} layout
 * 		  
 * NOTA: 
 * ol.geom.GeometryLayout{string}
 *  Il layout di coordinate per geometrie, che indica se un 3° o 4 ° Z ('Z') o la misura ('M') coordinate è disponibile. 
 *  I valori supportati sono 'XY', 'XYZ', 'XYM', 'XYZM'.
 * 
 * Returns:  
 * 	  {@link ol.geom.Point} Coordinate | null. 
 */
FunctionBuilder.olCoordinateToPoint = function (coordinates, layout){
 try{
	 
	 return new ol.geom.Point(coordinates, layout);
 
 }catch(e){
   console.log(e);
   return null;
 }
};

/**
 * Ritorna il layer vector appositamente configurato per la visualizzazione su mappa
 * della posizione gelocalizzata dell'utente.
 * 
 * Returns:  
 * 	  {@link ol.layer.Vector} Vector | null. 
 */
FunctionBuilder.olCreaLayerVectorToGeolocate = function (){
   var iconStyle = new ol.style.Style({
		image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
		   /** Anchor. Default value is [0.5, 0.5] (icon center). **/
		   anchor: [0.5, 0.5],
		   /** 
		    * Units in which the anchor x value is specified. A value of 'fraction' indicates the x value is a fraction of the icon.
		    * A value of 'pixels' indicates the x value in pixels. Default is 'fraction'. **/
		   anchorXUnits: 'fraction',
		   /**
		    * Units in which the anchor y value is specified. A value of 'fraction' indicates the y value is a fraction of the icon. 
		    * A value of 'pixels' indicates the y value in pixels. Default is 'fraction'.
		    */
	       anchorYUnits: 'pixels',
		   opacity: 1,
		   src: './images/marker/marker_add_red.png', // TODO DECIDERE QUALE USARE
		   /** Icon size in pixel. **/
//		   size: [24, 24],
		   rotateWithView: true
		}))
   });
	
   var simple = new ol.style.Style({
       image: new ol.style.Circle({
           /** Circle radius. **/
	       radius: 7,
	       snapToPixel: false,
	       /** Fill style. **/
	       fill: new ol.style.Fill({color: 'blue'}),
	       /** Stroke style.**/
	       stroke: new ol.style.Stroke({
	           color: 'white', width: 2
	       })
       })
   });
	
   /** **/
   var vector = new ol.layer.Vector({
	   	/** The bounding extent for layer rendering. The layer will not be rendered outside of this extent. **/
//		extent: FunctionBuilder.olProjTransormExtent(options.extent, options.sourceProjection, options.destProjection),
		source: new ol.source.Vector({ features: [] }),
	    /** The feature style. ol.style.Style | Array.<ol.style.Style> | ol.FeatureStyleFunction **/
		style: simple,
		/** Nome del layer **/
		name: ol.ApplicationProperty.NAME_LAYER_GEOLOCATION
   });
   
   return vector;
};

/**
 * Ritorna il layer vector appositamente configurato per la visualizzazione su mappa
 * della feature della geometria(Linestring) disegnata e della quale si vuole conoscere la
 * lunghezza.
 * 
 * Returns:  
 * 	  {@link ol.layer.Vector} Vector | null. 
 */
FunctionBuilder.olCreaLayerVectorToMeasureLength = function (){
	
   var simple = 
	   new ol.style.Style({
		   fill: new ol.style.Fill({
			   color: 'rgba(255, 255, 255, 0.2)'
		   }),
		   stroke: new ol.style.Stroke({
			   color: '#ffcc33',
			   width: 2
		   }),
		   image: new ol.style.Circle({
			   radius: 7,
			   fill: new ol.style.Fill({
				   color: '#ffcc33'
			   })
		   })
	   });
	
   /** **/
   var vector = new ol.layer.Vector({
		source: new ol.source.Vector({ features: [] }),
	    /** The feature style. ol.style.Style | Array.<ol.style.Style> | ol.FeatureStyleFunction **/
		style: simple,
		/** Nome del layer **/
		name: ol.ApplicationProperty.NAME_LAYER_MEASURE_LENGTH
	});
   
   return vector;
};

/**
 * Evento Attivato quando il puntatore viene spostato sulla mappa per tracciare la linea. 
 * 
 * @param {ol.MapBrowserEvent} evt
 * @param {@link Object} this, The object to use as this in listener.
 */
FunctionBuilder.olPointerMoveHandlerToMeasureLength = function (evt){
  if (evt.dragging) return;
  var options = $(this)[0];

  /** @type {string} - Messaggio da mostrare quando l'utente attiva il controllo. */
  var helpMsg = jQuery.i18n.prop('OpenLayers.olMeasure.helpMsg');
  /** @type {ol.Coordinate|undefined} */
  var tooltipCoord = evt.coordinate;

  if(sketchLength){
	  var output;
	  var geom = (sketchLength.getGeometry());
	  
	  if (geom instanceof ol.geom.LineString) {
	      output = FunctionBuilder.formatLengthMeasure(geom); /** @type {ol.geom.LineString} */ 
	      /** Messaggio da mostrare quando l'utente sta disegnando una linea. **/
	      helpMsg = jQuery.i18n.prop('OpenLayers.olMeasure.continueLineMsg');
	      tooltipCoord = geom.getLastCoordinate();
	  }
	  
	  var measureTooltipElement = document.getElementById("measureTooltipElement");
		  measureTooltipElement.innerHTML = output;
		  
	  /** Overlay to show the measurement **/
	  var measureTooltip = evt.map.getOverlayById(ol.ApplicationProperty.ID_OVERLAY_MEASURE_LENGTH_TOOLTIP);
		  measureTooltip.setPosition(tooltipCoord);
  }
  
  var helpTooltipElement = document.getElementById("helpTooltipElement");
  	  helpTooltipElement.innerHTML = helpMsg;
  	  
  /**  Overlay to show the help messages. **/
  var helpTooltip = evt.map.getOverlayById(ol.ApplicationProperty.ID_OVERLAY_HELP_MESS_MEASURE_LENGTH);
  	  helpTooltip.setPosition(evt.coordinate);
};

/**
 * Ritorna lo strumento di disegno appositamente configurato per tracciare linee
 * su mappa. Le feature disegnate saranno aggiunte al layer fornito come parametro d'ingresso.
 * 
 * @param {@link ol.source.Vector}
 * Returns:  
 * 	  {@link ol.interaction.Draw} Draw | null. 
 */
FunctionBuilder.olCreaInteractionDrawToMeasureLength = function (layer){
	
	var draw = new ol.interaction.Draw({
	    source: layer,
	    type: /** @type {ol.geom.GeometryType} */ ('LineString'),
	    style: new ol.style.Style({
	    	fill: new ol.style.Fill({
		        color: 'rgba(255, 255, 255, 0.2)'
		    }),
		    stroke: new ol.style.Stroke({
		        color: 'rgba(0, 0, 0, 0.5)',
		        lineDash: [10, 10],
		        width: 2
		    }),
		    image: new ol.style.Circle({
		        radius: 5,
		        stroke: new ol.style.Stroke({
		           color: 'rgba(0, 0, 0, 0.7)'
		        }),
		        fill: new ol.style.Fill({
		           color: 'rgba(255, 255, 255, 0.2)'
		        })
		    })
	    })
	});
	
  draw.setId(ol.ApplicationProperty.ID_INTERACTION_DRAW_MEASURE_LENGTH);
	
  return draw;
};

/**
 * Creates a new help tooltip per quando è attivo il controllo per misurare la lunghezza della
 * linea tracciata o l'area. Tooltip con un messaggio informativo che vuole essere da guida quando
 * si disegna su mappa.
 * 
 * @param {@link ol.Map} map2DInstance
 */
FunctionBuilder.createHelpTooltipMeasure = function (map2DInstance, idOverlay, idElement) {
  var helpTooltipElement = document.getElementById(idElement);
	
  if(helpTooltipElement != undefined) helpTooltipElement.parentNode.removeChild(helpTooltipElement);

  /**
   * The help tooltip element.
   * @type {Element}
   */
  helpTooltipElement = document.createElement('div');
  helpTooltipElement.className = 'tooltip-m';
  helpTooltipElement.id = idElement;
  
  /** Overlay to show the help messages. **/
  var helpTooltip = map2DInstance.getOverlayById(idOverlay);
	  	
  if(helpTooltip != undefined && helpTooltip != null) map2DInstance.removeOverlay(helpTooltip);
  
  /**
   * @type {ol.Overlay}
   */
  helpTooltip = new ol.Overlay({
	 element: helpTooltipElement,
	 offset: [15, 0],
	 positioning: "center-left"
  });
  helpTooltip.setId(idOverlay);
  
  map2DInstance.addOverlay(helpTooltip);
};

/**
 * Creates a new measure tooltip per quando è attivo il controllo per misurare la lunghezza della
 * linea tracciata o l'area. Tooltip con la lunghezza dell'arco tracciato fino a quel momento.
 * 
 * @param {@link ol.Map} map2DInstance
 */
FunctionBuilder.createMeasureTooltip = function (map2DInstance, idOverlay, idElement) {
  /**
   * The measure tooltip element.
   * @type {Element}
   */
  var measureTooltipElement = document.createElement('div');
      measureTooltipElement.className = 'tooltip-m tooltip-measure';
      measureTooltipElement.id = idElement;
  
  /** Overlay to show the measurement **/
  var measureTooltip = map2DInstance.getOverlayById(idOverlay);
  
  /**
   * Overlay to show the measurement.
   * @type {ol.Overlay}
   */
   measureTooltip = new ol.Overlay({
	    element: measureTooltipElement,
	    offset: [0, -15],
	    positioning: "bottom-center"
  });
  measureTooltip.setId(idOverlay);
   
  map2DInstance.addOverlay(measureTooltip);
};

/**
 * Format length output
 * 
 * @param {ol.geom.LineString} line
 * @return {string}
 */
FunctionBuilder.formatLengthMeasure = function(line) {

  	var length;
    var coordinates = line.getCoordinates();
    length = 0;
    var sourceProj = appMappa.maps.getView().getProjection();
    for (var i = 0, ii = coordinates.length - 1; i < ii; ++i) {
    	var wgs84Sphere = new ol.Sphere(6378137);
		var c1 = ol.proj.transform(coordinates[i], sourceProj, 'EPSG:4326');
      	var c2 = ol.proj.transform(coordinates[i + 1], sourceProj, 'EPSG:4326');
      	length += wgs84Sphere.haversineDistance(c1, c2);
    }
  	var output;
  	if (length > 1000) {
	    output = (Math.round(length / 1000 * 100) / 100) + ' ' + 'km';
  	} else {
	    output = (Math.round(length * 100) / 100) + ' ' + 'm';
  	}
  	return output;
};


/**
 * Ritorna il layer vector appositamente configurato per la visualizzazione su mappa
 * della feature della geometria(Polygon) disegnata e della quale si vuole conoscere l'area.
 * 
 * Returns:  
 * 	  {@link ol.layer.Vector} Vector | null. 
 */
FunctionBuilder.olCreaLayerVectorToMeasureArea = function (){
	
   var simple = 
	   new ol.style.Style({
		   fill: new ol.style.Fill({
			   color: 'rgba(255, 255, 255, 0.2)'
		   }),
		   stroke: new ol.style.Stroke({
			   color: '#ffcc33',
			   width: 2
		   }),
		   image: new ol.style.Circle({
			   radius: 7,
			   fill: new ol.style.Fill({
				   color: '#ffcc33'
			   })
		   })
	   });
	
   /** **/
   var vector = new ol.layer.Vector({
		source: new ol.source.Vector({ features: [] }),
	    /** The feature style. ol.style.Style | Array.<ol.style.Style> | ol.FeatureStyleFunction **/
		style: simple,
		/** Nome del layer **/
		name: ol.ApplicationProperty.NAME_LAYER_MEASURE_AREA
	});
   
   return vector;
};


/**
 * Evento Attivato quando il puntatore viene spostato sulla mappa per tracciare l'area. 
 * 
 * @param {ol.MapBrowserEvent} evt
 * @param {@link Object} this, The object to use as this in listener.
 */
FunctionBuilder.olPointerMoveHandlerToMeasureArea = function (evt){
  if (evt.dragging) return;
  var options = $(this)[0];

  /** @type {string} - Messaggio da mostrare quando l'utente attiva il controllo. */
  var helpMsg = jQuery.i18n.prop('OpenLayers.olMeasure.helpMsg');
  /** @type {ol.Coordinate|undefined} */
  var tooltipCoord = evt.coordinate;

  if(sketchLength){
	  var output;
	  var geom = (sketchLength.getGeometry());
	  
	  if (geom instanceof ol.geom.Polygon) {
	      output = FunctionBuilder.formatLengthArea(geom); /** @type {ol.geom.LineString} */ 
	      /** Messaggio da mostrare quando l'utente sta disegnando una linea. **/
	      helpMsg = jQuery.i18n.prop('OpenLayers.olMeasure.continueAreaMsg');
	      tooltipCoord = geom.getLastCoordinate();
	  }
	  
	  var measureTooltipElement = document.getElementById("measureTooltipAreaElement");
		  measureTooltipElement.innerHTML = output;
		  
	  /** Overlay to show the measurement **/
	  var measureTooltip = evt.map.getOverlayById(ol.ApplicationProperty.ID_OVERLAY_MEASURE_AREA_TOOLTIP);
		  measureTooltip.setPosition(tooltipCoord);
  }
  
  var helpTooltipElement = document.getElementById("helpTooltipAreaElement");
  	  helpTooltipElement.innerHTML = helpMsg;
  	  
  /**  Overlay to show the help messages. **/
  var helpTooltip = evt.map.getOverlayById(ol.ApplicationProperty.ID_OVERLAY_HELP_MESS_MEASURE_AREA);
  	  helpTooltip.setPosition(evt.coordinate);
};


/**
 * Format length output
 * @param {ol.geom.Polygon} polygon
 * @return {string}
 */
FunctionBuilder.formatLengthArea = function(polygon) {
    var area;
	var wgs84Sphere = new ol.Sphere(6378137);
	var sourceProj = appMappa.maps.getView().getProjection();
	var geom = /** @type {ol.geom.Polygon} */(polygon.clone().transform(sourceProj, 'EPSG:4326'));
	var coordinates = geom.getLinearRing(0).getCoordinates();
	area = Math.abs(wgs84Sphere.geodesicArea(coordinates));
  	var output;
  	if (area > 10000) {
    	output = (Math.round(area / 1000000 * 100) / 100) + ' ' + 'km<sup>2</sup>';
  	} else {
    	output = (Math.round(area * 100) / 100) + ' ' + 'm<sup>2</sup>';
  	}
  	return output;
};


/**
 * Ritorna lo strumento di disegno appositamente configurato per tracciare aree
 * su mappa. Le feature disegnate saranno aggiunte al layer fornito come parametro d'ingresso.
 * 
 * @param {@link ol.source.Vector}
 * Returns:  
 * 	  {@link ol.interaction.Draw} Draw | null. 
 */
FunctionBuilder.olCreaInteractionDrawToMeasureArea = function (layer){
	
	var draw = new ol.interaction.Draw({
	    source: layer,
	    type: /** @type {ol.geom.GeometryType} */ ('Polygon'),
	    style: new ol.style.Style({
	    	fill: new ol.style.Fill({
		        color: 'rgba(255, 255, 255, 0.2)'
		    }),
		    stroke: new ol.style.Stroke({
		        color: 'rgba(0, 0, 0, 0.5)',
		        lineDash: [10, 10],
		        width: 2
		    }),
		    image: new ol.style.Circle({
		        radius: 5,
		        stroke: new ol.style.Stroke({
		           color: 'rgba(0, 0, 0, 0.7)'
		        }),
		        fill: new ol.style.Fill({
		           color: 'rgba(255, 255, 255, 0.2)'
		        })
		    })
	    })
	});
	
  draw.setId(ol.ApplicationProperty.ID_INTERACTION_DRAW_MEASURE_AREA);
	
  return draw;
};
