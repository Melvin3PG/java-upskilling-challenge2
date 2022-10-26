package net.example.finance.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.example.finance.mybank.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {

	Account findByNumber(String number);
}
