<script id="sezioni-mappa-template" type="text/x-handlebars-template">
	
		<!-- SEZIONI MAPPA -->
		<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2 bg-primary aside-filters">
			<ul class="nav flex-column nav-tabs-vertical nav-tabs">
           		<li id="dati-base-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-base')">
             		<a class="nav-link" href="#" >DATI DI BASE</a>
           		</li>
           		<li id="dati-layer-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-layer')">
             		<a class="nav-link " href="#" >LIVELLI
						<span class="badge" >{{numLayers}}</span>
					</a>
           		</li>
           		<li id="dati-ricerche-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-ricerche')">
             		<a class="nav-link " href="#" >RICERCHE
             			<span class="badge" >{{numSearchs}}</span>
             		</a>
           		</li>
           		<li id="dati-tools-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-tools')">
             		<a class="nav-link " href="#" >STRUMENTI
             			<span class="badge" >{{numTools}}</span>
             		</a>
           		</li>
				<li id="dati-tools-link" class="nav-item tablinks" onclick="openSezione(event, 'dati-tools')">
             		<a class="nav-link " href="#" >PERMESSI
             			<span class="badge" >{{numTools}}</span>
             		</a>
           		</li>
        	</ul>
		</div>
     	
		<!-- DATI DI BASE -->
		<div id="dati-base" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent">
       		<div>
           		<div class="row form-row">
               		<div class="form-group col-xs-12 col-md-6">
                       	<label for="input-1-01">Codice</label>
                       	<div class="input-group">
                           	<input type="text" class="form-control" id="code" value="{{code}}" placeholder="Codice mappa" readonly="">
                       	</div>        
                     </div> <!-- /.col.form-group -->
               		 <div class="form-group col-xs-12 col-md-6">
                     	<label for="input-1-02">Titolo</label>
                       	<div class="input-group">
                        	<input type="text" class="form-control" id="title" value="{{title}}" placeholder="Titolo mappa" readonly="">
                       	</div>        
                     </div> <!-- /.col.form-group -->
               		 <div class="form-group col-xs-12 col-md-12">
                     	<label for="input-1-03">Descrizione</label>
                     	<div class="input-group">
                      		<input type="text" class="form-control" id="description" value="{{description}}" placeholder="Descrizione mappa" readonly="">
                     	</div>
                     </div> <!-- /.col.form-group -->
						<div class="form-group col-xs-12 col-md-3">
                     	<label for="input-1-03">Stato</label>
                     	<div class="input-group">
                      	<select class="form-control" id="stato" disabled>
						<option value="D">Disabilitato</option>
						<option value="P">Pubblicato</option>
						</select>
                     	</div>
                     </div> <!-- /.col.form-group -->
  					 <div class="form-group col-xs-12 col-md-8">
					 <div class="col-xs-12 col-md-2"><!-- /.col.thema -->
                     	<label for="input-1-03">Includi in Thema</label>
                     	<div class="input-group">
                      		<input type="button" class="form-control" id="description" value="Add.." disabled>
                     	</div>
					</div><!-- /fine.col.thema -->
					<div class="col-xs-12 col-md-10" id="thema"> </div>
                     </div> <!-- /.col.form-group -->

       			</div>
     		</div>
		</div>
	
		<!-- LAYER -->
		<div id="dati-layer" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none">
       		<div>
           		<div class="row form-row">
					<div class="form-group col-xs-12 col-md-6">
                       	<label for="input-1-01">Catalogo</label>
						<!-- CONTAINER PER IL CATALOGO -->
						<div id="menu-content"></div>
                     </div> <!-- /.col.form-group -->
					<div class="form-group col-xs-12 col-md-6">
						<label for="input-1-01">Dati di base</label>
						<div>
							{{#layerOk}}
								<span style="float: left;width: auto;padding: 2px 10px;background-color: #861611;color: #fff;line-height: 30px;margin: 10px 5px 0px 0px;">
									{{layerName}}
									<input id="checkbox-{{layerName}}" type="checkbox" aria-label="Abilita il dato su mappa" value="{{enabled}}">
									<button disabled class="btn removeLayer" style="color: #861611;background-color: #fff;padding: 0px 5px;margin: 0px 0px 0px 10px;"
											onclick="appGestMappe.removeLayer('{{layerName}}')">
										<i class="fa fa-trash-o"></i>
									</button>
								</span>
							{{/layerOk}} 
						</div>
					</div>
       			</div>
     		</div>
		</div>
	
		<!-- RICERCHE -->
		<div id="dati-ricerche" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none">
       		<div class="row">
           		<div class="col-sm-4 col-sm-offset-1">
					<div class="list-group" id="searchList">
						<a href="#" class="list-group-item active">Ricerche disponibili</a>
						{{#searchListKo}}
							<div class="input-group">
  								<div class="input-group-prepend">
    								<div class="input-group-text">
      									<input id="checkbox-{{id}}" type="checkbox" aria-label="Checkbox for following text input">
    								</div>
  								</div>
								<label class="form-control" aria-label="Text input with checkbox">{{title}}</label>
							</div>
						{{/searchListKo}} 
					</div>
				</div>
				<div class="col-sm-2 text-center">
					<button title="Attiva la ricerca" class="btn btn-default center-block addElem">
						<i class="fa fa-arrow-right"></i>
					</button>
					<button title="Disattiva la ricerca" class="btn btn-default center-block removeElem">
						<i class="fa fa-arrow-left"></i>
					</button>
				</div>
				<div class="col-sm-4">
					<div class="list-group" id="ricercheAttiveList">
						<a href="#" class="list-group-item active">Ricerche attive</a>
						{{#searchListOk}}
							<div class="input-group">
  								<div class="input-group-prepend">
    								<div class="input-group-text">
      									<input id="checkbox-{{id}}" type="checkbox" aria-label="Checkbox for following text input">
    								</div>
  								</div>
								<label class="form-control" aria-label="Text input with checkbox">{{title}}</label>
							</div>
						{{/searchListOk}} 
					</div>
				</div>
     		</div>
		</div>
	
		<!-- TOOLS -->
		<div id="dati-tools" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none">
       		<div class="row">
           		<div class="col-sm-4 col-sm-offset-1">
					<div class="list-group" id="toolList">
						<a href="#" class="list-group-item active">Tool disponibili</a>
						{{#toolListKo}}
							<div class="input-group">
  								<div class="input-group-prepend">
    								<div class="input-group-text">
      									<input id="checkbox-{{id}}" type="checkbox" aria-label="Checkbox for following text input">
    								</div>
  								</div>
								<label class="form-control" aria-label="Text input with checkbox">{{title}}</label>
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
				<div class="col-sm-4">
					<div class="list-group" id="toolAttiviList">
						<a href="#" class="list-group-item active">Tool attivi</a>
						{{#toolListOk}} 
							<div class="input-group">
  								<div class="input-group-prepend">
    								<div class="input-group-text">
      									<input id="checkbox-{{id}}" type="checkbox" aria-label="Checkbox for following text input">
    								</div>
  								</div>
								<label class="form-control" aria-label="Text input with checkbox">{{title}}</label>
							</div>
						{{/toolListOk}} 
					</div>
				</div>
     		</div>
		</div>
	
</script>