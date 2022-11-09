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
		AccountObject paramAccount = convertAccountToResponseObject(acc);
		account.data(paramAccount);
		return ResponseEntity.ok(account);
	}

	@Override
	public ResponseEntity<AccountListResponse> getAllAccounts(String xChannelId, String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
		AccountListResponse accountListResponse = new AccountListResponse();
		List<AccountObject> listAccountObject = new ArrayList<>();
		List<Account> allAccounts = accountService.findAll();
		// Convert all entities
		allAccounts.forEach(account -> {
			AccountObject object = convertAccountToResponseObject(account);
			listAccountObject.add(object);
		});
		accountListResponse.data(listAccountObject);
		return ResponseEntity.ok(accountListResponse);
	}

	@Override
	public ResponseEntity<AccountDetailResponse> updateAccount(Long accountId, AccountObject accountObject) {
		AccountDetailResponse account = new AccountDetailResponse();
		Account result = accountService.updateAccount(accountId, convertResponseObjectToAccount(accountObject));
		AccountObject conversion = convertAccountToResponseObject(result);
		account.data(conversion);
		return ResponseEntity.ok(account);
	}

	@Override
	public ResponseEntity<AccountDetailResponse> createAccount(String xChannelId, String xCountryCode, String xApplCode, AccountObject accountObject, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
		AccountDetailResponse account = new AccountDetailResponse();
		Account result = accountService.createAccountOpenAPI(accountObject.getAccountNumber().toString(), accountObject.getAccountType(), accountObject.getBalance(), accountObject.getOverdraftAllowed(), accountObject.getOverdraftAmount());
		AccountObject conversion = convertAccountToResponseObject(result);
		account.data(conversion);
		return ResponseEntity.ok(account);
	}

	@Override
	public ResponseEntity<AccountDetailResponse> deleteAccount(Long accountId) {
		AccountDetailResponse response = new AccountDetailResponse();
		AccountObject object = new AccountObject();
		try{
			accountService.deleteAccount(accountId);
		}
		catch (Exception ex){
			object.setAccountNumber(123L);
			response.data(object);
		}
		return ResponseEntity.ok(response);
	}

	private AccountObject convertAccountToResponseObject(Account account){
		AccountObject object = new AccountObject();
		object.setAccountNumber(Long.parseLong(account.getNumber()));
		object.setAccountType(AccountObject.AccountTypeEnum.valueOf(String.valueOf(account.getType())));
		object.setBalance(account.getBalance());
		object.setOverdraftAmount(account.getAmount());
		object.setOverdraftAllowed(account.isOverdraft());
		return object;
	}

	private Account convertResponseObjectToAccount(AccountObject obj){
		Account account = new Account();
		account.setNumber(obj.getAccountNumber().toString());
		account.setTypeAccount(obj.getAccountType());
		account.setBalance(account.getBalance());
		account.setAmount(account.getAmount());
		account.setOverdraft(obj.getOverdraftAllowed());
		return account;
	}
}
