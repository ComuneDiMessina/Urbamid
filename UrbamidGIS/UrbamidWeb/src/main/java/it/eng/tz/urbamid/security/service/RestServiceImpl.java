package it.eng.tz.urbamid.security.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import it.eng.tz.urbamid.security.model.BaseResponse;
import it.eng.tz.urbamid.security.model.ResponseData;

@Service
public class RestServiceImpl implements RestService {
	
	private static final Logger logger = LoggerFactory.getLogger(RestServiceImpl.class.getName());

	@Autowired
	protected RestTemplate restTemplate; 
	
	
	/**
	 * Effettua una chiamata rest di tipo GET alla url e con i path params passati
	 * 
	 * @param {@link String} url
	 * @param {@link Map<{@link String}, {@link String}>} params
	 * @return {@link BaseResponse} response
	 * 
	 * @exception IllegalArgumentException, RestClientException
	 */
	public BaseResponse restGetPathParam(String url, Map<String, String> params) {
		if(!StringUtils.hasText(url))
			throw new IllegalArgumentException();
	
		if(logger.isDebugEnabled()) logger.debug(">>>> restGetPathParam --> url ["+ url+"]");
		return restTemplate.getForObject(url, BaseResponse.class, params);
	}
	
	/**
 	 * Effettua una chiamata rest di tipo GET al method della baseUrl e con i path params passati
	 *
	 * @param {@link String} baseUrl
	 * @param {@link String} method
	 * @param {@link Map<{@link String}, {@link String}>} params
	 * @return {@link BaseResponse} response
	 * 
	 * @exception IllegalArgumentException, RestClientException
	 */
	public BaseResponse restGetPathParam(String baseUrl, String method, Map<String, String> params) {
		if(StringUtils.hasText(baseUrl))
			baseUrl = baseUrl.trim();
		if(StringUtils.hasText(method))
			method = method.trim();
		return this.restGetPathParam(baseUrl+method, params);
	}
	
	/**
 	 * Effettua una chiamata rest di tipo GET alla url passata
	 *
	 * @param {@link String} url
	 * @return {@link BaseResponse} response
	 * 
	 * @exception IllegalArgumentException, RestClientException
	 */
	public BaseResponse restGet(String url) {	
		if(!StringUtils.hasText(url))
			throw new IllegalArgumentException();	
		if(logger.isDebugEnabled()) logger.debug(">>>> restGet --> url ["+ url+"]");
		return restTemplate.getForObject(url, BaseResponse.class);
	}

	public ResponseData restGetRD(String url) {	
		if(!StringUtils.hasText(url))
			throw new IllegalArgumentException();	
		if(logger.isDebugEnabled()) logger.debug(">>>> restGet --> url ["+ url+"]");
		return restTemplate.getForObject(url, ResponseData.class);
	}
	
	/**
 	 * Effettua una chiamata rest di tipo GET al method della baseUrl passati
	 *
	 * @param {@link String} baseUrl
	 * @param {@link String} method
	 * @return {@link BaseResponse} response
	 * 
	 * @exception IllegalArgumentException, RestClientException
	 */
	public BaseResponse restGet(String baseUrl, String method) {
		if(StringUtils.hasText(baseUrl))
			baseUrl = baseUrl.trim();
		if(StringUtils.hasText(method))
			method = method.trim();
		return this.restGet(baseUrl+method);
	}
	
	/**
 	 * Effettua una chiamata rest di tipo POST alla url e con la entity passati
	 *
	 * @param {@link String}url
	 * @param {@link Object} entity
	 * @return {@link BaseResponse} response
	 * 
	 * @exception IllegalArgumentException, RestClientException
	 */
	public BaseResponse restPost(String url, Object entity) {
		if(!StringUtils.hasText(url))
			throw new IllegalArgumentException();
		if(logger.isDebugEnabled()) logger.debug(">>>> restPost --> url ["+ url+"]");
		return restTemplate.postForObject(url, entity, BaseResponse.class);
	}
	
	public ResponseData restPostTable(String url, Object entity) {
		if(!StringUtils.hasText(url))
			throw new IllegalArgumentException();
		if(logger.isDebugEnabled()) logger.debug(">>>> restPost --> url ["+ url+"]");
		return restTemplate.postForObject(url, entity, ResponseData.class);
	}

	/**
 	 * Effettua una chiamata rest di tipo POST al method della baseUrl e con la entity passati
	 *
	 * @param {@link String}baseUrl
	 * @param {@link String}method
	 * @param {@link Object} entity
	 * @return {@link BaseResponse} response
	 * 
	 * @exception IllegalArgumentException, RestClientException
	 */
	public BaseResponse restPost(String baseUrl, String method, Object entity) {
		if(StringUtils.hasText(baseUrl))
			baseUrl = baseUrl.trim();
		if(StringUtils.hasText(method))
			method = method.trim();
		return this.restPost(baseUrl+method, entity);
	}
	
	
	
	/**
 	 * Effettua una chiamata rest di tipo PUT alla url con la entity e i params passati
	 *
	 * @param {@link String}url
	 * @param {@link Object} entity
	 * @param {@link Map<{@link String}, {@link String}>} params
	 * @return {@link BaseResponse} response
	 * 
	 * @exception IllegalArgumentException, RestClientException
	 */
	public BaseResponse restPut(String url, Object entity, Map<String, String> params) {
		if(!StringUtils.hasText(url))
			throw new IllegalArgumentException();	
		BaseResponse response = new BaseResponse();
		if(logger.isDebugEnabled()) logger.debug(">>>> restPut --> url ["+ url+"]");
		restTemplate.put(url, entity, params);
			return response.setOK();				
	}
	
	/**
 	 * Effettua una chiamata rest di tipo PUT alal method della baseUrl con la entity e i params passati
	 *
	 * @param {@link String}baseUrl
	 * @param {@link String}method
	 * @param {@link Object} entity
	 * @param {@link Map<{@link String}, {@link String}>} params
	 * @return {@link BaseResponse} response
	 * 
	 * @exception IllegalArgumentException, RestClientException
	 */
	public BaseResponse restPut(String baseUrl, String method, Object entity, Map<String, String> params) {
		if(StringUtils.hasText(baseUrl))
			baseUrl = baseUrl.trim();
		if(StringUtils.hasText(method))
			method = method.trim();
		return this.restPut(baseUrl+method, entity, params);
	}


}
