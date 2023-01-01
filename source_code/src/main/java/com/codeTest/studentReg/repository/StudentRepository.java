package com.codeTest.studentReg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.codeTest.studentReg.entity.Student;

@EnableJpaRepositories
public interface StudentRepository extends JpaRepository<Student, Integer> {
	@Query(value = "select CASE WHEN COUNT(id) IS NULL THEN 0 ELSE COUNT(id) END from student group by id", nativeQuery = true)
	public Object getStudentCount();
}