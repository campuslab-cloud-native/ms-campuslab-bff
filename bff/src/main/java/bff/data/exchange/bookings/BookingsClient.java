package bff.data.exchange.bookings;

import bff.presentation.request.CreateBookingRequest;
import bff.presentation.request.UpdateBookingStatusRequest;
import bff.presentation.response.BookingsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingsClient {

    private RestClient restClient;

    private static final String BASE_URL =
            "https://localhost:8081/api/booking";

    public BookingsResponse createBooking(CreateBookingRequest request) {
        return restClient.post()
                .uri(BASE_URL)
                .body(request)
                .retrieve()
                .body(BookingsResponse.class);
    }

    public BookingsResponse getBookingById(Long id) {
        return restClient.get()
                .uri(BASE_URL + "/" + id)
                .retrieve()
                .body(BookingsResponse.class);

    }

    public List<BookingsResponse> getBookings() {
        return restClient.get()
                .uri(BASE_URL)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                }
                );
    }

    public BookingsResponse updateStatus(
            Long id,
            UpdateBookingStatusRequest request) {
        return restClient.put()
                .uri(BASE_URL + "/" + id + "/status")
                .body(request)
                .retrieve()
                .body(BookingsResponse.class);
    }
}
