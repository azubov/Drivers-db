package ru.lanit.driversdb.controller;

import generated.PersonType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import ru.lanit.driversdb.service.SecondaryDriversServiceImpl;

@Controller
@RequestMapping("/us/drivers")
public class SecondaryController {

    private final String countryDb = "us";
    private final SecondaryDriversServiceImpl service;
    private final KafkaTemplate<String, PersonType> kafkaTemplate;
    private final String topic = "secondary";

    public SecondaryController(SecondaryDriversServiceImpl service, KafkaTemplate<String, PersonType> kafkaTemplate) {
        this.service = service;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping()
    public String allDrivers(Model model) {
        model.addAttribute("drivers", service.findAll());
        return "driversList";
    }

    @GetMapping("/new")
    public String createPage() {
        return "createDriver";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("driver") PersonType driver) {
        service.save(driver);
        sendToKafka(topic, "NEW", driver);
        return "redirect:/" + countryDb + "/drivers";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("driver", service.findById(id));
        return "editDriver";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("driver") PersonType driver) {
        service.update(driver);
        sendToKafka(topic, "UPDATE", driver);
        return "redirect:/" + countryDb + "/drivers";
    }


    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable String id) {
        PersonType driver = service.findById(id);
        sendToKafka(topic, "DELETE", driver);
        service.deleteById(id);
        return "redirect:/" + countryDb + "/drivers";
    }

    private void sendToKafka(String topic, String msgId, PersonType driver) {
        ListenableFuture<SendResult<String, PersonType>> future = kafkaTemplate.send(topic, msgId, driver);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }
}
