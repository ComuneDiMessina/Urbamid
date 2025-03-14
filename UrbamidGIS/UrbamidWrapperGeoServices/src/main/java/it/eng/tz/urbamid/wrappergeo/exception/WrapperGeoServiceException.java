package it.eng.tz.urbamid.wrappergeo.exception;

public class WrapperGeoServiceException extends Exception {

	private static final long serialVersionUID = -5724648140351891036L;

	public WrapperGeoServiceException() {
		super();
	}

	public WrapperGeoServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrapperGeoServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrapperGeoServiceException(String message) {
		super(message);
	}

	public WrapperGeoServiceException(Throwable cause) {
		super(cause);
	}

}