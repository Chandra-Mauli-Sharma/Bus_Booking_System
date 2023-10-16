package com.example.bus_booking_system.user.repository;

import com.example.bus_booking_system.user.model.Booking;
import com.example.bus_booking_system.user.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location,Long> {
    Location getLocationById(Long id);
}
