package net.example.finance.mybank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import net.example.finance.mybank.model.dto.PaginatedDataDto;
import net.example.finance.mybank.openapi.api.AccountsApi;
import net.example.finance.mybank.openapi.model.AccountDetailResponseDto;
import net.example.finance.mybank.openapi.model.AccountListResponseDto;
import net.example.finance.mybank.openapi.model.AccountObjectDto;
import net.example.finance.mybank.service.AccountService;

/**
 * REST API to manage the accounts of customer.
 * 
 * @author jesus.quintero
 *
 */
@RestController
@Log4j2
public class AccountController implements AccountsApi {
	
	AccountService service;
	
	public AccountController(AccountService service) {
		this.service = service;
	}
	
	/**
	 * Return all accounts
	 * 
	 * @return		Response Entity object with all accounts
	 */
	@Override
	public ResponseEntity<AccountListResponseDto> getAllAccounts(String xChannelId, String xCountryCode,
			String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
		log.debug("Start retrieving accounts.");
		
		AccountListResponseDto accountsResponse = null;
		
		PaginatedDataDto<AccountObjectDto> data = service.getAll(0, 10, "accountNumber", "asc");
		if(null != data) {
			accountsResponse = new AccountListResponseDto();
			accountsResponse.setData(data.getData());
		}
				
		return ResponseEntity.ok(accountsResponse);
	}
	
	/**
	 * Return an account filtering by account number
	 * 
	 * @param accountNumber		Account number
	 * 
	 * @return					Response Entity with account object
	 */
	@Override
	public ResponseEntity<AccountDetailResponseDto> getAccountByAccountNumber(Long accountNumber, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		log.debug(String.format("Start retrieving account - Account Number : %s.", accountNumber));
		
		AccountDetailResponseDto accountDetailResponse = null;
		
		AccountObjectDto account = service.getAccountByNumber(accountNumber);
		
		if(null != account) {
			accountDetailResponse = new AccountDetailResponseDto();
			accountDetailResponse.setData(account);
		}
		
		return ResponseEntity.ok(accountDetailResponse);
	}
	
	/**
	 * Create an account
	 * 
	 * @param accountDto	Object model to receive the data from JSON object of client.
	 * 
	 * @return 				Response Entity object with result code, message and object account created
	 */
	@Override
	public ResponseEntity<AccountDetailResponseDto> createAccount(String xChannelId, String xCountryCode,
			String xApplCode, AccountObjectDto accountDto, String xB3Spanid, String xB3Traceid,
			String xUserContext, String xApiVersion) {
		log.debug(String.format("Start creating account : ", accountDto.toString()));
		
		AccountDetailResponseDto accountDetailResponse = null;
		
		AccountObjectDto newAccount = service.createAccount(accountDto);
		if(null != newAccount) {
			accountDetailResponse = new AccountDetailResponseDto();
			accountDetailResponse.data(newAccount);
		}
		
		return ResponseEntity.ok(accountDetailResponse);
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
	@Override
	public ResponseEntity<AccountDetailResponseDto> updateAccount(Long accountId, AccountObjectDto accountDto) {
		log.debug(String.format("Start updating account : ", accountDto.toString()));
		
		AccountDetailResponseDto accountDetailResponse = null;
		
		AccountObjectDto accountUpdated = service.updateAccount(accountId, accountDto);
		
		if(null != accountUpdated) {
			accountDetailResponse = new AccountDetailResponseDto();
			accountDetailResponse.setData(accountUpdated);
		}
		
		return ResponseEntity.ok(accountDetailResponse);
	}
	
	
	@Override
	public ResponseEntity<AccountDetailResponseDto> partialUpdateAccount(Long accountId,
			AccountObjectDto accountObjectDto) {
		log.debug(String.format("Start updating account : ", accountObjectDto.toString()));
		
		AccountDetailResponseDto accountDetailResponse = null;
		
		AccountObjectDto accountUpdated = service.updateAccount(accountId, accountObjectDto);
		
		if(null != accountUpdated) {
			accountDetailResponse = new AccountDetailResponseDto();
			accountDetailResponse.setData(accountUpdated);
		}
		
		return ResponseEntity.ok(accountDetailResponse);
	}
	
	
	/**
	 * Delete an account
	 * 
	 * @param accountId			Account Id
	 * 
	 * @return					Response Entity with result
	 */
	@Override
	public ResponseEntity<AccountDetailResponseDto> deleteAccount(Long accountId) {
		log.debug(String.format("Start delete account - Account ID : %s.", accountId));
		
		AccountDetailResponseDto accountDetailResponse = new AccountDetailResponseDto();
				
		service.deleteAccount(accountId);
		
		accountDetailResponse.setData(null);
		
		return ResponseEntity.ok(accountDetailResponse);
	}
}
