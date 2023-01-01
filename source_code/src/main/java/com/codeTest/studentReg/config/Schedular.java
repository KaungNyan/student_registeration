package com.codeTest.studentReg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.codeTest.studentReg.service.StudentService;

@Configuration
@EnableScheduling
public class Schedular {
	@Autowired
	StudentService stuService;	
	
	@Scheduled(fixedRate = 60 * 60 * 1000)
	public void scheduleFixedRateTask() {
	    System.out.println("Number of Student : " + stuService.getStudentCount());
	}
}