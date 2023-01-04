package com.sonar.sonarAdmin.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Entity
public class EmailData {

	@Id
	String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "email")
	Customer composedByCustomer;
	int numberOfViews;

	String emailHeaderIdentifier;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdTimeStamp = new Date();

//	@PrePersist
//	private void onCreate() {
//		createdTimeStamp = new Date();
//	}
}
