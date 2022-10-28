package net.example.finance.mybank.service;

import net.example.finance.mybank.model.entity.AccountDomain;

import java.util.List;

public interface AccountDomainService {

    AccountDomain saveAccountDomain(AccountDomain accountDomain);

    List<AccountDomain> fetchAccountDomainList();

    AccountDomain fetchAccountDomainByAccountNumber(Long accountId);

    void deleteAccountDomainById(Long accountId);

    AccountDomain updateAccountDomainById(Long accountId, AccountDomain accountDomain);
}
