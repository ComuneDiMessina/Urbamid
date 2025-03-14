/** ==================================================================================== **/
/**  GESTIONE CONTROLLI COSTRUTTORE : ol.control **/
/** ==================================================================================== **/

/**
 * Class: ControlsBuilder 
 * Al click, la function è eseguita.
 * 
 * Inherits from:
 *  - <ol.Control>
 *
 * Uso:
 * (code)
 * var map = ...
 * var control = new ControlsBuilder.<nome_function>(<param>);
 * map.addControl(control);
 * (end)
 */
var ControlsBuilder = ControlsBuilder || {};
    
/**
 * Variabile in cui conservo quando attivo l'instanza del controllo di geolocalizzazione.
 */
ControlsBuilder.geolocation = null;

/********************************************************/
/*                                                      */
/*               Control Functions                      */
/*                                                      */
/*    Implementazione delle funzioni per aggiungere     */
/*      i controlli (ol.Controls) alla mappa            */
/* 	    e per rimuoverli dalla stessa.					*/
/*                                                      */
/********************************************************/ 


/** 
 * Un controllo è un widget visibile con un elemento DOM in una posizione fissa sullo schermo. 
 * Essi possono coinvolgere l'input dell'utente (pulsanti), o essere solo informativo; la posizione è determinato 
 * utilizzando i CSS. Per impostazione predefinita, questi vengono inseriti nel contenitore con nome della classe 
 * CSS "ol-overlaycontainer-stopevent", ma è possibile utilizzare qualsiasi elemento DOM di fuori.
 * 
 * @param {@link ol.Map} map2DInstance
 * @param {@link olcs.OLCesium} map3DInstance
 * @param {@link ClassBuilder.MapOptions} OPTIONS
 * **/
ControlsBuilder.buildAdvancedControls = function(map2DInstance, map3DInstance, OPTIONS){
 try{
	// loading ..
	appUtil.showLoader(null, jQuery.i18n.prop("Loader.buildControlMap"));
	 
	/**
	 * Verifico se e quali controlli non di default aggiungere alla mappa
	 */
//	if(OPTIONS.enableFullscreen) map2DInstance.addControl(ControlsBuilder.fullScreenControls());
	if(OPTIONS.enableMousePosition) map2DInstance.addControl(ControlsBuilder.mousePositionControls(OPTIONS));
	  
	if(OPTIONS.enableZoomSlider && OPTIONS.enableZoom) {
		/**
		 * Quando è attivo sia lo zoom base che quello con lo slider, attivo l'opzione che permette di swichare da
		 * una modalità di zoom all'altra.
		 */
		$('.ol-zoom-in, .ol-zoom-out').tooltip({
			  placement: 'left',
			  delay: { "show": 0, "hide": 800 },
			  html: true
		});
		/** Gestione cambio modalità di zoom con e senza slider. **/
		ControlsBuilder.changeInOutSliderZoom('DEFAULT', map2DInstance);
	}else
		if(OPTIONS.enableZoomSlider) {
		 	 $(".ol-overlaycontainer-stopevent").addClass("full-tools");
		 	 map2DInstance.addControl(ControlsBuilder.zoomSliderControls());
		}
	  
	if(OPTIONS.enableZoomToExtent) map2DInstance.addControl(ControlsBuilder.zoomToExtentControls(OPTIONS));
	if(OPTIONS.enable3D) map2DInstance.addControl(ControlsBuilder.ol3DControls(map3DInstance));
	if(OPTIONS.enableLayersTree) map2DInstance.addControl(ControlsBuilder.olLayersControls(OPTIONS));
	if(OPTIONS.enableGeolocation) map2DInstance.addControl(ControlsBuilder.olGeolocationControls(map2DInstance, OPTIONS));
	if(OPTIONS.enableOverviewMap) map2DInstance.addControl(ControlsBuilder.overviewMapControls());
	if(OPTIONS.enableScaleLine) map2DInstance.addControl(ControlsBuilder.scaleLineControls());
	if(OPTIONS.enableMeasureLength) map2DInstance.addControl(ControlsBuilder.olMeasureLengthControls(map2DInstance, OPTIONS));
	if(OPTIONS.enableMeasureArea) map2DInstance.addControl(ControlsBuilder.olMeasureAreaControls(map2DInstance, OPTIONS));
	
	/**
	 * Active tooltip on button map
	 */
//	$(".ol-full-screen button, .ol-mouse-position button, .ol-zoom-extent button, .ol-3d button, .ol-layers button, " +
//	  ".ol-geolocation button, .ol-overviewmap button, .ol-scale-line button, .ol-measure-length button, .ol-measure-area button").tooltip({
//		  placement: 'left',
//		  container: 'body',
//		  delay: { "show": 0, "hide": 200 }
//	});
	 
//	$(".ol-attribution button").tooltip({ placement: 'left', delay: { "show": 0, "hide": 200 } });
	
	// spengo il loading
	appUtil.hideLoader();
	
 }catch(e){
	 console.log("Error build controls " + e);
	 /**
	  * La procedura di costruzione dei controlli della mappa è andata in errore, apro una popup informativa dell'errore.
	  */
	 // spengo il loading
	 appUtil.hideLoader();
	 // apri popup errore
	 appUtil.showMessageAlertApplication(jQuery.i18n.prop('Init.build.controlmap.error'), jQuery.i18n.prop('PopupAlert.attenzione'), false, true, false);
 }
};


