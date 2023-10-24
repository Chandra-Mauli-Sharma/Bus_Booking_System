package com.example.bus_booking_system.admin.repository;

import com.example.bus_booking_system.admin.model.Bus;
import com.example.bus_booking_system.location.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends MongoRepository<Bus, Long> {
    int deleteBusById(String busID);

    List<Bus> findBusesBySrcAndDst(Location src, Location dst);


    Optional<Bus> getBusById(String id);
}
