package net.example.finance.mybank.service;


import net.example.finance.mybank.model.dto.PaginatedDataDto;
import net.example.finance.mybank.openapi.model.AccountObjectDto;

public interface AccountService {
	
	/**
	 * Create an account and saves in database.
	 * 
	 * @param accountDto		DTO Object for account
	 * 
	 * @return	{@code AccountObjectDto}	DTO Object with new account saved	
	 */
	AccountObjectDto createAccount(AccountObjectDto accountDto);
	
	/**
	 * Update an existing account in database
	 * 
	 * @param accountNumber		Account number of account to update
	 * 
	 * @param accountDto		DTO Object with information to update			
	 * 
	 * @return {@code AccountObjectDto}	DTO Object with account updated
	 */
	AccountObjectDto updateAccount(long accountNumber, AccountObjectDto accountDto);
	
	/**
	 * Returns the account given an account number.
	 * 
	 * @param accountNumber		Account number.
	 * 
	 * @return	{@code AccountObjectDto}	DTO Object with account information
	 */
	AccountObjectDto getAccountByNumber(long accountNumber);
	
	/**
	 * Delete an account
	 * 
	 * @param accountId		Account ID
	 */
	void deleteAccount(long accountId);
	
	/**
	 * Returns all accounts
	 * 
	 * @return {@code List<AccountObjectDto>}	 List of DTO objects with all accounts
	 */
	PaginatedDataDto<AccountObjectDto> getAll(int pageNo, int pageSize, String sortBy, String sortDir);
	
	
}
