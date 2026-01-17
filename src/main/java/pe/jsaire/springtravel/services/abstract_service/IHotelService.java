package pe.jsaire.springtravel.services.abstract_service;

import pe.jsaire.springtravel.models.dto.response.HotelResponse;

import java.util.Set;

public interface IHotelService extends ICatalgoService<HotelResponse> {
    Set<HotelResponse> readByRating(Integer rating);
}
