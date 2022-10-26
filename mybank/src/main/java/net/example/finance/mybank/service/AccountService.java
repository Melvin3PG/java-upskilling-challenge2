package net.example.finance.mybank.service;

import java.util.List;

import net.example.finance.mybank.model.entity.Account;

public interface AccountService {
    Account saveAccount(Account account);
    List<Account> fetchAccountList();
    Account updataAccount(Account account, String accNum);
    void deleteDepartmentById(String accNum);
    Account fetchById(String accNum);
}
