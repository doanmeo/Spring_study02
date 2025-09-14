package com.hocSpring.bai_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.hocSpring.bai_01", "Controller" })
public class Bai01Application {

	public static void main(String[] args) {
		SpringApplication.run(Bai01Application.class, args);
	}

}
