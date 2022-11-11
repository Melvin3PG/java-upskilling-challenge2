package net.example.finance.mybank.controller;

import com.example.mvnprg.openapi.api.AuthenticationApi;
import com.example.mvnprg.openapi.model.*;
import net.example.finance.mybank.serviceimpl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements AuthenticationApi {
	@Autowired
	LoginServiceImpl loginService;

	@Override
	public ResponseEntity<UserDetailResponse> login(String xChannelId, String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion, LoginObject loginObject) {
		UserDetailResponse userResponse = new UserDetailResponse();
		UserObject result = loginService.authenticate(loginObject.getUsername(), loginObject);
		userResponse.data(result);
		return ResponseEntity.ok(userResponse);
	}
}
