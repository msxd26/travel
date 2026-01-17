package pe.jsaire.springtravel.mappers;

import org.mapstruct.Mapper;
import pe.jsaire.springtravel.models.dto.request.TicketRequest;
import pe.jsaire.springtravel.models.dto.response.TicketResponse;
import pe.jsaire.springtravel.models.entity.TicketEntity;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketEntity toEntity(TicketRequest request);

    TicketResponse toResponse(TicketEntity entity);
}
