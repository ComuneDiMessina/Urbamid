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
						<li class="breadcrumb-item"><a class="home-link back" href="./home"><i class="fa fa-home"></i>&nbsp;Home</a></li>
						<li class="breadcrumb-item"><a href="./gestioneMappe">Servizi Server Cartografico</a></li>
                   	</ol>
                </nav>                
            </div>
        </div>
    </header>
	
    <div class="globalContainer noabs">
    	<div class="row">
    		<div class="container-fluid" style="padding:0px;">
				<div class="search">
					<div class="panel-body_vw">
						<div class="row nom nop tab-content">
							<div class="col-md-4 col-sm-6 col-xs-12 nop disable">
								<div id="id" class="sidekick" title="Servizi rest">
									<a class="js-link" href="/geoserver/rest" target="blank">
										<p class="cubotto" style="background-image:url(<%=request.getContextPath()%>/images/geoserver.png);width: 50%;height: auto;"></p>
										<span>Servizi rest</span>
									</a>
								</div>
							</div>
							<div class="col-md-4 col-sm-6 col-xs-12 nop disable">
								<div id="id" class="sidekick" title="Preview Layers">
									<a class="js-link" href="/geoserver/web/wicket/bookmarkable/org.geoserver.web.demo.MapPreviewPage?1" target="blank">
										<p class="cubotto" style="background-image:url(<%=request.getContextPath()%>/images/geoserver.png);width: 50%;height: auto;"></p>
										<span>Preview Layers</span>
									</a>
								</div>
							</div>
							<div class="col-md-4 col-sm-6 col-xs-12 nop disable">
								<div id="id" class="sidekick" title="Documentazione API GeoServer">
									<a class="js-link" href="https://docs.geoserver.org/" target="blank">
										<p class="cubotto" style="background-image:url(<%=request.getContextPath()%>/images/geoserver.png);width: 50%;height: auto;"></p>
										<span>Documentazione API GeoServer</span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
     
</main>