package it.eng.tz.urbamid.security.config;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml2.core.Saml2X509Credential;
import org.springframework.security.saml2.core.Saml2X509Credential.Saml2X509CredentialType;
import org.springframework.security.saml2.provider.service.authentication.OpenSamlAuthenticationProvider;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import it.eng.tz.urbamid.security.handler.CustomAccessDeniedHandler;
import it.eng.tz.urbamid.security.service.CustomUserDetailsService;
import it.eng.tz.urbamid.security.service.saml2.provider.metadata.CitizenSamlAuthentication;
import it.eng.tz.urbamid.web.dto.UtenteDto;

/*
 * Riferimento: https://www.chakray.com/wso2-identity-server-integration-spring-boot-security-saml/
 * https://stackoverflow.com/questions/50696976/spring-security-with-both-sso-and-form-login
 * https://www.baeldung.com/spring-security-two-login-pages
 * https://www.baeldung.com/spring-security-multiple-auth-providers
 * https://docs.spring.io/spring-security-saml/docs/current/reference/html/chapter-idp-guide.html
 * https://dzone.com/articles/tldr-database-authentication-spring-security-saml
 * https://stackoverflow.com/questions/25794680/multiple-authentication-mechanisms-in-a-single-app-using-java-config
 * https://stackoverflow.com/questions/29027961/can-i-implement-both-saml-and-basic-spring-security-within-an-application
 * https://github.com/spring-projects/spring-security-saml
 * https://codetinkering.com/spring-security-saml2-service-provider-example/
 * https://github.com/spring-projects/spring-security/blob/master/docs/manual/src/docs/asciidoc/_includes/servlet/saml2/saml2-login.adoc
 * https://github.com/spring-projects/spring-security-saml/blob/master/sample/src/main/webapp/WEB-INF/securityContext.xml
 * https://www.baeldung.com/spring-security-multiple-entry-points
 * https://openclassrooms.com/en/courses/5683681-secure-your-web-application-with-spring-security/6695831-configure-oauth-2-0-with-openid-connect-on-a-spring-web-application
 * 
 * https://dzone.com/articles/tldr-database-authentication-spring-security-saml

http://www.canchito-dev.com/public/blog/2020/11/22/spring-security-with-saml2-and-okta/


 * 
 * github oauth2
 * clientId= cb46e3ce59be3eabb124
 * Client secrets = ba3d69d0d4390c27f64221118501e65abbef6bbf
 * 
 * 
 * 
 */

@EnableWebSecurity
@Configuration
public class CitizenSAMLWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(CitizenSAMLWebSecurityConfig.class.getName());
	
	private static final String CF_TINIT_PREFIX = "TINIT-";

	@Value("${urbamid.spid.external.idp.metadata.location}")
	private String assertingPartyMetadataLocation;

	@Value("${urbamid.spid.service.provider.sael.admin.metadata.entity.id}")
	private String spEntityId;

	@Value("${urbamid.spid.external.idp.metadata.registration.id}")
	private String registrationId;

	@Value("${urbamid.spid.jks.alias}")
	private String keyStoreAlias;

	@Value("${urbamid.spid.jks.key.password}")
	private String keyStoreKeyPassword;

	@Value("${urbamid.spid.baseClaims}")
	private String urbamidSpidBaseClaims;

	@Value("${urbamid.spid.claims}")
	private String urbamidSpidClaims;
	
	@Value("${urbamid.spid.assertion.consumer.location}")
	private String assertionConsumerServiceLocation;

	@Autowired
	@Qualifier("urbamidKeyStore")
	private KeyStore ks;
	public static final String LOGOUT_URL = "/logout";
	public static final String LOGIN_PAGE = "/users/login";

