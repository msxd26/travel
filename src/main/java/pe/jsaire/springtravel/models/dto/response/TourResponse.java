package pe.jsaire.springtravel.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TourResponse {

    private Long id;
    private CustomerResponse customer;
    private Set<UUID> ticketIds;
    private Set<UUID> reservationIds;
}
