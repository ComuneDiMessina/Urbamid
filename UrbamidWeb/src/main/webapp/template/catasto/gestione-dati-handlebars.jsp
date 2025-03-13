<!-- 
    ______  _______  ____  ____  _________       ____  ___  __________
   /  _/  |/  / __ \/ __ \/ __ \/_  __/   |     / __ \/   |/_  __/  _/
   / // /|_/ / /_/ / / / / /_/ / / / / /| |    / / / / /| | / /  / /  
 _/ // /  / / ____/ /_/ / _, _/ / / / ___ |   / /_/ / ___ |/ / _/ /   
/___/_/  /_/_/    \____/_/ |_| /_/ /_/  |_|  /_____/_/  |_/_/ /___/   
                                                                      
 -->
 
<script id="modImportaDati" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Gestione Dati</a></li>
					<li class="active"><a href="#">Importa Dati</a></li>
				</ul>
			</div>
			<div id="laRispostaAllaVitaLUniversoETuttoQuanto_42">
				<!-- STEPPER -->
				<div class="wizard">
					<div class="wizard-inner">
						<div class="connecting-line"></div>
						<ul class="nav nav-tabs" role="tablist">
							<!-- STEP FABBRICATI -->
							<li role="presentation" class="active">
								<a href="#stepUploadFabbricatiDiv" id="stepUploadFabbricati" data-toggle="tab" aria-controls="stepUploadFabbricati" role="tab" data-info="Step scelta file relativi ai fabbricati">
									<span class="round-tab">
										<i class="fa fa-folder-open"></i> FABBRICATI
									</span>
								</a>
							</li>
							<!-- STEP TERRENI -->
							<li role="presentation">
								<a href="#stepUploadTerreniDiv" id="stepUploadTerreni" data-toggle="tab" aria-controls="stepUploadTerreni" role="tab" data-info="Step scelta file relativi ai terreni">
									<span class="round-tab">
										<i class="fa fa-folder-open"></i> TERRENI
									</span>
								</a>
							</li>
							<!-- STEP CARTOGRAFIA -->
							<li role="presentation">
								<a href="#stepUploadCartografiaDiv" id="stepUploadCartografia" data-toggle="tab" aria-controls="stepUploadCartografia" role="tab" data-info="Step scelta file relativi alla cartografia">
									<span class="round-tab">
										<i class="fa fa-folder-open"></i> CARTOGRAFIA
									</span>
								</a>
							</li>
							<!-- STEP PLANIMETRIE -->
							<li role="presentation">
								<a href="#stepUploadPlanimetrieDiv" id="stepUploadPlanimetrie" data-toggle="tab" aria-controls="stepUploadPlanimetrie" role="tab" data-info="Step scelta file relativi alle planimetrie">
									<span class="round-tab">
										<i class="fa fa-folder-open"></i> PLANIMETRIE
									</span>
								</a>
							</li>
						</ul>
					</div>
					<form role="form">
						<div class="tab-content">
							<div class="tab-pane" role="tabpanel" id="stepUploadFabbricatiDiv">
								<!-- UPLOADER FABBRICATI -->
								<div id="importaDatiFabbricati" class="search">
									<div class="col-md-12" style="margin-top: 20px;">
										<div>
											<input type="file" name="files[]" id="uploadFabbricatiInput" multiple="multiple">
											<div class="row" style="padding-top: 0px; padding-bottom: 20px;">
												<div class="col-md-5"></div>
												<div class="col-md-2">
													<input id="avviaUploadFabbricatiButton" type="button" class="btn-custom green" value="Avvia upload"></div>
												<div class="col-md-5"></div>
											</div>
										</div>
									</div>
								</div>
								<!-- TABELLA RISULTATI FABBRICATI -->
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaFileFabbricati" style="width:100%">
										<thead>
									    	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
									      		<th>Data</th>
												<th></th>
									    	</tr>
									  	</thead>
									  	<tbody>
										</tbody>
										<tfoot>
							            	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
									      		<th>Data</th>
												<th></th>
									    	</tr>
							        	</tfoot>
									</table>
								</div>
							</div>
							<div class="tab-pane" role="tabpanel" id="stepUploadTerreniDiv">
								<!-- UPLOADER TERRENI -->
								<div id="importaDatiTerreni" class="search">
									<div class="col-md-12" style="margin-top: 20px;">
										<div>
											<input type="file" name="files[]" id="uploadTerreniInput" multiple="multiple">
											<div class="row" style="padding-top: 0px; padding-bottom: 20px;">
												<div class="col-md-5"></div>
												<div class="col-md-2">
													<input id="avviaUploadTerreniButton" type="button" class="btn-custom green" value="Avvia upload">
												</div>
												<div class="col-md-5"></div>
											</div>
										</div>
									</div>
								</div>
								<!-- TABELLA RISULTATI TERRENI -->
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaFileTerreni" style="width:100%">
										<thead>
									    	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
									      		<th>Data</th>
												<th></th>
									    	</tr>
									  	</thead>
									  	<tbody>
										</tbody>
										<tfoot>
							            	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
									      		<th>Data</th>
												<th></th>
									    	</tr>
							        	</tfoot>
									</table>
								</div>
							</div>
							<div class="tab-pane" role="tabpanel" id="stepUploadCartografiaDiv">
								<div id="importaDatiCartografia" class="search">
									<div class="col-md-12" style="margin-top: 20px;">
										<div>
											<input type="file" name="files[]" id="uploadCartografiaInput" multiple="multiple">
											<div class="row" style="padding-top: 0px; padding-bottom: 20px;">
												<div class="col-md-5"></div>
												<div class="col-md-2">
													<input id="avviaUploadCartografiaButton" type="button" class="btn-custom green" value="Avvia upload">
												</div>
												<div class="col-md-5"></div>
											</div>
										</div>
									</div>
								</div>
								<!-- TABELLA RISULTATI CARTOGRAFIA -->
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaFileCartografia" style="width:100%">
										<thead>
									    	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
									      		<th>Data</th>
												<th></th>
									    	</tr>
									  	</thead>
									  	<tbody>
										</tbody>
										<tfoot>
							            	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
									      		<th>Data</th>
												<th></th>
									    	</tr>
							        	</tfoot>
									</table>
								</div>
							</div>
							<div class="tab-pane" role="tabpanel" id="stepUploadPlanimetrieDiv">
								<div id="importaDatiPlanimetrie" class="search">
									<div class="col-md-12" style="margin-top: 20px;">
										<div>
											<input type="file" name="files[]" id="uploadPlanimetrieInput" multiple="multiple">
											<div class="row" style="padding-top: 0px; padding-bottom: 20px;">
												<div class="col-md-5"></div>
												<div class="col-md-2">
													<input id="avviaUploadPlanimetrieButton" type="button" class="btn-custom green" value="Avvia upload">
												</div>
												<div class="col-md-5"></div>
											</div>
										</div>
									</div>
								</div>
								<!-- TABELLA RISULTATI PLANIMETRIE -->
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaFilePlanimetrie" style="width:100%">
										<thead>
									    	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
									      		<th>Data</th>
												<th></th>
									    	</tr>
									  	</thead>
									  	<tbody>
										</tbody>
										<tfoot>
							            	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
									      		<th>Data</th>
												<th></th>
									    	</tr>
							        	</tfoot>
									</table>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</form>
				</div>				
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a href="#" id="importaDatiBtn" class=""><em class="fa fa-upload" aria-hidden="true"></em>&nbsp;&nbsp;Importa</a></li>
					<li><a href="#" id="annullaBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Annulla</a></li>
					<li><a id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
	
