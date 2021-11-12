//package ru.lanit.driversdb.listener;
//
//import io.debezium.config.Configuration;
//import io.debezium.embedded.Connect;
//import io.debezium.engine.DebeziumEngine;
//import io.debezium.engine.RecordChangeEvent;
//import io.debezium.engine.format.ChangeEventFormat;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.tuple.Pair;
//import org.apache.kafka.connect.data.Field;
//import org.apache.kafka.connect.data.Struct;
//import org.apache.kafka.connect.source.SourceRecord;
//import org.springframework.stereotype.Component;
//import ru.lanit.driversdb.service.DriversService;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//import static io.debezium.data.Envelope.FieldName.*;
//import static io.debezium.data.Envelope.Operation;
//import static java.util.stream.Collectors.toMap;
//
//@Slf4j
//@Component
//public class DebeziumListener {
//
//    private final Executor executor = Executors.newSingleThreadExecutor();
//    private final DriversService service;
//    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
//
//    public DebeziumListener(Configuration countryConnectorConfiguration, DriversService service) {
//
//        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
//                .using(countryConnectorConfiguration.asProperties())
//                .notifying(this::handleChangeEvent)
//                .build();
//
//        this.service = service;
//    }
//
//    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
//        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
//
//        log.info("Key = '" + sourceRecord.key() + "' value = '" + sourceRecord.value() + "'");
//
//        Struct sourceRecordChangeValue= (Struct) sourceRecord.value();
//
//        if (sourceRecordChangeValue != null) {
//            Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));
//
//            if(operation != Operation.READ) {
//                String record = operation == Operation.DELETE ? BEFORE : AFTER;
//
//                Struct struct = (Struct) sourceRecordChangeValue.get(record);
//                Map<String, Object> payload = struct.schema().fields().stream()
//                        .map(Field::name)
//                        .filter(fieldName -> struct.get(fieldName) != null)
//                        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
//                        .collect(toMap(Pair::getKey, Pair::getValue));
//
//                this.service.replicateData(payload, operation);
//                log.info("Updated Data: {} with Operation: {}", payload, operation.name());
//            }
//        }
//    }
//
//    @PostConstruct
//    private void start() {
//        this.executor.execute(debeziumEngine);
//    }
//
//    @PreDestroy
//    private void stop() throws IOException {
//        if (this.debeziumEngine != null) {
//            this.debeziumEngine.close();
//        }
//    }
//
//}
