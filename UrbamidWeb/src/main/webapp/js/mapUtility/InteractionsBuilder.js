/** ==================================================================================== **/
/**  GESTIONE CONTROLLI COSTRUTTORE : ol.interactions **/
/** ==================================================================================== **/

var InteractionsBuilder = InteractionsBuilder || {};

/** 
 * Le azioni degli utenti che modificano lo stato della mappa. Alcuni sono simili ai controlli, ma non sono associati a un elemento DOM. 
 * Ad esempio, ol.interaction.Keyboard Zoom è funzionalmente lo stesso di ol.control.Zoom, ma innescata da un evento di tastiera non da un pulsante.
 * 
 * @param {@link ol.Map} map2DInstance
 * @param {@link olcs.OLCesium} map3DInstance
 * @param {@link ClassBuilder.MapOptions} OPTIONS
 * **/
InteractionsBuilder.buildAdvancedInteractions = function(map2DInstance, map3DInstance, OPTIONS){
 try{
	
	
		  
 }catch(e){
	console.log("Error build interactions " + e);
 }
};
 

/**
 * Currently drawn feature.
 * @type {ol.Feature}
 */
var sketchLength;

/**
 * Attiva/disattiva lo strumento per disegnare su mappa e misurare la lunghezza dell'arco tracciato.
 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
 * 
 * @param {@link Boolean} active, true o false a seconda se sto attivando o disattivando il widget
 * @param {@link ol.Map} map2DInstance
 * @param {@link ClassBuilder.MapOptions} options
 **/
InteractionsBuilder.olMeasureLength = function(active, map2DInstance, options) {
 /** INTERACTION ATTIVA **/
 if(active){
	/** recupero il layer **/
	var layer = map2DInstance.getLayerByName(ol.ApplicationProperty.NAME_LAYER_MEASURE_LENGTH);
		  
	if(layer == null) {
		/** Il layer non esiste, quindi lo creo e lo aggiungo alla mappa **/
		layer = FunctionBuilder.olCreaLayerVectorToMeasureLength();
		// add layer map
		map2DInstance.addLayer(layer);
		/**
		 * Triggered when a pointer is moved. Note that on touch devices this is triggered when the map is panned, so is not the same as mousemove.
		 * 
		 * @param {@link string | Array.<string>} type, The event type or array of event types.
		 * @param {@link function} listener, The listener function.
		 * @param {@link Object} this, The object to use as this in listener.
		 */
		map2DInstance.on('pointermove', FunctionBuilder.olPointerMoveHandlerToMeasureLength, options);
	}  
		
	/** recupero interaction **/
	var interaction = map2DInstance.getInteractionById(ol.ApplicationProperty.ID_INTERACTION_DRAW_MEASURE_LENGTH);
	
	if(interaction == null){
		/** l'interaction non esiste, quindi la creo e l'aggiungo alla mappa **/
		interaction = FunctionBuilder.olCreaInteractionDrawToMeasureLength(layer.getSource());
		/** add **/
		map2DInstance.addInteraction(interaction);
		
		/**
		 * Triggered upon feature draw start
		 */
		interaction.on('drawstart', function(evt) {
		    // set sketchLength
		    sketchLength = evt.feature;
		}, this);
		
		/**
		 * Triggered upon feature draw end
		 */
		interaction.on('drawend', function(evt) {
			/** end draw cambio il css class alla tooltip **/
			var measureTooltipElement = document.getElementById("measureTooltipElement");
				measureTooltipElement.className = 'tooltip-m tooltip-static';
				  
			/** Overlay to show the measurement **/
			var measureTooltip = map2DInstance.getOverlayById(ol.ApplicationProperty.ID_OVERLAY_MEASURE_LENGTH_TOOLTIP);
			 	measureTooltip.setOffset([0, -7]);
			 	
			// unset sketchLength
			sketchLength = null;
			// unset tooltip so that a new one can be created
			measureTooltipElement = null;
			
			/**
			 * Reinizializza il tooltip della misura
			 */
			FunctionBuilder.createMeasureTooltip(map2DInstance, ol.ApplicationProperty.ID_OVERLAY_MEASURE_LENGTH_TOOLTIP, "measureTooltipElement");
		}, this);
	}
   /**
	* Creates a new measure tooltip per quando è attivo il controllo per misurare la lunghezza della
	* linea tracciata o l'area.
    */
   FunctionBuilder.createMeasureTooltip(map2DInstance, ol.ApplicationProperty.ID_OVERLAY_MEASURE_LENGTH_TOOLTIP, "measureTooltipElement");
   /**
	* Creates a new help tooltip per quando è attivo il controllo per misurare la lunghezza della
    * linea tracciata o l'area.
	*/
   FunctionBuilder.createHelpTooltipMeasure(map2DInstance, ol.ApplicationProperty.ID_OVERLAY_HELP_MESS_MEASURE_LENGTH, "helpTooltipElement");
 }else{
   /** INTERACTION DISATTIVA **/
	 
   /** 
    * Unlisten type of event.
	*/
   map2DInstance.un('pointermove', FunctionBuilder.olPointerMoveHandlerToMeasureLength, options);
		
   /** Remove Overlay to show the help messages. **/
   var helpTooltip = map2DInstance.getOverlayById(ol.ApplicationProperty.ID_OVERLAY_HELP_MESS_MEASURE_LENGTH);
		  	
   if(helpTooltip != undefined && helpTooltip != null) map2DInstance.removeOverlay(helpTooltip);

   /** Remove The measure tooltip element **/
   $("div").remove( "#measureTooltipElement" );
	  
   /** recupero interaction **/
   var interaction = map2DInstance.getInteractionById(ol.ApplicationProperty.ID_INTERACTION_DRAW_MEASURE_LENGTH);
   /** remove interaction **/	
   map2DInstance.removeInteraction(interaction);
	
   /** recupero il layer **/
   var layer = map2DInstance.getLayerByName(ol.ApplicationProperty.NAME_LAYER_MEASURE_LENGTH);
   /** remove layer **/	
   map2DInstance.removeLayer(layer);
 }// close else 
};