</div>
</script>
 
 
 
<script id="modImportaDati_prev" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Gestione Dati</a></li>
					<li class="active"><a href="#">Importa Dati</a></li>
				</ul>
			</div>
			<div id="importaDati" class="search">
				<div class="col-md-12" style="margin-top: 20px;">
					<input type="file" name="files[]" id="upload-input" multiple="multiple">
				</div>
			</div>
			
			<!-- TABELLA RISULTATI -->
			<div class="table-responsive">
				<table class="table table-striped" id="tabellaFile" style="width:100%">
					<thead>
				    	<tr>
				      		<th>Nome file</th>
				      		<th>Dimensione</th>
				      		<th>Tipo</th>
				      		<th>Codice comune</th>
				      		<th>Data di riferimento</th>
				      		<th>Tipo di importazione</th>
				    	</tr>
				  	</thead>
				  	<tbody>
					</tbody>
					<tfoot>
		            	<tr>
				      		<th>Nome file</th>
				      		<th>Dimensione</th>
				      		<th>Tipo</th>
				      		<th>Codice comune</th>
				      		<th>Data di riferimento</th>
				      		<th>Tipo di importazione</th>
				    	</tr>
		        	</tfoot>
				</table>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a href="#" id="importaDatiBtn" class=""><em class="fa fa-upload" aria-hidden="true"></em>&nbsp;&nbsp;Importa</a></li>
					<li><a href="#" id="annullaBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Annulla</a></li>
					<li><a id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>	
