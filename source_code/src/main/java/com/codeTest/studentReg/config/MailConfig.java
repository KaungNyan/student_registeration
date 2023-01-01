package com.codeTest.studentReg.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//@Configuration
public class MailConfig {
	@Value("smtp.gmail.com")
	private String host;
	
	@Value("587")
	private Integer port;
	
	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		
		javaMailSenderImpl.setHost(host);
		javaMailSenderImpl.setPort(port);
		
		javaMailSenderImpl.setUsername("Enter your gmail username");
		javaMailSenderImpl.setPassword("Enter your gmail password");
		
		javaMailSenderImpl.setJavaMailProperties(javaMailProperties());
		
		return javaMailSenderImpl;
	}

	private Properties javaMailProperties() {
		Properties properties = new Properties();
		
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.debug", "true");
		
		return properties;
	}
}