//	@Order(1) 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		OpenSamlAuthenticationProvider authenticationProvider = new OpenSamlAuthenticationProvider();
		authenticationProvider.setResponseAuthenticationConverter(responseToken -> {
			
			Assertion assertion = responseToken.getResponse().getAssertions().get(0);
			List<String> nameClaims = Arrays.asList(urbamidSpidClaims.split("\\s*;\\s*"));
			HashMap<String,String> hm = new HashMap<String,String>();
			/** RECUPERO DEI CLAIMS **/
			List<AttributeStatement> attrStatements = assertion.getAttributeStatements();
			String value = new String();
			for (AttributeStatement attrStatement : attrStatements) {
				List<Attribute> attrs = attrStatement.getAttributes();
				for (Attribute attr : attrs) {
					
					String nomeAttributo = attr.getName();
					List<XMLObject> valoriAttributo = attr.getAttributeValues();
					XMLObject valueObj = valoriAttributo.get(0);
					value = getValue(valueObj, value);
					hm.put(nomeAttributo,value);
				}
			}
			String codiceFiscale = hm.get(nameClaims.get(0));
			String email = hm.get(nameClaims.get(1));
			String name = hm.get(nameClaims.get(2));
			String familyName = hm.get(nameClaims.get(3));
			/** VERIFICO SE UTENTE E' PRESENTE IN URBAMID **/
			if (!StringUtils.hasText(codiceFiscale)) {
				throw new IllegalStateException(
						"Impossibile proseguire. Codice Fiscale non trovato tra gli attributi SAML");
			} else if (codiceFiscale != null && codiceFiscale.startsWith(CF_TINIT_PREFIX)) {
				
		        codiceFiscale = codiceFiscale.substring(CF_TINIT_PREFIX.length());
			}
			
			UserDetails userDetails = null;
			try {
				userDetails = this.userDetailsService().loadUserByUsername(codiceFiscale);
			} catch (UsernameNotFoundException unfException) {
				logger.error("Salvataggio utente");
				UtenteDto utenteDto = new UtenteDto(codiceFiscale, codiceFiscale, name, familyName, "", email, true);
				try {
					((CustomUserDetailsService) this.userDetailsService()).createUser(utenteDto);
					userDetails = this.userDetailsService().loadUserByUsername(codiceFiscale);
				} catch(UsernameNotFoundException unfCreateException) {
					
					logger.error("L'utente non e' stato salvato", unfCreateException);
				}
			}

			return new CitizenSamlAuthentication(userDetails);
		});

		Converter<HttpServletRequest, RelyingPartyRegistration> relyingPartyRegistrationResolver = new DefaultRelyingPartyRegistrationResolver(
				this.relyingPartyRegistrations());
		Saml2MetadataFilter filter = new Saml2MetadataFilter(relyingPartyRegistrationResolver,
				new OpenSamlMetadataResolver());

		http
			.csrf().disable()
			.antMatcher("/**") //customized entry poin
			.authorizeRequests()
			
			/** #############################################################################	PARTI COMUNI **/
			.antMatchers("/home").authenticated()// la home deve essere visibile anche agli anonimi
			/** #############################################################################	PARTI COMUNI **/
			.antMatchers("/images/**").permitAll()
			.antMatchers("/fontawesome*").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/bundle/**").permitAll()
			.antMatchers("/webfonts/**").permitAll()
			.antMatchers("/fonts/**").permitAll()
			.antMatchers("/plugin/**").permitAll()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/page/**").permitAll()
			.antMatchers("/srg/**").permitAll()
			.antMatchers("/shared/**").permitAll()
			.antMatchers("/login/saml2/**").permitAll()
			.antMatchers("/saml2/**").permitAll()
			.antMatchers("/findFoglioByGeom").permitAll()
			//FUNZIONALITA' MAPPE
			.antMatchers("/Mappa*").permitAll()
			.antMatchers("/catastoController/getExecutionJob").permitAll()
			//RICERCHE
			.antMatchers("/catastoController/findFoglio").permitAll()
			.antMatchers("/catastoController/findParticella").permitAll()
			.antMatchers("/toponomasticaConsultazioneCtrl/ricercaToponimo").permitAll()
			//INFOSUMAPPA
			.antMatchers("/webGisController/findGeometryLayerByWkt").permitAll()
			//PRINT
			.antMatchers("/printMap").permitAll()
			/** #############################################################################	SERVIZI PROFILATORE **/
			.antMatchers("/profileManagerController/**").permitAll()
			/** #############################################################################	SERVIZI DASHBOARD **/
			.antMatchers("/getFunzionalita").permitAll()
			.antMatchers("/getMenuFunzionalita").permitAll()
			/** #############################################################################	SERVIZI GIS **/
			.antMatchers("/webgis").permitAll() 							
			.antMatchers("/mappaController/**").permitAll()	
