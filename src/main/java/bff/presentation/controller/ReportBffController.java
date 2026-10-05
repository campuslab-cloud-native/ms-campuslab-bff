package bff.presentation.controller;

import bff.data.exchange.report.ReportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bff/report")
@RequiredArgsConstructor
public class ReportBffController {

    private final ReportClient reportClient;

    @GetMapping("/kpis")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<KpiResponse> getKpis(
            @RequestParam(required = false) String range
    ) {
        return ResponseEntity.ok(
                reportClient.getKpis(range)
        );
    }

    @GetMapping("/top-resources")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUDITOR')")
    public ResponseEntity<List<TopResourceResponse>> getTopResources(
            @RequestParam(required = false) String range
    ) {
        return ResponseEntity.ok(
                reportClient.getTopResources(range)
        );
    }
}