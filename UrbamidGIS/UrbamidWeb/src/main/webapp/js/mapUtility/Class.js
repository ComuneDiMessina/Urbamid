

/** ==================================================================================== **/
/** Definizione delle classi interne.  **/
/** ==================================================================================== **/

var ClassBuilder = ClassBuilder || {};


/********************************************************/
/*                                                      */
/*          Elenco property extended class ol	        */
/*                                                      */
/********************************************************/  

/**
 * Fix property application
 * @enum {string}
 */
ol.ApplicationProperty = {
    /** Class name aggiunto al (DIV) della mappa quando è visibile l'albero dei layer, **/
    CLASS_DIV_SHOW_LAYERS: 'pleft',
    /** Nome del layer per la visuallizazione su mappa della feature della geolocalizzazione **/
    NAME_LAYER_GEOLOCATION: "l_geolocate_user",
    /** Bing Maps API key **/
    BING_MAPS_KEY: "Ak-dzM4wZjSqTlzveKz5u0d4IQ4bRzVI309GxmkgSVr1ewS6iPSrOvOKhA-CJlm3",
    /** Nome del layer per la visualizzazione su mappa della feature della linea di cui si desidera la lunghezza **/
    NAME_LAYER_MEASURE_LENGTH: "l_measure_length",
    /** Nome del layer per la visualizzazione su mappa della feature dell'area di cui si desidera la grandezza della superficie **/
    NAME_LAYER_MEASURE_AREA: "l_measure_area",
    /** Id dello strumento per tracciare linee su mappa **/
    ID_INTERACTION_DRAW_MEASURE_LENGTH: "id_line_draw_measure_length",
    /** Id dello strumento per tracciare l'area su mappa **/
    ID_INTERACTION_DRAW_MEASURE_AREA: "id_pol_draw_measure_area",
    /** Id dell'Overlay to show the help messages. **/
    ID_OVERLAY_HELP_MESS_MEASURE_LENGTH: "id_help_message_measure_length",
    /** Id dell'Overlay to show the help messages. **/
    ID_OVERLAY_HELP_MESS_MEASURE_AREA: "id_help_message_measure_area",
    /** Id dell'Overlay to show the measurement. **/
    ID_OVERLAY_MEASURE_LENGTH_TOOLTIP: "id_measure_length_tooltip",
    /** Id dell'Overlay to show the measurement. **/
    ID_OVERLAY_MEASURE_AREA_TOOLTIP: "id_measure_area_tooltip",
    /** Layer class bof **/
    LAYER_CLASS_BOF: "BOF",
    /** Key del linguaggio in italiano **/
    LANG_IT: "IT",
    /** key del linguaggio in inglese **/
    LANG_EN: "EN",
    /** Identificativo del profilo da usare per recuperare le informazioni per costruire la mappa(albero, controlli, ecc..) */
    ID_PROFILO: 1,
    /** Url che restituisce il json delle configurazioni impostate per il profilo **/
    URL_CONFIGURAZIONI_PROFILO: "profilo/findConfigurationProfileById.json"
};

/**
 * Tipologia layer oggetto
 * 
 * @enum {string}
 */
ol.LayerType = {
	/** Layer vettoriale */
	VECTOR: "VECTOR",
	/** Cluster di un layer vettoriale */
	CLUSTER: "CLUSTER",
	/** Source for WMS servers providing single, untiled images. */
	IMAGE_WMS: "IMAGE_WMS",
	/** Layer source for tile data from WMS servers. */
	TILE_WMS: "TILE_WMS"
};

/**
 * Extended property ol.Geolocation
 * @enum {string}
 */
ol.ExtendedOlGeolocationProperty = {
	MAP: "map"
}

/**
 * Extended property ol.layer.Base
 * @enum {string}
 */
ol.ExtendedOlLayerBaseProperty = {
	ID: "id", 
	NAME: "name"
}

/**
 * Extended property ol.control.Control
 * @enum {string}
 */
ol.ExtendedOlControlProperty = {
	ID: "id", 
	NAME: "name"
}

