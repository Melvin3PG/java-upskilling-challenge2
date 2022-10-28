package net.example.finance.mybank.model.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import net.example.finance.mybank.model.enums.AccountType;

/**
 * Data transfer object for Account
 * 
 * @author jesus.quintero
 *
 */
@Data
public class AccountDto {

	/**
	 * ID 
	 */
	private long id;
	
	/**
	 * Account number
	 */
	@NotEmpty(message = "Account number should not be empty.")
	@Size(min = 10, message = "Account number must be minimum 10 charactes=")
	private String number;
	
	/**
	 * Account type
	 */
	@NotEmpty(message = "Account type should not be empty.")
	private AccountType type;
	
	/**
	 * Account balance
	 */
	private double balance;
	
	/**
	 * Determine is Overdraft
	 */
	private boolean isOverdraft;
	
	/**
	 * Overdraft Amount
	 */
	private double	overdraftAmount;
	
}
