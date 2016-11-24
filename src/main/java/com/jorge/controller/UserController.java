package com.jorge.controller;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jorge.dao.UserDAO;
import com.jorge.model.User;
import com.jorge.util.Constants;

@Controller // This says this class is a controller
public class UserController {
	
	@Autowired // // Injecting UserDAO
	private UserDAO userDAO; // Because of @Autowired, the userDAO field will be automatically initialized by Spring using dependency injection
	
	@RequestMapping("hi")
	@ResponseBody
	public String hello(){
		return "Hi there!!!";
	}
	
	@RequestMapping("form")
	public String form(Model model){
		model.addAttribute("message", null);
		
		return "form";
	}
	
	@RequestMapping(method=RequestMethod.POST, path="form")
	public String formSubmit(@ModelAttribute("defaultUser") User user, Model model){//It doesn't create a new User object, 
																	   //it uses the default User object created before
		try{
			userDAO.add(user);
			
			user.setFirstName(null);
			user.setAge(40);
			model.addAttribute("message", Constants.MESSAGE_OK);
		}
		catch(Exception e){
			model.addAttribute("message", Constants.MESSAGE_KO);
		}
		
		return "form";
		
	}
	
	
	//Models
	@ModelAttribute("defaultUser")
	public User defaultUser() {
		User user = new User();
		
		user.setAge(40);
		
		return user;
	}
	
	@ModelAttribute("ages")
	public Map<String, Integer> ages() {
		SortedMap<String, Integer> m = new TreeMap<String, Integer>(java.util.Collections.reverseOrder());
		
		for(int i= 18; i < 100; i++)
			m.put(Integer.toString(i), i);
		
		return m;
	}

}
