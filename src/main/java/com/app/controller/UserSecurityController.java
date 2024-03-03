package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.LoginRequest;
import com.app.entity.UserDetailEntity;
import com.app.service.JWTService;
import com.app.service.UserSecurityService;

@RestController
@RequestMapping("/api")
public class UserSecurityController {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authManager;

	@Autowired
	UserSecurityService userService;
	
	@Autowired
	JWTService jwt;
	
	@PostMapping("/register")
	public String register(@RequestBody UserDetailEntity userDetail) {
		String encodePwd = encoder.encode(userDetail.getPwd());
		userDetail.setPwd(encodePwd);
		boolean registerUser = userService.registerUser(userDetail);
		if(registerUser)
			return "User registered";
		else
			return "USer not registered";
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loign(@RequestBody LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPwd());
		 Authentication authenticate = authManager.authenticate(token);
		 if(authenticate.isAuthenticated()) {
			 
			 String jwtToken = jwt.generateToken(loginRequest.getName());
				return new ResponseEntity<>(jwtToken, HttpStatus.OK);
		 }

		 return new ResponseEntity<>("Invalid Credentrials" , HttpStatus.BAD_REQUEST);
	}

	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome to My App!";
	}
	
	@GetMapping("/greet")
	public String greet() {
		return "Greetings to My App!";
	}
}
