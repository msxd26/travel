package pe.jsaire.springtravel.controllers;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.springtravel.models.dto.request.TourRequest;
import pe.jsaire.springtravel.models.dto.response.TourResponse;
import pe.jsaire.springtravel.services.abstract_service.ITourService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tour")
public class TourController {

    private final ITourService service;


    @Operation(summary = "search for tours by id")
    @GetMapping("{id}")
    public ResponseEntity<TourResponse> getTour(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "create a tour")
    @PostMapping
    public ResponseEntity<TourResponse> save(@Valid @RequestBody TourRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }


    @Operation(summary = "remove a ticket from a tour")
    @PatchMapping("/{tourId}/remove-ticket/{ticketId}")
    public ResponseEntity<TourResponse> removeTicketS(@PathVariable Long tourId, @PathVariable UUID ticketId) {
        service.removeTicket(tourId, ticketId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "remove a reservation from a tour")
    @PatchMapping("/{tourId}/remove-reservation/{reservationId}")
    public ResponseEntity<TourResponse> removeReservation(@PathVariable Long tourId, @PathVariable UUID reservationId) {
        service.removeReservation(tourId, reservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "add a tour ticket")
    @PatchMapping("/{tourId}/add-ticket/{flyId}")
    public ResponseEntity<TourResponse> addTicket(@PathVariable Long tourId, @PathVariable Long flyId) {
        service.addTicket(flyId, tourId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "add a tour reservation")
    @PatchMapping("/{tourId}/add-reservation/{hotelId}")
    public ResponseEntity<TourResponse> addTicket(@PathVariable Long tourId,
                                                  @PathVariable Long hotelId,
                                                  @RequestParam Integer totalDays) {
        service.addReservation(hotelId, tourId, totalDays);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "delete a tour")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
