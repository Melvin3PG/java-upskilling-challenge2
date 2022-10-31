package net.example.finance.mybank.controller;

import org.springframework.http.ResponseEntity;

import com.example.mvnprg.openapi.api.AccountsApi;
import com.example.mvnprg.openapi.model.AccountDetailResponse;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController implements AccountsApi {

	@Override
	public ResponseEntity<AccountDetailResponse> getAccountByAccountNumber(Long accountId, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		AccountDetailResponse account = new AccountDetailResponse();
		return ResponseEntity.ok(account);
	}
	
}
