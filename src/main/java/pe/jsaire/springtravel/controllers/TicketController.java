package pe.jsaire.springtravel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.springtravel.models.dto.request.TicketRequest;
import pe.jsaire.springtravel.models.dto.response.TicketResponse;
import pe.jsaire.springtravel.services.abstract_service.ITicketService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final ITicketService service;

    @Operation(summary = "search ticket by id")
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> readyById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "create a ticket")
    @PostMapping
    public ResponseEntity<TicketResponse> save(@Valid @RequestBody TicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @Operation(summary = "update a ticket")
    @PutMapping("{id}")
    public ResponseEntity<TicketResponse> update(@PathVariable UUID id,
                                                 @Valid @RequestBody TicketRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(
            @RequestParam Long flyId,
            @RequestHeader(required = false) Currency currency) {
        if (Objects.isNull(currency)) currency = Currency.getInstance("USD");
        return ResponseEntity.ok(Collections.singletonMap("flyPrice", this.service.findPrice(flyId, currency)));
    }


    @Operation(summary = "delete a ticket")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
