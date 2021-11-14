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

    @Value("${spring.data.uri.secondary}")
    private String connectionUri;
    @Value("${spring.data.db.secondary}")
    private String countryDb;
    @Value("${spring.kafka.topic.secondary}")
    private String kafkaTopic;

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
        return new MongoTemplate(mongoClientSecondary(), countryDb);
    }

    @Bean
    public String secondaryCountryDb() {
        return countryDb;
    }

    @Bean
    public String secondaryKafkaTopic() {
        return kafkaTopic;
    }
}
