package net.example.finance.mybank.model.dto;


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
	private String number;
	
	/**
	 * Account type
	 */
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
