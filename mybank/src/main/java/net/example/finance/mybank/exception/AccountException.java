package net.example.finance.mybank.exception;

public class AccountException extends Exception  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8389788114017665586L;

	public AccountException() {
		super();
	}

	public AccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountException(String message) {
		super(message);
	}

	public AccountException(Throwable cause) {
		super(cause);
	}

	
	
}