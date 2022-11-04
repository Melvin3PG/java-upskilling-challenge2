package net.example.finance.mybank.model.enums;

/**
 * Customer types enum
 * @author aline.perez
 *
 */
public enum CustomerType {

	RETAIL("RETAIL"),
    
    CORPORATE("CORPORATE");
	
	
	private final String value;
	
	private CustomerType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
