'use strict'

/**
 * Controller principale della pagina catasto. Fondamentalmente aggiunge solo
 * degli eventi di click che aprono un oggetto controller appropriato che
 * gestisce la pagina di ricerca.
 */
class RicercaDatiPageCtrl {
	/**
	 * Costruttore
	 */
	constructor( ) {
		this.aggiungiEventiClickVaiAPagina();
		appUtil.reloadTooltips();
	}
	/**
	 * Aggiunge gli eventi di click sul menu che allocano il relativo controller
	 * della modale
	 */
	aggiungiEventiClickVaiAPagina(){
		$('#ricercaSoggettiPersoneFisiche').off('click').on('click', function(event){ new PaginaRicercaSoggettiPersoneFisicheCtrl();});
		$('#ricercaSoggettiGiuridici').off('click').on('click', function(event){ new PaginaRicercaSoggettiGiuridiciCtrl();});
		$('#ricercaImmobiliUIU').off('click').on('click', function(event){ new PaginaRicercaImmobiliUIUCtrl();});
		$('#ricercaImmobiliParticelle').off('click').on('click', function(event){ new PaginaRicercaImmobiliParticelleCtrl();});
		$('#ricercaIntestazioniPersoneFisiche').off('click').on('click', function(event){ new PaginaRicercaIntestazioniPersoneFisicheCtrl();});
		$('#ricercaIntestazioniSoggettiGiuridici').off('click').on('click', function(event){ new PaginaRicercaIntestazioniSoggettiGiuridiciCtrl();});
	}
}

/**
 * Classe controller per la ricerca dei soggetti persone fisiche
 */
class PaginaRicercaSoggettiPersoneFisicheCtrl extends BaseModaleRicercaCtrl {
	constructor( ) {
		super('paginaRicercaSoggettiPersoneFisiche','RICERCA PERSONE FISICHE', 'ricSoggettiPersoneFisiche');
		drawLayerSource.clear();
		this.isRicercaEffettuata = false;
		this.lsCategorieCatastali = [];
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		this.lsComuni = [];
		this.lsProvince = [];
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita(),
			ricercheSoggettiRest.getComuni(),
			ricercheSoggettiRest.getProvince()
		).done(function(categorieCatastali, codiciDiritto, codiciQualita, comuni, province){
			self.lsCategorieCatastali = categorieCatastali[0].aaData;
			self.lsCodiciDiritto = codiciDiritto[0].aaData;
			self.lsCodiciQualita = codiciQualita[0].aaData;
			self.lsComuni = comuni[0].aaData;
			self.lsProvince = province[0].aaData;
		});
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventiClickTornaIndietroARicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			self.aggiungiEventoClickDownloadFileVisura();
			self.nascondiPaginaDettaglio();
			$("#dataNascitaDa").datepicker({dateFormat: 'dd/mm/yy'});
			$("#dataNascitaA").datepicker({dateFormat: 'dd/mm/yy'});
			appUtil.reloadTooltips();
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		$('#tabellaRisultati').hide();
		$( "#dataAggiornamento" ).hide();

		var dropdownProvince = $("#provinciaNascita");
		var dropdownComuni = $("#comuneNascita");

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(
			ricercheSoggettiRest.getProvince()
		).done(function(province){
			province = province.aaData;
			$.each(province, function(key, value) {
				dropdownProvince.append($("<option />").val(value.codice).text(value.descrizione));
			});
			appUtil.hideLoader();
		});

		$('#provinciaNascita').change(function() {
			if ($(this).val() !== '') {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				$('#comuneNascita').empty().append('<option value="" selected>Seleziona...</option>')
				$.when(
					ricercheSoggettiRest.getComuniByProvincia($(this).val())
				).done(function(comuni){
					comuni = comuni.aaData;
					$.each(comuni, function(key, value) {
						dropdownComuni.append($("<option />").val(value.codice).text(value.descrizione));
					});
					appUtil.hideLoader();
				});
			}
		});

		$( "#eseguiBtnSoggPerFis" ).click(function() {
			var provincia = $('#provinciaNascita').val();
			var comune = $('#comuneNascita').val();
			if (provincia != '' && comune == '') {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "Selezionare anche il campo Comune se si vuole effettuare una ricerca per quel criterio",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				self.inizializzaDatatableRicerca();
			}
		});
	
		$( "#azzeraBtn" ).click(function() {
			$('#nome').val('');
			$('#cognome').val('');
			$('#sesso').val('');
			$('#codiceFiscale').val('');
			$('#dataNascitaDa').val('');
			$('#dataNascitaA').val('');
			$("#provinciaNascita").val($("#provinciaNascita option:first").val());
			$('#comuneNascita').empty().append('<option value="" selected>Seleziona...</option>')
			$('#note').val('');
		});

		$( "#esportaBtnSoggPerFis" ).click(function() {
			if (self.isRicercaEffettuata == false) {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "E' necessario effettuare una ricerca prima di poter esportare",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				var nome = $('#nome').val().trim();
				var cognome = $('#cognome').val().trim();
				var sesso = $('#sesso').val();
				var codiceFiscale = $('#codiceFiscale').val().trim();
				var dataNascitaDa = $('#dataNascitaDa').val();
				var dataNascitaA = $('#dataNascitaA').val();
				var provinciaNascita = $('#provinciaNascita').val();
				var comuneNascita = $('#comuneNascita').val();
				var note = $('#note').val();
				ricercheSoggettiRest.exportPersoneFisiche(nome, cognome, sesso, codiceFiscale, dataNascitaDa, dataNascitaA, provinciaNascita, comuneNascita, note).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/msword;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "export.xls";
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
			}
		});

