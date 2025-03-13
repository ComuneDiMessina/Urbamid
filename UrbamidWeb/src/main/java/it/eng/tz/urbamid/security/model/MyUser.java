package it.eng.tz.urbamid.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUser extends User {


	private static final long serialVersionUID = 1L;
	
	private final String nome;
    private final String cognome;
    private final String codiceFiscale; 
    private final List<MyRole> ruoli;
    
    
	public MyUser(String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection authorities,
            String nome, String cognome, String codiceFiscale, List<MyRole> ruoli) {

                super(username, password, enabled, accountNonExpired,
                   credentialsNonExpired, accountNonLocked, authorities);

                this.nome = nome;
                this.cognome = cognome;
                this.codiceFiscale = codiceFiscale;
                this.ruoli = ruoli;
        }
    
    
    public String getNome() {
		return nome;
	}


	public String getCognome() {
		return cognome;
	}


	public String getCodiceFiscale() {
		return codiceFiscale;
	}


	public List<MyRole> getRuoli() {
		return ruoli;
	}
	
    
}