/**
 * Currently drawn feature.
 * @type {ol.Feature}
 */
var sketchArea;

/**
 * Un controllo che attiva/disattiva lo strumento per disegnare su mappa un poligono e misurarne la grandezza della superficie.
 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
 *
 * @param {@link Boolean} active, true o false a seconda se sto attivando o disattivando il widget
 * @param {@link ol.Map} map2DInstance
 * @param {@link ClassBuilder.MapOptions} options
 **/
InteractionsBuilder.olMeasureArea = function(active, map2DInstance, options) {
 /** INTERACTION ATTIVA **/
 if(active){
	/** recupero il layer **/
	var layer = map2DInstance.getLayerByName(ol.ApplicationProperty.NAME_LAYER_MEASURE_AREA);
		  
	if(layer == null) {
		/** Il layer non esiste, quindi lo creo e lo aggiungo alla mappa **/
		layer = FunctionBuilder.olCreaLayerVectorToMeasureArea();
		// add layer map
		map2DInstance.addLayer(layer);
		/**
		 * Triggered when a pointer is moved. Note that on touch devices this is triggered when the map is panned, so is not the same as mousemove.
		 * 
		 * @param {@link string | Array.<string>} type, The event type or array of event types.
		 * @param {@link function} listener, The listener function.
		 * @param {@link Object} this, The object to use as this in listener.
		 */
		map2DInstance.on('pointermove', FunctionBuilder.olPointerMoveHandlerToMeasureArea, options);
	}  
		
	/** recupero interaction **/
	var interaction = map2DInstance.getInteractionById(ol.ApplicationProperty.ID_INTERACTION_DRAW_MEASURE_AREA);
	
	if(interaction == null){
		/** l'interaction non esiste, quindi la creo e l'aggiungo alla mappa **/
		interaction = FunctionBuilder.olCreaInteractionDrawToMeasureArea(layer.getSource());
		/** add **/
		map2DInstance.addInteraction(interaction);
		
		/**
		 * Triggered upon feature draw start
		 */
		interaction.on('drawstart', function(evt) {
		    // set sketchLength
		    sketchLength = evt.feature;
		}, this);
		
		/**
		 * Triggered upon feature draw end
		 */
		interaction.on('drawend', function(evt) {
			/** end draw cambio il css class alla tooltip **/
			var measureTooltipElement = document.getElementById("measureTooltipAreaElement");
				measureTooltipElement.className = 'tooltip-m tooltip-static';
				  
			/** Overlay to show the measurement **/
			var measureTooltip = map2DInstance.getOverlayById(ol.ApplicationProperty.ID_OVERLAY_MEASURE_AREA_TOOLTIP);
			 	measureTooltip.setOffset([0, -7]);
			 	
			// unset sketchLength
			sketchLength = null;
			// unset tooltip so that a new one can be created
			measureTooltipElement = null;
			
			/**
			 * Reinizializza il tooltip della misura
			 */
			FunctionBuilder.createMeasureTooltip(map2DInstance, ol.ApplicationProperty.ID_OVERLAY_MEASURE_AREA_TOOLTIP, "measureTooltipAreaElement");
		}, this);
	}
   /**
	* Creates a new measure tooltip per quando è attivo il controllo per misurare la lunghezza della
	* linea tracciata o l'area.
    */
   FunctionBuilder.createMeasureTooltip(map2DInstance, ol.ApplicationProperty.ID_OVERLAY_MEASURE_AREA_TOOLTIP, "measureTooltipAreaElement");
   /**
	* Creates a new help tooltip per quando è attivo il controllo per misurare la lunghezza della
    * linea tracciata o l'area.
	*/
   FunctionBuilder.createHelpTooltipMeasure(map2DInstance, ol.ApplicationProperty.ID_OVERLAY_HELP_MESS_MEASURE_AREA, "helpTooltipAreaElement");
 }else{
   /** INTERACTION DISATTIVA **/
	 
   /** 
    * Unlisten type of event.
	*/
   map2DInstance.un('pointermove', FunctionBuilder.olPointerMoveHandlerToMeasureArea, options);
		
   /** Remove Overlay to show the help messages. **/
   var helpTooltip = map2DInstance.getOverlayById(ol.ApplicationProperty.ID_OVERLAY_HELP_MESS_MEASURE_AREA);
		  	
   if(helpTooltip != undefined && helpTooltip != null) map2DInstance.removeOverlay(helpTooltip);

   /** Remove The measure tooltip element **/
   $("div").remove( "#measureTooltipAreaElement" );
	  
   /** recupero interaction **/
   var interaction = map2DInstance.getInteractionById(ol.ApplicationProperty.ID_INTERACTION_DRAW_MEASURE_AREA);
   /** remove interaction **/	
   map2DInstance.removeInteraction(interaction);
	
   /** recupero il layer **/
   var layer = map2DInstance.getLayerByName(ol.ApplicationProperty.NAME_LAYER_MEASURE_AREA);
   /** remove layer **/	
   map2DInstance.removeLayer(layer);
 }// close else 
};
