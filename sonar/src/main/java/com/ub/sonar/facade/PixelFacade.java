package com.ub.sonar.facade;

import java.text.SimpleDateFormat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ub.sonar.dto.EmailDataDTO;
import com.ub.sonar.model.EmailData;
import com.ub.sonar.service.PixelServiceImpl;

@Component
public class PixelFacade {

	@Autowired
	PixelServiceImpl pixelService;

	@Autowired
	public ModelMapper modelMapper;

	public EmailData createEmailDataEntry(String customerId, String emailHeaderIdentifier, String emailUUID) {
		return pixelService.createEmailData(customerId, emailHeaderIdentifier, emailUUID);

	}

	public byte[] updateEmailDataEntry(String emailIdetifier) {
		pixelService.updateEmailDataViewCount(emailIdetifier);
		//return returnTrackingPixel();
		return returnTrackingPixeByteArray();
	}
//
//	public ResponseEntity<byte[]> returnTrackingPixel() {
//		return pixelService.returnTrackingPixel();
//	}
//	
	public byte[] returnTrackingPixeByteArray() {
		return pixelService.returnTrackingPixelContentDisposition();
	}

	public ResponseEntity<byte[]> returnSmileTrackingPixel() {
		return pixelService.returnTestingSmileTrackingPixel();
	}

	public ResponseEntity<byte[]> updateSmileEmailDataEntry(String emailIdetifier) {
		pixelService.updateEmailDataViewCount(emailIdetifier);
		return returnSmileTrackingPixel();
	}
	protected EmailDataDTO convertToBookingDto(EmailData emailData) {
		EmailDataDTO emailDataDTO = modelMapper.map(emailData, EmailDataDTO.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

		emailDataDTO.setCreatedDate(dateFormat.format(emailData.getCreatedTimeStamp()));
		emailDataDTO.setCreatedTime(timeFormat.format(emailData.getCreatedTimeStamp()));
		return emailDataDTO;
	}

}
