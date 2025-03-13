<!----------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- IMPORT SHAPE FILE --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestioneImport" type="text/x-handlebars-template">
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
					<li><a href="#">Gestione Import</a></li>
					<li class="active"><a href="#">Importa Dati</a></li>
				</ul>
			</div>
			<div>
				<!-- STEPPER -->
				<div class="wizard">
					<div class="wizard-inner">
						<div class="connecting-line"></div>
						<ul class="nav nav-tabs" role="tablist">
							<!-- STEP SHAPE FILES -->
							<li role="presentation" class="active">
								<a href="#stepUploadShapeDiv" id="stepUploadShape" data-toggle="tab" aria-controls="stepUploadShape" role="tab" data-info="Step scelta shape file">
									<span class="round-tab">
										<i class="fa fa-folder-open"></i> SHAPE FILE
									</span>
								</a>
							</li>
						</ul>
					</div>
					<form id="upload" role="form">
						<div class="tab-content">
							<div class="tab-pane" role="tabpanel" id="stepUploadShapeDiv">
								<!-- UPLOADER SHAPE FILE -->
								<div id="importaDatiShape" class="search">
									<div class="col-md-12" style="margin-top: 20px">
										<div>
											<input type="file" name="file" id="uploadShapeInput" accept="application/zip">
											<div class="row" style="padding-top: 0px; padding-bottom: 20px;">
												<div class="col-md-5"></div>
												<div class="col-md-2">
													<input id="avviaUploadShapeButton" type="button" class="btn-custom green" value="Avvia upload"></div>
												<div class="col-md-5"></div>
											</div>
										</div>
									</div>
								</div>
								<!-- TABELLA RISULTATI SHAPE -->
								<div class="table-responsive">
									<table class="table table-striped" id="tabellaShapeFile" style="width: 100%;">
										<thead>
									    	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Data upload</th>
												<th></th>
									    	</tr>
									  	</thead>
									  	<tbody></tbody>
										<tfoot>
							            	<tr>
									      		<th>Nome file</th>
									      		<th>Dimensione</th>
									      		<th>Data upload</th>
												<th></th>
									    	</tr>
							        	</tfoot>
									</table>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="absPulsante">
				<ul id="pulsante-ricerca" class="noStyle inline pulsante-ricerca">
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnImport"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</script>
<!-------------------------------------------------UPLOADER----------------------------------------------------------------------->
<script id="dragdropUploader" type="text/x-handlebars-template">
	<div class="jFiler-input-dragDrop">
		<div class="jFiler-input-inner">
			<div class="jFiler-input-icon">
				<i class="icon-jfi-cloud-up-o"></i>
			</div>
			<div class="jFiler-input-text">
				<h3>Trascina e rilascia i file qui</h3>
				<span style="display:inline-block; margin: 15px 0">oppure</span>
			</div>
			<a class="jFiler-input-choose-btn messina">Scegli i File</a>
		</div>
	</div>
</script>
<!-- --------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- TOPONIMO STRADALE --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestioneToponimi" type="text/x-handlebars-template">
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
					<li><a href="#">Toponomastica</a></li>
					<li class="active"><a href="#">Toponimo Stradale</a></li>
				</ul>
			</div>
			<div id="ricercaAnagToponimo" class="">
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
							<form class="form-horizontal" id="ricerca">
								<div class="panel-body row nom">
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Classe</label>
										<input id="dug" type="text" class="form-control" name="dug" placeholder="Classe...">   
									</div>
									<div class="col-sm-12 col-md-6 col-lg-6">
										<label>Denominazione ufficiale</label>
										<div class="input-group">
											<input id="descrizioneToponimoStradale" type="text" class="form-control" name="descrizione" placeholder="Denominazione ufficiale...">
			    						</div>
									</div>
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Numero delibera</label>
										<input id="numeroDelibera" type="text" class="form-control" name="numeroDelibera" placeholder="Numero delibera...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Numero provvedimento</label>
										<input id="codiceAutorizzazione" type="text" class="form-control" name="codiceAutorizzazione" placeholder="Numero provvedimento...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-3">
										<label>Data delibera dal</label>
										<div class="input-group">
											<input id="dataDeliberaToponimoDal" type="text" class="form-control" name="dataDeliberaToponimoDal" placeholder="Data delibera dal...">
											<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
										</div>
									</div>
									<div class="col-sm-12 col-md-6 col-lg-3">
										<label>Data delibera al</label>
										<div class="input-group">
											<input id="dataDeliberaToponimoAl" type="text" class="form-control" name="dataDeliberaToponimoAl" placeholder="Data delibera al...">
											<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
										</div>
									</div>
									<div class="col-sm-12 col-md-6 col-lg-3">
										<label>Data autorizzazione dal</label>
										<div class="input-group">
											<input id="dataAutorizzazioneToponimoDal" type="text" class="form-control" name="dataAutorizzazioneToponimoDal" placeholder="Data autorizzazione dal...">
											<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
										</div>	
									</div>
									<div class="col-sm-12 col-md-6 col-lg-3">
										<label>Data autorizzazione al</label>
										<div class="input-group">
											<input id="dataAutorizzazioneToponimoAl" type="text" class="form-control" name="dataAutorizzazioneToponimoAl" placeholder="Data autorizzazione al...">
											<div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
										</div>	
									</div>
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Stato</label>
										<div class="input-group">
											<select class="fa form-control" id="stato">
												<option value="">Seleziona...</option>
												<option value="BOZZA">BOZZA</option>
												<option value="PUBBLICATO">PUBBLICATO</option>
												<option value="ARCHIVIATO">ARCHIVIATO</option>
											</select>   
			    						</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaToponimoStradale" style="width:100%">
						<thead>
							<tr>
								<th>Classe</th>
								<th>Denominazione Ufficiale</th>
								<th>Comune</th>
								<th>CAP</th>
								<th>Tipo</th>
								<th>Lunghezza</th>
								<th>Codice Toponimo</th>
								<th>Numero delibera</th>
								<th>Data delibera</th>
								<th>Numero provvedimento</th>
								<th>Data autorizzazione</th>
								<th>Stato</th>
								<th></th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>Classe</th>
								<th>Denominazione Ufficiale</th>
								<th>Comune</th>
								<th>CAP</th>
								<th>Tipo</th>
								<th>Lunghezza (m)</th>
								<th>Codice Toponimo</th>
								<th>Numero delibera</th>
								<th>Data delibera</th>
								<th>Numero provvedimento</th>
								<th>Data autorizzazione</th>
								<th>Stato</th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="dettaglioAnagToponimo" class="col-md-12 tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li id="liDetAnagraficaTopo" class="nav-item" data-info="Anagrafica">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica</strong>
							</a>
			  			</li>
						<li id="liDetDocumentiTopo" class="nav-item" data-info="Documenti">
			    			<a class="nav-link dettaglioTab" id="documenti-tab" data-toggle="tab" href="#documentiTabContent1" role="tab" aria-controls="documentiTabContent1" aria-selected="true">
								<strong>Documenti</strong>
							</a>
			  			</li>
						<li id="liDetAccessiTopo" class="nav-item" data-info="Accessi">
			    			<a class="nav-link dettaglioTab" id="accessi-tab" data-toggle="tab" href="#accessiTabContent1" role="tab" aria-controls="accessiTabContent1" aria-selected="true">
								<strong>Accessi</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
							<form class="form-horizontal" id="dettaglio">
								
							</form>					
						</div>
						<div class="tab-pane dettaglioTabContent" id="documentiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button id="documentoBtnToponimo" type="button" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci documento</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaDocumentiToponimo" style="width:100%">
								<thead>
									<tr>
										<th>Nome documento</th>
										<th>Data upload</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Nome documento</th>
										<th>Data upload</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
						<div class="tab-pane dettaglioTabContent" id="accessiTabContent1" role="tabpanel" aria-labelledby="documenti-tab2">
							<div class="btn-group">
								<button id="accessoBtnToponimo" type="button" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci accesso</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaAccessiToponimo" style="width:100%">
								<thead>
									<tr>
										<th>Localit&agrave;</th>
										<th>Numero</th>
										<th>Esponente</th>
										<th>Tipo</th>
										<th>Passo Carrabile</th>
										<th>Principale</th>
										<th>Note</th>											
					      				<th></th>
					    			</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Localit&agrave;</th>
										<th>Numero</th>
										<th>Esponente</th>
										<th>Tipo</th>
										<th>Passo Carrabile</th>
										<th>Principale</th>
										<th>Note</th>										
					      				<th></th>
					    			</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div id="buttonToponimi" class="absPulsante">
				<ul id="pulsante-ricerca" class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnToponimo"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li id="exportLiToponimo"><a data-info="Export shape file" href="#" id="exportBtnToponimo"><em class="fa fa-upload">&nbsp;&nbsp;Export</em></a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtnToponimo"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea un nuovo Toponimo Stradale" href="#" id="nuovoBtnToponimo"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnToponimo"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul id ="pulsante-inserimento" class="noStyle inline pulsante-inserimento">
					<li><a data-info="Salva" href="#" id="salvaBtnToponimo"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
				<ul id="pulsante-dettaglio" class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Salva" href="#" id="aggiornaBtnToponimo"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleDettaglioToponimi" type="text/x-handlebars-template">
