package com.ub.sonar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
public class PremiumCustomerNudge {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

//	private int day;
//	private int minute;
//	private int hour;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date triggerTime;
	
	@OneToOne
	private EmailData emailData;

	@ElementCollection // 1
	private List<String> recipientEmails = new ArrayList<String>();

}
