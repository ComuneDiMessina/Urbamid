//NOTA BENE... questo è tutto codice da copia-incolla... fare refactoring dalle relative classi
//del piano particellare... pena la radiazione dall'albo XD

/**
 * Superclasse per i controller del piano particellare. Raggruppa quelli che possono essere i dati e/o i metodi comuni
 */
class BaseCertificatoUrbanisticoCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		sessionStorage.setItem('ambito.variante.singleclick.mappa.abilitato', 'false');

		this.drawStyle = new ol.style.Style({
			stroke: new ol.style.Stroke({ color: '#861611d9', width: 2 }),
			fill: new ol.style.Fill({color: 'rgba(134, 22, 17, 0.5)'})
		});
		this.geoJSONFormat = new ol.format.GeoJSON();
		this.featuresSelezionate = [];
		this.idLayerSelezonePianoParticellare = 'layerSelezonePianoParticellare';
		this.layerConFeatureSelezionate = new ol.layer.Vector({});
		this.layerConFeatureSelezionate.setZIndex( 1000 ); 
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
			if(properties && properties.name && properties.name === 'Particelle'){
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
			if(this.sonoUguali(features, this.featuresSelezionate[j])){
				dovreiInserireQuestaFeatureNellaNuovaSelezione = false;
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
		if(self.featuresSelezionate.length > 0) {
			self.layerConFeatureSelezionate.setSource(new ol.source.Vector({ features: self.featuresSelezionate }));
		    if( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) != null){
			    (appMappa.maps).removeLayer( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) );
		    }
		    self.layerConFeatureSelezionate.setZIndex( 1000 ); 
		    (appMappa.maps).addLayer(self.layerConFeatureSelezionate);
		    self.impostaZoomMappaPerContenereFeatureSelezionate();
		} else {
			if( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) != null){
			    (appMappa.maps).removeLayer( (appMappa.maps).getLayerById(self.idLayerSelezonePianoParticellare) );
		    }
		}
	}
	
	impostaZoomMappaPerContenereFeatureSelezionate(){
		var extent = this.layerConFeatureSelezionate.getSource().getExtent();
		(appMappa.maps).getView().fit(extent, (appMappa.maps).getSize());
	}

	/**
	 * Funzione che serve per la chiusura del toast strumenti quando la modale di selezione viene chiusa
	 * @param {*} idIziToast, id iziToast
	 */
	chiusuraToastStrumentiQuandoLaModaleVieneChiusa(idIziToast) {
		$('#' + sessionStorage.getItem('windowOpened')).on('dialogclose', function(event, ui) {
			var iziToastStrumenti = document.getElementById(idIziToast);
			iziToast.hide({
				transitionOut: 'fadeOutUp'
			}, iziToastStrumenti);
		})
	}
	
}

/**
 * Controller per la modale di import dati catastali
 */ 
