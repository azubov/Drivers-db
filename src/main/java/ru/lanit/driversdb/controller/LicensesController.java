package ru.lanit.driversdb.controller;

import generated.LicenseType;
import generated.PersonType;
import generated.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.driversdb.kafka.KafkaDriversProducer;
import ru.lanit.driversdb.service.drivers.DriversService;
import ru.lanit.driversdb.service.licenses.LicensesService;

@Controller
@RequestMapping("/drivers")
public class LicensesController {

    private final DriversService driversService;
    private final LicensesService licensesService;
    private final KafkaDriversProducer producer;
    private final String topic = "primary";

    @Autowired
    public LicensesController(DriversService driversService, LicensesService licensesService, KafkaDriversProducer producer) {
        this.driversService = driversService;
        this.licensesService = licensesService;
        this.producer = producer;
    }

    @GetMapping("/licenses/{driverId}")
    public String allLicenses(@PathVariable String driverId, Model model) {
        model.addAttribute("driver", driversService.findById(driverId));
        return "listLicenses";
    }

    @GetMapping("/licenses/{driverId}/new")
    public String createLicense(Model model) {
        model.addAttribute("status", StatusType.class);
        return "createLicense";
    }

    @PostMapping("/licenses/{driverId}/new")
    public String addLicense(@PathVariable String driverId, @ModelAttribute("license") LicenseType license) {
        PersonType driver = driversService.findById(driverId);
        licensesService.addLicenseToDriver(driver, license);
        driversService.save(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/licenses/{driverId}";
    }

    @GetMapping("/licenses/{driverId}/update/{licenseId}")
    public String editLicense(@PathVariable String driverId, @PathVariable String licenseId, Model model) {
        PersonType driver = driversService.findById(driverId);
        model.addAttribute("license", licensesService.findDriversLicenseById(driver, licenseId));
        return "editLicense";
    }

    @PostMapping("/licenses/{driverId}/update/{licenseId}")
    public String updateLicense(@PathVariable String driverId, @PathVariable String licenseId, @ModelAttribute("license") LicenseType license) {
        PersonType driver = driversService.findById(driverId);
        licensesService.removeLicenseFromDriverById(driver, licenseId);
        licensesService.addLicenseToDriver(driver, license);
        driversService.save(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/licenses/{driverId}";
    }

    @GetMapping("/licenses/{driverId}/delete/{licenseId}")
    public String deleteLicenseById(@PathVariable String driverId, @PathVariable String licenseId) {
        PersonType driver = driversService.findById(driverId);
        licensesService.removeLicenseFromDriverById(driver, licenseId);
        driversService.save(driver);
        producer.sendToKafka(topic, "UPDATE", driver);
        return "redirect:/drivers/licenses/{driverId}";
    }

    @GetMapping(value={"/cars/{id}/back", "/licenses/{id}/back"})
    public String back() {
        return "redirect:/drivers";
    }
}
