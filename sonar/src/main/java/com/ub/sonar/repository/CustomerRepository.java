package com.ub.sonar.repository;

import org.springframework.data.repository.CrudRepository;

import com.ub.sonar.model.Customer;


public interface CustomerRepository extends CrudRepository<Customer, String> {
	//Optional<Customer> findByEmail(String email);
}
