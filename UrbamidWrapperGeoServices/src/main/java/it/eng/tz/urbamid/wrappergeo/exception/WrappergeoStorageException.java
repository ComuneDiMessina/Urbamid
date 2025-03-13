package it.eng.tz.urbamid.wrappergeo.exception;

public class WrappergeoStorageException extends WrapperGeoServiceException {

	private static final long serialVersionUID = -3569841035471271944L;

	public WrappergeoStorageException() {
		super();
	}

	public WrappergeoStorageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrappergeoStorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrappergeoStorageException(String message) {
		super(message);
	}

	public WrappergeoStorageException(Throwable cause) {
		super(cause);
	}

}