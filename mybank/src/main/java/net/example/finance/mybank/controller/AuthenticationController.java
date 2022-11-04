package net.example.finance.mybank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvnprg.openapi.api.AuthenticationApi;
import com.example.mvnprg.openapi.model.LoginObject;
import com.example.mvnprg.openapi.model.UserDetailResponse;
import com.example.mvnprg.openapi.model.UserObject;

import net.example.finance.mybank.service.UserService;

@RestController
public class AuthenticationController implements AuthenticationApi {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<UserDetailResponse> login(String xChannelId, String xCountryCode, String xApplCode,
            String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion,
            @Valid LoginObject loginObject) {

        UserDetailResponse response = new UserDetailResponse();
        UserObject userObject = userService.postLogin(loginObject);
        
        if(userObject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        response.setData(userObject);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
