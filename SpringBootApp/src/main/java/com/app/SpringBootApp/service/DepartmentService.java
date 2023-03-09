package com.app.SpringBootApp.service;

import java.util.List;

import com.app.SpringBootApp.model.Department;

public interface DepartmentService {

	public Department saveDepartment(Department department);
	public List<Department> fetchAll();
	public Department getById(long id);
	public void deleteById(long id);
	public Department updateDepartment(long id, Department department);

}
