package net.example.finance.mybank.service;

import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.LoginObject;
import com.example.mvnprg.openapi.model.UserObject;

@Service
public interface UserService {
    UserObject postLogin(LoginObject login);
}
