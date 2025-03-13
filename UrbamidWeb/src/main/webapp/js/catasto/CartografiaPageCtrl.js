/**
 * @include CatastoRest.js						catastoRest, 	viene utilizzato per le chiamate rest
 */

'use strict'

/**
 * Controller principale della pagina di gestione dati catastali. 
 * Fondamentalmente aggiunge solo degli eventi di click che aprono un oggetto controller appropriato che gestisce
 * la pagina di gestione dati.
 */
class CartografiaPageCtrl {
	constructor( ) {
		//questa è una variabile nel session storage che ci aiuta a capire se la funzionalità del piano particellare con la quale
		//si selezionano le particelle sulla mappa è stata abilitata o meno (questo perchè non possiamo tenere sempre
		//in "funzione" gli eventi di singleclick sulla mappa).
		sessionStorage.setItem('piano.particellare.singleclick.mappa.abilitato', 'false');
		this.isSelezioneParticelleAbilitato = false;
		this.aggiungiEventiClickVaiAPagina();
		appUtil.reloadTooltips();
		
		this.inizializzaPagina();
	}
	aggiungiEventiClickVaiAPagina(){
		$('#pianoParticellare').off('click').on('click', function(event){ 
			new PaginaScegliModalitaPianoParticellareCtrl();
		});
	}
	inizializzaPagina(){
		var self = this;
		self.batchStatus();
		setInterval(function() {
			self.batchStatus();	
		}, 30000);
	}
	batchStatus(){
		
		$.when(
			catastoRest.batchStatus()
		).then(function( risultatiBatchInCorso ){
			if(risultatiBatchInCorso && risultatiBatchInCorso.success){
				if (risultatiBatchInCorso.aaData==null)
					$("#headernav #message").html("Nessun processo di Import del catasto avviato");
				else
					$("#headernav #message").html("E' in corso un processo di Import del catasto");
					
				$("#headernav").show();
			}
		}, function(xhr, ajaxOptions, thrownError){
			appUtil.hideLoader();
			deferred.reject('Errore');
		});
	}
}

/**
 * Superclasse per i controller del piano particellare. Raggruppa quelli che possono essere i dati e/o i metodi comuni
 */
class BasePianoParticellareCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		this.drawStyle = new ol.style.Style({
			stroke: new ol.style.Stroke({ color: '#861611d9', width: 2 }),
			fill: new ol.style.Fill({color: 'rgba(134, 22, 17, 0.5)'})
		});
		this.geoJSONFormat = new ol.format.GeoJSON();
		this.featuresSelezionate = [];
		this.lsCategorieCatastali = [];
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		this.idLayerSelezonePianoParticellare = 'layerSelezonePianoParticellare';
		
		(appMappa.maps).removeLayer((appMappa.maps).getLayerById(this.idLayerSelezonePianoParticellare));
		
		this.layerConFeatureSelezionate = new ol.layer.Vector({});
		this.layerConFeatureSelezionate.setId(this.idLayerSelezonePianoParticellare);
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita()
			).done(function(categorieCatastali, codiciDiritto, codiciQualita){
				self.lsCategorieCatastali = categorieCatastali[0].aaData;
				self.lsCodiciDiritto = codiciDiritto[0].aaData;
				self.lsCodiciQualita = codiciQualita[0].aaData;
		});
	}
	
	/**
	 * Controlla se tra i layer aggiunti alla mappa vi è quello delle particelle.
	 * N.B. Il check viene fatto sul nome settato nelle properties (in mancanza di un id).
	 * Questo check puo' essere modificato se si trovano metodi più efficaci.
	 */
	isLayerParticelleAbilitato(){
		var isAbilitato = false;
		$.each( (appMappa.maps).getLayers().getArray(), function(indice, layer){
			let properties = layer.getProperties();
			if(properties && properties.name && properties.name === 'particelle'){
				isAbilitato = true;
			}
		});
		return isAbilitato;
	}
	/**
	 * Crea la variabile layersEnable... da capire meglio (copiato da vecchio codice).
	 */
	creaListaLayerAbilitatiPerEffettuareRicerca(){
		var layersEnable = "";
		$.each( appUrbamid.defaultLayerAddedView, function( keyL, valueL ) {
			if (appUrbamid.layerAddedMap[valueL]!=null)
				layersEnable += (appConfig.workspaceGeoserver+":"+valueL+",");
		});
		$.each( appUrbamid.layerAddedView, function( keyL, valueL ) {
			if (appUrbamid.layerAddedMap[valueL]!=null)
				layersEnable += (appConfig.workspaceGeoserver+":"+valueL+",");
		});
		layersEnable=layersEnable.slice(0,-1);
		return layersEnable;
	}
	/**
	 * Crea un ol.layer.Image per la richiesta al geoserver... da capire meglio (copiato da vecchio codice).
	 */
	creaImageUntiledPerRichiestaGeoserver(){
		let layersEnable = this.creaListaLayerAbilitatiPerEffettuareRicerca();
		return new ol.layer.Image({
			source: new ol.source.ImageWMS({
				ratio: 1,
				url: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceService + appConfig.wmsService,
				params: {'FORMAT': appConfig.acceptsFormatGeoserver, 'VERSION': '1.1.1', "LAYERS": layersEnable, "exceptions": 'application/vnd.ogc.se_inimage'}
			})
		});
	}
	/**
	 * Scarta le feature che non riguardano il layer delle particelle
	 */
	scartaFeatureNonRelativeAlleParticelle(features){
		let array = [];
		if($.isArray(features) && features.length > 0){
			for(var i=0; i<features.length; i++){
				//TODO al più qui capire SE ci sta un metodo più efficace
				if($.type(features[i].a) === 'string' && features[i].a.startsWith("particelle")){
					array.push(features[i]);
				}
			}
		}
		return array;
	}
	isEventoSingleclickAbilitato(){
		let valore = sessionStorage.getItem('piano.particellare.singleclick.mappa.abilitato');
		return valore === 'true';
	}
	disabilitaEventoSingleclickSuMappa(){ sessionStorage.setItem('piano.particellare.singleclick.mappa.abilitato', 'false'); }
	abilitaEventoSingleclickSuMappa(){ sessionStorage.setItem('piano.particellare.singleclick.mappa.abilitato', 'true'); }
	/**
	 * Aggiunge le nuove features alla lista di quelle selezionate
	 */
	aggiungiFeatureAllaSelezione(feature){
		var self = this;
			feature.setStyle(self.drawStyle);
			if( !self.isGiaInserita(feature) ){
				self.featuresSelezionate.push(feature);
			}
	}
	/**
	 * Rimuove dalla selezione le features passate come argomento
	 */
	rimuoviFeatureDallaSelezione(feature){
		let nuovoArrayDiFeaturesSelezionate = [];
		//confronto ogni feature del click con ogni feature della selezione.
		//se trovo che la feature del click è uguale ad una delle feature della selezione,
		//non la tengo in considerazione per la costruzione del nuovo array della selezione
		for(var j=0; j<this.featuresSelezionate.length;j++){
			let dovreiInserireQuestaFeatureNellaNuovaSelezione = true;
			if(this.sonoUguali(feature, this.featuresSelezionate[j])){
				dovreiInserireQuestaFeatureNellaNuovaSelezione = false;
				break
			}
			if(dovreiInserireQuestaFeatureNellaNuovaSelezione){
				nuovoArrayDiFeaturesSelezionate.push(this.featuresSelezionate[j]);
			}
		}
		this.featuresSelezionate = nuovoArrayDiFeaturesSelezionate;
	}
	/**
	 * Testa l'equality di due features (si basa su di un presunto id delle particelle... è da verificare)
	 */
	sonoUguali(feature1, feature2){
		//non faccio particolari controlli perchè ndringhete ndringhete ndrà miez 'o mar nu scoglj' ce stà.
		return feature1.I.gid === feature2.I.gid;
	}
	/**
	 * 
	 */
	isGiaInserita(feature){
		let isGiaInserita = false;
		if(feature && this.featuresSelezionate.length > 0 ){
			for(var i=0; i<this.featuresSelezionate.length; i++){
				if(this.sonoUguali(feature, this.featuresSelezionate[i])){
					isGiaInserita = true;
				}
			}
		}
		return isGiaInserita;
	}
	/**
	 * Ricarica il layer delle particelle selezionate ricreando il layer stesso
	 */
	ricaricaLayerSelezione(){
		var self = this;
		self.layerConFeatureSelezionate.setSource(new ol.source.Vector({ features: self.featuresSelezionate }));
		if( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) != null){
			(appMappa.maps).removeLayer( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) );
		}
		(appMappa.maps).addLayer(self.layerConFeatureSelezionate);
		self.impostaZoomMappaPerContenereFeatureSelezionate();
	}
	
	impostaZoomMappaPerContenereFeatureSelezionate(){
		var extent = this.layerConFeatureSelezionate.getSource().getExtent();
		(appMappa.maps).getView().fit(extent, (appMappa.maps).getSize());
	}

}/**
 * Controller per la modale di import dati catastali
 */ 
