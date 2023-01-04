package com.ub.sonar.dto;

import java.util.ArrayList;
import java.util.List;

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
public class CustomerDTO {
	private String email;

	private String firstName;
	private String lastName;
	private String preferedLang;
	private Boolean isActive;
	private String timezone;
	private String customerType;
	private String loginSource;

	private List<EmailDataDTO> emailData = new ArrayList<>();
}
