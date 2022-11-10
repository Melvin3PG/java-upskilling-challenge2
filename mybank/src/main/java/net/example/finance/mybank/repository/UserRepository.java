package net.example.finance.mybank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.example.finance.mybank.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
