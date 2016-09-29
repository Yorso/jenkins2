package com.jorge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jorge.dao.UserDAO;

@Controller // This says this class is a controller
public class UserController {
	
	@Autowired // // Injecting UserDAO
	private UserDAO userDAO; // Because of @Autowired, the userDAO field will be automatically initialized by Spring using dependency injection
	
	
}