/**
 * Extended property ol.interaction.Interaction
 * @enum {string}
 */
ol.ExtendedOlInteractionProperty = {
	ID: "id", 
	NAME: "name"
}

/**
 * Extended property ol.Overlay
 * @enum {string}
 */
ol.ExtendedOverlayProperty = {
  ID: 'id'
};

/********************************************************/
/*                                                      */
/*         Class Map Options e default                  */
/*                                                      */
/********************************************************/  

/** 
 * Classe di configurazione dei layer di base da aggiungere di default alla mappa.
 * **/
ClassBuilder.BasicLayerInfo = {
	/**
     * {boolean} false per default
     */
//	hasBingRoad: false,
//	hasBingAerial: false,
//	hasBingAerialWithLabels: false,	
	hasOpenStreetMap: true,
	/** Removed in Ol 3.17.0 
	 *  ol.source.MapQuest removal
		Because of changes at MapQuest (see: https://lists.openstreetmap.org/pipermail/talk/2016-June/076106.html) 
		we had to remove the MapQuest source for now (see #5484 for details).
	 * */
	hasGoogleSat: false,
	hasGoogleHybrid: false
};


/** 
 * Classe contenente le impostazioni di configurazione di default della mappa
 * **/
ClassBuilder.BasicMapOptions = {
		/** La projection con la quale l'entità sono gestite. **/
		sourceProjection: "EPSG:4326",
		/** La projection con la quale l'entità sono disegnate in mappa. **/
		destProjection: "EPSG:4326",
		/** Nella misura in cui vincola il centro, in altre parole, il centro non può essere impostato fuori misura. **/
		extent: appConfig.mapExtent, //[13.425, 39.758, 18.512, 42.204],
		/** Array[lon, lat] rappresentanti la coordinata sulla quale centrare la mappa **/
		center: appConfig.mapCenter, //[16.621036, 40.984266], 
		/** Identificativo dell'elemento (div) in cui disegnare la mappa **/
		targetMap: null,
		/** Identificativo dell'elemento (div) in cui è incluso l'elemento (div) dell'albero dei layer **/
		targetContainerTree: "areeTematiche",
		/** Identificativo dell'elemento (div) in cui disegnare l'albero dei layer **/
		targetTree: "layoutTree",
		/** 
		 * Url del servizio che restituisce il json con il quale disegnare l'albero(Categoria e sottocategoria). 
		 * L'url che mi aspetto prende come parametri d'ingresso l'id profilo e l'id mappa necessariamente.
	     * In generale, questi parametri li passo all'url facendo il replace delle sequenti stringhe:
	     *   - {value1} per l'id profilo.
	     *   - {value2} per l'id mappa.
	     * 
	     * Es url: "/getTreeCategoria.json"
		 * **/
		treeUrl: null,
		/** Url del servizio che restituisce il json con l'elenco dei layer da aggiungere alla mappa. **/
		layerUrl: null,
		/** Url del servizio che restituisce il json con l'elenco degli stili con cui rappresentare i layer su mappa. **/
		layerStyleUrl: null,
		/** Individua se e quali layer aggiungere di default alla mappa. **/
		basicLayers: ClassBuilder.BasicLayerInfo,
		/** Livello di zoom utilizzato per calcolare la risoluzione iniziale per la visualizzazione. **/
		zoom: 6,
		/** Il livello di zoom minimo utilizzato. **/
		minZoom: 0,
		/** Il livello di zoom massimo utilizzato. **/
		maxZoom: 28,
		/** Risoluzione minima **/
//		minResolution: null,
		/** Risoluzione massima **/
//		maxResolution: null,
		/** Indica il livello di zoom al quale portare la mappa quando viene geolocalizzata la posizione dell'utente. **/
		geolocateZoom: 12,
		/** Opzione per mostrare tutti gli attributi associati con le fonti di livello nella mappa. **/
		enableAttribution: false,
		/** Opzione per abilitare la rotazione della mappa e il pulsante per ripristinare la rotazione a 0. **/
		enableRotation: false,
		/** Opzione per abilitare lo zoom in e lo zoom out. **/
		enableZoom: true,
		/** Opzione che fornisce un pulsante per attivare o disattivare la mappa in modalità a schermo intero. **/
		enableFullscreen: false,
		/** Un controllo per mostrare le coordinate 2D del cursore del mouse. **/
		enableMousePosition: false,
		/** Un controllo con 2 pulsanti, uno per lo zoom in e una per lo zoom out e lo slider centrale per spostarsi direttamente ad un certo livello di zoom. **/
		enableZoomSlider: false,
		/** Un controllo pulsante che, se premuto, cambia la visualizzazione della mappa in misura specifica. **/
		enableZoomToExtent: true,
		/** Un controllo pulsante che, se premuto, attiva/disattiva la visualizzazione della mappa in 3D/2D. **/
		enable3D: false,
		/** Un controllo pulsante che, se premuto, attiva/disattiva la visualizzazione dell'albero dei layer **/
		enableLayersTree: false,
		/** Un controllo pulsante che, se premuto, viene individuata la posizione di un utente. **/
		enableGeolocation: false,
		/** Creare un nuovo controllo con una mappa in qualità di una mappa panoramica per un'altra mappa definita. **/
		enableOverviewMap: false,
		/** Un controllo che visualizza distanze x assi grezze, calcolato per il centro della finestra. **/
		enableScaleLine: false,
		/** Un controllo per misurare la lunghezza dell'arco tracciato. **/
		enableMeasureLength: false,
		/** Un controllo per misurare la grandezza della superficie tracciata. **/
		enableMeasureArea: false,
		/** Identificativo del profilo della mappa (Entrambi determinabili consultando l'interfaccia di gestione mappe) **/
		profiloId: null,
		/** Identificativo della mappa (Entrambi determinabili consultando l'interfaccia di gestione mappe) **/
		mappaId: null
};


