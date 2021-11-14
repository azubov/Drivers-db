package ru.lanit.driversdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.driversdb.repository.primary.PrimaryRepository;

@Service
public class PrimaryDriversServiceImpl extends AbstractService {

    @Autowired
    public PrimaryDriversServiceImpl(PrimaryRepository repository) {
        super(repository);
    }
}


