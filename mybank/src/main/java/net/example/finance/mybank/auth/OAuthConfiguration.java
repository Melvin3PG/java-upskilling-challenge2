package net.example.finance.mybank.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * This class is used to enable the authorization server and the 
 * authentication and its settings.
 * 
 * @author jesus.quintero
 *
 */
@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter 
{
	
	   private final AuthenticationManager authenticationManager;

	   private final BCryptPasswordEncoder passwordEncoder;

	   /**
	    * Client ID valid to accept requests from clients
	    */
	   @Value("${jwt.clientId:mybank}")
	   private String clientId;

	   /**
	    * Secret key used to generate a valid token.
	    */
	   @Value("${jwt.client-secret:secret}")
	   private String clientSecret;	

	   @Value("${jwt.signing-key:123}")
	   private String jwtSigningKey;

	   /**
	    * Duration time for the token
	    */
	   @Value("${jwt.accessTokenValidititySeconds:43200}") // 12 hours
	   private int accessTokenValiditySeconds;

	   /**
	    * Valid granted types in header request
	    */
	   @Value("${jwt.authorizedGrantTypes:password,authorization_code,refresh_token}")
	   private String[] authorizedGrantTypes;

	   public OAuthConfiguration(AuthenticationManager authenticationManager, 
			   BCryptPasswordEncoder passwordEncoder) {
	       this.authenticationManager = authenticationManager;
	       this.passwordEncoder = passwordEncoder;
	   }
	   
	   /**
	    * Configure the /oauth/token endpoint to generate the token, 
	    * Set the default settings required to have the endpoint to validate the user and token.
	    */
	   @Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
		}

	   /**
	    * Configure valid clients and properties to comsume the API.
	    */
	   @Override
	   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		   clients.inMemory()
	               .withClient(clientId)
	               .secret(passwordEncoder.encode(clientSecret))
	               .accessTokenValiditySeconds(accessTokenValiditySeconds)
	               .authorizedGrantTypes(authorizedGrantTypes)
	               .scopes("read", "write");
	              // .resourceIds("api");
	   }
	   

	   
	   /**
	    *
	    * Configure services to validate the user and token store and validation and other customizations 
	    * to grant access to APIs.
	    **/
	   @Override
	   public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception  {
		   TokenEnhancerChain tokenEnhancer = new TokenEnhancerChain();
			//tokenEnhancer.setTokenEnhancers(Arrays.asList(aditionalDataToken, getAccessTokenConverter()));
			
	       endpoints
	       		   //.pathMapping("/oauth/token", "/authentication/login")
	       			.authenticationManager(authenticationManager)
	       			.tokenStore(getTokenStore())
	               //.userDetailsService(userService)
	               
	       		   .accessTokenConverter(accessTokenConverter());
	   }
	   
	   /**
	    * Bean used to store/read access token with enhancer for JWT tokens
	    * @return
	    */
	   @Bean
		public JwtTokenStore getTokenStore() {
			return new JwtTokenStore(accessTokenConverter());
		}

	   /**
	    * Utility bean to translate the jwt token with values and oauth information
	    * @return
	    */
	   @Bean
	   JwtAccessTokenConverter accessTokenConverter() {
	       JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	       //converter.setSigningKey(jwtSigningKey);
	       return converter;
	   }

}
