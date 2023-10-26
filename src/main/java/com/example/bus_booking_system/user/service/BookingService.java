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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BusRepository busRepository;

    private final BookingRepository bookingRepository;

    private final LocationRepository locationRepository;


    public List<Bus> getBusesDetails(String src, String dst,Long day) {
        var srcLocation = locationRepository.getLocationById(src);
        var dstLocation = locationRepository.getLocationById(dst);

        if(srcLocation.isEmpty() || dstLocation.isEmpty()){
            return Collections.emptyList();
        }


        System.out.println("Date Test"+ LocalDateTime.ofEpochSecond(day,9999999, ZoneOffset.UTC).getDayOfWeek().getValue());
        return busRepository.findBusesBySrcAndDst(srcLocation.get(), dstLocation.get()).stream().filter(bus->
            bus.getDaysOfOperation().contains(LocalDateTime.ofEpochSecond(day,9999999, ZoneOffset.UTC).getDayOfWeek().getValue()+1)
        ).toList();
    }


    public Object seatBooking(String busId, String seatId,String userID) {
        var busById = busRepository.getBusById(busId);


        if(busById.isEmpty()) return Map.of("message", "Invalid Bus Id");
        var bus=busById.get();
        var seats = bus.getSeats();

        if (seats.get(seatId)) return Map.of("message", "Seat Already Booked");

        if ((seats.entrySet().stream().filter(Map.Entry::getValue).toList().size() / (double)seats.entrySet().size()) * 100.0 >= 80.0) {
            return Map.of("message", "Bus is at 80% occupancy. Book Another bus.");
        }

//        if(bookingRepository.findBookingByUserId(userID)==null) return Map.of("message","Seat already booked by other user");


        var booking = new Booking();
        booking.setSeatId(seatId);
        booking.setBus(bus);
        booking.setUserId(userID);
        seats.put(seatId, true);
        bus.setSeats(seats);
        busRepository.save(bus);
        bookingRepository.save(booking);
        return Map.of("message", "Seat Booked","data",booking);
    }

    public Integer getNumberOfSeats(String id) {
        if(busRepository.getBusById(id).isEmpty()) return null;
        return busRepository.getBusById(id).get().getSeats().size();
    }

    public Integer getAvailableNumberOfSeats(String id) {
        if(busRepository.getBusById(id).isEmpty()) return null;
        return busRepository.getBusById(id).get().getSeats().entrySet().stream().filter(set -> !set.getValue()).toList().size();
    }

    public Object cancelBooking(String bookingId) {
        var booking = bookingRepository.findBookingById(bookingId);
        if (booking == null) return Map.of("message", "Book a ride");

        bookingRepository.deleteBookingById(bookingId);
        return Map.of("message", "ride cancelled");
    }

    public List<Booking> getAllBooking(String uid){
        return bookingRepository.findBookingsByUserId(uid);
    }
}
