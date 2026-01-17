package pe.jsaire.springtravel.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TourRequest implements Serializable {

    @Size(min = 18, max = 20, message = "The size have to a length between 18 and 20 characters")
    @NotBlank(message = "Id client is mandatory")
    public String customerId;

    @Size(min = 1, message = "Min flight tour per tour")
    private Set<FlyRequest> flights;
    @Size(min = 1, message = "Min hotel tour per tour")
    private Set<HotelRequest> hotels;
}
