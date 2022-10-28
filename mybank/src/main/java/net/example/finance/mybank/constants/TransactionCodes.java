package net.example.finance.mybank.constants;

public enum TransactionCodes {

	SUCCESSFUL("00"),
	ERROR("-01"),
	NOT_VALID("01");
	
	private final String code;
	
	TransactionCodes(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}
