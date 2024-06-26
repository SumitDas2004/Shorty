package com.shorty.redirector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RedirectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedirectorApplication.class, args);
	}

}
