package bff.presentation.controller;

import bff.data.exchange.catalog.CatalogClient;
import bff.presentation.request.ResourceRequest;
import bff.presentation.response.CatalogResourceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bff/catalog")
@RequiredArgsConstructor
public class CatalogBffController {

    private final CatalogClient catalogClient;

    @GetMapping("/resources")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<List<CatalogResourceResponse>> getResources() {
        return ResponseEntity.ok(catalogClient.getResource());
    }

    @GetMapping("/resources/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR')")
    public ResponseEntity<CatalogResourceResponse> getResourceById(
            @PathVariable Long id) {
        return ResponseEntity.ok(catalogClient.getResourceById(id));
    }

    @PostMapping("/resources")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogResourceResponse> createResource(
            @Valid @RequestBody ResourceRequest request
            ) {
        return ResponseEntity.status(
                HttpStatus.CREATED)
                .body(catalogClient.createResource(request)
                );
    }

    @PutMapping("/resources/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogResourceResponse> updateResource(
            @PathVariable Long id,
            @Valid @RequestBody ResourceRequest request
    ) {
        return ResponseEntity.ok(catalogClient.updateResource(id,request));
    }
}
