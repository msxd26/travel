package pe.jsaire.springtravel.repositories;

import org.springframework.data.repository.CrudRepository;
import pe.jsaire.springtravel.models.entity.ReservationEntity;

import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
