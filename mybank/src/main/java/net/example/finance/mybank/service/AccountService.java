package net.example.finance.mybank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.AccountObject;

@Service
public interface AccountService {
    AccountObject saveAccount(AccountObject accountObject);
    List<AccountObject> fetchAccountList();
    AccountObject updateAccount(AccountObject accountObject, Long accNum);
    AccountObject deleteAccountById(Long accNum);
    AccountObject fetchAccountById(Long accNum);
    AccountObject partialUpdateAccount(AccountObject accountObject, Long accNum);
}
