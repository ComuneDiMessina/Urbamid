package it.eng.tz.urbamid.catasto.exception;

public class KettleJobExecutionException extends CatastoServiceException {

	private static final long serialVersionUID = 3416793675195978175L;

	public KettleJobExecutionException() {
		super();
		
	}

	public KettleJobExecutionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public KettleJobExecutionException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public KettleJobExecutionException(String message) {
		super(message);
		
	}

	public KettleJobExecutionException(Throwable cause) {
		super(cause);
		
	}

}