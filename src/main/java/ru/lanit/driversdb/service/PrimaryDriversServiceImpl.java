package ru.lanit.driversdb.service;

import generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.driversdb.repository.PrimaryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrimaryDriversServiceImpl implements DriversService {

    private PrimaryRepository repository;

    @Autowired
    public PrimaryDriversServiceImpl(PrimaryRepository repository) {
        this.repository = repository;
    }

    public void save(PersonType driver) {
        if (driver.getId() == null) {
            driver.setId(generateUUID());
            driver.setCars(new CarsType());
            driver.setLicenses(new LicensesType());
        }
        repository.save(driver);
    }

    public PersonType findById(String id) {
        Optional<PersonType> driver = repository.findById(id);
        return driver.orElseThrow(NoSuchElementException::new);
    }

    public void update(PersonType driver) {
        repository.save(driver);
    }


    public List<PersonType> findAll() {
        return repository.findAll();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void addCarToDriver(PersonType driver, CarType car) {
        if (car.getId() == null) {
            car.setId(generateUUID());
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

    public void addLicenseToDriver(PersonType driver, LicenseType license) {
        if (license.getLicenseNumber() == null) {
            license.setLicenseNumber(generateUUID());
        }
        driver.getLicenses().getLicense().add(license);
    }

    public LicenseType findDriversLicenseById(PersonType driver, String licenseId) {
        return driver.getLicenses().getLicense().stream()
                .filter(licenseType -> licenseType.getLicenseNumber().equals(licenseId))
                .findAny().orElseThrow(NoSuchElementException::new);
    }

    public void removeLicenseFromDriverById(PersonType driver, String licenseId) {
        driver.getLicenses().getLicense().removeIf(licenseType -> licenseType.getLicenseNumber().equals(licenseId));
    }


    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

}


