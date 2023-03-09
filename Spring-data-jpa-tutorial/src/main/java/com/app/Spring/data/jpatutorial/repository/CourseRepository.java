package com.app.Spring.data.jpatutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.Spring.data.jpatutorial.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

}
