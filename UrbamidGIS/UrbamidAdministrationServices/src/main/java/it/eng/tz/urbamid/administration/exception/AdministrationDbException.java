package it.eng.tz.urbamid.administration.exception;

public class AdministrationDbException extends Exception {

	private static final long serialVersionUID = 6873516606539034370L;

	public AdministrationDbException() {
		super();
		
	}

	public AdministrationDbException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public AdministrationDbException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public AdministrationDbException(String message) {
		super(message);
		
	}

	public AdministrationDbException(Throwable cause) {
		super(cause);
		
	}

}
