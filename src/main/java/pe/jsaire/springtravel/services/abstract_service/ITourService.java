package pe.jsaire.springtravel.services.abstract_service;

import pe.jsaire.springtravel.models.dto.request.TourRequest;
import pe.jsaire.springtravel.models.dto.response.TourResponse;

import java.util.UUID;


public interface ITourService extends ISimpleCrudService<TourResponse, TourRequest, Long> {


    void removeTicket(Long tourId, UUID ticketId);

    UUID addTicket(Long flyId, Long tourId);

    void removeReservation(Long tourId, UUID reservationId);

    UUID addReservation(Long flyId, Long tourId, Integer totalDays);

}
