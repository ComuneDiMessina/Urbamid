<main id="#landmark-main">
	<!-- BREADCRUMB - TITOLO -->
	<header class="page-heading full-width">
        <div class="container-fluid">
            <div class="row nop">
               <nav aria-label="breadcrumb" class="full-width col nop">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a class="home-link back" href="./home"><i class="fa fa-home"></i>&nbsp;Home</a></li>
						<li class="breadcrumb-item"><a href="./gestioneMappe">Mappe</a></li>
                      	<li class="breadcrumb-item">Gestione Temi</li>
                   	</ol>
                </nav>                
            </div>
            <h1 class="map-title">Gestione Temi <span id="map-title"></span> </h1>
        </div>
    </header>

    <div class="globalContainer noabs">
    	<div class="tabbable-panel">
    		<div class="tabbable-line">
    			<!-- MACRO ATTIVITA MAPPA --> 
				<ul class="nav nav-tabs">
					<li class="nav-item">
						<a class="nav-link gestioneMappe" href="./gestioneMappe" >Gestione Mappe</a>
					</li>
					<li class="nav-item">
						<a class="nav-link active gestioneTemi" href="#">Gestione Temi</a>
					</li>
					<li class="nav-item" style="display:none;">
						<a class="nav-link gestioneLivelli" href="#">Gestione Livelli</a>
					</li>
				</ul>
	    		<div class="tab-content">
					<div class="d-flex" style="padding-bottom: 25px;">
						<div class="col text-left data-table-actions header menu-gestione-temi">
							<!-- ATTIVITA SU SINGOLE MAPPE -->
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnew-tema" onclick="applicationTema.openSezione(event, 'nuovo-tema')">
			                       	<span class="label">Nuovo</span>
			                   	</button>
							</div>
							<div class="btn-group">
								<!-- style="background-color: #861611;border-radius: unset;height: 40px;font-size: 16px;" -->
								<button type="button" class="btn btn-primary dettaglio-tema" disabled="disabled">
			                       	<span class="label">Dettaglio</span>
			                   	</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary elimina-tema" disabled="disabled">
									<span class="label">Elimina</span>
								</button>
							</div>
						</div>
					</div>

       				<!-- DETTAGLIO TEMA -->
    				<div class="data-table-content">
    					<div id="sezioni-mappa-tema" class="container-fluid">
					    	<!-- SEZIONI MAPPA LA RENDO NASCOSTA E MODIFICO I CLASS DEI LIVELLI SOTTOSTANTI DA  col-lg-10 A col-lg-12-->
							<div class="col" style="display:none;">
								<ul class="nav flex-column nav-tabs-vertical nav-tabs">
					           		<li id="elenco-tema-link" class="nav-item tablinks">
					             		<a class="nav-link not">ELENCO TEMI</a>
					           		</li>
					           		<li id="dettaglio-tema-link" class="nav-item tablinks">
					             		<a class="nav-link not">DETTAGLIO TEMA
											<span class="badge" ></span>
										</a>
					           		</li>
					           		<li id="nuovo-tema-link" class="nav-item tablinks">
					             		<a class="nav-link not">NUOVO TEMA
											<span class="badge" ></span>
										</a>
					           		</li>
					        	</ul>
							</div> 
    
							<!-- ELENCO TEMA -->
							<div id="elenco-tema" class="tabTemi row" style="padding-top: 0px;display:block;">
								<div class="col">
									<div class="table-responsive">
										<table id="tabellaTema" class="table table-striped" style="width:100%"></table>
									</div>
									<!-- AZIONI FOOTER -->
								</div>
							</div><!-- ELENCO TEMA -->
    
     						<!-- /BASE DETTAGLIO TEMA -->
					     	<div id="dettaglio-tema" class="tabTemi row" style="padding-top: 0px;display:none;">
				       			<div class="col">
				           			<div class="row form-row">
				               			<div class="form-group col-xs-12 col-md-12">
				                       		<label for="input-1-01">Nome</label>
				                       		<div class="input-group">
				                           		<input type="text" class="form-control" id="mod-nome" value="" placeholder="Nome tema">
				                       		</div>        
				                     	</div> <!-- /.col.form-group -->
				               		 	<div class="form-group col-xs-12 col-md-12">
				                     		<label for="input-1-02">Descrizione</label>
				                       		<div class="input-group">
					                        	<input type="text" class="form-control" id="mod-descrizione" value="" placeholder="Descrizione tema">
				                       		</div>        
				                     	</div> <!-- /.col.form-group -->
				                      	<div class="form-group col-xs-12 col-md-12">
					                     	<label for="input-1-02">Posizione</label>
				                       		<div class="input-group">
					                        	<input type="number" class="form-control" id="mod-position" value="" placeholder="1" min="1">
				                       		</div>        
				                     	</div> <!-- /.col.form-group -->
				                	</div>
					 			</div>
					    	    <!-- AZIONI FOOTER -->
					    	    <div class="col text-right data-table-actions header">
	 								<div class="btn-group">
				                    	<button type="button" class="btn btn-primary" onclick="applicationTema.openSezione(event, 'elenco-tema')">
				                        	<span class="fa fa-chevron-left mr-2" role="presentation"></span>
				                        	<span class="label">Torna Lista Temi</span>
				                    	</button>
				                    </div>
				                    <div class="btn-group">
					                    <button type="button" class="btn btn-primary midifica-tema">
					                        <span class="label"> Salva </span>
					                    </button>
					                </div>
				                    <div class="btn-group">
										<a href="./gestioneMappe" class="btn btn-primary">
											<span class="label"> Chiudi </span>
										</a>
									</div>
								</div> <!-- AZIONI FOOTER -->
					    	</div><!-- /BASE DETTAGLIO TEMA -->	   
    	
    						<!-- /BASE NUOVO TEMA -->
					    	<div id="nuovo-tema" class="row tabTemi" style="padding-top: 0px;display:none;">
								<div class="col">
									<div class="col-xs-12 col-sm-8 col-md-9 col-lg-12">
						       			<div style="height:300px">
							           		<div class="row form-row">
						               			<div class="form-group col-xs-12 col-md-12">
							                       	<label for="input-1-01">Nome</label>
						                       		<div class="input-group">
							                           	<input type="text" class="form-control" id="new-nome" value="" placeholder="Nome tema">
						                       		</div>        
						                     	</div> <!-- /.col.form-group -->
						               		 	<div class="form-group col-xs-12 col-md-12">
						                     		<label for="input-1-02">Descrizione</label>
						                       		<div class="input-group">
							                        	<input type="text" class="form-control" id="new-descrizione" value="" placeholder="Descrizione tema">
						                       		</div>        
						                     	</div> <!-- /.col.form-group -->
						                       	<div class="form-group col-xs-12 col-md-12">
						                     		<label for="input-1-02">Posizione</label>
						                       		<div class="input-group">
							                        	<input type="number" class="form-control" id="new-position" value="1" placeholder="1">
						                       		</div>        
						                     	</div> <!-- /.col.form-group -->
						       				</div>
						     			</div>
							 		</div>
							 	</div>
					    	    <!-- AZIONI FOOTER -->
					   			<div class="col text-right data-table-actions header">
	 								<div class="btn-group">
										<button type="button" class="btn btn-primary" onclick="applicationTema.openSezione(event, 'elenco-tema')">
											<span class="fa fa-chevron-left mr-2" role="presentation"></span>
											<span class="label">Torna Lista Temi</span>
										</button>
									</div>
									<div class="btn-group">
										<button type="button" class="btn btn-primary salva-tema">
								        	<span class="label"> Salva </span>
										</button>
									</div>
									<div class="btn-group">
										<a href="./gestioneMappe" class="btn btn-primary">
											<span class="label"> Chiudi </span>
										</a>
									</div>
			                    	<!--button type="button" class="btn btn-primary showElenco">
			                        	<span class="label"> Chiudi </span>
			                    	</button-->
								</div> <!-- AZIONI FOOTER -->
					    	</div> <!-- /BASE NUOVO TEMA -->
					    </div>
					</div>
				</div>	
    		</div>
    	</div>
    </div>

    
</main>