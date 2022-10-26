package net.example.finance.mybank.serviceimpl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.example.finance.mybank.controller.AccountController;
import net.example.finance.mybank.exception.AccountException;
import net.example.finance.mybank.exception.AccountNotFoundException;
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
	private Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	/**
	 * Repository Interface to perform operation on database.
	 */
	private final AccountRepository accountRepository;
	

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
			Account accountFound = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);
			account.setAccountNumber(accountFound.getAccountNumber());
			accountUpdated = accountRepository.save(account);
		} catch (AccountNotFoundException | DataAccessException e) {
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
			LOGGER.debug("Trying to delete the account {}", accountNumber);
			accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);
			accountRepository.deleteById(accountNumber);
		} catch (AccountNotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to delete the account {}", accountNumber, e);
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
			accountRetrieved = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);
		} catch (AccountNotFoundException | DataAccessException e) {
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
				throw new AccountNotFoundException();
		} catch (AccountNotFoundException e) {
			LOGGER.error("No accounts were found", e);
			throw new AccountException("No accounts were found}", e);
		} catch (DataAccessException e) {
			LOGGER.error("Something went wrong while trying to retrieve the accounts", e);
			throw new AccountException("Something went wrong while trying to retrieve the accounts", e);
		}
		return accounts;
	}


	private Boolean isValid(final Account account) {
		if(null == account.getAccountType() ||
				null == account.getBalance() ||
					null == account.getOverdraft() ||
						null == account.getOverdraftAmount()) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

	
}
