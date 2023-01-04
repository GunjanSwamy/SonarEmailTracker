package com.sonar.sonarAdmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sonar.sonarAdmin.dto.CustomerDataDTO;
import com.sonar.sonarAdmin.facade.AdminUserFacade;

/**
 * Controller used for registration and activation of users
 * 
 * @author LVK
 *
 */
@RestController
@RequestMapping("/admin")
public class AdminUserController {

	Logger log = LoggerFactory.getLogger(AdminUserController.class);

	@Autowired
	AdminUserFacade adminUserFacade;

 
	@RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET)
	public Map<String, List<CustomerDataDTO>> getAllCustomers() {

		List<CustomerDataDTO> customerList = adminUserFacade.getCustomerDataList();
		Map<String, List<CustomerDataDTO>> map = new HashMap<>();
		map.put("response", customerList);
		return map;

	}

	@RequestMapping(value = "/convertCustomer", method = RequestMethod.PUT)
	public List<CustomerDataDTO> getAllCustomers(@RequestBody Map<String, String> bodyRequestMap) {
		System.out.println(bodyRequestMap);
		 adminUserFacade.convertCustomerType(bodyRequestMap);
		return adminUserFacade.getCustomerDataList();

	}

}
