package it.eng.tz.urbamid.prg.exception;

public class PRGDbException extends Exception {

	private static final long serialVersionUID = 6873516606539034370L;

	public PRGDbException() {
		super();
		
	}

	public PRGDbException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public PRGDbException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public PRGDbException(String message) {
		super(message);
		
	}

	public PRGDbException(Throwable cause) {
		super(cause);
		
	}

}
