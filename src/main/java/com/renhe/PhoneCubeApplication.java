package com.renhe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"com.renhe"})
public class PhoneCubeApplication {

	public static void main(String[] args) {

		SpringApplication.run(PhoneCubeApplication.class, args);
	}

}

