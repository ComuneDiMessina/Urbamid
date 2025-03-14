<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="site-hat bg-secondary text-white usernav-collapse d-md-block" style="padding: 0px;">
    <div class="container-fluid">
        <div class="row nopt">
            <div class="col align-self-end">
                <ul class="nav" style="height: 30px;">
                    <li class="nav-item" style="line-height: 30px;margin-top: 0px;padding: 0px 0px 0px 0px;">
                        <a class="nav-link" href="#" >
                            <span class="nav-link-label">
                                Assistenza <i class="fa fa-question-circle"></i>
                            </span>
                        </a>
                    </li>
					<li class="nav-item" style="line-height: 30px;margin-top: 0px;padding: 0px 0px 0px 0px;border-left:1px solid white">
                        <a class="nav-link" href="#" style="padding:0px 10px 0px 10px;">
                            <span class="nav-link-label">
                                Benvenuto 
                            </span>
							<strong class="nav-link-label">${nome} ${cognome}</strong>
                        </a>
                    </li>
                    <li class="nav-item" style="line-height: 30px;margin-top: 0px;padding: 0px 0px 0px 0px;border-left:1px solid white">
                        <a class="nav-link" href="logout">
                            <span class="nav-link-label">
                                Esci <i class="fa fa-sign-out"></i>
                            </span>
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
					<span class="second-line header-blu">Citt&#225; di Messina</span><br />
					<span class="third-line  header-blu">Citt&#225; Metropolitana di Messina</span>
				</div>
			</div>
		</div>
		<span class="navbar-co-brandno-border">
			<span class="navbar-co-brand-label"></span>
		</span>
    </div>	  
</nav>