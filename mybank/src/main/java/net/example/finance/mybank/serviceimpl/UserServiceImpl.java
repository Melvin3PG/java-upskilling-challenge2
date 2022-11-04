package net.example.finance.mybank.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.example.finance.mybank.exception.NotFoundException;
import net.example.finance.mybank.exception.UserException;
import net.example.finance.mybank.model.entity.UserData;
import net.example.finance.mybank.model.repository.UserRepository;
import net.example.finance.mybank.service.UserService;

/**
 * Account Service implementation class
 * @author aline.perez
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	/**
	 * Logger instance.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	/**
	 * User repository interface to perform operations on database.
	 */
	private final UserRepository userRepository;
	
	/**
	 * Retrieve user information
	 * @param username the user name
	 * @param password the user's password	 
	 * @return UserData object and data
	 * @throws UserException one of the parameters is null or if something goes wrong.
	 *  
	 */
	@Override
	public UserData getUserByUserNameAndPassword(String username, String password) throws UserException {
		UserData user = null;
		if(username==null || password==null) {
			LOGGER.error("Username or password must not be null");
			throw new UserException("Username or password must not be null");
		}
		try {
			LOGGER.debug("Trying to retrieve the user {}", username);
			user = userRepository.findByUserNameAndPassword(username, password).orElseThrow(NotFoundException::new);
			
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to retrieve the user {}", username, e);
			throw new UserException("Something went wrong while trying to retrieve an user", e);
		}
		return user;
	}

}
