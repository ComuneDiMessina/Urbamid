<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="site-hat bg-secondary text-white usernav-collapse d-md-block">
    <div class="container-fluid">
        <div class="row nopt">
            <div class="col align-self-end">
                <ul class="nav">
                    
					<li class="nav-item" style="height: 30px;line-height: 30px;">
                        <a class="nav-link" href="./home">
                            <span class="nav-link-label" style="display:inline !important;"><span class="resp-display-2">Home</span> 
                            <i class="fa fa-home px-2" aria-hidden="true"></i></span>
                        </a>
                    </li>
                    <li class="nav-item" style="height: 30px;line-height: 30px;border-left:1px solid white">
                        <a class="nav-link" href="#" >
                            <span class="nav-link-label" style="display:inline !important;"><span class="resp-display-2">Assistenza</span> <i class="fa fa-question-circle"></i></span>
                        </a>
                    </li>
					<li class="nav-item" style="height: 30px;line-height: 30px;border-left:1px solid white">
                        <a class="nav-link" href="#">
                            <span class="nav-link-label" style="display:inline !important;">
                                Benvenuto 
                            </span>
							<strong class="nav-link-label" style="display:inline !important;">${nome} ${cognome}</strong>
                        </a>
                    </li>
                    <li class="nav-item" style="height: 30px;line-height: 30px;border-left:1px solid white">
                        <a class="nav-link" href="logout">
                            <span class="nav-link-label" style="display:inline !important;"><span class="resp-display-2">Esci </span> <i class="fa fa-sign-out"></i></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<nav class="navbar navbar-expand-lg navbar-light bg-primary navbar-offcanvas">
	<div class="navbar-brand mr-auto">
		<div class="navbar-co-brand pl-4">
			<div class="araldica-citta-messina">
				<div class="brand-img img_home">
					<img src="./images/messina.svg" alt="Araldica Citt&#225; Metropolitana di Messina">
				</div>
				<div class="brand-label" style="padding-top:10px">
					<span class="second-line  header-blu">Citt&#225; di Messina</span><br />
					<span class="third-line   header-blu">Citt&#225; Metropolitana di Messina</span>
				</div>
			</div>
		</div>
		<span class="navbar-co-brandno-border">
			<span class="navbar-co-brand-label"></span>
		</span>
    </div>	  
</nav>