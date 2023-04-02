package ru.academits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhonebookSpringWithLogsApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhonebookSpringWithLogsApplication.class, args);
	}
}
