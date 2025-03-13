
ricFoglio = {
		
		tabellaCatasto					: null,
		tabellaPageLength				: 5,
		arrayFogli		: [],
		
		arrayFeature	: {
		            	   1:[
		            	   	ol.proj.transform([15.555331185538307 , 38.19186093411969], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555517599064842 , 38.191611127627795], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555568561036125 , 38.19162588415324], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555551126677528 , 38.19169334251718], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555595383126274 , 38.19169861269921], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555604770857826 , 38.191632208377506], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555918589312569 , 38.19168807233475], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555899813849464 , 38.19171758535148], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555846169669167 , 38.19170915306219], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555840805251137 , 38.19174393624913], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555673167187706 , 38.19173444992707], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555632934052483 , 38.19195368905466], 'EPSG:4326', 'EPSG:4326'),
		            	   	ol.proj.transform([15.555331185538307 , 38.19186093411969], 'EPSG:4326', 'EPSG:4326')
		            	   ],
		            	  2:[
		            	   ol.proj.transform([15.55647833951491 , 38.1918428152714], 'EPSG:4326', 'EPSG:4326'),
		            	   ol.proj.transform([15.556523937068164 , 38.19183438299664], 'EPSG:4326', 'EPSG:4326'),
		            	   ol.proj.transform([15.556490333012448 , 38.19144420898074], 'EPSG:4326', 'EPSG:4326'),
		            	   ol.proj.transform([15.556192607811795 , 38.19147793825466], 'EPSG:4326', 'EPSG:4326'),
		            	   ol.proj.transform([15.55647833951491 , 38.1918428152714], 'EPSG:4326', 'EPSG:4326')
		            	  ],
		            	  3: [
		      				ol.proj.transform([15.553185844454674 , 38.18908813283139], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.55330922606936 , 38.18900802315188], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.553175115618615 , 38.188843587217946], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.55303027633181 , 38.18893634599555], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.553089284930138 , 38.18902067205452], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552767419848351 , 38.18917667500624], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552724504504113 , 38.18910921431135], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552579665217309 , 38.18917667500624], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552654767069725 , 38.18928629850215], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.55255284312716 , 38.18935375903302], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552509927782921 , 38.189311596208555], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552252435717492 , 38.18947603108595], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552391910586266 , 38.189640465592156], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552665495905785 , 38.189459165987415], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552606487307457 , 38.18940013811176], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552695000204949 , 38.18934321832919], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552719140086083 , 38.18935797531411], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.552853250536828 , 38.189282082216906], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.55293103459826 , 38.18935797531411], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.553255581889061 , 38.18916824242278], 'EPSG:4326', 'EPSG:4326'),
		      				ol.proj.transform([15.553185844454674 , 38.18908813283139], 'EPSG:4326', 'EPSG:4326')
		          	   	  ]
		            	 },

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
			
			if ( $.fn.DataTable.isDataTable( '#resultRicercaFoglio' ) ) {
				(ricFoglio.tabellaCatasto).fnDestroy();
			}
			
			$("#resultRicercaParticella").hide();
			$("#resultRicercaFoglio").hide();
			$("#resultRicercaToponimo").hide();

        	/**RECUPERO DATI**/
        	ricFoglio.partFoglio 	= $("#partFoglio-rf").val().trim();
			$("#resultRicerca tbody").empty();
			
			//FILTRO DI RICERCA
			if ( (ricFoglio.partFoglio!=null && ricFoglio.partFoglio!=undefined && ricFoglio.partFoglio!="") ) {
				
				if ( $.fn.DataTable.isDataTable( '#resultRicercaFoglio' ) ) {
					(ricFoglio.tabellaCatasto).fnDestroy();
				}

				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				
				hrefUrbamid		= appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname +(appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + appConfig.urbamidCercaFoglio;
				var dataPost = new FormData();
		        dataPost.append("numFoglio", ricFoglio.partFoglio);
		        
				urbamidCatastoResponse = appRest.restPostUrbamid(hrefUrbamid, "POST", dataPost );
				if ( urbamidCatastoResponse!=null) {
					urbamidCatastoResponse = JSON.parse( urbamidCatastoResponse );
					
					ricFoglio.arrayFogli = urbamidCatastoResponse.aaData;
					
					ricFoglio.tabellaCatasto 	= $('#resultRicercaFoglio').dataTable( {
						pageLength	: ricFoglio.tabellaPageLength,
				        data		: urbamidCatastoResponse.aaData,
						destroy		: true,
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
				            	className:"nowrap", 
				            	bSortable: false,
				            	checkboxes: {
				                    selectRow: true
				                },
				                render : function(data,type,row,meta){
				            		return "<i class='fa fa-search' aria-hidden='true' onclick='ricFoglio.showRicercaFoglioFeature("+row.id+")'></i>";
		                         },
		                         title: ""
		                    }
						],
						drawCallback: function( settings ) {
							$('#resultRicercaParticella_wrapper').hide();
							$('#resultRicercaToponimo_wrapper').hide();
						}
				    });
					$('#resultRicercaFoglio').removeAttr("style");
				}
				$("#resultRicercaFoglio").show();
			}			
			appUtil.hideLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
        },

        showRicercaFoglioFeature : function( id ) {
        	
        	WKT = "";
        	$.each( ricFoglio.arrayFogli, function( keyFoglio, valueFoglio ) {
        		if ( valueFoglio.id == id) 
        			WKT = valueFoglio.geometry;
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