/**
 * Serie di controlli inclusi nelle mappe per impostazione predefinita. A meno che non configurato altrimenti,
 * restituisce un insieme contenente un'istanza di ciascuno dei seguenti controlli:
 *   - ol.control.Zoom 
 *   - ol.control.Rotate
 *   - ol.control.Attribution
 *  
 *  @param {@link ClassBuilder.MapOptions} OPTIONS
 ***/
ControlsBuilder.configureDefaultControls = function(OPTIONS){
	
  /**
   * Controllo per mostrare tutti gli attributi associati con le fonti di livello nella mappa. 
   * (es : la scritta contributors di open street map)
   * Questo controllo è uno dei controlli predefiniti inclusi nelle mappe. Di default mostrerà nella parte in basso a 
   * destra della mappa, ma questo può essere modificato tramite un selettore CSS per ".ol-attribution" .
   */
  var attributionOptions = {
	  /** CSS class name. Default is ol-attribution. **/
	  className: "ol-attribution",
	  /** Target. **/
//	  target: "",
	  /** Specify if attributions can be collapsed. If you use an OSM source, should be set to false — see OSM Copyright — Default is true. **/
	  collapsible: true,
	  /** Specify if attributions should be collapsed at startup. Default is true. **/
	  collapsed: true,
	  /** Text label to use for the button tip. Default is Attributions **/
	  tipLabel: jQuery.i18n.prop('OpenLayers.attributions.tipLabel'),
	  /** Text label to use for the collapsed attributions button. Default is i. Instead of text, also a Node (e.g. a span element) can be used. **/
	  label: "i"
	  /** Text label to use for the expanded attributions button. Default is ». Instead of text, also a Node (e.g. a span element) can be used. **/
//	  collapseLabel: "",
	  /** Function called when the control should be re-rendered. This is called in a requestAnimationFrame callback. **/
//	  render: ""
   };
	  
  /**
   * Un controllo pulsante per ripristinare la rotazione a 0. Per lo stile questo uso di controllo selettore CSS .ol-rotate. 
   * Un selettore CSS .ol-hidden viene aggiunto al pulsante quando la rotazione è 0.
   * Strumento attivabile con la combinazione ALT + SHIFT + Drag to rotate.
   */
  var rotateOptions = {
	  /** CSS class name. Default is ol-rotate. **/
	  className: "ol-rotate",
	  /** Text label to use for the rotate button. Default is ⇧. Instead of text, also a Node (e.g. a span element) can be used. **/
//	  label: "",
	  /** Text label to use for the rotate tip. Default is Reset rotation **/
	  tipLabel: jQuery.i18n.prop('OpenLayers.rotateOptions.tipLabel'),
	  /** Animation duration in milliseconds. Default is 250. **/
	  duration: 250,
	  /** Hide the control when rotation is 0. Default is true. **/
	  autoHide: true
	  /** Function called when the control should be re-rendered. This is called in a requestAnimationFrame callback. **/
//	  render: "",
	  /** Target. **/
//	  target: ""
  };
  
  /**
   * Un controllo con 2 pulsanti, uno per lo zoom in e una per lo zoom out. Questo controllo è uno dei controlli predefiniti di una mappa. 
   * Per stile di questo uso di controllo selettori CSS .ol-zoom-in e .ol-zoom-out.
   */
  var zoomOptions = {
	  /** Animation duration in milliseconds. Default is 250. **/
	  duration: 250,
	  /** CSS class name. Default is ol-zoom. **/
	  className: "ol-zoom",
	  /** Text label to use for the zoom-in button. Default is +. Instead of text, also a Node (e.g. a span element) can be used. **/
	  zoomInLabel: "+",
	  /** Text label to use for the zoom-out button. Default is -. Instead of text, also a Node (e.g. a span element) can be used. **/
	  zoomOutLabel: "-",
	  /** Text label to use for the button tip. Default is Zoom in **/
	  zoomInTipLabel: jQuery.i18n.prop('OpenLayers.zoomOptions.zoomInTipLabel'),
	  /** Text label to use for the button tip. Default is Zoom out **/
	  zoomOutTipLabel: jQuery.i18n.prop('OpenLayers.zoomOptions.zoomOutTipLabel')
	  /** The zoom delta applied on each click. **/
//	  delta: "",
	  /** Target. **/
//	  target: ""
  };
  
  /**
   * ol.control.defaults(opt_options){ol.Collection.<ol.control.Control>} 
   */
  var olControlDefault = ol.control.defaults({
	  /** Attribution. Default is true. **/
	  attribution: OPTIONS.enableAttribution,
	  /** Attribution options. **/
	  attributionOptions: attributionOptions,
	  /** Rotate. Default is true. **/
	  rotate: OPTIONS.enableRotation,
	  /** Rotate options **/
	  rotateOptions: rotateOptions,
	  /** Zoom. Default is true. **/
	  zoom: OPTIONS.enableZoom,
	  /** Zoom options. **/
	  zoomOptions: zoomOptions
   });
	 
  return olControlDefault; 
};


