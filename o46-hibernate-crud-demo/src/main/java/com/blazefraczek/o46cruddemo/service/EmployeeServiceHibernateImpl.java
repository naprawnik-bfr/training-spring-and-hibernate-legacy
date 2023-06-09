package com.blazefraczek.o46cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blazefraczek.o46cruddemo.dao.EmployeeDAO;
import com.blazefraczek.o46cruddemo.entity.Employee;

@Service
public class EmployeeServiceHibernateImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDAO employeeDAO;

	
	@Override
	@Transactional
	public List<Employee> findAll() {
		return employeeDAO.findAll();
	}

	@Override
	@Transactional
	public Employee findById(int theId) {
		// TODO Auto-generated method stub
		return employeeDAO.findById(theId);
	}

	@Override
	@Transactional
	public void save(Employee theEmployee) {
		employeeDAO.save(theEmployee);
		
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		employeeDAO.deleteById(theId);
		
	}

}
