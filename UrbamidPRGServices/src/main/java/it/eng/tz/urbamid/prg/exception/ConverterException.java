package it.eng.tz.urbamid.prg.exception;

public class ConverterException extends PRGDbException {

	private static final long serialVersionUID = -3569841035471271944L;

	public ConverterException() {
		super();
	}

	public ConverterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConverterException(String message) {
		super(message);
	}

	public ConverterException(Throwable cause) {
		super(cause);
	}

}