/** 
 * Classe contenente le impostazioni di configurazione della mappa
 **/
ClassBuilder.MapOptions = function (settings){
	/**
	 * APIProperty: sourceProjection
	 * La projection con la quale l'entità sono gestite.
	 * {string}
	 */
	this.sourceProjection = settings.sourceProjection != undefined ? settings.sourceProjection : ClassBuilder.BasicMapOptions.sourceProjection;
	/**
	 * APIProperty: destProjection
	 * La projection con la quale l'entità sono disegnate in mappa.
	 * {string}
	 */
	this.destProjection = settings.destProjection != undefined ? settings.destProjection : ClassBuilder.BasicMapOptions.destProjection,
	/**
	 * APIProperty: extent
	 * Nella misura in cui vincola il centro, in altre parole, il centro non può essere impostato fuori misura.
	 * {Array.<number>} 
	 */
	this.extent = settings.extent != undefined ? settings.extent : ClassBuilder.BasicMapOptions.extent,
	/**
	 * APIProperty: center
	 * Array[lon, lat] rappresentanti la coordinata sulla quale centrare la mappa.
	 * {Array.<number>} 
	 */
	this.center = settings.center != undefined ? settings.center : ClassBuilder.BasicMapOptions.center,
	/**
     * APIProperty: targetMap
     * Identificativo dell'elemento (div) in cui disegnare la mappa
     * {string}
     */
	this.targetMap = settings.targetMap != undefined ? settings.targetMap : ClassBuilder.BasicMapOptions.targetMap,
	/**
	 * APIProperty: targetContainerTree
	 * Identificativo dell'elemento (div) in cui è incluso l'elemento (div) dell'albero dei layer
	 * {Stringa}
	 */
	this.targetContainerTree = settings.targetContainerTree != undefined ? settings.targetContainerTree : ClassBuilder.BasicMapOptions.targetContainerTree,
	/**
     * APIProperty: targetTree
     * Identificativo dell'elemento (div) in cui disegnare l'albero dei layer
     * {String}
     */
	this.targetTree = settings.targetTree != undefined ? settings.targetTree : ClassBuilder.BasicMapOptions.targetTree,
	 /**
     * APIProperty: treeUrl
     * Url del servizio che restituisce il json con il quale disegnare l'albero(Ctegoria e sottocategoria).
     * 
     * NOTA: 
     * L'url che mi aspetto prende come parametri d'ingresso l'id profilo e l'id mappa necessariamente.
     * In generale, questi parametri li passo all'url facendo il replace delle sequenti stringhe:
     *   - {value1} per l'id profilo.
     *   - {value2} per l'id mappa.
     * 
     * Es url: "/getTreeCategoria.json"
     * {String}
     */
	this.treeUrl = settings.treeUrl != undefined ? settings.treeUrl : ClassBuilder.BasicMapOptions.treeUrl,
	/**
     * APIProperty: layerUrl
     * Url del servizio che restituisce il json con l'elenco dei layer da aggiungere alla mappa.
     * {String}
     */
	this.layerUrl = settings.layerUrl != undefined ? settings.layerUrl : ClassBuilder.BasicMapOptions.layersUrl,
	/**
     * APIProperty: layerStyleUrl
     * Url del servizio che restituisce il json con l'elenco degli stili con cui rappresentare i layer su mappa.
     * {String}
     */
	this.layerStyleUrl = settings.layerStyleUrl != undefined ? settings.layerStyleUrl : ClassBuilder.BasicMapOptions.layerStyleUrl,
	/**
     * APIProperty: basicLayersOpt
     * Individua se e quali layer aggiungere di default alla mappa
     * {Object ClassBuilder.BasicLayerInfo}
     */
	this.basicLayers = settings.basicLayers != undefined ? settings.basicLayers : ClassBuilder.BasicMapOptions.basicLayers,
	/**
	 * APIProperty: zoom
	 * Livello di zoom utilizzato per calcolare la risoluzione iniziale per la visualizzazione. 
	 * {intero}
	 */	
	this.zoom = settings.zoom != undefined ? settings.zoom : ClassBuilder.BasicMapOptions.zoom,
	/**
     * APIProperty: minZoom
     * Il livello di zoom minimo utilizzato.
     * {intero}
     */	
	this.minZoom = settings.minZoom != undefined ? settings.minZoom : ClassBuilder.BasicMapOptions.minZoom,
	/**
     * APIProperty: maxZoom
     * Il livello di zoom massimo utilizzato.
     * {intero}
     */	
	this.maxZoom = settings.maxZoom != undefined ? settings.maxZoom : ClassBuilder.BasicMapOptions.maxZoom,
	/**
	 * APIProperty: minResolution
	 * Risoluzione minima.
	 * {number}
	 */	
//	this.minResolution = settings.minResolution != undefined ? settings.minResolution : ClassBuilder.BasicMapOptions.minResolution,
	/**
	 * APIProperty: maxResolution
	 * Risoluzione massima.
	 * {number}
	 */	
//	this.maxResolution = settings.maxResolution != undefined ? settings.maxResolution : ClassBuilder.BasicMapOptions.maxResolution,
	/**
	 * APIProperty: geolocateZoom
	 * Indica il livello di zoom al quale portare la mappa quando viene geolocalizzata la posizione dell'utente. 
	 * {intero}
	 */	
	this.geolocateZoom = settings.geolocateZoom != undefined ? settings.geolocateZoom : ClassBuilder.BasicMapOptions.geolocateZoom,				
	/**
	 * APIProperty: enableAttribution
	 * Opzione per mostrare tutti gli attributi associati con le fonti di livello nella mappa. 
	 * Di default è visualizzato nella parte in basso a destra della mappa.
	 * {boolean}
	 */
	this.enableAttribution = settings.enableAttribution != undefined ? settings.enableAttribution : ClassBuilder.BasicMapOptions.enableAttribution,
	/**
	 * APIProperty: enableRotation
	 * Opzione per abilitare la rotazione della mappa e il pulsante per ripristinare la rotazione a 0. 
	 * {boolean} 
	 */
	this.enableRotation = settings.enableRotation != undefined ? settings.enableRotation : ClassBuilder.BasicMapOptions.enableRotation,
	/**
	 * APIProperty: enableZoom
	 * Opzione per abilitare lo zoom in e lo zoom out. 
	 * {boolean}
	 */
	this.enableZoom = settings.enableZoom != undefined ? settings.enableZoom : ClassBuilder.BasicMapOptions.enableZoom,
	/**
	 * APIProperty: enableFullscreen
	 * Opzione che fornisce un pulsante per attivare o disattivare la mappa in modalità a schermo intero.
	 * {boolean} Il valore predefinito è false. 
	 */
	this.enableFullscreen = settings.enableFullscreen != undefined ? settings.enableFullscreen : ClassBuilder.BasicMapOptions.enableFullscreen,
	/**
	 * APIProperty: enableMousePosition
	 * Un controllo per mostrare le coordinate 2D del cursore del mouse. Per default, queste sono nella proiezione definita nella
	 * proprietà "sourceProjection" di questo oggetto.  
	 * {boolean}
	 */
	this.enableMousePosition = settings.enableMousePosition != undefined ? settings.enableMousePosition : ClassBuilder.BasicMapOptions.enableMousePosition,
	/**
	 * APIProperty: enableZoomSlider
	 * Un controllo con 2 pulsanti, uno per lo zoom in e una per lo zoom out e lo slider centrale per spostarsi direttamente 
	 * ad un certo livello di zoom.
	 * {boolean}
	 */
	this.enableZoomSlider = settings.enableZoomSlider != undefined ? settings.enableZoomSlider : ClassBuilder.BasicMapOptions.enableZoomSlider,
	/**
	 * APIProperty: enableZoomToExtent
	 * Un controllo pulsante che, se premuto, cambia la visualizzazione della mappa in misura specifica. 
	 * {boolean}
	 */
	this.enableZoomToExtent = settings.enableZoomToExtent != undefined ? settings.enableZoomToExtent : ClassBuilder.BasicMapOptions.enableZoomToExtent,
	/**
	 * APIProperty: enable3D
	 * Un controllo pulsante che, se premuto, attiva/disattiva la visualizzazione della mappa in 3D/2D. 
	 * {boolean}
	 */
	this.enable3D = settings.enable3D != undefined ? settings.enable3D : ClassBuilder.BasicMapOptions.enable3D,
	/**
     * APIProperty: enableLayersTree
     * Un controllo pulsante che, se premuto, crea e attiva/disattiva la visualizzazione dell'albero dei layer
     * {boolean}
     */
	this.enableLayersTree = settings.enableLayersTree != undefined ? settings.enableLayersTree : ClassBuilder.BasicMapOptions.enableLayersTree,
	/**
	 * APIProperty: enableGeolocation
	 * Un controllo pulsante che, se premuto, viene individuata la posizione di un utente.
	 * {boolean}
	 */
	this.enableGeolocation = settings.enableGeolocation != undefined ? settings.enableGeolocation : ClassBuilder.BasicMapOptions.enableGeolocation,
	/**
	 * APIProperty: enableOverviewMap
	 * Creare un nuovo controllo con una mappa in qualità di una mappa panoramica per un'altra mappa definita.
	 * {boolean}
	 */
	this.enableOverviewMap = settings.enableOverviewMap != undefined ? settings.enableOverviewMap : ClassBuilder.BasicMapOptions.enableOverviewMap,
	/**
	 * APIProperty: enableScaleLine
	 * Un controllo che visualizza distanze x assi grezze, calcolato per il centro della finestra.
	 * {boolean}
	 */
	this.enableScaleLine = settings.enableScaleLine != undefined ? settings.enableScaleLine : ClassBuilder.BasicMapOptions.enableScaleLine,
	/**
	 * APIProperty: enableMeasureLength
	 * Un controllo che attiva/disattiva lo strumento per disegnare su mappa e misurare la lunghezza dell'arco tracciato.
	 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
	 * {boolean}
	 */
	this.enableMeasureLength = settings.enableMeasureLength != undefined ? settings.enableMeasureLength : ClassBuilder.BasicMapOptions.enableMeasureLength,
	/**
	 * APIProperty: enableMeasureArea
	 * Un controllo che attiva/disattiva lo strumento per disegnare su mappa un poligono e misurarne la grandezza della superficie.
	 * NOTA: la misura avviene prendendo in considerazione la curvatura terrestre
	 * {boolean}
	 */
	this.enableMeasureArea = settings.enableMeasureArea != undefined ? settings.enableMeasureArea : ClassBuilder.BasicMapOptions.enableMeasureArea,
	/**
	 * APIProperty: profiloId
	 * Identificativo della mappa (Entrambi determinabili consultando l'interfaccia di gestione mappe)
	 * {Integer}
	 */
	this.mappaId = settings.mappaId != undefined ? settings.mappaId : ClassBuilder.BasicMapOptions.mappaId,
	/**
	 * APIProperty: mappaId
	 * Identificativo del profilo della mappa (Entrambi determinabili consultando l'interfaccia di gestione mappe).
	 * {Integer}
	 */
	this.profiloId = settings.profiloId != undefined ? settings.profiloId : ClassBuilder.BasicMapOptions.profiloId,
			
	// Call parent constructor
	ol.Object.call(this);
};
// add class as subclasses of abstract class ol.Object
ol.inherits(ClassBuilder.MapOptions, ol.Object);


