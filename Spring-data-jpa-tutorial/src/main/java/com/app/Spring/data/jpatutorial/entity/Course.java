package com.app.Spring.data.jpatutorial.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
	@Id
	@SequenceGenerator(name = "course_seq", sequenceName = "course_sequence",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
	private Long courseId;
	private String title;
	private Integer credit;
	
	@OneToOne(mappedBy = "course")
	private CourseMaterial courseMaterial;
	
}
