package ru.lanit.driversdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.File;
import java.io.IOException;

@Configuration
@Lazy
public class DebeziumConnectorConfig {

    @Value("${sourceCountry.datasource.host}")
    private String sourceCountryDbHost;

    @Value("${sourceCountry.datasource.port}")
    private String sourceCountryDbPort;

    private String sourceCountryDbName;

    @Value("${sourceCountry.datasource.username}")
    private String sourceCountryDbUsername;

    @Value("${sourceCountry.datasource.password}")
    private String sourceCountryDbPassword;

    @Autowired
    public DebeziumConnectorConfig(@Lazy String sourceCountryDbName) throws IOException {
        this.sourceCountryDbName = sourceCountryDbName;
        sourceCountryConnector();
    }

    @Bean
    @Lazy
    public io.debezium.config.Configuration sourceCountryConnector() throws IOException {
        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        File dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");
        return io.debezium.config.Configuration.create()
                .with("name", "sourceCountry-mongo-connector")
                .with("connector.class", "io.debezium.connector.mongodb.MongoDbConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", sourceCountryDbHost)
                .with("database.port", sourceCountryDbPort)
                .with("database.dbname", sourceCountryDbName)
                .with("database.user", sourceCountryDbUsername)
                .with("database.password", sourceCountryDbPassword)
                .with("database.include.list", sourceCountryDbName)
                .with("include.schema.changes", "false")
                .with("database.allowPublicKeyRetrieval", "true")
                .with("database.server.id", "10181")
                .with("database.server.name", "sourceCountry-mongo-db-server")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", dbHistoryTempFile.getAbsolutePath())
                .build();
    }
}
