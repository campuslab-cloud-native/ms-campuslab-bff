package bff.presentation.controller;

import bff.data.exchange.bookings.BookingsClient;
import bff.presentation.request.CreateBookingRequest;
import bff.presentation.request.UpdateBookingStatusRequest;
import bff.presentation.response.BookingsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bff/bookings")
@RequiredArgsConstructor
public class BookingsBffController {

    private final BookingsClient bookingsClient;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'CLIENT')")
    public ResponseEntity<BookingsResponse> createBooking(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateBookingRequest request) {


        return ResponseEntity.status(
                HttpStatus.CREATED)
                .body(bookingsClient
                        .createBooking(
                                request,
                                jwt.getClaimAsString("preferred_username")
                        )
                );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<BookingsResponse> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingsClient.getBookingById(id)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<List<BookingsResponse>> getBookings() {

        return ResponseEntity.ok(
                bookingsClient.getBookings()
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<BookingsResponse>> getMyBookings(
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(
                bookingsClient.getBookingsByUserId(
                        jwt.getClaimAsString("preferred_username")
                )
        );
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<BookingsResponse> updateBookingStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingStatusRequest request) {

        return ResponseEntity.ok(
                bookingsClient.updateStatus(id, request)
        );
    }
}