package ru.lanit.driversdb.listener;

import generated.PersonType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.lanit.driversdb.service.DriversService;

@Component
public class DriversListeners {

    private DriversService service;

    @Autowired
    public DriversListeners(DriversService service) {
        this.service = service;
    }

    @KafkaListener(topics="msg")
    public void msgListener(ConsumerRecord<Long, PersonType> record){
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println("TRY TO SERIALIZE");
//        PersonType driver = record.value();
//        service.save(driver);
    }
}
