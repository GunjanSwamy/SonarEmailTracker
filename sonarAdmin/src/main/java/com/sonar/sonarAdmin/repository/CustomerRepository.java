package com.sonar.sonarAdmin.repository;

import org.springframework.data.repository.CrudRepository;

import com.sonar.sonarAdmin.model.Customer;


public interface CustomerRepository extends CrudRepository<Customer, String> {
	//Optional<Customer> findByEmail(String email);
}
