package bff.presentation.controller;

import bff.data.exchange.bookings.BookingsClient;
import bff.presentation.request.CreateBookingRequest;
import bff.presentation.response.BookingsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingsBffControllerTest {

    private static final String USER_EMAIL = "w.vinet.h@gmail.com";

    private final BookingsClient bookingsClient = mock(BookingsClient.class);
    private final BookingsBffController controller =
            new BookingsBffController(bookingsClient);

    @Test
    void createsBookingUsingPreferredUsernameAsUserId() {
        Jwt jwt = jwtWithPreferredUsername();
        CreateBookingRequest request = new CreateBookingRequest();
        BookingsResponse expectedResponse = new BookingsResponse();
        when(bookingsClient.createBooking(request, USER_EMAIL))
                .thenReturn(expectedResponse);

        var response = controller.createBooking(jwt, request);

        assertEquals(expectedResponse, response.getBody());
        verify(bookingsClient).createBooking(request, USER_EMAIL);
    }

    @Test
    void getsCurrentUserBookingsUsingPreferredUsernameAsUserId() {
        Jwt jwt = jwtWithPreferredUsername();
        List<BookingsResponse> expectedResponse = List.of(new BookingsResponse());
        when(bookingsClient.getBookingsByUserId(USER_EMAIL))
                .thenReturn(expectedResponse);

        var response = controller.getMyBookings(jwt);

        assertEquals(expectedResponse, response.getBody());
        verify(bookingsClient).getBookingsByUserId(USER_EMAIL);
    }

    private Jwt jwtWithPreferredUsername() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("preferred_username")).thenReturn(USER_EMAIL);
        return jwt;
    }
}