//			.antMatchers("/mappaController/getMappa").permitAll()
//			.antMatchers("/mappaController/getAllTemaMappe").permitAll()
//			.antMatchers("/mappaController/getMapAttTools").permitAll()
//			.antMatchers("/mappaController/getMapAttRicerche").permitAll()
			.antMatchers("/layerController/**").permitAll()
//			.antMatchers("/layerController/group-layer-mappa").permitAll()
			.antMatchers("/webGisController/**").permitAll()
			.antMatchers("/print").permitAll()
			.antMatchers("/print/printMap").permitAll()
			/** #############################################################################	SERVIZI INTEGRAZIONE GIS **/
			.antMatchers("/servicegis").permitAll()							
			.antMatchers("/servicegis/**").permitAll()
			/** #############################################################################	SERVIZI CATASTO **/
			/** #############################################################################	SERVIZI TOPONOMASTICA **/
			.antMatchers("/services/viario/**").permitAll()
			/**free 
			.antMatchers("/toponomasticaCtrl").permitAll()
			.antMatchers("/toponomasticaConsultazioneCtrl").permitAll()
			**/
			/** #############################################################################	SERVIZI PIANO REGOLATORE (PRG) **/
			/**free
			.antMatchers("/prgConsultazioneController").permitAll()
			.antMatchers("/prgController").permitAll()
			**/
			/** #############################################################################	SERVIZI EDITING LAYER (EDIT) **/
			/**free
			.antMatchers("/editingLayer").permitAll()
			**/
			/** #############################################################################	SERVIZI GESTIONE MAPPE **/
			/**free
			.antMatchers("/gestioneMappe").permitAll()
			**/
			
			/** ###########################################################################################################################################**/
			/** ########################## PROFILATURA ####################################################################################################**/
			/** ###########################################################################################################################################**/
			/** ACCESSO FUNZIONALITA'**/
			.antMatchers("/accessoPRG").hasAnyRole("ACCESSO_GESTIONE_PRG")
			.antMatchers("/accessoConsPRG").hasAnyRole("ACCESSO_CONSULTAZIONE_PRG")
			.antMatchers("/accessoToponomastica").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
			.antMatchers("/accessoConsToponomastica").hasAnyRole("ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/accessoCatasto").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			.antMatchers("/accessoConsCatasto").hasAnyRole("ACCESSO_CONSULTAZIONE_CATASTO")
			.antMatchers("/accessoGestioneProfile").hasAnyRole("ACCESSO_PROFILE_MANAGER")			
			.antMatchers("/accessoGestioneMappe").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
			.antMatchers("/accessoEditingLayer").hasAnyRole("ACCESSO_EDITING_LAYER")
			
			/** #############################################################################	SERVIZI COMUNI CON PERMESSI
			 **/
			.antMatchers("/catastoController/findParticellaByGeom").hasAnyRole("ACCESSO_GESTIONE_PRG","ACCESSO_CONSULTAZIONE_PRG","ACCESSO_GESTIONE_CATASTO")
			/** #############################################################################	SERVIZI PROFILATORE **/
			/**#############################################################################	SERVIZI CATASTO **/
			.antMatchers("/catastoController/categorieCatastali").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
			.antMatchers("/catastoController/codiciDiritto").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
			.antMatchers("/catastoController/codiciQualita").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
			.antMatchers("/catastoController/getComuni").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
			.antMatchers("/catastoController/getProvince").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
			.antMatchers("/ricercheController/**").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/getDataUltimoAggiornamento").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettaglioSoggettiPT").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettaglioSoggettiUIU").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/localizzaUIU").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/localizzaPT").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/catastoController/ricercaListaIntestatari").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettaglioParticellePTPersoneFisiche").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettaglioParticellePTSoggettiGiuridici").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettagliParticelleUIUIdentificativi").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettagliParticellePTMultiplo").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/ricercaPersoneFisiche").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/ricercaListaIntestatariTranneCorrenteConDiritto","ACCESSO_CONSULTAZIONE_CATASTO").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
