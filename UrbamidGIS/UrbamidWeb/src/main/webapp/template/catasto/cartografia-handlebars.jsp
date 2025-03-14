<!-- 
    ____  _______    _   ______     ____  ___    ____  ________________________    __    ___    ____  ______
   / __ \/  _/   |  / | / / __ \   / __ \/   |  / __ \/_  __/  _/ ____/ ____/ /   / /   /   |  / __ \/ ____/
  / /_/ // // /| | /  |/ / / / /  / /_/ / /| | / /_/ / / /  / // /   / __/ / /   / /   / /| | / /_/ / __/   
 / ____// // ___ |/ /|  / /_/ /  / ____/ ___ |/ _, _/ / / _/ // /___/ /___/ /___/ /___/ ___ |/ _, _/ /___   
/_/   /___/_/  |_/_/ |_/\____/  /_/   /_/  |_/_/ |_| /_/ /___/\____/_____/_____/_____/_/  |_/_/ |_/_____/   
                                                                                                            
 -->
<script id="modPianoParticellare" type="text/x-handlebars-template">
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
			<div class="search hidden" style="margin-bottom: 0px;">
				<ul class="breadcrumb">
					<li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
					<li><a href="#">Gestione Dati Catastali</a></li>
					<li><a href="#">Cartografia</a></li>
					<li class="active"><a href="#">Piano Particellare</a></li>
				</ul>
			</div>
			<div id="pianoParticellare" class="">
				<div class="col-sm-12">
					<label>Tipo Geometria</label>
				    <select class="fa form-control" id="geometria">
						<option selected class="fa">&#xf096 &nbsp; Poligono</option>
						<option class="fa">&#xf1db &nbsp; Cerchio</option>
					</select>
				</div>				
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a href="#" id="avviaDisegnoSuMappaBtn" data-info="Inizia a tracciare l'area di interesse su mappa"><em class="fa fa-play" aria-hidden="true"></em>&nbsp;&nbsp;Avvia</a></li>
					<li><a href="#" id="terminaDisegnoSuMappaBtn" data-info="Termina il disegno dell'area di interesse su mappa"><em class="fa fa-stop" aria-hidden="true"></em>&nbsp;&nbsp;Termina</a></li>
					<li><a href="#" id="annullaBtn" data-info="Annulla"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Annulla</a></li>
					<li><a id="chiudiBtn" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</script>

<script id="modExportPdf" type="text/x-handlebars-template">
	<div class="dropdown finestra mainMenu">
		<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</a>
 	</div>
	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="estrazionePart">
			<div class="globalContainer">
				<div class="col-md-12 tabbable-panel">
					<div class="row">
						<div class="col-sx-12 col-md-12 col-lg-12">
							Titolo: <input type="text" class="form-control" id="titoloExport">
						</div>
					</div>
					<div class="row">
						<div class="col-sx-3 col-md-3 col-lg-3">
							<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="fabbricatiNominativo"><em class="fa fa-file-pdf-o"></em>&nbsp;Fabbricati per Nominativo</button>
						</div>
						<div class="col-sx-3 col-md-3 col-lg-3">
							<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="terreniNominativo"><em class="fa fa-file-pdf-o"></em>&nbsp;Terreni per Nominativo</button>
						</div>
						<div class="col-sx-3 col-md-3 col-lg-3">
							<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="fabbricatiParticella"><em class="fa fa-file-pdf-o"></em>&nbsp;Fabbricati per Particella</button>
						</div>
						<div class="col-sx-3 col-md-3 col-lg-3">
							<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="terreniParticella"><em class="fa fa-file-pdf-o"></em>&nbsp;Terreni per Particella</button>
						</div>
					</div>
				</div>
				<div class="absPulsante">
					<ul class="noStyle inline pulsanti-ricerca">
						<li><a data-info="Esporta tutti i PDF" href="#" id="totalPdf"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Esporta tutto</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</script>

