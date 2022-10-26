package net.example.finance.mybank.model.dto;


import lombok.Data;
import net.example.finance.mybank.model.enums.AccountType;

@Data
public class AccountDto {

	private long id;
	
	private String number;
	
	private AccountType type;
	
	private double balance;
	
	private boolean isOverdraft;
	
	private double	overdraftAmount;
	
}
