package com.tarya.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
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
			Employee empFound = employeeRepository.insert(employee);
			response = setResponseEmployer(empFound, HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
		} else {
			response = setResponseEmployer(tempEmp, HttpStatus.CONFLICT.value(), "Record already exist");
		}
		return response;
	}


	public ResponseData<Employee> getEmployeeById(String id) throws NoSuchElementException {
		Optional<Employee> tempEmp = Optional.ofNullable(employeeRepository.findById(id).get());
		return response = setResponseEmployer(tempEmp.get(), HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());

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

	public ResponseData<Employee> updateEmployee(Employee employee) throws NoSuchElementException {
		return response = setResponseEmployer(employeeRepository.save(employee), HttpStatus.OK.value(),
				HttpStatus.OK.getReasonPhrase());
	}

	@Query(value = "{'id' : $0}", delete = true)
	public ResponseData<String> deleteEmployee(String id) throws NoSuchElementException {
		employeeRepository.deleteById(id);
		String msg = "Record deleted succssfully!";
		responseString = setResponseEmployerString(msg, HttpStatus.ACCEPTED.value(),
				HttpStatus.ACCEPTED.getReasonPhrase());
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
