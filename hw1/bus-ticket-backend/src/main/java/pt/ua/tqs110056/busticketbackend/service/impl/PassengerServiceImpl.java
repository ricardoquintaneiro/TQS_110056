package pt.ua.tqs110056.busticketbackend.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pt.ua.tqs110056.busticketbackend.model.Passenger;
import pt.ua.tqs110056.busticketbackend.repository.PassengerRepository;
import pt.ua.tqs110056.busticketbackend.service.PassengerService;

@Service
public class PassengerServiceImpl implements PassengerService {

    private PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Optional<Passenger> getPassengerById(Long id) {
        return passengerRepository.findById(id);
    }

    @Override
    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public void deletePassengerById(Long id) {
        passengerRepository.deleteById(id);
    }
    
}
