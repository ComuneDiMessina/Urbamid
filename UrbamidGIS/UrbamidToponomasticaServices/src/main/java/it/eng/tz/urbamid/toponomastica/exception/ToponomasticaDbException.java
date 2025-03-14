package it.eng.tz.urbamid.toponomastica.exception;

public class ToponomasticaDbException extends Exception {

	private static final long serialVersionUID = 6873516606539034370L;

	public ToponomasticaDbException() {
		super();
		
	}

	public ToponomasticaDbException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ToponomasticaDbException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ToponomasticaDbException(String message) {
		super(message);
		
	}

	public ToponomasticaDbException(Throwable cause) {
		super(cause);
		
	}

}
