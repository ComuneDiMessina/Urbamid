package it.eng.tz.urbamid.toponomastica.exception;

public class ToponomasticaServiceException extends Exception {

	private static final long serialVersionUID = -5724648140351891036L;

	public ToponomasticaServiceException() {
		super();
	}

	public ToponomasticaServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ToponomasticaServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ToponomasticaServiceException(String message) {
		super(message);
	}

	public ToponomasticaServiceException(Throwable cause) {
		super(cause);
	}

}