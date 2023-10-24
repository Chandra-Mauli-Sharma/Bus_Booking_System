package com.example.bus_booking_system.user.service;

import com.example.bus_booking_system.admin.model.Bus;
import com.example.bus_booking_system.admin.repository.BusRepository;
import com.example.bus_booking_system.location.LocationRepository;
import com.example.bus_booking_system.user.model.Booking;
import com.example.bus_booking_system.user.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BusRepository busRepository;

    private final BookingRepository bookingRepository;

    private final LocationRepository locationRepository;


    public List<Bus> getBusesDetails(String src, String dst) {
        var srcLocation = locationRepository.getLocationById(src);
        var dstLocation = locationRepository.getLocationById(dst);

        if(srcLocation.isEmpty() || dstLocation.isEmpty()){
            return Collections.emptyList();
        }
        return busRepository.findBusesBySrcAndDst(srcLocation.get(), dstLocation.get());
    }


    public Object seatBooking(String busId, String seatId) {
        var busById = busRepository.getBusById(busId);


        if(busById.isEmpty()) return "";
        var bus=busById.get();
        var seats = bus.getSeats();

        if (seats.get(seatId)) return Map.of("message", "Seat Already Booked");

        if ((seats.entrySet().stream().filter(Map.Entry::getValue).toList().size() / (double)seats.entrySet().size()) * 100 >= 80)
            return Map.of("message", "Bus is at 80% occupancy. Book Another bus.");


        var booking = new Booking();
        booking.setSeatId(seatId);
        booking.setBus(bus);
        seats.put(seatId, true);
        bus.setSeats(seats);
        busRepository.save(bus);

        bookingRepository.save(booking);
        return Map.of("message", "Seat Booked");
    }

    public Integer getNumberOfSeats(String id) {
        if(busRepository.getBusById(id).isEmpty()) return null;
        return busRepository.getBusById(id).get().getSeats().size();
    }

    public Integer getAvailableNumberOfSeats(String id) {
        if(busRepository.getBusById(id).isEmpty()) return null;
        return busRepository.getBusById(id).get().getSeats().entrySet().stream().filter(set -> !set.getValue()).toList().size();
    }

    public ResponseEntity<?> cancelBooking(String bookingId) {
        var booking = bookingRepository.findBookingById(bookingId);
        if (booking == null) return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "Book a ride"));

        bookingRepository.deleteBookingById(bookingId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "ride cancelled"));
    }
}
