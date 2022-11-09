package net.example.finance.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.example.finance.mybank.model.entity.Customer;

public interface CustomerRepository extends  JpaRepository<Customer, Long> {

}
