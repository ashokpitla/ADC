package com.app.Spring.data.jpatutorial.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="tbl_student", uniqueConstraints = @UniqueConstraint(name="emailid_unique",columnNames = "email_address"))
public class Student {
	@Id
	@SequenceGenerator(name = "student-seq",sequenceName = "student_sequence",allocationSize = 1)
	private Long studentId;
	private String firstName;
	private String  lastName;
	@Column(name="email_address",nullable = false)
	private String  emailId;
	
	@Embedded
	private Gaurdian gaurdian;
	
}
