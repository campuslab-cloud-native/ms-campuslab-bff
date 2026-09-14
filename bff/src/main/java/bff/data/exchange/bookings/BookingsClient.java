package bff.data.exchange.bookings;

import bff.presentation.request.CreateBookingRequest;
import bff.presentation.request.UpdateBookingStatusRequest;
import bff.presentation.response.BookingsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingsClient {

    private final RestClient restClient;

    @Value("${service.booking.url}")
    private String bookingServiceUrl;


    public BookingsResponse createBooking(CreateBookingRequest request) {
        return restClient.post()
                .uri(bookingServiceUrl + "/api/booking")
                .body(request)
                .retrieve()
                .body(BookingsResponse.class);
    }

    public BookingsResponse getBookingById(Long id) {
        return restClient.get()
                .uri(bookingServiceUrl + "/api/booking/" + id)
                .retrieve()
                .body(BookingsResponse.class);

    }

    public List<BookingsResponse> getBookings() {
        return restClient.get()
                .uri(bookingServiceUrl + "/api/booking")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {}
                );
    }

    public BookingsResponse updateStatus(
            Long id,
            UpdateBookingStatusRequest request) {
        return restClient.put()
                .uri(bookingServiceUrl + "/api/booking/" + id + "/status")
                .body(request)
                .retrieve()
                .body(BookingsResponse.class);
    }
}
