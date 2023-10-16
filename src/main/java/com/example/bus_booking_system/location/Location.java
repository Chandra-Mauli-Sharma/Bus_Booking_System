package com.example.bus_booking_system.location;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Location {
    @Id
    private String id;
    private Double lat;
    private Double lng;
}
