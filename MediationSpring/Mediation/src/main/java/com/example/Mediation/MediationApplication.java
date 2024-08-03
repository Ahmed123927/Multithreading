package com.example.Mediation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MediationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediationApplication.class, args);
	}

}
