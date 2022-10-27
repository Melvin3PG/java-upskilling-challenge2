package net.example.finance.mybank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.example.finance.mybank.exceptions.ResourceNotFoundException;
import net.example.finance.mybank.model.dto.AccountDto;
import net.example.finance.mybank.model.dto.PaginatedDataDto;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.repository.AccountRepository;
import net.example.finance.mybank.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	AccountRepository repository;
	ModelMapper modelMapper;
	
	public AccountServiceImpl(AccountRepository repository,
								ModelMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	
	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public AccountDto createAccount(AccountDto accountDto) {
		log.debug(String.format("Service creating account in database : %s", accountDto.toString()));
		
		
		Account account = mapToEntity(accountDto);
		
		Account newAccount = repository.save(account);
		
		return mapToDTO(newAccount);
	}

	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public AccountDto updateAccount(String accountNumber, AccountDto accountDto) {
		log.debug(String.format("Service updating account in database : %s - %s",
									accountNumber,
									accountDto.toString()));

		//retrieve account by account number
		Account account = repository.findByNumber(accountNumber).orElseThrow(() -> new ResourceNotFoundException("", ""));
		
		account.setBalance(account.getBalance());
		account.setOverdraft(accountDto.isOverdraft());
		account.setOverdraftAmount(accountDto.getOverdraftAmount());
		
		Account accountUpdated = repository.save(account);
		
		return mapToDTO(accountUpdated);
	}

	@Override
	@Transactional(readOnly = true)
	public AccountDto getAccountByNumber(String accountNumber) {
		log.debug(String.format("Service retrieve account from database : %s",
				accountNumber));
		
		//retrieve account by account number
		Account account = repository.findByNumber(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("", ""));
				
		return mapToDTO(account);
	}

	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public void deleteAccount(long id) {
		//retrieve account by id
		Account account = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("", ""));
		repository.delete(account);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PaginatedDataDto<AccountDto> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
		//create sort object with direction
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
						: Sort.by(sortBy).descending();
		
		//create pageable object for pagination
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Account> pageOfAccounts = repository.findAll(pageable);
		
		//Get content from page object
		List<Account> accounts = pageOfAccounts.getContent();
		
		//Create response object with all details of pagination and data
		PaginatedDataDto<AccountDto> dataPaginated = new PaginatedDataDto<>();
		dataPaginated.setPageNo(pageOfAccounts.getNumber());
		dataPaginated.setPageSize(pageOfAccounts.getSize());
		dataPaginated.setTotalElements(pageOfAccounts.getTotalElements());
		dataPaginated.setTotalPages(pageOfAccounts.getTotalPages());
		
		//Set list accounts to paginated object in dto objects
		dataPaginated.setData(accounts.stream()
								  .map(account -> mapToDTO(account))
								  .collect(Collectors.toList())
							  );
		
		return dataPaginated;
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
