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
            <h1 id="map-title" class="map-title">Elenco Utenti </h1>
        </div>
    </header>
    <div class="globalContainer noabs">
    	<div class="tabbable-panel">
    		<div class="tabbable-line">
    			<!-- MACRO ATTIVITA MAPPA --> 
				<ul class="nav nav-tabs">
					<li class="nav-item">
						<a class="nav-link utenti active" href="#" >Utenti</a>
					</li>
					<li class="nav-item">
						<a class="nav-link ruoliPermessi" href="#">Ruoli Permessi</a>
					</li>
				</ul>
				
				<!-- ------------------------------------- -->
				<!-- ------------ ELENCO UTENTI ---------- -->
				<!-- ------------------------------------- -->						
	    		<div id="eleUtente" class="tab-content">
	    			<div class="d-flex">
						<div class="col text-left data-table-actions header">
							<!-- ATTIVITA SU SINGOLE UTENTI -->
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnNuovoUtente">
		                        	<span class="label">Nuovo</span>
		                    	</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnDettaglioUtente" disabled="disabled">
		                        	<span class="label">Dettaglio</span>
		                    	</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnEliminaUtente" disabled="disabled">
									<span class="label">Elimina</span>
								</button>
							</div>
	    				</div>
					</div>
					
					<!-- ELENCO UTENTI -->
					<div class="data-table-content">
						<div class="container-fluid">
							<div class="row">
								<div class="col">
									<div class="table-responsive">
										<table id="tabellaUtenti" class="table table-striped" style="width: 100%"></table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- ----------------------------------------- -->
				<!-- ------------ DETTAGLIO UTENTE ----------- -->
				<!-- ----------------------------------------- -->				
	    		<div id="detNewUtente" class="tab-content" style="display:none;">
					<div class="data-table-content">
						<div class="container-fluid">
							<div class="collapse show collapse-body" id="collapse-03">
								<div class="row form-row">
									<!-- /.col.form-group -->
									<div class="form-group col-xs-12 col-md-3 col-lg-3">
										<label for="input-unicazione-2">* Username</label>
										<div class="input-group">
											<input type="text" class="form-control" id="input-username" onchange="appGestUtente.changeDettUtente()"
												value="" placeholder="Inserisci username">
										</div>
									</div>
									<!-- /.col.form-group -->
									<div class="form-group col-xs-12 col-md-3 col-lg-3">
										<label for="input-unicazione-3">* Codice Fiscale</label>
										<div class="input-group">
											<input type="text" class="form-control" id="input-codicefiscale" onchange="appGestUtente.changeDettUtente()"
												value="" placeholder="Inserisci codice fiscale">
										</div>
									</div>
									<div class="form-group col-xs-12 col-md-3 col-lg-3">
										<label for="input-unicazione-1">Ruolo</label> 
										<select class="custom-select" id="input-ruoli" onchange="appGestUtente.changeDettUtente()">
										</select>
									</div>
									<div class="form-group col-xs-12 col-md-1 col-lg-1">
										<label for="input-unicazione-1" style="text-align: center;width: 100%;">Abilitato</label> 
										<div class="input-group" style="height: 34px;padding: 7px 0px 7px 0px;">
											<input type="checkbox" class="form-control" id="input-abilitato" style="height: 20px;" onchange="appGestUtente.changeDettUtente()"
												value="" placeholder="Inserisci abilitato">
										</div>
									</div>
								</div>
								<div class="row form-row">
									<!-- /.col.form-group -->
									<div class="form-group col-xs-9 col-md-3 col-lg-3">
										<label for="input-unicazione-4">* Nome</label>
										<div class="input-group">
											<input type="text" class="form-control" id="input-nome" onchange="appGestUtente.changeDettUtente()"
												value="" placeholder="Inserisci nome">
										</div>
									</div>
									<!-- /.col.form-group -->
									<div class="form-group col-xs-9 col-md-3 col-lg-3">
										<label for="input-unicazione-5">* Cognome</label>
										<div class="input-group">
											<input type="text" class="form-control" id="input-cognome" onchange="appGestUtente.changeDettUtente()"
												value="" placeholder="Inserisci cognome">
										</div>
									</div>
									<!-- /.col.form-group -->
									<div class="form-group col-xs-9 col-md-3 col-lg-3">
										<label for="input-unicazione-5">Email</label>
										<div class="input-group">
											<input type="text" class="form-control" id="input-email" onchange="appGestUtente.changeDettUtente()"
												value="" placeholder="Inserisci email">
										</div>
									</div>
									<!-- /.col.form-group -->
								</div>
								<!-- /.form-row -->
								<div class="row form-row">
									<!-- /.col.form-group -->
									<div class="form-group col-xs-9 col-md-9 col-lg-9">
										<label for="input-unicazione-4">Note</label>
										<div class="input-group">
											<input type="text" class="form-control" id="input-note" onchange="appGestUtente.changeDettUtente()"
												value="" placeholder="Inserisci note">
										</div>
									</div>
									<!-- /.col.form-group -->
								</div>
							</div>
						</div>
					</div>
					<div class="d-flex">
						<div class="col text-right data-table-actions header">
							<!-- ATTIVITA SU SINGOLE UTENTI -->
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnSalvaUtente" disabled="disabled">
		                        	<span class="label">Salva</span>
		                    	</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary btnChiudiUtente">
									<span class="label">Chiudi</span>
								</button>
							</div>
	    				</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>