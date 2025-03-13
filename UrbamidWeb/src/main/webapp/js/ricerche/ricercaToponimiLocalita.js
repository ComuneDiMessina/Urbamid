ricToponimo = {

	tabellaToponimo 	: null,
	tabellaPageLength	: 5,
	arrayToponimi		: [],
				     	 
	urlSearch		: appUtil.getOrigin() + appConfig.geoserverService + appConfig.workspaceService + appConfig.wfsService,
	featureTypes	: ["Catasto_Particelle"],
	outputFormat	: "application/json",
	defFilter		: null,
		
	partFoglio 		: "",
	partMappale 	: "",
	infoResult 		: [],
	range	 		: {"min":0,"max":100},
		
	indexSel		: null,

    cerca : function(){

		if($.fn.DataTable.isDataTable( '#resultRicercaToponimo' )) {
			(ricToponimo.tabellaToponimo).fnDestroy();
		}

		$("#resultRicercaParticella").hide();
		$("#resultRicercaFoglio").hide();
		$("#resultRicercaToponimo").hide();

		/**RECUPERO DATI**/
		ricToponimo.denominazioneUfficiale 	= $('#partToponimo-rt').val().trim();
		$('#resultRicerca').empty();

		/** FILTRO DI RICERCA */
		if(ricToponimo.denominazioneUfficiale != null && ricToponimo.denominazioneUfficiale != undefined && ricToponimo.denominazioneUfficiale.length >= 3) {

			if($.fn.DataTable.isDataTable( '#resultRicercaToponimo' )) {
				(ricToponimo.tabellaToponimo).fnDestroy();
			}

			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');

			//hrefUrbamid = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaCtrl/ricercaToponimo';
			hrefUrbamid = appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'toponomasticaConsultazioneCtrl/ricercaToponimo';
			
			var filtro = {denominazioneUfficiale: ricToponimo.denominazioneUfficiale};

			urbamidToponomasticaResponse = appRest.restPost(hrefUrbamid, "POST", JSON.stringify(filtro));

			if(urbamidToponomasticaResponse != null) {

				urbamidToponomasticaResponse = JSON.parse(urbamidToponomasticaResponse);
				ricToponimo.arrayToponimi = urbamidToponomasticaResponse.data

				ricToponimo.tabellaToponimo = $('#resultRicercaToponimo').dataTable({
					pageLength	: ricToponimo.tabellaPageLength,
					data		: urbamidToponomasticaResponse.data,
					language	: Constants.TABLE_LANGUAGE,
					lengthChange: false,
					searching	: false,
					info		: false,
					destroy		: true,
					order		: [0, "asc"],
					columns		: [
						{mData: 'denominazioneUfficiale', title: 'Denominazione Ufficiale'},
						{mData: 'comuneLabel', title: 'Localit&agrave;'},
						{mData: 'select',
							className: 'nowrap',
							bSortable: false,
							checkboxes: {
								selectRow: true
							},
							render: function(data, type, row, meta) {
								return "<i class='fa fa-search' aria-hidden='true' onclick='ricToponimo.showRicercaToponimoFeature("+row.id+")'></i>";
							},
							title: ''
						}
					],
					drawCallback: function( settings ) {
						$('#resultRicercaParticella_wrapper').hide();
						$('#resultRicercaFoglio_wrapper').hide();
					}
				});
				$('#resultRicercaToponimo').removeAttr("style");
			}
			$('#resultRicercaToponimo').show();
		} else {
			iziToast.error({
				title: 'Attenzione',
				theme: 'dark',
				icon: 'fa fa-times',
				message: 'Devi inserire almeno 3 caratteri per effettuare la ricerca!',
				animateInside: false,
				position: 'topCenter',
			});
		}
		
		appUtil.hideLoader();
    },
        
    showRicercaToponimoFeature : function (id){
		WKT = "";
        $.each( ricToponimo.arrayToponimi, function( keyToponimo, valueToponimo ) {
        	if ( valueToponimo.id == id) 
        		WKT = valueToponimo.geom;
		});
			
        if (WKT!="") {
	        drawLayerSource.clear();
	        	
	        var format = new ol.format.WKT();
	        var feature = format.readFeature(WKT, {
	        	dataProjection: 'EPSG:4326',
	        	featureProjection: 'EPSG:3857'
	        });
	        drawLayerSource.addFeature( feature );
		    const extent = feature.getGeometry().getExtent();
			(appMappa.maps).getView().fit(
					extent, {duration: 1000}
			);
        }
	}
};