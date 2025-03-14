/**
 * CONFIGURAZIONI
 * 
 * Nel seguente file sono presenti le configurazioni per il: 
 * 		- namespace
 * 		- mappa
 * 		- menu delle aree tematiche e dei tematismi 
 **/
appConfig={
		
		/**
		 * Definizione del nameSpace Urbamid
		 * @requires jquery-3.4.1.min.js, ...
		 */
		urbamidProtocol			: "https",
		urbamidHostname			: "urbamidplus-collaudo.comune.messina.it",
		urbamidPort				: "",
		urbamidService			: "/UrbamidWeb/",
		urbamidMappa			: "mappaController/getMappa",
		urbamidMappe			: "mappaController/getMappe.json",
		urbamidDuplicaMappa		: "mappaController/duplicaMappa.json",
		urbamidRuoli			: "adminController/getFunzionalita",
		urbamidMenuFunzionalita : "adminController/getMenuFunzionalita",

		urbamidMappaTool		: "mappaController/getMapAttTools",
		urbamidMappaRicerca		: "mappaController/getMapAttRicerche",
		urbamidMappeTemi		: "mappaController/getAllTemaMappe",
		urbamidMappeLayer		: "layerController/group-layer-mappa",
		
		urbamidCercaParticella	: "catastoController/findParticella",
		urbamidCercaFoglio		: "catastoController/findFoglio",
		
		/**
		 * Definizione del nameSpace geoserver
		 * @requires jquery-3.4.1.min.js, ...
		 */
		protocol				: "https",
		hostname				: "urbamidplus-collaudo.comune.messina.it",
		port					: "",
		geoserverService		: "/geoserver/",
		workspaceService		: "urbamid/",
		restService				: "rest/",
		wmsService				: "wms",
		wfsService				: "wfs/",
			
		/**
		 * Definizione della mappa
		 * @requires ol.js,...
		 */
		map						: null,																	// Object mappa di OpenLayers
		idTagMappa				: "map",																// Id del tag dove verrà instaziata la mappa
		layerOsm				: new ol.layer.Tile({ source: new ol.source.OSM()}),					// Layer di default OpenStreeMap
		nameLayerOsm			: "layer_osm",															// Nome del Layer di default di OpenStreetMap
		//mapCenter				: [15.554462, 38.206314],												// Coordinate del centro della mappa (Messina)
		mapCenter				: [1731514.79, 4608612.20],												// Coordinate del centro della mappa (Messina)
		mapZoom	        		: 11,																	// Zoom di default della mappa
		//mapProjection			: "EPSG:4326",															// Projection per la mappa
		mapProjection			: "EPSG:3857",
		geoserverProjection		: "EPSG:4326",															// Projection di Geoserver
		mapExtent				: [15.72052027, 38.22631407, 14.85626221, 38.21444608], 
		
		/**
		 * UTILIZZATO PER IL MENU
		 * Contiene tutti i layer del menu (Key:areePercorseDalFuoco_ATE_areePercorseDalFuoco_TE_APF_2007 	value: LAYER
		 * la key viene generata con 		generateItemId(parentAreaTematica, parentTematismo, parentLayerGroup, layerName)
		 **/
		layerMap				: new Object(),															 
		/**
		 * UTILIZZATO PER AVERE IL LAYER DATO L'ID
		 * Contiene i layer attivati (Key:APF_2007 	value: LAYER
		 * la key e' l'id del layer 
		 **/
		layerEnabledMap			: new Object(),	
		/**
		 * UTILIZZATO 
		 * Contiene i layer attivati (Key:areePercorseDalFuoco_tematismo%APF_2007 	value: LAYER
		 * la key e' parent%idLayer 
		 **/
		vectorLayerMap			: new Object(),
		/**Array ordinati dei layer attivati**/
		vectorLayerEnabledArray	: [],
		
		/**GEOSERVER**/
		workspaceGeoserver		: "urbamid",															// Workspace di default Geoserver
		acceptsFormatGeoserver	: "application/json",
		/*Controlli Mappa*/
		measureEnable			: true,																	// Boolean per abilitare la misurazione
		zoomEnable				: true,																	// Boolean per abilitare lo zoom
		positionEnable			: true,																	// Boolean per abilitare la posizione in mappa
		scaleEnable				: true,																	// Boolean per abilitare la scala
		overviewMapEnable		: true,																	// Boolean per abilitare il quadrato di zoom in mappa
		numFeatureSelect		: 100,																	// Numero di feature selezionabili
        /** I seguenti parametri sono utilizzati per le misurazioni su mappa **/
        sketch					: null,
        helpTooltipElement		: null,
        helpTooltip				: null,
		
		/**
		 * I seguenti parametri sono per la visualizzazione delle selezioni su mappa.
		 * Se si imposta un numMaxFeatureSelected di "n" elementi, devono essere definiti "n" featureStyleSelected e "n" featureSourceSelected.
		 * Inoltre nel definire l'n-esimo stile, va aggiunto nel css il rispettivo class (oneSel, twoSel, threeSel...)
		 */
		numFeatureSelected		: 0,
		featureSelected			: [],
		featureOnClick			: [],
		numMaxFeatureSelected	: 5,
		featureStyleSelected 	: {"olStyle":new ol.style.Style({stroke: new ol.style.Stroke({color: '#861611d9',width: 2}),fill: new ol.style.Fill({color: '#8616119c'})}),"cssStyle":"oneSel"},
        featureSourceSelected 	: new ol.source.Vector({format: new ol.format.GeoJSON(),strategy: ol.loadingstrategy.bbox}),
        featureVectorSelected 	: {},
        listFeatureAdded		: {},
        
        /**
         * I seguenti parametri sono utilizzati per la visualizzazione su mappa dei risultati delle ricerche
         */        							
        vectorSourceSearched	: new ol.source.Vector(),
        vectorStyleSearched		: new ol.style.Style({
        											stroke	: new ol.style.Stroke({color: 'rgba(255,204,51, 1.0)',width: 2}), 
        											fill	: new ol.style.Fill({color: 'rgba(255,204,51, 0.6)'})
        }),
        							
		/**
		 * Definizione del menu
		 * @requires jstree.min.js,...
		 */
        jsTreeMenu				: null,
		emptyMenu				: "Non sono presenti voci di menu",										// Messaggio se non sono presenti Layer-LayerGroup su Geoserver
		menuJson 				: {"core":{																// Json del menu per jsTree
											"data":[]
								    }
									,"plugins":["sort"/*"checkbox","contextmenu"*/]
								  },
								  
		ricercheGeoserver		: [],																	// Lista delle ricerche abilitate, recuperate dalle AreeTematiche e Tematismi
		elencoRicerche			: ["ricercaToponimiLocalita","ricercaCatastale"],						// Elenco delle possibili ricerche aggiunte ad Urbamid
		divRicercheId			: "nav-risultatiRicerca",												// Id del div dove saranno presenti le ricerche di Urbamid
		itemLoaded				: new Object(),															// Lista dei figli legati al padre attravers ID
		itemIcon				: {"folder":"./images/folder.png","folder_layer":"./images/layer.png","layer":"./images/layer.png"},
								  
        getUrlVars 			    : function (){
									    var vars = {};
									    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
									        vars[key] = value;
									    });
									    return vars;
								  },
								  
	    /**************************************************************************************************************************************************/
        /********* SOLO PER IL CATALOGO ******************************************************************************************************** INIZIO ***/
		jsTreeCatalogo			: null,
		catalogoJson			: {"core":{																// Json del menu per jsTree
										"data":[]
										}
								  		,"plugins":["sort"]
								  },
		restartCatalogJson		: function (){
									  appConfig.catalogoJson =  {
											  					 "core":{
																	"data":[]
																 }
									  							 ,"plugins":["sort"]
									  							};
								  },								  
		itemC_Loaded			: new Object(),															// Lista dei figli legati al padre attravers ID
		itemC_Icon				: {"folder":"./images/folder.png","folder_layer":"./images/layer.png","layer":"./images/layer.png"},
		/********* SOLO PER IL CATALOGO ******************************************************************************************************** FINE   ***/
		/**************************************************************************************************************************************************/								  
};

