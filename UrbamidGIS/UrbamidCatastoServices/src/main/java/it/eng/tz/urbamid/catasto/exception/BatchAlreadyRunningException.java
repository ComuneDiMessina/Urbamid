package it.eng.tz.urbamid.catasto.exception;

public class BatchAlreadyRunningException extends BatchServiceException {

	private static final long serialVersionUID = -9172860679380339152L;

	public BatchAlreadyRunningException() {
		super();
		
	}

	public BatchAlreadyRunningException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public BatchAlreadyRunningException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public BatchAlreadyRunningException(String message) {
		super(message);
		
	}

	public BatchAlreadyRunningException(Throwable cause) {
		super(cause);
		
	}

}