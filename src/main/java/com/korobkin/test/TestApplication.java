package com.korobkin.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication()
public class TestApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		SpringApplication.run(TestApplication.class, args);
	}

}
