package com.hoxify.hoxify_new;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HoxifyNewApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoxifyNewApplication.class, args);
	}

}
