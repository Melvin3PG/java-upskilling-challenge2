package net.example.finance.mybank.constants;

public enum TransactionCodes {

	SUCCESSFUL("I00"),
	ERROR("E01"),
	NOT_VALID("W01");
	
	private final String code;
	
	TransactionCodes(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}
