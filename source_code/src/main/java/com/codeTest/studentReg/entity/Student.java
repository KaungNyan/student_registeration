package com.codeTest.studentReg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Student {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int age;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String createdAt;
	
	@Column(nullable = false)
	private String updatedAt;
}