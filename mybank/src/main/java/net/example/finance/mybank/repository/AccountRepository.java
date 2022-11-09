package net.example.finance.mybank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.example.finance.mybank.exceptions.ResourceNotFoundException;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.entity.Customer;

public interface AccountRepository extends JpaRepository<Account,Long> {

	Optional<Account> findByAccountNumber(long number) throws ResourceNotFoundException;
	
	List<Account> findByCustomer(Customer customer);
}
