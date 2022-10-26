package net.example.finance.mybank.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.service.AccountService;

@RestController
public class AccountController {
    @Autowired 
    private AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<Account> saveAccount(@Valid @RequestBody Account account)
    {
        return new ResponseEntity<>(accountService.saveAccount(account),HttpStatus.CREATED);
    }

    @GetMapping("/account")
    public ResponseEntity<List<Account>> fetchAccountList()
    {
        return new ResponseEntity<>(accountService.fetchAccountList(),HttpStatus.OK);
    }

    @GetMapping("/account/{accNum}")
    public ResponseEntity<Account> fetchAccountById(@PathVariable("accNum") String accNum)
    {
        return new ResponseEntity<>(accountService.fetchById(accNum),HttpStatus.OK);
    }

    @PutMapping("/account/{accNum}")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable("accNum") String accNum)
    {
        return new ResponseEntity<>(accountService.updataAccount(account, accNum),HttpStatus.OK);
    }

    @DeleteMapping("/account/{accNum}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable("accNum") String accNum)
    {
        accountService.deleteDepartmentById(accNum);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
