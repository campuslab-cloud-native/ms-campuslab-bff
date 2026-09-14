package bff.presentation.controller;

import bff.data.exchange.catalog.CatalogClient;
import bff.presentation.response.CatalogResourceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bff/catalog")
@RequiredArgsConstructor
public class CatalogBffController {

    private final CatalogClient catalogClient;

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Operator')")
    public ResponseEntity<List<CatalogResourceResponse>> getResources() {
        return ResponseEntity.ok(catalogClient.getResource());
    }
}
