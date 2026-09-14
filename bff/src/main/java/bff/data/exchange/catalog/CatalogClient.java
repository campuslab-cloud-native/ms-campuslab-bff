package bff.data.exchange.catalog;

import bff.presentation.request.ResourceRequest;
import bff.presentation.response.CatalogResourceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CatalogClient {

    private final RestClient restClient;

    @Value("${service.catalog.url}")
    private String catalogServiceUrl;


    public List<CatalogResourceResponse> getResource() {
        return restClient.get()
                .uri(catalogServiceUrl + "/api/catalog/resources")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                }
            );
    }

    public CatalogResourceResponse getResourceById(Long id) {
        return restClient.get()
                .uri(catalogServiceUrl + "/api/catalog/resources/" + id)
                .retrieve()
                .body(CatalogResourceResponse.class);
    }

    public CatalogResourceResponse createResource(ResourceRequest request) {
        return restClient.post()
                .uri(catalogServiceUrl + "/api/catalog/resources")
                .body(request)
                .retrieve()
                .body(CatalogResourceResponse.class);
    }

    public CatalogResourceResponse updateResource(
            Long id,
            ResourceRequest request
    ) {
        return restClient.put()
                .uri(catalogServiceUrl + "/api/catalog/resources/" + id)
                .body(request)
                .retrieve()
                .body(CatalogResourceResponse.class);
    }
}
