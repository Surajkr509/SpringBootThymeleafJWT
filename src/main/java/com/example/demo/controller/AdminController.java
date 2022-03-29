package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Admin;
import com.example.demo.model.JwtRequest;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	private AdminRepository adminRepository;

	Logger logger;

	@PostMapping("/testUser")
	public ResponseEntity<?> testUser(@RequestBody JwtRequest reqData) {
		System.out.println("Test:::::::::::::::::::::::");
		try {
			Optional<Admin> existData = adminRepository.findByEmail(reqData.getEmail());
			if (existData.isPresent()) {
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("name", existData.get().getUsername());
				return new ResponseEntity<>(hashMap, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User Not Exist", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostConstruct
	public void init() {
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("UserController Initialised");
	}

	@GetMapping("/login")
	public String login(JwtRequest jwtRequest) {
		return "login";
	}

	@GetMapping("/signUp")
	public String signUp(Admin admin) {
		System.out.println("SignUp GET ::");
		return "signUp";
	}

	@PostMapping("/createUser")
	public ModelAndView createUser(@Valid Admin adm, BindingResult bindingResult) {
		System.out.println("CreateUser ::::" + bindingResult.getErrorCount());
		System.out.println("username::::::::"+adm.getUsername());
		if (adm.getUsername().isEmpty()) {
			bindingResult.rejectValue("username", "error.admin.username", "Enter Username");
		}
//		if(adm.getUsername().length()<3 || adm.getUsername().length()>10 ) {
//			bindingResult.rejectValue("username", "error.admin.username", " username length should be in between 3 to 50");
//		}
		String userpassword = adm.getUserpassword();
		if (userpassword != "") {
			System.err.println("password" + userpassword);
			if (adm.getUserpassword().equals(adm.getUsername())) {
				bindingResult.rejectValue("userpassword", "error.admin.userpassword",
						"Password cannot be same as Username");
			}
		}
		String email = adm.getEmail();
		if (adminService.checkEmail(email) == true) {
			bindingResult.rejectValue("email", "errors.admin.email", "Enter email is already exist");
		}
		if (bindingResult.hasErrors()) {
			for (Object data : bindingResult.getAllErrors()) {
				System.out.println("Data ::: " + data.toString());

			}
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("signUp");
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView();
			adminService.save(adm);
			modelAndView.addObject("successMessage", "Registration Successfully");
			modelAndView.setViewName("signUp");
			return modelAndView;

		}
	}

	@GetMapping("/getUser")
	public List<Admin> getAdmin() {
		return adminService.findAll();
	}
//	@PostMapping("/userLogin")
//	public ResponseEntity<?> userlogin(@Valid @RequestBody JwtRequest jwtRequest, BindingResult bindingResult) {
//		try {
//			System.err.println("::: userLogin ::: ");
//			String email = jwtRequest.getEmail();
//			if (adminService.checkEmail(email) == false) {
//				bindingResult.rejectValue("email", "errors.admin.email", "Enter email address does not exist");
//			}
//			String userpassword = jwtRequest.getUserpassword();
//			if (adminService.checkPassword(userpassword) == false) {
//				bindingResult.rejectValue("userpassword", "errors.admin.userpassword", "Please Enter correct password");
//			}
//			return ResponseEntity.ok(adminService.userlogin(jwtRequest));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	@PostMapping("/userLogin")
	public ModelAndView userlogin(@Valid JwtRequest jwtRequest, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			System.err.println("::: userLogin ::: ");
			String email = jwtRequest.getEmail();
			if (adminService.checkEmail(email) == false) {
				bindingResult.rejectValue("email", "errors.jwtRequest.email", "Enter email address does not exist");
			}
			String userpassword = jwtRequest.getUserpassword();
			if (adminService.checkPassword(userpassword) == false) {
				bindingResult.rejectValue("userpassword", "errors.jwtRequest.userpassword", "Please Enter correct password");
			}
			adminService.userlogin(jwtRequest);
			modelAndView.setViewName("/home");
			return modelAndView;
		}catch (Exception e) {
		e.printStackTrace();
		
		}
		modelAndView.setViewName("/login");
		return modelAndView;
	}

	@GetMapping("/checkEmail/{email}")
	public ResponseEntity<?> checkEmail(@PathVariable String email) {
		System.out.println("email" + email);
		return ResponseEntity.ok(adminService.checkEmail(email));
	}

	@GetMapping("/checkPassword/{email}")
	public ResponseEntity<?> checkPassword(@PathVariable String userpassword) {
		System.out.println("password" + userpassword);
		return ResponseEntity.ok(adminService.checkPassword(userpassword));
	}

	@GetMapping("/forgotPassword")
	public String forgot() {
		return "forgotPassword";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

//	@GetMapping("/books")
//	public String books() {
//		return "books";
//	}
//	@PostMapping("loginUser")
//	public ModelAndView loginUser(@ModelAttribute Admin adn) {
//	System.err.println(":::UserController.loginUser ::: ");
//	Optional<Admin> existData = adminRepository.findByEmail(adn.getEmail());
//	adn.getUserpassword();
//	ModelAndView modelAndView = new ModelAndView();
//	if(existData.isPresent()) {
//		 logger.info("here are we sending to home");
//		 modelAndView.addObject("user",existData);
//		 modelAndView.addObject("successMesage", "Your are successfully login");
//         modelAndView.setViewName("/home");
//	
//	 return modelAndView;
//    } else {
//    	logger.warn("Invalid User !");
//            modelAndView.addObject("errorMesage", "Wrong user and password");
//            modelAndView.setViewName("/login");
//            return modelAndView;
//    }
//	}
}
