package ru.lanit.driversdb.service.drivers;

import generated.PersonType;

import java.util.List;

public interface DriversService {

    void save(PersonType driver);
    PersonType findById(String id);
    List<PersonType> findAll();
    void deleteById(String id);

}
