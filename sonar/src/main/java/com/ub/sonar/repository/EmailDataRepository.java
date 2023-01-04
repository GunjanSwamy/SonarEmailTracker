package com.ub.sonar.repository;

import org.springframework.data.repository.CrudRepository;

import com.ub.sonar.model.EmailData;


public interface EmailDataRepository extends CrudRepository<EmailData, String> {
	//Optional<Customer> findByEmail(String email);
}