/********************************************************/
/*                                                      */
/*          Extended ol.Geolocation                     */
/*                                                      */
/*  Aggiunto l'attributo "map", "geolocateZoom" e i     */
/*  metodi set e get.									*/
/*                                                      */
/********************************************************/    

/**
 * Mio oggetto Ol.Geolocation dove ho aggiunto a quelli già previsti 
 * la proprietà "map".
 */
var ExtendedOlGeolocation = function(options){
	// Call parent constructor
    var geolocation = new ol.Geolocation(options);
    	// set map 
    	geolocation.setMap(options.map !== undefined ? options.map : null);
    	// set zoom 
    	geolocation.setGeolocateZoom(options.geolocateZoom !== undefined ? options.geolocateZoom : null);
    return geolocation;
}; 

/**
 * Set map on class ol.Geolocation
 * @param {ol.Map}
 */
ol.Geolocation.prototype.setMap = function(map) {
  this.set(ol.ExtendedOlGeolocationProperty.MAP, map);
};

/**
 * Ritorna l'instanza di map a cui il controllo è stato aggiunto.
 * @return {ol.Map}
 */
ol.Geolocation.prototype.getMap = function() {
  return (this.get(ol.ExtendedOlGeolocationProperty.MAP));
};

/**
 * Set del livello di zoom on class ol.Geolocation
 * @param {number}
 */
