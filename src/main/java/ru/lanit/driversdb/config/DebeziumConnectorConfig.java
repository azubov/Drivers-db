//package ru.lanit.driversdb.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//import java.io.IOException;
//
//@Configuration
//public class DebeziumConnectorConfig {
//
//    @Bean
//    public io.debezium.config.Configuration sourceCountryConnector() throws IOException {
//        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
//        File dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");
//        return io.debezium.config.Configuration.create()
//                .with("name", "mongo-connector")
//                .with("connector.class", "io.debezium.connector.mongodb.MongoDbConnector")
//                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
//                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
//                .with("tasks.max", "1")
//                .with("mongodb.members.auto.discover", false)
//                .with("mongodb.hosts", "localhost:27017")
//                .with("mongodb.name", "logical name")
//                .with("database.whitelist", "us,ca")
//                .with("transforms", "AddPrefix")
//                .with("transforms.AddPrefix.type", "org.apache.kafka.connect.transforms.RegexRouter")
//                .with("transforms.AddPrefix.regex", "mongo.([a-zA-Z_0-9]*).([a-zA-Z_0-9]*)")
//                .with("transforms.AddPrefix.replacement", "data.cdc.mongo_$1")
//                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
//                .with("database.history.file.filename", dbHistoryTempFile.getAbsolutePath())
//                .build();
//    }
//}
