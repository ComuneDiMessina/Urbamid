
var MapBuilder = MapBuilder || {};


/** 
 * Recupero le configurazioni definite per l'identificativo profilo impostato, se recuperate procedo al 
 * build della mappa.
 * 
 * @param {@link String} profiloId, identificativo del profilo
 */
MapBuilder.buildConfiguration = function (profiloId) {
 
 var maps = {};

 $.ajax({
	 type: "GET",
	 dataType: "json",
	 contentType: "application/json",
	 url: ol.ApplicationProperty.URL_CONFIGURAZIONI_PROFILO,
	 data: { "idProfilo": profiloId},
	 cache: false,
	 async: false,
	 beforeSend: function(){
		 // loading ..
		 appUtil.showLoader(null, jQuery.i18n.prop("Loader.buildConf"));
	 },
	 success : function(data) {
		 if(data != null && data.success && data.aaData != null && data.aaData != "") {
				
			// get list
			var aData = JSON.parse(data.aaData);
				
			// spengo il loading
			appUtil.hideLoader();
			
			/**
			 * Inizializzo Classe contenente le impostazioni di configurazione della mappa.
			 */
			var mapOptions = new ClassBuilder.MapOptions(aData);
				/** Aggiungo all'oggetto l'id del profilo, servirà nel cors del build delle varie componenti della mappa **/
				mapOptions.profiloId = profiloId;
			/** 
			 * Creare una nuova mappa con il costruttore ol.Map.
			 */
			var map2D = MapBuilder.buildMap2D(mapOptions);
			/** 
			 * Creare la mappa 3D con il costruttore olcs.OLCesium.
			 */	
			var map3D = MapBuilder.buildMap3D(map2D, mapOptions);
			/** 
			 * Un controllo è un widget visibile con un elemento DOM in una posizione fissa sullo schermo. 
			 * Essi possono coinvolgere l'input dell'utente (pulsanti), o essere solo informativo; la posizione è determinato 
			 * utilizzando i CSS. Per impostazione predefinita, questi vengono inseriti nel contenitore con nome della classe 
			 * CSS "ol-overlaycontainer-stopevent", ma è possibile utilizzare qualsiasi elemento DOM di fuori.
			 */
			ControlsBuilder.buildAdvancedControls(map2D, map3D, mapOptions);
			/** 
			 * Funzione per la costruzione dei layer di base della mappa.
			 */
			LayersBuilder.buildTreeCategory(mapOptions);

//			InteractionsBuilder.buildAdvancedInteractions(map2D, map3D, mapOptions);
			
			/**
			 * Valorizzo l'oggetto di ritorno con l'istanza delle mappe appena create
			 */
			maps = { "2D": map2D, "3D": map3D };
			
		}else{
			console.log("Configurazioni non recuperate per l'id profilo " + Constants.ID_PROFILO);
			/**
			 * La procedura che presenta l'anagrafica del layer/style della mappa è andata in errore, apro una popup informativa dell'errore.
			 */
			// spengo il loading
			appUtil.hideLoader();
			// apri popup errore
			appUtil.showMessageAlertApplication(jQuery.i18n.prop('Init.load.conf.error', 1), jQuery.i18n.prop('PopupAlert.attenzione'), false, true, false);
		}
	},
	error: function(jqXHR, textStatus, errorThrown) {
		console.log(errorThrown);
		/**
		 * La procedura che presenta l'anagrafica del layer/style della mappa è andata in errore, apro una popup informativa dell'errore.
		 */
		// spengo il loading
		appUtil.hideLoader();
		// apri popup errore
		appUtil.showMessageAlertApplication(jQuery.i18n.prop('Init.load.conf.error', 1), jQuery.i18n.prop('PopupAlert.attenzione'), false, true, false);
	}// close error
 });//close call ajax
 
 return maps;
};


/** 
 * Creare una nuova mappa con il costruttore ol.Map.
 * 
 * @param {@link ClassBuilder.MapOptions} OPTIONS
 * 
 * @return {@link ol.Map}
 */
