package ru.lanit.driversdb.service.drivers;

import generated.CarsType;
import generated.LicensesType;
import generated.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.driversdb.repository.DriversRepository;
import ru.lanit.driversdb.service.UUIDGenerator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DriversServiceImpl implements DriversService {

    private final DriversRepository repository;

    @Autowired
    public DriversServiceImpl(DriversRepository repository) {
        this.repository = repository;
    }

    public void save(PersonType driver) {
        if (driver.getId() == null) {
            driver.setId(UUIDGenerator.generateString());
            driver.setCars(new CarsType());
            driver.setLicenses(new LicensesType());
        }
        repository.save(driver);
    }

    public PersonType findById(String id) {
        Optional<PersonType> driver = repository.findById(id);
        return driver.orElseThrow(NoSuchElementException::new);
    }

    public List<PersonType> findAll() {
        return repository.findAll();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

}


