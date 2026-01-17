package pe.jsaire.springtravel.repositories;


import org.springframework.data.repository.CrudRepository;
import pe.jsaire.springtravel.models.entity.TicketEntity;

import java.util.UUID;

public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}
