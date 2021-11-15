package ru.lanit.driversdb.service;

import generated.CarType;
import generated.PersonType;
import java.util.List;

public interface DriversService {

    void save(PersonType driver);
    PersonType findById(String id);
    void update(PersonType driver);
    List<PersonType> findAll();
    void deleteById(String id);

    void addCarToADriver(PersonType driver, CarType car);
    CarType findDriversCarById(PersonType driver, String carId);


}
