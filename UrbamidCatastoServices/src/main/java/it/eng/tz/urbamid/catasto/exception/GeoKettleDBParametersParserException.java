package it.eng.tz.urbamid.catasto.exception;

public class GeoKettleDBParametersParserException extends CatastoServiceException {

	private static final long serialVersionUID = -3569841035471271944L;

	public GeoKettleDBParametersParserException() {
		super();
	}

	public GeoKettleDBParametersParserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GeoKettleDBParametersParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeoKettleDBParametersParserException(String message) {
		super(message);
	}

	public GeoKettleDBParametersParserException(Throwable cause) {
		super(cause);
	}

}