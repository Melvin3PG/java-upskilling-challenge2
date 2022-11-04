package net.example.finance.mybank.model.enums;

public enum ResponseCodes {

	IA01("A new account was created successfully"),
	IA02("The account was updated successfully"),
	IA03("The information was retrieved successfully"),
	EA04("An error occurred. Internal error."),
	EA05("The specified account does not exist"),
	EA06("Malformed request body"),
	IA07("The account was deleted successfully"),

	
	IU01("User was authenticated successfully"),
	EU02("User name or password does not exist"),
	EU03("An error occurred. Internal error."),
	EU04("Malformed request body"),
	
	IC01("A new customer was created successfully"),
	IC02("The customer was updated successfully"),
	IC03("The information was retrieved successfully"),
	IC04("The customer was deleted successfully"),
	EC04("An error occurred. Internal error."),
	EC05("The specified customer does not exist"),
	EC06("Malformed request body"),
	EC07("The customer or account provided does not exist");
	
	private final String message;
	
	private ResponseCodes(String message) {
		this.message = message;
	}
	
	public String getValue() {
		return message;
	}
}
