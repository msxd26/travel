package pe.jsaire.springtravel.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HotelResponse implements Serializable {
    private Long id;
    private String name;
    private String address;
    private Integer rating;
    private BigDecimal price;

}
