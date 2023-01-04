package com.sonar.sonarAdmin.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode

@Entity
public class AdminUser {

	@Id
	String email;
	String password;
	boolean isActive;

	String activationCode;
}
