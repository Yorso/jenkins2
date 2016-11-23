package com.jorge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jorge.dao.UserDAO;

@Controller // This says this class is a controller
public class UserController {
	
	@Autowired // // Injecting UserDAO
	private UserDAO userDAO; // Because of @Autowired, the userDAO field will be automatically initialized by Spring using dependency injection
	
	@RequestMapping("hi")
	@ResponseBody
	public String hello(){
		return "Hi there!!!";
	}
}
