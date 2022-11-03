package net.example.finance.mybank.model.enums;

public enum AccountTypeEnum {
	SAVING("saving"),
	CHECKING("checking");
	
	private String value;

    AccountTypeEnum(String value) {
      this.value = value;
    }
    
    public String getValue() {
      return value;
    }

    public String toString() {
      return String.valueOf(value);
    }
}
