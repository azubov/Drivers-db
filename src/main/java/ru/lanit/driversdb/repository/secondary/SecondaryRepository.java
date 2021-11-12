package ru.lanit.driversdb.repository.secondary;

import generated.PersonType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecondaryRepository extends MongoRepository<PersonType, String> {
}