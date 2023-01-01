package com.codeTest.studentReg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeTest.studentReg.entity.Student;
import com.codeTest.studentReg.repository.StudentPageRepository;
import com.codeTest.studentReg.repository.StudentRepository;

@Service
public class StudentServiceImp implements StudentService {
	@Autowired
	StudentRepository stuRepo;

	@Autowired
	StudentPageRepository stuPageRepo;

	@Override
	public List<Student> getAllStudents() {
		return stuRepo.findAll();
	}

	@Override
	public Page<Student> getAllByPage(int pageNo, int noOfData) {
		Pageable pagination = PageRequest.of(pageNo, noOfData);
		
		return stuPageRepo.findAll(pagination);
	}

	@Override
	public Student getStudentById(long id) {
		return stuRepo.findById((int) id).orElse(null);
	}

	@Override
	public Student addStudent(Student studentData) {
		return stuRepo.save(studentData);
	}

	@Override
	public Student updateStudent(Student studentData) {
		Student findStudent = stuRepo.findById((int)studentData.getId()).orElse(null);
		
		if(findStudent == null) {
			return null;
		}
		
		findStudent.setName(studentData.getName());
		findStudent.setAge(studentData.getAge());
		findStudent.setPhone(studentData.getPhone());
		findStudent.setAddress(studentData.getAddress());
		findStudent.setCreatedAt(studentData.getCreatedAt());
		findStudent.setUpdatedAt(studentData.getUpdatedAt());
		
		return stuRepo.save(findStudent);
	}

	@Override
	public boolean deleteStudent(long id) {
		Student findBook = stuRepo.findById((int)id).orElse(null);
		
		if(findBook == null) {
			return false;
		}
		
		stuRepo.deleteById((int) id);
		
		return true;
	}

	@Override
	public long getStudentCount() {
		Object tempCount = stuRepo.getStudentCount();
		long studentCount = tempCount == null ? 0 : Long.parseLong(tempCount.toString());
		
		return studentCount;
	}
}