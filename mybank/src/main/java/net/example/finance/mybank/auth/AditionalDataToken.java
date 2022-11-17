package net.example.finance.mybank.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import net.example.finance.mybank.model.entity.User;
import net.example.finance.mybank.service.UserService;

/**
 * This class is used to add additional data into token.
 * 
 * @author jesus.quintero
 *
 */
@Component
public class AditionalDataToken implements TokenEnhancer {

	@Autowired
	UserService userService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String,Object> data = new HashMap<>();
		
		User user = userService.findByUsername(authentication.getName());
		
		data.put("username", user.getUsername());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(data);
		
		return accessToken;
	}

}

