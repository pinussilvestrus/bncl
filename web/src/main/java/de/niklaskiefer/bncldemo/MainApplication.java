package de.niklaskiefer.bncldemo;

import de.niklaskiefer.bncldemo.config.ThymeleafConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ThymeleafConfig.class)
public class MainApplication {
    public static void main(String[] args) {
        System.out.println("----Starting Demo Server-----");
        SpringApplication.run(MainApplication.class, args);
    }
}
