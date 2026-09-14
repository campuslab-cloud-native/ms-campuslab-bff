package bff.data.exchange.catalog;

import bff.presentation.request.ResourceRequest;
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

    private static final String BASE_URL =
            "http://localhost:8082/api/catalog/resources";

    public List<CatalogResourceResponse> getResource() {
        return restClient.get()
                .uri(BASE_URL)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                }
            );
    }

    public CatalogResourceResponse getResourceById(Long id) {
        return restClient.get()
                .uri(BASE_URL + "/" + id)
                .retrieve()
                .body(CatalogResourceResponse.class);
    }

    public CatalogResourceResponse createResource(ResourceRequest request) {
        return restClient.post()
                .uri(BASE_URL)
                .body(request)
                .retrieve()
                .body(CatalogResourceResponse.class);
    }

    public CatalogResourceResponse updateResource(
            Long id,
            ResourceRequest request
    ) {
        return restClient.put()
                .uri(BASE_URL + "/" + id)
                .body(request)
                .retrieve()
                .body(CatalogResourceResponse.class);
    }
}