class PaginaPianoParticellareDaSelezioneSuMappaCtrl extends BasePianoParticellareCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super();
		this.tipCtrlZ = false;
		this.apriToastStrumenti();
		this.mostraLayerParticelle();
		this.aggiungiEventoClickSelezionaSuMappa();
		this.abilitaEventoSingleclickSuMappa();
		this.handleClickEventCallback = null;
	}
	/**
	 * Aggiunge un evento di singleclick sulla mappa
	 */
	aggiungiEventoClickSelezionaSuMappa(){
		var self = this;
		self.handleClickEventCallback = function(event) {
			var isDeseleziona = (event.originalEvent.metaKey || event.originalEvent.ctrlKey) ? true : false;
			if( self.isEventoSingleclickAbilitato() ) {
				if(self.tipCtrlZ === false) self.mostraTipCtrlZ();
					let untiled = self.creaImageUntiledPerRichiestaGeoserver();
					let view = (appMappa.maps).getView();
					let viewResolution = view.getResolution();
					let source = untiled.getSource();
					
					
					
					this.hrefEstrazioneParticellareByGeom = appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
																	'/UrbamidWeb/catastoController/findParticellaByGeom';
					var data = new FormData();
					var coord = new ol.proj.transform([event.coordinate[0], event.coordinate[1]], 'EPSG:3857', 'EPSG:4326');
					data.append('wktGeom', 'POINT('+coord[0]+' '+coord[1]+')');
					$.ajax({
							type: "POST",
							enctype: 'multipart/form-data',
							url: this.hrefEstrazioneParticellareByGeom,
							data: data,
							processData: false,
							contentType: false,
							cache: false,
							// timeout: 600000,
							async: false,
							// timeout: 600000,
							success: function (data) {
								
								if(data.success) {
									let format = new ol.format.WKT();
									let feature = format.readFeature( data.aaData[0].geometry , {
										  dataProjection: 'EPSG:4326',
										  featureProjection: 'EPSG:3857'
									});
									
									feature = self.popolaFeatureProp(feature, data.aaData[0]);
									
									if(isDeseleziona){
										self.rimuoviFeatureDallaSelezione(feature);
									}else{
										self.aggiungiFeatureAllaSelezione(feature);
									}
									self.ricaricaLayerSelezione();
								}
							},
							error: function (e) {
								console.log("ERROR : ", e);
							}
					});
					
					
					
			}
		};
		(appMappa.maps).on('singleclick', self.handleClickEventCallback);
	}
	
	popolaFeatureProp(feature, prop){
		feature.I.gid = prop.id || null;
		feature.I.codice_com= prop.codiceCom || null;
		feature.I.foglio= prop.foglio || null;
		feature.I.mappale= prop.mappale || null;
		feature.I.allegato= prop.allegato || null;
		feature.I.sviluppo= prop.sviluppo || null;
		feature.I.htxt= prop.htxt || null;
		feature.I.rtxt= prop.rtxt || null;
		feature.I.xtxt= prop.xtxt || null;
		feature.I.ytxt= prop.ytxt || null;
		return feature;
	}
	
	/**
	 * Mostra il toast che informa della possibilità di fare CTRL+Z
	 */
	mostraTipCtrlZ(){
		if(this.tipCtrlZ === false){
			this.tipCtrlZ = true;
			iziToast.show({
				balloon: false,
				icon:'fa fa-info-circle', 
				animateInside: false,
				theme: 'dark', 
				position: 'topCenter',
				title: 'Informazione',
			    message: 'Ricorda che puoi deselezionare le particelle tenendo premuto il tasto CTRL e cliccando su di essa.',
			});
		}
	}
	/**
	 * Abilita nel visualizzatore e sulla mappa il layer delle Particelle.
	 */
	mostraLayerParticelle(){
		if( $('div#Particelle_divchk input#Particelle_chk').length > 0 ){
			if($('div#Particelle_divchk input#Particelle_chk').prop('checked') === false){
				$('div#Particelle_divchk input#Particelle_chk').prop('checked', true );
				$('div#Particelle_divchk input#Particelle_chk').trigger('change');
			}
		}
	}
	/**
	 * Rimuove le feature selezionate
	 */
	rimuoviFeatureSelezionate(){
		this.featuresSelezionate = [];
		this.idLayerSelezonePianoParticellare = 'layerSelezonePianoParticellare';
		this.layerConFeatureSelezionate = new ol.layer.Vector({});
		this.layerConFeatureSelezionate.setId(this.idLayerSelezonePianoParticellare);
		(appMappa.maps).removeLayer( (appMappa.maps).getLayerById(this.idLayerSelezonePianoParticellare));
	}
	/**
	 * Rimuove l'ultima feature aggiunta
	 */
	rimuoviUltimaSelezione(){
		if( this.featuresSelezionate && this.featuresSelezionate.length >= 1){
			this.featuresSelezionate.pop();
			this.ricaricaLayerSelezione();
		}
	}
	/**
	 * Aggiunge un evento che al CTRL+Z elimina l'ultimo punto disegnato.
	 */
	aggiungiEventoKeydown_CtrlZ(){
		var self = this;
		$(document).off('keydown').on('keydown', function(event) {
			if(event.keyCode === 90) {
	            if (event.metaKey || event.ctrlKey) {
	                self.draw.removeLastPoint();
	            }
			}
		});
	}
	/**
	 * Apre il toast che mostra gli strumenti per il piano particellare
	 */
	apriToastStrumenti(){
		var self = this;
		iziToast.show({
			id: 'pianoParticellareIziToast',
			title: '', 
			message: '', 
			theme: 'dark', 
			icon:'fa fa-object-group', 
			layout:1,
			maxWidth: 400,
			balloon: false, 
			displayMode: 'once',
			progressBar: false,
			position: 'bottomLeft',
			animateInside: false, 
			timeout: false,
			drag: false,
			onOpened: function(){
			},
			onClosed: function(instance, toast, closedBy){
				//TODO disabilitare la selezione
				self.disabilitaEventoSingleclickSuMappa();
				self.rimuoviFeatureSelezionate();
		    },
			buttons: [
				[
					'<button type="button" title="Rimuovi l\'ultima selezione" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-undo"></em>&nbsp;</button>',
					function(instance, toast){
						self.rimuoviUltimaSelezione();
					}
				],
				[
					'<button type="button" title="Rimuovi tracciato su mappa" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
					function(instance, toast){
						self.rimuoviFeatureSelezionate();
					}
				],
				[
					'<button type="button" title="Estrai piano particellare" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Estrai</button>',
					function(instance, toast){
						if( $.isArray(self.featuresSelezionate) && self.featuresSelezionate.length > 0 ){
							appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							setTimeout(function(){
								iziToast.hide({transitionOut: 'fadeOutUp'}, toast);
								self.disabilitaEventoSingleclickSuMappa();

								var particelle = self.popolaItemLayerSelezionati();
								sessionStorage.setItem('particelle', JSON.stringify(particelle));
								sessionStorage.setItem('tipologiaEstrazione', 'Estrazione da selezione su mappa');
								self.rimuoviFeatureSelezionate();
								$.when(
									catastoRest.ricercaSuParticellePT(particelle),
									catastoRest.ricercaSuParticelleUI(particelle),
									catastoRest.ricercaPersoneFisiche(particelle),
									catastoRest.ricercaSoggettiGiuridici(particelle)
								).then(function( risultatiPT, risultatiUI, risultatiPS, risultatiSG ){
									if(risultatiPT && risultatiUI && risultatiPS && risultatiSG){
										(appMappa.maps).un('singleclick', self.handleClickEventCallback);
										new PaginaPianoParticellareEstrattoCtrl(risultatiPT, risultatiUI, risultatiPS, risultatiSG);
										appUtil.reloadTooltips();
									}
								}, function(xhr, ajaxOptions, thrownError){
									appUtil.hideLoader();
									deferred.reject('Errore');
								});
							}, 2000);
						} else {
							appUtil.showMessageAlertApplication(
									'Per favore, seleziona almeno un elemento su mappa prima di lanciare la funzionalit&aacute;	 di estrazione del piano particellare', 
									'Nessuna area tracciata', false, true, false, null, null);
						}
					}
				],
			],
		});
	}

	popolaItemLayerSelezionati(){
		var self = this;
		var particelle = [];
		self.featuresSelezionate.forEach(function(item) {
			delete(item.I.geometry);
			delete(item.I.bbox);
			particelle.push({
				gid: item.I.gid || null,
				codice_com: item.I.codice_com || null,
				foglio: item.I.foglio || null,
				mappale: item.I.mappale || null,
				allegato: item.I.allegato || null,
				sviluppo: item.I.sviluppo || null,
				htxt: item.I.htxt || null,
				rtxt: item.I.rtxt || null,
				xtxt: item.I.xtxt || null,
				ytxt: item.I.ytxt || null,
			});
		});
		return particelle;
	}
	
}
/**
 * Controller per la modale di import dati catastali
 */ 
