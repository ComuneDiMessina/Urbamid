package it.eng.tz.urbamid.security.service.saml2.provider.metadata;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class CitizenSamlAuthentication  extends AbstractAuthenticationToken{

	private static final long serialVersionUID = -30458702841977682L;
	private UserDetails userDetails;

	public CitizenSamlAuthentication(UserDetails userDetails) {
		super(userDetails.getAuthorities());
		this.userDetails = userDetails;
		setAuthenticated(userDetails.isEnabled());
	}
	@Override
	public Object getCredentials() {
		
		return userDetails.getAuthorities();
	}

	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}
}
