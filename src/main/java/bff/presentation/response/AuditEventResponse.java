package bff.presentation.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditEventResponse {
    private String eventId;
    private Long bookingId;
    private String userId;
    private String type;
    private LocalDateTime timestamp;
    private String source;
}
