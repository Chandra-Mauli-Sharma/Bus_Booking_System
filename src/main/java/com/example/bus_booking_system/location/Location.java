package com.example.bus_booking_system.location;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Location {
    @Id
    private String id;

    @NotNull
    @Min(-90)
    @Max(90)
    private Double lat;

    @NotNull
    @Min(-180)
    @Max(180)
    private Double lng;
}
