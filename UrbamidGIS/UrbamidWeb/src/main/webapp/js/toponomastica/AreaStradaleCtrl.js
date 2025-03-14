class PaginaGestioneAreeStradaliCtrl extends BaseModaleRicercaCtrl {
	constructor() {
		super('paginaGestioneAreeStradaliCtrl','Elementi e Aree Stradali', 'modaleGestioneAreeStradali');		
		this.datiMockAreeStradali = [{
			id: 1,
			classificaFunzionale: '',
			stato: 'in esercizio',
			classificaAmminsitrativa: 'cambio toponimo/patrimonialita',
			tipo: 'di tronco carreggiata',
			classeLarghezza: 'lunghezza compresa tra 3.5m ...',
			sede: 'a raso',
			toponimoStradale: 'VIA MASSENZATICO',
			estesaAmministrativa: '',
			sensoPercorrenza: 'doppio senso di marcia',
			carreggiata: 'carreggiata unica',
			limiteAmministrativo: 'nessun limite amministrativo',
			livello: 'non in sottopasso',
			origine: 'arco esistente',
			fonte: 'altre ortofoto locali a grande ...',
			fondo: 'pavimentato',
			note: ''
		}];
		
		this.datiMockDocumenti = [{
			id: 1,
			descrizione: 'prova.pdf',
			path: 'C:/Documents/prova.pdf'
		},{
			id: 2,
			descrizione: 'prova.doc',
			path: 'C:/Documents/prova.doc'
		}];
		
		this.datiMockMyltiPolygon 	= ["MULTIPOLYGON(((15.5314400000206 38.2925099784818,15.5315435648781 38.2925501965456,15.5316200157919 38.2924215886319,15.5315180880437 38.2923894349758,15.5314400000206 38.2925099784818)))",
				   					   "MULTIPOLYGON(((15.5492421026891 38.1751705615742,15.5489505731785 38.1748511047374,15.5488619893432 38.1749009462801,15.5489955417672 38.1750500975808,15.5489389020861 38.1750819054921,15.5489102123455 38.175053545832,15.548824805372 38.1751024624327,15.5490090503804 38.1753023737251,15.5492421026891 38.1751705615742),(15.5489668018598 38.1751159535099,15.5490265804846 38.1750826983308,15.5490928633309 38.1751561411114,15.5490339061581 38.1751893654148,15.5489668018598 38.1751159535099)))",
				   					   "MULTIPOLYGON(((15.5492421026891 38.1751705615742,15.5489505731785 38.1748511047374,15.5488619893432 38.1749009462801,15.5489955417672 38.1750500975808,15.5489389020861 38.1750819054921,15.5489102123455 38.175053545832,15.548824805372 38.1751024624327,15.5490090503804 38.1753023737251,15.5492421026891 38.1751705615742),(15.5489668018598 38.1751159535099,15.5490265804846 38.1750826983308,15.5490928633309 38.1751561411114,15.5490339061581 38.1751893654148,15.5489668018598 38.1751159535099)))",
				   					   "MULTIPOLYGON(((15.553931292577 38.1824399437717,15.5545683482012 38.1820932143671,15.5542871629522 38.181783883978,15.5536440931681 38.182129063974,15.553931292577 38.1824399437717),(15.553903249003 38.1822058277531,15.5539171520926 38.182198509287,15.5539388004075 38.182223378767,15.5539248905902 38.1822313098738,15.553903249003 38.1822058277531),(15.5539356127113 38.1821722503713,15.5539205124418 38.1821558720994,15.5539348421966 38.1821476326876,15.553949508276 38.1821639499657,15.5539356127113 38.1821722503713),(15.5539858537983 38.1822285867638,15.5539698505408 38.1822090596852,15.5539846185605 38.1822014127657,15.5540001325967 38.1822196539009,15.5539858537983 38.1822285867638),(15.5540473429792 38.1822438789382,15.5540638496286 38.182233737257,15.5540866230698 38.1822594571898,15.5540681140365 38.182268950774,15.5540473429792 38.1822438789382),(15.5542616801877 38.1821287225955,15.5542782377473 38.1821192743274,15.554302987292 38.1821436965157,15.5542864282513 38.1821544420777,15.5542616801877 38.1821287225955),(15.5542774825637 38.182069846028,15.5542587118148 38.1820511159682,15.5542713508524 38.1820441818653,15.5542881222997 38.1820641556835,15.5542774825637 38.182069846028),(15.5542408480062 38.1818989235463,15.5542605599404 38.1818870458425,15.5542770254255 38.1819058229374,15.5542579821859 38.1819170848474,15.5542408480062 38.1818989235463),(15.5543876477914 38.1820712392492,15.5544101566741 38.1820579427826,15.5544320171233 38.1820791985857,15.55440714426 38.1820938126304,15.5543876477914 38.1820712392492)))"];
		this.inizializzaPagina();
	}
	inizializzaPagina(){
		var self = this;
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			appUtil.reloadTooltips();
			
	    	self.aggiungiEventoClickChiudiFinestra();
	    	self.aggiungiEventiClickTornaIndietroARicerca();
			self.inizializzaBreadcrumb();
	    	
			self.nascondiPaginaDettaglio();
			
			var colonne = [];
			/* NASCONDO LA TABELLA */
			$("#tabellaRisultati").hide();
			$("#dropdownAree").hide();
			
	    	$('#' + self.idDialog + ' #eseguiBtn').click(function() {
				$("#tabellaRisultati").show();
				$("#dropdownAree").show();

				self.inizializzaDatatableRicerca(self.datiMockAreeStradali);

			});
	    	
	    	$('#' + self.idDialog + ' #nuovoBtn').click(function() {
	    		//VISUALIZZO I PULSANTI CORRETTI
	    		$('#' + self.idDialog + ' .pulsante-dettaglio').prop('hidden', false);
	    		$('#' + self.idDialog + ' .pulsante-ricerca').prop('hidden', true);
	    		
	    		self.nascondiPaginaRicerca();
	    		self.aggiornaBreadcrumb('nuovo');
	    				    	
		    	self.visualizzaDettaglio();
	    	})
	    	
	    	$('#' + self.idDialog + ' #azzeraBtn').click(function() {
	    		self.resettaForm('#ricerca')
	    	})
	    	
	    	$('#' + self.idDialog + ' #indietroBtn').click(function() {
	    		//VISUALIZZO I PULSANTI CORRETTI
	    		$('#' + self.idDialog + ' .pulsante-dettaglio').prop('hidden', true);
	    		$('#' + self.idDialog + ' .pulsante-inserimento').prop('hidden', true);
	    		$('#' + self.idDialog + ' .pulsante-ricerca').prop('hidden', false);
	    		self.resettaForm('#dettaglio')
	    	})
		}, {closable: true});
	}
	
	resettaForm(form) {
		$(':input', form).each(function() {
			let type = this.type;
			let tag = this.tagName.toLowerCase();
		    if(type == 'checkbox' || type == 'radio') {
				 this.checked = false;
			} else if (tag == 'select') {
				this.selectedIndex = 0;
			} else if (type == 'text') {
                this.value = ''
            }
		})
	}
	
	inizializzaDatatableRicerca(giunzione) {
		var self = this;
		$("#tabellaRisultati").DataTable({
			data: giunzione,
			language: self.datatableLan,
			buttons: [ {
				extend: 'colvis',
				text: '<div class="input-group">' +
							'<input type="search" class="form-control" id="data-column-search" placeholder="Seleziona colonne">' +
							'<div class="input-group-append"><span class="input-group-text"><i class="fa fa-chevron-down"></i></span></div>' +
					   '</div>',
				columns: [ 1, 2, 3, 4, 5, 6, 7 ],
				postfixButtons: [ { extend: 'colvisRestore',
									text: '<hr size="1" style="margin-top: 6px">' +
										  '<button class="btn btn-primary" style="position: relative; left: 6%;">Visualizza tutto</button>',
								} ]
			} ],
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        processing: true,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(_d, _t, r) {return r.classificaFunzionale;}, orderable : true},
	        	{targets : 1, render: function(_d, _t, r) {return r.stato;}, orderable : true},
	        	{targets : 2, render: function(_d, _t, r) {return r.classificaAmminsitrativa;}, orderable : true},
	        	{targets : 3, render: function(_d, _t, r) {return r.tipo;}, orderable : true},
	        	{targets : 4, render: function(_d, _t, r) {return r.classeLarghezza;}, orderable : true},
	        	{targets : 5, render: function(_d, _t, r) {return r.sede;}, orderable : true},
	        	{targets : 6, render: function(_d, _t, r) {return r.toponimoStradale;}, orderable : true},
	        	{targets : 7, render: function(_d, _t, r) {return r.estesaAmministrativa;}, orderable : true},
	        	{targets : 8, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabella', {id: r.id});
	        		return templatePulsanti;
	        	}}
		   ],
		   initComplete: function(settings, json) {
				$('.dt-button').removeClass('dt-button buttons-collection buttons-colvis');
			},
	       drawCallback: function( _settings ) {
	        	sessionStorage.setItem('ricercaAreeStradali', JSON.stringify(giunzione));
	        	self.aggiungiEventiAiPulsantiAzioneAreeStradali();
	        }
		});
	}
	
	aggiungiEventiAiPulsantiAzioneAreeStradali() {
		var self = this;
		$("#tabellaRisultati button.modifica-btn").off('click').on('click', function(_event){
			self.visualizzaDettaglio($(this).data('id'));
        });
		
		$("#tabellaRisultati button.elimina-btn").off('click').on('click', function(_event){
			self.elimina($(this).data('id'));
        });
		
//		$("#tabellaRisultati button.localizza-giunzione-btn").off('click').on('click', function(event) {
//			getGeometry($(this).data('id'), self, 6);
//		})
		
	}
	
	visualizzaDettaglio(id) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function() {
			
			appUtil.hideLoader();
			
    		$('#' + self.idDialog + ' .pulsante-ricerca').prop('hidden', true);
    		
			if(id != null) {
				
    			self.inizializzaDatatableDocumenti(self.datiMockDocumenti);
				
				$('#' + self.idDialog + ' ul.nav-tabs li a#documenti-tab').prop('hidden', false)
				
				//RECUPERO IL DETTAGLIO
	    		var dettaglio = self.recuperaDettaglio(id);
	    		
	    		self.aggiornaBreadcrumb('dettaglio');
				
				//NASCONDO LA PAGINA DI RICERCA
				self.nascondiPaginaRicerca();
				//VISUALIZZO I PULSANTI CORRETTI
				$('#' + self.idDialog + ' .pulsante-dettaglio').prop('hidden', false);
	    		$('#' + self.idDialog + ' .pulsante-inserimento').prop('hidden', true);
	    		
	    		//IL DETTAGLIO VIENE INSERITO DIRETTAMENTE NEGLI INPUT
	    		var classificaFunzionale = $('#' + self.idDialog + ' #classificaFunzionaleM').val(dettaglio.classificaFunzionale == '' ? 'n.d' : dettaglio.classificaFunzionale);
	    		var stato = $('#' + self.idDialog + ' #statoM').val(dettaglio.stato == '' ? 'n.d' : dettaglio.stato);
	    		var classificaAmministrativa = $('#' + self.idDialog + ' #classAmministrativaM').val(dettaglio.classificaAmminsitrativa == '' ? 'n.d' : dettaglio.classificaAmminsitrativa);
	    		var tipo = $('#' + self.idDialog + ' #tipoM').val(dettaglio.tipo == '' ? 'n.d' : dettaglio.tipo);
	    		var classeLarghezza = $('#' + self.idDialog + ' #classeM').val(dettaglio.classeLarghezza == '' ? 'n.d' : dettaglio.classeLarghezza);
	    		var sede = $('#' + self.idDialog + ' #sedeM').val(dettaglio.sede == '' ? 'n.d' : dettaglio.sede);
	    		var toponimoStradale = $('#' + self.idDialog + ' #toponimoStradaleM').val(dettaglio.toponimoStradale == '' ? 'n.d' : dettaglio.toponimoStradale);
	    		var estesaAmministrativa = $('#' + self.idDialog + ' #estesaAmministrativaM').val(dettaglio.estesaAmministrativa == '' ? 'n.d' : dettaglio.estesaAmministrativa);
	    		var sensoPercorrenza = $('#' + self.idDialog + ' #sensoPercorrenza').val(dettaglio.tipoFunzionale == '' ? 'n.d' : dettaglio.sensoPercorrenza);
	    		var carreggiata = $('#' + self.idDialog + ' #carreggiataM').val(dettaglio.carreggiata == '' ? 'n.d' : dettaglio.carreggiata);
	    		var limiteAmministrativo = $('#' + self.idDialog + ' #limiteAmministrativoM').val(dettaglio.limiteAmministrativo == '' ? 'n.d' : dettaglio.limiteAmministrativo);
	    		var livello = $('#' + self.idDialog + ' #livelloM').val(dettaglio.livello == '' ? 'n.d' : dettaglio.livello);
	    		var origine = $('#' + self.idDialog + ' #origineM').val(dettaglio.origine == '' ? 'n.d' : dettaglio.origine);
	    		var fonte = $('#' + self.idDialog + ' #fonteM').val(dettaglio.fonte == '' ? 'n.d' : dettaglio.fonte);
	    		var fondo = $('#' + self.idDialog + ' #fondoM').val(dettaglio.fondo == '' ? 'n.d' : dettaglio.fondo);
	    		var note = $('#' + self.idDialog + ' #noteAreeStradali').val(dettaglio.note == '' ? 'n.d' : dettaglio.note);
	    		
	    		//CREO L'OGGETTO DA MANDARE AL SERVER
	    		
				appUtil.hideLoader();
				
				$('#' + self.idDialog + ' #aggiornaBtn').click(function() {
//					self.modifica(cippoMod);
				})
				
			} else {
				
				$('#' + self.idDialog + ' .pulsante-dettaglio').prop('hidden', true);
	    		$('#' + self.idDialog + ' .pulsante-inserimento').prop('hidden', false);
	    		$('#' + self.idDialog + ' ul.nav-tabs li a#documenti-tab').prop('hidden', true)
	    		
	    		$('#salvaBtn').click(function() {
	    			
//	    			var toponimoStradale = {id: null, 
//							   				classe:  $('#' + self.idDialog + ' #classeToponimo').val(), 
//							   				descrizione: $('#' + self.idDialog + ' #descrizioneToponimo').val(),
//							   				comune: $('#' + self.idDialog + ' #comuneToponimo').val(),
//							   				denominazione: $('#' + self.idDialog + ' #denominazioneToponimo').val(),
//							   				tipo: $('#' + self.idDialog + ' #tipoToponimo').val(),
//							   				codice: $('#' + self.idDialog + ' #codiceToponimo').val(), 
//							   				note: $('#' + self.idDialog + ' #noteToponimo').val()};
	    			
//	    			self.inserisci(toponimoStradale);
	    			
	    		})
	    		
	    		
			}
			
			
		}, 1000);
	}
	
	recuperaDettaglio(id) {
		
		let risultato = JSON.parse(sessionStorage.getItem('ricercaGiunzione'));
		let dettaglio;
		for(var i=0; i<risultato.length; i++){
			if(risultato[i].id === id){
				dettaglio = risultato[i];
				break;
			}
		}
		
		return dettaglio;
		
	}
	
	inizializzaDatatableDocumenti(dati) {
		var self = this;
		$("#tabellaDocumentiAreeStradali").DataTable({
			data: dati,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        destroy: true,
	        processing: true,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets : 0, render: function(_d, _t, r) {return r.descrizione;}, width: '70%', orderable : true},
	        	{targets : 1, render: function(_d, _t, r) {return r.path;}, orderable : true},
	        	{targets : 2, className: "text-right", orderable : false, render: function(_d, _t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaDocumenti', {id: r.id});
	        		return templatePulsanti;
	        	}}
	       ],
	       drawCallback: function( _settings ) {
	        	self.aggiungiEventiAiPulsantiDocumentiAreeStradali();
	        }
		});
	}
	
	aggiungiEventiAiPulsantiDocumentiAreeStradali() {
		var self = this;
		$("#tabellaDocumenti button.elimina-documento-btn").off('click').on('click', function(_event){
			self.eliminaDocumento($(this).data('id'));
        });
		
		$("#tabellaDocumenti button.download-documento-btn").off('click').on('click', function(_event){
			self.downloadDocumento($(this).data('id'));
        });
	}
	
	downloadDocumento(_id) {
		alert('DOWNLOAD')
	}
	
	eliminaDocumento(_id) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				appUtil.hideLoader();
				iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: 'Documento eliminato con successo',
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
			})
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione del documento?');
	}
	
	elimina(_id) {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				appUtil.hideLoader();
				iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: 'Eliminato con successo',
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
			})
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione della localit&agrave;?');
	}
	
	inserisci() {
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){
			appUtil.hideLoader();
			iziToast.show({
	    		title: 'OK',
	    		theme: 'dark',
	    		icon:'fa fa-check',
	    		message: 'Inserito con successo',
	    		animateInside: false,
	    		position: 'topCenter',
	    	});
		})

	}
	
	modifica() {
		var self = this;
		appUtil.confirmOperation(function() {
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				appUtil.hideLoader();
				iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: 'Modificato con successo',
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
			})
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla modifica dell\'accesso?');
		
	}
	
}