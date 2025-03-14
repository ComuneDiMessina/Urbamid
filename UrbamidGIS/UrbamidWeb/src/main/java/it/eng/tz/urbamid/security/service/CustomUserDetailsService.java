package it.eng.tz.urbamid.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import it.eng.tz.urbamid.core.model.User;
import it.eng.tz.urbamid.core.model.UserProfile;
import it.eng.tz.urbamid.security.model.MyRole;
import it.eng.tz.urbamid.security.model.MyUser;
import it.eng.tz.urbamid.security.model.ProfiloUtenteDto;
import it.eng.tz.urbamid.web.dto.RuoloUtenteDto;
import it.eng.tz.urbamid.web.dto.RuoloUtenteShrDto;
import it.eng.tz.urbamid.web.dto.UtenteDto;


public class CustomUserDetailsService implements UserDetailsService{

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	static final String ROLE_CITTADINO = "ROLE_CITTADINO";
	
	@Autowired
	ProfileManagerService profileManagerService;

	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		ProfiloUtenteDto profilo = null;
		MyUser myUser = null; 
		try {
			profilo = profileManagerService.getUtenteByUsername(username);
			
			logger.info("User : {}", profilo);
			if(profilo==null){
				logger.info("User not found");
				throw new UsernameNotFoundException("Username not found");
			}
				/*return new org.springframework.security.core.userdetails.User(profilo.getUsername(), profilo.getUsername(), 
					 true, true, true, true, getGrantedAuthorities(profilo)); */
			
			List <MyRole> ruoli = new ArrayList<MyRole>();
			for(RuoloUtenteDto ruoloCaricato : profilo.getRuoli()  ) 
			{
				MyRole ruolo = new MyRole();
				ruolo.setCodice(ruoloCaricato.getCodice());
				ruolo.setId(ruoloCaricato.getId());
				ruoli.add(ruolo);
			}
				
				
				myUser = new MyUser(profilo.getUsername(),
						profilo.getUsername(), true, true, true, true,getGrantedAuthorities(profilo), 
						profilo.getNome(), profilo.getCognome(),
						profilo.getCodiceFiscale(), ruoli);

				return myUser;
				
				
		} catch (Exception e) {
			logger.error("loadUserByUsername --> errore nel recupero dell'utente. ", e);
			throw new UsernameNotFoundException("Username not found");
		}
		
	}

	public void createUser(UtenteDto utenteDto)throws UsernameNotFoundException {
		
		RuoloUtenteShrDto utenteRuoloDto = null;
		try {
			
			/**SALVO UTENTE E RUOLO**/
			utenteDto = profileManagerService.salvaUtente(utenteDto);
			utenteRuoloDto = new RuoloUtenteShrDto(utenteDto.getId(), ROLE_CITTADINO );
			profileManagerService.salvaUtenteRuolo(utenteRuoloDto);
		} catch (Exception e) {
			
			logger.error("createUser --> errore nel salvataggio e recupero dell'utente. ", e);
			throw new UsernameNotFoundException("Username not found");
		}
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(ProfiloUtenteDto profilo){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for(String userProfile : profilo.getPermessi()){
			logger.info("UserProfile : {}", userProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_"+ userProfile));
		}
		logger.info("authorities : {}", authorities);
		return authorities;
	} 


	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//Here we are using dummy data, you need to load user data from
	    // database or other third party application
		User user = findUserbyUername(username);
		
		if (user != null){
			return new org.springframework.security.core.userdetails.User(user.getSsoId(), user.getPassword(), 
					true, true, true, true, getGrantedAuthorities(user));
        }else{
            throw new UsernameNotFoundException(String.format("Username[%s] not found"));
        }

		
	}

	private User findUserbyUername(String username) {
		if(username.equalsIgnoreCase("admin")) {

			User user =  new User();

			user.setSsoId(username);
			user.setPassword("admin");


			UserProfile userProfile = new UserProfile();
			userProfile.setType("AMMINISTRATORE");

			user.getUserProfiles().add(userProfile);

			return user;
		}
		return null;
	} */


	private List<GrantedAuthority> getGrantedAuthorities(User user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for(UserProfile userProfile : user.getUserProfiles()){
			logger.info("UserProfile : {}", userProfile);
			authorities.add(new SimpleGrantedAuthority(userProfile.getType()));
		}
		logger.info("authorities : {}", authorities);
		return authorities;
	} 

}