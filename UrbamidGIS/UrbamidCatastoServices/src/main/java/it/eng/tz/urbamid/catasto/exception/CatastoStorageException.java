package it.eng.tz.urbamid.catasto.exception;

public class CatastoStorageException extends CatastoServiceException {

	private static final long serialVersionUID = -3569841035471271944L;

	public CatastoStorageException() {
		super();
	}

	public CatastoStorageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CatastoStorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public CatastoStorageException(String message) {
		super(message);
	}

	public CatastoStorageException(Throwable cause) {
		super(cause);
	}

}