/**
 * Fornisce un pulsante che se cliccato riempie l'intero schermo con la mappa. Quando in modalità a schermo intero, 
 * un pulsante di chiusura viene mostrato per uscire dalla modalità a schermo intero. 
 * L'API a tutto schermo viene utilizzato per attivare o disattivare la mappa in modalità a schermo intero.
 */
//ControlsBuilder.fullScreenControls = function(){
//	
//  var fullScreen = new ol.control.FullScreen({
//	  /** CSS class name. Default is ol-full-screen. **/
//	  className: "ol-full-screen",
//	  /** Text label to use for the button. Default is \u2194 (an arrow). Instead of text, also a Node (e.g. a span element) can be used. **/
//	  label: "\u2194",
//	  /** Text label to use for the button when full-screen is active. Default is \u00d7 (a cross).  **/
//	  labelActive: "\u2194",
//	  /** Text label to use for the button tip. Default is Toggle full-screen **/
//	  tipLabel: jQuery.i18n.prop('OpenLayers.fullScreen.tipLabel'),
//	  /** Full keyboard access. **/
////	  keys: "",
//	  /** Target. **/
//	  target: "gisTool"
//  });
//  
//  return fullScreen;
//};


/**
 * Un controllo per mostrare le coordinate 2D del cursore del mouse. Per default, questi sono nella proiezione della mappa, 
 * ma possono essere in qualsiasi proiezione supportata. Per impostazione predefinita, il controllo viene mostrata 
 * nell'angolo in alto a destra della mappa, ma questo può essere modificato utilizzando il selettore CSS .ol-mouse-position.
 * 
 * @param {@link ClassBuilder.MapOptions} OPTIONS
 */
ControlsBuilder.mousePositionControls = function(OPTIONS){

  var mousePosition = new ol.control.MousePosition({
	  /** CSS class name. Default is ol-mouse-position. **/
	  className: "ol-mouse-position ol-control",
	  /** Coordinate format (precisione) **/
	  coordinateFormat: function(coordinate) {
	      return ol.coordinate.format(coordinate, jQuery.i18n.prop('OpenLayers.mousePosition.tipLabel') + ': {x} , {y}', 4);
	  },
	  /** Projection. **/
	  projection: OPTIONS.sourceProjection,
	  /** Function called when the control should be re-rendered. This is called in a requestAnimationFrame callback. **/
//	  render: "",
	  /** Target. **/
//	  target: "",
	  /** Markup for undefined coordinates. Default is `` (empty string). **/
	  undefinedHTML: jQuery.i18n.prop('OpenLayers.mousePosition.tipLabel') + ": "
  });
  
  return mousePosition;
};


/**
 * Un controllo con 2 pulsanti, uno per lo zoom in e una per lo zoom out e lo slider centrale per spostarsi direttamente 
 * ad un certo livello di zoom.
 */
ControlsBuilder.zoomSliderControls = function(){
	
  var zoomSlider = new ol.control.ZoomSlider({
	  /** CSS class name. **/
//	  className: "",	
	  /**  Animation duration in milliseconds. Default is 200. **/
	  duration: 200,
	  /** Maximum resolution. **/
//	  maxResolution: "",
	  /** Minimum resolution. **/
//	  minResolution: "",
	  /** Function called when the control should be re-rendered. This is called in a requestAnimationFrame callback. **/
//	  render: ""
  });
  
  return zoomSlider;
};


/**
 * Un controllo pulsante che, se premuto, cambia la visualizzazione della mappa in misura specifica. 
 * Per stile di questo controllo utilizzare il selettore CSS .ol-zoom-extent.
 * 
 * @param {@link ClassBuilder.MapOptions} OPTIONS
 */
ControlsBuilder.zoomToExtentControls = function(OPTIONS){
  
  var zoomToExtent = new ol.control.ZoomToExtent({
	  /**  Class name. Default is ol-zoom-extent. **/
	  className: "ol-zoom-extent",
	  /** Target. **/
	  target: "gisTool",
	  /** Text label to use for the button. Default is E. Instead of text, also a Node (e.g. a span element) can be used. **/
	  label: " ",
	  /** Text label to use for the button tip. Default is Zoom to extent **/
	  tipLabel: jQuery.i18n.prop('OpenLayers.zoomToExtent.tipLabel'),
	  /** The extent to zoom to. If undefined the validity extent of the view projection is used.  **/
	  extent: FunctionBuilder.olProjTransormExtent(OPTIONS.extent, OPTIONS.sourceProjection, OPTIONS.destProjection)
  });
  
  return zoomToExtent;
};


/**
 * Un controllo pulsante che, se premuto, attiva/disattiva la visualizzazione della mappa in 3D/2D. 
 * Per stile di questo controllo utilizzare il selettore CSS .ol-3d.
 * 
 * @param {@link olcs.OLCesium} map3DInstance
 */
