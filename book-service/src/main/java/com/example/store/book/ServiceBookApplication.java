package com.example.store.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBookApplication.class, args);
	}

}
