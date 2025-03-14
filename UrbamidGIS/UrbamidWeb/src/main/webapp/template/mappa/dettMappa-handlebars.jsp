<script id="sezioni-mappa-template" type="text/x-handlebars-template">
	
		<!-- SEZIONI MAPPA -->
		<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2 bg-primary aside-filters">
			<ul class="nav flex-column nav-tabs-vertical nav-tabs">
           		<li id="dati-base-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-base')">
             		<a class="nav-link" href="javascript:void(0)" >DATI DI BASE</a>
           		</li>
				{{#if (isNewMapp isNew)}}
           		<li id="dati-layer-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-layer')">
             		<a class="nav-link " href="javascript:void(0)" >LIVELLI
					</a>
           		</li>
           		<li id="dati-ricerche-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-ricerche')">
             		<a class="nav-link " href="#" >RICERCHE
             			<span id="search" class="badge" style="background-color: white;color: #004275;font-size: 14px;margin-left: 10px;">{{numSearchs}}</span>
             		</a>
           		</li>
           		<li id="dati-tools-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-tools')">
             		<a class="nav-link " href="#" >STRUMENTI
             			<span id="tool" class="badge" style="background-color: white;color: #004275;font-size: 14px;margin-left: 10px;">{{numTools}}</span>
             		</a>
           		</li>
           		<li id="dati-permessi-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-permessi')">
             		<a class="nav-link " href="#" >PERMESSI
					</a>
           		</li>
			{{/if}}
        	</ul>
		</div>
     	
		<!-- DATI DI BASE -->
		<div id="dati-base" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent">
       		<div>
           		<div class="row form-row">
               		<div class="form-group col-xs-12 col-md-6">
                       	<label for="input-1-01">Codice</label>
                       	<div class="input-group">
                           	<input type="text" class="form-control" id="code" value="{{codice}}" placeholder="Codice mappa" readonly="">
                       	</div>        
                     </div> 
               		 <div class="form-group col-xs-12 col-md-6">
                     	<label for="input-1-02">Titolo</label>
                       	<div class="input-group">
                        	<input type="text" class="form-control" id="title" value="{{nome}}" placeholder="Titolo mappa" readonly="">
                       	</div>        
                     </div>
               		 <div class="form-group col-xs-12 col-md-12">
                     	<label for="input-1-03">Descrizione</label>
                     	<div class="input-group">
                      		<input type="text" class="form-control" id="description" value="{{descrizione}}" placeholder="Descrizione mappa" readonly="">
                     	</div>
                     </div> 
						<div class="form-group col-xs-12 col-md-6">
                     	<label for="input-1-03">Stato</label>
                     	<div class="input-group">
                      	<select class="form-control mw250" id="stato" disabled>
						<option value="P" {{isPubblicato stato}}>Pubblicato</option>						
						<option value="D" {{isDisabilitato stato}}>Disabilitato</option>			
						</select>
                     	</div>
                     </div> <!-- /.col.form-group -->
  					 <div class="form-group col-xs-12 col-md-6">
					<label>Mappa Predifinita</label>
                     	<div class="input-group">
						<input type="checkbox" class="check25" name="mappaPredifinita" id="mappaPredifinita" {{isChecked mappaPredefinita}}>
						</div>
                     </div> <!--  .col.form-group -->
					{{#if (isNewMapp isNew)}}
 					<div class="form-group col-xs-12 col-md-12 line-pad-top">
                     	<label for="input-1-03">Temi</label>
                     	<div class="input-group">
                      		<div class="col-xs-12 col-md-12" id="boxtemi"> </div>
                     	</div>
					
                     </div> <!--  .col.form-group -->
					<div class="form-group col-xs-12 col-md-12">
                     	<label for="input-1-03"></label>
                     	<div class="input-group">
                      		<input type="button" class="form-control mw130" id="btnAddTema" onclick="appGestMappe.showTemi()" value="Seleziona" disabled>
                     	</div>
                     </div> <!--  .col.form-group -->
					{{/if}}
       			</div>
     		</div>
		</div>
	
		<!-- LAYER -->
		<div id="dati-layer" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none;margin-top: -20px;">
       		<div>
           		<div class="row form-row">
					<div class="form-group col-xs-12 col-md-8">
                       	<div class="boxMappa_heading">
							<i class="fa view" aria-hidden="true"></i>
							<span class="label">Catalogo dei livelli</span>
						</div>
						<!-- CONTAINER PER IL CATALOGO -->
						<div id="menu-content"></div>
                     </div> <!-- /.col.form-group -->
					

<div class="form-group col-xs-12 col-md-4">

<!-- Title layer -->
	<div class="boxMappa_heading">
		<i class="fa view" aria-hidden="true"></i>
		<span class="label">
			Livelli Selezionati
		</span>
	</div>
	<div class="col-xs-12 col-md-12" style="margin-top:15px;padding:0px 5px;">
		<button type="button" class="btn btn-primary btnNuovoGruppo" onclick="appGestMappe.showModalNuovoGruppo()" disabled="disabled" style="width: 28%;float: left;margin: 0px;">
			<span class="label">Nuova Tavola</span>
	 	</button>
		<div id="selectGroupTable" style="width: 38%;height: 34 px;float: left;margin: 0px 7px;"></div>
		<button type="button" class="btn btn-primary btnImportaGruppo" onclick="appGestMappe.copyTavole()" disabled="false" style="float: left;width: 30%;margin: 0px;">
	 		<span class="label">Importa Tavola</span>
 		</button>
	</div>
	<!-- Lista layer -->
	<div class="boxMappa_body" id="boxGruppiLayer"></div>
	<!-- end layer -->				
</div>
<div class="col-xs-12 col-md-12" style="margin-top:10px;padding-bottom:30px;"><!--OPZIONI cat_visible - zoom -->
<div class="col-xs-12 col-md-3" style="float: left;">
<label for="mappa_catalogo_visible">Catalogo visibile in mappa</abel>
<input type="checkbox" {{isChecked showCatalog}} title="Catalogo visibile in mappa" id="mappa_catalogo_visible" style="width: 20px;height: 20px;float: left;margin-right: 10px;">
</div>
<div class="col-xs-12 col-md-5" style="float: left;">
<label style="float: left;">Scala di riferimento <span id="mappa_label_zoom">{{zoomLabel}}</span></label>
<input type="range" title="Scala di riferimento" class="custom-range ui-slider ui-corner-all ui-slider-horizontal ui-widget ui-widget-content" id="mappa_livello_zoom" min="10" max="19" step="1" value="0" onchange="appGestMappe.changeZoom()" style="width: 250px;margin-left: 10px;">
</div>
</div><!--END OPZIONI cat_visible - zoom -->

       			</div><!--ROW -->
     		</div>
		</div><!--END LAYER -->
	
		<!-- RICERCHE -->
		<div id="dati-ricerche" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none">
       		<div class="row">
           		<div class="col-sm-5 col-lg-3">
					<div class="list-group" id="searchList">
						<a href="#" class="list-group-item active" style="border-radius: 0;">RICERCHE DISPONIBILI</a>
						{{#searchListKo}}
							<div class="input-group">
  								<div class="input-group-prepend">
    								<div class="input-group-text" style="border-radius: 0;">
      									<input id="checkbox-{{id}}" type="checkbox" aria-label="Checkbox for following text input" style="border-radius: 0;">
    								</div>
  								</div>
								<label class="form-control" aria-label="Text input with checkbox" style="border-radius: 0;">{{title}}</label>
							</div>
						{{/searchListKo}} 
					</div>
				</div>
				<div class="col-sm-2 text-center">
					<button title="Attiva la ricerca" class="btn btn-default center-block addElem" disabled>
						<i class="fa fa-arrow-right"></i>
					</button>
					<button title="Disattiva la ricerca" class="btn btn-default center-block removeElem" disabled>
						<i class="fa fa-arrow-left"></i>
					</button>
				</div>
				<div class="col-sm-5 col-lg-3">
					<div class="list-group" id="ricercheAttiveList">
						<a href="#" class="list-group-item active" style="border-radius: 0;">RICERCHE ATTIVE</a>
						{{#searchListOk}}
							<div class="input-group">
  								<div class="input-group-prepend">
    								<div class="input-group-text" style="border-radius: 0;">
      									<input id="checkbox-{{id}}" type="checkbox" aria-label="Checkbox for following text input">
    								</div>
  								</div>
								<label class="form-control" aria-label="Text input with checkbox" style="border-radius: 0;">{{title}}</label>
							</div>
						{{/searchListOk}} 
					</div>
				</div>
     		</div>
		</div>
	
		<!-- TOOLS -->
		<div id="dati-tools" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none">
       		<div class="row">
           		<div class="col-sm-5 col-lg-3">
					<div class="list-group" id="toolList">
						<a href="#" class="list-group-item active" style="border-radius: 0;">TOOL DISPONIBILI</a>
						{{#toolListKo}}
							<div class="input-group">
  								<div class="input-group-prepend">
    								<div class="input-group-text" style="border-radius: 0;">
      									<input id="checkbox-{{id}}" type="checkbox" aria-label="Checkbox for following text input">
    								</div>
  								</div>
								<label class="form-control" aria-label="Text input with checkbox" style="border-radius: 0;">{{title}}</label>
							</div>
						{{/toolListKo}} 
					</div>
				</div>
				<div class="col-sm-2 text-center">
					<button title="Attiva il Tool" class="btn btn-default center-block addElem" disabled>
						<i class="fa fa-arrow-right"></i>
					</button>
					<button title="Disattiva il Tool" class="btn btn-default center-block removeElem" disabled>
						<i class="fa fa-arrow-left"></i>
					</button>
				</div>
				<div class="col-sm-5 col-lg-3">
					<div class="list-group" id="toolAttiviList">
						<a href="#" class="list-group-item active" style="border-radius: 0;">TOOL ATTIVI</a>
						{{#toolListOk}} 
							<div class="input-group">
  								<div class="input-group-prepend">
    								<div class="input-group-text" style="border-radius: 0;">
      									<input id="checkbox-{{id}}" type="checkbox" aria-label="Checkbox for following text input">
    								</div>
  								</div>
								<label class="form-control" aria-label="Text input with checkbox" style="border-radius: 0;">{{title}}</label>
							</div>
						{{/toolListOk}} 
					</div>
				</div>
     		</div>
		</div>

		<!-- PERMESSI -->
		<div id="dati-permessi" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none">
			<div class="data-table-content">
				<div class="container-fluid">
					<div class='row form-row' style='height: 36px;border-bottom: 1px solid #333;background-color: #f3f3f3;padding: 7px 0 7px 40px;text-transform: uppercase;font-weight: bold;border-top: 1px solid #333;'>
						<div class="form-group col-xs-6 col-md-4 col-lg-6"></div>			
						<div class="form-group col-xs-6 col-md-4 col-lg-6" style="text-align:center;">VISUALIZZAZIONE</div>
					</div>
					{{#ruoliList}}
					<div id="{{id}}" class="row form-row" style="padding: 10px 0px;height: 36px;border-bottom: 1px solid #e9ecef;">
                        <div class="form-group col-xs-6 col-md-4 col-lg-6">{{denominazione}} ({{codice}})</div>
                        <div class="form-group col-xs-6 col-md-4 col-lg-6" style="text-align:center;">
	                    	<input id="perm-{{id}}" "+checked+" type="checkbox" onclick="(appGestMappe).changePermesso({{id}})">
                        </div>
                    </div>
					{{/ruoliList}}
				</div>
			</div>
		</div>
</script>

<script id="permessiLayer" type="text/x-handlebars-template">
<div class="col-md-12">	
	<div class="data-table-content">			
		<div class="row form-row" style="height: 36px;border-bottom: 1px solid #333;background-color: #f3f3f3;padding: 7px 0 7px 40px;text-transform: uppercase;font-weight: bold;border-top: 1px;">				
			<div class="form-group col-6"></div>				
			<div class="form-group col-3" style="text-align:center;">VISUALIZZAZIONE</div>				
			<div class="form-group col-3" style="text-align:center;">EDITING</div>			
		</div>
	</div>
	{{#ruoliList}}
	<div id="{{id}}" class="row form-row" style="padding: 10px 0px;height: 36px;border-bottom: 1px solid #e9ecef;">	
		<div class="form-group col-6">{{denominazione}} ({{codice}})</div>	
		<div class="form-group col-3" style="text-align: center; padding-left: 3%">		
			<input id="perm-layer-{{id}}" {{checkedVisualizza}} type="checkbox" onclick="appGestMappe.changeVisualizzaLayer({{id}})"></div> 	
		<div class="form-group col-3" style="text-align: center; padding-left: 2%">		
			<input id="editing-layer-{{id}}" {{checkedModifica}} type="checkbox" style="text-align: center" onclick="(appGestMappe).changeModificaLayer({{id}})">	
		</div>
	</div>
	{{/ruoliList}}
</div>
</script>
<script id="htmlBoxGruppoLayer" type="text/x-handlebars-template">
	<div onclick="appGestMappe.setLayerGrupSelezionato('{{idParent}}','{{groupName}}')" class="panel-group p10 clearfix" id="accordion_{{idParent}}" role="tablist" aria-multiselectable="true">
			<input type="hidden" id="current_{{idParent}}" value="{{groupName}}">
			<div class="panel panel-default">
				<div class="panel-heading gruppo-layer" role="tab" id="heading_{{idParent}}">
					<h4 class="panel-title">
						<div id="viewAll{{idParent}}" class="viewAll" title="Visualizza tutti i layer per il gruppo" onclick="">
							<i class="fa fa-object-group" aria-hidden="true"></i>
						</div>
						<a id="visualizzatore{{idParent}}" role="button" data-toggle="collapse" data-parent="#accordion_{{idParent}}" href="#layers{{idParent}}" aria-expanded="true" aria-controls="collapse{{idParent}}" title="Chiudi gruppo layer" style="width: 90%;">
							{{groupName}}
						</a>
<div class="toolbar">
			<div class="btn-group" title="Opzioni" style="float: right;margin-top: -28px;margin-right: 0px;">
		  		<button type="button" class="btn btn-secondary dropdown-toggle iconLegend" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="Opzioni layer" style="background: transparent;">
    				<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
  				</button>
  				<div class="dropdown-menu dropdown-menu-right" style="">
						<a style="color: #000;font-size: 14px;font-weight: 100;" class="dropdown-item" href="javascript:void(0)" onclick="appGestMappe.eliminaGruppo('{{idMappa}}','{{groupName}}','accordion_{{idParent}}','{{idParent}}')" title="Elimina">
							<i class="fa fa-trash-o" aria-hidden="true"></i>
							Elimina
						</a>
					<a style="color: #000;font-size: 14px;font-weight: 100;" class="dropdown-item" href="javascript:void(0)" onclick="appGestMappe.modificaGruppo('{{idMappa}}','{{groupName}}','visualizzatore{{idParent}}','{{idParent}}')" title="Modifica">
						<i class="fa fa fa-cog" aria-hidden="true"></i>
						Modifica
					</a>
				</div>
			</div>
</div>
					</h4>
				</div>
				<div id="layers{{idParent}}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading_{{idParent}}">
					<div id="default-layer-{{idParent}}" class="panel-body p7">
					</div>
				</div>
			</div>
		</div>
</script>

<script id="itemGruppoLayer" type="text/x-handlebars-template">
<div id="itemViewer_{{nomeLayer}}_{{idParent}}" class="itemViewerContainer">	
<div id="{{nomeLayer}}_divchk" class="checkbox itemVisualizzatore">
		<label  onclick="appGestMappe.setAbilitato('{{nomeLayer}}','{{idParentNonReplace}}', '{{idMappa}}','{{idParent}}_{{nomeLayer}}_chk','{{idParent}}_{{nomeLayer}}_chkImg')">
			<input id="{{idParent}}_{{nomeLayer}}_chk" type="checkbox" value=""></input>
			<span class="cr" title="{{titleLayer}}"><i id="{{idParent}}_{{nomeLayer}}_chkImg" class="{{isAbilitato abilitato}}"></i></span>
		</label>
		<span class="layerName" title='{{titleLayer}}'>{{titleLayer}}</span>
		<div class="toolbar">
			<div class="btn-group hidden" title="Legenda">		
				<button type="button" class="btn btn-secondary iconLegend" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
						onclick="appGestMappe.showLegend('{{idParent}}_{{nomeLayer}}')">
    				<i class="fa fa-file-image-o" aria-hidden="true"></i>
  				</button>
			</div>
			<div class="btn-group" title="Opzioni">
		  		<button type="button" class="btn btn-secondary dropdown-toggle iconLegend" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="Opzioni layer">
    				<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
  				</button>
  				<div class="dropdown-menu dropdown-menu-right">
						<a class="dropdown-item" href="javascript:void(0)" onclick="appGestMappe.removeLayer('{{nomeLayer}}','{{idMappa}}','{{idParentNonReplace}}','itemViewer_{{nomeLayer}}_{{idParent}}')" title="Elimina">
							<i class="fa fa-trash-o" aria-hidden="true"></i>
							Elimina
						</a>
					 
					<a class="dropdown-item" href="javascript:void(0)" onclick="appGestMappe.showOpacity('{{idParent}}_{{nomeLayer}}')" title="Trasparenza">
						<i class="fa fa fa-cog" aria-hidden="true"></i>
						Trasparenza
					</a>
					 
				</div>
			</div>
		</div>
	</div>
	<div id="{{idParent}}_{{nomeLayer}}_divlegend" class="hidden itemLegenda">
		<span>Legenda</span>
		<!--img src="hrefLegend"></img-->
	</div>
	<div id="{{idParent}}_{{nomeLayer}}_divgear" class="hidden itemGear">
		<span class="title-opa">Trasparenza</span>
		<span id="{{idParent}}_{{nomeLayer}}_value-opa" class="value-opa" title="Valore trasparenza">{{trasparenza}}%</span>
		<span class="close-opa"><i class="fa fa-times" onclick="appGestMappe.hideOpacity('{{idParent}}_{{nomeLayer}}')" id="catalogoClose" data-info="Chiudi Catalogo"></i></span>
		<input type="range" title="Modifica trasparenza layer" class="custom-range" id="{{idParent}}_{{nomeLayer}}_slider"  min="0" max="10" value="{{trasparenza}}" onchange="appGestMappe.changeOpacity('{{nomeLayer}}','{{idParentNonReplace}}','{{idMappa}}','{{idParent}}_{{nomeLayer}}')">
	</div>
</div>
</script>

<script id="itemGruppoTavole" type="text/x-handlebars-template">
	<select class="form-control" id="importaGruppiSlc" name="importaGruppiSlc" style="width: 100%;float: right;height: 34px;padding: 2px;" disabled="false">
		<option value="" selected="">Seleziona...</option>
		{{#each this}}
		<optgroup label="{{ this.tavoleGroup }}" id="{{@key}}">
			{{#each this.tavole}}
			<option id="{{@key}}" data-mappa="{{../this.idMappa}}" value="{{this}}" >{{this}}</option>
			{{/each}}
		</optgroup>
		{{/each}}
	</select>
</script>
