package com.ub.sonar.security.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Email generation class
 * 
 * @author LVK
 *
 */
@Service
public class EmailService {
	
	@Autowired
	private Environment env;

	Logger log = LoggerFactory.getLogger(EmailService.class);

	private JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendNudgeReminderMail(String toEmail, List<String> recipientEmails, String subject, String uuid)
			throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = createEmailBody(recipientEmails,subject, uuid);
		mimeMessage.setContent(htmlMsg, "text/html");

		helper.setText(htmlMsg, true); // Use this or above line.

		mimeMessage.setSubject("SONAR nudge reminder " + subject);

		helper.setTo(toEmail);

		javaMailSender.send(mimeMessage);
	}

	private String createEmailBody(List<String> recipientEmails, String subject,String emailUUID) {
		String recepientStr = new String();
		for (String recipientEmail : recipientEmails) {
			recepientStr += recipientEmail + ",";
		}
		recepientStr = recepientStr.substring(0, recepientStr.length() - 1);
		String API_URL = env.getProperty("sonar.baseURL") + "/nudge/";
		API_URL = API_URL + emailUUID;

		String htmlMsg = "<body style='border:2px solid black'>" + " Your email to " + recepientStr
				+ " with the subject "+subject+" has not been openened.</br> Snooze for " 
				+ "<a href=" + API_URL + "/12 target=\"_blank\" onClick=\"javascript: setTimeout(window.close, 10);\">12Hours</a>, " 
				+ "<a href="+API_URL + "/24>24Hours</a>, " 
				+ "<a href="+API_URL + "/48>48Hours</a>, or "
				+ "<a href="+API_URL + "/72>72Hours</a>, "
				+ "<a href="+API_URL + "/delete>(disable)</a> " + "</body>";

		return htmlMsg;

	}
}