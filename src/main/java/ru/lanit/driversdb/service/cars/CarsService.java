package ru.lanit.driversdb.service.cars;

import generated.CarType;
import generated.PersonType;

public interface CarsService {

    void addCarToDriver(PersonType driver, CarType car);
    CarType findDriversCarById(PersonType driver, String carId);
    void removeCarFromDriverById(PersonType driver, String carId);
}
