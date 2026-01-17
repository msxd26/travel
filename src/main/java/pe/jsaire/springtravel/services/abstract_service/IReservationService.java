package pe.jsaire.springtravel.services.abstract_service;

import pe.jsaire.springtravel.models.dto.request.ReservationRequest;
import pe.jsaire.springtravel.models.dto.response.ReservationResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface IReservationService extends ICrudService<ReservationResponse, ReservationRequest, UUID> {

    BigDecimal findPrice(Long hotelId, Currency currency);

}
