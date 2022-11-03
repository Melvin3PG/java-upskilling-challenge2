package net.example.finance.mybank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.example.finance.mybank.exceptions.ResourceNotFoundException;
import net.example.finance.mybank.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {

	Optional<Account> findByAccountNumber(long number) throws ResourceNotFoundException;
}
