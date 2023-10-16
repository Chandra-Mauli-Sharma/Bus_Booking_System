package com.example.bus_booking_system.admin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Booking {
    @Id
    private Long id;
    private int seatNumber;
    private Bus bus;
}
