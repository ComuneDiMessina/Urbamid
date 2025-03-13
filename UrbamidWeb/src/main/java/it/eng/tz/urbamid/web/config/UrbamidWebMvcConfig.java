package it.eng.tz.urbamid.web.config;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import it.eng.tz.urbamid.web.util.ProxyAuthenticator;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "it.eng.tz.urbamid" })
@PropertySource(value = { "classpath:configuration.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
@EnableScheduling
public class UrbamidWebMvcConfig extends WebMvcConfigurerAdapter
{

	private static final Log logger = LogFactory.getLog(UrbamidWebMvcConfig.class.getName());
	// max upload file size di default 10485760 byte = 10Mb = 100Mb * 1024 (Kb/1Mb) * 1024 (1 byte/1Kb)
	private static final long DEFAULT_MAX_UPLOAD_SIZE_FILE = 4294967296L; //Equivalgono a 4GB

	@Autowired
	private Environment env;
	
	public UrbamidWebMvcConfig() {
		super();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/bundle/**").addResourceLocations("/bundle/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/webfonts/**").addResourceLocations("/webfonts/");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/plugin/**").addResourceLocations("/plugin/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
	{
		configurer
			.useJaf(false)
			.favorPathExtension(false)
			.ignoreAcceptHeader(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("html", MediaType.TEXT_HTML)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML);
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager)
	{
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		List<ViewResolver> viewResolvers = new ArrayList<>(2);
		viewResolvers.add(jspViewResolver());
		viewResolvers.add(beanNameViewResolver());
		resolver.setViewResolvers(viewResolvers);
		List<View> defaultViews = new ArrayList<>(1);
		defaultViews.add(new MappingJackson2JsonView());
		resolver.setDefaultViews(defaultViews);
		return resolver;
	}

	@Bean("beanNameViewResolver")
	public ViewResolver beanNameViewResolver()	{
		return new BeanNameViewResolver();
	}

	@Bean("jspViewResolver")
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/srg/page/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
 
	// Resource bundle
	@Bean
	public MessageSource messageSource()
	{
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/bundle/Messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}
	@Bean
	public Boolean initSystemProxyConnection() {
		try {

			boolean proxyEnable = Boolean.parseBoolean(env.getProperty("urbamid.web.http.client.proxyEnable"));
			String portNumber = null;
			try {
				
				portNumber = env.getProperty("urbamid.web.http.client.portProxy");
				String proxyHost = env.getProperty("urbamid.web.http.client.hostProxy");
				String usernameProxy = env.getProperty("urbamid.web.http.client.usernameProxy");
				String passwordProxy = env.getProperty("urbamid.web.http.client.passwordProxy");
				String nonProxyHosts = "localhost";
				int proxyPort = Integer.parseInt(portNumber);
				System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
				
				/**** Proxy ****************************************/
				if (proxyEnable) {
	
					System.setProperty("http.proxyHost", proxyHost);
					System.setProperty("http.proxyPort", String.valueOf(proxyPort));
					System.setProperty("http.proxyUser", usernameProxy);
					System.setProperty("http.proxyPassword", passwordProxy);
					System.setProperty("http.nonProxyHosts", nonProxyHosts);
					
					
					System.setProperty("https.proxyHost", proxyHost);
					System.setProperty("https.proxyPort", String.valueOf(proxyPort));
					System.setProperty("https.proxyUser", usernameProxy);
					System.setProperty("https.proxyPassword", passwordProxy);
					System.setProperty("https.nonProxyHosts", nonProxyHosts);
					
					if (StringUtils.hasText(proxyHost) && StringUtils.hasText(proxyHost) && StringUtils.hasText(usernameProxy) && StringUtils.hasText(portNumber) && StringUtils.hasText(passwordProxy)  ) { {
						if (usernameProxy != null && !usernameProxy.equals("")
								&& passwordProxy != null && !passwordProxy.equals(""))
							Authenticator.setDefault(new ProxyAuthenticator(
									usernameProxy, passwordProxy));
					}
					
					logger.info(">>>>>>>>>>>>>>>>>>>>> PROXY ABILITATO >>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
				}
				else
					logger.info(">>>>>>>>>>>>>>>>>>>>> PROXY NON ABILITATO >>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
				}
			}catch(NumberFormatException nfe) {
				logger.error(">>> proxyPort is not a numeric field in configuration.properties ["+ portNumber + "]... skipping configuration!!!");
			}

			/***************************************************/
		} catch (Exception e) {
			logger.error("Errore nell 'impostare i dati del proxy " + e, e);
			throw (e);
		}
		return true;
	}
	
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    String property = env.getProperty("urbamid.jackrabbit.max.upload.file.size");
	    Long maxUploadSizePerFile = property!=null ? Long.parseLong(property) : DEFAULT_MAX_UPLOAD_SIZE_FILE;	    
	    resolver.setMaxUploadSizePerFile(maxUploadSizePerFile);
	    return resolver;
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
