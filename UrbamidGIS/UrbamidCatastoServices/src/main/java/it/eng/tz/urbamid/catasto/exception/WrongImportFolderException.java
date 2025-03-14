package it.eng.tz.urbamid.catasto.exception;

public class WrongImportFolderException extends CatastoServiceException {

	private static final long serialVersionUID = 6333189517070590973L;

	public WrongImportFolderException() {
		super();
	}

	public WrongImportFolderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public WrongImportFolderException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public WrongImportFolderException(String message) {
		super(message);
		
	}

	public WrongImportFolderException(Throwable cause) {
		super(cause);
		
	}

}