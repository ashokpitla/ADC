package com.app.Spring.data.jpatutorial.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.Spring.data.jpatutorial.entity.Course;
import com.app.Spring.data.jpatutorial.entity.Teacher;

@SpringBootTest
class TeacherRepositoryTest {

	@Autowired
	private TeacherRepository repository;
	@Test
	void  saveTeacher() {
		Course courseJava=Course.builder()
				.title("java")
				.credit(142)
				.build();
		Course courseWeb=Course.builder()
				.title("web")
				.credit(143)
				.build();
		
		Teacher teacher=Teacher.builder()
				.firstName("ashok")
				.lastName("pitla")
				.courses(List.of(courseJava,courseWeb))
				.build();
		repository.save(teacher);
	}

}
