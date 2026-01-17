package pe.jsaire.springtravel.models.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "hotel")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private BigDecimal price;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "hotel",
            fetch = FetchType.LAZY)
    private Set<ReservationEntity> reservations;
}
