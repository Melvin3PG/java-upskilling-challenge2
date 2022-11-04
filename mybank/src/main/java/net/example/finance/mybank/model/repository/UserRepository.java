package net.example.finance.mybank.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.example.finance.mybank.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    public User findByUsername(String username);
}