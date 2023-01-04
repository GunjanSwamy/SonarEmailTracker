package com.sonar.sonarAdmin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sonar.sonarAdmin.model.AdminUser;
import com.sonar.sonarAdmin.model.CustomUserDetails;
import com.sonar.sonarAdmin.repository.AdminUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

	@Autowired
	AdminUserRepository adminUserRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<AdminUser> user = adminUserRepository.findById(userName);
		if (user == null) {
			log.error("User with userId" + userName + "not found");
			throw new UsernameNotFoundException("User not found");
		}

		AdminUser userModel = user.get();
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

		return getUserForAuth(userModel, grantedAuthorities);

	}

	private UserDetails getUserForAuth(AdminUser userModel, Collection<GrantedAuthority> grantedAuthorities) {
		CustomUserDetails customUserDeatils = new CustomUserDetails(userModel);
		return customUserDeatils;
	}
}
