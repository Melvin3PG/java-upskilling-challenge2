package net.example.finance.mybank.service;

import net.example.finance.mybank.model.entity.AccountDomain;
import net.example.finance.mybank.model.repository.AccountDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDomainServiceImpl implements AccountDomainService {

    @Autowired
    private AccountDomainRepository accountDomainRepository;

    @Override
    public AccountDomain saveAccountDomain(AccountDomain accountDomain) {
        return accountDomainRepository.save(accountDomain);
    }

}
