package ru.lanit.driversdb;

import generated.PersonType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
public class DriversDbApplication {

    @KafkaListener(topics="msg")
    public void msgListener(ConsumerRecord<Long, PersonType> record){
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
    }

    public static void main(String[] args) {
        SpringApplication.run(DriversDbApplication.class, args);
    }

}
