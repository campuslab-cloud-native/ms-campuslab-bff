package bff.presentation.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopResourceResponse {
    private Long resourceId;
    private String resourceName;
    private Integer totalBookings;
}