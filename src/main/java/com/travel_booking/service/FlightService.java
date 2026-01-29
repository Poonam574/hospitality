package com.travel_booking.service;

import com.travel_booking.entity.Flight;
import com.travel_booking.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> findFlightsByRouteAndDate(String origin, String destination, LocalDate departureDate) {
        return flightRepository.findByOriginAndDestinationAndDepartureDate(origin, destination, departureDate);
    }
}
