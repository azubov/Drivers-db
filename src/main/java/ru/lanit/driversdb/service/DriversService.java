package ru.lanit.driversdb.service;

import generated.PersonType;
import java.util.List;

public interface DriversService {

    void save(PersonType driver);
    PersonType findById(String id);
    void update(PersonType driver);
    List<PersonType> findAll();
    void deleteById(String id);

}
