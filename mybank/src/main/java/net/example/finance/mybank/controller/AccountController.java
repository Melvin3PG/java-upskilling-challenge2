package net.example.finance.mybank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvnprg.openapi.api.AccountsApi;
import com.example.mvnprg.openapi.model.AccountDetailResponse;
import com.example.mvnprg.openapi.model.AccountListResponse;
import com.example.mvnprg.openapi.model.AccountObject;

import net.example.finance.mybank.service.AccountService;


@RestController
public class AccountController implements AccountsApi {

	@Autowired
	private AccountService accountService;

	@Override
	public ResponseEntity<AccountDetailResponse> createAccount(String xChannelId, String xCountryCode, String xApplCode,
			@Valid AccountObject accountObject, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		AccountDetailResponse response = new AccountDetailResponse();
		try
		{ 
			response.setData(accountService.saveAccount(accountObject));
			return new ResponseEntity<>(response,HttpStatus.CREATED);
		}
		catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
	}

	@Override
	public ResponseEntity<AccountDetailResponse> deleteAccount(Long accountId) {
		AccountDetailResponse response = new AccountDetailResponse();
		try
		{
			response.setData(accountService.deleteAccountById(accountId));
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
	}

	@Override
	public ResponseEntity<AccountDetailResponse> getAccountByAccountNumber(Long accountId, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		AccountDetailResponse response = new AccountDetailResponse();
		try
		{	
			response.setData(accountService.fetchAccountById(accountId));
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
	}

	@Override
	public ResponseEntity<AccountListResponse> getAllAccounts(String xChannelId, String xCountryCode, String xApplCode,
			String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
		AccountListResponse response = new AccountListResponse();	
		try
		{
			response.setData(accountService.fetchAccountList());
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountListNofication(ex, response),HttpStatus.BAD_REQUEST);
        } 
	}

	@Override
	public ResponseEntity<AccountDetailResponse> partialUpdateAccount(Long accountId,
			@Valid AccountObject accountObject) {
		AccountDetailResponse response = new AccountDetailResponse();
		try
		{
			response.setData(accountService.partialUpdateAccount(accountObject, accountId));
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
	}

	@Override
	public ResponseEntity<AccountDetailResponse> updateAccount(Long accountId, @Valid AccountObject accountObject) {
		AccountDetailResponse response = new AccountDetailResponse();
		try
		{
			response.setData(accountService.updateAccount(accountObject, accountId));
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(Exception ex)
		{        
			return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
		} 
	}
	
}
