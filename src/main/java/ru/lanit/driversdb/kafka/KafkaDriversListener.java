package ru.lanit.driversdb.kafka;

import generated.PersonType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.lanit.driversdb.service.DriversService;
import ru.lanit.driversdb.service.PrimaryDriversServiceImpl;

@Component
public class KafkaDriversListener {

    @Autowired
    private PrimaryDriversServiceImpl primaryService;

    @KafkaListener(topics="primary")
    public void primaryListener(ConsumerRecord<String, PersonType> record){
        System.out.println("LISTENER !!!");
        System.out.println("PRIMARY SERVICE ACTION:");
        kafkaAction(record, primaryService);
    }

    private void kafkaAction(ConsumerRecord<String, PersonType> record, DriversService service) {
        String action = record.key();
        PersonType driver = record.value();
        if (action.equals("NEW") || action.equals("UPDATE")) {
            System.out.println(action);
            service.save(driver);
        } else if (action.equals("DELETE")){
            System.out.println(action);
            String driverId = driver.getId();
            service.deleteById(driverId);
        }
    }
}