class PaginaPianoParticellareDaTracciatoSuMappaCtrl extends BasePianoParticellareCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super();
		this.tipCtrlZ = false;
		this.tipModifica = false;
		this.raster = new ol.layer.Tile({source: new ol.source.OSM()});
		this.raster.setId('pianoParticellareRaster');
		this.features = new ol.Collection();
		this.source = new ol.source.Vector({features: this.features, wrapX: false});
		this.featureOverlay = new ol.layer.Vector({source: this.source});
		this.modify = new ol.interaction.Modify({
			features: this.features,   
		    deleteCondition: function(event) {
				return ol.events.condition.shiftKeyOnly(event) &&
					ol.events.condition.singleClick(event);
			}
		});
		this.draw = new ol.interaction.Draw({
			features: this.features,
		    type: 'Polygon',
		    style: this.drawStyle,
		});
		this.abilitaDisegnaEModificaSuMappa();
		this.apriToastStrumenti();
		this.mostraLayerParticelle();
		this.aggiungiEventoKeydown_CtrlZ();
		
		this.geometrySelected = [];
	}
	/**
	 * Aggiunge un evento che al CTRL+Z elimina l'ultimo punto disegnato.
	 */
	aggiungiEventoKeydown_CtrlZ(){
		var self = this;
		$(document).off('keydown').on('keydown', function(event) {
			if(event.keyCode === 90) {
	            if (event.metaKey || event.ctrlKey) {
	                self.draw.removeLastPoint();
	            }
			}
		});
	}
	/**
	 * Abilita nel visualizzatore e sulla mappa il layer dei Fogli.
	 */	
	mostraLayerFogli(){
		if( $('div#Fogli_divchk input#Fogli_chk').length > 0 ){
			if($('div#Fogli_divchk input#Fogli_chk').prop('checked') === false){
				$('div#Fogli_divchk input#Fogli_chk').prop('checked', true );
				$('div#Fogli_divchk input#Fogli_chk').trigger('change');
			}
		}
	}
	/**
	 * Abilita nel visualizzatore e sulla mappa il layer delle Particelle.
	 */
	mostraLayerParticelle(){
		if( $('div#Particelle_divchk input#Particelle_chk').length > 0 ){
			if($('div#Particelle_divchk input#Particelle_chk').prop('checked') === false){
				$('div#Particelle_divchk input#Particelle_chk').prop('checked', true );
				$('div#Particelle_divchk input#Particelle_chk').trigger('change');
			}
		}
	}
	/**
	 * Abilita il draw and modify su mappa
	 */
	abilitaDisegnaEModificaSuMappa(){
		var self = this;
//		(appMappa.maps).addLayer(self.raster);
		self.featureOverlay.setMap((appMappa.maps));
		(appMappa.maps).addInteraction(self.modify);
		(appMappa.maps).addInteraction(self.draw);
		this.draw.on('drawend', function (e) {
			self.mostraTipModificaDisegnoTracciato();
			var feature = e.feature;
			
		    var coords = feature.getGeometry().getCoordinates();
		    console.log(coords);
		    
		    self.geometrySelected = feature.getGeometry();
		});
		this.draw.on('drawstart', function(e) {
			e.feature.setStyle(self.drawStyle);
			self.mostraTipCtrlZ();
			let features = self.source.getFeatures();
			if( $.isArray(features) && features.length > 0 ){
				appUtil.confirmOperation(function(){
					let features = self.source.getFeatures();
					for(var i=0; i<features.length; i++){
						self.source.removeFeature( features[i] );
					}
				},function(){
					self.draw.removeLastPoint();
					self.draw.finishDrawing();
				},null,'Iniziando a disegnare una nuova area per il piano particellare, perderai quella precedente. Desideri continuare comunque?');
			}
		});
	}
	/**
	 * Mostra il toast che informa della possibilità di fare CTRL+Z
	 */
	mostraTipCtrlZ(){
		if(this.tipCtrlZ === false){
			this.tipCtrlZ = true;
			iziToast.show({
				balloon: false,
				icon:'fa fa-info-circle', 
				animateInside: false,
				theme: 'dark', 
				position: 'topCenter',
				title: 'Informazione',
			    message: 'Ricorda che puoi cancellare il punto appena disegnato digitando CTRL+Z',
			});
		}
	}
	/**
	 * Mostra il toast che informa della possibilità di modificare la mappa
	 */
	mostraTipModificaDisegnoTracciato(){
		if(this.tipModifica === false){
			this.tipModifica = true;
			iziToast.show({
				balloon: false,
				icon:'fa fa-info-circle', 
				animateInside: false,
				theme: 'dark', 
				position: 'topCenter',
				title: 'Informazione',
			    message: 'Ricorda che puoi modificare il tracciato cliccando e trascinando col mouse un qualunque punto del suo perimetro',
			});
		}
	}
	/**
	 * Rimuove le feature disegnate
	 */
	rimuoviFeatureDisegnate(){
		var self = this;
		let features = self.source.getFeatures();
		if( $.isArray(features) && features.length > 0 ){
			for(var i=0; i<features.length; i++){
				self.source.removeFeature( features[i] );
			}
		}
	}
	/**
	 * Apre il toast che mostra gli strumenti per il piano particellare
	 */
	apriToastStrumenti(){
		var self = this;
		iziToast.show({
			id: 'pianoParticellareIziToast',
			title: '', 
			message: '', 
			theme: 'dark', 
			icon:'fa fa-object-group', 
			layout:1,
			maxWidth: 400,
			balloon: false, 
			displayMode: 'once',
			progressBar: false,
			position: 'bottomLeft',
			animateInside: false, 
			timeout: false,
			drag: false,
			onOpened: function(){
			},
			onClosed: function(instance, toast, closedBy){
				self.rimuoviFeatureDisegnate();
		        (appMappa.maps).removeInteraction(self.draw);
		        (appMappa.maps).removeLayer(self.raster);
				(appMappa.maps).removeLayer(self.vector);
		    },
			buttons: [
				[
					'<button type="button" title="Rimuovi tracciato su mappa" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
					function(instance, toast){
						//iziToast.hide({transitionOut: 'fadeOutUp'}, toast);
						self.rimuoviFeatureDisegnate();
					}
				],
				[
					'<button type="button" title="Estrai piano particellare" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Estrai</button>',
					function(instance, toast){
						let features = self.source.getFeatures();
						if( $.isArray(features) && features.length > 0 ){
							appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							setTimeout(function(){ 
								iziToast.hide({transitionOut: 'fadeOutUp'}, toast);
								appUtil.hideLoader();
								self.rimuoviFeatureDisegnate();
								
								var src = 'EPSG:3857';
								var dest = 'EPSG:4326';
								var format = new ol.format.WKT();
								var wktfeaturegeom = format.writeGeometry( self.geometrySelected.transform(src, dest) );
								
								//this.hrefEstrazioneParticellareByGeom = appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
								//												'/UrbamidWeb/catastoController/findParticellaByWkt';
								this.hrefEstrazioneParticellareByGeom = appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
																				'/UrbamidWeb/catastoController/findParticellaCompleteByWkt';																				
								var data = new FormData();
								data.append('wktGeom', wktfeaturegeom);
								return $.ajax({
									type: "POST",
									enctype: 'multipart/form-data',
									url: this.hrefEstrazioneParticellareByGeom,
									data: data,
									processData: false,
									contentType: false,
									cache: false,
									timeout: 600000,
									success: function (data) {
										if(data.success) {
											let format = new ol.format.WKT();
											$.each( data.aaData, function( key, value ) {
												
												let feature = format.readFeature( value.geomText , {
													  dataProjection: 'EPSG:4326',
													  featureProjection: 'EPSG:3857'
												});
												feature = self.popolaFeatureProp(feature, value);
												self.aggiungiFeatureAllaSelezione(feature);
											});
											self.ricaricaLayerSelezione();
											
											var particelle = self.popolaItemLayerSelezionati();
											sessionStorage.setItem('particelle', JSON.stringify(particelle));
											sessionStorage.setItem('tipologiaEstrazione', 'Estrazione da tracciato su mappa');
											self.rimuoviFeatureSelezionate();
											
											$.when(
												catastoRest.ricercaSuParticellePT(particelle),
												catastoRest.ricercaSuParticelleUI(particelle),
												catastoRest.ricercaPersoneFisiche(particelle),
												catastoRest.ricercaSoggettiGiuridici(particelle)
											).then(function( risultatiPT, risultatiUI, risultatiPS, risultatiSG ){
												if(risultatiPT && risultatiUI && risultatiPS && risultatiSG){
													(appMappa.maps).un('singleclick', self.handleClickEventCallback);
													new PaginaPianoParticellareEstrattoCtrl(risultatiPT, risultatiUI, risultatiPS, risultatiSG);
													appUtil.reloadTooltips();
												}
											}, function(xhr, ajaxOptions, thrownError){
												appUtil.hideLoader();
												deferred.reject('Errore');
											});
										}
									},
									error: function (e) {
										console.log("ERROR : ", e);
									}
								});
								
							}, 2000);
						} else {
							appUtil.showMessageAlertApplication(
									'Per favore, traccia un\'area su mappa prima di lanciare la funzionalità di estrazione del piano particellare', 
									'Nessuna area tracciata', false, true, false, null, null);
						}
					}
				],
			],
		});
	}
	popolaFeatureProp(feature, prop){
		feature.I.gid = prop.id || null;
		feature.I.codice_com= prop.codiceCom || null;
		feature.I.foglio= prop.foglio || null;
		feature.I.mappale= prop.mappale || null;
		feature.I.allegato= prop.allegato || null;
		feature.I.sviluppo= prop.sviluppo || null;
		feature.I.htxt= prop.htxt || null;
		feature.I.rtxt= prop.rtxt || null;
		feature.I.xtxt= prop.xtxt || null;
		feature.I.ytxt= prop.ytxt || null;
		return feature;
	}
	popolaItemLayerSelezionati(){
		var self = this;
		var particelle = [];
		self.featuresSelezionate.forEach(function(item) {
			delete(item.I.geometry);
			delete(item.I.bbox);
			particelle.push({
				gid: item.I.gid || null,
				codice_com: item.I.codice_com || null,
				foglio: item.I.foglio || null,
				mappale: item.I.mappale || null,
				allegato: item.I.allegato || null,
				sviluppo: item.I.sviluppo || null,
				htxt: item.I.htxt || null,
				rtxt: item.I.rtxt || null,
				xtxt: item.I.xtxt || null,
				ytxt: item.I.ytxt || null,
			});
		});
		return particelle;
	}
	rimuoviFeatureSelezionate(){
		this.featuresSelezionate = [];
		this.idLayerSelezonePianoParticellare = 'layerSelezonePianoParticellare';
		this.layerConFeatureSelezionate = new ol.layer.Vector({});
		this.layerConFeatureSelezionate.setId(this.idLayerSelezonePianoParticellare);
		(appMappa.maps).removeLayer( (appMappa.maps).getLayerById(this.idLayerSelezonePianoParticellare));
	}
	ricaricaLayerSelezione(){
		var self = this;
		self.layerConFeatureSelezionate.setSource(new ol.source.Vector({ features: self.featuresSelezionate }));
		if( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) != null){
			(appMappa.maps).removeLayer( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) );
		}
		(appMappa.maps).addLayer(self.layerConFeatureSelezionate);
		self.impostaZoomMappaPerContenereFeatureSelezionate();
	}
}


