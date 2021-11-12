package ru.lanit.driversdb.listener;

import generated.PersonType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.lanit.driversdb.service.PrimaryDriversServiceImpl;
import ru.lanit.driversdb.service.SecondaryDriversServiceImpl;

@Component
public class KafkaDriversListeners {

    @Autowired
    private PrimaryDriversServiceImpl primaryService;
    @Autowired
    private SecondaryDriversServiceImpl secondaryService;

    @KafkaListener(topics="primary")
    public void primaryListener(ConsumerRecord<Long, PersonType> record){
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println("PRIMARY SERVICE");
        PersonType driver = record.value();

        secondaryService.save(driver);
    }

    @KafkaListener(topics="secondary")
    public void secondaryListener(ConsumerRecord<Long, PersonType> record){
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println("SECONDARY SERVICE");
        PersonType driver = record.value();

        primaryService.save(driver);
    }
}
