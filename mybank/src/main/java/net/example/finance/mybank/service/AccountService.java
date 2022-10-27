package net.example.finance.mybank.service;

import net.example.finance.mybank.model.dto.AccountDto;
import net.example.finance.mybank.model.dto.PaginatedDataDto;

public interface AccountService {
	
	/**
	 * Create an account and saves in database.
	 * 
	 * @param accountDto		DTO Object for account
	 * 
	 * @return	{@code AccountDto}	DTO Object with new account saved	
	 */
	AccountDto createAccount(AccountDto accountDto);
	
	/**
	 * Update an existing account in database
	 * 
	 * @param accountNumber		Account number of account to update
	 * 
	 * @param accountDto		DTO Object with information to update			
	 * 
	 * @return {@code AccountDto}	DTO Object with account updated
	 */
	AccountDto updateAccount(String accountNumber, AccountDto accountDto);
	
	/**
	 * Returns the account given an account number.
	 * 
	 * @param accountNumber		Account number.
	 * 
	 * @return	{@code AccountDto}	DTO Object with account information
	 */
	AccountDto getAccountByNumber(String accountNumber);
	
	/**
	 * Delete an account
	 * 
	 * @param accountId		Account ID
	 */
	void deleteAccount(long accountId);
	
	/**
	 * Returns all accounts
	 * 
	 * @return {@code List<AccountDto>}	 List of DTO objects with all accounts
	 */
	PaginatedDataDto<AccountDto> getAll(int pageNo, int pageSize, String sortBy, String sortDir);
	
	
}
