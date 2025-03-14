package it.eng.tz.urbamid.catasto.exception;

public class WrongImportContentType extends CatastoServiceException {

	private static final long serialVersionUID = 2072479768718895646L;

	public WrongImportContentType() {
		super();
	}

	public WrongImportContentType(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public WrongImportContentType(String message, Throwable cause) {
		super(message, cause);
		
	}

	public WrongImportContentType(String message) {
		super(message);
		
	}

	public WrongImportContentType(Throwable cause) {
		super(cause);
		
	}

}