<main id="#landmark-main">
	<!-- BREADCRUMB - TITOLO -->
	<header class="page-heading full-width">
        <div class="container-fluid">
            <div class="row nop">
               <nav aria-label="breadcrumb" class="full-width col nop">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a class="home-link back" href="./home"><i class="fa fa-home"></i>&nbsp;Home</a></li>
						<li class="breadcrumb-item"><a href="#">Mappe</a></li>
                      	<li class="breadcrumb-item">Nuova Mappa</li>
                   	</ol>
                </nav>                
            </div>
            <h1 class="map-title">Gestine Temi <span id="map-title"></span> </h1>
        </div>
    </header>

	<div class="d-flex" style="padding-bottom: 25px;padding-top: 20px;">
		<div class="col text-left data-table-actions header">
			<!-- ATTIVITA SU SINGOLE MAPPE -->
			<div class="btn-group">
				<button type="button" class="btn btn-primary btnew-tema" onclick="openSezione(event, 'nuovo-tema')">
                      	<span class="label">Nuovo</span>
                  	</button>
			</div>
			<div class="btn-group">
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
    <div class="container-fluid">
    	<div id="sezioni-mappa-tema" class="row">
    	
	    	<!-- SEZIONI MAPPA onclick="openSezione(event, 'elenco-tema')"-->
			<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2 bg-primary aside-filters">
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
			<div id="elenco-tema" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent">
				<div class="container-fluid" style="min-height:300px">
					<div class="row">
						<div class="col">
							<div class="table-responsive">
								<table id="tabellaTema" class="table table-striped" style="width: 100%;"></table>
							</div>
						</div>
					</div>
				</div>
			    <!-- AZIONI FOOTER -->
				<div class="col text-right data-table-actions header">
					<div class="btn-group">
						<button type="button" class="btn btn-primary showElenco">
                       		<span class="label"> Chiudi </span>
                   		</button>
					</div>
				</div>
			</div>
    
     		<!-- /BASE DETTAGLIO TEMA -->
			<div id="dettaglio-tema" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none">
	       		<div style="height:300px">
	           		<div class="row form-row">
	               		<div class="form-group col-xs-12 col-md-12">
	                       	<label for="input-1-01">Nome</label>
	                       	<div class="input-group">
	                           	<input type="text" class="form-control" id="nome" value="" placeholder="Nome tema">
	                       	</div>        
	                     </div> <!-- /.col.form-group -->
	               		 <div class="form-group col-xs-12 col-md-12">
	                     	<label for="input-1-02">Descrizione</label>
	                       	<div class="input-group">
	                        	<input type="text" class="form-control" id="descrizione" value="" placeholder="Descrizione tema">
	                       	</div>        
	                     </div> <!-- /.col.form-group -->
	       			</div>
	     		</div>
	 
	    	    <!-- AZIONI FOOTER -->
	   			<div class="col text-right data-table-actions header">
					<div class="btn-group">
	                   	<button type="button" class="btn btn-primary" onclick="openSezione(event, 'elenco-tema')">
	                       	<span class="fa fa-chevron-left mr-2" role="presentation"></span>
	                       	<span class="label">Torna Lista Temi</span>
	                   	</button>
		          	</div>
		          	<div class="btn-group">
	                    <button type="button" class="btn btn-primary midifica-tema" disabled>
	                        <span class="label"> Salva </span>
	                    </button>
	                </div>
		          	<div class="btn-group">
	                   	<button type="button" class="btn btn-primary showElenco">
	                       	<span class="label"> Chiudi </span>
	                   	</button>
					</div>
				</div>
			</div> <!-- AZIONI FOOTER -->
    	
    		<!-- /BASE NUOVO TEMA -->
			<div id="nuovo-tema" class="col-xs-12 col-sm-8 col-md-9 col-lg-10 tabcontent" style="display:none">
	       		<div style="height:300px">
	           		<div class="row form-row">
	               		<div class="form-group col-xs-12 col-md-12">
	                       	<label for="input-1-01">Nome</label>
	                       	<div class="input-group">
	                           	<input type="text" class="form-control" id="nome" value="" placeholder="Nome tema">
	                       	</div>        
	                     </div> <!-- /.col.form-group -->
	               		 <div class="form-group col-xs-12 col-md-12">
	                     	<label for="input-1-02">Descrizione</label>
	                       	<div class="input-group">
	                        	<input type="text" class="form-control" id="descrizione" value="" placeholder="Descrizione tema">
	                       	</div>        
	                     </div> <!-- /.col.form-group -->
	       			</div>
	     		</div>
		 
	    	    <!-- AZIONI FOOTER -->
	   			<div class="col text-right data-table-actions header">
					<div class="btn-group">
                    	<button type="button" class="btn btn-primary" onclick="openSezione(event, 'elenco-tema')">
                        	<span class="fa fa-chevron-left mr-2" role="presentation"></span>
                        	<span class="label">Torna Lista Temi</span>
                    	</button>
					</div>		          
					<div class="btn-group">
	                    <button type="button" class="btn btn-primary midifica-tema" disabled>
	                        <span class="label"> Salva </span>
	                    </button>
	                </div>		          
					<div class="btn-group">    
                    	<button type="button" class="btn btn-primary showElenco">
                        	<span class="label"> Chiudi </span>
                    	</button>
                    </div>
				</div>
			</div> <!-- AZIONI FOOTER -->
	    </div> <!-- /BASE NUOVO TEMA -->	
    </div>
</main>