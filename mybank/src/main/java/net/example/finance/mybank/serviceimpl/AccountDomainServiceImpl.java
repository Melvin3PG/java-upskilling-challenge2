package net.example.finance.mybank.serviceimpl;

import net.example.finance.mybank.model.entity.AccountDomain;
import net.example.finance.mybank.model.repository.AccountDomainRepository;
import net.example.finance.mybank.service.AccountDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountDomainServiceImpl implements AccountDomainService {

    @Autowired
    private AccountDomainRepository accountDomainRepository;

    @Override
    public AccountDomain saveAccountDomain(AccountDomain accountDomain) {
        return accountDomainRepository.save(accountDomain);
    }

    @Override
    public List<AccountDomain> fetchAccountDomainList() {
        return accountDomainRepository.findAll();
    }

    @Override
    public AccountDomain fetchAccountDomainByAccountNumber(Long accountId) {
        return accountDomainRepository.findById(accountId).get();
    }

    @Override
    public void deleteAccountDomainById(Long accountId) {
        accountDomainRepository.deleteById(accountId);
    }

    @Override
    public AccountDomain updateAccountDomainById(Long accountId, AccountDomain accountDomain) {
        AccountDomain accountBD = accountDomainRepository.findById(accountId).get();

        if(Objects.nonNull(accountDomain.getAccountNumber())){ // && !"".equalsIgnoreCase(accountDomain.getAccountNumber())){
            accountBD.setAccountNumber(accountDomain.getAccountNumber());
        }

        if(Objects.nonNull(accountDomain.getAccountType())){
            accountBD.setAccountType(accountDomain.getAccountType());
        }

        if(Objects.nonNull(accountDomain.getBalance())){
            accountBD.setBalance(accountDomain.getBalance());
        }

        if(Objects.nonNull(accountDomain.getOverdrafts())){
            accountBD.setOverdrafts(accountDomain.getOverdrafts());
        }

        if(Objects.nonNull(accountDomain.getOverdraftAmount())){
            accountBD.setOverdraftAmount(accountDomain.getOverdraftAmount());
        }

        return accountDomainRepository.save(accountBD);
    }


}
