<style type="text/css">
<!--
.dataTables_wrapper{width: 100%;}
.dataTables_length {
    float: right!important;
    display: block!important;
    position: absolute;
    right: 320px;
}
.dataTables_filter{ display: block!important; }

.dataTables_filter label{
	float: right;
    text-align: right;
    background: #004275;
    color: #fff;
    padding: 0px 15px 0px 0px;
    line-height: 2.8;
    text-transform: uppercase;
}

.dataTables_filter input{
   background: #fff;
    border: 1px solid #adadad;
    height: 40px;
    width: 240px;
    border-radius: 0px;
    float: left;
    margin: 0px!important;
    padding: 0px;
    margin-right: 9px!important;
} 
.dataTables_length label{
    inline-size: min-content;
    margin-top: -20px;
}   
.dataTables_length select{
	background: #fff;
    width: 260px;
    height: 40px;
    border: 1px solid #adadad;
    margin-right: 15px;
    border-radius: 0px;
    margin-left: -2px;
}
 div.dt-buttons {
 float: right;
}
 div.dt-buttons button {
    border: 1px solid #861611;
    border-radius: 0px;
}
.dataTables_wrapper{
margin-top: 30px!important;
}

.dataTables_wrapper .dataTables_length, .dataTables_wrapper .dataTables_filter, .dataTables_wrapper .dataTables_info, .dataTables_wrapper .dataTables_processing, .dataTables_wrapper .dataTables_paginate {
    color: #560804!important;
}

.custom-control-input:checked~.custom-control-label::before {
    color: #fff;
    border-color: #004275!important;
    background-color: #004275!important;
}

.header button {
    min-width: 130px!important;
}

table.dataTable tbody tr:hover td {
    background-color: #e1dedea8 !important;
}
-->
</style>

<main id="#landmark-main">
	<!-- BREADCRUMB - TITOLO -->
	<header class="page-heading">
        <div class="container-fluid">
            <div class="row nop">
                <nav aria-label="breadcrumb" class="full-width col nop">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="./home" class="back home-link"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                       	<li class="breadcrumb-item">Mappe<span class="map-title"></span></li>
                    </ol>
                </nav>                
            </div>
            <h1 class="map-title">Elenco mappe </h1>
        </div>
    </header>
    <div class="globalContainer noabs">
    	<div class="tabbable-panel">
    		<div class="tabbable-line">
    			<!-- MACRO ATTIVITA MAPPA --> 
				<ul class="nav nav-tabs">
					<li class="nav-item">
						<a class="nav-link active" href="./gestioneMappe" >Gestione Mappe</a>
					</li>
					<li class="nav-item">
						<a class="nav-link gestioneTemi" href="#">Gestione Temi</a>
					</li>
					<li class="nav-item" style="display:none;">
						<a class="nav-link gestioneLivelli" href="#">Gestione Livelli</a>
					</li>
				</ul>
	    		<div class="tab-content">
	    			<div class="d-flex">
						<div class="col text-left data-table-actions header" style="padding-bottom: 20px;">
							<!-- ATTIVITA SU SINGOLE MAPPE -->
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnew">
		                        	<span class="label">Nuovo</span>
		                    	</button>
							</div>
							<div class="btn-group">
								<!-- style="background-color: #861611;border-radius: unset;height: 40px;font-size: 16px;" -->
								<button type="button" class="btn btn-primary dettaglio" disabled="disabled">
		                        	<span class="label">Dettaglio</span>
		                    	</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary elimina" disabled="disabled">
									<span class="label">Elimina</span>
								</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary duplica" disabled="disabled">
									<span class="label">Duplica</span>
								</button>
							</div>
	    				</div>
					</div>
					<!-- ELENCO MAPPE -->
					<div class="data-table-content" style="clear: both;">
						<div class="container-fluid">
							<div class="row">
								<div class="col">
									<div class="table-responsive">
										<table id="tabellaMappe" class="table table-striped" style="width: 100%;"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>