package net.example.finance.mybank.serviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        Account accDB = accountRepository.findById(accountId).get();

        if(accDB == null)
            return null;

        Account account = modelMapper.map(accountObject, Account.class);
  
        account = accountRepository.save(ServiceUtil.updateAccountFields(accDB,account));
		return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject deleteAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).get();

        if(account == null)
            return null;
        
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

        if(account == null)
            return null;

        return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject partialUpdateAccount(AccountObject accountObject, Long accountId) {
        Account accDB = accountRepository.findById(accountId).get();

        if(accDB == null)
            return null;

        Account account = modelMapper.map(accountObject, Account.class);

        account = accountRepository.save(ServiceUtil.updateAccountFields(accDB,account));
		return modelMapper.map(account, AccountObject.class);
    }
}
