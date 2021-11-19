package ru.lanit.driversdb.kafka;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaDriversProducer {

    @Autowired
    private KafkaTemplate<String, PersonType> kafkaTemplate;

    public void sendToKafka(String topic, String msgId, PersonType driver) {
        System.out.println("SAVED TO DB");
        ListenableFuture<SendResult<String, PersonType>> future = kafkaTemplate.send(topic, msgId, driver);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
        System.out.println("SENT TO LISTENER");
    }
}
