
/** ==================================================================================== **/
/**  GESTIONE LAYER : ol.layer **/
/** ==================================================================================== **/

var LayersBuilder = LayersBuilder || {};

/**
 * Bing maps - Type of imagery.
 */
var BingMaps_ImageryType = [ 'Road', 'Aerial', 'AerialWithLabels', 'collinsBart', 'ordnanceSurvey' ];

/** 
 * Funzione per la costruzione dei layer di base della mappa.
 * 
 * @param {@link ClassBuilder.BasicLayerInfo} OPTIONS_LAYER
 * 
 * @return {@link Array.<ol.layer>} 
 ***/
LayersBuilder.buildCustomBaseLayer = function (OPTIONS_LAYER){
 try{
   var baseLayers = new Array();
	
   /** BING ROAD **/
	if(OPTIONS_LAYER.hasBingRoad){
		try{
		    var bingRoad = 
		    	new ol.layer.Tile({ 
		    		name: "Bing Road",
		    		source: new ol.source.BingMaps({
//		    			  culture: "it-IT",
		    			  /** Bing Maps API key **/
		    		      key: ol.ApplicationProperty.BING_MAPS_KEY,
		    		      /** Type of imagery. **/
		    			  imagerySet: BingMaps_ImageryType[0]
		    		})
		        });
		    
		    baseLayers.push(bingRoad);
		}catch (e) {
			console.log("Error add base layer bing road " + e);
		}
	}
   /** BING AERIAL **/
	if(OPTIONS_LAYER.hasBingAerial){
		try{
		    var bingAerial = 
		    	new ol.layer.Tile({ 
		    		name: "Bing Aerial",
		    		source: new ol.source.BingMaps({
//		    			  culture: "it-IT",
		    			  /** Bing Maps API key **/
		    		      key: ol.ApplicationProperty.BING_MAPS_KEY,
		    		      /** Type of imagery. **/
		    			  imagerySet: BingMaps_ImageryType[1],
		    			  /** Whether to wrap the world horizontally **/
		    			  wrapX: false 
		    		})
		        });
		    
		    baseLayers.push(bingAerial);
		}catch (e) {
			console.log("Error add base layer bing Aerial " + e);
		}
	}
   /** BING AERIAL WITH LABELS **/
	if(OPTIONS_LAYER.hasBingAerialWithLabels){
		try{
		    var bingAerialWithLabels = 
		    	new ol.layer.Tile({ 
		    		name: "Bing AerialWithLabels",
		    		source: new ol.source.BingMaps({
//		    			  culture: "it-IT",
		    			  /** Bing Maps API key **/
		    		      key: ol.ApplicationProperty.BING_MAPS_KEY,
		    		      /** Type of imagery. **/
		    			  imagerySet: BingMaps_ImageryType[2],
		    			  /** Whether to wrap the world horizontally **/
		    			  wrapX: false
		    		})
		        });
		    
		    baseLayers.push(bingAerialWithLabels);
		}catch (e) {
			console.log("Error add base layer bing AerialWithLabels " + e);
		}
	}
   /** OSM **/
	if(OPTIONS_LAYER.hasOpenStreetMap){
		try{
		    var osm = new ol.layer.Tile({ name: "Osm", source: new ol.source.OSM({ /** Whether to wrap the world horizontally **/ wrapX: false }) });
		    
		    baseLayers.push(osm);
		}catch (e) {
			console.log("Error add base layer openstreetmap " + e);
		}
	}	
	
    /** GOOGLE SAT **/
	/** Removed in Ol 3.17.0 
	 *  ol.source.MapQuest removal
		Because of changes at MapQuest (see: https://lists.openstreetmap.org/pipermail/talk/2016-June/076106.html) 
		we had to remove the MapQuest source for now (see #5484 for details).
	 * */
//	if(OPTIONS_LAYER.hasGoogleSat){
//		try{
//		    var googleSat = new ol.layer.Tile({ source: new ol.source.MapQuest({layer: "sat"}) });
//		    
//		    baseLayers.push(googleSat);
//		}catch (e) {
//			console.log("Error add base layer google sat " + e);
//		}
//	}
    /** GOOGLE HYBRID **/
	/** Removed in Ol 3.17.0 
	 *  ol.source.MapQuest removal
		Because of changes at MapQuest (see: https://lists.openstreetmap.org/pipermail/talk/2016-June/076106.html) 
		we had to remove the MapQuest source for now (see #5484 for details).
	 * */
//	if(OPTIONS_LAYER.hasGoogleHybrid){
//		try{
//		    var googleHyb = new ol.layer.Tile({ source: new ol.source.MapQuest({layer: "hyb"}) });
//		    
//		    baseLayers.push(googleHyb);
//		}catch (e) {
//			console.log("Error add base layer google hybrid " + e);
//		}
//	}

//	mappa.addLayers( [new OpenLayers.Layer("vuoto",{isBaseLayer: true, allOverlays: true})] );	  // TODO
	
	if(baseLayers == null || baseLayers.length == 0) console.log("ATTENZIONE!!! Non sono stati definiti layer di base.");
		
 	return baseLayers;
 }catch(e){
	 console.log("Error build layers " + e);
 }
};


/**
 * Funzione che crea ed inizializza l'albero dei layer con le categorie definite per un certo profilo e mappa.
 * 
 * @param {@link ClassBuilder.MapOptions} OPTIONS
 */
