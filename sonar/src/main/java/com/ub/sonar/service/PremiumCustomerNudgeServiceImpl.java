package com.ub.sonar.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ub.sonar.model.PremiumCustomerNudge;
import com.ub.sonar.repository.PremiumCustomerNudgeRepository;

@Service
public class PremiumCustomerNudgeServiceImpl {

	@Autowired
	PremiumCustomerNudgeRepository premiumCustomerNudgeRepository;

	public void savePremiumCustomerNudge(PremiumCustomerNudge nudge) {
		premiumCustomerNudgeRepository.save(nudge);
	}


}
