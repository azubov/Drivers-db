package ru.lanit.driversdb.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.lanit.driversdb.repository.primary", mongoTemplateRef = "mongoTemplatePrimary")
public class PrimaryMongoConfiguration {

    @Value("${spring.data.mongodb.primary}")
    private String connectionUri;
    private String dbName = "ca";

    @Bean
    public MongoClient mongoClientPrimary() {
        ConnectionString connectionString = new ConnectionString(connectionUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplatePrimary() throws Exception {
        return new MongoTemplate(mongoClientPrimary(), dbName);
    }

}