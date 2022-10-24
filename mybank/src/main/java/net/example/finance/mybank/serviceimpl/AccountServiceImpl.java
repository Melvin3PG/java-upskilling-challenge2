package net.example.finance.mybank.serviceimpl;

import net.example.finance.mybank.dao.AccountDao;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {


    @Autowired
    private AccountDao accountDao;

    @Override
    public Account create(Account account) {
        return accountDao.save(account);
    }

    @Override
    public List<Account> findAll() {
        return (List<Account>)accountDao.findAll();
    }

    @Override
    public Account findById(long id) {
        return accountDao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(long id) {
        accountDao.deleteById(id);
    }
}