ol.Geolocation.prototype.setGeolocateZoom = function(geolocateZoom) {
  this.set(ol.ExtendedOlGeolocationProperty.GEOLOCATE_ZOOM, geolocateZoom);
};

/**
 * Ritorna il livello di zoom al quale portare la mappa quando viene geolocalizzata la posizione dell'utente. 
 * @return {number}
 */
ol.Geolocation.prototype.getGeolocateZoom = function() {
  return (this.get(ol.ExtendedOlGeolocationProperty.GEOLOCATE_ZOOM));
};


/********************************************************/
/*                                                      */
/*           Extended ol.layer.Base                     */
/*                                                      */
/*   Aggiunti gli attributi "id" e "name" e i metodi    */
/*   set e get agli attributi di base già previsti.     */
/*                                                      */
/********************************************************/

/**
 * Ritorna l'identificativo del layer.
 * @return {String}
 */
ol.layer.Base.prototype.getId = function() {
  return (this.get(ol.ExtendedOlLayerBaseProperty.ID));
};

/**
 * Set id on class ol.layer.Base
 * @param {String}
 */
ol.layer.Base.prototype.setId = function(id) {
  this.set(ol.ExtendedOlLayerBaseProperty.ID, id);
};

/**
 * Ritorna il nome del layer.
 * @return {String}
 */
