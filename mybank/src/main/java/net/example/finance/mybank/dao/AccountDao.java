package net.example.finance.mybank.dao;

import net.example.finance.mybank.model.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<Account, Long> {
    public Account findByAccountNumber(String accountNumber);
}