/**
 * Definizione dei controlli della mappa
 * @requires ol.js,...
 */
appConfig.overviewView = new ol.View({projection:"EPSG:4326"});
appConfig.controls	= [
    				   appConfig.zoomEnable			? new ol.control.Zoom(): null,												// Livello di zoom 
    				   appConfig.positionEnable		? new ol.control.MousePosition({											// Posizione del mouse
    					    													projection: 'EPSG:4326',
    					    													coordinateFormat: function(coordinate) {
    					    														return ol.coordinate.format(coordinate, '{y}, {x}', 7);
    					    													}}) : null, 
    				   appConfig.scaleEnable		? new ol.control.ScaleLine : null, 											// Abilitazione della scala
    				   appConfig.overviewMapEnable  ? new ol.control.OverviewMap({view: appConfig.overviewView}) : null			// Box per la visualizzazione della mappa in un box
    						   
];

$(document).ready(function() {
	
	/** 
	 * Definizione del vector per la visualizzazione dei risultati di una ricerca
	 **/
	appConfig.vectorLayerSearched = new ol.layer.Vector({
        source: appConfig.vectorSourceSearched,
        style: appConfig.vectorStyleSearched
	});
     
	/** 
	 * Definizione del vector per la visualizzazione dei risultati di una ricerca
	 * Definisco per il vector per un n (appConfig.numMaxFeatureSelected) di selezioni  
	 **/
	appConfig.featureVectorSelected = null;//new ol.layer.Vector({source	: appConfig.featureSourceSelected,style	: appConfig.featureStyleSelected.olStyle}); 
});