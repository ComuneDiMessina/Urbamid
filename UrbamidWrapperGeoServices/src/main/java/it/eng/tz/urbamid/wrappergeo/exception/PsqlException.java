package it.eng.tz.urbamid.wrappergeo.exception;

public class PsqlException extends WrapperGeoServiceException {

	private static final long serialVersionUID = -5818015538238591786L;

	public PsqlException() {
		super();
	}

	public PsqlException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PsqlException(String message, Throwable cause) {
		super(message, cause);
	}

	public PsqlException(String message) {
		super(message);
	}

	public PsqlException(Throwable cause) {
		super(cause);
	}

}