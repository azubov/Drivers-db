package ru.lanit.driversdb.repository.primary;

import generated.CarType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryCarsRepository extends MongoRepository<CarType, String> {
}
