package net.example.finance.mybank.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.example.finance.mybank.exception.AccountException;
import net.example.finance.mybank.exception.AccountNotFoundException;
import net.example.finance.mybank.model.dto.AccountResponseDetails;
import net.example.finance.mybank.model.dto.ResponseDetails;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.enums.ResponseCodes;
import net.example.finance.mybank.service.AccountService;

/**
 * Restful resource for handle account operations.
 * @author aline.perez
 *
 */
@RestController
@RequiredArgsConstructor
public class AccountController {

	/**
	 * Logger instance.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	/**
	 * Account service layer.
	 */
	private final AccountService accountService;
			
	
	/**
	 * Create an account.
	 * @param account model with the account information
	 * @return  Response Entity with code, message and Account
	 */
	@PostMapping("/accounts")
	public ResponseEntity createAccount(@RequestBody final Account account) {
		try {
			LOGGER.info("Trying to create new account");
			final Account accountCreated = accountService.createAccount(account);
			final AccountResponseDetails accountResponse = new AccountResponseDetails(
					ResponseCodes.A01.name(), 
						ResponseCodes.A01.getValue(), 
							Collections.singletonList(accountCreated)); 
			LOGGER.info("Succesful operation");
			return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
		} catch (final AccountException e) {
			LOGGER.error("Error while trying to create an account"+ e.getMessage());
			final ResponseDetails responseDetails = new ResponseDetails(ResponseCodes.A04.name(), ResponseCodes.A04.getValue());
			return ResponseEntity.internalServerError().body(responseDetails);
		}
	}
	
	/**
	 * Update the specified account
	 * @param account model with the account information
	 * @param accountNumber the account number
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@PutMapping("/accounts/{accountNumber}")
	public ResponseEntity<AccountResponseDetails> updateAccount(
			@RequestBody Account account, @PathVariable Long accountNumber) {
		ResponseEntity response;
		try {
			LOGGER.info("Trying to update an account");
			final Account accountUpdated = accountService.updateAccount(account, accountNumber);
			final AccountResponseDetails accountResponse = new AccountResponseDetails(
					ResponseCodes.A02.name(), 
						ResponseCodes.A02.getValue(), 
							Collections.singletonList(accountUpdated)); 
			response = ResponseEntity.ok(accountResponse);
		} catch (final AccountException e) {
			if(e.getCause() instanceof AccountNotFoundException) {
				LOGGER.error("Account with number [{}] was not found while trying to update it", accountNumber, e.getCause());
				final ResponseDetails responseDetails = new ResponseDetails(ResponseCodes.A05.name(), ResponseCodes.A05.getValue());
				response = ResponseEntity.internalServerError().body(responseDetails);
			} else {
				LOGGER.error("Error while trying to update account", e);
				final ResponseDetails responseDetails = new ResponseDetails(ResponseCodes.A04.name(), ResponseCodes.A04.getValue());
				response = ResponseEntity.internalServerError().body(responseDetails);
			}
		}
		return response;
	}
	
	/**
	 * Delete the specified account.
	 * @param accountNumber the account number
	 * @return  Response Entity with code, message and Account
	 */
	@DeleteMapping("/accounts/{accountNumber}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber){
		ResponseEntity response;
		try {
			LOGGER.info("Trying to delete account:" + accountNumber);
			accountService.deleteAccount(accountNumber);
			response = ResponseEntity.noContent().build();
		} catch (final AccountException e) {
			if(e.getCause() instanceof AccountNotFoundException) {
				LOGGER.error("Account with number [{}] was not found while trying to delete it", accountNumber, e.getCause());
				final ResponseDetails responseDetails = new ResponseDetails(ResponseCodes.A05.name(), ResponseCodes.A05.getValue());
				response = ResponseEntity.internalServerError().body(responseDetails);
			} else {
				LOGGER.error("Error while trying to delete an account:" + accountNumber);
				final ResponseDetails responseDetails = new ResponseDetails(ResponseCodes.A04.name(), ResponseCodes.A04.getValue());
				response = ResponseEntity.internalServerError().body(responseDetails);
			}
		}
		return response;
	}
	
	
	/**
	 * Return an account by its account number.
	 * @param accountNumber the account number
	 * @return Response Entity with code, message and Account
	 */
	@GetMapping("/accounts/{accountNumber}")
	public ResponseEntity<AccountResponseDetails> getAccountById(@PathVariable Long accountNumber){
		ResponseEntity response;
		try {
			LOGGER.info("Trying to retreieve account with number:" + accountNumber);
			final Account account = accountService.getAccountById(accountNumber);
			final AccountResponseDetails accountResponse = new AccountResponseDetails(
					ResponseCodes.A03.name(), 
						ResponseCodes.A03.getValue(), 
							Collections.singletonList(account)); 
			response = ResponseEntity.status(HttpStatus.OK).body(accountResponse);
		} catch (Exception e) {
			if(e.getCause() instanceof AccountNotFoundException) {
				LOGGER.error("Account with number [{}] was not found while trying to retrieve it", accountNumber, e.getCause());
				final ResponseDetails responseDetails = new ResponseDetails(ResponseCodes.A05.name(), ResponseCodes.A05.getValue());
				response = ResponseEntity.internalServerError().body(responseDetails);
			} else {
				LOGGER.error("Error while trying to retrieve an account with number:" + accountNumber);
				final ResponseDetails responseDetails = new ResponseDetails(ResponseCodes.A04.name(), ResponseCodes.A04.getValue());
				response = ResponseEntity.internalServerError().body(responseDetails);
			}
		}
		return response;
	}
	
	
	/**
	 * Return all existing accounts.
	 * @return Response Entity with code, message and Account
	 */
	@GetMapping("/accounts")
	public ResponseEntity<AccountResponseDetails> getAllAccounts(){
		ResponseEntity response;
		try {
			LOGGER.info("Trying to retreieve all the accounts" );
			final List<Account> accounts = accountService.getAll();
			final AccountResponseDetails accountResponse = new AccountResponseDetails(
					ResponseCodes.A03.name(), 
						ResponseCodes.A03.getValue(), accounts); 
			response = ResponseEntity.status(HttpStatus.OK).body(accountResponse);
		} catch (Exception e) {
			LOGGER.error("Error while trying to retrieve all the accounts");
			final ResponseDetails responseDetails = new ResponseDetails(ResponseCodes.A04.name(), ResponseCodes.A04.getValue());
			response = ResponseEntity.internalServerError().body(responseDetails);
		}
		return response;
	}
	
}
