package com.ub.sonar.facade;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ub.sonar.cron.NudgeCronService;
import com.ub.sonar.dto.PremiumCustomerOptionsDTO;
import com.ub.sonar.model.EmailData;
import com.ub.sonar.model.PremiumCustomerNudge;
import com.ub.sonar.service.PremiumCustomerNudgeServiceImpl;

@Component
public class PremiumCustomerNudgeFacade {

	@Autowired
	PremiumCustomerNudgeServiceImpl premiumCustomerNudgeServiceImpl;

	public void runCronJob() {

	}

	@Autowired
	NudgeCronService nudgeCronService;
	
	
	public void savePremiumCustomerNudgeData(PremiumCustomerOptionsDTO premiumCustomerNudgeDTO, EmailData emailData) {

		int days = premiumCustomerNudgeDTO.getDay();
		int minute = premiumCustomerNudgeDTO.getMinute();
		int hour = premiumCustomerNudgeDTO.getHour();
		PremiumCustomerNudge nudge = new PremiumCustomerNudge();
		nudge.setRecipientEmails(premiumCustomerNudgeDTO.getRecipientEmails());
		nudge.setEmailData(emailData);
		Instant instant = Instant.now();
		System.out.println("Current time in UTC is " + instant.toString());
		ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		Calendar cal = GregorianCalendar.from(zdt);

		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + days);
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + hour);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + minute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date d = cal.getTime();
		System.out.println("Set time in UTC is " + d.toString());
		nudge.setTriggerTime(d);
		premiumCustomerNudgeServiceImpl.savePremiumCustomerNudge(nudge);
//		// trail
//		try {
//			nudgeCronService.cronJobSch();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
