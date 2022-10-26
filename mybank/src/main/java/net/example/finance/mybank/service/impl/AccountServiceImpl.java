package net.example.finance.mybank.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import net.example.finance.mybank.model.dto.AccountDto;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.repository.AccountRepository;
import net.example.finance.mybank.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	AccountRepository repository;
	ModelMapper modelMapper;
	
	public AccountServiceImpl(AccountRepository repository,
								ModelMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = mapToEntity(accountDto);
		Account newAccount = repository.save(account);
		return mapToDTO(newAccount);
	}

	@Override
	public AccountDto updateAccount(String accountNumber, AccountDto accountDto) {
		Account account = repository.findByNumber(accountNumber);
		account.setBalance(account.getBalance());
		account.setOverdraft(accountDto.isOverdraft());
		account.setOverdraftAmount(accountDto.getOverdraftAmount());
		
		Account accountUpdated = repository.save(account);
		
		return mapToDTO(accountUpdated);
	}

	@Override
	public AccountDto getAccountByNumber(String number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAccount(String number) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Convert an account entity object to account dto
	 * 
	 * @param account
	 * 
	 * @return
	 */
	private AccountDto mapToDTO(Account account) {
		return modelMapper.map(account, AccountDto.class);
	}
	
	/**
	 * Convert an account dto object to account entity
	 * 
	 * @param accountDto
	 * 
	 * @return
	 */
	private Account mapToEntity(AccountDto accountDto) {
		return modelMapper.map(accountDto, Account.class);
	}

}
