package net.example.finance.mybank.controller;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private IAccountService service;

    @GetMapping("/accounts")
    public List<Account> findAll(){
        return service.findAll();
    }
}
