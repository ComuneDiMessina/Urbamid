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
	<meta name="_csrf" content="${_csrf.token}" />
	<!-- default header name is X-CSRF-TOKEN -->
	<meta name="_csrf_header" content="${_csrf.headerName}" />
	<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/80px-Messina-Stemma.ico" type="image/x-icon" />
	<title>Urbamid - Comune di Messina</title>

	
	<!-- Import plugin -->
	<script> 
		var appGestMappe = {};
	</script>
	<jsp:include page="/page/head.jsp"></jsp:include>
		<!-- CUSTOM URBAMID JS -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/catalogo.js"></script>								<!-- Per il caricamento del catalogo in mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/mappa.js" crossorigin="anonymous"></script>		<!-- Per il caricamento del catalogo in mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/urbamid.js"></script>								<!-- Js per il caricamento della mappa -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/util/rest.js"></script>									<!-- Js per chiamate Rest -->
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestMappe/gestione-mappe.js"></script>				<!-- Js per il caricamento della mappa -->
	<!-- Gestione tema js -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestioneTema/gestione-temi.js"></script>	
	<!-- Albero dei layer -->
	<link type="text/css" href="<%=request.getContextPath()%>/css/srg.css" rel="Stylesheet">
	<script> 
		var data = {};
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/plugin/moment.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsontratta.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsonpoi.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/geojsonpoipel.js"></script>
<%-- 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/include/mappa.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/general.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/util/jq-ajax-support.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/util/jBoxGroup.js"></script>
	
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

<body lang="it">
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
		<jsp:include page="/template/header/header-gestMappe.jsp"></jsp:include>
	    
	    <!-- ELENCO MAPPE -->
		<div id="containerTableMappe" style="background-color:#fff">
			<jsp:include page="/template/mappa/elencoMappe.jsp"></jsp:include>
		</div>
		
		<!-- DETTAGLIO MAPPA -->
		<div id="containerDetailMappa" style="display:none;background-color:#fff">
			<jsp:include page="/template/mappa/dettaglioMappa.jsp"></jsp:include>
		</div>
		
		<!-- NUOVA MAPPA -->
		<div id="containerGestioneTemi" style="display:none;background-color:#fff">
			<jsp:include page="/template/mappa/gestioneTemi.jsp"></jsp:include>
		</div>
		
	</div>
	
	<div id="duplicaModalData" class="modal fade customModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><i class="fa fa-exclamation-triangle" aria-hidden="true" style="float: left;margin-right: 10px;line-height: 25px;"></i>Warning</h4>
				</div>
				<div class="modal-body ui-dialog-content ">
					<span class="alert block alert-warning" id="messaggioWarning">Warning</span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- USER-MAP -->
	<jsp:include page="/template/footer/footer.jsp"></jsp:include>
	
	<!-- INCLUDE TEMPLATE HANDLEBARS -->
	<jsp:include page="/template/mappa/dettMappa-handlebars.jsp"></jsp:include>
</body>
</html>