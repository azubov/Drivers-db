package ru.lanit.driversdb.controller;

import generated.CarType;
import generated.PersonType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.lanit.driversdb.service.DriversService;

public abstract class AbstractController {

    private final DriversService service;
    private final KafkaTemplate<String, PersonType> kafkaTemplate;
    private final String countryDb;
    private final String topic;

    public AbstractController(DriversService service, KafkaTemplate<String, PersonType> kafkaTemplate, String countryDb, String topic) {
        this.service = service;
        this.kafkaTemplate = kafkaTemplate;
        this.countryDb = countryDb;
        this.topic = topic;
    }

    @GetMapping()
    public String allDrivers(Model model) {
        model.addAttribute("drivers", service.findAll());
        return "driversList";
    }

    @GetMapping("/new")
    public String createDriver() {
        return "createDriver";
    }

    @PostMapping("/new")
    public String addDriver(@ModelAttribute("driver") PersonType driver) {
        service.save(driver);
        sendToKafka(topic, "NEW", driver);
        return "redirect:/" + countryDb;
    }

    @GetMapping("/update/{id}")
    public String editDriver(@PathVariable String id, Model model) {
        model.addAttribute("driver", service.findById(id));
        return "editDriver";
    }

    @PostMapping("/update/{id}")
    public String updateDriver(@ModelAttribute("driver") PersonType driver) {
        service.update(driver);
        sendToKafka(topic, "UPDATE", driver);
        return "redirect:/" + countryDb;
    }

    @GetMapping("/delete/{id}")
    public String deleteDriverById(@PathVariable String id) {
        PersonType driver = service.findById(id);
        sendToKafka(topic, "DELETE", driver);
        service.deleteById(id);
        return "redirect:/" + countryDb;
    }

    @GetMapping("/cars/{driverId}")
    public String allCars(@PathVariable String driverId, Model model) {
        model.addAttribute("driver", service.findById(driverId));
        return "carsList";
    }

    @GetMapping("/cars/{driverId}/new")
    public String createCar() {
        return "createCar";
    }

    @PostMapping("/cars/{driverId}/new")
    public String addCar(@PathVariable String driverId, @ModelAttribute("car") CarType car) {
        PersonType driver = service.findById(driverId);
        service.addCarToDriver(driver, car);
        service.save(driver);
        sendToKafka(topic, "UPDATE", driver);
        return "redirect:/" + countryDb + "/cars/{driverId}";
    }

    @GetMapping("/cars/{driverId}/update/{carId}")
    public String editCar(@PathVariable String driverId, @PathVariable String carId, Model model) {
        PersonType driver = service.findById(driverId);
        model.addAttribute("car", service.findDriversCarById(driver, carId));
        return "editCar";
    }

    @PostMapping("/cars/{driverId}/update/{carId}")
    public String updateCar(@PathVariable String driverId, @PathVariable String carId, @ModelAttribute("car") CarType car) {
        PersonType driver = service.findById(driverId);
        service.removeCarFromDriverById(driver, carId);
        service.addCarToDriver(driver, car);
        service.update(driver);
        sendToKafka(topic, "UPDATE", driver);
        return "redirect:/" + countryDb + "/cars/{driverId}";
    }

    @GetMapping("/cars/{driverId}/delete/{carId}")
    public String deleteCarById(@PathVariable String driverId, @PathVariable String carId) {
        PersonType driver = service.findById(driverId);
        driver.getCars().getCar().removeIf(carType -> carType.getId().equals(carId));
        service.update(driver);
        sendToKafka(topic, "DELETE", driver);
        return "redirect:/" + countryDb;
    }

    @GetMapping("/cars/{id}/back")
    public String back() {
        return "redirect:/" + countryDb;
    }


    private void sendToKafka(String topic, String msgId, PersonType driver) {
        ListenableFuture<SendResult<String, PersonType>> future = kafkaTemplate.send(topic, msgId, driver);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }
}
