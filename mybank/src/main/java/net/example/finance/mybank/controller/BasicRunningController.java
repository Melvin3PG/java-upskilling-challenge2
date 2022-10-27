package net.example.finance.mybank.controller;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.serviceimpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("")
public class BasicRunningController {
	@Autowired
	AccountServiceImpl accountService;
	
	@GetMapping("/")
	public String runningTest() {
		return "Running JVA_Banking_API ...";
	}

	@GetMapping("/accounts")
	public List<Account> getAllAccounts() {
		return accountService.findAll();
	}

	@GetMapping("/accounts/{id}")
	public Account getById(@PathVariable(value = "id") Long accountNumber) {
		return accountService.findById(accountNumber);
	}
}

