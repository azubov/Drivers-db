//package ru.lanit.driversdb.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import generated.PersonType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class DriversServiceTemplateImpl implements DriversService {
//
//    private final MongoTemplate repository;
//
//    @Autowired
//    public DriversServiceTemplateImpl(@Lazy MongoTemplate mongoTemplate) {
//        this.repository = mongoTemplate;
//    }
//
//    @Override
//    public void save(PersonType driver) {
//        String id = generateUUID();
//        driver.setId(id);
//        repository.save(driver);
//    }
//
//    @Override
//    public PersonType findById(String id) {
//        return repository.findById(id, PersonType.class);
//    }
//
//    @Override
//    public void update(PersonType driver) {
//        repository.save(driver);
//    }
//
//    @Override
//    public List<PersonType> findAll() {
//        return repository.findAll(PersonType.class);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        PersonType driver = findById(id);
//        repository.remove(driver);
//    }
//
//    public String generateUUID() {
//        return UUID.randomUUID().toString();
//    }
//}
