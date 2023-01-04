package com.ub.sonar.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
public class Customer {
//Logger log = LoggerFactory.getLogger(Customer.class);

	@Id
	private String email;

	private String firstName;
	private String lastName;
	private String preferedLang;
	private Boolean isActive;
	private String timezone;

	private CustomerType customerType;
	private String loginSource;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "composedByCustomer")
	private List<EmailData> emailData = new ArrayList<>();

}
