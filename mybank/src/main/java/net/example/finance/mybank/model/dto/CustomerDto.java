package net.example.finance.mybank.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

/**
 * Data transfer object for Customer
 * 
 * @author jesus.quintero
 *
 */
@Data
public class CustomerDto {

	/**
	 * ID
	 */
	private long id;
	
	/**
	 * Customer name
	 */
	private String name;
	
	/**
	 * Customer birthdate
	 */
	private Date birthdate;
	
	/**
	 * Determine if customer is active
	 */
	private boolean isActive;

	/**
	 * Determine if customer has debts or is up to date.
	 */
	private boolean isNotDebts;
	
	/**
	 * Accounts list of customer
	 */
	Set<AccountDto> accounts = new HashSet<>(); 
}
