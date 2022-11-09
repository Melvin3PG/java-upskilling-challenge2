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
		AccountListResponse accountListResponse = new AccountListResponse();
		List<AccountObject> listAccountObject = new ArrayList<>();
		// Call service
		List<Account> allAccounts = accountService.findAll();
		// Convert all entities
		allAccounts.forEach(account -> {
			AccountObject object = new AccountObject();
			object.setAccountNumber(Long.parseLong(account.getNumber()));
			object.setAccountType(AccountObject.AccountTypeEnum.valueOf(String.valueOf(account.getType())));
			object.setBalance(account.getBalance());
			object.setOverdraftAmount(account.getAmount());
			object.setOverdraftAllowed(account.isOverdraft());
			listAccountObject.add(object);
		});
		accountListResponse.data(listAccountObject);
		return ResponseEntity.ok(accountListResponse);
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


}