</div>
</script>

<!-- 
   _________    ____  __________  __________  ___    _____________       _________  _________   ______________    __    ______
  / ____/   |  / __ \/_  __/ __ \/ ____/ __ \/   |  / ____/  _/   |     / ____/   |/_  __/   | / ___/_  __/   |  / /   / ____/
 / /   / /| | / /_/ / / / / / / / / __/ /_/ / /| | / /_   / // /| |    / /   / /| | / / / /| | \__ \ / / / /| | / /   / __/   
/ /___/ ___ |/ _, _/ / / / /_/ / /_/ / _, _/ ___ |/ __/ _/ // ___ |   / /___/ ___ |/ / / ___ |___/ // / / ___ |/ /___/ /___   
\____/_/  |_/_/ |_| /_/  \____/\____/_/ |_/_/  |_/_/   /___/_/  |_|   \____/_/  |_/_/ /_/  |_/____//_/ /_/  |_/_____/_____/   
                                                                                                                              
-->
<script id="modImportaCartografiaCatastale" type="text/x-handlebars-template">
<div class="dropdown finestra mainMenu">
</div>
<div class="tab-content">
	<div role="tabpanel" class="tab-pane active" id="ricSpess">
		<div class="globalContainer">
			<div class="search" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Gestione Dati</a></li>
					<li class="active"><a href="#">Importa Cartografia Catastale</a></li>
				</ul>
			</div>
			<div id="statoAggiornamenti">
				<!-- STEPPER -->
				<div class="wizard">
					<div class="wizard-inner">
						<div class="connecting-line"></div>
						<ul class="nav nav-tabs" role="tablist">
							<!-- STEP SCEGLI FILES -->
							<li role="presentation" class="active">
								<a href="#stepUpload" id="stepUploadA" data-toggle="tab" aria-controls="step1" role="tab" data-info="Step scelta file caricamento">
									<span class="round-tab">
										<i class="fa fa-folder-open"></i> Upload
									</span>
								</a>
							</li>
							<!-- STEP OPZIONI CONVERSIONE -->
							<li role="presentation" class=""><!-- CLASS DISABLED PER DISATTIVARE -->
								<a href="#stepOpzioni" id="stepOpzioniA" data-toggle="tab" aria-controls="step3" role="tab" data-info="Step opzioni di conversione">
									<span class="round-tab">
										<i class="fa fa-cog"></i> Opzioni di conversione
									</span>
								</a>
							</li>
						</ul>
					</div>
					<form role="form">
						<div class="tab-content">
							<div class="tab-pane active" role="tabpanel" id="stepUpload">
								<!-- FILTRO TIPO FILE -->

							<div id="importaDati" class="col-sm-12 search mt-4 mb-4" style="background: #fff;">
							<div class="col-md-3">
			        			<label>Filtra per tipo di file</label>
			        			<select class="form-control" id="selectCartografiaCatastale" name="provinciaNascita">
									<option value="">Seleziona...</option>
									<option value="CXF">Cartografia catastale vettoriale (*.cxf)</option>
									<option value="CMF">Cartografia catastale formato XML (*.cmf)</option>
								</select>
			        		</div>
							</div>
								<!-- TABELLA RISULTATI -->
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaFile" style="width:100%">
										<thead>
									    	<tr>
												<th></th>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
												<th>Codice Comune</th>
												<th>Data riferimento</th>
									    	</tr>
									  	</thead>
									  	<tbody>
										</tbody>
										<tfoot>
							            	<tr>
												<th></th>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Tipo</th>
									      		<th>Codice comune</th>
												<th>Data riferimento</th>
									    	</tr>
							        	</tfoot>
									</table>
								</div>
																
							</div>
							<div class="tab-pane" role="tabpanel" id="stepOpzioni">
								<div class="row search nom nop">
									<div class="col-sm-12 col-md-6">
										<div class="panel panel-default" style="min-height: 150px;">
		  									<div class="panel-heading">Opzione di conversione</div>
		  									<div class="panel-body">
												<div class="col-md-12">
													<div class="checkbox">
														<label>
															<input type="checkbox" value="">
																<span class="cr">
																<i class="fa fa-check"></i></span> Elimina esterni alla mappa
														</label>
													</div>
												</div>
												<div class="col-md-12">
													<label>Default altezza test</label>
													<div class="input-group">
														<input id="defaultAltezzaTesti" type="text" class="form-control" name="defaultAltezzaTesti" placeholder="Valore di default in metri...">   
														<div class="input-group-append"><span class="input-group-text">m</span></div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 col-md-6">
										<div class="panel panel-default" style="min-height: 150px;">
		  									<div class="panel-heading">Conversione coordinate</div>
		  									<div class="panel-body">
												<div class="col-md-12">
													<div class="checkbox">
														<label>
															<input type="checkbox" value="">
																<span class="cr">
																<i class="fa fa-check"></i></span> Converti le coordinate da Cassini-Soldner a Gauss-Boaga usando le impostazioni di CS2GB
														</label>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 col-md-6 clearfix">
										<div class="panel panel-default" style="min-height: 250px;">
											<div class="panel-heading">Oggetti da convertire</div>
											<div class="panel-body text-center">
												<select id='multiselect-options' multiple='multiple'>
													<option value='elem_1'>BORDI</option>
													<option value='elem_2'>TESTI</option>
													<option value='elem_3'>SIMBOLI</option>
													<option value='elem_4'>FIDUCIALI</option>
													<option value='elem_100'>LINEE</option>
												</select>
											</div>
										</div>
									</div>
									<div class="col-sm-12 col-md-6">
										<div class="panel panel-default" style="min-height: 250px;">
		  									<div class="panel-heading">Adeguamento coordinate</div>
		  									<div class="panel-body">
												<div class="">
													<div class="row nom nop bhoechie-tab-container">
														<div class="col-md-3 col-sm-12 bhoechie-tab-menu">
															<div class="list-group">
																<a href="#" class="list-group-item active text-center"><em class="da-cancellare-al-momento-opportuno fa fa-check" style="font-size: 1.2em;margin-right:5px;"></em>Nessun adegiamento</a>
																<a href="#" class="list-group-item text-center">Traslazione</a>
																<a href="#" class="list-group-item text-center">File di punti corrispondenti</a>
															</div>
														</div>
														<div class="col-md-9 col-sm-12 bhoechie-tab">
															<div class="bhoechie-tab-content active">Nessun adeguamento per le coordinate</div>
															<div class="bhoechie-tab-content hidden">
																<div class="col-md-12">
																	<label>Valore per X</label>
																	<div class="input-group">
																		<input id="valoreX" type="text" class="form-control" name="valoreX" placeholder="Valore in metri...">   
																		<span class="input-group-addon">m</span>
																	</div>
																</div>
																<div class="col-md-12 clearfix" style="margin-top:12px; margin-bottom: 12px;">
																	<label>Valore per Y</label>
																	<div class="input-group">
																		<input id="valoreY" type="text" class="form-control" name="valoreY" placeholder="Valore in metri...">   
																		<span class="input-group-addon">m</span>
																	</div>
																</div>
															</div>
															<div class="bhoechie-tab-content hidden">
																<div class="col-md-12">
																	<input type="file" name="fileAdeguamento" id="fileAdeguamento">
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
							<div class="clearfix"></div>
						</div>
					</form>
				</div>				
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a href="#" id="nextStep" class="prev-step"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Precedente</a></li>
					<li><a href="#" id="prevStep" class="next-step"><em class="fa fa-chevron-right"></em>&nbsp;&nbsp;Successivo</a></li>
					<li><a href="#" id="annullaBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Annulla</a></li>
					<li class="hidden"><a href="#" id="importaDatiBtn" class=""><em class="fa fa-upload" aria-hidden="true"></em>&nbsp;&nbsp;Importa</a></li>
					<li><a href="#" id="chiudiBtn"><em class="fa fa-times" aria-hidden="true"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>	
