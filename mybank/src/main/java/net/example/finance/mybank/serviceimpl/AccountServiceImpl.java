package net.example.finance.mybank.serviceimpl;

import net.example.finance.mybank.dao.AccountDao;
import net.example.finance.mybank.exception.AlreadyExistsException;
import net.example.finance.mybank.exception.NotFoundException;
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
    public Account create(Account account) throws AlreadyExistsException {
        if(alreadyExistsByAccountNumber(account.getAccountNumber())) {
            throw new AlreadyExistsException(String.format("The account number: %s . Already exists.", account.getAccountNumber()));
        }

        return accountDao.save(account);
    }

    @Override
    public void update(Long id, Account account) throws NotFoundException {
        Account accountResponse = accountDao.findById(id).orElse(null);

        if(accountResponse == null) {
            throw new NotFoundException("Not found account");
        }

        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setAccountType(account.getAccountType());
        accountResponse.setBalance(account.getBalance());
        accountResponse.setOverdrafts(account.getOverdrafts());
        accountResponse.setOverdraft(account.getOverdraft());

        accountDao.save(accountResponse);
    }

    @Override
    public List<Account> findAll() {
        List<Account> accountList =  (List<Account>)accountDao.findAll();

        if(accountList.stream().count() == 0) {
            throw new NotFoundException(String.format("Not found accounts"));
        }

        return accountList;
    }

    @Override
    public Account findById(long id) throws NotFoundException {
        Account account = accountDao.findById(id).orElse(null);

        if(account == null) {
            throw new NotFoundException(String.format("Not found with the %s:", id));
        }

        return account;
    }

    @Override
    public void deleteById(long id) {
        accountDao.deleteById(id);
    }

    public boolean alreadyExistsByAccountNumber(String accountNumber){
        Account account = accountDao.findByAccountNumber(accountNumber);
        return account != null;
    }
}