ol.layer.Base.prototype.getName = function() {
  return (this.get(ol.ExtendedOlLayerBaseProperty.NAME));
};

/**
 * Set del nome on class ol.layer.Base
 * @param {String}
 */
ol.layer.Base.prototype.setName = function(name) {
  this.set(ol.ExtendedOlLayerBaseProperty.NAME, name);
};


/********************************************************/
/*                                                      */
/*                    Map Functions                     */
/*                                                      */
/*   The following functions deal with get layers map   */
/*                                                      */
/********************************************************/ 

/**
 * APIMethod: getLayersByName
 * Ottenere il layer con nome corrispondente al nome dato.
 *
 * Parametri:
 *   match - {String | Object} Nome del layer.  
 *  
 * Se non vengono trovati layer, viene restituito null.
 *
 * Returns:
 * {ol.layer.Base} 
 * Il layer corrispondente al nome dato. Viene restituito null se non vengono trovate corrispondenze.
 */
ol.Map.prototype.getLayerByName = function(match) {
	var test = (typeof match.test == "function");
     
	var layers = this.getLayerGroup().getLayers();
	
	var found = null;
	  
	layers.forEach(function(layer, i) {
        if(layer.getName() == match || (test && match.test(layer.getName()))){
        	found = layer;
        };
    });
    return found; 
};

