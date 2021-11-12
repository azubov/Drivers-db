package ru.lanit.driversdb.kafka;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg")
public class MsgController {

    @Autowired
    private KafkaTemplate<Long, PersonType> kafkaTemplate;

    @PostMapping
    public void sendMsg(Long msgId){
        PersonType msg = new PersonType();
        msg.setId("IDSET");
        ListenableFuture<SendResult<Long, PersonType>> future = kafkaTemplate.send("msg", msgId, msg);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }
}