ControlsBuilder.ol3DControls = function(map3DInstance) {
  
  /**
   * Define a namespace for the application.
   */
  var app = {};

  /**
   * @constructor
   * @extends {ol.control.Control}
   * @param {Object=} opt_options Control options.
   */
  app.Ol3DControl = function(opt_options) {

	  var options = opt_options || {};
	   
	  // create button
	  var button = document.createElement('button');
		  button.innerHTML = '<i class="fa fa-globe"></i>';
		  button.title = jQuery.i18n.prop('OpenLayers.ol3DControl.tipLabel');
	  
		  var handleEnableDisable3D = function(e) {
			   // get button 
			   var element = e.currentTarget;
			   // get DIV
			   var parent = $(element).parent();
					 
			   /** controllo se sto deselezionando o seleionando il controllo **/
			   if( $(parent).hasClass("ol-selectable") ){
				   /** --> deselezione del controllo **/
					 
				   $(parent).removeClass("ol-selectable");
				   /** aggiungo il class che graficamente individua l'elemento come non selezionato **/
				   $(parent).addClass("ol-unselectable");	 
			   }else{
				   /** --> selezione del controllo **/
					 
				   $(parent).removeClass("ol-unselectable");
				   /** aggiungo il class che graficamente individua l'elemento come non selezionato **/
				   $(parent).addClass("ol-selectable");
			   }
			   
			  // abilita / disabilita
			  map3DInstance.setEnabled(!map3DInstance.getEnabled());
		  };
		  
		  // add event click on button
		  button.addEventListener('click', handleEnableDisable3D, false);

	  // create div
	  var element = document.createElement('div');
		  element.className = 'ol-3d ol-unselectable ol-control';
		  element.appendChild(button);

	  ol.control.Control.call(this, {
	     element: element,
	     target: options.target
	  });
  };
  
  // add control in openlayers instance
  ol.inherits(app.Ol3DControl, ol.control.Control);
 
  // instanzio il controllo appena creato
  var ol3DControls = new app.Ol3DControl();
 
  return ol3DControls;
};


/**
 * Un controllo pulsante che, se premuto, attiva/disattiva la visualizzazione dell'albero dei layer 
 * Per stile di questo controllo utilizzare il selettore CSS .ol-layers.
 * 
 * Uso HTML Albero dei layer:
 * (code)
 *    <!-- Albero dei layer -->
 *    <div id="..." class="areeTematiche" style="display: none;"> 
 *    		<!-- Title -->
 *    		<div class="boxMappa_heading">
 *    			<span class="label">...<i class="fa fa-times pointer close" id="areeTematicheClose"></i></span>
 *    		</div>
 *    		
 *    		<!-- Ricerca layer -->
 *    		<div class="input-group input-group-sm">
 *    		    <input id="layoutTree_q" type="text" class="form-control" placeholder="..." aria-describedby="clearLayoutTree_q">
 *    			<span class="input-group-addon pointer" id="clearLayoutTree_q"><i class="fa fa-times"></i></span>
 *    		</div>
 *    
 *    		<!-- Albero -->
 *    		<div id="..."></div>
 *    </div>
 * (end)
 * 
 */
ControlsBuilder.olLayersControls = function(options) {
  
  /**
   * Define a namespace for the application.
   */
  var app = {};

  /**
   * @constructor
   * @extends {ol.control.Control}
   * @param {Object=} opt_options Control options.
   */
  app.OlLayersControls = function(opt_options) {

	 var options = opt_options || {};

	 // create button
	 var button = document.createElement('button');
	     button.innerHTML = '';
	     button.title = jQuery.i18n.prop('OpenLayers.olLayersControls.tipLabel');
		  
	 var handleEnableDisableLayers = function(e) {
		  // get button 
		  var element = e.currentTarget;
		  // get DIV
		  var parent = $(element).parent();
			 
		  /** controllo se sto deselezionando o selezionando il controllo **/
		  if( $(parent).hasClass("ol-selectable") ){
			 /** --> deselezione del controllo **/
				 
			 $(parent).removeClass("ol-selectable");
			 /** aggiungo il class che graficamente individua l'elemento come non selezionato **/
			 $(parent).addClass("ol-unselectable");
				 
			 /** nascondo il DIV dell'albero dei layer **/
			 $("#" + options.targetContainerTree + "").hide();
				 
			 /** rimuovo il class che sposta a destra la mappa rispetto al DIV dell'albero  **/
			 $("#" + options.targetMap + "").removeClass(ol.ApplicationProperty.CLASS_DIV_SHOW_LAYERS);
		  }else{
			 /** --> selezione del controllo **/
				 
			 $(parent).removeClass("ol-unselectable");
			 /** aggiungo il class che graficamente individua l'elemento come non selezionato **/
			 $(parent).addClass("ol-selectable");
			 
			 /** mostro il DIV dell'albero dei layer **/
			 $("#" + options.targetContainerTree + "").show();
			 
			 /** aggiungo il class che sposta a destra la mappa rispetto al DIV dell'albero  **/
			 $("#" + options.targetMap + "").addClass(ol.ApplicationProperty.CLASS_DIV_SHOW_LAYERS);
		  }
				
		  /**
		   * Gestione Click sul button "Chiudi" presente nel DIV dell'albero dei layer.
		   */
		  $("#areeTematicheClose").click(function(){
			 /** --> deselezione del controllo **/
				 
			 $(parent).removeClass("ol-selectable");
			 /** aggiungo il class che graficamente individua l'elemento come non selezionato **/
			 $(parent).addClass("ol-unselectable");
					 
			 /** nascondo il DIV dell'albero dei layer **/
			 $("#" + options.targetContainerTree + "").hide();
					 
			 /** rimuovo il class che sposta a destra la mappa rispetto al DIV dell'albero  **/
			 $("#" + options.targetMap + "").removeClass(ol.ApplicationProperty.CLASS_DIV_SHOW_LAYERS);
		  });
	 };// close handleEnableDisableLayers

	 // add event click on button
	 button.addEventListener('click', handleEnableDisableLayers, false);

	 // create div
	 var element = document.createElement('div');
		 element.className = 'ol-layers ol-unselectable ol-control';
		 element.appendChild(button);

	 ol.control.Control.call(this, {
	    element: element,
	    target: options.target
	 });
  };

  // add control in openlayers instance
  ol.inherits(app.OlLayersControls, ol.control.Control);
  
  //instanzio il controllo appena creato
  var olLayersControls = new app.OlLayersControls(options);
	 
  return olLayersControls;
};


