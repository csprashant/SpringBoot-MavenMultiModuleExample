package com.nt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nt.configuration.AuthenticationRequest;
import com.nt.configuration.AuthenticationResponse;
import com.nt.configuration.MyUserDetialsService;
import com.nt.utility.JwtUtil;


import ch.qos.logback.classic.Logger;
@RestController
public class HomeController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetialsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/")
	public String home() {
		return "<h1> welcome</h1>";
	}
	@GetMapping("/user")
	public String user() {
		return "<h1> welcome user</h1>";
	}
	@GetMapping("/admin")
	public String admin() {
		return "<h1> welcome admin</h1>";
	}
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticateToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		System.out.println("HomeController.createAuthenticateToken()");
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
			authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException e) { 
			throw new Exception("Invalid username and password",e);
		}
		final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt=jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
		
	}
}
