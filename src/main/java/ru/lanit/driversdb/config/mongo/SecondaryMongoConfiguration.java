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
@EnableMongoRepositories(basePackages = "ru.lanit.driversdb.repository.secondary", mongoTemplateRef = "mongoTemplateSecondary")
public class SecondaryMongoConfiguration {

    @Value("${spring.data.mongodb.secondary}")
    private String connectionUri;
    private String dbName = "us";

    @Bean
    public MongoClient mongoClientSecondary() {
        ConnectionString connectionString = new ConnectionString(connectionUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplateSecondary() throws Exception {
        return new MongoTemplate(mongoClientSecondary(), dbName);
    }
}
