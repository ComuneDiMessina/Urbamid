package it.eng.tz.urbamid.catasto.exception;

public class Shp2PostgisException extends CatastoServiceException {

	private static final long serialVersionUID = -5818015538238591786L;

	public Shp2PostgisException() {
		super();
	}

	public Shp2PostgisException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Shp2PostgisException(String message, Throwable cause) {
		super(message, cause);
	}

	public Shp2PostgisException(String message) {
		super(message);
	}

	public Shp2PostgisException(Throwable cause) {
		super(cause);
	}

}