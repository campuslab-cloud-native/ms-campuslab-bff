package bff.presentation.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequest {

    @NotBlank
    private String name;

    @NotNull
    private String type;

    @NotNull
    @Min(0)
    private Integer availableQuantity;
}
