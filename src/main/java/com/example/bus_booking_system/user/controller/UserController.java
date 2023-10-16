package com.example.bus_booking_system.user.controller;


import com.example.bus_booking_system.location.Location;
import com.example.bus_booking_system.user.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final BookingService bookingService;



    // Get Buses Details with src and dst along with eta and distance
    @GetMapping("/getBusesDetails")
    public ResponseEntity<?> getBusesDetails(@RequestBody Location userLocation, @RequestParam String srcId, @RequestParam String dstId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingService.getBusesDetails(srcId, dstId).stream().sorted(Comparator.comparing(c -> (int) (getDistance(c.getSrc(), userLocation)))));
    }

    @GetMapping("/checkAvailabilty")
    public ResponseEntity<?> checkAvailabilty(@RequestParam String busId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("total_seats", bookingService.getNumberOfSeats(busId), "available_seats", bookingService.getAvailableNumberOfSeats(busId)));
    }

    @PostMapping("/bookSeat")
    public ResponseEntity<?> bookSeat(@RequestParam String busId, @RequestParam Long seatId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingService.seatBooking(busId, seatId));
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(String bookingId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingService.cancelBooking(bookingId));
    }

    public double getDistance(Location s, Location t) {
        return Math.sqrt(Math.pow(s.getLat() - t.getLat(), 2) + Math.pow(s.getLng() - t.getLng(), 2));
    }
}