MapBuilder.buildMap2D = function(OPTIONS){
 try{
	// loading ..
	appUtil.showLoader(null, jQuery.i18n.prop("Loader.buildMap2D"));
	 
	var view = new ol.View({
		/**
		 * The initial center for the view. The coordinate system for the center is specified with the projection option. 
		 * Default is undefined, and layer sources will not be fetched if this is not set.
		 * {@link ol.Coordinate | undefined	}
		 */
		center: FunctionBuilder.olProjTransorm(OPTIONS.center[0], OPTIONS.center[1], OPTIONS.sourceProjection, OPTIONS.destProjection),
		/**
		 * Rotation constraint. false means no constraint. true means no constraint, but snap to zero near zero. 
		 * A number constrains the rotation to that number of values. For example, 4 will constrain the rotation to 0, 90, 180, and 270 degrees. 
		 * The default is true.
		 * {@link boolean | number | undefined }
		 */
		constrainRotation: true,
		/**
		 * Abilita rotazione(SHIFT+ALT+MOUSE). Il valore predefinito è vero.
		 * L'opzione "constrainRotation" non ha effetto se "enableRotation" è falso.
		 * {@link boolean | undefined }
		 */
		enableRotation: OPTIONS.enableRotation,	
		/**
		 * La misura che vincola il centro, in altre parole, il centro non può essere impostato fuori misura.
		 * {@link ol.Extent | undefined }
		 */
//		extent: OPTIONS.extent,  // TODO
		/**
		 * The maximum resolution used to determine the resolution constraint. It is used together with minResolution (or maxZoom) and zoomFactor. 
		 * If unspecified it is calculated in such a way that the projection's validity extent fits in a 256x256 px tile. 
		 * If the projection is Spherical Mercator (the default) then maxResolution defaults to 40075016.68557849 / 256 = 156543.03392804097.
		 * {@link number | undefined }
		 */
//		maxResolution: OPTIONS.maxResolution,
		/**
		 * The minimum resolution used to determine the resolution constraint. It is used together with maxResolution (or minZoom) and zoomFactor. 
		 * If unspecified it is calculated assuming 29 zoom levels (with a factor of 2). If the projection is Spherical Mercator (the default) 
		 * then minResolution defaults to 40075016.68557849 / 256 / Math.pow(2, 28) = 0.0005831682455839253.
		 * {@link number | undefined }
		 */
//		minResolution: OPTIONS.minResolution,
		/**
		 * Livello massimo di zoom.
		 * {@link number | undefined }
		 */
		maxZoom: OPTIONS.maxZoom,	
		/**
		 * Livello minimo di zoom.
		 * {@link number | undefined }
		 */
		minZoom: OPTIONS.minZoom,
		/**
		 * The projection.
		 * {@link ol.proj.ProjectionLike }
		 */
		projection: OPTIONS.destProjection,
		/**
		 * The initial resolution for the view. The units are projection units per pixel (e.g. meters per pixel). An alternative to setting 
		 * this is to set zoom. Default is undefined, and layer sources will not be fetched if neither this nor zoom are defined.
		 * {@link number | undefined }
		 */
//		resolution: undefined,
		/**
		 * Risoluzioni per determinare il vincolo risoluzione. Se impostato verrano ignorate le opzioni: maxResolution, minResolution, minZoom, 
		 * maxZoom, and zoomFactor.
		 * {@link Array.<number> | undefined }
		 */
//		resolutions: [],
		/**
		 * The initial rotation for the view in radians (positive rotation clockwise). Default is 0.
		 * {@link number | undefined }
		 */
		rotation: 0,	
		/**
		 * Utilizzato solo se la risoluzione non è definito. Livello di zoom utilizzato per calcolare la risoluzione iniziale per la 
		 * visualizzazione. La risoluzione iniziale è determinato con il metodo ol.View # constrainResolution.
		 * {@link number | undefined }
		 */
		zoom: OPTIONS.zoom,
		/**
		 * The zoom factor used to determine the resolution constraint. Default is 2.
		 * {@link number | undefined }
		 */
		zoomFactor: 2
	});
	
	var map = new ol.Map({
		/**
		 * Configuro i controlli di default (se attivi):
		 *   - ol.control.Zoom 
		 *   - ol.control.Rotate
		 *   - ol.control.Attribution
		 ***/
		controls: ControlsBuilder.configureDefaultControls(OPTIONS),
		/**
		 * The ratio between physical pixels and device-independent pixels (dips) on the device. 
		 * If undefined then it gets set by using window.devicePixelRatio.
		 * {@link number | undefined}
		 */
//		pixelRatio: 
		/**
		 * 
		 */
		interactions: ol.interaction.defaults({ doubleClickZoom: false }), // TODO
		/** 
		 * The element to listen to keyboard events on 
		 * **/
//		keyboardEventTarget:
		/** 
		 * Funzione per la costruzione dei layer di base della mappa.
		 ***/
		layers: LayersBuilder.buildCustomBaseLayer(OPTIONS.basicLayers),
		/**
		 * When set to true, tiles will be loaded during animations. This may improve the user experience, but can also 
		 * make animations stutter on devices with slow memory. Default is false.
		 */
		loadTilesWhileAnimating: true,
		/**
		 * When set to true, tiles will be loaded while interacting with the map. This may improve the user experience, but can 
		 * also make map panning and zooming choppy on devices with slow memory. Default is false.
		 */
		loadTilesWhileInteracting: true,
		/**
		 * Il logo mappa. Un logo da visualizzare sulla mappa in ogni momento. Se viene fornita una stringa, viene impostato come 
		 * fonte immagine del logo. Se viene fornito un oggetto, la proprietà src dovrebbe essere l'URL di un'immagine e 
		 * la proprietà href dovrebbe essere un URL per la creazione di un collegamento. 
		 * Per disabilitare il logo mappa, impostare l'opzione a false. Per impostazione predefinita, viene mostrato il logo di OpenLayers 3.
		 * {@link boolean | string | olx.LogoOptions | undefined }
		 */
		logo: false,
		/**
		 * Overlays inizialmente aggiunto alla mappa. Per impostazione predefinita, non vengono aggiunti Overlays.
		 */
		overlays: [],
		/**
		 * Renderer. By default, Canvas, DOM and WebGL renderers are tested for support in that order, and the first supported used. 
		 * Specify a ol.RendererType here to use a specific renderer. Note that at present only the Canvas renderer supports vector data.
		 * {@link ol.RendererType | Array.<(ol.RendererType|string)> | string | undefined }
		 */
//		renderer: 
		/** 
		 * The container for the map, either the element itself or the id of the element. 
		 * **/
		target: OPTIONS.targetMap,
		/**
		 * The map's view. No layer sources will be fetched unless this is specified at construction time or through ol.Map#setView.
		 */
		view: view
	});
	
	// spengo il loading
	appUtil.hideLoader();
	
	return map;
 }catch(e){
	console.log("Error build map 2D " + e);
	/**
	 * La procedura di costruzione della mappa 2D è andata in errore, apro una popup per informativa dell'errore.
	 */
	// spengo il loading
	appUtil.hideLoader();
	// apri popup errore
	appUtil.showMessageAlertApplication(jQuery.i18n.prop('Init.build.map2d.error'), jQuery.i18n.prop('PopupAlert.attenzione'), false, true, false);
 }
};


/** 
 * Creare la mappa 3D con il costruttore olcs.OLCesium.
 * 
 * @param {@link ol.Map} map2DInstance
 * @param {@link ClassBuilder.MapOptions} OPTIONS
 * 
 * @return {@link olcs.OLCesium}
 */
MapBuilder.buildMap3D = function(map2D, OPTIONS){
 try{
	// loading ..
	appUtil.showLoader(null, jQuery.i18n.prop("Loader.buildMap3D"));
	 
	var map3D = new olcs.OLCesium({map: map2D});
	
	// spengo il loading
	appUtil.hideLoader();
	
	return map3D;
 }catch(e){
	console.log("Error build map 3D " + e);
	/**
	 * La procedura di costruzione della mappa 3D è andata in errore, apro una popup informativa dell'errore.
	 */
	// spengo il loading
	appUtil.hideLoader();
	// apri popup errore
	appUtil.showMessageAlertApplication(jQuery.i18n.prop('Init.build.map3d.error'), jQuery.i18n.prop('PopupAlert.attenzione'), false, true, false);
 }
};


