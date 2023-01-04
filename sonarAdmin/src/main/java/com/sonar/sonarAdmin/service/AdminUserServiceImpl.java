package com.sonar.sonarAdmin.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sonar.sonarAdmin.model.AdminUser;
import com.sonar.sonarAdmin.model.Customer;
import com.sonar.sonarAdmin.model.CustomerType;
import com.sonar.sonarAdmin.repository.AdminUserRepository;
import com.sonar.sonarAdmin.repository.CustomerRepository;

@Component
public class AdminUserServiceImpl {

	Logger log = LoggerFactory.getLogger(AdminUserServiceImpl.class);

	@Autowired
	EmailService emailService;

	@Autowired
	CommonUtils commonUtils;

	@Autowired
	AdminUserRepository adminUserRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	CustomerService customerService;

	@Transactional
	public void createAdminUser(AdminUser user) {

		String accountActivationCode = commonUtils.generateRandomAlphaNumericValue();
		user.setActivationCode(accountActivationCode);
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			adminUserRepository.save(user);
			log.info("New user created with email " + user.getEmail());
			emailService.sendActivationMail(user.getEmail(), "Sonar Admin : Authentication", accountActivationCode);

			log.info("Sending authentication mail to " + user.getEmail());

		} catch (Exception e) {

		}

	}

	public String activateAdminUser(String userEmail, String activationCode) throws Exception {
		AdminUser user = getCustomer(userEmail);
		if (user == null) {
			return "User with email " + userEmail + " does not exist";
		}
		if (!user.isActive() && user.getActivationCode().equals(activationCode)) {
			user.setActive(true);
			user.setActivationCode(null);
			adminUserRepository.save(user);
			log.info(userEmail + " activated");
			return userEmail + " successfully validated";
		} else {
			return "User " + userEmail + " already validated";
		}
	}

	public AdminUser getCustomer(String customerEmail) {
		try {
			Optional<AdminUser> customerOptional = adminUserRepository.findById(customerEmail);
			if (customerOptional != null) {
				AdminUser customer = customerOptional.get();
				return customer;
			}
		} catch (Exception e) {
			return null;
		}
		return null;

	}

	public Iterable<Customer> getAllCustomers() {
		Iterable<Customer> customers = customerRepository.findAll();
		return customers;
	}

	public void convertCustomerType(String customerEmail, CustomerType newCustomerType) {
		Customer customer = customerService.getCustomer(customerEmail);
		customer.setCustomerType(newCustomerType);
		customerRepository.save(customer);
	}

}
