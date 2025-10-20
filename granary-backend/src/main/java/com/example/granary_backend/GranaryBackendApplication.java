package com.example.granary_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.example.granary_backend.infrastructure.external.mpesa.configuration.MpesaConfig;

@SpringBootApplication
@EnableConfigurationProperties(MpesaConfig.class)
@ComponentScan(basePackages = "com.example.granary_backend")
@EntityScan("com.example.granary_backend") // <--- REFINED PACKAGE
public class GranaryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GranaryBackendApplication.class, args);
	}

}