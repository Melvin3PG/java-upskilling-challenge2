package net.example.finance.mybank.service;

import java.util.List;

import net.example.finance.mybank.openapi.model.AccountObjectDto;
import net.example.finance.mybank.openapi.model.CustomerObjectDto;

public interface CustomerService {
	
	/**
	 * Return all customers available in database
	 * 
	 * @return {@link List<CustomerObjectDto>}	List of DTO objects with all customers
	 */
	List<CustomerObjectDto> getAllCustomer();
	
	/**
	 * Create a customer and saves in database
	 * 
	 * @param customerDto	DTO object with all data
	 * 
	 * @return {@link CustomerObjectDto}	DTO object with new customer created
	 */
	CustomerObjectDto createCustomer(CustomerObjectDto  customerDto);
	
	/**
	 * Update an existing customer in database.
	 * 
	 * @param customerNumber	Customer number
	 * 
	 * @param customerDto		DTO object with information to update
	 * 
	 * @return {@link CustomerObjectDto}	DTO object with customer information updated
	 */
	CustomerObjectDto updateCustomer(long customerNumber, CustomerObjectDto customerDto);
	
	/**
	 * Update an existing customer in database.
	 * 
	 * @param customerNumber	Customer number
	 * 
	 * @param customerDto		DTO object with information to update
	 * 
	 * @return {@link CustomerObjectDto}	DTO object with customer information updated
	 */
	CustomerObjectDto partialUpdateCustomer(long customerId, CustomerObjectDto customerDto);
	
	/**
	 * Delete an account of customer
	 * 
	 * @param customerId		Customer number
	 * 
	 * @param accountId			Account number
	 */
	void deleteAccountOfCustomer(long customerId, long accountId);
	
	/**
	 * Delete a customer 
	 * 
	 * @param customerId		Customer number
	 */
	void deleteCustomer(long customerId);
	
	/**
	 * Returns a DTO account object using the customer number and account number
	 * 
	 * @param customerId		Customer number
	 * 
	 * @param accountId			Account number
	 * 
	 * @return	{@link 	AccountObjectDto}
	 */
	AccountObjectDto getAccountByCustomerAndAccountNumber(long customerId, long accountId);
	
	/**
	 * Returns all accounts of customer
	 * 
	 * @param customerId		Customer number
	 * 
	 * @return	{@link List<AccountObjectDto>}	List of DTO objects with all accounts
	 */
	List<AccountObjectDto> getAllAccountsOfCustomer(long customerId);
	
	/**
	 * Returns a DTO customer object with all customer data.
	 * 
	 * @param customerId	Customer number
	 * 
	 * @return	{link CustomerObjectDto}	DTO object with customer data
	 */
	CustomerObjectDto getCustomerByCustomerNumber(long customerId);

	/**
	 * Partial update of account customer
	 * 
	 * @param customerId		Customer number
	 * 
	 * @param accountId			Account number
	 * 
	 * @param accountDto		Account DTO object with data
	 * 
	 * @return {@link AccountObjectDto}		DTO object with account data updated
	 */
	AccountObjectDto partialUpdateAccountOfCustomer(long customerId, long accountId, AccountObjectDto accountDto);
	
	/**
	 * Update an account customer
	 * 
	 * @param customerId		Customer number
	 * 
	 * @param accountId			Account number
	 * 
	 * @param accountDto		Account DTO object with data
	 * 
	 * @return {@link AccountObjectDto}		DTO object with account data updated
	 */
	AccountObjectDto updateAccountOfCustomer(long customerId, long accountId, AccountObjectDto accountDto);
	
	/**
	 * Creates an account customer
	 * 
	 * @param customerId		Customer number
	 * 
	 * @param accountDto		Account DTO object with data
	 * 
	 * @return {@link AccountObjectDto}		DTO object with account data updated
	 */
	AccountObjectDto createAccountByCustomer(long customerId, AccountObjectDto accountDto);
}