//			.antMatchers("/ricercheController/exportPersoneFisiche").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/ricercaSoggettiGiuridici").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/exportSoggettiGiuridici").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/ricercaImmobiliUIU").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettaglioImmobiliUIUIdentificativi").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettaglioImmobiliUIUPersoneFisiche").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettaglioImmobiliUIUSoggettiGiuridici").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/dettaglioImmobiliUIUPlanimetrie").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/exportVisuraCatastaleFabbricati").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/exportVisuraCatastaleStoricaFabbricati").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/ricercaParticellePT").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/exportVisuraCatastaleTerreni").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/exportVisuraCatastaleStoricaTerreni").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/ricercaIntestazioniPersoneFisiche").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
//			.antMatchers("/ricercheController/exportIntestazioniPF").hasAnyRole("ACCESSO_GESTIONE_CATASTO","ACCESSO_CONSULTAZIONE_CATASTO")
			.antMatchers("/catasto/storage/**").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			.antMatchers("/catastoController/ricercaSuParticellePT").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			.antMatchers("/catastoController/ricercaSuParticelleUI").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			.antMatchers("/catastoController/ricercaPersoneFisiche").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			.antMatchers("/catastoController/ricercaSoggettiGiuridici").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			.antMatchers("/catastoController/exportPdf").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			.antMatchers("/catastoController/exportXls").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			.antMatchers("/catastoController/exportShape").hasAnyRole("ACCESSO_GESTIONE_CATASTO")
			/** #############################################################################	SERVIZI TOPONOMASTICA **/
			.antMatchers("/toponomasticaConsultazioneCtrl/getTipoToponimo").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/findAllLocalita").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/ricercaAccesso").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getTipoAccesso").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getTipoLocalita").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/ricercaLocalita").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getToponimo").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getEnteGestore").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getClassificaAmministrativa").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getClassificaFunzionale").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getPatrimonialita").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/ricercaEstesaAmministrativa").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/ricercaCippo").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA","ACCESSO_CONSULTAZIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getDug").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaConsultazioneCtrl/getEstesaAmministrativa").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
			.antMatchers("/toponomasticaCtrl/**").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/ricercaDocumenti").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/upload").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/download").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/eliminaDocumenti").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/insertOrUpdateAccesso").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/insertOrUpdateToponimo").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/getToponimoFigli").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/cancellaToponimo").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/cancellaAccessoByToponimo").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/exportShape").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/insertOrUpdateLocalita").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/cancellaAccessoByLocalita").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/cancellaLocalita").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/getTipoTopologico").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/getTipoFunzionale").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/ricercaGiunzioniStradali").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/findIntersections").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/insertOrUpdateGiunzione").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/cancellaGiunzione").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/insertOrUpdateEstesa").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/insertOrUpdateCippo").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/cancellaCippo").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/cancellaEstesaAmministrativa").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/cancellaAccesso").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/getShapeFiles").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/importShape").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
//			.antMatchers("/toponomasticaCtrl/eliminaShapeFile").hasAnyRole("ACCESSO_GESTIONE_TOPONOMASTICA")
			/** #############################################################################	SERVIZI PIANO REGOLATORE **/
			.antMatchers("/prgController/**").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/reperimentoCatalogoVariante").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/reperimentoColonneLayer").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/salvaLayer").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/varianteByNomeLayer").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cancellaLayer").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/salvaNuovoGruppo").hasAnyRole("ACCESSO_GESTIONE_PRG")			
