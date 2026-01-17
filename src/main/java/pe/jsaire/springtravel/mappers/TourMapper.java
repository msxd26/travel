package pe.jsaire.springtravel.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.jsaire.springtravel.models.dto.request.TourRequest;
import pe.jsaire.springtravel.models.dto.response.TourResponse;
import pe.jsaire.springtravel.models.entity.ReservationEntity;
import pe.jsaire.springtravel.models.entity.TicketEntity;
import pe.jsaire.springtravel.models.entity.TourEntity;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TourMapper {

    TourEntity toEntity(TourRequest request);

    @Mapping(source = "tickets", target = "ticketIds")
    @Mapping(source = "reservations", target = "reservationIds")
    TourResponse toResponse(TourEntity entity);

    default UUID mapTicketToId(TicketEntity ticket) {
        return ticket != null ? ticket.getId() : null;
    }

    default UUID mapReservationToId(ReservationEntity reservation) {
        return reservation != null ? reservation.getId() : null;
    }
}