		$.when(
	    	ricercheSoggettiRest.getDataUltimoAggiornamento()
		).done(function(dataAggiornamento){
			$( "#dataAggiornamento" ).append('Data ultimo aggiornamento dati catastali: ' + dataAggiornamento.aaData);
		});

	}

	inizializzaDatatableRicerca(){
		var self = this;
		var nome = $('#nome').val().trim();
		var cognome = $('#cognome').val().trim();
		var sesso = $('#sesso').val();
		var codiceFiscale = $('#codiceFiscale').val().trim();
		var dataNascitaDa = $('#dataNascitaDa').val();
		var dataNascitaA = $('#dataNascitaA').val();
		var provinciaNascita = $('#provinciaNascita').val();
		var comuneNascita = $('#comuneNascita').val();
		var note = $('#note').val();
		var url = ricercheSoggettiRest.ricercaPersoneFisiche(nome, cognome, sesso, codiceFiscale, dataNascitaDa, dataNascitaA, provinciaNascita, comuneNascita, note);
		$('#'+self.idDialog+' #tabellaRisultati').DataTable( {
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
	        ajax: url,
	        bSort: false,
	        columnDefs: [
	        	{targets : 0, render: function(d, t, r) {return r.nome;}, orderable : true},
	        	{targets : 1, render: function(d, t, r) {return r.sesso;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.codiceFiscale;}, orderable : true},
	        	{targets : 3, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.luogoNascita);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
	        	{targets : 4, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.luogoNascita);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Provincia
	        	{targets : 5, render: function(d, t, r) {return r.dataNascita;}, orderable : true},
	        	{targets : 6, render: function(d, t, r) {return r.indicazioniSupplementari;}, orderable : true},
	        	{targets : 7, className: "text-right", orderable : false, render: function(d, t, r){
	        		return '<button type="button" data-id="'+r.idSoggetto+'" data-info="Vai al dettaglio" class="btn-trasp bttn vai-dettaglio-btn"><em class="fa fa-arrow-right"></em></button>';
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	// MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('id'));
	            });
	            $('#tabellaRisultati').show();
	            sessionStorage.setItem('soggetti', JSON.stringify(settings.json.data));
				$( "#dataAggiornamento" ).show();
	            appUtil.hideLoader();
	            self.isRicercaEffettuata = true;
	        }
	    });
	}

	inizializzaDatatableTabellaUIU(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaUIU').DataTable({
			data: dati,
			language: self.datatableLan,
			destroy: true,
			bSort: false,
			columns: [
				{
	        		targets: 0,
	                className:      'text-left',
	                orderable:      false,
	                render: function(d,t,r){
	                	if (r.singoloProprietario === true) {
	                		return null;
	                	} else {
	                	return '<button type="button" id=' + r.uiu + ' class="btn-trasp apri-dettaglio-riga-btn closed" style="border: 2px solid; border-radius: 5px;" data-info="Mostra lista intestatari">\
	                				<em class="fa fa-plus"></em>\
	                			</button>';
	                	}
	                }
	            },
		        { data: 'uiu' },
		        {targets :  1, render: function(d, t, r) {
	        		var categoria = lsCategorieCatastali.find(x => x.codice == r.categoria);
	        		if (categoria != undefined) {
	        			return categoria.descrizione
	        		} else {
	        			return 'N/D';
	        		}
	        	}, orderable : true},
		        { data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        { data: 'indirizzo' },
		        {targets : 8, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
        		{targets : 9, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Provincia
		        { data: 'diritto' },
		        { data: 'partita' },
		        { data: 'renditaLire' },
		        { data: 'renditaEuro' },
		        { orderable : false, render: function(d, t, r){
		        	return '<button type="button" id="' + r.uiu + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {

		    	$('#'+self.idDialog+' #tabellaUIU button.vai-alla-mappa').off('click').on('click', function () {
		    		var clickedId = this.id
		    		let dettaglio = '';
		    		let WKT = "";
		    		$.each( dati, function( key, value ) {
	            		if ( value.uiu == clickedId) {
	            			dettaglio = value;
	            		}
	            	});
			    	$.when(
			    		ricercheSoggettiRest.localizzaUIU(dettaglio.foglio, dettaglio.numero)
					).done(function(geometria){
						WKT = geometria.aaData;
						if (WKT!="" && WKT != null && WKT != undefined) {
		    	        	drawLayerSource.clear();
		    	        	
		    	        	var format = new ol.format.WKT();
		    	        	var feature = format.readFeature(WKT, {
		    	        	  dataProjection: 'EPSG:4326',
		    	        	  featureProjection: 'EPSG:3857'
		    	        	});
		    	        	drawLayerSource.addFeature( feature );
		    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
		    		    	(appMappa.maps).getView().setZoom(18);
		    		    	self.riduciAdIconaTutteLeModali();
		            	} else {
		            		iziToast.show({
								balloon: false,
								icon:'fa fa-info-circle', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
							    title: 'Attenzione',
							    message: "Non e' stata trovata nessuna geometria dal DB",
							});
		            	}
					});
	            });

		    	$('#'+self.idDialog+' #tabellaUIU .apri-dettaglio-riga-btn').off('click').on('click', function () {
	            	let this_click = this;
	            	if( $(this).hasClass('closed') ){
	            		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	            		var clickedId = this.id
	            		let dettaglio = '';
			    		$.each( dati, function( key, value ) {
		            		if ( value.uiu == clickedId) {
		            			dettaglio = value;
		            		}
		            	});
			    		let idSoggetto = $('#idSoggetto').val();
	            		let app = ricercheSoggettiRest.ricercaListaIntestatariTranneCorrenteConDiritto(dettaglio.idImmobile, idSoggetto).then(function(data){
	            			if (data.aaData.length === 0) {
	            				self.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatariDirittoVuota');
	            			} else {
	            				self.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatariDiritto', {intestatari: data.aaData});	            				
	            			}
	            			// APRI
	            			$(this_click).addClass('opened').removeClass('closed');
	            			$(this_click).children('em').removeClass('fa-plus').addClass('fa-minus');
	            			$(this_click).closest('tr').after(self.templateRigaIntestazione).slideDown("slow");
	            			
	            			let tabellaIntestatari = $(this_click).closest('tr').next('tr').find('table.tabella-riga-dettaglio-intestatari-diritto').eq(0);
	            			
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
	            		// CHIUDI
	            		$(this).removeClass('opened').addClass('closed');
	            		$(this).children('em').addClass('fa-plus').removeClass('fa-minus');
	            		$(this).closest('tr').next('tr.riga-da-rimuovere-al-momento-opportuno').first().remove();
	            	}
	            });

	        	appUtil.hideLoader();
	        }
		});
	}
	inizializzaDatatableTabellaPT(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPT').DataTable({
			data: dati,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        bSort: false,
			columns: [
				{
	        		targets: 0,
	                className:      'text-left',
	                orderable:      false,
	                render: function(d,t,r){
	                	if (r.singoloProprietario === true) {
	                		return null;
	                	} else {
	                	return '<button type="button" id=' + r.id + ' class="btn-trasp apri-dettaglio-riga-btn closed" style="border: 2px solid; border-radius: 5px;" data-info="Mostra lista intestatari">\
	                				<em class="fa fa-plus"></em>\
	                			</button>';
	                	}
	                }
	            },
		        { data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        {targets : 5, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
        		{targets : 6, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Provincia
		        { data: 'diritto' },
		        { data: 'partita' },
		        // { data: 'uteSoggetto' },
		        { orderable : false, render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaPT button.vai-alla-mappa').off('click').on('click', function () {
		    		var clickedId = this.id
		    		let dettaglio = '';
		    		let WKT = "";
		    		$.each( dati, function( key, value ) {
	            		if ( value.id == clickedId) {
	            			dettaglio = value;
	            		}
	            	});
			    	$.when(
			    		ricercheSoggettiRest.localizzaPT(dettaglio.foglio, dettaglio.numero)
					).done(function(geometria){
						WKT = geometria.aaData;
						if (WKT!="" && WKT != null && WKT != undefined) {
		    	        	drawLayerSource.clear();
		    	        	
		    	        	var format = new ol.format.WKT();
		    	        	var feature = format.readFeature(WKT, {
		    	        	  dataProjection: 'EPSG:4326',
		    	        	  featureProjection: 'EPSG:3857'
		    	        	});
		    	        	drawLayerSource.addFeature( feature );
		    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
		    		    	(appMappa.maps).getView().setZoom(18);
		    		    	self.riduciAdIconaTutteLeModali();
		            	} else {
		            		iziToast.show({
								balloon: false,
								icon:'fa fa-info-circle', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
							    title: 'Attenzione',
							    message: "Non e' stata trovata nessuna geometria dal DB",
							});
		            	}
					});
	            });
		    	
		    	$('#'+self.idDialog+' #tabellaPT .apri-dettaglio-riga-btn').off('click').on('click', function () {
	            	let this_click = this;
	            	if( $(this).hasClass('closed') ){
	            		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	            		var clickedId = this.id
	            		let dettaglio = '';
			    		$.each( dati, function( key, value ) {
		            		if ( value.id == clickedId) {
		            			dettaglio = value;
		            		}
		            	});
			    		let idSoggetto = $('#idSoggetto').val();
	            		let app = ricercheSoggettiRest.ricercaListaIntestatariTranneCorrenteConDiritto(dettaglio.idImmobile, idSoggetto).then(function(data){
	            			self.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatariDiritto', {intestatari: data.aaData});
	            			// APRI
	            			$(this_click).addClass('opened').removeClass('closed');
	            			$(this_click).children('em').removeClass('fa-plus').addClass('fa-minus');
	            			$(this_click).closest('tr').after(self.templateRigaIntestazione).slideDown("slow");
	            			
	            			let tabellaIntestatari = $(this_click).closest('tr').next('tr').find('table.tabella-riga-dettaglio-intestatari-diritto').eq(0);
	            			
	            			$(tabellaIntestatari).DataTable({
	            				ordering: true, 
	            				info: false, 
	            				bPaginate: false, 
	            				language: self.datatableLan
	            				// scrollY: "100px",
	            		        // scrollCollapse: true,
	            		        // paging: false
	            		    });
	            			$(tabellaIntestatari).parent('div').find('.dataTables_paginate').first().show();
	            			$(tabellaIntestatari).parent('div').find('.dataTables_info').first().show();
	            			appUtil.hideLoader();
	            		}, function(){
	            			console.log("OH MY GOLD!");
	            		});
	            	} else if( $(this).hasClass('opened') ){
	            		// CHIUDI
	            		$(this).removeClass('opened').addClass('closed');
	            		$(this).children('em').addClass('fa-plus').removeClass('fa-minus');
	            		$(this).closest('tr').next('tr.riga-da-rimuovere-al-momento-opportuno').first().remove();
	            	}
	            });

	        }
		});
	}
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			$.when(
				ricercheSoggettiRest.dettaglioSoggettiPT(id),
				ricercheSoggettiRest.dettaglioSoggettiUIU(id)
			).done(function(dettaglioPT, dettaglioUIU){
				self.inizializzaDatatableTabellaPT(dettaglioPT[0].aaData);
				self.inizializzaDatatableTabellaUIU(dettaglioUIU[0].aaData)
			});
			let dettaglio = self.recuperaDettaglio(id);
			let nominativo = $.trim(dettaglio.nome);
			let sesso = $.trim(dettaglio.sesso);
			let codiceFiscale = $.trim(dettaglio.codiceFiscale);
			let dataNascita = $.trim(dettaglio.dataNascita);
			let note = $.trim(dettaglio.note);

			let codComune = $.trim(dettaglio.luogoNascita);

			var comune = '';
			var provincia = '';
			
			var luogoNascita = lsComuni.find(x => x.codice == codComune);
    		if (luogoNascita != undefined) {
    			comune = luogoNascita.descrizione
    			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
    			provincia = provinciaNascita.descrizione;
    		}

    		$('#idSoggetto').val(id);
			$('#nominativoDettaglio').val( nominativo.length > 0 ? nominativo : 'n.d.');
			$('#sessoDettaglio').val( sesso.length > 0 ? sesso : 'n.d.');
			$('#codiceFiscaleDettaglio').val( codiceFiscale.length > 0 ? codiceFiscale : 'n.d.');
			$('#comuneDettaglio').val( comune.length > 0 ? comune : 'n.d.');
			$('#provinciaDettaglio').val( provincia.length > 0 ? provincia : 'n.d.');
			$('#dataNascitaDettaglio').val( dataNascita.length > 0 ? dataNascita : 'n.d.');
			$('#noteDettaglio').val( note.length > 0 ? note : 'n.d.');
			self.nascondiPaginaRicerca();
			$('#uiu-tab1').removeClass('active');
			$('#pt-tab1').removeClass('active');
			$('#uiuTabContent1').removeClass('active');
			$('#ptTabContent1').removeClass('active');
			$('#anagrafica-tab1').addClass('active');
			$('#anagraficaTabContent1').addClass('active');
			// aggiorno il breadcrumb
			self.aggiornaBreadcrumb('dettaglio', dettaglio.nome);
		}, 1000);
	}

	recuperaDettaglio(id){
		let soggetti = JSON.parse(sessionStorage.getItem('soggetti'));
		let dettaglio;
		for(var i=0; i<soggetti.length; i++){
			if(soggetti[i].idSoggetto === id){
				dettaglio = soggetti[i];
				break;
			}
		}
		return dettaglio;
	}
}
class PaginaRicercaSoggettiGiuridiciCtrl extends BaseModaleRicercaCtrl{
	constructor() {
		super('paginaRicercaSoggettiGiuridici','RICERCA SOGGETTI GIURIDICI', 'ricSoggettiGiuridici');
		drawLayerSource.clear();
		this.inizializzaPagina();
		this.isRicercaEffettuata = false;
		this.lsCategorieCatastali = [];
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		this.lsComuni = [];
		this.lsProvince = [];
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita(),
			ricercheSoggettiRest.getComuni(),
			ricercheSoggettiRest.getProvince()
		).done(function(categorieCatastali, codiciDiritto, codiciQualita, comuni, province){
			self.lsCategorieCatastali = categorieCatastali[0].aaData;
			self.lsCodiciDiritto = codiciDiritto[0].aaData;
			self.lsCodiciQualita = codiciQualita[0].aaData;
			self.lsComuni = comuni[0].aaData;
			self.lsProvince = province[0].aaData;
		});
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.aggiungiEventiClickTornaIndietroARicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.inizializzaBreadcrumb();
			self.aggiungiEventoClickDownloadFileVisura();
			appUtil.reloadTooltips();
			self.nascondiPaginaDettaglio();
		
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		$('#tabellaRisultatiSG').hide();
		$( "#dataAggiornamento" ).hide();

		var dropdownProvince = $("#provinciaNascitaSG");
		var dropdownComuni = $("#comuneNascitaSG");

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(
			ricercheSoggettiRest.getProvince()
		).done(function(province){
			province = province.aaData;
			$.each(province, function(key, value) {
				dropdownProvince.append($("<option />").val(value.codice).text(value.descrizione));
			});
			appUtil.hideLoader();
		});

		$('#provinciaNascitaSG').change(function() {
			if ($(this).val() !== '') {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				$('#comuneNascitaSG').empty().append('<option value="" selected>Seleziona...</option>')
				$.when(
					ricercheSoggettiRest.getComuniByProvincia($(this).val())
				).done(function(comuni){
					comuni = comuni.aaData;
					$.each(comuni, function(key, value) {
						dropdownComuni.append($("<option />").val(value.codice).text(value.descrizione));
					});
					appUtil.hideLoader();
				});
			}
		});
		
		$( "#eseguiBtnSoggGiur" ).click(function() {
			var provincia = $('#provinciaNascitaSG').val();
			var comune = $('#comuneNascitaSG').val();
			if (provincia != '' && comune == '') {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "Selezionare anche il campo Comune se si vuole effettuare una ricerca per quel criterio",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				self.inizializzaDatatableRicerca();
			}
		});

		$( "#azzeraBtnSG" ).click(function() {
			$('#denominazione').val('');
			$('#provinciaNascita').val('');
			$("#provinciaNascitaSG").val($("#provinciaNascita option:first").val());
			$('#comuneNascitaSG').empty().append('<option value="" selected>Seleziona...</option>')
			$('#codiceFiscale').val('');
			$('#note').val('');
		});

		$( "#esportaBtnSoggGiur" ).click(function() {
			if (self.isRicercaEffettuata == false) {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "E' necessario effettuare una ricerca prima di poter esportare",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				var denominazione = $('#denominazione').val().trim();
				var provinciaNascita = $('#provinciaNascitaSG').val();
				var comuneNascita = $('#comuneNascitaSG').val();
				var codiceFiscale = $('#codiceFiscale').val().trim();
				ricercheSoggettiRest.exportSoggettiGiuridici(denominazione, provinciaNascita, comuneNascita, codiceFiscale).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/msword;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "export.xls";
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
			}
		});

		$.when(
	    	ricercheSoggettiRest.getDataUltimoAggiornamento()
		).done(function(dataAggiornamento){
			$( "#dataAggiornamento" ).append('Data ultimo aggiornamento dati catastali: ' + dataAggiornamento.aaData);
		});

	}

	inizializzaDatatableRicerca(){
		var self = this;
		var denominazione = $('#denominazione').val().trim();
		var provinciaNascita = $('#provinciaNascitaSG').val();
		var comuneNascita = $('#comuneNascitaSG').val();
		var codiceFiscale = $('#codiceFiscale').val().trim();
		var url = ricercheSoggettiRest.ricercaSoggettiGiuridici(denominazione, provinciaNascita, comuneNascita, codiceFiscale);
		$('#'+self.idDialog+' #tabellaRisultatiSG').DataTable( {
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
	        ajax: url,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(d, t, r) {return r.denominazione;}, orderable : true},
	        	{targets : 1, render: function(d, t, r) {return r.codiceFiscale;}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.sede);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
	        	{targets : 3, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.sede);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true},
	        	{targets : 4, className: "text-right", orderable : false, render: function(d, t, r){
	        		return '<button type="button" data-id="'+r.idSoggetto+'" data-info="Vai al dettaglio" class="btn-trasp bttn vai-dettaglio-btn"><em class="fa fa-arrow-right"></em></button>';
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	// MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('id'));
	            });
	            $('#tabellaRisultatiSG').show();
	            sessionStorage.setItem('soggetti', JSON.stringify(settings.json.data));
				$( "#dataAggiornamento" ).show();
	            appUtil.hideLoader();
	            self.isRicercaEffettuata = true;
	        }
	    });
	}
	inizializzaDatatableTabellaUIU(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaUIUSG').DataTable({
			data: dati,
			language: self.datatableLan,
			destroy: true,
			bSort: false,
			columns: [
				{
	        		targets: 0,
	                className:      'text-left',
	                orderable:      false,
	                render: function(d,t,r){
	                	if (r.singoloProprietario === true) {
	                		return null;
	                	} else {
	                	return '<button type="button" id=' + r.uiu + ' class="btn-trasp apri-dettaglio-riga-btn closed" style="border: 2px solid; border-radius: 5px;" data-info="Mostra lista intestatari">\
	                				<em class="fa fa-plus"></em>\
	                			</button>';
	                	}
	                }
	            },
				{ data: 'uiu' },
		        {targets :  1, render: function(d, t, r) {
	        		var categoria = lsCategorieCatastali.find(x => x.codice == r.categoria);
	        		if (categoria != undefined) {
	        			return categoria.descrizione
	        		} else {
	        			return 'N/D';
	        		}
	        	}, orderable : true},
		        { data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        { data: 'indirizzo' },
		        {targets : 8, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
        		{targets : 9, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Provincia
		        { data: 'diritto' },
		        { data: 'partita' },
		        { data: 'renditaLire' },
		        { data: 'renditaEuro' },
		        { orderable : false, render: function(d, t, r){
		        	return '<button type="button" id="' + r.uiu + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaUIUSG button.vai-alla-mappa').off('click').on('click', function () {
		    		var clickedId = this.id
		    		let dettaglio = '';
		    		let WKT = "";
		    		$.each( dati, function( key, value ) {
	            		if ( value.uiu == clickedId) {
	            			dettaglio = value;
	            		}
	            	});
			    	$.when(
			    		ricercheSoggettiRest.localizzaUIU(dettaglio.foglio, dettaglio.numero)
					).done(function(geometria){
						WKT = geometria.aaData;
						if (WKT!="" && WKT != null && WKT != undefined) {
		    	        	drawLayerSource.clear();
		    	        	
		    	        	var format = new ol.format.WKT();
		    	        	var feature = format.readFeature(WKT, {
		    	        	  dataProjection: 'EPSG:4326',
		    	        	  featureProjection: 'EPSG:3857'
		    	        	});
		    	        	drawLayerSource.addFeature( feature );
		    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
		    		    	(appMappa.maps).getView().setZoom(18);
		    		    	self.riduciAdIconaTutteLeModali();
		            	} else {
		            		iziToast.show({
								balloon: false,
								icon:'fa fa-info-circle', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
							    title: 'Attenzione',
							    message: "Non e' stata trovata nessuna geometria dal DB",
							});
		            	}
					});
	            });

		    	$('#'+self.idDialog+' #tabellaUIUSG .apri-dettaglio-riga-btn').off('click').on('click', function () {
	            	let this_click = this;
	            	if( $(this).hasClass('closed') ){
	            		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	            		var clickedId = this.id
	            		let dettaglio = '';
			    		$.each( dati, function( key, value ) {
		            		if ( value.uiu == clickedId) {
		            			dettaglio = value;
		            		}
		            	});
			    		let idSoggetto = $('#idSoggetto').val();
	            		let app = ricercheSoggettiRest.ricercaListaIntestatariTranneCorrenteConDiritto(dettaglio.idImmobile, idSoggetto).then(function(data){
	            			self.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatariDiritto', {intestatari: data.aaData});
	            			// APRI
	            			$(this_click).addClass('opened').removeClass('closed');
	            			$(this_click).children('em').removeClass('fa-plus').addClass('fa-minus');
	            			$(this_click).closest('tr').after(self.templateRigaIntestazione).slideDown("slow");
	            			
	            			let tabellaIntestatari = $(this_click).closest('tr').next('tr').find('table.tabella-riga-dettaglio-intestatari-diritto').eq(0);
	            			
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
	            		// CHIUDI
	            		$(this).removeClass('opened').addClass('closed');
	            		$(this).children('em').addClass('fa-plus').removeClass('fa-minus');
	            		$(this).closest('tr').next('tr.riga-da-rimuovere-al-momento-opportuno').first().remove();
	            	}
	            });

	        	appUtil.hideLoader();
	        }
		});
	}
	inizializzaDatatableTabellaPT(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPTSG').DataTable({
			data: dati,
			language: self.datatableLan,
			destroy: true,
			bSort: false,
			columns: [
				{
	        		targets: 0,
	                className:      'text-left',
	                orderable:      false,
	                render: function(d,t,r){
	                	if (r.singoloProprietario === true) {
	                		return null;
	                	} else {
	                	return '<button type="button" id=' + r.id + ' class="btn-trasp apri-dettaglio-riga-btn closed" style="border: 2px solid; border-radius: 5px;" data-info="Mostra lista intestatari">\
	                				<em class="fa fa-plus"></em>\
	                			</button>';
	                	}
	                }
	            },
		        { data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        {targets : 5, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
        		{targets : 6, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Provincia
		        { data: 'diritto' },
		        { data: 'partita' },
		        // { data: 'uteSoggetto' },
		        { orderable : false, render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaPTSG button.vai-alla-mappa').off('click').on('click', function () {
		    		var clickedId = this.id
		    		let dettaglio = '';
		    		let WKT = "";
		    		$.each( dati, function( key, value ) {
	            		if ( value.id == clickedId) {
	            			dettaglio = value;
	            		}
	            	});
			    	$.when(
			    		ricercheSoggettiRest.localizzaPT(dettaglio.foglio, dettaglio.numero)
					).done(function(geometria){
						WKT = geometria.aaData;
						if (WKT!="" && WKT != null && WKT != undefined) {
		    	        	drawLayerSource.clear();
		    	        	
		    	        	var format = new ol.format.WKT();
		    	        	var feature = format.readFeature(WKT, {
		    	        	  dataProjection: 'EPSG:4326',
		    	        	  featureProjection: 'EPSG:3857'
		    	        	});
		    	        	drawLayerSource.addFeature( feature );
		    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
		    		    	(appMappa.maps).getView().setZoom(18);
		    		    	self.riduciAdIconaTutteLeModali();
		            	} else {
		            		iziToast.show({
								balloon: false,
								icon:'fa fa-info-circle', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
							    title: 'Attenzione',
							    message: "Non e' stata trovata nessuna geometria dal DB",
							});
		            	}
					});
	            });
		    	
		    	$('#'+self.idDialog+' #tabellaPTSG .apri-dettaglio-riga-btn').off('click').on('click', function () {
	            	let this_click = this;
	            	if( $(this).hasClass('closed') ){
	            		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
	            		var clickedId = this.id
	            		let dettaglio = '';
			    		$.each( dati, function( key, value ) {
		            		if ( value.id == clickedId) {
		            			dettaglio = value;
		            		}
		            	});
			    		let idSoggetto = $('#idSoggetto').val();
	            		let app = ricercheSoggettiRest.ricercaListaIntestatariTranneCorrenteConDiritto(dettaglio.idImmobile, idSoggetto).then(function(data){
	            			self.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatariDiritto', {intestatari: data.aaData});
	            			// APRI
	            			$(this_click).addClass('opened').removeClass('closed');
	            			$(this_click).children('em').removeClass('fa-plus').addClass('fa-minus');
	            			$(this_click).closest('tr').after(self.templateRigaIntestazione).slideDown("slow");
	            			
	            			let tabellaIntestatari = $(this_click).closest('tr').next('tr').find('table.tabella-riga-dettaglio-intestatari-diritto').eq(0);
	            			
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
	            		// CHIUDI
	            		$(this).removeClass('opened').addClass('closed');
	            		$(this).children('em').addClass('fa-plus').removeClass('fa-minus');
	            		$(this).closest('tr').next('tr.riga-da-rimuovere-al-momento-opportuno').first().remove();
	            	}
	            });

	        }
		});
	}
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			$.when(
				ricercheSoggettiRest.dettaglioSoggettiPT(id),
				ricercheSoggettiRest.dettaglioSoggettiUIU(id)
			).done(function(dettaglioPT, dettaglioUIU){
				self.inizializzaDatatableTabellaPT(dettaglioPT[0].aaData);
				self.inizializzaDatatableTabellaUIU(dettaglioUIU[0].aaData)
			});
			let dettaglio = self.recuperaDettaglio(id);
			let denominazione = $.trim(dettaglio.denominazione);
			let codiceFiscale = $.trim(dettaglio.codiceFiscale);

			let codComune = $.trim(dettaglio.sede);

			var comune = '';
			var provincia = '';
			
			var luogoNascita = lsComuni.find(x => x.codice == codComune);
    		if (luogoNascita != undefined) {
    			comune = luogoNascita.descrizione
    			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
    			provincia = provinciaNascita.descrizione;
    		}
			self.nascondiPaginaRicerca();
    		$('#idSoggetto').val(id);
			$('#denominazioneDettaglio').val( denominazione.length > 0 ? denominazione : 'n.d.');
			$('#codiceFiscaleDettaglio').val( codiceFiscale.length > 0 ? codiceFiscale : 'n.d.');
			$('#comuneDettaglio').val( comune.length > 0 ? comune : 'n.d.');
			$('#provinciaDettaglio').val( provincia.length > 0 ? provincia : 'n.d.');
			// nascondo la ricerca
			self.nascondiPaginaRicerca();
			$('#uiu-tab2').removeClass('active');
			$('#pt-tab2').removeClass('active');
			$('#uiuTabContent2').removeClass('active');
			$('#ptTabContent2').removeClass('active');
			$('#anagrafica-tab2').addClass('active');
			$('#anagraficaTabContent2').addClass('active');
			// aggiorno il breadcrumb
			self.aggiornaBreadcrumb('dettaglio', dettaglio.denominazione);
		}, 1000);
	}
	
	recuperaDettaglio(id){
		let soggetti = JSON.parse(sessionStorage.getItem('soggetti'));
		let dettaglio;
		for(var i=0; i<soggetti.length; i++){
			if(soggetti[i].idSoggetto === id){
				dettaglio = soggetti[i];
				break;
			}
		}
		return dettaglio;
	}
}

/**
 * Controller per la modale di ricerca immobili UIU
 */
class PaginaRicercaImmobiliUIUCtrl extends BaseModaleRicercaCtrl{
	constructor( ) {
		super('paginaRicercaImmobiliUIU','RICERCA IMMOBILI UIU', 'ricImmobiliUIU');
		drawLayerSource.clear();
		this.isRicercaEffettuata = false;
		this.lsCategorieCatastali = [];
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		this.lsComuni = [];
		this.lsProvince = [];
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita(),
			ricercheSoggettiRest.getComuni(),
			ricercheSoggettiRest.getProvince()
		).done(function(categorieCatastali, codiciDiritto, codiciQualita, comuni, province){
			self.lsCategorieCatastali = categorieCatastali[0].aaData;
			self.lsCodiciDiritto = codiciDiritto[0].aaData;
			self.lsCodiciQualita = codiciQualita[0].aaData;
			self.lsComuni = comuni[0].aaData;
			self.lsProvince = province[0].aaData;
		});
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			$('#'+self.idDialog+' #tabellaIdentificativiUIU').DataTable( {ordering: true, language: self.datatableLan,} );
			self.inizializzaGestioneTabRicerca();
			self.inizializzaBreadcrumb();
			self.aggiungiEventiClickTornaIndietroARicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.nascondiPaginaDettaglio();
			$('#'+self.idDialog+' .vai-alla-mappa').off('click').on('click', function(event){ self.zoomSuFeatureRandom(); });
			$('#'+self.idDialog+' #localizzaBtn').off('click').on('click', function(event){ self.zoomSuFeatureRandom(); });
			self.aggiungiEventoClickDownloadFileVisura();
			appUtil.reloadTooltips();
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		$('#tabellaRisultatiUIU').hide();
		$( "#dataAggiornamento" ).hide();

		$( "#eseguiBtnUIU" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			self.inizializzaDatatableRicerca();
		});

		$( "#azzeraBtnUIU" ).click(function() {
			$('#comune').val('');
			$('#indirizzo').val('');
			$('#zona').val('');
			$('#sezioneUrbana').val('');
			$('#consistenza').val('');
			$('#categoriaUIU').val('');
			$('#foglio').val('');
			$('#superficie').val('');
			$('#classe').val('');
			$('#numero').val('');
			$('#renditaLire').val('');
			$('#partita').val('');
			$('#subalterno').val('');
			$('#renditaEuro').val('');
			$('#soppresso').prop( "checked", false );
		});
	
		$( "#localizzaBtnUIU" ).click(function() {
			var geometria = sessionStorage.getItem('geometria')
			if (geometria != 'null') {
				drawLayerSource.clear();
				
				var format = new ol.format.WKT();
				var feature = format.readFeature(geometria, {
					dataProjection: 'EPSG:4326',
					featureProjection: 'EPSG:3857'
				});
				drawLayerSource.addFeature( feature );
				(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
				(appMappa.maps).getView().setZoom(18);
				self.riduciAdIconaTutteLeModali();
			} else {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "Non e' stata trovata nessuna geometria dal DB",
				});
			}
		});

		$( "#esportaDettaglioBtnUIU" ).click(function() {
			if (self.isRicercaEffettuata == false) {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "E' necessario effettuare una ricerca prima di poter esportare",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				var comune = $('#comune').val();
				var indirizzo = $('#indirizzo').val().trim();
				var zona = $('#zona').val().trim();
				var sezioneUrbana = $('#sezioneUrbana').val().trim();
				var consistenza = $('#consistenza').val().trim();
				var categoria = $('#categoriaUIU').val();
				var foglio = $('#foglio').val().trim();
				var superficie = $('#superficie').val().trim();
				var classe = $('#classe').val().trim();
				var numero = $('#numero').val().trim();
				var renditaLire = $('#renditaLire').val().trim();
				var partita = $('#partita').val().trim();
				var subalterno = $('#subalterno').val().trim();
				var renditaEuro = $('#renditaEuro').val().trim();
				var soppresso;
				if ($('#soppresso').prop('checked')){
					soppresso = true;
				} else {
					soppresso = false;
				}
				ricercheSoggettiRest.exportImmobiliUIU(comune, indirizzo, zona, sezioneUrbana, consistenza, categoria, foglio, superficie, classe, numero, renditaLire, partita, subalterno, renditaEuro, soppresso).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/msword;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "export.xls";
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
			}
		});

		$( "#effettuaVisuraBtnUIU" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var idImmobile = $('#idImmobileUIU').val();
			ricercheSoggettiRest.exportVisuraCatastaleFabbricati(idImmobile).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/pdf;base64,'+response;
					const downloadLink = document.createElement('a');
					const fileName = "visura_catastale.pdf";
					downloadLink.href = linkSource;
					downloadLink.download = fileName;
					downloadLink.click();
				} else {
					appUtil.hideLoader();
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

		$( "#effettuaVisuraStoricaBtnUIU" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var idImmobile = $('#idImmobileUIU').val();
			ricercheSoggettiRest.exportVisuraCatastaleStoricaFabbricati(idImmobile).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/pdf;base64,'+response;
					const downloadLink = document.createElement('a');
					const fileName = "visura_catastale_storica.pdf";
					downloadLink.href = linkSource;
					downloadLink.download = fileName;
					downloadLink.click();
				} else {
					appUtil.hideLoader();
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

		var dropdownComuni = $("#comune");
		var dropdownCategorie = $("#categoriaUIU");

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(
			ricercheSoggettiRest.getComuni(),
			catastoRest.popolaLsCategorieCatastali()
		).done(function(comuni, categorie){
			comuni = comuni[0].aaData;
			$.each(comuni, function(key, value) {
				dropdownComuni.append($("<option />").val(value.codice).text(value.descrizione));
			});

			categorie = categorie[0].aaData;
			$.each(categorie, function(key, value) {
				dropdownCategorie.append($("<option />").val(value.codice).text(value.descrizione));
			});
			$("#comune").val('F158');
			appUtil.hideLoader();
		});

		$.when(
	    	ricercheSoggettiRest.getDataUltimoAggiornamento()
		).done(function(dataAggiornamento){
			$( "#dataAggiornamento" ).append('Data ultimo aggiornamento dati catastali: ' + dataAggiornamento.aaData);
		});

	}

	inizializzaGestioneTabRicerca(){
		// inseriamo i contenuti dei tab (che sono tutti molto simili)
		// generandoli con handlebars
		$("div.bhoechie-tab>div.bhoechie-tab-content").eq(0).append(compilaTemplate('ricercaUIUTabContent', {mappale:true}));
		$("div.bhoechie-tab>div.bhoechie-tab-content").eq(1).append(compilaTemplate('ricercaUIUTabContent', {protocollo:true}));
		$("div.bhoechie-tab>div.bhoechie-tab-content").eq(2).append(compilaTemplate('ricercaUIUTabContent', {variazione:true}));
		$("div.bhoechie-tab>div.bhoechie-tab-content").eq(3).append(compilaTemplate('ricercaUIUTabContent', {scheda:true}));
		// evento di click per cambiare tab
		$("div.bhoechie-tab-menu>div.list-group>a").on('click', function(e) {
			e.preventDefault();
	        $(this).siblings('a.active').removeClass("active");
	        $(this).addClass("active");
	        var index = $(this).index();
	        $("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active").addClass("hidden");
	        $("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active").removeClass('hidden');
	        $("em.da-cancellare-al-momento-opportuno").remove();
	        $("div.bhoechie-tab-menu>div.list-group>a").eq(index).prepend('<em class="da-cancellare-al-momento-opportuno fa fa-check" style="font-size: 1.2em;margin-right:5px;"></em>')
	    });
	}

	inizializzaDatatableRicerca(){
		var self = this;
		var comune = $('#comune').val();
		var indirizzo = $('#indirizzo').val().trim();
		var zona = $('#zona').val().trim();
		var sezioneUrbana = $('#sezioneUrbana').val().trim();
		var consistenza = $('#consistenza').val().trim();
		var categoria = $('#categoriaUIU').val();
		var foglio = $('#foglio').val().trim();
		var superficie = $('#superficie').val().trim();
		var classe = $('#classe').val().trim();
		var numero = $('#numero').val().trim();
		var renditaLire = $('#renditaLire').val().trim();
		var partita = $('#partita').val().trim();
		var subalterno = $('#subalterno').val().trim();
		var renditaEuro = $('#renditaEuro').val().trim();
		var soppresso;
		if ($('#soppresso').prop('checked')){
			soppresso = true;
		} else {
			soppresso = false;
		}
		var url = ricercheSoggettiRest.ricercaImmobiliUIU(comune, indirizzo, zona, sezioneUrbana, consistenza, categoria, foglio, superficie, classe, numero, renditaLire, partita, subalterno, renditaEuro, soppresso);
		$('#'+self.idDialog+' #tabellaRisultatiUIU').DataTable( {
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
	        ajax: url,
	        order: [],
	        columnDefs: [
	        	{targets :  0, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true},
	        	{targets :  1, render: function(d, t, r) {return r.zona;}, orderable : true},
	        	{targets :  2, render: function(d, t, r) {var categoria = lsCategorieCatastali.find(x => x.codice == r.categoria);
        		if (categoria != undefined) {
        			return categoria.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true},
	        	{targets :  3, render: function(d, t, r) {return r.classe;}, orderable : true},
	        	{targets :  4, render: function(d, t, r) {return r.indirizzo;}, orderable : true},
	        	{targets :  5, render: function(d, t, r) {return r.civico1;}, orderable : true},
	        	{targets :  6, render: function(d, t, r) {return r.civico2;}, orderable : true},
	        	{targets :  7, render: function(d, t, r) {return r.sezione;}, orderable : true},
	        	{targets :  8, render: function(d, t, r) {return r.foglio;}, orderable : true},
	        	{targets : 	9, render: function(d, t, r) {return r.numero;}, orderable : true},
	        	{targets : 10, render: function(d, t, r) {return r.subalterno;}, orderable : true},
	        	{targets : 11, render: function(d, t, r) {return r.consistenza;}, orderable : true},
	        	{targets : 12, render: function(d, t, r) {return r.superficie;}, orderable : true},
	        	{targets : 13, render: function(d, t, r) {return r.renditaLireStr+"&#163;";}, orderable : true},
	        	{targets : 14, render: function(d, t, r) {return r.renditaEuroStr+"&#8364;";}, orderable : true},
	        	{targets : 15, render: function(d, t, r) {return r.partita;}, orderable : true},
	        	{targets : 16, className: "text-right", orderable : false, render: function(d, t, r){
	        		return '<button type="button" data-id="'+r.id+'" data-info="Vai al dettaglio" class="btn-trasp bttn vai-dettaglio-btn"><em class="fa fa-arrow-right"></em></button>';
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	// MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('id'));
	            });
	            $('#tabellaRisultatiUIU').show();
	            sessionStorage.setItem('soggetti', JSON.stringify(settings.json.data));
				$( "#dataAggiornamento" ).show();
	            appUtil.hideLoader();
	            self.isRicercaEffettuata = true;
	        }
	    });
	}
	inizializzaDatatableIndirizzi(indirizzi){
		var self = this;
		$('#'+self.idDialog+' #tabellaIndirizzi').DataTable({
			language: self.datatableLan,
			data: indirizzi,
			destroy: true,
			columns: [
		        { data: 'indirizzo' },
		        { data: 'civico1' },
		        { data: 'civico2' },
		        { data: 'civico3' },
		    ]
		});
	}
	inizializzaDatatableIdentificativiUIU(data){
		var self = this;
		$('#'+self.idDialog+' #tabellaIdentificativiUIU').DataTable({
			language: self.datatableLan,
			data: data,
			destroy: true,
			columns: [
				{ data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        { orderable : false, render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaIdentificativiUIU button.vai-alla-mappa').off('click').on('click', function () {
	            	let WKT = "";
	            	var clickedId = this.id
	            	$.each( data, function( keyParticella, valueParticella ) {
	            		if ( valueParticella.id == clickedId) {
	            			WKT = valueParticella.geometry;
	            		}
	            	});
	            	if (WKT!="" && WKT != null) {
	    	        	drawLayerSource.clear();
	    	        	
	    	        	var format = new ol.format.WKT();
	    	        	var feature = format.readFeature(WKT, {
	    	        	  dataProjection: 'EPSG:4326',
	    	        	  featureProjection: 'EPSG:3857'
	    	        	});
	    	        	drawLayerSource.addFeature( feature );
	    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
	    		    	(appMappa.maps).getView().setZoom(18);
	    		    	self.riduciAdIconaTutteLeModali();
	            	} else {
	            		iziToast.show({
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
						    title: 'Attenzione',
						    message: "Non e' stata trovata nessuna geometria dal DB",
						});
	            	}
	            });

	        	appUtil.hideLoader();
	        }
		});
	}
	inizializzaDatatablePersoneFisiche(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPersoneFisiche').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			bSort: false,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}
	inizializzaDatatableSoggettiGiuridici(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaSoggettiGiuridici').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			bSort: false,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ],
	        drawCallback: function( settings ) {
	        	appUtil.hideLoader();
	        }
		});
	}
	inizializzaDatatablePlaniemtrie(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPlanimetrie').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			columns: [
			    { data: 'nomeImage' },
				{ data: 'foglio' },
				{ data: 'mappale' },
				{ data: 'subalterno' },
				{ data: 'dug' },
				{ data: 'indirizzo' },
				{ data: 'numCivico' },
				{ data: 'catCatastale' },
				{ data: 'superficie' },
				{ data: 'scaleFrom' },
				{ data: 'scaleTo' },
				{ data: "", 
	            	className:"nowrap", 
	            	bSortable: false,
	                render : function(data,type,row,meta){
	                	var btn = '<div class="btn-group p5">' +
	                				  '<button id="'+row.nomeImage+'" type="button" class="btn btn-primary btn-download">' +
			        						'<span class="label">Download</span>' +
			        		   		  '</button>' +
			        		   	  '</div>';
	                	return btn;
	                }
                }
		    ],
	        drawCallback: function( settings ) {
	        	appUtil.hideLoader();
	        }
		});
		$( ".btn-download" ).click(function(e) {
			e.preventDefault();
	        var nomeImage = $(this).attr("id");
			ricercheSoggettiRest.downloadPlanimetria(nomeImage).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/msword;base64,'+response;
					const downloadLink = document.createElement('a');
					const fileName = nomeImage+".tif";
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
		})
	}
	
	
	
	
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			let dettaglio = self.recuperaDettaglio(id);
			let comuneDettaglio = $.trim(dettaglio.comune);
			let identificativoDettaglio = 'Foglio: ' + dettaglio.foglio + ' Numero: ' + dettaglio.numero + ' Subalterno: ' + dettaglio.subalterno;
			let zonaDettaglio = $.trim(dettaglio.zona);
			let categoriaDettaglio = $.trim(dettaglio.categoria);
			
			var categoria = lsCategorieCatastali.find(x => x.codice == categoriaDettaglio);
    		if (categoria != undefined) {
    			categoriaDettaglio = categoria.descrizione;
    		}
			
			let classeDettaglio = $.trim(dettaglio.classe);
			let partitaDettaglio = $.trim(dettaglio.partita);
			let superficieDettaglio = $.trim(dettaglio.superficie);
			let consistenzaDettaglio = $.trim(dettaglio.consistenza);
			let renditaEuroDettaglio = $.trim(dettaglio.renditaEuroStr);
			let renditaLireDettaglio = $.trim(dettaglio.renditaLireStr);
			
			let lottoDettaglio = $.trim(dettaglio.lotto);
			let edificioDettaglio = $.trim(dettaglio.edificio);
			let scalaDettaglio = $.trim(dettaglio.scala);
			let internoDettaglio = $.trim(dettaglio.interno);
			let pianoDettaglio = $.trim(dettaglio.piano);
			let annotazioneDettaglio = $.trim(dettaglio.annotazione);

			$('#idImmobileUIU').val(dettaglio.idImmobile);
			
			$('#comuneDettaglio').val( comuneDettaglio.length > 0 ? comuneDettaglio : 'n.d.');
			$('#identificativoDettaglio').val( identificativoDettaglio.length > 0 ? identificativoDettaglio : 'n.d.');
			$('#zonaDettaglio').val( zonaDettaglio.length > 0 ? zonaDettaglio : 'n.d.');
			$('#categoriaDettaglio').val( categoriaDettaglio.length > 0 ? categoriaDettaglio : 'n.d.');
			$('#classeDettaglio').val( classeDettaglio.length > 0 ? classeDettaglio : 'n.d.');
			$('#partitaDettaglio').val( partitaDettaglio.length > 0 ? partitaDettaglio : 'n.d.');
			$('#superficieDettaglio').val( superficieDettaglio.length > 0 ? superficieDettaglio : 'n.d.');
			$('#consistenzaDettaglio').val( consistenzaDettaglio.length > 0 ? consistenzaDettaglio : 'n.d.');
			$('#renditaEuroDettaglio').val( renditaEuroDettaglio.length > 0 ? renditaEuroDettaglio : 'n.d.');
			$('#renditaLireDettaglio').val( renditaLireDettaglio.length > 0 ? renditaLireDettaglio : 'n.d.');
			
			$('#lottoDettaglio').val( lottoDettaglio.length > 0 ? lottoDettaglio : 'n.d.');
			$('#edificioDettaglio').val( edificioDettaglio.length > 0 ? edificioDettaglio : 'n.d.');
			$('#scalaDettaglio').val( scalaDettaglio.length > 0 ? scalaDettaglio : 'n.d.');
			$('#internoDettaglio').val( internoDettaglio.length > 0 ? internoDettaglio : 'n.d.');
			$('#pianoDettaglio').val( pianoDettaglio.length > 0 ? pianoDettaglio : 'n.d.');
			$('#annotazioneDettaglio').val( annotazioneDettaglio.length > 0 ? annotazioneDettaglio : 'n.d.');
			
			var indirizzi = [
				{indirizzo: dettaglio.indirizzo, civico1: dettaglio.civico1, civico2: dettaglio.civico2, civico3: dettaglio.civico3}
			]
			
			self.inizializzaDatatableIndirizzi(indirizzi);

			$.when(
				ricercheSoggettiRest.dettaglioImmobiliUIUIdentificativi(dettaglio.idImmobile),
				ricercheSoggettiRest.dettaglioImmobiliUIUPersoneFisiche(dettaglio.idImmobile),
				ricercheSoggettiRest.dettaglioImmobiliUIUSoggettiGiuridici(dettaglio.idImmobile),
				ricercheSoggettiRest.dettaglioImmobiliUIUPlanimetrie(dettaglio.foglio, dettaglio.numero, dettaglio.subalterno),
				ricercheSoggettiRest.localizzaUIU(dettaglio.foglio, dettaglio.numero)
			).done(function(identificativiUIU, personeFisiche, soggettiGiuridici, soggettiPlanimetrie, geometria){
				self.inizializzaDatatableIdentificativiUIU(identificativiUIU[0].aaData);
				self.inizializzaDatatablePersoneFisiche(personeFisiche[0].aaData);
				self.inizializzaDatatableSoggettiGiuridici(soggettiGiuridici[0].aaData);
				self.inizializzaDatatablePlaniemtrie(soggettiPlanimetrie[0].aaData);
				sessionStorage.setItem('geometria', geometria[0].aaData);
			});
			
			$('#anagrafica-tab3').click();
			$('#indirizzi-tab3').removeClass('active');
			$('#identificativi-tab3').removeClass('active');
			$('#persone-tab3').removeClass('active');
			$('#soggetti-tab3').removeClass('active');
			$('#calcolo-tab3').removeClass('active');

			self.nascondiPaginaRicerca();
			// aggiorno il breadcrumb
			self.aggiornaBreadcrumb('dettaglio', dettaglio.idImmobile);
		}, 1000);
	}
	recuperaDettaglio(id){
		let soggetti = JSON.parse(sessionStorage.getItem('soggetti'));
		let dettaglio = {};
		for(var i=0; i<soggetti.length; i++){
			if(soggetti[i].id === id){
				dettaglio = soggetti[i];
				break;
			}
		}
		return dettaglio;
	}
}