/** 
 * Un controllo pulsante che, se premuto, viene individuata la posizione di un utente.
 * 
 * Uso HTML riepilogo dati geolocalizzazione:
 *  (code)	
 *    <div id="geolocateUser"></div>
 *  (end)
 * 
 * @param {@link ol.Map} map2DInstance
 * @param {@link ClassBuilder.MapOptions} OPTIONS
 * **/
ControlsBuilder.olGeolocationControls = function(map2DInstance, options) {
 
  /**
   * Instance geolocation
   */
  geolocation = new ExtendedOlGeolocation({
	  /** The projection the position is reported in. **/
	  projection: options.destProjection,
	  /**  Start Tracking. Default is false. **/
	  tracking: false,
	  /** instanza della mappa (Required) **/
	  map: map2DInstance,
	  /** Livello di zoom al quale portare la mappa quando viene geolocalizzata la posizione dell'utente. **/
	  geolocateZoom: options.geolocateZoom
  });

  /**
   * update the HTML page and feature coordinate when the position changes.
   */
  geolocation.on('change', function() {
	  /** The current position of the device reported in the current projection. **/
	  var position = geolocation.getPosition();
	  /**
	   * Ritorna un ol.Coordinate (senza modificare i punti originali), ma convertendo la longitudine e la latitudine dalla proiezione di origine 
	   * a quella di destinazione.
	   */
	  position = FunctionBuilder.olProjTransorm(position[0], position[1], options.destProjection, options.sourceProjection);
	   
	  /** posizione **/
	  var latitudine = position[1].toFixed(4); 	var longitudine = position[0].toFixed(4);
	  /** The altitude of the position in meters above mean sea level. **/
	  var altitudine = geolocation.getAltitude() != undefined ? geolocation.getAltitude() : jQuery.i18n.prop('Informazione.nonDisponibile');
	  /** The instantaneous speed of the device in meters per second. **/
	  var speed = geolocation.getSpeed() != undefined ? geolocation.getSpeed() : "0.0";
	  
	  /** scrivo l'html che riepiloga le info sulla geolocalizzazione **/
	  $("#geolocateUser").html(
			  "<div class='geolocateMe'>" +
				  "<strong>" + jQuery.i18n.prop('OpenLayers.longitudine') + "</strong>: " + longitudine +
				  "<br />" + 
				  "<strong>" + jQuery.i18n.prop('OpenLayers.latitudine') + "</strong>: " + latitudine +
				  "<br />" + 
				  "<strong>" + jQuery.i18n.prop('OpenLayers.olGeolocation.altitudine') + "</strong>: " + altitudine +
				  "<br />" +
				  "<strong>" + jQuery.i18n.prop('OpenLayers.olGeolocation.velocita') + "</strong>: " + speed + " m/s" +
			  "</div>"
	  );
	  /** mostro il div del riepilogo **/
	  $("#geolocateUser").show();
	  
	  /** disegno la feature della posizione **/
	  var positionFeature =  new ol.Feature({
		  	/** The default geometry for the feature. ol.geom.Geometry **/
			geometry: FunctionBuilder.olCoordinateToPoint(geolocation.getPosition(), "XY"),
			/** **/
			name: 'Posizione Utente',
			/** number | string | undefined **/
			id: "geolocateMe"
	  }); 
	  
	  /** recupero il layer **/
	  var layer = geolocation.getMap().getLayerByName(ol.ApplicationProperty.NAME_LAYER_GEOLOCATION);
		  
	  	  if(layer == null) {
		   	 /** Il layer non esiste, quindi lo creo e lo aggiungo alla mappa **/
		   	 layer = FunctionBuilder.olCreaLayerVectorToGeolocate();
		   	 // add layer map
		   	 geolocation.getMap().addLayer(layer);
		  }  
	  	  /** svuoto il layer dalle feature **/
		  layer.getSource().clear();
		  /** aggiungo la nuova feature **/
		  layer.getSource().addFeature(positionFeature);
	
	  /* OL 3.13.1*/
	  /**
	   * Genero una transizione animata durante l'aggiornamento del centro della mappa.
	   */
//	  var pan = ol.animation.pan({
//		    /** The duration of the animation in milliseconds. Default is 1000. **/
//		    duration: 2000,
//		    /** The location to start panning from, typically map.getView().getCenter(). **/
//		    source: geolocation.getMap().getView().getCenter()
//	  });
//	  
	  /**
	   * Aggiungo l'animazione che deve essere richiamata prima del rendering(prima di aggiornare la visualizzazione della mappa). 
	   */
//	  geolocation.getMap().beforeRender(pan);
	  /** centro la mappa sulle coordinate della posizione utente **/
	  //geolocation.getMap().getView().setCenter(geolocation.getPosition());
	  /** zoom alla coordinata **/
//	  geolocation.getMap().getView().setZoom(geolocation.getGeolocateZoom());
		
		  
	 /**
	   * Genero una transizione animata durante l'aggiornamento del centro della mappa.
	   */
	  /* OL 4.0.1*/
	  geolocation.getMap().getView().animate({
          center: geolocation.getPosition(),
          duration: 2000,
          zoom : geolocation.getGeolocateZoom()
      });
  });
  
  /**
   * handle geolocation error.
   */
  geolocation.on('error', function(error) {
	  /**
	   * shows the alert box - if the title is passed display that otherwise shows 
	   * the default title
	   */
	  appUtil.showMessageAlertApplication(
			  	jQuery.i18n.prop('OpenLayers.olGeolocation.errorGeolocate'), 
			  	jQuery.i18n.prop('PopupAlert.attenzione'), false, true, false);
	  
	  $(".ol-geolocation").removeClass("ol-selectable");
	  /** aggiungo il class che graficamente individua l'elemento come non selezionato **/
	  $(".ol-geolocation").addClass("ol-unselectable");
  });
	
  /**
   * Define a namespace for the application.
   */
  var app = {};

  /**
   * @constructor
   * @extends {ol.control.Control}
   * @param {Object=} opt_options Control options.
   */
  app.OlGeolocationControls = function(map, opt_options) {

	 var options = opt_options || {};

 	 // create button
	 var button = document.createElement('button');
		 button.innerHTML = '<i class="fa fa-location-arrow"></i>';
		 button.title = jQuery.i18n.prop('OpenLayers.olGeolocation.tipLabel');
	  
		 var handleGeolocateUser = function(e) {
			  // get button 
			  var element = e.currentTarget;
			  // get DIV
			  var parent = $(element).parent();
				 
			  /** controllo se sto deselezionando o selezionando il controllo **/
			  if( $(parent).hasClass("ol-selectable") ){
				 /** --> deselezione del controllo **/
					 
				 $(parent).removeClass("ol-selectable");
				 /** aggiungo il class che graficamente individua l'elemento come non selezionato **/
				 $(parent).addClass("ol-unselectable");
				 
				 // nascondo il cubotto di riepilogo info geolocalizzazione
				 $("#geolocateUser").hide();
				 // clear html
				 $("#geolocateUser").html("");
				 
				 /** recupero il layer **/
				 var layer = geolocation.getMap().getLayerByName(ol.ApplicationProperty.NAME_LAYER_GEOLOCATION);
				 
				 if(layer != null) /** rimuovo il layer dalla mappa **/ geolocation.getMap().removeLayer(layer);
					  
				 // Disabilito il tracking.			
				 geolocation.setTracking(false);
			 }else{
				 /** --> selezione del controllo **/
				 
				 $(parent).removeClass("ol-unselectable");
				 /** aggiungo il class che graficamente individua l'elemento come non selezionato **/
				 $(parent).addClass("ol-selectable");
				 
				 // Abilito il tracking.			
				 geolocation.setTracking(true);
				
				 /** recupero il layer **/
				 var layer = geolocation.getMap().getLayerByName(ol.ApplicationProperty.NAME_LAYER_GEOLOCATION);
				 
				 if(layer == null) {
				   	/** Il layer non esiste, quindi lo creo e lo aggiungo alla mappa **/
				   	layer = FunctionBuilder.olCreaLayerVectorToGeolocate();
				   	// add layer map
				   	map.addLayer(layer);
				 }  
			 }
		 };
	
		 // add event click on button
		 button.addEventListener('click', handleGeolocateUser, false);

	 // create div 
	 var element = document.createElement('div');
		 element.className = 'ol-geolocation ol-unselectable ol-control';
		 element.appendChild(button);

	 ol.control.Control.call(this, {
	     element: element,
	     target: 'gisTool'
	 });
  };
	
  //add control in openlayers instance
  ol.inherits(app.OlGeolocationControls, ol.control.Control);
  
  //instanzio il controllo appena creato
  var olGeolocation = new app.OlGeolocationControls(map2DInstance, options);
 
  return olGeolocation;
};


