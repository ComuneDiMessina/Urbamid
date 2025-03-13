package it.eng.tz.urbamid.profilemanager.exception;

public class ProfileManagerDbException extends Exception {

	private static final long serialVersionUID = 6873516606539034370L;

	public ProfileManagerDbException() {
		super();
		
	}

	public ProfileManagerDbException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ProfileManagerDbException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ProfileManagerDbException(String message) {
		super(message);
		
	}

	public ProfileManagerDbException(Throwable cause) {
		super(cause);
		
	}

}
