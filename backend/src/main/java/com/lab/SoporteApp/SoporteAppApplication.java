package com.lab.SoporteApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoporteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoporteAppApplication.class, args);
	}

	// @Bean
    // CommandLineRunner validateConfig(@Value("${jwt.secret}") String secret) {
    //     return args -> {
    //         System.out.println("DEBUG: La llave cargada es: " + secret);
    //     };
	// }
}