//			.antMatchers("/prgController/ricercaTipoDocumenti").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/insertOrUpdateTipoDocumento").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/varianteByTipoDocumento").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cancellaTipoDocumento").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/ricercaVarianti").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cercaDocumentiByIdVariante").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cercaCartografieByIdVariante").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/findAllTipoDocumento").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cercaTipiDocumentoMancanti").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/creaOrSalvaDocumento").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/downloadDocumento").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cercaIndiciByIdDocumento").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/salvaIndice").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cercaCodiciZto").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/salvaCodici").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cercaCodiciByIdIndice").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cancellaIndice").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/esportaIndice").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/importaIndice").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cancellaDocumento").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/salvaCartografia").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cancellaCartografia").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/creaOrSalvaVariante").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cancellaVariante").hasAnyRole("ACCESSO_GESTIONE_PRG")
//			.antMatchers("/prgController/cercaCdu").hasAnyRole("ACCESSO_GESTIONE_PRG")
			.antMatchers("/prgConsultazioneController/getAllVariantiByDate").hasAnyRole("ACCESSO_GESTIONE_PRG","ACCESSO_CONSULTAZIONE_PRG")
			.antMatchers("/prgConsultazioneController/cercaProtocollo").hasAnyRole("ACCESSO_GESTIONE_PRG","ACCESSO_CONSULTAZIONE_PRG")
			.antMatchers("/prgConsultazioneController/downloadCdu").hasAnyRole("ACCESSO_GESTIONE_PRG","ACCESSO_CONSULTAZIONE_PRG")
			.antMatchers("/prgConsultazioneController/downloadCduByProtocollo").hasAnyRole("ACCESSO_GESTIONE_PRG")
			/** #############################################################################	SERVIZI EDITING LAYER **/
			.antMatchers("/adminController/findAllLayers").hasAnyRole("ACCESSO_EDITING_LAYER")
			.antMatchers("/adminController/countLayerByIdentificativo").hasAnyRole("ACCESSO_EDITING_LAYER")
			.antMatchers("/adminController/insertOrUpdate").hasAnyRole("ACCESSO_EDITING_LAYER")
			.antMatchers("/adminController/findAllLayers").hasAnyRole("ACCESSO_EDITING_LAYER")
			.antMatchers("/adminController/findAllGeometry").hasAnyRole("ACCESSO_EDITING_LAYER")
			.antMatchers("/adminController/eliminaGeometria").hasAnyRole("ACCESSO_EDITING_LAYER")
			.antMatchers("/adminController/insertOrUpdate").hasAnyRole("ACCESSO_EDITING_LAYER")
			.antMatchers("/adminController/eliminaLayer").hasAnyRole("ACCESSO_EDITING_LAYER")
			/** #############################################################################	SERVIZI GESTIONE MAPPE **/
