package com.travel_booking.service;

import com.travel_booking.entity.Booking;
import com.travel_booking.entity.BookingRequest;
import com.travel_booking.entity.Flight;
import com.travel_booking.repository.BookingRepository;
import com.travel_booking.repository.FlightRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private FlightRepository flightRepository;

    public ResponseEntity<?> createBooking(BookingRequest bookingRequest) {
        Flight flight = flightRepository.findById(bookingRequest.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() < bookingRequest.getNumberOfSeats()) {
            return ResponseEntity.badRequest().body("Not enough available seats");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - bookingRequest.getNumberOfSeats());
        flightRepository.save(flight);

        Booking booking = new Booking();
        booking.setFlight(flight);
        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        booking.setUser(user);


        Booking savedBooking = bookingRepository.save(booking);

        return ResponseEntity.ok(savedBooking);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        Flight flight = booking.getFlight();
        BigDecimal refundAmount = flight.getPrice().multiply(BigDecimal.valueOf(0.5));
        System.out.println("Refund amount: " + refundAmount);

        flight.setAvailableSeats(flight.getAvailableSeats() + 1); // Assuming 1 seat cancellation
        flightRepository.save(flight);

        bookingRepository.delete(booking);
    }
}
