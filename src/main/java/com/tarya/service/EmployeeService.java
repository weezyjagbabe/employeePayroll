package com.tarya.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tarya.entity.Employee;
import com.tarya.model.ResponseData;
import com.tarya.repository.EmployeeRepository;

/**
 *
 * @author SBortey
 */
@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	ResponseData<Employee> response = new ResponseData();
	ResponseData<List<Employee>> responseList = new ResponseData();
	Employee tempEmp = new Employee();
	List<Employee> empList = new ArrayList<>();
	ResponseData<String> responseString = new ResponseData();
	
	
	public ResponseData<Employee> createEmployee(Employee employee) {
		tempEmp = employeeRepository.findByEmailAndName(employee.getEmail(), employee.getName());
		if (null == tempEmp) {
			Employee empFound = employeeRepository.save(employee);
			setResponseEmployer(empFound,HttpStatus.CREATED.value(),HttpStatus.CREATED.getReasonPhrase());
		} else {
			setResponseEmployer(tempEmp,HttpStatus.CONFLICT.value(),"Record already exist");
		}
		return response;
	}

	

	public ResponseData<Employee> getEmployeeById(String id) {
		try {
			tempEmp = employeeRepository.findById(id).get();
			setResponseEmployer(tempEmp,HttpStatus.CREATED.value(),HttpStatus.CREATED.getReasonPhrase());
		} catch (Exception e) {
			setResponseEmployer(new Employee(),HttpStatus.NO_CONTENT.value(),"Record Not Found");	
		}
		return response;
	}

	public ResponseData<List<Employee>> getAllEmployees() {
		
		empList = employeeRepository.findAll();
		if(empList.size() > 0) {
			setResponseEmployerList(empList, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
			
		}else {
			setResponseEmployerList(new ArrayList<>(), HttpStatus.NO_CONTENT.value(), "No record exists");
		}
		return responseList;
	}

	public ResponseData<Employee> updateEmployee(Employee employee) {
		try {
			employeeFindById(employee);
			setResponseEmployer(employeeRepository.save(employee),HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase());
		
		} catch (Exception e) {
			setResponseEmployer(new Employee(),HttpStatus.NO_CONTENT.value(),"Record doesn't exist for update");		
		}
		return response;
		
	}



	private void employeeFindById(Employee employee) {
		tempEmp = employeeRepository.findById(employee.getId()).get();
	}

	@SuppressWarnings("deprecation")
	public ResponseData<String> deleteEmployee(String id) {
		String msg = "Record not found for deletion!";
		try {
			Employee byId =  employeeRepository.findById(id).get();
			if (null != byId) {
				employeeRepository.deleteById(id);
				msg = "Record deleted succssfully!";
				setResponseEmployerString(msg, HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.getReasonPhrase());				
			}
			setResponseEmployerString(msg, HttpStatus.METHOD_FAILURE.value(), HttpStatus.METHOD_FAILURE.getReasonPhrase());	
	
		} catch (Exception e) {
			setResponseEmployerString(msg, HttpStatus.METHOD_FAILURE.value(), HttpStatus.METHOD_FAILURE.getReasonPhrase());
		}
		return responseString;
	}

	public List<Employee> getEmployeesByName(String name) {
		return employeeRepository.findByName(name);
	}

	public Employee employeeByNameAndEmail(String name, String email) {
		return employeeRepository.findByEmailAndName(email, name);
	}

	public Employee employeesByNameOrEmail(String name, String email) {
		return employeeRepository.findByNameOrEmail(name, email);
	}
	private void setResponseEmployer(Employee employee,int respCode, String respMsg) {
		response.setResponseContent(employee);
		response.setResponseCode(respCode);
		response.setResponseMessage(respMsg);
	}
	private void setResponseEmployerList(List<Employee> employee,int respCode, String respMsg) {
		responseList.setResponseContent(employee);
		responseList.setResponseCode(respCode);
		responseList.setResponseMessage(respMsg);
	}
	private void setResponseEmployerString(String employee,int respCode, String respMsg) {
		responseString.setResponseContent(employee);
		responseString.setResponseCode(respCode);
		responseString.setResponseMessage(respMsg);
	}

}
