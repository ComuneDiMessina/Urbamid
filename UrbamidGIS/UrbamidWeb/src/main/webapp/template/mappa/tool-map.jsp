


<div class="ol-maps ol-control ol-selectable">
	<button title="" id="mappeBtn" class="btnToggle" type="button" onclick="appMappa.toggleTool('mapsChoice', event)">
		<em class="fa fa-map-o" data-info="Mappe"></em>
	</button>
</div>
<div class="ol-layers ol-control ol-selectable">
	<button title="" id="catalogoBtn" class="btnToggle" type="button" onclick="appMappa.toggleTool('catalogo', event)">
		<em class="fa fa-book" data-info="Catalogo"></em>
	</button>
</div>
<div class="ol-gistool ol-control ol-selectable">
	<button title="" id="gisToolContainerBtn" class="btnToggle" type="button" onclick="appMappa.toggleTool('gisToolContainer', event)">
		<em class="fa fa-gear" data-info="Strumenti mappa"></em>
	</button>
</div>
<div class="ol-searchtool ol-control ol-selectable">
	<button title="" id="searchToolContainerBtn" class="btnToggle" type="button" onclick="appMappa.toggleTool('searchToolContainer', event)">
		<em class="fa fa-search" data-info="Ricerche"></em>
	</button>
</div>
<div class="ol-printtool ol-control ol-selectable">
	<button title="" id="printToolContainerBtn" class="btnToggle" type="button" onclick="appMappa.toggleTool('printToolContainer', event)">
		<em class="fa fa-print" data-info="Stampa"></em>
	</button>
</div>
<div class="ol-infotool ol-control ol-selectable">
	<button title="" id="infoLayerContainerBtn" class="btnToggle" type="button" onclick="appMappa.toggleTool('infoLayerContainer', event);appMappa.abilitaInfoMappa()">
		<em class="fa fa-info" data-info="Info su mappa"></em>
	</button>
</div>

<div>
	<!-- CONTAINER PER LE MAPPE
	<div id="mappe" class="boxMappa hidden" style="height: 65%;">
		<div class="boxMappa_heading">
			<i class="fa fa-map-o view" aria-hidden="true"></i>
			<span class="label">
				Mappe
				<i class="fa fa-times" onclick="appMappa.toggleTool('mappe', event)" id="mappeClose" data-info="Chiudi Mappe"></i>
			</span>
		</div>
		<div class="boxMappa_body">
			<div class="panel-group" id="accordionLB" role="tablist" aria-multiselectable="true">
				<div id="elenco-mappe-content" class="elenco-mappe-content"></div>
			</div>
		</div>
	</div>
	 -->
	<!-- CONTAINER PER LE MAPPE -->
	<div id="mapsChoice" class="boxMappaChoice hidden" style="overflow-y: scroll;height: 100%;">
		<!-- Lista layer -->
		<div class="boxMappa_body">
			<div class="panel-group" id="accordionLB" role="tablist" aria-multiselectable="true">
				<div id="elenco-mappe-choice-content" class="elenco-mappe-content"></div>
			</div>
		</div>
	</div>
	
	<!-- CONTAINER PER IL CATALOGO -->
	<div id="catalogo" class="boxMappa hidden">
		<!-- Title layer -->
		<div class="boxMappa_heading">
			<i class="fa fa-book view" aria-hidden="true"></i>
			<span class="label">
				Catalogo
				<i class="fa fa-times" onclick="appMappa.toggleTool('catalogo', event)" id="catalogoClose" data-info="Chiudi Catalogo"></i>
			</span>
		</div>
		<!-- Lista layer -->
		<div class="boxMappa_body">
			<div class="panel-group p10" id="accordionLB" role="tablist" aria-multiselectable="true">
				<div id="menu-content"></div>
			</div>
		</div>
	</div>

	<!-- CONTAINER DEL GIS -->		
	<div id="gisToolContainer" class="boxMappa hidden">
		<div class="boxMappa_heading">
			<i class="fa fa-cog view" aria-hidden="true"></i>
			<span class="label">
				Gis Tool
				<i class="fa fa-times" onclick="appMappa.toggleTool('gisToolContainer', event)" aria-hidden="true" data-info="Chiudi Strumenti"></i>
			</span>
		</div>
		<div id="gisTool" class="gisTool boxMappa_body"></div>
		<!-- Info posizione geolocalizzazione utente  -->
		<div id="geolocateUser"></div>
	</div>

	<!-- CONTAINER PER LE RICERCHE -->
	<div id="searchToolContainer" class="boxMappa hidden">
		<div class="boxMappa_heading">
			<i class="fa fa-search view" aria-hidden="true"></i>
			<span class="label">
				Cerca
				<i class="fa fa-times" onclick="appMappa.toggleTool('searchToolContainer', event)" data-info="Chiudi Ricerche"></i>
			</span>
		</div>
		<div class="searchTool boxMappa_body">
			<div id="ricercaParticella" class="boxScale"></div>
			<div id="ricercaFoglio" class="boxScale"></div>
			<div id="ricercaToponimo" class="boxScale"></div>
		</div>
		<div id="risultatiRicerca" class="boxScale boxMappa_table">
			<div class="table-responsive">
				<table id="resultRicercaParticella" class="table table-striped" style="width: 100%"></table>
				<table id="resultRicercaFoglio" class="table table-striped" style="width: 100%"></table>
				<table id="resultRicercaToponimo" class="table table-striped" style="width: 100%"></table>
			</div>
		</div>
	</div>
	
	<!-- CONTAINER PER LA STAMPA -->
	<div id="printToolContainer" class="boxMappa hidden">
		<div class="boxMappa_heading">
			<i class="fa fa-print view" aria-hidden="true"></i>
			<span class="label">
				Stampa
				<i class="fa fa-times" onclick="appMappa.toggleTool('printToolContainer', event)" data-info="Chiudi Stampa"></i>
			</span>
		</div>
		<div class="printTool boxMappa_body">
			<div class="row">
				<label>Formato </label>
			  	<select title="Formato del foglio" id="format">
				    <option value="a0">A0 (slow)</option>
			    	<option value="a1">A1</option>
			    	<option value="a2">A2</option>
			    	<option value="a3">A3</option>
			    	<option value="a4" selected="">A4</option>
			    	<option value="a5">A5 (fast)</option>
			  	</select>
			</div>
			<div class="row">
			  	<label>Risoluzione </label>
			  		<select title="Risoluzione della mappa" id="resolution">
					    <option value="72">72 dpi (fast)</option>
			    	<option value="150">150 dpi</option>
			    	<option value="300">300 dpi (slow)</option>
			  	</select>
			</div>
			<div class="absPulsante">
				<ul class="noStyle inline">
					<li><a data-info="Stampa" href="#" id="stampaBtn" onclick="appMappa.print()"><em class="fa fa-print"></em>&nbsp;&nbsp;Stampa</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- CONTAINER PER LE INFO DEL LAYER -->
	<div id="infoLayerContainer" class="boxMappa hidden" style="padding-bottom: 25px;">
		<!-- Title layer -->
		<div class="boxMappa_heading">
			<i class="fa fa-book view" aria-hidden="true"></i>
			<span class="label">
				Informazioni
				<i class="fa fa-times" onclick="appMappa.toggleTool('infoLayerContainer', event)" data-info="Chiudi Informazioni"></i>
			</span>
		</div>
		<!-- Lista Layer Info -->
		<div class="boxMappa_body">
			<div class="panel-group">
				<div id="layer-info-content" class="panel panel-default">
					<h5>Seleziona un punto sulla mappa</h5>
				</div>
			</div>
		</div>
	</div>
</div>