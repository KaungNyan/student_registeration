package com.codeTest.studentReg.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeTest.studentReg.entity.User;
import com.codeTest.studentReg.service.UserService;
import com.codeTest.studentReg.utility.generalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService uService;
	
	@PostMapping("/login")
	public User create(@RequestBody HashMap<String, String> loginUser, HttpServletRequest request, HttpServletResponse resp){
		return uService.login(loginUser.get("domain"), loginUser.get("username"), loginUser.get("password"), request, resp);
	}
	
	@PostMapping("/save")
	public User addUser(@RequestBody User data) {
		String currentDate = generalUtil.getCurrentDate();
		data.setCreatedAt(currentDate);
		data.setUpdatedAt(currentDate);
		
		return uService.addUser(data);
	}
}