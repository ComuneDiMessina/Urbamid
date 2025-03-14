/**
 * CONTROLLER PER LA GESTIONE DELLA CREAZIONE DEL LAYER
 */
class LayerPageCtrl {

    constructor( ) {
        this.aggiungiEventiClickVaiAPagina();
        appUtil.reloadTooltips();
    }

    aggiungiEventiClickVaiAPagina() {
        sessionStorage.setItem('windowOpened',null);
        $('#insLayer').off('click').on('click', function(e) {
            new PaginaGestioneLayerCtrl(); 
        });
    }

}

function manageEditing(self, prop) {

    (appMappa.maps).removeInteraction(self.draw);
    (appMappa.maps).removeInteraction(self.modify);

    /** INIZIALIZZAZIONI */
    self.editFeatures = new ol.Collection();
	self.editSourceFeatures = new ol.source.Vector({features: self.editFeatures, wrapX: false});
	self.editVectorFeatures = new ol.layer.Vector({
		source: self.editSourceFeatures, 
		style: new ol.style.Style({
			/**Stile della linea**/
		    stroke: new ol.style.Stroke({color: 'rgba(200,0,0,1)',width: 10}),
		    /**Stile dei point sulla mappa**/
		    image: new ol.style.Circle({radius: 8,
		        /**Centro del punto**/
		        fill: new ol.style.Fill({color: 'rgba(255,255,255)',width: 5}),
		        /**Bordo del punto**/
		        stroke: new ol.style.Stroke({color: 'rgb(200,0,0,0.8)',width: 3})
		    }),
		})
	});

	(appMappa.maps).addLayer( self.editVectorFeatures );

    /** avvio l'editing */
    activeEditing(self, prop);

    /**
     * Viene abilitato la modifica della entità
     * @param self
     * @param prop
     * @returns
     */
    function activeEditing(self, prop){
        (appMappa.maps).on('dblclick', function (e) {return;});
        self.editSourceFeatures.clear();
        
        /** 1. Inserisco su mappa la geometria */
        if(prop.geometryWKT != null) {

            /** COSTRUISCE FEATURE DAL WKT */
            var format = new ol.format.WKT();
            var feature = format.readFeature(prop.geometryWKT, {
                dataProjection: 'EPSG:4326',
			    featureProjection: 'EPSG:3857'
            });
            /** AGGIUNGE LA FEATURE */
            self.editSourceFeatures.addFeature(feature);
            (appMappa.maps).getView().fit(feature.getGeometry(), { padding: [0, 0, 0, 0] });
            (appMappa.maps).getView().setZoom(12);

            /** 3. Interazione di modifica**/
            self.modify = new ol.interaction.Modify({
                features: self.editFeatures,   
                deleteCondition: function(event) {
                    return ol.events.condition.shiftKeyOnly(event) &&
                        ol.events.condition.singleClick(event);
                }
            });

            (appMappa.maps).addInteraction(self.modify);
            self.modify.on('modifystart', function(e) {
                self.modified = true;
            });
            self.modify.on('modifyend', function (e) {
                self.modified = true;
            });

        } 

        /**2 Imposto lo stile per disegnare**/
        self.drawStyle = new ol.style.Style({
            /**Stile della linea**/
            stroke: new ol.style.Stroke({color: 'rgba(200,0,0,1)',width: 8}),
            /**Stile dei point sulla mappa**/
            image: new ol.style.Circle({radius: 8,
                /**Centro del punto**/
                fill: new ol.style.Fill({color: 'rgba(200,0,0,1)',width: 5}),
                /**Bordo del punto**/
                stroke: new ol.style.Stroke({color: 'rgb(255,255,255)',width: 3})
            }),
        });

        if(prop.newEntita || prop.geometryWKT == null) {
            self.draw = new ol.interaction.Draw({
                features: self.editFeatures,
                type: prop.typeEditing,
                style: self.drawStyle,
            });
            (appMappa.maps).addInteraction(self.draw);

            self.draw.on('drawstart', function(e) {
                self.modified = true;
                let features = self.editSourceFeatures.getFeatures();
                
                if($.isArray(features) && features.length > 0) {
                    appUtil.confirmOperation(function() {
                        for(var i=0; i<features.length; i++){
                            self.editSourceFeatures.removeFeature( features[i] );
                        }

                        (appMappa.maps).removeInteraction(self.draw);
                        (appMappa.maps).removeInteraction(self.modified);
                        activeEditing(self, prop);
                        
                    }, function() { /** ANNULLA */
                        self.draw.finishDrawing();
                        self.draw.removeLastPoint();
                    }, null, 'Iniziando a disegnare una nuova geometria, perderai quella precedente. Desideri continuare comunque?')
                }
               
                
            });
            
            self.draw.on('drawend', function (e) {
                
            });

        }

        if(prop.newEntita) {
            iziToast.show({
                id: prop.idIziToast,
                title: 'Geometria',
                message: '',
                theme: 'dark',
                icon: '',
                layout: 1,
                maxWidth: 550,
                balloon: false,
                displayMode: 'once',
                progressBar: false,
                position: 'bottomLeft',
                animateInside: false,
                timeout: false,
                drag: false,
                onClosed: function(instance, toast, closedBy) {
                    /** Riapro la modale */
                    minifize( prop.pagina );
                    /** Ripulisco la mappa */
                    (appMappa.maps).removeInteraction(self.draw);
                    (appMappa.maps).removeInteraction(self.modify);
                    /** VISUALIZZO UN MESSAGGIO NEL CASO IN CUI LA GEOMETRIA NON SIA STATA MODIFICATA */
                    if ( !self.modified ){ 
                        iziToast.show({
                            title: '',
                            theme: 'dark',
                            icon: 'fa fa-info',
                            message: 'Chiudendo l\'editing, l\'entit&aacute; non verr&agrave; modificata',
                            animateInside: false,
                            position: 'bottomRight',
                        });
                    }
                    /** PULISCO */
                    self.editSourceFeatures.clear();
                },
                buttons: [
                    [
    
                        '<button type="button" title="Punto" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-circle"></em>&nbsp;Punto</button>',
                        function(instance, toast) {
                            (appMappa.maps).removeInteraction(self.draw);
                            (appMappa.maps).removeInteraction(self.modify);
    
                            prop.typeEditing = 'Point'
                            activeEditing(self, prop);
                        }
    
                    ],
    
                    [
    
                        '<button type="button" title="Linea" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-arrows-h"></em>&nbsp;Linea</button>',
                        function(instance, toast) {
                            (appMappa.maps).removeInteraction(self.draw);
                            (appMappa.maps).removeInteraction(self.modify);
    
                            prop.typeEditing = 'LineString'
                            activeEditing(self, prop);
                        }
    
                    ],
    
                    [
    
                        '<button type="button" title="Poligono" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-square-o"></em>&nbsp;Poligono</button>',
                        function(instance, toast) {
                            (appMappa.maps).removeInteraction(self.draw);
                            (appMappa.maps).removeInteraction(self.modify);
    
                            prop.typeEditing = 'Polygon'
                            activeEditing(self, prop);
                        }
    
                    ],
    
                    [
                        '<button type="button" title="Pulisci" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
                        function(instance, toast) {
                            activeEditing(self, prop);
                        }
                    ],
    
                    [
                        '<button type="button" title="Conferma geometria" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina</button>',
                        function(instance, toast){
                            let features = self.editSourceFeatures.getFeatures();
                            if($.isArray(features) && features.length == 0) {
                                iziToast.error({
                                    title: 'Attenzione',
                                    theme: 'dark',
                                    icon:'fa fa-times',
                                    message: 'Devi tracciare una geometria prima di poter confermare il disegno!',
                                    animateInside: false,
                                    position: 'topCenter',
                                });
                            
                            } else {
                                self.confermaFeatureDisegnate();
                            
                            }
                            
                        }
                    ]
                ]
            });

        } else {

            iziToast.show({
                id: prop.idIziToast,
                title: 'Geometria',
                message: '',
                theme: 'dark',
                icon: '',
                layout: 1,
                maxWidth: 550,
                balloon: false,
                displayMode: 'once',
                progressBar: false,
                position: 'bottomLeft',
                animateInside: false,
                timeout: false,
                drag: false,
                onClosed: function(instance, toast, closedBy) {
                    /** Riapro la modale */
                    minifize( prop.pagina );
                    /** Ripulisco la mappa */
                    (appMappa.maps).removeInteraction(self.draw);
                    (appMappa.maps).removeInteraction(self.modify);
                    /** VISUALIZZO UN MESSAGGIO NEL CASO IN CUI LA GEOMETRIA NON SIA STATA MODIFICATA */
                    if ( !self.modified ){ 
                        iziToast.show({
                            title: '',
                            theme: 'dark',
                            icon: 'fa fa-info',
                            message: 'Chiudendo l\'editing, l\'entit&aacute; non verr&agrave; modificata',
                            animateInside: false,
                            position: 'bottomRight',
                        });
                    }
                    /** PULISCO */
                    self.editSourceFeatures.clear();
                },
                buttons: [    
                    [
                        '<button type="button" title="Pulisci" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-eraser"></em>&nbsp;Pulisci</button>',
                        function(instance, toast) {
                            activeEditing(self, prop);
                        }
                    ],
    
                    [
                        '<button type="button" title="Conferma modifica" class="btn-trasp bttn" style="background-color: #004275;"><em class="fa fa-check"></em>&nbsp;Termina</button>',
                        function(instance, toast){
                            self.confermaFeatureDisegnate();
                        }
                    ]
                ]
            });

        }
       
    
    }

}