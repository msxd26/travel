package pe.jsaire.springtravel.mappers;

import org.mapstruct.Mapper;
import pe.jsaire.springtravel.models.dto.request.FlyRequest;
import pe.jsaire.springtravel.models.dto.response.FlyResponse;
import pe.jsaire.springtravel.models.entity.FlyEntity;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface FlyMapper {

    FlyEntity toEntity(FlyRequest request);

    FlyResponse toResponse(FlyEntity entity);


    Set<FlyResponse> toSetResponse(Set<FlyEntity> responses);


}
