package com.g4bor.payment.integration.acquire_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication
@ComponentScan({"com.g4bor.payment"})
@EntityScan("com.g4bor.payment.database.model")
@EnableJpaRepositories("com.g4bor.payment.database.repository")
public class AcquirerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AcquirerApplication.class, args);
	}

}
