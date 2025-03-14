<script id="menusTemplate" type="text/x-handlebars-template">
	<div class="overlay-header">
		<span class="title-menu">{{title}}</span>
		<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
	</div>
	<div id="menu-overlay-content" class="overlay-content">
		{{#data}}
			<div class="subnav">
				<a class="first-level" href="#">{{title}}</a>
				<div class="subnav-content subnav-second hidden">
					{{#children-1}}
						<div class="second-level-container">
							<a id="{{id}}" class="second-level" href="#">{{title}}</a>
							<div class="subnav-content subnav-third hidden">
								{{#children-2}}
									<div class="third-level-container">
										<a id="{{id}}" class="third-level" href="#">{{title}}</a>
									</div>
								{{/children-2}}
							</div>
						</div>	
					{{/children-1}}
				</div>
			</div>
		{{/data}}
		<div id="headernav" class="headernav" style="display: none;">
			<i class="fa fa-info-circle" aria-hidden="true" style="padding-right:5px;"></i>
			<span id="message"></span>
		</div>
	</div>
</script>