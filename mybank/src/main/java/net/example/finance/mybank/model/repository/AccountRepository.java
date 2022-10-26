package net.example.finance.mybank.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.example.finance.mybank.model.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>{
	
	
}