class PaginaRicercaImmobiliParticelleCtrl extends BaseModaleRicercaCtrl{
	constructor( ) {
		super('paginaRicercaImmobiliParticelle','RICERCA PARTICELLE', 'ricImmobiliParticelle');
		drawLayerSource.clear();
		this.isRicercaEffettuata = false;
		this.lsCategorieCatastali = [];
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		this.lsComuni = [];
		this.lsProvince = [];
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita(),
			ricercheSoggettiRest.getComuni(),
			ricercheSoggettiRest.getProvince()
		).done(function(categorieCatastali, codiciDiritto, codiciQualita, comuni, province){
			self.lsCategorieCatastali = categorieCatastali[0].aaData;
			self.lsCodiciDiritto = codiciDiritto[0].aaData;
			self.lsCodiciQualita = codiciQualita[0].aaData;
			self.lsComuni = comuni[0].aaData;
			self.lsProvince = province[0].aaData;
		});
		this.templateRigaIntestazione = compilaTemplate('rigaDettaglioIntestatari', {});
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.inizializzaBreadcrumb();
			self.aggiungiEventiClickTornaIndietroARicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.nascondiPaginaDettaglio();
			$('#'+self.idDialog+' #localizzaBtn').off('click').on('click', function(event){ self.zoomSuFeatureRandom(); });
			self.aggiungiEventoClickDownloadFileVisura();
			appUtil.reloadTooltips();
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		$('#tabellaRisultatiPT').hide();
		$( "#dataAggiornamento" ).hide();

