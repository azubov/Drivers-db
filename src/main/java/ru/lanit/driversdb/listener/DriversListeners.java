package ru.lanit.driversdb.listener;

import com.mongodb.ConnectionString;
import generated.PersonType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.lanit.driversdb.config.DatabaseConfiguration;
import ru.lanit.driversdb.service.DriversService;
import ru.lanit.driversdb.service.DriversServiceTemplateImpl;

@Component
public class DriversListeners {
    String connectionString = "mongodb://localhost:27017/us";
    ConnectionString connection = new ConnectionString(connectionString);
    DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(connection);
    MongoTemplate template = new MongoTemplate(databaseConfiguration);
    DriversService service = new DriversServiceTemplateImpl(template);

    @KafkaListener(topics="msg")
    public void msgListener(ConsumerRecord<Long, PersonType> record){
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println("TRY TO SERIALIZE");
        PersonType driver = record.value();
        System.out.println("DRIVER " + driver);
//        System.out.println(databaseConfiguration.doGetMongoD);

//        service.save(driver);
    }
}
