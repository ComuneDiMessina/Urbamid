//NOTA BENE... questo è tutto codice da copia-incolla... fare refactoring dalle relative classi
//del piano particellare... pena la radiazione dall'albo XD

/**
 * Superclasse per i controller del piano particellare. Raggruppa quelli che possono essere i dati e/o i metodi comuni
 */
class BaseAmbitoCtrl {
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
		this.idLayerSelezonePianoParticellare = 'layerSelezonePianoParticellare';
		this.layerConFeatureSelezionate = new ol.layer.Vector({});
		this.layerConFeatureSelezionate.setId(this.idLayerSelezonePianoParticellare);
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
				if($.type(features[i].a) === 'string' && features[i].a.startsWith("Particelle.")){
					array.push(features[i]);
				}
			}
		}
		return array;
	}
	isEventoSingleclickAbilitato(){
		let valore = sessionStorage.getItem('ambito.variante.singleclick.mappa.abilitato');
		return valore === 'true';
	}
	disabilitaEventoSingleclickSuMappa(){ sessionStorage.setItem('ambito.variante.singleclick.mappa.abilitato', 'false'); }
	abilitaEventoSingleclickSuMappa(){ sessionStorage.setItem('ambito.variante.singleclick.mappa.abilitato', 'true'); }
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
	rimuoviFeatureDallaSelezione(features){
		let nuovoArrayDiFeaturesSelezionate = [];
		//confronto ogni feature del click con ogni feature della selezione.
		//se trovo che la feature del click è uguale ad una delle feature della selezione,
		//non la tengo in considerazione per la costruzione del nuovo array della selezione
		for(var j=0; j<this.featuresSelezionate.length;j++){
			let dovreiInserireQuestaFeatureNellaNuovaSelezione = true;
			for(var i=0; i<features.length; i++){
				if(this.sonoUguali(features[i], this.featuresSelezionate[j])){
					dovreiInserireQuestaFeatureNellaNuovaSelezione = false;
					break
				}
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
	
}

/**
 * Controller per la modale di import dati catastali
 */ 
class PaginaAmbitoDaSelezioneSuMappaCtrl extends BaseAmbitoCtrl {
	/**
	 * Costruttore
	 */
	constructor( callbackRiaperturaModaleVarianti ) {
		super();
		this.tipCtrlZ = false;
		this.apriToastStrumenti();
		this.mostraLayerParticelle();
		this.aggiungiEventoClickSelezionaSuMappa();
		this.abilitaEventoSingleclickSuMappa();
		this.callbackRiaperturaModaleVarianti = callbackRiaperturaModaleVarianti;
	}
	/**
	 * Aggiunge un evento di singleclick sulla mappa
	 */
	aggiungiEventoClickSelezionaSuMappa(){
		var self = this;
		(appMappa.maps).on('singleclick', function(event) {
			event.stopPropagation(); //oppure return false per evitare che altri eventi impostati su singleclick influenzino il comportamento del metodo
			var isDeseleziona = (event.originalEvent.metaKey || event.originalEvent.ctrlKey) ? true : false;
			if( self.isEventoSingleclickAbilitato() ) {
				if(self.tipCtrlZ === false) self.mostraTipCtrlZ();
					let untiled = self.creaImageUntiledPerRichiestaGeoserver();
					let view = (appMappa.maps).getView();
					
					this.hrefEstrazioneParticellareByGeom = appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname +(appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
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
						timeout: 600000,
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
				appMappa.removeLayer("u_cat_particelle");
				appMappa.removeLayer("u_cat_fabbricati");
		    },
			buttons: [
				[
					'<button type="button" title="Impostazioni estrazione ambito" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-cogs"></em></button>',
					function(instance, toast){
						new PaginaGestionePreferenzePianoCtrl();
					}
				],
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
					'<button type="button" title="Estrai poligono ambito variante" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Salva</button>',
					function(instance, toast){
						if( $.isArray(self.featuresSelezionate) && self.featuresSelezionate.length > 0 ){
							
							appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							setTimeout(function(){ 
								iziToast.hide({transitionOut: 'fadeOutUp'}, toast);

								var src = 'EPSG:3857';
								var dest = 'EPSG:4326';
								var oggettiSelezionatiWkt = [];
								
								var format = new ol.format.WKT();
								
								$.each(self.featuresSelezionate, function(index, value){
									oggettiSelezionatiWkt.push(format.writeGeometry(value.getGeometry().transform(src, dest)));
								});

								var ambito = new Object();
								ambito.wktGeom = oggettiSelezionatiWkt;
								ambito.idVariante = JSON.parse(sessionStorage.getItem('idVariante'));
								$.when(
									pianoRegolatoreRest.salvaAmbitoVarianteSelezione(ambito)
								).done(function(risultato){
									if (risultato.message === 'Success') {
										appUtil.hideLoader();
									} else {
										iziToast.error({
											title: 'Attenzione',
											theme: 'dark',
											icon:'fa fa-info-circle',
											message: 'Si &egrave; verificato un errore durante il salvataggio della geometria.',
											animateInside: false,
											position: 'bottomCenter',
										});
									}
								});
							}, 2000);
							
							appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							setTimeout(function(){ 
								iziToast.hide({transitionOut: 'fadeOutUp'}, toast);
								appUtil.hideLoader();
								self.rimuoviFeatureSelezionate();
								self.disabilitaEventoSingleclickSuMappa();
								if($.isFunction(self.callbackRiaperturaModaleVarianti)) 
									self.callbackRiaperturaModaleVarianti();
							}, 2000);
						} else {
							appUtil.showMessageAlertApplication(
									'Per favore, seleziona almeno un elemento su mappa prima di lanciare la funzionalit&agrave; di estrazione del piano particellare', 
									'Nessuna area tracciata', false, true, false, null, null);
						}
					}
				],
			],
		});
	}
}

/**
 * Controller per la modale di import dati catastali
 */ 
class PaginaAmbitoDaTracciatoSuMappaCtrl extends BaseAmbitoCtrl {
	/**
	 * Costruttore
	 */
	constructor( callbackRiaperturaModaleVarianti) {
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
		//this.mostraLayerFogli();
		this.mostraLayerParticelle();
		this.aggiungiEventoKeydown_CtrlZ();
		this.callbackRiaperturaModaleVarianti = callbackRiaperturaModaleVarianti;
		
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
		(appMappa.maps).addLayer(self.raster);
		self.featureOverlay.setMap((appMappa.maps));
		(appMappa.maps).addInteraction(self.modify);
		(appMappa.maps).addInteraction(self.draw);
		this.draw.on('drawend', function (e) {
			self.mostraTipModificaDisegnoTracciato();
			var feature = e.feature;
			var src = 'EPSG:3857';
			var dest = 'EPSG:4326';
				
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
		        (appMappa.maps).removeInteraction(self.draw);
		        (appMappa.maps).removeLayer(self.raster);
				(appMappa.maps).removeLayer(self.vector);
		    },
			buttons: [
				[
					'<button type="button" title="Impostazioni estrazione piano particellare" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-cogs"></em></button>',
					function(instance, toast){
						new PaginaGestionePreferenzePianoCtrl();
					}
				],
				[
					'<button type="button" title="Rimuovi tracciato su mappa" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
					function(instance, toast){
						self.rimuoviFeatureDisegnate();
					}
				],
				[
					'<button type="button" title="Estrai piano particellare" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Salva</button>',
					function(instance, toast){
						let features = self.source.getFeatures();
						if( $.isArray(features) && features.length > 0 ){
							appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							setTimeout(function(){ 
								iziToast.hide({transitionOut: 'fadeOutUp'}, toast);
								
								var src = 'EPSG:3857';
								var dest = 'EPSG:4326';
								var format = new ol.format.WKT();
								var wktfeaturegeom = format.writeGeometry( self.geometrySelected.transform(src, dest) );

								var ambito = new Object();
								ambito.wktGeom = wktfeaturegeom;
								ambito.idVariante = JSON.parse(sessionStorage.getItem('idVariante'));
								$.when(
									pianoRegolatoreRest.salvaAmbitoVarianteTracciato(ambito)
								).done(function(risultato){
									if (risultato.message === 'Success') {
										appUtil.hideLoader();
										if($.isFunction(self.callbackRiaperturaModaleVarianti)) 
											self.callbackRiaperturaModaleVarianti();
										self.rimuoviFeatureDisegnate();
									} else {
										iziToast.error({
											title: 'Attenzione',
											theme: 'dark',
											icon:'fa fa-info-circle',
											message: 'Si &grave; verificato un errore durante il salvataggio della geometria.',
											animateInside: false,
											position: 'bottomCenter',
										});
									}
								});
							}, 2000);
						} else {
							appUtil.showMessageAlertApplication(
									'Per favore, traccia un\'area su mappa prima di lanciare la funzionalit&agrave; di estrazione del piano particellare', 
									'Nessuna area tracciata', false, true, false, null, null);
						}
					}
				],
			],
		});
	}
}


class PaginaAmbitoDaStrumentiRicercaCtrl extends BaseModaleRicercaCtrl {
	/**
	 * Costruttore
	 */
	/**
	 * Costruttore
	 */
	constructor( callbackRiaperturaModaleVarianti ) {
		super('paginaPianoParticellareDaStrumentiRicerca','RICERCA PARTICELLE', 'modPianoParticellareDaStrumentiRicerca');
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
		this.callbackRiaperturaModaleVarianti = callbackRiaperturaModaleVarianti;
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
			var selezionati = $("input[name='nomeACaso']:checked");
			if (selezionati.length > 0) {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				$.each( selezionati, function( key, item ) {
					oggettiDaPassare.push(self.listaRisultati.find(x => x.id == item.value));
	        	});
	
				oggettiDaPassare.forEach(function(item) {
					delete(item.geometry);
					delete(item.bbox);
					oggettiDaSpedire.push({
						foglio: item.foglio || null,
						mappale: item.mappale || null,
						idVariante: JSON.parse(sessionStorage.getItem('idVariante'))
					});
				});

				$.when(
					pianoRegolatoreRest.salvaAmbitoVarianteRicerca(oggettiDaSpedire)
				).done(function(risultato){
					if (risultato.message === 'Success') {
						appUtil.hideLoader();
						drawLayerSource.clear();
						$('#'+self.idDialog).dialog('close');
						if($.isFunction(self.callbackRiaperturaModaleVarianti)) 
							self.callbackRiaperturaModaleVarianti();
					} else {
						iziToast.error({
							title: 'Attenzione',
							theme: 'dark',
							icon:'fa fa-info-circle',
							message: 'Si &grave; verificato un errore durante il salvataggio della geometria.',
							animateInside: false,
							position: 'bottomCenter',
						});
					}
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
	        	{targets: 0, orderable: false, data: null, defaultContent: '', render: function(d, t, r){
	        		return '<input type="checkbox" name="nomeACaso" value="' + r.id + '">'
	        	}},
	        	{targets : 1, render: function(d, t, r) {return 'F158';}, orderable : true},
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

class PaginaPianoParticellareDaSelezionaLayerCtrl extends BaseModaleRicercaCtrl {
	/**
	 * Costruttore
	 */
	constructor( callbackRiaperturaModaleVarianti ) {
		super('PaginaPianoParticellareDaSelezionaLayerCtrl', 'PIANO PARTICELLARE', 'modPianoParticellareDaSelezioneLayer');
		
		this.datiMockRicerca = null;
		this.inizializzaPagina();
		this.chiudiToastAperti();
		this.apriToastStrumenti();
		this.layerConFeatureSelezionate = new ol.layer.Vector({});
		this.callbackRiaperturaModaleVarianti = callbackRiaperturaModaleVarianti;
		appMappa.lsLayerEnabled = [];
	
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
			console.log("carico il catalogo per l'estrazione particellare");
			
			cbkOnClick = "selezionaCartograficaLayer";
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
		//non faccio particolari controlli perchè ndringhete ndringhete ndrà miez 'o mar nu scoglj' ce stà.
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
					'<button type="button" title="Estrai piano particellare" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Salva</button>',
					function(instance, toast){
						if( $.isArray(appConfig.featureSourceSelected) && appConfig.featureSourceSelected.length > 0 ){

							appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							
							iziToast.hide({transitionOut: 'fadeOutUp'}, toast);
							
							var ambito = new Object();
							ambito.wktGeom = appConfig.featureSourceSelected;
							ambito.idVariante = JSON.parse(sessionStorage.getItem('idVariante'));
							$.when(
								pianoRegolatoreRest.salvaAmbitoVarianteSelezioneLayer(ambito)
							).done(function(risultato){
								if (risultato.message === 'Success') {
									appUtil.hideLoader();
									if($.isFunction(self.callbackRiaperturaModaleVarianti)) 
										self.callbackRiaperturaModaleVarianti();
								} else {
									iziToast.error({
										title: 'Attenzione',
										theme: 'dark',
										icon:'fa fa-info-circle',
										message: 'Si &egrave; verificato un errore durante il salvataggio della geometria.',
										animateInside: false,
										position: 'bottomCenter',
									});
								}
							});
						} else {
							appUtil.showMessageAlertApplication(
									'Per favore, seleziona almeno un elemento su mappa prima di lanciare la funzionalit&agrave; di estrazione del piano particellare', 
									'Nessuna area tracciata', false, true, false, null, null);
						}
					}
				],
			],
		});
	}
}

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
					    var singleFeatures = feature.geometry;
					    (appConfig.featureSourceSelected).push( singleFeatures );
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

function rimuoviLayerSelezionato(){
	(appMappa.maps).removeLayer( appMappa.lsLayerEnabled[0] );
	appMappa.lsLayerEnabled = []; 
}