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
	ResponseData<Employee> response = new ResponseData<Employee>();
	ResponseData<List<Employee>> responseList = new ResponseData<List<Employee>>();
	Employee tempEmp = new Employee();
	List<Employee> empList = new ArrayList<>();
	ResponseData<String> responseString = new ResponseData<String>();

	public ResponseData<Employee> createEmployee(Employee employee) {
		tempEmp = employeeRepository.findByEmailAndName(employee.getEmail(), employee.getName());
		if (null == tempEmp) {
			Employee empFound = employeeRepository.save(employee);
			response = setResponseEmployer(empFound, HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
		} else {
			response = setResponseEmployer(tempEmp, HttpStatus.CONFLICT.value(), "Record already exist");
		}
		return response;
	}

	public ResponseData<Employee> getEmployeeById(String id) {
		response = setResponseEmployer(new Employee(), HttpStatus.NO_CONTENT.value(), "Record Not Found");
		tempEmp = employeeRepository.findById(id).get();
		if (null != tempEmp) {
			response = setResponseEmployer(tempEmp, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
		}
		return response;
	}

	public ResponseData<List<Employee>> getAllEmployees() {

		empList = employeeRepository.findAll();
		if (empList.size() > 0) {
			responseList = setResponseEmployerList(empList, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());

		} else {
			responseList = setResponseEmployerList(new ArrayList<>(), HttpStatus.NO_CONTENT.value(),
					"No record exists");
		}
		return responseList;
	}

	public ResponseData<Employee> updateEmployee(Employee employee) {
		response = setResponseEmployer(new Employee(), HttpStatus.NO_CONTENT.value(),
				"Record doesn't exist for update");
		Employee e =  employeeRepository.findById(employee.getId()).get();
		if (null != e) {
			response = setResponseEmployer(employeeRepository.save(employee), HttpStatus.OK.value(),
					HttpStatus.OK.getReasonPhrase());
		}
		return response;

	}

	@SuppressWarnings({ "deprecation" })
	public ResponseData<String> deleteEmployee(String id) {
		String msg = "Record not found for deletion!";

		responseString = setResponseEmployerString(msg, HttpStatus.METHOD_FAILURE.value(),
				HttpStatus.METHOD_FAILURE.getReasonPhrase());
		Employee byId = employeeRepository.findById(id).get();
		if (null != byId) {
			employeeRepository.deleteById(id);
			msg = "Record deleted succssfully!";
			responseString = setResponseEmployerString(msg, HttpStatus.ACCEPTED.value(),
					HttpStatus.ACCEPTED.getReasonPhrase());
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

	public ResponseData<Employee> setResponseEmployer(Employee employee, int respCode, String respMsg) {
		response.setResponseContent(employee);
		response.setResponseCode(respCode);
		response.setResponseMessage(respMsg);
		return response;
	}

	public ResponseData<List<Employee>> setResponseEmployerList(List<Employee> employee, int respCode, String respMsg) {
		responseList.setResponseContent(employee);
		responseList.setResponseCode(respCode);
		responseList.setResponseMessage(respMsg);
		return responseList;
	}

	public ResponseData<String> setResponseEmployerString(String employee, int respCode, String respMsg) {
		responseString.setResponseContent(employee);
		responseString.setResponseCode(respCode);
		responseString.setResponseMessage(respMsg);
		return responseString;
	}

}
