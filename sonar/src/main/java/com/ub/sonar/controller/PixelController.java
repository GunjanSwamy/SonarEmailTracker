package com.ub.sonar.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ub.sonar.dto.PremiumCustomerOptionsDTO;
import com.ub.sonar.facade.PixelFacade;
import com.ub.sonar.facade.PremiumCustomerNudgeFacade;
import com.ub.sonar.model.EmailData;

@RestController
@RequestMapping("/pixel")
public class PixelController {

	@Autowired
	PixelFacade pixelFacade;

	@Autowired
	PremiumCustomerNudgeFacade preCustomerNudgeFacade;

	@RequestMapping(value = "/{emailHeader}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public void updateReadRecepts(@PathVariable String emailHeader, HttpServletResponse response) throws IOException {
 
		byte[] pixelData = pixelFacade.updateEmailDataEntry(emailHeader);
		response.reset();

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0); 

		response.setHeader("Content-Disposition", "inline; filename=\"" + emailHeader + ".png\"");
		response.setHeader("Link", "<https://ec2-18-119-156-157.us-east-2.compute.amazonaws.com:9999/pixel/"
				+ emailHeader + ">; rel=\"canonical\"");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("X-Frame-Options", "");
		response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(pixelData.length));
		response.setHeader("X-nc", "HIT mdw 1");
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("X-Content-Type-Options", "");

		InputStream targetStream = new ByteArrayInputStream(pixelData);

		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(targetStream, response.getOutputStream());

 

	}

	@RequestMapping(value = "/{emailHeaderIdentifier}/{emailUUID}", method = RequestMethod.POST)
	public void createEmail(@PathVariable String emailHeaderIdentifier, @PathVariable String emailUUID, Principal auth,
			@RequestBody PremiumCustomerOptionsDTO requestBody) {

		if (!requestBody.isToggle()) {

			EmailData emailData = pixelFacade.createEmailDataEntry(auth.getName(), emailHeaderIdentifier, emailUUID);
			if (requestBody.isNudge() && emailData != null) {
				preCustomerNudgeFacade.savePremiumCustomerNudgeData(requestBody, emailData);
			}
		}

	}

	@RequestMapping(value = "/test/{emailHeader}", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> updateSmileReadRecepts(@PathVariable String emailHeader)
			throws IOException {
		pixelFacade.updateSmileEmailDataEntry(emailHeader);

		final ByteArrayResource inputStream = new ByteArrayResource(
				Files.readAllBytes(Paths.get("src/main/resources/image/smile.png")));
		return ResponseEntity.status(HttpStatus.OK).contentLength(inputStream.contentLength()).body(inputStream);

	}

	@RequestMapping(value = "test/{emailHeaderIdentifier}/{emailUUID}", method = RequestMethod.POST)
	public ResponseEntity<byte[]> createSmileEmail(@PathVariable String emailHeaderIdentifier,
			@PathVariable String emailUUID, Principal auth) {
		pixelFacade.createEmailDataEntry(auth.getName(), emailHeaderIdentifier, emailUUID);
		return pixelFacade.returnSmileTrackingPixel();

	}

}
