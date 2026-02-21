package pe.jsaire.springtravel.models.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import pe.jsaire.springtravel.utils.enums.AeroLine;

import java.math.BigDecimal;
import java.util.Set;


@Entity
@Table(name = "fly")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "origin_lat", nullable = false)
    private BigDecimal originLat;

    @Column(name = "origin_lng", nullable = false)
    private BigDecimal originLng;

    @Column(name = "destiny_lng", nullable = false)
    private BigDecimal destinyLng;

    @Column(name = "destiny_lat", nullable = false)
    private BigDecimal destinyLat;

    @Column(name = "origin_name", length = 20, nullable = false)
    private String originName;

    @Column(name = "destiny_name", length = 20, nullable = false)
    private String destinyName;

    @Column(name = "aero_line", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;

    @Column(nullable = false)
    private BigDecimal price;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "fly",
            fetch = FetchType.LAZY)
    private Set<TicketEntity> tickets;
}
