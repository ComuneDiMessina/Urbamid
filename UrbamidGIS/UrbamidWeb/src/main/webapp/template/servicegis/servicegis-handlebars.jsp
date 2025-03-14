<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<!-- ------------------------------------- TABELLA PARTICELLA SERVIZIO GIS ---------------------------------------------------------- -->
<!-- -------------------------------------------------------------------------------------------------------------------------------- -->
<script id="modServiceGis" type="text/x-handlebars-template">
<!-- TAB CONTENT PARTICELLE TERRENO -->
<div class="tab-content" id="dettaglioTabContent">
	<div class="globalContainer">
		<div class="tab-pane active" id="pianoPtTabContent1" role="tabpanel" aria-labelledby="piano-pt-tab1">
			<div class="table-responsive">
				<table class="table table-striped" id="tabellaRisultatiServiceGis" style="width:100%">
					<thead>
						<tr>
							<th>Comune</th>
							<th>Sezione</th>
							<th>Foglio</th>
							<th>Numero</th>
							<th>Subalterno</th>
							<th>Categoria</th>
							<th>Classe</th>
							<th>Consistenza (MQ)</th>
							<th>Azioni</th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>					
		</div>
		<div class="absPulsante">
			<ul class="noStyle inline pulsanti-ricerca">
				<li><a id="backBtn" href="#"><em class="fa fa-undo"></em>&nbsp;&nbsp;Indietro</a></li>
				<li><a id="inviaBtn" href="#"><em class="fa fa-paper-plane"></em>&nbsp;&nbsp;Invia</a></li>
			</ul>
		</div>
	</div>
</div>	
</script>
