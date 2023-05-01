package com.ameec.camino;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.swing.Spring;


@SpringBootTest
// @TestPropertySource(properties = {
//     "spring.datasource.url=" + "${DB_URL}",
//     "spring.datasource.username=" + "${DB_USERNAME}",
//     "spring.datasource.password=" + "${DB_PASSWORD}"
// })
class CaminoApplicationTests {

	@BeforeAll
	static void setUp() {
		// Get the current working directory
		// String workingDirectory = System.getProperty("user.dir");
		// System.out.println("Working Directory = " + workingDirectory);

		// Load the .env file from the src/main/resources directory
		// Path envPropertiesPath = Paths.get(workingDirectory, "src", "main", "resources", "env.properties");
		// System.out.println("envPropertiesPath = " + envPropertiesPath);
		// Properties properties = new Properties();
		// try (FileInputStream inputStream = new FileInputStream(envPropertiesPath.toFile())) {
		// 	properties.load(inputStream);
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		// System.out.println("Loaded properties = " + properties);
        // System.setProperty("DB_URL", properties.getProperty("DB_URL"));
		// System.setProperty("DB_USERNAME", properties.getProperty("DB_USERNAME"));
		// System.setProperty("DB_PASSWORD", properties.getProperty("DB_PASSWORD"));
    }

	// @Test
	// void contextLoads() {
	// 	ApplicationContext context = SpringApplication.run(CaminoApplication.class);
	// 	assertNotNull(context);
	// }

}
