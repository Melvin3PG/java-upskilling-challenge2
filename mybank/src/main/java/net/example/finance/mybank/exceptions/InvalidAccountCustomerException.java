package net.example.finance.mybank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
 * This class is used to throw an exception when the account is does not 
 * have relation with customer.
 * @author jesus.quintero
 *
 */
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidAccountCustomerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAccountCustomerException(long customerNumber, long accountNumber) {
		super(String.format("The account %s is not valid for the customer with id: %s", accountNumber, customerNumber));

	}
}
