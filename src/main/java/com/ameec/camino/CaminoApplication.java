package com.ameec.camino;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;


@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class CaminoApplication {

	public static void main(String[] args) {
        Properties props = new Properties();
        try {
            InputStream stream = CaminoApplication.class.getClassLoader().getResourceAsStream("env.properties");
            props.load(stream);
        } catch (IOException e) {
            System.err.println("Failed to load env.properties: " + e.getMessage());
            System.exit(1);
        }

		String dbUrl = props.getProperty("DB_URL");
        String dbUser = props.getProperty("DB_USERNAME");
        String dbPassword = props.getProperty("DB_PASSWORD");

        System.setProperty("spring.datasource.url", dbUrl);
        System.setProperty("spring.datasource.username", dbUser);
        System.setProperty("spring.datasource.password", dbPassword);

		SpringApplication.run(CaminoApplication.class, args);
	}

}
