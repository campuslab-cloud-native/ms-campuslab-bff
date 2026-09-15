package bff.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResourceResponse {

    private Long id;
    private String name;
    private String type;
    private Integer availableQuantity;
}
