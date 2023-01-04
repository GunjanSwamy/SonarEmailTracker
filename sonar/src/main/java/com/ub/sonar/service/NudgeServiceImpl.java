package com.ub.sonar.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ub.sonar.model.PremiumCustomerNudge;
import com.ub.sonar.repository.PremiumCustomerNudgeRepository;

@Component
public class NudgeServiceImpl {

	@Autowired
	PremiumCustomerNudgeRepository nudgeRepository;

	public String updateNudgeTriggerTime(int hour, String uuid) {

		PremiumCustomerNudge nudgeData = nudgeRepository.findByEmailDataId(uuid);
		if(nudgeData==null) {
			return "Email you are nudging has been disabled";
		}
		Date triggerTime = nudgeData.getTriggerTime();

		Calendar cal = Calendar.getInstance();
		cal.setTime(triggerTime);
		cal.add(Calendar.HOUR_OF_DAY, hour); // adds hour

		nudgeData.setTriggerTime(cal.getTime());
		nudgeRepository.save(nudgeData);
		return "Nudge has been reset, you can close this tab.";

	}

	public void removeNudgeTriggerTime(String uuid) {

		PremiumCustomerNudge nudgeData = nudgeRepository.findByEmailDataId(uuid);

		nudgeRepository.delete(nudgeData);

	}

}