</div>
</script>

<!-- 
    ______  _______  ____  ____  _________       ___   ____________________  ____  _   _____    __  __________   ____________
   /  _/  |/  / __ \/ __ \/ __ \/_  __/   |     /   | / ____/ ____/  _/ __ \/ __ \/ | / /   |  /  |/  / ____/ | / /_  __/  _/
   / // /|_/ / /_/ / / / / /_/ / / / / /| |    / /| |/ / __/ / __ / // / / / /_/ /  |/ / /| | / /|_/ / __/ /  |/ / / /  / /  
 _/ // /  / / ____/ /_/ / _, _/ / / / ___ |   / ___ / /_/ / /_/ // // /_/ / _, _/ /|  / ___ |/ /  / / /___/ /|  / / / _/ /   
/___/_/  /_/_/    \____/_/ |_| /_/ /_/  |_|  /_/  |_\____/\____/___/\____/_/ |_/_/ |_/_/  |_/_/  /_/_____/_/ |_/ /_/ /___/   
                                                                                                                             
 -->
<script id="modImportaAggiornamenti" type="text/x-handlebars-template">


</script>

<!-- 
    ______  _______  ____  ____  _________       ____  __    ___    _   ________  ___________________  __________
   /  _/  |/  / __ \/ __ \/ __ \/_  __/   |     / __ \/ /   /   |  / | / /  _/  |/  / ____/_  __/ __ \/  _/ ____/
   / // /|_/ / /_/ / / / / /_/ / / / / /| |    / /_/ / /   / /| | /  |/ // // /|_/ / __/   / / / /_/ // // __/   
 _/ // /  / / ____/ /_/ / _, _/ / / / ___ |   / ____/ /___/ ___ |/ /|  // // /  / / /___  / / / _, _// // /___   
