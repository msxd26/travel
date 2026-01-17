package pe.jsaire.springtravel.mappers;

import org.mapstruct.Mapper;
import pe.jsaire.springtravel.models.dto.request.HotelRequest;
import pe.jsaire.springtravel.models.dto.response.HotelResponse;
import pe.jsaire.springtravel.models.entity.HotelEntity;

import java.util.Set;


@Mapper(componentModel = "spring")
public interface HotelMapper {

    HotelEntity toEntity(HotelRequest request);

    HotelResponse toResponse(HotelEntity entity);

    Set<HotelResponse> toSetResponse(Set<HotelEntity> entities);

}
