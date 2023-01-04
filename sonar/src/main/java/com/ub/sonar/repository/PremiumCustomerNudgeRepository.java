package com.ub.sonar.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.ub.sonar.model.PremiumCustomerNudge;

public interface PremiumCustomerNudgeRepository extends CrudRepository<PremiumCustomerNudge, Integer> {

	List<PremiumCustomerNudge> findByTriggerTimeBetween(Date startDate, Date endDate);

	
	PremiumCustomerNudge findByEmailDataId(String emailDataId);
	 

}
