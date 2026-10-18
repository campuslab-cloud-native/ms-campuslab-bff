package bff.presentation.controller;

import bff.common.RemoteServiceExceptionHandler;
import bff.data.exchange.bookings.BookingsClient;
import bff.presentation.request.UpdateBookingStatusRequest;
import bff.presentation.response.BookingsResponse;
import bff.security.JwtRoleConverter;
import bff.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = BookingsBffController.class,
        properties = "AZURE_ISSUER_URI=https://issuer.example"
)
@Import({
        SecurityConfig.class,
        JwtRoleConverter.class,
        RemoteServiceExceptionHandler.class
})
class BookingsAdminStatusBffTest {

    private static final long BOOKING_ID = 23L;
    private static final String ENDPOINT =
            "/api/v1/bookings/" + BOOKING_ID + "/admin-status";
    private static final String REQUEST_BODY = """
            {"status":"IN_PREPARATION"}
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingsClient bookingsClient;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void adminCanUpdateBookingStatusAndRequestIsForwarded() throws Exception {
        BookingsResponse expectedResponse = new BookingsResponse();
        expectedResponse.setId(BOOKING_ID);
        expectedResponse.setStatus("IN_PREPARATION");
        when(bookingsClient.updateAdminStatus(
                eq(BOOKING_ID),
                any(UpdateBookingStatusRequest.class)
        )).thenReturn(expectedResponse);

        mockMvc.perform(put(ENDPOINT)
                        .with(jwt().authorities(
                                new SimpleGrantedAuthority("ROLE_ADMIN")
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":23,\"status\":\"IN_PREPARATION\"}"
                ));

        var requestCaptor =
                org.mockito.ArgumentCaptor.forClass(UpdateBookingStatusRequest.class);
        verify(bookingsClient).updateAdminStatus(eq(BOOKING_ID), requestCaptor.capture());
        assertEquals("IN_PREPARATION", requestCaptor.getValue().getStatus());
    }

    @Test
    void clientCannotUpdateBookingAdminStatus() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                        .with(jwt().authorities(
                                new SimpleGrantedAuthority("ROLE_CLIENT")
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isForbidden());

        verify(bookingsClient, never())
                .updateAdminStatus(any(Long.class), any(UpdateBookingStatusRequest.class));
    }

    @Test
    void operatorCannotUpdateBookingAdminStatus() throws Exception {
        mockMvc.perform(put(ENDPOINT)
                        .with(jwt().authorities(
                                new SimpleGrantedAuthority("ROLE_OPERATOR")
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isForbidden());

        verify(bookingsClient, never())
                .updateAdminStatus(any(Long.class), any(UpdateBookingStatusRequest.class));
    }

    @Test
    void forwardsBookingsErrorsWithTheirOriginalStatusAndBody() throws Exception {
        byte[] errorBody = "{\"message\":\"transition not allowed\"}"
                .getBytes(StandardCharsets.UTF_8);
        when(bookingsClient.updateAdminStatus(
                eq(BOOKING_ID),
                any(UpdateBookingStatusRequest.class)
        )).thenThrow(
                new HttpClientErrorException(HttpStatus.BAD_REQUEST),
                new HttpClientErrorException(HttpStatus.FORBIDDEN),
                new HttpClientErrorException(HttpStatus.NOT_FOUND),
                HttpClientErrorException.create(
                        HttpStatus.CONFLICT,
                        "Conflict",
                        HttpHeaders.EMPTY,
                        errorBody,
                        StandardCharsets.UTF_8
                ),
                new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE)
        );

        performAsAdmin()
                .andExpect(status().isBadRequest());

        performAsAdmin()
                .andExpect(status().isForbidden());

        performAsAdmin()
                .andExpect(status().isNotFound());

        performAsAdmin()
                .andExpect(status().isConflict())
                .andExpect(result -> assertEquals(
                        "{\"message\":\"transition not allowed\"}",
                        result.getResponse().getContentAsString()
                ));

        performAsAdmin()
                .andExpect(status().isServiceUnavailable());
    }

    private org.springframework.test.web.servlet.ResultActions performAsAdmin()
            throws Exception {
        return mockMvc.perform(put(ENDPOINT)
                .with(jwt().authorities(
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_BODY));
    }
}
