package pe.jsaire.springtravel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.springtravel.mappers.TourMapper;
import pe.jsaire.springtravel.models.dto.request.TourRequest;
import pe.jsaire.springtravel.models.dto.response.TourResponse;
import pe.jsaire.springtravel.models.entity.CustomerEntity;
import pe.jsaire.springtravel.models.entity.FlyEntity;
import pe.jsaire.springtravel.models.entity.HotelEntity;
import pe.jsaire.springtravel.models.entity.ReservationEntity;
import pe.jsaire.springtravel.models.entity.TicketEntity;
import pe.jsaire.springtravel.models.entity.TourEntity;
import pe.jsaire.springtravel.repositories.CustomerRepository;
import pe.jsaire.springtravel.repositories.FlyRepository;
import pe.jsaire.springtravel.repositories.HotelRepository;
import pe.jsaire.springtravel.repositories.TourRepository;
import pe.jsaire.springtravel.services.abstract_service.ITourService;
import pe.jsaire.springtravel.utils.DateUtils;
import pe.jsaire.springtravel.utils.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TourServiceImpl implements ITourService {

    private final TourRepository tourRepository;
    private final CustomerRepository customerRepository;
    private final TourMapper tourMapper;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional(readOnly = true)
    public TourResponse findById(Long id) {
        return tourMapper.toResponse(tourRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Tour", "id", id)));
    }

    @Override
    @Transactional
    public TourResponse save(TourRequest tourRequest) {
        var customer = customerRepository.findById(tourRequest.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", tourRequest.getCustomerId()));
        TourEntity tour = new TourEntity();
        tour.setCustomer(customer);
        tourRequest.getFlights().forEach(flyReq -> {
            var fly = flyRepository.findById(flyReq.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fly", "id", flyReq.getId()));
            tour.addTicket(createTicket(fly, customer));
        });

        tourRequest.getHotels().forEach(hotelReq -> {
            var hotel = hotelRepository.findById(hotelReq.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelReq.getId()));
            tour.addReservation(createReservation(hotel, customer, hotelReq.getTotalDays()));
        });
        TourEntity savedTour = tourRepository.save(tour);
        return tourMapper.toResponse(savedTour);
    }

    @Override
    @Transactional
    public void removeTicket(Long tourId, UUID ticketId) {
        var tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", tourId));
        tour.removeTicket(ticketId);
        tourRepository.save(tour);
    }

    @Override
    @Transactional
    public UUID addTicket(Long flyId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", tourId));
        var fly = this.flyRepository.findById(flyId)
                .orElseThrow(() -> new ResourceNotFoundException("Fly", "id", flyId));
        var ticket = createTicket(fly, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    @Transactional
    public void removeReservation(Long tourId, UUID reservationId) {
        var tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", tourId));
        tour.removeReservation(reservationId);
        tourRepository.save(tour);
    }

    @Override
    @Transactional
    public UUID addReservation(Long hotelId, Long tourId, Integer totalDays) {
        var tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", tourId));
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));
        var reservation = createReservation(hotel, tour.getCustomer(), totalDays);
        tour.addReservation(reservation);
        tourRepository.save(tour);
        return reservation.getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tour", "id", id);
        }
        tourRepository.deleteById(id);
    }


    private TicketEntity createTicket(FlyEntity fly, CustomerEntity customer) {
        var price = fly.getPrice().multiply(BigDecimal.valueOf(1.10));
        return TicketEntity.builder()
                .fly(fly)
                .customer(customer)
                .price(price)
                .purchaseDate(LocalDate.now())
                .departureDate(DateUtils.getRandomSoon())
                .arrivalDate(DateUtils.getRandomLatter())
                .build();
    }

    private ReservationEntity createReservation(HotelEntity hotel, CustomerEntity customer, Integer totalDays) {
        var price = hotel.getPrice().multiply(BigDecimal.valueOf(1.10)); // +10%
        return ReservationEntity.builder()
                .hotel(hotel)
                .customer(customer)
                .totalDays(totalDays)
                .dateReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(totalDays))
                .price(price)
                .build();
    }
}
