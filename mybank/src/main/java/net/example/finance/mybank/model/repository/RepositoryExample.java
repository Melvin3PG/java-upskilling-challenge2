package net.example.finance.mybank.model.repository;

import net.example.finance.mybank.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryExample extends JpaRepository<Account, Long> {

}
