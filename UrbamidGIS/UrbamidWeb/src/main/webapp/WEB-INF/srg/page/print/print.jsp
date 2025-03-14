<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="IT" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="keywords" content="Infomobilit&#224;, trasporti, percorsi, trasporti pericolosi" />
	<meta content="initial-scale=1.0, maximum-scale=1.0, width=device-width, user-scalable=no" name="viewport" />
	<meta name="description" content="Infomobilit&#224;" />
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta charset="utf-8">
	<meta name="_csrf" content="${_csrf.token}" />
	<!-- default header name is X-CSRF-TOKEN -->
	<meta name="_csrf_header" content="${_csrf.headerName}" />
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/80px-Messina-Stemma.ico" type="image/x-icon" />
	<title>Urbamid - Comune di Messina</title>
	
	<!-- Import plugin -->
	<jsp:include page="/page/head.jsp"></jsp:include>
		<!-- CUSTOM URBAMID JS -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/catalogo.js"></script>								<!-- Per il caricamento del catalogo in mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/mappa.js" crossorigin="anonymous"></script>		<!-- Per il caricamento del catalogo in mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/print.js"></script>								<!-- Js per il caricamento della mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/util/rest.js"></script>									<!-- Js per chiamate Rest -->
			
	<!-- Albero dei layer -->
	<link type="text/css" href="<%=request.getContextPath()%>/css/srg.css" rel="Stylesheet">
	<script> 
		var data = {};
	</script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsontratta.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsonpoi.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsonpoipel.js"></script>
<%-- 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/mappa.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/general.js"></script>
	
	<style>
		.ol-zoom.ol-unselectable.ol-control, .ol-full-screen, .ol-measure-length, .ol-measure-area {
		    display: none;
		}
	</style>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugin/html2canvas/html2canvas.js"></script>
	<script src="https://cdn.polyfill.io/v3/polyfill.min.js?features=fetch,requestAnimationFrame,Element.prototype.classList,TextDecoder"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/3.18.3/minified.js"></script>
    <script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.3.1/jspdf.umd.min.js"></script>
	
	<script type="text/javascript"> 
		var _paq = window._paq = window._paq || []; 
		/* tracker methods like "setCustomDimension" should be called before "trackPageView" */ 
		_paq.push(['trackPageView']); 
		_paq.push(['enableLinkTracking']); 
		(function() { 
			var u="https://ingestion.webanalytics.italia.it/"; 
			_paq.push(['setTrackerUrl', u+'matomo.php']); 
			_paq.push(['setSiteId', 'kjpR7Alq1y']); 
			var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0]; 
			g.type='text/javascript'; 
			g.async=true; 
			g.src=u+'matomo.js'; 
			s.parentNode.insertBefore(g,s); 
		})(); 
	</script> 
	<!-- End Matomo Code -->
</head>

<!--[if lt IE 7]>      <body class="no-js lt-ie10 lt-ie9 lt-ie8 lt-ie7 ie" lang="it"> <![endif]-->
<!--[if IE 7]>         <body class="no-js lt-ie10 lt-ie9 lt-ie8 ie" lang="it"> <![endif]-->
<!--[if IE 8]>         <body class="no-js lt-ie10 lt-ie9 ie" lang="it"> <![endif]-->
<!--[if IE 9]>         <body class="no-js lt-ie10 ie" lang="it"> <![endif]-->
<!--[if gt IE 9]><!-->

<body lang="it" style="background-color:white;">
	<!--<![endif]-->
	<!--[if lt IE 7]>
		    	<p class="chromeframe">Stai utilizzando un browser <strong>obsoleto</strong>. Ti preghiamo di <a href="http://browsehappy.com/">aggiornare il tuo browser</a> o di <a href="http://www.google.com/chromeframe/?redirect=true">attivare Google Chrome Frame</a> per migliorare la fruizione dell'applicazione.</p>
		<![endif]-->

	<div id="loader" style="display:block">
	<div class="overlayer">
		<img src="<%=request.getContextPath()%>/images/Load1.gif" /> <span>Caricamento delle componenti</span>
		</div>
	</div>

	<div id="banner_preview" class="banner_preview" style="display:none">
		<h3>PREVIEW</h3>
	</div>
	
	<div class="generalContainer">
		<!-- PARAMETRI STAMPA -->
	    <div id="scaleText" style="width: 100%;float: left;height: 10%;padding: 15px;">
	        Stampa in scala 1:
		    <select id="lstScales" name="scala" onchange="appUrbamid.changeScale(this.value)">
		        <option value="20000000">20.000.000</option>
		        <option value="10000000">10.000.000</option>
		        <option value="5000000">5.000.000</option>
		        <option value="2000000">2.000.000</option>
		        <option value="1000000">1.000.000</option>
		        <option value="500000">500.000</option>
		        <option value="200000" selected>200.000</option>
		        <option value="100000">100.000</option>
		        <option value="50000">50.000</option>
		        <option value="20000">20.000</option>
		        <option value="10000">10.000</option>
		        <option value="5000">5.000</option>
		        <option value="2000">2.000</option>
		        <option value="1000">1.000</option>
		        <option value="500">500</option>
		    </select>
		    in formato:
		    <select id="lstFormati" name="formato" onchange="appUrbamid.changeFormat(this.value)">
            	<option value="a4v">A4 verticale</option>
            	<option value="a4h">A4 orizzontale</option>
            	<option value="a3">A3 orizzontale</option>
            </select>
            
		    <button onclick="appUrbamid.print()">Stampa</button><br>
		    <div style="font-size: x-small">
		        L'ingombro territoriale qui illustrato &#233; indicativo: in fase di stampa sar&#225; calcolato correttamente sulla base della scala impostata.
		    </div>
		</div>
	
		<!-- HEADER -->
		<jsp:include page="/template/header/header-print-map.jsp"></jsp:include>
		
		<!-- BOX MAPPA STAMPA -->
		<div id="printBox" style="background-color: white;width: 100%;float: left;height: 75%;">
			<!-- MAPPA -->
			<div id="containerMap" style="width: 100%;float: left;height: 100%;">
				<div id="map" class="map" style="position: absolute;top: 25%;height: 75%;width: 100%;float: left;left: 0px;right: 0px;bottom: 5px;"></div>
			</div>
		</div>
	</div>
	
	<!-- INCLUDE TEMPLATE HANDLEBARS -->
	<jsp:include page="/template/common/ricerche-handlebars.jsp"></jsp:include>
	<jsp:include page="/template/catasto/gestione-dati-handlebars.jsp"></jsp:include>
	<jsp:include page="/template/common/menu-handlebars.jsp"></jsp:include>
	<jsp:include page="/template/catasto/cartografia-handlebars.jsp"></jsp:include>
	<!-- CUSTOM PAGE CONTROLLER -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/BaseModaleCtrl.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/catasto/RicercaDatiPageCtrl.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/catasto/GestioneDatiPageCtrl.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/catasto/CartografiaPageCtrl.js"></script>
	
</body>
</html>