package pe.jsaire.springtravel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.springtravel.models.entity.ReservationEntity;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
}
