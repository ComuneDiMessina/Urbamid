package it.eng.tz.urbamid.profilemanager.configuration;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource(value = { "classpath:application.properties" }, encoding = "UTF-8", ignoreResourceNotFound = false)
public class RestApiDocumentationConfiguration {
	@Autowired
	private Environment env;
	@SuppressWarnings("unchecked")
	@Bean
	public Docket api() { 
		Docket result = new Docket(DocumentationType.SWAGGER_2)  
				.select()                                  
				.apis(RequestHandlerSelectors.any())              
				.paths(and(not(PathSelectors.regex("/actuator*")), not(PathSelectors.regex("/actuator/*")), not(PathSelectors.regex("/error*"))))
				.build()
				.securitySchemes(Lists.newArrayList(apiKey()))
				.securityContexts(Lists.newArrayList(securityContext()))
				.apiInfo(apiInfo());
				getTags(result);

		boolean abilitaCors = Boolean.parseBoolean(env.getProperty("profilemanager.web.cors.enbaled"));
		if( abilitaCors )
		{
			List<Parameter> params = new ArrayList<>(5);
			//"X-Auth-Token","x-auth-token", "x-requested-with", "x-xsrf-token","Access-Control-Allow-Origin", "content-type"
			ParameterBuilder aParameterBuilder = new ParameterBuilder();
			params.add(aParameterBuilder.name("Access-Control-Allow-Origin")                 // name of header
					.modelRef(new ModelRef("string"))
					.parameterType("header")               // type - header
					.defaultValue("*")
					.required(false)
					.build());
//			params.add(aParameterBuilder.name("X-Auth-Token")                 // name of header
//					.modelRef(new ModelRef("string"))
//					.parameterType("header")               // type - header
//					.required(false)
//					.build());
//			params.add(aParameterBuilder.name("x-auth-token")                 // name of header
//					.modelRef(new ModelRef("string"))
//					.parameterType("header")               // type - header
//					.required(false)
//					.build());
//			params.add(aParameterBuilder.name("x-xsrf-token")                 // name of header
//					.modelRef(new ModelRef("string"))
//					.parameterType("header")               // type - header
//					.required(false)
//					.build());
			params.add(aParameterBuilder.name("Content-Type")  // name of header
					.modelRef(new ModelRef("string"))
					.parameterType("header")                  // type - header
					.defaultValue("application/json")
					.required(false)
					.build());
			params.add(aParameterBuilder.name("Origin")  // name of header
					.modelRef(new ModelRef("string"))
					.parameterType("header")                  // type - header
					.defaultValue("INDIRIZZO BROWSER")
					.required(false)
					.build());
			
			result.globalOperationParameters(params);
		}
		return result;
	}

	private ApiInfo apiInfo(){
		return new ApiInfoBuilder().title("API Profile_Manager").version("1.0.0").build();
	}

	private void getTags(Docket docket){
		docket.tags(new Tag("Applicazione", " Controller che si occupa della gestione delle Applicazioni"),
					new Tag("Ruolo", " Controller che si occupa della gestione dei Ruoli"),
				    new Tag("Gruppo", " Controller che si occupa della gestione dei Gruppi"),
					new Tag("Permesso", " Controller che si occupa della gestione dei Permessi"),
				    new Tag("TipoGruppo", " Controller che si occupa della gestione dei Tipo Gruppo"),
					new Tag("Utente", " Controller che si occupa della gestione degli Utenti"),
					new Tag("LoggedUser", " Controller che si occupa della gestione degli Utenti Registrati"),
					new Tag("ApplicazioneUtente", " Controller che si occupa della gestione della relazioni tra Appicazioni e Utenti"),
					new Tag("UtenteRuolo", " Controller che si occupa della gestione delle relazioni tra Utenti e Ruoli"),
					new Tag("UtenteGruppo", " Controller che si occupa della gestione delle relazioni tra Utenti e Gruppi"),
					new Tag("GruppoRuolo", " Controller che si occupa della gestione delle relazioni tra Gruppi e Ruoli"),
					new Tag("RichiestaAbilitazione", "  Controller che si occupa della gestione delle Richieste di Abilitazione"),
					new Tag("RuoloPermesso", " Controller che si occupa della gestione della relazioni tra Appicazioni e Utenti"),
					new Tag("Ricerche", " Controller che si occupa della gestione delle ricerche con filtri"));
	}

	@Bean
	SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.any())
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(
				new SecurityReference("JWT", authorizationScopes));
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}
}