/___/_/  /_/_/    \____/_/ |_| /_/ /_/  |_|  /_/   /_____/_/  |_/_/ |_/___/_/  /_/_____/ /_/ /_/ |_/___/_____/   
                                                                                                                 
-->
<script id="modImportaPlanimetrie" type="text/x-handlebars-template">
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
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Gestione Dati</a></li>
					<li class="active"><a href="#">Importa Planimetrie</a></li>
				</ul>
			</div>
			
			<div class="col-sm-12">
				<h3 class="decorated"><span>Formato di acquisizione delle schede planimetriche</span></h3>
			</div>
			<div class="row nom nop bhoechie-tab-container">
				<div class="col-md-6 col-sm-12 bhoechie-tab-menu">
					<div class="list-group">
						<a href="#" class="list-group-item active text-center">
							<em class="da-cancellare-al-momento-opportuno fa fa-check" style="font-size: 1.2em;margin-right:5px;"></em>
							Specifica tecnica per l'acquisizione delle planimetrie del N.C.E.U.
						</a>
						<a href="#" class="list-group-item text-center">
							Nuova Specifica tecnica per l'acquisizione delle planimetrie del N.C.E.U.
						</a>
						<a href="#" class="list-group-item text-center">
							Specifica tecnica per l'acquisizione delle planimetrie del N.C.E.U. del 22/12/2006
						</a>
						<a href="#" class="list-group-item text-center">
							File contenente le relazioni tra le schede planimetriche e le UIU
						</a>
					</div>
				</div>
				<div class="col-md-6 col-sm-12 bhoechie-tab">
					<div class="bhoechie-tab-content active">
						<blockquote style="padding: 5px 10px;">
						<h3 class="text text-danger" style="margin-top: 0px;">Descrizione acquisizione eseguita</h3>
						<p style="font-size: 0.8em;">
							L'acquisizione delle schede planimetriche si basa sulla specifica tecnica del N.C.E.U.
							Questa specifica rende necessari due file:
							<br><em class="fa fa-chevron-right"></em>&nbsp;File INDICE con estensione .IND;
							<br><em class="fa fa-chevron-right"></em>&nbsp;File ATTRIBUTI con estensione .ATT.
						</p>
						</blockqote>
					</div>
					<div class="bhoechie-tab-content hidden">
						<blockquote style="padding: 5px 10px;">
						<h3 class="text text-danger" style="margin-top: 0px;">Descrizione acquisizione eseguita</h3>
						<p style="font-size: 0.8em;">
							L'acquisizione delle schede planimetriche si basa sulla nuova specifica tecnica del N.C.E.U.
							Questa specifica necessita di:
							<br><em class="fa fa-chevron-right"></em>&nbsp;Un unico file con le superfici degli ambienti già digitalizzati
						</p>
						</blockqote>
					</div>
					<div class="bhoechie-tab-content hidden">
						<blockquote style="padding: 5px 10px;">
						<h3 class="text text-danger" style="margin-top: 0px;">Descrizione acquisizione eseguita</h3>
						<p style="font-size: 0.8em;">
							L'acquisizione delle schede planimetriche si basa sulla specifica tecnica del N.C.E.U. del 22 Dicembre del 2006
							Questa specifica rende necessari tre file:
							<br><em class="fa fa-chevron-right"></em>&nbsp;File CodComune_AAAAMM_DM.DAT con i dati metrici delle UIU.
							<br><em class="fa fa-chevron-right"></em>&nbsp;File CodComune_AAAAMM_SC.DAT con i dati alfanumerici delle schede planimetriche.
							<br><em class="fa fa-chevron-right"></em>&nbsp;File CodComune_AAAAMM_DM_PL.LIS con informazioni riepilogative della fornitura.
						</p>
						</blockqote>
					</div>
					<div class="bhoechie-tab-content hidden">
						<blockquote style="padding: 5px 10px;">
						<h3 class="text text-danger" style="margin-top: 0px;">Descrizione acquisizione eseguita</h3>
						<p style="font-size: 0.8em;">
							L'acquisizione delle schede planimetriche si basa su:
							<br><em class="fa fa-chevron-right"></em>&nbsp;Un unico file contenente le relazioni tra le schede planimetriche
							e le UIU.
						</p>
						</blockquote>
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<h3 class="decorated"><span>File necessari</span></h3>
			</div>
			<div class="" id="scelta0">
				<div class="col-sm-12 col-md-6">
					<label>File indice (*.ind)</label>
					<input type="file" name="fileAdeguamento" id="file00">
				</div>
				<div class="col-sm-12 col-md-6">
					<label>File attributi (*.att)</label>
					<input type="file" name="fileAdeguamento" id="file01">
				</div>
			</div>
			<div class="" id="scelta1">
				<div class="col-sm-12 col-md-6">
					<label>File con le superfici degli ambienti già digitalizzati</label>
					<input type="file" name="fileAdeguamento" id="file10">
				</div>
			</div>
			<div class="" id="scelta2">
				<div class="col-sm-12 col-md-6">
					<label>File dati metrici UIU (*.dat)</label>
					<input type="file" name="fileAdeguamento" id="file20">
				</div>
				<div class="col-sm-12 col-md-6">
					<label>File con i dati alfanumerici delle schede planimetriche (*.dat)</label>
					<input type="file" name="fileAdeguamento" id="file21">
				</div>
				<div class="col-sm-12 col-md-6">
					<label>File con informazioni riepilogative della fornitura (*.lis)</label>
					<input type="file" name="fileAdeguamento" id="file22">
				</div>
			</div>
			<div class="" id="scelta3">
				<div class="col-sm-12 col-md-6">
					<label>File con le relazioni tra le schede planimetriche e le UIU</label>
					<input type="file" name="fileAdeguamento" id="file30">
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a href="#" id="annullaBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Annulla</a></li>
					<li><a href="#" id="importaDatiBtn"><em class="fa fa-upload" aria-hidden="true"></em>&nbsp;&nbsp;Importa</a></li>
					<li><a href="#" id="chiudiBtn"><em class="fa fa-times" aria-hidden="true"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>

