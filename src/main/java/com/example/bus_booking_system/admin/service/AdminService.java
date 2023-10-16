package com.example.bus_booking_system.admin.service;

import com.example.bus_booking_system.admin.model.Bus;
import com.example.bus_booking_system.admin.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BusRepository adminRepository;

    public Bus addBus(Bus bus) {
        return adminRepository.save(bus);
    }

    public Bus updateBus(Bus bus) {
        return adminRepository.save(bus);
    }

    public ResponseEntity<?> deleteBus(String busID) {
        adminRepository.deleteBusById(busID);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "Bus Details Deleted"));
    }
}
