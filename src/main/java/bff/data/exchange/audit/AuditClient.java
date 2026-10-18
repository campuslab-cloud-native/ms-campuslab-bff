package bff.data.exchange.audit;

import bff.presentation.response.AuditEventResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuditClient {

    private final RestClient restClient = RestClient.create();

    @Value("${services.audit.url}")
    private String auditServiceUrl;

    public List<AuditEventResponse> getEvents(
            String userId,
            String type,
            LocalDateTime from,
            LocalDateTime to
    ) {

        var builder = UriComponentsBuilder
                .fromUriString(auditServiceUrl)
                .path("/api/v1/audit/events");

        if (userId != null && !userId.isBlank()) {
            builder.queryParam("userId", userId);
        }

        if (type != null && !type.isBlank()) {
            builder.queryParam("type", type);
        }

        if (from != null) {
            builder.queryParam("from", from);
        }

        if (to != null) {
            builder.queryParam("to", to);
        }

        return restClient.get()
                .uri(builder.build().toUri())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<AuditEventResponse> getBookingTimeline(Long bookingId) {

        var uri = UriComponentsBuilder
                .fromUriString(auditServiceUrl)
                .path("/api/v1/audit/bookings/{bookingId}")
                .buildAndExpand(bookingId)
                .toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}