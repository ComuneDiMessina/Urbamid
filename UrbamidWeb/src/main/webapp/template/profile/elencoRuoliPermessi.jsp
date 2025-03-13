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
    border: 1px solid #004275;
    border-radius: 0px;
}
.dataTables_wrapper{
margin-top: 30px!important;
}

.dataTables_wrapper .dataTables_length, .dataTables_wrapper .dataTables_filter, .dataTables_wrapper .dataTables_info, .dataTables_wrapper .dataTables_processing, .dataTables_wrapper .dataTables_paginate {
    color: #004275!important;
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

</style>

<main id="#landmark-main">
	<!-- BREADCRUMB - TITOLO -->
	<header class="page-heading">
        <div class="container-fluid">
            <div class="row nop">
                <nav aria-label="breadcrumb" class="full-width col nop">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="./home" class="back home-link"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                       	<li class="breadcrumb-item">Profilatore<span class="map-title"></span></li>
                    </ol>
                </nav>                
            </div>
            <h1 id="map-title" class="map-title">Elenco Ruoli Permessi </h1>
        </div>
    </header>
    <div class="globalContainer noabs">
    	<div class="tabbable-panel">
    		<div class="tabbable-line">
    			<!-- MACRO ATTIVITA MAPPA --> 
				<ul class="nav nav-tabs">
					<li class="nav-item">
						<a class="nav-link utenti" href="#" >Utenti</a>
					</li>
					<li class="nav-item">
						<a class="nav-link ruoliPermessi active" href="#">Ruoli Permessi</a>
					</li>
				</ul>

				<!-- ------------------------------------- -->
				<!-- ------------ ELENCO RUOLI ----------- -->
				<!-- ------------------------------------- -->				
	    		<div id="ruoli" class="tab-content">
	    			<div class="d-flex">
						<div class="col text-left data-table-actions header">
							<!-- ATTIVITA SU SINGOLE UTENTI -->
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnNuovoRuoloPermesso">
		                        	<span class="label">Nuovo</span>
		                    	</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnDettaglioRuoloPermesso" disabled="disabled">
		                        	<span class="label">Dettaglio</span>
		                    	</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnEliminaRuoloPermesso" disabled="disabled">
									<span class="label">Elimina</span>
								</button>
							</div>
	    				</div>
					</div>
					<div class="data-table-content">
						<div class="container-fluid">
							<div class="row">
								<div class="col">
									<div class="table-responsive">
										<table id="tabellaRuoli" class="table table-striped" style="width: 100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- ------------------------------------- -->
				<!-- ------- DETTAGLIO DEL RUOLO --------- -->
				<!-- ------------------------------------- -->				
				<div id="permessiRuolo" class="tab-content">	
					<div class="data-table-content">
						<div class="container-fluid">
							<div class="collapse show collapse-body" id="collapse-03">
								<div class="row form-row">
									<!-- /.col.form-group -->
									<div class="form-group col-xs-12 col-md-4 col-lg-4">
										<label for="input-codice">* Codice</label>
										<div class="input-group">
											<input type="text" class="form-control" id="input-codice" onchange="appGestRuoliPermessi.changeDettRuolo()"
												value="" placeholder="Inserisci codice">
										</div>
									</div>
									<!-- /.col.form-group -->
									<div class="form-group col-xs-12 col-md-4 col-lg-4">
										<label for="input-denominazione">* Denominazione</label>
										<div class="input-group">
											<input type="text" class="form-control" id="input-denominazione" onchange="appGestRuoliPermessi.changeDettRuolo()"
												value="" placeholder="Inserisci denominazione">
										</div>
									</div>
									<div class="form-group col-xs-12 col-md-4 col-lg-4">
										<label for="input-descrizione">Descrizione</label> 
										<div class="input-group">
											<input type="text" class="form-control" id="input-descrizione" onchange="appGestRuoliPermessi.changeDettRuolo()"
												value="" placeholder="Inserisci descrizione">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="data-table-content">
						<div class="container-fluid">
							<div class="row">
								<div class="col">
									<div id="listaRuoliPermessi"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="d-flex">
						<div class="col text-right data-table-actions header">
							<!-- ATTIVITA SU SINGOLI RUOLI -->
							<div class="btn-group">
								<button type="button" class="btn btn-primary ruoliPermessi">
		                        	<span class="label">Indietro</span>
		                    	</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnSalvaRuoloPermesso" disabled="disabled">
		                        	<span class="label">Salva</span>
		                    	</button>
							</div>
	    				</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</main>