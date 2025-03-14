<main id="#landmark-main">
	<!-- BREADCRUMB - TITOLO -->
	<header class="page-heading">
        <div class="container-fluid">
            <div class="row nop">
                <nav aria-label="breadcrumb" class="full-width col nop">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a class="home-link back" href="./home"><i class="fa fa-home"></i>&nbsp;Home</a></li>
						<li class="breadcrumb-item"><a href="./gestioneMappe">Mappe</a></li>
                       	<li class="breadcrumb-item">Dettaglio Mappa</li>
                   	</ol>
                </nav>                
            </div>
            <h1 class="map-title"><span id="map-title"></span> </h1>   
        </div>
    </header>
	
    
    <!-- DETTAGLIO MAPPA -->
    <div class="container-fluid">
    	<div id="sezioni-mappa" class="row" >
    	</div>
    </div>
     
    <!-- AZIONI MAPPA FOOTER -->
    <div class="col text-right data-table-actions header" style="padding-bottom: 20px;">
	 	<div class="btn-group">
           	<button type="button" class="btn btn-primary showElenco" >
               	<span class="fa fa-chevron-left mr-2" role="presentation"></span>
               	<span class="label">Torna alla lista</span>
           	</button>
	    </div>
	    <div class="btn-group">
            <button type="button" class="btn btn-primary salva" disabled>
                <span class="label">Salva</span>
            </button>
		</div>
		<div class="btn-group">
            <button type="button" class="btn btn-primary modifica">
               	<span class="label">Modifica</span>
           	</button>
		</div>
		<div class="btn-group">
		<a style="padding:7px 15px;" class="btn btn-primary visualizzamappa" target="_blank" href="/UrbamidWeb/webgis?authority=ROLE_ACCESSO_WEBGIS&preview=true&codeMappa=">
			 Visualizza mappa 
		</a>
		</div>
	</div>
</main>