package com.app.Spring.data.jpatutorial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.Spring.data.jpatutorial.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	public List<Student> findByFirstName(String firstName);

}
