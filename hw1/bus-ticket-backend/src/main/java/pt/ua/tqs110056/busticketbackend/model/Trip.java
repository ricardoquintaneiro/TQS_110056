package pt.ua.tqs110056.busticketbackend.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private City origin;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private City destination;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bus bus;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Duration duration;

    private BigDecimal ticketPriceInEuro;

    public Trip() {
    }

    public Trip(City origin, City destination, Bus bus, LocalDateTime departureTime, LocalDateTime arrivalTime,
            BigDecimal ticketPriceInEuro) {
        this.origin = origin;
        this.destination = destination;
        this.bus = bus;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketPriceInEuro = ticketPriceInEuro;
    }

    public Trip(City origin, City destination, Bus bus, LocalDateTime departureTime, Duration duration,
            BigDecimal ticketPriceInEuro) {
        this.origin = origin;
        this.destination = destination;
        this.bus = bus;
        this.departureTime = departureTime;
        this.duration = duration;
        this.ticketPriceInEuro = ticketPriceInEuro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getOrigin() {
        return origin;
    }

    public void setOrigin(City origin) {
        this.origin = origin;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.duration = Duration.between(departureTime, arrivalTime);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        this.arrivalTime = departureTime.plus(duration);
    }

    public BigDecimal getTicketPriceInEuro() {
        return ticketPriceInEuro;
    }

    public void setTicketPriceInEuro(BigDecimal ticketPriceInEuro) {
        this.ticketPriceInEuro = ticketPriceInEuro;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((bus == null) ? 0 : bus.hashCode());
        result = prime * result + ((departureTime == null) ? 0 : departureTime.hashCode());
        result = prime * result + ((arrivalTime == null) ? 0 : arrivalTime.hashCode());
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + ((ticketPriceInEuro == null) ? 0 : ticketPriceInEuro.hashCode());
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
        Trip other = (Trip) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (origin == null) {
            if (other.origin != null)
                return false;
        } else if (!origin.equals(other.origin))
            return false;
        if (destination == null) {
            if (other.destination != null)
                return false;
        } else if (!destination.equals(other.destination))
            return false;
        if (bus == null) {
            if (other.bus != null)
                return false;
        } else if (!bus.equals(other.bus))
            return false;
        if (departureTime == null) {
            if (other.departureTime != null)
                return false;
        } else if (!departureTime.equals(other.departureTime))
            return false;
        if (arrivalTime == null) {
            if (other.arrivalTime != null)
                return false;
        } else if (!arrivalTime.equals(other.arrivalTime))
            return false;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (ticketPriceInEuro == null) {
            if (other.ticketPriceInEuro != null)
                return false;
        } else if (!ticketPriceInEuro.equals(other.ticketPriceInEuro))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Trip [id=" + id + ", origin=" + origin + ", destination=" + destination + ", bus=" + bus
                + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", duration=" + duration
                + ", ticketPriceInEuro=" + ticketPriceInEuro + "]";
    }

    
}
