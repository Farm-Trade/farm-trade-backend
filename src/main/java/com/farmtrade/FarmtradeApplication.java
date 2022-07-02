package com.farmtrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class FarmtradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmtradeApplication.class, args);
	}

}
