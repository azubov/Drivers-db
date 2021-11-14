package ru.lanit.driversdb.service;

import generated.PersonType;
import ru.lanit.driversdb.repository.DriversRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractService implements DriversService {

    private final DriversRepository repository;

    public AbstractService(DriversRepository repository) {
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

}
