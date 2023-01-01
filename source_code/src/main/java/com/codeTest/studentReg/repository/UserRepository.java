package com.codeTest.studentReg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.codeTest.studentReg.entity.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value = "select * from user_data where user_name=? and password=?", nativeQuery = true)
	public User checkUser(String userName, String password);
}