package it.eng.tz.urbamid.catasto.exception;

public class CatastoServiceException extends Exception {

	private static final long serialVersionUID = -5724648140351891036L;

	public CatastoServiceException() {
		super();
	}

	public CatastoServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CatastoServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CatastoServiceException(String message) {
		super(message);
	}

	public CatastoServiceException(Throwable cause) {
		super(cause);
	}

}