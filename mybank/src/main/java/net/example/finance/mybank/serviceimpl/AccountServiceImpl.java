package net.example.finance.mybank.serviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.AccountObject;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.repository.AccountRepository;
import net.example.finance.mybank.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public AccountObject saveAccount(AccountObject accountObject) {
        Account account = accountRepository.save(modelMapper.map(accountObject, Account.class));
		return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public List<AccountObject> fetchAccountList() {
        List<Account> list = accountRepository.findAll();
        return Arrays.asList(modelMapper.map(list, AccountObject[].class));
    }

    @Override
    public AccountObject updateAccount(AccountObject accountObject, Long accountId) {
        Account accDB
            = accountRepository.findById(accountId)
                  .get();
        Account account = modelMapper.map(accountObject, Account.class);
  
        account = accountRepository.save(updateFields(accDB,account));
		return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject deleteAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        
        if (Objects.nonNull(account.getAccountNumber())
            && account.getAccountNumber().equals(accountId)) 
        {
            accountRepository.deleteById(accountId);
        }  
        
        return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject fetchAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject partialUpdateAccount(AccountObject accountObject, Long accountId) {
        Account accDB
            = accountRepository.findById(accountId)
                  .get();
        Account account = modelMapper.map(accountObject, Account.class);

        account = accountRepository.save(updateFields(accDB,account));
		return modelMapper.map(account, AccountObject.class);
    }

    private Account updateFields(Account accDB, Account account)
    {
        if (Objects.nonNull(account.getAccountNumber())
            && accDB.getAccountNumber() != 
            account.getAccountNumber()
            && account.getAccountNumber() != 0) {
            accDB.setAccountNumber(
                account.getAccountNumber());
        }
  
        if (Objects.nonNull(
                account.getAccountType())
            && !"".equals(
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

        if (Objects.nonNull(account.getOverdraftAmount())
            && accDB.getOverdraftAmount() !=
                account.getOverdraftAmount()
                && account.getOverdraftAmount() != 0) {
            accDB.setOverdraftAmount(
                account.getOverdraftAmount());
        }

        if (!(accDB.getOverdraftAllowed().equals(account.getOverdraftAllowed()))) 
        {
            accDB.setOverdraftAllowed(
                account.getOverdraftAllowed());
        }

        return accDB;
    }
    
}
