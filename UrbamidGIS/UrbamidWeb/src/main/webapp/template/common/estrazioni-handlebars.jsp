<script id="elementiEstrazione" type="text/x-handlebars-template">
<div class="boxMappa_body" style="height: 100%;">
	<div class="panel-group" id="accordionLB" role="tablist" aria-multiselectable="true">
		<div id="elenco-mappe-content" class="elenco-mappe-content">
			<div class="panel-group p10 clearfix" id="accordionLB" role="tablist" aria-multiselectable="true" style="heigth:100%">
				<div class="panel panel-default">
					{{#tematismi}}
						<div class="panel-heading clearfix" role="tab" id="headingLB" style="margin-bottom: 4px;">
							<h4 class="panel-title" style="margin-bottom: 0px;">
								<a id="visualizza{{idTema}}" role="button" data-toggle="collapse" data-parent="#accordionLB" href="#visualizza{{idTema}}" aria-expanded="false" 
										class="collapsed" aria-controls="collapseLB" title="{{nomeTema}}">
									{{nomeTema}}
								</a>
							</h4>
						</div>
						<div id="visualizza{{idTema}}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingLB">
							{{#each layers}}
								<div class="itemMappa">
									<a class="linkItemMappa" href="#" title="{{titleLayer}}">{{titleLayer}}</a>
									<input type="checkbox" class="pull-right" name="aggiungi-layer" value="{{idLayer}} + {{titleLayer}}">
								</div>
							{{else}}
								<span>Non sono presenti layer per estrazione</span>
							{{/each}}
						</div>
					{{/tematismi}}
				</div>
			</div>
		</div>
	</div>
</div>
</script>