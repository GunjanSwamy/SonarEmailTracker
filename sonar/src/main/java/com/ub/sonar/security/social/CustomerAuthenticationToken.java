package com.ub.sonar.security.social;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerAuthenticationToken extends AbstractAuthenticationToken {

	private String email;
	private String firstName;
	private String lastName;

	private String loginSource;

	public CustomerAuthenticationToken(String email, String firstName, String lastName, String loginSource) {
		super(null);
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.loginSource = loginSource;
	}

	public CustomerAuthenticationToken(final String email, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.email = email;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return email;
	}

}
