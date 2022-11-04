package net.example.finance.mybank.service;

import net.example.finance.mybank.exception.UserException;
import net.example.finance.mybank.model.entity.UserData;

/**
 * User service interface
 * @author aline.perez
 *
 */
public interface UserService {

	/**
	 * Retrieve user information
	 * @param username the user name
	 * @param password the user's password	 
	 * @return UserData object and data
	 * @throws UserException one of the parameters is null or if something goes wrong.
	 *  
	 */
	public UserData getUserByUserNameAndPassword(String username, String password) throws UserException;
	
	
}
