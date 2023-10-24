package com.example.bus_booking_system.admin.service;

import com.example.bus_booking_system.admin.model.Bus;
import com.example.bus_booking_system.admin.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BusRepository adminRepository;

    public Bus addBus(Bus bus) {
        return adminRepository.save(bus);
    }

    public Bus updateBus(Bus bus) {
        var newBus=adminRepository.getBusById(bus.getId());
        newBus.ifPresent(b->{
            if (bus.getSeats()!=null){
                b.setSeats(bus.getSeats());
            }

            if (bus.getSrc()!=null){
                b.setSrc(bus.getSrc());
            }

            if (bus.getDst()!=null){
                b.setDst(bus.getDst());
            }

            if (bus.getName()!=null){
                b.setName(bus.getName());
            }
        });
        return newBus.map(adminRepository::save).orElse(null);
    }

    public Object deleteBus(String busID) {
        var buses=adminRepository.deleteBusById(busID);
        return Map.of("message", "Bus Details Deleted");
    }
}
