package com.sonar.sonarAdmin.service;

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

	public void sendActivationMail(String toEmail, String subject, String activationId) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = createEmailBody(toEmail, activationId);
		mimeMessage.setContent(htmlMsg, "text/html");

		helper.setText(htmlMsg, true); // Use this or above line.

		mimeMessage.setSubject("SONAR Admin User " + subject);

		helper.setTo(toEmail);

		javaMailSender.send(mimeMessage);

	}


	private String createEmailBody(String email, String activationId) {

		String API_URL = env.getProperty("sonar.baseURL") + "/activateUser/" + email + "/" + activationId;
		//API_URL = API_URL + emailUUID;

		String htmlMsg = "<body style='border:2px solid black'>" + "Please click link to activate your admin account" 
				+ "<a href=" + API_URL + "> here </a> " 
				+ "</body>";

		return htmlMsg;

	}

}