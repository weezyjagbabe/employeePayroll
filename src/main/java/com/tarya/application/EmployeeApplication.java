package com.tarya.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
/**
*
* @author SBortey
*/

@SpringBootApplication
@EnableMongoRepositories("com.tarya.repository")
@ComponentScan("com.tarya.*")
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
