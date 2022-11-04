package net.example.finance.mybank.serviceimpl;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.example.finance.mybank.exception.AccountException;
import net.example.finance.mybank.exception.NotFoundException;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.repository.AccountRepository;
import net.example.finance.mybank.service.AccountService;

/**
 * Account Service implementation class
 * @author aline.perez
 *
 */
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

	/**
	 * Logger instance.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	/**
	 * Repository Interface to perform operation on database.
	 */
	private final AccountRepository accountRepository;
	/**
	 * Model mapper instance.
	 */
	private final ModelMapper modelMapper;
	

	/**
	 * Creates a new account and saves it on DB.
	 * @param account model with all the information to build an account
	 * @return The created account
	 * @throws AccountException If the account parameter is null or if something goes wrong.
	 *  
	 */
	@Override
	public Account createAccount(final Account account) throws AccountException { 
		if(!isValid(account)) {
			LOGGER.error("Account must not be null");
			throw new AccountException(" Account must not be null");
		}
		Account accountInserted = null;
		try {
			LOGGER.debug("Trying to create new account {}", account);
			accountInserted = accountRepository.save(account);
		} catch (Exception e) {
			LOGGER.error("Something went wrong while trying to create a new account {}", account, e);
			throw new AccountException(e.getMessage(), e);
		}
		return accountInserted;
		
	}

	/**
	 * Modify account data.
	 * @param account model with the account information
	 * @param accountNumber the account number
	 * @return The updated account
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 *  
	 */
	@Override
	public Account updateAccount(final Account account, final Long accountNumber) throws AccountException {
		Account accountUpdated = null;
		if(!isValid(account)) {
			LOGGER.error("Account must not be null");
			throw new AccountException("Account must not be null");
		}
		if(Objects.isNull(accountNumber)) {
			LOGGER.error("Account number must not be null");
			throw new AccountException("Account number must not be null");
		}
		try {
			LOGGER.debug("Trying to update an account {}", accountNumber);
			Account accountFound = accountRepository.findById(accountNumber).orElseThrow(NotFoundException::new);
			account.setAccountNumber(accountFound.getAccountNumber());
			accountUpdated = accountRepository.save(account);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to update an account {}", accountNumber, e);
			throw new AccountException("Something went wrong while trying to update an account", e);
		}
		return accountUpdated;
	}

	/**
	 * Delete an account from database.
	 * @param accountNumber the account number
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 *  
	 */
	@Override
	public void deleteAccount(final Long accountNumber) throws AccountException {
		if (accountNumber==null) {
			LOGGER.error("Account number must not be null");
			throw new AccountException("Account number must not be null");
		}
		try {
			LOGGER.info("Trying to delete the account {}", accountNumber);
			accountRepository.findById(accountNumber).orElseThrow(NotFoundException::new);
			accountRepository.deleteById(accountNumber);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to delete the account {}, {}", accountNumber, e);
			throw new AccountException("Something went wrong while trying to delete the account", e);
		}
	}

	/**
	 * Retrieve account that match with the given account number
	 * @param accountNumber the account number
	 * @return account
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 *  
	 */
	@Override
	public Account getAccountById(Long accountNumber) throws AccountException {
		Account accountRetrieved = null;
		if(accountNumber==null) {
			LOGGER.error("Account number must not be null");
			throw new AccountException("Account number must not be null");
		}
		try {
			LOGGER.debug("Trying to retreieve the account {}", accountNumber);
			accountRetrieved = accountRepository.findById(accountNumber).orElseThrow(NotFoundException::new);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to retrieve the account {}", accountNumber, e);
			throw new AccountException("Something went wrong while trying to retrieve the account", e);
		}
		return accountRetrieved;
	}

	
	/**
	 * Retrieve all the accounts
	 * @return an account List
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 *  
	 */
	@Override
	public List<Account> getAll() throws AccountException {
		List<Account> accounts = null;
		try {
			LOGGER.debug("Trying to retreieve the account ");
			accounts = (List<Account>) accountRepository.findAll();
			if(accounts.isEmpty())
				throw new NotFoundException();
		} catch (NotFoundException e) {
			LOGGER.error("No accounts were found", e);
			throw new AccountException("No accounts were found}", e);
		} catch (DataAccessException e) {
			LOGGER.error("Something went wrong while trying to retrieve the accounts", e);
			throw new AccountException("Something went wrong while trying to retrieve the accounts", e);
		}
		return accounts;
	}


	/**
	 * This method applies partial modifications
	 * @param account model with the account information
	 * @param accountNumber the account number
	 * @return The updated account
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	@Override
	public Account partialUpdateAccount(Account account, Long accountNumber) throws AccountException {
		Account accountUpdated = null;
		if(Objects.isNull(accountNumber)) {
			LOGGER.error("Account number must not be null");
			throw new AccountException("Account number must not be null");
		}
		try {
			LOGGER.debug("Trying a partial update for an account {}", accountNumber);
			Account accountFound = accountRepository.findById(accountNumber).orElseThrow(NotFoundException::new);
			account.setAccountNumber(accountFound.getAccountNumber());
			modelMapper.map(account, accountFound);
			accountUpdated = accountRepository.save(accountFound);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to update an account {}", accountNumber, e);
			throw new AccountException("Something went wrong while trying to update an account", e);
		}
		return accountUpdated;
	}

	

	/**
	 * This method deletes an account by customer ID and account ID
	 * @param customerNumber customer identifier
	 * @param accountId account identifier
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	@Transactional
	@Override
	public void deleteByAccountIdAndCustomerId(Long customerNumber, Long accountNumber) throws AccountException {
		if (Objects.isNull(customerNumber) || Objects.isNull(accountNumber)  ) {
			LOGGER.error("Parameters must not be null");
			throw new AccountException("Parameters must not be null");
		}
		try {
			LOGGER.info("Trying to delete the account {}", accountNumber);
			accountRepository.findById(accountNumber).orElseThrow(NotFoundException::new);
			accountRepository.deleteAccountByAccountIdAndCustomerId(customerNumber, accountNumber);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to delete the account {}, {}", accountNumber, e);
			throw new AccountException("Something went wrong while trying to delete the account", e);
		}
	}

	
	/**
	 * Method that update an account by customer ID and account ID
	 * @param account customer identifier
	 * @param customerNumber customer identifier
	 * @param accountNumber account identifier
	 * @return The updated account
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	@Transactional
	@Override
	public Account updateByAccountIdAndCustomerId(Account account, Long customerNumber, Long accountNumber) throws AccountException {
		if (Objects.isNull(customerNumber) || Objects.isNull(accountNumber)  ) {
			LOGGER.error("Parameters must not be null");
			throw new AccountException("Parameters must not be null");
		}
		try {
			LOGGER.info("Trying to update the account {}", accountNumber);
			Account accountFound = accountRepository.findById(accountNumber).orElseThrow(NotFoundException::new);
			account.setAccountNumber(accountFound.getAccountNumber());
			accountRepository.updateAccountByAccountIdAndCustomerId(account.getAccountType(), account.getBalance(), account.getOverdraftAllowed(), 
																		account.getOverdraftAmount(), customerNumber, accountNumber);
		} catch (Exception e) {
			LOGGER.error("Something went wrong while trying to update the account {}, {}", accountNumber, e);
			throw new AccountException("Something went wrong while trying to update the account", e);
		}
		return account;
	}

	
	/**
	 * Method that get all Accounts by customer ID and account ID
	 * @param customerNumber customer identifier
	 * @return All accounts
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	@Override
	public List<Account> getAllAccountsByCustomerId(Long customerNumber) throws AccountException {
		List<Account> accounts = null;
		if (Objects.isNull(customerNumber)) {
			LOGGER.error("Parameters must not be null");
			throw new AccountException("Parameters must not be null");
		}
		try {
			LOGGER.info("Trying to update the account, with customer number: {}", customerNumber);
			accounts = accountRepository.getAllAccountsByCustomerId(customerNumber);
			if(accounts.isEmpty())
				throw new NotFoundException();
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to update the account, with customer number: {}, {}", customerNumber, e);
			throw new AccountException("Something went wrong while trying to update the account", e);
		}
		return accounts;
	}

	
	/**
	 * Method that gets the account related to the customer and account IDs provided. 
	 * @param customerNumber customer identifier
	 * @param accountNumber account identifier
	 * @return Account object
	 * @throws AccountException if the account parameter is null or if something goes wrong.
	 */
	@Override
	public Account getByAccountIdAndCustomerId(Long customerNumber, Long accountNumber) throws AccountException {
		Account account = null;
		if (Objects.isNull(customerNumber)) {
			LOGGER.error("Customer Number must not be null");
			throw new AccountException("Customer Number must not be null");
		}
		try {
			LOGGER.info("Trying to get an account, with customer number: {}, and account number: {}", customerNumber, accountNumber);
			account = accountRepository.getAccountByAccountIdAndCustomerId(customerNumber, accountNumber);
			if(Objects.isNull(account)) {
				throw new NotFoundException();
			}
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying  to get an account, with customer number: {}, and account number: {}, {}", customerNumber, accountNumber, e);
			throw new AccountException("Something went wrong while trying to update the account", e);
		}
		return account;
	}
	
	
	/**
	 * Validate Account object
	 * @param account
	 * @return a Boolean to indicate if it is valid or not
	 */
	private Boolean isValid(final Account account) {
		if(null == account.getAccountType() ||
				null == account.getBalance() ||
					null == account.getOverdraftAllowed() ||
						null == account.getOverdraftAmount()) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}
	
}
