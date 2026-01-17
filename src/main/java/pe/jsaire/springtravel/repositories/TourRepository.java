package pe.jsaire.springtravel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.springtravel.models.entity.TourEntity;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
}
