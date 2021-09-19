package com.nt.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nt.filter.JwtRequestFilter;
import com.nt.model.MyUserDetails;
import com.nt.model.User;
import com.nt.repo.UserRepo;
@Service	
public class MyUserDetialsService implements UserDetailsService{
	@Autowired
	private UserRepo repo;
	Logger logger=LoggerFactory.getLogger(JwtRequestFilter.class);
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.trace("MyUserDetialsService.loadUserByUsername()");
		Optional<User> findByUserName = repo.getUserByUsername(username);
		System.out.println(findByUserName);
		findByUserName.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		return findByUserName.map(MyUserDetails :: new).get();}

}
