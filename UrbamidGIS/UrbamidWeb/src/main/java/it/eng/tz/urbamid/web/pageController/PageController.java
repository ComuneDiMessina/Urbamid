package it.eng.tz.urbamid.web.pageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import it.eng.tz.urbamid.security.model.MyUser;

/**
 * Controller principale Spring MVC.
 * @author Alessandro PAolillo
 */
@Controller(value = "PageController")
public class PageController extends AbstractController {
	
	@Autowired
	Environment environment;
	
	@GetMapping(value = "/")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "redirect:/home";
	}
	
	/**
	 * Metodo che gestisce mediante GET lo stato della connessione al SIT
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/Mappa")
	public ModelAndView mappa(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("auth/login");
		return mav;	
	}
	
	/**
	 * Metodo che gestisce la logout al SIT
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
		if (auth != null){      
			
			new SecurityContextLogoutHandler().logout(request, response, auth);  
		}  
		return "redirect:/login";  	
	}

	/**
	 * Metodo che gestisce la GET l'accesso al SIT
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = null;
		if (!isAuthenticated())  {
			
			mav = new ModelAndView("auth/accessoNegato");
			mav.addObject("error", request.getParameter("error") );
		} else  {

			mav = new ModelAndView("index");
		}
		/*InMemoryRelyingPartyRegistrationRepository idps = (InMemoryRelyingPartyRegistrationRepository)relyingPartyRegistrations;
		
		   for (Iterator i = idps.iterator(); i.hasNext(); ) 
		   {
			   
			   RelyingPartyRegistration idp =  (RelyingPartyRegistration)i.next();
			   String regId = idp.getRegistrationId();
			   mav.addObject("regId", regId);
		   }  */          
		return mav;	
	}
	
	/**
	 * Metodo che gestisce la GET la pagina di sessione scaduta sul SIT
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/loginExpired")
	public ModelAndView loginExpired(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = null;
		if (!isAuthenticated()) {
			
			mav = new ModelAndView("auth/chiudiBrowser");
		}
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET il non accesso al SIT
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/accessoNegato")
	public ModelAndView accessoNegato(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("auth/accessoNegato");
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET il non accesso alla funzionalità del SIT
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/accessoNegatoFunzionalita")
	public ModelAndView accessoNegatoFunzionalita(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("auth/accessoNegatoFunzionalita");
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET la login al SIT
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/users/login")
	public ModelAndView UserLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = null;
		if (!isAuthenticated())  {
			
			mav = new ModelAndView("auth/accessoNegato");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav = new ModelAndView("index");
		}         
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla home page
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/home")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("index");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegato");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
			mav.addObject("codicefiscale", currentUser.getCodiceFiscale() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla home del SIT
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/users/home")
	public ModelAndView UserHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("index");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
	    	 
	    	 mav = new ModelAndView("auth/accessoNegato");
	    	 mav.addObject("error", request.getParameter("error") );
	    } else {
	    	 
	    	 mav.addObject("nome", currentUser.getNome() );
		     mav.addObject("cognome", currentUser.getCognome() );
		     mav.addObject("codicefiscale", currentUser.getCodiceFiscale() );
	    }
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso al webgis
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/webgis")
	public ModelAndView webgis(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("webgis/webgis");
		if ( request.getParameter("role")!=null && request.getParameter("role").equals("guest") ) {
			
			mav.addObject("nome", "Utente" );
			mav.addObject("cognome", "Esterno" );
		}
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET la stampa di una mappa
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/print")
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("print/print");
		if ( request.getParameter("role")!=null && request.getParameter("role").equals("guest") ) {
			
			mav.addObject("nome", "Utente" );
			mav.addObject("cognome", "Esterno" );
		}
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla gestione del Catasto
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoCatasto")
	public ModelAndView catasto(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("catasto/catasto");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
			
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla consultazione del Catasto
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoConsCatasto")
	public ModelAndView catastoConsultazione(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("catasto/catasto");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
			
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alle ricerche del Catasto
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/users/ricercaCatasto")
	public ModelAndView consultaCatasto(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("catasto/catasto");
		MyUser currentUser = getCurrentUser();
		mav.addObject("nome", currentUser.getNome() );
		mav.addObject("cognome", currentUser.getCognome() );
		return mav;	
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina principale di Gestione Dati Toponomastica.
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoToponomastica")
	public ModelAndView toponomastica(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("toponomastica/toponomastica");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina principale di Consultazione Dati Toponomastica.
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoConsToponomastica")
	public ModelAndView toponomasticaConsultazione(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("toponomastica/toponomastica");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina principale della gestione Mappe.
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoGestioneMappe")
	public ModelAndView gestioneMappe(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("mappe/gestioneMappe");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina principale del profilatore
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoGestioneProfile")
	public ModelAndView manageProfile(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("profile/gestioneProfile");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina principale di Gestione Piano Regolatore
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoPRG")
	public ModelAndView pianoRegolatore(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("pianoRegolatore/pianoRegolatore");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina principale di Consultazione Piano Regolatore
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoConsPRG")
	public ModelAndView pianoRegolatoreConsultazione(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("pianoRegolatore/pianoRegolatore");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina principale di Editing dei layer
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/accessoEditingLayer")
	public ModelAndView editingLayer(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("editingLayer/editingLayer");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina dei servizi rest del SIT
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/urbamidGeoServices")
	public ModelAndView urbamidGeoServices(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("urbamidGeoServices/urbamidGeoServices");
		MyUser currentUser = getCurrentUser();
		if (!isAuthenticated() || currentUser == null) {
			
			mav = new ModelAndView("auth/accessoNegatoFunzionalita");
			mav.addObject("error", request.getParameter("error") );
		} else {
			
			mav.addObject("nome", currentUser.getNome() );
			mav.addObject("cognome", currentUser.getCognome() );
		}
		return mav;
	}
	
	/**
	 * Metodo che gestisce mediante GET l'accesso alla pagina principale dei servizi gis.
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return la stringa che viene risolta dal {@link ViewResolver} di Spring
	 */
	@GetMapping(value = "/servicegis")
	public String servicegis(HttpServletRequest request, HttpServletResponse response) {
		
		return "servicegis/servicegis";
	}
	
	public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
		
		for (E e : enumClass.getEnumConstants()) {
			if (e.name().equals(value)) {
				return true;
			}
		}
		return false;
	}
}
