package com.sonar.sonarAdmin.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sonar.sonarAdmin.facade.AdminUserFacade;
import com.sonar.sonarAdmin.model.AdminUser;

/**
 * Controller used for registration and activation of users
 * 
 * @author LVK
 *
 */

@RestController
public class RegistrationController {

	Logger log = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	AdminUserFacade adminUserFacade;


 
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void createUser(@RequestBody AdminUser adminUser, HttpServletResponse response) {

		if(adminUser==null || adminUser.getEmail().length()==0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		adminUserFacade.createAdminUser(adminUser);

	}
 
	@RequestMapping(value = "/activateUser/{userEmail}/{activationCode}", method = RequestMethod.GET)
	public String createUser(@PathVariable String activationCode, @PathVariable String userEmail) throws Exception {

		return adminUserFacade.activateAdminUser(activationCode, userEmail);

	}

}
