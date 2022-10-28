package net.example.finance.mybank.controller;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.serviceimpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class BasicRunningController {
	@Autowired
	AccountServiceImpl accountService;
	
	@GetMapping("/")
	public String runningTest() {
		return "Running JVA_Banking_API ...";
	}

	@GetMapping("/all")
	public List<Account> getAllAccounts() {
		return accountService.findAll();
	}

	@GetMapping("/{id}")
	public Account getById(@PathVariable(value = "id") Long id) {
		return accountService.findById(id);
	}

	@PostMapping("/")
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account.getNumber(), account.getType(), account.getBalance(), account.isOverdraft(), account.getAmount());
	}

	@PutMapping("/{id}")
	public Account getById(@PathVariable(value = "id") Long id, @RequestBody Account account) {
		return accountService.updateAccount(id, account);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable(value = "id") Long id)
	{
		accountService.deleteAccount(id);
	}
	}

