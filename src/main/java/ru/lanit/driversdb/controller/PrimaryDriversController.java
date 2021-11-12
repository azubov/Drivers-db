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

@Controller
@RequestMapping("/db/ca/drivers")
public class PrimaryDriversController {

    private static final String COUNTRY_ID = "/db/ca";
    private final PrimaryDriversServiceImpl service;
    private KafkaTemplate<Long, PersonType> kafkaTemplate;

    @Autowired
    public PrimaryDriversController(PrimaryDriversServiceImpl service, KafkaTemplate<Long, PersonType> kafkaTemplate) {
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

//        Long msgId = 686L;
//        PersonType msg = driver;
//        LicensesType licenses = new LicensesType();
//        LicenseType license = new LicenseType();
//        license.setStatus(StatusType.VALID);
//        license.setLicenseNumber("001");
//        licenses.getLicense().add(license);
//
//        CarsType cars = new CarsType();
//        CarType car = new CarType();
//        car.setId("888");
//        car.setModel("Aston");
//        car.setHorsepower("600");
//        cars.getCar().add(car);
//
//        ListenableFuture<SendResult<Long, PersonType>> future = kafkaTemplate.send("msg", msgId, msg);
//        future.addCallback(System.out::println, System.err::println);
//        kafkaTemplate.flush();

        return "redirect:" + COUNTRY_ID + "/drivers";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("driver", service.findById(id));
        return "editDriver";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("driver") PersonType driver) {
        service.update(driver);
        return "redirect:" + COUNTRY_ID + "/drivers";
    }


    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:" + COUNTRY_ID + "/drivers";
    }

}
