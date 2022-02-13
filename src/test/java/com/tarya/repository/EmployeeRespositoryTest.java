package com.tarya.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tarya.entity.Employee;


/**
*
* @author SBortey
*/


@ExtendWith(MockitoExtension.class)
public class EmployeeRespositoryTest {
	
	@Mock
	private EmployeeRepository employeeRepository;

	@Test
	public void testFindByName() {
		when(employeeRepository.findByName("Solomon"))
		.thenReturn(Arrays.asList(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer")));
		
		List<Employee> employees = employeeRepository.findByName("Solomon");
		assertEquals("Solomon",employees.get(0).getName());
	}
	@Test
	public void testFindAll() {
		when(employeeRepository.findAll())
		.thenReturn(Arrays.asList(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer")));
		List<Employee> employees = employeeRepository.findAll();
		assertEquals(1,employees.size());
	}

	@Test
	public void testFindOne() {
		final String Id = "620564da21a3ed511318a1af";
		Optional<Employee> employee = Optional.ofNullable(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"));
		when(employeeRepository.findById(Id))
		.thenReturn(employee);	
		Employee employee1 = employeeRepository.findById("620564da21a3ed511318a1af").get();		
		assertEquals(employee1.getId(), Id);
	}
	@Test
	public void testFindOne_Null() {
		final String Id = "620564da21a3ed511318a1af";
		Optional<Employee> employee = Optional.ofNullable(new Employee());
		lenient().when(employeeRepository.findById(Id))
		.thenReturn(employee);	
		assertEquals(null, employee.get().getId());
	}
	
	@Test
	public void testFindByEmailAndName() {
		final String name = "Solomon";
		final String email = "borteys@yahoo.com";
		when(employeeRepository.findByEmailAndName(email,name)).thenReturn(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"));
		Employee employee = employeeRepository.findByEmailAndName(email,name);		
		assertEquals("620564da21a3ed511318a1af",employee.getId());
	}
	
	@Test
	public void testFindByNameOrEmail() {
		final String name = "Solomon";
		final String email = "borteys@yahoo.com";
		when(employeeRepository.findByNameOrEmail(name,email)).thenReturn(new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer"));
		Employee employee = employeeRepository.findByNameOrEmail(name,email);		
		assertEquals("620564da21a3ed511318a1af",employee.getId());
	}
	
	@Test
	public void testFindByEmailIsLike() {
		String email = "borteys";
		when(employeeRepository.findByEmailIsLike(email)).thenReturn(Arrays.asList(new Employee("620564fb21a3ed511318a1b0","Borketey","borteys@taryafrica.com","Human Resource"),
				new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer")));
		List<Employee> employees = employeeRepository.findByEmailIsLike(email);
		
		assertEquals(2, employees.size());
	}
	
	@Test
	public void testFindByNameStartsWith() {
		String name = "Solo";
		when(employeeRepository.findByNameStartsWith(name)).thenReturn(Arrays.asList(new Employee("620564fb21a3ed511318a1b0","Solo","borteys@taryafrica.com","Human Resource"),
				new Employee("620564da21a3ed511318a1af","Solomon","borteys@yahoo.com","Software Engineer")));
		List<Employee> employees = employeeRepository.findByNameStartsWith(name);
		assertEquals(2, employees.size());
		
	}

}
