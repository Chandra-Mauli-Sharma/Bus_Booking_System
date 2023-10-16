package com.example.bus_booking_system.user.model;

import com.example.bus_booking_system.admin.model.Bus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
public class Booking {
    @Id
    private String id;
    private Long seatId = UUID.randomUUID().node();
    @DBRef
    private Bus bus;
}
