package com.example.bus_booking_system.location;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/addLocation")
    ResponseEntity<?> addLocation(@RequestBody Location location){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(locationService.addLocation(location));
    }
}
