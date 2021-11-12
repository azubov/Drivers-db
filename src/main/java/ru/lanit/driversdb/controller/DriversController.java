package ru.lanit.driversdb.controller;

import generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import ru.lanit.driversdb.service.DriversService;
import ru.lanit.driversdb.service.PrimaryDriversServiceImpl;
import ru.lanit.driversdb.service.SecondaryDriversServiceImpl;

@Controller
@RequestMapping("/{countryDb}/drivers")
public class DriversController {

    private String countryDb = "{countryDb}";
    private DriversService service;
    private KafkaTemplate<String, PersonType> kafkaTemplate;
    private String topic;

    @Autowired
    public DriversController(PrimaryDriversServiceImpl primaryDriversService,
                             SecondaryDriversServiceImpl secondaryDriversService,
                             KafkaTemplate<String, PersonType> kafkaTemplate) {
        this.service = pickService(primaryDriversService, secondaryDriversService);
        this.kafkaTemplate = kafkaTemplate;
        this.topic = pickTopic();
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
        return "redirect:/" + countryDb + "/drivers";
    }


    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/" + countryDb + "/drivers";
    }

    private void sendToKafka(String topic, String msgId, PersonType driver) {
        ListenableFuture<SendResult<String, PersonType>> future = kafkaTemplate.send(topic, msgId, driver);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }

    private DriversService pickService(PrimaryDriversServiceImpl primaryDriversService,
                                       SecondaryDriversServiceImpl secondaryDriversService) {
        return isPrimary() ? primaryDriversService : secondaryDriversService;
    }

    private String pickTopic() {
        return isPrimary() ? "primary" : "secondary";
    }

    private boolean isPrimary() {
        return countryDb.equals("ca");
    }

}
