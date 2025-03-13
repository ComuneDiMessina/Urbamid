package it.eng.tz.urbamid.profilemanager.configuration;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.DelegatingJwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.IssuerClaimVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableResourceServer
@PropertySource(value = { "classpath:application.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;
	@Autowired
	@Qualifier("httpClientRequestFactory")
	private ClientHttpRequestFactory httpClientRequestFactory;
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
			/*.antMatchers("/swagger-ui.html","/webjars/**","/swagger-resources/**", "/v2/**","/csrf","/public/**")
			.permitAll()
			.antMatchers("/**")
			.authenticated()
			*/
			.antMatchers("/**")
			.permitAll()
		.and()
			.cors()
			.configurationSource(corsConfigurationSource())
		.and()
			.exceptionHandling()
			.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config
		.tokenServices(tokenServices())
		.resourceId(env.getProperty("profilemanager.oauth.resource.id"));
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		return tokenServices;
	}

	@Bean
	public TokenStore tokenStore()
	{
		return new JwkTokenStore(env.getProperty("profilemanager.oauth.jwt.key.url"), accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter()
	{
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setAccessTokenConverter(new  DefaultAccessTokenConverter() {
			@Override
			public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
				final OAuth2Authentication auth = super.extractAuthentication(map);
				auth.setDetails(map);
				return auth;
			}
		});
		return converter;
	}
	
	@Bean
	public JwtClaimsSetVerifier jwtClaimsSetVerifier() {
		return new DelegatingJwtClaimsSetVerifier(Arrays.asList(issuerClaimVerifier()));
	}

	@Bean
	public JwtClaimsSetVerifier issuerClaimVerifier() {
		try {
			return new IssuerClaimVerifier(new URL(env.getProperty("profilemanager.oauth.jks.claim.issuer.url")));
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean(name= {"oauth2Rt"})
	public OAuth2RestTemplate oauth2RestTemplate() {
		BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
//		resource.setGrantType("implicit");
//        ImplicitResourceDetails resource = new ImplicitResourceDetails();
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource); 
        restTemplate.setRequestFactory(httpClientRequestFactory);
        return restTemplate;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		boolean abilitaCors = Boolean.parseBoolean(env.getProperty("profilemanager.web.cors.enbaled"));
		if( abilitaCors )
		{

			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowCredentials(true);
			configuration.addAllowedOrigin("*");
			configuration.addAllowedHeader("*");
			configuration.addAllowedMethod("*");
			configuration.setExposedHeaders(Arrays.asList("X-Auth-Token","x-auth-token", "x-requested-with", "x-xsrf-token","Access-Control-Allow-Origin", "content-type"));

			/*configuration.setAllowedOrigins(Arrays.asList(	"http://localhost:4200",
															"http://localhost:8080", 
															"http://localhost:8180"));
			configuration.setAllowedMethods(Arrays.asList(	
					RequestMethod.GET.name(),
					RequestMethod.POST.name(), 
					RequestMethod.OPTIONS.name(), 
					RequestMethod.DELETE.name(),
					RequestMethod.PUT.name()));
			configuration.setExposedHeaders(Arrays.asList("x-auth-token", "x-requested-with", "x-xsrf-token", "Access-Control-Allow-Origin", "content-type"));
			configuration.setAllowedHeaders(Arrays.asList("X-Auth-Token","x-auth-token", "x-requested-with", "x-xsrf-token","Access-Control-Allow-Origin", "content-type"));*/
			source.registerCorsConfiguration("/**", configuration);
		}
		return source;
	}
}