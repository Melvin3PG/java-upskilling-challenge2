package net.example.finance.mybank.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.example.finance.mybank.model.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,String>{
    
}
