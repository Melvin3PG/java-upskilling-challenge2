package net.example.finance.mybank.service;

import java.util.List;

import net.example.finance.mybank.exception.AccountException;
import net.example.finance.mybank.model.entity.Account;

/**
 * Account service interface
 * @author aline.perez
 *
 */
public interface AccountService {
	
	/**
	 * Creates a new account and saves it on DB.
	 * @param account model with all the information to build an account
	 * @return The created account
	 * @throws AccountException If the account parameter is null or if something goes wrong.
	 *  
	 */
	public Account createAccount(Account account) throws AccountException;
	
	/**
	 * Modify account data.
	 * @param account model with the account information
	 * @param accountNumber the account number
	 * @return The updated account
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 *  
	 */
	public Account updateAccount(Account account, Long accountNumber) throws AccountException;
	
	/**
	 * Delete an account from database.
	 * @param accountNumber the account number
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 *  
	 */
	public void deleteAccount(Long accountNumber) throws AccountException;

	/**
	 * Retrieve account that match with the given account number
	 * @param accountNumber the account number
	 * @return account
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 *  
	 */
	public Account getAccountById(Long accountNumber) throws AccountException;
	
	/**
	 * Retrieve all the accounts
	 * @return an account List
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 *  
	 */
	public List<Account> getAll() throws AccountException;
	
	/**
	 * This method applies partial modifications
	 * @param account model with the account information
	 * @param accountNumber the account number
	 * @return The updated account
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	public Account partialUpdateAccount(Account account, Long accountNumber) throws AccountException;
	
	/**
	 * This method deletes an account by customer ID and account ID
	 * @param customerNumber customer identifier
	 * @param accountId account identifier
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	public void deleteByAccountIdAndCustomerId(Long customerNumber, Long accountNumber)  throws AccountException;
	
	/**
	 * Method that update an account by customer ID and account ID
	 * @param account customer identifier
	 * @param customerNumber customer identifier
	 * @param accountNumber account identifier
	 * @return The updated account
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	public Account updateByAccountIdAndCustomerId(Account account, Long customerNumber, Long accountNumber)  throws AccountException;
	
	/**
	 * Method that get all Accounts by customer ID and account ID
	 * @param customerNumber customer identifier
	 * @return All accounts
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	public List<Account> getAllAccountsByCustomerId(Long customerNumber)  throws AccountException;
	
	/**
	 * Method that gets the account related to the customer and account IDs provided. 
	 * @param customerNumber customer identifier
	 * @param accountNumber account identifier
	 * @return Account object
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	public Account getByAccountIdAndCustomerId(Long customerNumber, Long accountNumber)  throws AccountException;
}