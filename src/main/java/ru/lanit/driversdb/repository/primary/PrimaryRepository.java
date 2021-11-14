package ru.lanit.driversdb.repository.primary;

import generated.PersonType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryRepository extends MongoRepository<PersonType, String> {
}
