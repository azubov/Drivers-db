package ru.lanit.driversdb.config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import ru.lanit.driversdb.filter.ConnectionStorage;

import java.util.Objects;

public class DatabaseConfiguration extends SimpleMongoClientDatabaseFactory {

    public DatabaseConfiguration(ConnectionString connectionString) {
        super(connectionString);
    }

    @Override
    protected MongoDatabase doGetMongoDatabase(String dbName) {

        ConnectionString connectionString = new ConnectionString(ConnectionStorage.getConnection());
        return super.doGetMongoDatabase(Objects.requireNonNull(connectionString.getDatabase()));
    }
}