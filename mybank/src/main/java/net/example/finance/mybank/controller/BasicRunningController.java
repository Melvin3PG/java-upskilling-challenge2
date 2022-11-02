package net.example.finance.mybank.controller;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.serviceimpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myaccounts")
public class BasicRunningController {
	@Autowired
	AccountServiceImpl accountService;
	
	@GetMapping("/")
	public String runningTest() {
		return "Running JVA_Banking_API ...";
	}

	@GetMapping("/all")
	public ResponseEntity<List<Account>> getAllAccounts() {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Account> getById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.findById(id));
	}

	@PostMapping("/")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.createAccount(account.getNumber(), account.getType(), account.getBalance(), account.isOverdraft(), account.getAmount()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Account> getById(@PathVariable(value = "id") Long id, @RequestBody Account account) {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(id, account));
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable(value = "id") Long id)
	{
		accountService.deleteAccount(id);
	}
	}

