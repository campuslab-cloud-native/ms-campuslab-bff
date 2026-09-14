package bff.data.exchange.catalog;

import bff.presentation.response.CatalogResourceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CatalogClient {

    private RestClient restClient;

    public List<CatalogResourceResponse> getResource() {
        return restClient.get()
                .uri("http://localhost:8082/api/catalog/resources")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                }
            );
    }
}
