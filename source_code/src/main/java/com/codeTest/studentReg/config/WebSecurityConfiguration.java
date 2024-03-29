package com.codeTest.studentReg.config;

import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
//       http.csrf().disable().authorizeRequests()
//        .antMatchers("/").permitAll()
//        .antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
//        .antMatchers(HttpMethod.POST,"/api/user/save/*").permitAll()
//        .anyRequest().authenticated();
       
       http.cors().and().csrf().disable();
    }
}