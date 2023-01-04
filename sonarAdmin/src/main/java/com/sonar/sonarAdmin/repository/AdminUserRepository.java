package com.sonar.sonarAdmin.repository;

import org.springframework.data.repository.CrudRepository;

import com.sonar.sonarAdmin.model.AdminUser;

public interface AdminUserRepository extends CrudRepository<AdminUser, String> {
	//Optional<AdminUser> findByEmail(String email);
}
