package ru.lanit.driversdb.service.cars;

import generated.CarType;
import generated.PersonType;
import org.springframework.stereotype.Service;
import ru.lanit.driversdb.service.UUIDGenerator;

import java.util.NoSuchElementException;

@Service
public class CarsServiceImpl implements CarsService {

    public void addCarToDriver(PersonType driver, CarType car) {
        if (car.getId() == null) {
            car.setId(UUIDGenerator.generateString());
        }
        driver.getCars().getCar().add(car);
    }

    public CarType findDriversCarById(PersonType driver, String carId) {
        return driver.getCars().getCar().stream()
                .filter(carType -> carType.getId().equals(carId))
                .findAny().orElseThrow(NoSuchElementException::new);
    }

    public void removeCarFromDriverById(PersonType driver, String carId) {
        driver.getCars().getCar().removeIf(carType -> carType.getId().equals(carId));
    }
}
