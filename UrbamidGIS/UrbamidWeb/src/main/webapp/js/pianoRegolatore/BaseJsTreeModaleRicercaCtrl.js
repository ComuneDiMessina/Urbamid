class BaseJsTreeModaleRicercaCtrl extends BaseModaleRicercaCtrl {
	constructor(arg1, arg2, arg3){
		super(arg1, arg2, arg3);
		this.jsTreeMenu = {};
		this.hrefListaAreeTematiche = appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "workspaces/"+appConfig.workspaceGeoserver+"/layergroups.json";
		this.idPrimoNodo = "id_primo_nodo" + new Date().getTime();
		//servono per determinare le icone di jstree (se abilitato il plugin 'types'
		this.tipiJstree = {
			ente: 		{icon: "glyphicon glyphicon-home"},
		    documento:  {icon: "./images/documento.png"},
		    layer: 		{icon: "./images/layer.png"},
		    "default": 	{icon: "./images/folder.png"},
		}
		//oggetto usato per i primi nodi della struttura del piano ( ente -> documenti/strati informativi)
		this.primoNodoStrutturaPiano = [{
			id : 'ente', 
			text : 'Messina', 
			data: {ente: true},
			state: {opened: true},
			type: 'ente',
			children: [{
				id : 'documenti',
				text : 'Documenti', 
				data: {documenti: true},
				//state: {opened: true},
				type: 'cartella',
				children: true,
			},{
				id : this.idPrimoNodo,
				text : 'Strati informativi', 
				data: {layers: true},
				children: true,
				type: 'cartella'
			}],
			href: appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "workspaces/"+appConfig.workspaceGeoserver+"/layergroups.json",
		}];
		//oggetto usato come primo nodo per gli strati informativi
		this.primoNodo = [{
			text : "Strati informativi", 
			id : this.idPrimoNodo, 
			children: true,
			data: {layers: true},
			href: appUtil.getOrigin() + appConfig.geoserverService + appConfig.restService + "workspaces/"+appConfig.workspaceGeoserver+"/layergroups.json",
		}];
		this.captionFiler = {
			button: "Scegli i file",
			feedback: "Scegli i file da caricare",
			feedback2: "I files sono stati scelti",
			drop: "Rilascia il file qui per caricarlo",
			removeConfirmation: "Sei sicuro di voler rimuovere il file?",
			errors: {
				filesLimit: "Solo {{fi-limit}} file sono al pi&#250; consentiti per il caricamento.",
				filesType: "&#201; consentito il caricamento di solo file di tipo immagine.",
				filesSize: "{{fi-name}} &egrave; troppo grande! Per favore carica file di dimensioni fino a {{fi-maxSize}} MB.",
				filesSizeAll: "I file che hai scelto sono troppo grandi! Per favore carica file di dimensioni fino a {{fi-maxSize}} MB."
			}
		};
	}
	isListaAreeTematicheValida(listaAreeTematiche){
		return (listaAreeTematiche!=null && listaAreeTematiche.layerGroups!=null && listaAreeTematiche.layerGroups.layerGroup!=null) ? true : false;
	}
	creaMenuDocumenti(documenti) {
		return {
			id: 'documenti',
			text: documenti.codice + " - " + documenti.descrizione,
			data: {documenti: true},
			children: true,
			type: 'cartella'
		}
	}
	creaItemMenu(dettaglioAreaTematica){
		if( dettaglioAreaTematica.layerGroup ){
			return {
				id: appUtil.generateItemId("",dettaglioAreaTematica.layerGroup.name), 
				text: dettaglioAreaTematica.layerGroup.title,
				dettaglioAreaTematica: dettaglioAreaTematica,
				data: {layers: true},
				children: true,		//TODO
				type: 'cartella'
			};
		} else if( dettaglioAreaTematica.layer ){
			return {
				id: appUtil.generateItemId("",dettaglioAreaTematica.layer.name), 
				text: dettaglioAreaTematica.layer.name,
				dettaglioAreaTematica: dettaglioAreaTematica,
				children: false,
				data: {
					layer: true,
					esportato: '02/05/2018',	
					importato: '03/06/2017',
				},
				type: 'layer'
			};
		}
	}
	scartaAreeTematicheSenzaSuffisso_areaTematica(areeTematiche){
		let nuovoArray = [];
		for(var i=0; i<areeTematiche.length; i++){
			if ((areeTematiche[i].name).indexOf("_areaTematica") != -1) {
				nuovoArray.push(areeTematiche[i]);
			}
		}
		return nuovoArray;
	}
	listaChiamateRecuperaDettagli(areeTematiche){
		let chiamateAjax = [];
		for(var i=0; i<areeTematiche.length; i++){
			chiamateAjax.push(pianoRegolatoreRest.recuperaDettaglioAreaTematica(areeTematiche[i]));
		}
		return chiamateAjax;
	}
	recuperaDocumenti() {
		var menu = [];
		var result = [];
		var self = this;
		var deferred = $.Deferred();
		pianoRegolatoreRest.recupera().done(function(response) {
			$.each( response.aaData, function( key, item ) {
				menu.push(self.creaMenuDocumenti(item));
			});
		});
		return deferred.resolve(menu);
	}

	recuperaSottoMenuStratiInformativi(){
		var self = this;
		var deferred = $.Deferred();
		pianoRegolatoreRest.recuperaListaAreeTematiche(this.hrefListaAreeTematiche).then( function(responseListaAreeTematiche){
			if(self.isListaAreeTematicheValida(responseListaAreeTematiche)){
				var areeTematiche = self.scartaAreeTematicheSenzaSuffisso_areaTematica(responseListaAreeTematiche.layerGroups.layerGroup);
				$.when.apply($, self.listaChiamateRecuperaDettagli(areeTematiche)).done(function(){
					var listaResponseDettagli = arguments;
					var menu = [];
					for(var i=0; i<listaResponseDettagli.length; i++){
						let dettaglio = listaResponseDettagli[i][0];
						if(dettaglio != null && dettaglio.layerGroup != null){
							menu.push( self.creaItemMenu(dettaglio) );
						}
					}
					deferred.resolve(menu);
				});
			} else {
				deferred.reject('ko');
			}
		},function(){
			deferred.reject('ko');
		});
		return deferred;
	}
	recuperaSottoMenuNodo( dettaglioAreaTematica ){
		var self = this;
		//prendismo tutti gli href
		var deferred = $.Deferred();
		var menu = [];
		//recupero il dettaglio del nodo attuale
		if(dettaglioAreaTematica.layerGroup 
				&& dettaglioAreaTematica.layerGroup.publishables
					&& dettaglioAreaTematica.layerGroup.publishables.published){
			if($.isArray(dettaglioAreaTematica.layerGroup.publishables.published)){
				//ARRAY DI DETTAGLI
				let listaChiamateAjax = [];
				$.each(dettaglioAreaTematica.layerGroup.publishables.published, function(index, valore){
					listaChiamateAjax.push( pianoRegolatoreRest.recuperaDettaglioAreaTematica( dettaglioAreaTematica.layerGroup.publishables.published[index]) );
				});
				$.when.apply($, listaChiamateAjax).done(function(){
					var listaResponseDettagli = arguments;
					var menu = [];
					for(var i=0; i<listaResponseDettagli.length; i++){
						let dettaglio = listaResponseDettagli[i][0];
						menu.push( self.creaItemMenu(dettaglio) );
					}
					deferred.resolve(menu);
				});
			} else {
				//DETTAGLIO SINGOLO
				pianoRegolatoreRest.recuperaDettaglioAreaTematica(dettaglioAreaTematica.layerGroup.publishables.published).then(function(dettaglio){
					menu.push(self.creaItemMenu(dettaglio));
					deferred.resolve(menu);
				},function(error){
					deferred.reject('42');
				});
			}
		} else {
			deferred.resolve([]);
		}
		return deferred;
	}
	inizializzaJsTree(idDivJsTree, idInputRicerca, isRicercaAbilitata) {
		var self = this;
		self.jsTreeMenu = $(idDivJsTree).jstree({
			core : {
				data : function (node, cb) {
					if(node.id === "#") {
						//primo nodo è indicato come "strati informativi"
						cb(self.primoNodo);
					} else if( node.id === self.idPrimoNodo){
						//sotto il primo nodo ci sta il primo menu di base
						self.recuperaSottoMenuStratiInformativi().then(function(menu){
							cb(menu);
						}, function(error){
							appUtil.showMessageAlertApplication(
									'Si &egrave; verificato un errore di comunicazione con Geoserver. La preghiamo di riprovare pi&ugrave; tardi.', 'ERRORE', 
									false, true, false, null, null);
						});
					} else {
						//sotto il menu di base si recuperano mano mano i vari sottomenu
						let dettaglioAreaTematica = node.original.dettaglioAreaTematica;
						self.recuperaSottoMenuNodo(dettaglioAreaTematica).then(function(menu){
							cb(menu);
						}, function(){
							appUtil.showMessageAlertApplication(
									'Si &egrave; verificato un errore di comunicazione con Geoserver. La preghiamo di riprovare pi&ugrave; tardi.', 'ERRORE', 
									false, true, false, null, null)
						});
					}
				}
			},
			plugins: ['checkbox', 'sort', 'search', 'types'],
			types: self.tipiJstree,
		});

		//N.B. non ci prendiamo carico di nascondere l'input di ricerca dalla pagina
		//nel caso in cui la ricerca non è abilitata
		if(isRicercaAbilitata){
			$(idInputRicerca).keyup(function () {
				if(self.timeoutRicerca) { clearTimeout(self.timeoutRicerca); }
			    self.timeoutRicerca = setTimeout(function () {
			    	var valore = $(idInputRicerca).val();
			    	$(idDivJsTree).jstree('search', valore);
			    }, 250);
			 });
		}
	}
	inizializzaJsTreeGrid(idDivJsTree, idInputRicerca, isRicercaAbilitata) {
		var self = this;
		self.jsTreeMenu = $('#'+self.idDialog+' '+ idDivJsTree).jstree({
			core : {
				data : function (node, cb) {
					if(node.id === "#") {
						//primo nodo è indicato come "strati informativi"
						cb(self.primoNodo);
					} else if( node.id === self.idPrimoNodo){
						//sotto il primo nodo ci sta il primo menu di base
						self.recuperaSottoMenuStratiInformativi().then(function(menu){
							cb(menu);
						}, function(error){
							appUtil.showMessageAlertApplication(
									'Si &egrave; verificato un errore di comunicazione con Geoserver. La preghiamo di riprovare pi&ugrave; tardi.', 'ERRORE', 
									false, true, false, null, null);
						});
					} else {
						//sotto il menu di base si recuperano mano mano i vari sottomenu
						let dettaglioAreaTematica = node.original.dettaglioAreaTematica;
						self.recuperaSottoMenuNodo(dettaglioAreaTematica).then(function(menu){
							cb(menu);
						}, function(){
							appUtil.showMessageAlertApplication(
									'Si &egrave; verificato un errore di comunicazione con Geoserver. La preghiamo di riprovare pi&ugrave; tardi.', 'ERRORE', 
									false, true, false, null, null)
						});
					}
				}
			},
			plugins: ['checkbox', 'sort', 'search', 'grid', 'types'],
			types: self.tipiJstree,
			grid: {
				columns: [
					{
						width: 750,
						header: "Strato informativo", 
						title:"_DATA_"
					}, {
						width: 200,
						value: "esportato", 
						header: "Data esportazione", 
					}, {
						width: 200,
						value: "importato", 
						header: "Data importazione"
					}],
				resizable:true,
			},
			checkbox: {
				cascade: false,
			}
		});
		//N.B. non ci prendiamo carico di nascondere l'input di ricerca dalla pagina
		//nel caso in cui la ricerca non è abilitata
		if(isRicercaAbilitata){
			$('#'+self.idDialog+' '+ idInputRicerca).keyup(function () {
				if(self.timeoutRicerca) { clearTimeout(self.timeoutRicerca); }
			    self.timeoutRicerca = setTimeout(function () {
			    	var valore = $('#'+self.idDialog+' '+ idInputRicerca).val();
			    	$('#'+self.idDialog+' '+ idDivJsTree).jstree('search', valore);
			    }, 250);
			 });
		}
	}
	
	inizializzaJsTreeStrutturaPiano(idDivJsTree) {
		var self = this;
		self.jsTreeMenu = $('#'+self.idDialog+' '+ idDivJsTree).jstree({
			core : {
				data : function (node, cb) {
					if(node.id === "#") {
						self.recuperaDocumenti();
						//primo nodo è indicato come "strati informativi"
						cb(self.primoNodoStrutturaPiano);
					} else if( node.id === self.idPrimoNodo){
						//sotto il primo nodo ci sta il primo menu di base
						self.recuperaSottoMenuStratiInformativi().then(function(menu){
							cb(menu);
						}, function(error){
							appUtil.showMessageAlertApplication(
									'Si &egrave; verificato un errore di comunicazione con Geoserver. La preghiamo di riprovare pi&ugrave; tardi.', 'ERRORE', 
									false, true, false, null, null);
						});
					} else {
						//sotto il menu di base si recuperano mano mano i vari sottomenu
						let dettaglioAreaTematica = node.original.dettaglioAreaTematica;
						self.recuperaSottoMenuNodo(dettaglioAreaTematica).then(function(menu){
							cb(menu);
						}, function(){
							appUtil.showMessageAlertApplication(
									'Si &egrave; verificato un errore di comunicazione con Geoserver. La preghiamo di riprovare pi&ugrave; tardi.', 'ERRORE', 
									false, true, false, null, null)
						});
					}
				}
			},
			plugins: ['sort', 'contextmenu', 'types'],
			types: self.tipiJstree,
			contextmenu : {
				items: function(node){
					var items = {};
					//TODO dovrei spostarli tutti questi item... o almeno non ricrearli ogni volta...
					let itemAggiungiDocumento = {
						label: 'Aggiungi Documento',
						icon: "glyphicon glyphicon-plus text-success",
				        action: function () { 
				        	self.apriModaleCreaModificaTipoDocumento();
				        }
					};
					let itemModificaDocumento = {
						label: 'Modifica Documento',
						icon: "glyphicon glyphicon-pencil text-warning",
						action: function(){
							self.apriModaleCreaModificaTipoDocumento(node.text);
						}
					};
					let itemEliminaDocumento = {
						label: 'Elimina Documento',
						icon: "glyphicon glyphicon-remove text-danger",
						action: function(){
							self.apriModaleConfermaEliminazione();
						}		
					}
					let itemAggiungiGruppo = {
						label: 'Aggiungi Gruppo',
						icon: "glyphicon glyphicon-plus text-success",
						action: function(){
							self.apriModaleCreaModificaGruppo();
						}	
					};
					let itemModificaGruppo = {
						label: 'Modifica Gruppo',
						icon: "glyphicon glyphicon-pencil text-warning",
						action: function(){
							self.apriModaleCreaModificaGruppo(node.text);
						}	
					};
					let itemEliminaGruppo = {
						label: 'Elimina Gruppo',
						icon: "glyphicon glyphicon-remove text-danger",
						action: function(){
							self.apriModaleConfermaEliminazione();
						}	
					}
					let itemAggiungiLayer = {
						label: 'Aggiungi Layer',
						icon: "glyphicon glyphicon-plus text-success",
						action: function(){
							self.apriModaleCreaModificaLayer();
						}	
					};
					let itemModificaLayer = {
						label: 'Modifica Layer',
						icon: "glyphicon glyphicon-pencil text-warning",
						action: function(){
							self.apriModaleCreaModificaLayer(node);
						}	
					};
					let itemEliminaLayer = {
						label: 'Elimina Layer',
						icon: "glyphicon glyphicon-remove text-danger",
						action: function(){
							self.apriModaleConfermaEliminazione();
						}	
					}
					let itemVisualizzaLayer = {
						label: 'Visualizza Layer',
						icon: "glyphicon glyphicon-eye-open text-success",
						action: function(){
							let nome = node.original.dettaglioAreaTematica.layer.name;
							let href = node.original.dettaglioAreaTematica.layer.resource.href;
							self.addLayerVisualizzatore(nome, href);
						}	
					}
					//costruisco le voci di menu a seconda del tipo di nodo su cui si è cliccato
					if( node.data.documento ){
						items.itemModificaDocumento = itemModificaDocumento;
						items.itemEliminaDocumento = itemEliminaDocumento;
					} else if( node.data.documenti ){
						items.itemAggiungiDocumento = itemAggiungiDocumento;
					} else if( node.data.ente){
						items.itemAggiungiDocumento = itemAggiungiDocumento;
					} else if( node.data.layers ){
					} else if( node.data.layer ){
						items.itemVisualizzaLayer = itemVisualizzaLayer;
					}
					//il click sul tasto destro vale anche come selezione
					self.mostraPulsantiAzioneSuNodo(node);
					return items;
				}
			},
		});
		
		self.jsTreeMenu.on("select_node.jstree", function(event, data){
			//selected node object: data.node;
			self.mostraPulsantiAzioneSuNodo(data.node);
		});
		
	}
	
	/**
	 * Costruisce il menu contestuale del jstree, ovvero il menu che esce cliccando col tasto
	 * destro su di un nodo.
	 */
	costruisciMenuContestuale(){
		//TODO
	}
	
	/**
	 * Metodo invocato quando si seleziona un nodo e serve per mostrare i pulsanti azione 
	 * associato con un determinato nodo
	 * 
	 * TODO sta parte di codice è proprio inguardabile... vedere di sistemarla un po'...
	 */
	mostraPulsantiAzioneSuNodo(node){
		var self = this;
		$('#'+self.idDialog+' .context-btn').parent('li').hide();
		$('#'+self.idDialog+' .context-btn').off('click');
		if( node.data.documento ){
			$('#'+self.idDialog+' .context-btn.modifica-documento-btn').parent('li').show();
			$('#'+self.idDialog+' .context-btn.modifica-documento-btn').on('click', function(){
				self.apriModaleCreaModificaTipoDocumento(node.text);
			});
			$('#'+self.idDialog+' .context-btn.rimuovi-documento-btn').parent('li').show();
			$('#'+self.idDialog+' .context-btn.rimuovi-documento-btn').on('click', function(){
				self.apriModaleConfermaEliminazione();
			});
		} else if( node.data.documenti ){
			$('#'+self.idDialog+' .context-btn.aggiungi-documento-btn').parent('li').show();
			$('#'+self.idDialog+' .context-btn.aggiungi-documento-btn').on('click', function(){
				self.apriModaleCreaModificaTipoDocumento();
			});
		} else if( node.data.ente){
			$('#'+self.idDialog+' .context-btn.aggiungi-documento-btn').parent('li').show();
			$('#'+self.idDialog+' .context-btn.aggiungi-documento-btn').on('click', function(){
				self.apriModaleCreaModificaTipoDocumento();
			});
		} else if( node.data.layers ){
		} else if( node.data.layer ){
			$('#'+self.idDialog+' .context-btn.visualizza-layer-btn').parent('li').show();
			$('#'+self.idDialog+' .context-btn.visualizza-layer-btn').on('click', function(){
				let nome = node.original.dettaglioAreaTematica.layer.name;
				let href = node.original.dettaglioAreaTematica.layer.resource.href;
				self.addLayerVisualizzatore(nome, href);
			});
		}
	}
	
	apriModaleCreaModificaTipoDocumento(tipo){
		var self = this;
		var messaggioModale = tipo ? 'Il tipo documento &egrave; stato aggiornato con successo.' : 'Il tipo documento &egrave; stato creato con successo.';
		var titolo = tipo ? 'MODIFICA TIPO DOCUMENTO' : 'CREA NUOVO TIPO DOCUMENTO';
    	openStaticDetailViaHandlebars(self.idDialog+'creaModificaTipoDocumento', 'modaleCreaModificaTipoDocumento', {}, titolo, function(){
    		appUtil.reloadTooltips();
   			$('#'+self.idDialog+'creaModificaTipoDocumento #codice').val(tipo);
			$('#'+self.idDialog+'creaModificaTipoDocumento #descrizione').val(tipo);
			$('#'+self.idDialog+'creaModificaTipoDocumento #descrizioneCDU').val('');
			$('#'+self.idDialog+'creaModificaTipoDocumento #note').val('');
    		$('#'+self.idDialog+'creaModificaTipoDocumento #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaTipoDocumento').dialog('close');
    		});
    		$('#'+self.idDialog+'creaModificaTipoDocumento #salvaBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaTipoDocumento').dialog('close');
    			iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: messaggioModale,
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
    		});
    	}, {width: 500, height: 360, resizable: false, modale: true, closable: true});
	}
	
	apriModaleConfermaEliminazione(){
		appUtil.confirmOperation( function(){
			//CONFERMA
			appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
			setTimeout(function(){
				appUtil.hideLoader();
				iziToast.show({
					title: 'OK',
					theme: 'dark',
					icon:'fa fa-check',
					message: 'L\'elemento &egrave; stata rimosso con successo.',
					animateInside: false,
					position: 'topCenter',
				});
			}, 1500);
		}, function(){
			//ANNULLA
		}, {}, 'Sei sicuro di voler procedere con la cancellazione dell\'elemento?');
	}
	
	apriModaleCreaModificaGruppo(descrizione){
		var self = this;
		var messaggioModale = descrizione ? 'Il gruppo &egrave; stato aggiornato con successo.' : 'Il gruppo &egrave; stato creato con successo.';
		var titolo = descrizione ? 'MODIFICA GRUPPO' : 'CREA NUOVO GRUPPO';
    	openStaticDetailViaHandlebars(self.idDialog+'creaModificaGruppo', 'modaleCreaModificaGruppo', {}, titolo, function(){
    		appUtil.reloadTooltips();
			$('#'+self.idDialog+'creaModificaGruppo #descrizione').val(descrizione);
			$('#'+self.idDialog+'creaModificaGruppo #note').val('');
    		$('#'+self.idDialog+'creaModificaGruppo #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaGruppo').dialog('close');
    		});
    		$('#'+self.idDialog+'creaModificaGruppo #salvaBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaGruppo').dialog('close');
    			iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: messaggioModale,
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
    		});
    	}, {width: 500, height: 250, resizable: false, modale: true, closable: true});
	}
	
	apriModaleCreaModificaLayer(node){
		var self = this;
		var messaggioModale = node ? 'Lo strato informativo &egrave; stato aggiornato con successo.' : 'Lo strato informativo &egrave; stato creato con successo.';
		var titolo = node ? 'MODIFICA STRATO INFORMATIVO' : 'CREA NUOVO STRATO INFORMATIVO';
    	openStaticDetailViaHandlebars(self.idDialog+'creaModificaLayer', 'modaleCreaModificaLayer', {}, titolo, function(){
    		appUtil.reloadTooltips();
    		let opzioniUploader = {
    			limit: 1,
    			showThumbs: true,
    			addMore: true,
    			allowDuplicates: false,
    			dialogs: {
    				alert: function(text) {
    					return appUtil.showMessageAlertApplication(text, null, false, true, false, null, null);
    				},
    				confirm: function (text, callback) {
    					appUtil.confirmOperation(callback,null,null,text) ? callback() : null;
    				}
    			},
    			captions: self.captionFiler
    		};
    		$('#'+self.idDialog+'creaModificaLayer'+' #shapefileUpload').filer(opzioniUploader);
    		
    		
			$('#'+self.idDialog+'creaModificaLayer #descrizione').val('');
			$('#'+self.idDialog+'creaModificaLayer #note').val('');
    		$('#'+self.idDialog+'creaModificaLayer #chiudiBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaLayer').dialog('close');
    		});
    		$('#'+self.idDialog+'creaModificaLayer #salvaBtn').off('click').on('click', function(){
    			$('#'+self.idDialog+'creaModificaLayer').dialog('close');
    			iziToast.show({
	    			title: 'OK',
	    			theme: 'dark',
	    			icon:'fa fa-check',
	    			message: messaggioModale,
	    			animateInside: false,
	    			position: 'topCenter',
	    		});
    		});
    	}, {width: 700, height: 600, closable: true});
		
	}
	
	addLayerVisualizzatore(layerName, hrefDetail) {
		var itemViewerId = appUtil.generateId(layerName);
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		setTimeout(function(){ 
			if ( appUtil.containsString(appUrbamid.defaultLayerAddedView, layerName)
					|| appUtil.containsString(appUrbamid.layerAddedView, layerName) ) {
				let checkbox = $("#itemViewer_"+itemViewerId).find('input:checkbox');
				if( $(checkbox).prop('checked') === false || $(checkbox).prop('checked') === undefined){
					$(checkbox).trigger('change');
					$(checkbox).prop('checked', true);
				}
			} else {
				/**Aggiungo il tag dell'item legenda layer**/
				if ( appUrbamid.layerAddedView.length==0 ) $("#enable-layer").html("");
				var enableLayerHtml = "<div id=\"itemViewer_"+itemViewerId+"\" class='itemViewerContainer'></div>";
				$("#enable-layer").append(enableLayerHtml);
				appUrbamid.layerAddedView.push(layerName);
				if ( $("#visualizzatore").hasClass("showHide") ) appMappa.toggleVisualizzatore('visualizzatore', event);
				
				var responseLayerDetail = appRest.restGeoserver ( hrefDetail );
				
				/**Valorizzo l'item legenda layer**/
				var itemCheckId 	= appUtil.generateId(layerName); 
				var hrefLegend		= appUtil.getOrigin() + appConfig.geoserverService + appConfig.wmsService + "?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER="+appConfig.workspaceGeoserver+":"+layerName;
				
				var itemViewer = {};
				if ( responseLayerDetail.featureType!=null )
			    	itemViewer = {"title":responseLayerDetail.featureType.title, "layerName":layerName, "hrefLegend":hrefLegend, "visibilita":true, "itemCheckId":itemCheckId};
				else if ( responseLayerDetail.coverage!=null )
			    	itemViewer = {"title":responseLayerDetail.coverage.title, "layerName":layerName, "hrefLegend":hrefLegend, "visibilita":true, "itemCheckId":itemCheckId};				    	
				$("#itemViewer_"+itemViewerId).html( compilaTemplate("itemViewer", itemViewer) );
				//pezzottone fatto da Francesco Esposito che setta il checkbox come checkato e
				//lancia un evento di change che... fondamentalmente richiama la addLayer()
				//simulando un click sul checkbox
				let checkbox = $("#itemViewer_"+itemViewerId).find('input:checkbox');
				
				$(checkbox).trigger('change');
				$(checkbox).prop('checked', true);
			}
			appUtil.hideLoader();
		}, 1000);
	}
	
}