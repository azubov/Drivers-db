package ru.lanit.driversdb.service;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.driversdb.repository.DriversRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriversServiceImpl implements DriversService {

    private final DriversRepository repository;

    @Autowired
    public DriversServiceImpl(DriversRepository repository) {
        this.repository = repository;
    }

    public void save(PersonType driver) {
        String id = generateUUID();
        driver.setId(id);
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

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

//    public LicenseType buildLicense(String licenseNumber, StatusType status) {
//        LicenseType license = new LicenseType();
//        license.setLicenseNumber(licenseNumber);
//        license.setStatus(status);
//        return license;
//    }
//
//    public CarType buildCar(String carId, String model, String horsepower) {
//        CarType car = new CarType();
//        car.setId(carId);
//        car.setModel(model);
//        car.setHorsepower(horsepower);
//        return car;
//    }
//

//
//    public PersonType buildDriver(String id,
//                                  String country,
//                                  String name,
//                                  String birthdate,
//                                  LicenseType license,
//                                  CarType car) {
//
//        PersonType driver = new PersonType();
//
//        driver.setId(id);
//        driver.setCountry(country);
//        driver.setName(name);
//        driver.setBirthdate(birthdate);
//        driver.getLicenses().getLicense().add(license);
//        driver.getCars().getCar().add(car);
//
//        return driver;
//    }
//
//    public PersonType buildDriverSimple(String country,
//                                    String name,
//                                    String birthdate) {
//
//        PersonType driver = new PersonType();
//
//        String id = buildId();
//
//        driver.setId(id);
//        driver.setCountry(country);
//        driver.setName(name);
//        driver.setBirthdate(birthdate);
//
//        return driver;
//    }

}
