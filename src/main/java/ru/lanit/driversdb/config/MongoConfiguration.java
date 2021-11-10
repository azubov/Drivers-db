package ru.lanit.driversdb.config;

import com.mongodb.ConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.lanit.driversdb.filter.ConnectionStorage;

import java.io.IOException;

@Configuration
public class MongoConfiguration {

    @Bean
    @Lazy
    public MongoTemplate mongoTemplate() throws IOException {
        ConnectionString connectionString = new ConnectionString(ConnectionStorage.getConnection());
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(connectionString);
        MongoTemplate template = new MongoTemplate(databaseConfiguration);

        new DebeziumConnectorConfig(connectionString.getDatabase());

        return template;
    }
}