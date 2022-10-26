package net.example.finance.mybank.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.example.finance.mybank.model.dto.AccountDto;
import net.example.finance.mybank.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	AccountService service;
	
	public AccountController(AccountService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
		AccountDto newAccount = service.createAccount(accountDto);
		return ResponseEntity.ok(newAccount);
	}
	
	@PutMapping("{accountId}")
	public ResponseEntity<AccountDto> updateAccount(@PathVariable("accountId") long accountId){
		return ResponseEntity.ok(null);
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("{accountNumber}")
	public ResponseEntity<AccountDto> getAccountByNumber(@PathVariable("accountNumber") String accountId){
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("{accountId}")
	public ResponseEntity<AccountDto> deleteAccount(@PathVariable("accountId") String accountId){
		return ResponseEntity.ok(null);
	}
}