/**
 * Creare un nuovo controllo con una mappa in qualità di una mappa panoramica per un'altra mappa definita.
 */
ControlsBuilder.overviewMapControls = function(){
  
  var overviewMap = new ol.control.OverviewMap({
	  /**  Whether the control should start collapsed or not (expanded). Default to true. **/
	  collapsed: true,
	  /** Text label to use for the expanded overviewmap button. Default is «. Instead of text, also a Node (e.g. a span element) can be used. **/
//	  collapseLabel: "",
	  /** Whether the control can be collapsed or not. Default to true. **/
	  collapsible: true,
	  /**  Text label to use for the collapsed overviewmap button. Default is ». Instead of text, also a Node (e.g. a span element) can be used. **/
//	  label: "",
	  /** Layers for the overview map. If not set, then all main map layers are used instead. **/
//	  layers: null,
	  /** Function called when the control should be re-rendered. This is called in a requestAnimationFrame callback. **/
//	  render: "",
	  /** Specify a target if you want the control to be rendered outside of the map's viewport. **/
	  target: "gisTool",
	  /** Text label to use for the button tip. Default is Overview map **/
	  tipLabel: jQuery.i18n.prop('OpenLayers.overviewMap.tipLabel')
  });
  
  return overviewMap;
};


/**
 * Un controllo che visualizza distanze x assi grezze, calcolato per il centro della finestra. 
 * Nessuna linea scala verrà mostrata quando la distanza asse x non può essere calcolato nella proiezione vista (ad esempio in corrispondenza o 
 * al di là dei poli in EPSG: 4326). Per impostazione predefinita, la linea di scala mostrerà nella parte in basso a sinistra della mappa, 
 * ma questo può essere modificato utilizzando il selettore CSS .ol-scale-line .
 */
