package ru.lanit.driversdb.controller;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.driversdb.kafka.KafkaDriversProducer;
import ru.lanit.driversdb.service.drivers.DriversService;

@Controller
@RequestMapping("/drivers")
public class DriversController {

    private final DriversService driversService;
    private final KafkaDriversProducer producer;
    private final String topic = "primary";

    @Autowired
    public DriversController(DriversService driversService, KafkaDriversProducer producer) {
        this.driversService = driversService;
        this.producer = producer;
    }

    @GetMapping
    public String allDrivers(Model model) {
        model.addAttribute("drivers", driversService.findAll());
        return "listDrivers";
    }

    @GetMapping("/new")
    public String createDriver() {
        return "createDriver";
    }

    @PostMapping("/new")
    public String addDriver(@ModelAttribute("driver") PersonType driver) {
        driversService.save(driver);
        producer.sendToKafka(topic, "NEW", driver);
        return "redirect:/drivers";
    }

    @GetMapping("/update/{driverId}")
    public String editDriver(@PathVariable String driverId, Model model) {
        model.addAttribute("driver", driversService.findById(driverId));
        return "editDriver";
    }

    @PostMapping("/update/{driverId}")
    public String updateDriver(@PathVariable String driverId, @ModelAttribute("driver") PersonType driver) {
        driver.setCars(driversService.findById(driverId).getCars());
        driver.setLicenses(driversService.findById(driverId).getLicenses());
        driversService.save(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers";
    }

    @GetMapping("/delete/{driverId}")
    public String deleteDriverById(@PathVariable String driverId) {
        PersonType driver = driversService.findById(driverId);
        producer.sendToKafka(topic, "DELETE", driver);
        driversService.deleteById(driverId);
        return "redirect:/drivers";
    }

}
