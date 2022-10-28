package net.example.finance.mybank.service;

import net.example.finance.mybank.model.entity.Account;
import java.util.List;

public interface IAccountService {
    List<Account> findAll();

    Account findById(Long id);

    Account createAccount(String accountNumber, Account.AccountType accountType, float balance, boolean isOverdraft, float overdraftAmount);

    Account updateAccount(Long id, Account accDetails);

    void deleteAccount(Long id);
}
