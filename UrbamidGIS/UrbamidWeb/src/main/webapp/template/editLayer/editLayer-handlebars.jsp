<!-- --------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------- EDITING LIVELLO --------------------------------------------------------------------------	INIZIO --->
<script id="modaleGestioneLayer" type="text/x-handlebars-template">
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
					<li><a href="#">Editing Layer</a></li>
					<li class="active"><a href="#">Layers</a></li>
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
                                        <label>Nome</label>
                                        <input id="nomeLayerRic" type="text" class="form-control" name="nomeLayer" placeholder="Nome layer...">
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-6">
                                        <label>Descrizione</label>
                                        <input id="descrizioneLayerRic" type="text" class="form-control" name="descrizioneLayer" placeholder="Descrizione layer...">
                                    </div>
                                    <div class="col-sm-12 col-md-6 col-lg-2">
                                        <label>Stato</label>
                                        <div class="input-group">
                                            <select class="fa form-control" id="statoRic">
                                                <option value="">Seleziona...</option>
                                                <option value="BOZZA">BOZZA</option>
                                                <option value="PUBBLICATO">PUBBLICATO</option>
                                                <!-- <option value="ARCHIVIATO">ARCHIVIATO</option> -->
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
                    <table class="table table-striped" id="tabellaEditingLivello" style="width:100%">
                        <thead>
                            <tr>
                                <th>Identificativo</th>
                                <th>Nome</th>
                                <th>Descrizione</th>
                                <th>Stato</th>
                                <th></th> 
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div id="tabDettaglio" class="col-md-12 tabbable-panel">
                
            </div>
            <!-- PULSANTI MODALE -->
            <div id="buttonEditingLayer" class="absPulsante">
                <ul id="pulsante-ricerca" class="noStyle inline pulsante-ricerca">
                    <li><a data-info="Ricerca" href="#" id="ricercaBtnEditing"><em class="fa fa-search"></em>&nbsp;&nbsp;Cerca</a></li>
                    <li><a data-info="Azzera i filtri" href="#" id="azzeraBtnEditing"><em class="fa fa-eraser"></em>&nbsp;&nbsp;Azzera</a></li>
                    <li><a data-info="Crea un nuovo layer"  href="#" id="aggiungiBtnEditing"> <em class="fa fa-plus"></em>&nbsp;&nbsp;Aggiungi</a></li>
                    <li><a data-info="Chiudi la finestra" href="#" id="chiudiBtnEditing"><em class="fa fa-times"></em>&nbsp;&nbsp;Chiudi</a></li>
                </ul>
                <ul id="pulsante-inserimento" class="noStyle inline pulsante-ricerca">
                    <li><a data-info="Salva" href="#" id="salvaBtnEditing"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
                    <li><a data-info="Indietro" href="#" id="indietroBtn"><em class="fa fa-times"></em>&nbsp;&nbsp;Annulla</a></li>
                </ul>
                <ul id="pulsante-dettaglio" class="noStyle inline pulsante-ricerca">
                    <li><a data-info="Salva" href="#" id="aggiornaBtnEditing"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
                    <li><a data-info="Indietro" href="#" id="indietroBtn"><em class="fa fa-chevron-left"></em>&nbsp;&nbsp;Indietro</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
</script>

