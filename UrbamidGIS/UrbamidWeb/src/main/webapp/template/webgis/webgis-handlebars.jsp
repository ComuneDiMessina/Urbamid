<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<!-- ------------------------------------- RICERCA PARTICELLA ----------------------------------------------------------------------- -->
<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<script id="ricParticella" type="text/x-handlebars-template">
	<div id="ricercaParticellaBox" class="" role="tabpanel" aria-labelledby="headingLB">
		<div class="panel-heading" role="tab" id="headingLB" data-toggle="form-search-panel">
			<h4 class="panel-title" title="Ricerca particella">
				<div class="icon"><i class="fa fa-arrow-right" aria-hidden="true"></i></div>
				<a role="button" data-toggle="collapse" data-parent="#headingLB" href="#ricercaParticelleForm" aria-expanded="false" class="collapsed" aria-controls="collapseLB">Ricerca Particella</a>
			</h4>
		</div>
		<div id="ricercaParticelleForm" class="form-search-panel panel-collapse collapse" role="tabpanel" aria-labelledby="headingLB">
			<div class="input-group p10">
				<div class="input-group-prepend"><span class="input-group-text"><i class="fa fa-align-left"></i></span></div> 
				<input id="partFoglio-rp" type="text" class="form-control" placeholder="Foglio" aria-describedby="basic-addon1" title="Inserisci Foglio">
				<div class="input-group-append deleteField" onclick="deleteField('partFoglio-rp')" title="Cancella campo"><span class="input-group-text"><i class="fa fa-close"></i></span></div>				
			</div>
			<div class="input-group p10">
				<div class="input-group-prepend"><span class="input-group-text"><i class="fa fa-align-left"></i></span></div> 
				<input id="partMappale-rp" type="text" class="form-control" placeholder="Mappale" aria-describedby="basic-addon1" title="Inserisci Mappale">
				<div class="input-group-append deleteField" onclick="deleteField('partMappale-rp')" title="Cancella campo"><span class="input-group-text"><i class="fa fa-close"></i></span></div>				
			</div>
			<div class="col-md-12 nop text-center">
				<button id="btnRicercaCatastale" type="button" class="bttn p412 bttnRed" onclick="ricCatastale.cerca();" title="Cerca">Cerca</button>
			</div>
		</div>
	</div>
</script>
<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<!-- ------------------------------------- RICERCA FOGLIO --------------------------------------------------------------------------- -->
<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<script id="ricFoglio" type="text/x-handlebars-template">
	<div id="ricercaFoglioBox" class="" role="tabpanel" aria-labelledby="headingLB">
		<div class="panel-heading" role="tab" id="headingLB">
			<h4 class="panel-title" title="Ricerca Foglio">
				<div class="icon"><i class="fa fa-arrow-right" aria-hidden="true"></i></div>
				<a role="button" data-toggle="collapse" data-parent="#headingLB" href="#ricercaFoglioForm" aria-expanded="false" class="collapsed" aria-controls="collapseLB">Ricerca Foglio</a>
			</h4>
		</div>
		<div id="ricercaFoglioForm" class="form-search-panel panel-collapse collapse" role="tabpanel" aria-labelledby="headingLB">
			<div class="input-group p10">
				<div class="input-group-prepend"><span class="input-group-text"><i class="fa fa-align-left"></i></span></div> 
				<input id="partFoglio-rf" type="text" class="form-control" placeholder="Foglio" aria-describedby="basic-addon1" title="Inserisci Foglio">
				<div class="input-group-append deleteField" onclick="deleteField('partFoglio-rf')" title="Cancella campo"><span class="input-group-text"><i class="fa fa-close"></i></span></div>				
			</div>
			<div class="col-md-12 nop text-center">
				<button id="btnRicercaCatastale" type="button" class="bttn p412 bttnRed" onclick="ricFoglio.cerca();" title="Cerca">Cerca</button>
			</div>
		</div>
	</div>