class PaginaPianoParticellareDaSelezionaLayerCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('PaginaPianoParticellareDaSelezionaLayerCtrl', 'PIANO PARTICELLARE', 'modPianoParticellareDaSelezioneLayer');
		
		this.datiMockRicerca = null;
		this.inizializzaPagina();
		this.chiudiToastAperti();
		this.lsCategorieCatastali = [];
		this.apriToastStrumenti();
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		this.layerConFeatureSelezionate = new ol.layer.Vector({});
		
		appMappa.lsLayerEnabled = [];
		
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita()
		).done(function(categorieCatastali, codiciDiritto, codiciQualita){
			self.lsCategorieCatastali = categorieCatastali[0].aaData;
			self.lsCodiciDiritto = codiciDiritto[0].aaData;
			self.lsCodiciQualita = codiciQualita[0].aaData;
		});
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
			$('#tabellaRisultati').hide();
		}, {width: 600, heigth: 500, closable: true});

		$('#chiudiBtnQ').off('click').on('click', function(event){
			
			rimuoviLayerSelezionato();
			$('#'+self.idDialog).dialog('close');
			self.chiudiToastAperti();
        });

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when( appUrbamid.startCatalogo( "estrazionePart" ) ).then(function( x ) {
			
			var cbkOnClick = "selezionaCartograficaLayer";
			appCatalogo.initializeCatalogo("particelle-menu-content", cbkOnClick, "Utilizza per estrazione particellare");
			
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		});
	}
	
	
	/**
	 * Imposta un evento di onchange sulla select per filtare i file in formato CXF/CMF
	 */
	aggiungiEventoChangeSuSelectFiltroDati(){
		var self = this;
		$('#'+self.idDialog+' #selectCartografiaCatastale').off('change').on('change', function(){
			let table = $('#'+self.idDialog+' #tabellaFile').DataTable();
			table.column(3).search($(this).val()).draw();
		});
	}

	
	popolaFeatureProp(feature, prop){
		feature.I.gid = prop.id || null;
		feature.I.codice_com= prop.codiceCom || null;
		feature.I.foglio= prop.foglio || null;
		feature.I.mappale= prop.mappale || null;
		feature.I.allegato= prop.allegato || null;
		feature.I.sviluppo= prop.sviluppo || null;
		feature.I.htxt= prop.htxt || null;
		feature.I.rtxt= prop.rtxt || null;
		feature.I.xtxt= prop.xtxt || null;
		feature.I.ytxt= prop.ytxt || null;
		feature.I.area = prop.area || null;
		feature.I.intersectArea = prop.intersectArea || null;
		return feature;
	}
	
	/**
	 * Aggiunge le nuove features alla lista di quelle selezionate
	 */
	aggiungiFeatureAllaSelezione(feature){
		var self = this;
		feature.setStyle(self.drawStyle);
		if( !self.isGiaInserita(feature) ){
			self.featuresSelezionate.push(feature);
		}
	}
	
	/**
	 * Ricarica il layer delle particelle selezionate ricreando il layer stesso
	 */
	ricaricaLayerSelezione(){
		var self = this;
		self.layerConFeatureSelezionate.setSource(new ol.source.Vector({ features: self.featuresSelezionate }));
		if( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) != null){
			(appMappa.maps).removeLayer( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) );
		}
		(appMappa.maps).addLayer(self.layerConFeatureSelezionate);
		self.impostaZoomMappaPerContenereFeatureSelezionate();
	}
	
	popolaItemLayerSelezionati(){
		var self = this;
		var particelle = [];
		self.featuresSelezionate.forEach(function(item) {
			delete(item.I.geometry);
			delete(item.I.bbox);
			particelle.push({
				gid: item.I.gid || null,
				codice_com: item.I.codice_com || null,
				foglio: item.I.foglio || null,
				mappale: item.I.mappale || null,
				allegato: item.I.allegato || null,
				sviluppo: item.I.sviluppo || null,
				htxt: item.I.htxt || null,
				rtxt: item.I.rtxt || null,
				xtxt: item.I.xtxt || null,
				ytxt: item.I.ytxt || null,
				area: item.I.area || null,
				intersectArea: item.I.intersectArea || null
			});
		});
		return particelle;
	}

	/**
	 * 
	 */
	isGiaInserita(feature){
		let isGiaInserita = false;
		if(feature && this.featuresSelezionate.length > 0 ){
			for(var i=0; i<this.featuresSelezionate.length; i++){
				if(this.sonoUguali(feature, this.featuresSelezionate[i])){
					isGiaInserita = true;
				}
			}
		}
		return isGiaInserita;
	}
	
	/**
	 * Testa l'equality di due features (si basa su di un presunto id delle particelle... è da verificare)
	 */
	sonoUguali(feature1, feature2){
		return feature1.I.gid === feature2.I.gid;
	}
	
	rimuoviFeatureSelezionate(){
		this.featuresSelezionate = [];
		this.idLayerSelezonePianoParticellare = 'layerSelezonePianoParticellare';
		this.layerConFeatureSelezionate = new ol.layer.Vector({});
		this.layerConFeatureSelezionate.setId(this.idLayerSelezonePianoParticellare);
		(appMappa.maps).removeLayer( (appMappa.maps).getLayerById(this.idLayerSelezonePianoParticellare));
	}
	
	chiudiToastAperti(){
		let listaToast = document.querySelectorAll('.iziToast');
		for(var i=0;i<listaToast.length; i++){
			iziToast.hide({}, listaToast[i]);
		}
	}
	
	/**
	 * Apre il toast che mostra gli strumenti per il piano particellare
	 */
	apriToastStrumenti(){
		var self = this;
		iziToast.show({
			id: 'pianoParticellareSelezionaLayerIziToast',
			title: '', 
			message: '', 
			theme: 'dark', 
			icon:'fa fa-object-group', 
			layout:1,
			maxWidth: 400,
			balloon: false, 
			displayMode: 'once',
			progressBar: false,
			position: 'bottomLeft',
			animateInside: false, 
			timeout: false,
			drag: false,
			onOpened: function(){
			},
			onClosed: function(instance, toast, closedBy){
				//TODO disabilitare la selezione
				$('#'+self.idDialog).dialog('close');
				rimuoviLayerSelezionato();
		    },
			buttons: [
				[
					'<button type="button" title="Rimuovi Layer Selezionato" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
					function(instance, toast){
						rimuoviLayerSelezionato();
					}
				],
				[
					'<button type="button" title="Estrai piano particellare" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Estrai</button>',
					function(instance, toast){
						if( $.isArray(appConfig.featureSourceSelected) && appConfig.featureSourceSelected.length > 0 ){

							self.featuresSelezionate = [];
							appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							
							iziToast.hide({transitionOut: 'fadeOutUp'}, toast);
							
							var format = new ol.format.WKT();
							var wktRepresenation  = "";
							if (appMappa.isLayerDb) {
                                $.each( appConfig.featureSourceSelected, function( key, val ) {
								    wktRepresenation  += val+"|";
							    });
							} else {
								$.each( appConfig.featureSourceSelected, function( key, val ) {
									wktRepresenation  += format.writeGeometry(val.getGeometry())+"|";
								});
							}
							//var hrefEstrazioneParticellareByGeom = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort)
							//			+ '/UrbamidWeb/catastoController/findParticellaByWkt';
							var hrefEstrazioneParticellareByGeom = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort)
										+ '/UrbamidWeb/catastoController/findParticellaCompleteByWkt';
							var data = new FormData();
							data.append('wktGeom', wktRepresenation);
							$.ajax({
								type : "POST",
								enctype : 'multipart/form-data',
								url : hrefEstrazioneParticellareByGeom,
								data : data,
								processData : false,
								contentType : false,
								cache : false,
								timeout : 600000,
								success : function(data) {
									if(data.success) {
										let format = new ol.format.WKT();
										$.each( data.aaData, function( key, value ) {
											
											let feature = format.readFeature( value.geomText , {
												  dataProjection: 'EPSG:4326',
												  featureProjection: 'EPSG:3857'
											});
											feature = self.popolaFeatureProp(feature, value);
											self.aggiungiFeatureAllaSelezione(feature);
										});
										
										var particelle = self.popolaItemLayerSelezionati();
										sessionStorage.setItem('particelle', JSON.stringify(particelle));
										sessionStorage.setItem('tipologiaEstrazione', 'Estrazione da selezione layer');
										
										$.when(
											catastoRest.ricercaSuParticellePT(particelle),
											catastoRest.ricercaSuParticelleUI(particelle),
											catastoRest.ricercaPersoneFisiche(particelle),
											catastoRest.ricercaSoggettiGiuridici(particelle)
										).then(function( risultatiPT, risultatiUI, risultatiPS, risultatiSG ){
											if(risultatiPT && risultatiUI && risultatiPS && risultatiSG){
												(appMappa.maps).un('singleclick', self.handleClickEventCallback);
												new PaginaPianoParticellareEstrattoCtrl(risultatiPT, risultatiUI, risultatiPS, risultatiSG);
												appUtil.reloadTooltips();
											}
										}, function(xhr, ajaxOptions, thrownError){
											appUtil.hideLoader();
											deferred.reject('Errore');
										});
									} else {
										appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							            appUtil.showMessageAlertApplication(
									            'Non &egrave; stato possibile effettuare Estrazione particellare', 'Riprova più tardi', false, true, false, null, null);			
									}
								},
								error : function(e) {
									console.log("ERROR : ", e);
								}
							});
						} else {
							appUtil.showMessageAlertApplication(
									'Per favore, seleziona almeno un elemento su mappa prima di lanciare la funzionalit&aacute;	 di estrazione del piano particellare', 
									'Nessuna area tracciata', false, true, false, null, null);
						}
					}
				],
			],
		});
	}
}

