package net.example.finance.mybank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidAccountCustomerException extends RuntimeException {

	public InvalidAccountCustomerException(long customerNumber, long accountNumber) {
		super(String.format("The account %s is not valid for the customer with id: %s", accountNumber, customerNumber));

	}
}
