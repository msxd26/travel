package pe.jsaire.springtravel.models.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tour")
@AllArgsConstructor
@Getter
@Setter
public class TourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private CustomerEntity customer;


    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "tour")
    private Set<ReservationEntity> reservations;


    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "tour")
    private Set<TicketEntity> tickets;


    public TourEntity() {
        this.reservations = new HashSet<>();
        this.tickets = new HashSet<>();
    }

    public void addReservation(ReservationEntity reservation) {
        if (this.reservations == null) {
            this.reservations = new HashSet<>();
        }
        reservations.add(reservation);
        reservation.setTour(this);
    }

    public void addTicket(TicketEntity ticket) {
        if (this.tickets == null) {
            this.tickets = new HashSet<>();
        }
        this.tickets.add(ticket);
        ticket.setTour(this);
    }

    public void removeTicket(UUID id) {
        Iterator<TicketEntity> it = tickets.iterator();
        while (it.hasNext()) {
            TicketEntity ticket = it.next();
            if (ticket.getId().equals(id)) {
                ticket.setTour(null);
                it.remove();
                break;
            }
        }
    }

    public void removeReservation(UUID id) {
        Iterator<ReservationEntity> it = reservations.iterator();
        while (it.hasNext()) {
            ReservationEntity reservation = it.next();
            if (reservation.getId().equals(id)) {
                reservation.setTour(null);
                it.remove();
                break;
            }
        }
    }
}
