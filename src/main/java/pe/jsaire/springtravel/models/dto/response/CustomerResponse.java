package pe.jsaire.springtravel.models.dto.response;

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
public class CustomerResponse implements Serializable {

    private String dni;
    private String fullName;
    private Integer totalFlights;
    private Integer totalLodgings;
    private Integer totalTours;
    private String phoneNumber;
}
