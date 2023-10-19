package com.example.bus_booking_system.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public Location addLocation(Location location){
        return locationRepository.save(location);
    }
}
