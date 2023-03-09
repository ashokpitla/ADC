package com.app.Spring.data.jpatutorial.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.Spring.data.jpatutorial.entity.Course;
import com.app.Spring.data.jpatutorial.entity.CourseMaterial;

@SpringBootTest
class CourseMaterialRepositoryTest {

	@Autowired
	CourseMaterialRepository courseMaterialRepository;


	 @Test 
	 public void saveCourseMaterial() { 
		 Course course=Course.builder()
							 .title("mca") 
							 .credit(123) 
							 .build();
							
	 CourseMaterial courseMaterial=CourseMaterial.builder() 
			 .course(course)
			 .url("www.google.com") 
			 .build();
			 courseMaterialRepository.save(courseMaterial); }
	@Test
	public void saveCourseMeterial() {
		List<CourseMaterial> list=courseMaterialRepository.findAll();
		System.out.println(list);
	}

}
