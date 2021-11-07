package ru.lanit.driversdb.controller;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.lanit.driversdb.service.DriversService;

@Controller
public class DriversController {

    private final DriversService service;

    @Autowired
    public DriversController(DriversService service) {
        this.service = service;
    }

    @GetMapping("/drivers")
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
        return "redirect:/drivers";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("driver", service.findById(id));
        return "editDriver";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("driver") PersonType driver) {
        service.update(driver);
        return "redirect:/drivers";
    }


    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/drivers";
    }

}