ControlsBuilder.scaleLineControls = function(){
	
  var scaleLine = new ol.control.ScaleLine({
	  /** CSS Class name. Default is ol-scale-line. **/
	  className: "ol-scale-line",
	  /**  Minimum width in pixels. Default is 64. **/
	  minWidth: 64,
	  /** Function called when the control should be re-rendered. This is called in a requestAnimationFrame callback. **/
//	  render: "",
	  /** Target. **/
//	  target: '',
	  /**  Units. Default is metric. Units for the scale line. Supported values are 'degrees', 'imperial', 'nautical', 'metric', 'us'. **/
	  units: "metric"
  });
	
  return scaleLine;
};


/**
 * Questa funzione gestisce la possibilita di passare da una modalità di zoom all'altra.
 * In particolare quando in ingresso viene fornita la stringa:
 *   - DEFAULT: Viene attivato il controllo con 2 pulsanti, uno per lo zoom in e una per lo zoom out.
 *   - SLIDER:  Viene attivato il controllo con 2 pulsanti, uno per lo zoom in e una per lo zoom out e lo slider centrale per spostarsi direttamente 
 * 			    ad un certo livello di zoom.
 */
ControlsBuilder.changeInOutSliderZoom = function(modalita, map2DInstance){
	if(modalita == 'SLIDER'){
		 $(".ol-overlaycontainer-stopevent").addClass("full-tools");
		 $(".ol-zoom-in")
		 	.attr("data-original-title",
			 		 jQuery.i18n.prop('OpenLayers.zoomOptions.zoomInTipLabel') +
					 "<br /> <a href=\"javascript:ControlsBuilder.changeInOutSliderZoom(\'DEFAULT\',this);\">" + 
					 jQuery.i18n.prop('OpenLayers.zoomOptions.nascondiCursoreTipLabel') + "</a>");
		 $(".ol-zoom-out")
		 	.attr("data-original-title", 
		 			jQuery.i18n.prop('OpenLayers.zoomOptions.zoomOutTipLabel')
		 			+ "<br /> <a href=\"javascript:ControlsBuilder.changeInOutSliderZoom(\'DEFAULT\',this);\">" +
		 			jQuery.i18n.prop('OpenLayers.zoomOptions.nascondiCursoreTipLabel') + "</a>");
		 map2DInstance.addControl(ControlsBuilder.zoomSliderControls());
	}else
		if(modalita == 'DEFAULT'){
			/** Disattivare un controllo si gnifica rimuoverlo dalla mappa **/
			map2DInstance.getControls().forEach(function (control, index) {
				 if(control instanceof ol.control.ZoomSlider) {
					 $(".ol-overlaycontainer-stopevent").removeClass("full-tools");
					 map2DInstance.removeControl(control);
				 }
			});
			 
			$(".ol-zoom-in").attr("data-original-title",
				 jQuery.i18n.prop('OpenLayers.zoomOptions.zoomInTipLabel') +
				 "<br /> <a href=\"javascript:ControlsBuilder.changeInOutSliderZoom(\'SLIDER\',this);\">" + 
				 jQuery.i18n.prop('OpenLayers.zoomOptions.mostraCursoreTipLabel') + "</a>");
			
			$(".ol-zoom-out").attr("data-original-title",
				 jQuery.i18n.prop('OpenLayers.zoomOptions.zoomOutTipLabel') + 
				 "<br /> <a href=\"javascript:ControlsBuilder.changeInOutSliderZoom(\'SLIDER\',this);\">" +
				 jQuery.i18n.prop('OpenLayers.zoomOptions.mostraCursoreTipLabel') + "</a>");
	    }
};


/**
 * Un controllo pulsante che, se premuto, attiva/disattiva lo strumento per disegnare su mappa
 * e misurare la lunghezza dell'arco tracciato.
 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
 *
 * @param {@link ol.Map} map2DInstance
 * @param {@link ClassBuilder.MapOptions} options
 **/
