package com.farmtrade;

import com.farmtrade.configurations.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@SpringBootApplication
public class FarmtradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmtradeApplication.class, args);
	}

}
