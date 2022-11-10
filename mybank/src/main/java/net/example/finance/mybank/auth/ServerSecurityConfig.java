package net.example.finance.mybank.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class is used to create the configuration for the spring security server.
 * 
 * @author jesus.quintero
 *
 */
@Configuration
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	@Qualifier("userService")
	private UserDetailsService userService;
	
	@Autowired
	private AuthenticationEventPublisher eventPublisher;
	
	
	//Bean encoder to encrypt passwords
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configure the service used to get the user information and set the password encoder
	 * to validate data to authenticate
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userService).passwordEncoder(passwordEncoder())
				.and().authenticationEventPublisher(eventPublisher);
	}
	
	/**
	 * Configure resource access and properties to accept or reject requests
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		        .authorizeRequests()
		        .antMatchers("/customers/**").hasAuthority("USER")
		        .antMatchers("/accounts/**").hasAuthority("USER")
		        .antMatchers("/oauth/token").permitAll()
		        .antMatchers("/login").permitAll()
		        .antMatchers("/h2-console/**").permitAll()
		        .antMatchers("/h2-console/login.do?**").permitAll()
		        .anyRequest().authenticated()
		        .and().headers().frameOptions().sameOrigin()
		        ;
	}

	/**
	 * Bean used to authenticate the user and set the granted authorities if user
	 * is authenticated successfully.
	 */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		
		return super.authenticationManager();
	}

}
