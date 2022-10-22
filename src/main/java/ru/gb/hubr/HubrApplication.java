package ru.gb.hubr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HubrApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubrApplication.class, args);
	}

}
