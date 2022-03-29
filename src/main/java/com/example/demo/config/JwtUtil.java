package com.example.demo.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.model.Admin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${example.app.jwtSecret}")
	private String jwtSecret;

	@Value("${example.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Admin user) {
//		UserDetailsImpl userPrincipal = (UserDetailsImpl) user.getPrincipal();
		return Jwts.builder().setSubject((user.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

}
