package de.niklaskiefer.bnclDemo;

import de.niklaskiefer.bnclDemo.config.MongoConfig;
import de.niklaskiefer.bnclDemo.config.ThymeleafConfig;
import de.niklaskiefer.bnclDemo.model.BnclStatementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
@Import(ThymeleafConfig.class)
public class MainApplication {

    @Autowired
    private BnclStatementRepository bnclStatementRepository;

    public static void main(String[] args) {
        System.out.println("----Starting Demo Server-----");
        SpringApplication.run(MainApplication.class, args);
    }
}