<script id="modExportExcel" type="text/x-handlebars-template">
	<div class="dropdown finestra mainMenu">
		<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</a>
 	</div>
	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="estrazionePart">
			<div class="globalContainer">
				<div class="col-md-12 tabbable-panel">
					<div class="row" style="padding: 10px 5px;">
						<div class="col-md-3">
							<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="soggettiXls"><em class="fa fa-file-excel-o"></em>&nbsp;Soggetti</button>
						</div>
						<div class="col-md-3">
							<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="uiuXls"><em class="fa fa-file-excel-o"></em>&nbsp;UIU</button>
						</div>
						<div class="col-md-3">
							<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="terreniXls"><em class="fa fa-file-excel-o"></em>&nbsp;Terreni</button>
						</div>
						<div class="col-md-3">
							<button type="button" class="btn-trasp bttn iziToast-buttons-child" id="intestazioniXls"><em class="fa fa-file-excel-o"></em>&nbsp;Intestazioni</button>
						</div>
					</div>
				</div>
				<div class="absPulsante">
					<ul class="noStyle inline pulsanti-ricerca">
						<li><a data-info="Esporta tutti gli xls" href="#" id="totalXls"><em class="fa fa-file-excel-o"></em>&nbsp;&nbsp;Esporta tutto</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</script>

<!-- 
    ____  _______    _   ______     _____________________  ___  ________________ 
   / __ \/  _/   |  / | / / __ \   / ____/ ___/_  __/ __ \/   |/_  __/_  __/ __ \
  / /_/ // // /| | /  |/ / / / /  / __/  \__ \ / / / /_/ / /| | / /   / / / / / /
 / ____// // ___ |/ /|  / /_/ /  / /___ ___/ // / / _, _/ ___ |/ /   / / / /_/ / 
/_/   /___/_/  |_/_/ |_/\____/  /_____//____//_/ /_/ |_/_/  |_/_/   /_/  \____/  
                                                                                 
 -->
