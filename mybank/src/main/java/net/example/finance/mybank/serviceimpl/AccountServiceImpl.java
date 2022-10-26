package net.example.finance.mybank.serviceimpl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.repository.AccountRepository;
import net.example.finance.mybank.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> fetchAccountList() {
        return (List<Account>) accountRepository.findAll();
    }

    @Override
    public Account updataAccount(Account account, String accNum) {
        Account accDB
            = accountRepository.findById(accNum)
                  .get();
  
        if (Objects.nonNull(account.getAccountNum())
            && !"".equalsIgnoreCase(
                account.getAccountNum())) {
            accDB.setAccountNum(
                account.getAccountNum());
        }
  
        if (Objects.nonNull(
                account.getAccountType())
            && !"".equalsIgnoreCase(
                account.getAccountType())) {
            accDB.setAccountType(
                account.getAccountType());
        }
  
        if (Objects.nonNull(account.getBalance())
            && accDB.getBalance() != 
                account.getBalance()
                && account.getBalance() != 0) {
            accDB.setBalance(
                account.getBalance());
        }

        if (Objects.nonNull(account.getOverdraftAmt())
            && accDB.getOverdraftAmt() !=
                account.getOverdraftAmt()
                && account.getOverdraftAmt() != 0) {
            accDB.setOverdraftAmt(
                account.getOverdraftAmt());
        }

        if (!(accDB.getOverdrafts().equals(account.getOverdrafts()))) 
        {
            accDB.setOverdrafts(
                account.getOverdrafts());
        }
  
        return accountRepository.save(accDB);
    }

    @Override
    public void deleteDepartmentById(String accNum) {
        accountRepository.deleteById(accNum);
        
    }

    @Override
    public Account fetchById(String accNum) {
        return accountRepository.findById(accNum).get();
    }
    
}
