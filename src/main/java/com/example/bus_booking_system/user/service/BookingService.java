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
        return busRepository.findBusesBySrcAndDst(srcLocation, dstLocation);
    }


    public Object seatBooking(String busId, String seatId) {
        var bus = busRepository.getBusById(busId);
        var seats = bus.getSeats();

        if (seats.get(seatId)) return Map.of("message", "Seat Already Booked");

        if ((seats.entrySet().stream().filter(Map.Entry::getValue).toList().size() / seats.entrySet().size()) * 100 >= 80)
            return Map.of("message", "Bus is at 80% occupancy. Book Another bus.");


        var booking = new Booking();
        booking.setSeatId(seatId);
        booking.setBus(busRepository.getBusById(busId));
        seats.put(seatId, true);
        bus.setSeats(seats);
        busRepository.save(bus);

        bookingRepository.save(booking);
        return Map.of("message", "Seat Booked");
    }

    public Integer getNumberOfSeats(String id) {
        return busRepository.getBusById(id).getSeats().size();
    }

    public Integer getAvailableNumberOfSeats(String id) {
        return busRepository.getBusById(id).getSeats().entrySet().stream().filter(set -> !set.getValue()).toList().size();
    }

    public ResponseEntity<?> cancelBooking(String bookingId) {
        var booking = bookingRepository.findBookingById(bookingId);
        if (booking == null) return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "Book a ride"));

        bookingRepository.deleteBookingById(bookingId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "ride cancelled"));
    }
}
