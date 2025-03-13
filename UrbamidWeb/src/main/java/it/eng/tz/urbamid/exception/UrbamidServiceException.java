package it.eng.tz.urbamid.exception;

public class UrbamidServiceException extends Exception {

	private static final long serialVersionUID = -5724648140351891036L;

	public UrbamidServiceException() {
		super();
	}

	public UrbamidServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UrbamidServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UrbamidServiceException(String message) {
		super(message);
	}

	public UrbamidServiceException(Throwable cause) {
		super(cause);
	}

}