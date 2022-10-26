package net.example.finance.mybank.model.enums;

public enum ResponseCodes {

	A01("A new account was created successfully"),
	A02("The account was updated successfully"),
	A03("The information was retrieved successfully"),
	A04("An error occurred. Internal error."),
	A05("The specified account does not exist");
	
	
	private final String message;
	
	private ResponseCodes(String message) {
		this.message = message;
	}
	
	public String getValue() {
		return message;
	}
}
