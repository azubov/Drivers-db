package ru.lanit.driversdb.repository;

import generated.PersonType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriversRepository extends MongoRepository<PersonType, String> {
}
