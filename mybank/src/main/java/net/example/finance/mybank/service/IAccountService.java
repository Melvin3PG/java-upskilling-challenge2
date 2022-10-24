package net.example.finance.mybank.service;

import net.example.finance.mybank.model.entity.Account;

import java.util.List;

public interface IAccountService {
    public Account create(Account account);
    public List<Account> findAll();
    public Account findById(long id);
    public void deleteById(long id);
}
