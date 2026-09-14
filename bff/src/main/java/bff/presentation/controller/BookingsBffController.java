package bff.presentation.controller;

import bff.data.exchange.bookings.BookingsClient;
import bff.presentation.request.CreateBookingRequest;
import bff.presentation.request.UpdateBookingStatusRequest;
import bff.presentation.response.BookingsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bff/bookings")
@RequiredArgsConstructor
public class BookingsBffController {

    private final BookingsClient bookingsClient;

    @PostMapping
    @PreAuthorize("hasAnyRole('Admin', 'Operador', 'Cliente')")
    public ResponseEntity<BookingsResponse> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {

        return ResponseEntity.ok(
                bookingsClient.createBooking(request)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Operador', 'Cliente')")
    public ResponseEntity<BookingsResponse> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingsClient.getBookingById(id)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Operador', 'Cliente')")
    public ResponseEntity<List<BookingsResponse>> getBookings() {

        return ResponseEntity.ok(
                bookingsClient.getBookings()
        );
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('Admin', 'Operador')")
    public ResponseEntity<BookingsResponse> updateBookingStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingStatusRequest request) {

        return ResponseEntity.ok(
                bookingsClient.updateStatus(id, request)
        );
    }
}