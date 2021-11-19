package ru.lanit.driversdb.controller;

import generated.CarType;
import generated.LicenseType;
import generated.PersonType;
import generated.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import ru.lanit.driversdb.kafka.KafkaDriversProducer;
import ru.lanit.driversdb.service.DriversService;
import ru.lanit.driversdb.service.PrimaryDriversServiceImpl;

@Controller
@RequestMapping("/drivers")
public class DriversController {

    private final DriversService service;
    private final KafkaDriversProducer producer;
    private final String topic = "primary";

    @Autowired
    public DriversController(DriversService service, KafkaDriversProducer producer) {
        this.service = service;
        this.producer = producer;
    }

    // DRIVERS
    @GetMapping
    public String allDrivers(Model model) {
        model.addAttribute("drivers", service.findAll());
        return "listDrivers";
    }

    @GetMapping("/new")
    public String createDriver() {
        return "createDriver";
    }

    @PostMapping("/new")
    public String addDriver(@ModelAttribute("driver") PersonType driver) {
        service.save(driver);
        producer.sendToKafka(topic, "NEW", driver);
        return "redirect:/drivers";
    }

    @GetMapping("/update/{driverId}")
    public String editDriver(@PathVariable String driverId, Model model) {
        model.addAttribute("driver", service.findById(driverId));
        return "editDriver";
    }

    @PostMapping("/update/{driverId}")
    public String updateDriver(@ModelAttribute("driver") PersonType driver) {
        service.update(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers";
    }

    @GetMapping("/delete/{driverId}")
    public String deleteDriverById(@PathVariable String driverId) {
        PersonType driver = service.findById(driverId);
        producer.sendToKafka(topic, "DELETE", driver);
        service.deleteById(driverId);
        return "redirect:/drivers";
    }

    // CARS
    @GetMapping("/cars/{driverId}")
    public String allCars(@PathVariable String driverId, Model model) {
        model.addAttribute("driver", service.findById(driverId));
        return "listCars";
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
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/cars/{driverId}";
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
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/cars/{driverId}";
    }

    @GetMapping("/cars/{driverId}/delete/{carId}")
    public String deleteCarById(@PathVariable String driverId, @PathVariable String carId) {
        PersonType driver = service.findById(driverId);
        service.removeCarFromDriverById(driver, carId);
        service.update(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/cars/{driverId}";
    }

    // LICENSES
    @GetMapping("/licenses/{driverId}")
    public String allLicenses(@PathVariable String driverId, Model model) {
        model.addAttribute("driver", service.findById(driverId));
        return "listLicenses";
    }

    @GetMapping("/licenses/{driverId}/new")
    public String createLicense(Model model) {
        model.addAttribute("status", StatusType.class);
        return "createLicense";
    }

    @PostMapping("/licenses/{driverId}/new")
    public String addLicense(@PathVariable String driverId, @ModelAttribute("license") LicenseType license) {
        PersonType driver = service.findById(driverId);
        service.addLicenseToDriver(driver, license);
        service.save(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/licenses/{driverId}";
    }

    @GetMapping("/licenses/{driverId}/update/{licenseId}")
    public String editLicense(@PathVariable String driverId, @PathVariable String licenseId, Model model) {
        PersonType driver = service.findById(driverId);
        model.addAttribute("license", service.findDriversLicenseById(driver, licenseId));
        return "editLicense";
    }

    @PostMapping("/licenses/{driverId}/update/{licenseId}")
    public String updateLicense(@PathVariable String driverId, @PathVariable String licenseId, @ModelAttribute("license") LicenseType license) {
        PersonType driver = service.findById(driverId);
        service.removeLicenseFromDriverById(driver, licenseId);
        service.addLicenseToDriver(driver, license);
        service.update(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/licenses/{driverId}";
    }

    @GetMapping("/licenses/{driverId}/delete/{licenseId}")
    public String deleteLicenseById(@PathVariable String driverId, @PathVariable String licenseId) {
        PersonType driver = service.findById(driverId);
        service.removeLicenseFromDriverById(driver, licenseId);
        service.update(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/licenses/{driverId}";
    }

    @GetMapping(value={"/cars/{id}/back", "/licenses/{id}/back"})
    public String back() {
        return "redirect:/drivers";
    }

}
