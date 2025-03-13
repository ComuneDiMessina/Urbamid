<style type="text/css">
	.dataTables_wrapper{width: 100%;}
	.dataTables_length {
		float: right!important;
		display: block!important;
		position: absolute;
		right: 320px;
	}
	.dataTables_filter{ display: block!important; }

	.dataTables_filter label{
		inline-size: min-content;
    	margin-top: 5px;
    	margin-bottom: 20px;
    	text-transform: uppercase;
    	text-align: left;
	}

	.dataTables_filter input{
		background: #fff;
		border: 1px solid #adadad;
		height: 40px;
		width: 240px;
		border-radius: 0px;
		float: left;
		margin: 0px!important;
		padding: 0px;
		margin-right: 9px!important;
	} 
	.dataTables_length label{
		inline-size: min-content;
		margin-top: -20px;
	}   
	.dataTables_length select{
		background: #fff;
		width: 260px;
		height: 40px;
		border: 1px solid #adadad;
		margin-right: 15px;
		border-radius: 0px;
		margin-left: -2px;
	}
		div.dt-buttons {
		float: right;
	}
		div.dt-buttons button {
		border: 1px solid #004275;
		border-radius: 0px;
	}
	.dataTables_wrapper{
	margin-top: 10px!important;
	}

	.dataTables_wrapper .dataTables_length, .dataTables_wrapper .dataTables_filter, .dataTables_wrapper .dataTables_info, .dataTables_wrapper .dataTables_processing, .dataTables_wrapper .dataTables_paginate {
		color: #004275!important;
	}

	.custom-control-input:checked~.custom-control-label::before {
		color: #fff;
		border-color: #004275!important;
		background-color: #004275!important;
	}

	.header button {
		min-width: 130px!important;
	}

	table.dataTable tbody tr:hover td {
		background-color: #e1dedea8 !important;
	}
</style>
<script id="templatePulsantiTabellaVarianti" type="text/x-handlebars-template">
<div class="btn-group" role="group" aria-label="Azioni">
	<button type="button" data-id="{{id}}" class="bttn btn-trasp dettaglio-variante-btn" data-info="Vai al dettaglio"><em class="fa fa-arrow-right"></em></button>
	<button type="button" data-id="{{id}}" class="bttn btn-trasp storico-variante-btn" data-info="Mostra lo storico"><em class="fa fa-history"></em></button>
	<!-- <button type="button" data-id="{{id}}" class="bttn btn-trasp cambia-proprietario-btn" data-info="Cambia utente proprietario"><em class="fa fa-user"></em></button> -->
	<button type="button" data-id="{{id}}" class="bttn btn-trasp rimuovi-variante-btn" data-info="Rimuovi la variante"><em class="fa fa-trash-o"></em></button>
</div>
</script>
<script id="templatePulsantiTabellaDocumenti" type="text/x-handlebars-template">
<div class="btn-group" role="group" aria-label="Azioni">
	<button type="button" data-id="{{id}}" class="bttn btn-trasp dettaglio-documento-btn" data-info="Mostra dettaglio con associazioni"><em class="fa fa-arrow-right"></em></button>
	<button type="button" data-id="{{id}}" class="bttn btn-trasp download-documento-btn" data-info="Visualizza documento"><em class="fa fa-download"></em></button>
	<button type="button" data-id="{{id}}" class="bttn btn-trasp rimuovi-documento-btn" data-info="Rimuovi il documento"><em class="fa fa-trash-o"></em></button>
</div>
</script>
<script id="templatePulsantiTabellaDocumentiVarianti" type="text/x-handlebars-template">
<div class="btn-group" role="group" aria-label="Azioni">
	<!-- <button type="button" data-id="{{id}}" class="bttn btn-trasp nuova-versione-documento-btn" data-info="Aggiungi nuova versione"><em class="fa fa-plus"></em></button> -->
	<button type="button" data-id="{{id}}" class="bttn btn-trasp modifica-versione-documento-btn" data-info="Modifica documento"><em class="fa fa-pencil"></em></button>
	<button type="button" data-id="{{id}}" class="bttn btn-trasp rimuovi-documento-btn" data-info="Rimuovi documento"><em class="fa fa-trash-o"></em></button>
	<button type="button" data-id="{{id}}" class="bttn btn-trasp download-documento-btn" data-info="Visualizza documento"><em class="fa fa-download"></em></button>
	<!-- <button type="button" data-id="{{id}}" class="bttn btn-trasp gestisci-associazioni-btn" data-info="Gestisci associazioni con entita' cartografiche"><em class="fa fa-cog"></em></button> -->
</div>
</script>

<script id="templatePulsantiTabellaDocumentiVariantiApprovata" type="text/x-handlebars-template">
<div class="btn-group" role="group" aria-label="Azioni">
	<button type="button" data-id="{{id}}" class="bttn btn-trasp download-documento-btn" data-info="Visualizza documento"><em class="fa fa-download"></em></button>
</div>
</script>

<script id="templatePulsantiTipoDocumento" type="text/x-handlebars-template">
<div class="btn-group" role="group" aria-label="Azioni">
	<button type="button" data-id="{{id}}" class="bttn btn-trasp vai-dettaglio-btn" data-info="Mostra il dettaglio"><em class="fa fa-arrow-right"></em></button>
	<button type="button" data-id="{{id}}" data-codice="{{codice}}" class="bttn btn-trasp rimuovi-btn" data-info="Rimuovi tipo documento"><em class="fa fa-trash-o"></em></button>
</div>
</script>

<script id="templatePulsantiTabellaInterrogazionePiano" type="text/x-handlebars-template">
	<div class="btn-group" role="group" aria-label="Azioni">
		<button type="button" data-id="{{id}}" class="bttn btn-trasp localizza-btn" data-info="Localizza"><em class="fa fa-map-marker"></em></button>
	</div>
</script>

<script id="templatePulsantiTabellaAnagraficaCdu" type="text/x-handlebars-template">
<div class="btn-group" role="group" aria-label="Azioni">
	<button type="button" data-id="{{id}}" class="bttn btn-trasp download-documento-btn" data-info="Visualizza documento"><em class="fa fa-download"></em></button>
</div>
</script>

<!-- 
 _    _____    ____  _______    _   ____________
| |  / /   |  / __ \/  _/   |  / | / /_  __/  _/
| | / / /| | / /_/ // // /| | /  |/ / / /  / /  
| |/ / ___ |/ _, _// // ___ |/ /|  / / / _/ /   
|___/_/  |_/_/ |_/___/_/  |_/_/ |_/ /_/ /___/   
                                                
