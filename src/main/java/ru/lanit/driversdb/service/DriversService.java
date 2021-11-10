package ru.lanit.driversdb.service;

import generated.PersonType;
import io.debezium.data.Envelope.Operation;
import java.util.List;
import java.util.Map;

public interface DriversService {

    void save(PersonType driver);
    PersonType findById(String id);
    void update(PersonType driver);
    List<PersonType> findAll();
    void deleteById(String id);
    void replicateData(Map<String, Object> driversData, Operation operation);

}
