package pe.jsaire.springtravel.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.springtravel.mappers.ReservationMapper;
import pe.jsaire.springtravel.models.dto.request.ReservationRequest;
import pe.jsaire.springtravel.models.dto.response.ReservationResponse;
import pe.jsaire.springtravel.models.entity.ReservationEntity;
import pe.jsaire.springtravel.repositories.CustomerRepository;
import pe.jsaire.springtravel.repositories.HotelRepository;
import pe.jsaire.springtravel.repositories.ReservationRepository;
import pe.jsaire.springtravel.services.abstract_service.IReservationService;
import pe.jsaire.springtravel.utils.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository repository;
    private final ReservationMapper mapper;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final CurrencyServiceApi currencyServiceApi;
    private final EmailService emailService;

    public static final BigDecimal CHARGES_PORCENTAGE = BigDecimal.valueOf(0.20);


    @Override
    @Transactional(readOnly = true)
    public ReservationResponse findById(UUID uuid) {
        return mapper.toResponse(repository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", uuid.toString())));
    }

    @Override
    @Transactional
    public ReservationResponse save(ReservationRequest request) {
        var customer = customerRepository.findById(request.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", request.getIdClient()));
        var hotel = hotelRepository.findById(request.getIdHotel()).
                orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", request.getIdHotel()));
        var reservaUpdate = ReservationEntity.builder()
                .customer(customer)
                .hotel(hotel)
                .totalDays(request.getTotalDays())
                .dateReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(new BigDecimal(10))))
                .build();

        if (Objects.nonNull(request.getEmail())) {
            emailService.sendEmail(request.getEmail(), customer.getFullName(), "reservation");
        }
        return mapper.toResponse(repository.save(reservaUpdate));
    }

    @Override
    @Transactional
    public ReservationResponse update(UUID uuid, ReservationRequest request) {
        var reservateToUpdate = repository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", uuid.toString()));

        var customer = customerRepository.findById(request.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", request.getIdClient()));

        var hotel = hotelRepository.findById(request.getIdHotel()).
                orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", request.getIdHotel()));

        reservateToUpdate = ReservationEntity.builder()
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(new BigDecimal(10))))
                .build();
        return mapper.toResponse(repository.save(reservateToUpdate));
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        if (repository.findById(uuid).isEmpty()) {
            throw new ResourceNotFoundException("Reservation", "id", uuid.toString());
        }
        repository.deleteById(uuid);
    }


    @Override
    public BigDecimal findPrice(Long hotelId, Currency currency) {
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", "id", hotelId));
        var priceInDollars = hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PORCENTAGE));
        if (currency.getCurrencyCode().equals("USD")) return priceInDollars;
        var currencyDTO = this.currencyServiceApi.getCurrency(currency);
        String currencyCode = currency.getCurrencyCode();
        BigDecimal exchangeRate = currencyDTO.rates().get(currencyCode);
        if (exchangeRate == null) {
            throw new ResourceNotFoundException("Exchange Rate", "currency", currencyCode);
        }
        log.info("API currency in {}, response: {}", currencyDTO.date(), currencyDTO.rates());
        return priceInDollars.multiply(exchangeRate);
    }
}
