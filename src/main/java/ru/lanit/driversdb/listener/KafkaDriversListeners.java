package ru.lanit.driversdb.listener;

import generated.PersonType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaDriversListeners {

    @KafkaListener(topics="msg")
    public void msgListener(ConsumerRecord<Long, PersonType> record){
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println("TRY TO SERIALIZE");
        PersonType driver = record.value();
        System.out.println("DRIVER " + driver);
//        System.out.println(databaseConfiguration.doGetMongoD);

//        service.save(driver);
    }
}
