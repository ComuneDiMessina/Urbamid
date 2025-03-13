package it.eng.tz.urbamid.catasto.exception;

public class CatastoDbException extends CatastoServiceException {

	private static final long serialVersionUID = 6873516606539034370L;

	public CatastoDbException() {
		super();
	}

	public CatastoDbException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CatastoDbException(String message, Throwable cause) {
		super(message, cause);
	}

	public CatastoDbException(String message) {
		super(message);
	}

	public CatastoDbException(Throwable cause) {
		super(cause);
		
	}

}