<script id="modPianoParticellareEstratto" type="text/x-handlebars-template">
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
					<li><a href="#">Cartografia</a></li>
					<li class="active"><a href="#">Piano Particellare</a></li>
				</ul>
			</div>
			<!-- DETTAGLIO PIANO PARTICELLARE -->
			<div id="dettaglioPianoParticellare" class="">
				<div class="col-md-12 tabbable-panel" id="dettaglio" style="padding: 0px; margin-top: 20px;">
					<div class="tabbable-line">
						<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
				  			<li class="nav-item" data-info="Particelle Terreni del Piano Particellare">
				    			<a class="nav-link" id="piano-pt-tab1" data-toggle="tab" href="#pianoPtTabContent1" role="tab" aria-controls="pianoPtTabContent1" aria-selected="false">
									<strong>Particelle Terreni</strong>
								</a>
				  			</li>
				  			<li class="nav-item" data-info="Unita Immobiliari Urbane del Piano Particellare">
				    			<a class="nav-link" id="piano-uiu-tab1" data-toggle="tab" href="#pianoUiuTabContent1" role="tab" aria-controls="pianoUiuTabContent1" aria-selected="false">
									<strong>Unita Immbiliari</strong>
								</a>
				  			</li>
				  			<li class="nav-item" data-info="Intestatari Persone Fisiche">
				    			<a class="nav-link" id="piano-intestatari-pf-tab1" data-toggle="tab" href="#intestatariPfTabContent1" role="tab" aria-controls="intestatariPfTabContent1" aria-selected="false">
									<strong>Intestazioni: Persone Fisiche</strong>
								</a>
				  			</li>
							<li class="nav-item" data-info="Intestatari Soggetti Giuridici">
				    			<a class="nav-link" id="piano-intestatari-sg-tab1" data-toggle="tab" href="#intestatariSgTabContent1" role="tab" aria-controls="intestatariSgTabContent1" aria-selected="false">
									<strong>Intestazioni: Soggetti Giuridici</strong>
								</a>
				  			</li>
						</ul>
	
						<!-- TAB CONTENT PARTICELLE TERRENO -->
						<div class="tab-content" id="dettaglioTabContent">
				  			<div class="tab-pane active" id="pianoPtTabContent1" role="tabpanel" aria-labelledby="piano-pt-tab1">
								<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
									<div class="table-responsive">
										<table class="table table-striped" id="tabellaRisultatiPT" style="width:100%">
											<thead>
										    	<tr>
													<th></th>
										      		<th>Comune</th>
										      		<th>Provincia</th>
										      		<th>Foglio</th>
										      		<th>Numero</th>
										      		<th>Sub.</th>
										      		<th>Partita</th>
													<th>Classe</th>
										      		<th>Qualita</th>
										      		<th data-info="Ettari">ha</th>
										      		<th data-info="Are">are</th>
										      		<th data-info="Centiare">ca</th>
										      		<th>Superficie (mq)</th>
													<th>Reddito dominicale (lire)</th>
										      		<th>Reddito dominicale (euro)</th>
													<th>Reddito agrario (lire)</th>
													<th>Reddito agrario (euro)</th>
										    	</tr>
										  	</thead>
										  	<tbody></tbody>
											<tfoot>
								            	<tr>
													<th></th>
										      		<th>Comune</th>
										      		<th>Provincia</th>
										      		<th>Foglio</th>
										      		<th>Numero</th>
										      		<th>Sub.</th>
										      		<th>Partita</th>
													<th>Classe</th>
										      		<th>Qualita</th>
										      		<th data-info="Ettari">ha</th>
										      		<th data-info="Are">are</th>
										      		<th data-info="Centiare">ca</th>
										      		<th>Superficie (mq)</th>
													<th>Reddito dominicale (lire)</th>
													<th>Reddito agrario (lire)</th>
										      		<th>Reddito dominicale (euro)</th>
													<th>Reddito agrario (euro)</th>
										    	</tr>
								        	</tfoot>
										</table>
									</div>					
								</div>
							</div>
	
							<!-- TAB CONTENT UNITA IMMOBILIARI -->
				  			<div class="tab-pane" id="pianoUiuTabContent1" role="tabpanel" aria-labelledby="piano-uiu-tab1">
								<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
									<div class="table-responsive">
										<table class="table table-striped" id="tabellaRisultatiUIU" style="width:100%">
											<thead>
										    	<tr>
													<th></th>
										      		<th>Comune</th>
										      		<th>Zona</th>
										      		<th>Categoria</th>
										      		<th>Classe</th>
										      		<th>Indirizzo</th>
										      		<th>Civico</th>
										      		<th>Piano</th>
													<th>Sez. Urbana</th>
										      		<th>Foglio</th>
										      		<th>Numero</th>
										      		<th>Subalterno</th>
										      		<th>Consistenza</th>
										      		<th>Superficie (mq)</th>
													<th>Rendita (lire)</th>
										      		<th>Rendita (euro)</th>
										      		<th>Partita</th>
										    	</tr>
										  	</thead>
										  	<tbody></tbody>
											<tfoot>
								            	<tr>
													<th></th>
										      		<th>Comune</th>
										      		<th>Zona</th>
										      		<th>Categoria</th>
										      		<th>Classe</th>
										      		<th>Indirizzo</th>
										      		<th>Civico</th>
										      		<th>Piano</th>
													<th>Sez. Urbana</th>
										      		<th>Foglio</th>
										      		<th>Numero</th>
										      		<th>Subalterno</th>
										      		<th>Consistenza</th>
										      		<th>Superficie (mq)</th>
													<th>Rendita (lire)</th>
										      		<th>Rendita (euro)</th>
										      		<th>Partita</th>
										    	</tr>
								        	</tfoot>
										</table>
									</div>
								</div>				
							</div>
	
							<!-- TAB CONTENT INTESTAZIONI PERSONE FISICHE -->
				  			<div class="tab-pane" id="intestatariPfTabContent1" role="tabpanel" aria-labelledby="piano-intestatari-pf-tab1">
								<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
									<div class="table-responsive">
										<table class="table table-striped" id="tabellaRisultatiPersoneFisiche" style="width:100%">
											<thead>
										    	<tr>
										      		<th>Nominativo</th>
										      		<th>Sesso</th>
										      		<th>Codice fiscale</th>
										      		<th>Comune</th>
										      		<th>Provincia</th>
										      		<th>Data di nascita</th>
										      		<th>Note</th>
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
										    	</tr>
								        	</tfoot>
										</table>
									</div>
								</div>				
							</div>
	
							<!-- TAB CONTENT INTESTAZIONI SOGGETTI GIURIDICI -->
							<div class="tab-pane" id="intestatariSgTabContent1" role="tabpanel" aria-labelledby="piano-intestatari-sg-tab1">
								<div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;">
									<div class="table-responsive">
										<table class="table table-striped" id="tabellaRisultatiSoggettiGiuridici" style="width:100%">
											<thead>
										    	<tr>
										      		<th>Denominazione</th>
										      		<th>Codice fiscale</th>
										      		<th>Comune</th>
										      		<th>Provincia</th>
										    	</tr>
										  	</thead>
										  	<tbody></tbody>
											<tfoot>
								            	<tr>
										      		<th>Denominazione</th>
										      		<th>Codice fiscale</th>
										      		<th>Comune</th>
										      		<th>Provincia</th>
										    	</tr>
								        	</tfoot>
										</table>
									</div>
								</div>				
							</div>
						</div>
					</div>
				</div>				
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a  id="pdfExport" href="#"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Esporta Piano in PDF</a></li>
					<li><a  id="xlsExport" href="#"><em class="fa fa-file-excel-o"></em>&nbsp;&nbsp;Esporta Piano in Excel</a></li>
					<li><a  id="shapeExport" href="#"><em class="fa fa-file-pdf-o"></em>&nbsp;&nbsp;Esporta Piano in Shape File</a></li>
					<li><a  id="chiudiBtnParticelle" href="#"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>
	
