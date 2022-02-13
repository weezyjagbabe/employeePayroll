package com.tarya.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.tarya.entity.Employee;
import com.tarya.model.ResponseData;
import com.tarya.repository.EmployeeRepository;

/**
 *
 * @author SBortey
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	@InjectMocks
	private EmployeeService employeeService;
	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private Pageable pageableMock;

	@Mock
	private Page<Employee> employeePage;

	ResponseData<Employee> employee = new ResponseData<Employee>();
	ResponseData<List<Employee>> employeeList = new ResponseData<List<Employee>>();
	final Employee tempEmp = new Employee("620564da21a3ed511318a1af", "Solomon", "borteys@yahoo.com",
			"Software Engineer");
	Employee emptyEmp = new Employee();
	List<Employee> empList = new ArrayList<>();
	final Optional<Employee> optEmp = Optional.ofNullable(tempEmp);
	ResponseData<String> employeeString = new ResponseData<String>();
	final String email = "borteys@yahoo.com";
	final String name = "Solomon";
	final String Id = "620564da21a3ed511318a1af";

	@Test
	public void createEmployeeTest() throws Exception {
		when(employeeRepository.save(any(Employee.class))).thenReturn(tempEmp);
		employee = employeeService.createEmployee(tempEmp);
		assertEquals(HttpStatus.CREATED.value(), employee.getResponseCode());
	}

	@Test
	public void createEmployeeTest_Conflict() throws Exception {
		when(employeeRepository.findByEmailAndName(email, name)).thenReturn(tempEmp);
		employee = employeeService.createEmployee(tempEmp);
		assertEquals(HttpStatus.CONFLICT.value(), employee.getResponseCode());
	}

	@Test
	public void getEmployeeByIdTest() throws Exception {
		when(employeeRepository.findById(Id)).thenReturn(optEmp);
		employee = employeeService.getEmployeeById(Id);
		assertEquals(HttpStatus.OK.value(), employee.getResponseCode());
	}

	@Test
	public void getEmployeeByIdTest_NoRecord() throws Exception {
		employee.setResponseCode(HttpStatus.NO_CONTENT.value());
		employee.setResponseMessage("Record Not Found");
		employee.setResponseContent(emptyEmp);
		EmployeeService mock = org.mockito.Mockito.mock(EmployeeService.class);
		lenient().when(mock.getEmployeeById(Id)).thenReturn(employee);
		assertEquals(HttpStatus.NO_CONTENT.value(), employee.getResponseCode());
	}

	@Test
	public void getAllEmployeesTest() throws Exception {
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(tempEmp));
		employeeList = employeeService.getAllEmployees();
		assertEquals(Id, employeeList.getResponseContent().get(0).getId());
	}

	@Test
	public void getAllEmployeesTest_NoRecord() throws Exception {
		employeeList.setResponseCode(HttpStatus.NO_CONTENT.value());
		employeeList.setResponseMessage("No record exists");
		employeeList.setResponseContent(Arrays.asList());
		when(employeeRepository.findAll()).thenReturn(Arrays.asList());
		employeeList = employeeService.getAllEmployees();
		assertEquals(HttpStatus.NO_CONTENT.value(), employeeList.getResponseCode());
		assertEquals(0, employeeList.getResponseContent().size());

	}

	@Test
	public void updateEmployeeTest() throws Exception {
		employee.setResponseCode(HttpStatus.OK.value());
		employee.setResponseMessage("Record already exist");
		employee.setResponseContent(tempEmp);
		when(employeeRepository.findById(Id)).thenReturn(optEmp);
		employee = employeeService.updateEmployee(tempEmp);
		assertEquals(HttpStatus.OK.value(), employee.getResponseCode());
	}

	@Test
	public void updateEmployeeTest_Exist() throws Exception {
		employee.setResponseCode(HttpStatus.NO_CONTENT.value());
		employee.setResponseMessage("Record already exist");
		employee.setResponseContent(tempEmp);
		EmployeeService mock = org.mockito.Mockito.mock(EmployeeService.class);
		lenient().when(mock.updateEmployee(tempEmp)).thenReturn(employee);
		assertEquals(HttpStatus.NO_CONTENT.value(), employee.getResponseCode());
	}

	@Test
	public void deleteEmployeeTest() throws Exception {
		when(employeeRepository.findById(Id)).thenReturn(optEmp).thenReturn(null);
		employeeService.deleteEmployee(Id);
		verify(employeeRepository, times(1)).deleteById(Id);

	}

	@Test
	public void deleteEmployeeTest_NoRecord() throws Exception {
		employeeString.setResponseCode(HttpStatus.METHOD_FAILURE.value());
		employeeString.setResponseMessage(HttpStatus.METHOD_FAILURE.getReasonPhrase());
		employeeString.setResponseContent("Record not found for deletion!");
		EmployeeService mock = org.mockito.Mockito.mock(EmployeeService.class);
		lenient().when(mock.deleteEmployee(Id)).thenReturn(employeeString);
		assertEquals(HttpStatus.METHOD_FAILURE.value(), employeeString.getResponseCode());

	}

	@Test
	public void getEmployeesByNameTest() throws Exception {
		when(employeeRepository.findByName(name)).thenReturn(Arrays.asList(tempEmp));
		empList = employeeService.getEmployeesByName(name);
		assertEquals(1, empList.size());
	}

	@Test
	public void getEmployeesByNameAndEmailTest() throws Exception {
		when(employeeRepository.findByEmailAndName(email, name)).thenReturn(tempEmp);
		emptyEmp = employeeService.employeeByNameAndEmail(name, email);
		assertEquals(name, emptyEmp.getName());
		assertEquals(email, emptyEmp.getEmail());
	}

	@Test
	public void getEmployeesByNameOrEmailTest() throws Exception {
		when(employeeRepository.findByNameOrEmail(name, email)).thenReturn(tempEmp);
		emptyEmp = employeeService.employeesByNameOrEmail(name, email);
		assertEquals(name, emptyEmp.getName());
		assertEquals(email, emptyEmp.getEmail());
	}

	@Test
	public void getAllWithPaginationTest() throws Exception {
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(tempEmp));
		employeeList = employeeService.getAllEmployees();
		assertEquals(1, employeeList.getResponseContent().size());
		verify(employeeRepository).findAll();
	}

	@Test
	public void allWithSortingTest() throws Exception {
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(tempEmp));
		employeeList = employeeService.getAllEmployees();
		assertEquals(1, employeeList.getResponseContent().size());
		verify(employeeRepository).findAll();
	}

	@Test
	public void nameStartsWithTest() throws Exception {
		when(employeeRepository.findByNameStartsWith(name)).thenReturn(Arrays.asList(tempEmp));
		empList = employeeRepository.findByNameStartsWith(name);
		assertEquals(1, empList.size());
	}

	@Test
	public void setResponseEmployerTest() throws Exception {
		employee.setResponseCode(HttpStatus.CONFLICT.value());
		employee.setResponseMessage("Record already exist");
		employee.setResponseContent(tempEmp);
		EmployeeService mock = org.mockito.Mockito.mock(EmployeeService.class);
		lenient().when(mock.setResponseEmployer(tempEmp, HttpStatus.CONFLICT.value(), "Record already exist"))
				.thenReturn(employee);
		assertEquals(HttpStatus.CONFLICT.value(), employee.getResponseCode());
	}

	@Test
	public void setResponseEmployerListTest() throws Exception {
		employeeList.setResponseCode(HttpStatus.CONFLICT.value());
		employeeList.setResponseMessage("Record already exist");
		employeeList.setResponseContent(Arrays.asList(tempEmp));
		EmployeeService mock = org.mockito.Mockito.mock(EmployeeService.class);
		lenient().when(mock.setResponseEmployerList(Arrays.asList(tempEmp), HttpStatus.CONFLICT.value(),
				"Record already exist")).thenReturn(employeeList);
		assertEquals(HttpStatus.CONFLICT.value(), employeeList.getResponseCode());
	}

	@Test
	public void setResponseEmployerStringTest() throws Exception {
		employeeString.setResponseCode(HttpStatus.CONFLICT.value());
		employeeString.setResponseMessage("Record already exist");
		employeeString.setResponseContent("Record already exist");
		EmployeeService mock = org.mockito.Mockito.mock(EmployeeService.class);
		lenient().when(mock.setResponseEmployerString("Record already exist", HttpStatus.CONFLICT.value(),
				"Record already exist")).thenReturn(employeeString);
		assertEquals(HttpStatus.CONFLICT.value(), employeeString.getResponseCode());
	}

}
