package it.eng.tz.urbamid.profilemanager.exception;

public class ProfileManagerServiceException extends Exception {

	private static final long serialVersionUID = -5724648140351891036L;

	public ProfileManagerServiceException() {
		super();
	}

	public ProfileManagerServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProfileManagerServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProfileManagerServiceException(String message) {
		super(message);
	}

	public ProfileManagerServiceException(Throwable cause) {
		super(cause);
	}

}