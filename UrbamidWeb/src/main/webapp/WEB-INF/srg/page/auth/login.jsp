<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="keywords" content="Snam,mappa, gasdotti, rete gasdotti, simulazioni" />
		<meta content="initial-scale=1.0, maximum-scale=1.0, width=device-width, user-scalable=no" name="viewport" />
		<meta name="description" content="Urbamid" />
		<title>Accesso area riservata Urbamid</title>
		<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/80px-Messina-Stemma.ico" type="image/x-icon" />	
		
		<link type="text/css" href="<%=request.getContextPath()%>/plugin/jquery-ui-1.12.1/jquery-ui.min.css" rel="Stylesheet">
		<script type="text/javascript" src="<%=request.getContextPath()%>/plugin/jquery-2.2.0/jquery-2.2.0.min.js"></script>
		<!-- Bootstrap -->
		<link type="text/css" href="<%=request.getContextPath()%>/plugin/bootstrap-v3.3.6/css/bootstrap.min.css" rel="Stylesheet" />
		<script src="<%=request.getContextPath()%>/plugin/bootstrap-v3.3.6/js/bootstrap.min.js"></script>
	
		<!-- Import plugin -->
		<jsp:include page="/page/head-slim.jsp"></jsp:include>
		
		<link type="text/css" href="<%=request.getContextPath()%>/css/common.css" rel="Stylesheet" />
		<!--[if gte IE 9]>
		  <style type="text/css">
		    .gradient {
		       filter: none;
		    }
		  </style>
		<![endif]-->
		<style>
			.imgLogo {width: 400px;height: 130px;}
			@media (max-width: 767px) {
				.imgLogo {width: 240px;height: 78px;margin-bottom:20px;}
				.square_1, .square_2, .square_3, .square_4, .square_5, .square_6, .square_7 { display:none;}
				.modulo-login {margin:30px auto;}
			}
		</style>
	</head>
	<body onload='document.loginForm.username.focus();' class="login">
		<div class="square_1"></div>
		<div class="square_2"></div>
		<div class="square_3"></div>
		<div class="square_4"></div>
		<div class="square_5"></div>
		<div class="square_6"></div>
		<div class="square_7"></div>
		<div class="modulo-login" style="text-align: center;">
			<header>
				<img class="imgLogo" src="<%=request.getContextPath()%>/images/logo/Urbamid_logo_positivo.png" />
			</header>
			<!--h1>Login</h1-->
			<div id="mess-error" class="alert alert-danger hide" role="alert">
				<strong>Attenzione!</strong> Siamo spiacenti, si &#232; verificato un errore nella procedura di autenticazione.
				Si prega di riprovare pi&#249; tardi o di contattare l'amministratore.
			</div>
			
			<c:url var="loginUrl" value="/login" />
			<form name='loginForm' id='loginForm' action="${loginUrl}" method='POST' class="form-horizontal">
				<div class="form-group noml nomr" style="margin-left:0px!important">
					<label class="control-label bold">Username</label>
					<!--div class="col-sm-9"-->
						<input id="username" type='text' class="form-control" name="username" style="height:40px;" value="" />
					<!--/div-->
				</div>
				<div class="form-group noml nomr" style="margin-left:0px!important">
					<label class="control-label bold">Password</label>
					<!--div class="col-sm-9"-->
						<input id="password" class="form-control" type='password' style="height:40px;" name='password' />
					<!--/div-->
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="submit" class="btn btn-block btn-login" style="margin-top:15px; line-height:34px; border-radius:2px" value="Accedi"/>
			</form>
		</div>
	</body>
</html>