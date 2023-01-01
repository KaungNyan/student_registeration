package com.codeTest.studentReg.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.codeTest.studentReg.entity.Student;

public interface StudentService {
	public List<Student> getAllStudents();
	public Page<Student> getAllByPage(int pageNo, int noOfData);
	public Student getStudentById(long id);
	public Student addStudent(Student studentData);
	public Student updateStudent(Student studentData);
	public boolean deleteStudent(long id);
	
	public long getStudentCount();
}