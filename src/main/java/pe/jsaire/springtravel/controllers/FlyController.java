package pe.jsaire.springtravel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.springtravel.models.dto.response.FlyResponse;
import pe.jsaire.springtravel.services.abstract_service.IFlyService;
import pe.jsaire.springtravel.utils.enums.SortType;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fly")
public class FlyController {

    private final IFlyService service;

    @Operation(summary = "show all paginated flights for price.")
    @GetMapping("/all")
    public ResponseEntity<Page<FlyResponse>> getAll(@RequestParam Integer page,
                                                    @RequestParam Integer size,
                                                    @RequestParam(required = false) SortType sortType) {
        if (sortType == null) {
            sortType = SortType.NONE;
        }
        return ResponseEntity.ok(service.getAll(page, size, sortType));
    }

    @Operation(summary = "show the lowest price flights.")
    @GetMapping("/all-price")
    public ResponseEntity<Set<FlyResponse>> getLessPrice(@RequestParam BigDecimal price) {
        return ResponseEntity.ok(service.readLessPrice(price));
    }

    @Operation(summary = "show flights with a minimum and maximum price.")
    @GetMapping("/all-price-min")
    public ResponseEntity<Set<FlyResponse>> getBetweenPrice(@RequestParam BigDecimal min,
                                                            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(service.readBetweenPrices(min, max));
    }


    @Operation(summary = "show the origin and destination flights.")
    @GetMapping("/origin_destiny")
    public ResponseEntity<Set<FlyResponse>> getBetweenPrice(@RequestParam String origin,
                                                            @RequestParam String destination) {
        return ResponseEntity.ok(service.readByOriginDistination(origin, destination));
    }
}
