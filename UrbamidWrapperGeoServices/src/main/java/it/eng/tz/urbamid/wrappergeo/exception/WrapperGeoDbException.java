package it.eng.tz.urbamid.wrappergeo.exception;

public class WrapperGeoDbException extends Exception {

	private static final long serialVersionUID = 6873516606539034370L;

	public WrapperGeoDbException() {
		super();
		
	}

	public WrapperGeoDbException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public WrapperGeoDbException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public WrapperGeoDbException(String message) {
		super(message);
		
	}

	public WrapperGeoDbException(Throwable cause) {
		super(cause);
		
	}

}
