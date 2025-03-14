/**
 * CONTROLLER PER LA GESTIONE DEI LAYER
 */
class PaginaGestioneLayerCtrl extends BaseModaleRicercaCtrl {

	/**
	 * Costruttore
	 */
	constructor( ) {
		super('paginaGestioneLayerCtrl', 'Gestione Livello', 'modaleGestioneLayer');
		this.inizializzaPagina();
		/** CREO GLI STATI DEL LAYER */
		this.stato = [
			{value: 'BOZZA', descrizione: 'BOZZA'},
			{value: 'PUBBLICATO', descrizione: 'PUBBLICATO'},
		]
		
		this.geometrieLayer 	  = []; /** CREO UNA LISTA CHE VERRA' RIEMPITA CON LE GEOMETRIE PROVENIENTI SIA DAL DB, CHE DAL DISEGNO */
		this.geometrieDaEliminare = []; /** CREO UNA LISTA CHE VERRA' RIEMPITA CON LE GEOMETRIE DA ELIMINARE */
	}

	inizializzaPagina() {
		var self = this;
		/**Il seguente metodo viene chiamato nella BaseModaleCtrl per formare la modale**/
		let contextHandlebar = {};
		openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
			/** INIZIALIZZAZIONI */
	    	appUtil.reloadTooltips();
			self.aggiungiEventiClickTornaIndietroARicerca();
			self.inizializzaBreadcrumb();
			self.setButtonModale('ricerca');

			/** IMPOSTAZIONI EDITING */
			self.editingProperties = {
				geometryWKT : null,
				typeEditing: 'LineString',
				pagina: 'paginaGestioneLayerCtrl',
				idIziToast: 'editingLayerIziToast',
				newEntita: null,
				idGeometria: null
			}

			/** NASCONDO LA TABELLA */
			$('#' + self.idDialog + ' #tabellaEditingLivello').hide();
			$('#' + self.idDialog + ' #tabDettaglio').hide();

			/****************** AZIONI MODALE RICERCA ***************** INIZIO **/
    		/** CERCA **/
			$('#' + self.idDialog + ' #ricercaBtnEditing').on('click', function(e) {
				/** VISUALIZZO LA TABELLA */
				$('#' + self.idDialog + ' #tabellaEditingLivello').show();
				/** INIZIALIZZO DATATABLE */
				self.inizializzaDataTableRicerca();
			
			});

			/** AL CLICK DEL BUTTON AZZERA PULISCO IL FORM DELLA RICERCA */
			$('#' + self.idDialog + ' #azzeraBtnEditing').on('click', function(e) {
				self.resettaForm('ricerca');
			
			});

			/** NUOVO */
			$('#' + self.idDialog + ' #aggiungiBtnEditing').on('click', function(e) {
				/** INIZIALIZZAZIONI */
				self.geometrieDaEliminare = [];
				/** INIZIALIZZAZIONI EDITING */
				(self.editingProperties).newEntita = true;

				/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
	    		self.riduciAdIconaTutteLeModali();
				/** PULISCO LA LISTA  */
				self.geometrieLayer = [];
				/** visualizzo il dettaglio */
				self.visualizzaDettaglio(null);
				/** ABILITO IL DISEGNO SU MAPPA */
				self.avviaEditingLayer();
			});

 			$('.ui-dialog-titlebar-close').click(function() {
                drawLayerSource.clear();
            });
			/** CHIUDI **/
	    	$('#' + self.idDialog + ' #chiudiBtnEditing').click(function() {
	    		self.geometrieDaEliminare = [];
				/**PULIZIA DEL LAYER DEL DISEGNO**/
	    		drawLayerSource.clear();
	    		/**CHIUDE LA MODALE DELL'ANAGRAFICA**/
	    		$('#'+self.idDialog).dialog('close');
	    	});
			/****************** AZIONI MODALE RICERCA ***************** FINE **/

			/****************** AZIONI MODALE INSERIMENTO ***************** INIZIO **/
			/** SALVA */
			$('#' + self.idDialog + ' #salvaBtnEditing').click(function(e) {
				e.stopImmediatePropagation();
				if($('#' + self.idDialog + ' #nomeLayer').val() != '') {

					/** INIZIALIZZAZIONE */
					let geometrie = [];
					let errore = false;

					/** MI CICLO LA LISTA */
					for(let i in self.geometrieLayer) {
						/** CONTROLLO SE IL NOME DELLA GEOMETRIA NON SIA VUOTO */
						if($('#tabellaGeometrieLivello #nomeGeometria_' + i).val() != '') {
							geometrie.push({ id: null,
											 idLayer: null,
											 tipo: self.geometrieLayer[i].tipo,
											 nome: $('#tabellaGeometrieLivello #nomeGeometria_' + i).val(),
											 descrizione: $('#tabellaGeometrieLivello #descrizioneGeometria_' + i).val() != '' ? $('#tabellaGeometrieLivello #descrizioneGeometria_' + i).val() : null,
											 geom: self.geometrieLayer[i].geom })
						} else {
							/** MESSAGGIO */
							errore = true;
							iziToast.error({
								title: 'Attenzione',
								theme: 'dark',
								icon:'fa fa-times',
								message: 'Il nome della geometria non pu&ograve; essere vuoto!',
								animateInside: false,
								position: 'topCenter',
							});
							/** RESETTO LA VARIABILE */
							geometrie = [];
							break;
						}
					}

					/** VERIFICO CHE LA VARIABILE ERRORE NON SIA SU TRUE */
					if(!errore) {
						/** CREO L'OGGETTO DA MANDARE AL SERVER */
						var layer = { id: null,
							identificativo: $('#' + self.idDialog + ' #identificativoLayer').val(),
							nome: $('#' + self.idDialog + ' #nomeLayer').val(),
							descrizione: $('#' + self.idDialog + ' #descrizioneLayer').val() != '' ? $('#' + self.idDialog + ' #descrizioneLayer').val() : null,
							note: $('#annotazioniLayer').val() != '' ? $('#annotazioniLayer').val() : null,
							stato: $('#' + self.idDialog + ' #statoLayer option:selected').val(),
							geometrie: geometrie }

						/** CHIAMO IL METODO INSERISCILAYER */	
			  			self.inserisciLayer(layer);
					}
				
				} else {
					/** MESSAGGIO */
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Il nome del layer non pu&ograve; essere vuoto!',
						animateInside: false,
						position: 'topCenter',
					});

				}

			});
			/****************** AZIONI MODALE INSERIMENTO ***************** FINE **/

			/****************** AZIONI MODALE MODIFICA ***************** INIZIO **/
			/** AGGIORNA */
			$('#' + self.idDialog + ' #aggiornaBtnEditing').click(function(e) {
				e.stopImmediatePropagation();
				if($('#' + self.idDialog + ' #nomeLayer').val() != '') {
					
					/** INIZIALIZZAZIONE **/
					let geometrie = [];
					let errore = false;

					/** MI CICLO LA LISTA */
					for(let i in self.geometrieLayer) {
						/** CONTROLLO SE IL NOME DELLA GEOMETRIA NON SIA VUOTO */
						if($('#tabellaGeometrieLivello #nomeGeometria_' + i).val() != '') {
							if(self.geometrieLayer[i].idLayer === self.idDettaglio && self.geometrieLayer[i].id != null) {
								/** CREO L'OGGETTO GEOMETRIE PER L'UPDATE */
								geometrie.push({id: self.geometrieLayer[i].id,
												idLayer: self.idDettaglio,
												tipo: self.geometrieLayer[i].tipo,
												nome: $('#tabellaGeometrieLivello #nomeGeometria_' + self.geometrieLayer[i].id).val(),
												descrizione: $('#tabellaGeometrieLivello #descrizioneGeometria_' + self.geometrieLayer[i].id).val() != '' ? $('#tabellaGeometrieLivello #descrizioneGeometria_' + self.geometrieLayer[i].id).val() : null,
												geom: self.geometrieLayer[i].geom});
							
							} else if(self.geometrieLayer[i].id === null) {
								/** CREO L'OGGETTO GEOMETRIE PER L'INSERIMENTO DI UNA NUOVA GEOMETRIA */
								geometrie.push({id: null,
												idLayer: self.idDettaglio,
												tipo: self.geometrieLayer[i].tipo,
												nome: $('#tabellaGeometrieLivello #nomeGeometria_' + i).val(),
												descrizione: $('#tabellaGeometrieLivello #descrizioneGeometria_' + i).val() != '' ? $('#tabellaGeometrieLivello #descrizioneGeometria_' + i).val() : null,
												geom: self.geometrieLayer[i].geom})
							}
						} else {
							errore = true;
							/** MESSAGGIO */
							iziToast.error({
								title: 'Attenzione',
								theme: 'dark',
								icon:'fa fa-times',
								message: 'Il nome della geometria non pu&ograve; essere vuoto!',
								animateInside: false,
								position: 'topCenter',
							});
							geometrie = [];
							break;
						}
					}

					/** VERIFICO CHE LA VARIABILE ERRORE NON SIA SU TRUE */
					if(!errore) {

						/** CREO L'OGGETTO DA MANDARE AL SERVER */
						var layerMod = { id: self.idDettaglio,
										 identificativo: $('#' + self.idDialog + ' #identificativoLayer').val(),
										 nome: $('#' + self.idDialog + ' #nomeLayer').val(),
										 descrizione: $('#' + self.idDialog + ' #descrizioneLayer').val() != '' ? $('#' + self.idDialog + ' #descrizioneLayer').val() : null,
										 note: $('#annotazioniLayer').val() != '' ? $('#annotazioniLayer').val() : null,
										 stato: $('#' + self.idDialog + ' #statoLayer option:selected').val(),
										 geometrie: geometrie }

						/** VERIFICO CHE CI SIANO DELLE GEOMETRIE DA ELIMINARE */
						if(self.geometrieDaEliminare.length != 0) {
							/** MI CICLO LA LISTA DELLE GEOMETRIE DA ELIMINARE */
							$.each(self.geometrieDaEliminare, function(key, value) {
							// 	/** EFFETTUO LA CHIAMATA PER ELIMINARE LA GEOMETRIA */
								$.when(editingLayerRest.eliminaGeometria(self.geometrieDaEliminare[key])).done(function(response) {
									if(!response.success) {
										errore = true;
										/** MESSAGGIO */
										iziToast.error({
											title: 'Attenzione',
											theme: 'dark',
											icon:'fa fa-times',
											message: 'Errore nella modifica del layer!',
											animateInside: false,
											position: 'topCenter',
										});
									} 
								});
							});
							
							/** SE NON CI DOVESSERO ESSERE ERRORI NELL'ELIMINAZIONE DELLE GEOMETRIE, CHIAMO IL METODO MODIFICA */
							if(!errore) {
								/** IN CASO DI SUCCESSO PULISCO LA LISTA DELLE GEOMETRIA DA ELIMINARE */
								self.geometrieDaEliminare = []
								/** CHIAMO IL METODO PER LA MODIFICA */
								self.modificaLayer(layerMod);
							}
						
						} else { /** SE NON CI DOVESSERO ESSERE DELLE GEOMETRIE DA ELIMINARE, VERRA' CHIAMATO IL METODO DELLA MODIFICA */
							/** CHIAMO IL METODO PER LA MODIFICA */
							self.modificaLayer(layerMod);
						}
					} 
				
				} else {
					/** MESSAGGIO */
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Il nome del layer non pu&ograve; essere vuoto!',
						animateInside: false,
						position: 'topCenter',
					});

				}
			})
			/****************** AZIONI MODALE MODIFICA ***************** FINE **/
			/** INDIETRO VALE SIA PER L'INSERIMENTO CHE PER LA MODIFICA */
			$('#' + self.idDialog + ' #indietroBtn').click(function(e) {
				drawLayerSource.clear();
				/** CONTROLLO SE CI SIANO ANCORA DELLE GEOMETRIE DA ELIMINARE */
				if(self.geometrieDaEliminare.length != 0) {
					/** CONFERMA */
					appUtil.confirmOperation(function() {
						/** RESETTO LA VARIABILE GEOMETRIE DA ELIMINARE */
						self.geometrieDaEliminare = [];
						/** MI CICLO TUTTI I FIGLI DEL DIV TABDETTAGLIO COSI DA POTERLI RIMUOVERE */
						$('#' + self.idDialog + ' #tabDettaglio').children().each(function() {
							$(this).remove();
						});
						/** NASCONDO IL DIV TABDETTAGLIO E VISUALIZZO LA PAGINA DI RICERCA */
						$('#' + self.idDialog + ' #tabDettaglio').hide();
						$('#' + self.idDialog + ' #ricerca').show();

						/** SETTO I BUTTON PER LA RICERCA */
						self.setButtonModale('ricerca');
						/** ELIMINO L'ULTIMO ELEMENTO DEL BREADCRUMB */
						$('.breadcrumb').children().each(function() {
							$(this).remove('.active'); /** ELEMENTO CON LA CLASSE ACTIVE */
						});

					}, function() {
						//ANNULLA
					}, {}, 'Non hai salvato le ultime modifiche, sei sicuro di voler continuare?');
				} else {
					/** MI CICLO TUTTI I FIGLI DEL DIV TABDETTAGLIO COSI DA POTERLI RIMUOVERE */
					$('#' + self.idDialog + ' #tabDettaglio').children().each(function() {
						$(this).remove();
					});
					/** NASCONDO IL DIV TABDETTAGLIO E VISUALIZZO LA PAGINA DI RICERCA */
					$('#' + self.idDialog + ' #tabDettaglio').hide();
					$('#' + self.idDialog + ' #ricerca').show();

					/** SETTO I BUTTON PER LA RICERCA */
					self.setButtonModale('ricerca');
					/** ELIMINO L'ULTIMO ELEMENTO DEL BREADCRUMB */
					$('.breadcrumb').children().each(function() {
						$(this).remove('.active'); /** ELEMENTO CON LA CLASSE ACTIVE */
					});

				}

			});

		}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
	}

	/**
	 * Il metodo setta i bottoni nella modale per tipo di azione. 
	 * Le azioni possibili sono: "ricerca", "inserimento" e "dettaglio"
	 */
	setButtonModale(type) {
		switch (type) {
			case 'ricerca':
				$("#pulsante-ricerca").show();
				$("#pulsante-inserimento").hide();
				$("#pulsante-dettaglio").hide();
				break;
			case 'inserimento':
				$("#pulsante-ricerca").hide();
				$("#pulsante-inserimento").show();
				$("#pulsante-dettaglio").hide();
				break;
			case 'dettaglio': 
				$("#pulsante-ricerca").hide();
				$("#pulsante-inserimento").hide();
				$("#pulsante-dettaglio").show();
				break;
			default:
				$("#pulsante-ricerca").show();
				$("#pulsante-inserimento").hide();
				$("#pulsante-dettaglio").hide();
		}
	}

	/**
	 * Il metodo resetta il form di ricerca dato l'id.
	 * Gli input da ripulire hanno i type: text, textarea,select, checkbox e radio 
	 */
	resettaForm(form) {
		$(':input', '#'+form).each(function() {
			let type = this.type;
			let tag = this.tagName.toLowerCase();
		    if(type == 'checkbox' || type == 'radio') {
				this.checked = false;
			} else if (tag == 'select') {
				this.selectedIndex = 0;
			} else if (type == 'text' || tag == 'textarea') {
                this.value = ''
            }
		})
	}

	/** 
	 * Il metodo ricarica DataTable
	 * @param idTable, identificativo della tabella
	 */
	ricaricaDataTable(idTable) {
		var self = this;
		if (!$.fn.DataTable.isDataTable('#' + idTable)) {
			self.inizializzaDataTableRicerca();
			$('#' + idTable).show();
		} else {
			var table = $('#' + idTable).DataTable()
			table.ajax.reload();
		}
	}

	inizializzaDataTableRicerca() {
		/** INIZIALIZZAZIONI */
		var self = this;
		/** FILTRI DI RICERCA */
		var nome = $('#nomeLayerRic').val();
		var descrizione = $('#descrizioneLayerRic').val();
		var stato = $('#statoRic option:selected').val();
		/** DATATABLE */
		$('#tabellaEditingLivello').DataTable({
			dom: self.datatableDom,
			buttons: [],
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: false,
			destroy: true,
			serverSide: true,
			processing: true,
			ajax: {
				type: 'POST',
				processData : false,
				dataType : 'json',
				url : appConfig.urbamidProtocol + "://" + appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + appConfig.urbamidService + 'adminController/findAllLayers',
				contentType : 'application/json; charset=UTF-8',
				data: function(json) {
					var colonnaSort = '';
					for (let i = 0; i < json.columns.length; i++) {
						if(json.columns[i].data === json.order[0].column) {
							colonnaSort = json.columns[i].name;
							break;
						}
					}
					var filtri = {nome: nome.trim(),
								  descrizione: descrizione.trim(),
								  stato: stato,
								  pageIndex: json.start,
								  pageSize: json.length,
								  pageDraw: json.draw,
								  pageOrder: { column: colonnaSort, dir: json.order[0].dir } }

					return JSON.stringify(filtri);
				}
			},
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets :  0, name: 'identificativo', render: function(d, t, r) {return r.identificativo != null ? r.identificativo : null;}, orderable : true},
	        	{targets :  1, name: 'nome', render: function(d, t, r) {return r.nome != null ? r.nome : null;}, orderable : true},
	        	{targets :  2, name: 'descrizione', render: function(d, t, r) {return r.descrizione != null ? r.descrizione : null;}, orderable : true},
	        	{targets :  3, name: 'stato', render: function(d, t, r) {return r.stato != null ? r.stato : null;}, orderable : true},
	        	{targets : 4, className: "text-right", orderable : false, render: function(d, t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRisultati', {id: r.id});
	        		return templatePulsanti;
	        	}}
	        ],
	        initComplete: function( settings ) {

			},
			drawCallback: function( settings ) {
				self.aggiungiEventiAiPulsantiAzione();
			 	sessionStorage.setItem('ricercaEditingLayer', JSON.stringify(settings.json.data));	
			}
		})
	}

	/** 
	 * Il metodo recupera i click sulle azioni presenti nella riga della tabella.
	 * Le azioni possibili in tabella sono: 
	 * - modifica, apre un form di dettaglio dell'editing layer. Il salvataggio di una modifica non cambierà lo stato dell'entità ma soltanto gli attributi
	 * - elimina, aprirà un box di conferma con la richiesta per l'eliminazione.
	 * 
	 **/
	aggiungiEventiAiPulsantiAzione() {
		var self = this;
		$('#tabellaEditingLivello button.modifica-btn').off('click').on('click', function(e) {
			/** RECUPERO LE GEOMETRIE DEL LAYER */
			self.recuperaGeometrie($(this).data('id'));
			/** VISUALIZZA DETTAGLIO */
			self.visualizzaDettaglio($(this).data('id'));
		});
		$('#tabellaEditingLivello button.elimina-btn').off('click').on('click', function(e) {
			/** ELIMINA LAYER */
			self.eliminaLayer($(this).data('id'));
		});
	}

	/**
	 * Metodo per il recupero del dettaglio di un layer dato l'id
	 * @id, identificativo del layer
	 */
	recuperaDettaglio(id) {
		var layers = JSON.parse(sessionStorage.getItem('ricercaEditingLayer'));
		var dettaglioLayer;
		for (let i = 0; i < layers.length; i++) {
			if(layers[i].id === id) {
				dettaglioLayer = layers[i];
				break;
			}
		}
		return dettaglioLayer;
	}

	/**
	 * Metodo per il recupero delle geometrie di un layer dato l'ID
	 * @param {*} idLayer, l'id del layer
	 */
	recuperaGeometrie(idLayer) {
		var self = this;
		$.when(editingLayerRest.visualizzaGeometrie(idLayer)).done(function(response) {
			if(response.success) {
				/** POPOLO LA LISTA DELLE GEOMETRIE APPENA RECUPERATA */
				self.geometrieLayer = response.aaData;
				/** MESSAGGIO */
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Geometrie caricate con successo!',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			} else {
				/** PULISCO LA LISTA DELLE GEOMETRIE IN CASO DI ERRORE */
				self.geometrieLayer = [];
				/** MESSAGGIO */
				iziToast.error({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Errore nel recupero delle geometrie!',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		});
	}

	/**
	 * Metodo per visualizzare il dettaglio di un layer. 
	 * 1. Se l'id è null, vuol dire che è un nuovo layer
	 * 2. Se l'id è diverso da null, vuol dire che è una modifica
	 * @param {*} id 
	 */
	visualizzaDettaglio(id) {
		var self = this;

		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		if(id != null) {
			/** RESETTO LA LISTA DELLE GEOMETRIE DA ELIMINARE */
			self.geometrieDaEliminare = [];
			self.idDettaglio = id;

			/** RECUPERO DEL DETTAGLIO */
			var dettaglio = self.recuperaDettaglio(self.idDettaglio);
			
			/** IMPOSTAZIONI PAGINA - INIZIO */
			$('#' + self.idDialog + ' #ricerca').hide();
			$('#' + self.idDialog + ' #tabDettaglio').show();

			self.setButtonModale('dettaglio');
			self.aggiornaBreadcrumb('dettaglio', dettaglio.nome);
			
			/** GESTIONE DEL DETTAGLIO DA VISUALIZZARE */
			var editingLayerDettaglio = {id: id,
										 identificativo: dettaglio.identificativo != null ? dettaglio.identificativo : '',
										 nome: dettaglio.nome != null ? dettaglio.nome : '',
										 descrizione: dettaglio.descrizione != null ? dettaglio.descrizione : '',
										 note: dettaglio.note != null ? dettaglio.note : '',
										 stato: dettaglio.stato != null ? dettaglio.stato : '',
										 stati: self.stato };

			var htmlDet = compilaTemplate('modaleDettaglioEditing', editingLayerDettaglio);
			$('#tabDettaglio').html(htmlDet);

			/** INIZIALIZZO LE SELECT */
			$('#statoLayer').val(dettaglio.stato != null ? dettaglio.stato : '');
			/** IMPOSTAZIONI PAGINA - FINE */

			/** INIZIALIZZO LA TABELLA DELLE GEOMETRIE */
			self.inizializzaDataTableGeometrie(self.geometrieLayer);
			
			/** AZIONI MODALE MODIFICA - INIZIO */
			/** BUTTON AGGIUNGI GEOMETRIE */
			$('#geometriaBtnEditing').click(function(e) {
				/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
				self.riduciAdIconaTutteLeModali();
				/**RIPULISCE LAYER**/
				drawLayerSource.clear();
				/** AVVIO IL DISEGNO PER INSERIRE NUOVE GEOMETRIE */
				(self.editingProperties).newEntita = true;
				/** AVVIO L'EDITING */
				self.avviaEditingLayer();
			});
			/** AZIONI MODALE MODIFICA - FINE */

		} else {

			/** IMPOSTAZIONI PAGINA - INIZIO*/
			$('#' + self.idDialog + ' #ricerca').hide();
			$('#' + self.idDialog + ' #tabDettaglio').show();

			self.setButtonModale('inserimento');
			self.aggiornaBreadcrumb('nuovo');

			/** GESTIONE DEL DETTAGLIO DA VISUALIZZARE */
			var editingLayerIns = {stati: self.stato, readonly: 'disabled'};
			var htmlDet = compilaTemplate('modaleDettaglioEditing', editingLayerIns);
			$('#tabDettaglio').html(htmlDet);

			/** IMPOSTO LA SELECT SU BOZZA */
			$('#statoLayer').val('BOZZA');

			/** INIZIALIZZO LA TABELLA DELLE GEOMETRIE RECUPERATE DAL DISEGNO */
			self.inizializzaDataTableGeometrie(self.geometrieLayer);
			/** IMPOSTAZIONI PAGINA - FINE */

			/** CREO L'IDENTIFICATIVO DEL LAYER
			 *  gli spazi il . e la , verranno modificati con il carattere underscore (_)
			  */
			$('#' + self.idDialog + ' #nomeLayer').change(function() {
				let str = $(this).val().trim();
				$('#' + self.idDialog + ' #identificativoLayer').val(str.replace(/\s/g, '_').replace(/[.,]/gi, '_').toUpperCase())
			})

			/** BUTTON AGGIUNGI GEOMETRIE */
			$('#geometriaBtnEditing').click(function(e) {
				/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
				self.riduciAdIconaTutteLeModali();
				/** GLI INDICO CHE E' UNA NUOVA GEOMETRIA */
				(self.editingProperties).newEntita = true;
				/**RIPULISCE LAYER**/
				drawLayerSource.clear();
				/** AVVIO IL DISEGNO PER INSERIRE NUOVE GEOMETRIE */
				self.avviaEditingLayer();
			});
		}
		appUtil.hideLoader();
	}

	inizializzaDataTableGeometrie(data) {
		/** INIZIALIZZAZIONI */
		var self = this;
		/** DATATABLE */
		$('#tabellaGeometrieLivello').DataTable({
			dom: self.datatableDom,
			buttons: [],
			data: data,
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        order: [[ 0, 'asc' ]],
	        columnDefs: [
	        	{targets :  0, name: 'ID', render: function(d, t, r) {return r.id != null ? r.id : '';}, orderable : true},
	        	{targets :  1, name: 'Tipo', render: function(d, t, r) {return r.tipo != null ? r.tipo : '';}, orderable : true},
	        	{targets :  2, name: 'Nome', render: function(d, t, r, meta) { 
					let templateInputName = compilaTemplate('nomeTemplateTabellaGeometria', {id: r.id != null ? r.id : meta.row, nome: r.nome, readonly: r.id != null ? 'disabled' : ''})
					return templateInputName;
				}, orderable : true},
	        	{targets :  3, name: 'Descrizione', render: function(d, t, r, meta) {
					let templateInputDescription = compilaTemplate('descrizioneTemplateTabellaGeometria', {id: r.id != null ? r.id : meta.row, descrizione: r.descrizione, readonly: r.id != null ? 'disabled' : ''})
					return templateInputDescription;
				}, orderable : true},
	        	{targets : 4, className: "text-right", orderable : false, render: function(d, t, r){
	        		let templatePulsanti = compilaTemplate('templatePulsantiTabellaRisultati', {id: r.id, geom: r.geom});
	        		return templatePulsanti;
	        	}}
	        ],
			drawCallback: function( settings ) {
				self.aggiungiEventiAiPulsantiAzioneGeometrie();
			 	sessionStorage.setItem('ricercaGeometryLayer', JSON.stringify(data));	
			}
		})
	}

	aggiungiEventiAiPulsantiAzioneGeometrie() {
		var self = this;
		$('#tabellaGeometrieLivello button.modifica-btn').click(function(e) {
			e.stopImmediatePropagation();
			/**PULIZIA DEL LAYER DEL DISEGNO**/
			drawLayerSource.clear();
			/**RIDUCE LE MODALE DELL'ANAGRAFICA**/
			self.riduciAdIconaTutteLeModali();
			/** ABILITO IL DISEGNO SU MAPPA PER LA MODIFICA */
			/**IMPOSTAZIONI AVANZATE EDITING**/
			(self.editingProperties).newEntita = false;
			(self.editingProperties).geometryWKT = $(this).data('geometry');
			(self.editingProperties).idGeometria = $(this).data('id');
			self.avviaEditingLayer();
		});

		$('#tabellaGeometrieLivello button.elimina-btn').click(function(e) {
			/** ELIMINA LA GEOETRIA DAL SERVER */
			self.eliminaGeometria($(this).data('id'))
		});

		$('#tabellaGeometrieLivello button.localizza-btn').click(function(e) {
			/** VISUALIZZO LA GEOMETRIA SU MAPPA */
			self.getGeometry($(this).data('geometry'));
		});
	}

	/**
	 * Metodo utile solo per aggiungere su mappa una geometria
	 * @param WKT
	 * @returns
	*/
	getGeometry(WKT) {
		var self = this;

		if (WKT != '' && WKT != null && WKT != undefined) {						//WKT DIVERSO DA NULL
			/**RIPULISCE LAYER**/
			drawLayerSource.clear();
			/**COSTRUISCE FEATURE DAL WKT**/
			var format = new ol.format.WKT();
			var feature = format.readFeature(WKT, {
				dataProjection: 'EPSG:4326',
				featureProjection: 'EPSG:3857'
			});
			/**AGGIUNGE LA FEATURE AL LAYER CON ZOOM**/
			drawLayerSource.addFeature(feature);
			const extent = feature.getGeometry().getExtent();
			(appMappa.maps).getView().fit(
					extent, {duration: 1000}
			);
			/**RIDUCE LA MODALE**/
			self.riduciAdIconaTutteLeModali();
		} else {																//WKT NULL												
			iziToast.error({
				title: 'Attenzione',
				theme: 'dark',
				icon: 'fa fa-times',
				message: 'Impossibile visualizzare la geometria in mappa!',
				animateInside: false,
				position: 'topCenter',
			});
		}
	}

	/**
	 * Il metodo salva il layer e riporta l'utente sulla pagina di ricerca
	 * @param {*} layer, è un oggetto contente le informazioni di un layer e le sue geometrie
	 */
	inserisciLayer(layer) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(editingLayerRest.countLayerByIdentificativo(layer.identificativo)).done(function(response) {
			if(response.success) {
				/** CONTROLLO SE L'IDENTIFICATIVO ESISTE */
				if(response.aaData == 0) {
					$.when(editingLayerRest.inserisciLayer(layer)).done(function(response) {
						if(response.success) {
							/** NASCONDO IL LOADER */
							appUtil.hideLoader();
							/** RITORNO ALLA PAGINA DI RICERCA */
							$('#' + self.idDialog + ' #indietroBtn').click();
							/** RICARICO LA TABELLA */
							self.ricaricaDataTable('tabellaEditingLivello');
							/** PULISCO LA LISTA DELLE GEOMETRIE */
							self.geometrieLayer = [];
							/** MESSAGGIO */
							iziToast.show({
								title: 'OK',
								theme: 'dark',
								icon:'fa fa-check',
								message: 'Layer: ' + layer.nome +  ' aggiunto con successo',
								animateInside: false,
								position: 'topCenter',
							});
						} else {
							/** NASCONDO IL LOADER */
							appUtil.hideLoader();
							/** MESSAGGIO */
							iziToast.error({
								title: 'Attenzione',
								theme: 'dark',
								icon:'fa fa-times',
								message: 'Errore nell\'inserimento del layer ' + layer.nome,
								animateInside: false,
								position: 'topCenter',
							});
						}
					});
				} else {
					/** NASCONDO IL LOADER */
					appUtil.hideLoader();
					/** MESSAGGIO */
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Il layer con l\'identificativo: ' + layer.identificativo + ' esiste gi&agrave;',
						animateInside: false,
						position: 'topCenter',
					});

				}
			} else {
				/** NASCONDO IL LOADER */
				appUtil.hideLoader();
				/** MESSAGGIO */
				iziToast.error({
					title: 'Attenzione',
					theme: 'dark',
					icon:'fa fa-times',
					message: 'Errore nell\'inserimento del layer ' + layer.nome,
					animateInside: false,
					position: 'topCenter',
				});
			}
		});
	}

	/**
	 * Il metodo modifica il layer e riporta l'utente sulla pagina di ricerca
	 * @param {*} layerMod, è un oggetto contenente le informazioni del layer e le sue geometrie
	 */
	modificaLayer(layerMod) {
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$.when(editingLayerRest.inserisciLayer(layerMod)).done(function(response) {
			if(response.success) {
				/** NASCONDO IL LOADER */
				appUtil.hideLoader();
				/** TORNO INDIETRO ALLA RICERCA */
				$('#' + self.idDialog + ' #indietroBtn').click();
				/** RICARICO LA TABELLA */
				self.ricaricaDataTable('tabellaEditingLivello');
				/** MESSAGGIO */
				iziToast.show({
		    		title: 'OK',
		    		theme: 'dark',
		    		icon:'fa fa-check',
		    		message: 'Layer: ' + layerMod.nome +  ' modificato con successo',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			} else {
				/** NASCONDO IL LOADER */
				appUtil.hideLoader();
				/** MESSAGGIO */
				iziToast.error({
		    		title: 'Attenzione',
		    		theme: 'dark',
		    		icon:'fa fa-times',
		    		message: 'Errore nella modifica del layer',
		    		animateInside: false,
		    		position: 'topCenter',
		    	});
			}
		});
	}

	/**
	 * Il metodo elimina un layer con le relative geometrie, chiedendo conferma. 
	 * In seguito all'operazione l'utente viene riportato sulla pagina di ricerca. 
	 * @param id, identificativo del layer.
	 */
	eliminaLayer(id) {
		var self = this;
		var dettaglio = this.recuperaDettaglio(id);
		appUtil.confirmOperation(function() {	
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			$.when(editingLayerRest.eliminaLayer(dettaglio)).done(function(response) {
				if(response.success) {
					/** NASCONDO IL LOADER */
					appUtil.hideLoader();
					/** RICARICO LA TABELLA DELLA RICERCA */
					self.ricaricaDataTable('tabellaEditingLivello');
					iziToast.show({
						title: 'OK',
						theme: 'dark',
						icon:'fa fa-check',
						message: 'Layer eliminato con successo!',
						animateInside: false,
						position: 'topCenter',
					});
				} else {
					/** NASCONDO IL LOADER */
					appUtil.hideLoader();
					/** MESSAGGIO */
					iziToast.error({
						title: 'Attenzione',
						theme: 'dark',
						icon:'fa fa-times',
						message: 'Errore nell\'eliminazione del layer',
						animateInside: false,
						position: 'topCenter',
					});
				}

			});

		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione del layer?\nEliminerai anche tutte le geometrie.');
	}

	/**
	 * Il metodo eliminerà una singola geometria in base all'ID di quest'ultima
	 * @param {*} idGeometria identificativo della geometria
	 */
	eliminaGeometria(idGeometria) {
		var self = this;
		appUtil.confirmOperation(function() {	
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			/** NASCONDO IL LOADER */
			appUtil.hideLoader();
			/** ELIMINO LA GEOMETRIA DALLA LISTA */
			for(let i in self.geometrieLayer) {
				if(self.geometrieLayer[i].id == idGeometria) {
					self.geometrieLayer.splice(i, 1);
					break;
				}
			}
			self.geometrieDaEliminare.push(idGeometria);
			/** RICARICO LA TABELLA DELLA RICERCA */
			let tabellaGeometria = $('#tabellaGeometrieLivello').DataTable();
			tabellaGeometria.clear().destroy();
			self.inizializzaDataTableGeometrie(self.geometrieLayer);
		}, function() {
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere alla cancellazione della geometria?');
	}

	/****************************************************************************************************************************************************************/
	/************ EDITING LAYER ********************************************************************************************************************* INIZIO ********/
	/****************************************************************************************************************************************************************/
	/**
	 * Avvio dell'editing
	 */
	avviaEditingLayer() {
		/** 1. inizializza */
		var self = this;
		/** 2. avvia la gestione dell'editing */
		manageEditing(self, self.editingProperties);
	}

	/**
	 * Il metodo conferma le feature disegnate e da il via al salvataggio chiudendo il iziToast "editingLayerIziToast"
	 */
	confermaFeatureDisegnate(){
		var self = this;
		
		/** DISABILITO IL DISEGNO */
		self.toast = document.getElementById('editingLayerIziToast');
		iziToast.hide({transitionOut: 'fadeOutUp'}, self.toast);

		var writer = new ol.format.WKT();
        var feature = self.editSourceFeatures.getFeatures();
    	var WKT = writer.writeFeatures(feature, {
                    dataProjection: 'EPSG:4326',
                    featureProjection: 'EPSG:3857' 
				});
	
		if((self.editingProperties).newEntita) {
			let anagGeom = { id: null,
							 tipo: self.editingProperties.typeEditing,
							 nome: null,
							 descrizione: null,
							 geom: WKT }
			
			/** AGGIORNO LA LISTA DELLE GEOMETRIE */
			self.geometrieLayer.push(anagGeom);

			/** AGGIUNGO UNA NUOVA RIGA NELLA TABELLA DELLE GEOMETRIE */
			let tabellaGeometria = $('#tabellaGeometrieLivello').DataTable();
			tabellaGeometria.row.add(anagGeom).draw();
			
		} else {
			/** AGGIORNO IL WKT DELLA GEOMETIA APPENA MODIFICATA */
			for(let i in self.geometrieLayer) {
				if(self.geometrieLayer[i].id === (self.editingProperties).idGeometria) {
					self.geometrieLayer[i].geom = WKT;
					/** AGGIORNO LA TABELLA */
					let tabella = $('#'+ self.idDialog + ' #tabellaGeometrieLivello').DataTable();
					tabella.row(i).invalidate().draw();
					/** SBLOCCO GLI INPUT TYPE TEXT DEL NOME E DELLA DESCRIZIONE DELLA TABELLA */
					$('#tabellaGeometrieLivello #nomeGeometria_'+(self.editingProperties).idGeometria).prop('disabled', false);
					$('#tabellaGeometrieLivello #descrizioneGeometria_'+(self.editingProperties).idGeometria).prop('disabled', false);
					
					break;
				}
			}

		}
		/** RIPRISTINO LE IMPOSTAZIONI DI EDITING */
		self.editingProperties = {	geometryWKT : null,
									typeEditing: 'LineString',
									pagina: 'paginaGestioneLayerCtrl',
									idIziToast: 'editingLayerIziToast',
									newEntita: null,
									idGeometria: null }
		
		
	}

}