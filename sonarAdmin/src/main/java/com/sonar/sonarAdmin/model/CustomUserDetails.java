package com.sonar.sonarAdmin.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
	String ROLE_PREFIX = "ROLE_";
	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities = new ArrayList<>();
	private String role;

	public CustomUserDetails(AdminUser user) {
		// convert string userType to GrantedAuthority
		List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
		authority.add(new SimpleGrantedAuthority("ADMIN"));

		this.userName = user.getEmail();
		this.password = user.getPassword();
		this.active = user.isActive();

		this.authorities = authority;
		this.role = ROLE_PREFIX + "ADMIN";

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return active;
	}

	public String getRole() {
		return role;
	}

}
