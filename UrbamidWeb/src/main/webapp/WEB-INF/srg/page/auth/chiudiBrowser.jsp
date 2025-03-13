<!DOCTYPE html>
<html>
<head>

	<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/80px-Messina-Stemma.ico" type="image/x-icon" />
	<title>Urbamid - Comune di Messina</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="utf-8">

	<!-- Import plugin -->
	<script> 
		var appCubotti 		= {};
		logged 	= '${logged}';
		role 	= '${role}';
	</script>
	<jsp:include page="/page/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/auth/manageAuth.js"></script>
		
	<!-- Albero dei layer -->
	<link type="text/css" href="<%=request.getContextPath()%>/css/srg.css" rel="Stylesheet">
	<script> 
		var data = {};
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsontratta.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsonpoi.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsonpoipel.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/general.js"></script>
	
	<!-- INCLUDE TEMPLATE HANDLEBARS -->
	<jsp:include page="/template/common/cubi-handlebars.jsp"></jsp:include>
</head>
 
<body>
	<!--<![endif]-->
	<!--[if lt IE 7]>
		    	<p class="chromeframe">Stai utilizzando un browser <strong>obsoleto</strong>. Ti preghiamo di <a href="http://browsehappy.com/">aggiornare il tuo browser</a> o di <a href="http://www.google.com/chromeframe/?redirect=true">attivare Google Chrome Frame</a> per migliorare la fruizione dell'applicazione.</p>
		<![endif]-->

	<div id="loader">
		<img src="<%=request.getContextPath()%>/images/Load1.gif" /> <span>Loading</span>
		<div class="overlayer"></div>
	</div>

	<div id="myNav" class="overlay hidden">
	</div>
	
	<div class="generalContainer">
		<!-- USER-MAP -->
		<jsp:include page="/template/header/header-accessoNegato.jsp"></jsp:include>

		<div class="container-fluid nop">
			<div class="row nom nopt mainCont">
				<div class="container-fluid" style="padding: 5% 0px 5% 0px;">
					<div style="width: 95%;background-color: white;border: 2px solid #004275;margin-left: 2%;
							box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px;padding-top: 10px;padding-bottom: 10px;">
						<i class="fa fa-info-circle" aria-hidden="true" style="padding-right:5px;font-size: 35px;color: #004275;margin-left: 10px;width: 30px;float: left;margin-top: 5px;margin-right: 10px;"></i>
						<h2 class="text-align:center" style="margin: 0px 1%;width: 100%;color: #004275;font-size: 35px;">Sei stato disconnesso dalla sessione</h2>
						<h3 style="margin: 5px 1%;width: 100%;color: #004275;font-size: 20px;">Chiudi il browser per una nuova navigazione</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- USER-MAP -->
	<jsp:include page="/template/footer/footer.jsp"></jsp:include>
	
</html>