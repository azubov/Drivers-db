package ru.lanit.driversdb.service;

import generated.PersonType;

import java.util.List;

public interface DriversService {

    void save(PersonType driver);
    PersonType findById(String id);
    void update(PersonType driver);
    List<PersonType> findAll();
    void deleteById(String id);

//    LicenseType buildLicense(String licenseNumber, StatusType status);
//    CarType buildCar(String carId, String model, String horsepower);
//    String buildId();
//    PersonType buildDriver(String id,
//                           String country,
//                           String name,
//                           String birthdate,
//                           LicenseType license,
//                           CarType car);
//
//    PersonType buildDriverSimple(String country,
//                                 String name,
//                                 String birthdate);

}
