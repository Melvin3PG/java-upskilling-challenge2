package net.example.finance.mybank.service;

import com.example.mvnprg.openapi.model.LoginObject;
import com.example.mvnprg.openapi.model.UserObject;

public interface ILoginService {
    UserObject authenticate(String username, LoginObject accDetails);
}
