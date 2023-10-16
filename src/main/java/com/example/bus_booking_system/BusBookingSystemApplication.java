package com.example.bus_booking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BusBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusBookingSystemApplication.class, args);
    }

}
