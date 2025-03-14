package it.eng.tz.urbamid.catasto.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionsHandler.class.getClass());

	/**
	 * Intercetta le eccezioni lanciate dall'esecuzione dei servizi batch
	 */
	@ExceptionHandler(BatchAlreadyRunningException.class)
	public ResponseEntity<String> batchServiceExceptionHandler( BatchAlreadyRunningException e ){
		LOG.error("[EXCEPTIONS HANDLER] Intercettata eccezione di tipo <<BatchAlreadyRunningException>>");
		return 
				ResponseEntity
					.badRequest( )
						.body("Impossibile avviare un nuovo batch quando ne esiste già uno in esecuzione.");
	}
	
	/**
	 * Intercetta le eccezioni lanciate dall'esecuzione dei servizi batch
	 */
	@ExceptionHandler(BatchServiceException.class)
	public ResponseEntity<String> batchServiceExceptionHandler( BatchServiceException e ){
		LOG.error("[EXCEPTIONS HANDLER] Intercettata eccezione di tipo <<BatchServiceException>>");
		return ResponseEntity.badRequest( ).body("KO");
	}
	
	/**
	 * Intercetta le eccezioni lanciate dall'esecuzione dei servizi batch
	 */
	@ExceptionHandler(CatastoDbException.class)
	public ResponseEntity<String> catastoDbExceptionHandler( CatastoDbException e ){
		LOG.error("[EXCEPTIONS HANDLER] Intercettata eccezione di tipo <<CatastoDbException>>");
		return ResponseEntity.badRequest( ).body( "KO" );
	}
	
	/**
	 * Intercetta le eccezioni lanciate dall'esecuzione dei servizi batch
	 */
	@ExceptionHandler(KettleJobExecutionException.class)
	public ResponseEntity<String> kettleJobExecutionExceptionHandler( KettleJobExecutionException e ){
		LOG.error("[EXCEPTIONS HANDLER] Intercettata eccezione di tipo <<KettleJobExecutionException>>");
		return ResponseEntity.badRequest( ).body( "KO" );
	}
	
	/**
	 * Intercetta le eccezioni lanciate dall'esecuzione dei servizi batch
	 */
	@ExceptionHandler(PsqlException.class)
	public ResponseEntity<String> psqlExceptionHandler( PsqlException e ){
		LOG.error("[EXCEPTIONS HANDLER] Intercettata eccezione di tipo <<PsqlException>>");
		//nel message c'è l'output dell'errore ricevuto da psql
		return ResponseEntity.badRequest( ).body( e.getMessage() );
	}
	
	/**
	 * Intercetta le eccezioni lanciate dall'esecuzione dei servizi batch
	 */
	@ExceptionHandler(CatastoEntityNotFoundException.class)
	public ResponseEntity<Object> catastoEntityNotFoundExceptionHandler( CatastoEntityNotFoundException e ){
		LOG.error("[EXCEPTIONS HANDLER] Intercettata eccezione di tipo <<CatastoEntityNotFoundException>>");
		//204 NO CONTENT è la migliora scelta quando non viene trovata una entità
		return 
				ResponseEntity
					.noContent()
						.build();
	}
	
}