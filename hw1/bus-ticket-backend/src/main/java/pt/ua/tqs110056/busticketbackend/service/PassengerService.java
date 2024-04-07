package pt.ua.tqs110056.busticketbackend.service;

import java.util.Optional;

import pt.ua.tqs110056.busticketbackend.model.Passenger;

public interface PassengerService {

    Optional<Passenger> getPassengerById(Long id);

    Passenger savePassenger(Passenger passenger);

    void deletePassengerById(Long id);

}
