package bff.data.exchange.bookings;

import bff.presentation.request.UpdateBookingStatusRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class BookingsClientAdminStatusTest {

    @Test
    void forwardsAdminStatusRequestToBookingsEndpointWithoutChangingBody() {
        RestClient.Builder restClientBuilder = RestClient.builder();
        MockRestServiceServer server =
                MockRestServiceServer.bindTo(restClientBuilder).build();
        BookingsClient client = new BookingsClient(restClientBuilder.build());
        ReflectionTestUtils.setField(
                client,
                "bookingServiceUrl",
                "http://bookings.internal"
        );

        server.expect(requestTo(
                        "http://bookings.internal/api/v1/bookings/23/admin-status"
                ))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\":\"IN_PREPARATION\"}"))
                .andRespond(withSuccess(
                        "{\"id\":23,\"status\":\"IN_PREPARATION\"}",
                        MediaType.APPLICATION_JSON
                ));

        var response = client.updateAdminStatus(
                23L,
                UpdateBookingStatusRequest.builder()
                        .status("IN_PREPARATION")
                        .build()
        );

        assertEquals(23L, response.getId());
        assertEquals("IN_PREPARATION", response.getStatus());
        server.verify();
    }
}
