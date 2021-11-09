package ru.lanit.driversdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import ru.lanit.driversdb.service.DriversService;

@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
public class DriversDbApplication implements CommandLineRunner {

    @Autowired
    private DriversService service;

    public static void main(String[] args) {
        SpringApplication.run(DriversDbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        service.save(
//                service.buildDriverSimple(
//                        "UK",
//                        "Artem",
//                        "16.01.1991"));

//        repository.deleteAll();
//
//        // save a couple of customers
//        repository.save(new Customer("Alice", "Smith"));
//        repository.save(new Customer("Bob", "Smith"));
//
//        // fetch all customers
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (PersonType customer : service.findAll()) {
//            System.out.println(customer.toString());
//        }
//        System.out.println();
//
//        // fetch an individual customer
//        System.out.println("Customer found with findByFirstName('Alice'):");
//        System.out.println("--------------------------------");
//        System.out.println(repository.findByFirstName("Alice"));
//
//        System.out.println("Customers found with findByLastName('Smith'):");
//        System.out.println("--------------------------------");
//        for (Customer customer : repository.findByLastName("Smith")) {
//            System.out.println(customer);
//        }
    }

}
