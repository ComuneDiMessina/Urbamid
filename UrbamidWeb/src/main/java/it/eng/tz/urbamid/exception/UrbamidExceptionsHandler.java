package it.eng.tz.urbamid.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class UrbamidExceptionsHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(UrbamidExceptionsHandler.class.getClass());
	
	
	@ExceptionHandler(UrbamidServiceException.class)
	public ResponseEntity<String> urbamidServiceExceptionHandler( UrbamidServiceException e ){
		LOG.debug("[EXCEPTIONS HANDLER] Intercettata eccezione di tipo <<UrbamidServiceException>>");
		
		//TODO IMPROVE THIS CODE!
		
		return 
				ResponseEntity
					.badRequest( )
						.body("Impossibile avviare un nuovo batch quando ne esiste già uno in esecuzione.");
	}
	
	
	@ExceptionHandler(RestClientException.class)
	public ResponseEntity<String> restClientExceptionHandler( RestClientException rce ){
		LOG.debug("[EXCEPTIONS HANDLER] Intercettata eccezione di tipo <<RestClientException>>");
		ResponseEntity<String> res = null;
		if(rce instanceof HttpClientErrorException) {
			res = ResponseEntity.badRequest().body("Rest template bad request");
		} else if(rce instanceof HttpServerErrorException) {
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rest template internal server error");
		} else {
			res = ResponseEntity.badRequest().body("Rest template unknown error");
		}
		return res;
	}
	
	
	
}
	

