package net.example.finance.mybank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.AccountObject;

@Service
public interface AccountService {
    AccountObject saveAccount(AccountObject account);
    List<AccountObject> fetchAccountList();
    AccountObject updateAccount(AccountObject account, Long accNum);
    AccountObject deleteAccountById(Long accNum);
    AccountObject fetchById(Long accNum);
    AccountObject partialUpdateAccount(AccountObject account, Long accNum);
}
