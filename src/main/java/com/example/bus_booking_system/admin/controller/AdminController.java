package com.example.bus_booking_system.admin.controller;

import com.example.bus_booking_system.admin.model.Bus;
import com.example.bus_booking_system.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;

    @PostMapping("/addBus")
    ResponseEntity<?> addBus(@RequestBody Bus bus) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.addBus(bus));
    }

    @PutMapping("/updateBus")
    ResponseEntity<?> updateBus(@RequestBody Bus bus) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateBus(bus));
    }

    @DeleteMapping("/deleteBus/{busId}")
    ResponseEntity<?> deleteBus(@PathVariable String busId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.deleteBus(busId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
    }
}
