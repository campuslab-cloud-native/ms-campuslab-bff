package bff.presentation.controller;

import bff.data.exchange.audit.AuditClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/bff/audit")
@RequiredArgsConstructor
public class AuditBffController {

    private final AuditClient auditClient;

    @GetMapping("/events")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<List<AuditEventResponse>> getEvents(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {
        return ResponseEntity.ok(
                auditClient.getEvents(userId, type, from, to)
        );
    }

    @GetMapping("/bookings/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<List<AuditEventResponse>> getBookingTimeline(
            @PathVariable Long bookingId
    ) {
        return ResponseEntity.ok(
                auditClient.getBookingTimeline(bookingId)
        );
    }
}
