package com.ub.sonar.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.ub.sonar.model.Customer;
import com.ub.sonar.repository.CustomerRepository;

@Component
public class CustomerServiceImpl {

	String EMAIL = "email";
	String FIRST_NAME = "given_name";
	String LAST_NAME = "family_name";
	String LANG = "locale";

	@Autowired
	CustomerRepository customerRepo;

	public Customer saveCustomer(OAuth2User oauthUser) {
		// customerModel
		Customer customer = new Customer();

		Map<String, Object> attributes = oauthUser.getAttributes();

		// customer.
		customer.setEmail(String.valueOf(attributes.get(EMAIL)));
		customer.setFirstName(String.valueOf(attributes.get(FIRST_NAME)));
		customer.setLastName(String.valueOf(attributes.get(LAST_NAME)));
		customer.setPreferedLang(String.valueOf(attributes.get(LANG)));

		customerRepo.save(customer);
		return customer;
	}

	public Customer getCustomer(String customerEmail) {
		Optional<Customer> customerOptional = customerRepo.findById(customerEmail);
		if (customerOptional != null) {
			Customer customer = customerOptional.get();
			return customer;
		}
		return null;
	}

}
