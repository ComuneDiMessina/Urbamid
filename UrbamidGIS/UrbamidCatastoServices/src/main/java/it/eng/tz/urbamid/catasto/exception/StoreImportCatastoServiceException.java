package it.eng.tz.urbamid.catasto.exception;

public class StoreImportCatastoServiceException extends Exception {

	private static final long serialVersionUID = -5724648140351891036L;

	public StoreImportCatastoServiceException() {
		super();
	}

	public StoreImportCatastoServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StoreImportCatastoServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public StoreImportCatastoServiceException(String message) {
		super(message);
	}

	public StoreImportCatastoServiceException(Throwable cause) {
		super(cause);
	}

}