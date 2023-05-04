package com.ameec.camino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class CaminoApplication {

	public static void main(String[] args) {
   
		String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");

        System.setProperty("spring.datasource.url", dbUrl);
        System.setProperty("spring.datasource.username", dbUser);
        System.setProperty("spring.datasource.password", dbPassword);

        String port = System.getenv("PORT");
        System.setProperty("server.address", "0.0.0.0");
        System.setProperty("server.port", port);

		SpringApplication.run(CaminoApplication.class, args);
	}

}
