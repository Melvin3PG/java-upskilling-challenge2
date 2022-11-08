package net.example.finance.mybank.controller;

import com.example.mvnprg.openapi.api.AccountsApi;
import com.example.mvnprg.openapi.model.AccountDetailResponse;
import com.example.mvnprg.openapi.model.AccountListResponse;
import com.example.mvnprg.openapi.model.AccountObject;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.serviceimpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class AccountController  implements AccountsApi {
	@Autowired
	AccountServiceImpl accountService;

	@Override
	public ResponseEntity<AccountDetailResponse> getAccountByAccountNumber(Long accountId, String xChannelId,
                                                                           String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
                                                                           String xApiVersion) {
		AccountDetailResponse account = new AccountDetailResponse();
		Account acc = accountService.findById(accountId);
		AccountObject paramAccount = new AccountObject();
		paramAccount.setAccountNumber(Long.parseLong(acc.getNumber()));
		paramAccount.setAccountType(AccountObject.AccountTypeEnum.valueOf(String.valueOf(acc.getType())));
		paramAccount.setBalance(acc.getBalance());
		paramAccount.setOverdraftAmount(acc.getAmount());
		paramAccount.setOverdraftAllowed(acc.isOverdraft());
		account.data(paramAccount);
		return ResponseEntity.ok(account);
	}

	@Override
	public ResponseEntity<AccountListResponse> getAllAccounts(String xChannelId, String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
		// Call service
		return AccountsApi.super.getAllAccounts(xChannelId, xCountryCode, xApplCode, xB3Spanid, xB3Traceid, xUserContext, xApiVersion);
	}

	@Override
	public ResponseEntity<AccountDetailResponse> updateAccount(Long accountId, AccountObject accountObject) {
		AccountDetailResponse account = new AccountDetailResponse();
		Account obj = new Account();
		obj.setNumber(String.valueOf(accountObject.getAccountNumber()));
		//obj.setType(AccountObject.AccountTypeEnum);
		obj.setBalance(accountObject.getBalance());
		obj.setAmount(accountObject.getOverdraftAmount());
		obj.setOverdraft(accountObject.getOverdraftAllowed());
		Account result = accountService.updateAccount(accountId, obj);
		//Conversion
		AccountObject conversion = new AccountObject();
		conversion.setAccountNumber(Long.valueOf(String.valueOf(result.getNumber())));
		account.data(conversion);
		return ResponseEntity.ok(account);
	}

	public ResponseEntity<AccountListResponse> getAllAccounts() {
		AccountDetailResponse account = new AccountDetailResponse();
		AccountObject obj = new AccountObject();
		obj.setAccountNumber(1234L);
		obj.setAccountType(AccountObject.AccountTypeEnum.SAVING);
		obj.setBalance((float) 234.4);
		obj.setOverdraftAmount((float) 234.4);
		obj.setOverdraftAllowed(true);
		account.data(obj);
		List<AccountDetailResponse> list = new ArrayList<>();
		list.add(account);
		AccountListResponse jio = new AccountListResponse();
		jio.addDataItem(obj);
		return ResponseEntity.ok(jio);
	}
	
}
