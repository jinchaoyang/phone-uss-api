package com.renhe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;

@ServletComponentScan(basePackages = "com.renhe")
@SpringBootApplication(scanBasePackages = {"com.renhe"})
public class PhoneCubeApplication {

	public static void main(String[] args) {

		SpringApplication.run(PhoneCubeApplication.class, args);
	}

}

