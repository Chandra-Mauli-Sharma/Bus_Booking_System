package com.example.bus_booking_system.admin.repository;

import com.example.bus_booking_system.admin.model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<Bus,Long> {
    void deleteBusById(Long busID);
}
