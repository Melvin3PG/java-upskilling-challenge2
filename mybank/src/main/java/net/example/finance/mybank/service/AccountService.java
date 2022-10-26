package net.example.finance.mybank.service;

import net.example.finance.mybank.model.dto.AccountDto;

public interface AccountService {
	
	AccountDto createAccount(AccountDto account);
	
	AccountDto updateAccount(String accountNumber, AccountDto account);
		
	AccountDto getAccountByNumber(String accountNumber);
	
	void deleteAccount(String accountNumber);
	
	
}
