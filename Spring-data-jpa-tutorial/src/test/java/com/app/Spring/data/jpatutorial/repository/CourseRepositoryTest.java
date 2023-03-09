package com.app.Spring.data.jpatutorial.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.app.Spring.data.jpatutorial.entity.Course;

@SpringBootTest
class CourseRepositoryTest {

	@Autowired
	private CourseRepository courseRepository;
	
	@Test
	public void printCourse() {
		
	List<Course> list=courseRepository.findAll();
	System.out.println(list);
	}

	@Test
	public void coursePagination() {
	
		Pageable firstPage=PageRequest.of(0, 4);
		Pageable secondPage=PageRequest.of(1, 1);
		
		List<Course> list=courseRepository.findAll(firstPage).getContent();
		long totalElements=courseRepository.findAll(firstPage).getTotalElements();
		long totalPages=courseRepository.findAll(firstPage).getTotalPages();
		
		
		System.out.println(list);
		System.out.println(totalElements);
				System.out.println(totalPages);
		
	}
	
	public void sortByCourse() {
		
		Pageable sortBytitle=PageRequest.of(0,2,Sort.by("title"));
		Pageable sortByCredit=PageRequest.of(0,1,Sort.by("credit").descending());
		List<Course> title=courseRepository.findAll(sortBytitle).getContent();
		List<Course> credit=courseRepository.findAll(sortByCredit).getContent();
		System.out.println("sort by title "+title);
	
	}
}