</script>
<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<!-- ------------------------------------- RICERCA TOPONIMO ------------------------------------------------------------------------- -->
<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<script id="ricToponimo" type="text/x-handlebars-template">
	<div id="ricercaToponimoBox" class="" role="tabpanel" aria-labelledby="headingLB">
		<div class="panel-heading" role="tab" id="headingLB">
			<h4 class="panel-title" title="Ricerca Toponimo">
				<div class="icon"><i class="fa fa-arrow-right" aria-hidden="true"></i></div>
				<a role="button" data-toggle="collapse" data-parent="#headingLB" href="#ricercaToponimoForm" aria-expanded="false" class="collapsed" aria-controls="collapseLB">Ricerca Toponimo</a>
			</h4>
		</div>
		<div id="ricercaToponimoForm" class="form-search-panel panel-collapse collapse" role="tabpanel" aria-labelledby="headingLB">
			<div class="input-group p10">
				<div class="input-group-prepend"><span class="input-group-text"><i class="fa fa-align-left"></i></span></div> 
				<input id="partToponimo-rt" type="text" class="form-control" placeholder="Cerca Toponimo" aria-describedby="basic-addon1" title="Inserisci Toponimo">
				<div class="input-group-append deleteField" onclick="deleteField('partToponimo-rt')" title="Cancella campo"><span class="input-group-text"><i class="fa fa-close"></i></span></div>				
			</div>
			<div class="col-md-12 nop text-center">
				<button id="btnRicercaTopLoc" type="button" class="bttn p412 bttnRed" onclick="ricToponimo.cerca();" title="Cerca">Cerca</button>
			</div>
		</div>
	</div>
</script>





<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<!-- ------------------------------------- RISULTATI RICERCA ------------------------------------------------------------------------ -->
<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<script  id="risultatiRic" type="text/x-handlebars-template">
	<div class="table-responsive">
	{{#ifEquals tipoRisultato "particella"}}
		<table id="resultRicercaParticella" class="table table-striped table-result">
			<thead class="">
				<tr>	
					<th>codice catastale comune</th>
					<th>Numero sezione</th>	
					<th>numero foglio</th>
					<th>numero mappale</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr id="index" class="odd" >
					<td>F158</td><td>nd</td><td>102</td><td>85</td>
					<td class="toMap" title="Seleziona per visualizzare su mappa" onclick="ricCatastale.showRicercaCatastaleFeature(2)"><i class="fa fa-map-marker" aria-hidden="true"></i></td>
				</tr>
			</tbody>
		</table>
	{{/ifEquals}}	
	{{#ifEquals tipoRisultato "foglio"}}
		<table id="resultRicercaFoglio" class="table table-striped table-result">
			<thead class="">
				<tr>	
					<th>codice catastale comune</th>
					<th>Numero sezione</th>	
					<th>numero foglio</th>
					<th>allegato</th>
					<th>Origine</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr id="index" class="odd" >
					<td>F158</td><td>nd</td><td>112</td><td>A</td><td>nd</td>
					<td class="toMap" title="Seleziona per visualizzare su mappa" onclick="ricFoglio.showRicercaFoglioFeature(index)"><i class="fa fa-map-marker" aria-hidden="true"></i></td>
				</tr>
				<tr id="index" class="odd" >
					<td>F158</td><td>nd</td><td>122</td><td>A</td><td>nd</td>
					<td class="toMap" title="Seleziona per visualizzare su mappa" onclick="ricFoglio.showRicercaFoglioFeature(index)"><i class="fa fa-map-marker" aria-hidden="true"></i></td>
				</tr>
				<tr id="index" class="odd" >
					<td>F158</td><td>nd</td><td>123</td><td>A</td><td>nd</td>
					<td class="toMap" title="Seleziona per visualizzare su mappa" onclick="ricFoglio.showRicercaFoglioFeature(index)"><i class="fa fa-map-marker" aria-hidden="true"></i></td>
				</tr>
			</tbody>
		</table>
	{{/ifEquals}}	
	{{#ifEquals tipoRisultato "toponimo"}}
		<table id="resultRicercaToponimo" class="table table-striped table-result">
			<thead class="">
				<tr>	
					<th>Localita'</th>
					<th>Indirizzo</th>
					<th></th>	
				</tr>
			</thead>
			<tbody>
				<tr id="index" class="odd" >
					<td>Via Pippo Romeo</td><td>Messina</td>
					<td class="toMap" title="Seleziona per visualizzare su mappa" onclick="ricToponimo.showRicercaToponimoFeature(1)"><i class="fa fa-map-marker" aria-hidden="true"></i></td>
				</tr>
				<tr id="index" class="odd" >
					<td>Via Cesare Battisti</td><td>Messina</td>
					<td class="toMap" title="Seleziona per visualizzare su mappa" onclick="ricToponimo.showRicercaToponimoFeature(2)"><i class="fa fa-map-marker" aria-hidden="true"></i></td>
				</tr>
				<tr id="index" class="odd" >
					<td>Via Ghibellina</td><td>Messina</td>
					<td class="toMap" title="Seleziona per visualizzare su mappa" onclick="ricToponimo.showRicercaToponimoFeature(3)"><i class="fa fa-map-marker" aria-hidden="true"></i></td>
				</tr>
			</tbody>
		</table>
	{{/ifEquals}}
	</div>	
</script>
