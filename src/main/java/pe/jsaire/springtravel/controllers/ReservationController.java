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
import pe.jsaire.springtravel.models.dto.request.ReservationRequest;
import pe.jsaire.springtravel.models.dto.response.ReservationResponse;
import pe.jsaire.springtravel.services.abstract_service.IReservationService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final IReservationService service;

    @Operation(summary = "search reservation by id")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "create a reservation")
    @PostMapping
    public ResponseEntity<ReservationResponse> save(@Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @Operation(summary = "update a reservation")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> update(@PathVariable UUID id,
                                                      @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "return a reservation price given a hotel id")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(
            @RequestParam Long hotelId,
            @RequestHeader(required = false) Currency currency) {
        if (Objects.isNull(currency)) currency = Currency.getInstance("USD");
        return ResponseEntity.ok(Collections.singletonMap("ticketPrice", this.service.findPrice(hotelId, currency)));
    }

    @Operation(summary = "delete a reservation")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
