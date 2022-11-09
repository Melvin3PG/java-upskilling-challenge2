package net.example.finance.mybank.model.enums;


public enum CustomerTypeEnum {
	RETAIL("RETAIL"),
	CORPORATE("CORPORATE");
	
	private String value;

    CustomerTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
	
}
