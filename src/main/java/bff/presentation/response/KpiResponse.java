package bff.presentation.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KpiResponse {
    private Integer totalBookings;
    private Double bookingsPerHour;
    private Double averageCycleTimeMinutes;
    private Integer occupiedResources;
}