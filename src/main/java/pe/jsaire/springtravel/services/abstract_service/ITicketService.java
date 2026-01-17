package pe.jsaire.springtravel.services.abstract_service;

import pe.jsaire.springtravel.models.dto.request.TicketRequest;
import pe.jsaire.springtravel.models.dto.response.TicketResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface ITicketService extends ICrudService<TicketResponse, TicketRequest, UUID> {
    BigDecimal findPrice(Long flyId, Currency currency);

}
