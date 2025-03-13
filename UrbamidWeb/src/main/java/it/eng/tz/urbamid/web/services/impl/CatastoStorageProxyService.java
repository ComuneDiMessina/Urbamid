package it.eng.tz.urbamid.web.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.urbamid.exception.UrbamidServiceException;
import it.eng.tz.urbamid.security.model.ResponseData;
import it.eng.tz.urbamid.web.dto.DettaglioFileDTO;
import it.eng.tz.urbamid.web.services.CatastoStorageService;
import it.eng.tz.urbamid.web.util.IConstants;

@Service
public class CatastoStorageProxyService implements CatastoStorageService {

	private static final Logger LOG = LoggerFactory.getLogger(CatastoStorageProxyService.class.getName());
	
	/**
	 * Rest template
	 */
	private final RestTemplate restTemplate;
	
	/**
	 * Environment spring
	 */
	private final Environment environment;
	
	/**
	 * URI di base per l'API REST di storage
	 */
	private final String storageBaseUrl;
	
	/**
	 * Enumerazione per i tipi di import dati
	 */
	private enum ImportType { ATTUALITA, AGGIORNAMENTO;	}
	
	/**
	 * Enumerazione per i tipi di cartella di upload
	 */
	private enum UploadFolder { CARTOGRAFIA, TERRENI, FABBRICATI, PLANIMETRIE; }
	
	/**
	 * Costruttore.
	 * 
	 * @param restTemplate {@link RestTemplate} per l'invocazione delle API esposte dai microservizi
	 * @param environment {@link Environment} di Spring
	 */
	public CatastoStorageProxyService(RestTemplate restTemplate, Environment environment) {
		Assert.notNull(restTemplate, "RestTemplate MUST not be null but don't panic!");
		Assert.notNull(environment, "Spring Environment MUST not be null but don't panic!");
		this.restTemplate = restTemplate;
		this.environment = environment;
		String catastoEndpoint = this.environment.getProperty(IConstants.WS_CATASTO_ENDPOINT);
		String storageRestAPI = this.environment.getProperty(IConstants.WS_CATASTO_STORAGE_REST_API);
		Assert.hasText(catastoEndpoint, "Catasto endpoint MUST not be empty but don't panic!");
		Assert.hasText(storageRestAPI, "Storage rest API MUST not be empty but don't panic!");
		this.storageBaseUrl = catastoEndpoint.concat(storageRestAPI);
	}
	
	@Override
	public List<DettaglioFileDTO> elencoFileInCartella(String importType, String folder) throws UrbamidServiceException {
		
		this.checkParams(importType, folder);
		
		String url = String.format(getStorageBaseUrl().concat("/%s/%s"), importType, folder);
		if(LOG.isDebugEnabled()) {
			LOG.debug("STORAGE CATASTO SERVICE --> GET REQUEST TO URL: {}.",url);
		}
		
		ResponseEntity<List<DettaglioFileDTO>> response = restTemplate.exchange(
				url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DettaglioFileDTO>>() {});
		
		if(response.getStatusCode().is2xxSuccessful()){
			return response.getBody();
		} else {
			LOG.error("STORAGE CATASTO SERVICE --> GET REQUEST ERROR! Status code {}.", response.getStatusCode());
			throw new UrbamidServiceException("Errore nel recupero elenco file in cartella");
		}
		
	}

	@Override
	public void upload(String importType, String folder, MultipartFile file) throws UrbamidServiceException {
		
		this.checkParams(importType, folder);
		
        try {
        	
        	//costruisco un HttpEntity con i dettagli della request ed i vari headers
        	HttpHeaders headers = new HttpHeaders();
        	headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        	MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("file", file.getResource());
			String url = String.format(getStorageBaseUrl().concat("/%s/%s"), importType, folder);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
			ResponseData response = this.restTemplate.postForObject(url, requestEntity, ResponseData.class);
			if(response!=null && response.isSuccess()) {
				
				LOG.error("STORAGE CATASTO SERVICE --> POST REQUEST ERROR! Status code {}.", response.getMessage());
				throw new UrbamidServiceException("Errore nell'upload di un file in cartella");
			}			
		} catch( Exception e ) {
			
			LOG.error("Si è verificato un errore nell'invocazione del metodo di upload.");
			throw new UrbamidServiceException("Si è verificato un errore nell'invocazione del metodo di upload.", e);
			
		}
        
	}
	
	@Override
	public void rimuovi(String importType, String folder, String nomeFile)
			throws UrbamidServiceException {
		
		this.checkParams(importType, folder);
		String uri = String.format(getStorageBaseUrl().concat("/%s/%s/%s"), importType, folder, nomeFile);
		this.restTemplate.delete(uri);
		
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
	public String getStorageBaseUrl() {
		return storageBaseUrl;
	}
	
	/**
	 * Metodo privato per la validazione della stringa folder
	 * 
	 * @param folder
	 * @return
	 */
	private boolean isValidUploadFolder(String folder) {
		for(UploadFolder uf : UploadFolder.values()) {
			if(uf.name().equalsIgnoreCase(folder))
				return true;
		}
		return false;
	}

	/**
	 * Metodo privato per la validazione della stringa importType
	 * 
	 * @param importType
	 * @return
	 */
	private boolean isValidImportType(String importType) {
		for(ImportType it : ImportType.values()) {
			if(it.name().equalsIgnoreCase(importType))
				return true;
		}
		return false;
	}
	
	/**
	 * Metodo privato che valida la folder e l'importType
	 * 
	 * @param importType
	 * @param folder
	 */
	private void checkParams(String importType, String folder) {
		if(!StringUtils.hasText(importType) || !isValidImportType(importType)) {
			LOG.error("STORAGE CATASTO SERVICE -- Valore errato per importType: {}.", importType);
			throw new IllegalArgumentException();
		}
		if(!StringUtils.hasText(folder) || !isValidUploadFolder(folder)) {
			LOG.error("STORAGE CATASTO SERVICE -- Valore errato per upload folder: {}.", folder);
			throw new IllegalArgumentException();
		}
	}

}
