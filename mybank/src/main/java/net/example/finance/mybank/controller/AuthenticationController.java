package net.example.finance.mybank.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import net.example.finance.mybank.openapi.api.AuthenticationApi;
import net.example.finance.mybank.openapi.model.LoginObjectDto;
import net.example.finance.mybank.openapi.model.UserDetailResponseDto;

/**
 * REST API to manage the authentication
 * .
 * @author jesus.quintero
 *
 */
@RestController
public class AuthenticationController implements AuthenticationApi {

	@Override
	public ResponseEntity<UserDetailResponseDto> login(String xChannelId, String xCountryCode, String xApplCode,
			String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion,
			@Valid LoginObjectDto loginObjectDto) {
		return null;
	}
	
}
