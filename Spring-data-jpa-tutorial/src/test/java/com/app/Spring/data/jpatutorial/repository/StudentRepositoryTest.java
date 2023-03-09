package com.app.Spring.data.jpatutorial.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.Spring.data.jpatutorial.entity.Gaurdian;
import com.app.Spring.data.jpatutorial.entity.Student;

@SpringBootTest
class StudentRepositoryTest {

	@Autowired
	private StudentRepository repository;
		
//	@Test
//	public void saveStudentWithGaurdian() {
//		Gaurdian gaurdian=Gaurdian.builder()
//				.name("rithwik")
//				.email("ashok@gmail.com")
//				.mobile("9676708864")
//				.build();
//		Student student=Student.builder()
//				  .firstName("ashok") 
//				  .lastName("ashok") 
//				  .emailId("ashok488.se@gmail.com")
//				  .gaurdian(gaurdian)
//				  .build();
//		repository.save(student);
//	}
	
	@Test
	public void getAll() {
		List<Student> ls=repository.findAll();
		System.out.println(ls);
	}
	@Test
	public void findAll() {
		
		List<Student> listStudent=repository.findByFirstName("pitla");
		System.out.println(listStudent);
	}
}
