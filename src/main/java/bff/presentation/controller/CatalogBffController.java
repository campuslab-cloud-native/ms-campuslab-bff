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

    @GetMapping
    @PreAuthorize("hasAnyRole('Admin', 'Operator')")
    public ResponseEntity<List<CatalogResourceResponse>> getResources() {
        return ResponseEntity.ok(catalogClient.getResource());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Operator')")
    public ResponseEntity<CatalogResourceResponse> getResourceById(
            @PathVariable Long id) {
        return ResponseEntity.ok(catalogClient.getResourceById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Admin', 'Operator')")
    public ResponseEntity<CatalogResourceResponse> createResource(
            @Valid @RequestBody ResourceRequest request
            ) {
        return ResponseEntity.status(
                HttpStatus.CREATED)
                .body(catalogClient.createResource(request)
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Operator')")
    public ResponseEntity<CatalogResourceResponse> updateResource(
            @PathVariable Long id,
            @Valid @RequestBody ResourceRequest request
    ) {
        return ResponseEntity.ok(catalogClient.updateResource(id,request));
    }
}
