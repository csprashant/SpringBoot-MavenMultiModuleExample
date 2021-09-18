package com.nt.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nt.model.User;

public class MyUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private User user;
	public MyUserDetails() {
	
	}
	public MyUserDetails(User user) {
		this.user=user;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		var simpleGrantedAuthority= new SimpleGrantedAuthority(user.getRoles());
		System.out.println(simpleGrantedAuthority);
		return  List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
	
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
			return true;
	}

}