<!--------------------------- ------------ PULSANTI TABELLE ---------------------------------------------------------------------- INIZIO --->
<script id="templatePulsantiTabellaRisultati" type="text/x-handlebars-template">
    <div class="btn-group" role="group" aria-label="Azioni">
        {{#if id}}
        <button type="button" data-id="{{id}}" data-geometry="{{geom}}" class="bttn btn-trasp modifica-btn" data-info="Anagrafica"><em class="fa fa-pencil"></em></button>
        <button type="button" data-id="{{id}}" class="bttn btn-trasp elimina-btn" data-info="Elimina"><em class="fa fa-trash"></em></button>
        {{/if}}
        {{#if geom}}
        <button type="button" data-id="{{id}}" data-geometry="{{geom}}" class="bttn btn-trasp localizza-btn" data-info="Localizza"><em class="fa fa-map-marker"></em></button>
        {{/if}}
    </div>
</script>
<!---------------------------------------- PULSANTI TABELLE ---------------------------------------------------------------------- FINE --->

<!--------------------------- INPUT TEXT TABELLA EDITING LIVELLO -------------------------------------------------------------------------- INIZIO --->
<script id="nomeTemplateTabellaGeometria" type="text/x-handlebars-template">
    <div class="input-group">
        <input type="text" class="form-control nome-txt" id="nomeGeometria_{{id}}" data-new="{{nuovo}}" placeholder="Nome..." value="{{nome}}" {{readonly}}>
    </div>
</script>
<script id="descrizioneTemplateTabellaGeometria" type="text/x-handlebars-template">
    <div class="input-group">
        <input type="text" class="form-control descrizione-txt" id="descrizioneGeometria_{{id}}" data-id="{{nuovo}}" placeholder="Descrizione..." value="{{descrizione}}" {{readonly}}>
    </div>
</script>
<!--------------------------- INPUT TEXT TABELLA EDITING LIVELLO -------------------------------------------------------------------------- INIZIO --->


<!--------------------------- DETTAGLIO LAYER -------------------------------------------------------------------------- INIZIO --->
<script id="modaleDettaglioEditing" type="text/x-handlebars-template">
    <div class="tabbable-line">
        <ul class="nav nav-tabs" id="dettaglioTab1" role="tablist">
            <li id="liDetAnagraficaTopo" class="nav-item" data-info="Anagrafica">
              <a class="nav-link active" id="anagrafica-tab1" data-toggle="tab" href="#anagraficaTabContent1" role="tab" aria-controls="anagraficaTabContent1" aria-selected="true" data-info="Anagrafica">
                  <strong>Anagrafica</strong>
              </a>
            </li>
        </ul>
    </div>
    <div class="tab-content" id="dettaglioTabContent">
        <div class="tab-pane active" id="anagraficaTabContent1" role="tabpanel" aria-labelledby="anagrafica-tab1">
            <!-- <form class="form-horizontal" id="dettaglio"></form> -->
                <div class="row nom nop">
                    <div class="form-group col-md-2">
                        <label for="identificativoLayer" class="control-label">Identificativo</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="identificativoLayer" placeholder="Identificativo..." value="{{identificativo}}" disabled>
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="nomeLayer" class="control-label">Nome *</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="nomeLayer" placeholder="Nome..." value="{{nome}}">
                        </div>
                    </div>
                    <div class="form-group col-md-2">
                        <label for="statoLayer" class="control-label">Stato</label>
                        <div class="input-group">
                            <select class="fa form-control" id="statoLayer" {{readonly}}>
                                <option value="">Seleziona...</option>
                                {{#each stati}}
                                    <option value="{{this.value}}">{{this.descrizione}}</option>
                                {{/each}}
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="descrizioneLayer" class="control-label">Descrizione</label>
                        <div class="input-group">
                            <textarea class="form-control" id="descrizioneLayer" rows="3" placeholder="Descrizione..." >{{descrizione}}</textarea>
                        <div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
                        </div>
                    </div>
                    <div class="form-group col-md-12">
                        <label for="annotazioniLayer" class="control-label">Annotazioni</label>
                        <div class="input-group">
                            <textarea class="form-control" id="annotazioniLayer" rows="3" placeholder="Annotazioni..." >{{note}}</textarea>
                        <div class="input-group-append"><span class="input-group-text"><i class="fa fa-clipboard"></i></span></div>
                        </div>
                    </div>
                    <div class="form-group col-md-12">
                        <label for="annotazioniLayer" class="control-label">Geometrie del layer</label>
                        <div class="input-group">
                            <button id="geometriaBtnEditing" type="button" class="btn btn-primary" style="float:left;width: auto;margin-bottom: 10px; display:inline;">
                                <span class="label">Aggiungi geometria</span>
                            </button>
                        
                            <div class="table-responsive">
                                <table class="table table-striped" id="tabellaGeometrieLivello" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tipo</th>
                                            <th>Nome</th>
                                            <th>Descrizione</th>
                                            <th></th> 
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            <!-- </form> -->
            </div>					
        </div>
    </div>
</script>
<!--------------------------- DETTAGLIO LAYER -------------------------------------------------------------------------- FINE --->
<script id="anagraficaTemplate" type="text/x-handlebars-template">
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="ricSpess">
            <div class="row nom nop">
                <div class="form-group col-md-12">
                    <label for="nomeGeometria" class="control-label">Nome *</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="nomeGeometria" placeholder="Nome...">
                    </div>
                </div>
                <div class="form-group col-md-12">
                    <label for="descrizioneGeometria" class="control-label">Descrizione</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="descrizioneGeometria" placeholder="Descrizione..." >
                    </div>
                </div>
            </div>
        </div>
        <div id="buttonAnagGeom" class="absPulsante">
            <ul id="pulsante-inserimento" class="noStyle inline pulsante-ricerca">
                <li><a data-info="Salva" href="#" id="salvaBtnEditing"><em class="fa fa-floppy-o"></em>&nbsp;&nbsp;Salva</a></li>
            </ul>
        </div>
    </div>
</script>