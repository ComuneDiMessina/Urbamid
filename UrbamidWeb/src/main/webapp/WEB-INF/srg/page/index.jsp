<!DOCTYPE html>
<html>
<head>

	<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/80px-Messina-Stemma.ico" type="image/x-icon" />
	<title>Urbamid - Comune di Messina</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta content="initial-scale=1.0, width=device-width" name="viewport">
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
		<!-- CUSTOM URBAMID JS -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/catalogo.js"></script>								<!-- Per il caricamento del catalogo in mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/mappa.js" crossorigin="anonymous"></script>		<!-- Per il caricamento del catalogo in mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/urbamid.js"></script>								<!-- Js per il caricamento della mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/util/rest.js"></script>									<!-- Js per chiamate Rest -->
	
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
	
	<style>
		body{
			background-image:url("images/caricamento_in_corso.gif"); 
			background-repeat:no-repeat; 
			background-position:center;
		}
	</style>
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
		<jsp:include page="/template/header/header-index.jsp"></jsp:include>
		
		<div class="container-fluid nop">
			<div id="cubotti" class="row nom nopt mainCont"></div>
		</div>
	</div>
	
	<!-- USER-MAP -->
	<jsp:include page="/template/footer/footer.jsp"></jsp:include>
	
</html>