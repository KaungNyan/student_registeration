package com.codeTest.studentReg.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeTest.studentReg.entity.User;
import com.codeTest.studentReg.repository.UserRepository;
import com.codeTest.studentReg.utility.JwtTokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	UserRepository uRepo;

	@Override
	public User login(String domain, String userName, String password, HttpServletRequest request, HttpServletResponse resp) {
		User loginData = null;
		
		String key = JwtTokenUtil.getConnectionKey(domain);
		
		if (key.equals("")) {
			loginData = new User();
			loginData.setId(0);
			return loginData;
		}
		
		loginData = uRepo.checkUser(userName, password);
		
		if (loginData != null) {
			loginData.setOrgId(key);
			loginData.setOrgId(JwtTokenUtil.createToken(loginData));
			
			Cookie cok = new Cookie("token", key);
			resp.addCookie(cok);

//			request.getSession().setAttribute("uusid-bean", getSynchronizedMap(key, String.valueOf(loginData.getId())));
			request.getSession().setAttribute("uusid-bean", loginData);
		}
		
		return loginData == null ? new User() : loginData;
	}

    public Map<String, String> getSynchronizedMap(String orgid, String uid) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map<String, String> map = new HashMap();
        map.put("oid", orgid);
        map.put("upk", uid);
        
        Map<String, String> synMap = Collections.synchronizedMap(map);
        
        return synMap;
    }

	@Override
	public User addUser(User data) {
		return uRepo.save(data);
	}
	
}