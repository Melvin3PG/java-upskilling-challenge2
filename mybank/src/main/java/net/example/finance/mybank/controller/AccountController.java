package net.example.finance.mybank.controller;

import net.example.finance.mybank.exception.AlreadyExistsException;
import net.example.finance.mybank.exception.NotFoundException;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private IAccountService service;

    @PostMapping(path= "/accounts")
    public ResponseEntity<Object> create(@Valid @RequestBody Account account) {
        try {
            service.create(account);
            return ResponseEntity.ok(account);
        }
        catch (AlreadyExistsException aee){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(aee.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping(path = "/accounts/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Account account) {
        try{
            service.update(id, account);
            return ResponseEntity.ok(account);
        }
        catch (NotFoundException nfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nfe.getMessage());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<Object> findAll(){
        try {
            List<Account> accountList = service.findAll();
            return ResponseEntity.ok(accountList);
        }
        catch(NotFoundException nfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nfe.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            Account account = service.findById(id);
            return ResponseEntity.ok(account);
        }
        catch(NotFoundException nfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nfe.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