/**
 * APIMethod: getLayersById
 * Ottenere il layer con identificativo corrispondente all 'id dato.
 *
 * Parametri:
 *   match - {String | Object} Identificativo del layer.  
 *  
 * Se non vengono trovati layer, viene restituito null.
 *
 * Returns:
 * {ol.layer.Base} 
 * Il layer corrispondente all'identificativo dato. Viene restituito null se non vengono trovate corrispondenze.
 */
ol.Map.prototype.getLayerById = function(match) {
	var test = (typeof match.test == "function");
     
	var layers = this.getLayerGroup().getLayers();
	
	var found = null;
	  
	layers.forEach(function(layer, i) {
        if(layer.getId() == match || (test && match.test(layer.getId()))){
        	found = layer;
        };
    });
    return found; 
};

/**
 * APIMethod: getNumLayers
 * 
 * Returns:
 *  {Int} Numero di layer aggiunti alla mappa.
 */
ol.Map.prototype.getNumLayers = function () {
	var layers = this.getLayerGroup().getLayers();
	
	return (layers != undefined && layers != null ) ? layers.getLength() : 0;
};

/**
 * APIMethod: addLayers 
 *
 * Parameters:
 *  layers - {Array(<ol.layer.BaseLayer>)} 
 */    
ol.Map.prototype.addLayers = function (layers) {
  for (var i=0, len=layers.length; i<len; i++) {
      this.addLayer(layers[i]);
  }
};


/********************************************************/
/*                                                      */
/*              Extended ol.control.Control             */
/*                                                      */
/*   Aggiunti gli attributi "id" e "name" e i metodi    */
/*   set e get agli attributi di base già previsti.     */
/*                                                      */
/********************************************************/

/**
 * Ritorna l'identificativo del controllo.
 * @return {String}
 */
ol.control.Control.prototype.getId = function() {
  return (this.get(ol.ExtendedOlControlProperty.ID));
};

/**
 * Set id on class ol.control.Control
 * @param {String}
 */
ol.control.Control.prototype.setId = function(id) {
  this.set(ol.ExtendedOlControlProperty.ID, id);
};

/**
 * Ritorna il nome del controllo.
 * @return {String}
 */
ol.control.Control.prototype.getName = function() {
  return (this.get(ol.ExtendedOlControlProperty.NAME));
};

/**
 * Set name on class ol.control.Control
 * @param {String}
 */
ol.control.Control.prototype.setName = function(name) {
  this.set(ol.ExtendedOlControlProperty.NAME, name);
};

/********************************************************/
/*                                                      */
/*                 Control Functions                    */
/*                                                      */
/*  The following functions deal with get Controls      */
/*					from the Map   					    */
/*                                                      */
/********************************************************/

