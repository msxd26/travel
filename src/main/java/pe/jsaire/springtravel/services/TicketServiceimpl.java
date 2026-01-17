package pe.jsaire.springtravel.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.springtravel.mappers.TicketMapper;
import pe.jsaire.springtravel.models.dto.request.TicketRequest;
import pe.jsaire.springtravel.models.dto.response.TicketResponse;
import pe.jsaire.springtravel.models.entity.TicketEntity;
import pe.jsaire.springtravel.repositories.CustomerRepository;
import pe.jsaire.springtravel.repositories.FlyRepository;
import pe.jsaire.springtravel.repositories.TicketRepository;
import pe.jsaire.springtravel.services.abstract_service.ITicketService;
import pe.jsaire.springtravel.utils.DateUtils;
import pe.jsaire.springtravel.utils.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceimpl implements ITicketService {

    private final TicketRepository repository;
    private final TicketMapper mapper;
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final CurrencyServiceApi currencyServiceApi;

    public static final BigDecimal CHARGES_PORCENTAGE = BigDecimal.valueOf(0.20);

    @Override
    @Transactional(readOnly = true)
    public TicketResponse findById(UUID uuid) {
        return mapper.toResponse(repository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", uuid.toString())));
    }

    @Override
    @Transactional
    public TicketResponse save(TicketRequest ticketRequest) {
        var fly = flyRepository.findById(ticketRequest.getIdFly())
                .orElseThrow(() -> new ResourceNotFoundException("Fly", "id", ticketRequest.getIdFly()));
        var customer = customerRepository.findById(ticketRequest.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", ticketRequest.getIdClient()));
        var ticketSave = TicketEntity.builder()
                .fly(fly)
                .customer(customer)
                .departureDate(DateUtils.getRandomSoon())
                .arrivalDate(DateUtils.getRandomLatter())
                .purchaseDate(LocalDate.now())
                .price(BigDecimal.valueOf(10))
                .build();
        return mapper.toResponse(repository.save(ticketSave));
    }

    @Override
    @Transactional
    public TicketResponse update(UUID uuid, TicketRequest ticketRequest) {
        var ticketUpdate = repository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", uuid.toString()));
        var fly = flyRepository.findById(ticketRequest.getIdFly())
                .orElseThrow(() -> new ResourceNotFoundException("Fly", "id", ticketRequest.getIdFly()));
        var customer = customerRepository.findById(ticketRequest.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", ticketRequest.getIdClient()));
        ticketUpdate.setFly(fly);
        ticketUpdate.setCustomer(customer);
        ticketUpdate.setDepartureDate(DateUtils.getRandomSoon());
        ticketUpdate.setArrivalDate(DateUtils.getRandomLatter());
        ticketUpdate.setPurchaseDate(LocalDate.now());
        ticketUpdate.setPrice(BigDecimal.valueOf(10));
        return mapper.toResponse(repository.save(ticketUpdate));
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        if (!repository.existsById(uuid)) {
            throw new ResourceNotFoundException("Ticket", "id", uuid.toString());
        }
        repository.deleteById(uuid);
    }

    @Override
    public BigDecimal findPrice(Long flyId, Currency currency) {
        var fly = flyRepository.findById(flyId)
                .orElseThrow(() -> new ResourceNotFoundException("Fly", "id", flyId.toString()));
        var priceInDollars = fly.getPrice().add(fly.getPrice().multiply(CHARGES_PORCENTAGE));
        var currencyDto = this.currencyServiceApi.getCurrency(currency);
        if (currency.getCurrencyCode().equals("USD")) {
            return priceInDollars;
        }
        var currencyCode = currency.getCurrencyCode();
        BigDecimal rate = currencyDto.rates().get(currencyCode);
        if (rate == null) {
            throw new ResourceNotFoundException("Rate", "id", currency.getCurrencyCode());
        }
        return priceInDollars.multiply(rate);
    }
}
