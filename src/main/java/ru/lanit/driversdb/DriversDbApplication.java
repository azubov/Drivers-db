package ru.lanit.driversdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class DriversDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriversDbApplication.class, args);
    }

}