-->
<script id="gestioneVariantiPiano" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation"><a href="#" role="tab" data-toggle="tab" id="menuRicercaVarianti">Ricerca</a></li>
		<li role="presentation"><a href="#" role="tab" data-toggle="tab" id="menuAggiungiVariante">Aggiungi</a></li>
	</ul>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li class="active"><a href="#">Varianti</a></li>
				</ul>
			</div>
			<div id="ricerca" class="">
				<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading">
						  <h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri1">
								<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
							</a>
						  </h4>
						</div>
						<div id="collapseFiltri1" class="panel-collapse collapse in show">
							<div class="panel-body row nom">
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Ente di riferimento</label>
									<select class="form-control" id="enteRiferimentoVariante" name="enteRiferimentoVariante">
										<option value="F158" selected>Messina</option>
									</select>   
								</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Nome</label>
									<input id="nomeVariante" type="text" class="form-control" name="nomeVariante" placeholder="Nome...">
								</div>
								<div class="col-sm-12 col-md-6 col-lg-6">
									<label>Descrizione</label>
									<input id="descrizioneVariante" type="text" class="form-control" name="descrizioneVariante" placeholder="Descrizione...">  
								</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Data adozione dal</label>
									<div class="input-group">
										<input id="dataAdozioneDalVariante" type="text" class="form-control" name="dataAdozioneDalVariante" placeholder="Data adozione dal...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
									</div>
	    						</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Data adozione al</label>
									<div class="input-group">
										<input id="dataAdozioneAlVariante" type="text" class="form-control" name="dataAdozioneAlVariante" placeholder="Data adozione al...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
									</div>
								</div> 
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Data approvazione dal</label>
									<div class="input-group">
										<input id="dataApporvazioneDalVariante" type="text" class="form-control" name="dataApporvazioneDalVariante" placeholder="Data approvazione dal...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
									</div>
	    						</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Data approvazione al</label>
									<div class="input-group">
										<input id="dataApporvazioneAlVariante" type="text" class="form-control" name="dataApporvazioneAlVariante" placeholder="Data approvazione al...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
		    						</div>   
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaRisultatiVariante" style="width:100%">
						<thead>
					    	<tr>
					      		<th>Nome</th>
					      		<th>Data adozione</th>
					      		<th>Data approvazione</th>
					      		<th>Descrizione</th>
					      		<th></th>
					    	</tr>
					  	</thead>
					  	<tbody></tbody>
						<tfoot>
			            	<tr>
					      		<th>Nome</th>
					      		<th>Data adozione</th>
					      		<th>Data approvazione</th>
					      		<th>Descrizione</th>
					      		<th></th>
					    	</tr>
			        	</tfoot>
					</table>
				</div>
			</div>
			<!-- DETTAGLIO -->
			<div class="col-md-12 tabbable-panel hidden" id="dettaglio" style="padding: 0px; margin-top: 20px;">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
						<li class="nav-item" data-info="Generalita' variante">
							<a class="nav-link" id="tab1varianti" data-toggle="tab" href="#generalitaVarianteTab" role="tab" aria-controls="generalitaVarianteTab" aria-selected="true">
								<strong>Generalit&agrave;</strong>
							</a>
			  			</li>
			  			<li class="nav-item" data-info="Documenti associati">
			    			<a class="nav-link" id="tab2varianti" data-toggle="tab" href="#documentiVarianteTab" role="tab" aria-controls="documentiVarianteTab" aria-selected="false">
								<strong>Documenti</strong>
							</a>
			  			</li>
			  			<li class="nav-item" data-info="Cartografia variante">
			    			<a class="nav-link" id="tab3varianti" data-toggle="tab" href="#cartografiaVarianteTab" role="tab" aria-controls="cartografiaVarianteTab" aria-selected="false">
								<strong>Cartografia</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="generalitaVarianteTab" role="tabpanel" aria-labelledby="tab1varianti">
								<form class="form-horizontal">
									<div class="row nom nop">
										<div class="form-group col-md-3">
											<label for="nomeVarianteDettaglio" class="control-label">Nome variante *</label>
											<div class="input-group">
												<input type="text" class="form-control" id="nomeVarianteDettaglio" placeholder="Nome variante...">
												<input type="text" hidden id="idVarianteDettaglio">
											</div>
										</div>
										<div class="form-group col-md-3">
											<label for="enteRiferimentoDettaglio" data-info="Ente di riferimento" class="control-label">Ente *</label>
											<div class="input-group">
												<select class="form-control" id="enteRiferimentoDettaglio" name="enteRiferimentoDettaglio" >
													<option value="F158">Messina</option>
												</select>
											</div> 
										</div> 
					  					<div class="form-group col-md-6">
					    					<label for="descrizioneVarianteDettaglio" class="control-label">Descrizione</label>
					    					<div class="input-group">
												<textarea class="form-control" id="descrizioneVarianteDettaglio" rows="3" placeholder="Descrizione..."></textarea>
												<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
					    					</div>
										</div>
										<!-- ESTREMI ADOZIONE -->
										<div class="tabbable-panel col-md-12" style="margin-top: 10px;">
											<div style="background: #ccc; padding: 5px 10px; text-align: center; border-radius: 0;" class="">
												<strong>Estremi della delibera di adozione della variante</strong>
											</div>
											<div style="margin-top: 10px;">
												<div class="panel-body row nom">
													<div class="form-group col-md-3">
														<label for="dataAdozioneDettaglio" class="control-label">Data Adozione *</label>
														<div class="input-group">
															<input id="dataAdozioneDettaglio" type="text" class="form-control" name="dataAdozioneDettaglio" placeholder="Data adozione...">   
															<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
														</div>
													</div>
													<div class="form-group col-md-3">
														<label for="numeroAdozioneDettaglio" data-info="Numero della delibera" class="control-label">Numero *</label>
														<div class="input-group">
															<div class="input-group">
																<input type="text" class="form-control" id="numeroAdozioneDettaglio" placeholder="Numero adozione...">
																<div class="input-group-append"><span class="input-group-text">#</span></div>
															</div>
														</div>
													</div>
													<div class="form-group col-md-3" >
														<label for="organoAdozioneDettaglio" data-info="Organo approvante" class="control-label">Approvato da</label>
														<div class="input-group">
															<input type="text" class="form-control" id="organoAdozioneDettaglio" placeholder="Approvato da...">
														</div>
													</div>
													<div class="form-group col-md-12">
														<label for="noteAdozioneDettaglio" class="control-label">Note</label>
														<div class="input-group">
															<textarea class="form-control" id="noteAdozioneDettaglio" rows="3" placeholder="Note..."></textarea>
															<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<!-- ESTREMI APPROVAZIONE -->
										<div class="tabbable-panel col-md-12" style="margin-top: 10px;">
											<div style="background: #ccc; padding: 5px 10px; text-align: center; border-radius: 0;" class="">
												<strong>Estremi della delibera di approvazione della variante</strong>
											</div>
											<div style="margin-top: 10px;">
												<div class="panel-body row nom">
													<div class="form-group col-md-3">
														<label for="dataApprovazioneDettaglio" class="control-label">Data Approvazione</label>
														<div class="input-group">
															<input id="dataApprovazioneDettaglio" type="text" class="form-control" name="dataApprovazioneDettaglio" placeholder="Data approvazione...">   
															<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
														</div>
													</div>
													<div class="form-group col-md-3">
														<label for="numeroApprovazioneDettaglio" data-info="Numero della delibera" class="control-label">Numero</label>
														<div class="input-group">
															<!-- <span class="input-group-addon">#</span> -->
															<input type="text" class="form-control" id="numeroApprovazioneDettaglio" placeholder="Numero...">
															<div class="input-group-append"><span class="input-group-text">#</span></div>
														</div>
													</div>
													<div class="form-group col-md-3">
														<label for="organoApprovazioneDettaglio" data-info="Organo approvante" class="control-label">Approvato da</label>
														<div class="input-group">
															<input type="text" class="form-control" id="organoApprovazioneDettaglio" placeholder="Approvato da...">
														</div>
													</div>	
												</div>
												<div class="form-group col-md-12">
													<label for="noteApprovazioneDettaglio" class="control-label">Note</label>
													<div class="input-group">
														<textarea class="form-control" id="noteApprovazioneDettaglio" rows="3" placeholder="Note..."></textarea>
														<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</form>					
							<!-- </div>			 -->
						</div>
			  			<div class="tab-pane" id="documentiVarianteTab" role="tabpanel" aria-labelledby="tab2varianti">
							<!-- LISTA DOCUMENTI -->
							<div class="col-sm-12" style="margin-top: 12px; margin-bottom: 12px;">
								<h3 class="decorated"><span>Lista documenti variante</span></h3>
								<div class="col-md-6">
									<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="nuovoDocumento">&nbsp;Nuovo documento</button>
								</div>
								<!-- </br> -->
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaDocumentiVariante" style="width:100%">
										<thead>
									    	<tr>
												<th></th>
									      		<th>Tipo</th>
									      		<th>Descrizione</th>
									      		<th>Variante</th>
									      		<th>Documento</th>
									      		<th></th>
									    	</tr>
									  	</thead>
									  	<tbody></tbody>
										<tfoot>
							            	<tr>
												<th></th>
									      		<th>Tipo</th>
									      		<th>Descrizione</th>
									      		<th>Variante</th>
									      		<th>Documento</th>
									      		<th></th>
									    	</tr>
							        	</tfoot>
									</table>
								</div>
								<br>
								<div class="col-md-6" style="float: left" id="sezioneIndice">
									<div>
										<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="nuovoIndice">&nbsp;Aggiungi Indice</button>
										<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="importaIndice">&nbsp;Importa Indice</button>
										<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="esportaIndice">&nbsp;Esporta Indice</button>
									</div>
									<br>
									<div class="table-responsive">
										<table class="table table-striped" id="tabellaDocumentiIndice" style="width:100%">
											<thead>
										    	<tr>
													<th></th>
										      		<th>Articolo</th>
										      		<th>Elenco Pagine</th>
													<th></th>
										    	</tr>
										  	</thead>
										  	<tbody></tbody>
											<tfoot>
								            	<tr>
													<th></th>
										      		<th>Articolo</th>
										      		<th>Elenco Pagine</th>
													<th></th>
										    	</tr>
								        	</tfoot>
										</table>
									</div>
								</div>
								<div class="col-md-6" style="float: left" id="sezioneCodici">
									<div>
										<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="nuovoCodice">&nbsp;Aggiungi Codice</button>
									</div>
									<br>
									<div class="table-responsive">
										<table class="table table-striped" id="tabellaIndiceCodici" style="width:100%">
											<thead>
									    		<tr>
										      		<th>Codice</th>
										      		<th>Descrizione</th>
									    		</tr>
										  	</thead>
										  	<tbody></tbody>
											<tfoot>
							    	        	<tr>
									    	  		<th>Codice</th>
									      			<th>Descrizione</th>
									    		</tr>
								        	</tfoot>
										</table>
									</div>
								</div>
							</div>				
						</div>
			  			<div class="tab-pane" id="cartografiaVarianteTab" role="tabpanel" aria-labelledby="tab3varianti">
							<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
								<h3 class="decorated"><span>Lista cartografia variante</span></h3>
								<div class="col-md-6">
									<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="aggiungiCartografia">&nbsp;Aggiungi Cartografia</button>
									<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="visualizzaCartografia">&nbsp;Visualizza Cartografia</button>
									<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="cancellaCartografia">&nbsp;Cancella Cartografia</button>
								</div>
								<!-- </br> -->
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaCartografiaVariante" style="width:100%">
										<thead>
									    	<tr>
												<th><input type="checkbox" class="select-checkbox" name="select-all-cartografia" style="margin-left: 5%"></th>
												<th>Nome Layer</th>
												<th>Gruppo</th>  
									    	</tr>
									  	</thead>
									  	<tbody></tbody>
										<tfoot>
							            	<tr>
												<th></th>
									    	</tr>
							        	</tfoot>
									</table>
								</div>
							</div>				
						</div>
						<div class="tab-pane" id="localizzazioneVarianteTab" role="tabpanel" aria-labelledby="tab4varianti">
							<div class="col-sm-12">
								<h3 class="decorated"><span>Modalita' di acquisizione tracciato ambito della Variante</span></h3>
							</div>
							<div class="col-sm-12" id="messaggioVariante">
								<h3 class="decorated"><span>La variante e' stata storicizzata e non e' possibile salvare/modificare l'ambito della variante</span></h3>
							</div>
							<div class="search row nom nop" id="tabAmbitoVariante">
								<div class="col-sm-12 col-md-4">
									<div class="panel panel-default">
										<div class="panel-heading">Seleziona da mappa</div>
								   		<div class="panel-body text-center">
											<div class="hovereffect">
												<img src="./images/catasto/seleziona.png" class="img-responsive img-fluid" alt="Seleziona su mappa">
								        		<div class="overlay-catasto">
								           			<h2>Seleziona su mappa</h2>
								           			<a class="info bttn btn-trasp" href="#" id="selezionaSuMappaBtn">Inizia</a>
								        		</div>
								    		</div>
										</div>
								    </div>
								</div>
								<div class="col-sm-12 col-md-4">
									<div class="panel panel-default">
										<div class="panel-heading">Traccia su mappa</div>
								   		<div class="panel-body text-center">
											<div class="hovereffect">
												<img src="./images/catasto/traccia.png" class="img-responsive img-fluid" alt="Traccia su mappa">
								        		<div class="overlay-catasto">
								           			<h2>Traccia su mappa</h2>
								           			<a class="info bttn btn-trasp" href="#" id="tracciaSuMappaBtn">Inizia</a>
								        		</div>
								    		</div>
										</div>
								    </div>
								</div>
								<div class="col-sm-12 col-md-4">
									<div class="panel panel-default">
										<div class="panel-heading">Ricerca particelle</div>
								   		<div class="panel-body text-center">
											<div class="hovereffect">
												<img src="./images/catasto/ricerca.png" class="img-responsive img-fluid" alt="Ricerca su mappa">
								        		<div class="overlay-catasto">
								           			<h2>Strumenti ricerca</h2>
								           			<a class="info bttn btn-trasp" href="#" id="strumentiRicercaMappaBtn">Inizia</a>
								        		</div>
								    		</div>
										</div>
								    </div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" class="pulsante-ricerca" href="#" id="eseguiBtnVariante" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" class="pulsante-ricerca" href="#" id="azzeraBtnVariante"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea una nuova variante" class="pulsante-ricerca" href="#" id="nuovoBtn"><em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Salva la variante" class="pulsante-dettaglio" href="#" id="salvaBtn"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Ritorna alla ricerca" class="pulsante-dettaglio" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<!-- 
   _______________  ____  ______________     _    _____    ____  _______    _   ______________
  / ___/_  __/ __ \/ __ \/  _/ ____/ __ \   | |  / /   |  / __ \/  _/   |  / | / /_  __/ ____/
  \__ \ / / / / / / /_/ // // /   / / / /   | | / / /| | / /_/ // // /| | /  |/ / / / / __/   
 ___/ // / / /_/ / _, _// // /___/ /_/ /    | |/ / ___ |/ _, _// // ___ |/ /|  / / / / /___   
