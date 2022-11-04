package net.example.finance.mybank.model.enums;

import com.example.mvnprg.openapi.model.AccountObject.AccountTypeEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountType {
	 	
		SAVING("saving"),
	    
	    CHECKING("checking");

	    private String value;

	    AccountType(String value) {
	      this.value = value;
	    }

	    @JsonValue
	    public String getValue() {
	      return value;
	    }
	    
	    @Override
	    public String toString() {
	      return String.valueOf(value);
	    }

	    @JsonCreator
	    public static AccountType fromValue(String value) {
	      for (AccountType b : AccountType.values()) {
	        if (b.value.equals(value)) {
	          return b;
	        }
	      }
	      throw new IllegalArgumentException("Unexpected value '" + value + "'");
	    }
	  
}
