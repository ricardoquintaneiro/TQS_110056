package pt.ua.tqs110056.busticketbackend.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private City origin;
    
    @OneToOne
    private City destination;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Duration duration;

    private BigDecimal ticketPriceInEuro;

    public Trip() {
    }

    public Trip(City origin, City destination, LocalDateTime departureTime, LocalDateTime arrivalTime,
            BigDecimal ticketPriceInEuro) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketPriceInEuro = ticketPriceInEuro;
    }

    public Trip(City origin, City destination, LocalDateTime departureTime, Duration duration,
            BigDecimal ticketPriceInEuro) {
        this.origin = origin;
        this.destination = destination;
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
        this.duration = Duration.between(this.departureTime, this.arrivalTime);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        this.arrivalTime = this.departureTime.plus(duration);
    }

    public BigDecimal getTicketPriceInEuro() {
        return ticketPriceInEuro;
    }

    public void setTicketPriceInEuro(BigDecimal ticketPriceInEuro) {
        this.ticketPriceInEuro = ticketPriceInEuro;
    }

}
