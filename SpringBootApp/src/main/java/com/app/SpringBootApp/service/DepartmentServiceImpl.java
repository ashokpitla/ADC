package com.app.SpringBootApp.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.SpringBootApp.model.Department;
import com.app.SpringBootApp.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository repository;
	@Override  	
	public Department saveDepartment(Department department) {
		
		return repository.save(department);
	}
	@Override
	public List<Department> fetchAll() {
		
		return repository.findAll();
	}
	@Override
	public Department getById(long id) {
		
		
		return repository.findById(id).get();
	}
	@Override
	public void deleteById(long id) {
		
		repository.deleteById(id);
	}
	@Override
	public Department updateDepartment(long id, Department department) {
		Department dpmt=repository.findById(id).get();
		
		if(Objects.nonNull(department.getName()) && !"".equalsIgnoreCase(department.getName())) {
			dpmt.setName(department.getName());
			
		}
		if(Objects.nonNull(department.getAddress())&& !"".equalsIgnoreCase(department.getAddress())){
			dpmt.setAddress(department.getAddress());
		}
		
		if(Objects.nonNull(department.getCode()) && !"".equalsIgnoreCase(department.getCode())) {
			dpmt.setCode(department.getCode());
		}
		return repository.save(dpmt);
	}
	
	

}
