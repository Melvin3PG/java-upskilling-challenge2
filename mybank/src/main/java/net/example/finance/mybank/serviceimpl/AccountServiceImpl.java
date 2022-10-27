package net.example.finance.mybank.serviceimpl;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.repository.RepositoryExample;
import net.example.finance.mybank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private RepositoryExample accountRepository;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        Account tmpAccount;
        tmpAccount = accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Account not available for Id : "+id));
        return tmpAccount;
    }

    @Override
    public Account createAccount(String accountNumber, Account.AccountType accountType, float balance, boolean isOverdraft, float overdraftAmount) {
        Account account = new Account();
        account.setNumber(accountNumber);
        account.setType(accountType);
        account.setBalance(balance);
        account.setOverdraft(isOverdraft);
        account.setAmount(overdraftAmount);
        return accountRepository.save(account);
    }
}
