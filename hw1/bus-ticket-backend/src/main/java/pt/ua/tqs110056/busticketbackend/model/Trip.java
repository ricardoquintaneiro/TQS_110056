package pt.ua.tqs110056.busticketbackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private City origin;
    private City destination;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private float ticketPriceInEuro;

}
