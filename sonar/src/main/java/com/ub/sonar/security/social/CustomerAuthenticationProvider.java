package com.ub.sonar.security.social;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ub.sonar.model.Customer;
import com.ub.sonar.model.CustomerType;
import com.ub.sonar.repository.CustomerRepository;
import com.ub.sonar.security.service.MyUserDetailsService;

@Component
public class CustomerAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication paramAuthentication) throws AuthenticationException {

		final CustomerAuthenticationToken customerSocialToken = (CustomerAuthenticationToken) paramAuthentication;

		// check if fields are valid/ not null etc if needed
		String customerEmail = customerSocialToken.getEmail();

		UserDetails userDetails = getCustomerUserDetails(customerEmail);
		if (userDetails == null) {
			// create new userdetials for user
			createNewCustomer(customerSocialToken);
		} else {
			// update existing Customer model
			updateCustomer(customerSocialToken, customerEmail);
		}

		userDetails = getCustomerUserDetails(customerSocialToken.getEmail());

		return createSuccessAuthentication(paramAuthentication.getPrincipal(), paramAuthentication, userDetails);
	}

	private void updateCustomer(final CustomerAuthenticationToken customerSocialToken, String customerEmail) {
		Customer customerModel = customerRepository.findById(customerEmail).get();
		customerModel.setFirstName(customerSocialToken.getFirstName());
		customerModel.setLastName(customerSocialToken.getLastName());
		customerModel.setLoginSource(customerSocialToken.getLoginSource());
		customerRepository.save(customerModel);
	}

	private void createNewCustomer(final CustomerAuthenticationToken customerSocialToken) {
		Customer customerModel = new Customer();
		customerModel.setEmail(customerSocialToken.getEmail());
		customerModel.setFirstName(customerSocialToken.getFirstName());
		customerModel.setLoginSource(customerSocialToken.getLoginSource());
		customerModel.setLastName(customerSocialToken.getLastName());
		customerModel.setTimezone("EST");
		customerModel.setPreferedLang("US-En");
		customerModel.setCustomerType(CustomerType.NON_PREMIUM);
		customerModel.setIsActive(true);

		customerRepository.save(customerModel);
	}

	private Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails userDetails) {

		CustomerAuthenticationToken result = new CustomerAuthenticationToken(principal.toString().toLowerCase(),
				userDetails.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}

	private UserDetails getCustomerUserDetails(String email) {
		try {
			UserDetails userDetails = null;
			userDetails = this.getUserDetailsService().loadUserByUsername(email);
			return userDetails;
		} catch (final UsernameNotFoundException une) {
			// Log.info("EXCEPTION user name not found", une);
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return Objects.nonNull(authentication) && CustomerAuthenticationToken.class.isAssignableFrom(authentication);
	}

	protected UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}
}
