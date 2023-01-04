package com.ub.sonar.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import javax.activation.FileTypeMap;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.ub.sonar.model.Customer;
import com.ub.sonar.model.EmailData;
import com.ub.sonar.repository.CustomerRepository;
import com.ub.sonar.repository.EmailDataRepository;

@Component
public class PixelServiceImpl {

	Logger log = LoggerFactory.getLogger(PixelServiceImpl.class);
 
	private String PIXEL_PATH = "image/pixel.png";
	private String SMILE_PIXEL_PATH = "/smile.png";

	@Autowired
	EmailDataRepository emailDataRepository;
	@Autowired
	CustomerRepository customerDataRepository;
	@Autowired
	CustomerServiceImpl customerService;

	@Autowired
	private Environment env;

	public EmailData createEmailData(String customerId, String emailHeaderIdentifier, String emailUUID) {
		if (emailDataRepository.findById(emailUUID).isPresent()) {
			log.info("Email Data with emailUUID " + emailUUID + " already exists!");
			return null;
		}
		Customer customer = customerService.getCustomer(customerId);

		EmailData emailData = new EmailData();
		emailData.setId(emailUUID);
		emailData.setEmailHeaderIdentifier(emailHeaderIdentifier);
		emailData.setNumberOfViews(0);
		emailData.setComposedByCustomer(customer);
		emailDataRepository.save(emailData);

		List<EmailData> emailDataList = customer.getEmailData();
		emailDataList.add(emailData);
		customer.setEmailData(emailDataList);
		customerDataRepository.save(customer);

		return emailData;

	}

	public void updateEmailDataViewCount(String emailIdentifier) {
		Optional<EmailData> emailDataOptional = emailDataRepository.findById(emailIdentifier);
		if (emailDataOptional.isPresent()) {
			EmailData emailData = emailDataOptional.get();
			int count = emailData.getNumberOfViews();
			emailData.setNumberOfViews(count + 1);
			emailDataRepository.save(emailData);
		}
	}

	public ResponseEntity<byte[]> returnTrackingPixel() {

		try {

			ClassLoader classLoader = getClass().getClassLoader();
			ClassPathResource sourceFile = new ClassPathResource("pixel.png");

			byte[] imageInByte = FileCopyUtils.copyToByteArray(sourceFile.getInputStream());
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);

			return new ResponseEntity<byte[]>(imageInByte, headers, HttpStatus.OK);
		} catch (Exception ex) {
			log.info(ex.toString());
			System.out.println("Pixel not found");
		}
		return null;
	}

	public byte[] returnTrackingPixelContentDisposition() {

		try {

			ClassPathResource sourceFile = new ClassPathResource("pixel.png");
			//ClassPathResource sourceFile = new ClassPathResource("smile.png");

			byte[] imageInByte = FileCopyUtils.copyToByteArray(sourceFile.getInputStream());

			return imageInByte;

			 
		} catch (Exception ex) {
			log.info(ex.toString());
			System.out.println("Pixel not found");
		}
		return null;
	}

	public ResponseEntity<byte[]> returnTestingSmileTrackingPixel() {

		try {
			File fnew = new File(SMILE_PIXEL_PATH);

			return ResponseEntity.ok()
					.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(fnew)))
					.body(Files.readAllBytes(fnew.toPath()));

		} catch (Exception ex) {
			log.info(ex.toString());
			System.out.println("Pixel not found");
		}
		return null;
	}

}
