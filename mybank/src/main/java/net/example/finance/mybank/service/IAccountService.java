package net.example.finance.mybank.service;

import com.example.mvnprg.openapi.model.AccountObject;
import net.example.finance.mybank.model.entity.Account;
import java.util.List;

public interface IAccountService {
    List<Account> findAll();

    Account findById(Long id);

    Account createAccount(String accountNumber, Account.AccountType accountType, float balance, boolean isOverdraft, float overdraftAmount);

    Account updateAccount(Long id, Account accDetails);

    void deleteAccount(Long id);

    /*OpenAPI*/
    Account createAccountOpenAPI(String accountNumber, AccountObject.AccountTypeEnum accountType, float balance, boolean isOverdraft, float overdraftAmount);
}
