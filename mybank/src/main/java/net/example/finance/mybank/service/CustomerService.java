package net.example.finance.mybank.service;

import java.util.List;

import net.example.finance.mybank.openapi.model.AccountObjectDto;
import net.example.finance.mybank.openapi.model.CustomerObjectDto;

public interface CustomerService {
	
	List<CustomerObjectDto> getAllCustomer();
	
	CustomerObjectDto createCustomer(CustomerObjectDto  customerDto);
	
	CustomerObjectDto updateCustomer(long customerNumber, CustomerObjectDto customerDto);
	
	CustomerObjectDto updateAccount(long customerNumber, CustomerObjectDto customerDto);
	
	CustomerObjectDto partialUpdateCustomer(long customerId, CustomerObjectDto customerDto);
	
	void deleteAccountOfCustomer(long customerId, long accountId);
	
	void deleteCustomer(long customerId);
	
	AccountObjectDto getAccountByCustomerAndAccountNumber(long customerId, long accountId);
	
	List<AccountObjectDto> getAllAccountsOfCustomer(long customerId);
	
	CustomerObjectDto getCustomerByCustomerNumber(long customerId);

	AccountObjectDto partialUpdateAccountOfCustomer(long customerId, long accountId, AccountObjectDto accountDto);
	
	AccountObjectDto updateAccountOfCustomer(long customerId, long accountId, AccountObjectDto accountDto);
}
