<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<!-- ------------------------------------- ELEMENTO LEGENDA ------------------------------------------------------------------------- -->
<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<script id="itemViewer" type="text/x-handlebars-template">
	<div id="{{itemCheckId}}_divchk" class="checkbox itemVisualizzatore">
		<label onchange="appMappa.addLayerMap('{{layerName}}', '{{title}}', null, '{{idParent}}', '{{itemCheckId}}')">
			<input id="{{itemCheckId}}{{countLayer}}_chk" data-gruppo="{{idParent}}" type="checkbox" value=""></input>
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
					{{#if (isVisible visibilita)}}
						<a class="dropdown-item" href="#" onclick="appUrbamid.removeLayerVisualizzatore('{{layerName}}', '{{idParent}}')" title="Elimina">
							<i class="fa fa-trash-o" aria-hidden="true"></i>
							Cancella
						</a>
					{{/if}}
					<a class="dropdown-item" href="#" onclick="appUrbamid.showOpacity('{{itemCheckId}}', '{{opacity}}', '{{layerName}}')" title="Trasparenza">
						<i class="fa fa fa-cog" aria-hidden="true"></i>
						Trasparenza
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- <div id="{{itemCheckId}}{{suffixGraf}}_divlegend" class="hidden itemLegenda">
		<span>Legenda</span>
		<img src="{{hrefLegend}}"></img>
	</div> -->
	<div id="{{itemCheckId}}_divgear" class="hidden itemGear">
		<span class="title-opa">Trasparenza</span>
		<span id="value-opa" class="value-opa" title="Valore trasparenza">{{opacityPercent}}%</span>
		<span class="close-opa"><i class="fa fa-times" onclick="appUrbamid.hideOpacity('{{itemCheckId}}')" id="catalogoClose" data-info="Chiudi Catalogo"></i></span>
		<input type="range" title="Modifica trasparenza layer" class="custom-range" id="{{itemCheckId}}_slider"  min="0" max="10" value="{{opacity}}" onchange="appUrbamid.changeOpacity('{{layerName}}','{{itemCheckId}}')">
	</div>
</script>
 
<!-- <script id="buttonMinifizeVisualizzatore" type="text/x-handlebars-template">
	<button type=button onclick=minifize("visualizzatore") class="ui-dialog-titlebar-minifize" style="position: absolute; left: 90%"><i class="fa fa-chevron-up"></i></button>
</script> -->

<script id="itemVisualizzatore" type="text/x-handlebars-template">
	
	<!-- Title layer -->
	<!-- <div class="boxMappa_heading">
		<i class="fa view" aria-hidden="true"></i>
		<span class="label">
			Visualizzatore
			<i class="fa fa-chevron-up" onclick="appMappa.toggleVisualizzatore('visualizzatore', event)" aria-hidden="true" data-info="Riduci visualizzatore"></i>
		</span>
	</div> -->
	<!-- <button type=button onclick=minifize("visualizzatore") class="ui-dialog-titlebar-minifize" style="position: absolute; left: 90%"><i class="fa fa-chevron-up"></i></button> -->
	<!-- Lista layer -->
	<div id="boxVisualizzatoreMappa" class="boxMappa_body">
	</div>
</script>

<script id="contGruppo" type="text/x-handlebars-template">
	<div class="panel-group prl10 clearfix" id="accordionLB" role="tablist" aria-multiselectable="true">
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingLB">
				<h4 class="panel-title">
					
					<input type="checkbox" id="visualizzatoreDatiBase{{idGruppo}}_chk" style="margin-left: -100%; margin-top: 2%; width: 20px;" onchange="(appUrbamid).attivaDisattivaTuttiLayer('{{idGruppo}}')">
					
					<a id="visualizzatoreDatiBase{{idGruppo}}" style="padding: 2% 5% 0 7%" role="button" data-toggle="collapse" data-parent="#accordionLB" href="#visualizzatore{{idGruppo}}" aria-expanded="false" aria-controls="collapseLB" title="Chiudi {{nomeGruppo}}" style="padding-right: 5%;padding-left: 8%;">
						{{nomeGruppo}}
					</a>
				</h4>
				<!-- <input type="checkbox" id="isualizzatoreDatiBase{{idGruppo}}_chk" style="margin-left: -99%; margin-top: 2%"> -->
			</div>
			<div id="visualizzatore{{idGruppo}}" class="panel-collapse in collapse" role="tabpanel" aria-labelledby="headingLB">
				<div id="{{idGruppo}}-layer" class="panel-body">Nessun Livello presente nella Tavola
				</div>
			</div>
		</div>
	</div>
</script>

<script id="legendaLayers" type="text/x-handlebars-template">
	<!-- <div class="dropdown finestra mainMenu">
		<a role="button" data-toggle="dropdown" class="btn btn-primary" data-target="#" href="#">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</a>
	</div> -->
	<div class="tab-content legendaClass">
		<div id="legend">
			<span>{{title}}</span><br>
			<img src="{{hrefLegend}}" class="legendImageClass">
		</div>
	</div>
</script>

<script id="popupTemplate" type="text/x-handlebars-template">
	<!--div class="intestazione">
		<span id="titlePopupLayer">Dettaglio</span>
		<a href="#" id="popupLayer-closer" class="ol-popup-closer" onclick="appMappa.chiudiPopup()"></a>
	</div-->
	<div class="popupLayerContainer">
		<div id="popup-content">
			<h4 class="popover-title">{{layerTitle}}</h4>
			<div class="popup-detail">
				{{#if (isArrayEmpty data)}}
					<span class="emptyDetail">Nessun dettaglio per il dato richiesto</span>
				{{else}}
					{{#data}}
						<div class="row">
							<span class="labelDetail">{{label}}:&nbsp;</span>
							<span class="valueDetail">{{value}}</span>
						</div>
					{{/data}}
				{{/if}}
			</div>
		</div>
	</div>
</script>
<script id="popupCutingTemplate" type="text/x-handlebars-template">
	<div class="intestazioneCutLayer">
		<span id="check-{{idPopup}}" style="margin-right:10px;color:green;background-color:#fff;border-radius: 5px;padding: 0px 3px;display:none;"><em class="fa fa-check"></em></span>
		<span>Definizione Taglio</span>
		<a href="#" id="popupLayer-reduces" class="fa fa-chevron-down" onclick="appMappa.riduciCutLayerPopup({{idPopup}})"
				style="float:right;color:#fff;padding: 0px 5px;line-height: 20px;"></a>
	</div>
	<div class="cutLayerContainer" style="display: none;">
		<div class="popup-content">
			<div class="popup-detail">
				<div class="row nom nop">
					<div class="form-group col-md-12">
    					<label for="classeToponimo" class="control-label">Classe *</label>
    					<div class="input-group">
    						<input type="text" class="form-control" id="classeToponimoM-{{idPopup}}" placeholder="Classe..." value="{{classe}}" {{readonly}}>
							<input type="hidden" id="classeToponimoM-{{idPopup}}Value">
						</div>
					</div>
				</div>
				<div class="row nom nop">
					<div class="form-group col-md-12">
    					<label for="descrizioneToponimo" class="control-label">Denominazione Ufficiale *</label>
    					<div class="input-group">
    						<input type="text" class="form-control" id="descrizioneToponimoM-{{idPopup}}" placeholder="Denominazione Ufficiale..." value="{{descrizione}}" {{readonly}}>
						</div>
    				</div>
				</div>
				<div class="row nom nop">
					<div class="form-group col-md-12">
    					<label for="comuneToponimo" class="control-label">Comune *</label>
    					<div class="input-group">
    						<select class="fa form-control" id="comuneToponimoM-{{idPopup}}" {{disabled}}>
								<option value="">Seleziona...</option>
								{{#each comuni}}
									<option value="{{this.id}}">{{this.descrizione}}</option>
								{{/each}}
							</select>
						</div>
    				</div>
				</div>
				<div class="btn-group" style="width: 100%;padding: 0px 20px;">
					<button id="{{idPopup}}" type="button" class="btn btn-primary"><span class="label\">Definisci Anagrafica</span></button>
				</div>
			</div>
		</div>
	</div>
</script>
<script id="editingPopupTemplate" type="text/x-handlebars-template">
	<div class="intestazione">
		<span id="titlePopupLayer">Inserisci {{entita}}</span>
		<a href="#" id="popupLayer-closer" class="ol-popup-closer" onclick="appMappa.chiudiPopup()"></a>
	</div>
	<div class="popupLayerContainer" style="padding: 10px 0px;">
		<div id="popup-content">
			<div class="iziToast-buttons">
				<button id="returnAnagrafica" type="button" title="Anagrafica {{entita}}" class="btn-trasp bttn iziToast-buttons-child" 
						style="background-color: #861611;width: 98%;">
					&nbsp;Anagrafica {{entita}}
				</button>
			</div>
		</div>
	</div>
</script>

<script id="popupClusterTemplate" type="text/x-handlebars-template">
	<div class="intestazione">
		<span id="titlePopupLayer">Dettaglio </span>
		<a href="#" id="popupLayer-closer" class="ol-popup-closer" onclick="appMappa.chiudiPopupCluster()"></a>
	</div>
	{{#dataFeatures}}
		<div class="intestazioneCluster">
			<span id="titlePopupLayer"> {{name}} </span>
		</div>
		<div class="popupLayerContainer">
			<div id="popup-content">
				<div class="popup-detail">
					{{#if (isArrayEmpty data)}}
						<span class="emptyDetail">Nessun dettaglio per il dato richiesto</span>
					{{else}}
						{{#data}}
							<div class="row">
								<span class="labelDetail">{{label}}:&nbsp;</span>
								<span class="valueDetail">{{value}}</span>
							</div>
						{{/data}}
					{{/if}}
				</div>
			</div>
		</div>
	{{/dataFeatures}}
</script>

<script id="associaTemiTemplate" type="text/x-handlebars-template">
	<div class="row show-dialog-application">
	{{#aaData}}
		<div class="itemTema col-md-12">
			 <input type="checkbox" id="{{id}}" value="{{nome}}">
			 <label style="width:92%;" for="{{id}}">{{nome}}</label>
		</div>
	{{/aaData}}
</div>
</script>

<script id="temiAssociatiTemplate" type="text/x-handlebars-template">
	 <div id="boxResult" class="btn-group" style="max-width: 100%;display: contents;"> 
	{{#aaData}}
    <span class="btn btn-primary" tema-id="{{id}}" style="margin-left:8px;margin-top: 10px;color:#ffffff">{{nome}}
	<button class="btn-remove" onclick="appGestMappe.removeTema('{{id}}',this.parentElement)" aria-hidden="true">&times;</button>&nbsp;
    </span>
	{{/aaData}}
	 
	</div>
</script>

