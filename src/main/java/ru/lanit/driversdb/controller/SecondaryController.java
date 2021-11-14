package ru.lanit.driversdb.controller;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.driversdb.service.SecondaryDriversServiceImpl;

@Controller
@RequestMapping("/us")
public class SecondaryController extends AbstractController {

    @Autowired
    public SecondaryController(SecondaryDriversServiceImpl service,
                               KafkaTemplate<String, PersonType> kafkaTemplate,
                               String secondaryCountryDb,
                               String secondaryKafkaTopic) {
        super(service, kafkaTemplate, secondaryCountryDb, secondaryKafkaTopic);
    }
}
