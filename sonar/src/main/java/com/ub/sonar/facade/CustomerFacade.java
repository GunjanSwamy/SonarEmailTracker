package com.ub.sonar.facade;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.ub.sonar.dto.CustomerDTO;
import com.ub.sonar.model.Customer;
import com.ub.sonar.model.EmailData;
import com.ub.sonar.service.CustomerServiceImpl;

@Component
public class CustomerFacade {

	@Autowired
	CustomerServiceImpl customerServiceImpl;

	@Autowired
	PixelFacade pixelFacade;
	@Autowired
	public ModelMapper modelMapper;

	public Customer saveCustomer(OAuth2User oauthUser) {
		Customer customerModel = customerServiceImpl.saveCustomer(oauthUser);

		return customerModel;
	}

	public CustomerDTO getCustomer(String customerEmail) {
		Customer customer = customerServiceImpl.getCustomer(customerEmail);
		return convertToBYSUserDto(customer);
	}

	private CustomerDTO convertToBYSUserDto(Customer customer) {
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
//		for (EmailData emailData : customer.getEmailData()) {
//			customerDTO.getEmailData().add(pixelFacade.convertToBookingDto(emailData));
//		}

		return customerDTO;
	}

}
