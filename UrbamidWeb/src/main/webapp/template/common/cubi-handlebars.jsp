<script id="cubottiTemplate" type="text/x-handlebars-template">
	<div class="container-fluid" style="padding: 3% 0px 2% 0px;">
		<div class="search">
			<div class="panel-body_vw">
				<div class="row nom nop tab-content">
					{{#data}}
						<!--------------------------------------------------------------------------------------------------------------- WEBGIS -->
						<div class="col-md-4 col-sm-6 col-xs-12 nop {{disable}}">
							<div id="{{id}}" class="sidekick" title="{{denominazione}}">
							{{#if locked}}
								<div class="js-link" target="">
									<p class="cubotto" style="background-image:url(<%=request.getContextPath()%>{{imgUrl}})"></p>
									<span>{{denominazione}}</span>
								</div>
							{{else}}
								<a class="js-link" href="<%=request.getContextPath()%>{{linkUrl}}?authority={{permesso}}&codeMappa={{mappa}}" target="">
									<p class="cubotto" style="background-image:url(<%=request.getContextPath()%>{{imgUrl}})"></p>
									<span>{{denominazione}}</span>
								</a>
							{{/if}}
							</div>
						</div>
					{{/data}}
				</div>
			</div>
		</div>
	</div>
</script>

<script id="emptyCubottiTemplate" type="text/x-handlebars-template">
	<div class="container-fluid" style="padding: 3% 0px 2% 0px;">
		<div class="search">
			<div class="panel-body_vw">
				<div class="row nom nop tab-content">
					<h3>Login non andata a buon fine, vi invitiamo a contattare l'helpdesk</h3>
				</div>
			</div>
		</div>
	</div>
</script>