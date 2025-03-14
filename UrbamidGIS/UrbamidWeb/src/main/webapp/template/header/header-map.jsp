<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav id="headerMappa" class="navbar mappa navbar-expand-lg navbar-light bg-primary navbar-offcanvas">
	<div class="row full-width align-items-center nom nop">
		
		<button id="myNavbar" class="navbar-toggler d-block" type="button" onclick="openNav(event)">
			<span class="navbar-toggler-pictogram navbar-toggler-on">
	        	<img src="<%=request.getContextPath()%>/images/ui/icon-nav-hamburger.svg" aria-hidden="true" alt="Mostra il menu">
	      	</span>
	      	<span class="navbar-toggler-pictogram navbar-toggler-off">
		        <img src="<%=request.getContextPath()%>/images/ui/icon-nav-times.svg" aria-hidden="true" alt="Nascondi il menu">
	      	</span>
	      	<span class="navbar-toggler-label">Menu</span>
	    </button>
		
	    <div class="col-auto navbar-brand mr-auto header-map">
	    	<div class="navbar-co-brand">
	    		<div class="araldica-citta-messina">
	        		<div class="brand-img">
	            		<img src="./images/messina.svg" alt="Araldica Citt&#225; Metropolitana di Messina">
	        		</div> 
	        		<div class="brand-label">
	            		<span class="second-line">Citt&#225; di Messina</span><br />
	            		<span class="third-line">Citt&#225; Metropolitana di Messina</span>
	        		</div>
	    		</div>
			</div>
			<span class="navbar-co-brand">
				<span id="title-map" class="navbar-co-brand-label"></span>
			</span>
		</div>
		<div class="col-lg-4 justify-content-end nop">
			<div class="nopr-md nav-header-right text-right">
				<a id="backHome" class="back" href="./home" title="Torna alla home">
					<i class="fa fa-home px-2" aria-hidden="true"></i>
					<span class="resp-display" style="font-size:small">Home</span>
				</a>
				
				<button id="dropdownMenu1" class="btn btn-primary dropdown  fa fa-user-circle px-2" style="font-size:24px" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
				<i id="dd-info" class="fa fa-angle-down" style="font-size:12px !important;color:white;left:-6px;position: relative;color:white;"></i>
				<span id="dd-benvenuto" class="resp-display" style="color:#fff;padding-right:15px;top:4px;position:relative;">Benvenuto <strong>${nome}</strong></span>
				<div id="dd-no-webgis" class="dropdown-menu" aria-labelledby="dropdownMenuButton" style="background-color: #1a1a1a;opacity:0.95;border-radius: 0;right: 0;top: 59px;padding: 0;text-align: right;">
					<a class="dropdown-item dropdown-display" href="#">
						<span class="" style="color: #fff;">Benvenuto <strong>${nome} ${cognome}</strong></span>
					</a>
					<a class="dropdown-item" href="#"><span  style="color: #fff;">Assistenza <i class="fa fa-question-circle"></i></span></a>					
					<a id="dd-esci" class="dropdown-item" href="logout"><span  style="color: #fff;">Esci <i class="fa fa-sign-out"></i></span></a>
				</div>	
			</div>
		</div>
	</div>
</nav>