package ru.lanit.driversdb.controller;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.lanit.driversdb.service.PrimaryDriversServiceImpl;

@Controller
@RequestMapping("/ca")
public class PrimaryController extends AbstractController {

    @Autowired
    public PrimaryController(PrimaryDriversServiceImpl service,
                             KafkaTemplate<String, PersonType> kafkaTemplate,
                             String primaryCountryDb,
                             String primaryKafkaTopic) {
        super(service, kafkaTemplate, primaryCountryDb, primaryKafkaTopic);
    }
}
