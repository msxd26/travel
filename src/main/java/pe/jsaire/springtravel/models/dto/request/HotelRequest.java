package pe.jsaire.springtravel.models.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class HotelRequest implements Serializable {

    @Positive
    @NotNull(message = "Id hotel is mandatory")
    public Long id;

    @Positive
    @NotNull(message = "Total days is mandatory")
    private Integer totalDays;
}
