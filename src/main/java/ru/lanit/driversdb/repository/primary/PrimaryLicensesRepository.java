package ru.lanit.driversdb.repository.primary;

import generated.LicenseType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryLicensesRepository extends MongoRepository<LicenseType, String> {
}
