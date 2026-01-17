package pe.jsaire.springtravel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.springtravel.models.dto.response.HotelResponse;
import pe.jsaire.springtravel.services.abstract_service.IHotelService;
import pe.jsaire.springtravel.utils.enums.SortType;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

    private final IHotelService service;

    @Operation(summary = "show all paginated hotels for price.")
    @GetMapping("/all")
    public ResponseEntity<Page<HotelResponse>> readAll(@RequestParam Integer page,
                                                       @RequestParam Integer size,
                                                       @RequestParam(required = false) SortType sortType) {
        if (sortType == null) {
            sortType = SortType.NONE;
        }
        return ResponseEntity.ok(service.getAll(page, size, sortType));
    }

    @Operation(summary = "show the lowest price hotels.")
    @GetMapping("/all-price")
    public ResponseEntity<Set<HotelResponse>> getLessPrice(@RequestParam BigDecimal price) {
        return ResponseEntity.ok(service.readLessPrice(price));
    }

    @Operation(summary = "show hotels with a minimum and maximum price.")
    @GetMapping("/all-price-min")
    public ResponseEntity<Set<HotelResponse>> getBetweenPrice(@RequestParam BigDecimal min,
                                                              @RequestParam BigDecimal max) {
        return ResponseEntity.ok(service.readBetweenPrices(min, max));
    }

    @Operation(summary = "show hotels for raiting.")
    @GetMapping("/all-rating")
    public ResponseEntity<Set<HotelResponse>> getByRating(@RequestParam Integer rating) {
        return ResponseEntity.ok(service.readByRating(rating));
    }
}
