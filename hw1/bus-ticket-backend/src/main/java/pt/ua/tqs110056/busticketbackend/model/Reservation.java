package pt.ua.tqs110056.busticketbackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Passenger passenger;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Trip trip;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    private BusSeat seat;

    @ManyToOne(cascade = CascadeType.ALL)
    private CreditCard creditCard;

    private ReservationStatus status;
    private LocalDateTime reservationTime;
    

    public Reservation() {
    }
    
    public Reservation(Passenger passenger, Trip trip, BusSeat seat, CreditCard creditCard) {
        this.passenger = passenger;
        this.trip = trip;
        this.seat = seat;
        this.creditCard = creditCard;
        this.status = ReservationStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public BusSeat getSeat() {
        return seat;
    }

    public void setSeat(BusSeat seat) {
        this.seat = seat;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((passenger == null) ? 0 : passenger.hashCode());
        result = prime * result + ((trip == null) ? 0 : trip.hashCode());
        result = prime * result + ((seat == null) ? 0 : seat.hashCode());
        result = prime * result + ((creditCard == null) ? 0 : creditCard.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((reservationTime == null) ? 0 : reservationTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reservation other = (Reservation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (passenger == null) {
            if (other.passenger != null)
                return false;
        } else if (!passenger.equals(other.passenger))
            return false;
        if (trip == null) {
            if (other.trip != null)
                return false;
        } else if (!trip.equals(other.trip))
            return false;
        if (seat == null) {
            if (other.seat != null)
                return false;
        } else if (!seat.equals(other.seat))
            return false;
        if (creditCard == null) {
            if (other.creditCard != null)
                return false;
        } else if (!creditCard.equals(other.creditCard))
            return false;
        if (status != other.status)
            return false;
        if (reservationTime == null) {
            if (other.reservationTime != null)
                return false;
        } else if (!reservationTime.equals(other.reservationTime))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", passenger=" + passenger + ", trip=" + trip + ", seat=" + seat
                + ", creditCard=" + creditCard + ", status=" + status + ", reservationTime=" + reservationTime + "]";
    }

}
