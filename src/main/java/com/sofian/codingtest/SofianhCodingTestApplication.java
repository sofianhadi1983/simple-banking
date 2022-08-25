package com.sofian.codingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SofianhCodingTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SofianhCodingTestApplication.class, args);
	}

}
