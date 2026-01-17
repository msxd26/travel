package pe.jsaire.springtravel.models.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationRequest implements Serializable {

    @Size(min = 18, max = 20, message = "The size have to a length between 18 and 20 characters")
    @NotBlank(message = "Id client is mandatory")
    private String idClient;

    @Positive
    @NotNull(message = "Id hotel is mandatory")
    private Long idHotel;

    @Min(value = 1, message = "Min one days to make reservation")
    @Max(value = 30, message = "Max 30 days to make reservation")
    @NotNull(message = "total days is mandatory")
    private Integer totalDays;

}
