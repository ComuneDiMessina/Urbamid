package it.eng.tz.urbamid.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import it.eng.tz.urbamid.core.config.UrbamidConfiguration;
import it.eng.tz.urbamid.security.config.CitizenSAMLWebSecurityConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
   @Override
   protected Class<?>[] getRootConfigClasses() {
	   return new Class[] { UrbamidConfiguration.class,  CitizenSAMLWebSecurityConfig.class };
   }
 
   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] { UrbamidWebMvcConfig.class };
   }
 
   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }
}
