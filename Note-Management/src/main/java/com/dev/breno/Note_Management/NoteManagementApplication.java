package com.dev.breno.Note_Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@SpringBootApplication
public class NoteManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteManagementApplication.class, args);
	}

}
