package com.demo.taskdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (exclude = { SimpleTaskAutoConfiguration.class })
public class ElectricBillProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricBillProcessingApplication.class, args);
	}

}
