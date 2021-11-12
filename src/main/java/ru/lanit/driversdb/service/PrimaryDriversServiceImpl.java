package ru.lanit.driversdb.service;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.driversdb.repository.primary.PrimaryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrimaryDriversServiceImpl implements DriversService {

    private final PrimaryRepository repository;

    @Autowired
    public PrimaryDriversServiceImpl(PrimaryRepository repository) {
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


