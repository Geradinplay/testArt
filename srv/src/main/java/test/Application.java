package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
    "test",
    "com.sap.capire.vectortest.controllers",
    "com.sap.capire.vectortest.services",
    "com.sap.capire.vectortest.embedding"
})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
