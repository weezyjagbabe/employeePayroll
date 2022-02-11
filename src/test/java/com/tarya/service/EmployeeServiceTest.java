package com.tarya.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

  
	
	@Test
	public void createEmployeeTest() throws Exception{
		when(employeeRepository.save(any(Employee.class)))
		.thenReturn(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"));
		ResponseData<Employee> employee1 = employeeService.createEmployee(new Employee("Solomon","borteys@yahoo.com","Software Engineer"));
		assertEquals(HttpStatus.CREATED.value(), employee1.getResponseCode());
	}
	
	@Test
	public void getEmployeeByIdTest() throws Exception{
		final String Id = "620564da21a3ed511318a1af";
		Optional<Employee> employee = Optional.ofNullable(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"));
		when(employeeRepository.findById(Id))
		.thenReturn(employee);
		ResponseData<Employee> employee1 = employeeService.getEmployeeById(Id);
		assertEquals("Solomon", employee1.getResponseContent().getName());
	}
	
	@Test
	public void getAllEmployeesTest() throws Exception{
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"),
				new Employee("620564fb21a3ed511318a1b0","Anne","anne.nutsuklo@taryafrica.com","Human Resource")));
		ResponseData<List<Employee>> employees = employeeService.getAllEmployees();
		
		assertEquals("620564da21a3ed511318a1af", employees.getResponseContent().get(0).getId());
		assertEquals("620564fb21a3ed511318a1b0", employees.getResponseContent().get(1).getId());
	}
	
	@Test
	public void updateEmployeeTest() throws Exception{
		//final String role = "Senior Software Engineer";
		lenient().when(employeeRepository.save(any(Employee.class)))
		.thenReturn(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Senior Software Engineer"));
		ResponseData<Employee> employee1 = employeeService.updateEmployee(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Senior Software Engineer"));
		assertEquals(HttpStatus.NO_CONTENT.value(), employee1.getResponseCode());
	}
	

	
	@Test
	public void deleteEmployeeTest() throws Exception {
		String Id = "620564da21a3ed511318a1af";
		Optional<Employee> employee = Optional.ofNullable(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"));
		when(employeeRepository.findById("620564da21a3ed511318a1af"))
		.thenReturn(employee).thenReturn(null);
		employeeService.deleteEmployee(Id);
		verify(employeeRepository, times(1)).deleteById(Id);

	}
	
	@Test
	public void getEmployeesByNameTest() throws Exception {
		when(employeeRepository.findByName("Solomon")).thenReturn(Arrays.asList(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer")));
		List<Employee> employees = employeeService.getEmployeesByName("Solomon");	
		assertEquals( 1, employees.size());
	}
	
	@Test
	public void getEmployeesByNameAndEmailTest() throws Exception{
		when(employeeRepository.findByEmailAndName("borteys@yahoo.com","Solomon"))
		.thenReturn(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"));
		Employee employee = employeeService.employeeByNameAndEmail("Solomon","borteys@yahoo.com");	
		assertEquals("Solomon", employee.getName());
		assertEquals( "borteys@yahoo.com", employee.getEmail());
	}
	
	@Test
	public void getEmployeesByNameOrEmailTest() throws Exception{
		when(employeeRepository.findByNameOrEmail("Solomon","borteys@yahoo.com"))
		.thenReturn(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"));
		Employee employee = employeeService.employeesByNameOrEmail("Solomon","borteys@yahoo.com");	
		assertEquals("Solomon", employee.getName());
		assertEquals( "borteys@yahoo.com", employee.getEmail());
	}
	
	@Test
	public void getAllWithPaginationTest() throws Exception{
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"),
				new Employee("620564fb21a3ed511318a1b0","Anne","anne.nutsuklo@taryafrica.com","Human Resource")));
		ResponseData<List<Employee>> employees1 = employeeService.getAllEmployees();	
        assertEquals(2, employees1.getResponseContent().size());
        verify(employeeRepository).findAll();
	}
	
	@Test
	public void allWithSortingTest() throws Exception{
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(new Employee("620564fb21a3ed511318a1b0","Anne","anne.nutsuklo@taryafrica.com","Human Resource"),
				new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer")));
		ResponseData<List<Employee>> employees = employeeService.getAllEmployees();
		
		 assertEquals(2, employees.getResponseContent().size());

	     verify(employeeRepository).findAll();
	}
	
	@Test
	public void nameStartsWithTest() throws Exception{
		String name = "Solo";
		when(employeeRepository.findByNameStartsWith(name)).thenReturn(Arrays.asList(new Employee("620564fb21a3ed511318a1b0","Solo","borteys@taryafrica.com","Human Resource"),
				new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer")));
		List<Employee> employees = employeeRepository.findByNameStartsWith(name);
		assertEquals(2, employees.size());
	}
}
