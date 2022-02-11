package com.tarya.repository;

import java.util.List;
/**
*
* @author SBortey
*/

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tarya.entity.Employee;
/**
*
* @author SBortey
*/
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

	List<Employee> findByName(String name);
	
	Employee findByEmailAndName (String email, String name);
	
	Employee findByNameOrEmail (String name, String email);
	
	List<Employee> findByEmailIsLike (String email);
	
	List<Employee> findByNameStartsWith (String name);
}
