package com.ub.sonar.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class PremiumCustomerOptionsDTO {

	private boolean nudge = false;
	private int day;
	private int minute;
	private int hour;
	private List<String> recipientEmails;
	private boolean toggle = false;
}
