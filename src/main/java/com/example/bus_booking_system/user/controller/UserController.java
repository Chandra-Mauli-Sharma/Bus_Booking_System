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
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final BookingService bookingService;


    // Get Buses Details with src and dst along with eta and distance
    @GetMapping("/getBusesDetails/{date}")
    public ResponseEntity<?> getBusesDetails(@RequestBody Location userLocation, @RequestParam String srcId, @RequestParam String dstId,@PathVariable Long date) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingService.getBusesDetails(srcId, dstId,date).stream().sorted(Comparator.comparing(c -> (getDistance(c.getSrc(), userLocation)))));
    }

    @GetMapping("/checkAvailabilty")
    public ResponseEntity<?> checkAvailabilty(@RequestParam String busId) {
        var availableSeats = bookingService.getAvailableNumberOfSeats(busId);
        var noOfSeats = bookingService.getNumberOfSeats(busId);
        if (availableSeats == null || noOfSeats == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Bus missing"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("total_seats", noOfSeats, "available_seats", availableSeats));
    }

    @PostMapping("/bookSeat/{userID}")
    public ResponseEntity<?> bookSeat(@RequestParam String busId, @RequestParam String seatId,@PathVariable String userID) {
        if (busId == null || seatId == null || busId.isEmpty() || seatId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid request parameters"));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingService.seatBooking(busId, seatId,userID));
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancel(@PathVariable String bookingId) {
        if (bookingId == null || bookingId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid booking ID"));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookingService.cancelBooking(bookingId));
    }

    @GetMapping("/getAllBooking/{uid}")
    ResponseEntity<?> getAllBooking(@PathVariable String uid){
        return ResponseEntity.status(HttpStatus.FOUND).body(bookingService.getAllBooking(uid));
    }

    public double getDistance(Location s, Location t) {
        double R = 6371;
        double dLat = Math.toRadians(t.getLat() - s.getLat());
        double dLon = Math.toRadians(t.getLng() - s.getLng());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(s.getLat()) * Math.cos(t.getLat()) * Math.cos(dLon);
        double b = Math.sqrt(1 - a);
        return R * b;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
    }
}
