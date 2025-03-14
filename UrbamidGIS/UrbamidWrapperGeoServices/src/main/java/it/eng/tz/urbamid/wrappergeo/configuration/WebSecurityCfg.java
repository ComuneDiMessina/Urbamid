package it.eng.tz.urbamid.wrappergeo.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//@Configuration
//@EnableWebSecurity
@PropertySource(value = { "classpath:application.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment env;
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        	.anyRequest()
        	.permitAll()
        .and()
	        .csrf()
	        .disable()
        	.cors()
        	.configurationSource(corsConfigurationSource());
    }
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		boolean abilitaCors = Boolean.parseBoolean(env.getProperty("wrappergeo.web.cors.enbaled"));
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