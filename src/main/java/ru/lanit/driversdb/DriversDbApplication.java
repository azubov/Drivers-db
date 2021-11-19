package ru.lanit.driversdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
//@SpringBootApplication(exclude = {
//        MongoAutoConfiguration.class,
//        MongoDataAutoConfiguration.class
//})
public class DriversDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriversDbApplication.class, args);
    }

}
