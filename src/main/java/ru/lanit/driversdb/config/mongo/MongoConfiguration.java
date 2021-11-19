package ru.lanit.driversdb.config.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfiguration {

    @Value("${spring.data.uri.primary}")
    private String connectionUri;

//    @Value("${spring.data.uri.secondary}")
//    private String connectionUri;

}