LayersBuilder.buildTreeCategory = function (OPTIONS) {
 try{
	/** controllo se l'albero dei layer è abilitato, se no interrompo l'esecuzione **/
	if(!OPTIONS.enableLayersTree) return ;
			
	// loading ..
	appUtil.showLoader(null, jQuery.i18n.prop("Loader.buildLayer"));
	 
	/**
	 * Restituisce il json con il quale disegnare l'albero(Categoria e sottocategoria) 
	 * 
	 * Da RICORDARE che l'id profilo e l'id mappa sono sostituiti all'url facendo il replace delle sequenti stringhe:
	 *   - {value1} per l'id profilo.
	 *   - {value2} per l'id mappa.
	 * 
	 * Es url: "/getTreeCategoria.json/{value1}/{value2}"
	 * */
	var url = OPTIONS.treeUrl;
		/** replace dei parametri **/
	    url = "." + url + "/" + OPTIONS.profiloId + "/" + OPTIONS.mappaId;
	
	/**
	 * Build tree
	 */
	$("#" + OPTIONS.targetTree + "").jstree({
		"core" : {
		  "data": {
			"async": false,
		    "url": function (node) {
		       return url;
		    }
		  },
		  "error" : function(error){
			  console.log("Error call service return json tree " + error);
		  },
		  "strings": {
			  /*
			   * Configurare le varie stringhe utilizzate in tutto l'albero
			   * La chiave è la stringa da sostituire e il valore è la nostra sostituzione.
			   */
	          "Loading ..." : jQuery.i18n.prop("OpenLayers.loadingTree")
	      },
	      /* the open / close animation duration in milliseconds - set this to false to disable the animation (default is 200) **/
	      "animation": 200,
	      /* a boolean indicating if multiple nodes can be selected **/
		  "multiple": true,
		  /* Should the node should be toggled if the text is double clicked . Defaults to true */
		  "dblclick_toggle": true
		},
		"checkbox" : {
		    "keep_selected_style" : false
		},
		"types" : {
		    "default" : {
		       "icon" : "jstree-nothemeicon"
		    }
		},
		"search": {
			/* Indicates if the search should be fuzzy or not (should chnd3 match child node 3). Default is false. */
			"fuzzy": false,
			/** Indicates if the search should be case sensitive. Default is false. **/
			"case_sensitive" : false,
			/*
			 * Indica se l'albero dovrebbe essere filtrata (per impostazione predefinita) per mostrare i nodi solo corrispondenti 
			 * (tenere a mente questo può essere un pesante su grandi alberi vecchi browser).
			 * Questa impostazione può essere modificata in fase di esecuzione quando si chiama il metodo di ricerca. 
			 * Il valore predefinito è falso.
			 */
			"show_only_matches": false,
			/*
			 * Indica se vengono visualizzate i figli di elemento abbinato (quando show_only_matches è vero)
			 * Questa impostazione può essere modificata in fase di esecuzione quando si chiama il metodo di ricerca. 
			 * Il valore predefinito è falso.
			 */
			"show_only_matches_children": false,
			/*
			 * Indica se tutti i nodi aperti per rivelare il risultato della ricerca, devono essere chiusi quando la ricerca viene cancellato 
			 * o viene eseguita una nuova ricerca. Il valore predefinito è vero.
			 */
			"close_opened_onclear": true,
			/*
			 * Indica se solo nodi foglia devono essere inclusi nei risultati di ricerca. Il valore predefinito è falso.
			 */
			"search_leaves_only": false
		},
		"plugins" : ["checkbox", "types", "search", "sort" ],
		'sort' :  function (a, b) {
		    return this.get_node(a).li_attr.ordine > this.get_node(b).li_attr.ordine ? 1 : -1;
		}
	});
	
	/**
	 * Gestione filro di ricerca dell'albero dei layer.
	 */
	$('#layoutTree_q').keyup(function () {
		  /** get testo digitato **/
	      var v = $('#layoutTree_q').val();
	      
	      // $('#tree2').jstree(true); -->> get an existing instance (will not create new instance)
	      
	      /** utilizzato per cancellare l'ultima ricerca (rimuove le classi e mostra tutti i nodi di filtraggio è ON) **/
	      $("#" + OPTIONS.targetTree + "").jstree(true).clear_search();
	      /** avvio ricerca **/
	      $("#" + OPTIONS.targetTree + "").jstree(true).search(v);
	});
	
	/**
	 * Gestione reset filtro ricerca layer
	 */
	$('#clearLayoutTree_q').click(function () {
		/** svuoto il campo di ricerca **/
	    $('#layoutTree_q').val("");
	    /** utilizzato per cancellare l'ultima ricerca (rimuove le classi e mostra tutti i nodi di filtraggio è ON) **/
	    $("#" + OPTIONS.targetTree + "").jstree(true).clear_search();
	});
	
	// spengo il loading
	appUtil.hideLoader();
 }catch(e){
	 console.log("Error build tree " + e);
	 /**
	  * La procedura che costruisce l'alabero dei layer, apro una popup informativa dell'errore.
	  */
	 // spengo il loading
	 appUtil.hideLoader();
	 // apri popup errore
	 appUtil.showMessageAlertApplication(jQuery.i18n.prop('Init.build.tree.error'), jQuery.i18n.prop('PopupAlert.attenzione'), false, true, false);
 }
};
