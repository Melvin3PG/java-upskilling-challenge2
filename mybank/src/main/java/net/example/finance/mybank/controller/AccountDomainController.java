package net.example.finance.mybank.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.example.finance.mybank.model.entity.AccountDomain;
import net.example.finance.mybank.service.AccountDomainService;
import net.example.finance.mybank.service.AccountDomainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountDomainController {
    @Autowired
    private AccountDomainService accountDomainService;

    @PostMapping("/accounts")
    public AccountDomain saveAccountDomain(@RequestBody AccountDomain accountDomain){
        return accountDomainService.saveAccountDomain(accountDomain);
    }
}
