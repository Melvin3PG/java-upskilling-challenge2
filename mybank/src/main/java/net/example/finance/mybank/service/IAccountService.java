package net.example.finance.mybank.service;

import net.example.finance.mybank.model.entity.Account;

import java.util.List;

public interface IAccountService {
    Account create(Account account);
    void update(Long id, Account account);
    List<Account> findAll();
    Account findById(long id);
    void deleteById(long id);
}
