package ru.lanit.driversdb.repository;

import generated.PersonType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaRepository extends MongoRepository<PersonType, String> {
}
