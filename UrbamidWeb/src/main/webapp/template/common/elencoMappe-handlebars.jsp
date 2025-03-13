<script id="elencoMappeTemplate" type="text/x-handlebars-template">
<div class="panel-group p10 clearfix" id="accordionLB" role="tablist" aria-multiselectable="true">
	<div class="panel panel-default">
		{{#temiHm}}
			<div class="panel-heading clearfix" role="tab" id="headingLB" style="margin-bottom: 4px;">
				<h4 class="panel-title" style="margin-bottom: 0px;">
					<a id="visualizza{{id}}" role="button" data-toggle="collapse" data-parent="#accordionLB" href="#visualizza{{id}}" aria-expanded="false" 
							class="collapsed" aria-controls="collapseLB" title="{{nomeTema}}">
						{{nomeTema}}
					</a>
				</h4>
			</div>
			<div id="visualizza{{id}}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingLB">
				{{#mappe}}
					<div class="itemMappa" onclick="appUtil.confirmOperation(appUrbamid.applicaMappa, null, '{{codeMappa}}', 'Vuoi caricare la mappa selezionata?')">
						<em class="iconMappa fa fa-map-o" class="fa fa-map-o" title="Mappe"></em>
						<a class="linkItemMappa" href="#" title="{{title}}">{{title}}</a>
						<i class="fa fa-plus" aria-hidden="true" title="Aggiungi al visualizzatore"></i>
					</div>
				{{/mappe}}
			</div>
		{{/temiHm}}
	</div>
</div>
</script>

<script id="elencoMappeChoiceTemplate" type="text/x-handlebars-template">
	<div id="boxElencoMappeChoice" class="boxMappa_body">
		<div class="panel panel-default">
			{{#temiHm}}
				<div class="panel-heading clearfix" role="tab" id="headingLB" style="margin-bottom: 4px;">
					<h4 class="panel-title" style="margin-bottom: 0px;">
						<a id="visualizza{{id}}" role="button" data-toggle="collapse" data-parent="#accordionLB" href="#visualizza{{id}}" aria-expanded="false" 
								class="collapsed" aria-controls="collapseLB" title="{{nomeTema}}">
							{{nomeTema}}
						</a>
					</h4>
				</div>
				<div id="visualizza{{id}}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingLB">
					{{#mappe}}
						<div class="itemMappa" onclick="appUtil.confirmOperation(appUrbamid.applicaMappa, null, '{{codeMappa}}', 'Vuoi caricare la mappa selezionata?')">
							<em class="iconMappa fa fa-map-o" class="fa fa-map-o" title="Mappe"></em>
							<a class="linkItemMappa" href="#" title="{{title}}">{{title}}</a>
							<i class="fa fa-plus" aria-hidden="true" title="Aggiungi al visualizzatore"></i>
						</div>
					{{/mappe}}
				</div>
			{{/temiHm}}
		</div>
	</div>
</script>