</script>

<!-- 
   ______________  __________     ___   ____________________  ____  _   _____    __  __________   ____________
  / ___/_  __/   |/_  __/ __ \   /   | / ____/ ____/  _/ __ \/ __ \/ | / /   |  /  |/  / ____/ | / /_  __/  _/
  \__ \ / / / /| | / / / / / /  / /| |/ / __/ / __ / // / / / /_/ /  |/ / /| | / /|_/ / __/ /  |/ / / /  / /  
 ___/ // / / ___ |/ / / /_/ /  / ___ / /_/ / /_/ // // /_/ / _, _/ /|  / ___ |/ /  / / /___/ /|  / / / _/ /   
/____//_/ /_/  |_/_/  \____/  /_/  |_\____/\____/___/\____/_/ |_/_/ |_/_/  |_/_/  /_/_____/_/ |_/ /_/ /___/   
                                                                                                              
 -->
<script id="modStatoAggiornamenti" type="text/x-handlebars-template">
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
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Gestione Dati</a></li>
					<li class="active"><a href="#">Importa Dati</a></li>
				</ul>
			</div>
			<div id="statoAggiornamenti" class="search">
				<!-- TABELLA STATO AGGIORNAMENTI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaStatoAggiornamenti" style="width:100%">
						<thead>
				    		<tr>
				      			<th>Codice</th>
				      			<th>Comune</th>
				      			<th>Particelle Terreni</th>
				      			<th>Unità immobiliari urbane</th>
					    	</tr>
					  	</thead>
					  	<tbody>
							<tr>
								<td>F158</td>
								<td>MESSINA</td>
								<td>02/05/2018</td>
								<td>02/05/2018</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a  id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</script>


<script id="modStatoAggiornamenti" type="text/x-handlebars-template">
	{{#aaData}}
	<div class="jFiler-item-container">
		<div class="jFiler-item-inner">
			<div class="jFiler-item-thumb">
				<div class="jFiler-item-status"></div>									
				<div class="jFiler-item-thumb-overlay">
					<div class="jFiler-item-info">
						<div style="display:table-cell;vertical-align: middle;">
							<span class="jFiler-item-title"><b title="estrazione-particellare_.zip">{{nomeImage}}</b></span>												
							<span class="jFiler-item-others">4.52 MB</span>											
						</div>										
					</div>									
				</div>									
				<div class="jFiler-item-thumb-image">
					<span class="jFiler-icon-file f-file f-file-ext-zip" style="background-color: rgb(33, 200, 114);">.zip</span>
				</div>								
			</div>								
			<div class="jFiler-item-assets jFiler-row">									
				<ul class="list-inline pull-left"><li></li></ul>									
				<ul class="list-inline pull-right"><li><a class="fa fa-eye"></a></li></ul>								
				<ul class="list-inline pull-right"><li><a class="fa fa-download"></a></li></ul>
			</div>							
		</div>						
	</div>
	{{/aaData}}
</script>	