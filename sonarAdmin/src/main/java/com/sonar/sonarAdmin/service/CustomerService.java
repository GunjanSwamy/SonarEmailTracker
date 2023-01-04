package com.sonar.sonarAdmin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sonar.sonarAdmin.model.Customer;
import com.sonar.sonarAdmin.repository.CustomerRepository;

@Component
public class CustomerService {

	

	@Autowired
	CustomerRepository customerRepo;

	

	public Customer getCustomer(String customerEmail) {
		Optional<Customer> customerOptional = customerRepo.findById(customerEmail);
		if (customerOptional != null) {
			Customer customer = customerOptional.get();
			return customer;
		}
		return null;
	}

}
