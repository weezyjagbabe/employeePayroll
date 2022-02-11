package com.tarya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarya.entity.Employee;
import com.tarya.model.ResponseData;
import com.tarya.service.EmployeeService;
/**
*
* @author SBortey
*/
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

	@PostMapping("/create")
	public ResponseEntity<ResponseData<Employee>> createEmployee(@RequestBody Employee employee) {
		ResponseData<Employee> response = employeeService.createEmployee(employee);
		return finalResponse(response, HttpStatus.OK);
	}

	private ResponseEntity<ResponseData<Employee>> finalResponse(ResponseData<Employee> response,  HttpStatus ok) {
		return new ResponseEntity< ResponseData<Employee>>(response, ok);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseData<Employee>> getEmployeeById(@PathVariable String id) {
		ResponseData<Employee> response = employeeService.getEmployeeById(id);
		return finalResponse(response,  HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseData<List<Employee>>> getAllEmployees() {
		ResponseData<List<Employee>> response = employeeService.getAllEmployees();
		return new ResponseEntity<ResponseData<List<Employee>>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseData<Employee>> updateEmployee(@RequestBody Employee employee) {
		ResponseData<Employee> response = employeeService.updateEmployee(employee);
		return finalResponse(response,  HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseData<String>> deleteEmployee(@PathVariable String id) {
		ResponseData<String> response = employeeService.deleteEmployee(id);
		return new ResponseEntity< ResponseData<String>>(response, HttpStatus.resolve(response.getResponseCode()));
	}
	
	
}
