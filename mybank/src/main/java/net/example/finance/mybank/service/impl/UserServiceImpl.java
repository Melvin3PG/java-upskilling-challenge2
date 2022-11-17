package net.example.finance.mybank.service.impl;

import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.example.finance.mybank.exceptions.ResourceNotFoundException;
import net.example.finance.mybank.model.entity.Customer;
import net.example.finance.mybank.model.entity.User;
import net.example.finance.mybank.repository.UserRepository;
import net.example.finance.mybank.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService,UserDetailsService   {

	UserRepository repository;
	
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Entro a validar usuario : "+ username);
		
		User user = repository.findByUsername(username)
								.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	    
		
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(user.getUsername(), 
				user.getPassword(), true, true, true,true,  
				Arrays.asList(authority));
		
		System.out.println(securityUser);
		
		return securityUser;
	}

	@Override
	public User findByUsername(String username) {
		//retrieve customer by customer number
		User user = repository.findByUsername(username)
							.orElseThrow(() -> new ResourceNotFoundException("Customer", "customer number", String.valueOf(username)));
		
		return user;
	}

}
