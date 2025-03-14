package it.eng.tz.urbamid.web.pageController;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import it.eng.tz.urbamid.security.model.MyRole;
import it.eng.tz.urbamid.security.model.MyUser;

public abstract class AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(AbstractController.class.getName());
	
	protected boolean isAuthenticated() 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		return auth != null &&
				 SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				 !(SecurityContextHolder.getContext().getAuthentication() 
				          instanceof AnonymousAuthenticationToken);
	}
	
//	protected MyUser getCurrentUser() 
//	{
//		logger.error("######################################	START getCurrentUser");
//		MyUser currentUser = null;
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    if ( auth != null && auth.getPrincipal()!=null ) {
//	    	logger.error("######################################	auth != null && auth.getPrincipal()!=null");	
//	    	
//	    	currentUser = (MyUser)auth.getPrincipal();
//	    }
////	    if (auth != null) {
////			  if (auth.getPrincipal()!=null && auth.getPrincipal() instanceof DefaultSaml2AuthenticatedPrincipal) {
////				  
////				  /**Autenticazione con SAML**/
////				  DefaultSaml2AuthenticatedPrincipal userObj = (DefaultSaml2AuthenticatedPrincipal)auth.getPrincipal();
////				  String givenName = (String) userObj.getAttribute("http://wso2.org/claims/givenname").get(0);
////				  String lastname = (String) userObj.getAttribute("http://wso2.org/claims/lastname").get(0);
////				  String fiscalnumber = (String) userObj.getAttribute("http://wso2.org/claims/fiscalNumber").get(0);
////				  currentUser = new MyUser(fiscalnumber, "", true, true, true, true, new Collection(), givenname,lastname,fiscalnumber, );
////			  } else {
////				  
////				  /**Autenticazione**/
////				  currentUser = (MyUser)auth.getPrincipal();
////			  }
////		    } else {
////		    	
////		    	/**TODO: settaggio di un utente anonimo**/
//////		    	currentUser = new MyUser("","",false,true,true,true,);
////		    }
//	    return currentUser;
//
//	}
	
	protected MyUser getCurrentUser() {
		
		logger.debug("######################################	START getCurrentUser");
		MyUser currentUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MyUser) {
				
				currentUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				logger.debug("######################################>>>>	getCurrentUser UTENTE RECUPERATO");
			}			
		} else {
			logger.error("######################################>>>> getCurrentUser ERRORE ERRORE ERRORE");	
		}
	    return currentUser;
	}
}
