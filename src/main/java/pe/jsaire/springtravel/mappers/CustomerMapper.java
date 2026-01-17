package pe.jsaire.springtravel.mappers;


import org.mapstruct.Mapper;
import pe.jsaire.springtravel.models.dto.request.CustomerRequest;
import pe.jsaire.springtravel.models.dto.response.CustomerResponse;
import pe.jsaire.springtravel.models.entity.CustomerEntity;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerEntity toResponse(CustomerRequest request);

    CustomerResponse toEntity(CustomerEntity entity);
}
