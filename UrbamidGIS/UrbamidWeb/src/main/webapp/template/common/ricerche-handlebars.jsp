<script id="rigaDettaglioIntestatari" type="text/x-handlebars-template">
<tr class="riga-da-rimuovere-al-momento-opportuno">
	<td colspan="19">
		<div class="table-responsive">
			<table class="table table-striped tabella-riga-dettaglio-intestatari" style="width:100%">
				<thead>
					<tr>
			      		<th colspan="3" class="text-center"><span class="text-danger"><strong>Lista intestatari</strong></span></th>
					</tr>
				   	<tr>
			      		<th>Nome e Cognome/Denominazione</th>
			      		<th>Codice fiscale/Partita IVA</th>
					</tr>
				</thead>
				<tbody>
					{{#each intestatari}}
					<tr>
			      		<td>{{this.nome}}</td>
			      		<td>{{this.codiceFiscale}}</td>
					</tr>
					{{/each}}
				</tbody>
			</table>
		</div>
	</td>
</tr>
</script>

<script id="rigaDettaglioIntestatariDiritto" type="text/x-handlebars-template">
<tr class="riga-da-rimuovere-al-momento-opportuno">
	<td colspan="19">
		<div class="table-responsive">
			<table class="table table-striped tabella-riga-dettaglio-intestatari-diritto" style="width:100%">
				<thead>
					<tr>
			      		<th colspan="3" class="text-center"><span class="text-danger"><strong>Lista intestatari</strong></span></th>
					</tr>
				   	<tr>
			      		<th>Nome e Cognome/Denominazione</th>
			      		<th>Codice fiscale/Partita IVA</th>
						<th>Diritto</th>
					</tr>
				</thead>
				<tbody>
					{{#each intestatari}}
					<tr>
			      		<td>{{this.nome}}</td>
			      		<td>{{this.codiceFiscale}}</td>
			      		<td>{{this.diritto}}</td>
					</tr>
					{{/each}}
				</tbody>
			</table>
		</div>
	</td>
</tr>
</script>

<script id="rigaDettaglioIntestatariDirittoVuota" type="text/x-handlebars-template">
<tr class="riga-da-rimuovere-al-momento-opportuno">
	<td colspan="19">
		<div class="table-responsive">
			Nessun risultato
		</div>
	</td>
</tr>
</script>


<!-- 
   _____ ____  __________________________________   ____  __________  _____ ____  _   ________   _______________ ____________  ________
  / ___// __ \/ ____/ ____/ ____/_  __/_  __/  _/  / __ \/ ____/ __ \/ ___// __ \/ | / / ____/  / ____/  _/ ___//  _/ ____/ / / / ____/
  \__ \/ / / / / __/ / __/ __/   / /   / /  / /   / /_/ / __/ / /_/ /\__ \/ / / /  |/ / __/    / /_   / / \__ \ / // /   / /_/ / __/   
 ___/ / /_/ / /_/ / /_/ / /___  / /   / / _/ /   / ____/ /___/ _, _/___/ / /_/ / /|  / /___   / __/ _/ / ___/ // // /___/ __  / /___   
/____/\____/\____/\____/_____/ /_/   /_/ /___/  /_/   /_____/_/ |_|/____/\____/_/ |_/_____/  /_/   /___//____/___/\____/_/ /_/_____/   
                                                                                                                                       
-->
<script id="ricSoggettiPersoneFisiche" type="text/x-handlebars-template">
<!-- DROPDOWN MENU -->
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<!-- PAGINA RICERCA SOGGETTI PERSONE FISICHE -->
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Ricerca soggetti</a></li>
					<li class="active"><a href="#">Persone fisiche</a></li>
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
							<div class="panel-body row nom">
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Nome</label>
									<div class="input-group">
										<input id="nome" type="text" class="form-control" name="nome" placeholder="Nome...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-user"></i></span></div>
			    					</div>
								</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Cognome</label>
									<div class="input-group">
										<input id="cognome" type="text" class="form-control" name="cognome" placeholder="Cognome...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-user"></i></span></div>
			    					</div>
								</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
				        			<label>Sesso</label>
				        			<select class="fa form-control" id="sesso">
										<option value="" selected>Seleziona...</option>
										<option value="1">Uomo</option>
										<option value="2">Donna</option>
									</select>
				        		</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Codice fiscale</label>
									<div class="input-group">
										<input id="codiceFiscale" type="text" class="form-control" name="codiceFiscale" placeholder="Codice fiscale...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-credit-card"></i></span></div>
			    					</div>
								</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Data nascita da</label>
									<div class="input-group">
										<input id="dataNascitaDa" type="text" class="form-control" name="dataNascitaDa" placeholder="Data nascita da...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
			    					</div>
								</div>
								<div class="col-sm-12 col-md-6 col-lg-3">
									<label>Data nascita a</label>
									<div class="input-group">
										<input id="dataNascitaA" type="text" class="form-control" name="dataNascitaA" placeholder="Data nascita a...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
			    					</div>
								</div>
				        		<div class="col-sm-12 col-md-6 col-lg-3">
				        			<label>Provincia di nascita</label>
				        			<select class="form-control" id="provinciaNascita" name="provinciaNascita">
										<option value="" selected>Seleziona...</option>
									</select>
				        		</div>
						        <div class="col-sm-12 col-md-6 col-lg-3">
						        	<label>Comune di nascita</label>
						        	<select class="form-control" id="comuneNascita" name="comuneNascita">
										<option value="" selected>Seleziona...</option>
									</select>
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
					      		<th>Nominativo</th>
					      		<th>Sesso</th>
					      		<th>Codice fiscale</th>
					      		<th>Comune</th>
					      		<th>Provincia</th>
					      		<th>Data di nascita</th>
					      		<th>Note</th>
					      		<th></th>
					    	</tr>
					  	</thead>
					  	<tbody></tbody>
						<tfoot>
			            	<tr>
					      		<th>Nominativo</th>
					      		<th>Sesso</th>
					      		<th>Codice fiscale</th>
					      		<th>Comune</th>
					      		<th>Provincia</th>
					      		<th>Data di nascita</th>
					      		<th>Note</th>
					      		<th></th>
					    	</tr>
			        	</tfoot>
					</table>
				</div>
				<div id="dataAggiornamento">
					 
				</div> 
			</div>
		
			<!-- DETTAGLIO -->
			<div class="col-md-12 hidden tabbable-panel" id="dettaglio">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li class="nav-item" data-info="Anagrafica Persona">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica Persona</strong>
							</a>
			  			</li>
			  			<li class="nav-item" data-info="Unita Immobiliari Urbane">
			    			<a class="nav-link" id="uiu-tab1" data-toggle="tab" href="#uiuTabContent1" role="tab" aria-controls="uiuTabContent1" aria-selected="false">
								<strong>UIU</strong>
							</a>
			  			</li>
			  			<li class="nav-item" data-info="Particelle terreni">
			    			<a class="nav-link" id="pt-tab1" data-toggle="tab" href="#ptTabContent1" role="tab" aria-controls="ptTabContent1" aria-selected="false">
								<strong>PT</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
								<form class="form-horizontal">
									<!-- DETTAGLIO -->
									<div class="row nom nop">
										<div class="form-group col-md-4">
								    		<label for="nominativoDettaglio" class="control-label">Nominativo</label>
								    		<div class="input-group">
												<input type="text" hidden id="idSoggetto">
								    			<input type="text" class="form-control" id="nominativoDettaglio" readonly>
												<div class="input-group-append"><span class="input-group-text"><i class="fa fa-user"></i></span></div> 
											</div>
								    	</div>
										<div class="form-group col-md-4">
											<label for="dataNascitaDettaglio" class="control-label">Data di nascita</label>
			    							<div class="input-group">
			      								<input type="text" class="form-control" id="dataNascitaDettaglio" readonly>
												<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
											</div>
			    						</div>
										<div class="form-group col-md-4">
			    							<label for="codiceFiscaleDettaglio" class="control-label">Codice fiscale</label>
											<div class="input-group">
				      							<input type="text" class="form-control" id="codiceFiscaleDettaglio" readonly>
												<div class="input-group-append"><span class="input-group-text"><i class="fa fa-credit-card"></i></span></div>
											</div>
			    						</div>
			  						</div>
									<div class="row nom nop">
			  							<div class="form-group col-md-4">
			    							<label for="comuneDettaglio" class="control-label">Comune</label>
			    							<input type="text" class="form-control" id="comuneDettaglio" readonly>
			    						</div>
										<div class="form-group col-md-4">
			    							<label for="provinciaDettaglio" class="control-label">Provincia</label>
			    							<input type="text" class="form-control" id="provinciaDettaglio" readonly>
			    						</div>
										<div class="form-group col-md-2">
			    							<label for="sessoDettaglio" class="control-label">Sesso</label>
			    							<input type="text" class="form-control" id="sessoDettaglio" readonly>
			    						</div>
			  						</div>
			  						<!-- NOTE -->
			  						<div class="form-group col-md-12">
								    	<label for="noteDettaglio" class="col-sm-12 col-md-1 control-label">Note</label>
								    	<div class="input-group">
											<textarea class="form-control" id="noteDettaglio" rows="5" readonly></textarea>
											<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
										</div>
								  	</div>
								</form>					
							<!--/div-->			
						</div>
			  			<div class="tab-pane" id="uiuTabContent1" role="tabpanel" aria-labelledby="uiu-tab1">
							<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaUIU" style="width:100%">
									<thead>
								    	<tr>
											<th>
								      		<th>UIU</th>
								      		<th>Categoria</th>
								      		<th>Sez. urb.</th>
								      		<th>Foglio</th>
								      		<th>Numero</th>
								      		<th>Sub</th>
								      		<th>Indirizzo</th>
								      		<th>Comune</th>
								      		<th>Provincia</th>
								      		<th>Diritto</th>
								      		<th>Partita</th>
								      		<th>Rendita (L)</th>
								      		<th>Rendita (E)</th>
											<th></th>
								    	</tr>
								  	</thead>
								  	<tbody>
								  	</tbody>
								</table>
							</div>
							</div>				
						</div>
			  			<div class="tab-pane" id="ptTabContent1" role="tabpanel" aria-labelledby="pt-tab1">
							<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaPT" style="width:100%">
										<thead>
									    	<tr>
												<th></th>
									      		<th>Sezione</th>
									      		<th>Foglio</th>
									      		<th>Numero</th>
									      		<th>Subalterno</th>
									      		<th>Comune</th>
									      		<th>Provincia</th>
									      		<th>Diritto</th>
									      		<th>Partita</th>
												<th></th>
									    	</tr>
									  	</thead>
									  	<tbody>
									  	</tbody>
									</table>
								</div>
							</div>				
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" class="pulsante-ricerca" href="#" id="eseguiBtnSoggPerFis" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" class="pulsante-ricerca" href="#" id="azzeraBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Esporta i dati" class="pulsante-ricerca" href="#" id="esportaBtnSoggPerFis"><em class="fa fa-download"></em>&nbsp;&nbsp;Esporta</a></li>
					<li><a class="pulsante-ricerca pulsante-dettaglio" data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li class="hidden"><a data-info="Ritorna alla ricerca" class="pulsante-dettaglio" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
					<li><a class="pulsante-ricerca pulsante-dettaglio" data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<!-- 
   _____ ____  __________________________________   ____________  ______  ________  ______________
  / ___// __ \/ ____/ ____/ ____/_  __/_  __/  _/  / ____/  _/ / / / __ \/  _/ __ \/  _/ ____/  _/
  \__ \/ / / / / __/ / __/ __/   / /   / /  / /   / / __ / // / / / /_/ // // / / // // /    / /  
 ___/ / /_/ / /_/ / /_/ / /___  / /   / / _/ /   / /_/ // // /_/ / _, _// // /_/ // // /____/ /   
/____/\____/\____/\____/_____/ /_/   /_/ /___/   \____/___/\____/_/ |_/___/_____/___/\____/___/   
                                                                                                  
 -->
<script id="ricSoggettiGiuridici" type="text/x-handlebars-template">
<!-- DROPDOWN MENU -->
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<!-- PAGINA RICERCA SOGGETTI GIURIDICI -->
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Ricerca soggetti</a></li>
					<li class="active"><a href="#">Soggetti Giuridici</a></li>
				</ul>
			</div>
			<div id="ricerca">
			<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading">
					  <h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri2">
							<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
						</a>
					  </h4>
					</div>
					<div id="collapseFiltri2" class="panel-collapse collapse in show">
						<div class="panel-body row nom">
							<div class="col-md-3">
								<label>Denominazione</label>
								<input id="denominazione" type="text" class="form-control" name="denominazione" placeholder="Denominazione...">   
							</div>
							<div class="col-md-3">
			        			<label>Provincia</label>
			        			<select class="form-control" id="provinciaNascitaSG" name="provinciaNascitaSG">
									<option value="" selected>Seleziona...</option>
								</select>
			        		</div>
					        <div class="col-md-3">
					        	<label>Comune</label>
					        	<select class="form-control" id="comuneNascitaSG" name="comuneNascitaSG">
									<option value="" selected>Seleziona...</option>
									<option>Trapani</option>
								</select>
					        </div>
							<div class="col-md-3">
								<label>Partita IVA</label>
								<div class="input-group">
									<input id="codiceFiscale" type="text" class="form-control" name="codiceFiscale" placeholder="Partita IVA...">   
									<div class="input-group-append"><span class="input-group-text"><i class="fa fa-credit-card"></i></span></div>
		    					</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- TABELLA RISULTATI -->
			<div class="table-responsive">
				<table class="table table-striped" id="tabellaRisultatiSG" style="width:100%">
					<thead>
				    	<tr>
				      		<th>Denominazione</th>
				      		<th>Codice fiscale</th>
				      		<th>Comune</th>
				      		<th>Provincia</th>
				      		<th></th>
				    	</tr>
				  	</thead>
				  	<tbody></tbody>
					<tfoot>
		            	<tr>
				      		<th>Denominazione</th>
				      		<th>Codice fiscale</th>
				      		<th>Comune</th>
				      		<th>Provincia</th>
				      		<th></th>
				    	</tr>
		        	</tfoot>
				</table>
			</div>
				<div id="dataAggiornamento">
					 
				</div> 
			</div>
		
			<!-- DETTAGLIO -->
			<div class="col-md-12 hidden tabbable-panel" id="dettaglio" >
				<div class="tabbable-line">
				<ul class="nav nav-tabs" id="dettaglioTab2" role="tablist">
		  			<li class="nav-item" data-info="Anagrafica Soggetto Giuridico">
		    			<a class="nav-link active" id="anagrafica-tab2" data-toggle="tab" href="#anagraficaTabContent2" role="tab" aria-controls="anagraficaTabContent2" aria-selected="true">
							Anagrafica Soggetto Giuridico
						</a>
		  			</li>
		  			<li class="nav-item" data-info="Unita Immobiliari Urbane">
		    			<a class="nav-link" id="uiu-tab2" data-toggle="tab" href="#uiuTabContent2" role="tab" aria-controls="uiuTabContent2" aria-selected="false">
							UIU
						</a>
		  			</li>
		  			<li class="nav-item" data-info="Particelle terreni">
		    			<a class="nav-link" id="pt-tab2" data-toggle="tab" href="#ptTabContent2" role="tab" aria-controls="ptTabContent2" aria-selected="false">
							PT
						</a>
		  			</li>
				</ul>
				<div class="tab-content" id="dettaglioTabContent2">
		  			<div class="tab-pane active" id="anagraficaTabContent2" role="tabpanel" aria-labelledby="anagrafica-tab2">
						<form class="form-horizontal">
							<div class="row nom nop">
								<!-- DENOMINAZIONE -->
								<div class="form-group col-md-8">
							    	<label for="denominazioneDettaglio" class="control-label">Denominazione</label>
									<input type="text" hidden id="idSoggetto">
							    	<input type="text" class="form-control" id="denominazioneDettaglio" readonly>
							  	</div>
							  	<!-- C.F. / COMUNE / PROVINCIA -->
		  						<div class="form-group col-md-4">
		    						<label for="codiceFiscaleDettaglio" class="control-label">Codice fiscale</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="codiceFiscaleDettaglio" readonly>
										<div class="input-group-append"><span class="input-group-text"><em class="fa fa-credit-card"></em></span></div>
									</div>
		    					</div>
							</div>
							<div class="row nom nop">
								<div class="form-group col-md-4">
		    						<label for="comuneDettaglio" class="control-label">Comune</label>
		    						<input type="text" class="form-control" id="comuneDettaglio" readonly>
		    					</div>
								<div class="form-group col-md-4">
		    						<label for="provinciaDettaglio" class="control-label">Provincia</label>
		    						<input type="text" class="form-control" id="provinciaDettaglio" readonly>
		    					</div>
		  					</div>
						</form>					
						
					</div>
		  			<div class="tab-pane" id="uiuTabContent2" role="tabpanel" aria-labelledby="uiu-tab2">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaUIUSG" style="width:100%">
								<thead>
							    	<tr>
										<th></th>
							      		<th>UIU</th>
							      		<th>Categoria</th>
							      		<th>Sezione urbana</th>
							      		<th>Foglio</th>
							      		<th>Numero</th>
							      		<th>Subalterno</th>
							      		<th>Indirizzo</th>
							      		<th>Comune</th>
							      		<th>Provincia</th>
							      		<th>Diritto</th>
							      		<th>Partita</th>
							      		<th>Rendita (L)</th>
							      		<th>Rendita (E)</th>
										<th></th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
		  			<div class="tab-pane" id="ptTabContent2" role="tabpanel" aria-labelledby="pt-tab2">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaPTSG" style="width:100%">
								<thead>
							    	<tr>
										<th></th>
							      		<th>Sezione</th>
							      		<th>Foglio</th>
							      		<th>Numero</th>
							      		<th>Subalterno</th>
							      		<th>Comune</th>
							      		<th>Provincia</th>
							      		<th>Diritto</th>
							      		<th>Partita</th>
										<th></th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
				</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" class="pulsante-ricerca" href="#" id="eseguiBtnSoggGiur" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" class="pulsante-ricerca" href="#" id="azzeraBtnSG"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Esporta i dati" class="pulsante-ricerca" href="#" id="esportaBtnSoggGiur"><em class="fa fa-download"></em>&nbsp;&nbsp;Esporta</a></li>
					<li><a class="pulsante-ricerca pulsante-dettaglio" data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Ritorna alla ricerca" class="pulsante-dettaglio" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
					<li><a class="pulsante-ricerca pulsante-dettaglio" data-info="Chiudi la finestra" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</script>
<!-- 
    ______  _____  _______  ____  ______    ____   __  ________  __
   /  _/  |/  /  |/  / __ \/ __ )/  _/ /   /  _/  / / / /  _/ / / /
   / // /|_/ / /|_/ / / / / __  |/ // /    / /   / / / // // / / / 
 _/ // /  / / /  / / /_/ / /_/ // // /____/ /   / /_/ // // /_/ /  
/___/_/  /_/_/  /_/\____/_____/___/_____/___/   \____/___/\____/   
                                                                   
 -->
<script id="ricImmobiliUIU" type="text/x-handlebars-template">
<!-- DROPDOWN MENU -->
<div class="dropdown finestra mainMenu">
</div>
<!-- PAGINA RICERCA IMMOBILI UIU -->
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Ricerca immobili</a></li>
					<li class="active"><a href="#">UIU</a></li>
				</ul>
			</div>
			<div id="ricerca">
			<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading">
					  <h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri3">
							<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
						</a>
					  </h4>
					</div>
					<div id="collapseFiltri3" class="panel-collapse collapse in show">
						<div class="panel-body">
							<div class="row nop nom">
								<div class="col-md-3">
									<label>Comune</label>
									<select class="form-control" id="comune" name="comune">
									<option value="" selected>Seleziona...</option>
								</select> 
								</div>
								<div class="col-md-9">
									<label>Indirizzo</label>
									<div class="input-group">
										<input id="indirizzo" type="text" class="form-control" name="indirizzo" placeholder="Indirizzo...">
										<div class="input-group-append"><span class="input-group-text"><em class="fa fa-map"></em></span></div>
									</div>
								</div>
							</div>
							<div class="col-sm-12 mt-4">
								<div class="row nom bhoechie-tab-container">
									<div class="col-lg-12 bhoechie-tab">
										<!-- Mappale section -->
										<div class="bhoechie-tab-content active"></div>
										<!-- Protocollo section -->
										<div class="bhoechie-tab-content hidden"></div>
										<!-- Variazione section -->
										<div class="bhoechie-tab-content hidden"></div>
										<!-- Scheda section -->
										<div class="bhoechie-tab-content hidden"></div>
										<div class="col-md-5">
										<table>
											<thead></thead>
											<tbody>
												<tr>
													<td>Includi nella ricerca le unit&agrave; immobiliari soppresse</td>
													<td style="width:15px"><input type="checkbox" id="soppresso" name="soppresso"></td>
												</tr>
											</tbody>
										</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- TABELLA RISULTATI -->
			<div class="table-responsive">
				<table class="table table-striped" id="tabellaRisultatiUIU" style="width:100%">
					<thead>
				    	<tr>
				      		<th>Comune</th>
				      		<th>Zona</th>
				      		<th>Cat.</th>
				      		<th>Classe</th>
				      		<th>Indirizzo</th>
				      		<th>Civico</th>
				      		<th>Piano</th>
							<th>Sez. Urb.</th>
				      		<th>Foglio</th>
				      		<th>Numero</th>
				      		<th>Sub.</th>
				      		<th>Consist.</th>
				      		<th>Superf.</th>
							<th>Rendita (lire)</th>
				      		<th>Rendita (euro)</th>
				      		<th>Partita</th>
				      		<th></th>
				    	</tr>
				  	</thead>
				  	<tbody></tbody>
				</table>
			</div>
				<div id="dataAggiornamento">
					 
				</div> 
			</div>
		
			<!-- DETTAGLIO -->
			<div class="col-md-12 hidden tabbable-panel" id="dettaglio">
				<div class="tabbable-line">
				<ul class="nav nav-tabs" id="dettaglioTab3" role="tablist">
					<!--data-info="Anagrafica UIU"-->
		  			<li class="nav-item">
		    			<a class="nav-link active" id="anagrafica-tab3" data-toggle="tab" href="#anagraficaTabContent3" role="tab" aria-controls="anagraficaTabContent3" aria-selected="true">
							Anagrafica UIU
						</a>
		  			</li>
					<!--data-info="Indirizzi"-->
		  			<li class="nav-item">
		    			<a class="nav-link" id="indirizzi-tab3" data-toggle="tab" href="#indirizziTabContent3" role="tab" aria-controls="indirizziTabContent3" aria-selected="false">
							Indirizzi
						</a>
		  			</li>
					<!--data-info="Identificativi UIU"-->
		  			<li class="nav-item">
		    			<a class="nav-link" id="identificativi-tab3" data-toggle="tab" href="#identificativiTabContent3" role="tab" aria-controls="identificativiTabContent3" aria-selected="false">
							Identificativi UIU
						</a>
		  			</li>
					<!-- data-info="Persone fisiche"-->
		  			<li class="nav-item">
		    			<a class="nav-link" id="persone-tab3" data-toggle="tab" href="#personeTabContent3" role="tab" aria-controls="personeTabContent3" aria-selected="true">
							Persone fisiche
						</a>
		  			</li>
					<!--data-info="Soggetti Giuridici"-->
		  			<li class="nav-item">
		    			<a class="nav-link" id="soggetti-tab3" data-toggle="tab" href="#soggettiTabContent3" role="tab" aria-controls="soggettiTabContent3" aria-selected="false">
							Soggetti Giuridici
						</a>
		  			</li>
					<!--data-info="Calcolo Superfici"-->
					<!--data-info="Planimetrie"-->
		  			<li class="nav-item">
		    			<a class="nav-link" id="calcolo-tab3" data-toggle="tab" href="#planimetrieTabContent3" role="tab" aria-controls="planimetrieTabContent3" aria-selected="false">
							Planimetrie
						</a>
		  			</li>
				</ul>
				<div class="tab-content" id="dettaglioTabContent3">
		  			<div class="tab-pane active" id="anagraficaTabContent3" role="tabpanel" aria-labelledby="anagrafica-tab3">
						<!--div class="col-md-12"-->
							<form class="form-horizontal">
								<!-- COMUNE / IDENTIFICATIVO -->
								<div class="d-flex">
									<div class="form-group col">
							    		<label for="comuneDettaglio" class="control-label">Comune</label>
							    		<input type="text" class="form-control" id="comuneDettaglio" readonly>
							    	</div>
									<div class="form-group col">
							    		<label for="identificativoDettaglio" class="control-label">Identificativo</label>
							    		<input type="text" class="form-control" id="identificativoDettaglio" readonly>
										<input type="text" class="hidden" id="idImmobileUIU">
							  		</div>
								</div>
							  	<!-- ZONA / CATEGORIA / CLASSE -->
								<div class="d-flex">
		  							<div class="form-group col-md-2">
		    							<label for="zonaDettaglio" class="control-label">Zona</label>
		    							<input type="text" class="form-control" id="zonaDettaglio" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="categoriaDettaglio" class="control-label">Categoria</label>
		    							<input type="text" class="form-control" id="categoriaDettaglio" readonly>
									</div>
									<div class="form-group col-md-2">
		    							<label for="classeDettaglio" class="control-label">Classe</label>
		    							<input type="text" class="form-control" id="classeDettaglio" readonly>
		    						</div>
		  						
		  							<!-- PARTITA / SUPERFICIE / CONSISTENZA -->
		  							<div class="form-group col-md-2">
		    							<label for="partitaDettaglio" class="control-label">Partita</label>
		    							<input type="text" class="form-control" id="partitaDettaglio" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="superficieDettaglio" class="control-label">Superficie</label>
		    							<input type="text" class="form-control" id="superficieDettaglio" readonly >
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="consistenzaDettaglio" class="control-label">Consistenza</label>
		    							<input type="text" class="form-control" id="consistenzaDettaglio" readonly>
		    						</div>
		  						</div>
		  						<!-- RENDITA EURO / LIRE -->
								<div class="d-flex">
		  							<div class="form-group col-md-3">
		    							<label for="renditaEuroDettaglio" class="control-label">Rendita Euro</label>
										<div class="input-group">
		    								<input type="text" class="form-control" id="renditaEuroDettaglio" readonly>
											<div class="input-group-append">
												<span class="input-group-text"><em class="fa fa-euro"></em></span>
											</div>
										</div>
		    						</div>
									<div class="form-group col-md-3">
		    							<label for="renditaLireDettaglio" class="control-label">Rendita Lire</label>
										<div class="input-group">
		    								<input type="text" class="form-control" id="renditaLireDettaglio" readonly>
											<div class="input-group-append">
												<span class="input-group-text"><em class="fa fa-gbp"></em></span>
											</div>
										</div>
		    						</div>
								</div>
								<div class="d-flex">
		  							<!-- LOTTO / EDIFICIO / SCALA -->
		  							<div class="form-group col-md-2">
		    							<label for="lottoDettaglio" class="control-label">Lotto</label>
		    							<input type="text" class="form-control" id="lottoDettaglio" readonly>
		    						</div>
									<div class="form-group col-md-4">
		    							<label for="edificioDettaglio" class="control-label">Edificio</label>
		    							<input type="text" class="form-control" id="edificioDettaglio" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="scalaDettaglio" class="control-label">Scala</label>
		    							<input type="text" class="form-control" id="scalaDettaglio" readonly>
		    						</div>
		  							<!-- INTERNO / PIANO -->
		  							<div class="form-group col-md-2">
		    							<label for="internoDettaglio" class="control-label">Interno</label>
		    							<input type="text" class="form-control" id="internoDettaglio" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="pianoDettaglio" class="control-label">Piano</label>
		    							<input type="text" class="form-control" id="pianoDettaglio" readonly>
		    						</div>
		  						</div>
		  						<!-- ANNOTAZIONE -->
								<div class="d-flex">
		  							<div class="form-group col">
							    		<label for="annotazioneDettaglio" class="control-label">Annotazione</label>
							    		<div class="input-group">
			      							<textarea class="form-control" id="annotazioneDettaglio" rows="3" readonly></textarea>
			    							<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>   
									    </div>
							  		</div>
								</div>
							</form>					
					</div>
		  			<div class="tab-pane" id="indirizziTabContent3" role="tabpanel" aria-labelledby="indirizzi-tab3">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaIndirizzi" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Indirizzo</th>
							      		<th>Civico (1)</th>
							      		<th>Civico (2)</th>
							      		<th>Civico (3)</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
		  			<div class="tab-pane" id="identificativiTabContent3" role="tabpanel" aria-labelledby="identificativi-tab3">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaIdentificativiUIU" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Sezione Urbana</th>
							      		<th>Foglio</th>
							      		<th>Numero</th>
							      		<th>Subalterno</th>
							      		<th class="text-center"></th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="personeTabContent3" role="tabpanel" aria-labelledby="persone-tab3">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaPersoneFisiche" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="soggettiTabContent3" role="tabpanel" aria-labelledby="soggetti-tab3">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaSoggettiGiuridici" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
						<div class="tab-pane" id="planimetrieTabContent3" role="tabpanel" aria-labelledby="calcolo-tab3">
							<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaPlanimetrie" style="width:100%">
										<thead>
							    			<tr>
							      				<th>Nome</th>
							      				<th>Foglio</th>
							      				<th>Numero</th>
							      				<th>Subalterno</th>
												<th>Dug</th>
							      				<th>Indirizzo</th>
							      				<th>Numero Civico</th>
							      				<th>Categoria Catastale</th>
												<th>Superficie</th>
												<th>Scala da</th>
												<th>Scala a</th>
												<th>Download</th>
							    			</tr>
							  			</thead>
							  			<tbody>
							  			</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" class="pulsante-ricerca" href="#" id="eseguiBtnUIU" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" class="pulsante-ricerca" href="#" id="azzeraBtnUIU"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Esporta il dettaglio" class="pulsante-ricerca" href="#" id="esportaDettaglioBtnUIU"><em class="fa fa-download"></em>&nbsp;&nbsp;Esporta</a></li>
					<li><a data-info="Chiudi la finestra" class="pulsante-ricerca pulsante-dettaglio"  id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul  class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Ritorna alla ricerca" class="pulsante-dettaglio" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
					<li><a data-info="Localizza su mappa" class="pulsante-dettaglio" href="#" id="localizzaBtnUIU"><em class="fa fa-map-marker"></em>&nbsp;&nbsp;Localizza</a></li>
					<li><a data-info="Esegui visura catastale" class="pulsante-dettaglio" href="#" id="effettuaVisuraBtnUIU"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Visura catastale</a></li>
					<li><a data-info="Esegui visura catastale storica" class="pulsante-dettaglio" href="#" id="effettuaVisuraStoricaBtnUIU"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Visura catastale storica</a></li>
					<li><a data-info="Chiudi la finestra" class="pulsante-ricerca pulsante-dettaglio"  id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>

<script id="ricercaUIUTabContent" type="text/x-handlebars-template">
	<div class="row nop nom">
		<div class="col-md-3">
 			<label for="zona" class="control-label">Zona</label>
 			<input type="text" class="form-control" id="zona" placeholder="Zona...">
 		</div>
		<div class="col-md-3">
			<label for="categoria" class="control-label">Categoria</label>
			<select class="form-control" id="categoriaUIU" name="categoriaUIU">
				<option value="" selected>Seleziona...</option>
			</select> 
		</div>
		<div class="col-md-3">
			<label for="classe" class="control-label">Classe</label>
			<input type="text" class="form-control" id="classe" placeholder="Classe...">
		</div>
		<div class="col-md-3" {{#unless mappale}}style="display: none;"{{/unless}}>
 			<label for="sezioneUrbana" class="control-label">Sezione</label>
			<input type="text" class="form-control" id="sezioneUrbana" placeholder="Sezione urbana...">
		</div>
	</div>
	<div class="row nom">
		{{#if mappale}}
		<div class="col-md-3">
			<label for="foglio" class="control-label">Foglio</label>
			<input type="text" class="form-control" id="foglio" placeholder="Foglio...">
		</div>
		{{else}}
		<div class="col-md-3">
			<label for="anno" class="control-label">Anno</label>
			<input type="text" class="form-control" id="anno" placeholder="Anno...">
		</div>
		{{/if}}
		<div class="col-md-3" {{#unless mappale}}style="display: none;"{{/unless}}>
			<label for="numero" class="control-label">Numero</label>
			<input type="text" class="form-control" id="numero" placeholder="Numero...">
		</div>
		{{#if mappale}}
		<div class="col-md-3">
			<label for="subalterno" class="control-label">Subalterno</label>
			<input type="text" class="form-control" id="subalterno" placeholder="Subalterno...">
		</div>
		{{/if}}
		<div class="col-md-3">
			<label for="consistenza" class="control-label">Consistenza</label>
			<input type="text" class="form-control" id="consistenza" placeholder="Consistenza...">
		</div>
	</div>
	<div class="row nom pb-4">
		<div class="col-md-3">
			<label for="superficie" class="control-label">Superficie</label>
			<input type="text" class="form-control" id="superficie" placeholder="Superficie...">
		</div>
		<div class="col-md-3">
			<label for="renditaLire" class="control-label">Rendita</label>
			<div class="input-group">
				<input type="text" class="form-control" id="renditaLire" placeholder="Rendita in lire...">
				<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
			</div>
		</div>
		<div class="col-md-3">
			<label for="renditaEuro" class="control-label">Rendita</label>
			<div class="input-group">
				<input type="text" class="form-control" id="renditaEuro" placeholder="Rendita in euro...">
				<div class="input-group-append"><span class="input-group-text"><i class="fa fa-euro"></i></span></div>
			</div>
		</div>
		<div class="col-md-3">
			<label for="partita" class="control-label">Partita</label>
			<input type="text" class="form-control" id="partita" placeholder="Partita...">
		</div>
		{{#if protocollo}}
		<div class="col-md-3">
			<label for="subalterno" class="control-label">Protocollo</label>
			<input type="text" class="form-control" id="subalterno" placeholder="Protocollo...">
		</div>
		{{/if}}
		{{#if variazione}}
		<div class="col-md-3">
			<label for="variazione" class="control-label">Variazione</label>
			<input type="text" class="form-control" id="variazione" placeholder="Variazione...">
		</div>
		{{/if}}
		{{#if scheda}}
		<div class="col-md-3">
			<label for="scheda" class="control-label">Scheda</label>
			<input type="text" class="form-control" id="scheda" placeholder="Scheda...">
		</div>
		{{/if}}
	</div>
</script>

<!-- 
    ______  _____  _______  ____  ______    ____   ____  ___    ____  ________________________    __    ______
   /  _/  |/  /  |/  / __ \/ __ )/  _/ /   /  _/  / __ \/   |  / __ \/_  __/  _/ ____/ ____/ /   / /   / ____/
   / // /|_/ / /|_/ / / / / __  |/ // /    / /   / /_/ / /| | / /_/ / / /  / // /   / __/ / /   / /   / __/   
 _/ // /  / / /  / / /_/ / /_/ // // /____/ /   / ____/ ___ |/ _, _/ / / _/ // /___/ /___/ /___/ /___/ /___   
/___/_/  /_/_/  /_/\____/_____/___/_____/___/  /_/   /_/  |_/_/ |_| /_/ /___/\____/_____/_____/_____/_____/   
                                                                                                              
 -->
<script id="ricImmobiliParticelle" type="text/x-handlebars-template">
<!-- DROPDOWN MENU -->
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<!-- PAGINA RICERCA IMMOBILI UIU -->
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Ricerca immobili</a></li>
					<li class="active"><a href="#">Particelle</a></li>
				</ul>
			</div>
			<div id="ricerca">
			<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading">
					  <h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri4">
							<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
						</a>
					  </h4>
					</div>
					<div id="collapseFiltri4" class="panel-collapse collapse in show">
						<div class="panel-body">
							<div class="row nom nop">
								<div class="col-md-4">
									<label>Comune</label>
									<select class="form-control" id="comunePT" name="comune">
										<option value="" selected>Seleziona...</option>
									</select> 
								</div>
								<div class="col-md-4">
									<label>Sezione</label>
									<input id="sezionePT" type="text" class="form-control" name="sezione" placeholder="Sezione...">   
								</div>
							</div>
							<div class="row nom">
								<div class="col-sm-4 clearfix">
									<div class="panel panel-default">
  										<div class="panel-heading">Dati catastali</div>
  										<div class="panel-body row nom">
											<div class="col-md-6">
												<label>Foglio</label>
												<input id="foglioPT" type="text" class="form-control" name="foglio" placeholder="Foglio...">   
											</div>
											<div class="col-md-6">
												<label>Numero</label>
												<input id="numeroPT" type="text" class="form-control" name="numero" placeholder="Numero...">   
											</div>
											<div class="col-md-6" hidden>
												<label>Subalterno</label>
												<input id="subalternoPT" type="text" class="form-control" name="subalterno" placeholder="Subalterno...">   
											</div>
											<div class="col-md-6">
												<label>Partita</label>
												<input id="partitaPT" type="text" class="form-control" name="partita" placeholder="Partita...">   
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="panel panel-default">
  										<div class="panel-heading">Reddito</div>
  										<div class="panel-body row nom">
											<div class="col-md-6">
												<label>Dominicale</label>
												<div class="input-group">
													<input id="redditoDominicaleEuroPT" type="text" class="form-control" name="redditoDominicaleEuro" placeholder="in euro...">   
													<div class="input-group-append"><span class="input-group-text"><em class="fa fa-euro"></em></span></div>
												</div>
											</div>
											<div class="col-md-6">
												<label>&nbsp;</label>
												<div class="input-group">
													<input id="redditoDominicaleLirePT" type="text" class="form-control" name="redditoDominicaleLire" placeholder="in lire...">   
													<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
												</div>
											</div>
											<div class="col-md-6">
												<label>Agrario</label>
												<div class="input-group">
													<input id="redditoAgrarioEuroPT" type="text" class="form-control" name="redditoAgrarioEuro" placeholder="in euro...">   
													<div class="input-group-append"><span class="input-group-text"><em class="fa fa-euro"></em></span></div>
												</div>
											</div>
											<div class="col-md-6">
												<label>&nbsp;</label>
												<div class="input-group">
													<input id="redditoAgrarioLirePT" type="text" class="form-control" name="redditoAgrarioLire" placeholder="in lire...">   
													<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-4">
								<div class="panel panel-default">
  									<div class="panel-heading">Superficie</div>
  									<div class="panel-body">
										<div class="col-md-12">
											<label>Superficie</label>
											<div class="input-group">
												<input id="superficiePT" type="text" class="form-control" name="superficiePT" placeholder="in ettari...">   
												<span class="input-group-addon">ha</span>
											</div>
										</div>
									</div>
								</div>
								</div>
							</div>
								<div class="col-md-12">
										<table>
											<thead></thead>
											<tbody>
												<tr>
													<td>Includi nella ricerca le unit&agrave; immobiliari soppresse</td>
													<td style="width:15px"><input type="checkbox" id="soppressoPT" name="soppresso"></td>
												</tr>
											</tbody>
										</table>
								</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- TABELLA RISULTATI -->
			<div class="table-responsive">
				<table class="table table-striped" id="tabellaRisultatiPT" style="width:100%">
					<thead>
				    	<tr>
							<th></th>
				      		<th>Comune</th>
				      		<th>Provincia</th>
				      		<th>Sez.</th>
				      		<th>Foglio</th>
				      		<th>Numero</th>
				      		<th>Partita</th>
							<th>Classe</th>
				      		<th>Qualita</th>
				      		<th data-info="Ettari">ha</th>
				      		<th data-info="Are">are</th>
				      		<th data-info="Centiare">ca</th>
				      		<th>Sup.</th>
							<th>Reddito dominicale (lire)</th>
				      		<th>Reddito dominicale (euro)</th>
							<th>Reddito agrario (lire)</th>
							<th>Reddito agrario (euro)</th>
				      		<th></th>
				    	</tr>
				  	</thead>
				  	<tbody></tbody>
					<tfoot>
		            	<tr>
							<th></th>
				      		<th>Comune</th>
				      		<th>Provincia</th>
				      		<th>Sez.</th>
				      		<th>Foglio</th>
				      		<th>Numero</th>
				      		<th>Partita</th>
							<th>Classe</th>
				      		<th>Qualita</th>
				      		<th data-info="Ettari">ha</th>
				      		<th data-info="Are">are</th>
				      		<th data-info="Centiare">ca</th>
				      		<th>Sup.</th>
							<th>Reddito dominicale (lire)</th>
							<th>Reddito agrario (lire)</th>
				      		<th>Reddito dominicale (euro)</th>
							<th>Reddito agrario (euro)</th>
				      		<th></th>
				    	</tr>
		        	</tfoot>
				</table>
			</div>
				<div id="dataAggiornamento">
					 
				</div> 
			</div>
		
			<!-- DETTAGLIO -->
			<div class="col-md-12 hidden tabbable-panel" id="dettaglio">
				<div class="tabbable-line">
				<ul class="nav nav-tabs" id="dettaglioTab4" role="tablist">
		  			<li class="nav-item">
		    			<a class="nav-link active" id="anagrafica-tab4" data-toggle="tab" href="#anagraficaTabContent4" role="tab" aria-controls="anagraficaTabContent4" aria-selected="true">
							Anagrafica
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="porzione-tab4" data-toggle="tab" href="#porzioneTabContent4" role="tab" aria-controls="porzioneTabContent4" aria-selected="false">
							Porzione
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="deduzione-tab4" data-toggle="tab" href="#deduzioneTabContent4" role="tab" aria-controls="deduzioneTabContent4" aria-selected="false">
							Deduzione
						</a>
		  			</li>
					<li class="nav-item">
		    			<a class="nav-link" id="riserva-tab4" data-toggle="tab" href="#riservaTabContent4" role="tab" aria-controls="riservaTabContent4" aria-selected="false">
							Riserva
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="persone-tab4" data-toggle="tab" href="#personeTabContent4" role="tab" aria-controls="personeTabContent4" aria-selected="true">
							Persone fisiche
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="soggetti-tab4" data-toggle="tab" href="#soggettiTabContent4" role="tab" aria-controls="soggettiTabContent4" aria-selected="false">
							Soggetti Giuridici
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="identificativi-tab4" data-toggle="tab" href="#identificativiTabContent4" role="tab" aria-controls="identificativiTabContent4" aria-selected="false">
							Identificativi UIU
						</a>
		  			</li>
				</ul>
				<div class="tab-content" id="dettaglioTabContent">
		  			<div class="tab-pane active" id="anagraficaTabContent4" role="tabpanel" aria-labelledby="anagrafica-tab4">
						<form class="form-horizontal">
							<div class="row nom nop">
								<!-- COMUNE -->
								<div class="form-group col-md-6">
							    	<label for="comuneDettaglio" class="control-label">Comune</label>
							    	<input type="text" class="form-control" id="comuneDettaglioPT" readonly>
									<input type="text" class="hidden" id="idImmobilePT">
							    </div>
							  	<!-- FOGLIO / NUMERO / DENOMINATORE -->
		  						<div class="form-group col-md-2">
		    						<label for="zonaDettaglio" class="control-label">Foglio</label>
		    						<input type="text" class="form-control" id="foglioDettaglioPT" readonly>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="categoriaDettaglio" class="control-label">Numero</label>
		    						<input type="text" class="form-control" id="numeroDettaglioPT" readonly>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="classeDettaglio" class="control-label">Denominatore</label>
		    						<input type="text" class="form-control" id="denominatoreDettaglioPT" readonly>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- SUBALTERNO / PARTITA -->
		  						<div class="form-group col-md-3">
		    						<label for="partitaDettaglio" class="control-label">Subalterno</label>
		    						<input type="text" class="form-control" id="subalternoDettaglioPT" readonly>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="superficieDettaglio" class="control-label">Partita</label>
		    						<input type="text" class="form-control" id="partitaDettaglioPT" readonly >
		    					</div>
		  						<!-- QUALITA / CLASSE -->
		  						<div class="form-group col-md-3">
		    						<label for="renditaEuroDettaglio" class="control-label">Qualit&agrave;</label>
		    						<input type="text" class="form-control" id="qualitaDettaglioPT" readonly >
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="renditaLireDettaglio" class="control-label">Classe</label>
		    						<input type="text" class="form-control" id="classeDettaglioPT" readonly >
		  						</div>
							</div>
							<div class="row nom nop">
								<!-- ETTARI / ARE / CENTIARE -->
		  						<div class="form-group col-md-2">
		    						<label for="lottoDettaglio" class="control-label">Ettari</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="ettariDettaglioPT" readonly >
		      							<div class="input-group-append"><span class="input-group-text">ha</span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="edificioDettaglio" class="control-label">Are</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="areDettaglioPT" readonly >
		      							<div class="input-group-append"><span class="input-group-text">are</span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="scalaDettaglio" class="control-label">Centiare</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="centiareDettaglioPT" readonly >
		      							<div class="input-group-append"><span class="input-group-text">ca</span></div>
		      						</div>
		    					</div>
		  						<!-- REDDITO AGRARIO -->
		  						<div class="form-group col-md-3">
		    						<label for="internoDettaglio" class="control-label">Agrario euro</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="agrarioEuroDettaglioPT" readonly >
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-euro"></em></span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="pianoDettaglio" class="control-label">Agrario lire</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="agrarioLireDettaglioPT" readonly >
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
		      						</div>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- REDDITO DOMINICALE -->
		  						<div class="form-group col-md-3">
		    						<label for="internoDettaglio" class="control-label">Dominicale euro</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="dominicaleEuroDettaglioPT" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-euro"></em></span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="pianoDettaglio" class="control-label">Dominicale lire</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="dominicaleLireDettaglioPT" readonly >
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
		      						</div>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- ANNOTAZIONE -->
		  						<div class="form-group col-md-12">
							    	<label for="annotazioneDettaglio" class="control-label">Annotazione</label>
							    	<div class="input-group">
							    		<textarea class="form-control" id="annotazioneDettaglioPT" rows="3" readonly>Nessuna nota</textarea>
										<div class="input-group-append"><span class="input-group-text"><em class="fa fa-clipboard"></em></span></div>
									</div>
							    </div>
							  </div>
						</form>		
					</div>
		  			<div class="tab-pane" id="porzioneTabContent4" role="tabpanel" aria-labelledby="porzione-tab4">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaPorzionePT" style="width:100%">
									<thead>
										<tr>
											<th>Classe</th>
											<th>Ettari</th>
											<th>Are</th>
											<th>Centiare</th>
											<th>Qualita</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
		  			<div class="tab-pane" id="deduzioneTabContent4" role="tabpanel" aria-labelledby="deduzione-tab4">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaDeduzionePT" style="width:100%">
									<thead>
										<tr>
											<th>Simbolo Deduzione</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
					<div class="tab-pane" id="riservaTabContent4" role="tabpanel" aria-labelledby="riserva-tab4">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaRiservaPT" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Riserva</th>
							      		<th>Partita Iscrizione</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="personeTabContent4" role="tabpanel" aria-labelledby="persone-tab4">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaPersoneFisichePT" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="soggettiTabContent4" role="tabpanel" aria-labelledby="soggetti-tab4">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaSoggettiGiuridiciPT" style="width:100%">
									<thead>
										<tr>
											<th>Nominativo</th>
											<th>Diritto</th>
											<th>Data Efficacia</th>
											<th>Data registrazione</th>
											<th>Tipo Atto</th>
											<th>Numero Atto</th>
											<th>Progressivo Atto</th>
											<th>Anno Atto</th>
											<th>Descrizione Atto</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
					<div class="tab-pane" id="identificativiTabContent4" role="tabpanel" aria-labelledby="identificativi-tab4">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaIdentificativiUIUPT" style="width:100%">
									<thead>
										<tr>
											<th>Sezione urbana</th>
											<th>Foglio</th>
											<th>Numero</th>
											<th>Subalterno</th>
											<th>Partita</th>
											<th class="text-center"></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>		
						</div>		
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" class="pulsante-ricerca" href="#" id="eseguiBtnPT" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" class="pulsante-ricerca" href="#" id="azzeraBtnPT"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Esporta il dettaglio" class="pulsante-ricerca" href="#" id="esportaDettaglioBtnPT"><em class="fa fa-download"></em>&nbsp;&nbsp;Esporta</a></li>
					<li><a data-info="Chiudi la finestra" class="pulsante-ricerca pulsante-dettaglio" id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Ritorna alla ricerca" class="pulsante-dettaglio" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
					<li><a data-info="Localizza su mappa" class="pulsante-dettaglio" href="#" id="localizzaBtnPT"><em class="fa fa-map-marker"></em>&nbsp;&nbsp;Localizza</a></li>
					<li><a data-info="Esegui visura catastale" class="pulsante-dettaglio" href="#" id="effettuaVisuraBtnPT"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Visura catastale</a></li>
					<li><a data-info="Esegui visura catastale storica" class="pulsante-dettaglio" href="#" id="effettuaVisuraStoricaBtnPT"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Visura catastale storica</a></li>
				</ul>
			</div>
		</div>
	</div> 
</div>
</script> 
<!-- 
    _____   ____________________________ _____   ________  _   ______   ____  __________  _____ ____  _   ________   _______________ ____________  ________
   /  _/ | / /_  __/ ____/ ___/_  __/   /__  /  /  _/ __ \/ | / /  _/  / __ \/ ____/ __ \/ ___// __ \/ | / / ____/  / ____/  _/ ___//  _/ ____/ / / / ____/
   / //  |/ / / / / __/  \__ \ / / / /| | / /   / // / / /  |/ // /   / /_/ / __/ / /_/ /\__ \/ / / /  |/ / __/    / /_   / / \__ \ / // /   / /_/ / __/   
 _/ // /|  / / / / /___ ___/ // / / ___ |/ /___/ // /_/ / /|  // /   / ____/ /___/ _, _/___/ / /_/ / /|  / /___   / __/ _/ / ___/ // // /___/ __  / /___   
/___/_/ |_/ /_/ /_____//____//_/ /_/  |_/____/___/\____/_/ |_/___/  /_/   /_____/_/ |_|/____/\____/_/ |_/_____/  /_/   /___//____/___/\____/_/ /_/_____/   
                                                                                                                                                           
 -->
<script id="ricIntestazioniPersoneFisiche" type="text/x-handlebars-template">
<!-- DROPDOWN MENU -->
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<!-- PAGINA RICERCA INTESTAZIONI PERSONE FISICHE -->
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Ricerca intestazioni</a></li>
					<li class="active"><a href="#">Persone fisiche</a></li>
				</ul>
			</div>
			<div id="ricerca">
			<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
					<div class="panel-heading">
					  <h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri5">
							<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
						</a>
					  </h4>
					</div>
					<div id="collapseFiltri5" class="panel-collapse collapse in show">
						<div class="panel-body row nom">
							<div class="col-md-4">
								<label>Nome</label>
								<div class="input-group">
									<input id="nomeIntestazioniPF" type="text" class="form-control" name="nome" placeholder="Nome...">   
									<div class="input-group-append"><span class="input-group-text"><i class="fa fa-user"></i></span></div>
		    					</div>
							</div>
							<div class="col-md-4">
								<label>Cognome</label>
								<div class="input-group">
									<input id="cognomeIntestazioniPF" type="text" class="form-control" name="cognome" placeholder="Cognome...">   
									<div class="input-group-append"><span class="input-group-text"><i class="fa fa-user"></i></span></div>
		    					</div>
							</div>
							<div class="col-md-4">
								<label>Codice fiscale</label>
								<div class="input-group">
									<input id="codiceFiscaleIntestazioniPF" type="text" class="form-control" name="codiceFiscale" placeholder="Codice fiscale...">   
									<div class="input-group-append"><span class="input-group-text"><i class="fa fa-credit-card"></i></span></div>
		    					</div>
							</div>
							<div class="col-sm-12 mt-4">
								<h3 class="decorated"><span>Tipo estrazione</span></h3>
							</div>
							<div class="col-md-6">
								<table>
									<thead></thead>
									<tbody>
										<tr>
											<td>UIU (Catasto urbano)</td>
											<td style="width:15px"><input type="checkbox" id="checkboxUiuPf" name="checkboxUiuPf"></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="col-md-6">
								<table>
									<thead></thead>
									<tbody>
										<tr>
											<td>PT (Catasto terreno)</td>
											<td style="width:15px"><input type="checkbox" id="checkboxPtPf" name="checkboxPtPf"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- TABELLA RISULTATI -->
			<div class="table-responsive">
				<table class="table table-striped" id="tabellaRisultatiIntestazioniPF" style="width:100%">
					<thead>
				    	<tr>
				      		<th>Cognome</th>
				      		<th>Nome</th>
				      		<th>Codice fiscale</th>
				      		<th>ID Soggetto</th>
				      		<th>Tipo</th>
				      		<th>Sezione</th>
				      		<th>Foglio</th>
				      		<th>Numero</th>
							<th>Subalterno</th>
							<th>Codice Comune</th>
							<th>Comune</th>
							<th>Provincia</th>
							<th></th>
				    	</tr>
				  	</thead>
				  	<tbody></tbody>
					<tfoot>
		            	<tr>
				      		<th>Cognome</th>
				      		<th>Nome</th>
				      		<th>Codice fiscale</th>
				      		<th>ID Soggetto</th>
				      		<th>Tipo</th>
				      		<th>Sezione</th>
				      		<th>Foglio</th>
				      		<th>Numero</th>
							<th>Subalterno</th>
							<th>Codice Comune</th>
							<th>Comune</th>
							<th>Provincia</th>
							<th></th>
				    	</tr>
		        	</tfoot>
				</table>
			</div>
				<div id="dataAggiornamento">
					 
				</div> 
			</div>
		
			<!-- DETTAGLIO -->
			<div class="col-md-12 hidden tabbable-panel" id="dettaglio">
				<div class="tabbable-line">
				<ul class="nav nav-tabs" id="dettaglioTabPt" role="tablist">
		  			<li class="nav-item">
		    			<a class="nav-link" id="anagrafica-tab5-pt" data-toggle="tab" href="#anagraficaTabContent5-pt" role="tab" aria-controls="anagraficaTabContent5-pt" aria-selected="true">
							Anagrafica
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="porzione-tab5-pt" data-toggle="tab" href="#porzioneTabContent5-pt" role="tab" aria-controls="porzioneTabContent5-pt" aria-selected="false">
							Porzione
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="deduzione-tab5-pt" data-toggle="tab" href="#deduzioneTabContent5-pt" role="tab" aria-controls="deduzioneTabContent5-pt" aria-selected="false">
							Deduzione
						</a>
		  			</li>
					<li class="nav-item">
		    			<a class="nav-link" id="riserva-tab5-pt" data-toggle="tab" href="#riservaTabContent5-pt" role="tab" aria-controls="riservaTabContent5-pt" aria-selected="false">
							Riserva
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="persone-tab5-pt" data-toggle="tab" href="#personeTabContent5-pt" role="tab" aria-controls="personeTabContent5-pt" aria-selected="false">
							Persone fisiche
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="soggetti-tab5-pt" data-toggle="tab" href="#soggettiTabContent5-pt" role="tab" aria-controls="soggettiTabContent5-pt" aria-selected="false">
							Soggetti Giuridici
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="calcolo-tab5-pt" data-toggle="tab" href="#identificativiTabContent5-pt" role="tab" aria-controls="identificativiTabContent5-pt" aria-selected="false">
							Identificativi UIU
						</a>
		  			</li>
				</ul>
				<ul class="nav nav-tabs" id="dettaglioTabUiu" role="tablist">
		  			<li class="nav-item">
		    			<a class="nav-link" id="anagrafica-tab5-uiu" data-toggle="tab" href="#anagraficaTabContent3-uiu" role="tab" aria-controls="anagraficaTabContent3-uiu" aria-selected="true">
							Anagrafica UIU
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="indirizzi-tab5-uiu" data-toggle="tab" href="#indirizziTabContent3-uiu" role="tab" aria-controls="indirizziTabContent3-uiu" aria-selected="false">
							Indirizzi
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="identificativi-tab5-uiu" data-toggle="tab" href="#identificativiTabContent3-uiu" role="tab" aria-controls="identificativiTabContent3-uiu" aria-selected="false">
							Identificativi UIU
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="persone-tab5-uiu" data-toggle="tab" href="#personeTabContent3-uiu" role="tab" aria-controls="personeTabContent3-uiu" aria-selected="false">
							Persone fisiche
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="soggetti-tab5-uiu" data-toggle="tab" href="#soggettiTabContent3-uiu" role="tab" aria-controls="soggettiTabContent3-uiu" aria-selected="false">
							Soggetti Giuridici
						</a>
		  			</li>
				</ul>
				<div class="tab-content" id="dettaglioTabContent">
		  			<div class="tab-pane active" id="anagraficaTabContent5-pt" role="tabpanel" aria-labelledby="anagrafica-tab5-pt">
						<form class="form-horizontal">
							<div class="row nom nop">
								<!-- COMUNE -->
								<div class="form-group col-md-6">
							    	<label for="comuneDettaglioPtIntestazioniPf" class="control-label">Comune</label>
							    	<input type="text" class="form-control" id="comuneDettaglioPtIntestazioniPf" readonly>
									<input type="text" class="hidden" id="idImmobilePtPF">
							    </div>
								<!-- FOGLIO / NUMERO / DENOMINATORE -->
		  						<div class="form-group col-md-2">
		    						<label for="foglioDettaglioPtIntestazioniPf" class="control-label">Foglio</label>
		    						<input type="text" class="form-control" id="foglioDettaglioPtIntestazioniPf" readonly>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="numeroDettaglioPtIntestazioniPf" class="control-label">Numero</label>
		    						<input type="text" class="form-control" id="numeroDettaglioPtIntestazioniPf" readonly>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="denominatoreDettaglioPtIntestazioniPf" class="control-label">Denominatore</label>
		    						<input type="text" class="form-control" id="denominatoreDettaglioPtIntestazioniPf" readonly>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- SUBALTERNO / PARTITA -->
		  						<div class="form-group col-md-3">
		    						<label for="subalternoDettaglioPtIntestazioniPf" class="control-label">Subalterno</label>
		    						<input type="text" class="form-control" id="subalternoDettaglioPtIntestazioniPf" readonly>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="partitaDettaglioPtIntestazioniPf" class="control-label">Partita</label>
		    						<input type="text" class="form-control" id="partitaDettaglioPtIntestazioniPf" readonly>
		    					</div>
		  						<!-- QUALITA / CLASSE -->
		  						<div class="form-group col-md-3">
		    						<label for="qualitaDettaglioPtIntestazioniPf" class="control-label">Qualit&agrave;</label>
		    						<input type="text" class="form-control" id="qualitaDettaglioPtIntestazioniPf" readonly>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="classeDettaglioPtIntestazioniPf" class="control-label">Classe</label>
		    						<input type="text" class="form-control" id="classeDettaglioPtIntestazioniPf" readonly>
		    					</div>
		  					</div>
							<div class="row nom nop">
								<!-- ETTARI / ARE / CENTIARE -->
		  						<div class="form-group col-md-2">
		    						<label for="ettariDettaglioPtIntestazioniPf" class="control-label">Ettari</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="ettariDettaglioPtIntestazioniPf" readonly>
		      							<div class="input-group-append"><span class="input-group-text">ha</span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="areDettaglioPtIntestazioniPf" class="control-label">Are</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="areDettaglioPtIntestazioniPf" readonly>
		      							<div class="input-group-append"><span class="input-group-text">are</span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="centiareDettaglioPtIntestazioniPf" class="control-label">Centiare</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="centiareDettaglioPtIntestazioniPf" readonly>
		      							<div class="input-group-append"><span class="input-group-text">ca</span></div>
		      						</div>
		    					</div>
		  						<!-- REDDITO AGRARIO -->
		  						<div class="form-group col-md-3">
		    						<label for="agrarioEuroDettaglioPtIntestazioniPf" class="control-label">Agrario euro</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="agrarioEuroDettaglioPtIntestazioniPf" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-euro"></em></span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="agrarioLireDettaglioPtIntestazioniPf" class="control-label">Agrario lire</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="agrarioLireDettaglioPtIntestazioniPf" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
		      						</div>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- REDDITO DOMINICALE -->
		  						<div class="form-group col-md-3">
		    						<label for="dominicaleEuroDettaglioPtIntestazioniPf" class="control-label">Dominicale euro</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="dominicaleEuroDettaglioPtIntestazioniPf" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-euro"></em></span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="dominicaleLireDettaglioPtIntestazioniPf" class="control-label">Dominicale lire</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="dominicaleLireDettaglioPtIntestazioniPf" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
		      						</div>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- ANNOTAZIONE -->
		  						<div class="form-group col-md-12">
							    	<label for="annotazioneDettaglioPtIntestazioniPf" class="control-label">Annotazione</label>
							    	<div class="input-group">
							    		<textarea class="form-control" id="annotazioneDettaglioPtIntestazioniPf" rows="3" readonly></textarea>
										<div class="input-group-append"><span class="input-group-text"><em class="fa fa-clipboard"></em></span></div>
									</div>
							    </div>
							  </div>
						</form>
					</div>
		  			<div class="tab-pane" id="porzioneTabContent5-pt" role="tabpanel" aria-labelledby="porzione-tab5-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaPorzionePtIntestazioniPf" style="width:100%">
									<thead>
										<tr>
											<th>Classe</th>
											<th>Ettari</th>
											<th>Are</th>
											<th>Centiare</th>
											<th>Qualita</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
		  			<div class="tab-pane" id="deduzioneTabContent5-pt" role="tabpanel" aria-labelledby="deduzione-tab5-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaDeduzionePtIntestazioniPf" style="width:100%">
									<thead>
										<tr>
											<th>Simbolo Deduzione</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
					<div class="tab-pane" id="riservaTabContent5-pt" role="tabpanel" aria-labelledby="riserva-tab5-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaRiservaPtIntestazioniPf" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Riserva</th>
							      		<th>Partita Iscrizione</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="personeTabContent5-pt" role="tabpanel" aria-labelledby="persone-tab5-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaPersoneFisichePtIntestazioniPf" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="soggettiTabContent5-pt" role="tabpanel" aria-labelledby="soggetti-tab5-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaSoggettiGiuridiciPtIntestazioniPf" style="width:100%">
									<thead>
										<tr>
											<th>Nominativo</th>
											<th>Diritto</th>
											<th>Data Efficacia</th>
											<th>Data registrazione</th>
											<th>Tipo Atto</th>
											<th>Numero Atto</th>
											<th>Progressivo Atto</th>
											<th>Anno Atto</th>
											<th>Descrizione Atto</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
					<div class="tab-pane" id="identificativiTabContent5-pt" role="tabpanel" aria-labelledby="identificativi-tab5-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaIdentificativiUIUPtIntestazioniPf" style="width:100%">
									<thead>
										<tr>
											<th>Sezione urbana</th>
											<th>Foglio</th>
											<th>Numero</th>
											<th>Subalterno</th>
											<th>Partita</th>
											<th class="text-center"></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
					<div class="tab-pane active" id="anagraficaTabContent3-uiu" role="tabpanel" aria-labelledby="anagrafica-tab5-uiu">
							<form class="form-horizontal">
								<!-- COMUNE / IDENTIFICATIVO -->
								<div class="d-flex">
									<div class="form-group col">
							    		<label for="comuneDettaglioUiuIntestazioniPf" class="control-label">Comune</label>
							    		<input type="text" class="form-control" id="comuneDettaglioUiuIntestazioniPf" readonly>
										<input type="text" class="hidden" id="idImmobileUiuPF">
							    	</div>
									<div class="form-group col">
							    		<label for="identificativoDettaglioUiuIntestazioniPf" class="control-label">Identificativo</label>
							    		<input type="text" class="form-control" id="identificativoDettaglioUiuIntestazioniPf" readonly>
							  		</div>
								</div>
							  	<!-- ZONA / CATEGORIA / CLASSE -->
								<div class="d-flex">
		  							<div class="form-group col-md-2">
		    							<label for="zonaDettaglioUiuIntestazioniPf" class="control-label">Zona</label>
		    							<input type="text" class="form-control" id="zonaDettaglioUiuIntestazioniPf" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="categoriaDettaglioUiuIntestazioniPf" class="control-label">Categoria</label>
		    							<input type="text" class="form-control" id="categoriaDettaglioUiuIntestazioniPf" readonly>
									</div>
									<div class="form-group col-md-2">
		    							<label for="classeDettaglioUiuIntestazioniPf" class="control-label">Classe</label>
		    							<input type="text" class="form-control" id="classeDettaglioUiuIntestazioniPf" readonly>
		    						</div>
		  						
		  							<!-- PARTITA / SUPERFICIE / CONSISTENZA -->
		  							<div class="form-group col-md-2">
		    							<label for="partitaDettaglioUiuIntestazioniPf" class="control-label">Partita</label>
		    							<input type="text" class="form-control" id="partitaDettaglioUiuIntestazioniPf" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="superficieDettaglioUiuIntestazioniPf" class="control-label">Superficie</label>
		    							<input type="text" class="form-control" id="superficieDettaglioUiuIntestazioniPf" readonly >
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="consistenzaDettaglioUiuIntestazioniPf" class="control-label">Consistenza</label>
		    							<input type="text" class="form-control" id="consistenzaDettaglioUiuIntestazioniPf" readonly>
		    						</div>
		  						</div>
		  						<!-- RENDITA EURO / LIRE -->
								<div class="d-flex">
		  							<div class="form-group col-md-3">
		    							<label for="renditaEuroDettaglioUiuIntestazioniPf" class="control-label">Rendita Euro</label>
										<div class="input-group">
		    								<input type="text" class="form-control" id="renditaEuroDettaglioUiuIntestazioniPf" readonly>
											<div class="input-group-append">
												<span class="input-group-text"><em class="fa fa-euro"></em></span>
											</div>
										</div>
		    						</div>
									<div class="form-group col-md-3">
		    							<label for="renditaLireDettaglioUiuIntestazioniPf" class="control-label">Rendita Lire</label>
										<div class="input-group">
		    								<input type="text" class="form-control" id="renditaLireDettaglioUiuIntestazioniPf" readonly>
											<div class="input-group-append">
												<span class="input-group-text"><em class="fa fa-gbp"></em></span>
											</div>
										</div>
		    						</div>
								</div>
								<div class="d-flex">
		  							<!-- LOTTO / EDIFICIO / SCALA -->
		  							<div class="form-group col-md-2">
		    							<label for="lottoDettaglioUiuIntestazioniPf" class="control-label">Lotto</label>
		    							<input type="text" class="form-control" id="lottoDettaglioUiuIntestazioniPf" readonly>
		    						</div>
									<div class="form-group col-md-4">
		    							<label for="edificioDettaglioUiuIntestazioniPf" class="control-label">Edificio</label>
		    							<input type="text" class="form-control" id="edificioDettaglioUiuIntestazioniPf" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="scalaDettaglioUiuIntestazioniPf" class="control-label">Scala</label>
		    							<input type="text" class="form-control" id="scalaDettaglioUiuIntestazioniPf" readonly>
		    						</div>
		  							<!-- INTERNO / PIANO -->
		  							<div class="form-group col-md-2">
		    							<label for="internoDettaglioUiuIntestazioniPf" class="control-label">Interno</label>
		    							<input type="text" class="form-control" id="internoDettaglioUiuIntestazioniPf" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="pianoDettaglioUiuIntestazioniPf" class="control-label">Piano</label>
		    							<input type="text" class="form-control" id="pianoDettaglioUiuIntestazioniPf" readonly>
		    						</div>
		  						</div>
		  						<!-- ANNOTAZIONE -->
								<div class="d-flex">
		  							<div class="form-group col">
							    		<label for="annotazioneDettaglioUiuIntestazioniPf" class="control-label">Annotazione</label>
							    		<div class="input-group">
			      							<textarea class="form-control" id="annotazioneDettaglioUiuIntestazioniPf" rows="3" readonly></textarea>
			    							<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>   
									    </div>
							  		</div>
								</div>
							</form>					
					</div>
		  			<div class="tab-pane" id="indirizziTabContent3-uiu" role="tabpanel" aria-labelledby="indirizzi-tab5-uiu">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaIndirizziUiuIntestazioniPf" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Indirizzo</th>
							      		<th>Civico (1)</th>
							      		<th>Civico (2)</th>
							      		<th>Civico (3)</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
		  			<div class="tab-pane" id="identificativiTabContent3-uiu" role="tabpanel" aria-labelledby="identificativi-tab5-uiu">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaIdentificativiUiuIntestazioniPf" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Sezione Urbana</th>
							      		<th>Foglio</th>
							      		<th>Numero</th>
							      		<th>Subalterno</th>
							      		<th class="text-center"></th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="personeTabContent3-uiu" role="tabpanel" aria-labelledby="persone-tab5-uiu">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaPersoneFisicheUiuIntestazioniPf" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="soggettiTabContent3-uiu" role="tabpanel" aria-labelledby="soggetti-tab5-uiu">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaSoggettiGiuridiciUiuIntestazioniPf" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="calcoloTabContent3-uiu" role="tabpanel" aria-labelledby="calcolo-tab5-uiu">
							<form class="form-horizontal">
								<div class="row nom">
									<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Superficie catastale</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							  		</div>
									<div class="form-group col">
										<div class="d-flex">
											<div class="col">
							    				<label for="superficieCatastaleDettaglio" class="control-label">Locali principali</label>
												<div class="input-group">
							    					<input type="text" class="form-control" id="superficieCatastaleDettaglio">
													<div class="input-group-append"><span class="input-group-text">mq</span></div>
												</div>
							    			</div>
											<div class="col">
												<label for="superficieCatastaleDettaglio" class="control-label">Balconi al piano</label>
							    				<div class="input-group">
							    					<input type="text" class="form-control" id="superficieCatastaleDettaglio">
													<div class="input-group-append"><span class="input-group-text">mq</span></div>
												</div>
							    			</div>
										</div>
							  		</div>
								</div>
								<div class="row nom">
									<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Accessi diretti</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
									<div class="form-group col">
										<label for="superficieCatastaleDettaglio" class="control-label">Balconi non a livello</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
							  		<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Accessi indiretti collegati</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
									<div class="form-group col">
										<label for="superficieCatastaleDettaglio" class="control-label">Aree scoperte esclusive</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
							  	</div>
								<div class="row nom">
									<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Accessi indiretti non collegati</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
									<div class="form-group col">
										<label for="superficieCatastaleDettaglio" class="control-label">Ambienti non classificabili</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
							  		<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Superficie totale</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
									<div class="form-group col">
										<label for="superficieCatastaleDettaglio" class="control-label">Superficie calcolata</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
							  	</div>
								<div class="col-md-12 pt-5 pb-3 text-center">
    								<button type="button" data-info="Lista planimetrie" class="btn-trasp bttn align-center">Lista planimetrie</button>
								</div>
							</form>
						</div>				

					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" class="pulsante-ricerca" href="#" id="eseguiBtnIntestazioniPF" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" class="pulsante-ricerca" href="#" id="azzeraBtnIntestazioniPF"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Esporta il dettaglio" class="pulsante-ricerca" href="#" id="esportaDettaglioBtnIntestazioniPF"><em class="fa fa-download"></em>&nbsp;&nbsp;Esporta</a></li>
					<li><a data-info="Chiudi la finestra" class="pulsante-ricerca pulsante-dettaglio"  id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Ritorna alla ricerca" class="pulsante-dettaglio" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
					<li><a data-info="Esegui visura catastale" class="pulsante-dettaglio" href="#" id="effettuaVisuraBtntestazioniPF"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Visura catastale</a></li>
					<li><a data-info="Esegui visura catastale storica" class="pulsante-dettaglio" href="#" id="effettuaVisuraStoricaBtnIntestazioniPF"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Visura catastale storica</a></li>
					<li><a data-info="Chiudi la finestra" class="pulsante-ricerca pulsante-dettaglio"  id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div> 
</div>
</script>
<!-- 
    _____   ____________________________ _____   ________  _   ______   _____ ____  __________________________________   ____________  ______  ________  ______________
   /  _/ | / /_  __/ ____/ ___/_  __/   /__  /  /  _/ __ \/ | / /  _/  / ___// __ \/ ____/ ____/ ____/_  __/_  __/  _/  / ____/  _/ / / / __ \/  _/ __ \/  _/ ____/  _/
   / //  |/ / / / / __/  \__ \ / / / /| | / /   / // / / /  |/ // /    \__ \/ / / / / __/ / __/ __/   / /   / /  / /   / / __ / // / / / /_/ // // / / // // /    / /  
 _/ // /|  / / / / /___ ___/ // / / ___ |/ /___/ // /_/ / /|  // /    ___/ / /_/ / /_/ / /_/ / /___  / /   / / _/ /   / /_/ // // /_/ / _, _// // /_/ // // /____/ /   
/___/_/ |_/ /_/ /_____//____//_/ /_/  |_/____/___/\____/_/ |_/___/   /____/\____/\____/\____/_____/ /_/   /_/ /___/   \____/___/\____/_/ |_/___/_____/___/\____/___/   
                                                                                                                                                                       
 -->
<script id="ricIntestazioniSoggettiGiuridici" type="text/x-handlebars-template">
<!-- DROPDOWN MENU -->
<div class="dropdown finestra mainMenu">
   	<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   		<span class="icon-bar"></span>
   	</a>
</div>
<!-- PAGINA RICERCA INTESTAZIONI PERSONE FISICHE -->
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Ricerca intestazioni</a></li>
					<li class="active"><a href="#">Persone fisiche</a></li>
				</ul>
			</div>
			<div id="ricerca">
				<div class="panel-group search" id="accordionFiltriRicerca" role="tablist" aria-multiselectable="true">
					<div class="panel panel-default">
						<div class="panel-heading">
					 		<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordionFiltriRicerca" href="#collapseFiltri6">
									<i class="fa fa-filter"></i>&nbsp;&nbsp;Filtra per
								</a>
					  		</h4>
						</div>
						<div id="collapseFiltri6" class="panel-collapse collapse in show">
							<div class="panel-body row nom nop">
								<div class="col-md-3">
									<label>Denominazione</label>
									<input id="denominazioneSG" type="text" class="form-control" name="nome" placeholder="Denominazione...">   
								</div>
								<div class="col-md-3">
			        				<label>Provincia di nascita</label>
			        				<select class="form-control" id="provinciaNascitaISG" name="provinciaNascitaISG">
										<option value="" selected>Seleziona...</option>
									</select>
			        			</div>
					        	<div class="col-md-3">
						        	<label>Comune di nascita</label>
					        		<select class="form-control" id="comuneNascitaISG" name="comuneNascitaISG">
										<option value="" selected>Seleziona...</option>
									</select>
					        	</div>
								<div class="col-md-3">
									<label>Codice fiscale</label>
									<div class="input-group">
										<input id="codiceFiscaleSG" type="text" class="form-control" name="codiceFiscale" placeholder="Codice fiscale...">   
										<div class="input-group-append"><span class="input-group-text"><i class="fa fa-credit-card"></i></span></div>
		    						</div>
								</div>
								<div class="col-sm-12 mt-4">
									<h3 class="decorated"><span>Tipo estrazione</span></h3>
								</div>
								<div class="col-md-6">
									<table>
										<thead></thead>
										<tbody>
											<tr>
												<td>UIU (Catasto urbano)</td>
													<td style="width:15px"><input type="checkbox" id="checkboxUiuSg" name="checkboxUiuSg"></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="col-md-6">
									<table>
										<thead></thead>
										<tbody>
											<tr>
												<td>PT (Catasto terreno)</td>
												<td style="width:15px"><input type="checkbox" id="checkboxPtSg" name="checkboxPtSg"></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaRisultatiIntestazioniSG" style="width:100%">
						<thead>
				    		<tr>
				      		<th>Denominazione</th>
				      		<th>Codice fiscale</th>
				      		<th>ID Soggetto</th>
				      		<th>Tipo</th>
				      		<th>Sezione</th>
				      		<th>Foglio</th>
				      		<th>Numero</th>
							<th>Subalterno</th>
							<th>Codice Comune</th>
							<th>Comune</th>
							<th>Provincia</th>
				      		<th>UTE Soggetto</th>
							<th></th>
				    	</tr>
				  	</thead>
				  	<tbody></tbody>
					<tfoot>
		            	<tr>
				      		<th>Denominazione</th>
				      		<th>Codice fiscale</th>
				      		<th>ID Soggetto</th>
				      		<th>Tipo</th>
				      		<th>Sezione</th>
				      		<th>Foglio</th>
				      		<th>Numero</th>
							<th>Subalterno</th>
							<th>Codice Comune</th>
							<th>Comune</th>
							<th>Provincia</th>
				      		<th>UTE Soggetto</th>
							<th></th>
				    	</tr>
		        	</tfoot>
				</table>
				</div>
				<div id="dataAggiornamento">
					 
				</div> 
			</div>
		
			<!-- DETTAGLIO -->
			<div class="col-md-12 hidden tabbable-panel" id="dettaglio">
				<div class="tabbable-line">
				<ul class="nav nav-tabs" id="dettaglioTabPt" role="tablist">
		  			<li class="nav-item">
		    			<a class="nav-link" id="anagrafica-tab6-pt" data-toggle="tab" href="#anagraficaTabContent6-pt" role="tab" aria-controls="anagraficaTabContent6-pt" aria-selected="true">
							Anagrafica
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="porzione-tab6-pt" data-toggle="tab" href="#porzioneTabContent6-pt" role="tab" aria-controls="porzioneTabContent6-pt" aria-selected="false">
							Porzione
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="deduzione-tab6-pt" data-toggle="tab" href="#deduzioneTabContent6-pt" role="tab" aria-controls="deduzioneTabContent6-pt" aria-selected="false">
							Deduzione
						</a>
		  			</li>
					<li class="nav-item">
		    			<a class="nav-link" id="riserva-tab6-pt" data-toggle="tab" href="#riservaTabContent6-pt" role="tab" aria-controls="riservaTabContent6-pt" aria-selected="false">
							Riserva
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="persone-tab6-pt" data-toggle="tab" href="#personeTabContent6-pt" role="tab" aria-controls="personeTabContent6-pt" aria-selected="false">
							Persone fisiche
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="soggetti-tab6-pt" data-toggle="tab" href="#soggettiTabContent6-pt" role="tab" aria-controls="soggettiTabContent6-pt" aria-selected="false">
							Soggetti Giuridici
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="calcolo-tab6-pt" data-toggle="tab" href="#identificativiTabContent6-pt" role="tab" aria-controls="identificativiTabContent6-pt" aria-selected="false">
							Identificativi UIU
						</a>
		  			</li>
				</ul>
				<ul class="nav nav-tabs" id="dettaglioTabUiu" role="tablist">
		  			<li class="nav-item">
		    			<a class="nav-link" id="anagrafica-tab6-uiu" data-toggle="tab" href="#anagraficaTabContent6-uiu" role="tab" aria-controls="anagraficaTabContent6-uiu" aria-selected="true">
							Anagrafica UIU
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="indirizzi-tab6-uiu" data-toggle="tab" href="#indirizziTabContent6-uiu" role="tab" aria-controls="indirizziTabContent6-uiu" aria-selected="false">
							Indirizzi
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="identificativi-tab6-uiu" data-toggle="tab" href="#identificativiTabContent6-uiu" role="tab" aria-controls="identificativiTabContent6-uiu" aria-selected="false">
							Identificativi UIU
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="persone-tab6-uiu" data-toggle="tab" href="#personeTabContent6-uiu" role="tab" aria-controls="personeTabContent6-uiu" aria-selected="false">
							Persone fisiche
						</a>
		  			</li>
		  			<li class="nav-item">
		    			<a class="nav-link" id="soggetti-tab6-uiu" data-toggle="tab" href="#soggettiTabContent6-uiu" role="tab" aria-controls="soggettiTabContent6-uiu" aria-selected="false">
							Soggetti Giuridici
						</a>
		  			</li>
				</ul>
				<div class="tab-content" id="dettaglioTabContent6">
		  			<div class="tab-pane active" id="anagraficaTabContent6-pt" role="tabpanel" aria-labelledby="anagrafica-tab6-pt">
						<form class="form-horizontal">
							<div class="row nom nop">
								<!-- COMUNE -->
								<div class="form-group col-md-6">
							    	<label for="comuneDettaglioPtIntestazioniSg" class="control-label">Comune</label>
							    	<input type="text" class="form-control" id="comuneDettaglioPtIntestazioniSg" readonly>
									<input type="text" class="hidden" id="idImmobilePtSG">
							    </div>
								<!-- FOGLIO / NUMERO / DENOMINATORE -->
		  						<div class="form-group col-md-2">
		    						<label for="foglioDettaglioPtIntestazioniSg" class="control-label">Foglio</label>
		    						<input type="text" class="form-control" id="foglioDettaglioPtIntestazioniSg" readonly>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="numeroDettaglioPtIntestazioniSg" class="control-label">Numero</label>
		    						<input type="text" class="form-control" id="numeroDettaglioPtIntestazioniSg" readonly>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="denominatoreDettaglioPtIntestazioniSg" class="control-label">Denominatore</label>
		    						<input type="text" class="form-control" id="denominatoreDettaglioPtIntestazioniSg" readonly>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- SUBALTERNO / PARTITA -->
		  						<div class="form-group col-md-3">
		    						<label for="subalternoDettaglioPtIntestazioniSg" class="control-label">Subalterno</label>
		    						<input type="text" class="form-control" id="subalternoDettaglioPtIntestazioniSg" readonly>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="partitaDettaglioPtIntestazioniSg" class="control-label">Partita</label>
		    						<input type="text" class="form-control" id="partitaDettaglioPtIntestazioniSg" readonly>
		    					</div>
		  						<!-- QUALITA / CLASSE -->
		  						<div class="form-group col-md-3">
		    						<label for="qualitaDettaglioPtIntestazioniSg" class="control-label">Qualit&agrave;</label>
		    						<input type="text" class="form-control" id="qualitaDettaglioPtIntestazioniSg" readonly>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="classeDettaglioPtIntestazioniSg" class="control-label">Classe</label>
		    						<input type="text" class="form-control" id="classeDettaglioPtIntestazioniSg" readonly>
		    					</div>
		  					</div>
							<div class="row nom nop">
								<!-- ETTARI / ARE / CENTIARE -->
		  						<div class="form-group col-md-2">
		    						<label for="ettariDettaglioPtIntestazioniSg" class="control-label">Ettari</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="ettariDettaglioPtIntestazioniSg" readonly>
		      							<div class="input-group-append"><span class="input-group-text">ha</span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="areDettaglioPtIntestazioniSg" class="control-label">Are</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="areDettaglioPtIntestazioniSg" readonly>
		      							<div class="input-group-append"><span class="input-group-text">are</span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-2">
		    						<label for="centiareDettaglioPtIntestazioniSg" class="control-label">Centiare</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="centiareDettaglioPtIntestazioniSg" readonly>
		      							<div class="input-group-append"><span class="input-group-text">ca</span></div>
		      						</div>
		    					</div>
		  						<!-- REDDITO AGRARIO -->
		  						<div class="form-group col-md-3">
		    						<label for="agrarioEuroDettaglioPtIntestazioniSg" class="control-label">Agrario euro</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="agrarioEuroDettaglioPtIntestazioniSg" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-euro"></em></span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="agrarioLireDettaglioPtIntestazioniSg" class="control-label">Agrario lire</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="agrarioLireDettaglioPtIntestazioniSg" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
		      						</div>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- REDDITO DOMINICALE -->
		  						<div class="form-group col-md-3">
		    						<label for="dominicaleEuroDettaglioPtIntestazioniSg" class="control-label">Dominicale euro</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="dominicaleEuroDettaglioPtIntestazioniSg" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-euro"></em></span></div>
		      						</div>
		    					</div>
								<div class="form-group col-md-3">
		    						<label for="dominicaleLireDettaglioPtIntestazioniSg" class="control-label">Dominicale lire</label>
		    						<div class="input-group">
		      							<input type="text" class="form-control" id="dominicaleLireDettaglioPtIntestazioniSg" readonly>
		      							<div class="input-group-append"><span class="input-group-text"><em class="fa fa-gbp"></em></span></div>
		      						</div>
		    					</div>
		  					</div>
							<div class="row nom nop">
		  						<!-- ANNOTAZIONE -->
		  						<div class="form-group col-md-12">
							    	<label for="annotazioneDettaglioPtIntestazioniSg" class="control-label">Annotazione</label>
							    	<div class="input-group">
							    		<textarea class="form-control" id="annotazioneDettaglioPtIntestazioniSg" rows="3" readonly></textarea>
										<div class="input-group-append"><span class="input-group-text"><em class="fa fa-clipboard"></em></span></div>
									</div>
							    </div>
							  </div>
						</form>
					</div>
		  			<div class="tab-pane" id="porzioneTabContent6-pt" role="tabpanel" aria-labelledby="porzione-tab6-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaPorzionePtIntestazioniSg" style="width:100%">
									<thead>
										<tr>
											<th>Classe</th>
											<th>Ettari</th>
											<th>Are</th>
											<th>Centiare</th>
											<th>Qualita</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
		  			<div class="tab-pane" id="deduzioneTabContent6-pt" role="tabpanel" aria-labelledby="deduzione-tab6-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaDeduzionePtIntestazioniSg" style="width:100%">
									<thead>
										<tr>
											<th>Simbolo Deduzione</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
					<div class="tab-pane" id="riservaTabContent6-pt" role="tabpanel" aria-labelledby="riserva-tab6-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaRiservaPtIntestazioniSg" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Riserva</th>
							      		<th>Partita Iscrizione</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="personeTabContent6-pt" role="tabpanel" aria-labelledby="persone-tab6-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaPersoneFisichePtIntestazioniSg" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="soggettiTabContent6-pt" role="tabpanel" aria-labelledby="soggetti-tab6-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaSoggettiGiuridiciPtIntestazioniSg" style="width:100%">
									<thead>
										<tr>
											<th>Nominativo</th>
											<th>Diritto</th>
											<th>Data Efficacia</th>
											<th>Data registrazione</th>
											<th>Tipo Atto</th>
											<th>Numero Atto</th>
											<th>Progressivo Atto</th>
											<th>Anno Atto</th>
											<th>Descrizione Atto</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
					<div class="tab-pane" id="identificativiTabContent6-pt" role="tabpanel" aria-labelledby="identificativi-tab6-pt">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
							<div class="table-responsive">
								<table class="table table-striped" id="tabellaIdentificativiUIUPtIntestazioniSg" style="width:100%">
									<thead>
										<tr>
											<th>Sezione urbana</th>
											<th>Foglio</th>
											<th>Numero</th>
											<th>Subalterno</th>
											<th>Partita</th>
											<th class="text-center"></th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>				
					</div>
					<div class="tab-pane active" id="anagraficaTabContent6-uiu" role="tabpanel" aria-labelledby="anagrafica-tab6-uiu">
							<form class="form-horizontal">
								<!-- COMUNE / IDENTIFICATIVO -->
								<div class="d-flex">
									<div class="form-group col">
							    		<label for="comuneDettaglioUiuIntestazioniSg" class="control-label">Comune</label>
							    		<input type="text" class="form-control" id="comuneDettaglioUiuIntestazioniSg" readonly>
										<input type="text" class="hidden" id="idImmobileUiuSG">
							    	</div>
									<div class="form-group col">
							    		<label for="identificativoDettaglioUiuIntestazioniSg" class="control-label">Identificativo</label>
							    		<input type="text" class="form-control" id="identificativoDettaglioUiuIntestazioniSg" readonly>
							  		</div>
								</div>
							  	<!-- ZONA / CATEGORIA / CLASSE -->
								<div class="d-flex">
		  							<div class="form-group col-md-2">
		    							<label for="zonaDettaglioUiuIntestazioniSg" class="control-label">Zona</label>
		    							<input type="text" class="form-control" id="zonaDettaglioUiuIntestazioniSg" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="categoriaDettaglioUiuIntestazioniSg" class="control-label">Categoria</label>
		    							<input type="text" class="form-control" id="categoriaDettaglioUiuIntestazioniSg" readonly>
									</div>
									<div class="form-group col-md-2">
		    							<label for="classeDettaglioUiuIntestazioniSg" class="control-label">Classe</label>
		    							<input type="text" class="form-control" id="classeDettaglioUiuIntestazioniSg" readonly>
		    						</div>
		  						
		  							<!-- PARTITA / SUPERFICIE / CONSISTENZA -->
		  							<div class="form-group col-md-2">
		    							<label for="partitaDettaglioUiuIntestazioniSg" class="control-label">Partita</label>
		    							<input type="text" class="form-control" id="partitaDettaglioUiuIntestazioniSg" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="superficieDettaglioUiuIntestazioniSg" class="control-label">Superficie</label>
		    							<input type="text" class="form-control" id="superficieDettaglioUiuIntestazioniSg" readonly >
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="consistenzaDettaglioUiuIntestazioniSg" class="control-label">Consistenza</label>
		    							<input type="text" class="form-control" id="consistenzaDettaglioUiuIntestazioniSg" readonly>
		    						</div>
		  						</div>
		  						<!-- RENDITA EURO / LIRE -->
								<div class="d-flex">
		  							<div class="form-group col-md-3">
		    							<label for="renditaEuroDettaglioUiuIntestazioniSg" class="control-label">Rendita Euro</label>
										<div class="input-group">
		    								<input type="text" class="form-control" id="renditaEuroDettaglioUiuIntestazioniSg" readonly>
											<div class="input-group-append">
												<span class="input-group-text"><em class="fa fa-euro"></em></span>
											</div>
										</div>
		    						</div>
									<div class="form-group col-md-3">
		    							<label for="renditaLireDettaglioUiuIntestazioniSg" class="control-label">Rendita Lire</label>
										<div class="input-group">
		    								<input type="text" class="form-control" id="renditaLireDettaglioUiuIntestazioniSg" readonly>
											<div class="input-group-append">
												<span class="input-group-text"><em class="fa fa-gbp"></em></span>
											</div>
										</div>
		    						</div>
								</div>
								<div class="d-flex">
		  							<!-- LOTTO / EDIFICIO / SCALA -->
		  							<div class="form-group col-md-2">
		    							<label for="lottoDettaglioUiuIntestazioniSg" class="control-label">Lotto</label>
		    							<input type="text" class="form-control" id="lottoDettaglioUiuIntestazioniSg" readonly>
		    						</div>
									<div class="form-group col-md-4">
		    							<label for="edificioDettaglioUiuIntestazioniSg" class="control-label">Edificio</label>
		    							<input type="text" class="form-control" id="edificioDettaglioUiuIntestazioniSg" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="scalaDettaglioUiuIntestazioniSg" class="control-label">Scala</label>
		    							<input type="text" class="form-control" id="scalaDettaglioUiuIntestazioniSg" readonly>
		    						</div>
		  							<!-- INTERNO / PIANO -->
		  							<div class="form-group col-md-2">
		    							<label for="internoDettaglioUiuIntestazioniSg" class="control-label">Interno</label>
		    							<input type="text" class="form-control" id="internoDettaglioUiuIntestazioniSg" readonly>
		    						</div>
									<div class="form-group col-md-2">
		    							<label for="pianoDettaglioUiuIntestazioniSg" class="control-label">Piano</label>
		    							<input type="text" class="form-control" id="pianoDettaglioUiuIntestazioniSg" readonly>
		    						</div>
		  						</div>
		  						<!-- ANNOTAZIONE -->
								<div class="d-flex">
		  							<div class="form-group col">
							    		<label for="annotazioneDettaglioUiuIntestazioniSg" class="control-label">Annotazione</label>
							    		<div class="input-group">
			      							<textarea class="form-control" id="annotazioneDettaglioUiuIntestazioniSg" rows="3" readonly></textarea>
			    							<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>   
									    </div>
							  		</div>
								</div>
							</form>					
					</div>
		  			<div class="tab-pane" id="indirizziTabContent6-uiu" role="tabpanel" aria-labelledby="indirizzi-tab6-uiu">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaIndirizziUiuIntestazioniSg" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Indirizzo</th>
							      		<th>Civico (1)</th>
							      		<th>Civico (2)</th>
							      		<th>Civico (3)</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
		  			<div class="tab-pane" id="identificativiTabContent6-uiu" role="tabpanel" aria-labelledby="identificativi-tab6-uiu">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaIdentificativiUiuIntestazioniSg" style="width:100%">
								<thead>
							    	<tr>
							      		<!--<th class="text-center">Principale</th>-->
							      		<th>Sezione Urbana</th>
							      		<th>Foglio</th>
							      		<th>Numero</th>
							      		<th>Subalterno</th>
							      		<th class="text-center"></th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="personeTabContent6-uiu" role="tabpanel" aria-labelledby="persone-tab6-uiu">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaPersoneFisicheUiuIntestazioniSg" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="soggettiTabContent6-uiu" role="tabpanel" aria-labelledby="soggetti-tab6-uiu">
						<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
						<div class="table-responsive">
							<table class="table table-striped" id="tabellaSoggettiGiuridiciUiuIntestazioniSg" style="width:100%">
								<thead>
							    	<tr>
							      		<th>Nominativo</th>
							      		<th>Diritto</th>
							      		<th>Data Efficacia</th>
							      		<th>Data registrazione</th>
										<th>Tipo Atto</th>
							      		<th>Numero Atto</th>
							      		<th>Progressivo Atto</th>
							      		<th>Anno Atto</th>
										<th>Descrizione Atto</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  	</tbody>
							</table>
						</div>
						</div>				
					</div>
					<div class="tab-pane" id="calcoloTabContent6-uiu" role="tabpanel" aria-labelledby="calcolo-tab6-uiu">
						<!--div class="col-md-12"-->
							<form class="form-horizontal">
								<div class="row nom">
									<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Superficie catastale</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							  		</div>
									<div class="form-group col">
										<div class="d-flex">
											<div class="col">
							    				<label for="superficieCatastaleDettaglio" class="control-label">Locali principali</label>
												<div class="input-group">
							    					<input type="text" class="form-control" id="superficieCatastaleDettaglio">
													<div class="input-group-append"><span class="input-group-text">mq</span></div>
												</div>
							    			</div>
											<div class="col">
												<label for="superficieCatastaleDettaglio" class="control-label">Balconi al piano</label>
							    				<div class="input-group">
							    					<input type="text" class="form-control" id="superficieCatastaleDettaglio">
													<div class="input-group-append"><span class="input-group-text">mq</span></div>
												</div>
							    			</div>
										</div>
							  		</div>
								</div>
								<div class="row nom">
									<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Accessi diretti</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
									<div class="form-group col">
										<label for="superficieCatastaleDettaglio" class="control-label">Balconi non a livello</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
							  		<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Accessi indiretti collegati</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
									<div class="form-group col">
										<label for="superficieCatastaleDettaglio" class="control-label">Aree scoperte esclusive</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
							  	</div>
								<div class="row nom">
									<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Accessi indiretti non collegati</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
									<div class="form-group col">
										<label for="superficieCatastaleDettaglio" class="control-label">Ambienti non classificabili</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
							  		<div class="form-group col">
							    		<label for="superficieCatastaleDettaglio" class="control-label">Superficie totale</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
									<div class="form-group col">
										<label for="superficieCatastaleDettaglio" class="control-label">Superficie calcolata</label>
							    		<div class="input-group">
							    			<input type="text" class="form-control" id="superficieCatastaleDettaglio">
											<div class="input-group-append"><span class="input-group-text">mq</span></div>
										</div>
							    	</div>
							  	</div>
								<div class="col-md-12 pt-5 pb-3 text-center">
    								<button type="button" data-info="Lista planimetrie" class="btn-trasp bttn align-center">Lista planimetrie</button>
								</div>
							</form>
						<!--/div-->
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" class="pulsante-ricerca" href="#" id="eseguiBtnIntestazioniSG" class=""><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" class="pulsante-ricerca" href="#" id="azzeraBtnIntestazioniSG"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Esporta il dettaglio" class="pulsante-ricerca" href="#" id="esportaDettaglioBtnIntestazioniSG"><em class="fa fa-download"></em>&nbsp;&nbsp;Esporta</a></li>
					<li><a data-info="Chiudi la finestra" class="pulsante-ricerca pulsante-dettaglio"  id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li class="hidden"><a data-info="Ritorna alla ricerca" class="pulsante-dettaglio" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
					<li class="hidden"><a data-info="Esegui visura catastale" class="pulsante-dettaglio" href="#" id="effettuaVisuraBtnIntestazioniSG"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Visura catastale</a></li>
					<li class="hidden"><a data-info="Esegui visura catastale storica" class="pulsante-dettaglio" href="#" id="effettuaVisuraStoricaBtnIntestazioniSG"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Visura catastale storica</a></li>
					<li><a data-info="Chiudi la finestra" class="pulsante-ricerca pulsante-dettaglio"  id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div> 
</div>
</script>