ControlsBuilder.olMeasureLengthControls = function(map2DInstance, options) {

	/**
	 * Define a namespace for the application.
	 */
	var app = {};

	/**
	 * @constructor
	 * @extends {ol.control.Control}
	 * @param {Object=} opt_options Control options.
	 */
	app.olMeasureLengthControl = function(map, opt_options) {

		var options = opt_options || {};

	 	// create button
		var button = document.createElement('button');
			button.innerHTML = '<i class="fa fa-m-length"></i>';
			button.title = jQuery.i18n.prop('OpenLayers.olMeasureLength.tipLabel');
		  
		var handleMeasureLength = function(e) {
			// get button 
			var element = e.currentTarget;
			// get DIV
			var parent = $(element).parent();
					 
			/** controllo se sto deselezionando o selezionando il controllo **/
			if( $(parent).hasClass("ol-selectable") ){
				/** --> deselezione del controllo **/
						 
				$(parent).removeClass("ol-selectable");
				/** aggiungo il class che graficamente individua l'elemento come non selezionato **/
				$(parent).addClass("ol-unselectable");
					 
				/**
				 * Disattiva lo strumento per disegnare su mappa e misurare la lunghezza dell'arco tracciato.
				 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
				 **/
				InteractionsBuilder.olMeasureLength(false, map, opt_options);
			}else{
				/** --> selezione del controllo **/
					 
				$(parent).removeClass("ol-unselectable");
				/** aggiungo il class che graficamente individua l'elemento come non selezionato **/
				$(parent).addClass("ol-selectable");
				
				/**
				 * Disattivo l'alto controllo per la misura dell'area
				 */
				if($(".ol-measure-area").hasClass("ol-selectable")) $(".ol-measure-area button").click();
				/**
				 * Attiva lo strumento per disegnare su mappa e misurare la lunghezza dell'arco tracciato.
				 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
				 **/
				InteractionsBuilder.olMeasureLength(true, map, opt_options);
			}
		};
		
		// add event click on button
		button.addEventListener('click', handleMeasureLength, false);

		// create div 
		var element = document.createElement('div');
			element.className = 'ol-measure-length ol-unselectable ol-control';
			element.appendChild(button);

		ol.control.Control.call(this, {
		    element: element,
		    target: 'gisTool'
		});
	};
		
	//add control in openlayers instance
	ol.inherits(app.olMeasureLengthControl, ol.control.Control);
	  
	//instanzio il controllo appena creato
	var olMeasure = new app.olMeasureLengthControl(map2DInstance, options);
	 
	return olMeasure;
};


/**
 * Un controllo che attiva/disattiva lo strumento per disegnare su mappa un poligono e misurarne la grandezza della superficie.
 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
 *
 * @param {@link ol.Map} map2DInstance
 * @param {@link ClassBuilder.MapOptions} options
 **/
ControlsBuilder.olMeasureAreaControls = function(map2DInstance, options) {

	/**
	 * Define a namespace for the application.
	 */
	var app = {};

	/**
	 * @constructor
	 * @extends {ol.control.Control}
	 * @param {Object=} opt_options Control options.
	 */
	app.olMeasureAreaControl = function(map, opt_options) {

		var options = opt_options || {};

	 	// create button
		var button = document.createElement('button');
			button.innerHTML = '<i class="fa fa-m-area"></i>';
			button.title = jQuery.i18n.prop('OpenLayers.olMeasureArea.tipLabel');
		  
		var handleMeasureArea = function(e) {
			// get button 
			var element = e.currentTarget;
			// get DIV
			var parent = $(element).parent();
					 
			/** controllo se sto deselezionando o selezionando il controllo **/
			if( $(parent).hasClass("ol-selectable") ){
				/** --> deselezione del controllo **/
						 
				$(parent).removeClass("ol-selectable");
				/** aggiungo il class che graficamente individua l'elemento come non selezionato **/
				$(parent).addClass("ol-unselectable");
					 
				/**
				 * Disattiva lo strumento per disegnare su mappa e misurare la lunghezza dell'arco tracciato.
				 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
				 **/
				InteractionsBuilder.olMeasureArea(false, map, opt_options);
			}else{
				/** --> selezione del controllo **/
					 
				$(parent).removeClass("ol-unselectable");
				/** aggiungo il class che graficamente individua l'elemento come non selezionato **/
				$(parent).addClass("ol-selectable");
				
				/**
				 * Disattivo l'alto controllo per la misura della lunghezza
				 */
				if($(".ol-measure-length").hasClass("ol-selectable"))  $(".ol-measure-length button").click();
				/**
				 * Attiva lo strumento per disegnare su mappa e misurare la lunghezza dell'arco tracciato.
				 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
				 **/
				InteractionsBuilder.olMeasureArea(true, map, opt_options);
			}
		};
		
		// add event click on button
		button.addEventListener('click', handleMeasureArea, false);

		// create div 
		var element = document.createElement('div');
			element.className = 'ol-measure-area ol-unselectable ol-control';
			element.appendChild(button);

		ol.control.Control.call(this, {
		    element: element,
		    target: 'gisTool'
		});
	};
		
	//add control in openlayers instance
	ol.inherits(app.olMeasureAreaControl, ol.control.Control);
	  
	//instanzio il controllo appena creato
	var olMeasure = new app.olMeasureAreaControl(map2DInstance, options);
	 
	return olMeasure;
};
