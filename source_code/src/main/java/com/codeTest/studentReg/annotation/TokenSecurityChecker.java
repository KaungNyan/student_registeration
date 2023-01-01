package com.codeTest.studentReg.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;

import com.codeTest.studentReg.entity.User;

//import com.codeTest.studentReg.utility.JwtTokenUtil;

@Aspect
@Component
public class TokenSecurityChecker{
    
    @Around("@annotation(SecureWithToken)")
    public Object validator(ProceedingJoinPoint call) throws Throwable, NotFoundException{
    	Object[] args = call.getArgs();
        String access_token = null;
        HttpSession session = null;

        for (Object obj : args){
            if(obj instanceof HttpServletRequest){
            	session = ((HttpServletRequest) obj).getSession();
            	
            	access_token =  ((HttpServletRequest) obj).getHeader("Content-Over"); //access_token supplied in header
               
            	if(access_token==null){
            		access_token = ((HttpServletRequest) obj).getParameter("Content-Over"); // access_roken supplied in request parameters
            	}
            	
//            	access_token = JwtTokenUtil.getLoginUser((HttpServletRequest) obj);
            }
        }
        
        Object result = call.proceed();
        
        User u = new User();
        if(session.getAttribute("uusid-bean") != null) {
        	u = (User) session.getAttribute("uusid-bean");
        }
        
        if(access_token != null && access_token.equals(u.getOrgId())){
               return result;
        }else{
             throw new NotFoundException();
        }
    }
}