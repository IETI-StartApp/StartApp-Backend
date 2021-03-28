package edu.escuelaing.ieti.startapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class StartappApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartappApplication.class, args);
	}

}