		var dropdownComuni = $("#comunePT");

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(
			ricercheSoggettiRest.getComuni()
		).done(function(comuni){
			comuni = comuni.aaData;
			$.each(comuni, function(key, value) {
				dropdownComuni.append($("<option />").val(value.codice).text(value.descrizione));
			});
			$("#comunePT").val('F158');
			appUtil.hideLoader();
		});

		$( "#eseguiBtnPT" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			self.inizializzaDatatableRicerca();
		});

		$( "#azzeraBtnPT" ).click(function() {
			$('#comunePT').val('');
			$('#sezionePT').val('');
			$('#foglioPT').val('');
			$('#numeroPT').val('');
			$('#subalternoPT').val('');
			$('#partitaPT').val('');
			$('#redditoDominicaleEuroPT').val('');
			$('#redditoDominicaleLirePT').val('');
			$('#redditoAgrarioEuroPT').val('');
			$('#redditoAgrarioLirePT').val('');
			$('#superficiePT').val('');
			$('#soppressoPT').prop( "checked", false );
		});

		$( "#esportaDettaglioBtnPT" ).click(function() {
			if (self.isRicercaEffettuata == false) {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "E' necessario effettuare una ricerca prima di poter esportare",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				var comune = $('#comunePT').val();
				var sezione = $('#sezionePT').val().trim();
				var foglio = $('#foglioPT').val().trim();
				var numero = $('#numeroPT').val().trim();
				var subalterno = $('#subalternoPT').val().trim();
				var partita = $('#partitaPT').val().trim();
				var redditoDominicaleEuro = $('#redditoDominicaleEuroPT').val().trim();
				var redditoDominicaleLire = $('#redditoDominicaleLirePT').val().trim();
				var redditoAgrarioEuro = $('#redditoAgrarioEuroPT').val().trim();
				var redditoAgrarioLire = $('#redditoAgrarioLirePT').val().trim();
				var superficie = $('#superficiePT').val().trim();
				var soppresso;
				if ($('#soppressoPT').prop('checked')){
					soppresso = true;
				} else {
					soppresso = false;
				}
				ricercheSoggettiRest.exportParticellePT(comune, sezione, foglio, numero, subalterno, partita, redditoDominicaleEuro, redditoDominicaleLire, redditoAgrarioEuro, redditoAgrarioLire, superficie, soppresso).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/msword;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "export.xls";
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
			}
		});

		$( "#effettuaVisuraBtnPT" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var idImmobile = $('#idImmobilePT').val();
			ricercheSoggettiRest.exportVisuraCatastaleTerreni(idImmobile).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/pdf;base64,'+response;
					const downloadLink = document.createElement('a');
					const fileName = "visura_catastale.pdf";
					downloadLink.href = linkSource;
					downloadLink.download = fileName;
					downloadLink.click();
				} else {
					appUtil.hideLoader();
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

		$( "#effettuaVisuraStoricaBtnPT" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var idImmobile = $('#idImmobilePT').val();
			ricercheSoggettiRest.exportVisuraCatastaleStoricaTerreni(idImmobile).then(function(response){
				appUtil.hideLoader();
				if (response !== null || response !== undefined) {
					const linkSource = 'data:application/pdf;base64,'+response;
					const downloadLink = document.createElement('a');
					const fileName = "visura_catastale_storica.pdf";
					downloadLink.href = linkSource;
					downloadLink.download = fileName;
					downloadLink.click();
				} else {
					appUtil.hideLoader();
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

		$( "#localizzaBtnPT" ).click(function() {
			var geometria = sessionStorage.getItem('geometria')
			if (geometria != 'null') {
				drawLayerSource.clear();
				
				var format = new ol.format.WKT();
				var feature = format.readFeature(geometria, {
					dataProjection: 'EPSG:4326',
					featureProjection: 'EPSG:3857'
				});
				drawLayerSource.addFeature( feature );
				(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
				(appMappa.maps).getView().setZoom(18);
				self.riduciAdIconaTutteLeModali();
			} else {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "Non e' stata trovata nessuna geometria dal DB",
				});
			}
		});

		$.when(
	    	ricercheSoggettiRest.getDataUltimoAggiornamento()
		).done(function(dataAggiornamento){
			$( "#dataAggiornamento" ).append('Data ultimo aggiornamento dati catastali: ' + dataAggiornamento.aaData);
		});

	}


	inizializzaDatatableRicerca(){
		var self = this;
		var comune = $('#comunePT').val();
		var sezione = $('#sezionePT').val().trim();
		var foglio = $('#foglioPT').val().trim();
		var numero = $('#numeroPT').val().trim();
		var subalterno = $('#subalternoPT').val().trim();
		var partita = $('#partitaPT').val().trim();
		var redditoDominicaleEuro = $('#redditoDominicaleEuroPT').val().trim();
		var redditoDominicaleLire = $('#redditoDominicaleLirePT').val().trim();
		var redditoAgrarioEuro = $('#redditoAgrarioEuroPT').val().trim();
		var redditoAgrarioLire = $('#redditoAgrarioLirePT').val().trim();
		var superficie = $('#superficiePT').val().trim();
		var soppresso;
		if ($('#soppressoPT').prop('checked')){
			soppresso = true;
		} else {
			soppresso = false;
		}
		var url = ricercheSoggettiRest.ricercaParticellePT(comune, sezione, foglio, numero, subalterno, partita, redditoDominicaleEuro, redditoDominicaleLire, redditoAgrarioEuro, redditoAgrarioLire, superficie, soppresso);
		$('#'+self.idDialog+' #tabellaRisultatiPT').DataTable( {
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
	        ajax: url,
	        order: [],
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
	            {targets : 1, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
	        	{targets : 2, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.comune);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true},
	        	{targets :  3, render: function(d, t, r) {return r.sezione;}, orderable : true},
	        	{targets :  4, render: function(d, t, r) {return r.foglio;}, orderable : true},
	        	{targets :  5, render: function(d, t, r) {return r.numero;}, orderable : true},
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
	        	{targets : 9, render: function(d, t, r) {return r.ettari;}, orderable : true},
	        	{targets : 10, render: function(d, t, r) {return r.are;}, orderable : true},
	        	{targets : 11, render: function(d, t, r) {return r.centiare;}, orderable : true},
	        	{targets : 12, render: function(d, t, r) {return r.superficie;}, orderable : true},
	        	{targets : 13, render: function(d, t, r) {return r.redditoDominicaleLire;}, orderable : true},
	        	{targets : 14, render: function(d, t, r) {return r.redditoDominicaleEuro;}, orderable : true},
	        	{targets : 15, render: function(d, t, r) {return r.redditoAgrarioLire;}, orderable : true},
	        	{targets : 16, render: function(d, t, r) {return r.redditoAgrarioEuro;}, orderable : true},
	        	{targets : 17, className: "text-right", orderable : false, render: function(d, t, r){
	        		return '<button type="button" data-id="'+r.id+'" data-info="Vai al dettaglio" class="btn-trasp bttn vai-dettaglio-btn"><em class="fa fa-arrow-right"></em></button>';
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	// MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
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
	            			// APRI
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
	            		// CHIUDI
	            		$(this).removeClass('opened').addClass('closed');
	            		$(this).children('em').addClass('fa-plus').removeClass('fa-minus');
	            		$(this).closest('tr').next('tr.riga-da-rimuovere-al-momento-opportuno').first().remove();
	            	}
	            });
	            $('#tabellaRisultatiPT').show();
	            sessionStorage.setItem('soggetti', JSON.stringify(settings.json.data));
				$( "#dataAggiornamento" ).show();
	            appUtil.hideLoader();
	            self.isRicercaEffettuata = true;
	        }
	    });
	}
	inizializzaDatatablePorzione(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPorzionePT').DataTable({
			data: dati,
			language: self.datatableLan,
			destroy: true,
			columns: [
		        { data: 'classe' },
		        { data: 'ettari' },
		        { data: 'are' },
		        { data: 'centiare' },
		        {targets :  4, render: function(d, t, r) {
	        		var qualita = lsCodiciQualita.find(x => x.codice == r.qualita);
	        		if (qualita != undefined) {
	        			return qualita.descrizione
	        		} else {
	        			return 'N/D';
	        		}
	        	}, orderable : true},
		    ]
		});
	}
	inizializzaDatatableDeduzione(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaDeduzionePT').DataTable({
			data: dati,
			language: self.datatableLan,
			destroy: true,
			columns: [
		        { data: 'simboloDeduzione' },
		    ]
		});
	}
	inizializzaDatatablePersoneFisiche(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPersoneFisichePT').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			bSort: false,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}
	inizializzaDatatableSoggettiGiuridici(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaSoggettiGiuridiciPT').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			bSort: false,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}
	inizializzaDatatableRiserva(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaRiservaPT').DataTable({
			data: dati,
			language: self.datatableLan,
			destroy: true,
			columns: [
		        { data: 'riserva' },
		        { data: 'partitaIscrizione' },
		    ]
		});
	}
	inizializzaDatatableIdentificativiUIU(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaIdentificativiUIUPT').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			columns: [
		        { data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        {targets : 4, render: function(d, t, r) {
		        	var partita = sessionStorage.getItem('partita')
		        	if (partita.length > 0) {
		        		return partita;
		        	} else {
		        		return 'n.d.';
		        	}
		        }, orderable : true},
		        { orderable : false, className: 'text-center', render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaIdentificativiUIUPT button.vai-alla-mappa').off('click').on('click', function () {
	            	let WKT = "";
	            	var clickedId = this.id
	            	$.each( dati, function( keyParticella, valueParticella ) {
	            		if ( valueParticella.id == clickedId) {
	            			WKT = valueParticella.geometry;
	            		}
	            	});
	            	if (WKT!="" && WKT != null) {
	    	        	drawLayerSource.clear();
	    	        	
	    	        	var format = new ol.format.WKT();
	    	        	var feature = format.readFeature(WKT, {
	    	        	  dataProjection: 'EPSG:4326',
	    	        	  featureProjection: 'EPSG:3857'
	    	        	});
	    	        	drawLayerSource.addFeature( feature );
	    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
	    		    	(appMappa.maps).getView().setZoom(18);
	    		    	self.riduciAdIconaTutteLeModali();
	            	} else {
	            		iziToast.show({
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
						    title: 'Attenzione',
						    message: "Non e' stata trovata nessuna geometria dal DB",
						});
	            	}
	            });

	        	appUtil.hideLoader();
	        }
		});
	}
	
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			let dettaglio = self.recuperaDettaglio(id);
			let comune = $.trim(dettaglio.comune);
			let foglio = $.trim(dettaglio.foglio);
			let numero = $.trim(dettaglio.numero);
			let denominatore = $.trim(dettaglio.denominatore);
			let subalterno = $.trim(dettaglio.sub);
			let partita = $.trim(dettaglio.partita);
			sessionStorage.setItem('partita', partita);
			var qualita = lsCodiciQualita.find(x => x.codice == $.trim(dettaglio.qualita));
			let classe = $.trim(dettaglio.classe);
			let ettari = $.trim(dettaglio.ettari);
			let are = $.trim(dettaglio.are);
			let centiare = $.trim(dettaglio.centiare);
			let redditoAgrarioEuro = $.trim(dettaglio.redditoAgrarioEuro);
			let redditoAgrarioLire = $.trim(dettaglio.redditoAgrarioLire);
			let redditoDominicaleEuro = $.trim(dettaglio.redditoDominicaleEuro);
			let redditoDominicaleLire = $.trim(dettaglio.redditoDominicaleLire);
			let annotazioni = $.trim(dettaglio.annotazioni);
			$('#comuneDettaglioPT').val( comune.length > 0 ? comune : 'n.d.');
			$('#foglioDettaglioPT').val( foglio.length > 0 ? foglio : 'n.d.');
			$('#numeroDettaglioPT').val( numero.length > 0 ? numero : 'n.d.');
			$('#denominatoreDettaglioPT').val( denominatore.length > 0 ? denominatore : 'n.d.');
			$('#subalternoDettaglioPT').val( subalterno.length > 0 ? subalterno : 'n.d.');
			$('#partitaDettaglioPT').val( partita.length > 0 ? partita : 'n.d.');
			$('#qualitaDettaglioPT').val( qualita.descrizione.length > 0 ? qualita.descrizione : 'n.d.');
			$('#classeDettaglioPT').val( classe.length > 0 ? classe : 'n.d.');
			$('#ettariDettaglioPT').val( ettari.length > 0 ? ettari : 'n.d.');
			$('#areDettaglioPT').val( are.length > 0 ? are : 'n.d.');
			$('#centiareDettaglioPT').val( centiare.length > 0 ? centiare : 'n.d.');
			$('#agrarioEuroDettaglioPT').val( redditoAgrarioEuro.length > 0 ? redditoAgrarioEuro : 'n.d.');
			$('#agrarioLireDettaglioPT').val( redditoAgrarioLire.length > 0 ? redditoAgrarioLire : 'n.d.');
			$('#dominicaleEuroDettaglioPT').val( redditoDominicaleEuro.length > 0 ? redditoDominicaleEuro : 'n.d.');
			$('#dominicaleLireDettaglioPT').val( redditoDominicaleLire.length > 0 ? redditoDominicaleLire : 'n.d.');
			$('#annotazioneDettaglioPT').val( annotazioni.length > 0 ? annotazioni : 'n.d.');

			$('#idImmobilePT').val(dettaglio.id);

			$.when(
				ricercheSoggettiRest.dettaglioParticellePTPersoneFisiche(dettaglio.id),
				ricercheSoggettiRest.dettaglioParticellePTSoggettiGiuridici(dettaglio.id),
				ricercheSoggettiRest.dettagliParticelleUIUIdentificativi(dettaglio.id),
				ricercheSoggettiRest.localizzaPT(dettaglio.foglio, dettaglio.numero),
				ricercheSoggettiRest.dettagliParticellePTMultiplo(dettaglio.id)
			).done(function(personeFisiche, soggettiGiuridici, identificativiUIU, geometria, dettaglioMultiplo){
				self.inizializzaDatatablePersoneFisiche(personeFisiche[0].aaData);
				self.inizializzaDatatableSoggettiGiuridici(soggettiGiuridici[0].aaData);
				self.inizializzaDatatableIdentificativiUIU(identificativiUIU[0].aaData);
				sessionStorage.setItem('geometria', geometria[0].aaData);
				self.inizializzaDatatableDeduzione(dettaglioMultiplo[0].aaData.listDeduzione);
				self.inizializzaDatatablePorzione(dettaglioMultiplo[0].aaData.listPorzione);
				self.inizializzaDatatableRiserva(dettaglioMultiplo[0].aaData.listRiserva);
			});

			$('#anagrafica-tab4').click();
			$('#porzione-tab4').removeClass('active');
			$('#deduzione-tab4').removeClass('active');
			$('#riserva-tab4').removeClass('active');
			$('#persone-tab4').removeClass('active');
			$('#soggetti-tab4').removeClass('active');
			$('#identificativi-tab4').removeClass('active');

			self.nascondiPaginaRicerca();
			// aggiorno il breadcrumb
			self.aggiornaBreadcrumb('dettaglio', dettaglio.id);
			appUtil.hideLoader();
		}, 1000);
	}
	recuperaDettaglio(id){
		let soggetti = JSON.parse(sessionStorage.getItem('soggetti'));
		let dettaglio = {};
		for(var i=0; i<soggetti.length; i++){
			if(soggetti[i].id === id){
				dettaglio = soggetti[i];
				break;
			}
		}
		return dettaglio;
	}
}

