package com.saverfavor.microbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MicrobankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrobankApplication.class, args);
	}

}
