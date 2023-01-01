package com.codeTest.studentReg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeTest.studentReg.entity.Student;

@EnableJpaRepositories
public interface StudentPageRepository extends PagingAndSortingRepository<Student, Integer> {
	Page<Student> findAll(Pageable pageable);
}
