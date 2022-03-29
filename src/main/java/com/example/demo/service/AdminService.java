package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtUtil;
import com.example.demo.model.Admin;
import com.example.demo.model.JwtRequest;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	protected AuthenticationManager authenticationManager;
	@Autowired
	protected JwtUtil jwtUtil;

	public String save(Admin adm) {
		adminRepository.save(adm);
		return null;	
	}

	public List<Admin> findAll() {
		return adminRepository.findAll();
	}

//	public String userlogin(Admin adn) {
//		Optional<Admin> existAdmin = adminRepository.findByEmail(adn.getEmail());
//		if (existAdmin.isPresent() && adn.getUserpassword().equals(existAdmin.get().getUserpassword())) {
//			System.out.println(":::UserLogin");
//			return "home";
//		} else {
//			return "login";
//		}
//	}

	public Object userlogin(JwtRequest jwtRequest) {
		Optional <Admin> existUser = adminRepository.findByEmail(jwtRequest.getEmail());
		if (existUser.isPresent() && jwtRequest.getUserpassword().equals(existUser.get().getUserpassword())) {
			Admin user = existUser.get();
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(user.getUsername(), jwtRequest.getUserpassword()));
//			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtil.generateJwtToken(user);
			HashMap<String, Object> userData = new HashMap<>();
			userData.put("username", user.getUsername());
			userData.put("email", user.getEmail());
			userData.put("mobileNo", user.getMob());
			userData.put("token", jwt);
			System.err.println("token"+jwt);
			return userData;
		} else {
			throw new RuntimeException("Invalid Credentials");
		}
	}
	public Boolean checkEmail(String email) {
		Optional<Admin> existAdmin = adminRepository.findByEmail(email);
		return existAdmin.isPresent();
	}

	public Boolean checkPassword(String userpassword) {
		Optional<Admin> existAdmin = adminRepository.findByUserpassword(userpassword);
		return existAdmin.isPresent();
	}




}
