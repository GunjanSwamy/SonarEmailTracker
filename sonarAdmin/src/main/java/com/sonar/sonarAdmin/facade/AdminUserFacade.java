package com.sonar.sonarAdmin.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sonar.sonarAdmin.dto.CustomerDataDTO;
import com.sonar.sonarAdmin.model.AdminUser;
import com.sonar.sonarAdmin.model.Customer;
import com.sonar.sonarAdmin.model.CustomerType;
import com.sonar.sonarAdmin.service.AdminUserServiceImpl;

@Component
public class AdminUserFacade {

	@Autowired
	AdminUserServiceImpl adminUserService;

	@Autowired
	public ModelMapper modelMapper;

	public void createAdminUser(AdminUser adminUser) {
		
		adminUserService.createAdminUser(adminUser);
	}

	public String activateAdminUser(String activationcode, String userEmail) throws Exception {
		return adminUserService.activateAdminUser(userEmail, activationcode);
	}

	public AdminUser getAdminUser(String userEmail) {
		AdminUser bysUser = adminUserService.getCustomer(userEmail);
		return bysUser;
	}

	public List<CustomerDataDTO> getCustomerDataList() {
		Iterable<Customer> customers = adminUserService.getAllCustomers();
		List<CustomerDataDTO> customerDataDTOList = new ArrayList<>();

		for (Customer customer : customers) {
			CustomerDataDTO customerDTO = modelMapper.map(customer, CustomerDataDTO.class);
			customerDataDTOList.add(customerDTO);
		}
		return customerDataDTOList;
	}

	public void convertCustomerType(Map<String, String> map) {
		String customerEmail = map.get("customerEmail");
		String customerTypeTargetStr = map.get("customerTypeTarget");
		CustomerType customerTypeTarget = CustomerType.valueOf(customerTypeTargetStr);
		adminUserService.convertCustomerType(customerEmail, customerTypeTarget);
		
	}

}
