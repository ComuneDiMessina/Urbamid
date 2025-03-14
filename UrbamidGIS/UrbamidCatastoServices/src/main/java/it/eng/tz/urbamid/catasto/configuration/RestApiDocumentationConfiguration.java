package it.eng.tz.urbamid.catasto.configuration;

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

		boolean abilitaCors = Boolean.parseBoolean(env.getProperty("catasto.web.cors.enbaled"));
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
		return new ApiInfoBuilder().title("API CATASTO").version("1.0.0").build();
	}

	private void getTags(Docket docket){
		docket.tags(
				new Tag("BatchManagement", " API REST per la gestione dei batch di import dati catastali."),
				new Tag("StorageCatasto", " API REST per la gestione delle operazioni CRUD sui file sulla quale opera la procedura di import dei dati catastali forniti dall'agenzia del territorio."),
				new Tag("Shapefile", " API REST per l'import di uno shapefile nel database."),
				new Tag("PSQL", " API REST per l'esecuzione di script sql via PSQL."),
				new Tag("JobManagement", " API REST per il richiamo dei vari job geokettle."),
				new Tag("Python", "API REST per il richiamo della procedura python di conversione dei file CXF in shapefile"),
				new Tag("CatastoRicerche", "API REST per le ricerche catastali"),
				new Tag("TipologicheCatasto", "API REST per il recupero delle tipologiche catastali"),
				new Tag("CatastoExport", "API REST per l'export dei dati in vari formati"),
				new Tag("StoricoTabelle", "API REST per la storicizzazione delle tabelle geografiche"),
				new Tag("Ricerche", "API REST per le ricerche dei dati catastali tramite filtri")
				);
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