<div class="row nom nop">
	<div class="form-group col-md-2 col-lg-2">
    	<label for="classeToponimo" class="control-label">Classe *</label>
    	<div class="input-group">
    		<input type="text" class="form-control" id="classeToponimoM" placeholder="Classe..." value="{{classe}}" {{readonly}} data-dug="{{classe}}">
			<input type="hidden" id="classeToponimoMValue">
		</div>
	</div>
	<div class="form-group col-md-2 col-lg-2">
    	<label for="denominazione" class="control-label">Denominazione *</label>
    	<div class="input-group">
    		<input type="text" class="form-control" id="denominazione" placeholder="Denominazione..." value="{{denominazione}}" {{readonly}} data-denominazione="{{denominazione}}">
		</div>
    </div>
	<div class="form-group col-md-3 col-lg-4">
    	<label for="denominazioneUfficiale" class="control-label">Denominazione Ufficiale</label>
    	<div class="input-group">
    		<input type="text" class="form-control" id="denominazioneUfficiale" placeholder="Denominazione Ufficiale..." value="{{denominazioneUfficiale}}" {{readonly}} disabled>
		</div>
    </div>
	<div class="form-group col-md-4 col-lg-4">
    	<label for="comuneToponimo" class="control-label">Comune *</label>
    	<div class="input-group">
    		<select class="fa form-control" id="comuneToponimoM" {{disabled}}>
				<option value="">Seleziona...</option>
				{{#each comuni}}
					<option value="{{this.id}}">{{this.descrizione}}</option>
				{{/each}}
			</select>
		</div>
    </div>
	<div class="form-group col-md-3">
    	<label for="tipoToponimo" class="control-label">Tipo</label>
    	<div class="input-group">
    		<select class="fa form-control" id="tipoToponimoM" {{disabled}}>
				<option value="">Seleziona...</option>
				{{#each tipi}}
					<option value="{{this.id}}">{{this.descrizione}}</option>
				{{/each}}
			</select>
		</div>
    </div>
	<div class="form-group col-md-1">
    	<label for="codiceToponimo" class="control-label">Codice</label>
    	<div class="input-group">
    		<input type="text" class="form-control" id="codiceToponimoM" placeholder="Codice..." value="{{codice}}" {{readonly}}>
		</div>
	</div>
	<div class="form-group col-md-2">
		<label for="dataDeliberaToponimoM" class="control-label">Data delibera</label>
		<div class="input-group">
			<input type="text" class="form-control" id="dataDeliberaToponimoM" placeholder="Data delibera..." value="{{dataDelibera}}" {{readonly}}>
		</div>
	</div>
	<div class="form-group col-md-2">
		<label for="numeroDeliberaToponimoM" class="control-label">Numero delibera</label>
		<div class="input-group">
			<input type="text" class="form-control" id="numeroDeliberaToponimoM" placeholder="Numero delibera..." value="{{numeroDelibera}}" {{readonly}}>
		</div>
	</div>
	<div class="form-group col-md-2">
		<label for="dataAutorizzazioneToponimoM" class="control-label">Data autorizzazione</label>
		<div class="input-group">
			<input type="text" class="form-control" id="dataAutorizzazioneToponimoM" placeholder="Data autorizzazione..." value="{{dataAutorizzazione}}" {{readonly}}>
		</div>
	</div>
	<div class="form-group col-md-2">
		<label for="codiceAutorizzazioneToponimoM" class="control-label">Numero provvedimento</label>
		<div class="input-group">
			<input type="text" class="form-control" id="codiceAutorizzazioneToponimoM" placeholder="Numero provvedimento..." value="{{codiceAutorizzazione}}" {{readonly}}>
		</div>
	</div>
	<div class="form-group col-md-4">
		<label for="capToponimoM" class="control-label">CAP</label>
		<div class="input-group">
			<input type="text" class="form-control" id="capToponimoM" placeholder="CAP..." value="{{cap}}" {{readonly}}>
		</div>
	</div>
	<div class="form-group col-md-8">
		<label for="compendiToponimoM" class="control-label">Compendi</label>
		<div class="input-group">
			<textarea class="form-control" id="compendiToponimoM" rows="3" placeholder="Compendi..." {{readonly}}>{{compendi}}</textarea>
			<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
		</div>
	</div>
	<div class="form-group col-md-4" {{hidden}}>
        <label class="control-label">Georeferenziazione</label>
        <div class="input-group" style="top: 11px">
			<div class="btn-group" role="group" aria-label="Azioni" style="margin-right: 20px;" data-info="Editing Georeferenziazione">
					<button type="button" class="bttn btn-trasp editing-toponimo-btn"><em class="fa fa-globe"></em></button>
			</div> 
			{{#if geom}}
				<div class="btn-group" role="group" aria-label="Azioni" data-info="Localizza">
					<button type="button" class="bttn btn-trasp localizza-geo-toponimo-btn"><em class="fa fa-map-marker"></em></button>
				</div>
			{{/if}}   
        </div>
    </div>
	<!-- NOTE -->
	<div class="form-group col-md-12">
  		<label for="noteToponimo" class="control-label">Annotazioni</label>
   		<div class="input-group">
			<textarea class="form-control" id="noteToponimo" rows="5" placeholder="Note..." {{readonly}}>{{note}}</textarea>
			<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
		</div>
	</div>
	
	<div class="form-group col-md-12">
		<div>
			<div style="float:left;width: auto;margin-bottom: 10px;"><label class="control-label">Nuovi Toponimi Stradali</label></div>
			<div id="pubblicaFigliToponimo" style="float: right;width: auto;text-align: right;display:none;">
				<button type="button" class="bttn btn-trasp formalizes-toponimo-figli-btn" title="Ufficializza i nuovi toponimi stradali">
					<em class="fa fa-book"><span style="padding-left:10px">Ufficializza i nuovi toponimi stradali</span></em>
				</button>
			</div>
		</div>
		<table class="table table-striped" id="tabellaPercorso" style="width:100%">
			<thead>
				<tr>
					<th>Classe</th>
					<th>Denominazione Ufficiale</th>
					<th>Comune</th>
					<th>Numero delibera</th>
					<th>Data delibera</th>
					<th>Numero provvedimento</th>
					<th>Data autorizzazione</th>
					<th>Stato</th>
				</tr>
			</thead>
			<tbody id="figliToponimoTable">
				<tr class="odd"><td valign="top" colspan="6" class="dataTables_empty">Nessun nuovo toponimo</td></tr>
			</tbody>
		</table>
	</div>  
</div>
</script>

<!-- FIGLI DI UN TOPONIMO -->
<script id="figliToponimo" type="text/x-handlebars-template">
{{#figli}}
	<tr>
		<td>{{classeLabel}}</td>
		<td>{{denominazioneUfficiale}}</td>
		<td>{{comuneLabel}}</td>
		<td>{{numeroDelibera}}</td>
		<td>{{dateFormatter dataDelibera}}</td>
		<td>{{codiceAutorizzazione}}</td>
		<td>{{dateFormatter dataAutorizzazione}}</td>
		<td>{{stato}}</td>
	</tr>
{{/figli}}
</script>
<!-- MODALE UPLOAD DOCUMENTI -->
<script id="modaleUploadDocumentiToponimo" type="text/x-handlebars-template">
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
			<form class="form-horizontal" id="uploadForm">
				<div class="row nom nop">
					<div class="form-group col-md-12">
						<label class="control-label">File</label>
						<div class="input-group">
							<input type="file" id="file" class="form-control-file" name="file">
						</div>
					</div>
			  	</div>
			</form>	
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-upload">
					<li><a data-info="Salva" href="#" id="uploadDocumentoBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<!-- MODALE ANAGRAFICA ACCESSI TOPONIMO -->
<script id="modaleAnagraficaAccessi" type="text/x-handlebars-template">
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
				<form class="form-horizontal" id="dettaglio">
					<!-- DETTAGLIO -->
				</form>	
				<div class="absPulsante">
					<ul class="noStyle inline pulsante-accessi">
						<li><a data-info="Salva" href="#" id="salvaAccessiBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
						<li><a data-info="Annulla" href="#" id="chiudiBtn"> <em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
					</ul>
				</div>
			</div>			
		</div>
	</div>
</script>
<!--------------------------- TOPONIMO STRADALE --------------------------------------------------------------------------	FINE --->
<!-- ------------------------------------------------------------------------------------------------------------------------------->

<!-- ------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- PERCORSI --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestionePercorsi" type="text/x-handlebars-template">
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
					<li><a href="#">Toponimastica</a></li>
					<li class="active"><a href="#">Percorso</a></li>
				</ul>
			</div>
			<div id="ricercaAnagPercorsi" class="">
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
							<form class="form-horizontal" id="ricerca">
								<div class="panel-body row nom">
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Sigla</label>
										<input id="siglaPercorso" type="text" class="form-control" name="sigla" placeholder="Descrizione...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Descrizione</label>
										<input id="descrizionePercorso" type="text" class="form-control" name="descrizione" placeholder="Descrizione...">
									</div>
									<div class="col-sm-2 col-md-2 col-lg-2">
										<label>Estesa Amministrativa</label>
										<input type="radio" id="estesaAmministrativaPercorso" name="scelta" value="EA" style="width: 15px; position: relative; top: 8px; left: 20%">
									</div>
									<div class="col-sm-2 col-md-2 col-lg-2">
										<label>Toponimo Stradale</label>
										<input type="radio" id="toponimoStradalePercorso" name="scelta" value="TS" style="width: 15px; position: relative; top: 8px; left: 20%">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Stato</label>
										<div class="input-group">
											<select class="fa form-control" id="statoPercorso">
												<option value="">Seleziona...</option>
												<option value="BOZZA">BOZZA</option>
												<option value="COMPLETATO">COMPLETATO</option>
												<option value="PUBBLICATO">PUBBLICATO</option>
											</select>   
			    						</div>
									</div>
								</div>
							</form>	
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaPercorso" style="width:100%">
						<thead>
							<tr>
								<th>Sigla</th>
								<th>Descrizione</th>
								<th>Estesa Amministrativa</th>
								<th>Toponimo Stradale</th>
								<th>Stato</th>
								<th>Annotazioni</th>
								<th></th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>Sigla</th>
								<th>Descrizione</th>
								<th>Estesa Amministrativa</th>
								<th>Toponimo Stradale</th>
								<th>Stato</th>
								<th>Annotazioni</th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="dettaglioAnagPercorso" class="col-md-12 tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li id="liDetAnagraficaPercorso" class="nav-item" data-info="Anagrafica">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica</strong>
							</a>
			  			</li>
						<li id="liDetDocumentoPercorso" class="nav-item" data-info="Documenti">
			    			<a class="nav-link" id="documenti-tab" data-toggle="tab" href="#documentiTabContent1" role="tab" aria-controls="documentiTabContent1" aria-selected="true">
								<strong>Documenti</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
							<form class="form-horizontal" id="dettaglio">
								<!-- DETTAGLIO ANAGRAFICA PERCORSO -->
							</form>								
						</div>
						<div class="tab-pane" id="documentiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button type="button" id="documentoBtnPercorso" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci documento</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaDocumentiPercorsi" style="width:100%">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Path</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Nome</th>
										<th>Percorso</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div id="buttonToponimi" class="absPulsante">
				<ul id="pulsante-ricerca" class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnPercorso"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtnPercorso"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea un nuovo percorso" href="#" id="nuovoBtnPercorso"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnPercorso"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul id ="pulsante-inserimento" class="noStyle inline pulsante-inserimento">
					<li><a data-info="Salva" href="#" id="salvaBtnPercorso"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
				<ul id="pulsante-dettaglio" class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Salva" href="#" id="aggiornaBtnPercorso"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleDettaglioPercorso" type="text/x-handlebars-template">
	<div class="row nom nop">
		<div class="form-group col-md-4">
			<label>Sigla</label>
			<div class="input-group">
				<input type="hidden" id="idPercorso">
				<input type="text" class="form-control" id="siglaPercorsoM" placeholder="Sigla..." value="{{sigla}}" {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label>Descrizione</label>
			<div class="input-group">
				<input type="text" class="form-control" id="descrizionePercorsoM" placeholder="Descrizione..." value="{{descrizione}}" {{readonly}}>
			</div>
		</div>
		<div class="col-sm-2 col-md-2 col-lg-2">
			<label>Estesa Amministrativa</label>
			<input type="radio" id="estesaAmministrativaPercorsoChecked" name="scelta" value="EA" style="display: block; margin-top: 5%; margin-left: 20%" {{checkedEstesa}} {{disabled}}>
		</div>
		<div class="col-sm-2 col-md-2 col-lg-2">
			<label>Toponimo Stradale</label>
			<input type="radio" id="toponimoStradalePercorsoChecked" name="scelta" value="TS" style="display: block; margin-top: 5%; margin-left: 20%" {{checkedToponimo}} {{disabled}}>
		</div>
		<div class="form-group col-md-4">
			<label>Lato</label>
			<div class="input-group">
				<input type="text" class="form-control" id="latoM" placeholder="Lato..." value="{{lato}}" {{readonly}}>
			</div>
		   </div>
		<div class="form-group col-md-4">
			<label id="titoloInput">Estesa Amministrativa</label>
			<div class="input-group">
				<input type="text" class="form-control" id="estesaAmministrativaM" placeholder="Estesa Amministrativa..." value="{{estesaAmministrativa}}" {{readonly}}>
				<input type="hidden" id="estesaAmministrativaMValue">
			</div>
		</div>
		<div class="form-group col-md-4">
			<label id="titoloInput">Toponimo Stradale</label>
			<input type="text" class="form-control" id="toponimoStradaleM" placeholder="Toponimo Stradale..." value="{{toponimo}}" {{readonly}}>
			<input type="hidden" id="toponimoStradaleMValue">
		</div>
		<div class="form-group col-md-4" {{hidden}}>
			<label class="control-label">Georeferenziazione *</label>
			<div class="input-group" style="top: 11px">
				<div class="btn-group" role="group" aria-label="Azioni" style="margin-right: 20px;" data-info="Editing Georeferenziazione">
					   <button type="button" data-id="1" class="bttn btn-trasp editing-geom-percorso-btn"><em class="fa fa-globe"></em></button>
				</div>
				{{#if geom}}
				<div class="btn-group" role="group" aria-label="Azioni" data-info="Localizza">
					<button type="button" data-id="1" class="bttn btn-trasp localizza-geom-percorso-btn"><em class="fa fa-map-marker"></em></button>
				</div>   
				{{/if}}
			</div>
		</div>
	  <!-- NOTE -->
	  <div class="form-group col-md-12">
		<label for="notePercorso" class="col-sm-12 col-md-1 control-label">Annotazioni</label>
		<div class="input-group">
			<textarea class="form-control" id="notePercorso" rows="5" placeholder="Note..." value="{{note}}" {{readonly}}></textarea>
			<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
		</div>
	</div>
</script>
<!-- MODALE UPLOAD DOCUMENTI -->
<script id="modaleUploadDocumentiPercorso" type="text/x-handlebars-template">
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
				<form class="form-horizontal" id="uploadForm">
					<div class="row nom nop">
						<div class="form-group col-md-12">
							<label class="control-label">File</label>
							<div class="input-group">
								<input type="file" id="file" class="form-control-file" name="file">
							</div>
						</div>
					  </div>
				</form>	
				<div class="absPulsante">
					<ul class="noStyle inline pulsante-upload">
						<li><a data-info="Salva" href="#" id="uploadDocumentoBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					</ul>
				</div>
			</div>			
		</div>
	</div>
	</script>
<!--------------------------- PERCORSI --------------------------------------------------------------------------	FINE --->
<!-- ----------------------------------------------------------------------------------------------------------------------->

<!-- ------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- LOCALITA' --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestioneLocalita" type="text/x-handlebars-template">
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
					<li><a href="#">Toponomastica</a></li>
					<li class="active"><a href="#">Localit&agrave;</a></li>
				</ul>
			</div>
			<div id="ricercaAnagLocalita" class="">
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
							<form class="form-horizontal" id="ricerca">
								<div class="panel-body row nom">
									<div class="col-sm-12 col-md-6 col-lg-6">
										<label>Descrizione</label>
										<input id="descrizioneLocalita" type="text" class="form-control" name="descrizioneLocalita" placeholder="Descrizione..."> 
									</div>
									<div class="col-sm-12 col-md-6 col-lg-6">
										<label>Frazione</label>
										<input id="frazioneLocalita" type="text" class="form-control" name="frazioneLocalita" placeholder="Frazione..."> 
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaLocalita" style="width:100%">
						<thead>
					    	<tr>
					      		<th>Descrizione</th>
					      		<th>Comune</th>
								<th>Frazione</th>
								<th>Tipo</th>
								<th>Stato</th>  
					      		<th></th>
					    	</tr>
					  	</thead>
					  	<tbody></tbody>
						<tfoot>
			            	<tr>
					      		<th>Descrizione</th>
					      		<th>Comune</th>
								<th>Frazione</th>
								<th>Tipo</th>
								<th>Stato</th>  
					      		<th></th>
					    	</tr>
			        	</tfoot>
					</table>
				</div>
			</div>
			<div  id="dettaglioAnagLocalita" class="col-md-12 tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li id="liDetAnagraficaLocalita" class="nav-item" data-info="Anagrafica">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica</strong>
							</a>
			  			</li>
						<li id="liDetDocumentoLocalita" class="nav-item" data-info="Documenti">
			    			<a class="nav-link" id="documenti-tab" data-toggle="tab" href="#documentiTabContent1" role="tab" aria-controls="documentiTabContent1" aria-selected="true">
								<strong>Documenti</strong>
							</a>
			  			</li>
						<li id="liDetAccessoLocalita" class="nav-item" data-info="Accessi">
			    			<a class="nav-link" id="accessi-tab" data-toggle="tab" href="#accessiTabContent1" role="tab" aria-controls="accessiTabContent1" aria-selected="true">
								<strong>Accessi</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
							<!--div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;"-->
								<form class="form-horizontal" id="dettaglio">
									<!-- DETTAGLIO -->										
								</form>					
							<!--/div-->			
						</div>
						<div class="tab-pane" id="documentiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button type="button" id="documentoLocalitaBtn" class="btn btn-primary" style="margin-bottom: 15px" >
		                			<span class="label">Inserisci documento</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaDocumentiLocalita" style="width:100%">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Path</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Nome</th>
										<th>Percorso</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
						<div class="tab-pane" id="accessiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button type="button"  id="accessoLocalitaBtn" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci accesso</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaAccessiLocalita" style="width:100%">
								<thead>
									<tr>
										<th>Toponimo Stradale</th>
										<th>Numero</th>
										<th>Esponente</th>
										<th>Tipo</th>
										<th>Passo Carrabile</th>
										<th>Principale</th>
										<th>Note</th>											
					      				<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Toponimo Stradale</th>
										<th>Numero</th>
										<th>Esponente</th>
										<th>Tipo</th>
										<th>Passo Carrabile</th>
										<th>Principale</th>
										<th>Note</th>											
					      				<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnLocalita"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtnLocalita"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea una nuova localit&agrave;" href="#" id="nuovoBtnLocalita"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnLocalita"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-inserimento">
					<li><a data-info="Salva" href="#" id="salvaBtnLocalita"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Salva" href="#" id="aggiornaBtnLocalita"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleDettaglioLocalita" type="text/x-handlebars-template">
	<div class="row nom nop">
		<div class="form-group col-md-4">
			<label for="descrizioneLocalita" class="control-label">Descrizione *</label>
			<div class="input-group">
				<input type="hidden" id="idLocalita">
				<input type="text" class="form-control" id="descrizioneLocalitaM" placeholder="Descrizione..." value="{{descrizione}}" {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label for="comuneLocalita" class="control-label">Comune *</label>
			<div class="input-group">
				<select class="fa form-control" id="comuneLocalitaM" {{disabled}}>
					<option value="">Seleziona...</option>
					{{#each comuni}}
						<option value="{{this.id}}">{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label for="frazioneLocalita" class="control-label">Frazione</label>
			<div class="input-group">
				<input type="text" class="form-control" id="frazioneLocalitaM" placeholder="Frazione..." value="{{frazione}}" {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label for="tipoLocalita" class="control-label">Tipo</label>
			<div class="input-group">
				<select class="fa form-control" id="tipoLocalitaM" {{disabled}}>
					<option value="" selected>Seleziona...</option>
					{{#each tipi}}
					<option value="{{this.id}}">{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		</div>
		<div class="form-group col-md-4" {{hidden}}>
			<label class="control-label">Georeferenziazione *</label>
			<div class="input-group" style="top: 11px">
				<div class="btn-group" role="group" aria-label="Azioni" style="margin-right: 20px;" data-info="Editing Georeferenziazione">
					<button type="button" data-id="1" class="bttn btn-trasp editing-geom-localita-btn"><em class="fa fa-globe"></em></button>
				</div> 
				{{#if geom}}
				<div class="btn-group" role="group" aria-label="Azioni" data-info="Localizza">
					<button type="button" data-id="1" class="bttn btn-trasp localizza-geom-localita-btn"><em class="fa fa-map-marker"></em></button>
				</div>
				{{/if}}   
			</div>
		</div>
	</div>
	<!-- NOTE -->
	<div class="form-group col-md-12">
		<label for="noteLocalita" class="col-sm-12 col-md-1 control-label">Annotazioni</label>
		<div class="input-group">
			<textarea class="form-control" id="noteLocalita" rows="5" placeholder="Note..." {{readonly}}>{{note}}</textarea>
			<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
		</div>
	</div>
</script>
<!-- MODALE UPLOAD DOCUMENTI LOCALITA' -->
<script id="modaleUploadDocumentiLocalita" type="text/x-handlebars-template">
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
			<form class="form-horizontal" id="uploadForm">
				<div class="row nom nop">
					<div class="form-group col-md-12">
						<label class="control-label">File</label>
						<div class="input-group">
							<input type="file" id="file" class="form-control-file" name="file">
						</div>
					</div>
			  	</div>
			</form>	
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-upload">
					<li><a data-info="Salva" href="#" id="uploadDocumentoBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleAnagraficaAccessiLocalita" type="text/x-handlebars-template">
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
			<form class="form-horizontal" id="dettaglio">
				<!-- DETTAGLIO -->
			</form>	
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-accessi">
					<li><a data-info="Salva" href="#" id="salvaAccessiBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="chiudiBtn"> <em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<!--------------------------- LOCALITA' --------------------------------------------------------------------------	FINE --->
<!-- ----------------------------------------------------------------------------------------------------------------------->

<!-- --------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- GIUNZIONI STRADALI --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestioneGiunzioni" type="text/x-handlebars-template">
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
					<li><a href="#">Toponomastica</a></li>
					<li class="active"><a href="#">Giunzioni Stradali</a></li>
				</ul>
			</div>
			<div id="ricercaAnagGiunzione" class="">
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
							<form class="form-horizontal" id="ricerca">
								<div class="panel-body row nom">
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Descrizione</label>
										<input type="text" class="form-control" id="descrizioneGiunzione" placeholder="Descrizione...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Tipo topologico</label>
										<div class="input-group">
											<select class="fa form-control" id="tipoTopologicoGiunzione">
												<option value="">Seleziona...</option>
												{{#each tipiTopologici}}
												<option value="{{this.id}}">{{this.descrizione}}</option>
												{{/each}}
											</select>
										</div>
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Tipo funzionale</label>
										<div class="input-group">
											<select class="fa form-control" id="tipoFunzionaleGiunzione">
												<option value="">Seleziona...</option>
												{{#each tipiFunzionali}}
												<option value="{{this.id}}">{{this.descrizione}}</option>
												{{/each}}
											</select>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaGiunzioni" style="width:100%">
						<thead>
							<tr>
								<th>Descrizione</th>
								<th>Tipo topologico</th>
								<th>Tipo funzionale</th>
								<th>Limite amministrativo</th>
								<th>Inizio strada</th>
								<th>Stato</th>
								<th></th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>Descrizione</th>
								<th>Tipo topologico</th>
								<th>Tipo funzionale</th>
								<th>Limite amministrativo</th>
								<th>Inizio strada</th>
								<th>Stato</th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div class="col-md-12 tabbable-panel" id="dettaglioAnagGiunzione">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li id="liDetAnagraficaGiunzione" class="nav-item" data-info="Anagrafica">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica</strong>
							</a>
			  			</li>
						<li id="liDetDocumentoGiunzione" class="nav-item" data-info="Documenti">
			    			<a class="nav-link" id="documenti-tab" data-toggle="tab" href="#documentiTabContent1" role="tab" aria-controls="documentiTabContent1" aria-selected="true">
								<strong>Documenti</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
							<form class="form-horizontal" id="dettaglio">
								<!-- DETTAGLIO -->									
							</form>					
						</div>
						<div class="tab-pane" id="documentiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button id="documentoBtnGiunzione" type="button" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci documento</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaDocumenti" style="width:100%">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Data</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Nome</th>
										<th>Data</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnGiunzioni"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea una giunzione stradale" href="#" id="nuovoBtnGiunzione"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnGiunzione"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-inserimento">
					<li><a data-info="Salva" href="#" id="salvaBtnGiunzione"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Salva" href="#" id="aggiornaBtnGiunzione"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleDettaglioGiunzione" type="text/x-handlebars-template">
	<div class="row nom nop">
		<div class="form-group col-md-4">
			<label for="descrizione" class="control-label">Descrizione</label>
			<div class="input-group">
				<input type="text" class="form-control" id="descrizioneGiunzioneM" value="{{descrizione}}" placeholder="Descrizione...">
			</div>
		</div>
		<div class="form-group col-md-3">
			<label for="tipoTopologicoGiunzione" class="control-label">Tipo topologico</label>
			<div class="input-group">
				<select class="fa form-control" id="tipoTopologicoGiunzioneM">
					<option value="">Seleziona...</option>
					{{#each tipiTopologici}}
					<option value="{{this.id}}">{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		</div>
		<div class="form-group col-md-3">
			<label for="tipoFunzionaleGiunzione" class="control-label">Tipo funzionale</label>
			<div class="input-group">
				<select class="fa form-control" id="tipoFunzionaleGiunzioneM">
					<option value="">Seleziona...</option>
					{{#each tipiFunzionali}}
					<option value="{{this.id}}">{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		</div>
		<div class="form-group col-md-1">
			<label class="control-label">Limite amministrativo</label>
			<div class="input-group">
				<input type="checkbox" id="limiteAmministrativoGiunzioneM" style="margin-left: 40%; margin-top: 15%;" {{limiteAmministrativo}}>
			</div>
		</div>
		<div class="form-group col-md-1">
			<label class="control-label">Inizio strada</label>
			<div class="input-group">
				<input type="checkbox" id="inizioFineStradaGiunzioneM" style="margin-left: 25%; margin-top: 15%;" {{inizioFineStrada}}>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label class="control-label">Georeferenziazione *</label>
			<div class="input-group" style="top: 11px">
				<div class="btn-group" role="group" aria-label="Azioni" style="margin-right: 20px;" data-info="Editing Georeferenziazione">
					<button type="button" data-id="1" class="bttn btn-trasp editing-geom-giunzione-btn"><em class="fa fa-globe"></em></button>
				</div> 
				{{#if geom}}
				<div class="btn-group" role="group" aria-label="Azioni" data-info="Localizza">
					<button type="button" data-id="1" class="bttn btn-trasp localizza-geom-giunzione-btn"><em class="fa fa-map-marker"></em></button>
				</div>
				{{/if}}
			</div>
		</div>
	</div>
	<!-- NOTE -->
	<div class="form-group col-md-12">
		<label for="noteGiunzioni" class="control-label">Annotazioni</label>
		<div class="input-group">
			<textarea class="form-control" id="noteGiunzione" rows="5" placeholder="Note...">{{note}}</textarea>
			<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
		</div>
	</div>
</script>
<!-- MODALE UPLOAD DOCUMENTI -->
<script id="modaleUploadDocumentiGiunzione" type="text/x-handlebars-template">
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
				<form class="form-horizontal" id="uploadForm">
					<div class="row nom nop">
						<div class="form-group col-md-12">
							<label class="control-label">File</label>
							<div class="input-group">
								<input type="file" id="file" class="form-control-file" name="file">
							</div>
						</div>
					  </div>
				</form>	
				<div class="absPulsante">
					<ul class="noStyle inline pulsante-upload">
						<li><a data-info="Salva" href="#" id="uploadDocumentoBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					</ul>
				</div>
			</div>			
		</div>
	</div>
</script>
<!--------------------------- GIUNZIONI STRADALI --------------------------------------------------------------------------	FINE --->
<!-- ------------------------------------------------------------------------------------------------------------------------------->

<!-- ------------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- ESTESA AMMINISTRATIVA --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestioneEsteseAmministrative" type="text/x-handlebars-template">
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
					<li><a href="#">Toponomastica</a></li>
					<li class="active"><a href="#">Estesa Amministrativa</a></li>
				</ul>
			</div>
			<div id="ricercaAnagEstesa" class="">
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
							<form class="form-horizontal" id="ricerca">
								<div class="panel-body row nom">
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Sigla</label>
										<input id="siglaEstesaAmministrativa" type="text" class="form-control" name="sigla" placeholder="Sigla...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Codice</label>
										<input id="codiceEstesaAmministrativa" type="text" class="form-control" name="codiceEstesaAmministrativa" placeholder="Codice...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Descrizione Estesa Amministrativa</label>
										<input id="descrizioneEstesaAmministrativa" type="text" class="form-control" name="descrizioneEstesaAmministrativa" placeholder="Descrizione...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Classif. Amministrativa</label>
										<div class="input-group">
											<select class="fa form-control" id="classificaAmmEstesaAmministrativa">
												<option value="">Seleziona...</option>
												{{#each classificaAmministrativa}}
												<option value="{{this.id}}">{{this.descrizione}}</option>
												{{/each}}
											</select>
										</div>
									</div>
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label>Classif. Funzionale</label>
										<div class="input-group">
											<select class="fa form-control" id="classificaFunzEstesaAmministrativa">
												<option value="">Seleziona...</option>
												{{#each classificaFunzionale}}
												<option value="{{this.id}}">{{this.descrizione}}</option>
												{{/each}}
											</select>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaEstese" style="width:100%">
						<thead>
							<tr>
								<th>Sigla</th>
								<th>Descrizione</th>
								<th>Classif. Amministrativa</th>
								<th>Classif. Funzionale</th>
								<th>Codice</th>
								<th>Estensione</th>
								<th>Tronco</th>
								<th>Provincia</th>
								<th>Comune</th>
								<th>Stato</th>
								<th></th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>Sigla</th>
								<th>Descrizione</th>
								<th>Classif. Amministrativa</th>
								<th>Classif. Funzionale</th>
								<th>Codice</th>
								<th>Estensione</th>
								<th>Tronco</th>
								<th>Provincia</th>
								<th>Comune</th>
								<th>Stato</th>
								<th></th>
							</tr>
						 </tfoot>
					</table>
				</div>
			</div>
			<div class="col-md-12 tabbable-panel" id="dettaglioAnagEstesa">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li id="liDetAnagraficaEstesa" class="nav-item" data-info="Anagrafica">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica</strong>
							</a>
			  			</li>
						<li id="liDetDocumentoEstesa" class="nav-item" data-info="Documenti">
			    			<a class="nav-link" id="documenti-tab" data-toggle="tab" href="#documentiTabContent1" role="tab" aria-controls="documentiTabContent1" aria-selected="true">
								<strong>Documenti</strong>
							</a>
			  			</li>
						<li id="liDetCippiEstesa" class="nav-item" data-info="Cippi">
			    			<a class="nav-link" id="cippi-tab" data-toggle="tab" href="#cippiTabContent1" role="tab" aria-controls="cippiTabContent1" aria-selected="true">
								<strong>Cippi</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
							<form class="form-horizontal" id="dettaglio">
								<!-- DETTAGLIO -->
							</form>								
						</div>
						<div class="tab-pane" id="documentiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button type="button" id="documentoBtnEstesa" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci documento</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaDocumentiEstesaAmministrativa" style="width:100%">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Path</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Nome</th>
										<th>Percorso</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
						<div class="tab-pane" id="cippiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button type="button" id="cippoBtnEstesa" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci Cippo</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaCippiEstesaAmministrativa" style="width:100%">
								<thead>
									<tr>
										<th>Codice</th>
										<th>Misura</th>
										<th>Note</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Codice</th>
										<th>Misura</th>
										<th>Note</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnEstesa"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtnEstesa"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea una nuova Estesa Amministrativa" href="#" id="nuovoBtnEstesa"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnEstesa"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-inserimento">
					<li><a data-info="Salva" href="#" id="salvaBtnEstesa"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Salva" href="#" id="aggiornaBtnEstesa"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleDettaglioEstesa" type="text/x-handlebars-template">
	<div class="row nom nop">
		<div class="form-group col-md-2">
			<label for="siglaEstesaAmministrativaM" class="control-label">Sigla</label>
			<div class="input-group">
				<input type="text" class="form-control" id="siglaEstesaAmministrativaM" placeholder="Sigla..." value="{{sigla}}" {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-6">
			<label for="descrizioneEstesaAmministrativaM" class="control-label">Descrizione</label>
			<div class="input-group">
				<input type="text" class="form-control" id="descrizioneEstesaAmministrativaM" placeholder="Descrizione..." value="{{descrizione}}" {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-2">
			<label for="provinciaEstesaAmministrativaM" class="control-label">Provincia *</label>
			<div class="input-group">
				<select class="fa form-control" id="provinciaEstesaAmministrativaM" {{disabled}}>
					<option value="">Seleziona...</option>
					{{#each provinceMessina}}
					<option value="{{this.id}}" selected>{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		   </div>
		<div class="form-group col-md-2">
			<label for="comuneEstesaAmministrativaM" class="control-label">Comune *</label>
			<div class="input-group">
				<select class="fa form-control" id="comuneEstesaAmministrativaM" {{disabled}}>
					<option value="">Seleziona...</option>
					{{#each comuniMessina}}
						<option value="{{this.id}}" selected>{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		</div>
		<div class="form-group col-md-1">
			<label for="codiceEstesaAmministrativaM" class="control-label">Codice</label>
			<div class="input-group">
				<input type="text" class="form-control" id="codiceEstesaAmministrativaM" placeholder="Codice..." value="{{codice}}" {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-1">
			<label for="troncoEstesaAmministrativaM" class="control-label">Tronco</label>
			<div class="input-group">
				<input type="text" class="form-control" id="troncoEstesaAmministrativaM" placeholder="Tronco..." value="{{tronco}}" {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-2">
			<div class="input-group">
				<label for="classificaEstesaAmministrativaM" class="control-label">Classif. Amministrativa</label>
				<div class="input-group">
					<select class="fa form-control" id="classificaEstesaAmministrativaM" {{disabled}}>
						<option value="">Seleziona...</option>
						{{#each classificheAmministrative}}
						<option value="{{this.id}}">{{this.descrizione}}</option>
						{{/each}}
					</select>
				</div>
			</div>
		</div>
		<div class="form-group col-md-2">
			<div class="input-group">
				<label for="classificaFunzionaleEstesaAmministrativaM" class="control-label">Classifica Funzionale</label>
				<div class="input-group">
					<select class="fa form-control" id="classificaFunzionaleEstesaAmministrativaM" {{disabled}}>
						<option value="">Seleziona...</option>
						{{#each classificheFunzionali}}
						<option value="{{this.id}}">{{this.descrizione}}</option>
						{{/each}}
					</select>
				</div>
			</div>
		</div>
		<div class="form-group col-md-2">
			<label for="estensioneEstesaAmministrativaM" class="control-label">Estensione</label>
			<div class="input-group">
				<input type="text" class="form-control" id="estensioneEstesaAmministrativaM" placeholder="Estensione..." value="{{estensione}}" {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-2">
			<div class="input-group">
				<label for="patrimonialitaEstesaAmministrativaM" class="control-label">Patrimonialit&agrave;</label>
				<div class="input-group">
					<select class="fa form-control" id="patrimonialitaEstesaAmministrativaM" {{disabled}}>
						<option value="">Seleziona...</option>
						{{#each patrimonialita}}
						<option value="{{this.id}}">{{this.descrizione}}</option>
						{{/each}}
					</select>
				</div>
			</div>
		</div>
		
		<div class="form-group col-md-2">
			<div class="input-group">
				<label for="enteGestoreEstesaAmministrativaM" class="control-label">Ente Gestore</label>
				<div class="input-group">
					<select class="fa form-control" id="enteGestoreEstesaAmministrativaM" {{disabled}}>
						<option value="">Seleziona...</option>
						{{#each entiGestori}}
						<option value="{{this.id}}">{{this.codice}}</option>
						{{/each}}
					</select>
				</div>
			</div>
		</div>
		
		<div class="form-group col-md-4" {{hidden}}>
			<label class="control-label">Georeferenziazione *</label>
			<div class="input-group" style="top: 11px">
				<div class="btn-group" role="group" aria-label="Azioni" style="margin-right: 20px;" data-info="Editing Georeferenziazione">
					   <button type="button" data-id="1" class="bttn btn-trasp editing-geom-estesa-btn"><em class="fa fa-globe"></em></button>
				</div>
				{{#if geom}} 
				<div class="btn-group" role="group" aria-label="Azioni" data-info="Localizza">
					<button type="button" data-id="1" class="bttn btn-trasp localizza-geom-estesa-btn"><em class="fa fa-map-marker"></em></button>
				</div>
				{{/if}}   
			</div>
		</div>
	  <!-- NOTE -->
	  <div class="form-group col-md-12">
		<label for="noteEstesaAmministrativa" class="control-label">Annotazioni</label>
		<div class="input-group">
			<textarea class="form-control" id="noteEstesaAmministrativa" rows="5" placeholder="Note..." {{readonly}}>{{note}}</textarea>
			<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
		</div>
	</div>
</script>
<!-- MODALE UPLOAD DOCUMENTI -->
<script id="modaleUploadDocumentiEstese" type="text/x-handlebars-template">
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
				<form class="form-horizontal" id="uploadForm">
					<div class="row nom nop">
						<div class="form-group col-md-12">
							<label class="control-label">File</label>
							<div class="input-group">
								<input type="file" id="file" class="form-control-file" name="file">
							</div>
						</div>
					  </div>
				</form>	
				<div class="absPulsante">
					<ul class="noStyle inline pulsante-upload">
						<li><a data-info="Salva" href="#" id="uploadDocumentoBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					</ul>
				</div>
			</div>			
		</div>
	</div>
</script>
<script id="modaleAnagraficaCippi" type="text/x-handlebars-template">
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
				<form class="form-horizontal" id="dettaglio">
					<!-- DETTAGLIO CIPPO -->
				</form>	
				<div class="absPulsante">
					<ul class="noStyle inline pulsante-inserimento">
						<li><a data-info="Salva" href="#" id="salvaCippoBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
						<li><a data-info="Annulla" href="#" id="chiudiBtn"> <em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
					</ul>
				</div>
		 	</div>			
		</div>
 	</div>
</script>
<!--------------------------- ESTESA AMMINISTRATIVA --------------------------------------------------------------------------	FINE --->
<!-- ----------------------------------------------------------------------------------------------------------------------------------->

<!-- --------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- CIPPO CHILOMETRICO --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestioneCippo" type="text/x-handlebars-template">
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
					<li><a href="#">Toponimastica</a></li>
					<li class="active"><a href="#">Cippo Chilometrico</a></li>
				</ul>
			</div>
			<div id="ricercaAnagCippo" class="">
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
							<form class="form-horizontal" id="ricerca">
								<div class="panel-body row nom">
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Sigla</label>
										<input id="codiceCippo" type="text" class="form-control" name="codiceCippo" placeholder="Codice...">   
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Estesa Amministrativa</label>
										<input id="estesaCippo" type="text" class="form-control" name="estesaCippo" placeholder="Estesa Amministrativa...">   
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Misura</label>
										<input id="misuraCippo" type="text" class="form-control" name="misuraCippo" placeholder="Misura...">   
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaCippoChilometrico" style="width:100%">
						<thead>
							<tr>
								<th>Sigla</th>
								<th>Estesa Amminsitrativa</th>
								<th>Misura</th>
								<th>Stato</th>
								<th>Annotazioni</th>
								<th></th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>Sigla</th>
								<th>Estesa Amminsitrativa</th>
								<th>Misura</th>
								<th>Stato</th>
								<th>Annotazioni</th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="dettaglioAnagCippo" class="col-md-12 tabbable-panel">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li id="liDetAnagraficaCippo" class="nav-item" data-info="Anagrafica">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica</strong>
							</a>
			  			</li>
						<li id="liDetDocumentoCippo" class="nav-item" data-info="Documenti">
			    			<a class="nav-link" id="documenti-tab" data-toggle="tab" href="#documentiTabContent1" role="tab" aria-controls="documentiTabContent1" aria-selected="true">
								<strong>Documenti</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
							<form class="form-horizontal" id="dettaglio">
								<!-- DETTAGLIO -->										
							</form>			
						</div>
						<div class="tab-pane" id="documentiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button id="documentoBtnCippo" type="button" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci documento</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaDocumentiCippo" style="width:100%">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Path</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Nome</th>
										<th>Percorso</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div id="buttonCippi" class="absPulsante"> 
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnCippo"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtnCippo"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea un nuovo cippo chilometrico" href="#" id="nuovoBtnCippo"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnCippo"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-inserimento">
					<li><a data-info="Salva" href="#" id="salvaBtnCippo"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Salva" href="#" id="aggiornaBtnCippo"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleDettaglioCippo" type="text/x-handlebars-template">
	<div class="row nom nop">
		<div class="form-group col-md-6" {{hiddenEstesa}}>
			<label for="estesaAmministrativaCippo" class="control-label">Estesa Amministrativa *</label>
			<div class="input-group">
				<input type="text" class="form-control estesa" id="estesaAmministrativaCippoM" value="{{estesaAmministrativa}}" placeholder="Estesa Amministrativa..." {{readonly}}>
				<input type="hidden" id="estesaAmministrativaCippoMValue">
			</div>
		</div>
		<div class="form-group col-md-3">
			<label for="codiceCippoM" class="control-label">Sigla</label>
			<div class="input-group">
				<input type="text" class="form-control" id="codiceCippoM" value="{{codice}}" placeholder="Sigla..." {{readonly}} {{disabledSigla}}>
			</div>
		</div>
		<div class="form-group col-md-3">
			<label for="misuraCippo" class="control-label">Misura (KM)</label>
			<div class="input-group">
				<input type="text" class="form-control" id="misuraCippoM" value="{{misura}}" placeholder="Misura..." {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-4" {{hidden}}>
			<label class="control-label">Georeferenziazione *</label>
			<div class="input-group" style="top: 11px">
				<div class="btn-group" role="group" aria-label="Azioni" style="margin-right: 20px;" data-info="Editing Georeferenziazione">
					   <button type="button" data-id="1" class="bttn btn-trasp editing-geom-cippo-btn"><em class="fa fa-globe"></em></button>
				</div> 
				{{#if geom}}
				<div class="btn-group" role="group" aria-label="Azioni" data-info="Localizza">
					   <button type="button" data-id="1" class="bttn btn-trasp localizza-geom-cippo-btn"><em class="fa fa-map-marker"></em></button>
				</div>   
				{{/if}}
			</div>
		</div>
		<div class="form-group col-md-12">
			<label for="noteCippo" class="col-sm-12 col-md-1 control-label">Annotazioni</label>
			<div class="input-group">
				<textarea class="form-control" id="noteCippo" rows="5" placeholder="Note..." {{readonly}}>{{note}}</textarea>
				<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
			</div>
		</div>
	</div>
</script>
<!-- MODALE UPLOAD DOCUMENTI CIPPO CHILOMETRICO -->
<script id="modaleUploadDocumentiCippo" type="text/x-handlebars-template">
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
				<form class="form-horizontal" id="uploadForm">
					<div class="row nom nop">
						<div class="form-group col-md-12">
							<label class="control-label">File</label>
							<div class="input-group">
								<input type="file" id="file" class="form-control-file" name="file">
							</div>
						</div>
					  </div>
				</form>	
				<div class="absPulsante">
					<ul class="noStyle inline pulsante-upload">
						<li><a data-info="Salva" href="#" id="uploadDocumentoBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					</ul>
				</div>
			</div>			
		</div>
	</div>
</script>
<!--------------------------- CIPPO CHILOMETRICO --------------------------------------------------------------------------	FINE --->
<!-- ------------------------------------------------------------------------------------------------------------------------------->

<!-- ---------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- AREE STRADALI --------------------------------------------------------------------------	INIZIO -->
<script id="modaleGestioneAreeStradali" type="text/x-handlebars-template">
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
					<li><a href="#">Toponimastica</a></li>
					<li class="active"><a href="#">Elementi e aree stradali</a></li>
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
							<form class="form-horizontal" id="ricerca">
								<div class="panel-body row nom">
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Classifica funzionale</label>
										<input type="text" class="form-control" id="classificaFunzionale" placeholder="Classifica funzionale...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Stato</label>
										<input type="text" class="form-control" id="tipoTopologicoGiunzioni" placeholder="Stato...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Classif. amministrativa</label>
										<input type="text" class="form-control" id="classAmministrativa" placeholder="Classif. amministrativa...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Tipo</label>
										<input type="text" class="form-control" id="tipo" placeholder="Tipo...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Classe di larghezza</label>
										<input type="text" class="form-control" id="classe" placeholder="Classe di larghezza...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Sede</label>
										<input type="text" class="form-control" id="sede" placeholder="Sede...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Toponimo Stradale</label>
										<input type="text" class="form-control" id="toponimoStradale" placeholder="Toponimo Stradale...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Estesa Amministrativa</label>
										<input type="text" class="form-control" id="estesaAmministrativa" placeholder="Estesa Amministrativa...">
									</div>
									<div class="col-sm-12 col-md-6 col-lg-2">
										<label style="display: inline-block">Entit&agrave; non georeferenziate</label> 
										<input type="checkbox" id="geo" name="geo" style="width: 15px; display: inline-block; position: relative; top: 14px;">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaRisultati" style="width:100%">
						<thead>
							<tr>
								<th>Classif. Funzionale</th>
								<th>Stato</th>
								<th>Classif. Amministrativa</th>
								<th>Tipo</th>
								<th>Classe di larghezza</th>
								<th>Sede</th>
								<th>Toponimo Stradale</th>
								<th>Estesa Amministrativa</th>
								<th></th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>Classif. Funzionale</th>
								<th>Stato</th>
								<th>Classif. Amministrativa</th>
								<th>Tipo</th>
								<th>Classe di larghezza</th>
								<th>Sede</th>
								<th>Toponimo Stradale</th>
								<th>Estesa Amministrativa</th>
								<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div class="col-md-12 tabbable-panel" id="dettaglio">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li class="nav-item" data-info="Anagrafica">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica</strong>
							</a>
			  			</li>
						<li class="nav-item" data-info="Documenti">
			    			<a class="nav-link" id="documenti-tab" data-toggle="tab" href="#documentiTabContent1" role="tab" aria-controls="documentiTabContent1" aria-selected="true">
								<strong>Documenti</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
							<!--div class="col-md-12" style="margin-top: 12px; margin-bottom: 12px;"-->
								<form class="form-horizontal" id="dettaglio">
									<!-- DETTAGLIO -->										
									<div class="row nom nop">
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Classifica funzionale</label>
											<input type="text" class="form-control" id="classificaFunzionaleM" placeholder="Classifica funzionale...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Stato</label>
											<input type="text" class="form-control" id="statoM" placeholder="Stato...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Classifica amministrativa</label>
											<input type="text" class="form-control" id="classAmministrativaM" placeholder="Classif. amministrativa...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Tipo</label>
											<input type="text" class="form-control" id="tipoM" placeholder="Tipo...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Classe di larghezza</label>
											<input type="text" class="form-control" id="classeM" placeholder="Classe di larghezza...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Sede</label>
											<input type="text" class="form-control" id="sedeM" placeholder="Sede...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Toponimo Stradale</label>
											<input type="text" class="form-control" id="toponimoStradaleM" placeholder="Toponimo Stradale...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Estesa Amministrativa</label>
											<input type="text" class="form-control" id="estesaAmministrativaM" placeholder="Estesa Amministrativa...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Senso percorrenza</label>
											<input type="text" class="form-control" id="senzoPercorenzaM" placeholder="Senso percorrenza...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Carreggiata</label>
											<input type="text" class="form-control" id="carreggiataM" placeholder="Carreggiata...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Limite Amministrativo</label>
											<input type="text" class="form-control" id="limiteAmministrativoM" placeholder="Limite Amministrativo...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Livello</label>
											<input type="text" class="form-control" id="livelloM" placeholder="Livello...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Origine</label>
											<input type="text" class="form-control" id="origineM" placeholder="Origine...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Fonte</label>
											<input type="text" class="form-control" id="fonteM" placeholder="Fonte...">
										</div>
										<div class="col-sm-12 col-md-6 col-lg-4">
											<label>Fondo</label>
											<input type="text" class="form-control" id="fondoM" placeholder="Fondo...">
										</div>
			  						</div>
			  						<!-- NOTE -->
			  						<div class="form-group col-md-12">
								    	<label for="noteAreeStradali" class="col-sm-12 col-md-1 control-label">Annotazioni</label>
								    	<div class="input-group">
											<textarea class="form-control" id="noteAreeStradali" rows="5" placeholder="Note..."></textarea>
											<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
										</div>
								  	</div>
								</form>					
							<!--/div-->			
						</div>
						<div class="tab-pane" id="documentiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button type="button" class="btn btn-primary" style="margin-bottom: 15px" onclick="alert('cc')">
		                			<span class="label">Inserisci documento</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaDocumentiAreeStradali" style="width:100%">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Path</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Nome</th>
										<th>Percorso</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" href="#" id="eseguiBtn"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtn"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea un nuovo Cippo Chilometrico" href="#" id="nuovoBtn"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul class="noStyle inline pulsante-inserimento" hidden>
					<li><a data-info="Salva" href="#" id="salvaBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
				<ul class="noStyle inline pulsante-dettaglio" hidden>
					<li><a data-info="Salva" href="#" id="aggiornaBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<!--------------------------- AREE STRADALI --------------------------------------------------------------------------	FINE --->
<!-- --------------------------------------------------------------------------------------------------------------------------->

<!-- --------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- GESTIONE ACCESSO --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestioneAccessi" type="text/x-handlebars-template">
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
					<li><a href="#">Toponimastica</a></li>
					<li class="active"><a href="#">Accessi</a></li>
				</ul>
			</div>
			<div id="ricercaAnagAccesso" class="">
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
							<form class="form-horizontal" id="ricerca">
								<div class="panel-body row nom">
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Localit&agrave;</label>
										<select class="fa form-control" id="localitaAccesso">
											<option value="">Seleziona...</option>
											{{#each localita}}
												<option value="{{this.id}}">{{this.descrizione}}</option>
											{{/each}}
										</select>
									</div>										
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Toponimo Stradale</label>
										<input id="toponimoStradaleAccesso" type="text" class="form-control" name="toponimoStradale" placeholder="Toponimo...">
										<input id="toponimoStradaleAccessoValue" type="hidden">
									</div>	
									<div class="col-sm-12 col-md-6 col-lg-4">
										<label>Tipo</label>
										<select class="fa form-control" id="tipoAccesso">
											<option value="">Seleziona...</option>
											{{#each tipoAccesso}}
												<option value="{{this.id}}">{{this.descrizione}}</option>
											{{/each}}
										</select>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- TABELLA RISULTATI -->
				<div class="table-responsive">
					<table class="table table-striped" id="tabellaAccessi" style="width:100%">
						<thead>
							<tr>
								<th>Toponimo</th>
								<th>Localit&agrave;</th>
								<th>Numero</th>
								<th>Esponente</th>
								<th>Tipo</th>
								<th>Passo Carrabile</th>
								<th>Accesso Principale</th>
								<th>Stato</th>
					      		<th></th>
							</tr>
						</thead>
						<tbody></tbody>
						<tfoot>
							<tr>
								<th>Toponimo</th>
								<th>Localit&agrave;</th>
								<th>Numero</th>
								<th>Esponente</th>
								<th>Tipo</th>
								<th>Passo Carrabile</th>
								<th>Principale</th>
								<th>Stato</th>
					      		<th></th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div class="col-md-12 tabbable-panel" id="dettaglioAnagAccesso">
				<div class="tabbable-line">
					<ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
			  			<li  id="liDetAnagraficaAccesso" class="nav-item" data-info="Anagrafica">
			    			<a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
								<strong>Anagrafica</strong>
							</a>
			  			</li>
						<li id="liDetDocumentoAccesso" class="nav-item" data-info="Documenti">
			    			<a class="nav-link" id="documenti-tab" data-toggle="tab" href="#documentiTabContent1" role="tab" aria-controls="documentiTabContent1" aria-selected="true">
								<strong>Documenti</strong>
							</a>
			  			</li>
					</ul>
					<div class="tab-content" id="dettaglioTabContent">
			  			<div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
							<form class="form-horizontal" id="dettaglio">
								<!-- DETTAGLIO -->										
							</form>					
						</div>
						<div class="tab-pane" id="documentiTabContent1" role="tabpanel" aria-labelledby="documenti-tab">
							<div class="btn-group">
								<button type="button" id="documentoBtnAccesso" class="btn btn-primary" style="margin-bottom: 15px">
		                			<span class="label">Inserisci documento</span>
		            			</button>
							</div>
							<table class="table table-striped" id="tabellaDocumentiAccessi" style="width:100%">
								<thead>
									<tr>
										<th>Nome</th>
										<th>Path</th>
										<th></th>
									</tr>
								</thead>
								<tbody></tbody>
								<tfoot>
									<tr>
										<th>Nome</th>
										<th>Percorso</th>
										<th></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div id="buttonAccessi" class="absPulsante">
				<ul id="pulsante-ricerca" class="noStyle inline pulsante-ricerca">
					<li><a data-info="Ricerca" href="#" id="ricercaBtnAccesso"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
					<li><a data-info="Azzera i filtri" href="#" id="azzeraBtnAccesso"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
					<li><a data-info="Crea un nuovo Accesso" href="#" id="nuovoBtnAccesso"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
					<li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnAccesso"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
				</ul>
				<ul id ="pulsante-inserimento" class="noStyle inline pulsante-inserimento">
					<li><a data-info="Salva" href="#" id="salvaBtnAccesso"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
				<ul id="pulsante-dettaglio" class="noStyle inline pulsante-dettaglio">
					<li><a data-info="Salva" href="#" id="aggiornaBtnAccesso"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					<li><a data-info="Annulla" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
				</ul>
			</div>
		</div>		
	</div>
</div>
</script>
<script id="modaleDettaglioAccesso" type="text/x-handlebars-template">
<div class="row nom nop">
	<div class="form-group col-md-4" {{hiddenToponimo}}>
		<label for="accessoToponimo" class="control-label">Toponimo Stradale</label>
		<div class="input-group">
			<input type="text" class="form-control" id="accessoToponimo" value="{{toponimo}}" placeholder="Toponimo Stradale..." {{readonly}}>
			<input type="hidden" id="accessoToponimoValue">
		</div>
	</div>
	<div class="form-group col-md-4">
		<label for="accessoNumero" class="control-label">Numero</label>
			<div class="input-group">
				<input type="text" class="form-control" id="accessoNumCivico" value="{{numero}}" placeholder="Numero..." {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label for="accessoEsponente" class="control-label">Esponente</label>
			<div class="input-group">
				<input type="text" class="form-control" id="esponenteCivico" value="{{esponente}}" placeholder="Esponente..." {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-4" {{hiddenLocalita}}>
			<label for="localitaAccesso" class="control-label">Localit&agrave;</label>
			<div class="input-group">
				<select class="fa form-control" id="localitaAccessoM" {{disabled}}>
					<option value="">Seleziona...</option>
					{{#each localita}}
						<option value="{{this.id}}">{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label for="tipoAccesso" class="control-label">Tipo</label>
			<div class="input-group">
				<select class="fa form-control" id="tipoAccessoM" {{disabled}}>
					<option value="">Seleziona...</option>
					{{#each tipoAccesso}}
						<option value="{{this.id}}">{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		</div>
		<div class="form-group col-md-2">
			<label class="control-label">Passo Carrabile</label>
			<div class="input-group">
				<input type="checkbox" id="accessoPassoCarrabile" style="margin-left: 25%; margin-top: 7%;" {{passoCarrabile}} {{disabled}}>
			</div>
		</div>
		<div class="form-group col-md-2">
			<label class="control-label">Accesso Principale</label>
			<div class="input-group">
				<input type="checkbox" id="accessoPrincipale" style="margin-left: 25%; margin-top: 7%;" {{principale}} {{disabled}}>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label class="control-label">Metodo</label>
			<div class="input-group">
				<input type="text" class="form-control" id="accessoMetodo" value="{{metodo}}" placeholder="Metodo..." {{readonly}}>
			</div>
		</div>
	</div>
	<!-- NOTE -->
	<div class="form-group col-md-12">
		<label for="accessoNote" class="col-sm-12 col-md-1 control-label">Annotazioni</label>
		<div class="input-group">
			<textarea class="form-control" id="accessoNote" rows="5" placeholder="Note..." {{readonly}}>{{note}}</textarea>
			<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
		</div>
	</div>
	<div class="form-group col-md-4" {{hidden}}>
        <label class="control-label">Georeferenziazione</label>
        <div class="input-group" style="top: 11px">
			<div class="btn-group" role="group" aria-label="Azioni" style="margin-right: 20px;" data-info="Editing Georeferenziazione">
					<button type="button" class="bttn btn-trasp editing-accesso-btn"><em class="fa fa-globe"></em></button>
			</div> 
			{{#if geom}}
				<div class="btn-group" role="group" aria-label="Azioni" data-info="Localizza">
					<button type="button" class="bttn btn-trasp localizza-geo-accesso-btn"><em class="fa fa-map-marker"></em></button>
				</div>
			{{/if}}   
        </div>
    </div>
</div>
</script>
<!-- MODALE UPLOAD DOCUMENTI -->
<script id="modaleUploadDocumentiAccessi" type="text/x-handlebars-template">
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
				<form class="form-horizontal" id="uploadForm">
					<div class="row nom nop">
						<div class="form-group col-md-12">
							<label class="control-label">File</label>
							<div class="input-group">
								<input type="file" id="file" class="form-control-file" name="file">
							</div>
						</div>
					  </div>
				</form>	
				<div class="absPulsante">
					<ul class="noStyle inline pulsante-upload">
						<li><a data-info="Salva" href="#" id="uploadDocumentoBtn"> <em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</script>

<script id="templatePulsantiTabellaRicercaInterno" type="text/x-handlebars-template">
<div class="btn-group" role="group" aria-label="Azioni">
	<button type="button" data-id="{{id}}" class="bttn btn-trasp anagrafica-interno-btn" data-info="Anagrafica"><em class="fa fa-arrow-right"></em></button>
	{{#if geo}}
	<button type="button" data-id="{{id}}" data-geometry="{{geometry}}" class="bttn btn-trasp localizza-interno-btn" data-info="Localizza"><em class="fa fa-map-marker"></em></button>
	{{/if}}
</div>
</script>
<!--------------------------- GESTIONE ACCESSO --------------------------------------------------------------------------	FINE --->
<!-- ------------------------------------------------------------------------------------------------------------------------------->

<!--------------------------- PULSANTI TABELLE ENTITA' --------------------------------------------------------------------------	INIZIO --->
<!-- ------------------------------------------------------------------------------------------------------------------------------->
<script id="templatePulsantiTabella" type="text/x-handlebars-template">
	<div class="btn-group" role="group" aria-label="Azioni">
		<button type="button" data-id="{{id}}" data-geometry="{{geometry}}" class="bttn btn-trasp modifica-btn" data-info="Modifica"><em class="fa fa-pencil"></em></button>
		<button type="button" data-id="{{id}}" class="bttn btn-trasp elimina-btn" data-info="Elimina"><em class="fa fa-trash"></em></button>
		{{#if geom}}
		<button type="button" data-id="{{id}}" data-geometry="{{geometry}}" class="bttn btn-trasp localizza-btn" data-info="Localizza"><em class="fa fa-map-marker"></em></button>
		{{/if}}
	</div>
</script>
	
<script id="templatePulsantiTabellaDocumenti" type="text/x-handlebars-template">
	<div class="btn-group" role="group" aria-label="Azioni">
		<button type="button" data-nome="{{nomeFile}}" data-id="{{id}}" class="bttn btn-trasp elimina-documento-btn" data-info="Elimina" style="margin-right: 25%"><em class="fa fa-trash"></em></button>
		<button type="button" data-nome="{{nomeFile}}" data-id="{{id}}" class="bttn btn-trasp download-documento-btn" data-info="Scarica"><em class="fa fa-download"></em></button>
	</div>
</script>

<script id="templatePulsantiTabellaAccessi" type="text/x-handlebars-template">
	<div class="btn-group" role="group" aria-label="Azioni">
		<button type="button" data-id="{{id}}" class="bttn btn-trasp anagrafica-accesso-btn" data-info="Anagrafica"><em class="fa fa-arrow-right"></em></button>
		<button type="button" data-id="{{id}}" data-entity="{{idEntity}}" class="bttn btn-trasp elimina-accesso-btn" data-info="Elimina"><em class="fa fa-trash"></em></button>
		{{#if geom}}
		<button type="button" data-id="{{id}}" data-geometry="{{geometry}}" class="bttn btn-trasp localizza-accesso-btn" data-info="Localizza"><em class="fa fa-map-marker"></em></button>
		{{/if}}
	</div>
</script>

<script id="templatePulsantiTabellaCippi" type="text/x-handlebars-template">
	<div class="btn-group" role="group" aria-label="Azioni">
		<button type="button" data-id="{{id}}" class="bttn btn-trasp anagrafica-cippi-btn" data-info="Anagrafica"><em class="fa fa-arrow-right"></em></button>
		<button type="button" data-id="{{id}}" data-estesa="{{idEstesa}}" class="bttn btn-trasp elimina-cippi-btn" data-info="Elimina"><em class="fa fa-trash"></em></button>
		{{#if geom}}
		<button type="button" data-id="{{id}}" data-geometry="{{geometry}}" class="bttn btn-trasp localizza-cippi-btn" data-info="Localizza"><em class="fa fa-map-marker"></em></button>
		{{/if}}
	</div>
</script>
<!--------------------------- PULSANTI TABELLE ENTITA' --------------------------------------------------------------------------	FINE --->
<!-- ------------------------------------------------------------------------------------------------------------------------------->

<!-- ------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- RICERCHE --------------------------------------------------------------------------	INIZIO --->
<script id="modaleRicercaEntita" type="text/x-handlebars-template">
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="ricSpess">
            <div class="globalContainer">
                <div id="menu" class="search" style="margin-bottom: 0px;">
                    <ul class="breadcrumb">
                        <li><a href="#"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                        <li><a href="#">Toponomastica</a></li>
                        <li><a href="#">Ricerca dati Toponomastica</a></li>
                        <li class="active"><a href="#">Ricerca entit&agrave;</a></li>
                    </ul>
                </div>
                <div id="ricerca" class="wizard">
                    <div class="wizard-inner">
                        <div class="connecting-line"></div>
                        <ul class="nav nav-tabs" role="tablist">
                            <!-- STEP TOPONIMO STRADALE -->
                            <li role="presentation" class="active">
                                <a href="#stepToponimoStradaleDiv" id="stepToponimoStradale" data-toggle="tab" aria-controls="stepToponimoStradale" role="tab" data-info="Ricerca Toponimo Stradale">
                                    <span class="round-tab">
                                        TOPONIMO STRADALE
                                    </span>
                                </a>
                            </li>
                            <!-- STEP ACCESSO -->
                            <li role="presentation">
                                <a href="#stepAccessoDiv" id="stepAccesso" data-toggle="tab" aria-controls="stepAccesso" role="tab" data-info="Ricerca Accessi">
                                    <span class="round-tab">
                                        ACCESSO
                                    </span>
                                </a>
                            </li>
                            <!-- STEP ESTESA AMMINISTRATIVA -->
                            <li role="presentation">
                                <a href="#stepEstesaAmministrativaDiv" id="stepEstesaAmministrativa" data-toggle="tab" aria-controls="stepEstesaAmministrativa" role="tab" data-info="Ricerca Estesa Amministrativa">
                                    <span class="round-tab">
                                        ESTESA AMMINISTRATIVA
                                    </span>
                                </a>
                            </li>
                            <!-- STEP CIPPO CHILOMETRICO -->
                            <li role="presentation">
                                <a href="#stepCippoDiv" id="stepCippo" data-toggle="tab" aria-controls="stepCippo" role="tab" data-info="Ricerca Cippo Chilometrico">
                                    <span class="round-tab">
                                        CIPPO CHILOMETRICO
                                    </span>
                                </a>
                            </li>
                            <!-- PERCORSO 
							<!-- STEP LOCALITA -->
                            <li role="presentation">
                                <a href="#stepLocalitaDiv" id="stepLocalita" data-toggle="tab" aria-controls="stepLocalita" role="tab" data-info="Ricerca Localit&agrave;">
                                    <span class="round-tab">
                                        LOCALIT&Agrave;
                                    </span>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <form role="form" id="formRicerche">
                        <div class="tab-content">
                            <div class="tab-pane" role="tabpanel" id="stepToponimoStradaleDiv">
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
                                            <form class="form-horizontal" id="ricerca">
                                                <div class="panel-body row nom">
                                                    <div class="col-sm-12 col-md-6 col-lg-2">
                                                        <label>Classe</label>
                                                        <input id="dug" type="text" class="form-control" name="dug" placeholder="Classe...">   
                                                    </div>
                                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                                        <label>Denominazione ufficiale</label>
                                                        <div class="input-group">
                                                            <input id="descrizioneToponimoStradale" type="text" class="form-control" name="descrizione" placeholder="Denominazione ufficiale...">   
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-12 col-md-6 col-lg-2">
                                                        <label>Numero delibera</label>
                                                        <input id="numeroDelibera" type="text" class="form-control" name="numeroDelibera" placeholder="Numero delibera...">
                                                    </div>
                                                    <div class="col-sm-12 col-md-6 col-lg-2">
                                                        <label>Numero provvedimento</label>
                                                        <input id="codiceAutorizzazione" type="text" class="form-control" name="codiceAutorizzazione" placeholder="Numero provvedimento...">
                                                    </div>
                                                    <div class="col-sm-12 col-md-6 col-lg-3">
                                                        <label>Data delibera dal</label>
                                                        <div class="input-group">
                                                            <input id="dataDeliberaToponimoDal" type="text" class="form-control" name="dataDeliberaToponimoDal" placeholder="Data delibera dal...">
                                                            <div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-12 col-md-6 col-lg-3">
                                                        <label>Data delibera al</label>
                                                        <div class="input-group">
                                                            <input id="dataDeliberaToponimoAl" type="text" class="form-control" name="dataDeliberaToponimoAl" placeholder="Data delibera al...">
                                                            <div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-12 col-md-6 col-lg-3">
                                                        <label>Data autorizzazione dal</label>
                                                        <div class="input-group">
                                                            <input id="dataAutorizzazioneToponimoDal" type="text" class="form-control" name="dataAutorizzazioneToponimoDal" placeholder="Data autorizzazione dal...">
                                                            <div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                                                        </div>	
                                                    </div>
                                                    <div class="col-sm-12 col-md-6 col-lg-3">
                                                        <label>Data autorizzazione al</label>
                                                        <div class="input-group">
                                                            <input id="dataAutorizzazioneToponimoAl" type="text" class="form-control" name="dataAutorizzazioneToponimoAl" placeholder="Data autorizzazione al...">
                                                            <div class="input-group-append"><span class="input-group-text"><i class="fa fa-calendar"></i></span></div>
                                                        </div>	
													</div>
													<div class="col-sm-12 col-md-6 col-lg-3">
														<label>Numero civico Da - lato Dx</label>
														<input id="numCivicoDxDa" type="text" class="form-control" name="numCivicoDxDa" placeholder="Numero civico da - lato Dx...">
													</div>
													<div class="col-sm-12 col-md-6 col-lg-3">
														<label>Numero civico A - lato Dx</label>
														<input id="numCivicoDxA" type="text" class="form-control" name="numCivicoDxA" placeholder="Numero civico a - lato Dx...">
													</div>
													<div class="col-sm-12 col-md-6 col-lg-3">
														<label>Numero civico Da - lato Sx</label>
														<input id="numCivicoSxDa" type="text" class="form-control" name="numCivicoSxDa" placeholder="Numero civico da - lato Sx...">
													</div>
													<div class="col-sm-12 col-md-6 col-lg-3">
														<label>Numero civico A - lato Sx</label>
														<input id="numCivicoSxA" type="text" class="form-control" name="numCivicoSxA" placeholder="Numero civico a - lato Sx...">
													</div>
                                                    <div class="col-sm-12 col-md-6 col-lg-2">
                                                        <label>Stato</label>
                                                        <div class="input-group">
                                                            <select class="fa form-control" id="statoToponimo">
                                                                <option value="">Seleziona...</option>
                                                                <option value="BOZZA">BOZZA</option>
                                                                <option value="COMPLETATO">COMPLETATO</option>
                                                                <option value="PUBBLICATO">PUBBLICATO</option>
                                                            </select>   
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <!-- TABELLA RISULTATI TOPONIMO STRADALE -->
                                <table class="table table-striped" id="tabellaRicercaToponimoStradale" style="width:100%; display:none;">
                                    <thead>
                                        <tr>
                                            <th>Classe</th>
                                            <th>Denominazione Ufficiale</th>
                                            <th>Comune</th>
                                            <th>CAP</th>
											<th>Tipo</th>
											<th>Lunghezza</th>
											<th>Codice Toponimo</th>
                                            <th>Numero delibera</th>
                                            <th>Data delibera</th>
                                            <th>Numero provvedimento</th>
                                            <th>Data autorizzazione</th>
                                            <th>Stato</th>
                                            <th></th>
                                       </tr>
                                      </thead>
                                      <tbody></tbody>
                                      <tfoot>
                                          <tr>
                                            <th>Classe</th>
                                            <th>Denominazione Ufficiale</th>
                                            <th>Comune</th>
                                            <th>CAP</th>
											<th>Tipo</th>
											<th>Lunghezza</th>
											<th>Codice Toponimo</th>
                                            <th>Numero delibera</th>
                                            <th>Data delibera</th>
                                            <th>Numero provvedimento</th>
                                            <th>Data autorizzazione</th>
                                            <th>Stato</th>
                                            <th></th>
                                        </tr>
                                    </tfoot>
                                </table>
                                
                            </div>
                            <div class="tab-pane" role="tabpanel" id="stepAccessoDiv">
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
                                                    <label>Localit&agrave;</label>
                                                    <select class="fa form-control" id="localitaAccesso">
                                                        <option value="">Seleziona...</option>
                                                        {{#each localita}}
                                                        <option value="{{this.id}}">{{this.descrizione}}</option>
                                                        {{/each}}
                                                    </select>
                                                </div>												
                                                <div class="col-sm-12 col-md-6 col-lg-4">
                                                    <label>Toponimo Stradale</label>
                                                    <input id="toponimoStradaleAccesso" type="text" class="form-control" name="toponimoStradale" placeholder="Toponimo...">
                                                    <input id="toponimoStradaleAccessoValue" type="hidden">
                                                </div>
                                                <div class="col-sm-12 col-md-6 col-lg-4">
                                                    <label>Tipo</label>
                                                    <select class="fa form-control" id="tipoAccesso">
                                                        <option value="">Seleziona...</option>
                                                        {{#each tipoAccesso}}
                                                        <option value="{{this.id}}">{{this.descrizione}}</option>
                                                        {{/each}}
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- TABELLA RISULTATI ACCESSO -->
                                <table class="table table-striped" id="tabellaAccessi" style="width:100%; display:none;">
                                    <thead>
                                        <tr>
                                          <th>Toponimo</th>
                                          <th>Localit&agrave;</th>
                                          <th>Numero</th>
                                          <th>Esponente</th>
                                          <th>Tipo</th>
                                          <th>Passo Carrabile</th>
                                          <th>Principale</th>
                                          <th>Stato</th>
                                            <th></th>
                                      </thead>
                                      <tbody></tbody>
                                      <tfoot>
                                          <tr>
                                          <th>Toponimo</th>
                                          <th>Localit&agrave;</th>
                                          <th>Numero</th>
                                          <th>Esponente</th>
                                          <th>Tipo</th>
                                          <th>Passo Carrabile</th>
                                          <th>Principale</th>
                                          <th>Stato</th>
                                            <th></th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div class="tab-pane" role="tabpanel" id="stepEstesaAmministrativaDiv">
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
												<div class="col-sm-12 col-md-6 col-lg-2">
													<label>Sigla</label>
													<input id="siglaEstesaAmministrativa" type="text" class="form-control" name="sigla" placeholder="Sigla...">
												</div>
												<div class="col-sm-12 col-md-6 col-lg-2">
													<label>Codice</label>
													<input id="codiceEstesaAmministrativa" type="text" class="form-control" name="codiceEstesaAmministrativa" placeholder="Codice...">
												</div>
												<div class="col-sm-12 col-md-6 col-lg-4">
													<label>Descrizione Estesa Amministrativa</label>
													<input id="descrizioneEstesaAmministrativa" type="text" class="form-control" name="DescrizioneEstesaAmministrativa" placeholder="Descrizione...">
												</div>
												<div class="col-sm-12 col-md-6 col-lg-2">
													<label>Classif. Amministrativa</label>
													<div class="input-group">
														<select class="fa form-control" id="classificaAmmEstesaAmministrativa">
															<option value="">Seleziona...</option>
															{{#each classificaAmministrativa}}
															<option value="{{this.id}}">{{this.descrizione}}</option>
															{{/each}}
														</select>
													</div>
												</div>
												<div class="col-sm-12 col-md-6 col-lg-2">
													<label>Classif. Funzionale</label>
													<div class="input-group">
														<select class="fa form-control" id="classificaFunzEstesaAmministrativa">
															<option value="">Seleziona...</option>
															{{#each classificaFunzionale}}
															<option value="{{this.id}}">{{this.descrizione}}</option>
															{{/each}}
														</select>
													</div>
												</div>
											</div>
                                        </div>
                                    </div>
                                </div>
                                <!-- TABELLA RISULTATI ESTESA AMMINISTRATIVA -->
                                <table class="table table-striped" id="tabellaEstesaAmministrativa" style="width:100%; display:none;">
                                    <thead>
                                        <tr>
                                          <th>Sigla</th>
                                          <th>Descrizione</th>
										  <th>Classif. Amministrativa</th>
										  <th>Classif. Funzionale</th>
                                          <th>Codice</th>
                                          <th>Estensione</th>
										  <th>Tronco</th>
										  <th>Provincia</th>
                                          <th>Comune</th>
                                          <th>Stato</th>
                                          <th></th>
                                       </tr>
                                      </thead>
                                      <tbody></tbody>
                                      <tfoot>
										<tr>
											<th>Sigla</th>
											<th>Descrizione</th>
											<th>Classif. Amministrativa</th>
										  	<th>Classif. Funzionale</th>
											<th>Codice</th>
											<th>Estensione</th>
											<th>Tronco</th>
											<th>Provincia</th>
											<th>Comune</th>
											<th>Stato</th>
											<th></th>
										 </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div class="tab-pane" role="tabpanel" id="stepCippoDiv">
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
                                                    <label>Sigla</label>
                                                    <input id="cippoCodice" type="text" class="form-control" name="cippoCodice" placeholder="Sigla...">
                                                </div>
												<div class="col-sm-12 col-md-6 col-lg-4">
                                                    <label>Estesa Amministrativa</label>
                                                    <input id="cippoEstesaAmministrativa" type="text" class="form-control" name="cippoEstesaAmministrativa" placeholder="Estesa Amministrativa...">
                                                </div>
                                                <div class="col-sm-12 col-md-6 col-lg-4">
                                                    <label>Misura</label>
                                                    <input id="cippoMisura" type="text" class="form-control" name="cippoMisura" placeholder="Misura...">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- TABELLA RISULTATI CIPPO CHILOMETRICO -->
                                <table class="table table-striped" id="tabellaCippo" style="width:100%; display:none;">
                                    <thead>
                                        <tr>
										  <th>Sigla</th>
                                          <th>Estesa Amminsitrativa</th>
                                          <th>Misura</th>
                                          <th>Stato</th>
                                          <th>Annotazioni</th>
                                          <th></th>
                                       </tr>
                                      </thead>
                                      <tbody></tbody>
                                      <tfoot>
                                          <tr>
											<th>Sigla</th>
                                          	<th>Estesa Amminsitrativa</th>
                                          	<th>Misura</th>
                                          	<th>Stato</th>
                                          	<th>Annotazioni</th>
                                          <th></th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div class="tab-pane" role="tabpanel" id="stepPercorsoDiv">
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
                                                    <label>Sigla</label>
                                                    <input id="siglaPercorso" type="text" class="form-control" name="sigla" placeholder="Descrizione...">
                                                </div>
                                                <div class="col-sm-12 col-md-6 col-lg-8">
                                                    <label>Descrizione</label>
                                                    <input id="descrizionePercorso" type="text" class="form-control" name="descrizione" placeholder="Descrizione...">
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <label style="display: inline-block">Estesa Amministrativa</label>
                                                    <input type="radio" id="estesaAmministrativaPercorso" name="scelta" value="EA" style="display: inline-block; width: 15px; position: relative; top: 14px;">
                                                </div>
                                                <div class="col-sm-2 col-md-2 col-lg-2">
                                                    <label style="display: inline-block">Toponimo Stradale</label>
                                                    <input type="radio" id="toponimoStradalePercorso" name="scelta" value="TS" style="display: inline-block; width: 15px; position: relative; top: 14px;">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- TABELLA RISULTATI PERCORSO -->
                                <div class="table-responsive">
                                    <table class="table table-striped" id="tabellaPercorso" style="width:100%; display:none;" >
                                        <thead>
                                            <tr>
                                                  <th>Sigla</th>
                                                  <th>Descrizione</th>
                                                  <th>Estesa Amministrativa</th>
                                                <th>Toponimo Stradale</th>
                                                <th>Stato</th>
                                                <th>Annotazioni</th>
                                                <th></th>
                                            </tr>
                                          </thead>
                                          <tbody>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                  <th>Sigla</th>
                                                  <th>Descrizione</th>
                                                <th>Estesa Amministrativa</th>
                                                <th>Toponimo Stradale</th>
                                                <th>Stato</th>
                                                  <th>Annotazioni</th>
                                                <th></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <div class="tab-pane" role="tabpanel" id="stepLocalitaDiv">
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
                                                <div class="col-sm-12 col-md-6 col-lg-6">
                                                    <label>Descrizione</label>
                                                    <input id="descrizioneLocalita" type="text" class="form-control" name="descrizioneLocalita" placeholder="Descrizione...">
                                                </div>
                                                <div class="col-sm-12 col-md-6 col-lg-6">
                                                    <label>Frazione</label>
                                                    <input id="frazioneLocalita" type="text" class="form-control" name="frazioneLocalita" placeholder="Frazione...">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- TABELLA RISULTATI LOCALITA' -->
                                <table class="table table-striped" id="tabellaLocalita" style="width:100%; display:none;" >
                                    <thead>
                                        <tr>
                                          <th>Descrizione</th>
                                          <th>Comune</th>
                                          <th>Frazione</th>
                                          <th>Tipo</th>
                                          <th>Stato</th>
                                          <th></th>
                                       </tr>
                                      </thead>
                                      <tbody></tbody>
                                      <tfoot>
                                          <tr>
                                          <th>Descrizione</th>
                                          <th>Comune</th>
                                          <th>Frazione</th>
                                          <th>Tipo</th>
                                          <th>Stato</th>
                                          <th></th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div class="tab-pane" role="tabpanel" id="stepInternoDiv">
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
                                                <div class="col-sm-12 col-md-6 col-lg-2">
                                                    <label>Comune</label>
                                                    <select class="fa form-control" id="comuneInterno">
                                                        <option value="F158" selected>Messina</option>
                                                    </select>
                                                </div>
                                                <div class="col-sm-12 col-md-6 col-lg-6">
                                                    <label>Sezione Urbana</label>
                                                    <input id="sezioneUrbanaInterno" type="text" class="form-control" name="sezioneUrbana" placeholder="Sezione Urbana...">
                                                </div>
                                                <div class="col-sm-12 col-md-6 col-lg-2">
                                                    <label>Foglio</label>
                                                    <input id="foglioInterno" type="text" class="form-control" name="foglio" placeholder="Foglio...">
                                                </div>
                                                <div class="col-sm-12 col-md-6 col-lg-2">
                                                    <label>Numero</label>
                                                    <input id="numeroInterno" type="text" class="form-control" name="numero" placeholder="Numero...">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- TABELLA RISULTATI INTERNO -->
                                <table class="table table-striped" id="tabellaInterno" style="width:100%; display:none;">
                                    <thead>
                                        <tr>
                                          <th>Comune</th>
                                          <th>Foglio</th>
                                          <th>Numero</th>
                                          <th>Subalterno</th>
                                          <th></th>
                                       </tr>
                                      </thead>
                                      <tbody></tbody>
                                      <tfoot>
                                          <tr>
                                          <th>Comune</th>
                                          <th>Foglio</th>
                                          <th>Numero</th>
                                          <th>Subalterno</th>
                                          <th></th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </form>
                </div>				
                <div id="dettaglio">
                    <!-- dettaglio Toponimo Stradale-->
                    <div class="col-md-12 tabbable-panel" id="dettaglioRicercaToponimo">
                        <div class="tabbable-line">
                            <ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
                                  <li class="nav-item" data-info="Anagrafica Accesso">
                                    <a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
                                        <strong>Anagrafica Toponimo Stradale</strong>
                                    </a>
                                  </li>
                            </ul>
                            <div class="tab-content" id="dettaglioTabContent">
                                <div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
                                    <form id="dettaglioToponimo" class="form-horizontal">
                                    <!-- DETTAGLIO -->
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- dettaglio Accesso -->
                    <div class="col-md-12 tabbable-panel" id="dettaglioRicercaAccesso">
                        <div class="tabbable-line">
                            <ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
                                  <li class="nav-item" data-info="Anagrafica Accesso">
                                    <a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
                                        <strong>Anagrafica Accesso</strong>
                                    </a>
                                  </li>
                            </ul>
                            <div class="tab-content" id="dettaglioTabContent">
                                <div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
                                    <form id="dettaglioAccesso" class="form-horizontal">
                                    <!-- DETTAGLIO -->
                                    </form>
                                </div>
                            </div>
                        </div>		
                    </div>
                    <!-- dettaglio Estesa Amministrativa -->
                    <div class="col-md-12 tabbable-panel" id="dettaglioRicercaEstesa">
                        <div class="tabbable-line">
                            <ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
                                  <li class="nav-item" data-info="Anagrafica Accesso">
                                    <a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
                                        <strong>Anagrafica Estesa Amministrativa</strong>
                                    </a>
                                  </li>
                            </ul>
                            <div class="tab-content" id="dettaglioTabContent">
                                <div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
                                    <form id="dettaglioEstesaAmministrativa" class="form-horizontal">
                                    <!-- DETTAGLIO -->
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- dettaglio Cippo Chilometrico -->
                    <div class="col-md-12 tabbable-panel" id="dettaglioRicercaCippo">
                        <div class="tabbable-line">
                            <ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
                                  <li class="nav-item" data-info="Anagrafica Accesso">
                                    <a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
                                        <strong>Anagrafica Cippo Chilometrico</strong>
                                    </a>
                                  </li>
                            </ul>
                            <div class="tab-content" id="dettaglioTabContent">
                                <div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
                                    <form id="dettaglioCippo" class="form-horizontal">
                                    <!-- DETTAGLIO -->
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- dettaglio Percorso -->
                    <div class="col-md-12 tabbable-panel" id="dettaglioRicercaPercorso">
                        <div class="tabbable-line">
                            <ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
                                  <li class="nav-item" data-info="Anagrafica Accesso">
                                    <a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
                                        <strong>Anagrafica Percorso</strong>
                                    </a>
                                  </li>
                            </ul>
                            <div class="tab-content" id="dettaglioTabContent">
                                <div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
                                    <form id="dettaglioPercorso" class="form-horizontal">
                                    <!-- DETTAGLIO -->
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- dettaglio Localita -->
                    <div class="col-md-12 tabbable-panel" id="dettaglioRicercaLocalita">
                        <div class="tabbable-line">
                            <ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
                                  <li class="nav-item" data-info="Anagrafica Accesso">
                                    <a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
                                        <strong>Anagrafica Localita</strong>
                                    </a>
                                  </li>
                            </ul>
                            <div class="tab-content" id="dettaglioTabContent">
                                <div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
                                    <form id="dettaglioLocalita" class="form-horizontal">
                                    <!-- DETTAGLIO -->
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- dettaglio Interno-->
                    <div class="col-md-12 tabbable-panel" id="dettaglioRicercaInterno">
                        <div class="tabbable-line">
                            <ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
                                  <li class="nav-item" data-info="Anagrafica Accesso">
                                    <a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true">
                                        <strong>Anagrafica Interno</strong>
                                    </a>
                                  </li>
                            </ul>
                            <div class="tab-content" id="dettaglioTabContent">
                                <div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
                                    <form id="dettaglioInterno" class="form-horizontal">
                                    <!-- DETTAGLIO -->
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
				<div class="absPulsante">
					<ul class="noStyle inline pulsante-ricerca">
						<li><a id="ricercaBtn" href="#" data-info="Ricerca"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
						<li><a id="azzeraBtn" href="#" data-info="Azzera i filtri"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
						<li><a id="chiudiBtn"  href="#" data-info="Chiudi la finestra"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
					</ul>
					<ul class="noStyle inline pulsante-dettaglio">
						<li><a id="indietroBtn" href="#" data-info="Indietro"><em class="fa fa-chevron-left" aria-hidden="true"></em>&nbsp;&nbsp;Indietro</a></li>
						<li><a id="chiudiBtn"  href="#" data-info="Chiudi la finestra"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
					</ul>
				</div>
            </div>
        </div>
        
    </div>
</script>
<!-- PULSANTI TABELLA RICERCHE -->
<script id="templatePulsantiTabellaRicerca" type="text/x-handlebars-template">
<div class="btn-group" role="group" aria-label="Azioni">
	<button type="button" data-id="{{id}}" class="bttn btn-trasp anagrafica-btn" data-info="Anagrafica"><em class="fa fa-arrow-right"></em></button>
	{{#if geom}}
	<button type="button" data-id="{{id}}" data-geometry="{{geometry}}" class="bttn btn-trasp localizza-btn" data-info="Localizza"><em class="fa fa-map-marker"></em></button>
	{{/if}}
</div>
</script>


<script id="modaleDettaglioInterno" type="text/x-handlebars-template">
	<div class="row nom nop">
		<div class="form-group col-md-4">
			<label for="comuneLocalita" class="control-label">Comune *</label>
			<div class="input-group">
				<select class="fa form-control" id="comuneInternoM" {{disabled}}>
					<option value="">Seleziona...</option>
					{{#each comuniMessina}}
						<option value="{{this.id}}">{{this.descrizione}}</option>
					{{/each}}
				</select>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label for="internoFoglio" class="control-label">Foglio</label>
			<div class="input-group">
				<input type="text" class="form-control" id="internoFoglioM" value="{{foglio}}" placeholder="Foglio..." {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label for="internoNumero" class="control-label">Numero</label>
			<div class="input-group">
				<input type="text" class="form-control" id="internoNumeroM" value="{{numero}}" placeholder="Numero..." {{readonly}}>
			</div>
		</div>
		<div class="form-group col-md-4">
			<label for="internoSubalterno" class="control-label">Subalterno</label>
			<div class="input-group">
				<input type="text" class="form-control" id="internoSubalternoM" value="{{subalterno}}" placeholder="Subalterno..." {{readonly}}>
			</div>
		</div>
	</div>
	<!-- NOTE -->
	<div class="form-group col-md-12">
		<label for="internoNote" class="control-label">Annotazioni</label>
		<div class="input-group">
			<textarea class="form-control" id="internoNote" rows="5" placeholder="Note..." {{readonly}}>{{note}}</textarea>
			<div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
		</div>
	</div>
</script>