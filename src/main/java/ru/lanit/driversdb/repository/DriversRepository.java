package ru.lanit.driversdb.repository;

import generated.PersonType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriversRepository extends MongoRepository<PersonType, String> {

}
