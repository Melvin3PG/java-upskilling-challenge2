package net.example.finance.mybank.service;

import net.example.finance.mybank.model.entity.Account;
import java.util.List;

public interface IAccountService {
    List<Account> findAll();

    Account findById(Long id);
}
