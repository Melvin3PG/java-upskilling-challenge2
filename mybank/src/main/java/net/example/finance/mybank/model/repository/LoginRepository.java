package net.example.finance.mybank.model.repository;

import com.example.mvnprg.openapi.model.UserObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<UserObject, String> {

    Optional<UserObject> findByUsername(String username);
}
