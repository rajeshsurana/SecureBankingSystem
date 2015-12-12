package com.spring.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthenticationHandler {
	BCryptPasswordEncoder encoder;
	
	public AuthenticationHandler() {
		this.encoder = new BCryptPasswordEncoder(12);
	}
	
	public String encode(String rawPass){
		return this.encoder.encode(rawPass);
	}
	
	public boolean matches(String rawPass, String hash) {
		return this.encoder.matches(rawPass, hash);
	}

}
