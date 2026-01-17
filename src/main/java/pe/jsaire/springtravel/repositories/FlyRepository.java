package pe.jsaire.springtravel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.jsaire.springtravel.models.entity.FlyEntity;

import java.math.BigDecimal;
import java.util.Set;

public interface FlyRepository extends JpaRepository<FlyEntity, Long> {

    Set<FlyEntity> findByPriceLessThan(BigDecimal price);

    Set<FlyEntity> findByPriceBetween(BigDecimal min, BigDecimal max);

    Set<FlyEntity> findByOriginNameAndDestinyName(String originName, String destinyName);
}
