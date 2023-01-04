package com.sonar.sonarAdmin.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class CustomerDataDTO {

	private String firstName;
	private String lastName;
	private String customerType;
	private String email;

}
