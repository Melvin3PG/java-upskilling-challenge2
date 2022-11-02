package net.example.finance.mybank.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvnprg.openapi.api.AuthenticationApi;
import com.example.mvnprg.openapi.model.LoginObject;
import com.example.mvnprg.openapi.model.UserDetailResponse;

@RestController
public class AuthenticationController implements AuthenticationApi {

    @Override
    public ResponseEntity<UserDetailResponse> login(String xChannelId, String xCountryCode, String xApplCode,
            String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion,
            @Valid LoginObject loginObject) {
        // TODO Auto-generated method stub
        return AuthenticationApi.super.login(xChannelId, xCountryCode, xApplCode, xB3Spanid, xB3Traceid, xUserContext,
                xApiVersion, loginObject);
    }
}
