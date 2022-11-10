package net.example.finance.mybank.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import net.example.finance.mybank.exceptions.InvalidAccountCustomerException;
import net.example.finance.mybank.exceptions.ResourceNotFoundException;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.entity.Customer;
import net.example.finance.mybank.model.enums.AccountTypeEnum;
import net.example.finance.mybank.openapi.model.AccountObjectDto;
import net.example.finance.mybank.openapi.model.CustomerObjectDto;
import net.example.finance.mybank.openapi.model.CustomerObjectDto.CustomerTypeEnum;
import net.example.finance.mybank.repository.AccountRepository;
import net.example.finance.mybank.repository.CustomerRepository;
import net.example.finance.mybank.service.CustomerService;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {

	CustomerRepository customerRepository;
	AccountRepository accountRepository;
	
	public CustomerServiceImpl(CustomerRepository customerRepository,
									  AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CustomerObjectDto> getAllCustomer() {
		log.debug(String.format("Service retrieve all customers."));
		
		//retrieve all customers
		List<CustomerObjectDto> listCustomers = customerRepository.findAll()
										.stream()
										.map((customer) -> mapCustomerToDTO(customer))
										.collect(Collectors.toList());
		
		return listCustomers;
	}

	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public CustomerObjectDto createCustomer(CustomerObjectDto customerDto) {
		log.debug(String.format("Service creating customer in database : %s", customerDto.toString()));
		
		
		try {
			final Customer customerEntity = mapCustomerToEntity(customerDto);
			customerEntity.setAccounts(new ArrayList<>());
			
			final Customer customer = customerRepository.save(customerEntity);
			customerDto.getAccounts().forEach((AccountObjectDto accountDto) -> {
				customer.addAccount(mapAccountToEntity(accountDto));
			});
			
			return mapCustomerToDTO(customerRepository.save(customer));
		 
		}catch(Exception ex) {
			log.error(String.format("Error creating new account. Account: %s \n %s", customerDto.toString(), ex.getMessage()));
			throw ex;
		}
		
	}

	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public CustomerObjectDto updateCustomer(long customerNumber, CustomerObjectDto customerDto) {
		log.debug(String.format("Service updating customer in database : %s - %s",
				customerNumber,
				customerDto.toString()));

		Customer customerUpdated = null;
		
		//retrieve customer by customer number
		Customer customer = customerRepository.findById(customerNumber)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer number", String.valueOf(customerNumber)) );
		
		try {
			customer.setActive(customerDto.getActive());
			customer.setAtDate(customerDto.getAtDate());
			customer.setAccounts(customerDto.getAccounts().stream()
					.map(account -> mapAccountToEntity(account))
					.collect(Collectors.toList()) );
			
			customerUpdated = customerRepository.save(customer);
			
		}catch(Exception ex) {
			log.error(String.format("Error updating the customer with customer number: %s. \n Error: %s", 
					customerNumber, ex.getMessage()));
		throw ex;
		}
		
		return mapCustomerToDTO(customerUpdated);
	}

	
	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public void deleteCustomer(long customerId) {
		//retrieve account by id
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", 
									"customer number", String.valueOf(customerId)));
		try {
			customerRepository.delete(customer);
		}catch(Exception ex) {
			log.error(String.format("Error deleting the customer with customer number: %s. \n Error: %s", 
					String.valueOf(customerId), ex.getMessage()));
			throw ex;
		}
	}
	
	
	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public CustomerObjectDto updateAccount(long customerNumber, CustomerObjectDto customerDto) {
		log.debug(String.format("Service updating customer in database : %s - %s",
				customerNumber,
				customerDto.toString()));

		Customer customerUpdated = null;
		
		//retrieve customer by customer number
		Customer customer = customerRepository.findById(customerNumber)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer number", String.valueOf(customerNumber)) );
		
		try {
			customer.setActive(customerDto.getActive());
			customer.setAtDate(customerDto.getAtDate());
			customer.setAccounts(customerDto.getAccounts().stream()
					.map(account -> mapAccountToEntity(account))
					.collect(Collectors.toList()) );
			
			customerUpdated = customerRepository.save(customer);
			
		}catch(Exception ex) {
			log.error(String.format("Error updating the customer with customer number: %s. \n Error: %s", 
					customerNumber, ex.getMessage()));
		throw ex;
		}
		
		return mapCustomerToDTO(customerUpdated);
	}

	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public CustomerObjectDto partialUpdateCustomer(long customerId, CustomerObjectDto customerDto) {
		
		return updateCustomer(customerId, customerDto);
	}

	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public void deleteAccountOfCustomer(long customerId, long accountId) {
		log.debug(String.format("Service deleting account of customer. - Customer Number: %s - Account Number: %s",
				customerId,
				accountId));

		//retrieve customer by customer number
		Customer customer = customerRepository.findById(customerId)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer number", String.valueOf(customerId)));
		
		//retrieve account by id
		Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account", 
									"id", String.valueOf(accountId)));
		
		if(customer.getCustomerNumber() != account.getCustomer().getCustomerNumber()) {
			throw new InvalidAccountCustomerException(customerId, accountId);
		}
		
		
		try {
			accountRepository.delete(account);
		}catch(Exception ex) {
			log.error(String.format("Error deleting the account with account id: %s. \n Error: %s", 
					String.valueOf(accountId), ex.getMessage()));
			throw ex;
		}
	}

	@Override
	public AccountObjectDto getAccountByCustomerAndAccountNumber(long customerId, long accountId) {

		//retrieve customer by customer number
		Customer customer = customerRepository.findById(customerId)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer number", String.valueOf(customerId)));
		
		//retrieve account by id
		Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account", 
									"id", String.valueOf(accountId)));
		
		if(customer.getCustomerNumber() != account.getCustomer().getCustomerNumber()) {
			throw new InvalidAccountCustomerException(customerId, accountId);
		}
		
		return mapAccountToDTO(account);
	}

	@Override
	public List<AccountObjectDto> getAllAccountsOfCustomer(long customerId) {
		
		List<AccountObjectDto> accountsDto = null;
		
		//retrieve customer by customer number
		Customer customer = customerRepository.findById(customerId)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer number", String.valueOf(customerId)));
		
		//retrieve accounts by customer
		List<Account> accounts = accountRepository.findByCustomer(customer);
		if(null != accounts && accounts.size() > 0) {
			accountsDto = accounts.stream().map((Account account) -> mapAccountToDTO(account)).collect(Collectors.toList());
		}
		
		return accountsDto;
	}

	@Override
	public CustomerObjectDto getCustomerByCustomerNumber(long customerId) {
		//retrieve customer by customer number
		Customer customer = customerRepository.findById(customerId)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer number", String.valueOf(customerId)));
		
		return mapCustomerToDTO(customer);
		
	}

	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public AccountObjectDto partialUpdateAccountOfCustomer(long customerId, long accountId,
			AccountObjectDto accountDto) {
		AccountObjectDto accountUpdated = null;
		
		//retrieve customer by customer number
		Customer customer = customerRepository.findById(customerId)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer number", String.valueOf(customerId)));
		
		//retrieve account by id
		Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account", 
									"id", String.valueOf(accountId)));
		
		if(customer.getCustomerNumber() != account.getCustomer().getCustomerNumber()) {
			throw new InvalidAccountCustomerException(customerId, accountId);
		}
		
		try {
			account.setBalance(accountDto.getBalance());
			account.setOverdraftAllowed(accountDto.getOverdraftAllowed());
			account.setOverdraftAmount(accountDto.getOverdraftAmount());
			
			account = accountRepository.save(account);
			
			accountUpdated = mapAccountToDTO(account);
		}catch(Exception ex) {
			
			throw ex;
		}
		
		return accountUpdated;
	}

	@Override
	@Transactional
    (rollbackFor = Exception.class, 
     noRollbackFor = ResourceNotFoundException.class)
	public AccountObjectDto updateAccountOfCustomer(long customerId, long accountId, AccountObjectDto accountDto) {
		// TODO Auto-generated method stub
		return partialUpdateAccountOfCustomer(customerId, accountId, accountDto);
	}
	
	private CustomerObjectDto mapCustomerToDTO(Customer customer) {
		CustomerObjectDto customerDto = new CustomerObjectDto();
		customerDto.setActive(customer.isActive());
		customerDto.setAtDate(customer.isAtDate());
		customerDto.setCustomerNumber(customer.getCustomerNumber());
		customerDto.setCustomerType(CustomerTypeEnum.valueOf(customer.getCustomerType().name()));
		if(null != customer.getAccounts() && customer.getAccounts().size() > 0) {
			List<AccountObjectDto> accountsDto = customer.getAccounts().stream()
					.map(account -> mapAccountToDTO(account))
					.collect(Collectors.toList());
			
			customerDto.setAccounts(accountsDto);
		}
		
		return customerDto;
	}
	
	
	private Customer mapCustomerToEntity(CustomerObjectDto customerDto) {
		Customer customer = new Customer();
		customer.setActive(customerDto.getActive());
		customer.setAtDate(customerDto.getAtDate());
		customer.setCustomerType(CustomerTypeEnum.valueOf(customerDto.getCustomerType().name()));
		customer.setCustomerNumber(customerDto.getCustomerNumber());
		if(null != customerDto.getAccounts() && customerDto.getAccounts().size() > 0) {
			customerDto.getAccounts().forEach((AccountObjectDto account) -> {
				customer.addAccount(mapAccountToEntity(account));
			});
		}
		
		return customer;
	}
	
	
	/**
	 * Convert an account entity object to account dto
	 * 
	 * @param account
	 * 
	 * @return
	 */
	private AccountObjectDto mapAccountToDTO(Account account) {
		AccountObjectDto accountDto = new AccountObjectDto();
		accountDto.setAccountNumber(account.getAccountNumber());
		accountDto.setAccountType(net.example.finance.mybank.openapi.model.AccountObjectDto.AccountTypeEnum.valueOf(account.getAccountType().name()));
		accountDto.setBalance(account.getBalance());
		accountDto.setOverdraftAllowed(account.isOverdraftAllowed());
		accountDto.setOverdraftAmount(account.getOverdraftAmount());
		//return modelMapper.map(account, AccountObjectDto.class);
		return accountDto;
	}
	
	/**
	 * Convert an account dto object to account entity
	 * 
	 * @param accountDto
	 * 
	 * @return
	 */
	private Account mapAccountToEntity(AccountObjectDto accountDto) {
		Account account = new Account();
		account.setAccountNumber(accountDto.getAccountNumber());
		account.setAccountType(AccountTypeEnum.valueOf(accountDto.getAccountType().getValue()));
		account.setBalance(accountDto.getBalance());
		account.setOverdraftAllowed(accountDto.getOverdraftAllowed());
		account.setOverdraftAmount(accountDto.getOverdraftAmount());
		//return modelMapper.map(accountDto, Account.class);
		
		return account;
	}

	

}
