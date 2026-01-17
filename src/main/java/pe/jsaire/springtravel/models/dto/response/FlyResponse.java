package pe.jsaire.springtravel.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.jsaire.springtravel.utils.enums.AeroLine;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlyResponse implements Serializable {

    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    private String originName;
    private String destinyName;
    private BigDecimal price;
    private AeroLine aeroLine;
}
