package ru.lanit.driversdb.service;

import generated.PersonType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractService implements DriversService, CarsService, LicensesService {

    private final MongoRepository<PersonType, String> repository;

    public AbstractService(MongoRepository<PersonType, String> repository) {
        this.repository = repository;
    }

    public void save(PersonType driver) {
        if (driver.getId() == null) {
            assignId(driver);
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

    private void assignId(PersonType driver) {
        String id = generateUUID();
        driver.setId(id);
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
