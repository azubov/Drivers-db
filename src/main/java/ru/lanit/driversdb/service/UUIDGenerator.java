package ru.lanit.driversdb.service;

import java.util.UUID;

public class UUIDGenerator {

    public static String generateString() {
        return UUID.randomUUID().toString();
    }
}
