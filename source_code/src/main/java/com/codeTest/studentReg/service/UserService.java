package com.codeTest.studentReg.service;

import com.codeTest.studentReg.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
	public User login(String domain, String userName, String password, HttpServletRequest request, HttpServletResponse resp);
	public User addUser(User data);
}