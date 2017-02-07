package de.niklaskiefer.bnclWeb;

import de.niklaskiefer.bnclWeb.config.ThymeleafConfig;
import de.niklaskiefer.bnclWeb.model.BnclStatementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

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
