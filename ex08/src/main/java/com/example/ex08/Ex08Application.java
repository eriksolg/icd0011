package com.example.ex08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controller"})
public class Ex08Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex08Application.class, args);
	}

}