class PaginaRicercaIntestazioniPersoneFisicheCtrl extends BaseModaleRicercaCtrl{
	constructor( ) {
		super('paginaRicercaIntestazioniPersoneFisiche','RICERCA INTESTAZIONI PERSONE FISICHE', 'ricIntestazioniPersoneFisiche');
		drawLayerSource.clear();
		this.isRicercaEffettuata = false;
		this.lsCategorieCatastali = [];
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		this.lsComuni = [];
		this.lsProvince = [];
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita(),
			ricercheSoggettiRest.getComuni(),
			ricercheSoggettiRest.getProvince()
		).done(function(categorieCatastali, codiciDiritto, codiciQualita, comuni, province){
			self.lsCategorieCatastali = categorieCatastali[0].aaData;
			self.lsCodiciDiritto = codiciDiritto[0].aaData;
			self.lsCodiciQualita = codiciQualita[0].aaData;
			self.lsComuni = comuni[0].aaData;
			self.lsProvince = province[0].aaData;
		});
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.inizializzaBreadcrumb();
			self.aggiungiEventiClickTornaIndietroARicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.aggiungiEventoClickDownloadFileVisura();
			self.nascondiPaginaDettaglio();
			appUtil.reloadTooltips();
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		$('#tabellaRisultatiIntestazioniPF').hide();
		$( "#dataAggiornamento" ).hide();

		$( "#eseguiBtnIntestazioniPF" ).click(function() {
			var nome = $('#nomeIntestazioniPF').val();
			var cognome = $('#cognomeIntestazioniPF').val();
			var codiceFiscale = $('#codiceFiscaleIntestazioniPF').val();
			if (nome === '' && cognome === '' && codiceFiscale === '') {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "E' necessario valorizzare almeno 1 campo filtro",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				self.inizializzaDatatableRicerca();
			}
		});
	
		$( "#azzeraBtnIntestazioniPF" ).click(function() {
			$('#nomeIntestazioniPF').val('');
			$('#cognomeIntestazioniPF').val('');
			$('#codiceFiscaleIntestazioniPF').val('');
			$('#checkboxUiuPf').prop( "checked", false );
			$('#checkboxPtPf').prop( "checked", false );
		});

		$( "#esportaDettaglioBtnIntestazioniPF" ).click(function() {
			if (self.isRicercaEffettuata == false) {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "E' necessario effettuare una ricerca prima di poter esportare",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				var nome = $('#nomeIntestazioniPF').val().trim();
				var cognome = $('#cognomeIntestazioniPF').val().trim();
				var codiceFiscale = $('#codiceFiscaleIntestazioniPF').val().trim();
				var checkboxUiuPf;
				if ($('#checkboxUiuPf').prop('checked')){
					checkboxUiuPf = true;
				} else {
					checkboxUiuPf = false;
				}
				var checkboxPtPf;
				if ($('#checkboxPtPf').prop('checked')){
					checkboxPtPf = true;
				} else {
					checkboxPtPf = false;
				}
				ricercheSoggettiRest.exportIntestazioniPF(nome, cognome, codiceFiscale, checkboxUiuPf, checkboxPtPf).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/msword;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "export.xls";
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
			}
		})

