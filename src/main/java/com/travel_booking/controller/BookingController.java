package com.travel_booking.controller;

import com.travel_booking.entity.BookingRequest;
import com.travel_booking.entity.Flight;
import com.travel_booking.service.BookingService;
import com.travel_booking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private FlightService flightService;

    // Search for flights
    @GetMapping("/flights")
    public List<Flight> getAvailableFlights(@RequestParam String origin, @RequestParam String destination,
                                            @RequestParam String departureDate) {
        LocalDate date = LocalDate.parse(departureDate);
        return flightService.findFlightsByRouteAndDate(origin, destination, date);
    }

    // Create a booking
    @PostMapping("/")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest);
    }

    // Cancel a booking with half-price refund
    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }
}