class ExportPdfCtrl extends BasePianoParticellareCtrl {
	constructor() {
		super();
		this.inizializzaPagina();
	}

	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars('ExportPdfCtrl', 'modExportPdf', contextHandlebar, 'EXPORT PDF', function(){
		}, {closable: true});
		appUtil.hideLoader();

		$('#chiudiBtnPdf').on('click', function(){
			$('#modExportPdf').dialog('close');
		});

		$('#totalPdf').off('click').on('click', function(event){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			let particelle = sessionStorage.getItem('particelle');
			let titolo = $('#titoloExport').val();
			let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
			var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
			tipologiaEstrazione += " " + nomeLayerPdf;
			catastoRest.exportPdf(particelle, titolo, tipologiaEstrazione).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/octet-stream;base64,'+response;
				    const downloadLink = document.createElement('a');
				    const fileName = "export.zip";
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
			}, function(a,b,c){
				appUtil.hideLoader();
				console.log(a,b,c);
			});
        });

		$('#fabbricatiNominativo').off('click').on('click', function(event){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			let particelle = sessionStorage.getItem('particelle');
			let titolo = $('#titoloExport').val();
			let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
			var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
			tipologiaEstrazione += " " + nomeLayerPdf;
			catastoRest.exportFabbricatiPerNominativo(particelle, titolo, tipologiaEstrazione).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/pdf;base64,'+response;
				    const downloadLink = document.createElement('a');
				    const fileName = "FabbricatiPerNominativo.pdf";
				    downloadLink.href = linkSource;
				    downloadLink.download = fileName;
				    //downloadLink.target = '_blank'; 
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
			}, function(a,b,c){
				appUtil.hideLoader();
				console.log(a,b,c);
			});
        });

		$('#terreniNominativo').off('click').on('click', function(event){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			let particelle = sessionStorage.getItem('particelle');
			let titolo = $('#titoloExport').val();
			let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
			var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
			tipologiaEstrazione += " " + nomeLayerPdf;
			catastoRest.exportTerreniPerNominativo(particelle, titolo, tipologiaEstrazione).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/pdf;base64,'+response;
				    const downloadLink = document.createElement('a');
				    const fileName = "TerreniPerNominativo.pdf";
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
			}, function(a,b,c){
				appUtil.hideLoader();
				console.log(a,b,c);
			});
        });

		$('#fabbricatiParticella').off('click').on('click', function(event){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			let particelle = sessionStorage.getItem('particelle');
			let titolo = $('#titoloExport').val();
			let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
			var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
			tipologiaEstrazione += " " + nomeLayerPdf;
			catastoRest.exportFabbricatiPerParticella(particelle, titolo, tipologiaEstrazione).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/pdf;base64,'+response;
				    const downloadLink = document.createElement('a');
				    const fileName = "FabbricatiPerParticella.pdf";
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
			}, function(a,b,c){
				appUtil.hideLoader();
				console.log(a,b,c);
			});
        });

		$('#terreniParticella').off('click').on('click', function(event){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			let particelle = sessionStorage.getItem('particelle');
			let titolo = $('#titoloExport').val();
			let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
			var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
			tipologiaEstrazione += " " + nomeLayerPdf;
			catastoRest.exportTerreniPerParticella(particelle, titolo, tipologiaEstrazione).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/pdf;base64,'+response;
				    const downloadLink = document.createElement('a');
				    const fileName = "TerreniPerParticella.pdf";
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
			}, function(a,b,c){
				appUtil.hideLoader();
				console.log(a,b,c);
			});
        });

	}
}

class PaginaPianoParticellareEstrattoCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor(risultatiPT, risultatiUI, risultatiPS, risultatiSG) {
		super('paginaPianoParticellareEstratto','PIANO PARTICELLARE', 'modPianoParticellareEstratto');
		this.datiParticelleTerreni = risultatiPT[0].aaData;
		this.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatari', {});
		this.datiUnitaImmobiliari = risultatiUI[0].aaData;
		this.datiMockSoggettiGiuridici = risultatiSG[0].aaData;
		this.datiMockPersoneFisiche = risultatiPS[0].aaData;
		this.inizializzaPagina();
		$('#piano-pt-tab1').addClass('nav-link active');
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.inizializzaDatatablePT();
			self.inizializzaDatatableUIU();
			self.inizializzaDatatableIntestazioniPersoneFisiche();
			self.inizializzaDatatableIntestazioniSoggettiGiuridici();
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
		}, {closable: true});
		appUtil.hideLoader();

		$('#totalExport').off('click').on('click', function(event){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			let particelle = sessionStorage.getItem('particelle');
			catastoRest.exportXls(particelle).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/octet-stream;base64,'+response;
				    const downloadLink = document.createElement('a');
				    const fileName = "export.zip";
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
			}, function(a,b,c){
				appUtil.hideLoader();
				console.log(a,b,c);
			});
        });

		$('#pdfExport').off('click').on('click', function(event){
			self.apriModaleExportPdf();
        });

		$('#xlsExport').off('click').on('click', function(event) {
			self.apriModaleExportExcel();
		});
		
		$('#shapeExport').off('click').on('click', function(event){
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			let particelle = sessionStorage.getItem('particelle');
			let titolo = sessionStorage.getItem('titoloEstrazione');
			catastoRest.exportShape(particelle, titolo).then(function(response){
				appUtil.hideLoader();
				if ( (response !== null || response !== undefined) &&
				        response !=="") {
					const linkSource = 'data:application/octet-stream;base64,'+response;
				    const downloadLink = document.createElement('a');
				    const fileName = "export.zip";
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
					    message: 'Si &#233; verificato un errore durante la fase di export',
					});
				}
			}, function(a,b,c){
				appUtil.hideLoader();
				console.log(a,b,c);
			});
        });

		$('#chiudiBtnParticelle').off('click').on('click', function(event){
			sessionStorage.removeItem('particelle');
			$('#'+self.idDialog).dialog('close');
        });

	}
	
	/** Modale per l'export in PDF */
	apriModaleExportPdf() {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** IMPOSTAZIONI TITOLO */
		var titolo = 'EXPORT PDF';

		/** COMPILO CON HANDLEBARS */
		openStaticDetailViaHandlebars(self.idDialog + 'exportPdf', 'modExportPdf', {}, titolo, function() {
			appUtil.reloadTooltips();
			
			$('#totalPdf').off('click').on('click', function(event){
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				let titolo = $('#titoloExport').val();
				let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
				var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
				tipologiaEstrazione += " " + nomeLayerPdf;
				catastoRest.exportPdf(particelle, titolo, tipologiaEstrazione).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/octet-stream;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "export.zip";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});
	
			$('#fabbricatiNominativo').off('click').on('click', function(event){
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				let titolo = $('#titoloExport').val();
				let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
				var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
				tipologiaEstrazione += " " + nomeLayerPdf;
				catastoRest.exportFabbricatiPerNominativo(particelle, titolo, tipologiaEstrazione).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "FabbricatiPerNominativo.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						//downloadLink.target = '_blank'; 
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});
	
			$('#terreniNominativo').off('click').on('click', function(event){
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				let titolo = $('#titoloExport').val();
				let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
				var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
				tipologiaEstrazione += " " + nomeLayerPdf;
				catastoRest.exportTerreniPerNominativo(particelle, titolo, tipologiaEstrazione).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "TerreniPerNominativo.pdf";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});
	
			$('#fabbricatiParticella').off('click').on('click', function(event){
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				let titolo = $('#titoloExport').val();
				let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
				var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
				tipologiaEstrazione += " " + nomeLayerPdf;
				catastoRest.exportFabbricatiPerParticella(particelle, titolo, tipologiaEstrazione).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "FabbricatiPerParticella.pdf";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});
	
			$('#terreniParticella').off('click').on('click', function(event){
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				let titolo = $('#titoloExport').val();
				let tipologiaEstrazione = sessionStorage.getItem('tipologiaEstrazione');
				var nomeLayerPdf = sessionStorage.getItem('nomeLayerPdf');
				tipologiaEstrazione += " " + nomeLayerPdf;
				catastoRest.exportTerreniPerParticella(particelle, titolo, tipologiaEstrazione).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "TerreniPerParticella.pdf";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});
		}, { width: 630, height: 250, resizable: false, modale: true,  closable: true });
	}

	/** Modale per l'export in excel */
	apriModaleExportExcel() {
		/** INIZIALIZZAZIONE */
		var self = this;
		/** IMPOSTAZIONI TITOLO */
		var titolo = 'EXPORT EXCEL';

		/** COMPILO CON HANDLEBARS */
		openStaticDetailViaHandlebars(self.idDialog + 'exportXls', 'modExportExcel', {}, titolo, function() {
			appUtil.reloadTooltips();

			/** SCARICA I SOGGETTI NEL FORMATO XLS */
			$('#' + self.idDialog + 'exportXls #soggettiXls').off('click').on('click', function() {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				catastoRest.exportSoggettiXls(particelle).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/vnd.ms-excel;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "Soggetti.xls";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});

			/** SCARICA LA UIU NEL FORMATO XLS */
			$('#' + self.idDialog + 'exportXls #uiuXls').off('click').on('click', function() {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				catastoRest.exportUiuXls(particelle).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/vnd.ms-excel;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "UnitaImmobiliareUrbana.xls";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});

			/** SCARICA I TERRENI NEL FORMATO XLS */
			$('#' + self.idDialog + 'exportXls #terreniXls').off('click').on('click', function() {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				catastoRest.exportTerreniXls(particelle).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/vnd.ms-excel;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "ParticelleTerreni.xls";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});

			/** SCARICA LE INTESTAZIONI NEL FORMATO XLS */
			$('#' + self.idDialog + 'exportXls #intestazioniXls').off('click').on('click', function() {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				catastoRest.exportIntestazioniXls(particelle).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/vnd.ms-excel;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "Intestazioni.xls";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});

			/** SCARICA TUTTI I FILE XLS */
			$('#' + self.idDialog + 'exportXls #totalXls').off('click').on('click', function() {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				let particelle = sessionStorage.getItem('particelle');
				catastoRest.exportXls(particelle).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/octet-stream;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "export.zip";
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
				}, function(a,b,c){
					appUtil.hideLoader();
					console.log(a,b,c);
				});
			});

		}, { width: 630, height: 155, resizable: false, modale: true,  closable: true});
	}

	inizializzaDatatablePT(){
		var self = this;
		$('#'+self.idDialog+' #tabellaRisultatiPT').DataTable( {
			dom: self.datatableDom,
			buttons: self.datatableButtons,
			data: self.datiParticelleTerreni,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        order: [[ 1, 'asc' ]],
	        columnDefs: [
	        	{
	        		targets: 0,
	                className:      'text-left',
	                orderable:      false,
	                render: function(d,t,r){
	                	return '<button type="button" id=' + r.id + ' class="btn-trasp apri-dettaglio-riga-btn closed" style="border: 2px solid; border-radius: 5px;" data-info="Mostra lista intestatari">\
	                				<em class="fa fa-plus"></em>\
	                			</button>';
	                }
	            },
	        	{targets :  1, render: function(d, t, r) {return r.comune;}, orderable : true},
	        	{targets :  2, render: function(d, t, r) {return r.provincia;}, orderable : true},
	        	{targets :  3, render: function(d, t, r) {return r.foglio;}, orderable : true},
	        	{targets :  4, render: function(d, t, r) {return r.numero;}, orderable : true},
	        	{targets :  5, render: function(d, t, r) {return r.sub;}, orderable : true},
	        	{targets :  6, render: function(d, t, r) {return r.partita;}, orderable : true},
	        	{targets :  7, render: function(d, t, r) {return r.classe;}, orderable : true},
	        	{targets :  8, render: function(d, t, r) {
	        		var qualita = lsCodiciQualita.find(x => x.codice == r.qualita);
	        		if (qualita != undefined) {
	        			return qualita.descrizione
	        		} else {
	        			return 'N/D';
	        		}
	        	}, orderable : true},
	        	{targets :  9, render: function(d, t, r) {return r.ettari;}, orderable : true},
	        	{targets : 10, render: function(d, t, r) {return r.are;}, orderable : true},
	        	{targets : 11, render: function(d, t, r) {return r.centiare;}, orderable : true},
	        	{targets : 12, render: function(d, t, r) {return r.superficie;}, orderable : true},
	        	{targets : 13, render: function(d, t, r) {return r.redditoDominicaleEuro;}, orderable : true},
	        	{targets : 14, render: function(d, t, r) {return r.redditoDominicaleLire;}, orderable : true},
	        	{targets : 15, render: function(d, t, r) {return r.redditoAgrarioEuro;}, orderable : true},
	        	{targets : 16, render: function(d, t, r) {return r.redditoAgrarioLire;}, orderable : true}
	        ],
	        drawCallback: function( settings ) {
	        	//MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('id'));
	            });
	            $('#'+self.idDialog+' #tabellaRisultatiPT .apri-dettaglio-riga-btn').off('click').on('click', function () {
	            	let this_click = this;
	            	if( $(this).hasClass('closed') ){
	            		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

	            		let app = catastoRest.ricercaListaIntestatari(this.id).then(function(data){
	            			self.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatari', {intestatari: data.aaData});
	            			//APRI
	            			$(this_click).addClass('opened').removeClass('closed');
	            			$(this_click).children('em').removeClass('fa-plus').addClass('fa-minus');
	            			$(this_click).closest('tr').after(self.templateRigaIntestazione).slideDown("slow");
	            			
	            			let tabellaIntestatari = $(this_click).closest('tr').next('tr').find('table.tabella-riga-dettaglio-intestatari').eq(0);
	            			
	            			$(tabellaIntestatari).DataTable({
	            				ordering: true, 
	            				info: false, 
	            				bPaginate: false,
	            				language: self.datatableLan
	            		    });
	            			$(tabellaIntestatari).parent('div').find('.dataTables_paginate').first().show();
	            			$(tabellaIntestatari).parent('div').find('.dataTables_info').first().show();
	            			appUtil.hideLoader();
	            		}, function(){
	            			console.log("OH MY GOLD!");
	            		});
	            	} else if( $(this).hasClass('opened') ){
	            		//CHIUDI
	            		$(this).removeClass('opened').addClass('closed');
	            		$(this).children('em').addClass('fa-plus').removeClass('fa-minus');
	            		$(this).closest('tr').next('tr.riga-da-rimuovere-al-momento-opportuno').first().remove();
	            	}
	            });
	        }
	    });
	}

	ricercaListaIntestatari(idImmobile) {
		var hrefUrbamid	= appUtil.getUrbamidOrigin() + '/UrbamidWeb/ricerca/ricercaListaIntestatari?idImmobile=' + idImmobile;
		return $.ajax({
			type : "GET",
			processData : false,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
			url : hrefUrbamid,
			cache : true,
			async: true
		});
	}
	
	inizializzaDatatableUIU(){
		var self = this;
		$('#'+self.idDialog+' #tabellaRisultatiUIU').DataTable( {
			dom: self.datatableDom,
			buttons: self.datatableButtons,
			data: self.datiUnitaImmobiliari,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        order: [[ 1, 'asc' ]],
	        columnDefs: [
	        	{
	        		targets: 0,
	                className:      'text-left',
	                orderable:      false,
	                render: function(d,t,r){
	                	return '<button type="button" id=' + r.idImmobile + ' class="btn-trasp apri-dettaglio-riga-btn closed" style="border: 2px solid; border-radius: 5px;" data-info="Mostra lista intestatari">\
	                				<em class="fa fa-plus"></em>\
	                			</button>';
	                }
	            },
	        	{targets :  1, render: function(d, t, r) {return r.comune;}, orderable : true},
	        	{targets :  2, render: function(d, t, r) {return r.zona;}, orderable : true},
	        	{targets :  3, render: function(d, t, r) {
	        		var categoria = lsCategorieCatastali.find(x => x.codice == r.categoria);
	        		if (categoria != undefined) {
	        			return categoria.descrizione
	        		} else {
	        			return 'N/D';
	        		}
	        	}, orderable : true},
	        	{targets :  4, render: function(d, t, r) {return r.classe;}, orderable : true},
	        	{targets :  5, render: function(d, t, r) {return r.indirizzo;}, orderable : true},
	        	{targets :  6, render: function(d, t, r) {return r.civico1;}, orderable : true},
	        	{targets :  7, render: function(d, t, r) {return r.civico2;}, orderable : true},
	        	{targets :  8, render: function(d, t, r) {return r.sezione;}, orderable : true},
	        	{targets :  9, render: function(d, t, r) {return r.foglio;}, orderable : true},
	        	{targets : 10, render: function(d, t, r) {return r.numero;}, orderable : true},
	        	{targets : 11, render: function(d, t, r) {return r.subalterno;}, orderable : true},
	        	{targets : 12, render: function(d, t, r) {return r.consistenza;}, orderable : true},
	        	{targets : 13, render: function(d, t, r) {return r.superficie;}, orderable : true},
	        	{targets : 14, render: function(d, t, r) {return r.renditaLire;}, orderable : true},
	        	{targets : 15, render: function(d, t, r) {return r.renditaEuro;}, orderable : true},
	        	{targets : 16, render: function(d, t, r) {return r.partita;}, orderable : true}
	        ],
	        drawCallback: function( settings ) {
	        	//MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('idImmobile'));
	            });
	            $('#'+self.idDialog+' #tabellaRisultatiUIU .apri-dettaglio-riga-btn').off('click').on('click', function () {
	            	let this_click = this;
	            	if( $(this).hasClass('closed') ){
	            		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

	            		let app = catastoRest.ricercaListaIntestatari(this.id).then(function(data){
	            			self.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatari', {intestatari: data.aaData});
	            			//APRI
	            			$(this_click).addClass('opened').removeClass('closed');
	            			//$(this).data('info', 'Nascondi lista intestatari'); inutile se non si aggiornano i tooltip, ma Ciro deve dirci come perdincibacco fare
	            			$(this_click).children('em').removeClass('fa-plus').addClass('fa-minus');
	            			$(this_click).closest('tr').after(self.templateRigaIntestazione).slideDown("slow");
	            			
	            			let tabellaIntestatari = $(this_click).closest('tr').next('tr').find('table.tabella-riga-dettaglio-intestatari').eq(0);
	            			
	            			$(tabellaIntestatari).DataTable({
	            				ordering: true, 
	            				info: false, 
	            				bPaginate: false, 
	            				language: self.datatableLan
	            		    });
	            			$(tabellaIntestatari).parent('div').find('.dataTables_paginate').first().show();
	            			$(tabellaIntestatari).parent('div').find('.dataTables_info').first().show();
	            			appUtil.hideLoader();
	            		}, function(){
	            			console.log("OH MY GOLD!");
	            		});
	            	} else if( $(this).hasClass('opened') ){
	            		//CHIUDI
	            		$(this).removeClass('opened').addClass('closed');
	            		$(this).children('em').addClass('fa-plus').removeClass('fa-minus');
	            		$(this).closest('tr').next('tr.riga-da-rimuovere-al-momento-opportuno').first().remove();
	            	}
	            });
	        }
	    });
	}

	inizializzaDatatableIntestazioniPersoneFisiche(){
		var self = this;
		$('#'+self.idDialog+' #tabellaRisultatiPersoneFisiche').DataTable( {
			dom: self.datatableDom,
			buttons: self.datatableButtons,
			data: self.datiMockPersoneFisiche,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(d, t, r) {return r.nome;}, orderable : true},
	        	{targets : 1, render: function(d, t, r) {return r.sesso;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.codiceFiscale;}, orderable : true},
	        	{targets : 3, render: function(d, t, r) {return r.comune;}, orderable : true},
	        	{targets : 4, render: function(d, t, r) {return r.provincia;}, orderable : true},
	        	{targets : 5, render: function(d, t, r) {return r.dataNascita;}, orderable : true},
	        	{targets : 6, render: function(d, t, r) {return r.note;}, orderable : true}
	        ],
	        initComplete: function( settings ) {
	        	//MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('id'));
	            });
	        }
	    });
	}

	inizializzaDatatableIntestazioniSoggettiGiuridici(){
		var self = this;
		$('#'+self.idDialog+' #tabellaRisultatiSoggettiGiuridici').DataTable( {
			dom: self.datatableDom,
			buttons: self.datatableButtons,
			data: self.datiMockSoggettiGiuridici,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(d, t, r) {return r.denominazione;}, orderable : true},
	        	{targets : 1, render: function(d, t, r) {return r.codiceFiscale;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.comune;}, orderable : true},
	        	{targets : 3, render: function(d, t, r) {return r.provincia;}, orderable : true}
	        ],
	        initComplete: function( settings ) {
	        	//MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('id'));
	            });
	        }
	    });
	}

}

class PaginaPianoParticellareImpostazioniCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaPianoParticellareImpostazioni','IMPOSTAZIONI PIANO PARTICELLARE', 'modPianoParticellareImpostazioni');
		this.inizializzaPagina();
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventoClickChiudiFinestra();
			self.aggiungiEventoClickSalvaImpostazioni();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
		}, {width: 500, height: 500, resizable: false, closable: true} );
	}
	/**
	 * Aggiunge evento di click che "salva" le impostazioni.
	 * Fondamentalmente mostra solo il toast di successo
	 */
	aggiungiEventoClickSalvaImpostazioni(){
		var self = this;
		$('#'+self.idDialog+' #eseguiBtn').off('click').on('click', function(){
			$('#'+self.idDialog).dialog('close');
			iziToast.success({
			    title: 'OK',
			    message: 'Impostazioni salvate con successo!', 
			    animateInside: false, 
			    position: 'topCenter',
			});
		});
	}

}

class PaginaScegliModalitaPianoParticellareCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaScegliModalitaPianoParticellare','PIANO PARTICELLARE', 'modScegliModalitaPianoParticellare');
		this.chiudiToastAperti();
		this.inizializzaPagina();
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		sessionStorage.setItem('nomeLayerPdf', '');
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventoClickChiudiFinestra();
			$('#selezionaSuMappaBtn').off('click').on('click', function(){
				$('#'+self.idDialog).dialog('close');
				new PaginaPianoParticellareDaSelezioneSuMappaCtrl();
			});
			$('#tracciaSuMappaBtn').off('click').on('click', function(){
				$('#'+self.idDialog).dialog('close');
				new PaginaPianoParticellareDaTracciatoSuMappaCtrl();
			});
			$('#taglioParticellareBtn').off('click').on('click', function(){
				$('#'+self.idDialog).dialog('close');
				new PaginaPianoParticellareDaSelezionaLayerCtrl();
			});
			$('#strumentiRicercaMappaBtn').off('click').on('click', function(){
				$('#'+self.idDialog).dialog('close');
				new PaginaPianoParticellareDaStrumentiRicercaCtrl();
			});
		}, {width: 850, height: 450, closable: true} );
	}
	
	chiudiToastAperti(){
		let listaToast = document.querySelectorAll('.iziToast');
		for(var i=0;i<listaToast.length; i++){
			iziToast.hide({}, listaToast[i]);
		}
	}
}