class PaginaCertificatoUrbanisticoDaSelezioneSuMappaCtrl extends BaseCertificatoUrbanisticoCtrl {
	/**
	 * Costruttore
	 */
	constructor( callbackRiaperturaModaleVarianti ) {
		super();
		this.tipCtrlZ = false;
		this.apriToastStrumenti();
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
			var isDeseleziona = (event.originalEvent.type === 'pointerdown' && event.originalEvent.ctrlKey) ? true : false;
			if( self.isEventoSingleclickAbilitato() ) {
				if(self.tipCtrlZ === false) self.mostraTipCtrlZ();
				
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
						async: false,
						success: function (result) {
							
							if(result.success) {
								let format = new ol.format.WKT();
								let feature = format.readFeature( result.aaData[0].geometry , {
									  dataProjection: 'EPSG:4326',
									  featureProjection: 'EPSG:3857'
								});
								
								feature = self.popolaFeatureProp(feature, result.aaData[0]);
								
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
	mostraNascondiLayerParticelleFogli(){
		if( $('div#u_cat_particelle_divchk input#u_cat_particelle_chk').length > 0 && $('div#u_cat_fabbricati_divchk input#u_cat_fabbricati_chk').length > 0){
			if($('div#u_cat_particelle_divchk input#u_cat_particelle_chk').prop('checked') === false && $('div#u_cat_fabbricati_divchk input#u_cat_fabbricati_chk').prop('checked') === false){
				$('div#u_cat_particelle_divchk input#u_cat_particelle_chk').prop('checked', true );
				$('div#u_cat_fabbricati_divchk input#u_cat_fabbricati_chk').prop('checked', true);
				$('div#u_cat_particelle_divchk input#u_cat_particelle_chk').trigger('change');
				$('div#u_cat_fabbricati_divchk input#u_cat_fabbricati_chk').trigger('change');
			} else {
				$('div#u_cat_particelle_divchk input#u_cat_particelle_chk').prop('checked', false );
				$('div#u_cat_fabbricati_divchk input#u_cat_fabbricati_chk').prop('checked', false);
				$('div#u_cat_particelle_divchk input#u_cat_particelle_chk').trigger('change');
				$('div#u_cat_fabbricati_divchk input#u_cat_fabbricati_chk').trigger('change');
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
		this.layerConFeatureSelezionate.setZIndex( 1000 );
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
			id: 'certificatoUrbanisticoIziToast',
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
			onOpened: function(data){
				self.chiusuraToastStrumentiQuandoLaModaleVieneChiusa(data.id);
			},
			onClosed: function(instance, toast, closedBy){
				//TODO disabilitare la selezione
				self.disabilitaEventoSingleclickSuMappa();
				self.rimuoviFeatureSelezionate();
				appMappa.removeLayer("u_cat_particelle");
				appMappa.removeLayer("u_cat_fabbricati");
				minifize(sessionStorage.getItem('windowOpened'));
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
					'<button type="button" title="Estrai poligono certificato urbanistico" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Estrai</button>',
					function(instance, toast){
						if( $.isArray(self.featuresSelezionate) && self.featuresSelezionate.length > 0 ){
							appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
							setTimeout(function(){ 
								iziToast.hide({transitionOut: 'fadeOutUp'}, toast);
								var src = 'EPSG:3857';
								var dest = 'EPSG:4326';
								
								var oggettiSelezionati = [];
								
								var format = new ol.format.WKT();
								
								$.each(self.featuresSelezionate, function(index, value){
									var daTrasportare = new Object();
									daTrasportare.foglio = value.I.foglio;
									daTrasportare.mappale = value.I.mappale;
									daTrasportare.wkt = format.writeGeometry(value.getGeometry().transform(src, dest));
									oggettiSelezionati.push(daTrasportare);
								});
								
								sessionStorage.setItem('geometriaPiano', JSON.stringify(oggettiSelezionati));
								sessionStorage.setItem('modalitaAcquisizione', 'selezione');
								
								
								appUtil.hideLoader();
								self.rimuoviFeatureSelezionate();
								self.disabilitaEventoSingleclickSuMappa();
								if($.isFunction(self.callbackRiaperturaModaleVarianti)) 
									self.callbackRiaperturaModaleVarianti();

								
							}, 2000);
						} else {
							appUtil.showMessageAlertApplication(
									'Per favore, seleziona almeno un elemento su mappa prima di lanciare la funzionalit&agrave; di estrazione del certificato urbanistico', 
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
class PaginaCertificatoUrbanisticoDaTracciatoSuMappaCtrl extends BaseCertificatoUrbanisticoCtrl {
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
		this.mostraLayerParticelle();
		this.aggiungiEventoKeydown_CtrlZ();
		this.callbackRiaperturaModaleVarianti = callbackRiaperturaModaleVarianti;
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
			id: 'certificatoUrbanisticoIziToast',
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
			onOpened: function(data){
				self.chiusuraToastStrumentiQuandoLaModaleVieneChiusa(data.id);
			},
			onClosed: function(instance, toast, closedBy){
				minifize('paginaCertificatoUrbanistico');
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
					'<button type="button" title="Estrai certificato urbanistico" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Estrai</button>',
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
								
								this.hrefEstrazioneParticellareByGeom = appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname +(appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
																				'/UrbamidWeb/catastoController/findParticellaByWkt';
								var data = new FormData();
								data.append('wktGeom', wktfeaturegeom);
								
								var oggettiSelezionati = [];
								
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
											$.each( data.aaData, function( key, value ) {
												
												var daTrasportare = new Object();
												daTrasportare.foglio = value.foglio;
												daTrasportare.mappale = value.mappale;
												daTrasportare.wkt = value.geometry
												oggettiSelezionati.push(daTrasportare);
												
											});
											sessionStorage.setItem('geometriaPiano', JSON.stringify(oggettiSelezionati));
											sessionStorage.setItem('modalitaAcquisizione', 'tracciato');
											
											appUtil.hideLoader();
											if($.isFunction(self.callbackRiaperturaModaleVarianti)) 
												self.callbackRiaperturaModaleVarianti();
											self.rimuoviFeatureDisegnate();
										}
									}
								});
							}, 2000);
						} else {
							appUtil.showMessageAlertApplication(
									'Per favore, traccia un\'area su mappa prima di lanciare la funzionalit&agrave; di estrazione certificato urbanistico', 
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


class PaginaCertificatoUrbanisticoDaStrumentiRicercaCtrl extends BaseModaleRicercaCtrl {
	/**
	 * Costruttore
	 */
	constructor( callbackRiaperturaModaleVarianti ) {
		super('paginaPianoParticellareDaStrumentiRicerca','STRUMENTI RICERCA', 'modPianoParticellareDaStrumentiRicerca');
		this.datiMockRicerca = [{
			codiceComune: 'F158',
			sezione: 'n.d.',
			foglio: '142',
			mappale: '990',
		},{
			codiceComune: 'F158',
			sezione: 'n.d.',
			foglio: '142',
			mappale: '991',
		},{
			codiceComune: 'F158',
			sezione: 'n.d.',
			foglio: '142',
			mappale: '992',
		},{
			codiceComune: 'F158',
			sezione: 'n.d.',
			foglio: '144',
			mappale: '85',
		},{
			codiceComune: 'F158',
			sezione: 'n.d.',
			foglio: '144',
			mappale: '86',
		},{
			codiceComune: 'F158',
			sezione: 'n.d.',
			foglio: '144',
			mappale: '87',
		},{
			codiceComune: 'F158',
			sezione: 'n.d.',
			foglio: '144',
			mappale: '88',
		},{
			codiceComune: 'F158',
			sezione: 'n.d.',
			foglio: '145',
			mappale: '42',
		}];
		this.callbackRiaperturaModaleVarianti = callbackRiaperturaModaleVarianti;
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
			self.inizializzaBreadcrumb();
			self.inizializzaDatatable();
			//self.aggiungiEventoChangeSuSelectFiltroDati();
			$('#'+self.idDialog+' #estraiBtn').off('click').on('click', function(){
				$('#'+self.idDialog).dialog('destroy').remove();
				if($.isFunction(self.callbackRiaperturaModaleVarianti)) 
					self.callbackRiaperturaModaleVarianti();
			});
			appUtil.reloadTooltips();
		}, {width: 600, heigth: 500, closable: true});
	}
	/**
	 * Metodo che inizializza il datatable dei file
	 */
	inizializzaDatatable(){
		var self = this;
		let language = self.datatableLan;
		language.select = {
            rows: {
                _: "Hai selezionato %d particelle mappali per il certificato urbanistico",
                0: "Nessuna particella mappale selezionata",
            }
        };
		$('#'+self.idDialog+' #tabellaRisultati').DataTable( {
			data: self.datiMockRicerca,
			language: language,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        processing: true,
	        order: [[ 1, 'asc' ]],
	        select: {
	            style:    'multi',
	            selector: 'td:first-child'
	        },
	        columnDefs: [
	        	{targets: 0, orderable: false, className: 'select-checkbox', data: null, defaultContent: '',},
	        	{targets : 1, render: function(d, t, r) {return r.codiceComune;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.sezione;}, orderable : true},
	        	{targets : 3, render: function(d, t, r) {return r.foglio;}, orderable : true},
	        	{targets : 4, render: function(d, t, r) {return r.mappale;}, orderable : true},
	        	{targets : 5, orderable : false, render: function(d, t, r){
		        	return '<button type="button" data-info="Mostra la particella su mappa" class="btn-trasp bttn vai-alla-mappa pull-right"><em class="fa fa-map-marker"></em></button>';
		        }}
	        ],
	        initComplete: function( settings ) {
	        	//MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
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