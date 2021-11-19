package ru.lanit.driversdb.controller;

import generated.CarType;
import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.driversdb.kafka.KafkaDriversProducer;
import ru.lanit.driversdb.service.cars.CarsService;
import ru.lanit.driversdb.service.drivers.DriversService;

@Controller
@RequestMapping("/drivers")
public class CarsController {

    private final DriversService driversService;
    private final CarsService carsService;
    private final KafkaDriversProducer producer;
    private final String topic = "primary";

    @Autowired
    public CarsController(DriversService driversService, CarsService carsService, KafkaDriversProducer producer) {
        this.driversService = driversService;
        this.carsService = carsService;
        this.producer = producer;
    }

    @GetMapping("/cars/{driverId}")
    public String allCars(@PathVariable String driverId, Model model) {
        model.addAttribute("driver", driversService.findById(driverId));
        return "listCars";
    }

    @GetMapping("/cars/{driverId}/new")
    public String createCar() {
        return "createCar";
    }

    @PostMapping("/cars/{driverId}/new")
    public String addCar(@PathVariable String driverId, @ModelAttribute("car") CarType car) {
        PersonType driver = driversService.findById(driverId);
        carsService.addCarToDriver(driver, car);
        driversService.save(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/cars/{driverId}";
    }

    @GetMapping("/cars/{driverId}/update/{carId}")
    public String editCar(@PathVariable String driverId, @PathVariable String carId, Model model) {
        PersonType driver = driversService.findById(driverId);
        model.addAttribute("car", carsService.findDriversCarById(driver, carId));
        return "editCar";
    }

    @PostMapping("/cars/{driverId}/update/{carId}")
    public String updateCar(@PathVariable String driverId, @PathVariable String carId, @ModelAttribute("car") CarType car) {
        PersonType driver = driversService.findById(driverId);
        carsService.removeCarFromDriverById(driver, carId);
        carsService.addCarToDriver(driver, car);
        driversService.save(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/cars/{driverId}";
    }

    @GetMapping("/cars/{driverId}/delete/{carId}")
    public String deleteCarById(@PathVariable String driverId, @PathVariable String carId) {
        PersonType driver = driversService.findById(driverId);
        carsService.removeCarFromDriverById(driver, carId);
        driversService.save(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/cars/{driverId}";
    }
}
