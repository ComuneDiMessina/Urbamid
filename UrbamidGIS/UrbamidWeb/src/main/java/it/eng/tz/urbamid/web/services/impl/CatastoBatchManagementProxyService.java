package it.eng.tz.urbamid.web.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.web.dto.DettaglioBatchDTO;
import it.eng.tz.urbamid.web.services.CatastoBatchManagementService;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class CatastoBatchManagementProxyService implements CatastoBatchManagementService {

	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CatastoBatchManagementProxyService.class.getName());
	
	/**
	 * Rest template
	 */
	private final RestTemplate restTemplate;
	
	/**
	 * Environment spring
	 */
	private final Environment environment;
	
	/**
	 * URI di base per l'API REST di batch management
	 */
	private final String batchManagementBaseUrl;
	
	/**
	 * Costruttore. 
	 * 
	 * @param restTemplate {@link RestTemplate} per l'invocazione delle API esposte dai microservizi
	 * @param environment {@link Environment} di Spring
	 */
	public CatastoBatchManagementProxyService(RestTemplate restTemplate, Environment environment) {
		Assert.notNull(restTemplate, "RestTemplate MUST not be null but don't panic!");
		Assert.notNull(restTemplate, "Spring Environment MUST not be null but don't panic!");
		this.restTemplate = restTemplate;
		this.environment = environment;
		String catastoEndpoint = this.environment.getProperty(IConstants.WS_CATASTO_ENDPOINT);
		String batchManagementRestAPI = this.environment.getProperty(IConstants.WS_CATASTO_BATCH_MANAGEMENT_REST_API);
		Assert.hasText(catastoEndpoint, "Catasto endpoint MUST not be empty but don't panic!");
		Assert.hasText(batchManagementRestAPI, "Storage rest API MUST not be empty but don't panic!");
		this.batchManagementBaseUrl = catastoEndpoint.concat(batchManagementRestAPI);
	}
	
	@Override
	public DettaglioBatchDTO avviaProcessoBatchAttualita() throws UrbamidServiceException {
		
		String url = getBatchManagementBaseUrl().concat("/attualita");
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("CATASTO BATCH MANAGEMENT PROXY SERVICE --> POST REQUEST TO URL: {}.",url);
		}
		
		ResponseEntity<DettaglioBatchDTO> response = restTemplate.exchange(
				url, HttpMethod.POST, null, new ParameterizedTypeReference<DettaglioBatchDTO>() {});
		
		if(response.getStatusCode().is2xxSuccessful()){
			return response.getBody();
		} else {
			LOG.error("CATASTO BATCH MANAGEMENT PROXY SERVICE --> POST REQUEST ERROR! Status code {}.", response.getStatusCode());
			throw new UrbamidServiceException("Errore nella creazione di un nuovo batch di attualita");
		}
		
	}

	@Override
	public DettaglioBatchDTO avviaProcessoBatchAggiornamento() throws UrbamidServiceException {
		
		String url = getBatchManagementBaseUrl().concat("/aggiornamento");
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("CATASTO BATCH MANAGEMENT PROXY SERVICE --> POST REQUEST TO URL: {}.",url);
		}
		
		ResponseEntity<DettaglioBatchDTO> response = restTemplate.exchange(
				url, HttpMethod.POST, null, new ParameterizedTypeReference<DettaglioBatchDTO>() {});
		
		if(response.getStatusCode().is2xxSuccessful()){
			return response.getBody();
		} else {
			LOG.error("CATASTO BATCH MANAGEMENT PROXY SERVICE --> POST REQUEST ERROR! Status code {}.", response.getStatusCode());
			throw new UrbamidServiceException("Errore nella creazione di un nuovo batch di aggiornamento");
		}
		
	}

	@Override
	public DettaglioBatchDTO recuperaDettaglioBatchJob(Long jobId) throws UrbamidServiceException {
		
		Assert.notNull(jobId, "Job id MUST not be null!");
		
		String url = String.format(getBatchManagementBaseUrl().concat("/%s"), jobId);
		if(LOG.isDebugEnabled()) {
			LOG.debug("STORAGE CATASTO SERVICE --> GET REQUEST TO URL: {}.",url);
		}
		
		ResponseEntity<DettaglioBatchDTO> response = restTemplate.exchange(
				url, HttpMethod.GET, null, new ParameterizedTypeReference<DettaglioBatchDTO>() {});
		
		if(response.getStatusCode().is2xxSuccessful()){
			return response.getBody();
		} else {
			LOG.error("STORAGE CATASTO SERVICE --> GET REQUEST ERROR! Status code {}.", response.getStatusCode());
			throw new UrbamidServiceException("Errore nel recupero del dettaglio del batch.");
		}
		
	}
	
	@Override
	public DettaglioBatchDTO avviaRecuperoProcessoBatch() throws UrbamidServiceException {
		
		String url = getBatchManagementBaseUrl().concat("/getExecutionJob");
		
		if(LOG.isDebugEnabled()) {
			LOG.debug("CATASTO BATCH MANAGEMENT PROXY SERVICE --> POST REQUEST TO URL: {}.",url);
		}
		
		ResponseEntity<DettaglioBatchDTO> response = restTemplate.exchange(
				url, HttpMethod.POST, null, new ParameterizedTypeReference<DettaglioBatchDTO>() {});
		
		if(response.getStatusCode().is2xxSuccessful()){
			return response.getBody();
		} else {
			LOG.error("CATASTO BATCH MANAGEMENT PROXY SERVICE --> POST REQUEST ERROR! Status code {}.", response.getStatusCode());
			throw new UrbamidServiceException("Errore nel recupero delle informazioni del batch di import in corso");
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	/**
	 * 
	 * @return
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * 
	 * @return
	 */
	public String getBatchManagementBaseUrl() {
		return batchManagementBaseUrl;
	}

}
