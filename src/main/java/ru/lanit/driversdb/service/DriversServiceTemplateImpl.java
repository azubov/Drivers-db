package ru.lanit.driversdb.service;

import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DriversServiceTemplateImpl implements DriversService {

    private final MongoTemplate repository;

    @Autowired
    public DriversServiceTemplateImpl(@Lazy MongoTemplate mongoTemplate) {
        this.repository = mongoTemplate;
    }

    public void save(PersonType driver) {
        String id = generateUUID();
        driver.setId(id);
        repository.save(driver);
    }

    public PersonType findById(String id) {
        return repository.findById(id, PersonType.class);
    }

    public void update(PersonType driver) {
        repository.save(driver);
    }


    public List<PersonType> findAll() {
        return repository.findAll(PersonType.class);
    }

    public void deleteById(String id) {
        PersonType driver = findById(id);
        repository.remove(driver);
    }

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
