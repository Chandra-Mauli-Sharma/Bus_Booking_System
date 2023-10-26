package com.example.bus_booking_system.user.repository;

import com.example.bus_booking_system.admin.model.Bus;
import com.example.bus_booking_system.user.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking,Long> {
    void deleteBookingById(String  id);

    Booking findBookingById(String id);

    Booking findBookingByUserId(String userId);

    List<Booking> findBookingsByUserId(String userId);
}