/**
 * APIMethod: getControlById
 * Ritorna il controllo dove l'identificativo (ID) è uguale a quello dato.
 *
 * Parameters:
 *    match - {String} A control ID.
 *
 * Returns:
 * {ol.control.Control} 
 * Il controllo corrispondente all'identificativo dato. Viene restituito null se non vengono trovate corrispondenze.
 */
ol.Map.prototype.getControlById = function(match) {
	var controls = this.getControls();
	
	var found = null;
	  
	controls.forEach(function(control, i) {
        if(control.getId() == match){
        	found = control;
        };
    });
    return found; 
};


/********************************************************/
/*                                                      */
/*       Extended ol.interaction.Interaction            */
/*                                                      */
/*   Aggiunti gli attributi "id" e "name" e i metodi    */
/*   set e get agli attributi di base già previsti.     */
/*                                                      */
/********************************************************/

/**
 * Ritorna l'identificativo dell'interaction.
 * @return {String}
 */
ol.interaction.Interaction.prototype.getId = function() {
  return (this.get(ol.ExtendedOlInteractionProperty.ID));
};

/**
 * Set id on class ol.interaction.Interaction
 * @param {String}
 */
ol.interaction.Interaction.prototype.setId = function(id) {
  this.set(ol.ExtendedOlInteractionProperty.ID, id);
};

/**
 * Ritorna il nome dell'interaction.
 * @return {String}
 */
ol.interaction.Interaction.prototype.getName = function() {
  return (this.get(ol.ExtendedOlInteractionProperty.NAME));
};

/**
 * Set name on class ol.interaction.Interaction
 * @param {String}
 */
ol.interaction.Interaction.prototype.setName = function(name) {
  this.set(ol.ExtendedOlInteractionProperty.NAME, name);
};

/********************************************************/
/*                                                      */
/*                Interaction Functions                 */
/*                                                      */
/*  The following functions deal with get Interactions  */
/*					from the Map   					    */
/*                                                      */
/********************************************************/

/**
 * APIMethod: getInteractionById
 * Ritorna l'interaction dove l'identificativo (ID) è uguale a quello dato.
 *
 * Parameters:
 *    match - {String} An interaction ID.
 *
 * Returns:
 * {ol.interaction.Interaction} 
 * L'interaction corrispondente all'identificativo dato. Viene restituito null se non vengono trovate corrispondenze.
 */
ol.Map.prototype.getInteractionById = function(match) {
	var interactions = this.getInteractions();
	
	var found = null;
	  
	interactions.forEach(function(interaction, i) {
        if(interaction.getId() == match){
        	found = interaction;
        };
    });
    return found; 
};


/********************************************************/
/*                                                      */
/*       			Extended ol.Overlay   		        */
/*                                                      */
/*   Aggiunti gli attributi "id" e i metodi   			*/
/*   set e get agli attributi di base già previsti.     */
/*                                                      */
/********************************************************/

/**
 * Ritorna l'identificativo dell'interaction.
 * @return {String}
 */
ol.Overlay.prototype.getId = function() {
  return (this.get(ol.ExtendedOverlayProperty.ID));
};

/**
 * Set id on class ol.Overlay
 * @param {String}
 */
ol.Overlay.prototype.setId = function(id) {
  this.set(ol.ExtendedOverlayProperty.ID, id);
};

/********************************************************/
/*                                                      */
/*               	 Overlay Functions                  */
/*                                                      */
/*  The following functions deal with get Overlay  		*/
/*					from the Map   					    */
/*                                                      */
/********************************************************/

/**
 * APIMethod: getOverlayById
 * Ritorna l'overlay dove l'identificativo (ID) è uguale a quello dato.
 *
 * Parameters:
 *    match - {String} An overlay ID.
 *
 * Returns:
 * {ol.Overlay} 
 * L'overlay corrispondente all'identificativo dato. Viene restituito null se non vengono trovate corrispondenze.
 */
ol.Map.prototype.getOverlayById = function(match) {
	var overlays = this.getOverlays();
	
	var found = null;
	  
	overlays.forEach(function(overlay, i) {
        if(overlay.getId() == match){
        	found = overlay;
        };
    });
    return found; 
};