</div>
</script>

<script id="modPianoParticellareImpostazioni" type="text/x-handlebars-template">
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
					<li><a href="#">Cartografia</a></li>
					<li class="active"><a href="#">Impostazioni Piano Particellare</a></li>
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
								    	<label for="prova" class="col-sm-12 col-md-3 control-label">Ignora se</label>
								    	<div class="col-sm-12 col-md-9">
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

<script id="modScegliModalitaPianoParticellare" type="text/x-handlebars-template">
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
			<div class="col-sm-12">
				<h3 class="decorated"><span>Modalita' di acquisizione piano particellare</span></h3>
			</div>
			<div id="pianoParticellare" class="search row nom nop">
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
				<div class="col-sm-12 col-md-3">
					<div class="panel panel-default">
						<div class="panel-heading">Seleziona da Layer</div>
				   		<div class="panel-body text-center">
							<div class="hovereffect">
								<img src="./images/catasto/ricerca.png" class="img-responsive img-fluid" alt="Seleziona da Layer">
				        		<div class="overlay-catasto">
				           			<h2>Seleziona Layer</h2>
				           			<a class="info bttn btn-trasp" href="#" id="taglioParticellareBtn">Inizia</a>
				        		</div>
				    		</div>
						</div>
				    </div>
				</div>
				<div class="col-sm-12 col-md-3">
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
			<div class="absPulsante">
				<ul class="noStyle inline pulsanti-ricerca">
					<li><a href="#" id="chiudiBtn" >Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>	
</div>
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
					<li><a data-info="Avvia estrazione" href="#" id="estraiBtn"><em class="fa fa-check"></em>&nbsp;&nbsp;Estrai</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnQ"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
