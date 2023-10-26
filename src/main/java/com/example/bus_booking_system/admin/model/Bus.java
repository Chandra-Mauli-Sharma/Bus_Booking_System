package com.example.bus_booking_system.admin.model;

import com.example.bus_booking_system.location.Location;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
@Data
public class Bus {
    Map<String, Boolean> seats;
    @Id
    private String id;
    private String name;

    @DBRef
    private Location src;

    @DBRef
    private Location dst;

    private String eta;
    private double distance;

    private List<Integer> daysOfOperation;
}




