package net.example.finance.mybank.controller;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.example.finance.mybank.model.entity.AccountDomain;
import net.example.finance.mybank.service.AccountDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/accounts")
public class AccountDomainController {
    @Autowired
    private AccountDomainService accountDomainService;

    @PostMapping("/accounts")
    public AccountDomain saveAccountDomain(@RequestBody AccountDomain accountDomain){
        return accountDomainService.saveAccountDomain(accountDomain);
    }

    @GetMapping("/accounts")
    public List<AccountDomain> fetchAccountDomainList(){
        return accountDomainService.fetchAccountDomainList();
    }


    @GetMapping("/accounts/{id}")
    public AccountDomain fetchAccountDomainByAccountNumber(@PathVariable("id") Long accountId){
        return  accountDomainService.fetchAccountDomainByAccountNumber(accountId);
    }

    @DeleteMapping("/accounts/{id}")
    public String deleteAccountDomainById(@PathVariable("id") Long accountId){
        accountDomainService.deleteAccountDomainById(accountId);
        return ("AccountDomain Deleted Successfully!!!");
    }

    @PutMapping("/accounts/{id}")
    public AccountDomain updateAccountDomainById(@PathVariable("id") Long accountId, @RequestBody AccountDomain accountDomain){
        return accountDomainService.updateAccountDomainById(accountId, accountDomain);
    }
}
