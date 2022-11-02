package net.example.finance.mybank.controller;

import com.example.mvnprg.openapi.api.AccountsApi;
import com.example.mvnprg.openapi.model.AccountDetailResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController  implements AccountsApi {

	@Override
	public ResponseEntity<AccountDetailResponse> getAccountByAccountNumber(Long accountId, String xChannelId,
                                                                           String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
                                                                           String xApiVersion) {
		AccountDetailResponse account = new AccountDetailResponse();
		//TODO: Implement service calls
		return ResponseEntity.ok(account);
	}


	/*@Override
	public ResponseEntity<AccountListResponse> getAllAccounts() {
		AccountDetailResponse account = new AccountDetailResponse();
		AccountObject obj = new AccountObject();
		obj.setAccountNumber(1234L);
		obj.setAccountType(AccountObject.AccountTypeEnum.SAVING);
		obj.setBalance((float) 234.4);
		obj.setOverdraftAmount((float) 234.4);
		obj.setOverdraftAllowed(true);
		account.data(obj);
		//TODO: Implement service calls
		List<AccountDetailResponse> list = new ArrayList<>();
		list.add(account);
		AccountListResponse jio = new AccountListResponse();
		jio.addDataItem(obj);
		return ResponseEntity.ok(jio);
	}*/
	
}
