package com.nt.configuration;
public class AuthenticationRequest {
	private String username;
	private String password;
	public AuthenticationRequest(String username, String password) {
	
		super();
		System.out.println("AuthenticationRequest.AuthenticationRequest(-,-)");
		this.username = username;
		this.password = password;
	}	
	public AuthenticationRequest() {
		System.out.println("AuthenticationRequest.AuthenticationRequest()");

	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
