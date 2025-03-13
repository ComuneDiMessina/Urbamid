package it.eng.tz.urbamid.catasto.exception;

public class BatchServiceException extends CatastoServiceException {

	private static final long serialVersionUID = 3416793675195978175L;

	public BatchServiceException() {
		super();
		
	}

	public BatchServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public BatchServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public BatchServiceException(String message) {
		super(message);
		
	}

	public BatchServiceException(Throwable cause) {
		super(cause);
		
	}

}