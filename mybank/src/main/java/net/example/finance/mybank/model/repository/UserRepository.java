package net.example.finance.mybank.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.example.finance.mybank.model.entity.UserData;

/**
 * Repository interface to handle DB operations.
 * @author aline.perez
 *
 */
@Repository
public interface UserRepository extends CrudRepository<UserData, Long> {

	/**
	 * Find User by its user name and password
	 * @param username name created by user
	 * @param password field needed to authenticate user
	 * @return UserData object
	 */
	Optional<UserData> findByUserNameAndPassword(String username, String password);
}
