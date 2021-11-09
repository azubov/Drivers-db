package ru.lanit.driversdb.controller;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lanit.driversdb.service.DriversServiceTemplateImpl;

@Controller
@RequestMapping("/db/{countryId}/drivers")
public class DriversController {

    private static final String COUNTRY_ID = "/db/{countryId}";

    private final DriversServiceTemplateImpl service;

    @Autowired
    public DriversController(DriversServiceTemplateImpl service) {
        this.service = service;
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