class PaginaPianoParticellareDaStrumentiRicercaCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaPianoParticellareDaStrumentiRicerca','PIANO PARTICELLARE', 'modPianoParticellareDaStrumentiRicerca');
		this.datiMockRicerca = null;
		this.inizializzaPagina();
		this.lsCategorieCatastali = [];
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita()
		).done(function(categorieCatastali, codiciDiritto, codiciQualita){
			self.lsCategorieCatastali = categorieCatastali[0].aaData;
			self.lsCodiciDiritto = codiciDiritto[0].aaData;
			self.lsCodiciQualita = codiciQualita[0].aaData;
		});
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			appUtil.reloadTooltips();
			$('#tabellaRisultati').hide();
		}, {width: 600, heigth: 500, closable: true});

		$('#eseguiBtn').off('click').on('click', function(event){
			drawLayerSource.clear();
			var foglio = $('#partFoglioCart-rp').val();
			var mappale = $('#partMappaleCart-rp').val();
			if (foglio||mappale) {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				$.when(
					catastoRest.ricercaByFoglioMappale(foglio, mappale)
				).done(function(result){
					$('#tabellaRisultati').show();
				   	self.listaRisultati = result.aaData;
				   	self.inizializzaDatatable(result.aaData);
				});
			}
        });

		$('#azzeraBtn').off('click').on('click', function(event){
			$('#partFoglioCart-rp').val('');
			$('#partMappaleCart-rp').val('');
        });

		$('#chiudiBtnQ').off('click').on('click', function(event){
			drawLayerSource.clear();
			$('#'+self.idDialog).dialog('close');
        });

		$('#estraiBtn').off('click').on('click', function(event){
			var oggettiDaPassare = [];
			var oggettiDaSpedire = [];
			let tabellaFoglioMappale = $('#' + self.idDialog + ' #tabellaRisultati').DataTable();
			tabellaFoglioMappale.rows().every(function(rowIdx, tableLoop, rowLoop) {
				$('input[type="checkbox"]:checked', this.nodes()).each(function() {
					oggettiDaPassare.push(self.listaRisultati.find(x => x.id == $(this)[0].value));
				});
			});
			if (oggettiDaPassare.length > 0) {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	
				oggettiDaPassare.forEach(function(item) {
					delete(item.geometry);
					delete(item.bbox);
					oggettiDaSpedire.push({
						gid: "" + item.id || null,
						codice_com: 'F158',
						foglio: item.foglio || null,
						mappale: item.mappale || null,
						allegato: item.allegato || null,
						sviluppo: item.sviluppo || null,
						htxt: item.htxt || null,
						rtxt: item.rtxt || null,
						xtxt: item.xtxt || null,
						ytxt: item.ytxt || null,
					});
				});

				sessionStorage.setItem('particelle', JSON.stringify(oggettiDaSpedire));
				sessionStorage.setItem('tipologiaEstrazione', 'Estrazione da ricerca particelle');

				$.when(
					catastoRest.ricercaSuParticellePT(oggettiDaSpedire),
					catastoRest.ricercaSuParticelleUI(oggettiDaSpedire),
					catastoRest.ricercaPersoneFisiche(oggettiDaSpedire),
					catastoRest.ricercaSoggettiGiuridici(oggettiDaSpedire)
				).then(function( risultatiPT, risultatiUI, risultatiPS, risultatiSG ){
					if(risultatiPT && risultatiUI && risultatiPS && risultatiSG){
						(appMappa.maps).un('singleclick', self.handleClickEventCallback);
						new PaginaPianoParticellareEstrattoCtrl(risultatiPT, risultatiUI, risultatiPS, risultatiSG);
						appUtil.reloadTooltips();
					}
				}, function(xhr, ajaxOptions, thrownError){
					appUtil.hideLoader();
					deferred.reject('Errore');
				});
			} else {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Errore',
				    message: 'Selezionare una particella in tabella',
				});
			}
			
        });

	}
	/**
	 * Metodo che inizializza il datatable dei file
	 */
	inizializzaDatatable(item){
		var self = this;
		let language = self.datatableLan;
		language.select = {
            rows: {
                _: "Hai selezionato %d particelle mappali per il piano particellare",
                0: "Nessuna particella mappale selezionata",
            }
        };
		$('#'+self.idDialog+' #tabellaRisultati').DataTable( {
			data: item,
			language: language,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        processing: true,
	        destroy: true,
	        order: [[ 1, 'asc' ]],
	        select: {
	            style:    'multi',
	            selector: 'td:first-child'
	        },
	        columnDefs: [
	        	{targets: 0, orderable: false/*, className: 'select-checkbox'*/, data: null, defaultContent: '', render: function(d, t, r){
	        		return '<input type="checkbox" name="nomeACaso" value="' + r.id + '">'
	        	}},
	        	{targets : 1, render: function(d, t, r) {return 'F158';}, orderable : true},
	        	//{targets : 2, render: function(d, t, r) {return r.sezione;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.foglio;}, orderable : true},
	        	{targets : 3, render: function(d, t, r) {return r.mappale;}, orderable : true},
	        	{targets : 4, orderable : false, render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Mostra la particella su mappa" class="btn-trasp bttn vai-alla-mappa pull-right"><em class="fa fa-map-marker"></em></button>';
		        }}
	        ],
	        drawCallback: function( settings ) {
	        	//MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();

	        	$('#'+self.idDialog+' #tabellaRisultati .vai-alla-mappa').off('click').on('click', function () {
	            	let WKT = "";
	            	var clickedId = this.id
	            	$.each( item, function( keyParticella, valueParticella ) {
	            		if ( valueParticella.id == clickedId) {
	            			WKT = valueParticella.geometry;
	            		}
	            	});
	            	if (WKT!="") {
	    	        	drawLayerSource.clear();
	    	        	
	    	        	var format = new ol.format.WKT();
	    	        	var feature = format.readFeature(WKT, {
	    	        	  dataProjection: 'EPSG:4326',
	    	        	  featureProjection: 'EPSG:3857'
	    	        	});
	    	        	drawLayerSource.addFeature( feature );
	    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
	    		    	(appMappa.maps).getView().setZoom(18);
	            	}
	            });

	        	appUtil.hideLoader();
	        }
	    });
	}
	/**
	 * Imposta un evento di onchange sulla select per filtare i file in formato CXF/CMF
	 */
	aggiungiEventoChangeSuSelectFiltroDati(){
		var self = this;
		$('#'+self.idDialog+' #selectCartografiaCatastale').off('change').on('change', function(){
			let table = $('#'+self.idDialog+' #tabellaFile').DataTable();
			table.column(3).search($(this).val()).draw();
		});
	}

}


/**
 * Rimuove le feature selezionate
 */
function rimuoviLayerSelezionato(){
	(appMappa.maps).removeLayer( appMappa.lsLayerEnabled[0] );
	appMappa.lsLayerEnabled = []; 
}
/**
 * Aggiunge il layer sulla mappa
 */
function selezionaCartograficaLayer(layerName, layerTitle, hrefDetail) {
	
	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	sessionStorage.setItem('nomeLayerPdf', layerTitle);
		
	/*****************************/
	/**RIPULISCO LA MAPPA*********/
	/*****************************/
	rimuoviLayerSelezionato();
	
	/*****************************/
	/**AGGIUNGO LAYER ALLA MAPPA**/
	/*****************************/
	var isCluster 		= false;
	appMappa.isLayerDb 	= false;
	href = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "layers/"+layerName+".json";
	responseTmp = appRest.restGeoserver ( href );
	href = responseTmp.layer.resource.href;
	responseLayerDetail = appRest.restGeoserver ( href );
	if( responseLayerDetail.featureType && responseLayerDetail.featureType.keywords && responseLayerDetail.featureType.keywords.string){
		$.each( responseLayerDetail.featureType.keywords.string, function( key, val ) {
			if (val.indexOf("clusterLayer")!=-1 && 
					( (val.substring(val.indexOf("vocabulary=")+11,val.length-2)).toLowerCase()=="true" || (val.substring(val.indexOf("@")+1,val.length)).toLowerCase()=="true"))
				isCluster=true;
			if (val.indexOf("shapeDb")!=-1 && 
					( (val.substring(val.indexOf("vocabulary=")+11,val.length-2)).toLowerCase()=="true" || (val.substring(val.indexOf("@")+1,val.length)).toLowerCase()=="true"))
				appMappa.isLayerDb=true;
			
		});
	}
	if (isCluster) {																	// Devo aggiungere un layer tipo cluster
		appMappa.addClusterLayer( layerName, title );
		appMappa.abilitaClusterHover( );
	} else{																				// Devo aggiungere un layer non cluster
		var source = new ol.source.TileWMS({																		/**Layer source for tile data from WMS servers. */
			serverType	: "geoserver",																				/**The type of the remote WMS server: mapserver, geoserver or qgis. Only needed if hidpi is true. Default is undefined. */
			params		: {"service":"WMS", "layers":appConfig.workspaceGeoserver+":"+layerName},					/**WIDTH, HEIGHT, BBOX and CRS (SRS for WMS version < 1.3.0) will be set dynamically. Required.**/
			url			: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceGeoserver+"/wms",	/**WMS service URL. */
	        projection	: appConfig.geoserverProjection,															/**Recupero la projection del layer da geoserver**/
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
		tileWms.setZIndex(1);
		(appMappa.maps).addLayer(tileWms);
	}
	appMappa.lsLayerEnabled[0] = tileWms;

	/*****************************/
	/**RECUPERO LE PARTICELLE*****/
	/*****************************/
	appConfig.featureSourceSelected = [];
	if (appMappa.isLayerDb) {
	
		sessionStorage.setItem('titoloEstrazione', layerName);
		var hrefEstrazioneParticellareByLayer = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort)
													+ '/UrbamidWeb/webGisController/findGeometryLayer';
		var data = new FormData();
		data.append('layerName', layerName);
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : hrefEstrazioneParticellareByLayer,
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(data) {
				if (data.success) {
					var format = new ol.format.WKT();
					$.each( data.aaData, function(indice, feature){
					
					    var format = new ol.format.WKT();
                        var feature = format.readFeature(feature.geometry);
                        //TODO:la riproiezione è sbagliata
                        
                        var singleFeatures = feature.getGeometry().transform('EPSG:3857', 'EPSG:4326');
					    (appConfig.featureSourceSelected).push( format.writeGeometry(singleFeatures) );
					});
					
					minifize('PaginaPianoParticellareDaSelezionaLayerCtrl');
					appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				} else {
					appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				}
			},
			error : function(e) {
				console.log("ERROR : ", e);
				appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			}
		});
	} else {
		var featureRequest = new ol.format.WFS().writeGetFeature({
			srsName: appConfig.mapProjection,
			featurePrefix: 'urbamid',
			featureTypes: [layerName],
			outputFormat: 'application/json'
		});
		fetch(appUtil.getOrigin() + appConfig.geoserverService+"wfs", {
			method: 'POST',
			body: new XMLSerializer().serializeToString(featureRequest)
		}).then(function(response) {
			return response.json();
		}).then(function(json) {
			appConfig.featureSourceSelected = new ol.format.GeoJSON().readFeatures(json);
			minifize('PaginaPianoParticellareDaSelezionaLayerCtrl');
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		});
	}
}
