package com.vt.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vt.project.model.AuthResponse;
import com.vt.project.model.User;
import com.vt.project.service.userService;
import com.vt.project.utils.JwtUtils;

@RestController
public class LoginController {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private userService userService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody User loginUser) {
		User user = userService.getUserByEmail(loginUser.getEmail());

		if (user == null || !user.getPass().equals(loginUser.getPass())) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}

		// Generate JWT token
		String jwtToken = jwtUtils.generateToken(user);

		return ResponseEntity.ok(new AuthResponse(jwtToken));
	}

}
