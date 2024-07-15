package com.vt.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vt.project.model.User;
import com.vt.project.repository.userRepo;

@Service
public class userService {

	@Autowired
	private userRepo userRepo;
	
	public User getUserByEmail(String Email) {
		
		User user = userRepo.findByEmail(Email); 
		
		return user;
	}
	
}
