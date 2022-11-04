package net.example.finance.mybank.service;

import java.util.List;

import net.example.finance.mybank.exception.CustomerException;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.entity.Customer;

/**
 * Customer service interface
 * @author aline.perez
 *
 */
public interface CustomerService {

	/**
	 * Creates a new customer and saves it on DB.
	 * @param customer model with all the information to build an customer
	 * @return The created customer
	 * @throws CustomerException If the customer parameter is null or if something goes wrong.
	 *  
	 */
	public Customer createcustomer(Customer customer) throws CustomerException;
	
	/**
	 * Modify customer data.
	 * @param customer model with the customer information
	 * @param customerNumber the customer number
	 * @return The updated customer
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 *  
	 */
	public Customer updatecustomer(Customer customer, Long customerNumber) throws CustomerException;
	
	/**
	 * Delete an customer from database.
	 * @param customerNumber the customer number
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 *  
	 */
	public void deletecustomer(Long customerNumber) throws CustomerException;

	/**
	 * Retrieve customer that match with the given customer number
	 * @param customerNumber the customer number
	 * @return customer
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 *  
	 */
	public Customer getcustomerById(Long customerNumber) throws CustomerException;
	
	/**
	 * Retrieve all the customers
	 * @return an customer List
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 *  
	 */
	public List<Customer> getAll() throws CustomerException;
	
	/**
	 * This method applies partial modifications
	 * @param customer model with the customer information
	 * @param customerNumber the customer number
	 * @return The updated customer
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 */
	public Customer partialUpdateCustomer(Customer customer, Long customerNumber) throws CustomerException;
	
	/**
	 * Retrieve customer accounts
	 * @param customerNumber the customer number
	 * @return The accounts from customer
	 * @throws CustomerException if the some parameter is null or if something goes wrong.
	 */
	public List<Account> getAccountsByCustomerId(Long customerNumber) throws CustomerException;
	
	/**
	 * Retrieves an account related with the account and customer IDs provided
	 * @param customerNumber the customer identifier
	 * @param accountId account identifier
	 * @return an Account object
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 */
	public Account getAccountByCustomerAndAccountId(Long customerNumber, Long accountId) throws CustomerException;
	
	/**
	 * Verify if the accountId provided exists for the customer
	 * @param customerNumber the customer identifier
	 * @param accountId account identifier
	 * @throws CustomerException if some parameter is null or if something goes wrong.
	 */
	public void verifyAccountOfCustomer(Long customerNumber, Long accountId) throws CustomerException;

	
}
