package com.example.bus_booking_system.admin.controller;

import com.example.bus_booking_system.admin.model.Bus;
import com.example.bus_booking_system.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;

    @PostMapping("/addBus")
    ResponseEntity<?> addBus(@RequestBody Bus bus) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.addBus(bus));
    }

    @PostMapping("/updateBus")
    ResponseEntity<?> updateBus(@RequestBody Bus bus) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateBus(bus));
    }


    @PostMapping("/deleteBus")
    ResponseEntity<?> deleteBus(@RequestParam String busId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.deleteBus(busId));
    }
}
