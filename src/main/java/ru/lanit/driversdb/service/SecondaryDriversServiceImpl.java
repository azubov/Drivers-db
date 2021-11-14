package ru.lanit.driversdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.driversdb.repository.secondary.SecondaryRepository;

@Service
public class SecondaryDriversServiceImpl extends AbstractService {

    @Autowired
    public SecondaryDriversServiceImpl(SecondaryRepository repository) {
        super(repository);
    }
}