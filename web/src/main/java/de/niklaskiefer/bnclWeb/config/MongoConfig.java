package de.niklaskiefer.bnclWeb.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import static java.util.Collections.singletonList;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
    @Override
    public String getDatabaseName() {
        return DatabaseProperties.getDatabase();
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(singletonList(new ServerAddress(DatabaseProperties.getUrl(), 27017)));
    }
}
