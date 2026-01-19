package pe.jsaire.springtravel.models.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerEntity {

    @Id
    @Column(length = 20)
    private String dni;

    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Column(name = "credit_card", length = 20, nullable = false)
    private String creditCard;

    @Column(name = "total_flights", nullable = false)
    private Integer totalFlights;

    @Column(name = "total_lodgings", nullable = false)
    private Integer totalLodgings;

    @Column(name = "total_tours", nullable = false)
    private Integer totalTours;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "customer",
            fetch = FetchType.LAZY)
    private Set<TicketEntity> tickets;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "customer",
            fetch = FetchType.LAZY)
    private Set<ReservationEntity> reservations;

    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "customer",
            fetch = FetchType.LAZY)
    private Set<TourEntity> tours;

}
