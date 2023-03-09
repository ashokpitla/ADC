package com.app.SpringBootApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.SpringBootApp.model.Department;
import com.app.SpringBootApp.service.DepartmentService;


@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@PostMapping("/departments")
	public Department saveDepartment(@RequestBody Department department) {
				
		return departmentService.saveDepartment(department);
	}
	@GetMapping("/departments")
	public List<Department> fetchAll(){
		
		return departmentService.fetchAll();
	}
	@GetMapping("/departments/{id}")
	public Department getById(@PathVariable("id") long departmentId) {
		
		return departmentService.getById(departmentId);
	}
	@DeleteMapping("/departments/{id}")
	public String deleteById(@PathVariable("id") long id) {
		 departmentService.deleteById(id);
		 return "succesfully deleted";
	}
	@PutMapping("/departments/{id}")
	public Department updateDepartment(@PathVariable("id") long id, Department department) {
		
		return departmentService.updateDepartment(id, department);
	}
}