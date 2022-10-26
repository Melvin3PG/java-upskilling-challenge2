package net.example.finance.mybank.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.example.finance.mybank.model.dto.CustomerDto;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@PostMapping
	public ResponseEntity<CustomerDto> createAccount(@RequestBody CustomerDto CustomerDto){
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("{customerId}")
	public ResponseEntity<CustomerDto> updateAccount(@PathVariable("customerId") long customerId){
		return ResponseEntity.ok(null);
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerDto>> getAllAccounts(){
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("{customerId}")
	public ResponseEntity<CustomerDto> getAccountByNumber(@PathVariable("customerId") String customerId){
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("{customerId}")
	public ResponseEntity<CustomerDto> deleteAccount(@PathVariable("customerId") String customerId){
		return ResponseEntity.ok(null);
	}
}
