/**
 * Controller per la modale di import dati catastali
 */ 
class PaginaImportaDatiCtrl extends BaseModaleGestioneDatiCtrl {
	/**
	 * Costruttore
	 */
	constructor( importType ) {
		super('paginaImportaDati','IMPORTA DATI CATASTALI', 'modImportaDati');
		//import type (AGGIORNAMENTO/ATTUALITA)
		this.importType = importType;
		//storage REST API
		this.baseStorageUrl = 
			appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
			appConfig.urbamidService+'catasto/storage';
		//batch management REST API
		this.baseBatchManagementUrl = 
			appConfig.urbamidProtocol+"://"+appConfig.urbamidHostname + (appConfig.urbamidPort!='' ? (":"+ appConfig.urbamidPort) : appConfig.urbamidPort) + 
			appConfig.urbamidService+'catasto/batch';
		this.datiTabella = [
			{id: 'tabellaFileFabbricati', files: [],},
			{id: 'tabellaFileTerreni', files: [],},
			{id: 'tabellaFileCartografia', files: [],},
			{id: 'tabellaFilePlanimetrie', files: [],}
		];
		this.filesAggiunti = [
			{id: 'uploadFabbricatiInput', filesAggiunti: [],},
			{id: 'uploadTerreniInput', filesAggiunti: [],},
			{id: 'uploadCartografiaInput', filesAggiunti: [],},
			{id: 'uploadPlanimetrieInput', filesAggiunti: [],}
		];
		this.inizializzaPagina();
	}
	/**
	 * Metodo che apre la modale ed inizializza (nella funzione di callback all'interno del metodo openStaticDetailViaHandlebars richiamato)
	 * gli elementi della modale. 
	 */
	inizializzaPagina(){
		appUtil.showLoader(null, 'Per favore, attendere il caricamento della pagina.');
		var self = this;
		$.when(
			catastoRest.batchStatus()
		).then(function( risultatiBatchInCorso ){
			if(risultatiBatchInCorso && risultatiBatchInCorso.success){
				if (risultatiBatchInCorso.aaData==null) {
				
					let contextHandlebar = {};
					self.recuperaDatiInizialiPagina().then(function(){
						openStaticDetailViaHandlebars(self.idDialog, self.idScriptHandlebars, contextHandlebar, self.nomePagina, function(){
							self.inizializzaStepper();
							self.inizializzaUploader();
							self.inizializzaDatatable();
							self.aggiungiEventoClickChiudiFinestra();
							self.inizializzaBreadcrumb();
							self.aggiungiEventoClickFaiPartireUploadFile();
							self.aggiungiEventoClickFaiPartireBatchImport();
							appUtil.reloadTooltips();
							appUtil.hideLoader();
						}, {closable:true,  position : { my: "left top", at: "left+70 top+100", of: window, collision: "none"}, marginWidth:80, marginHeight:120});
					},function(messaggioErrore){
						appUtil.hideLoader();
						appUtil.showMessageAlertApplication(messaggioErrore, 'Attenzione', false, true, false, null, null);
					});
				} else {
				
					appUtil.hideLoader();
					appUtil.showMessageAlertApplication("E' in corso un processo di Import del catasto", 'Attenzione', false, true, false, null, null);
				}
					
				$("#headernav").show();
			}
		}, function(xhr, ajaxOptions, thrownError){
			appUtil.hideLoader();
			deferred.reject('Errore');
		});
	}
	/**
	 * 
	 */
	aggiungiEventoClickFaiPartireUploadFile(){
		var self = this;
		$('#avviaUploadFabbricatiButton').off('click').on('click', function(){
			self.gestisciClickUploadFile('uploadFabbricatiInput', 'FABBRICATI');
		});
		$('#avviaUploadTerreniButton').off('click').on('click', function(){
			self.gestisciClickUploadFile('uploadTerreniInput', 'TERRENI');
		});
		$('#avviaUploadCartografiaButton').off('click').on('click', function(){
			self.gestisciClickUploadFile('uploadCartografiaInput', 'CARTOGRAFIA');
		});
		$('#avviaUploadPlanimetrieButton').off('click').on('click', function(){
			self.gestisciClickUploadFile('uploadPlanimetrieInput', 'PLANIMETRIE');
		});
	}
	/**
	 * 
	 */
	gestisciClickUploadFile(idInput, folder){
		var self = this;
		appUtil.showLoader('Per favore, attendi il completamento dell\'upload dei file.');
		let files = self.getFilesAggiuntiPerId(idInput);
		let promises = [];
		var numUpload = 0;
		var numUploadFailed = 0;
		for(let i=0; i<files.length; i++){
			let url = this.baseStorageUrl+'/'+this.importType+'/'+folder; 
			var data = new FormData();
			data.append('file', files[i]);
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: url,
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				async: false,
				timeout: 60000000,
				success: function (data) {
					console.log("SUCCESS : ", data);
					numUpload++;
				},
				error: function (e) {
					numUploadFailed++;
					console.log("ERROR : ", e);
				}
			});
		}
		
		var populateTable = setInterval(function(){
			if ( (numUpload+numUploadFailed)>=files.length ) {
				self.ricaricaDatatables().then(function(){
					appUtil.hideLoader();
					self.resettaUploader();
					appUtil.hideLoader();
					
					if(numUploadFailed>0){
						iziToast.show({
							timeout: 20000,
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
							title: 'UPLOAD',
							message: numUploadFailed  + ' file non sono stati caricati con successo nella cartella ' + folder,
						});
					}else {
						iziToast.show({
							timeout: 20000,
							balloon: false,
							icon:'fa fa-info-circle', 
							animateInside: false,
							theme: 'dark', 
							position: 'topCenter',
							title: 'UPLOAD',
							message: files.length > 1 ? 
									files.length  + ' file sono stati caricati con successo nella cartella ' + folder
									 : 'Il file e\' stato caricato con successo nella cartella ' + folder,
						});
					}
					clearInterval(populateTable);
				});
			}
		}, 2000);
	}
	/**
	 * 
	 */
	uploadFile(file, folder){
		
	}
	/**
	 * Recupero il contenuto iniziale delle cartelle per inizializzare i datatables, e lo stato di esecuzione di
	 * eventuali altri job avviati.
	 */
	recuperaDatiInizialiPagina(){
		var self = this;
		var deferred = $.Deferred();
		$.when(
			self.recuperaFileInCartella('FABBRICATI'), 
			self.recuperaFileInCartella('TERRENI'), 
			self.recuperaFileInCartella('CARTOGRAFIA'),
			self.recuperaFileInCartella('PLANIMETRIE')
		).then(function( responseFabbricati, responseTerreni, responseCartografia,responsePlanimetrie){
			if(responseFabbricati && responseFabbricati[1] === 'success'){
				self.setDatiTabellaPerId('tabellaFileFabbricati', responseFabbricati[0]);
			}
			if(responseTerreni && responseTerreni[1] === 'success'){
				self.setDatiTabellaPerId('tabellaFileTerreni', responseTerreni[0]);
			}
			if(responseCartografia && responseCartografia[1] === 'success'){
				self.setDatiTabellaPerId('tabellaFileCartografia', responseCartografia[0]);
			}
			if(responsePlanimetrie && responsePlanimetrie[1] === 'success'){
				self.setDatiTabellaPerId('tabellaFilePlanimetrie', responsePlanimetrie[0]);
			}
			deferred.resolve(42);
		}, function(xhr, ajaxOptions, thrownError){
			appUtil.hideLoader();
			deferred.reject('Errori nel recupero del contenuto delle cartelle');
		});
		return deferred;
	}
	
	ricaricaDatatables(){
		var self = this;
		var deferred = $.Deferred();
		$.when(
			self.recuperaFileInCartella('FABBRICATI'), 
			self.recuperaFileInCartella('TERRENI'), 
			self.recuperaFileInCartella('CARTOGRAFIA'),
			self.recuperaFileInCartella('PLANIMETRIE')
		).then(function( responseFabbricati, responseTerreni, responseCartografia, responsePlanimetrie){
			if(responseFabbricati && responseFabbricati[1] === 'success'){
				self.setDatiTabellaPerId('tabellaFileFabbricati', responseFabbricati[0]);
			}
			if(responseTerreni && responseTerreni[1] === 'success'){
				self.setDatiTabellaPerId('tabellaFileTerreni', responseTerreni[0]);
			}
			if(responseCartografia && responseCartografia[1] === 'success'){
				self.setDatiTabellaPerId('tabellaFileCartografia', responseCartografia[0]);
			}
			if(responsePlanimetrie && responsePlanimetrie[1] === 'success'){
				self.setDatiTabellaPerId('tabellaFilePlanimetrie', responsePlanimetrie[0]);
			}
			//e che cazzo potevi non fare sta porcata! XD
			let datatableTerreni = $('#'+self.idDialog+' #tabellaFileTerreni').DataTable();
			let datatableFabbricati = $('#'+self.idDialog+' #tabellaFileFabbricati').DataTable();
			let datatableCartografia = $('#'+self.idDialog+' #tabellaFileCartografia').DataTable();
			let datatablePlanimetrie = $('#'+self.idDialog+' #tabellaFilePlanimetrie').DataTable();
			datatableTerreni.clear().draw();
			datatableFabbricati.clear().draw();
			datatableCartografia.clear().draw();
			datatablePlanimetrie.clear().draw();
			if(responseTerreni[0].length > 0)
				$('#'+self.idDialog+' #tabellaFileTerreni').dataTable().fnAddData(responseTerreni[0]);
			if(responseFabbricati[0].length > 0)
				$('#'+self.idDialog+' #tabellaFileFabbricati').dataTable().fnAddData(responseFabbricati[0]);
			if(responseCartografia[0].length > 0)
				$('#'+self.idDialog+' #tabellaFileCartografia').dataTable().fnAddData(responseCartografia[0]);
			if(responsePlanimetrie[0].length > 0)
				$('#'+self.idDialog+' #tabellaFilePlanimetrie').dataTable().fnAddData(responsePlanimetrie[0]);
			datatableTerreni.columns.adjust().draw();
			datatableFabbricati.columns.adjust().draw();
			datatableCartografia.columns.adjust().draw();
			datatablePlanimetrie.columns.adjust().draw();
			self.aggiungiEventiRimuoviFilePerId('tabellaFileFabbricati');
			self.aggiungiEventiRimuoviFilePerId('tabellaFileTerreni');
			self.aggiungiEventiRimuoviFilePerId('tabellaFileCartografia');
			self.aggiungiEventiRimuoviFilePerId('tabellaFilePlanimetrie');
			deferred.resolve(42);
		}, function(xhr, ajaxOptions, thrownError){
			appUtil.hideLoader();
			deferred.reject('Errori nel recupero del contenuto delle cartelle');
		});
		return deferred;
	}
	
	/**
	 * Resetta gli uploader
	 */
	resettaUploader(){
		var self = this;
		let uploaderFabbricati = $('#'+self.idDialog+' #uploadFabbricatiInput').prop('jFiler');
		uploaderFabbricati.reset();
		let uploaderTerreni = $('#'+self.idDialog+' #uploadTerreniInput').prop('jFiler');
		uploaderTerreni.reset();
		let uploaderCartografia = $('#'+self.idDialog+' #uploadCartografiaInput').prop('jFiler');
		uploaderCartografia.reset();
		let uploaderPlanimetrie = $('#'+self.idDialog+' #uploadPlanimetrieInput').prop('jFiler');
		uploaderPlanimetrie.reset();
		for(let i=0; i<self.filesAggiunti.length; i++){
			self.filesAggiunti[i].filesAggiunti = [];
		}
	}
	
	/**
	 * Metodo che effettua la chiamata per il recupero del contenuto di una cartella.
	 */
	recuperaFileInCartella(folder){
		var self = this;
		let url = self.baseStorageUrl+'/'+self.importType+'/'+folder;
		return $.ajax({ 
			type: 'GET',
			url: url,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
		});
	}
	
	recuperaDettaglioJob(idJob){
		var self = this;
		let url = self.baseBatchManagementUrl+'/'+idJob;
		return $.ajax({ 
			type: 'GET',
			url: url,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
		});
	}
	
	/**
	 * Avvia il batch
	 */
	avviaBatchJob(){
		var self = this;
		let url = self.baseBatchManagementUrl+'/'+self.importType.toLowerCase();
		return $.ajax({ 
			type: 'POST',
			url: url,
			dataType : 'json',
			contentType : 'application/json; charset=UTF-8',
		});
	}
	
	/**
	 * Aggiunge un evento di click che si occupa di far "partire" l'upload dei file
	 */
	aggiungiEventoClickFaiPartireBatchImport(){
		var self = this;
		appUtil.showLoader(null, 'Per favore, attendere il completamento dell\'operazione.');
		$('#'+self.idDialog+' #importaDatiBtn').off('click').on('click', function(){
			self.avviaBatchJob().then(function(dettaglioBatch){
				appUtil.hideLoader();
				iziToast.show({
					timeout: 20000,
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'PID',
				    message: 'Il processo di importazione con PID ' + dettaglioBatch.jobExecutionId + ' e\' stato avviato con successo! ',
				});
				self.impostaPollingStatoBatch(dettaglioBatch.jobExecutionId);
			},function(xhr, ajaxOptions, thrownError){
				appUtil.showMessageAlertApplication(xhr.responseText, thrownError, false, true, false, null, null);
				appUtil.hideLoader();
			});
			$('#'+self.idDialog).dialog('destroy').remove();
		});
	}
	
	impostaPollingStatoBatch(idBatchJob){
		var self = this;
		//eventualmente chiudo precedenti polling
		let intervalIdAttuale_string = sessionStorage.getItem('batch.status.polling.interval.id');
		if(intervalIdAttuale_string != null && intervalIdAttuale_string !== ''){
			let intervalIdAttuale = Number(intervalIdAttuale_string);
			clearInterval(intervalIdAttuale);
		}
		var intervalId = setInterval( function(){
			self.recuperaDettaglioJob(idBatchJob).then(function(dettaglioBatch){
				if(self.isJobTerminato(dettaglioBatch)){
					clearInterval(intervalId);
				} else {
					console.log('IL JOB E\' IN ESECUZIONE...');
					//TODO QUI VA FATTO QUALCOSA PER FAR CAPIRE CHE E' IN ESECUZIONE
					iziToast.show({
						timeout: 29000,
						balloon: false,
						icon:'fa fa-info-circle', 
						animateInside: false,
						theme: 'dark', 
						position: 'topCenter',
					    title: 'BATCH',
					    message: 'Importazione catasto in esecuzione...',
					});
				}
			},function(xhr, ajaxOptions, thrownError){
				console.log('errore recupero dettaglio job');
			});
		}, 30000 );
		sessionStorage.setItem('batch.status.polling.interval.id', intervalId);
	}
	
	isJobTerminato(dettaglioBatch){
		if(dettaglioBatch){
			if(dettaglioBatch.stato === 'COMPLETED' && dettaglioBatch.codiceUscita === 'COMPLETED'){
				iziToast.show({
					timeout: 20000,
					balloon: false,
					icon:'fa fa-info-circle', 
					animateInside: false,
					theme: 'dark', 
					position: 'topCenter',
				    title: 'BATCH',
				    message: 'Il processo di import con PID ' + dettaglioBatch.jobExecutionId + ' e\' stato completato con successo! ',
				});
				return true;
			}
			else if( this.ciSonoStepFalliti(dettaglioBatch)){
				appUtil.showMessageAlertApplication('Il processo non e\' stato eseguito. Si prega di riprovare piu\' tardi.', 'Attenzione', false, true, false, null, null);
				return true;
			}
		}
		return false;
	};
	
	isTerminatoStep(dettaglioBatch, nomeStep){
		for(let i=0; i<dettaglioBatch.listaStep.length; i++){
			if(dettaglioBatch.listaStep[i].nomeStep === nomeStep){
				if(dettaglioBatch.listaStep[i].stato === 'COMPLETED' 
					&& dettaglioBatch.listaStep[i].codiceUscita === 'COMPLETED'){
					return true;
				}
			}
		}
		return false;
	}
	
	ciSonoStepFalliti(dettaglioBatch){
		if(dettaglioBatch.stato === 'FAILED'){
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo che inizializza lo stepper
	 */
	inizializzaStepper(){
		var self = this;
		//impostiamo il primo tab ad attivo
		$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
		$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').first().addClass('active');
		$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
		$('#'+self.idDialog+' form div.tab-content div.tab-pane').first().addClass('active');
	    //impostiamo gli eventi di click per passare da un tab all'altro
	    $('#'+self.idDialog+' #stepUploadFabbricati').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(1)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
	    	$('#'+self.idDialog+' #stepUploadFabbricatiDiv').addClass('active');
	    });
	    $('#'+self.idDialog+' #stepUploadTerreni').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(2)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
	    	$('#'+self.idDialog+' #stepUploadTerreniDiv').addClass('active');
	    });
	    $('#'+self.idDialog+' #stepUploadCartografia').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(3)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
	    	$('#'+self.idDialog+' #stepUploadCartografiaDiv').addClass('active');
	    });
	    $('#'+self.idDialog+' #stepUploadPlanimetrie').on('click', function(){
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li').removeClass('active');
	    	$('#'+self.idDialog+' .wizard ul.nav.nav-tabs li:nth-child(4)').addClass('active');
	    	$('#'+self.idDialog+' form div.tab-content div.tab-pane').removeClass('active');
	    	$('#'+self.idDialog+' #stepUploadPlanimetrieDiv').addClass('active');
	    });
	}
	
	inizializzaDatatable(idTabella){
		var self = this;
		self.inizializzaDatatablePerId('tabellaFileFabbricati');
		self.inizializzaDatatablePerId('tabellaFileTerreni');
		self.inizializzaDatatablePerId('tabellaFileCartografia');
		self.inizializzaDatatablePerId('tabellaFilePlanimetrie');
		self.aggiungiEventiRimuoviFilePerId('tabellaFileFabbricati');
		self.aggiungiEventiRimuoviFilePerId('tabellaFileTerreni');
		self.aggiungiEventiRimuoviFilePerId('tabellaFileCartografia');
		self.aggiungiEventiRimuoviFilePerId('tabellaFilePlanimetrie');
	}
	
	aggiungiEventiRimuoviFilePerId(idTabella){
		var self = this;
		$('#'+self.idDialog+' #'+idTabella + ' button.rimuovi-file-btn' ).off('click').on('click',function(){
			let idTabella = $(this).data('id-tabella');
			let folder = null;
			if(idTabella === 'tabellaFileFabbricati') folder = 'FABBRICATI';
			if(idTabella === 'tabellaFileTerreni') folder = 'TERRENI';
			if(idTabella === 'tabellaFileCartografia') folder = 'CARTOGRAFIA';
			if(idTabella === 'tabellaFilePlanimetrie') folder = 'PLANIMETRIE';
			let nomeFile = $(this).data('nome-file');
			let url = self.baseStorageUrl+'/'+self.importType+'/'+folder+'/'+nomeFile;
			appUtil.confirmOperation(function(){
				appUtil.showLoader(null, 'Per favore, attendere il caricamento della pagina.');
				return $.ajax({ 
					type: 'DELETE',
					url: url,
					dataType : 'json',
					contentType : 'application/json; charset=UTF-8',
					success: function(){
						setTimeout(function(){
							self.ricaricaDatatables();
							appUtil.hideLoader();
							iziToast.show({
								timeout: 20000,
								balloon: false,
								icon:'fa fa-done', 
								animateInside: false,
								theme: 'dark', 
								position: 'topCenter',
								title: 'OK',
								message: 'Il file '+ nomeFile +' e\' stato rimosso con successo dalla cartella ' +folder,
							});
						}, 1000);
					},
					error: function(){
						appUtil.hideLoader();
						return appUtil.showMessageAlertApplication('Si e\' verifato un errore durante la cancellazione del file.', 'Attenzione', false, true, false, null, null);
					}
				});
			},function(){
				appUtil.hideLoader();
				return true;
			},null,'Sei sicuro di voler rimuovere il file '+nomeFile+'?');
		});
	}
	
	inizializzaUploader(){
		var self = this;
		self.inizializzaUploaderPerId('uploadFabbricatiInput');
		self.inizializzaUploaderPerId('uploadTerreniInput');
		self.inizializzaUploaderPerId('uploadCartografiaInput');
		self.inizializzaUploaderPerId('uploadPlanimetrieInput');
	}

	getDatiTabellaPerId(idTabella){
		let dati = [];
		for(let i=0; i<this.datiTabella.length; i++){
			if(this.datiTabella[i].id === idTabella){
				dati = this.datiTabella[i].files;
				break;
			}
		}
		return dati;
	}
	
	getFilesAggiuntiPerId(id){
		let filesAggiunti = [];
		for(let i=0; i<this.filesAggiunti.length; i++){
			if(this.filesAggiunti[i].id === id){
				filesAggiunti = this.filesAggiunti[i].filesAggiunti;
				break;
			}
		}
		return filesAggiunti;
	}
	
	setDatiTabellaPerId(idTabella, files){
		for(let i=0; i<this.datiTabella.length; i++){
			if(this.datiTabella[i].id === idTabella){
				this.datiTabella[i].files = files;
				break;
			}
		}
	}
	
	inizializzaDatatablePerId(idTabella){
		var self = this;
		$('#'+self.idDialog+' #'+idTabella).DataTable( {
			data: self.getDatiTabellaPerId(idTabella),
			language: self.datatableLan,
	        paging: true,
	        ordering: true,
	        info: true,
	        searching: true,
	        columnDefs: [
	        	{targets : 0, render: function(d, t, r) {return r.nome;}, orderable : true},
	        	{targets : 1, render: function(d, t, r) {return r.dimensione+' KB';}, orderable : true},
	        	{targets : 2, render: function(d, t, r) {return r.tipo;}, orderable : true},
	        	{targets : 3, render: function(d, t, r) {return r.codiceComune;}, orderable : true},
	        	{targets : 4, render: function(d, t, r) {return r.dataCreazione;}, orderable : true},
	        	{targets : 5, className: "text-right", orderable : false, render: function(d, t, r){
	        		return '<button type="button" data-id-tabella="'+idTabella+'" data-nome-file="'+r.nome+'" data-info="Rimuovi file" class="btn-trasp bttn rimuovi-file-btn"><em class="fa fa-trash"></em></button>';
	        	}}
	        ],
	        drawCallback: function( settings ) {
	        	self.aggiungiEventiRimuoviFilePerId(idTabella);
	        }
	    });
	}
	
	/**
	 * Metodo che aggiunge alla lista dei file i file scelti.
	 * Il metodo accetta come parametro l'array di argomenti che viene dal jQuery.filer.
	 */
	aggiungiElementiAllaListaFileAggiunti(parameters){
		var self = this;
		if(parameters && parameters.length > 0){
			let fileList = parameters[0];
			let idInput = $(parameters[4]).attr('id');
			for(let i=0; i<self.filesAggiunti.length; i++){
				if(self.filesAggiunti[i].id === idInput){
					for(let j=0; j<fileList.length; j++){
						self.filesAggiunti[i].filesAggiunti.push(fileList[j]);	
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Metodo che rimuove dalla lista dei file uno specifico file.
	 * Il metodo accetta come parametro l'array di argomenti che viene dal jQuery.filer.
	 */
	rimuoviElementoDallaTabella(parameters){
		var self = this;
		if(parameters && parameters.length > 0){
			let idInput = $(parameters[6]).attr('id');
			let fileDaRimuovere = parameters[1];
			for(let i=0; i<self.filesAggiunti.length; i++){
				if(self.filesAggiunti[i].id === idInput){
					for(let j=0; j<self.filesAggiunti[i].filesAggiunti.length; j++){
						if( self.filesAggiunti[i].filesAggiunti[j].name === fileDaRimuovere.name){
							self.filesAggiunti[i].filesAggiunti.splice(j, 1);
							break;
						}
					}
					break;
				}
			}
		}
	}
	/**
	 * Metodo che inizializza l'uploader di jQuery.filer
	 */
	inizializzaUploaderPerId(idInput){
		var self = this;
		$('#'+self.idDialog+' #'+idInput).filer({
			limit: null,
			maxSize: null,
			extensions: ['zip'],
			changeInput: '<div class="jFiler-input-dragDrop"><div class="jFiler-input-inner"><div class="jFiler-input-icon"><i class="icon-jfi-cloud-up-o"></i></div><div class="jFiler-input-text"><h3>Trascina e rilascia un file qui</h3> <span style="display:inline-block; margin: 15px 0">oppure</span></div><a class="jFiler-input-choose-btn messina">Scegli File</a></div></div>',
			showThumbs: true,
			theme: "dragdropbox",
			templates: self.templateFiler,
			dragDrop: { dragEnter: null, dragLeave: null, drop: null, dragContainer: null, },
			files: [],
			addMore: true,
			allowDuplicates: false,
			clipBoardPaste: true,
			excludeName: null,
			beforeRender: null,
			afterRender: null,
			beforeShow: function(itemEl, file, id, listEl, boxEl, newInputEl, inputEl){
				self.aggiungiElementiAllaListaFileAggiunti(arguments);
				return true;
			},
			beforeSelect: null,
			onSelect: null,
			afterShow: null,
			onRemove: function(itemEl, file, id, listEl, boxEl, newInputEl, inputEl){
				self.rimuoviElementoDallaTabella(arguments);	//TODO
			},
			onEmpty: null,
			options: null,
			dialogs: {
				alert: function(text) {
					return appUtil.showMessageAlertApplication(text, null, false, true, false, null, null);
				},
				confirm: function (text, callback) {
					appUtil.confirmOperation(callback,null,null,text) ? callback() : null;
				}
			},
			captions: self.captionFiler
		});
	}
}