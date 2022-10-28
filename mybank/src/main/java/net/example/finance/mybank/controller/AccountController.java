package net.example.finance.mybank.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.example.finance.mybank.constants.PaginationConstants;
import net.example.finance.mybank.model.dto.AccountDto;
import net.example.finance.mybank.model.dto.PaginatedDataDto;
import net.example.finance.mybank.service.AccountService;

/**
 * REST API to manage the accounts of customer.
 * 
 * @author jesus.quintero
 *
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	private Logger log = LoggerFactory.getLogger(AccountController.class);
	
	AccountService service;
	
	public AccountController(AccountService service) {
		this.service = service;
	}
	
	/**
	 * Create an account
	 * 
	 * @param accountDto	Object model to receive the data from JSON object of client.
	 * 
	 * @return 				Response Entity object with result code, message and object account created
	 */
	@PostMapping
	public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto){
		log.debug(String.format("Start creating account : ", accountDto.toString()));
		
		AccountDto newAccount = service.createAccount(accountDto);
		
		return ResponseEntity.ok(newAccount);
	}
	
	/**
	 * Update an account
	 * 
	 * @param accountNumber	Account number 
	 * 
	 * @param accountDto	Object model to receive the data from JSON object of client.
	 * 
	 * @return				Response Entity object with result code, message and object account created
	 */
	@PutMapping("{accountNumber}")
	public ResponseEntity<AccountDto> updateAccount(@PathVariable("accountNumber") String accountNumber,
									@Valid @RequestBody AccountDto accountDto){
		log.debug(String.format("Start updating account : ", accountDto.toString()));
		
		AccountDto accountUpdated = service.updateAccount(accountNumber, accountDto);
		
		return ResponseEntity.ok(accountUpdated);
	}
	
	/**
	 * Return all accounts
	 * 
	 * @return		Response Entity object with all accounts
	 */
	@GetMapping
	public ResponseEntity<PaginatedDataDto<AccountDto>> getAllAccounts(
				@RequestParam(value = "pageNo", defaultValue = PaginationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
				@RequestParam(value = "pageSize", defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
				@RequestParam(value = "sortBy", defaultValue = PaginationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
				@RequestParam(value = "sortDir", defaultValue = PaginationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
				){
		log.debug("Start retrieving accounts.");
		
		PaginatedDataDto<AccountDto> data = service.getAll(pageNo, pageSize, sortBy, sortDir);
		
		return ResponseEntity.ok(data);
	}
	
	
	/**
	 * Return an account filtering by account number
	 * 
	 * @param accountNumber		Account number
	 * 
	 * @return					Response Entity with account object
	 */
	@GetMapping("{accountNumber}")
	public ResponseEntity<AccountDto> getAccountByNumber(@PathVariable("accountNumber") String accountNumber){
		log.debug(String.format("Start retrieving account - Account Number : %s.", accountNumber));
		
		AccountDto account = service.getAccountByNumber(accountNumber);
		
		return ResponseEntity.ok(account);
	}
	
	/**
	 * Delete an account
	 * 
	 * @param accountId			Account Id
	 * 
	 * @return					Response Entity with result
	 */
	@DeleteMapping("{accountId}")
	public ResponseEntity<String> deleteAccount(@PathVariable("accountId") long accountId){
		log.debug(String.format("Start delete account - Account ID : %s.", accountId));
		
		service.deleteAccount(accountId);
		
		return ResponseEntity.ok("Account deleted successfully!!");
	}
}
