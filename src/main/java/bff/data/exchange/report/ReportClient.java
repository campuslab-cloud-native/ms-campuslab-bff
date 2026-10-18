package bff.data.exchange.report;

import bff.presentation.response.KpiResponse;
import bff.presentation.response.TopResourceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class ReportClient {

    private final RestClient restClient = RestClient.create();

    @Value("${services.report.url}")
    private String reportServiceUrl;

    public KpiResponse getKpis(String range) {

        var builder = UriComponentsBuilder
                .fromUriString(reportServiceUrl)
                .path("/api/v1/report/kpis");

        if (range != null && !range.isBlank()) {
            builder.queryParam("range", range);
        }

        return restClient.get()
                .uri(builder.build().toUri())
                .retrieve()
                .body(KpiResponse.class);
    }

    public List<TopResourceResponse> getTopResources(String range) {

        var builder = UriComponentsBuilder
                .fromUriString(reportServiceUrl)
                .path("/api/v1/report/top-resources");

        if (range != null && !range.isBlank()) {
            builder.queryParam("range", range);
        }

        return restClient.get()
                .uri(builder.build().toUri())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}