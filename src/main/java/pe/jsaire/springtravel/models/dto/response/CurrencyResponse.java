package pe.jsaire.springtravel.models.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record CurrencyResponse(
        @JsonProperty(value = "date")
        LocalDate date,
        Map<String, BigDecimal> rates
) {

}
