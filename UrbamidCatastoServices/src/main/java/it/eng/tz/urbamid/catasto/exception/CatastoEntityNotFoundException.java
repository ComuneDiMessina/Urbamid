package it.eng.tz.urbamid.catasto.exception;

public class CatastoEntityNotFoundException extends CatastoDbException {

	private static final long serialVersionUID = -5724648140351891036L;

	public CatastoEntityNotFoundException() {
		super();
	}

	public CatastoEntityNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CatastoEntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CatastoEntityNotFoundException(String message) {
		super(message);
	}

	public CatastoEntityNotFoundException(Throwable cause) {
		super(cause);
	}

}