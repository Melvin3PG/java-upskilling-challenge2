package net.example.finance.mybank.model.repository;

import net.example.finance.mybank.model.entity.AccountDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDomainRepository extends JpaRepository<AccountDomain, Long> {
}
