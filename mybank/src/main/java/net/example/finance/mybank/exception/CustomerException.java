/**
 * 
 */
package net.example.finance.mybank.exception;

/**
 * @author aline.perez
 *
 */
public class CustomerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6005944205939381017L;

	public CustomerException() {
		super();
	}

	public CustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CustomerException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerException(String message) {
		super(message);
	}

	public CustomerException(Throwable cause) {
		super(cause);
	}

}
