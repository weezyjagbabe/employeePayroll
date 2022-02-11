package com.tarya.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarya.application.CoreTestConfiguration;
import com.tarya.entity.Employee;
import com.tarya.model.ResponseData;
import com.tarya.service.EmployeeService;

/**
 *
 * @author SBortey
 */
@WebMvcTest(controllers = EmployeeController.class)
@ContextConfiguration(classes = CoreTestConfiguration.class)
public class EmployeeControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@InjectMocks
	EmployeeController employeeController;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}

	@Test
	public void getAllEmployees_basic() throws Exception {
		ResponseData<List<Employee>> response = new ResponseData();
		response.setResponseCode(HttpStatus.OK.value());
		response.setResponseMessage(HttpStatus.OK.getReasonPhrase());
		response.setResponseContent(
				Arrays.asList(new Employee("1", "Solomon", "borteys@yahoo.com", "Software Engineer"),
						new Employee("2", "Anne", "anne.nutsuklo@taryafrica.com", "Human Resource")));
		when(employeeService.getAllEmployees()).thenReturn(response);

		RequestBuilder request = MockMvcRequestBuilders.get("/employee/all").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(
				"{responseCode:200,responseMessage:'OK',responseContent:[{id:'1',name:Solomon,email:borteys@yahoo.com,role:'Software Engineer'},"
						+ "{id:'2',name:Anne,email:anne.nutsuklo@taryafrica.com,role:'Human Resource'}]}"));
	}

	@Test
	public void getAllEmployees_noitems() throws Exception {
		ResponseData<List<Employee>> response = new ResponseData();
		response.setResponseCode(HttpStatus.NO_CONTENT.value());
		response.setResponseMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
		response.setResponseContent(Arrays.asList());
		when(employeeService.getAllEmployees()).thenReturn(response);

		RequestBuilder request = MockMvcRequestBuilders.get("/employee/all").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content().json("{\"responseCode\":204,\"responseMessage\":\"No Content\",\"responseContent\":[]}")).andReturn();
	}

	@Test
	public void getEmployeeById_basic() throws Exception {
		final String Id = "1";
		ResponseData<Employee> response = new ResponseData();
		response.setResponseCode(HttpStatus.OK.value());
		response.setResponseMessage(HttpStatus.OK.getReasonPhrase());
		response.setResponseContent(new Employee("1", "Solomon", "borteys@yahoo.com", "Software Engineer"));
		when(employeeService.getEmployeeById(Id)).thenReturn(response);

		RequestBuilder request = MockMvcRequestBuilders.get("/employee/{id}", Id).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(
				"{responseCode:200,responseMessage:'OK',responseContent:{id:'1',name:Solomon,email:borteys@yahoo.com,role:'Software Engineer'}}"))
				.andReturn();
	}

	@Test
	public void getEmployeeById_noitems() throws Exception {
		final String Id = "1";
		ResponseData<Employee> response = new ResponseData();
		response.setResponseCode(HttpStatus.NO_CONTENT.value());
		response.setResponseMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
		response.setResponseContent(new Employee());
		when(employeeService.getEmployeeById(Id)).thenReturn(response);

		RequestBuilder request = MockMvcRequestBuilders.get("/employee/{id}", Id).accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content().json("{responseCode:204,responseMessage:'No Content',responseContent:{}}")).andReturn();
	}

	@Test
	public void createEmployee_basic() throws Exception {
		ResponseData<Employee> response = new ResponseData();
		response.setResponseCode(HttpStatus.CREATED.value());
		response.setResponseMessage(HttpStatus.CREATED.getReasonPhrase());
		response.setResponseContent(new Employee("1", "William", "willi@yahoo.com", "Support"));
		when(employeeService.createEmployee(new Employee("1", "William", "willi@yahoo.com", "Support")))
				.thenReturn(response);
		String c = mockMvc.perform(MockMvcRequestBuilders.post("/employee/create")
				.content(asJsonString(new Employee("1","William","willi@yahoo.com","Support")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andReturn().getResponse().getContentAsString();
//				.andExpect(status().isCreated())
//				.andExpect(content().json(
//						"{\"responseCode\":201,\"responseMessage\":\"CREATED\",\"responseContent\":{\"id\":\"1\",\"name\":\"William\",\"email\":\"willi@yahoo.com\",\"role\":\"Support\"}}"))
//				.andReturn();
		System.out.println("################### "+c);
	}

	@Test
	public void updateEmployee_basic() throws Exception {
		ResponseData<Employee> response = new ResponseData();
		response.setResponseCode(HttpStatus.CREATED.value());
		response.setResponseMessage(HttpStatus.CREATED.getReasonPhrase());
		response.setResponseContent(new Employee("1", "William", "willi@yahoo.com", "Support"));
		when(employeeService.updateEmployee(new Employee("1","William","willi@yahoo.com","Support")))
				.thenReturn(response);
		String b = mockMvc.perform(MockMvcRequestBuilders.put("/employee/update")
				.content(asJsonString(new Employee("1","William", "willi@yahoo.com", "Support")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				
		.andReturn().getResponse().getContentAsString();
//				.andExpect(content().json(
//						"{\"responseCode\":201,\"responseMessage\":\"CREATED\",\"responseContent\":{\"id\":\"1\",\"name\":\"William\",\"email\":\"willi@yahoo.com\",\"role\":\"Support\"}}"));
//				.andReturn();
		System.out.println("$$$$$$$$$$$$$$$$$$ "+b);

	}

	@Test
	public void deleteEmployee_basic() throws Exception {
		String Id = "1";
		ResponseData<String> response = new ResponseData();
		response.setResponseCode(HttpStatus.ACCEPTED.value());
		response.setResponseMessage(HttpStatus.ACCEPTED.getReasonPhrase());
		when(employeeService.deleteEmployee(Id)).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.delete("/employee/{id}", "1")).andExpect(status().isAccepted());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