		$( "#effettuaVisuraBtntestazioniPF" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var tipo = sessionStorage.getItem('tipo');
			if (tipo === 'PT') {
				var idImmobile = $('#idImmobilePtPF').val();
				ricercheSoggettiRest.exportVisuraCatastaleTerreni(idImmobile).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "visura_catastale.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						downloadLink.click();
					} else {
						appUtil.hideLoader();
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
			} else {
				var idImmobile = $('#idImmobileUiuPF').val();
				ricercheSoggettiRest.exportVisuraCatastaleFabbricati(idImmobile).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "visura_catastale.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						downloadLink.click();
					} else {
						appUtil.hideLoader();
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
			}
		});

		$( "#effettuaVisuraStoricaBtnIntestazioniPF" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var tipo = sessionStorage.getItem('tipo');
			if (tipo === 'PT') {
				var idImmobile = $('#idImmobilePtPF').val();
				ricercheSoggettiRest.exportVisuraCatastaleStoricaTerreni(idImmobile).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "visura_catastale_storica.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						downloadLink.click();
					} else {
						appUtil.hideLoader();
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
			} else {
				var idImmobile = $('#idImmobileUiuPF').val();
				ricercheSoggettiRest.exportVisuraCatastaleStoricaFabbricati(idImmobile).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "visura_catastale_storica.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						downloadLink.click();
					} else {
						appUtil.hideLoader();
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
			}
		});

		$.when(
	    	ricercheSoggettiRest.getDataUltimoAggiornamento()
		).done(function(dataAggiornamento){
			$( "#dataAggiornamento" ).append('Data ultimo aggiornamento dati catastali: ' + dataAggiornamento.aaData);
		});

	}

	inizializzaDatatableRicerca(){
		var self = this;
		var nome = $('#nomeIntestazioniPF').val().trim();
		var cognome = $('#cognomeIntestazioniPF').val().trim();
		var codiceFiscale = $('#codiceFiscaleIntestazioniPF').val().trim();
		var checkboxUiuPf;
		if ($('#checkboxUiuPf').prop('checked')){
			checkboxUiuPf = true;
		} else {
			checkboxUiuPf = false;
		}
		var checkboxPtPf;
		if ($('#checkboxPtPf').prop('checked')){
			checkboxPtPf = true;
		} else {
			checkboxPtPf = false;
		}
		var url = ricercheSoggettiRest.ricercaIntestazioniPersoneFisiche(nome, cognome, codiceFiscale, checkboxUiuPf, checkboxPtPf);
		$('#'+self.idDialog+' #tabellaRisultatiIntestazioniPF').DataTable( {
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
	        ajax: url,
	        order: [],
	        columnDefs: [
	        	{targets :  0, render: function(d, t, r) {return r.cognome;}, orderable : true},
	        	{targets :  1, render: function(d, t, r) {return r.nome;}, orderable : true},
	        	{targets :  2, render: function(d, t, r) {return r.codiceFiscale;}, orderable : true},
	        	{targets :  3, render: function(d, t, r) {return r.idSoggetto;}, orderable : true},
	        	{targets :  4, render: function(d, t, r) {return r.tipo;}, orderable : true},
	        	{targets :  5, render: function(d, t, r) {return r.sezione;}, orderable : true},
	        	{targets :  6, render: function(d, t, r) {return r.foglio;}, orderable : true},
	        	{targets :  7, render: function(d, t, r) {return r.numero;}, orderable : true},
	        	{targets :  8, render: function(d, t, r) {return r.subalterno;}, orderable : true},
	        	{targets :  9, render: function(d, t, r) {return r.codComune;}, orderable : true},
	        	{targets : 	10, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.codComune);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
	        	{targets : 11, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.codComune);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true},
	        	{targets : 12, className: "text-right", orderable : false, render: function(d, t, r){
	        		return '<button type="button" data-id="'+r.idImmobile+'" data-info="Vai al dettaglio" class="btn-trasp bttn vai-dettaglio-btn"><em class="fa fa-arrow-right"></em></button>';
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	// MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('id'));
	            });
	            $('#tabellaRisultatiIntestazioniPF').show();
				$( "#dataAggiornamento" ).show();
	            appUtil.hideLoader();
	            sessionStorage.setItem('soggetti', JSON.stringify(settings.json.data));
	            self.isRicercaEffettuata = true;
	        }
	    });
	}
	
	// PT
	inizializzaDatatablePorzione(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPorzionePtIntestazioniPf').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'classe' },
		        { data: 'ettari' },
		        { data: 'are' },
		        { data: 'centiare' },
		        { data: 'qualita' }
		    ]
		});
	}
	inizializzaDatatablePersoneFisiche(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPersoneFisichePtIntestazioniPf').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}
	inizializzaDatatableSoggettiGiuridici(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaSoggettiGiuridiciPtIntestazioniPf').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}
	inizializzaDatatableRiserva(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaRiservaPtIntestazioniPf').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'riserva' },
		        { data: 'partitaIscrizione' },
		    ]
		});
	}
	inizializzaDatatableIdentificativiUIU(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaIdentificativiUIUPtIntestazioniPf').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			columns: [
		        { data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        {targets : 4, render: function(d, t, r) {
		        	var partita = sessionStorage.getItem('partita')
		        	if (partita.length > 0) {
		        		return partita;
		        	} else {
		        		return 'n.d.';
		        	}
		        }, orderable : true},
		        { orderable : false, className: 'text-center', render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaIdentificativiUIUPtIntestazioniPf button.vai-alla-mappa').off('click').on('click', function () {
	            	let WKT = "";
	            	var clickedId = this.id
	            	$.each( dati, function( keyParticella, valueParticella ) {
	            		if ( valueParticella.id == clickedId) {
	            			WKT = valueParticella.geometry;
	            		}
	            	});
	            	if (WKT!="" && WKT != null) {
	    	        	drawLayerSource.clear();
	    	        	
	    	        	var format = new ol.format.WKT();
	    	        	var feature = format.readFeature(WKT, {
	    	        	  dataProjection: 'EPSG:4326',
	    	        	  featureProjection: 'EPSG:3857'
	    	        	});
	    	        	drawLayerSource.addFeature( feature );
	    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
	    		    	(appMappa.maps).getView().setZoom(18);
	    		    	self.riduciAdIconaTutteLeModali();
	            	} else {
	            		iziToast.show({
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
						    title: 'Attenzione',
						    message: "Non e' stata trovata nessuna geometria dal DB",
						});
	            	}
	            });

	        	appUtil.hideLoader();
	        }
		});
	}
	inizializzaDatatableDeduzione(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaDeduzionePtIntestazioniPf').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'simboloDeduzione' },
		    ]
		});
	}
	
	// UIU
	inizializzaDatatableIndirizzi2(indirizzi){
		var self = this;
		$('#'+self.idDialog+' #tabellaIndirizziUiuIntestazioniPf').DataTable({
			language: self.datatableLan,
			data: indirizzi,
			destroy: true,
			columns: [
		        { data: 'indirizzo' },
		        { data: 'civico1' },
		        { data: 'civico2' },
		        { data: 'civico3' },
		    ]
		});
	}
	inizializzaDatatableIdentificativiUIU2(data){
		var self = this;
		$('#'+self.idDialog+' #tabellaIdentificativiUiuIntestazioniPf').DataTable({
			language: self.datatableLan,
			data: data,
			destroy: true,
			columns: [
				{ data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        { orderable : false, render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaIdentificativiUiuIntestazioniPf button.vai-alla-mappa').off('click').on('click', function () {
	            	let WKT = "";
	            	var clickedId = this.id
	            	$.each( data, function( keyParticella, valueParticella ) {
	            		if ( valueParticella.id == clickedId) {
	            			WKT = valueParticella.geometry;
	            		}
	            	});
	            	if (WKT!="" && WKT != null) {
	    	        	drawLayerSource.clear();
	    	        	
	    	        	var format = new ol.format.WKT();
	    	        	var feature = format.readFeature(WKT, {
	    	        	  dataProjection: 'EPSG:4326',
	    	        	  featureProjection: 'EPSG:3857'
	    	        	});
	    	        	drawLayerSource.addFeature( feature );
	    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
	    		    	(appMappa.maps).getView().setZoom(18);
	    		    	self.riduciAdIconaTutteLeModali();
	            	} else {
	            		iziToast.show({
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
						    title: 'Attenzione',
						    message: "Non e' stata trovata nessuna geometria dal DB",
						});
	            	}
	            });

	        	appUtil.hideLoader();
	        }
		});
	}
	inizializzaDatatablePersoneFisiche2(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPersoneFisicheUiuIntestazioniPf').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}
	inizializzaDatatableSoggettiGiuridici2(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaSoggettiGiuridiciUiuIntestazioniPf').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ],
	        drawCallback: function( settings ) {
	        	appUtil.hideLoader();
	        }
		});
	}
	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			let dettaglio = self.recuperaDettaglio(id);
			let tipo = dettaglio.tipo;
			if (tipo === 'PT') {
				$.when(
					ricercheSoggettiRest.informazioniParticellaByIdImmobile(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioParticellePTPersoneFisiche(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioParticellePTSoggettiGiuridici(dettaglio.idImmobile),
					ricercheSoggettiRest.dettagliParticelleUIUIdentificativi(dettaglio.idImmobile),
					ricercheSoggettiRest.dettagliParticellePTMultiplo(dettaglio.idImmobile)
				).done(function(infoPT, personeFisiche, soggettiGiuridici, identificativiUIU, dettaglioMultiplo){
					$('#idImmobilePtPF').val(dettaglio.idImmobile);
					sessionStorage.setItem('tipo', tipo);
					let comune = $.trim(infoPT[0].aaData.comune);
					let foglio = $.trim(infoPT[0].aaData.foglio);
					let numero = $.trim(infoPT[0].aaData.numero);
					let denominatore = $.trim(infoPT[0].aaData.denominatore);
					let subalterno = $.trim(infoPT[0].aaData.sub);
					let partita = $.trim(infoPT[0].aaData.partita);
					sessionStorage.setItem('partita', partita);
					var qualita = lsCodiciQualita.find(x => x.codice == $.trim(infoPT[0].aaData.qualita));
					let classe = $.trim(infoPT[0].aaData.classe);
					let ettari = $.trim(infoPT[0].aaData.ettari);
					let are = $.trim(infoPT[0].aaData.are);
					let centiare = $.trim(infoPT[0].aaData.centiare);
					let redditoAgrarioEuro = $.trim(infoPT[0].aaData.redditoAgrarioEuro);
					let redditoAgrarioLire = $.trim(infoPT[0].aaData.redditoAgrarioLire);
					let redditoDominicaleEuro = $.trim(infoPT[0].aaData.redditoDominicaleEuro);
					let redditoDominicaleLire = $.trim(infoPT[0].aaData.redditoDominicaleLire);
					let annotazioni = $.trim(infoPT[0].aaData.annotazioni);
					$('#comuneDettaglioPtIntestazioniPf').val( comune.length > 0 ? comune : 'n.d.');
					$('#foglioDettaglioPtIntestazioniPf').val( foglio.length > 0 ? foglio : 'n.d.');
					$('#numeroDettaglioPtIntestazioniPf').val( numero.length > 0 ? numero : 'n.d.');
					$('#denominatoreDettaglioPtIntestazioniPf').val( denominatore.length > 0 ? denominatore : 'n.d.');
					$('#subalternoDettaglioPtIntestazioniPf').val( subalterno.length > 0 ? subalterno : 'n.d.');
					$('#partitaDettaglioPtIntestazioniPf').val( partita.length > 0 ? partita : 'n.d.');
					$('#qualitaDettaglioPtIntestazioniPf').val( qualita.descrizione.length > 0 ? qualita.descrizione : 'n.d.');
					$('#classeDettaglioPtIntestazioniPf').val( classe.length > 0 ? classe : 'n.d.');
					$('#ettariDettaglioPtIntestazioniPf').val( ettari.length > 0 ? ettari : 'n.d.');
					$('#areDettaglioPtIntestazioniPf').val( are.length > 0 ? are : 'n.d.');
					$('#centiareDettaglioPtIntestazioniPf').val( centiare.length > 0 ? centiare : 'n.d.');
					$('#agrarioEuroDettaglioPtIntestazioniPf').val( redditoAgrarioEuro.length > 0 ? redditoAgrarioEuro : 'n.d.');
					$('#agrarioLireDettaglioPtIntestazioniPf').val( redditoAgrarioLire.length > 0 ? redditoAgrarioLire : 'n.d.');
					$('#dominicaleEuroDettaglioPtIntestazioniPf').val( redditoDominicaleEuro.length > 0 ? redditoDominicaleEuro : 'n.d.');
					$('#dominicaleLireDettaglioPtIntestazioniPf').val( redditoDominicaleLire.length > 0 ? redditoDominicaleLire : 'n.d.');
					$('#annotazioneDettaglioPtIntestazioniPf').val( annotazioni.length > 0 ? annotazioni : 'n.d.');
					self.inizializzaDatatablePersoneFisiche(personeFisiche[0].aaData);
					self.inizializzaDatatableSoggettiGiuridici(soggettiGiuridici[0].aaData);
					self.inizializzaDatatableIdentificativiUIU(identificativiUIU[0].aaData);
					self.inizializzaDatatableDeduzione(dettaglioMultiplo[0].aaData.listDeduzione);
					self.inizializzaDatatablePorzione(dettaglioMultiplo[0].aaData.listPorzione);
					self.inizializzaDatatableRiserva(dettaglioMultiplo[0].aaData.listRiserva);
				});
			} else {
				$.when(
					ricercheSoggettiRest.informazioniUnitaImmobiliareByIdImmobile(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioImmobiliUIUIdentificativi(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioImmobiliUIUPersoneFisiche(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioImmobiliUIUSoggettiGiuridici(dettaglio.idImmobile)
				).done(function(infoUIU, identificativiUIU, personeFisiche, soggettiGiuridici, geometria){
					$('#idImmobileUiuPF').val(dettaglio.idImmobile);
					sessionStorage.setItem('tipo', tipo);
					let comuneDettaglio = $.trim(infoUIU[0].aaData.comune);
					let identificativoDettaglio = 'Foglio: ' + infoUIU[0].aaData.foglio + ' Numero: ' + infoUIU[0].aaData.numero + ' Subalterno: ' + infoUIU[0].aaData.subalterno;
					let zonaDettaglio = $.trim(infoUIU[0].aaData.zona);
					let categoriaDettaglio = $.trim(infoUIU[0].aaData.categoria);
					let classeDettaglio = $.trim(infoUIU[0].aaData.classe);
					let partitaDettaglio = $.trim(infoUIU[0].aaData.partita);
					let superficieDettaglio = $.trim(infoUIU[0].aaData.superficie);
					let consistenzaDettaglio = $.trim(infoUIU[0].aaData.consistenza);
					let renditaEuroDettaglio = $.trim(infoUIU[0].aaData.renditaEuroStr);
					let renditaLireDettaglio = $.trim(infoUIU[0].aaData.renditaLireStr);
					
					let lottoDettaglio = $.trim(infoUIU[0].aaData.lotto);
					let edificioDettaglio = $.trim(infoUIU[0].aaData.edificio);
					let scalaDettaglio = $.trim(infoUIU[0].aaData.scala);
					let internoDettaglio = $.trim(infoUIU[0].aaData.interno);
					let pianoDettaglio = $.trim(infoUIU[0].aaData.piano);
					let annotazioneDettaglio = $.trim(infoUIU[0].aaData.annotazione);

					$('#comuneDettaglioUiuIntestazioniPf').val( comuneDettaglio.length > 0 ? comuneDettaglio : 'n.d.');
					$('#identificativoDettaglioUiuIntestazioniPf').val( identificativoDettaglio.length > 0 ? identificativoDettaglio : 'n.d.');
					$('#zonaDettaglioUiuIntestazioniPf').val( zonaDettaglio.length > 0 ? zonaDettaglio : 'n.d.');
					$('#categoriaDettaglioUiuIntestazioniPf').val( categoriaDettaglio.length > 0 ? categoriaDettaglio : 'n.d.');
					$('#classeDettaglioUiuIntestazioniPf').val( classeDettaglio.length > 0 ? classeDettaglio : 'n.d.');
					$('#partitaDettaglioUiuIntestazioniPf').val( partitaDettaglio.length > 0 ? partitaDettaglio : 'n.d.');
					$('#superficieDettaglioUiuIntestazioniPf').val( superficieDettaglio.length > 0 ? superficieDettaglio : 'n.d.');
					$('#consistenzaDettaglioUiuIntestazioniPf').val( consistenzaDettaglio.length > 0 ? consistenzaDettaglio : 'n.d.');
					$('#renditaEuroDettaglioUiuIntestazioniPf').val( renditaEuroDettaglio.length > 0 ? renditaEuroDettaglio : 'n.d.');
					$('#renditaLireDettaglioUiuIntestazioniPf').val( renditaLireDettaglio.length > 0 ? renditaLireDettaglio : 'n.d.');
					
					$('#lottoDettaglioUiuIntestazioniPf').val( lottoDettaglio.length > 0 ? lottoDettaglio : 'n.d.');
					$('#edificioDettaglioUiuIntestazioniPf').val( edificioDettaglio.length > 0 ? edificioDettaglio : 'n.d.');
					$('#scalaDettaglioUiuIntestazioniPf').val( scalaDettaglio.length > 0 ? scalaDettaglio : 'n.d.');
					$('#internoDettaglioUiuIntestazioniPf').val( internoDettaglio.length > 0 ? internoDettaglio : 'n.d.');
					$('#pianoDettaglioUiuIntestazioniPf').val( pianoDettaglio.length > 0 ? pianoDettaglio : 'n.d.');
					$('#annotazioneDettaglioUiuIntestazioniPf').val( annotazioneDettaglio.length > 0 ? annotazioneDettaglio : 'n.d.');
					
					var indirizzi = [
						{indirizzo: infoUIU[0].aaData.indirizzo, civico1: infoUIU[0].aaData.civico1, civico2: infoUIU[0].aaData.civico2, civico3: infoUIU[0].aaData.civico3}
					]
					
					self.inizializzaDatatableIndirizzi2(indirizzi);
					self.inizializzaDatatableIdentificativiUIU2(identificativiUIU[0].aaData);
					self.inizializzaDatatablePersoneFisiche2(personeFisiche[0].aaData);
					self.inizializzaDatatableSoggettiGiuridici2(soggettiGiuridici[0].aaData);
				});
			}
			self.nascondiPaginaRicercaIntestazioni(tipo);
			// aggiorno il breadcrumb
			self.aggiornaBreadcrumb('dettaglio', ''/* dettaglio.idImmobile */);
		}, 1000);
	}
	recuperaDettaglio(id){
		let soggetti = JSON.parse(sessionStorage.getItem('soggetti'));
		let dettaglio = {};
		for(var i=0; i<soggetti.length; i++){
			if(soggetti[i].idImmobile === id){
				dettaglio = soggetti[i];
				break;
			}
		}
		return dettaglio;
	}

	nascondiPaginaRicercaIntestazioni(tipo){
		// nascondo la ricerca
		$('#'+this.idDialog+' #ricerca').addClass('hidden');
		$('#'+this.idDialog+' #dettaglio').removeClass('hidden');

		$('#'+this.idDialog+' #anagrafica-tab5-uiu').removeClass('active');
		$('#'+this.idDialog+' #indirizzi-tab5-uiu').removeClass('active');
		$('#'+this.idDialog+' #identificativi-tab5-uiu').removeClass('active');
		$('#'+this.idDialog+' #persone-tab5-uiu').removeClass('active');
		$('#'+this.idDialog+' #soggetti-tab5-uiu').removeClass('active');
		$('#'+this.idDialog+' #calcolo-tab5-uiu').removeClass('active');

		$('#'+this.idDialog+' #anagrafica-tab5-pt').removeClass('active');
		$('#'+this.idDialog+' #porzione-tab5-pt').removeClass('active');
		$('#'+this.idDialog+' #deduzione-tab5-pt').removeClass('active');
		$('#'+this.idDialog+' #riserva-tab5-pt').removeClass('active');
		$('#'+this.idDialog+' #persone-tab5-pt').removeClass('active');
		$('#'+this.idDialog+' #soggetti-tab5-pt').removeClass('active');
		$('#'+this.idDialog+' #calcolo-tab5-pt').removeClass('active');

		$('#'+this.idDialog+' #anagraficaTabContent3-uiu').removeClass('active');
		$('#'+this.idDialog+' #indirizziTabContent3-uiu').removeClass('active');
		$('#'+this.idDialog+' #identificativiTabContent3-uiu').removeClass('active');
		$('#'+this.idDialog+' #personeTabContent3-uiu').removeClass('active');
		$('#'+this.idDialog+' #soggettiTabContent3-uiu').removeClass('active');
		$('#'+this.idDialog+' #calcoloTabContent3-uiu').removeClass('active');

		$('#'+this.idDialog+' #anagraficaTabContent5-pt').removeClass('active');
		$('#'+this.idDialog+' #porzioneTabContent5-pt').removeClass('active');
		$('#'+this.idDialog+' #deduzioneTabContent5-pt').removeClass('active');
		$('#'+this.idDialog+' #riservaTabContent5-pt').removeClass('active');
		$('#'+this.idDialog+' #personeTabContent5-pt').removeClass('active');
		$('#'+this.idDialog+' #soggettiTabContent5-pt').removeClass('active');

		if(tipo === 'PT') {
			$('#'+this.idDialog+' #dettaglioTabPt').removeClass('hidden');
			$('#'+this.idDialog+' #dettaglioTabUiu').addClass('hidden');
			
			$('#'+this.idDialog+' #anagrafica-tab5-pt').addClass('active');
			$('#'+this.idDialog+' #anagraficaTabContent5-pt').addClass('active');
		} else {
			$('#'+this.idDialog+' #dettaglioTabPt').addClass('hidden');
			$('#'+this.idDialog+' #dettaglioTabUiu').removeClass('hidden');
			
			$('#'+this.idDialog+' #anagrafica-tab5-uiu').addClass('active');
			$('#'+this.idDialog+' #anagraficaTabContent3-uiu').addClass('active');
		}
		// mostro i pulsanti corretti
		$('#'+this.idDialog+' .pulsante-ricerca').each(function(){
			$(this).children('li').addClass('hidden');
		});
		$('#'+this.idDialog+' .pulsante-dettaglio').each(function(){
			$(this).children('li').removeClass('hidden');
		});
	}

}

class PaginaRicercaIntestazioniSoggettiGiuridiciCtrl extends BaseModaleRicercaCtrl{
	constructor( ) {
		super('paginaRicercaIntestazioniSoggettiGiuridici','RICERCA INTESTAZIONI SOGGETTI GIURIDICI', 'ricIntestazioniSoggettiGiuridici');
		drawLayerSource.clear();
		this.isRicercaEffettuata = false;
		this.lsCategorieCatastali = [];
		this.lsCodiciDiritto = [];
		this.lsCodiciQualita = [];
		this.lsComuni = [];
		this.lsProvince = [];
		$.when(
			catastoRest.popolaLsCategorieCatastali(),
			catastoRest.popolaLsCodiciDiritto(),
			catastoRest.popolaLsCodiciQualita(),
			ricercheSoggettiRest.getComuni(),
			ricercheSoggettiRest.getProvince()
		).done(function(categorieCatastali, codiciDiritto, codiciQualita, comuni, province){
			self.lsCategorieCatastali = categorieCatastali[0].aaData;
			self.lsCodiciDiritto = codiciDiritto[0].aaData;
			self.lsCodiciQualita = codiciQualita[0].aaData;
			self.lsComuni = comuni[0].aaData;
			self.lsProvince = province[0].aaData;
		});
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			self.inizializzaBreadcrumb();
			self.aggiungiEventiClickTornaIndietroARicerca();
			self.aggiungiEventoClickChiudiFinestra();
			self.aggiungiEventoClickDownloadFileVisura();
			self.nascondiPaginaDettaglio();
			appUtil.reloadTooltips();
		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
		$('#tabellaRisultatiIntestazioniSG').hide();
		$( "#dataAggiornamento" ).hide();

		var dropdownProvince = $("#provinciaNascitaISG");
		var dropdownComuni = $("#comuneNascitaISG");

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(
			ricercheSoggettiRest.getProvince()
		).done(function(province){
			province = province.aaData;
			$.each(province, function(key, value) {
				dropdownProvince.append($("<option />").val(value.codice).text(value.descrizione));
			});
			appUtil.hideLoader();
		});

		$('#provinciaNascitaISG').change(function() {
			if ($(this).val() !== '') {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				$('#comuneNascitaISG').empty().append('<option value="" selected>Seleziona...</option>')
				$.when(
					ricercheSoggettiRest.getComuniByProvincia($(this).val())
				).done(function(comuni){
					comuni = comuni.aaData;
					$.each(comuni, function(key, value) {
						dropdownComuni.append($("<option />").val(value.codice).text(value.descrizione));
					});
					appUtil.hideLoader();
				});
			}
		});

		$( "#eseguiBtnIntestazioniSG" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			self.inizializzaDatatableRicerca();
		});
	
		$( "#azzeraBtnIntestazioniSG" ).click(function() {
			$('#denominazioneSG').val('');
			$('#provinciaNascitaSG').val('');
			$('#comuneNascitaSG').val('');
			$('#codiceFiscaleSG').val('');
			$('#checkboxUiuSg').prop( "checked", false );
			$('#checkboxPtSg').prop( "checked", false );
			$("#provinciaNascitaISG").val($("#provinciaNascita option:first").val());
			$('#comuneNascitaISG').empty().append('<option value="" selected>Seleziona...</option>')
		});

		$( "#esportaDettaglioBtnIntestazioniSG" ).click(function() {
			if (self.isRicercaEffettuata == false) {
				iziToast.show({
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'Attenzione',
				    message: "E' necessario effettuare una ricerca prima di poter esportare",
				});
			} else {
				appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
				var denominazione = $('#denominazioneSG').val().trim();
				var provincia = $('#provinciaNascitaISG').val();
				var comune = $('#comuneNascitaISG').val();
				var codiceFiscale = $('#codiceFiscaleSG').val().trim();
				var checkboxUiuSg;
				if ($('#checkboxUiuSg').prop('checked')){
					checkboxUiuSg = true;
				} else {
					checkboxUiuSg = false;
				}
				var checkboxPtSg;
				if ($('#checkboxPtSg').prop('checked')){
					checkboxPtSg = true;
				} else {
					checkboxPtSg = false;
				}
				ricercheSoggettiRest.exportIntestazioniSG(denominazione, provincia, comune, codiceFiscale, checkboxUiuSg, checkboxPtSg).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/msword;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "export.xls";
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
			}
		});

		$( "#effettuaVisuraBtnIntestazioniSG" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var tipo = sessionStorage.getItem('tipo');
			if (tipo === 'PT') {
				var idImmobile = $('#idImmobilePtSG').val();
				ricercheSoggettiRest.exportVisuraCatastaleTerreni(idImmobile).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "visura_catastale.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						downloadLink.click();
					} else {
						appUtil.hideLoader();
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
			} else {
				var idImmobile = $('#idImmobileUiuSG').val();
				ricercheSoggettiRest.exportVisuraCatastaleFabbricati(idImmobile).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "visura_catastale.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						downloadLink.click();
					} else {
						appUtil.hideLoader();
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
			}
		});

		$( "#effettuaVisuraStoricaBtnIntestazioniSG" ).click(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			var tipo = sessionStorage.getItem('tipo');
			if (tipo === 'PT') {
				var idImmobile = $('#idImmobilePtSG').val();
				ricercheSoggettiRest.exportVisuraCatastaleStoricaTerreni(idImmobile).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "visura_catastale_storica.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						downloadLink.click();
					} else {
						appUtil.hideLoader();
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
			} else {
				var idImmobile = $('#idImmobileUiuSG').val();
				ricercheSoggettiRest.exportVisuraCatastaleStoricaFabbricati(idImmobile).then(function(response){
					appUtil.hideLoader();
					if (response !== null || response !== undefined) {
						const linkSource = 'data:application/pdf;base64,'+response;
						const downloadLink = document.createElement('a');
						const fileName = "visura_catastale_storica.pdf";
						downloadLink.href = linkSource;
						downloadLink.download = fileName;
						downloadLink.click();
					} else {
						appUtil.hideLoader();
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
			}
		});

		$.when(
	    	ricercheSoggettiRest.getDataUltimoAggiornamento()
		).done(function(dataAggiornamento){
			$( "#dataAggiornamento" ).append('Data ultimo aggiornamento dati catastali: ' + dataAggiornamento.aaData);
		});

	}

	inizializzaDatatableRicerca(){
		var self = this;
		var denominazione = $('#denominazioneSG').val().trim();
		var provincia = $('#provinciaNascitaISG').val();
		var comune = $('#comuneNascitaISG').val();
		var codiceFiscale = $('#codiceFiscaleSG').val().trim();
		var checkboxUiuSg;
		if ($('#checkboxUiuSg').prop('checked')){
			checkboxUiuSg = true;
		} else {
			checkboxUiuSg = false;
		}
		var checkboxPtSg;
		if ($('#checkboxPtSg').prop('checked')){
			checkboxPtSg = true;
		} else {
			checkboxPtSg = false;
		}
		var url = ricercheSoggettiRest.ricercaIntestazioniSoggettiGiuridici(denominazione, provincia, comune, codiceFiscale, checkboxUiuSg, checkboxPtSg);
		$('#'+self.idDialog+' #tabellaRisultatiIntestazioniSG').DataTable( {
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        serverSide: true,
	        processing: true,
	        ajax: url,
	        order: [],
	        columnDefs: [
	        	{targets :  0, render: function(d, t, r) {return r.denominazione;}, orderable : true},
	        	{targets :  1, render: function(d, t, r) {return r.codiceFiscale;}, orderable : true},
	        	{targets :  2, render: function(d, t, r) {return r.idSoggetto;}, orderable : true},
	        	{targets :  3, render: function(d, t, r) {return r.tipo;}, orderable : true},
	        	{targets :  4, render: function(d, t, r) {return r.sezione;}, orderable : true},
	        	{targets :  5, render: function(d, t, r) {return r.foglio;}, orderable : true},
	        	{targets :  6, render: function(d, t, r) {return r.numero;}, orderable : true},
	        	{targets :  7, render: function(d, t, r) {return r.subalterno;}, orderable : true},
	        	{targets :  8, render: function(d, t, r) {return r.codComune;}, orderable : true},
	        	{targets : 	9, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.codComune);
        		if (luogoNascita != undefined) {
        			return luogoNascita.descrizione
        		} else {
        			return 'N/D';
        		}}, orderable : true}, // Comune
	        	{targets : 10, render: function(d, t, r) {var luogoNascita = lsComuni.find(x => x.codice == r.codComune);
        		if (luogoNascita != undefined) {
        			var provinciaNascita = lsProvince.find(e => e.codice == luogoNascita.informazione);
        			return provinciaNascita.descrizione;
        		} else {
        			return 'N/D';
        		}}, orderable : true},
	        	{targets : 11, render: function(d, t, r) {return r.ute;}, orderable : true},
	        	{targets : 12, className: "text-right", orderable : false, render: function(d, t, r){
	        		return '<button type="button" data-id="'+r.idImmobile+'" data-info="Vai al dettaglio" class="btn-trasp bttn vai-dettaglio-btn"><em class="fa fa-arrow-right"></em></button>';
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	// MA PERCHE' HAN MESSO DAL CSS CHE NON SONO VISIBILI?!
	        	$('#'+self.idDialog+' .dataTables_paginate ').show();
	        	$('#'+self.idDialog+' .dataTables_info').show();
	            $('#'+self.idDialog+' .vai-dettaglio-btn').off('click').on('click', function(event){
	            	self.vaiAlDettaglio($(this).data('id'));
	            });
	            $('#tabellaRisultatiIntestazioniSG').show();
				$( "#dataAggiornamento" ).show();
	            appUtil.hideLoader();
	            sessionStorage.setItem('soggetti', JSON.stringify(settings.json.data));
	            self.isRicercaEffettuata = true;
	        }
	    });
	}

	// PT
	inizializzaDatatablePorzione(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPorzionePtIntestazioniSg').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'classe' },
		        { data: 'ettari' },
		        { data: 'are' },
		        { data: 'centiare' },
		        { data: 'qualita' }
		    ]
		});
	}

	inizializzaDatatablePersoneFisiche(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPersoneFisichePtIntestazioniSg').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}

	inizializzaDatatableSoggettiGiuridici(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaSoggettiGiuridiciPtIntestazioniSg').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}

	inizializzaDatatableRiserva(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaRiservaPtIntestazioniSg').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'riserva' },
		        { data: 'partita' },
		    ]
		});
	}

	inizializzaDatatableIdentificativiUIU(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaIdentificativiUIUPtIntestazioniSg').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			columns: [
		        { data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        {targets : 4, render: function(d, t, r) {
		        	var partita = sessionStorage.getItem('partita')
		        	if (partita.length > 0) {
		        		return partita;
		        	} else {
		        		return 'n.d.';
		        	}
		        }, orderable : true},
		        { orderable : false, className: 'text-center', render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaIdentificativiUIUPtIntestazioniSg button.vai-alla-mappa').off('click').on('click', function () {
	            	let WKT = "";
	            	var clickedId = this.id
	            	$.each( dati, function( keyParticella, valueParticella ) {
	            		if ( valueParticella.id == clickedId) {
	            			WKT = valueParticella.geometry;
	            		}
	            	});
	            	if (WKT!="" && WKT != null) {
	    	        	drawLayerSource.clear();
	    	        	
	    	        	var format = new ol.format.WKT();
	    	        	var feature = format.readFeature(WKT, {
	    	        	  dataProjection: 'EPSG:4326',
	    	        	  featureProjection: 'EPSG:3857'
	    	        	});
	    	        	drawLayerSource.addFeature( feature );
	    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
	    		    	(appMappa.maps).getView().setZoom(18);
	    		    	self.riduciAdIconaTutteLeModali();
	            	} else {
	            		iziToast.show({
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
						    title: 'Attenzione',
						    message: "Non e' stata trovata nessuna geometria dal DB",
						});
	            	}
	            });

	        	appUtil.hideLoader();
	        }
		});
	}

	inizializzaDatatableDeduzione(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaDeduzionePtIntestazioniSg').DataTable({
			data: dati,
			destroy: true,
			language: self.datatableLan,
			columns: [
		        { data: 'simboloDeduzione' },
		    ]
		});
	}
	
	// UIU
	inizializzaDatatableIndirizzi2(indirizzi){
		var self = this;
		$('#'+self.idDialog+' #tabellaIndirizziUiuIntestazioniSg').DataTable({
			language: self.datatableLan,
			data: indirizzi,
			destroy: true,
			columns: [
		        { data: 'indirizzo' },
		        { data: 'civico1' },
		        { data: 'civico2' },
		        { data: 'civico3' },
		    ]
		});
	}

	inizializzaDatatableIdentificativiUIU2(data){
		var self = this;
		$('#'+self.idDialog+' #tabellaIdentificativiUiuIntestazioniSg').DataTable({
			language: self.datatableLan,
			data: data,
			destroy: true,
			columns: [
				{ data: 'sezione' },
		        { data: 'foglio' },
		        { data: 'numero' },
		        { data: 'subalterno' },
		        { orderable : false, render: function(d, t, r){
		        	return '<button type="button" id="' + r.id + '" data-info="Vai alla mappa" class="btn-trasp bttn vai-alla-mappa"><em class="fa fa-map-marker"></em></button>';
		        }}
		    ],
		    drawCallback: function( settings ) {
		    	$('#'+self.idDialog+' #tabellaIdentificativiUiuIntestazioniSg button.vai-alla-mappa').off('click').on('click', function () {
	            	let WKT = "";
	            	var clickedId = this.id
	            	$.each( data, function( keyParticella, valueParticella ) {
	            		if ( valueParticella.id == clickedId) {
	            			WKT = valueParticella.geometry;
	            		}
	            	});
	            	if (WKT!="" && WKT != null) {
	    	        	drawLayerSource.clear();
	    	        	
	    	        	var format = new ol.format.WKT();
	    	        	var feature = format.readFeature(WKT, {
	    	        	  dataProjection: 'EPSG:4326',
	    	        	  featureProjection: 'EPSG:3857'
	    	        	});
	    	        	drawLayerSource.addFeature( feature );
	    	        	(appMappa.maps).getView().fit(feature.getGeometry(),{padding: [0, 0, 0, 0]});
	    		    	(appMappa.maps).getView().setZoom(18);
	    		    	self.riduciAdIconaTutteLeModali();
	            	} else {
	            		iziToast.show({
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
						    title: 'Attenzione',
						    message: "Non e' stata trovata nessuna geometria dal DB",
						});
	            	}
	            });

	        	appUtil.hideLoader();
	        }
		});
	}

	inizializzaDatatablePersoneFisiche2(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaPersoneFisicheUiuIntestazioniSg').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ]
		});
	}

	inizializzaDatatableSoggettiGiuridici2(dati){
		var self = this;
		$('#'+self.idDialog+' #tabellaSoggettiGiuridiciUiuIntestazioniSg').DataTable({
			language: self.datatableLan,
			data: dati,
			destroy: true,
			columns: [
		        { data: 'nominativo' },
		        { data: 'diritto' },
		        { data: 'dataEfficacia' },
		        { data: 'dataRegistrazione' },
		        { data: 'tipoAtto' },
		        { data: 'numeroAtto' },
		        { data: 'progressivoAtto' },
		        { data: 'annoAtto' },
		        { data: 'descrizioneAtto' }
		    ],
	        drawCallback: function( settings ) {
	        	appUtil.hideLoader();
	        }
		});
	}

	vaiAlDettaglio(id){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			let dettaglio = self.recuperaDettaglio(id);
			let tipo = dettaglio.tipo;
			if (tipo === 'PT') {
				$.when(
					ricercheSoggettiRest.informazioniParticellaByIdImmobile(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioParticellePTPersoneFisiche(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioParticellePTSoggettiGiuridici(dettaglio.idImmobile),
					ricercheSoggettiRest.dettagliParticelleUIUIdentificativi(dettaglio.idImmobile),
					ricercheSoggettiRest.dettagliParticellePTMultiplo(dettaglio.idImmobile)
				).done(function(infoPT, personeFisiche, soggettiGiuridici, identificativiUIU, dettaglioMultiplo){
					$('#idImmobilePtSG').val(dettaglio.idImmobile);
					sessionStorage.setItem('tipo', tipo);
					let comune = $.trim(infoPT[0].aaData.comune);
					let foglio = $.trim(infoPT[0].aaData.foglio);
					let numero = $.trim(infoPT[0].aaData.numero);
					let denominatore = $.trim(infoPT[0].aaData.denominatore);
					let subalterno = $.trim(infoPT[0].aaData.sub);
					let partita = $.trim(infoPT[0].aaData.partita);
					sessionStorage.setItem('partita', partita);
					var qualita = lsCodiciQualita.find(x => x.codice == $.trim(infoPT[0].aaData.qualita));
					let classe = $.trim(infoPT[0].aaData.classe);
					let ettari = $.trim(infoPT[0].aaData.ettari);
					let are = $.trim(infoPT[0].aaData.are);
					let centiare = $.trim(infoPT[0].aaData.centiare);
					let redditoAgrarioEuro = $.trim(infoPT[0].aaData.redditoAgrarioEuro);
					let redditoAgrarioLire = $.trim(infoPT[0].aaData.redditoAgrarioLire);
					let redditoDominicaleEuro = $.trim(infoPT[0].aaData.redditoDominicaleEuro);
					let redditoDominicaleLire = $.trim(infoPT[0].aaData.redditoDominicaleLire);
					let annotazioni = $.trim(infoPT[0].aaData.annotazioni);
					$('#comuneDettaglioPtIntestazioniSg').val( comune.length > 0 ? comune : 'n.d.');
					$('#foglioDettaglioPtIntestazioniSg').val( foglio.length > 0 ? foglio : 'n.d.');
					$('#numeroDettaglioPtIntestazioniSg').val( numero.length > 0 ? numero : 'n.d.');
					$('#denominatoreDettaglioPtIntestazioniSg').val( denominatore.length > 0 ? denominatore : 'n.d.');
					$('#subalternoDettaglioPtIntestazioniSg').val( subalterno.length > 0 ? subalterno : 'n.d.');
					$('#partitaDettaglioPtIntestazioniSg').val( partita.length > 0 ? partita : 'n.d.');
					$('#qualitaDettaglioPtIntestazioniSg').val( qualita.descrizione.length > 0 ? qualita.descrizione : 'n.d.');
					$('#classeDettaglioPtIntestazioniSg').val( classe.length > 0 ? classe : 'n.d.');
					$('#ettariDettaglioPtIntestazioniSg').val( ettari.length > 0 ? ettari : 'n.d.');
					$('#areDettaglioPtIntestazioniSg').val( are.length > 0 ? are : 'n.d.');
					$('#centiareDettaglioPtIntestazioniSg').val( centiare.length > 0 ? centiare : 'n.d.');
					$('#agrarioEuroDettaglioPtIntestazioniSg').val( redditoAgrarioEuro.length > 0 ? redditoAgrarioEuro : 'n.d.');
					$('#agrarioLireDettaglioPtIntestazioniSg').val( redditoAgrarioLire.length > 0 ? redditoAgrarioLire : 'n.d.');
					$('#dominicaleEuroDettaglioPtIntestazioniSg').val( redditoDominicaleEuro.length > 0 ? redditoDominicaleEuro : 'n.d.');
					$('#dominicaleLireDettaglioPtIntestazioniSg').val( redditoDominicaleLire.length > 0 ? redditoDominicaleLire : 'n.d.');
					$('#annotazioneDettaglioPtIntestazioniSg').val( annotazioni.length > 0 ? annotazioni : 'n.d.');
					self.inizializzaDatatablePersoneFisiche(personeFisiche[0].aaData);
					self.inizializzaDatatableSoggettiGiuridici(soggettiGiuridici[0].aaData);
					self.inizializzaDatatableIdentificativiUIU(identificativiUIU[0].aaData);
					self.inizializzaDatatableDeduzione(dettaglioMultiplo[0].aaData.listDeduzione);
					self.inizializzaDatatablePorzione(dettaglioMultiplo[0].aaData.listPorzione);
					self.inizializzaDatatableRiserva(dettaglioMultiplo[0].aaData.listRiserva);
				});
			} else {
				$.when(
					ricercheSoggettiRest.informazioniUnitaImmobiliareByIdImmobile(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioImmobiliUIUIdentificativi(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioImmobiliUIUPersoneFisiche(dettaglio.idImmobile),
					ricercheSoggettiRest.dettaglioImmobiliUIUSoggettiGiuridici(dettaglio.idImmobile)
				).done(function(infoUIU, identificativiUIU, personeFisiche, soggettiGiuridici, geometria){
					$('#idImmobileUiuSG').val(dettaglio.idImmobile);
					sessionStorage.setItem('tipo', tipo);
					let comuneDettaglio = $.trim(infoUIU[0].aaData.comune);
					let identificativoDettaglio = 'Foglio: ' + infoUIU[0].aaData.foglio + ' Numero: ' + infoUIU[0].aaData.numero + ' Subalterno: ' + infoUIU[0].aaData.subalterno;
					let zonaDettaglio = $.trim(infoUIU[0].aaData.zona);
					let categoriaDettaglio = $.trim(infoUIU[0].aaData.categoria);
					let classeDettaglio = $.trim(infoUIU[0].aaData.classe);
					let partitaDettaglio = $.trim(infoUIU[0].aaData.partita);
					let superficieDettaglio = $.trim(infoUIU[0].aaData.superficie);
					let consistenzaDettaglio = $.trim(infoUIU[0].aaData.consistenza);
					let renditaEuroDettaglio = $.trim(infoUIU[0].aaData.renditaEuroStr);
					let renditaLireDettaglio = $.trim(infoUIU[0].aaData.renditaLireStr);
					
					let lottoDettaglio = $.trim(infoUIU[0].aaData.lotto);
					let edificioDettaglio = $.trim(infoUIU[0].aaData.edificio);
					let scalaDettaglio = $.trim(infoUIU[0].aaData.scala);
					let internoDettaglio = $.trim(infoUIU[0].aaData.interno);
					let pianoDettaglio = $.trim(infoUIU[0].aaData.piano);
					let annotazioneDettaglio = $.trim(infoUIU[0].aaData.annotazione);

					$('#comuneDettaglioUiuIntestazioniSg').val( comuneDettaglio.length > 0 ? comuneDettaglio : 'n.d.');
					$('#identificativoDettaglioUiuIntestazioniSg').val( identificativoDettaglio.length > 0 ? identificativoDettaglio : 'n.d.');
					$('#zonaDettaglioUiuIntestazioniSg').val( zonaDettaglio.length > 0 ? zonaDettaglio : 'n.d.');
					$('#categoriaDettaglioUiuIntestazioniSg').val( categoriaDettaglio.length > 0 ? categoriaDettaglio : 'n.d.');
					$('#classeDettaglioUiuIntestazioniSg').val( classeDettaglio.length > 0 ? classeDettaglio : 'n.d.');
					$('#partitaDettaglioUiuIntestazioniSg').val( partitaDettaglio.length > 0 ? partitaDettaglio : 'n.d.');
					$('#superficieDettaglioUiuIntestazioniSg').val( superficieDettaglio.length > 0 ? superficieDettaglio : 'n.d.');
					$('#consistenzaDettaglioUiuIntestazioniSg').val( consistenzaDettaglio.length > 0 ? consistenzaDettaglio : 'n.d.');
					$('#renditaEuroDettaglioUiuIntestazioniSg').val( renditaEuroDettaglio.length > 0 ? renditaEuroDettaglio : 'n.d.');
					$('#renditaLireDettaglioUiuIntestazioniSg').val( renditaLireDettaglio.length > 0 ? renditaLireDettaglio : 'n.d.');
					
					$('#lottoDettaglioUiuIntestazioniSg').val( lottoDettaglio.length > 0 ? lottoDettaglio : 'n.d.');
					$('#edificioDettaglioUiuIntestazioniSg').val( edificioDettaglio.length > 0 ? edificioDettaglio : 'n.d.');
					$('#scalaDettaglioUiuIntestazioniSg').val( scalaDettaglio.length > 0 ? scalaDettaglio : 'n.d.');
					$('#internoDettaglioUiuIntestazioniSg').val( internoDettaglio.length > 0 ? internoDettaglio : 'n.d.');
					$('#pianoDettaglioUiuIntestazioniSg').val( pianoDettaglio.length > 0 ? pianoDettaglio : 'n.d.');
					$('#annotazioneDettaglioUiuIntestazioniSg').val( annotazioneDettaglio.length > 0 ? annotazioneDettaglio : 'n.d.');
					
					var indirizzi = [
						{indirizzo: infoUIU[0].aaData.indirizzo, civico1: infoUIU[0].aaData.civico1, civico2: infoUIU[0].aaData.civico2, civico3: infoUIU[0].aaData.civico3}
					]
					
					self.inizializzaDatatableIndirizzi2(indirizzi);
					self.inizializzaDatatableIdentificativiUIU2(identificativiUIU[0].aaData);
					self.inizializzaDatatablePersoneFisiche2(personeFisiche[0].aaData);
					self.inizializzaDatatableSoggettiGiuridici2(soggettiGiuridici[0].aaData);
				});
			}
			self.nascondiPaginaRicercaIntestazioni(tipo);
			// aggiorno il breadcrumb
			self.aggiornaBreadcrumb('dettaglio', '' /* dettaglio.idImmobile */);
		}, 1000);
	}
	recuperaDettaglio(id){
		let soggetti = JSON.parse(sessionStorage.getItem('soggetti'));
		let dettaglio = {};
		for(var i=0; i<soggetti.length; i++){
			if(soggetti[i].idImmobile === id){
				dettaglio = soggetti[i];
				break;
			}
		}
		return dettaglio;
	}

	nascondiPaginaRicercaIntestazioni(tipo){
		// nascondo la ricerca
		$('#'+this.idDialog+' #ricerca').addClass('hidden');
		$('#'+this.idDialog+' #dettaglio').removeClass('hidden');

		$('#'+this.idDialog+' #anagrafica-tab6-uiu').removeClass('active');
		$('#'+this.idDialog+' #indirizzi-tab6-uiu').removeClass('active');
		$('#'+this.idDialog+' #identificativi-tab6-uiu').removeClass('active');
		$('#'+this.idDialog+' #persone-tab6-uiu').removeClass('active');
		$('#'+this.idDialog+' #soggetti-tab6-uiu').removeClass('active');
		$('#'+this.idDialog+' #calcolo-tab6-uiu').removeClass('active');

		$('#'+this.idDialog+' #anagrafica-tab6-pt').removeClass('active');
		$('#'+this.idDialog+' #porzione-tab6-pt').removeClass('active');
		$('#'+this.idDialog+' #deduzione-tab6-pt').removeClass('active');
		$('#'+this.idDialog+' #riserva-tab6-pt').removeClass('active');
		$('#'+this.idDialog+' #persone-tab6-pt').removeClass('active');
		$('#'+this.idDialog+' #soggetti-tab6-pt').removeClass('active');
		$('#'+this.idDialog+' #calcolo-tab6-pt').removeClass('active');

		$('#'+this.idDialog+' #anagraficaTabContent6-uiu').removeClass('active');
		$('#'+this.idDialog+' #indirizziTabContent6-uiu').removeClass('active');
		$('#'+this.idDialog+' #identificativiTabContent6-uiu').removeClass('active');
		$('#'+this.idDialog+' #personeTabContent6-uiu').removeClass('active');
		$('#'+this.idDialog+' #soggettiTabContent6-uiu').removeClass('active');
		$('#'+this.idDialog+' #calcoloTabContent6-uiu').removeClass('active');

		$('#'+this.idDialog+' #anagraficaTabContent6-pt').removeClass('active');
		$('#'+this.idDialog+' #porzioneTabContent6-pt').removeClass('active');
		$('#'+this.idDialog+' #deduzioneTabContent6-pt').removeClass('active');
		$('#'+this.idDialog+' #riservaTabContent6-pt').removeClass('active');
		$('#'+this.idDialog+' #personeTabContent6-pt').removeClass('active');
		$('#'+this.idDialog+' #soggettiTabContent6-pt').removeClass('active');

		if(tipo === 'PT') {
			$('#'+this.idDialog+' #dettaglioTabPt').removeClass('hidden');
			$('#'+this.idDialog+' #dettaglioTabUiu').addClass('hidden');
			
			$('#'+this.idDialog+' #anagrafica-tab6-pt').addClass('active');
			$('#'+this.idDialog+' #anagraficaTabContent6-pt').addClass('active');
		} else {
			$('#'+this.idDialog+' #dettaglioTabPt').addClass('hidden');
			$('#'+this.idDialog+' #dettaglioTabUiu').removeClass('hidden');
			
			$('#'+this.idDialog+' #anagrafica-tab6-uiu').addClass('active');
			$('#'+this.idDialog+' #anagraficaTabContent6-uiu').addClass('active');
		}
		// mostro i pulsanti corretti
		$('#'+this.idDialog+' .pulsante-ricerca').each(function(){
			$(this).children('li').addClass('hidden');
		});
		$('#'+this.idDialog+' .pulsante-dettaglio').each(function(){
			$(this).children('li').removeClass('hidden');
		});
	}
}