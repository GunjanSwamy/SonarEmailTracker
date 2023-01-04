package com.ub.sonar.cron;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ub.sonar.model.EmailData;
import com.ub.sonar.model.PremiumCustomerNudge;
import com.ub.sonar.repository.PremiumCustomerNudgeRepository;
import com.ub.sonar.security.service.EmailService;

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
@Service
public class NudgeCronService {

	Logger log = LoggerFactory.getLogger(NudgeCronService.class);

	@Autowired
	PremiumCustomerNudgeRepository premiumCustomerNudgeRepository;

	@Autowired
	EmailService emailService;

	// cron to run for every minute
	@Scheduled(cron = "0 0/1 * * * ?")
	public void cronJobSch() throws Exception {

		Instant instant = Instant.now();
		System.out.println("Current time in UTC is " + instant.toString());
		ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		Calendar cal = GregorianCalendar.from(zdt);

		// comment these later
		/*
		 * cal.set(Calendar.DAY_OF_MONTH, 25); cal.set(Calendar.MONTH, 10);
		 * cal.set(Calendar.YEAR, 2022);
		 * 
		 * cal.set(Calendar.HOUR_OF_DAY, 13); cal.set(Calendar.MINUTE, 13);
		 * 
		 * // till here
		 */
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date dateFrom = cal.getTime();

		cal = GregorianCalendar.from(zdt);

		// cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.MINUTE)+1);
//		cal.set(Calendar.MONTH, 10);
//		cal.set(Calendar.YEAR, 2022);

		// cal.set(Calendar.HOUR_OF_DAY, );
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + 1);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date dateTo = cal.getTime();
		log.info("Cron executed for entries between " + dateFrom.toString() + " and " + dateTo.toString());
		List<PremiumCustomerNudge> emailNudges = premiumCustomerNudgeRepository.findByTriggerTimeBetween(dateFrom,
				dateTo);
		for (PremiumCustomerNudge emailNudge : emailNudges) {
			System.out.println(
					"Sending nudge reminder to" + emailNudge.getEmailData().getComposedByCustomer().getEmail());
			EmailData emailData = emailNudge.getEmailData();
			if (emailData.getNumberOfViews() == 0) {
				List<String> recipients = emailNudge.getRecipientEmails();

				emailService.sendNudgeReminderMail(emailData.getComposedByCustomer().getEmail(), recipients,
						emailData.getEmailHeaderIdentifier(), emailData.getId());

			}

		}
		// select email data for dates that fall in currnt time
		// for those emailData send emails to the recerpients
		// ggwp
	}

}
