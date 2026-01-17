package pe.jsaire.springtravel.services.abstract_service;

import pe.jsaire.springtravel.models.dto.response.FlyResponse;

import java.util.Set;

public interface IFlyService extends ICatalgoService<FlyResponse> {

    Set<FlyResponse> readByOriginDistination(String origin, String destination);

}
