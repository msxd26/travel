package pe.jsaire.springtravel.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.jsaire.springtravel.models.dto.request.ReservationRequest;
import pe.jsaire.springtravel.models.dto.response.ReservationResponse;
import pe.jsaire.springtravel.models.entity.ReservationEntity;

@Mapper(componentModel = "spring")
public interface ReservationMapper {


    @Mapping(target = "hotel.id", source = "idHotel")
    ReservationEntity toEntity(ReservationRequest request);

    @Mapping(target = "dateTimeReservation", source = "dateReservation")
    ReservationResponse toResponse(ReservationEntity entity);
}