/____//_/  \____/_/ |_/___/\____/\____/     |___/_/  |_/_/ |_/___/_/  |_/_/ |_/ /_/ /_____/   
                                                                                              
-->
<script id="modaleStoricoVariante" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 ">
				<h2>Storico variante</h2>
			</div>
			<div class="col-md-12 " id="dettaglio" style="padding: 10px;">
				<ul class="timeline">
					{{#each intestatari}}
					<li>
						<div class="timeline-panel">
							<div class="timeline-heading">
								<h4 class="timeline-title">{{this.azione}}</h4>
								<p><strong class="text-muted"><em class="fa fa-calendar"></em>     {{this.dataAzione}}</strong><br>
								</p>
								<p>{{this.descrizioneAzione}}</p>
							</div>
						</div>
					</li>
					{{/each}}
				</ul>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em
							class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<!-- 
   _________    __  _______  _______       ____  ____  ____  ____  ___________________    ____  ________ 
  / ____/   |  /  |/  / __ )/  _/   |     / __ \/ __ \/ __ \/ __ \/  _/ ____/_  __/   |  / __ \/  _/ __ \
 / /   / /| | / /|_/ / __  |/ // /| |    / /_/ / /_/ / / / / /_/ // // __/   / / / /| | / /_/ // // / / /
/ /___/ ___ |/ /  / / /_/ // // ___ |   / ____/ _, _/ /_/ / ____// // /___  / / / ___ |/ _, _// // /_/ / 
\____/_/  |_/_/  /_/_____/___/_/  |_|  /_/   /_/ |_|\____/_/   /___/_____/ /_/ /_/  |_/_/ |_/___/\____/  
                                                                                                         
 -->
<script id="modaleCambiaProprietario" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<div class="form-group col-sm-12">
					<label for="colore" class="col-sm-12 col-md-3 control-label">Utente attuale</label>
			    	<div class="col-sm-12 col-md-9">
						<div class="input-group">
		      				<input id="utenteAttuale" type="text" class="form-control" value="amministratore" readonly>
							<span class="input-group-addon">
								<em class="fa fa-user"></em>
							</span>
			    		</div>
					</div>
				</div>
				<div class="form-roup col-sm-12">
		    		<label for="stileSelect" class="col-sm-12 col-md-3 control-label">Nuovo utente</label>
		    		<div class="col-sm-12 col-md-9">
	    				<select class="form-control" id="stileSelect">
	    					<option>Seleziona...</option>
	    					<option>Operatore comunale 1</option>
	    					<option>Operatore comunale 2</option>
	    				</select>
		    		</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Chiudi la finestra" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</script>

<!-- 
    ____  ____  ________  ____  __________   ____________
   / __ \/ __ \/ ____/ / / /  |/  / ____/ | / /_  __/  _/
  / / / / / / / /   / / / / /|_/ / __/ /  |/ / / /  / /  
 / /_/ / /_/ / /___/ /_/ / /  / / /___/ /|  / / / _/ /   
/_____/\____/\____/\____/_/  /_/_____/_/ |_/ /_/ /___/   
                                                         
-->
<script id="gestioneDocumentiPiano" type="text/x-handlebars-template">
<!-- DROPDOWN MENU -->
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation"><a href="#" role="tab" data-toggle="tab" id="menuRicercaDocumenti">Ricerca</a></li>
		<li role="presentation"><a href="#" role="tab" data-toggle="tab" id="menuAggiungiDocumenti">Aggiungi</a></li>
	</ul>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li class="active"><a href="#">Documenti</a></li>
				</ul>
			</div>
			<div id="ricerca">
				<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading">
						  <h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri1">
								<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
							</a>
						  </h4>
						</div>
						<div id="collapseFiltri1" class="panel-collapse collapse in show">
							<div class="panel-body">
								<div class="col-sm-12 col-md-4" style="display: inline-block">
									<label>Ente di riferimento</label>
									<select class="form-control" id="enteRiferimento" name="enteRiferimento">
										<option>Messina</option>
										<option>Azzolo</option>
									</select>   
								</div>
								<div class="col-sm-12 col-md-4" style="display: inline-block">
									<label>Nome</label>
									<input id="nome" type="text" class="form-control" name="nome" placeholder="Nome...">   
								</div>
								<div class="col-sm-12 col-lg-12" style="display: inline-block">
									<label>Descrizione</label>
									<input id="descrizione" type="text" class="form-control" name="descrizione" placeholder="Descrizione...">   
								</div>
							</div>
						</div>
					</div>
				</div>
			
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaRisultati" style="width:100%">
						<thead>
					    	<tr>
					      		<th>Tipo</th>
					      		<th>Descrizione</th>
					      		<th>Variante</th>
					      		<th>Documento</th>
					      		<th></th>
					    	</tr>
					  	</thead>
					  	<tbody></tbody>
						<tfoot>
			            	<tr>
					      		<th>Tipo</th>
					      		<th>Descrizione</th>
					      		<th>Variante</th>
					      		<th>Documento</th>
					      		<th></th>
					    	</tr>
			        	</tfoot>
					</table>
				</div>
			</div>
		
			<!-- DETTAGLIO -->
			<div class="col-md-12 hidden tabbable-panel" id="dettaglio" style="padding: 0px; margin-top: 20px;">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="honoluluTab1" role="tablist">
			  			<li class="nav-item active" data-info="Associazioni con entita' geometriche">
			    			<a class="nav-link" id="honolulu-tab1" data-toggle="tab" href="#associazioniTabContent1" role="tab" aria-controls="associazioniTabContent1" aria-selected="false">
								<strong>Associazioni</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="associazioniTabContent1">
			  			<div class="tab-pane active" id="associazioniTabContent1" role="tabpanel" aria-labelledby="honolulu-tab1">
							<div class="col-sm-12 col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
								<h3 class="decorated"><span>Strati informativi correlati</span></h3>
								<div class="col-sm-12 clearfix">
									<label for="enteRiferimento">Ricerca</label>
									<div class="input-group">
										<input id="plugins4_q" type="text" placeholder="Ricerca..." class="form-control">
										<span class="input-group-addon">
											<em class="fa fa-search"></em>
										</span>
			    					</div>
								</div>
								<div class="col-sm-12 clearfix">
									<div id="menu-jstree"></div>
								</div>
							</div>
							<div style="margin-top: 12px; margin-bottom: 12px;">
								<h3 class="decorated"><span>Dettaglio documento</span></h3>
								<div class="col-sm-3" style="display: inline-block">
									<label for="enteRiferimento">Ente di riferimento</label>
									<select class="form-control" id="" name="">
										<option value="F158">Messina</option>
									</select>  
								</div>
								<div class="col-sm-3" style="display: inline-block">
									<label for="tipoDocumento">Tipo</label>
									<input id="tipoDocumento" type="text" class="form-control" name="tipoDocumento">   
								</div>
								<div class="col-sm-3" style="display: inline-block">
									<label for="varianteDocumento">Variante</label>
									<input id="varianteDocumento" type="text" class="form-control" name="varianteDocumento">   
								</div>
								<div class="col-sm-3" style="display: inline-block">
									<label for="nomeDocumento">Documento</label>
									<input id="nomeDocumento" type="text" class="form-control" name="nomeDocumento">   
								</div>
								<div class="col-sm-12" style="display: inline-block">
									<label for="descrizioneDocumento">Descrizione</label>
									<input id="descrizioneDocumento" type="text" class="form-control" name="descrizioneDocumento">   
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Ricerca" class="pulsante-ricerca" href="#" id="eseguiBtn" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" class="pulsante-ricerca" href="#" id="azzeraBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea una nuova versione documento" class="pulsante-ricerca" href="#" id="nuovoBtn"><em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li class="hidden"><a data-info="Ritorna alla ricerca" class="pulsante-dettaglio" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
					<li class="hidden"><a data-info="Salva il documento" class="pulsante-dettaglio" href="#" id="salvaBtn"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li class="hidden"><a data-info="Salva il documento e chiudi la finestra" class="pulsante-dettaglio" href="#" id="salvaChiudiBtn"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva e Chiudi</a></li>
					<li><a class="pulsante-ricerca pulsante-dettaglio" data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<!-- 
    ____  ____  ______________________  _______   _______   ______
   / __ \/ __ \/ ____/ ____/ ____/ __ \/ ____/ | / /__  /  / ____/
  / /_/ / /_/ / __/ / /_  / __/ / /_/ / __/ /  |/ /  / /  / __/   
 / ____/ _, _/ /___/ __/ / /___/ _, _/ /___/ /|  /  / /__/ /___   
/_/   /_/ |_/_____/_/   /_____/_/ |_/_____/_/ |_/  /____/_____/   
                                                                  
-->
<script id="gestionePreferenzePiano" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<!--
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation" class="active"><a href="#ricSpess" aria-controls="ricSpess" role="tab" data-toggle="tab">Ricerca</a></li>
		<li role="presentation"><a href="#nuovoSpess" aria-controls="nuovoSpess" role="tab" data-toggle="tab">Nuovo</a></li>
		<li role="presentation"><a href="#importSpess" aria-controls="importSpess" role="tab" data-toggle="tab">Import</a></li>
	</ul>
	-->
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li class="active"><a href="#">Gestione Preferenze</a></li>
				</ul>
			</div>
			<div class="col-md-12 tabbable-panel" id="preferenzePianoRegolatore" style="padding: 0px; margin-top: 20px;">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="tabContainer" role="tablist">
			  			<li class="nav-item active" data-info="Preferenze per l'area usata per l'interrogazione">
			    			<a class="nav-link" id="tab1" data-toggle="tab" href="#tab1Content" role="tab" aria-controls="tab1Content" aria-selected="true">
								<strong>Area usata per l'interrogazione</strong>
							</a>
			  			</li>
			  			<li class="nav-item" data-info="Preferenze di ricerca delle entita' intersecate">
			    			<a class="nav-link" id="tab2" data-toggle="tab" href="#tab2Content" role="tab" aria-controls="tab2Content" aria-selected="false">
								<strong>Ricerca entita intersecate</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="tab1Content" role="tabpanel" aria-labelledby="tab2Content">
							<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
								<form class="form-horizontal">
									<div class="form-group">
								    	<label for="tipoAreaSelect" class="col-sm-12 col-md-3 control-label">Tipo Area</label>
								    	<div class="col-sm-12 col-md-9">
							    			<select class="form-control" id="tipoAreaSelect">
							    				<option>Seleziona...</option>
							    				<option>Rettangolo</option>
							    				<option>Poligono</option>
							    				<option>Cerchio</option>
							    			</select>
								    	</div>
									</div>
									<div class="form-group">
										<label for="colore" class="col-sm-12 col-md-3 control-label">Colore</label>
			    						<div class="col-sm-12 col-md-9">
											<div class="input-group">
		      									<input id="colore" type="text" class="form-control" value="rgb(255, 128, 0)">
												<span class="input-group-addon" style="background: rgb(255, 128, 0)">
													<em class="fa fa-paint-brush" style="color: transparent;"></em>
												</span>
			    							</div>
										</div>
								  	</div>
			  						<div class="form-group">
								    	<label for="stileSelect" class="col-sm-12 col-md-3 control-label">Stile</label>
								    	<div class="col-sm-12 col-md-9">
							    			<select class="form-control" id="stileSelect">
							    				<option>Seleziona...</option>
							    				<option>Linee diagonali incrociate</option>
							    				<option>Linee diagonali verso destra</option>
							    				<option>Linee diagonali verso sinistra</option>
							    			</select>
								    	</div>
									</div>
			  						<div class="form-group">
										<label for="coloreBordo" class="col-sm-12 col-md-3 control-label">Colore bordo</label>
			    						<div class="col-sm-12 col-md-9">
											<div class="input-group">
		      									<input id="coloreBordo" type="text" class="form-control" value="rgb(216,62,62)">
												<span class="input-group-addon" style="background: rgb(216,62,62)">
													<em class="fa fa-paint-brush" style="color: transparent;"></em>
												</span>
			    							</div>
										</div>
								  	</div>
			  						<div class="form-group">
								    	<label for="spessore" class="col-sm-12 col-md-3 control-label">Spessore</label>
								    	<div class="col-sm-12 col-md-9">
											<div class="input-group">
									    		<input class="form-control" id="spessore" type="number" min="1">
												<span class="input-group-addon">px</span>
											</div>
								    	</div>
								  	</div>
								</form>					
							</div>			
						</div>
			  			<div class="tab-pane" id="tab2Content" role="tabpanel" aria-labelledby="tab2Content">
							
							<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
								<div class="form-group">
									<label for="prova" class="col-sm-12 control-label">Ignora poligoni se l'area intersecata &egrave; minore di</label>
									<div class="col-sm-12">
										<div class="input-group">
											<span class="input-group-addon">
												<input type="checkbox">
											</span>
											<input class="form-control" id="prova" type="text" min="1" style="width: 80%;">
											<select class="form-control" style="width: 20%;"><option>mq</option><option>%</option></select>
										</div>
									</div>
								</div>
							</div>
						</div>				
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva le impostazioni" href="#" id="eseguiBtn"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla le modifiche" href="#" id="azzeraBtn"><em class="fa fa-undo"></em>&nbsp;&nbsp;Annulla</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
	
</div>
</script>

<!-- 
 _    ___________ __  _____    __    ______________   ___ _____   ________  _   ________
| |  / /  _/ ___// / / /   |  / /   /  _/__  /__  /  /   /__  /  /  _/ __ \/ | / / ____/
| | / // / \__ \/ / / / /| | / /    / /   / /  / /  / /| | / /   / // / / /  |/ / __/   
| |/ // / ___/ / /_/ / ___ |/ /____/ /   / /__/ /__/ ___ |/ /___/ // /_/ / /|  / /___   
|___/___//____/\____/_/  |_/_____/___/  /____/____/_/  |_/____/___/\____/_/ |_/_____/   
                                                                                        
-->
<script id="visualizzazionePianoRegolatore" type="text/x-handlebars-template">
</script>









<!-- NOTA BENE: QUESTO SCRIPT E' STATO COPIATO DI SANA PIANTA... APPENA POSSIBILE FARE UN REFACTORING -->
<script id="modPianoParticellareDaStrumentiRicerca" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation"><a href="#" role="tab" data-toggle="tab" id="menuRicercaVarianti">Ricerca</a></li>
		<li role="presentation"><a href="#" role="tab" data-toggle="tab" id="menuAggiungiVariante">Aggiungi</a></li>
	</ul>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Cartografia</a></li>
					<li class="active"><a href="#">Ricerca Piano Particellare</a></li>
				</ul>
			</div>
			<div id="ricerca" class="">
				<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading">
						  <h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri1">
								<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
							</a>
						  </h4>
						</div>
						<div id="collapseFiltri1" class="panel-collapse collapse in show">
							<div class="panel-body">
								<div class="input-group p10">
									<div class="input-group-prepend"><span class="input-group-text"><i class="fa fa-align-left"></i></span></div> 
									<input id="partFoglioCart-rp" type="text" class="form-control" placeholder="Foglio..." aria-describedby="basic-addon1" title="Inserisci Foglio">
									<div class="input-group-append deleteField" onclick="deleteField('partFoglioCart-rp')" title="Cancella campo"><span class="input-group-text"><i class="fa fa-close"></i></span></div>				
								</div>
								<div class="input-group p10">
									<div class="input-group-prepend"><span class="input-group-text"><i class="fa fa-align-left"></i></span></div> 
									<input id="partMappaleCart-rp" type="text" class="form-control" placeholder="Mappale..." aria-describedby="basic-addon1" title="Inserisci Mappale">
									<div class="input-group-append deleteField" onclick="deleteField('partMappaleCart-rp')" title="Cancella campo"><span class="input-group-text"><i class="fa fa-close"></i></span></div>				
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaRisultati" style="width:100%">
						<thead>
					    	<tr>
					      		<th></th>
					      		<th>Codice catastale comune</th>
					      		<th>Foglio</th>
					      		<th>Mappale</th>
					      		<th></th>
					    	</tr>
					  	</thead>
					  	<tbody></tbody>
						<tfoot>
			            	<tr>
					      		<th></th>
					      		<th>Codice catastale comune</th>
					      		<th>Foglio</th>
					      		<th>Mappale</th>
					      		<th></th>
					    	</tr>
			        	</tfoot>
					</table>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Ricerca particelle" href="#" id="eseguiBtn" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Avvia estrazione" href="#" id="estraiBtn"><em class="fa fa-check"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnQ"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>


<script id="modaleStrutturaPianoEnti" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation"><a href="#menuRicercaEnti" aria-controls="menuRicercaEnti" role="tab" data-toggle="tab">Ricerca</a></li>
		<li role="presentation"><a href="#menuAggiungiEnte" aria-controls="menuAggiungiEnte" role="tab" data-toggle="tab">Nuovo</a></li>
	</ul>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li><a href="#">Struttura del piano</a></li>
					<li class="active"><a href="#">Enti</a></li>
				</ul>
			</div>
			<div id="ricerca">
				<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading">
						  <h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri1">
								<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
							</a>
						  </h4>
						</div>
						<div id="collapseFiltri1" class="panel-collapse collapse in show">
							<div class="panel-body">
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label for="sigla">Sigla</label>
									<input id="sigla" type="text" class="form-control" name="sigla" placeholder="Sigla...">   
								</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label for="descrizione">Descrizione</label>
									<input id="descrizione" type="text" class="form-control" name="descrizione" placeholder="Descrizione...">   
								</div>
								<div class="col-md-12">
									<label for="note">Note</label>
									<div class="input-group">
			      						<span class="input-group-addon"><i class="fa fa-clipboard"></i></span>   
			     	 					<textarea id="note" type="text" class="form-control" name="note" placeholder="Note..."></textarea>
			    					</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaRisultati" style="width:100%">
						<thead>
					    	<tr>
					      		<th>Sigla</th>
					      		<th>Descrizione</th>
					      		<th>Note</th>
					      		<th></th>
					    	</tr>
					  	</thead>
					  	<tbody></tbody>
						<tfoot>
			            	<tr>
					      		<th>Sigla</th>
					      		<th>Descrizione</th>
					      		<th>Note</th>
					      		<th></th>
					    	</tr>
			        	</tfoot>
					</table>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Ricerca" href="#" id="eseguiBtn"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea un nuovo ente" href="#" id="nuovoBtn"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleCreaModificaEnte" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<div class="form-group col-sm-12">
					<label for="colore" class="col-sm-12 col-md-3 control-label">Sigla</label>
			    	<div class="col-sm-12 col-md-9">
	      				<input id="sigla" type="text" class="form-control">
					</div>
				</div>
				<div class="form-group col-sm-12">
					<label for="colore" class="col-sm-12 col-md-3 control-label">Descrizione</label>
			    	<div class="col-sm-12 col-md-9">
	      				<input id="descrizione" type="text" class="form-control">
					</div>
				</div>
				<div class="form-group col-sm-12">
					<label for="colore" class="col-sm-12 col-md-3 control-label">Note</label>
			    	<div class="col-sm-12 col-md-9">
	      				<textarea id="note" rows="5" class="form-control"></textarea>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Chiudi la finestra" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<!--=================== GESTIONE TIPI DI DOCUMENTO =================== inizio-->
<script id="modaleStrutturaPianoTipiDocumento" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li><a href="#">Struttura del piano</a></li>
					<li class="active"><a href="#">Tipo documento</a></li>
				</ul>
			</div>
			<div id="ricerca" class="">
				<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading">
						  <h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri1">
								<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
							</a>
						  </h4>
						</div>
						<div id="collapseFiltri1" class="panel-collapse collapse in show">
							<div class="panel-body row nom">
								<div class="col-sm-12 col-md-6 col-lg-4">
									<label>Codice</label>
									<input id="codice" type="text" class="form-control" name="codice" placeholder="Codice...">
								</div>
								<div class="col-sm-12 col-md-6 col-lg-4">
									<label>Descrizione</label>
									<input id="descrizione" type="text" class="form-control" name="descrizione" placeholder="Descrizione...">  
								</div>
								<div class="col-sm-12 col-md-6 col-lg-4">
									<label>Descrizione CDU</label>
									<input id="descrizioneCDU" type="text" class="form-control" name="descrizioneCDU" placeholder="Descrizione CDU...">  
								</div>
								<div class="col-sm-12 col-md-6 col-lg-12">
									<label>Note</label>
									<textarea id="note" class="form-control" placeholder="Note..."></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaTipoDocumenti" style="width:100%">
						<thead>
					    	<tr>
					      		<th>Codice</th>
					      		<th>Descrizione</th>
								<th>Descrizione CDU</th>
					      		<th>Note</th>
					      		<th></th>
					    	</tr>
					  	</thead>
					  	<tbody></tbody>
						<tfoot>
			            	<tr>
					      		<th>Codice</th>
					      		<th>Descrizione</th>
								<th>Descrizione CDU</th>
					      		<th>Note</th>
					      		<th></th>
					    	</tr>
			        	</tfoot>
					</table>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnDocumento"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtnDocumento"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea un nuovo tipo di documento" href="#" id="nuovoBtn"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleCreaModificaTipoDocumento" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<div class="form-group col-sm-12">
					<label for="codice" class="col-sm-12 col-md-3 control-label">Codice *</label>
			    	<div class="col-sm-12 col-md-12">
	      				<input id="codiceM" type="text" class="form-control" value="{{codice}}" placeholder="Codice..." {{readonly}} required>
					</div>
				</div>
				<div class="form-group col-sm-12">
					<label for="descrizione" class="col-sm-12 col-md-3 control-label">Descrizione *</label>
			    	<div class="col-sm-12 col-md-12">
	      				<input id="descrizioneM" type="text" class="form-control" value="{{descrizione}}" placeholder="Descrizione..." required>
					</div>
				</div>
				<div class="form-group col-sm-12">
					<label for="descrizioneCDU" class="col-sm-12 col-md-3 control-label">Descrizione CDU</label>
			    	<div class="col-sm-12 col-md-12">
	      				<input id="descrizioneCDU" type="text" class="form-control" value="{{descrizioneCDU}}" placeholder="Descrizione CDU...">
					</div>
				</div>
				<div class="form-group col-sm-12">
					<label for="note" class="col-sm-12 col-md-3 control-label">Note</label>
			    	<div class="col-sm-12 col-md-12">
	      				<textarea id="note" rows="5" class="form-control" placeholder="Note...">{{note}}</textarea>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva il tipo documento" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<!--=================== GESTIONE TIPI DI DOCUMENTO =================== fine-->

<script id="modaleStrutturaPiano" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation"><a href="#" id="" aria-controls="menuRicercaEnti" role="tab" data-toggle="tab">Ricerca</a></li>
		<li role="presentation"><a href="#" id="" aria-controls="menuAggiungiEnte" role="tab" data-toggle="tab">Nuovo</a></li>
	</ul>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li><a href="#">Struttura del piano</a></li>
				</ul>
			</div>
			<div id="ricerca">
				<div class="alert alert-info alert-dismissible fade in">
  					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  					<strong>Ricorda:</strong> puoi usare il menu contestuale cliccando col tasto destro del mouse su di un elemento del piano regolatore.
				</div>
				<div class="col-sm-12">
					<div id="menu-jstree"></div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
					<!-- DOCUMENTI -->
					<li><a class="context-btn aggiungi-documento-btn" data-info="Aggiungi un nuovo documento" href="#"><em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi documento</a></li>
					<li><a class="context-btn modifica-documento-btn" data-info="Modifica il documento" href="#" ><em class="fa fa-pencil"></em>&nbsp;&nbsp;Modifica documento</a></li>
					<li><a class="context-btn rimuovi-documento-btn"  data-info="Elimina il documento" href="#"><em class="fa fa-trash-o"></em>&nbsp;&nbsp;Rimuovi documento</a></li>
					<!-- GRUPPI -->
					<li><a class="context-btn aggiungi-gruppo-btn" data-info="Aggiungi un nuovo gruppo" href="#"><em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi gruppo</a></li>
					<li><a class="context-btn modifica-gruppo-btn" data-info="Modifica il gruppo" href="#" ><em class="fa fa-pencil"></em>&nbsp;&nbsp;Modifica gruppo</a></li>
					<li><a class="context-btn rimuovi-gruppo-btn"  data-info="Elimina il gruppo" href="#"><em class="fa fa-trash-o"></em>&nbsp;&nbsp;Rimuovi gruppo</a></li>	
					<!-- LAYER -->
					<li><a class="context-btn aggiungi-layer-btn" data-info="Aggiungi un nuovo strato informativo" href="#"><em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi strato</a></li>
					<li><a class="context-btn modifica-layer-btn" data-info="Modifica lo strato informativo" href="#" ><em class="fa fa-pencil"></em>&nbsp;&nbsp;Modifica strato</a></li>
					<li><a class="context-btn rimuovi-layer-btn"  data-info="Elimina lo strato informativo" href="#"><em class="fa fa-trash-o"></em>&nbsp;&nbsp;Rimuovi strato</a></li>
					<li><a class="context-btn visualizza-layer-btn" data-info="Visualizza lo strato informativo" href="#"><em class="fa fa-eye"></em>&nbsp;&nbsp;Visualizza strato</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleCreaModificaGruppo" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<div class="form-group col-sm-12">
					<label for="descrizione" class="col-sm-12 col-md-3 control-label">Descrizione</label>
			    	<div class="col-sm-12 col-md-9">
	      				<input id="descrizione" type="text" class="form-control">
					</div>
				</div>
				<div class="form-group col-sm-12">
					<label for="note" class="col-sm-12 col-md-3 control-label">Note</label>
			    	<div class="col-sm-12 col-md-9">
	      				<textarea id="note" rows="5" class="form-control"></textarea>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva il gruppo" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleCreaModificaLayer" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<div class="col-sm-12 col-md-6">
					<label>Importa da</label>
      				<select id="importaDa" class="form-control">
      					<option selected="selected">ESRI Shapefile</option>
      				</select>
				</div>
				<div class="col-sm-12">
					<label>File</label>
      				<input type="file" name="shapefileUpload" id="shapefileUpload">
				</div>
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="stratoInformativoTab" role="tablist">
						<li class="nav-item active" data-info="Generalita' strato informativo">
							<a class="nav-link" id="tab1layer" data-toggle="tab" href="#generalitaLayerTab" role="tab" aria-controls="generalitaLayerTab" aria-selected="true">
								<strong>Generalita'</strong>
							</a>
						</li>
						<li class="nav-item" data-info="Classificazione strato informativo">
							<a class="nav-link" id="tab2layer" data-toggle="tab" href="#classificazioneVarianteTab" role="tab" aria-controls="classificazioneVarianteTab" aria-selected="false">
								<strong>Classificazione</strong>
							</a>
						</li>
					</ul>
					<div class="tab-content" id="stratoInformativoTabContent">
						<div class="tab-pane active" id="generalitaLayerTab" role="tabpanel" aria-labelledby="tab1layer">
							<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px; padding:0px;">

								<div class="col-sm-12 col-md-6">
									<label>Descrizione</label>
									<input id="descrizione" class="form-control" type="text">
								</div>
								<div class="col-sm-12 col-md-6">
									<label>Note</label>
									<input id="note" class="form-control" type="text">
								</div>
								<div class="col-sm-12">
									<div class="checkbox">
										<label>
											<input type="checkbox" value="">
											<span class="cr">
											<i class="cr-icon glyphicon glyphicon-ok"></i></span> Usa la certificazione
										</label>
									</div>
								</div>
								<!-- TABELLA ELENCO CAMPI CONTENUTI -->
								<div class="col-sm-12" style="padding:0px">
									<label class="col-sm-12 control-label">Elenco dei campi contenuti</label>
								</div>
								<div class="col-sm-12" style="padding:0px">
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaCampiContenuti" style="width:100%">
										<thead>
									    	<tr>
									      		<th>Nome</th>
									      		<th>Mostra</th>
												<th>Etichette</th>
									      		<th class="text-center">Indice</th>
									      		<th class="text-center">Classif.</th>
									      		<th class="text-center">Specif.</th>
									      		<th>Tipo</th>
									      		<th>Caratteri</th>
									      		<th>Decimali</th>
									    	</tr>
									  	</thead>
									  	<tbody>
											<tr>
									      		<td>DEF_PGT</td>
									      		<td></td>
												<td></td>
									      		<td class="text-center"><input type="checkbox" value=""></td>
									      		<td class="text-center"><input type="checkbox" value=""></td>
									      		<td class="text-center"><input type="checkbox" value=""></td>
									      		<td>testo</td>
									      		<td>254</td>
									      		<td>0</td>
									    	</tr>
										</tbody>
									</table>
								</div>
								</div>
								
							</div>			
						</div>
						<div class="tab-pane" id="classificazioneVarianteTab" role="tabpanel" aria-labelledby="tab2layer">
							<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
								CLASSIFICAZIONE
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva il layer" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleCreaModificaVersioneDocumento" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<label for="tipoDocumento" class="col-sm-12 control-label">Tipo di documento</label>
				<div class="form-group col-sm-12">
					<select id="tipoDocumento" type="text" class="form-control" name="tipoDocumento">
						{{#each tipoDocumenti}}
						<option value="{{this.codice}}">{{this.codice}} - {{this.descrizione}}</option>
						{{/each}}
					</select>
					<input type="text" hidden id="idDocumento">
				</div>
				<div class="col-sm-12">
					<label>File</label>
      				<input type="file" name="nuovaVersioneUpload" id="nuovaVersioneUpload">
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva versione documento" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleGestioneAssociazioniDocumento" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<div class="col-sm-12 clearfix">
					<label for="enteRiferimento">Ricerca</label>
					<div class="input-group">
						<input id="inputRicercaJsTree" type="text" placeholder="Ricerca..." class="form-control">
						<span class="input-group-addon">
							<em class="fa fa-search"></em>
						</span>
		 			</div>
				</div>
				<div class="col-sm-12 clearfix">
					<div id="gestione-associazione-documenti-jstree"></div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Chiudi la finestra" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Salva associazioni" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</script>

<script id="modaleCertificatoUrbanistico" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation"><a href="#" id="" aria-controls="menuRicercaEnti" role="tab" data-toggle="tab">Ricerca</a></li>
		<li role="presentation"><a href="#" id="" aria-controls="menuAggiungiEnte" role="tab" data-toggle="tab">Nuovo</a></li>
	</ul>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li><a href="#">Certificato Urbanistico</a></li>
				</ul>
			</div>
			<div id="ricerca">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row nom nop">
							<div class="form-group col-md-4">
								<label for="enteCDU">Ente</label>
			    				<select id="enteCDU" class="form-control" name="enteCDU">
									<!-- <option>Seleziona...</option> -->
									<option selected>Messina</option>
								</select>
							</div>
							<div class="form-group col-md-4">
								<label for="modelloCDU">Modello da utilizzare</label>
			   					<select id="modelloCDU" class="form-control" name="modelloCDU">
									<!-- <option>Seleziona...</option> -->
									<option selected>Analisi urbanistica</option>
								</select>
							</div>
						</div>
						<div style="background: #ccc;padding: 5px 10px;text-align: center;border-radius: 0;margin-bottom: 12px;" class="">
								<strong>Seleziona variante</strong>
						</div>
						<div class="row nom nop">
							<div class="form-group col-md-6"><h6>Selezionare la variante</h6></div>
							<div class="form-group col-md-6"><h6>Hai selezionato la seguente variante</h6></div>
						</div>
						<div class="row nom nop">
	    					<div class="form-group col-md-6 content-checkbox">
								<div class="custom-control custom-radio">
	                				<input type="radio" id="vigente" name="optradio" value="vigente" class="custom-control-input">
                					<label class="custom-control-label" style="float:left" for="vigente">Vigente</label>
									<p id="varVigLabel" class="" style="float: left;margin-bottom: 0;margin-top: 3px;position: relative;vertical-align: top;line-height: 20px;"></p>
           						</div>
            					<div class="custom-control custom-radio">
          				      		<input type="radio" id="salvaguardia" name="optradio" value="salvaguardia" class="custom-control-input">
	      				          	<label class="custom-control-label" style="float:left" for="salvaguardia">In salvaguardia</label>
										<p id="varSalvaLabel" class="" style="float: left;margin-bottom: 0;margin-top: 3px;position: relative;vertical-align: top;line-height: 20px;"></p>
     				       		</div>
     				       		<div class="custom-control custom-radio">
    				           		<input type="radio" id="datavigente" name="optradio" value="datavigente" class="custom-control-input">
     				           		<label class="custom-control-label" style="float: left;width: 30%;" for="datavigente">
										Vigente dalla data
									</label>
									<div class="input-group" style="float: left;width: 50%;">
			      						<input id="dataVigenteSel" type="text" class="form-control" readonly="" style="height: 28px;">
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
									</div>
   				         		</div>
  				      		</div>
							<div class="form-group col-md-6" id="varianteScelta">
								<table class="table table-striped" id="tabellaVarianteCertificato" style="width:100%">
									<thead>
										<tr>
											<th>Nome</th>
											<th>Data adozione</th>
											<th>Data approvazione</th>
											<th>Descrizione</th>
										</tr>
									</thead>
									<tbody><tr><td colspan="4">Nessun ambito di variante selezionato</td></tr></tbody>
								</table>
							</div>
						</div>
						
						<div class="row nom nop">
							<div class="col-sm-12">
							<!-- STEP ACQUISIZIONE CDU -->
							<div id="acquisizioneCduDiv">
								<div style="background: #ccc;padding: 5px 10px;text-align: center;border-radius: 0;margin-bottom: 12px;" class="">
									<strong>Modalit&aacute; di acquisizione geometria certificato urbanistico</strong>
								</div>
								<div class="search row nom nop">
									<div class="form-group col-md-6 "><h6>Modalit&aacute; di acquisizione</h6></div>
									<div class="form-group col-md-6 nom"><h6>Hai selezionato le seguenti geometrie</h6></div>
								</div>
								<div class="search row nom nop">
									<div class="col-sm-12 col-md-3">
										<div class="panel panel-default">
											<div class="panel-heading">Seleziona da mappa</div>
											<div class="panel-body text-center">
												<div class="hovereffect">
													<img src="./images/catasto/seleziona.png" class="img-responsive img-fluid" alt="Seleziona su mappa">
													<div class="overlay-catasto">
														<h2>Seleziona su mappa</h2>
														<a class="info bttn btn-trasp" href="#" id="selezionaSuMappaBtn">Inizia</a>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 col-md-3">
										<div class="panel panel-default">
											<div class="panel-heading">Traccia su mappa</div>
											<div class="panel-body text-center">
												<div class="hovereffect">
													<img src="./images/catasto/traccia.png" class="img-responsive img-fluid" alt="Traccia su mappa">
													<div class="overlay-catasto">
														<h2>Traccia su mappa</h2>
														<a class="info bttn btn-trasp" href="#" id="tracciaSuMappaBtn">Inizia</a>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 col-md-6">
										<div class="row nom nop" id="selezioneGeometria">
											<table class="table table-striped" id="tabellaSelezioneParticelle" style="margin-bottom: 0px;">
												<thead>
													<tr>
														<th style="width:10%">Foglio</th>
														<th style="width:90%">Numero</th>
													</tr>
												</thead>
											</table>
											<table class="table table-striped" id="tabellaSelezioneParticelle">
												<tbody><tr><td colspan="2">Nessuna geometria selezionata</td></tr></tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- SECONDO STEP ALBERO LAYER -->
				<div id="alberoLayerCDUDiv" class="hidden">
				</div>
			
				<!-- TERZO STEP RISULTATI TABELLARI -->
				<div id="tabellaAcquisizioneCduDiv" class="hidden">
					
					<!-- TABELLA RISULTATI -->
					<div class="table-responsive">
						<table class="table table-striped" id="tabellaRisultatiCDU" style="width:100%">
							<thead>
						    	<tr>
						      		<td>Area</td>
						      		<td>Gruppo Layer</td>
						      		<td>Nome Layer</td>
						      		<td>Codice classificazione geometria</td>
						      		<td>Numero entita intersecate</td>
						      		<td>Area intersecata</td>
						      		<td>Percentuale area intersecata</td>
						    	</tr>
						  	</thead>
						  	<tbody></tbody>
							<tfoot>
				            	<tr>
						      		<td>Area</td>
						      		<td>Gruppo Layer</td>
						      		<td>Nome Layer</td>
						      		<td>Codice classificazione geometria</td>
						      		<td>Numero entita intersecate</td>
						      		<td>Area intersecata</td>
						      		<td>Percentuale area intersecata</td>
						    	</tr>
				        	</tfoot>
						</table>
					</div>
				</div>
					
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
					<li><a data-info="Scarica certificato urbanistico" href="#" id="esportaCduBtn"><em class="fa fa-download"></em>&nbsp;&nbsp;Scarica</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleCertificatoUrbanisticoVariante" type="text/x-handlebars-template">
	<table class="table table-striped" id="tabellaVarianteCertificato" style="width:100%">
		<thead>
			<tr>
				<th>Nome</th>
				<th>Data adozione</th>
				<th>Data approvazione</th>
				<th>Descrizione</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>{{nome}}</td>
				<td>{{dataDelAdoz}}</td>
				<td>{{dataDelAppr}}</td>
				<td>{{descrizione}}</td>
			</tr>
		</tbody>
	</table>
</script>
<script id="modaleCertificatoUrbanisticoSelezioneParticelle" type="text/x-handlebars-template">
	<table class="table table-striped" id="tabellaSelezioneParticelleHeader" style="margin-bottom: 0px;">
		<thead>
			<tr>
				<th style="width:10%">Foglio</th>
				<th style="width:90%">Numero</th>
			</tr>
		</thead>
	</table>
	<table class="table table-striped" id="tabellaSelezioneParticelle">
		<tbody style="max-height: 280px;">
			{{#each listaParticelle}}
			<tr>
				<td style="width:10%">{{foglio}}</td>
				<td style="width:90%">{{numero}}</td>
			</tr>
			{{/each}}
		</tbody>
	</table>
</script>
<script id="modPianoParticellareDaSelezioneLayer" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation"><a href="#" role="tab" data-toggle="tab" id="menuRicercaVarianti">Ricerca</a></li>
		<li role="presentation"><a href="#" role="tab" data-toggle="tab" id="menuAggiungiVariante">Aggiungi</a></li>
	</ul>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Cartografia</a></li>
					<li class="active"><a href="#">Seleziona Layer</a></li>
				</ul>
			</div>
			<div id="ricerca" class="">
				<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading">
						  <h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri1">
								<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per Layer
							</a>
						  </h4>
						</div>
						<div id="collapseFiltri1" class="panel-collapse collapse in show">
							<div class="panel-body">
								<div id="particelle-menu-content"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnQ"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleCreaSelezionaCartografie" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 ">
				<h2>Elenco Cartografie</h2>
			</div>
			<!--<div id="cartografia-variante" class="jstree-checkbox-only-on-leaf" style="height: 100%;"></div>-->
			<div id="catalogoVarianteAggiunta" style="margin-bottom: 10px;"></div>
				<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva cartografia" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleCreaModificaIndici" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<label for="articolo" class="col-sm-12 control-label">Articolo</label>
				<div class="form-group col-sm-12">
      				<input id="articolo" type="text" class="form-control" name="articolo">
				</div>
				<label for="elencoPagine" class="col-sm-12 control-label">Elenco Pagine</label>
				<div class="form-group col-sm-12">
      				<input id="elencoPagine" type="text" class="form-control" name="elencoPagine">
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva indici" id="salvaBtnIndici" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleImportaIndiceDocumento" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="col-md-12 tabbable-panel">
				<div class="col-sm-12">
					<label>File</label>
      				<input type="file" name="importIndiceDocumento" id="importIndiceDocumento">
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva indice" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleSelezionaCodici" type="text/x-handlebars-template">
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer" style="overflow-y:hidden">
			<div class="col-md-12">
				<h2>Elenco Codici ZTO</h2>
				<!-- <p id="codiciSelezionati"></p> -->
				<table id="tabellaCodiciZTO" class="table table-striped" style="width: auto;"></table>
			</div>
			
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva codici" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
		
	</div>
</div>
</script>

<script id="listaVarianti" type="text/x-handlebars-template">
	{{{messaggioLayerAssociati}}}</br>
	{{#each listaVarianti}}
	<span class='itemListVar'>- {{nomeVariante}}</span></br>
	{{/each}}</br>
	{{messaggioDiConferma}}
</script>

<script id="modaleStrutturaPianiInformativi" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
	<ul class="dropdown-menu multi-level nav" role="tablist">
		<li role="presentation"><a href="#" id="" aria-controls="menuRicercaEnti" role="tab" data-toggle="tab">Ricerca</a></li>
		<li role="presentation"><a href="#" id="" aria-controls="menuAggiungiEnte" role="tab" data-toggle="tab">Nuovo</a></li>
	</ul>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li><a href="#">Strati Informativi</a></li>
				</ul>
			</div>
			<div id="ricerca">
				<div class="col-md-6" style="float: left">
					<div class="table-responsive" style="height: 400px;">
						<div id="menu-content-variante"></div>
					</div>
				</div>
				<div class="col-md-6" style="float: left">
					<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="aggiungiGruppo" style="margin-bottom: 10px">&nbsp;Aggiungi Gruppo</button>
					<div id="catalogoVariante"></div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleNuovoGruppoLayer" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<form class="form-horizontal">
				<div class="panel-body row nom">
					<div class="col-sm-6 col-md-6 col-lg-6">
						<label>Indentificativo gruppo *</label>
						<div class="input-group">
							<select class="fa form-control" id="codiceGruppo">
								<option value="">Seleziona...</option>
								<option value="CDU 01">CDU 01</option>
								<option value="CDU 02">CDU 02</option>
								<option value="CDU 03">CDU 03</option>
								<option value="CDU 04">CDU 04</option>
								<option value="CDU 05">CDU 05</option>
								<option value="CDU 06">CDU 06</option>
								<option value="CDU 07">CDU 07</option>
							</select>   
						</div>
					</div>
					<div class="col-sm-6 col-md-6 col-lg-6">
						<label for="nomeGruppo" class="control-label">Nome Gruppo *</label>
						<div class="input-group">
							<input id="nomeGruppo" type="text" class="form-control" placeholder="Nome gruppo...">
						</div>
					</div>
				</div>
			</form>
				<!-- </div> -->
			<!-- </div> -->
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva il tipo documento" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
		
	</div>
</div>
</script>

<script id="creazioneCatalogo" type="text/x-handlebars-template">
<div class="boxMappa_body" style="height: 370px;">
	<div class="panel-group" id="accordionLB" role="tablist" aria-multiselectable="true">
		<div id="elenco-mappe-content" class="elenco-mappe-content">
			<div class="panel-group p10 clearfix" id="accordionLB" role="tablist" aria-multiselectable="true" style="heigth:100%">
				<div class="panel panel-default">
					{{#result}}
						<div class="panel-heading clearfix" role="tab" id="headingLB" style="margin-bottom: 4px;">
							<h4 class="panel-title" style="margin-bottom: 0px;">
								<a id="visualizza{{id}}" role="button" data-toggle="collapse" data-parent="#accordionLB" href="#visualizza{{id}}" aria-expanded="false" class="collapsed" aria-controls="collapseLB" title="{{nomeGruppo}}">
									{{nomeGruppo}} 
									{{#ifEliminaGruppo nomeGruppo}}
										<button type="button" id="deleteGruppo" name="rimuoviGruppo" data-id="{{id}}" class="bttn btn-trasp rimuovi-gruppo-btn" data-info="Rimuovi gruppo" onclick="deleteGruppo('{{id}}')"><em class="fa fa-trash-o"></em></button>
									{{/ifEliminaGruppo}}
								</a>
							</h4>
						</div>
						<div id="visualizza{{id}}" class="panel-collapse collapse in show" role="tabpanel" aria-labelledby="headingLB">
							{{#each listCatalogoLayer}}
								<div class="itemMappa">
									<a class="linkItemMappa" href="#" title="{{nomeLayer}}">{{nomeLayer}}</a>
									<button type="button" name="rimuoviLayer" data-id="{{idLayer}}" class="bttn btn-trasp rimuovi-layer-btn pull-right" data-info="Rimuovi layer" onclick="deleteLayer('{{id}}', '{{idLayer}}')"><em class="fa fa-trash-o"></em></button>
								</div>
							{{else}}
								<span>Non sono presenti layer per questo gruppo</span>
							{{/each}}
						</div>
					{{/result}}
				</div>
			</div>
		</div>
	</div>
</div>
</script>

<script id="modaleAssociaLayer" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<form class="form-horizontal">
				<div class="panel-body row nom">
					<div class="col-sm-12 col-md-12">
						<label for="nomeGruppo" class="control-label">Nome Gruppo</label>
	      				<select id="gruppoLayerSelezionato" type="text" class="form-control" name="gruppoLayerSelezionato">
							{{#each listaGruppi}}
							<option value="{{this.id}}">{{this.nomeGruppo}}</option>
							{{/each}}
						</select>
					</div>
				</div>
				<div class="panel-body row nom" id="divColonneLayer">
					<div class="col-sm-12 col-md-12">
						<label for="nomeGruppo" class="control-label">Colonna</label>
	      				<select id="nomeColonnaLayer" type="text" class="form-control" name="nomeColonnaLayer">
							{{#each listaColonne}}
							<option value="{{this}}">{{this}}</option>
							{{/each}}
						</select>
					</div>
				</div>
			</form>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva il tipo documento" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
		
	</div>
</div>
</script>

<script id="modaleCreazioneCatalogoVariante" type="text/x-handlebars-template">
<div class="boxMappa_body" style="height: 400px;">
	<div class="panel-group" id="accordionLB" role="tablist" aria-multiselectable="true">
		<div id="elenco-mappe-content" class="elenco-mappe-content">
			<div class="panel-group p10 clearfix" id="accordionLB" role="tablist" aria-multiselectable="true" style="heigth:100%">
				<div class="panel panel-default">
					{{#result}}
						<div class="panel-heading clearfix" role="tab" id="headingLB" style="margin-bottom: 4px;">
							<h4 class="panel-title" style="margin-bottom: 0px;">
								<a id="visualizza{{id}}" role="button" data-toggle="collapse" data-parent="#accordionLB" href="#visualizza{{id}}" aria-expanded="false" 
										class="collapsed" aria-controls="collapseLB" title="{{nomeGruppo}}">
									{{nomeGruppo}}
								</a>
							</h4>
						</div>
						<div id="visualizza{{id}}" class="panel-collapse collapse in show" role="tabpanel" aria-labelledby="headingLB">
							{{#each listCatalogoLayer}}
								{{#containString idLayer nomeLayer}}
									{{else}}
								{{/containString}}
							{{else}}
								<span>Non sono presenti layer per questo gruppo</span>
							{{/each}}
						</div>
					{{/result}}
				</div>
			</div>
		</div>
	</div>
</div>
</script>

<script id="modaleInterrogazionePiano" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li><a href="#">Interrogazione Piano</a></li>
				</ul>
			</div>
			<div id="ricerca">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row nom nop">
							<div class="form-group col-md-4">
								<label for="enteCDU">Ente</label>
								<select id="enteCDU" class="form-control" name="enteCDU">
									 <!-- <option>Seleziona...</option> -->
									<option selected>Messina</option>
								</select>
							</div>
							<div class="form-group col-md-4">
								<label for="modelloCDU">Modello da utilizzare</label>
								<select id="modelloCDU" class="form-control" name="modelloCDU">
									<!-- <option>Seleziona...</option> -->
									<option selected>Analisi urbanistica</option>
								</select>
							</div>
						</div>
						<div style="background: #ccc;padding: 5px 10px;text-align: center;border-radius: 0;margin-bottom: 12px;" class="">
							<strong>Seleziona ambito variante per l'interrogazione piano</strong>
						</div>
						<div class="row nom nop">
							<div class="form-group col-md-6"><h6>Selezionare la variante</h6></div>
							<div class="form-group col-md-6"><h6>Hai selezionato la seguente variante</h6></div>
						</div>
						<div class="row nom nop">
							<div class="form-group col-md-6 content-checkbox">
								<div class="custom-control custom-radio">
									<input type="radio" id="vigente" name="optradio" value="vigente" class="custom-control-input">
									<label class="custom-control-label" style="float:left" for="vigente">Vigente</label>
								</div>
								<div class="custom-control custom-radio">
									<input type="radio" id="salvaguardia" name="optradio" value="salvaguardia" class="custom-control-input">
									<label class="custom-control-label" style="float:left" for="salvaguardia">In salvaguardia</label>
								</div>
								<div class="custom-control custom-radio">
									<input type="radio" id="datavigente" name="optradio" value="datavigente" class="custom-control-input">
									<label class="custom-control-label" style="float: left;width: 30%;" for="datavigente">
										Vigente dalla data
									</label>
									<div class="input-group" style="float: left;width: 50%;">
										<input id="dataVigenteSel" type="text" class="form-control" readonly="" style="height: 28px;">
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
									</div>
								</div>
							</div>
							<div class="form-group col-md-6" id="varianteScelta">
								<table class="table table-striped" id="tabellaVarianteCertificato" style="width:100%">
									<thead>
										<tr>
											<th>Nome</th>
											<th>Data adozione</th>
											<th>Data approvazione</th>
											<th>Descrizione</th>
										</tr>
									</thead>
									<tbody><tr><td colspan="4">Nessun ambito di variante selezionato</td></tr></tbody>
								</table>
							</div>
						</div>
						 
						<div class="row nom nop">
						 	<div class="col-sm-12">
							<!-- STEP ACQUISIZIONE CDU -->
								<div id="acquisizioneCduDiv">
									<div style="background: #ccc;padding: 5px 10px;text-align: center;border-radius: 0;margin-bottom: 12px;" class="">
										<strong>Modalit&aacute; di acquisizione geometria interrogazione piano</strong>
									</div>
									<div class="search row nom nop">
										<div class="form-group col-md-6 "><h6>Modalit&aacute; di acquisizione</h6></div>
										<div class="form-group col-md-6 nom"><h6>Hai selezionato le seguenti geometrie</h6></div>
									</div>
									<div class="search row nom nop">
										<div class="col-sm-12 col-md-3">
											<div class="panel panel-default">
												<div class="panel-heading">Seleziona da mappa</div>
												<div class="panel-body text-center">
													<div class="hovereffect">
														<img src="./images/catasto/seleziona.png" class="img-responsive img-fluid" alt="Seleziona su mappa">
														<div class="overlay-catasto">
															<h2>Seleziona su mappa</h2>
															<a class="info bttn btn-trasp" href="#" id="selezionaSuMappaBtn">Inizia</a>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-sm-12 col-md-3">
											<div class="panel panel-default">
												<div class="panel-heading">Traccia su mappa</div>
												<div class="panel-body text-center">
													<div class="hovereffect">
														<img src="./images/catasto/traccia.png" class="img-responsive img-fluid" alt="Traccia su mappa">
														<div class="overlay-catasto">
															<h2>Traccia su mappa</h2>
															<a class="info bttn btn-trasp" href="#" id="tracciaSuMappaBtn">Inizia</a>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-sm-12 col-md-6">
											<div class="row nom nop" id="selezioneGeometria">
												<table class="table table-striped" id="tabellaSelezioneParticelle" style="margin-bottom: 0px;">
													<thead>
														<tr>
															<th style="width:10%">Foglio</th>
															<th style="width:90%">Numero</th>
														</tr>
													</thead>
												</table>
												<table class="table table-striped" id="tabellaSelezioneParticelle">
													<tbody><tr><td colspan="2">Nessuna geometria selezionata</td></tr></tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="dettaglio">
				<div class="col-md-12 tabbable-panel" id="dettaglioAnagAccesso">
					<div class="tabbable-line">
						<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
							<li  id="liDetAnagraficaAccesso" class="nav-item" data-info="Dettaglio">
								<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
									<strong>Dettaglio</strong>
								</a>
							</li>
						</ul>
						<div class="tab-content" id="dettaglioTabContent">
							<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaInterrogazionePiano" style="width:100%">
										<thead>
											<tr>
												<th>Area</th>
												<th>Gruppo Layer</th>
												<th>Nome Layer</th>
												<th>Codice classificazione geometria</th>
												<th>Numero entita intersecate</th>
												<th>Area intersecata</th>
												<th>Percentuale area intersecata</th>
												<th></th> 
											</tr>
										</thead>
										<tbody></tbody>
										<tfoot>
											<tr>
												<td>Area</td>
												<td>Gruppo Layer</td>
												<td>Nome Layer</td>
												<td>Codice classificazione geometria</td>
												<td>Numero entita intersecate</td>
												<td>Area intersecata</td>
												<td>Percentuale area intersecata</td>
												<td></td> 
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
					<li><a data-info="Dettaglio interrogazione piano" href="#" id="dettaglioPianoBtn"><em class="fa fa-arrow-right"></em>&nbsp;&nbsp;Dettaglio</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
					<li><a data-info="Indietro" href="#" id="indietroBtn"><em class="fa fa-arrow-left"></em>&nbsp;&nbsp;Indietro</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="modaleInformazioniAggiuntiveCdu" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<form class="form-horizontal">
				<div class="row nom nop">
					<div class="form-group col-sm-3">
						<label for="protocollo" class="control-label">* Protocollo</label>
						<div class="input-group">
							<input id="protocollo" type="text" class="form-control" placeholder="Protocollo...">
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="dataRichiesta" class="control-label">Data presentazione richiesta</label>
						<div class="input-group">
							<input id="dataRichiesta" type="text" class="form-control" placeholder="Data richiesta...">
							<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
						</div>
					</div>
					<div class="form-group col-sm-5">
						<label for="nomeDitta" class="control-label">Nome ditta</label>
						<div class="input-group">
							<input id="nomeDitta" type="text" class="form-control" placeholder="Nome ditta...">
						</div>
					</div>
					<hr class="form-group col-sm-11"></hr>
					<div class="form-group col-sm-4">
						<label for="dataVisuraCatastale" class="control-label">Data Visura Catastale<label>
						<div class="input-group">
							<input id="dataVisuraCatastale" type="text" class="form-control" placeholder="Data Visura Catastale...">
							<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="riferimentoMappaCatastale" class="control-label">Rif. Mappa Catastale</label>
						<div class="input-group">
							<input id="riferimentoMappaCatastale" type="text" class="form-control" placeholder="Rif. Mappa Catastale...">
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="riferimentoVisuraCatastale" class="control-label">Rif. Visura Catastale</label>
						<div class="input-group">
							<input id="riferimentoVisuraCatastale" type="text" class="form-control" placeholder="Rif. Visura Catastale...">
						</div>
					</div>
					<hr class="form-group col-sm-11"></hr>
					<div class="form-group col-sm-6">
						<label for="codiceModelloF23" class="control-label">Codice Modello F23</label>
						<div class="input-group">
							<input id="codiceModelloF23" type="text" class="form-control" placeholder="Codice Modello F23...">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label for="dataModelloF23" class="control-label">Data pagamento F23</label>
						<div class="input-group">
							<input id="dataModelloF23" type="text" class="form-control" placeholder="Data pagamento F23...">
							<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="importoPrimoVersamento" class="control-label">Importo 1&deg; versamento</label>
						<div class="input-group">
							<input id="importoPrimoVersamento" type="text" class="form-control" placeholder="Importo primo versamento...">
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="dataPrimoVersamento" class="control-label">Data 1&deg; versamento</label>
						<div class="input-group">
							<input id="dataPrimoVersamento" type="text" class="form-control" placeholder="Data primo versamento...">
							<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="codicePrimoVersamento" class="control-label">Codice 1&deg; versamento</label>
						<div class="input-group">
							<input id="codicePrimoVersamento" type="text" class="form-control" placeholder="Codice primo versamento...">
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label for="importoSecondoVersamento" class="control-label">Importo 2&deg; versamento</label>
						<div class="input-group">
							<input id="importoSecondoVersamento" type="text" class="form-control" placeholder="Importo secondo versamento...">
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="dataSecondoVersamento" class="control-label">Data 2&deg; versamento</label>
						<div class="input-group">
							<input id="dataSecondoVersamento" type="text" class="form-control" placeholder="Data secondo versamento...">
							<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="codiceSecondoVersamento" class="control-label">Codice 2&deg; versamento</label>
						<div class="input-group">
							<input id="codiceSecondoVersamento" type="text" class="form-control" placeholder="Codice secondo versamento...">
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label for="dipartimento" class="control-label">Dipartimento</label>
						<div class="input-group">
							<input id="dipartimento" type="text" class="form-control" placeholder="Dipartimento...">
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="servizio" class="control-label">Servizio</label>
						<div class="input-group">
							<input id="servizio" type="text" class="form-control" placeholder="Servizio...">
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="emailReferente" class="control-label">Email Referente</label>
						<div class="input-group">
							<input id="emailReferente" type="text" class="form-control" placeholder="Email Referente...">
						</div>
					</div>

					<div class="form-group col-sm-4">
						<label for="responsabileServizio" class="control-label">Responsabile del servizio</label>
						<div class="input-group">
							<input id="responsabileServizio" type="text" class="form-control" placeholder="Responsabile del servizio...">
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="responsabilePianoTerritoriale" class="control-label">Responsabile Pian. Territoriale</label>
						<div class="input-group">
							<input id="responsabilePianoTerritoriale" type="text" class="form-control" placeholder="Responsabile Pian. Territoriale...">
						</div>
					</div>
					<div class="form-group col-sm-4">
						<label for="dirigenteDipartimento" class="control-label">Dirigente del Dipartimento</label>
						<div class="input-group">
							<input id="dirigenteDipartimento" type="text" class="form-control" placeholder="Dirigente del Dipartimento...">
						</div>
					</div>
				</div>
			</form>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Salva il tipo documento" id="salvaBtn" href="#"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="contGruppoV" type="text/x-handlebars-template">
	<div class="panel-group prl10 clearfix" id="accordionLB" role="tablist" aria-multiselectable="true">
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingLB">
				<h4 class="panel-title">
					
					<input type="checkbox" id="visualizzatoreDatiBaseVariante_chk" style="margin-left: -100%; margin-top: 2%; width: 20px;" onchange="(appUrbamid).attivaDisattivaTuttiLayer('Variante')">

					<a id="visualizzatoreDatiBase{{idGruppo}}" role="button" data-toggle="collapse" data-parent="#accordionLB" href="#visualizzatore{{idGruppo}}" aria-expanded="false" aria-controls="collapseLB" title="Chiudi {{nomeGruppo}}" style="padding-right: 5%;padding-left: 8%;">
						{{nomeGruppo}}
					</a>
				</h4>
			</div>
			<div id="visualizzatore{{idGruppo}}" class="panel-collapse in collapse" role="tabpanel" aria-labelledby="headingLB">
				<div id="{{idGruppo}}-layer" class="panel-body">
				</div>
			</div>
		</div>
	</div>
</script>

<script id="itemViewerSuffixGraf" type="text/x-handlebars-template">
	<div id="{{itemCheckId}}{{suffixGraf}}_divchk" class="checkbox itemVisualizzatore">
		<label onchange="appMappa.addLayerMap('{{layerName}}', '{{title}}', null, '', '{{idParent}}', '{{itemCheckId}}')">
			<input id="{{itemCheckId}}_chk" data-gruppo="Variante" name="visualizzatore" type="checkbox"></input>
			<span class="cr" title="{{title}}"><i id="{{itemCheckId}}_chkImg" class="cr-icon glyphicon glyphicon-ok"></i></span>
		</label>
		<span class="layerName" title='{{title}}'>{{title}}</span>
		<div class="toolbar">
			<div class="btn-group" title="Legenda">		
				<button type="button" class="btn btn-secondary iconLegend" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
						onclick="appUrbamid.showLegend('{{itemCheckId}}', '{{hrefLegend}}', '{{title}}')">
    				<i class="fa fa-file-image-o" aria-hidden="true"></i>
  				</button>
			</div>
			<div class="btn-group" title="Opzioni">
		  		<button type="button" class="btn btn-secondary dropdown-toggle iconLegend" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="Opzioni layer">
    				<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
  				</button>
  				<div class="dropdown-menu dropdown-menu-right">
					<a class="dropdown-item" href="#" onclick="appUrbamid.showOpacity('{{itemCheckId}}', '{{opacity}}', '{{layerName}}')" title="Trasparenza">
						<i class="fa fa fa-cog" aria-hidden="true"></i>
						Trasparenza
					</a>
				</div>
			</div>
		</div>
	</div>
	<div id="{{itemCheckId}}_divgear" class="hidden itemGear">
		<span class="title-opa">Trasparenza</span>
		<span id="value-opa" class="value-opa" title="Valore trasparenza">{{opacityPercent}}%</span>
		<span class="close-opa"><i class="fa fa-times" onclick="appUrbamid.hideOpacity('{{itemCheckId}}')" id="catalogoClose" data-info="Chiudi Catalogo"></i></span>
		<input type="range" title="Modifica trasparenza layer" class="custom-range" id="{{itemCheckId}}_slider"  min="0" max="10" value="{{opacity}}" onchange="appUrbamid.changeOpacity('{{layerName}}','{{itemCheckId}}')">
	</div>
</script>

<script id="modaleAnagraficaCdu" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Piano Regolatore</a></li>
					<li><a href="#">Struttura del piano</a></li>
					<li class="active"><a href="#">Anagrafica CDU</a></li>
				</ul>
			</div>
			<div id="ricerca" class="">
				<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading">
						  <h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri1">
								<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
							</a>
						  </h4>
						</div>
						<div id="collapseFiltri1" class="panel-collapse collapse in show">
							<div class="panel-body row nom">
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Protocollo</label>
									<input id="protocolloAnagraficaCdu" type="text" class="form-control" name="protocolloAnagraficaCdu" placeholder="Protocollo...">
								</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Data Creazione</label>
									<input id="dataCreazione" type="text" class="form-control" name="dataCreazione" placeholder="Data Creazione...">  
								</div>
								<div class="col-sm-12 col-md-6 col-lg-6"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaAnagraficaCdu" style="width:100%">
						<thead>
					    	<tr>
					      		<th>Protocollo</th>
					      		<th>Data Creazione</th>
					      		<th></th>
					    	</tr>
					  	</thead>
					  	<tbody></tbody>
						<tfoot>
			            	<tr>
					      		<th>Protocollo</th>
					      		<th>Data Creazione</th>
					      		<th></th>
					    	</tr>
			        	</tfoot>
					</table>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnAnagraficaCdu"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>