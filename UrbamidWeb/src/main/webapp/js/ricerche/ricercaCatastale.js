

ricCatastale = {
		


		tabellaCatasto					: null,
		tabellaPageLength				: 5,
		arrayParticelle					: [],
		 
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
			
			if ( $.fn.DataTable.isDataTable( '#resultRicercaParticella' ) ) {
				(ricCatastale.tabellaCatasto).fnDestroy();
			}

			$("#resultRicercaParticella").hide();
			$("#resultRicercaFoglio").hide();
			$("#resultRicercaToponimo").hide();

        	/**RECUPERO DATI**/
			ricCatastale.partFoglio 	= $("#partFoglio-rp").val();
			ricCatastale.partMappale 	= $("#partMappale-rp").val();
			$("#resultRicerca tbody").empty();
			
			//FILTRO DI RICERCA
			if ( (ricCatastale.partFoglio!=null && ricCatastale.partFoglio!=undefined && ricCatastale.partFoglio!="") 
					&& (ricCatastale.partMappale!=null && ricCatastale.partMappale!=undefined && ricCatastale.partMappale!="") ) {
				
				if ( $.fn.DataTable.isDataTable( '#resultRicercaParticella' ) ) {
					(ricCatastale.tabellaCatasto).fnDestroy();
				}

	        	appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				
				
				hrefUrbamid		= appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname +(appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + appConfig.urbamidCercaParticella;
				var dataPost = new FormData();
		        dataPost.append("numFoglio", ricCatastale.partFoglio);
		        dataPost.append("mappale",ricCatastale.partMappale);
		        
				urbamidCatastoResponse = appRest.restPostUrbamid(hrefUrbamid, "POST", dataPost );
				if ( urbamidCatastoResponse!=null && urbamidCatastoResponse!="") {
				
					urbamidCatastoResponse = JSON.parse( urbamidCatastoResponse );
					ricCatastale.arrayParticelle = urbamidCatastoResponse.aaData;
					ricCatastale.tabellaCatasto 	= $('#resultRicercaParticella').dataTable( {
						pageLength	: ricCatastale.tabellaPageLength,
				        data		: urbamidCatastoResponse.aaData,
				        language	: Constants.TABLE_LANGUAGE,
						lengthChange: false,
						searching	: false,
						info		: false,
						destroy		: true,
						order		: [ 0, "asc" ],
				        columns		: [
				            {mData:"foglio",title: "Foglio"},
				            {mData:"mappale",title: "Mappale"},
				            {mData:"allegato",title: "Allegato"},
				            {mData:"sviluppo",title: "Sviluppo"},
				            {mData:"select", 
				            	title: "Azioni",
				            	className:"nowrap", 
				            	bSortable: false,
				            	checkboxes: {
				                    selectRow: true
				                },
				                render : function(data,type,row,meta){
				            		return "<i class='fa fa-search' aria-hidden='true' onclick='ricCatastale.showRicercaCatastaleFeature("+row.id+")'></i>";
		                         },
		                         title: ""
		                    }
						],
						drawCallback: function( settings ) {
							$('#resultRicercaFoglio_wrapper').hide();
							$('#resultRicercaToponimo_wrapper').hide();
						}
				    });
					$('#resultRicercaParticella').removeAttr("style");
				}
				$("#resultRicercaParticella").show();
			}
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
        },

        showRicercaCatastaleFeature : function( id ) {
        	WKT = "";
        	$.each( ricCatastale.arrayParticelle, function( keyParticella, valueParticella ) {
        		if ( valueParticella.id == id) 
        			WKT = valueParticella.geometry;
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

Constants.TABLE_LANGUAGE = {
		"sEmptyTable":     "Nessun dato presente nella tabella",
		"sInfo":           "Vista da _START_ a _END_ di _TOTAL_ elementi",
		"sInfoEmpty":      "Vista da 0 a 0 di 0 elementi",
		"sInfoFiltered":   "(filtrati da _MAX_ elementi totali)",
		"sInfoPostFix":    "",
		"sInfoThousands":  ".",
		"sLengthMenu":     "Visualizza _MENU_ elementi",
		"sLoadingRecords": "Caricamento...",
		"sProcessing":     "Elaborazione...",
		"sSearch":         "Cerca:",
		"sZeroRecords":    "La ricerca non ha portato alcun risultato.",
		"oPaginate": {
			"sFirst":      "Inizio",
			"sPrevious":   "Indietro",
			"sNext":       "Successivo",
			"sLast":       "Fine"
		},
		"oAria": {
			"sSortAscending":  ": attiva per ordinare la colonna in ordine crescente",
			"sSortDescending": ": attiva per ordinare la colonna in ordine decrescente"
		}
}