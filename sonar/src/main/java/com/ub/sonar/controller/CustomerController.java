package com.ub.sonar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ub.sonar.dto.CustomerDTO;
import com.ub.sonar.facade.CustomerFacade;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerFacade customerFacade;

	/*
	 * @RequestMapping(value = "/register", method = RequestMethod.GET) public
	 * Customer registerCustomer(OAuth2AuthenticationToken oauthToken) {
	 * System.out.println(oauthToken.getPrincipal().getAttributes()); OAuth2User
	 * oAuth2User = oauthToken.getPrincipal(); Customer customerModel =
	 * customerFacade.saveCustomer(oAuth2User); return customerModel; }
	 * 
	 */
	@CrossOrigin()
	@RequestMapping(value = "/{customerEmail}", method = RequestMethod.GET)
	public CustomerDTO getCustomer(@PathVariable String customerEmail, Authentication authentication) {
		String customerEmailFromToken = authentication.getName();
		return customerFacade.getCustomer(customerEmailFromToken);
	}

	
}
