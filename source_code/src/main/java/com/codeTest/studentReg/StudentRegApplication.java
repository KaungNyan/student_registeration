package com.codeTest.studentReg;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.codeTest.studentReg.utility.JwtTokenUtil;

@SpringBootApplication
public class StudentRegApplication {

	public static void main(String[] args) {
		HashMap<String, String> domains = new HashMap<String, String>();
		domains.put("bss", "001");
		
		JwtTokenUtil.setDomains(domains);
		
		SpringApplication.run(StudentRegApplication.class, args);
	}

}
