package net.example.finance.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.example.finance.mybank.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