//			.antMatchers("/mappaController/getAllMapp").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/getMapTools").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/getMapAllRicerche").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/getAllRuoli").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/getAllMapPermessi").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/temiToMappa").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/saveOrUpdate").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/associaTemiToMap").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/deleteTemiToMap").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/updateZoomShowCat").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/insertRicerche").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/insertToolJoin").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/insertPermessi").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/duplica").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/mappaController/deleteSel").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/layerController/getGroupTableMap").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/layerController/saveGrups").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/layerController/getLayerToTavola").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/temaController/getTemi").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/temaController/saveOrUpdate").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/temaController/mappeToTema").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
//			.antMatchers("/temaController/delete").hasAnyRole("ACCESSO_GESTIONE_MAPPE")
			
			/**TUTTO IL RESTO E' DA AUTENTICATI**/
			.anyRequest().authenticated()	
			
			.and()
            	.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
            	.accessDeniedPage("/loginExpired")
			.and()
			.saml2Login(authorize ->{                try {
	                authorize
	                .authenticationManager(new ProviderManager(authenticationProvider));
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	        })
			.logout(logout -> {
				logout.logoutUrl("/logout")
						.logoutSuccessUrl("/loginExpired").invalidateHttpSession(true).deleteCookies("JSESSIONID")
						.permitAll();
			})
			.addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class);
	}

	@Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    
	
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
	    return new CustomLogoutSuccessHandler();
	}
	
	/*
	 * @Bean public RequestMatcher saelRequestMatcher() { return new
	 * SaelRequestMatcher(); }
	 * 
	 * @Bean public LogoutSuccessHandler saelLogoutSuccessHanlder() { return new
	 * SaelLogoutSuccessHandler(); }
	 * 
	 * @Bean Saml2AuthenticationRequestFactory authenticationRequestFactory(
	 * AuthnRequestConverter authnRequestConverter) {
	 * 
	 * OpenSamlAuthenticationRequestFactory authenticationRequestFactory = new
	 * OpenSamlAuthenticationRequestFactory();
	 * authenticationRequestFactory.setAuthenticationRequestContextConverter(
	 * authnRequestConverter); return authenticationRequestFactory; }
	 * 
	 * @Bean public Saml2AuthenticationRequestContextResolver
	 * customSaml2AuthReqResolver() throws Exception{
	 * Saml2AuthenticationRequestContextResolver delegate = new
	 * DefaultSaml2AuthenticationRequestContextResolver(new
	 * DefaultRelyingPartyRegistrationResolver(relyingPartyRegistrations()));
	 * 
	 * return new SaelCustomAuthenticationRequestContextResolver(delegate); }
	 */
	@Bean
	public RelyingPartyRegistrationRepository relyingPartyRegistrations() throws Exception {
		PrivateKey privateKey = (PrivateKey) ks.getKey(keyStoreAlias, keyStoreKeyPassword.toCharArray());
		X509Certificate cert = (X509Certificate) ks.getCertificate(keyStoreAlias);
		Saml2X509Credential rpSigningCredentials = new Saml2X509Credential(privateKey, cert,
				Saml2X509CredentialType.SIGNING);
		Saml2X509Credential rpDecriptionCredentials = new Saml2X509Credential(privateKey, cert,
				Saml2X509CredentialType.DECRYPTION);
		RelyingPartyRegistration registration = RelyingPartyRegistrations
				.fromMetadataLocation(assertingPartyMetadataLocation).registrationId(registrationId)
				.entityId(spEntityId).signingX509Credentials((c) -> c.add(rpSigningCredentials))
				.decryptionX509Credentials((c) -> c.add(rpDecriptionCredentials))
				.assertionConsumerServiceLocation(assertionConsumerServiceLocation).build();
		return new InMemoryRelyingPartyRegistrationRepository(registration);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	/*
	 * public UserDetailsService userDetailsService() {
	 * 
	 * return new UserDetailsService() {
	 * 
	 * @Override public UserDetails loadUserByUsername(String codiceFiscale) { try {
	 * Optional<SaelUtenteDto> user = userDao.loadUtenteByFiscalCode(codiceFiscale);
	 * if( user.isEmpty() ) { throw new
	 * UsernameNotFoundException("Nessun utente trovato per il codice fiscale ["
	 * +codiceFiscale+"]"); } SaelUtenteDto utenteCorrente = user.get();
	 * List<GrantedAuthority> roles = new
	 * ArrayList<>(utenteCorrente.getRuoli().size()); List<UUID> idEntiAssociati =
	 * new ArrayList<>(utenteCorrente.getEntiAssociati() != null ?
	 * utenteCorrente.getEntiAssociati().size():0); if( utenteCorrente.isAbilitato()
	 * ) { utenteCorrente.getRuoli().forEach(ruoloDb -> { roles.add(new
	 * SimpleGrantedAuthority("ROLE_"+ruoloDb.getCodiceRuolo())); }); if(
	 * utenteCorrente.getEntiAssociati() != null &&
	 * !utenteCorrente.getEntiAssociati().isEmpty() ) {
	 * utenteCorrente.getEntiAssociati().forEach(enteAssociato -> {
	 * idEntiAssociati.add(enteAssociato.getId()); }); } }else {
	 * 
	 * throw new UsernameNotFoundException("Utente con codice fiscale ["
	 * +codiceFiscale+"] non abilitato"); } LoggedUser result = new LoggedUser(
	 * utenteCorrente.getNome(), utenteCorrente.getCognome(),
	 * utenteCorrente.getCodiceFiscale(), utenteCorrente.getMail(),
	 * utenteCorrente.getTelefono(), idEntiAssociati, utenteCorrente.isAbilitato(),
	 * roles); return result; } catch (DataAccessException e) {
	 * 
	 * throw new UsernameNotFoundException("Errore nella ricerca dell'utente "+
	 * codiceFiscale, e); } } }; }
	 */
	private String getValue(XMLObject valueObj, String defaultValue) {
		if (valueObj instanceof XSStringImpl) {

			XSStringImpl stringImpl = (XSStringImpl) valueObj;
			return stringImpl.getValue();
		}
		return defaultValue;
	}
}
