package com.example.demo.model;

import javax.validation.constraints.NotBlank;

public class JwtRequest {

	@NotBlank
	private String email;
	@NotBlank
	private String userpassword;

	public JwtRequest(String email, String userpassword) {
		super();
		this.email = email;
		this.userpassword = userpassword;
	}

	public JwtRequest() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

}
