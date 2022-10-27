package net.example.finance.mybank.model.dto;

import lombok.Data;

/**
 * Data transfer for application user account
 * 
 * @author jesus.quintero
 *
 */
@Data
public class UserDto {
	
	/**
	 * Username
	 */
	private String username;
	
	/**
	 * Password
	 */
	private String password;
}
