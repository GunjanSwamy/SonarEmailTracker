package com.ub.sonar.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ub.sonar.model.Customer;

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
public class EmailDataDTO {

	String emailDataId;

	Customer composedByCustomerId;
	int numberOfViews;
	String emailHeaderIdentifier;
//	private Date createdTimeStamp;
	private String createdTime;
	private String createdDate;

}
